package mathParser;

import java.util.Scanner;

public class MainProgram {
	public static void main(String[] args) throws Exception {
		Scanner in = new Scanner(System.in);
		System.out.println("Enter your math expression: ");
        String example = in.nextLine();
        char[] var = new VariableControl().checkRE(example);

        if(var[0] == '-') {
        	MathParser mthRep = new MathParser(example);
            double a = mthRep.mainParser();
            System.out.println("Answer: " + a);
        }
        else{
            for(int i = 0; i < new VariableControl().sizeRE(var); i++){
                System.out.println(var[i]+"= ");
                example = example.replaceAll(String.valueOf(var[i]), in.next());
            }
            MathParser mthRep = new MathParser(example);
            System.out.println("Answer: " + mthRep.mainParser());
        }
	}

}
