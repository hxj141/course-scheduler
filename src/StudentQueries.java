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
public class StudentQueries {
    private static Connection connection;
    private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent_delete;
    private static PreparedStatement dropStudent_extract;
    private static PreparedStatement dropStudent_remove;
    private static PreparedStatement updateWaitlist_identify;
    private static PreparedStatement updateWaitlist_update;
    private static PreparedStatement dropStudent_update;
    private static ResultSet resultSet;
    private static ResultSet queuedCourse;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (id, firstname, lastname) values (?,?,?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
    }

    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> student = new ArrayList<StudentEntry>();
        try
        {
            getAllStudents = connection.prepareStatement("select id, firstname, lastname from app.student order by lastname, firstname");
            
            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                StudentEntry studentObj = new StudentEntry(resultSet.getString(1), resultSet.getString(2),resultSet.getString(3));
                student.add(studentObj);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
        
    }
    
    public static StudentEntry getStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        StudentEntry student = null;
        try
        {
            getStudent = connection.prepareStatement("select id, firstname, lastname from app.student where id=? order by lastname, firstname");
            getStudent.setString(1, studentID);
            resultSet = getStudent.executeQuery();
            
            while(resultSet.next())
            {
                StudentEntry studentObj = new StudentEntry(resultSet.getString(1), resultSet.getString(2),resultSet.getString(3));
                return studentObj;
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
        
    }
    public static StudentEntry dropStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        StudentEntry student = null;
        try
        {
            //Delete from list of students
            dropStudent_delete = connection.prepareStatement("delete from app.student where id=?");
            dropStudent_delete.setString(1, studentID);
            //Extract courses which need waitlists updated
            dropStudent_extract = connection.prepareStatement("select coursecode from app.schedule where studentid=? order by timestamp");
            dropStudent_extract.setString(1, studentID);
            resultSet = dropStudent_extract.executeQuery();
            //Remove student from waitlists
            dropStudent_remove = connection.prepareStatement("delete from app.schedule where studentid=?");
            dropStudent_remove.setString(1, studentID);
            dropStudent_delete.executeUpdate();
            dropStudent_remove.executeUpdate();
            
            
            while(resultSet.next())
            {

                
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return student;
        
    }
  public static void updateWaitlist()
    {
        connection = DBConnection.getConnection();
        try
        {
                // for each course, identify first person with "W" as status, then update them to "S"    
                updateWaitlist_identify = connection.prepareStatement("select top 1 (semester, studentid, coursecode) from app.schedule where status=? order by timestamp asc");
                updateWaitlist_identify.setString(1, "W");
                queuedCourse = updateWaitlist_identify.executeQuery();
                updateWaitlist_update = connection.prepareStatement("update app.schedule set status=? where semester=?, studentid=?, coursecode=?");
                updateWaitlist_update.setString(1, "S");
                updateWaitlist_update.setString(2, queuedCourse.getString(1));
                updateWaitlist_update.setString(3, queuedCourse.getString(2));
                updateWaitlist_update.setString(4, queuedCourse.getString(3));
        }
         catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
        
// where semester == -->> if statement