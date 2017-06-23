package basic;


public class PersonPerson extends Relation {
	
	@Override
	public String toString() {
		return "person-person | "+argument1.toString()+" | "+argument2.toString();
	}
}
