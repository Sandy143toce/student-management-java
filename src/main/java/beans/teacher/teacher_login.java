package beans.teacher;
import beans.teacher.*;
import beans.student.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class teacher_login {
    teacher user_obj;
    public teacher_login(teacher user_obj)
    {
        this.user_obj=user_obj;
    }


    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/student_management";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "12345";
    Connection conn;

    public boolean login() {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            //STEP 3: Open a connection
            System.out.println("Connecting to a selected database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected database successfully...");

            //STEP 4: Execute a query
                PreparedStatement ps = conn.prepareStatement("SELECT * FROM teacher WHERE username=? AND password=?");
                ps.setString(1, user_obj.getUsername());
                ps.setString(2, user_obj.getPassword());
                ResultSet rs = ps.executeQuery();
                if (rs.last()) {
                    System.out.println("found==");
                    return true;
                } else {
                    System.out.println("not found==");
                    return false;
                }

        }catch (SQLException se) {
            //Handle errors for JDBC
            System.out.println("exception"+se);
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            System.out.println("exceptio1"+e);
        }
        return false;
    }

}
