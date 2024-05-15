import java.lang.Math;

public class Polynomial{
	public double[] var;
	
	public Polynomial(){
		this.var = new double[]{0};
	}

	public Polynomial(double[] p){
		this.var = p;		
	}
	public Polynomial add(Polynomial y){
		//adds the calling object and argument Polynomials
		//returns the resulting polynomial (new polynomial)

		int max = Math.max(this.var.length, y.var.length);
        	double[] p = new double[max];
        
        	for (int i = 0; i < max; i++) {
            		double a = i < this.var.length ? this.var[i]:0;
            		double b = i < y.var.length ? y.var[i]:0;
            		p[i] = a+b;
        	}

        	return new Polynomial(p);
	}
	public double evaluate(double x){
		//using the given value of x, calculate the polynomial
		//return evaluated number

		double counter = 0;
		for (int i=0; i<this.var.length; i++){
			counter += this.var[i]*Math.pow(x,i);
		}
		return counter;
		
	}
	public boolean hasRoot(double x){
		//checks if the x value is a root for the polynomial (=0)
		//return true if root
		return evaluate(x) == 0;
	}
}