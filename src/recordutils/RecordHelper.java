package recordutils;

import models.Record;
import models.StudentRecord;
import models.TeacherRecord;

import java.util.ArrayList;
import java.util.HashMap;

public class RecordHelper {
    public static Character getFirstLetter(String lastName) {
        return lastName.toUpperCase().charAt(0);
    }

    public static void createTRecord(String firstName, String lastName, String address, long phone, String specialization, String location, HashMap<Character, ArrayList<Record>> records) {
        ArrayList<Record> recordList = records.get(getFirstLetter(lastName));
        Record newRecord = new TeacherRecord(firstName, lastName, address, phone, specialization, location);
        recordList.add(newRecord);
    }

    public static void createSRecord(String firstName, String lastName, ArrayList<String> courseRegistered, String status, String statusDate, HashMap<Character, ArrayList<Record>> records) {
        ArrayList<Record> recordList = records.get(getFirstLetter(lastName));
        Record newRecord = new StudentRecord(firstName, lastName, courseRegistered, status, statusDate);
        recordList.add(newRecord);
    }

    public static void printTeacherRecord(TeacherRecord teacherRecord) {
        System.out.println(teacherRecord.getAddress() + " " +
                teacherRecord.getLocation() + " " +
                teacherRecord.getSpecialization() + " " +
                teacherRecord.getPhoneNo());
    }

    public static void printStudentRecord(StudentRecord studentRecord) {
        System.out.println(studentRecord.getStatus()+" "+studentRecord.getCoursesRegistered()+" "+studentRecord.getStatusDate());
    }

    public static void printRecords(HashMap<Character, ArrayList<Record>> records) {
        for (Character crecord : records.keySet()) {
            ArrayList<Record> krecord = records.get(crecord);
            for (Record record : krecord) {
                System.out.println(record.recordID + " " + record.firstName + " " + record.lastName);
                if (record.recordID.substring(0, 2).equals("TR")) {
                    printTeacherRecord((TeacherRecord) record);
                } else {
                    printStudentRecord((StudentRecord) record);
                }

            }
        }
    }
}
