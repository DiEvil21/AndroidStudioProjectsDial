package com.sport.ppfitnessapp;

// Workout.java
import java.io.Serializable;

// Workout.java
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Workout implements Serializable {
    private String name;
    private String date;
    private String description;

    public Workout(String name, String date, String description) {
        this.name = name;
        this.date = date;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public static String toJsonArray(List<Workout> workouts) {
        JSONArray jsonArray = new JSONArray();
        for (Workout workout : workouts) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", workout.getName());
                jsonObject.put("date", workout.getDate());
                jsonObject.put("description", workout.getDescription());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArray.toString();
    }

    public static List<Workout> fromJsonArray(String json) {
        List<Workout> workouts = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                String date = jsonObject.getString("date");
                String description = jsonObject.getString("description");
                workouts.add(new Workout(name, date, description));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return workouts;
    }
}


