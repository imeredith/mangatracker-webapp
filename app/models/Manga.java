package models;
import play.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class Manga extends Model{
	@Required
	public String name;
	
	@Required
	public Long user_id;
	
	@Required
	public Long added;
	
	@Required 
	public Long updated;
	
	public Manga(User user, String name){
		this.user_id = user.id;
		this.name = name;
		this.added = System.currentTimeMillis();
		this.updated = System.currentTimeMillis();
	}

	public static Manga get_or_create(User user, String name){
		Manga manga = Manga.find("user_id IS ? AND name IS ?", user.id, name).first();
		if(manga == null){
			manga = new Manga(user, name);
			manga.save();
		}
		return manga;
	}
}