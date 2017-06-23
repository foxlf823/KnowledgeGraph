package utils;

import java.util.List;

import basic.Entity;
import basic.Relation;
import edu.stanford.nlp.ie.machinereading.structure.EntityMention;
import edu.stanford.nlp.ling.CoreLabel;
import knowledgegraph.KGvertex;

public class Miscellaneous {

	// Find the corresponding EntityMention in kg_entities
	public static Entity find(List<Entity> kg_entities, EntityMention entity, List<CoreLabel> tokens) {
		for(Entity kg_entity:kg_entities) {
	       CoreLabel tokenStart = tokens.get(entity.getExtentTokenStart());
	       CoreLabel tokenEnd = tokens.get(entity.getExtentTokenEnd());
			
	       if(kg_entity.span.a == tokenStart.beginPosition() && kg_entity.span.b == tokenEnd.endPosition()) {
	    	   return kg_entity;
	       }
		}
		
		return null;
	}
	
	// Find the relation with the same arguments and type but different direction
	public static Relation find(List<Relation> kg_relations, Relation relation) {
		for(Relation kg_relation:kg_relations) {
			if(relation.equals(kg_relation))
				return kg_relation;
		}
		return null;
	}
	
	public static KGvertex findVertexByEntity(List<KGvertex> vertices, Entity entity) {
		for(KGvertex vertex:vertices) {
			if(vertex.entities.contains(entity))
				return vertex;
		}
		return null;
	}
	
	
}
