/*****************************************************
 * class Scheme
 * Simulates a rudimentary Scheme interpreter
 *
 * ALGORITHM for EVALUATING A SCHEME EXPRESSION:
      1. Steal underpants.
      2. ...
      5. Profit!      

* Simple scheme expression 
      1. Iterate through the string and find the operator
      2. Store the operator
      3. Push the relevant numbers within the contained parentheses
      4. After all numbers are stored, evaluate

* Complex expression (eval()):
      1. Look for first inner and outer parentheses. 
      2. Find the innermost (simple) expression.
      3. Evaluate the expression using the simple scheme expression algo
      4. Replace the expression in the string with the answer.
      5. Repeat 1-4 until you are left with one pair of parentheses.
      6. Return the answer to the final expression. 

* STACK OF CHOICE: LLStack by Clyde Sinclair  
     b/c it is too legit to quit 
******************************************************/

//Team Gnomic Code - Sarah Yoon and Jason Mohabir
//APCS2 pd10
//HW24 - Schemin
//2016-04-02

public class Scheme{

    /****************************************************** 
     * precond:  Assumes expr is a valid Scheme (prefix) expression,
     *           with whitespace separating all operators, parens, and 
     *           integer operands.
     * postcond: Returns the simplified value of the expression, as a String
     * eg,
     *           evaluate( "( + 4 3 )" ) -> 7
     *	         evaluate( "( + 4 ( * 2 5 ) 3 )" ) -> 17
     ******************************************************/
    public static String evaluate( String expr ) 
    {
	Stack<String> stack = new LLStack<String>();
	Stack<String> tmp = new LLStack<String>();
	String numPush = ""; //for nums that will be pushed to stack
	
	int i = 0;
	while ( i < expr.length() ) { //iterate through the expr string

	    //System.out.println("~~~~~~~~~~~~~~~NEWITERATION~~~~~~~~~~~~");
	    //System.out.println("Iter:"+i);
	    //System.out.println("stack:"+stack);

	    String curr = expr.substring(i,i+1);
	    //System.out.println("curr:"+curr);


	    if (!curr.equals(")")) {
		//System.out.println("numPush:"+numPush);
		if (isNumber(curr)) { //curr is a number case
		    // System.out.println("isNumber curr:"+curr);
		    numPush += curr;
		    //System.out.println("numPush after isNumber curr add:"+numPush);
		}
		else if (curr.equals(" ")){ //curr is white space
		    //System.out.println("     Inside of whiteSpace case");
		    //System.out.println("whiteSpace case numPush:" + numPush + " " + numPush.length());
		     if (! (numPush.length() == 0)) { //numPush has something
			stack.push(numPush);
			//System.out.println("Whitespace numPush case stack:"+stack);
			numPush = "";
		     }
		}
		else {
		    stack.push(curr); // curr either operator or (
		}
		i++;
	    }
	    else { 
		while(!stack.peek().equals("(")) { //op determiner
		    tmp.push(stack.pop());
		}
		int op = 0;
		if (tmp.peek().equals("+")) {
		    tmp.pop();
		    op = 1;
		}
		else if (tmp.peek().equals("-")) {
		    tmp.pop();
		    op = 2;
		}
		else if (tmp.peek().equals("*")) {
		    tmp.pop();
		    op = 3;
		}
		//System.out.println("This means that I got to unloading");
		//System.out.println(tmp);
		String simp = unload(op, tmp);
		stack.pop();
		stack.push(simp);
		i++;
	    }
	}
	return stack.peek();
    }//end evaluate()

    
	/****************************************************** 
	 * precond:  Assumes top of input stack is a number.
	 * postcond: Performs op on nums until closing paren is seen thru peek().
	 *           Returns the result of operating on sequence of operands.
	 *           Ops: + is 1, - is 2, * is 3
	 ******************************************************/    
	public static String unload( int op, Stack<String> numbers ) 
	{
	    int result = Integer.parseInt(numbers.pop());
	    //System.out.println("This is the result " + result + ".");
	    while (!numbers.isEmpty()) {
		if (op==1) 
		    result += Integer.parseInt(numbers.pop());	    
		if (op==2)
		    result -= Integer.parseInt(numbers.pop());
		if (op==3)
		    result *= Integer.parseInt(numbers.pop());
	    }
	    return String.valueOf(result);
	}//end unload()
	 

	//optional check-to-see-if-its-a-number helper fxn:
	public static boolean isNumber( String s ) {
	    try {
		Integer.parseInt(s);
		return true;
	    }
	    catch( NumberFormatException e ) {
		return false;
	    }
	}

	//main method for testing
	public static void main( String[] args ) {

	    Stack<String> test = new LLStack<String>();
	    test.push("4");
	    test.push("3");
	    System.out.println( unload(1,test) );

	    
	    String zoo1 = "( + 4 3 )";
	    System.out.println(zoo1);
	    System.out.println("zoo1 eval'd: " + evaluate(zoo1) );
	    //...7

	    String zoo2 = "( + 4 ( * 2 5 ) 3 )";
	    System.out.println(zoo2);
	    System.out.println("zoo2 eval'd: " + evaluate(zoo2) );
	    //...17


	    String zoo3 = "( + 4 ( * 2 5 ) 6 3 ( - 56 50 ) )";
	    System.out.println(zoo3);
	    System.out.println("zoo3 eval'd: " + evaluate(zoo3) );
	    //...29

	    String zoo4 = "( - 1 2 3 )";
	    System.out.println(zoo4);
	    System.out.println("zoo4 eval'd: " + evaluate(zoo4) );
	    //...-4

	}//main

    }//end class Scheme
