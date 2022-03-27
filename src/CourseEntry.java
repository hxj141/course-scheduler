/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author thonkpad
 */
public class CourseEntry {
    private String courseID;
    private String description;
    private String semester;
    private int seats;

    public String getCourseID() {
        return courseID;
    }

    public String getDescription() {
        return description;
    }

    public String getSemester() {
        return semester;
    }

    public int getSeats() {
        return seats;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public CourseEntry(String courseID, String semester, String description, Integer seats) {
        this.courseID = courseID;
        this.semester = semester;
        this.description = description;
        this.seats = seats;
    }
  
    
}
