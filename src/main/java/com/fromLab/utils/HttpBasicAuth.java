package com.fromLab.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

import static com.fromLab.utils.JsonToObjectUtil.JsonToTask;


public class HttpBasicAuth {
    static String apiKey = "e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123";

    public JSONObject getJson(String url, String apikey) {
        try {
            URL url1 = new URL(url);
            Base64 b = new Base64();
            String key = "apikey:" + apikey;
            String encoding = b.encodeAsString(key.getBytes());
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", "Basic " + encoding);
            InputStream content = (InputStream) connection.getInputStream();
            BufferedReader in =
                    new BufferedReader(new InputStreamReader(content));
            String line = in.readLine();

            JsonObject jsonObject = (JsonObject) new JsonParser().parse(line);
            JSONObject json = JSONObject.fromObject(jsonObject.toString());
            return json;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String encoding(String apikey) {
        Base64 b = new Base64();
        String key = "apikey:" + apikey;
        String encoding = b.encodeAsString(key.getBytes());
        return encoding;
    }
}
