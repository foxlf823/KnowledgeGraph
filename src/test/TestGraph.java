package test;

import java.util.ArrayList;


import basic.Organization;
import basic.OrganizationOrganization;
import basic.OrganizationPerson;
import basic.Person;
import basic.PersonPerson;
import cn.fox.data_structure.GraphEdge;
import cn.fox.data_structure.GraphVertex;
import knowledgegraph.KGedge;
import knowledgegraph.KGgraph;
import knowledgegraph.KGvertex;

public class TestGraph {

	public static void main(String[] args) {
		// initialize entities
		Person john = new Person();
		john.fullName = "John";
		john.setId(1);
		Person john1 = new Person();
		john1.fullName = "John Paul";
		john1.setId(2);
		
		Person tom = new Person();
		tom.fullName = "Tom";
		tom.setId(3);

		Organization apple = new Organization();
		apple.fullName = "Apple"; 
		apple.setId(4);
		
		Organization tencent = new Organization();
		tencent.fullName = "Tencent";
		tencent.setId(5);
		
		// initialize relations
		PersonPerson john_tom = new PersonPerson();
		john_tom.argument1 = john;
		john_tom.argument2 = tom;
		john_tom.setId(1);
		
		OrganizationPerson apple_john = new OrganizationPerson();
		apple_john.argument1 = john;
		apple_john.argument2 = apple;
		apple_john.setId(2);
		OrganizationPerson apple_john1 = new OrganizationPerson();
		apple_john1.argument1 = john1;
		apple_john1.argument2 = apple;
		apple_john1.setId(3);
		
		OrganizationPerson tencent_tom = new OrganizationPerson();
		tencent_tom.argument1 = tom;
		tencent_tom.argument2 = tencent;
		tencent_tom.setId(4);

		OrganizationOrganization apple_tencent = new OrganizationOrganization();
		apple_tencent.argument1 = apple;
		apple_tencent.argument2 = tencent;
		apple_tencent.setId(5);
		
		// initialize kg vertices
		KGvertex kgv_john = new KGvertex();
		kgv_john.entities.add(john);
		kgv_john.entities.add(john1);
		
		KGvertex kgv_tom = new KGvertex();
		kgv_tom.entities.add(tom);
		
		KGvertex kgv_apple = new KGvertex();
		kgv_apple.entities.add(apple);
		
		KGvertex kgv_tencent = new KGvertex();
		kgv_tencent.entities.add(tencent);
		
		// initialize kg edges
		KGedge kge_john_tom = new KGedge();
		kge_john_tom.relation = john_tom;
		
		KGedge kge_apple_john = new KGedge();
		kge_apple_john.relation = apple_john;
		KGedge kge_apple_john1 = new KGedge();
		kge_apple_john1.relation = apple_john1;
		
		KGedge kge_tencent_tom = new KGedge();
		kge_tencent_tom.relation = tencent_tom;
		
		KGedge kge_apple_tencent = new KGedge();
		kge_apple_tencent.relation = apple_tencent;
		
		// build kg
		KGgraph graph = new KGgraph();
		graph.addByEdge(kgv_john, kgv_tom, kge_john_tom);
		graph.addByEdge(kgv_apple, kgv_john, kge_apple_john);
		graph.addByEdge(kgv_apple, kgv_john, kge_apple_john1);
		graph.addByEdge(kgv_tencent, kgv_tom, kge_tencent_tom);
		graph.addByEdge(kgv_apple, kgv_tencent, kge_apple_tencent);
		
		graph.resetVisited();
		ArrayList<GraphVertex> vertices = graph.traverse(kgv_apple);
		for(GraphVertex vertex : vertices) {
			System.out.println("current concept: "+vertex);
			
			ArrayList<GraphEdge> edges = graph.getOutgoingEdge(vertex);
			for(GraphEdge edge : edges) {
				System.out.println("outgoing edge: "+edge);
			}

			System.out.println();
		}
		
		graph.outputToXML("F:/知识图谱/1.xml");

		
	}

}
