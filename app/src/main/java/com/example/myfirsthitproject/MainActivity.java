package com.example.myfirsthitproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView textView; // Displays input and results
    String input = ""; // To store the current input
    String operator = ""; // To store the selected operator
    String operand1 = ""; // First operand
    String operand2 = ""; // Second operand
    boolean isOperatorClicked = false; // Flag to check if an operator was clicked

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the TextView
        textView = findViewById(R.id.textID);

        // Handle system bar insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Method to handle button clicks
    public void ButtonFunc(View view) {
        Button button = (Button) view;
        String value = button.getText().toString();

        // Check if the button is a digit or an operator
        if (value.matches("\\d")) { // Check if the button is a number (0-9)


            if (isOperatorClicked) {
                operand2 += value; // Add to the second operand
            } else {
                operand1 += value; // Add to the first operand
            }
            input += value; // Update the overall input string
        }
        else if (value.matches("[+X \\-:]")) { // Check if the button is an operator

            if (!operand1.isEmpty() && !operand2.isEmpty() && !operator.isEmpty()) {

                handleMoreThanTwo(value);
                input += " " + value + " "; // Update the input string with the operator

            }

            else if (!operand1.isEmpty()) { // Only allow an operator if there's a first operand

                    operator = value;
                isOperatorClicked = true; // Set flag to start capturing the second operand
                input += " " + value + " "; // Update the input string with the operator
            }
        }
        else if (value.equals("=")) { // Handle the "=" button

            if (!operand1.isEmpty() && !operand2.isEmpty() && !operator.isEmpty()) {

                if (operator.equals(":") && operand2.equals("0")){
                    textView.setText("Invalid Syntax");}

                else {
                    double result = calculateResult(operand1, operand2, operator); // Perform calculation

                    if (result % 1 == 0) {
                        int temp = (int) result;
                        input += " = " + temp; // Update the input string with the result
                        textView.setText(String.valueOf(temp));
                    } // Display the result}
                    else {
                        input += " = " + result; // Update the input string with the result
                        textView.setText(String.valueOf(result)); // Display the result
                    }}
                    input = "";
                    operator = "";
                    operand1 = "";
                    operand2 = "";

              // resetCalculator(); // Reset for next calculation
               return; // Exit early to avoid updating TextView again
            }
        } else if (value.equals("C")) { // Handle the "C" (clear) button
            resetCalculator();
        }

        // Update the TextView
       textView.setText(input);
    }

    // Helper method to perform calculations
    private double calculateResult(String op1, String op2, String operator) {
        double num1 = Double.parseDouble(op1);
        double num2 = Double.parseDouble(op2);


        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "X":
                return num1 * num2;
            case ":":
                if (num2 != 0) {
                    return num1 / num2;
                } else {
                    return Double.NaN; // Handle division by zero
                }
            default:
                return 0;
        }
    }

    // Helper method to reset the calculator
    private void resetCalculator() {
        input = "";
        operator = "";
        operand1 = "";
        operand2 = "";
        isOperatorClicked = false;
        textView.setText(""); // Clear the display
    }

    private void handleMoreThanTwo (String val){
        double result = calculateResult(operand1, operand2, operator); // Perform calculation

        operand1 = String.valueOf(result);
        operator = val;
        operand2 ="";

    }

}
