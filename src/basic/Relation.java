package basic;

import cn.fox.utils.Pair;

//Relation denotes a relation mention that occurs in the text.
public class Relation {
	// the begin (first character) and end (last character+1) position of the evidence mention of the relation
	public Pair<Integer, Integer> span = new Pair<>(); 
}
