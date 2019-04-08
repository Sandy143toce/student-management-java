package beans.student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class studentupdate {

    student stdobject;
    public studentupdate(student stdobject) { this.stdobject = stdobject;
    }

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/student_management";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "12345";
    Connection conn;
    public void saveUser()
    {
        System.out.println("updating user===");
        try {

            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
            System.out.println("Updating records in the table...");
            PreparedStatement ps = conn.prepareStatement("UPDATE student SET name = ? , phone_no= ? , dob=? ,address=? , attendance=? WHERE id = ?");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); // your template here
            java.util.Date dateStr = formatter.parse(stdobject.getDob());
            java.sql.Date dateDB = new java.sql.Date(dateStr.getTime());
            ps.setString(1, stdobject.getName());
            ps.setString(2,stdobject.getPhone_no());
            ps.setDate(3,dateDB);
            ps.setString(4,stdobject.getAddress());
            ps.setFloat(5,stdobject.getAttendance());
            ps.setInt(6,stdobject.getId());

            PreparedStatement ps1 = conn.prepareStatement("UPDATE marks SET marks_iat1=? , marks_iat2=? , marks_iat3=? where id=?");

            ps1.setInt(1,stdobject.getMarks_iat1());
            ps1.setInt(2,stdobject.getMarks_iat2());
            ps1.setInt(3,stdobject.getMarks_iat3());
            ps1.setInt(4,stdobject.getId());


            ps.executeUpdate();
            ps1.executeUpdate();
            System.out.println("Updated records of the table...");

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
