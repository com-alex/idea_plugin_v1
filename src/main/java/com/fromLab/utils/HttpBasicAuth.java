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
import net.sf.json.JSONObject;
import org.apache.commons.codec.binary.Base64;

import static com.fromLab.utils.JsonToObjectUtil.JsonToTask;
import static com.fromLab.utils.JsonToObjectUtil.JsonToTaskList;


public class HttpBasicAuth {
    static String apiKey="e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123";
    public JSONObject getJson(String url,String apikey){
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
            JSONObject json = JSONObject.fromObject(jsonObject.toString());
            //System.out.println(json);
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
    public String encoding(String apikey){
        Base64 b = new Base64();
        String key="apikey:"+apikey;
        String encoding = b.encodeAsString(key.getBytes());
        return encoding;
    }

    public static void main(String[] args) {
        HttpBasicAuth httpBasicAuth=new HttpBasicAuth();
        //httpBasicAuth.encoding(new String("apikey:e66517369652fea76049f9c3e1094230ad45fb5b723da5b392d86248c6472123"));
        JSONObject json=httpBasicAuth.getJson("http://projects.plugininide.com/openproject/api/v3/work_packages/14" ,apiKey);
        JsonToTask(json);
        JSONObject json1=httpBasicAuth.getJson("http://projects.plugininide.com/openproject/api/v3/work_packages/?filters=[{ \"type_id\": { \"operator\": \"=\", \"values\": \"1\"}}]" ,apiKey);
        //JsonToTaskList(json1);
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
