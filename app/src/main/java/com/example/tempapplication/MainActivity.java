package com.example.tempapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends Activity {

    private EditText inputEditText;
    private Spinner inputSpinner;
    private TextView outputTextView;
    private Spinner outputSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = findViewById(R.id.inputEditText);
        inputSpinner = findViewById(R.id.inputSpinner);
        outputTextView = findViewById(R.id.outputEditText);
        outputSpinner = findViewById(R.id.outputSpinner);

        // Set up a TextWatcher to listen for input changes
        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed for this implementation
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update the output as the input changes
                convertTemperature();
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed for this implementation
            }
        });

        // Set up a listener for the output spinner
        outputSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Update the output as the output unit changes
                convertTemperature();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Not needed for this implementation
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void convertTemperature() {
        // Get the input value and unit from the EditText and Spinner
        String inputText = inputEditText.getText().toString().trim();

        if (inputText.isEmpty()) {
            // Handle the case where the input is empty
            outputTextView.setText("");
            return;
        }

        try {
            double inputValue = Double.parseDouble(inputText);
            String inputUnit = inputSpinner.getSelectedItem().toString();
            String outputUnit = outputSpinner.getSelectedItem().toString();
            double result = performConversion(inputValue, inputUnit, outputUnit);

            // Display the result in the output TextView
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            outputTextView.setText(decimalFormat.format(result));
        } catch (NumberFormatException e) {
            // Handle the case where the input cannot be parsed as a double
            outputTextView.setText("Invalid input. Please enter a valid number.");
        }
    }

    private double performConversion(double inputValue, String inputUnit, String outputUnit) {
        double result = inputValue; // Default to the same value if units are the same

        switch (inputUnit) {
            case "Celsius":
                if (outputUnit.equals("Fahrenheit")) {
                    // Celsius to Fahrenheit conversion
                    result = (inputValue * 9 / 5) + 32;
                } else if (outputUnit.equals("Kelvin")) {
                    // Celsius to Kelvin conversion
                    result = inputValue + 273.15;
                } // Add more conversions as needed for Celsius
                break;
            case "Fahrenheit":
                if (outputUnit.equals("Celsius")) {
                    // Fahrenheit to Celsius conversion
                    result = (inputValue - 32) * 5 / 9;
                } else if (outputUnit.equals("Kelvin")) {
                    // Fahrenheit to Kelvin conversion
                    result = (inputValue - 32) * 5 / 9 + 273.15;
                } // Add more conversions as needed for Fahrenheit
                break;
            case "Kelvin":
                if (outputUnit.equals("Celsius")) {
                    // Kelvin to Celsius conversion
                    result = inputValue - 273.15;
                } else if (outputUnit.equals("Fahrenheit")) {
                    // Kelvin to Fahrenheit conversion
                    result = (inputValue - 273.15) * 9 / 5 + 32;
                } // Add more conversions as needed for Kelvin
                break;
        }

        // Add more if statements for other temperature units as needed

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return Double.parseDouble(decimalFormat.format(result));
    }
}
