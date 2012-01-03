package models;
import play.*;
import play.db.jpa.*;
import org.omg.DynamicAny.NameValuePair;
import javax.persistence.*;
import java.util.*;
import play.data.validation.*;
import play.mvc.Scope.Session;
import play.libs.Codec;

@Entity
public class User extends Model{
	public String api_key;
	@Required
	@Email
	@Unique(message = "User with this email already registered")
	public String email;
	@Required
	@Unique(message = "Login name already taken")
	public String name;
	@Required
	@MinSize(value=5, message = "validation.minlength")
	@Password
	public String password;
	
	public User(String name, String email, String password){
		this.email = email;
		this.name = name;
		this.password = password;
	}
	
	public static String generate_apikey(){
		return Codec.UUID();
	}
	
	public static User get_current_user(){
		Session session = Session.current();
		String username = session.get("username");
		if(username == null){
			return null;
		}
		User user = User.find("name", username).first();
		return user;
	}

	public boolean in_role(Role.RoleType role_type){
		Role role = Role.find("user_id",this.id).first();
		if(role != null && role.role_type.equals(role_type)){
			return true;
		}
		return false;
	}

	public void add_role(Role.RoleType role_type){
		Role role = new Role(this, role_type);
		role.save();
	}
}