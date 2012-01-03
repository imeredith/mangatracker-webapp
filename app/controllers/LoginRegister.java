package controllers;

import play.*;
import play.mvc.*;
import java.util.*;
import play.data.validation.*;
import play.mvc.Scope.Session;
import models.*;
import com.google.gson.*;

public class LoginRegister extends Controller {
    public static void login(String name, String password) {
            validation.required("name",name);
            validation.required("password",password);
            User user_found = User.find("name", name).first();
            if(validation.hasErrors() || user_found == null || !user_found.password.equals(password)) {
                 validation.addError("failed","Unknown username or password");
                 params.flash();
                 validation.keep();
                 userlogin();
            }
            session.put("username", user_found.name);
            String url = flash.get("url");
            redirect(url != null ? url : "/");
    }
    
    public static void logout(){
    	session.remove("username");
    	LoginRegister.userlogin();
    }
     
    public static void register(@Valid User user, String confirm) {

        validation.equals("confirm",user.password,"password",confirm);

        /*if(!user.password.equals(confirm)){
            validation.addError("confirm","validation.nomatch");
            validation.addError("user.password","validation.nomatch");
        }*/

		if(validation.hasErrors()){
            params.flash();
            validation.keep();
            flash.keep();
            userregister();
		}else{
            user.api_key = User.generate_apikey();
            user.save();
            Log.win(user.name + " - " + user.email + " has just signed up Success");
            login(user.name,user.password);
        }
    }

    public static void userlogin(){
        flash.keep();
        render();    
    }

    public static void userregister(){
        flash.keep();
        render();    
    }

    public static void get_logged_in(){
        User user = User.get_current_user();
        //user.password = "";
        //Gson gson = new Gson();
        boolean logged_in = user != null;
        renderJSON(logged_in);
    }
}