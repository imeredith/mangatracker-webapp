package models;
import play.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class Chapter extends Model{
	@Required		
	public Long user_id;
		
	@Required
	public Long manga_id;
	
	@Required
	public String issue;
	@Required
	public String url;
	@Required	
	public String host;
	@Required
	public Long added;
	
	public String toString(){
		return  issue; 
	}
	public Chapter(User user, Manga manga, String issue, String url, String host){
		this.user_id = user.id;
		this.manga_id = manga.id;
		this.issue = issue;
		this.url = url;
		this.host = host;
	}
}