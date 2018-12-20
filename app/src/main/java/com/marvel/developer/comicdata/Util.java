package com.marvel.developer.comicdata;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * Created by Tom Kowszun on 5/11/2016.
 */

public class Util {


    public static Date stringToDate(String dateString)
    {

//        System.out.println("----- stringToDate date before conversion ------" + dateString);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        Date convertedDate = new Date();
        try {
            convertedDate = dateFormat.parse(dateString);
        } catch (ParseException e) {

            e.printStackTrace();
        }
//        System.out.println("----- stringToDate converted date ------" + convertedDate);
        return convertedDate;
    }

    public static String dateToString(Date date)
    {
        String convertedDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

        convertedDate = dateFormat.format(date);
//        System.out.println("----- dateToString converted date ------" + convertedDate);
        return convertedDate;
    }

    public static boolean validateCityString(String str)
    {
        return str != null && str.length() > 0 && str.length() < 25;
    }

    private static boolean validateValueString(String str)
    {
        return str != null && str.length() > 0 && str.length() < 30;

    }

    // Check to make sure we have Wi Fi
    public static boolean isWiFiAvailable(ConnectivityManager conMan) {
        NetworkInfo networkInfo = conMan.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    // Weather description comes back in small caps like "cloudy conditions"
    // this method will change first letters of each word to caps
    public static String titleCaseConversion(String givenString) {
        if (givenString != null && givenString.length() > 0) {
            String[] arr = givenString.split(" ");
            StringBuilder sb = new StringBuilder();

            for (String anArr : arr) {
                sb.append(Character.toUpperCase(anArr.charAt(0)))
                        .append(anArr.substring(1)).append(" ");
            }
            return sb.toString().trim();
        }
        return null;
    }

    // Colors for the temperature display needed to be tweaked otherwise
    // they looked too dark
    public static int lighter(int color, float factor) {
        int red = (int) ((Color.red(color) * (1 - factor) / 255 + factor) * 255);
        int green = (int) ((Color.green(color) * (1 - factor) / 255 + factor) * 255);
        int blue = (int) ((Color.blue(color) * (1 - factor) / 255 + factor) * 255);
        return Color.argb(Color.alpha(color), red, green, blue);
    }

    // The weather conditions icons were too small so this method enlarges them
    // on-the-fly
    public static Bitmap scaleBitmap(Bitmap inputBitmap, int width, int height) {

        return Bitmap.createScaledBitmap(inputBitmap, width, height, true);
    }

    // This is for debugging layouts
    public static void outlineViews(View parent) {
        if (!(parent instanceof ViewGroup)) {
            return;
        }
        ViewGroup vg = (ViewGroup) parent;
        for (int i = 0; i < vg.getChildCount(); i++) {
            View view = vg.getChildAt(i);
            GradientDrawable gd = new GradientDrawable();
            gd.setStroke(1, Color.RED);
            view.setBackgroundDrawable(gd);
            outlineViews(view);
        }
    }


    /**
     * Simple point
     *
     *
     //    Step 1. Create a pointy class
     */
    private class Point {

        public float x = 0;
        public float y = 0;

        public Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }



    /**
     * Draw polygon
     *
     *     Step 2. Add a method/function for drawing
     *
     * @param canvas The canvas to draw on
     * @param color  Integer representing a fill color (see http://developer.android.com/reference/android/graphics/Color.html)
     * @param points Polygon corner points
     */
    private void drawPoly(Canvas canvas, int color, Point[] points) {
        // line at minimum...
        if (points.length < 2) {
            return;
        }

        // paint
        Paint polyPaint = new Paint();
        polyPaint.setColor(color);
        polyPaint.setStyle(Paint.Style.FILL);

        // path
        Path polyPath = new Path();
        polyPath.moveTo(points[0].x, points[0].y);
        int i, len;
        len = points.length;
        for (i = 0; i < len; i++) {
            polyPath.lineTo(points[i].x, points[i].y);
        }
        polyPath.lineTo(points[0].x, points[0].y);

        // draw
        canvas.drawPath(polyPath, polyPaint);
    }


// Step 3. Draw
//    drawPoly(canvas, 0xFF5555ee,
//                    new Point[]{
//        new Point(10, 10),
//                new Point(15, 10),
//                new Point(15, 20)
//    });
}