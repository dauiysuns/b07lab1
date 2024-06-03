import java.io.*;
import java.util.regex.*;
import java.lang.Math;

public class Polynomial{
    public double[] co;
    public int[] exp;

    public Polynomial(){
        this.co = null;
        this.exp = null;
    }

    public Polynomial(double[] co, int[] exp){
	if ((co == null && exp == null)||(co.length == 0 && exp.length == 0)){
		this.co = null; this.exp = null;
		return;
	}
        double[] temp_co = new double[co.length];
        int[] temp_exp = new int[exp.length];
        int size = 0;
        for (int i = 0; i < exp.length; i++){
            if (co[i] == 0) continue;
            boolean found = false;
            for (int j = 0; j < size; j++){
                if (temp_exp[j] == exp[i]){
                    temp_co[j] += co[i];
                    found = true;
                    break;
                }
            }
            if (!found){
                temp_exp[size] = exp[i];
                temp_co[size] = co[i];
                size++;
            }
        }
        int final_size = 0;
        for (int i = 0; i < size; i++){
            if (temp_co[i] != 0){
                final_size++;
            }
        }
        this.co = new double[final_size];
        this.exp = new int[final_size];
        int index = 0;
        for (int i = 0; i < size; i++){
            if (temp_co[i] != 0){
                this.co[index] = temp_co[i];
                this.exp[index] = temp_exp[i];
                index++;
            }
        }
    }


    /*
    d. Add a constructor that takes one argument of type File and initializes the polynomial
    based on the contents of the file. You can assume that the file contains one line with no
    whitespaces representing a valid polynomial. For example: the line 5-3x2+7x8
    corresponds to the polynomial 5 − 3x^2 + 7x^8
    Hint: you might want to use the following methods: split of the String class, parseInt of
    the Integer class, and parseDouble of the Double class
    */

    public Polynomial(File file) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = reader.readLine();
        reader.close();
        String[] terms = line.split("(?=[+-])"); //split in front of +,- using regex 
	if (terms.length == 1 && terms[0] == "0"){
		this.co = null;
		this.exp = null;
		return;
	}

