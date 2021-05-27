package models;

public class TeacherRecord extends Record {
    String address;
    long phoneNo;
    String specialization;
    String location;

    public TeacherRecord(String firstName, String lastName, String address, long phoneNo, String specialization, String location) {
        super("TR" + Record.recordCounter, firstName, lastName);
        this.address = address;
        this.phoneNo = phoneNo;
        this.specialization = specialization;
        this.location = location;
        Record.recordCounter++;
    }

    public String getAddress() {
        return address;
    }

    public long getPhoneNo() {
        return phoneNo;
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
        this.phoneNo = phoneNo;

    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;

    }

    public void setLocation(String location) {
        this.location = location;

    }

}
