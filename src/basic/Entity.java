package basic;

import cn.fox.utils.Pair;

// Entity denotes a mention that occurs in the text.
public class Entity {
	public String fullName; // the full name of the entity
	// the begin (first character) and end (last character+1) position of the entity mention
	public Pair<Integer, Integer> span = new Pair<>(); 
}
