package basic;

import java.util.ArrayList;
import java.util.List;


public class Person extends Entity {
	

	public String firstName;
	public String lastName;
	// e.g., chairman, president
	public List<String> title = new ArrayList<>();
	public boolean sex; // false-male, true-female
}
