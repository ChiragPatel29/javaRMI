package models;

public class Record {
    public String recordID;
    public String firstName;
    public String lastName;
    static int recordCounter=10000;
    Record(String recordID,String firstName,String lastName){
        this.recordID=recordID;
        this.firstName=firstName;
        this.lastName=lastName;
    }

}
