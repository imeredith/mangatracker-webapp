package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import play.data.validation.*;
import play.mvc.Scope.Session;
import models.*;
import models.Role.RoleType;
import com.google.gson.*;

public class Admin extends Controller {

    @Before
    public static void checkPermissions() {
        String loggedIn = session.get("username");
        if(loggedIn == null){
            flash.put("url", "GET".equals(request.method) ? request.url : "/");
            LoginRegister.userlogin();
        } else {
            User logged_in = User.get_current_user();
            if(!logged_in.in_role(RoleType.ADMIN)) {
                Application.noaccess();
            }
            renderArgs.put("admin", logged_in);
            renderArgs.put("logged_in", logged_in);
        }
    }

    public static void log() {
        User logged_in =  User.get_current_user();

        List<Log> logs = Log.findAll();
        render(logs);
    }
    
}