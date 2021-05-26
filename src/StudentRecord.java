public class StudentRecord extends Record{
    String recordID;
    String coursesRegistered;
    String status;
    String statusDate;

    StudentRecord(String firstName,String lastName,String coursesRegistered,String status,String statusDate){
        super(firstName,lastName);
        this.recordID="SR"+Record.recordCounter;
        this.coursesRegistered=coursesRegistered;
        this.status=status;
        this.statusDate=statusDate;
        Record.recordCounter++;

    }


}
