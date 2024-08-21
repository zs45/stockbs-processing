package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException, JSONException {
//TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
// to see how IntelliJ IDEA suggests fixing it.
        URL url = new URL("http://127.0.0.1:5000/stockData");
//        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.connect();
//
//        int code = connection.getResponseCode();


        JSONObject j = getJson(url).getJSONObject("Time Series (Daily)");

        List<Integer> l1 = new java.util.ArrayList<>();

        Iterator keys = j.keys();
        while (keys.hasNext()) {
            Object key = keys.next();
            JSONObject value = j.getJSONObject((String) key);
            l1.add( value.getInt("2. high"));
        }

        System.out.println(l1.stream().sorted().map(i -> i));


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