package beans.student;

import  beans.student.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class singlestudentdetails {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/student_management";
    static final String USER = "root";
    static final String PASS = "12345";

    public student retrivedata(int student_id) {
        Connection conn = null;
        Statement stmt = null;
        try {
            //STEP 2: Register JDBC driver
            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql = "SELECT s.*,m.* from student as s left join marks as m on s.id=m.student_id where s.id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,student_id);
            ResultSet rs = ps.executeQuery();

            student std = new student();
            System.out.println("Hellllllllllllllloooooooooooooooo");
            while (rs.next()) {
                //Retrieve by column name

                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                String phone = rs.getString("Phone_no");
                String dob = rs.getString("DOB");
                String address = rs.getString("Address");
                Float attandance=rs.getFloat("attendance");
                int marks_iat1=rs.getInt("marks_iat1");
                int marks_iat2=rs.getInt("marks_iat2");
                int marks_iat3=rs.getInt("marks_iat3");
                std.setId(id);
                std.setName(name);
                std.setDob(dob);
                std.setAddress(address);
                std.setPhone_no(phone);
                std.setAttendance(attandance);
                std.setMarks_iat1(marks_iat1);
                std.setMarks_iat2(marks_iat2);
                std.setMarks_iat3(marks_iat3);
                //Display values
                System.out.print("ID: " + std.getId());
                System.out.print(", Name " + std.getName());
                System.out.print(", Phone_no : " + std.getPhone_no());
                System.out.println(", DOB: " + std.getDob());
                System.out.println(",Address:"+std.getAddress());
                System.out.println(", attandance: " + std.getAttendance());
                System.out.println("iat1:" + std.getMarks_iat1());
                System.out.println("iat2:" + std.getMarks_iat2());
                System.out.println("iat3:"+ std.getMarks_iat3());

            }
            rs.close();
            return  std;
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            System.out.println(se);
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            System.out.println(e);
        }
        return null;
    }
}
