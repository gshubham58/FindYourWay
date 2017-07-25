package com.shubham.findyourway;

import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hp on 17-Jul-17.
 */

public class nameJsonParse {

    public static ArrayList<model> parse(String response)  {
        JSONObject o1= null;

        try {
            ArrayList<model> arr=new ArrayList<>();
            o1 = new JSONObject(response);
            JSONArray a1=o1.getJSONArray("results");
            int i=0;
            while(i<a1.length()){
            JSONObject o2=a1.getJSONObject(i);
            JSONObject o3=o2.getJSONObject("geometry");
            JSONObject o4=o3.getJSONObject("location");
            String la=o4.getString("lat");
            String ln=o4.getString("lng");
            model obj=new model();
            obj.setLatit(la);
            obj.setLongit(ln);
            String naam=o2.getString("name");
            obj.setName(naam);
                i++;
                arr.add(obj);
            }
            return arr;
        } catch (JSONException e) {


            e.printStackTrace();
            Log.e("json","catch");
            return null;
        }



    }
}
