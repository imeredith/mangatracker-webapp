package models;
import play.*;
import play.db.jpa.*;
import org.omg.DynamicAny.NameValuePair;
import javax.persistence.*;
import java.util.*;
import play.data.validation.*;
import play.libs.Codec;

@Entity
public class Role extends Model{
	
	public static enum RoleType {
 		ADMIN, UBER_ADMIN;  //; is optional
	}
	@Required
	public RoleType role_type;
	@Unique
	@Required
	public long user_id;

	
	public Role(User user, RoleType role_type){
		this.role_type = role_type;
		this.user_id = user.id;
	}
}