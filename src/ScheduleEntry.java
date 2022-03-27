/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Timestamp;
/**
 *
 * @author thonkpad
 */
public class ScheduleEntry {
    private String courseID;
    private String semester;
    private String studentID;
    private String status;
    private Timestamp time;

    public ScheduleEntry(String courseID, String semester, String studentID, String status, Timestamp time) {
        this.courseID = courseID;
        this.semester = semester;
        this.studentID = studentID;
        this.status = status;
        this.time = time;
    }

    
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimestamp(Timestamp time) {
        this.time = time;
    }

    public String getCourseID() {
        return courseID;
    }

    public String getSemester() {
        return semester;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getStatus() {
        return status;
    }

    public Timestamp getTimestamp() {
        return time;
    }
    
    
    
}
