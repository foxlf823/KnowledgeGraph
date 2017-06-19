package preprocess;

import cn.fox.ace2005.SgmXmlParser;


public class ACE05Utils {

	// transform an ACE05 sgm file to its raw text
	public static String transformSGMtoRawText(String filePath, String dtdFilePath) {
		
		SgmXmlParser sgmParser = new SgmXmlParser(dtdFilePath);

    	String text = sgmParser.transferXmlToRaw(filePath, false);

		return text;
	}
}
