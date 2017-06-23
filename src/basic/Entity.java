package basic;

import cn.fox.utils.Pair;

// Entity denotes a mention that occurs in the text.
public class Entity {
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(int id) {
		this.id = "E"+id;
	}

	public String type;
	public String fullName; // the full name of the entity
	// the begin (first character) and end (last character+1) position of the entity mention
	public Pair<Integer, Integer> span = new Pair<>(); 
	
	@Override
	public String toString() {
		return "fullname:"+fullName;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Entity))
			return false;
		Entity o = (Entity)obj;
		if(this.type.equals(o.type) && this.span.equals(o.span))
			return true;
		else 
			return false;
	}
	
	@Override
	public int hashCode() {
		int seed = 131; 
		int hash=0;
		hash = (hash * seed) + type.hashCode();  
		hash = (hash * seed) + span.hashCode();  
	    return hash;  
	}
}
