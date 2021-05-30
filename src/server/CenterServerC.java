package server;

import models.Record;
import models.StudentRecord;
import models.TeacherRecord;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

public class CenterServerC implements CenterServerI {
    HashMap<Character, ArrayList<Record>> records = new HashMap<>();

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
            records.put(key, newList);
            return "Record Added successfully";

        } else {
            System.out.println("Value will be added in existing list of records");
            ArrayList<Record> existingList = new ArrayList<Record>();
            existingList.add(teacherRecord);
            return "Record Added Successfully";
        }

        // else checkMissingValuesForTeacher(firstName, lastName, address, phone, specialization, location);

        //return null;
    }

    @Override
    public String createSRecord(String firstName, String lastName, ArrayList<String> courseRegistered, String status, String statusDate) throws RemoteException {
        StudentRecord studentRecord = new StudentRecord(firstName, lastName, courseRegistered, status, statusDate);
        Character key = lastName.toUpperCase().charAt(0);
        if (records.get(key) == null) {
            System.out.println("New List of Records is created");
            ArrayList<Record> newList = new ArrayList<Record>();
            records.put(key, newList);
            return "Record Added successfully";

        } else {
            System.out.println("Value will be added in existing list of records");
            ArrayList<Record> existingList = new ArrayList<Record>();
            existingList.add(studentRecord);
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

    public String editStudentRecord(String recordID, String fieldName, ArrayList<String> newValue) {

        for (Character crecord : records.keySet()) {
            ArrayList<Record> krecord = records.get(crecord);
            for (Record record : krecord) {
                if (record.recordID.equals(recordID)) {
                    StudentRecord studentRecord = (StudentRecord) record;
                    if (fieldName.equals("status")) {
                        studentRecord.setStatus(newValue.get(0));
                        return "Value edited successfully";
                    } else if (fieldName.equals("phone")) {
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
    public String editRecord(String recordID, String fieldName, ArrayList<String> newValue) throws RemoteException {
        String recordType = recordID.substring(0, 2);
        if (recordType.equals("TR"))
            return editTeacherRecord(recordID, fieldName, newValue.get(0));
        else
            return editStudentRecord(recordID, fieldName, newValue);
    }

}
