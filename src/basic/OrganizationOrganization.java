package basic;


public class OrganizationOrganization extends Relation {
	
	@Override
	public String toString() {
		return "organ-organ | "+argument1.toString()+" | "+argument2.toString();
	}
}
