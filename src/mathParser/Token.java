package mathParser;
/**
 * A class that describes an element from a string
 * it consists of two parts: the type - is used to determine the type of the token, if 0 token is number, if operation sign then symbol of operator
 * the value - is used to determine the value of the token(only for number,if token is sign 'value' = 0)
 */

public class Token{
   
    public char Type;
    public double Value;

    public void Token(char type,double value){
        this.Type = type;
        this.Value = value;
    }
}
