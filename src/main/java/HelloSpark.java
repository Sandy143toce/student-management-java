
import static spark.Spark.*;
import beans.student.*;
import beans.teacher.*;
import com.google.gson.Gson;

import java.util.ArrayList;

import com.google.gson.JsonObject;
import org.json.JSONArray;

public class HelloSpark {
    public static void main(String[] args) {

        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        get("/hello", (req, res) -> "Hello SandipDas");

        post("/api/student", (req, res) -> {
            System.out.println(req.body());
            student user = null;
            JsonObject json_obj = new JsonObject();
            try {

                user = new Gson().fromJson(req.body(), student.class);

                System.out.println("user is==" + user.getName());
                studentdb std = new studentdb(user);
                std.saveUser();
                json_obj.addProperty("status",true);

            } catch (Exception e) {
                System.out.println("exception is==" + e);
            }
            return json_obj;
        });
        get("api/studentdata", (req, res) -> {
            // Show somethin
            studentdetails data=new studentdetails();
            ArrayList<student> std;
            std = data.retrivedata();
            JSONArray jsArray = new JSONArray(std);
            return jsArray;
        });
        put("/api/student/:id", (req, res) -> {
            System.out.println(req.body() + " " + req.params(":id"));
            student std_obj = null;
            JsonObject json_obj = new JsonObject();
            try {
                std_obj = new Gson().fromJson(req.body(), student.class);
                std_obj.setId(Integer.parseInt(req.params(":id")));
                studentupdate student_obj = new studentupdate(std_obj);
                student_obj.saveUser();
                json_obj.addProperty("status",true);

            } catch (Exception e) {
                System.out.println("exception is==" + e);
            }
            return json_obj;
        });
        get("/api/student/:id", (req,res) -> {

            int id=Integer.parseInt(req.params(":id"));
            singlestudentdetails student_obj= new singlestudentdetails();

            student std = student_obj.retrivedata(id);
            Gson gson = new Gson();
            String json_obj = gson.toJson(std);
            return json_obj;


        });
        post("/api/login/teacher", (req, res) -> {
            teacher user_obj;
            boolean status;
            JsonObject json_obj = new JsonObject();
            try {
                user_obj = new Gson().fromJson(req.body(), teacher.class);
                teacher_login login_obj=new teacher_login(user_obj);
                status = login_obj.login();
                json_obj.addProperty("status",status);
            }catch (Exception e) {
                System.out.println("exception is==" + e);
            }
            return json_obj;

        });
        post("/api/login/student", (req, res) -> {
            student user_obj;
            int student_id;
            JsonObject json_obj = new JsonObject();
            try {
                user_obj = new Gson().fromJson(req.body(), student.class);
                student_login login_obj=new student_login(user_obj);
                student_id = login_obj.login();
                json_obj.addProperty("student_id",student_id);
            }catch (Exception e) {
                System.out.println("exception is==" + e);
            }
            return json_obj;

        });

    }
}
