package com.shubham.findyourway;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by hp on 20-Jul-17.
 */

public class LatAddressParser {
    static String parseResult(String response){
        try {
            JSONObject o1=new JSONObject(response);
            JSONArray a1=o1.getJSONArray("results");
            JSONObject o2=a1.getJSONObject(0);
            String add=o2.getString("formatted_address");
            char []aa=add.toCharArray();
            String xx="";
            for(int i=0;i<aa.length;i++){
                if(aa[i]==' '){
                    xx+="+";
                }
                else{
                    xx+=aa[i];
                }
            }
            return xx;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("LatAdd","parsecatch");
            return null;
        }

    }
}
