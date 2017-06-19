package test;

import java.util.List;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import preprocess.ACE05Utils;

public class EntityRecognition {

	public static void main(String[] args) throws Exception {
		String filePath = "D:\\data\\ace2005\\ACE2005-TrainingData-V5.0\\English\\bc\\timex2norm\\CNN_CF_20030303.1900.00.sgm";
		String dtdFilePath = "D:\\data\\ace2005\\ACE2005-TrainingData-V5.0\\dtd\\ace_source_sgml.v1.0.2.dtd";
		String rawText = ACE05Utils.transformSGMtoRawText(filePath, dtdFilePath);
		
		AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier("edu/stanford/nlp/models/ner/english.all.3class.distsim.crf.ser.gz");
	      List<List<CoreLabel>> out = classifier.classify(rawText);
	      for (List<CoreLabel> sentence : out) {
	        for (CoreLabel word : sentence) {
	          System.out.print(word.word() + '/' + word.get(CoreAnnotations.AnswerAnnotation.class) + ' ');
	        }
	        System.out.println();
	      }

	}

}
