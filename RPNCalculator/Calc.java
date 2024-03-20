package hw4;


import exceptions.EmptyException;
import java.util.Scanner;

/**
 * A program for an RPN calculator that uses a stack.
 */
public final class Calc {

  private LinkedStack<Integer> calculator = new LinkedStack<>();

  private boolean sufficientElements() {
    if (calculator.empty()) {
      System.out.println("ERROR: Need more numbers for this operation!");
      return false;
    }
    int elem = 0;
    try {
      elem = calculator.top();
      calculator.pop();
      calculator.top();
    } catch (EmptyException e) {
      System.out.println("ERROR: Need more numbers for this operation!");
      return false;
    } finally {
      calculator.push(elem);
    }
    return true;
  }

  private int setOp() {
    int n1 = calculator.top();
    calculator.pop();
    return n1;
  }


  private void operate(String input) {
    switch (input) {
      case "+":
        if (sufficientElements()) {
          calculator.push(setOp() + setOp());
        }
        break;
      case "-":
        if (sufficientElements()) {
          calculator.push(-setOp() + setOp());
        }
        break;
      case "*":
        if (sufficientElements()) {
          calculator.push(setOp() * setOp());
        }
        break;
      default:
        operate2(input);
    }
  }

  private void operate2(String input) {
    switch (input) {
      case "/":
        if (sufficientElements()) {
          divide(setOp(), setOp());
        }
        break;
      case "%":
        if (sufficientElements()) {
          modulo(setOp(), setOp());
        }
        break;
      default:
        operate3(input);
    }
  }

  private void operate3(String input) {
    switch (input) {
      case "?":
        System.out.println(calculator.toString());
        break;
      case ".":
        if (!calculator.empty()) {
          System.out.println(calculator.top());
        } else {
          System.out.println("ERROR: Number not found!");
        }
        break;
      case "!":
        break;
      default:
        if (validInput(input)) {
          calculator.push(Integer.parseInt(input));
        }
    }
  }

  private void divide(int n1, int n2) {
    if (n1 == 0) {
      System.out.println("ERROR: Cannot divide by zero!");
      calculator.push(n2);
      calculator.push(n1);
    } else {
      calculator.push(n2 / n1);
    }
  }

  private void modulo(int n1, int n2) {
    if (n1 == 0) {
      System.out.println("ERROR: Cannot divide by zero in modulo!");
      calculator.push(n2);
      calculator.push(n1);
    } else {
      calculator.push(n2 % n1);
    }
  }

  private boolean validInput(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (NumberFormatException e) {
      System.out.println("ERROR: Invalid input!");
      return false;
    }
  }


  /**
   * The main function. Starts calculator.
   *
   * @param args Not used.
   */
  public static void main(String[] args) {
    Scanner userInput = new Scanner(System.in);
    Calc calc = new Calc();
    boolean done = false;
    while (!done) {
      String input = userInput.next();
      if ("!".equals(input)) {
        done = true;
      }
      calc.operate(input);
    }
  }
}
