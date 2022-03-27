/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author acv
 */
public class CourseQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement getAllCourses;
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getCourseSeats;
    private static PreparedStatement dropCourse_delete;
    private static PreparedStatement dropCourse_remove;
    private static ResultSet resultSet;
    
    public static void addCourse(CourseEntry course)
    {
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (id, semester, description, seats) values (?,?,?,?)");
            addCourse.setString(1, course.getCourseID());
            addCourse.setString(2, course.getSemester());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
    

    public static ArrayList<CourseEntry> getAllCourses(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> course = new ArrayList<CourseEntry>();
        try
        {
            getAllCourses = connection.prepareStatement("select id, semester, description, seats from app.course where semester=? order by id");
            getAllCourses.setString(1,semester);
            resultSet = getAllCourses.executeQuery();
            
            while(resultSet.next())
            {
                // add columns to make object again
                CourseEntry courseObj = new CourseEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
                course.add(courseObj);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return course;
        
    }
    
  public static ArrayList<String> getAllCourseCodes(String semester)
    {
        connection = DBConnection.getConnection();
        ArrayList<String> courseID = new ArrayList<String>();
        try
        {
            getAllCourseCodes = connection.prepareStatement("select id from app.course where semester=? order by id");
            getAllCourseCodes.setString(1,semester);
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                courseID.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseID;
        
    }
    
      
  public static int getCourseSeats(String semester, String courseID)
    {
        connection = DBConnection.getConnection();
        int seats = 0;
        try
        {
            getCourseSeats = connection.prepareStatement("select seats from app.course where semester=? and id=?");
            getCourseSeats.setString(1,semester);
            getCourseSeats.setString(2,courseID);
            resultSet = getCourseSeats.executeQuery();
            
            while(resultSet.next())
            {
                seats = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return seats;
        
    }
    public static void dropCourse(String semester, String courseID)
    {
        connection = DBConnection.getConnection();
        try
        {
            //delete from list of courses
            dropCourse_delete = connection.prepareStatement("delete from app.course where semester=? and id=?");
            dropCourse_delete.setString(1,semester);
            dropCourse_delete.setString(2,courseID);
            //remove from schedules
            dropCourse_remove = connection.prepareStatement("delete from app.schedule where semester=? and coursecode=?");
            dropCourse_remove.setString(1,semester);
            dropCourse_remove.setString(2,courseID);
            dropCourse_delete.executeUpdate();
            dropCourse_remove.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }
}
