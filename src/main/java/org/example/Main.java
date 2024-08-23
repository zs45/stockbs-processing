package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, JSONException, ParseException {
//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
// to see how IntelliJ IDEA suggests fixing it.
        URL url = new URL("http://127.0.0.1:5000/stockData");
//        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.connect();
//
//        int code = connection.getResponseCode();


        JSONObject j = getJson(url).getJSONObject("Time Series (Daily)");

        List<Float> highs = new java.util.ArrayList<>();

        TreeMap<Date, Float> m = new TreeMap<>();

        Iterator keys = j.keys();
        while (keys.hasNext()) {
            Object key = keys.next();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
            Date date = dateFormat.parse(key.toString());
            JSONObject value = j.getJSONObject((String) key);
            highs.add(Float.parseFloat(value.getString("2. high")));
            m.put(date, Float.parseFloat(value.getString("2. high")));

        }

        Collections.max(m.entrySet(), Map.Entry.comparingByValue()).getKey();

        Float hi = Float.MIN_VALUE;
        Float lo = Float.MAX_VALUE;

        for(Float val : m.values() ){
            hi = Float.max(val, hi);
            lo = Float.min(val, lo);
        }

        System.out.println(lo);


    }

    public static String stream(URL url) throws IOException {
        try (InputStream input = url.openStream()) {
            InputStreamReader isr = new InputStreamReader(input);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                json.append((char) c);
            }
            return json.toString();
        }
    }

    public static JSONObject getJson(URL url) throws IOException, JSONException {
        String json = IOUtils.toString(url, Charset.forName("UTF-8"));
        return new JSONObject(json);
    }
}