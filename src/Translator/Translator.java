package Translator;
import java.io.BufferedReader;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Translator {
    // TODO: If you have your own Premium account credentials, put them down here:



    /**
     * Sends out a WhatsApp message via WhatsMate WA Gateway.
     */
    public static String translate(String text) throws Exception {
        String CLIENT_ID = "FREE_TRIAL_ACCOUNT";
        String CLIENT_SECRET = "PUBLIC_SECRET";
        String ENDPOINT = "http://api.whatsmate.net/v1/translation/translate";
        String fromLang = "en";
        String toLang = "vi";
        String jsonPayload = new StringBuilder()
                .append("{")
                .append("\"fromLang\":\"")
                .append(fromLang)
                .append("\",")
                .append("\"toLang\":\"")
                .append(toLang)
                .append("\",")
                .append("\"text\":\"")
                .append(text)
                .append("\"")
                .append("}")
                .toString();

        URL url = new URL(ENDPOINT);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-WM-CLIENT-ID", CLIENT_ID);
        conn.setRequestProperty("X-WM-CLIENT-SECRET", CLIENT_SECRET);
        conn.setRequestProperty("Content-Type", "application/json");

        OutputStream os = conn.getOutputStream();
        os.write(jsonPayload.getBytes());
        os.flush();
        os.close();

        int statusCode = conn.getResponseCode();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (statusCode == 200) ? conn.getInputStream() : conn.getErrorStream()
        ));
        String output;

        while ((output = br.readLine()) != null) {

            System.out.println(output);
        }
        conn.disconnect();
        return output;
    }

}
