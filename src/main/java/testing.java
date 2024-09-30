import java.util.*;

public class testing {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		List<String> names = new ArrayList<>();
		
		
		names.add("Michael");
		names.add("Phi");
		names.add("Junior2");
		names.add("Phlipino2");
		
		Collections.shuffle(names);
		
		Random rand = new Random();
		
		System.out.println(rand.nextInt(5,13));
				

		
		

	}

}
