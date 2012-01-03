package models;
import play.*;
import play.db.jpa.*;
import play.data.validation.*;
import javax.persistence.*;
import java.util.*;

@Entity
public class JSON extends Model{
	public String accepted;
	
	public JSON(String accepted){
		this.accepted = accepted;
	}
}