package com.fromLab.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.commons.codec.binary.Base64;


public class HttpBasicAuth {
    public static void main(String[] args) {

        try {
            URL url = new URL ("https://plugin.openproject.com//api/v3/projects");

            Base64 b = new Base64();
            String encoding = b.encodeAsString(new String("apikey:53e1d54ae78a000b5eecf4c95235b03d6099e394c3e03b2c28ba48eb7ef2f1b6").getBytes());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoOutput(true);
            connection.setRequestProperty  ("Authorization", "Basic " + encoding);
            InputStream content = (InputStream)connection.getInputStream();
            BufferedReader in   =
                    new BufferedReader (new InputStreamReader(content));
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
