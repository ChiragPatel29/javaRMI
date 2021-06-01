package server;

import models.Record;
import models.StudentRecord;
import models.TeacherRecord;
import recordutils.RecordHelper;
import utilities.ServerLogger;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class CenterServer extends UnicastRemoteObject implements CenterServerI {
    HashMap<Character, ArrayList<Record>> records = new HashMap<>();
    ServerLogger serverLogger;
    String serverName;

    public CenterServer() throws RemoteException {
        super();
    }

    public CenterServer(String serverName) throws IOException {
        super();
        this.serverName = serverName;
        serverLogger = new ServerLogger(serverName);
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
        //if (checkMissingValuesForTeacher(firstName, lastName, address, phone, specialization, location) == null) {
        TeacherRecord teacherRecord = new TeacherRecord(firstName, lastName, address, Long.parseLong(phone), specialization, location);
        Character key = lastName.toUpperCase().charAt(0);
        if (records.get(key) == null) {
            System.out.println("New List of Records is created");
            ArrayList<Record> newList = new ArrayList<Record>();
            newList.add(teacherRecord);
            records.put(key, newList);
            serverLogger.logger.log(Level.INFO,"Record Added with Last Name:"+lastName+" successfully");
            return "Record Added successfully";

        } else {
            System.out.println("Value will be added in existing list of records");
            ArrayList<Record> existingList = records.get(key);
            existingList.add(teacherRecord);
            serverLogger.logger.log(Level.INFO,"Record Added with Last Name:"+lastName+" successfully");
            return "Record Added Successfully";
        }

        // else checkMissingValuesForTeacher(firstName, lastName, address, phone, specialization, location);

        //return null;
    }

    @Override
    public String createSRecord(String firstName, String lastName, List<String> courseRegistered, String status, String statusDate) throws RemoteException {
        StudentRecord studentRecord = new StudentRecord(firstName, lastName, courseRegistered, status, statusDate);
        Character key = lastName.toUpperCase().charAt(0);
        if (records.get(key) == null) {
            System.out.println("New List of Records is created");
            ArrayList<Record> newList = new ArrayList<Record>();
            newList.add(studentRecord);
            records.put(key, newList);
            serverLogger.logger.log(Level.INFO,"Record Added with Last Name:"+lastName+" successfully");
            return "Record Added successfully";

        } else {
            System.out.println("Value will be added in existing list of records");
            ArrayList<Record> existingList = records.get(key);
            existingList.add(studentRecord);
            serverLogger.logger.log(Level.INFO,"Record Added with Last Name:"+lastName+" successfully");
            return "Record Added Successfully";
        }
    }

    @Override
    public String getRecordCounts() throws RemoteException {
        //Yet to be Implemented
        return null;
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

    public void printRecords() {
        for (Character crecord : records.keySet()) {
            ArrayList<Record> krecord = records.get(crecord);
            for (Record record : krecord) {
                System.out.println(record.recordID + " " + record.firstName + " " + record.lastName);
                if (record.recordID.substring(0, 2).equals("TR")) {
                    RecordHelper.printTeacherRecord((TeacherRecord) record);
                } else {
                    RecordHelper.printStudentRecord((StudentRecord) record);
                }

            }
        }
    }

}
