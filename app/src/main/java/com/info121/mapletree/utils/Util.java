package com.info121.mapletree.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import com.info121.mapletree.App;


import org.apache.commons.net.util.Base64;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by KZHTUN on 7/20/2017.
 */

public class Util {

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public static String getDeviceID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static boolean isNullOrEmpty(String string) {
        return (string == null || string.length() == 0 || string.toString().trim() == "");
    }

    static String printStackTrace(@NonNull Throwable exception) {
        StringWriter trace = new StringWriter();
        exception.printStackTrace(new PrintWriter(trace));
        return trace.toString();
    }

    public static void copyToClipboard(Context context, CharSequence text) {
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(null, text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(context, "Copy To Clipboard", Toast.LENGTH_SHORT).show();
    }

    static String safeReplaceToAlphanum(@Nullable String string) {
        if (string == null) {
            return null;
        }
        return string.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static int getVersionCode(Context context) {
        int versionCode = -1;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionName;
    }


    private int convertPxToDp(int px) {
        return Math.round(px / (Resources.getSystem().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }


    public static String replaceEscapeChr(String str) {
        if (str.length() > 0)
            return str.replace(",", "###");
        else
            return " ";
    }


    public static long convertDateStringToInt(String dateString) {
        long dateLong = 0L;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date date = sdf.parse(dateString);

            dateLong = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateLong;
    }

    public static Date convertDateStringToDate(String dateString, String inputDateFormat) {

        Date date = new Date();

        SimpleDateFormat sdf = new SimpleDateFormat(inputDateFormat);
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return date;
    }

    public static String convertLongDateToString(long date, String outputDateFormat) {
        return DateFormat.format(outputDateFormat, new Date(date)).toString();
    }

    public static String convertDateToString(Date date, String outputDateFormat) {
        return new SimpleDateFormat(outputDateFormat).format(date).toString();
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }


    public static String stringToBinary(String string) {
        byte[] decode = Base64.decodeBase64(string);
        return new BigInteger(1, decode).toString(2);
    }


    public static String convertStringToBinary(String s) {
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            //binary.append("");
        }

        return binary.toString();
    }

    public static String convertToSpecial(Context mContext) {
        Calendar c;

        c = Calendar.getInstance(App.timeZone);

        String dd, mm, yyyy, hh;

        dd = String.format("%02d", c.get(Calendar.DAY_OF_MONTH));
        mm = String.format("%02d", c.get(Calendar.MONTH) + 1);
        yyyy = String.format("%04d", c.get(Calendar.YEAR));
        hh = String.format("%02d", c.get(Calendar.HOUR_OF_DAY));

        String ret = "";
        String str = Util.getVersionCode(mContext) + dd + mm + yyyy + hh;

        for (char ch : str.toCharArray()) {
            ret = ret + convertChars(String.valueOf(ch));
        }
        return ret;
    }

    private static String convertChars(String chr) {

        Calendar c = Calendar.getInstance(App.timeZone);
        String hh;


        hh = String.format("%02d", c.get(Calendar.HOUR));

        String ret = "";

        if (hh.equals("01")) {
            if (chr.equals("0")) {
                ret = "f";
            }
            if (chr.equals("1")) {
                ret = "s";
            }
            if (chr.equals("2")) {
                ret = "v";
            }
            if (chr.equals("3")) {
                ret = "q";
            }
            if (chr.equals("4")) {
                ret = "n";
            }
            if (chr.equals("5")) {
                ret = "j";
            }
            if (chr.equals("6")) {
                ret = "h";
            }
            if (chr.equals("7")) {
                ret = "b";
            }
            if (chr.equals("8")) {
                ret = "r";
            }
            if (chr.equals("9")) {
                ret = "i";
            }
        }
        if (hh.equals("02")) {
            if (chr.equals("0")) {
                ret = "x";
            }
            if (chr.equals("1")) {
                ret = "b";
            }
            if (chr.equals("2")) {
                ret = "l";
            }
            if (chr.equals("3")) {
                ret = "j";
            }
            if (chr.equals("4")) {
                ret = "c";
            }
            if (chr.equals("5")) {
                ret = "n";
            }
            if (chr.equals("6")) {
                ret = "k";
            }
            if (chr.equals("7")) {
                ret = "h";
            }
            if (chr.equals("8")) {
                ret = "v";
            }
            if (chr.equals("9")) {
                ret = "m";
            }
        }
        if (hh.equals("03")) {
            if (chr.equals("0")) {
                ret = "q";
            }
            if (chr.equals("1")) {
                ret = "w";
            }
            if (chr.equals("2")) {
                ret = "e";
            }
            if (chr.equals("3")) {
                ret = "r";
            }
            if (chr.equals("4")) {
                ret = "a";
            }
            if (chr.equals("5")) {
                ret = "s";
            }
            if (chr.equals("6")) {
                ret = "d";
            }
            if (chr.equals("7")) {
                ret = "f";
            }
            if (chr.equals("8")) {
                ret = "z";
            }
            if (chr.equals("9")) {
                ret = "x";
            }
        }
        if (hh.equals("04")) {
            if (chr.equals("0")) {
                ret = "g";
            }
            if (chr.equals("1")) {
                ret = "b";
            }
            if (chr.equals("2")) {
                ret = "t";
            }
            if (chr.equals("3")) {
                ret = "u";
            }
            if (chr.equals("4")) {
                ret = "h";
            }
            if (chr.equals("5")) {
                ret = "n";
            }
            if (chr.equals("6")) {
                ret = "y";
            }
            if (chr.equals("7")) {
                ret = "i";
            }
            if (chr.equals("8")) {
                ret = "j";
            }
            if (chr.equals("9")) {
                ret = "m";
            }
        }
        if (hh.equals("05")) {
            if (chr.equals("0")) {
                ret = "o";
            }
            if (chr.equals("1")) {
                ret = "y";
            }
            if (chr.equals("2")) {
                ret = "e";
            }
            if (chr.equals("3")) {
                ret = "q";
            }
            if (chr.equals("4")) {
                ret = "i";
            }
            if (chr.equals("5")) {
                ret = "t";
            }
            if (chr.equals("6")) {
                ret = "w";
            }
            if (chr.equals("7")) {
                ret = "a";
            }
            if (chr.equals("8")) {
                ret = "u";
            }
            if (chr.equals("9")) {
                ret = "r";
            }
        }
        if (hh.equals("06")) {
            if (chr.equals("0")) {
                ret = "h";
            }
            if (chr.equals("1")) {
                ret = "c";
            }
            if (chr.equals("2")) {
                ret = "w";
            }
            if (chr.equals("3")) {
                ret = "j";
            }
            if (chr.equals("4")) {
                ret = "r";
            }
            if (chr.equals("5")) {
                ret = "a";
            }
            if (chr.equals("6")) {
                ret = "m";
            }
            if (chr.equals("7")) {
                ret = "s";
            }
            if (chr.equals("8")) {
                ret = "t";
            }
            if (chr.equals("9")) {
                ret = "y";
            }
        }
        if (hh.equals("07")) {
            if (chr.equals("0")) {
                ret = "p";
            }
            if (chr.equals("1")) {
                ret = "z";
            }
            if (chr.equals("2")) {
                ret = "h";
            }
            if (chr.equals("3")) {
                ret = "y";
            }
            if (chr.equals("4")) {
                ret = "j";
            }
            if (chr.equals("5")) {
                ret = "t";
            }
            if (chr.equals("6")) {
                ret = "f";
            }
            if (chr.equals("7")) {
                ret = "x";
            }
            if (chr.equals("8")) {
                ret = "q";
            }
            if (chr.equals("9")) {
                ret = "m";
            }
        }
        if (hh.equals("08")) {
            if (chr.equals("0")) {
                ret = "g";
            }
            if (chr.equals("1")) {
                ret = "t";
            }
            if (chr.equals("2")) {
                ret = "u";
            }
            if (chr.equals("3")) {
                ret = "f";
            }
            if (chr.equals("4")) {
                ret = "s";
            }
            if (chr.equals("5")) {
                ret = "x";
            }
            if (chr.equals("6")) {
                ret = "n";
            }
            if (chr.equals("7")) {
                ret = "e";
            }
            if (chr.equals("8")) {
                ret = "d";
            }
            if (chr.equals("9")) {
                ret = "q";
            }
        }
        if (hh.equals("09")) {
            if (chr.equals("0")) {
                ret = "n";
            }
            if (chr.equals("1")) {
                ret = "x";
            }
            if (chr.equals("2")) {
                ret = "s";
            }
            if (chr.equals("3")) {
                ret = "y";
            }
            if (chr.equals("4")) {
                ret = "w";
            }
            if (chr.equals("5")) {
                ret = "j";
            }
            if (chr.equals("6")) {
                ret = "k";
            }
            if (chr.equals("7")) {
                ret = "f";
            }
            if (chr.equals("8")) {
                ret = "p";
            }
            if (chr.equals("9")) {
                ret = "q";
            }
        }
        if (hh.equals("10")) {
            if (chr.equals("0")) {
                ret = "g";
            }
            if (chr.equals("1")) {
                ret = "b";
            }
            if (chr.equals("2")) {
                ret = "u";
            }
            if (chr.equals("3")) {
                ret = "j";
            }
            if (chr.equals("4")) {
                ret = "r";
            }
            if (chr.equals("5")) {
                ret = "x";
            }
            if (chr.equals("6")) {
                ret = "q";
            }
            if (chr.equals("7")) {
                ret = "i";
            }
            if (chr.equals("8")) {
                ret = "s";
            }
            if (chr.equals("9")) {
                ret = "d";
            }
        }
        if (hh.equals("11")) {
            if (chr.equals("0")) {
                ret = "j";
            }
            if (chr.equals("1")) {
                ret = "a";
            }
            if (chr.equals("2")) {
                ret = "b";
            }
            if (chr.equals("3")) {
                ret = "c";
            }
            if (chr.equals("4")) {
                ret = "d";
            }
            if (chr.equals("5")) {
                ret = "e";
            }
            if (chr.equals("6")) {
                ret = "f";
            }
            if (chr.equals("7")) {
                ret = "g";
            }
            if (chr.equals("8")) {
                ret = "h";
            }
            if (chr.equals("9")) {
                ret = "i";
            }
        }

        if (hh.equals("12") || hh.equals("00")) {
            if (chr.equals("0")) {
                ret = "n";
            }
            if (chr.equals("1")) {
                ret = "y";
            }
            if (chr.equals("2")) {
                ret = "x";
            }
            if (chr.equals("3")) {
                ret = "j";
            }
            if (chr.equals("4")) {
                ret = "v";
            }
            if (chr.equals("5")) {
                ret = "a";
            }
            if (chr.equals("6")) {
                ret = "s";
            }
            if (chr.equals("7")) {
                ret = "q";
            }
            if (chr.equals("8")) {
                ret = "e";
            }
            if (chr.equals("9")) {
                ret = "l";
            }
        }

        return ret;
    }




}
