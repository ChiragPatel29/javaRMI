package Models;

import java.util.List;

public class StudentRecord extends Record {
    List<String> coursesRegistered;
    String status;
    String statusDate;

    public StudentRecord(String firstName, String lastName, List<String> coursesRegistered, String status, String statusDate) {
        super("SR" + Record.recordCounter, firstName, lastName);
        this.coursesRegistered = coursesRegistered;
        this.status = status;
        this.statusDate = statusDate;
        Record.recordCounter++;

    }

    public List<String> getCoursesRegistered() {
        return coursesRegistered;
    }


    public String getStatus() {
        return status;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setCoursesRegistered(List<String> coursesRegistered) {
        this.coursesRegistered = coursesRegistered;

    }


    public void setStatus(String status) {
        this.status = status;

    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;

    }


    public String printRecord() {
        return "RecordID: " + this.recordID + " First Name: " + this.firstName + " LastName: "
                + this.lastName + " \n Courses Registered: " + this.coursesRegistered + " Status: " + this.status
                + " Status Date: " + this.statusDate;
    }


}
