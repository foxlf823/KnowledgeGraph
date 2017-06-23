package basic;

import cn.fox.utils.Pair;

//Relation denotes a relation mention that occurs in the text.
public class Relation {
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(int id) {
		this.id = "R"+id;
	}
	/*
	 *  the begin (first character) and end (last character+1) position of the evidence mention of the relation
	 *  For some relations, there may be no obvious evidence words so these fields may be none.
	 */
	public Pair<Integer, Integer> span = new Pair<>(); 
	public String evidence;
	
	public Entity argument1;
	public Entity argument2;
	public String type;
	// false-directed relation such as superior and subordinate, true-undirected relation such as friendship
	public boolean symmetry;
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof Relation))
			return false;
		Relation o = (Relation)obj;
		if(this.type.equals(o.type)) {
			if(symmetry) {
				if(argument1.equals(o.argument1) && argument2.equals(o.argument2))
					return true;
				else if(argument1.equals(o.argument2) && argument2.equals(o.argument1))
					return true;
				else 
					return false;
			} else {
				if(argument1.equals(o.argument1) && argument2.equals(o.argument2))
					return true;
				else
					return false;
			}
		} else
			return false;

	}
	
	@Override
	public int hashCode() {
		int seed = 131; 
		int hash=0;
		hash = (hash * seed) + type.hashCode();  
		hash = (hash * seed) + argument1.hashCode();  
		hash = (hash * seed) + argument2.hashCode(); 
	    return hash;  
	}
}
