package Server;

import Models.Record;
import Models.StudentRecord;
import Models.TeacherRecord;
import Utilities.Configurations;
import Utilities.ServerLogger;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class CenterServer extends UnicastRemoteObject implements CenterServerI {
    HashMap<Character, ArrayList<Record>> records = new HashMap<>();
    String serverName;
    String IPAddress;
    UDPServer udpServer;
    ServerLogger serverLogger;

    public CenterServer(String serverName) throws IOException {
        super();
        this.serverName = serverName;
        this.IPAddress = Configurations.getIP(serverName);
        serverLogger = new ServerLogger(serverName);
        System.out.println(serverName + " Started on: " + Configurations.getUDPPort(serverName));
        udpServer = new UDPServer(this);
        udpServer.start();
    }


    public String checkMissingValuesForTeacher(String firstName, String lastName, String address, String phone, String specialization, String location) {
        if (firstName.isBlank())
            return "First Name missing";
        if (lastName.isBlank())
            return "First Name missing";
        if (address.isBlank())
            return "address missing";
        if (phone.isBlank()) {
            return "Phone number missing";
        } else {
            try {
                long phoneNo = Long.parseLong(phone);
            } catch (NumberFormatException e) {
                return "Phone Number Invalid";
            }
        }
        if (specialization.isBlank())
            return "Specialization missing";
        if (location.isBlank())
            return "Location missing";
        return null;
    }


    @Override
    public String createTRecord(String firstName, String lastName, String address, String phone, String specialization, String location) throws RemoteException {
        TeacherRecord teacherRecord;
        synchronized (this) {
            teacherRecord = new TeacherRecord(firstName, lastName, address, Long.parseLong(phone), specialization, location);
        }

        Character key = lastName.toUpperCase().charAt(0);
        if (records.get(key) == null) {
            ArrayList<Record> newList = new ArrayList<>();
            newList.add(teacherRecord);
            records.put(key, newList);
            serverLogger.addLog("Record Added with Last Name: " + lastName + " successfully");
            return "New Teacher Record Created successfully with ID:" + teacherRecord.getRecordID();

        } else {
            ArrayList<Record> existingList = records.get(key);
            existingList.add(teacherRecord);
            serverLogger.addLog("Record Added with Last Name: " + lastName + " successfully");

            return "New Teacher Record Created successfully with ID:" + teacherRecord.getRecordID();
        }

    }

    @Override
    public String createSRecord(String firstName, String lastName, List<String> courseRegistered, String status, String statusDate) throws RemoteException {
        StudentRecord studentRecord;
        synchronized (this) {
            studentRecord = new StudentRecord(firstName, lastName, courseRegistered, status, statusDate);
        }

        Character key = lastName.toUpperCase().charAt(0);
        if (records.get(key) == null) {
            ArrayList<Record> newList = new ArrayList<>();
            newList.add(studentRecord);
            records.put(key, newList);
            serverLogger.addLog("Record Added with Last Name: " + lastName + " successfully");

            return "New Student Record Created successfully with ID:" + studentRecord.getRecordID();

        } else {
            ArrayList<Record> existingList = records.get(key);
            existingList.add(studentRecord);
            serverLogger.addLog("Record Added with Last Name: " + lastName + " successfully");

            return "New Student Record Created successfully with ID:" + studentRecord.getRecordID();
        }
    }

    synchronized public int getCurrentServerCount() {
        int count = 0;
        for (Character firstLetter : records.keySet()) {
            ArrayList<Record> krecord = records.get(firstLetter);
            count += krecord.size();
        }
        return count;
    }

    @Override
    public String getRecordCounts() throws RemoteException {


        ArrayList<String> recordCounts = new ArrayList<>();
        String recordCount;

        for (String location : Configurations.LOCATION_LIST) {
            if (location.equals(serverName)) {
                recordCounts.add(location + " " + getCurrentServerCount());
            } else {
                try {
                    InetAddress address = InetAddress.getLocalHost();
                    DatagramSocket datagramSocket = new DatagramSocket();

                    String echoString = "Getting counts from another server";

                    byte[] buffer = echoString.getBytes();

                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, Configurations.getUDPPort(location));
                    datagramSocket.send(packet);

                    byte[] buffer2 = new byte[50];
                    packet = new DatagramPacket(buffer2, buffer2.length);
                    datagramSocket.receive(packet);
                    System.out.println("Count received is: " + new String(buffer2, 0, packet.getLength()));

                    recordCount = new String(buffer2, 0, packet.getLength());
                    recordCounts.add(recordCount);

                } catch (SocketTimeoutException e) {
                    System.out.println("The socket timed out");
                } catch (IOException e) {
                    System.out.println("Client error: " + e.getMessage());
                }
            }
        }
        recordCount = "";
        for (int i = 0; i < 3; i++) {
            if (i == 2)
                recordCount += " " + recordCounts.get(i) + ".";
            else
                recordCount += " " + recordCounts.get(i) + ",";

        }
        return recordCount;
    }


    public String editTeacherRecord(String recordID, String fieldName, String newValue) {

        for (Character crecord : records.keySet()) {
            ArrayList<Record> krecord = records.get(crecord);
            for (Record record : krecord) {
                if (record.recordID.equals(recordID)) {
                    TeacherRecord teacherRecord = (TeacherRecord) record;
                    if (fieldName.equals("address")) {
                        teacherRecord.setAddress(newValue);
                        return "Value edited successfully";
                    } else if (fieldName.equals("phone")) {
                        teacherRecord.setPhoneNo(Long.parseLong(newValue));
                        return "Value edited successfully";
                    } else if (fieldName.equals("location")) {
                        teacherRecord.setLocation(newValue);
                        return "Value edited successfully";
                    } else {
                        return "Doesn't allow to modification of that field";
                    }
                }

            }
        }
        return "No such record found";
    }

    public String editStudentRecord(String recordID, String fieldName, List<String> newValue) {

        for (Character crecord : records.keySet()) {
            ArrayList<Record> krecord = records.get(crecord);
            for (Record record : krecord) {
                if (record.recordID.equals(recordID)) {
                    StudentRecord studentRecord = (StudentRecord) record;
                    if (fieldName.equals("status")) {
                        studentRecord.setStatus(newValue.get(0));
                        return "Value edited successfully";
                    } else if (fieldName.equals("statusDate")) {
                        studentRecord.setStatusDate(newValue.get(0));
                        return "Value edited successfully";
                    } else if (fieldName.equals("courseRegistered")) {
                        studentRecord.setCoursesRegistered(newValue);
                        return "Value edited successfully";
                    } else {
                        return "Doesn't allow to modification of that field";
                    }
                }

            }
        }
        return "No such record found";
    }


    @Override
    public String editRecord(String recordID, String fieldName, List<String> newValue) throws RemoteException {
        String recordType = recordID.substring(0, 2);
        if (recordType.equals("TR"))
            return editTeacherRecord(recordID, fieldName, newValue.get(0));
        else
            return editStudentRecord(recordID, fieldName, newValue);
    }
}
