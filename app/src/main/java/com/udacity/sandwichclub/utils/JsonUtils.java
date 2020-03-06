package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichJSON = new JSONObject(json);
            JSONObject name = new JSONObject(sandwichJSON.getJSONObject("name").toString());
            sandwich.setMainName(name.getString("mainName"));
            ArrayList<String> alsoKnownAs = parseJSONArray(name.getJSONArray("alsoKnownAs"));
            ArrayList<String> ingredients = parseJSONArray(sandwichJSON.getJSONArray("ingredients"));
            sandwich.setIngredients(ingredients);
            sandwich.setAlsoKnownAs(alsoKnownAs);
            sandwich.setImage(sandwichJSON.getString("image"));
            sandwich.setDescription(sandwichJSON.getString("description"));
            sandwich.setPlaceOfOrigin(sandwichJSON.getString("placeOfOrigin"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return sandwich;
    }

    private static ArrayList<String> parseJSONArray(JSONArray jsonArray){
        ArrayList<String> jsonArrayList = new ArrayList<>();
        if(jsonArray != null){
            for(int i = 0; i < jsonArray.length(); i ++){
                try {
                    jsonArrayList.add(jsonArray.get(i).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonArrayList;
    }
}
