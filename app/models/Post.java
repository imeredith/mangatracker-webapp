package models;
 
import java.util.*;
import javax.persistence.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import play.db.jpa.*;


 
@Entity
public class Post extends Model {
 
    public Date postedAt;
    
    @Lob
    public String body;
    
    public long user_id;

    public long[] to_ids;
    
    public Post(User user, String body) {
        this.user_id = user.id;
        this.body = body;
        this.postedAt = new Date();
        this.to_ids = Post.parse_for_uid(body);
    }

    public static long[] parse_for_uid(String body){
    	List uuids = new ArrayList();
    	Pattern pattern = Pattern.compile("(^|\\s)@(\\S+)(\\s|$)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(body);
    
        while (matcher.find()){
           User to =  User.find("byNameIlike", matcher.group(2)).first();
           	if(to != null){
           		uuids.add(to.id);
        	}
        }

        long[] ret = new long[uuids.size()];
	    Iterator iterator = uuids.iterator();
	    for (int index = 0; index < ret.length; index++) {
	        ret[index] = (Long)iterator.next();
	    }
        return ret;
    }
 
}