package com.zakati.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by rahil on 18/1/16.
 */
public class Utils {


    /**
     * This method is used to detect whether network is available or not
     *
     * @param context application Context
     * @return true if network available ,false otherwise
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }


    /**
     * Method to show keyboard
     *
     * @param context Context of the calling activity
     */
    public static void showKeyboard(Context context) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(((Activity) context).getCurrentFocus(), InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {

        }
    }

    public static DatePickerDialog getBirthDatePicker(Context context, DatePickerDialog.OnDateSetListener callback) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, callback, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());


        return datePickerDialog;
    }

    /**
     * Method to show keyboard
     *
     * @param context  Context of the calling activity
     * @param editText Edittext which will receive focus
     */
    public static void showKeyboard(Context context, EditText editText) {
        showKeyboard(context);
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_FORCED);
            imm.showSoftInput(((Activity) context).getCurrentFocus(), InputMethodManager.SHOW_FORCED);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void modifyDialogBounds(Dialog dialog) {
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
//        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.width = (int) (dialog.getContext().getResources().getDisplayMetrics().widthPixels * 0.8);
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT/*(int) (dialog.getContext().getResources().getDisplayMetrics().heightPixels * 0.35)*/;
        window.setAttributes(lp);
    }


    public static String downloadUrl(String strUrl) throws IOException {
        Lg.e("url", strUrl);
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Lg.e("Exception while downloading url", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }

        Lg.e("data", data);
        return data;

    }

    public static void generateKeyHash(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Lg.e("KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
        }
    }

    public static <T> String toJson(T model) {
        return new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .create().toJson(model);
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        return new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .create().fromJson(json, clazz);
    }

    public static <T> List<T> fromJsonToList(String json, Type listType) {
        return new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC)
                .create().fromJson(json, listType);
    }

    public static void hideView(View view) {
        if (view != null)
            view.setVisibility(View.GONE);
    }

    public static void showView(View view) {
        if (view != null)
            view.setVisibility(View.VISIBLE);
    }

    public static boolean validatePasswordSameFields(EditText password, EditText confPassword) {
        // boolean status = false;
        return password.getText().toString().equals(confPassword.getText().toString());
        // return status;
    }

    public static boolean validatePasswordSameFields(EditText password, EditText confPassword, TextView textView, String msg) {
        textView.setHint("*" + " " + msg);
        // boolean status = false;
        return password.getText().toString().equals(confPassword.getText().toString());
        // return status;
    }

    public static boolean validatePasswordSameFields(Context con, EditText password, EditText confPassword, String msg, TextView textView) {

        if (password.getText().toString().equals(confPassword.getText().toString())) {
            textView.setVisibility(View.INVISIBLE);
            return true;

        } else {
            DialogUtil.showToast(con, msg);
            textView.setVisibility(View.VISIBLE);
            return false;
        }
    }

    public static boolean validatePasswordSameFields(Context con, EditText password, EditText confPassword, String msg) {

        if (password.getText().toString().equals(confPassword.getText().toString())) {
            return true;

        } else {
            DialogUtil.showToast(con, msg);
            return false;
        }
    }

   /* public static void setDate(Context context, final TextView mDobEt) {

        DatePickerCustomDialog datePickerCustomDialog = DatePickerCustomDialog.getInstance(context, new DatePickerCallback() {
            @Override
            public void onDateSetCallBack(String date) {
                mDobEt.setText(date);
            }

        });
        Calendar c = Calendar.getInstance();
        int pYear = c.get(Calendar.YEAR);
        int pMonth = c.get(Calendar.MONTH);
        int pDay = c.get(Calendar.DAY_OF_MONTH);
        c.set(pYear, pMonth, pDay);
        datePickerCustomDialog.setMaxDateCustom(c.getTimeInMillis());
    }*/

    public long getSize(Context context, Uri uri) {

          /*
     * Get the file's content URI from the incoming Intent,
     * then query the server app to get the file's display name
     * and size.
     */
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);
    /*
     * Get the column indexes of the data in the Cursor,
     * move to the first row in the Cursor, get the data,
     * and display it.
     */
        //int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        Lg.e("Size", Long.toString(returnCursor.getLong(sizeIndex)));
        returnCursor.close();
        return returnCursor.getLong(sizeIndex);
    }


    public static Bitmap StringToBitMap(String image) {
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    public static String getUtc(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

        System.out.println(formatter.format(new Date(milliSeconds)));


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds * 1000);
        long time = System.currentTimeMillis();
        System.out.println(time);
        Date date = new Date(time);
        System.out.println(date);

        return formatter.format(calendar.getTime());
    }

    public static String getLocalDate(long milliSeconds, String dateFormat) {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        String dateString = formatter.format(new Date(milliSeconds));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);

        calendar.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(calendar.getTimeInMillis()));
