package basic;

import java.util.ArrayList;
import java.util.List;


public class OrganizationPerson extends Relation {

	public Entity organization;
	public Person person;
	// e.g., chairman, president
	public List<String> title = new ArrayList<>();
}
