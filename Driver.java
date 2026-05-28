import java.io.IOException;
import java.io.File;

public class Driver {
	public static void main(String [] args) throws IOException{
		File file = new File("polynomial.txt");
		double [] c1 = {6,5};
		int [] e1 = {0,3};
		double [] c2 = {-2,-9};
		int [] e2 = {1,4};
		Polynomial p = new Polynomial();
		System.out.println(p.evaluate(3));
		Polynomial p1 = new Polynomial(file);
		Polynomial p2 = new Polynomial(c2, e2);
		p = p1.multiply(p2);
		Polynomial s = p1.add(p2);
		p.saveToFile("outputFile.txt");
		System.out.println("s(0.1) = " + s.evaluate(0.1));
		if(s.hasRoot(1))
			System.out.println("1 is a root of s");
		else
			System.out.println("1 is not a root of s");
	}
}