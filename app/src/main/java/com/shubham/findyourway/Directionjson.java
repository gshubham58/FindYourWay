package com.shubham.findyourway;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;


/**
 * Created by hp on 18-Jul-17.
 */

public class Directionjson implements Serializable{
    double end, end2;
    double arr1[],arr2[];
    public dismodel  Dparse(String response) {
        try {
            JSONObject o1 = new JSONObject(response);
            JSONArray a1 = o1.getJSONArray("routes");
            JSONObject o2 = a1.getJSONObject(0);
            JSONArray a2 = o2.getJSONArray("legs");
            JSONObject o3 = a2.getJSONObject(0);
            JSONObject o7 = o3.getJSONObject("distance");
            JSONObject o8 = o3.getJSONObject("duration");
            String distance=o7.getString("text");
            String duration=o8.getString("value");
            double dist = Double.parseDouble(o7.getString("value"));
            double dura = Double.parseDouble(o8.getString("value"));
            dismodel ob = new dismodel();
            ob.setDuration(dura);
            ob.setDistance(dist);
            ob.setDistvalue(distance);
            ob.setDurationvalue(duration);
            return ob;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String disparse(String response) {
        try {
            JSONObject o1 = new JSONObject(response);
            JSONArray a1 = o1.getJSONArray("routes");
            JSONObject o2 = a1.getJSONObject(0);
            JSONArray a2 = o2.getJSONArray("legs");
            JSONObject o3 = a2.getJSONObject(0);
           JSONArray a3 = o3.getJSONArray("steps");
            JSONObject o4 = o3.getJSONObject("end_location");
            end = o4.getDouble("lat");
            end2 = o4.getDouble("lng");
            arr1 = new double[a3.length() + 1];
            arr2 = new double[a3.length() + 1];

            int count = 0;


            while (count < a3.length()) {
                JSONObject g5 = a3.getJSONObject(count);

                JSONObject g6 = g5.getJSONObject("start_location");
                arr1[count] = g6.getDouble("lat");

                arr2[count] = g6.getDouble("lng");
                count++;
            }
            arr1[count] = end;
            arr2[count] = end2;
            String s2 = "";


            for (int i = 0; i < arr1.length; i++) {
                if (i < arr1.length - 1)
                    s2 += arr1[i] + "," + arr2[i] + "|";
                else {
                    s2 += arr1[i] + "," + arr2[i];
                }

            }
            return s2;
        } catch (
                JSONException e)

        {
            e.printStackTrace();
            Log.e("Djson", "catch");
            return null;
        }
    }

}

