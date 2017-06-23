package knowledgegraph;

import java.util.ArrayList;
import java.util.List;

import basic.Entity;
import cn.fox.data_structure.GraphVertex;

/*
 *  A vertex in a knowledge graph denotes a set of entities that point to the same concept.
 */

public class KGvertex extends GraphVertex {
	public List<Entity> entities = new ArrayList<>();
	
	@Override
	public String toString() {
		String ret = "";
		for(int i=0;i<entities.size();i++) {
			if(i==0)
				ret += entities.get(i).toString();
			else
				ret += " # "+entities.get(i).toString();
		}
			

		return ret;
	}
}
