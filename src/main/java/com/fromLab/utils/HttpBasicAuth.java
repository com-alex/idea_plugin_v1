package com.fromLab.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.fromLab.OpenprojectURL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.codec.binary.Base64;


public class HttpBasicAuth {
    public JsonObject getJson(String url,String apikey){
        try {
            URL url1=new URL(url);
            Base64 b = new Base64();
            String key="apikey:"+apikey;
            String encoding = b.encodeAsString(key.getBytes());
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in   =
                    new BufferedReader (new InputStreamReader(content));
            String line=in.readLine();
            JsonObject jsonObject= (JsonObject) new JsonParser().parse(line);
            System.out.println(jsonObject);
            return jsonObject;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String encoding(String apikey){
        Base64 b = new Base64();
        String key="apikey:"+apikey;
        String encoding = b.encodeAsString(key.getBytes());
        return encoding;
    }

    public static void main(String[] args) {
        HttpBasicAuth httpBasicAuth=new HttpBasicAuth();
        httpBasicAuth.encoding(new String("apikey:53e1d54ae78a000b5eecf4c95235b03d6099e394c3e03b2c28ba48eb7ef2f1b6"));
//        try {
//            URL url = new URL ("https://plugin.openproject.com//api/v3/projects");
//
//            Base64 b = new Base64();
//            String encoding = b.encodeAsString(new String("apikey:53e1d54ae78a000b5eecf4c95235b03d6099e394c3e03b2c28ba48eb7ef2f1b6").getBytes());
//
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("GET");
//            connection.setDoOutput(true);
//            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
//            InputStream content = (InputStream)connection.getInputStream();
//            BufferedReader in   =
//                    new BufferedReader (new InputStreamReader(content));
//            String line;
//            while ((line = in.readLine()) != null) {
//                System.out.println(line);
//            }
//        }
//        catch(Exception e) {
//            e.printStackTrace();
//        }
    }
}
