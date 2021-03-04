import java.util.regex.Pattern;

public class Notation {
	//data fields
	private static NotationQueue<Character> output;
	private static NotationStack<Character> stack;
	
	private static String[] operators = {"+","-","*","/"};
	private static final String oPS = "-+/*";
	private static final String operands = "0123456789";
	
	/**
	 * converts from infix to postfix 
	 * @param input 
	 */
	public static String convertInfixToPostfix (String input)
	    throws InvalidNotationFormatException {
		if (input == null || input.length() == 0 || !parenthesisCheck(input)) {
			throw new InvalidNotationFormatException("Input format is invalid");
		}
		output = new NotationQueue<Character>(input.length());
		stack = new NotationStack<Character>(input.length());
		
		try {
			for (int index = 0; index < input.length(); index++) {
				char character = input.charAt(index);
				switch (character) {
				case '+':
				case '-':
					operationGot(character, 1);
					break; //First precedence  
				case '*':
				case '/':
					operationGot(character, 2);
					break; //Second precedence
				case '(':
					stack.push('('); // push the left parenthesis
					break;
   
				case ')':
					parenGot(')'); // pop the right parenthesis
					break;
			    default: //if not an OPS then must be an operand
			    	output.enqueue(character);
			    	break;
			}
		}
			while (!stack.isEmpty()) {
				output.enqueue(stack.pop());
			}
	}
		catch (Exception ex){
			System.out.print(ex);
		}
		return output.toString();
	}
	
	public static String convertPostfixToInfix (String ex) 
			throws InvalidNotationFormatException {
		String infix = "";
		String nextChar;
		String operand = "";
		
		//Get rid of all spaces
		String s = "";
		while (ex.indexOf(' ') >= 0) { 
			ex = ex.replaceAll(" ", " ");
		}
		
		//space being introduced after each token
		for (int index = 0; index < ex.length(); index++) {
			s = s+ex.charAt(index) + " ";
		}
		
		ex = s.trim();
		
		NotationStack <String> postFixStack = new NotationStack<String>(ex.length());
		NotationStack <String> inFixStack = new NotationStack<String>(ex.length());
		
		try {
			for(int index = 0; index < ex.length(); index++) {
				inFixStack.push(Character.toString(ex.charAt(index)));
			}
			while (!inFixStack.isEmpty()) {
				postFixStack.push(inFixStack.pop());
			}
			while (!postFixStack.isEmpty()) {
				nextChar = postFixStack.pop();
				
				if(nextChar.equalsIgnoreCase("+") ||  
				nextChar.equalsIgnoreCase("-") || 
				nextChar.equalsIgnoreCase("*") ||
				nextChar.equalsIgnoreCase("/")) {
					if(!operand.isEmpty()) {
						inFixStack.push(operand);
						operand = "";
					}
					try {
						String oper1 = inFixStack.pop();
						String oper2 = inFixStack.pop();
						
						if(!operand.isEmpty()) {
							String expression = "(" + oper1 + nextChar + oper2 + ")";
						}
						else {
							String expression = oper1 + nextChar + oper2;
							inFixStack.push(expression);
						}
					}
					catch (StackUnderflowException e) {
						throw new InvalidNotationFormatException ("\nCannot "
								+ "process current user data" + 
								"\nERROR -Invalid postfix expression entered" +
								"\nStack is now being cleared to allow for "
								+ " processing of additional ofexpressions\n\n ");
					}
					
 
				}
				else if (isNumber(nextChar) || nextChar.equals(".")) {
					operand += nextChar;
				}
				
				else if (nextChar.equalsIgnoreCase(" ")) {
					//ignore spaces
					if (!operand.isEmpty()) {
						inFixStack.push(operand);
						operand = " ";
					}
				}
				else if (Pattern.matches(" [A-Za-z] ", nextChar.toString())) {
					operand+=nextChar;
				}
				else {
					//Invalid character
					s = ("Invalid: \"" + nextChar + "\"");
					s+= ("ERROR - unsupported value or character");
					s+= ("Program has been terminated with an error  ");
					throw new InvalidNotationFormatException(s);
				}
		} // end of while loop
			infix = inFixStack.top();
			
			if (inFixStack.equals("")) {
				throw new InvalidNotationFormatException("Unable to convert user entered Postfix expression: \n  \""
                        + ex + "\"");
			}
			
			else {
				while (ex.endsWith(" ")) {
                    ex = ex.trim();
                }
                while (infix.endsWith(" ")) {
                    infix = infix.substring(0, infix.length() - 2);
                }
			}
	 
	 
	} 
		catch (Exception except) {
			throw new InvalidNotationFormatException(s);
		}
		
		return "(" + infix + ")";
		
	}
	
