package com.shubham.findyourway;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.shubham.findyourway.MainActivity.arr3;
import static com.shubham.findyourway.MainActivity.arr4;

/**
 * Created by hp on 20-Jul-17.
 */

public class RoadParse {
    static boolean Rparse(String response) {
        try {
            Log.e("dddddddddd", "1");

            JSONObject h1 = new JSONObject(response);
            Log.e("dddddddddd", "2");

            JSONArray b1 = h1.getJSONArray("snappedPoints");
            Log.e("dddddddddd", "3");

            int count3 = 0;
            arr3 = new double[b1.length()];
            arr4 = new double[b1.length()];
            while (count3 < b1.length()) {
                JSONObject h2 = b1.getJSONObject(count3);
                Log.e("dddddddddd", "4" + h2);

                JSONObject h3 = h2.getJSONObject("location");
                Log.e("dddddddddd", "5" + h3);

                arr3[count3] = h3.getDouble("latitude");
                arr4[count3] = h3.getDouble("longitude");
                count3++;

            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

}
