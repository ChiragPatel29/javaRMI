import models.Record;
import models.StudentRecord;
import models.TeacherRecord;
import recordutils.RecordHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static HashMap<Character, ArrayList<Record>>records=new HashMap<>();





    public static void main(String[] args) {
        char a='A';
        for(int i=0;i<26;i++){
            records.put((char) (a+i), new ArrayList<Record>());
        }
        RecordHelper.createTRecord("Joey","Paquet","Montreal",345666665,"APP","mtl",records);
        RecordHelper.createSRecord("Chirag","Patel","COMP 6231","active","01-01-2021",records);
        RecordHelper.createTRecord("Joey","Aquet","Montreal",345666665,"APP","mtl",records);
        RecordHelper.createTRecord("Joey","Ct","Montreal",345666665,"APP","mtl",records);

        RecordHelper.createSRecord("Chirag","Atel","COMP 6231","active","01-01-2021",records);
        RecordHelper.createSRecord("Chirag","Tel","COMP 6231","active","01-01-2021",records);
        RecordHelper.createTRecord("Joey","Quet","Montreal",345666665,"APP","mtl",records);
        RecordHelper.createTRecord("Joey","Et","Montreal",345666665,"APP","mtl",records);
        RecordHelper.createSRecord("Chirag","Rel","COMP 6231","active","01-01-2021",records);
        RecordHelper.createSRecord("Chirag","El","COMP 6231","active","01-01-2021",records);
        RecordHelper.createTRecord("Joey","Zt","Montreal",345666665,"APP","mtl",records);

        RecordHelper.printRecords(records);
    }
}