//        return formatter.format(calendar.getTime());
        return dateString;
    }

    public static String getCountryName(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Address result;

            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0).getLocality();
            }
            return "";
        } catch (IOException ignored) {
            //do something
        }
        return "";
    }

    public static Address getAddress(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            Address result;

            if (addresses != null && !addresses.isEmpty()) {
                return addresses.get(0);
            }
            return null;
        } catch (IOException ignored) {
            return null;
        }


    }


    /*public static final String getTimeGSAgo(String utcDateTime, Context ctx) {

        if (utcDateTime.length() > 0) {

            // "CreatedOn": "2013-11-21 12:14:39",

            DateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date;
            try {
                date = utcFormat.parse(utcDateTime);
                DateFormat pstFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                pstFormat.setTimeZone(TimeZone.getDefault());

                System.out.println("pst format" + pstFormat.format(date));
                Date convertedDate = pstFormat.parse(pstFormat.format(date));
                System.out.println("difference" + (System.currentTimeMillis() - convertedDate.getTime()) / (1000 * 3600));
                long totalSeconds = (System.currentTimeMillis() - convertedDate.getTime()) / (1000);
                if (totalSeconds < 60) {
                    if (totalSeconds < 1) {
                        return "0" + " " + ctx.getString(R.string.sec_ago);
                    }
                    if (totalSeconds == 1) {
                        return "1" + " " + ctx.getString(R.string.sec_ago);
                    } else {
                        return totalSeconds + " secs ago";
                    }
                } else if (totalSeconds >= 60 && totalSeconds < 3600) {
                    long min = totalSeconds / 60;
                    if (min == 1) {
                        return "1" + " " + ctx.getString(R.string.min_ago);
                    } else {
                        return min + " " + ctx.getString(R.string.min_ago);
                    }
                } else if (totalSeconds >= 3660 && totalSeconds < 86400) {
                    long hour = totalSeconds / 3600;
                    if (hour == 1) {
                        return "1"+ " " + ctx.getString(R.string.hour_ago);
                    } else {
                        return hour + " " + ctx.getString(R.string.hours_ago);
                    }
                } else if (totalSeconds >= 86400*//* && totalSeconds <= 259200*//*) {
                    int secondsInAMinute = 60;
                    int secondsInAnHour = 60 * secondsInAMinute;
                    long secondsInADay = 24 * secondsInAnHour;

                    long days = totalSeconds / secondsInADay;

                    if (days == 1) {
                        // return @"yesterday";
                        return  ctx.getString(R.string.yesterday);
                    } else {
                        return days + " " + ctx.getString(R.string.days_ago);
                    }
                } else {
                    DateFormat format = new SimpleDateFormat("MMMM dd, yyyy ");
                    return format.format(convertedDate);
                }
            } catch (Exception e) {

            }
        }
        return "";
    }*/

    public static Bitmap decodeFile(File f) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 200;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return checkRotationAndGetBitmap(f.getAbsolutePath(), o2);

