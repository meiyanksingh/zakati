package com.zakati.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rahil on 8/9/15.
 */
public class ValidationHelper {


    public enum ALERT_TYPE {TOAST, SNACK_BAR}

    /**
     * This method returns true if a edit text is blank ,false otherwise
     *
     * @param targetEditText source edit text
     * @param msg            message to be shown in snackbar
     * @return
     */
    public static boolean isBlank(@NonNull TextView targetEditText, String msg) {
        String source = targetEditText.getText().toString().trim();
        if (source.isEmpty()) {
            showAlert(targetEditText, ALERT_TYPE.SNACK_BAR, msg);
            return true;
        }
        return false;
    }

    public static boolean isBlank(@NonNull TextView targetEditText) {
        return targetEditText.getText().toString().trim().isEmpty();
    }


    public static boolean isBlank(@NonNull TextView textView, String msg, boolean showToast) {
        String source = textView.getText().toString().trim();
        if (source.isEmpty() && showToast) {
            showToast(textView.getContext(), msg);
            return true;
        }
        return false;
    }


    /**
     * This method returns true if a edit text contains valid email ,false otherwise
     *
     * @param targetEditText source edit text
     * @param msg            message to be shown in snackbar
     * @return
     */
    public static boolean isEmailValid(@NonNull EditText targetEditText, String msg) {
        String source = targetEditText.getText().toString().trim();
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern p = Pattern.compile(expression, Pattern.CASE_INSENSITIVE); // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
        Matcher m = p.matcher(source);
        if (m.matches() && source.trim().length() > 0) {
            return true;
        }
        showAlert(targetEditText, ALERT_TYPE.SNACK_BAR, msg);
        return false;
    }

    public static boolean isEmailValid(@NonNull EditText targetEditText, String msg, boolean showToast) {
        String source = targetEditText.getText().toString().trim();
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern p = Pattern.compile(expression, Pattern.CASE_INSENSITIVE); // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
        Matcher m = p.matcher(source);
        if (m.matches() && source.trim().length() > 0) {
            return true;
        }
        if (showToast)
            showAlert(targetEditText, ALERT_TYPE.TOAST, msg);
        return false;
    }

