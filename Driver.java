import java.io.*;

public class Driver {
	public static void main(String [] args) {

		Polynomial p0 = new Polynomial();
		System.out.println("p0(3)=" + p0.evaluate(3));
		if(p0.hasRoot(1))
			System.out.println("1 is a root of zero");
		else
			System.out.println("1 is not a root of zero");


		int[] e1 = {1,3,0,0,2};
		double[] c1 = {5.0,1.5,1.1,-3,1};
		Polynomial p1 = new Polynomial(c1, e1);
		System.out.println("p1(3)=" + p1.evaluate(3));

		int[] e2 = {4,4,4,0,2};
		double[] c2 = {1,2,3,3,-1};
		Polynomial p2 = new Polynomial(c2, e2);
		System.out.println("p2(3)=" + p2.evaluate(3));

		Polynomial add_p = p1.add(p2);
		System.out.println("add_p(0.1) = " + add_p.evaluate(0.1));

		Polynomial mult_p = p1.multiply(p2);
		System.out.println("mult_p(0.1) = " + mult_p.evaluate(0.1));

		if(mult_p.hasRoot(1))
			System.out.println("1 is a root of mult_p");
		else
			System.out.println("1 is not a root of mult_p");

		

		try {
            	Polynomial p3 = new Polynomial(new File("polytest1.txt"));
            	System.out.println("coefficient list: " + java.util.Arrays.toString(p3.co));
            	System.out.println("exponent list: " + java.util.Arrays.toString(p3.exp));
        	} 
		catch (IOException e){
            	System.out.println("error in file reading");
        	}
		try {
            	Polynomial p00 = new Polynomial(new File("polytest4.txt"));
            	System.out.println("coefficient list: " + java.util.Arrays.toString(p00.co));
            	System.out.println("exponent list: " + java.util.Arrays.toString(p00.exp));
        	} 
		catch (IOException e){
            	System.out.println("error in file reading");
        	}

        	
        	Polynomial p4 = new Polynomial(new double[]{1.0, -2.2, -7}, new int[]{5, 1, 0});
        	try {
            		p4.saveToFile("polytest2.txt");
            		System.out.println("saved to 'polytest2.txt'");
        	} 
		catch (IOException e){
            	System.out.println("error in file writing");
        	}
		try {
            		p0.saveToFile("polytest3.txt");
            		System.out.println("saved to 'polytest3.txt'");
        	} 
		catch (IOException e){
            	System.out.println("error in file writing");
        	}
    	}	
}