//            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
//
//            FileOutputStream fileOutputStream = new FileOutputStream(f);
//
//            b.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);

        } catch (FileNotFoundException e) {
        }
        return null;
    }


    public static Bitmap checkRotationAndGetBitmap(String photoPath, BitmapFactory.Options o2) {
        ExifInterface ei = null;
        Bitmap bitmap = null;
        try {
            ei = new ExifInterface(photoPath);

            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    bitmap = rotateImage(BitmapFactory.decodeStream(new FileInputStream(photoPath), null, o2), 90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    bitmap = rotateImage(BitmapFactory.decodeStream(new FileInputStream(photoPath), null, o2), 180);
                    break;
                default:
                    bitmap = BitmapFactory.decodeStream(new FileInputStream(photoPath), null, o2);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap retVal;

        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return retVal;
    }


    public static int getDeviceHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //mwidth = displaymetrics.widthPixels;
        return displaymetrics.heightPixels;
    }

    public static int getDeviceWidth(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //mwidth = displaymetrics.widthPixels;
        return displaymetrics.widthPixels;
    }

    public static int[] screenSize(Context context) {
        int[] size = new int[2];
        DisplayMetrics displaymetrics = context.getResources()
                .getDisplayMetrics();
        size[0] = displaymetrics.widthPixels;
        size[1] = displaymetrics.heightPixels;

        return size;
    }

    public static void invisibleView(View v) {
        v.setVisibility(View.INVISIBLE);
    }


    public static void openPlayStore(Context context) {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static StateListDrawable getStateListDrawable(Drawable normal, Drawable pressed) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{-android.R.attr.state_pressed}, normal);
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, pressed);
        return stateListDrawable;
    }

    public static String getUniqueId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static String enCodeMd5(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")), 0, s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi = new BigInteger(1, magnitude);
            return String.format("%0" + (magnitude.length << 1) + "x", bi);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Map<String, Object> getBeanToMap(Object bean) {
        Map<String, Object> properties = new HashMap<>();
        for (Method method : bean.getClass().getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers())
                    && method.getParameterTypes().length == 0
                    && method.getReturnType() != void.class
                    && method.getName().matches("^(get|is).+")
                    ) {
                String name = method.getName().replaceAll("^(get|is)", "");
                name = Character.toLowerCase(name.charAt(0)) + (name.length() > 1 ? name.substring(1) : "");
                Object value = null;
                try {
                    value = method.invoke(bean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                /*if (value != null)
                    properties.put(name, value);*/

                if (value != null) {
                    Field[] fields = bean.getClass().getDeclaredFields();
                    for (Field field :
                            fields) {
                        if (field.getName().equalsIgnoreCase(name)) {
                            properties.put(field.getName(), value);
                            break;
                        }
                    }
                }
            }
        }
        return properties;
    }

    public static JSONObject getJsonRestaurantImage(String userid, String restid, ArrayList<String> list) {
        JSONObject jsonObject = new JSONObject();
        return null;
    }

    public static String getJsonFomatString(ArrayList<String> list) {
        StringBuilder builder = new StringBuilder();
        if (list != null && list.size() > 0) {
            builder.append("[");
            for (int i = 0; i < list.size(); i++) {
                String url = "\"" + list.get(i) + "\"";
                builder.append(url);
                if (i != list.size() - 1) {
                    builder.append(",");
                }
            }
            builder.append("]");
        }
        return builder.toString();


    }

    public static String convertTimeStampToTime(String timestr) {
        if (timestr != null && !timestr.equals("")) {
            long timestamp = Long.parseLong(timestr);
            Date date = new Date(timestamp);
            DateFormat format = new SimpleDateFormat("h:mm a");
            String str = format.format(date);
            Lg.e("convert time stamp", str + "..");
            return str;
        } else {
            return "";
        }
    }

    public static String getValueWithTwoDecimal(String distance) {
        if (distance != null && !distance.equals("")) {
            try {
                Double dis = Double.parseDouble(distance);
                String newDis = new DecimalFormat("##.##").format(dis);
                return newDis;
            } catch (NumberFormatException e) {
                return distance;
            }
        }
        return "";
    }

    public static String convertListToString(List<Integer> list) {
        StringBuilder stringBuilder = new StringBuilder();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                stringBuilder.append(list.get(i));
                if (i != list.size() - 1) {
                    stringBuilder.append(",");
                }
            }
        }
        return stringBuilder.toString();
    }

    public static double ToRadians(double degrees) {
        double localpi = 3.1415926535897932385;
        double radians = degrees * localpi / 180;
        return radians;
    }

    public static Double directDistance(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 3958.75;
        double dLat = ToRadians(lat2 - lat1);
        double dLng = ToRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(ToRadians(lat1)) * Math.cos(ToRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;
        //double meterConversion = 1609.00;
        return dist /** meterConversion*/;
    }

    public static String getStringWithOutQoutes(String str, Context context) {
        String timestr = getRestTime(str);
        //  String withoutQuotes_line1 = str.replaceAll("\"", "");
        // String witoutBrackets = withoutQuotes_line1.replaceAll("\\p{P}", "");
        // String withoutzero = witoutBrackets.replace("00", "");
        //time = Integer.parseInt(withoutzero);
        String resttime = "";
  /*      if (time < 12) {
            resttime = time + " " + context.getString(R.string.am);
        } else {
            time = time - 12;
            resttime = time + " " + context.getString(R.string.pm);
        }*/
        return timestr;
    }

    public static String getRestTime(String str) {
        String witdecimal = str.replaceAll(":", ".");
        String withoutQuotes_line1 = witdecimal.replaceAll("\"", "");
        String witoutBrackets = withoutQuotes_line1.replaceAll("\\p{P}", "");
        Long timevalue = Long.parseLong(witoutBrackets);
        int x = 2;
        BigDecimal unscaled = new BigDecimal(timevalue);
        BigDecimal scaled = unscaled.scaleByPowerOfTen(-x);
        String time = String.valueOf(scaled).replace(".", ":");
        return convertToAmPm(time) + "";
    }

    public static Double getTimeInDouble(String str) {
        String witdecimal = str.replaceAll(":", ".");
        String withoutQuotes_line1 = witdecimal.replaceAll("\"", "");
        String witoutBrackets = withoutQuotes_line1.replaceAll("\\p{P}", "");
        Long timevalue = Long.parseLong(witoutBrackets);
        int x = 2;
        BigDecimal unscaled = new BigDecimal(timevalue);
        BigDecimal scaled = unscaled.scaleByPowerOfTen(-x);
        Double time = Double.parseDouble(scaled + "");
        return time;
    }


    public static String string(int timestamp) {
        long unixSeconds = Long.parseLong(timestamp + "");
        Date date = new Date(unixSeconds * 1000L);
        SimpleDateFormat localDateFormat = new SimpleDateFormat("MM-dd-yyyy hh:mm:ss a");
        String str = localDateFormat.format(date);
        return str;
    }

    public static String getServerTimeAmPm(String timestamp) {
        long unixSeconds = Long.parseLong(timestamp);
        Date date = new Date(unixSeconds * 1000L);
        SimpleDateFormat localDateFormat = new SimpleDateFormat("K:mm a");
        String str = localDateFormat.format(date);
        return str;
    }

    public static String convertToAmPm(String time) {
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
            final Date dateObj = sdf.parse(time);
            return new SimpleDateFormat("K:mm a").format(dateObj);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convertTimeStampToHours(int servretime) {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
        sdfDate.setTimeZone(TimeZone.getTimeZone("IST"));

        SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        sdfTime.setTimeZone(TimeZone.getTimeZone("UTC"));
        String servertimestring = String.valueOf(servretime);
        long servertimelong = Long.valueOf(servertimestring);
        Date date = new Date(servertimelong);

        String dateStr = sdfDate.format(date);
        String timeStr = sdfTime.format(date);
        Lg.e("<<>>>>>>>/////", timeStr + ".." + servertimelong + ".." + servretime);
        return timeStr.split(":")[0];
    }

    public static String convertTimeStampToDate(int timestamp) {
        long unixSeconds = Long.parseLong(timestamp + "");
        Date date = new Date(unixSeconds * 1000L);
        SimpleDateFormat localDateFormat = new SimpleDateFormat("MM-dd-yyyy  hh:mm:ss a");
        String str = localDateFormat.format(date);
        return str;
    }


    public static String getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        switch (day) {
            case Calendar.SUNDAY:
                return "SUN";

            case Calendar.MONDAY:
                return "MON";

            case Calendar.TUESDAY:
                return "TUE";
            case Calendar.WEDNESDAY:
                return "WED";
            case Calendar.THURSDAY:
                return "THU";
            case Calendar.FRIDAY:
                return "FRI";
            case Calendar.SATURDAY:
                return "SAT";
        }
        return "";
    }


    /**
     * Method to hide keyboard
     *
     * @param mContext Context of the calling class
     */
    public static void hideKeyboard(Context mContext) {
        try {
            InputMethodManager inputManager = (InputMethodManager) mContext
                    .getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(((Activity) mContext).getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }


    public static void hideKeyboard(View editText) {
        hideKeyboard(editText.getContext());
        try {
            InputMethodManager imm = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        } catch (Exception ignored) {
        }
    }

    public static double distanceBetween(double startLatitude, double startLongitude, double endLatitude, double endLongitude) {
//        float[] results = new float[0];
//        try{
//            android.location.Location.distanceBetween(startLatitude,startLongitude,endLatitude,endLongitude,results);
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//        return results[0];

        Log.e("calculate Distance: ", startLatitude + "");
        Log.e("startlat: ", startLatitude + "");
        Log.e("startLng: ", startLongitude + "");
        Log.e("endLat: ", endLatitude + "");
        Log.e("endLng: ", endLongitude + "");
        return Utils.directDistance(startLatitude, startLongitude, endLatitude, endLongitude);
    }

    public static String fbProfilePicUrl(String fbUserId) {
        return "graph.facebook.com/" + fbUserId + "/picture?type=squre";
    }


    public static double convertMiterToMilles(double meter) {

        return meter / 1609.344;
    }

    public static boolean getProviderState(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    }

    @NonNull
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse("text/plain"), descriptionString);
    }


    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public static void copyBean(Object source, Object target) {

        Field[] fields = source.getClass().getDeclaredFields();
        for (Field sourceField :
                fields) {
            System.out.println(fields.length);
            try {
                sourceField.setAccessible(true);
                Field targetField = target.getClass().getDeclaredField(sourceField.getName());
                targetField.setAccessible(true);
                Object value2 = sourceField.get(source);
                targetField.set(target, value2);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    public static String loadJsonFromAsset(Context context, String fileName) {
        String json = "";
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
        return json;
    }

    protected void setupParentForCloseKeyword(View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard(v);
                    return false;
                }
            });
        }
        //If a layout container, iterate over children
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupParentForCloseKeyword(innerView);
            }
        }
    }

    public Uri resourceToUri(Context context, int resourceId) {
        Resources resources = context.getResources();
        return new Uri.Builder()
                .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .authority(resources.getResourcePackageName(resourceId))
                .appendPath(resources.getResourceTypeName(resourceId))
                .appendPath(resources.getResourceEntryName(resourceId))
                .build();
    }

   /* private void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
    }*/



}

