package knowledgegraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import basic.Entity;
import basic.Relation;
import cn.fox.data_structure.Graph;
import cn.fox.data_structure.GraphEdge;
import cn.fox.data_structure.GraphVertex;

/*
 *  A KGgraph denotes a knowledge graph and it corresponds to a document.
 *  Given a document, we should extract the entities and relations in it and generate a KGgraph.
 */

public class KGgraph extends Graph {

	public KGgraph() {
		super(true); // It is assumed that a knowledge graph is a directed graph
		
	}
	
	public void outputToXML(String path) {
		File fPath = new File(path);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            
            Element elementKnowledgeGraph = document.createElement("knowledgegraph");
            document.appendChild(elementKnowledgeGraph);
            
            for(GraphVertex vertex:this.vertexes.keySet()) {
            	KGvertex kgVertex = (KGvertex)vertex;
            	
            	Element elementKGvertex = document.createElement("vertex");
            	elementKnowledgeGraph.appendChild(elementKGvertex);
            	
            	for(Entity entity:kgVertex.entities) {
            		Element elementEntity = document.createElement("entity");
            		elementKGvertex.appendChild(elementEntity);
            		
            		elementEntity.setAttribute("id", entity.getId());

            		Element elementFullname = document.createElement("fullname");
            		elementEntity.appendChild(elementFullname);
            		elementFullname.setTextContent(entity.fullName);
            		
            		Element elementType = document.createElement("type");
            		elementEntity.appendChild(elementType);
            		elementType.setTextContent(entity.type);
            		
            		Element elementBeginPosition = document.createElement("beginposition");
            		elementEntity.appendChild(elementBeginPosition);
            		elementBeginPosition.setTextContent(entity.span.a.toString());
            		
            		Element elementEndPosition = document.createElement("endposition");
            		elementEntity.appendChild(elementEndPosition);
            		elementEndPosition.setTextContent(entity.span.b.toString());
            		
            	}
            	
            	
            }
            
            for(GraphEdge edge:this.edges.keySet()) {
            	KGedge kgEdge = (KGedge)edge;
            	
            	Element elementKGedge = document.createElement("edge");
            	elementKnowledgeGraph.appendChild(elementKGedge);
            	
            	Relation relation = kgEdge.relation;
            	Element elementRelation = document.createElement("relation");
            	elementKGedge.appendChild(elementRelation);
            	
            	elementRelation.setAttribute("id", relation.getId());
            	
        		Element elementArgument1 = document.createElement("argument");
        		elementRelation.appendChild(elementArgument1);
        		elementArgument1.setTextContent(relation.argument1.getId());
        		
        		Element elementArgument2 = document.createElement("argument");
        		elementRelation.appendChild(elementArgument2);
        		elementArgument2.setTextContent(relation.argument2.getId());
        		
        		Element elementType = document.createElement("type");
        		elementRelation.appendChild(elementType);
        		elementType.setTextContent(relation.type);
        		
        		Element elementEvidence = document.createElement("evidence");
        		elementRelation.appendChild(elementEvidence);
        		elementEvidence.setTextContent(relation.evidence);
            	
            }
            	
            	
            TransformerFactory tff = TransformerFactory.newInstance();
            tff.setAttribute("indent-number", new Integer(2));
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            document.setXmlStandalone(true);
            tf.setOutputProperty(OutputKeys.STANDALONE, "yes");
            tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            //tf.transform(new DOMSource(document), new StreamResult(path));
            tf.transform(new DOMSource(document), new StreamResult(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)))));
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	


}
