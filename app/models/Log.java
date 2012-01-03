package models;
import play.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class Log extends Model{
	
	public static enum LogType {
 		GENERAL, ERROR, UNEXPECTED, WIN;  //; is optional
	}

	@Enumerated(EnumType.STRING)
	public LogType log_type;

	public String body;

	public long user_id;

	public Log(LogType type, String body){
		this.log_type = type;
		this.body = body;
	}

	public Log(User user, LogType type, String body){
		this.user_id = user.id;
		this.log_type = type;
		this.body = body;
	}

	public static Log general(String msg){
		Log log = null;
		User user = User.get_current_user();
		if(user != null)
			log = new Log(user, LogType.GENERAL, msg);
		else
			log = new Log(LogType.GENERAL, msg);
		log.save();
		return log;			
	}

	public static Log error(String msg){
		Log log = null;
		User user = User.get_current_user();
		if(user != null)
			log = new Log(user, LogType.ERROR, msg);
		else
			log = new Log(LogType.ERROR, msg);
		log.save();
		return log;	
	}

	public static Log unexpected(String msg){
		Log log = null;
		User user = User.get_current_user();
		if(user != null)
			log = new Log(user, LogType.UNEXPECTED, msg);
		else
			log = new Log(LogType.UNEXPECTED, msg);
		log.save();
		return log;	
	}

	public static Log win(String msg){
		Log log = null;
		User user = User.get_current_user();
		if(user != null)
			log = new Log(user, LogType.WIN, msg);
		else
			log = new Log(LogType.WIN, msg);
		log.save();
		return log;
	}
}