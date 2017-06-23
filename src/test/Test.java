package test;

import java.io.File;

import preprocess.ACE05Utils;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String filePath = "D:\\data\\ace2005\\ACE2005-TrainingData-V5.0\\English\\bc\\timex2norm\\CNN_CF_20030303.1900.00.sgm";
		String dtdFilePath = "D:\\data\\ace2005\\ACE2005-TrainingData-V5.0\\dtd\\ace_source_sgml.v1.0.2.dtd";
		String rawText = ACE05Utils.transformSGMtoRawText(filePath, dtdFilePath);
		
		System.out.println(rawText);
		
	}

}
