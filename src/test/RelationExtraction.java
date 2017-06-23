package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ie.machinereading.structure.EntityMention;
import edu.stanford.nlp.ie.machinereading.structure.MachineReadingAnnotations.EntityMentionsAnnotation;
import edu.stanford.nlp.ie.machinereading.structure.MachineReadingAnnotations.RelationMentionsAnnotation;
import edu.stanford.nlp.ie.machinereading.structure.RelationMention;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import preprocess.ACE05Utils;

public class RelationExtraction {

	public static void main(String[] args) throws Exception {
		String filePath = "D:\\data\\ace2005\\ACE2005-TrainingData-V5.0\\English\\bc\\timex2norm\\CNN_CF_20030303.1900.00.sgm";
		String dtdFilePath = "D:\\data\\ace2005\\ACE2005-TrainingData-V5.0\\dtd\\ace_source_sgml.v1.0.2.dtd";
		String rawText = ACE05Utils.transformSGMtoRawText(filePath, dtdFilePath);
		
		Set<String> entityTypes = new HashSet<>();
		Set<String> relationTypes = new HashSet<>();
		
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,relation");
        
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // read some text in the text variable
        String text = rawText;

        // create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

        // run all Annotators on this text
        pipeline.annotate(document);
        
	     // these are all the sentences in this document
	     // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
	     List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	
	     for(CoreMap sentence: sentences) {
	       // traversing the words in the current sentence
	       // a CoreLabel is a CoreMap with additional token-specific methods
/*	    	 System.out.println("### A new sentence begins ###");
	    	 
	       for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
	         // this is the text of the token
	         String word = token.get(TextAnnotation.class);
	         // this is the POS tag of the token
	         String pos = token.get(PartOfSpeechAnnotation.class);
	         // this is the NER label of the token
	         String ne = token.get(NamedEntityTagAnnotation.class);
	         
	         System.out.print(word+"/"+pos+"/"+ne+" ");
	       }
	       System.out.println();*/
	       
	       System.out.println("### Entities ###");
	       List<EntityMention> entities = sentence.get(EntityMentionsAnnotation.class);
	       for(EntityMention entity:entities) {
	    	   if(entity.getType().equals("O"))
	    		   continue;
	    	   System.out.println(entity.getType()+" | "+entity.getValue());
	    	   entityTypes.add(entity.getType());
	       }
	       
	       System.out.println("### Relations ###");
	       List<RelationMention> relations = sentence.get(RelationMentionsAnnotation.class);
	       for(RelationMention relation:relations) {
	    	   if(relation.getType().equals("_NR"))
	    		   continue;
	    	   System.out.println(relation.getType()+" | "+relation.getValue());
	    	   relationTypes.add(relation.getType());
	       }
	       
	       System.out.println();
	     }
	     
	     
	     System.out.print("existing entity types: ");
	     for(String type:entityTypes)
	    	 System.out.print(type+" ");
	     System.out.println();
	     
	     System.out.print("existing relation types: ");
	     for(String type:relationTypes)
	    	 System.out.print(type+" ");
	     System.out.println();
	
	}

}
