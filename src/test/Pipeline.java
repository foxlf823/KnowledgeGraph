package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import basic.Country;
import basic.Entity;
import basic.Organization;
import basic.Person;
import basic.Relation;
import edu.stanford.nlp.ie.machinereading.structure.EntityMention;
import edu.stanford.nlp.ie.machinereading.structure.RelationMention;
import edu.stanford.nlp.ie.machinereading.structure.MachineReadingAnnotations.EntityMentionsAnnotation;
import edu.stanford.nlp.ie.machinereading.structure.MachineReadingAnnotations.RelationMentionsAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import knowledgegraph.KGedge;
import knowledgegraph.KGgraph;
import knowledgegraph.KGvertex;
import preprocess.ACE05Utils;
import utils.Miscellaneous;

// A pipeline to generate a knowledge graph given a raw text. 
public class Pipeline {
	
	public static void main(String[] args) throws Exception {
		// For example, given a ACE 2005 document formatted by XML, we transform it into raw text
		String filePath = "D:\\data\\ace2005\\ACE2005-TrainingData-V5.0\\English\\bc\\timex2norm\\CNN_CF_20030303.1900.00.sgm";
		String dtdFilePath = "D:\\data\\ace2005\\ACE2005-TrainingData-V5.0\\dtd\\ace_source_sgml.v1.0.2.dtd";
		String rawText = ACE05Utils.transformSGMtoRawText(filePath, dtdFilePath);
		
		// Then, entities, attributes and relations are extracted respectively.
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,dcoref,relation");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        String text = rawText;
        Annotation document = new Annotation(text);
        pipeline.annotate(document);
        
	     List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	     
	     int entityID = 1;
	     int relationID = 1;
	     
	     List<Entity> kg_allEntities = new ArrayList<>();
	     List<Relation> kg_allRelations = new ArrayList<>();
	
	     for(CoreMap sentence: sentences) {
	    	 List<CoreLabel> tokens = sentence.get(CoreAnnotations.TokensAnnotation.class);
	    	 
		     List<Entity> kg_entities = new ArrayList<>();
		     List<Relation> kg_relations = new ArrayList<>();

	       List<EntityMention> entities = sentence.get(EntityMentionsAnnotation.class);
	       for(EntityMention entity:entities) {
	    	   if(entity.getType().equals("O"))
	    		   continue;
	    	   
		       Entity kg_entity = new Entity();
		       if(entity.getType().equals("PEOPLE")) {
		    	   
		       } else if(entity.getType().equals("LOCATION")) {
		    	   
		       } else if(entity.getType().equals("ORGANIZATION")) {
		    	   
		       } else
		    	   throw new Exception("unsurpported entity type");
		       kg_entity.type = entity.getType();
		       
		       kg_entity.fullName = entity.getFullValue();
		       CoreLabel tokenStart = tokens.get(entity.getExtentTokenStart());
		       CoreLabel tokenEnd = tokens.get(entity.getExtentTokenEnd());
		       kg_entity.span.a = tokenStart.beginPosition();
		       kg_entity.span.b = tokenEnd.endPosition();
		       kg_entity.setId(entityID);
		       entityID++;
		       
		       kg_entities.add(kg_entity);
	       }
	       
	       List<RelationMention> relations = sentence.get(RelationMentionsAnnotation.class);
	       for(RelationMention relation:relations) {
	    	   if(relation.getType().equals("_NR"))
	    		   continue;
	    	   
	    	   Relation kg_relation = new Relation();
	    	   if(relation.getType().equals("OrgBased_In")) {
	    		   // people-organization people-location
	    	   } else if(relation.getType().equals("Located_In")) {
	    		   // organization located in location
	    	   } else if(relation.getType().equals("Work_For")) {
	    		   // people work for organization
	    	   } else if(relation.getType().equals("Live_In")) {
	    		   // people live in location
	    	   } else 
	    		   throw new Exception("unsurpported relation type");
	    	   
	    	   kg_relation.evidence = relation.getFullValue();
		       CoreLabel tokenStart = tokens.get(relation.getExtentTokenStart());
		       CoreLabel tokenEnd = tokens.get(relation.getExtentTokenEnd());
		       kg_relation.span.a = tokenStart.beginPosition();
		       kg_relation.span.b = tokenEnd.endPosition();
		       kg_relation.type = relation.getType();
		       kg_relation.symmetry = true;
		       
		       List<EntityMention> arguments = relation.getEntityMentionArgs();
		       kg_relation.argument1 = Miscellaneous.find(kg_entities, arguments.get(0), tokens);
		       kg_relation.argument2 = Miscellaneous.find(kg_entities, arguments.get(1), tokens);
		       if(kg_relation.argument1==null || kg_relation.argument2==null)
		    	   continue;
		       
		       // remove repetitive relations
		       Relation repetitive = Miscellaneous.find(kg_relations, kg_relation);
		       if(repetitive != null)
		    	   continue;
		       
		       kg_relation.setId(relationID);
		       relationID++;
	    	   
	    	   kg_relations.add(kg_relation);
	    	   
	       }
	       
/*	       System.out.println("### Entities ###");
	       for(Entity entity:kg_entities) {
	    	   System.out.println(entity.type+" | "+entity.fullName);
	       }
	       
	       System.out.println("### Relations ###");
	       for(Relation relation:kg_relations) {
	    	   System.out.println(relation.type+" | "+relation.evidence);
	       }
	       
	       System.out.println();*/
	       
	       // add all
	       kg_allEntities.addAll(kg_entities);
	       kg_allRelations.addAll(kg_relations);
	       
	     }
	     
	     // Now, we didn't consider entity coreference and it should be added in the future.
	     
	     
	     // Build a knowledge graph based on the entities and relations
	     List<KGvertex> vertices = new ArrayList<>();
	     for(Entity kg_entity:kg_allEntities) {
	    	 KGvertex vertex = new KGvertex();
	    	 vertex.entities.add(kg_entity);
	    	 vertices.add(vertex);
	     }
	     
	     List<KGedge> edges = new ArrayList<>();
	     for(Relation kg_relation:kg_allRelations) {
	    	 KGedge edge = new KGedge();
	    	 edge.relation = kg_relation;
	    	 edges.add(edge);
	     }
	    
	     KGgraph graph = new KGgraph();	     
	     for(KGedge edge:edges) {
	    	 Entity argu1 = edge.relation.argument1;
	    	 Entity argu2 = edge.relation.argument2;
	    	 
	    	 KGvertex v_argu1 = Miscellaneous.findVertexByEntity(vertices, argu1);
	    	 KGvertex v_argu2 = Miscellaneous.findVertexByEntity(vertices, argu2);
	    	 if(v_argu1==null || v_argu2==null)
	    		 throw new Exception("can't find vertex by entity");
	    	 
	    	 graph.addByEdge(v_argu1, v_argu2, edge);
	     }
	     
	     for(KGvertex vertex:vertices) {
	    	 graph.addVertex(vertex);
	     }
	     
	     graph.outputToXML("F:/知识图谱/1.xml");
	     
	}
}
