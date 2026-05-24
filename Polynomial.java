public class Polynomial {
	public double[] coefficients;
	
	public Polynomial() {
		this.coefficients = new double[] {0};
	}
	
	public Polynomial(double[] coefficients) {
		this.coefficients = coefficients;
	}
	
	public Polynomial add(Polynomial p) {
		int highestDegree;
		int lowestDegree;
		
		highestDegree = Math.max(this.coefficients.length, p.coefficients.length);
		lowestDegree = Math.min(this.coefficients.length, p.coefficients.length);
		double[] coefficientSums = new double[highestDegree];
		
		for (int i = 0; i < lowestDegree; i++) {
			coefficientSums[i] = this.coefficients[i] + p.coefficients[i];
		}
		
		if (highestDegree == this.coefficients.length) {
			for (int i = lowestDegree; i < highestDegree; i++) {
				coefficientSums[i] = this.coefficients[i];
			}
		}
		else {
			for (int i = lowestDegree; i < highestDegree; i++) {
				coefficientSums[i] = p.coefficients[i];
			}
		}
		
		return new Polynomial(coefficientSums);
	}
	
	public double evaluate(double x) {
		double result = 0;
		for (int i = 0; i < this.coefficients.length; i++) {
			result += this.coefficients[i] * Math.pow(x, i);
		}
		return result;
	}
	
	public boolean hasRoot(double x) {
		return (this.evaluate(x) == 0);
	}
}