	/**
	 * Method to evaluatePostfixExpression to evaluate the postfix expression.
	 * it will take in a string and return a double 
	 */
	@SuppressWarnings("deprecation")
	public static Double evaluatePostfixExpression(String postfixExpr) 
			throws InvalidNotationFormatException {
		//checks for valid values
		if (postfixExpr == null || postfixExpr.length() == 0) {
			throw new InvalidNotationFormatException("Invalid postfix expression!");
		}
		
		NotationStack<Double> stack = new NotationStack<Double>();
		
		try {
            char[] chars = postfixExpr.toCharArray();

            for (char c : chars) {
                if (isOperand(c)) {
                    stack.push(new Double(c - '0')); // change char to int  
                } else if (isOperator(c)) {
                    Double oper1 = 0.0;
                    Double oper2 = 0.0;
                    if (stack.isEmpty()) {
                        throw new InvalidNotationFormatException("Invalid format");
                    }
                    oper1 = stack.pop();
                    if (stack.isEmpty()) {
                        throw new InvalidNotationFormatException("Invalid format");
                    }
                    oper2 = stack.pop();
                    Double result;
                    switch (c) {
                        case '*':
                            result = oper1 * oper2;
                            stack.push(result);
                            break;
                        case '/':
                            result = oper2 / oper1;
                            stack.push(result);
                            break;
                        case '+':
                            result = oper1 + oper2;
                            stack.push(result);
                            break;
                        case '-':
                            result = oper2 - oper1;
                            stack.push(result);
                            break;
                    }
                }
            }
            return stack.pop();
        } catch (Exception ex) {
            throw new InvalidNotationFormatException("Invalid postfix expression!");
        }
        
    }
	
	/**
     * Method to check if given string has balanced parenthesis
     *
     * @param s
     * @return
     */
    private static boolean parenthesisCheck(String s) {
        int stack = 0;
        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '(') {
                ++stack;
            } else if (c == ')') {
                --stack;
                if (stack < 0) {
                    return false;
                }
            }
        }
        return stack == 0;
    }

    private static boolean isOperator(char value) {
        return oPS.indexOf(value) >= 0;
    }

    private static boolean isOperand(char value) {
        return operands.indexOf(value) >= 0;
    }

    private static boolean isNumber(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException numForEx) {
            return false;
        }
    }
    
    private static void operationGot(char opThis, int prec1) throws StackUnderflowException, QueueOverflowException, StackOverflowException {
        while (!stack.isEmpty()) {
            char opTop = ((String) (stack.pop() + "")).charAt(0);
            if (opTop == '(') {
            	stack.push(opTop);
                break;
            }// it's an operator
            else {// precedence of new op
                int prec2;
                if (opTop == '+' || opTop == '-') {
                    prec2 = 1;
                } else {
                    prec2 = 2;
                }
                if (prec2 < prec1) // if prec of new op less
                { //    than prec of old
                	stack.push(opTop); // save newly-popped op
                    break;
                } else // prec of new not less
                {
                    output.enqueue(opTop); // than prec of old
                }
            }
        }
        stack.push(opThis);
    }

    private static void parenGot(char ch) throws StackUnderflowException, QueueOverflowException {
        while (!stack.isEmpty()) {
            char chx = ((String) (stack.pop() + "")).charAt(0);
            if (chx == '(') {
                break;
            } else {
                output.enqueue(chx);
            }
        }
    }

 
    private int operator(String n) {
        for (int i = 0; i < operators.length; i++) {
            if (n.equals(operators[i])) {
                return i;
            }
        }
        return operators.length;
    }

 
		
	}
 
		

	
