package com.zakati.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by rahil on 5/12/16.
 */

public class SpaceTextWatcher implements TextWatcher {


    private static final String LETTER_SPACING = "   ";

    private EditText mOtpEditText;

    private String myPreviousText;


    public SpaceTextWatcher(EditText otpEditText) {
        this.mOtpEditText = otpEditText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();

        // Only update the EditText when the user modify it -> Otherwise it will be triggered when adding spaces
        if (!text.equals(myPreviousText)) {
            // Remove spaces
            text = text.replace("   ", "");

            // Add space between each character
            StringBuilder newText = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                if (i == text.length() - 1) {
                    // Do not add a space after the last character -> Allow user to delete last character
                    newText.append(Character.toUpperCase(text.charAt(text.length() - 1)));
                }
                else {
                    newText.append(Character.toUpperCase(text.charAt(i)) + LETTER_SPACING);
                }
            }

            myPreviousText = newText.toString();

            // Update the text with spaces and place the cursor at the end
            mOtpEditText.setText(newText);
            mOtpEditText.setSelection(newText.length());
        }
    }
}
