import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    static HashMap<Character, ArrayList<Record>>records=new HashMap<>();



    public static Character getFirstLetter(String lastName){
        return lastName.toUpperCase().charAt(0);
    }
    public static void createTRecord (String firstName, String lastName, String address, long phone, String specialization, String location){
        ArrayList<Record>recordList=records.get(getFirstLetter(lastName));
        Record newRecord=new TeacherRecord(firstName,lastName,address,phone,specialization,location);
        recordList.add(newRecord);
    }

    public static void createSRecord (String firstName, String lastName, String courseRegistered, String status, String statusDate){
        ArrayList<Record>recordList=records.get(getFirstLetter(lastName));
        Record newRecord =new StudentRecord(firstName, lastName, courseRegistered, status, statusDate);
        recordList.add(newRecord);
    }

    public static void printRecords (){
        for(Character crecord:records.keySet()){
            ArrayList<Record> krecord=records.get(crecord);
            for(Record record:krecord){
                System.out.println(record.firstName+record.lastName);
            }
        }
    }

    public static void main(String[] args) {
        char a='A';
        for(int i=0;i<26;i++){
            records.put((char) (a+i), new ArrayList<Record>());
        }
        createTRecord("Joey","Paquet","Montreal",345666665,"APP","mtl");
        createSRecord("Chirag","Patel","COMP 6231","active","01-01-2021");
        createTRecord("Joey","Aquet","Montreal",345666665,"APP","mtl");
        createTRecord("Joey","Ct","Montreal",345666665,"APP","mtl");

        createSRecord("Chirag","Atel","COMP 6231","active","01-01-2021");
        createSRecord("Chirag","Tel","COMP 6231","active","01-01-2021");
        createTRecord("Joey","Quet","Montreal",345666665,"APP","mtl");
        createTRecord("Joey","Et","Montreal",345666665,"APP","mtl");
        createSRecord("Chirag","Rel","COMP 6231","active","01-01-2021");
        createSRecord("Chirag","El","COMP 6231","active","01-01-2021");
        createTRecord("Joey","Zt","Montreal",345666665,"APP","mtl");

        printRecords();
    }
}
