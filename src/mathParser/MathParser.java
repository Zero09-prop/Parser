package mathParser;

import java.util.Stack;
public class MathParser {
	
    private final String exp;

    public MathParser(String expression){
        this.exp = expression;
    }
    /**
     * Creating two stacks. One for putting operations in it, the other for putting the value of numbers
     */
    private final Stack<Token> StackOper = new Stack<>();
    private final Stack<Token> StackNum = new Stack<>();

    private Token item;

    /**
     * Method that evaluates the value of an expression
     * @return - value expression or throws an exception in case of incorrect input.
     */
    public double mainParser() throws Exception{
        double value;
        boolean flag = true;
        for(int i = 0; i < exp.length(); i++){
            item = new Token();
            char buff = exp.charAt(i);
            if (buff == ' ') continue;
            if(buff >= '0' && buff <= '9' || buff == '-' && flag){
                if(buff == '-' && flag) {
                    ++i;
                    if(exp.charAt(i) == 'P') {
                        item.Type = '0';
                        item.Value = -Math.PI;
                    }
                    else {
                        value = numFromStr(i);
                        item.Type = '0';
                        item.Value = -value;
                    }
                }
                else{
                    value = numFromStr(i);
                    item.Type = '0';
                    item.Value = value;
                }
                StackNum.push(item);
                i = indexOfEndNum(i)-1;
                flag = false;
                continue;
            }
            else if (buff == '-' || buff == '*' || buff == '/' || buff == '+') { 
                if(StackOper.size() == 0) {
                    item.Type = buff;
                    item.Value = 0;
                    StackOper.push(item);
                    continue;
                }
                else if(getRang(buff) > getRang(StackOper.peek().Type)){
                    item.Type = buff;
                    item.Value = 0;
                    StackOper.push(item);
                    continue;
                }
                else if(getRang(buff) <= getRang(StackOper.peek().Type)){
                    if (Maths()) { 
                        throw new Exception("Error");
                    }
                    item = new Token();
                    item.Type = buff;
                    item.Value = 0;
                    StackOper.push(item);
                    continue;
                }
            }
            if (buff == '('){
                item.Type = buff;
                item.Value = 0;
                StackOper.push(item);
                continue;
            }
            else if (buff == ')'){
                while (StackOper.peek().Type != '('){
                    if (Maths())
                        throw new Exception("Error");
                }
                StackOper.pop();
                continue;
            }

            if(buff == 's' || buff == 'c' || buff == 't'){
                i += 2;
                char buffTrigonometry = exp.charAt(i);
                if (buffTrigonometry == 'g')
                    item.Type = buffTrigonometry;
                else item.Type = buff;
                item.Value = 0;
                StackOper.push(item);
                continue;
            }
            if(buff == 'P') {
                item.Type = '0';
                item.Value = Math.PI;
                StackNum.push(item);
                flag = false;
            }
        }

        while (StackOper.size() != 0){
            if (Maths()) { 
                throw new Exception("Error");
            }
        }
        return StackNum.pop().Value;
    }

    /**
     * The method that performs the calculation
     * we take the top two numbers from the StackNum and apply the operation from StackOper to them
     * the resulting value is put back on the stack
     * @return - true - if the value you was considered correct 
     * false - if there is an error in writing the expression
     */
    private boolean Maths(){
        item = new Token();
        double a, b, c;
        a = StackNum.pop().Value; 
        switch (StackOper.peek().Type){  
            case '+': 
                b = StackNum.pop().Value;
                c = a + b;
                item.Type = '0';
                item.Value = c;
                StackNum.push(item); 
                StackOper.pop();
                return false;

            case '-':
                b = StackNum.pop().Value;
                c = b - a;
                item.Type = '0';
                item.Value = c;
                StackNum.push(item);
                StackOper.pop();
                return false;

            case '*':
                b = StackNum.pop().Value;
                c = a * b;
                item.Type = '0';
                item.Value = c;
                StackNum.push(item);
                StackOper.pop();
                return false;

            case '/':
                b = StackNum.pop().Value;
                if (a == 0){
                    return true;
                }
                else {
                    c = b / a;
                    item.Type = '0';
                    item.Value = c;
                    StackNum.push(item);
                    StackOper.pop();
                    return false;
                }

            case 's':
                c = Math.sin(a);
                item.Type = '0';
                item.Value = c;
                StackNum.push(item);
                StackOper.pop();
                return false;

            case 'c':
                c = Math.cos(a);
                item.Type = '0';
                item.Value = c;
                StackNum.push(item);
                StackOper.pop();
                return false;

            case 't':
                if (Math.cos(a) == 0) {
                    return true;
                }
                else {
                    c = Math.tan(a);
                    item.Type = '0';
                    item.Value = c;
                    StackNum.push(item);
                    StackOper.pop();
                    return false;
                }

            case 'g':
                if (Math.sin(a) == 0){
                    return true;
                }
                else {
                    c = 1/Math.tan(a);
                    item.Type = '0';
                    item.Value = c;
                    StackNum.push(item);
                    StackOper.pop();
                    return false;
                }
            default:
                return true;
        }
    }

    /**
     * Extracts a number from a string
     * @param index - index of the beginning of the number in the string
     * @return -number
     */
    private double numFromStr(int index){
        char[] numberChar = new char[5];
        for(int i = index; i < exp.length(); i++){
            if (exp.charAt(i) == '+'||
                    exp.charAt(i) == '-'||
                    exp.charAt(i) == '*'||
                    exp.charAt(i) == '/'||
                    exp.charAt(i) == '('||
                    exp.charAt(i) == ')')
                break;
            numberChar[i-index] = exp.charAt(i);
        }
        return Double.parseDouble(new String(numberChar));
    }

    /**
     * Getting the index of the end of a number by the index of the beginning
     * @param index - index of the beginning of the number in the string
     * @return - index of the end of a number in a string
     */
    private int indexOfEndNum(int index){
        for(; index < exp.length(); index++){
            if (exp.charAt(index) == '+'||
                    exp.charAt(index) == '-'||
                    exp.charAt(index) == '*'||
                    exp.charAt(index) == '/'||
                    exp.charAt(index) == '('||
                    exp.charAt(index) == ')')
                break;
        }
        return index;
    }

    /**
     * Determining the priority of operations
     * @param oper - symbol of operation
     * @return - priority number
     */
    private int getRang(char oper){
        if(oper == '+' || oper == '-')
            return 1;
        if (oper == '*' || oper == '/')
            return 2;
        if(oper == 's' || oper == 'c' || oper == 't'|| oper == 'g')
            return 4;
        return 0;
    }
}
