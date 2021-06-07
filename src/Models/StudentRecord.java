package Models;

import java.util.List;

public class StudentRecord extends Record {
    List<String> courseRegistered;
    String status;
    String statusDate;

    public StudentRecord(String firstName, String lastName, List<String> coursesRegistered, String status, String statusDate) {
        super("SR" + Record.recordCounter, firstName, lastName);
        this.courseRegistered = coursesRegistered;
        this.status = status;
        this.statusDate = statusDate;
        Record.recordCounter++;

    }

    public List<String> getCoursesRegistered() {
        return courseRegistered;
    }


    public String getStatus() {
        return status;
    }

    public String getStatusDate() {
        return statusDate;
    }

    public void setCoursesRegistered(List<String> coursesRegistered) {
        this.courseRegistered = coursesRegistered;

    }


    public void setStatus(String status) {
        this.status = status;

    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;

    }


}