        int[] exp = new int[terms.length];
        double[] co = new double[terms.length];
        for (int i = 0; i<terms.length; i++){
            String[] components = terms[i].split("(?<=x)|(?=x)");//split around x and keep it (to identify co/exp)
            if (components.length == 3){//ax^n form
                exp[i] = Integer.parseInt(components[2]);
                co[i] = Double.parseDouble(components[0]);
            }
            else if (components.length == 2 && components[1].equalsIgnoreCase("x")){//ax^1 form
                exp[i] = 1;
                co[i] = Double.parseDouble(components[0]);
            }
            else if (components.length == 2 && components[0].equalsIgnoreCase("x")){//x^n form
                exp[i] = Integer.parseInt(components[1]);
                co[i] = 1;
            }
            else if (components.length == 1){//ax^0 form
                exp[i] = 0;
                co[i] = Double.parseDouble(components[0]);
            }
            else {//not proper form, throw exception
                throw new IllegalArgumentException("invalid polynomial");
            }
        }

        
        Polynomial temp = new Polynomial(co, exp);
        this.co = temp.co;
        this.exp = temp.exp;
        /*
        QUESTION: what happens if we do the following:
        co = temp.co; exp = temp.exp;

            *notice how we have local vars AND object attributes with the same names co,exp within this constructor

        i. if we DIDN'T have local variables named co,exp, then we would have changed the values of the instance variables/object attributes for the object we are currently constructing!
        ii. however, since co,exp are local variables, we would be setting those LOCAL variables to temp.co,temp.exp, and not changing the object's attributes as desired.
        Thus, it is important to use "this" to refer to the object we are currently constructing (avoids confusion)
        */
    }


    private int findIndex(int[] array, int value, int length) {
        for (int i = 0; i < length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }
    
    public Polynomial add(Polynomial y){
        double[] new_co = new double[this.co.length + y.co.length]; // Initialize length to sum of both coefficient lengths
        int[] new_exp = new int[this.exp.length + y.exp.length]; // Same as coefficients
        int i = 0;

        // Loop 1: copying over calling polynomial
        for (i = 0; i < this.exp.length; i++) {
            int index = findIndex(new_exp, this.exp[i], i);
            if (index == -1 && this.co[i] != 0) { // Check if exp is a duplicate and co is nonzero
                new_exp[i] = this.exp[i]; // Append exp to new_exp at current index
                new_co[i] += this.co[i]; // Append co to new_co at current index
            } else if (this.co[i] != 0) { // Locate the index of the existing exp and add co value
                new_co[index] += this.co[i];
            }
        }

        int last_index = this.exp.length; // Tracker of last empty space in new arrays (to add new values from y)

        for (int j = 0; j < y.exp.length; j++) {
            int index = findIndex(new_exp, y.exp[j], last_index);
            if (index == -1 && y.co[j] != 0) { // Check if exp is a duplicate and co is nonzero
                new_exp[last_index] = y.exp[j];
                new_co[last_index] = y.co[j];
                last_index++;
            } else if (y.co[j] != 0) {
                new_co[index] += y.co[j];
            }
        }
        /*
        // Count the number of non-zero coefficients in new_co to know how long to make the final arrays
        int count = 0;
        for (i = 0; i < new_co.length; i++) {
            if (new_co[i] != 0) {
                count++;
            }
        }

        // Create the final arrays
        double[] final_co = new double[count];
        int[] final_exp = new int[count];

        // Copy the non-zero values from new_co and new_exp to final_co and final_exp
        int tracker = 0;
        for (i = 0; i < new_co.length; i++) {
            if (new_co[i] != 0) {
                final_co[tracker] = new_co[i];
                final_exp[tracker] = new_exp[i];
                tracker++;
            }
        }
        */
        //return new Polynomial(final_co, final_exp);
        return new Polynomial(new_co, new_exp);
    }
    public double evaluate(double x){
        //using the given value of x, calculate the polynomial
        //return evaluated number
	if (co == null && exp == null){
		return 0;
	}
        double counter = 0;
        for (int i = 0; i < co.length; i++) {
            counter += co[i] * Math.pow(x, exp[i]);
        }
        return counter;
    }

    public boolean hasRoot(double x){
        //checks if the x value is a root for the polynomial (=0)
        //return true if root
        return evaluate(x) == 0;
    }

    /*
    c. Add a method named multiply that takes one argument of type Polynomial and returns
    the polynomial resulting from multiplying the calling object and the argument. The
    resulting polynomial should not contain redundant exponents. */
    public Polynomial multiply(Polynomial x){
        //multiply the calling object and argument Polynomials
        //returns the resulting polynomial (new polynomial)

        // Loop 1: copying over calling polynomial
        /*
        make new exp and co lists, with length of this.exp * x.exp 
        loop with nested loop to multiply coefficients AND exponents, adding to new list at i+j
        construct final polynomial (constructor should deal with duplicates)
        */
        double[] new_co = new double[this.co.length * x.co.length];
        int[] new_exp = new int[this.exp.length * x.exp.length];
        int i=0,j=0,tracker=0;

        for (i = 0; i<this.exp.length; i++){//iterates through calling poly
            for (j=0; j<x.exp.length; j++){//iterates through argument poly
                new_co[tracker] = this.co[i] * x.co[j];
                new_exp[tracker] = this.exp[i] + x.exp[j];
                tracker++;
            }
        }
        return new Polynomial(new_co, new_exp);
    }
    
    /*
    e. Add a method named saveToFile that takes one argument of type String representing a
    file name and saves the polynomial in textual format in the corresponding file (similar to
    the format used in part d)

    */
    public void saveToFile(String name) throws IOException{
        FileWriter writer = new FileWriter(name);
	if (this.exp == null && this.co == null){
		System.out.println("HELPPP");
		writer.write("0");
		writer.close();
		return;
	}
        for (int i = 0; i < exp.length; i++){
            if (exp[i] == 0){
                writer.write(Double.toString(co[i]));
            }
            else{
                writer.write(Double.toString(co[i]) + "x" + Integer.toString(exp[i]));
            }
            if (i < exp.length - 1 && co[i+1] > 0){
                writer.write("+");
            }
        }
        writer.close();
    } 
}