package models;

import java.util.ArrayList;

public class StudentRecord extends Record {
    ArrayList<String> coursesRegistered;
    String status;
    String statusDate;

    public StudentRecord(String firstName, String lastName, ArrayList<String> coursesRegistered, String status, String statusDate) {
        super("SR" + Record.recordCounter, firstName, lastName);
        this.coursesRegistered = coursesRegistered;
        this.status = status;
        this.statusDate = statusDate;
        Record.recordCounter++;

    }

    public ArrayList<String> getCoursesRegistered() {
        return coursesRegistered;
    }


    public String getStatus() {
        return status;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setCoursesRegistered(ArrayList<String> coursesRegistered) {
        this.coursesRegistered = coursesRegistered;

    }


    public void setStatus(String status) {
        this.status = status;

    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;

    }


}
