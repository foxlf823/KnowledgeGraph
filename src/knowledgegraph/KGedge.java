package knowledgegraph;

import basic.Relation;
import cn.fox.data_structure.GraphEdge;

/*
 *  A edge in a knowledge graph denotes a relation between two entities.
 *  Since a vertex may contain several entities, there may be several edges between two vertices.
 */

public class KGedge extends GraphEdge {
	public Relation relation;
	
	@Override
	public String toString() {
		return relation.toString();
	}
}
