package controllers;

import play.*;
import play.mvc.*;

import java.util.*;
import play.mvc.Scope.Session;
import models.*;
import java.util.regex.*;
import models.Role.RoleType;

public class Application extends Controller {
	
	@Before
	public static void checkPermissions() {
        String loggedIn = session.get("username");
        System.out.println(Log.LogType.GENERAL);
        if(loggedIn == null){
            flash.put("url", "GET".equals(request.method) ? request.url : "/");
            LoginRegister.userlogin();
        }else{
            User logged_in = User.get_current_user();
            if(logged_in.in_role(RoleType.ADMIN)) {
                renderArgs.put("admin", logged_in);
            }
            renderArgs.put("logged_in", logged_in);
        }
    }
	
    public static void index() {
    	User logged_in =  User.get_current_user();
        String api_key = logged_in.api_key;
       
        render(api_key);
    } 
    
    public static void editprofile() {
        User user_data =  User.get_current_user();
        render(user_data);
    }

    public static void viewprofile(long id) {
        User view =  User.find("id", id).first();
        render(view);
    }

    public static void edit_me(User user, String confirm) {
        List<String> success = new ArrayList<String>();
        User user_data =  User.get_current_user();

        //validate username
        {
            if(user.name != null && !user.name.isEmpty() && !user.name.equals(user_data.name)){
                boolean found = User.find("name",user.name).first() != null;
                if(found) {
                    validation.addError("user.name","validation.unique");
                } else {
                    success.add("Your name has been changed");
                    user_data.name = user.name;
                    session.put("username", user_data.name);
                }
            }
        }
        //validate email
        {
            if(user.email != null && !user.email.isEmpty() && !user.email.equals(user_data.email)) {
                boolean found = User.find("email",user.email).first() != null;
                play.data.validation.Error error = validation.email("user.email",user.email).error;
                if(found){
                    validation.addError("user.email","validation.unique");
                }else if(error == null) {
                    success.add("Your email has been Changed");
                    user_data.email = user.email;
                }
            }
        }
        //validate password
        {
            if( user.password != null && !user.password.isEmpty() ) {

                play.data.validation.Error error_match = validation.equals("confirm",user.password,"password",confirm).error;
                play.data.validation.Error error_length = validation.minSize("user.password",user.password,5).error;

                if(error_match == null && error_length ==null ){
                    user_data.password = user.password;
                    success.add("Your password has been changed");
                }
            }
            
        }
        if(validation.hasErrors()){
            params.flash();
            validation.keep();
            flash.keep();
            
        }
        user_data.save();
        render("@editprofile", user_data, success);
    }

    
    public static void manga_list(){
		User logged_in =  User.get_current_user();
		if(logged_in != null){
    		List<Manga> mangas = Manga.find("user_id",logged_in.id).fetch();
    		List<Chapter> chapters = Chapter.find("user_id = ?", logged_in.id).fetch();
    		render(mangas,chapters);
		}
		render();
    }
    
    public static void get_chapter_list(Long mid){
		User logged_in =  User.get_current_user();
		List<Chapter> cs = Chapter.find("manga_id = ? and user_id =?",mid,logged_in.id).fetch();
		renderJSON(cs);
    }

    public static void set_post(Post post) {
        post.to_ids = Post.parse_for_uid(post.body);
        post.postedAt = new Date();
        post.save();
        renderJSON(post);
    }

    public static void get_post(long id) {
        //List<Post> p = Post.find("id > ?",id).fetch();
        List<Post> p = Post.find("postedAt >= DATE_SUB(now(),INTERVAL ? MINUTE)", 1).fetch();
        renderJSON(p);
    }

    public static void noaccess(){
        render();
    }



}
