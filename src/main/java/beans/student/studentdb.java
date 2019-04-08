package beans.student;

import  beans.student.*;
import java.sql.*;
import java.text.SimpleDateFormat;



public class studentdb {

    student student;
    public studentdb(student student) {
        this.student = student;
    }

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/student_management";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "12345";
    Connection conn;
    public void saveUser()
    {
        System.out.println("saving user===");
        try {

            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Inserting records into the table...");
            PreparedStatement ps = conn.prepareStatement("INSERT INTO student ( name,phone_no,dob,address,attendance,username,password) VALUES ( ?,?,?,?,?,?,?)");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // your template here
            java.util.Date dateStr = formatter.parse(student.getDob());
            java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
            ps.setString(1, student.getName());
            ps.setString(2,student.getPhone_no());
            ps.setDate(3,dateDB);
            ps.setString(4,student.getAddress());
            ps.setFloat(5,student.getAttendance());
            ps.setString(6,student.getName());
            ps.setString(7,student.getName());

            ps.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM student WHERE username=?");
            ps2.setString(1, student.getName());
            ResultSet rs = ps2.executeQuery();
            if (rs.last()) {
                System.out.println("found==");
                System.out.println(rs.getInt("id"));
                PreparedStatement ps1 = conn.prepareStatement("Insert into marks (student_id, marks_iat1 , marks_iat2, marks_iat3) VALUES (?, ?, ?, ? )");
                ps1.setInt(1, rs.getInt("id"));
                ps1.setInt(2, student.getMarks_iat1());
                ps1.setInt(3, student.getMarks_iat2());
                ps1.setInt(4, student.getMarks_iat3());
                ps1.executeUpdate();
            } else {
                System.out.println("not found==");

            }
            System.out.println("Inserted records into the table...");

        } catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("exception"+se);
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            System.out.println("exceptio1"+e);
        }
    }
}