    public static boolean isEmailValid(@NonNull EditText targetEditText) {
        String source = targetEditText.getText().toString().trim();
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern p = Pattern.compile(expression, Pattern.CASE_INSENSITIVE); // pattern=/^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
        Matcher m = p.matcher(source);
        if (m.matches() && source.trim().length() > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method returns true if a edit text contains any digit in it ,false otherwise
     *
     * @param targetEditText source edit text
     * @param msg            message to be shown in snackbar
     * @return
     */
    public static boolean isContainDigit(@NonNull EditText targetEditText, ALERT_TYPE alertType, String msg, boolean msgType) {
        String pattern = ".*\\d.*";
        String source = targetEditText.getText().toString().trim();
        if (source.matches(pattern)) {
            if (msgType) {
                showAlert(targetEditText, alertType, msg);
            }
            return true;
        } else {
            if (!msgType) {
                showAlert(targetEditText, alertType, msg);
            }
            return false;
        }
    }

    public static boolean containOnlyDigit(@NonNull EditText targetEditText, String msg) {
        String pattern = "\\d+";
        String source = targetEditText.getText().toString().trim();
        if (source.matches(pattern)) {
            return true;
        } else {
            showAlert(targetEditText, ALERT_TYPE.SNACK_BAR, msg);
            return false;
        }
    }


    public static boolean isEqual(@NonNull EditText sourceEditText, @NonNull EditText destinationEditText, String msg, boolean msgType) {
        return isEqual(sourceEditText, destinationEditText, ALERT_TYPE.SNACK_BAR, msg, msgType);

    }

    public static boolean isEqual(@NonNull EditText sourceEditText, @NonNull EditText destinationEditText, ALERT_TYPE alertType, String msg, boolean msgType) {

        String source = sourceEditText.getText().toString().trim();
        String destination = destinationEditText.getText().toString().trim();
        if (source.equalsIgnoreCase(destination)) {
            if (msgType) {
                showAlert(destinationEditText, alertType, msg);
            }
            return true;
        } else {
            if (!msgType) {
                showAlert(destinationEditText, alertType, msg);
            }
            return false;
        }

    }

    public static boolean isSame(@NonNull TextView sourceEditText,String destinationString, ALERT_TYPE alertType, String msg, boolean msgType) {

        String source = sourceEditText.getText().toString().trim();
        if (source.equalsIgnoreCase(destinationString)) {
            if (msgType) {
                showAlert(sourceEditText, alertType, msg);
            }
            return true;
        } else {
            if (!msgType) {
                showAlert(sourceEditText, alertType, msg);
            }
            return false;
        }

    }

    public static void showToast(Context context, String msg) {
        Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    /*private static void showAlert(EditText targetEditText, View parentLayout, String msg) {
        View v = parentLayout == null ? targetEditText.getRootView() : parentLayout;
        targetEditText.requestFocus();
        *//*YoYo.with(Techniques.Shake)
                .duration(1000)
                .playOn((View) targetEditText.getParent());*//*
        showSnackBar(v, msg);
    }*/

    private static void showAlert(Context context, String msg) {

    }

    private static void showAlert(TextView targetEditText, ALERT_TYPE alertType, String msg) {
        //View v = parentLayout == null ? targetEditText.getRootView() : parentLayout;
        targetEditText.requestFocus();
        if (alertType == ALERT_TYPE.TOAST) {
            showToast(targetEditText.getContext(), msg);
        } else if (alertType == ALERT_TYPE.SNACK_BAR) {
            showSnackBar(targetEditText, msg);
        }


       /* YoYo.with(Techniques.Shake)
                .duration(1000)
                .playOn((View) targetEditText.getParent());*/

    }


    public static void showSnackBar(View parentLayout, String msg) {
        //Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        final Snackbar snackBar = Snackbar.make(parentLayout, msg, Snackbar.LENGTH_SHORT);
        snackBar.setActionTextColor(Color.WHITE);
        View view = snackBar.getView();
        TextView tv = (TextView)
                view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(Color.WHITE);
       /* snackBar.setAction(R.string.dismiss, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackBar.dismiss();
            }
        });*/
        snackBar.show();

    }

    public static boolean hasMinimumLength(String source, int length) {
        if (source.trim().length() >= length)
            return true;
        return false;
    }

    public static boolean hasMinimumLength(EditText editText, int length, String message) {
        if (!hasMinimumLength(editText.getText().toString().trim(), length)) {
            showAlert(editText, ALERT_TYPE.SNACK_BAR, message);
            return false;
        }
        return true;

    }

    public static InputFilter getBlockedSpecialCharacterFilter() {
        final String blockCharacterSet = "~#^|$%&*!@+_-1234567890";
        return new InputFilter() {

            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (source != null && blockCharacterSet.contains(("" + source))) {
                    return "";
                }
                return null;
            }
        };

    }

    public static boolean isValidName(TextView textView, String message) {
        String targetString = textView.getText().toString().trim();
        String regx = "^[\\p{L} .'-]+$";
        if (Pattern.matches(regx, targetString)) {
            return true;
        }
        showAlert(textView, ALERT_TYPE.SNACK_BAR, message);
        return false;
    }

    public static boolean hasMinimumwords(EditText editText, ALERT_TYPE alertType, int length, String message) {
        if (editText.getText().toString().trim().length() >= length) {
            showAlert(editText, alertType, message);
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidURL(EditText mFeedEditText, ALERT_TYPE alertType, String msg) {

        String url = mFeedEditText.getText().toString().toLowerCase();
        if (Patterns.WEB_URL.matcher(url).matches()) {
            return true;
        } else {
            showAlert(mFeedEditText, alertType, msg);
            return false;
        }
    }

    public static boolean isValidExpDate(String expDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/yy", Locale.getDefault());
        Date strDate = null;
        try {
            strDate = sdf.parse(expDate);
        } catch (ParseException e) {
            return false;
        }
        Calendar validDate = new GregorianCalendar();
        validDate.setTime(strDate);
        return Calendar.getInstance().before(validDate);
    }

    public static boolean isValidExpDate(EditText expDateEt, String msg) {
        String expDate = expDateEt.getText().toString();
        if (!isValidExpDate(expDate)) {
            showSnackBar(expDateEt, msg);
            return false;
        }
        return true;
    }

}
