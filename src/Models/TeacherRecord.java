package Models;

public class TeacherRecord extends Record {
    String address;
    long phone;
    String specialization;
    String location;

    public TeacherRecord(String firstName, String lastName, String address, long phoneNo, String specialization, String location) {
        super("TR" + Record.recordCounter, firstName, lastName);
        this.address = address;
        this.phone = phoneNo;
        this.specialization = specialization;
        this.location = location;
        Record.recordCounter++;
    }

    public String getAddress() {
        return address;
    }

    public long getPhoneNo() {
        return phone;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getLocation() {
        return location;
    }

    public void setAddress(String address) {
        this.address = address;

    }

    public void setPhoneNo(long phoneNo) {
        this.phone = phoneNo;

    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;

    }

    public void setLocation(String location) {
        this.location = location;

    }

}
