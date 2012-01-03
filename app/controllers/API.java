package controllers;

import play.*;
import play.mvc.*;

import java.util.*;
import play.mvc.Scope.Session;
import models.*;

public class API extends Controller {
	
	 @Before
	 	 public static void check_api_key(){
	 		if(User.find("api_key",params.get("api_key").trim()).first() == null){
	 			renderJSON(new JSON("false"));
	 		}
	 }
	
	 public static void index() {
    	  //List mangas = Manga.find("user", "aaron").fetch();
        render(/*mangas*/);
    }
    
    public static void add_chapter(Chapter chapter, String manga_name,String api_key){
		User user = User.find("api_key",api_key).first();
		if(user != null) {
			Manga manga = Manga.get_or_create(user, manga_name);
			
			chapter.user_id = user.id;
			chapter.manga_id = manga.id;
			chapter.added = System.currentTimeMillis();
			
			boolean dosent_exist = chapter.find("manga_id = ? and issue = ? and user_id = ?", manga.id , chapter.issue , user.id ).first() == null;
			
			if(dosent_exist){
                if(Chapter.count("user_id",user.id) < 1){
                    Log.win(user.name + " - " + user.email + " First API use");
                }
                Log.general(manga.name + " added chapter");
				chapter.save();
				manga.updated = System.currentTimeMillis();
				manga.save();
			}    
		} 
    }
    
    public static void check_apikey(String api_key){
    		User user = User.find("api_key",params.get("api_key")).first();
    		user.password = "";
	 		renderJSON(user);	 		
    }

    /**

# Users schema
 
# --- !Ups
 
CREATE TABLE User (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    email varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    name varchar(255) NOT NULL,
    api_key varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO User
password, name, email)
VALUES
('ayubu', '14235a12', 'aaron@aaron-m.co.nz');
 
CREATE TABLE Role (
	id bigint(20) NOT NULL AUTO_INCREMENT,
    role_type varchar(255) NOT NULL,
    user_id bigint(20) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO Role
(user_id, role_type)
SELECT id, 'ADMIN'
FROM User
WHERE name = 'ayubu';

CREATE TABLE Log (
	id bigint(20) NOT NULL AUTO_INCREMENT,
    user_id bigint(20) NOT NULL,
    log_type varchar(255) NOT NULL,
    body text NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Manga (
	id bigint(20) NOT NULL AUTO_INCREMENT,
    user_id bigint(20) NOT NULL,
    added bigint(20) NOT NULL,
    updated bigint(20) NOT NULL,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Chapter (
	id bigint(20) NOT NULL AUTO_INCREMENT,
    user_id bigint(20) NOT NULL,
    manga_id bigint(20) NOT NULL,

    issue varchar(255) NOT NULL,
    url varchar(255) NOT NULL,
    host varchar(255) NOT NULL,
    added bigint(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE Post (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    body text NOT NULL,
    postedAt date NOT NULL,
    user_id bigint(20) NOT NULL,
    PRIMARY KEY (id)
);


# --- !Downs

drop table Role;
drop table User;
    */
}