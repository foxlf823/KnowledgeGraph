package basic;

import java.util.ArrayList;
import java.util.List;


public class OrganizationPerson extends Relation {

	// e.g., chairman, president
	public List<String> title = new ArrayList<>();
	
	@Override
	public String toString() {
		return "organ-person | "+argument1.toString()+" | "+argument2.toString();
	}
}
