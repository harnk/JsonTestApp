package com.harnk.jsontestapp;

/**
 * Created by scottnull on 12/22/16.
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class DownloadData implements Runnable  {

    public downloadComplete caller;

    public interface downloadComplete
    {
        public void getData(String data);
    }

    DownloadData(downloadComplete caller) {
        this.caller = caller;
    }

    private String link;
    public void downloadDataFromLink(String link)
    {
        this.link = link;
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {
        threadMsg(download(this.link));
    }

    private void threadMsg(String msg) {

        if (!msg.equals(null) && !msg.equals("")) {
            Message msgObj = handler.obtainMessage();
            Bundle b = new Bundle();
            b.putString("message", msg);
            msgObj.setData(b);
            handler.sendMessage(msgObj);
        }
    }


    private final Handler handler = new Handler() {

        public void handleMessage(Message msg) {

            String Response = msg.getData().getString("message");

            caller.getData(Response);

        }
    };




    public static String download(String url) {
        URL website;
        StringBuilder response = null;
        try {
            website = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) website.openConnection();
            connection.setRequestProperty("charset", "utf-8");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));

            response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null)
                response.append(inputLine);

            in.close();

        } catch (Exception  e) {
            return "";
        }


        return response.toString();
    }


}
