package com.example.fhictcompanion;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PersonTask extends AsyncTask<String, Void, String> {
    IPersonContext context;

    public PersonTask(IPersonContext context) {
        super();
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {
        String responseText = "";

        try {
            URL url = new URL("https://api.fhict.nl/people/me");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + strings[0]);
            connection.connect();

            InputStream is = connection.getInputStream();
            Scanner scn = new Scanner(is);

            responseText = scn.useDelimiter("\\Z").next();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return responseText;
    }

    @Override
    protected void onPostExecute(String responseText) {
        super.onPostExecute(responseText);

        // We have an encapsulating object with key value pairs.
        String givenName = "";
        String surName = "";

        try {
            JSONObject jsonResponse = new JSONObject(responseText);
            givenName = jsonResponse.getString("givenName");
            surName = jsonResponse.getString("surName");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Person student = new Person(givenName, surName);
        context.setPersonDetails(student);
    }
}
