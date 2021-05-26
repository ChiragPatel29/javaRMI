public class TeacherRecord extends Record{
    String recordID;
    String address;
    long phoneNo;
    String specialization;
    String location;

    TeacherRecord(String firstName,String lastName,String address,long phoneNo,String specialization,String location) {
        super(firstName,lastName);
        this.recordID="TR"+Record.recordCounter;
        this.address=address;
        this.phoneNo=phoneNo;
        this.specialization=specialization;
        this.location=location;
        Record.recordCounter++;
    }

}
