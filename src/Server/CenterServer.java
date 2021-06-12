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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        udpServer = new UDPServer(this);
        udpServer.start();
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
            serverLogger.addLog("New Teacher Record Created successfully with ID:" + teacherRecord.getRecordID());
            serverLogger.addLog(teacherRecord.printRecord());
            return "New Teacher Record Created successfully with ID:" + teacherRecord.getRecordID();

        } else {
            ArrayList<Record> existingList = records.get(key);
            existingList.add(teacherRecord);
            serverLogger.addLog("New Teacher Record Created successfully with ID:" + teacherRecord.getRecordID());
            serverLogger.addLog(teacherRecord.printRecord());
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
            serverLogger.addLog("New Student Record Created successfully with ID:" + studentRecord.getRecordID());
            serverLogger.addLog(studentRecord.printRecord());
            return "New Student Record Created successfully with ID:" + studentRecord.getRecordID();

        } else {
            ArrayList<Record> existingList = records.get(key);
            existingList.add(studentRecord);
            serverLogger.addLog("New Student Record Created successfully with ID:" + studentRecord.getRecordID());
            serverLogger.addLog(studentRecord.printRecord());
            return "New Student Record Created successfully with ID:" + studentRecord.getRecordID();
        }
    }

    public int getCurrentServerCount() {
        int count = 0;
        for (Character firstLetter : records.keySet()) {
            ArrayList<Record> krecord = records.get(firstLetter);
            count += krecord.size();
        }
        return count;
    }

    @Override
    synchronized public String getRecordCounts() throws RemoteException {


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
                    serverLogger.addLog("Getting counts from another server");
                    byte[] buffer = echoString.getBytes();

                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, Configurations.getUDPPort(location));
                    datagramSocket.send(packet);

                    byte[] buffer2 = new byte[50];
                    packet = new DatagramPacket(buffer2, buffer2.length);
                    datagramSocket.receive(packet);
                    System.out.println("Count received is: " + new String(buffer2, 0, packet.getLength()));
                    serverLogger.addLog("Count received is: " + new String(buffer2, 0, packet.getLength()));
                    recordCount = new String(buffer2, 0, packet.getLength());
                    recordCounts.add(recordCount);

                } catch (SocketTimeoutException e) {
                    System.out.println("The socket timed out");
                    serverLogger.addLog("The socket timed out");
                } catch (IOException e) {
                    System.out.println("Client error: " + e.getMessage());
                    serverLogger.addLog("Client error: " + e.getMessage());
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
                        serverLogger.addLog(recordID + "Address edited successfully");
                        serverLogger.addLog(teacherRecord.printRecord());
                        return "Value edited successfully";
                    } else if (fieldName.equals("phone")) {
                        teacherRecord.setPhoneNo(Long.parseLong(newValue));
                        serverLogger.addLog(recordID + "Phone No edited successfully");
                        serverLogger.addLog(teacherRecord.printRecord());
                        return "Value edited successfully";
                    } else if (fieldName.equals("location")) {
                        teacherRecord.setLocation(newValue);
                        serverLogger.addLog(recordID + " Location edited successfully");
                        serverLogger.addLog(teacherRecord.printRecord());
                        return "Value edited successfully";
                    } else {
                        serverLogger.addLog("Doesn't allow to modification of " + fieldName);
                        return "Doesn't allow to modification of that field";
                    }
                }

            }
        }
        serverLogger.addLog("No such record found");
        return "No such record found";
    }

    public String editStudentRecord(String recordID, String fieldName, List<String> newValue) {
        System.out.println(recordID + fieldName + newValue);
        for (Character crecord : records.keySet()) {
            ArrayList<Record> krecord = records.get(crecord);
            for (Record record : krecord) {
                if (record.recordID.equals(recordID)) {
                    StudentRecord studentRecord = (StudentRecord) record;
                    if (fieldName.equals("status")) {
                        studentRecord.setStatus(newValue.get(0));
                        serverLogger.addLog(recordID + " status edited successfully");
                        serverLogger.addLog(studentRecord.printRecord());
                        return "Value edited successfully";
                    } else if (fieldName.equals("statusDate")) {
                        studentRecord.setStatusDate(newValue.get(0));
                        serverLogger.addLog(recordID + " statusDate edited successfully");
                        serverLogger.addLog(studentRecord.printRecord());
                        return "Value edited successfully";
                    } else if (fieldName.equals("coursesRegistered")) {
                        studentRecord.setCoursesRegistered(newValue);
                        serverLogger.addLog(recordID + " coursesRegistered edited successfully");
                        serverLogger.addLog(studentRecord.printRecord());
                        return "Value edited successfully";
                    } else {
                        serverLogger.addLog("Doesn't allow to modification of " + fieldName);
                        return "Doesn't allow to modification of that field";
                    }
                }

            }
        }
        serverLogger.addLog("No such record found");
        return "No such record found";
    }


    @Override
    synchronized public String editRecord(String recordID, String fieldName, List<String> newValue) throws RemoteException {
        String recordType = recordID.substring(0, 2);
        if (recordType.equals("TR"))
            return editTeacherRecord(recordID, fieldName, newValue.get(0));
        else
            return editStudentRecord(recordID, fieldName, newValue);
    }
}
