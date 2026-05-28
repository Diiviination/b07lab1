import java.io.File;
import java.util.Scanner;
import java.io.IOException;
import java.io.PrintWriter;

public class Polynomial {
	public double[] coefficients;
	public int[] exponents;
	
	public Polynomial() {
		this.coefficients = new double[] {0};
		this.exponents = new int[] {0};
	}
	
	public Polynomial(double[] coefficients, int[] exponents) {
		this.coefficients = coefficients;
		this.exponents = exponents;
	}
	
	public Polynomial(File file) throws IOException {
		Scanner input = new Scanner(file);
		String stringPolynomial = input.nextLine();
		input.close();
		String[] terms = stringPolynomial.split("[+]|(?=-)");
		double[] coefficients = new double[terms.length];
		int[] exponents = new int[terms.length];
		for (int i = 0; i < terms.length; i++) {
			String[] coeffsAndExponents = terms[i].split("x", -1);
			if (coeffsAndExponents.length == 1) {
				coefficients[i] = Double.parseDouble(coeffsAndExponents[0]);
				exponents[i] = 0;
			}
			else {
				if (coeffsAndExponents[1].equals("")) {
					exponents[i] = 1;
				}
				else {
					exponents[i] = Integer.parseInt(coeffsAndExponents[1]);
				}
				if (coeffsAndExponents[0].equals("")) {
					coefficients[i] = 1.0;
				}
				else if (coeffsAndExponents[0].equals("-")) {
					coefficients[i] = -1.0;
				}
				else {
					coefficients[i] = Double.parseDouble(coeffsAndExponents[0]);
				}
			}
		}
		this.coefficients = coefficients;
		this.exponents = exponents;
	}
	
	public Polynomial add(Polynomial p) {
		int unique = this.exponents.length;
		outerloop:
		for (int i = 0; i < p.exponents.length; i++) {
			for (int j = 0; j < this.exponents.length; j++) {
				if (p.exponents[i] == this.exponents[j]) {
					continue outerloop;
				}
			}
			unique++;
		}
		
		int[] exponentList = new int[unique];
		double[] coefficientSums = new double[unique];
		for (int i = 0; i < this.exponents.length; i++) {
			exponentList[i] = this.exponents[i];
			coefficientSums[i] = this.coefficients[i];
		}
		
		int next = this.exponents.length;
		outerloop:
		for (int i = 0; i < p.exponents.length; i++) {
			for (int j = 0; j < this.exponents.length; j++) {
				if (p.exponents[i] == exponentList[j]) {
					coefficientSums[j] += p.coefficients[i];
					continue outerloop;
				}
			}
			exponentList[next] = p.exponents[i];
			coefficientSums[next] = p.coefficients[i];
			next++;
		}
		int numNonZero = 0;
		for (int i = 0; i < coefficientSums.length; i++) {
			if (coefficientSums[i] != 0) {
				numNonZero++;
			}
		}
		if (numNonZero == 0) {
			return new Polynomial();
		}
		int[] finalExponentList = new int[numNonZero];
		double[] finalCoefficientList = new double[numNonZero];
		int j = 0;
		for (int i = 0; i < coefficientSums.length; i++) {
			if (coefficientSums[i] != 0) {
				finalExponentList[j] = exponentList[i];
				finalCoefficientList[j] = coefficientSums[i];
				j++;
			}
		}
		
		return new Polynomial(finalCoefficientList, finalExponentList);
	}
	
	public Polynomial multiply(Polynomial p) {
		Polynomial runningPolynomial = new Polynomial();
		for (int i = 0; i < p.exponents.length; i++) {
			int[] newExponents = new int[this.exponents.length];
			double[] newCoefficients = new double[this.coefficients.length];
			for (int j = 0; j < newExponents.length; j++) {
				newExponents[j] = this.exponents[j] + p.exponents[i];
				newCoefficients[j] = this.coefficients[j] * p.coefficients[i];
			}
			Polynomial polynomialTimesMonomial = new Polynomial(newCoefficients, newExponents);
			runningPolynomial = runningPolynomial.add(polynomialTimesMonomial);
		}
		return runningPolynomial;
	}
	
	public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i < this.coefficients.length; i++) {
			result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		return (this.evaluate(x) == 0);
	}
	
	public void saveToFile(String fileAddress) throws IOException {
		PrintWriter output = new PrintWriter(fileAddress);
		if (coefficients[0] != 1) {
			output.print(coefficients[0]);
		}
		if (this.exponents[0] > 0) {
			output.print("x");
		}
		if (this.exponents[0] > 1) {
			output.print(this.exponents[0]);
		}
		for (int i = 1; i < this.exponents.length; i++) {
			if (coefficients[i] > 0) {
				output.print("+");
			}
			if (coefficients[i] != 1) {
				output.print(coefficients[i]);
			}
			if (this.exponents[i] > 0) {
				output.print("x");
			}
			if (this.exponents[i] > 1) {
				output.print(this.exponents[i]);
			}
		}
		output.close();
	}
}