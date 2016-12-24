package com.harnk.jsontestapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.harnk.jsontestapp.DownloadData.download_complete;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements download_complete {

    public ListView list;
    public ArrayList<Countries> countries = new ArrayList<Countries>();
    public ListAdapter adapter;


    // this project is explained here: http://www.kaleidosblog.com/android-listview-load-data-from-json

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = (ListView) findViewById(R.id.list);
        adapter = new ListAdapter(this);
        list.setAdapter(adapter);

        DownloadData download_data = new DownloadData((download_complete) this);
//        download_data.download_data_from_link("http://www.kaleidosblog.com/tutorial/tutorial.json");
        download_data.download_data_from_link("http://www.harnk.com/stellamaris/ships.json");

    }



    public void get_data(String data)
    {

        try {
            JSONArray dataArray=new JSONArray(data);

            // Saving
//            FileOutputStream fos = openFileOutput("ships.json", Context.MODE_PRIVATE);
//            ObjectOutputStream os = new ObjectOutputStream(fos);
//            os.writeObject(data);
//            os.close();
//            fos.close();

            // Loading
            FileInputStream fis = openFileInput("ships.json");
            ObjectInputStream is = new ObjectInputStream(fis);
            String jsonString = (String) is.readObject();
            JSONArray simpleClass = new JSONArray(jsonString);
            is.close();
            fis.close();


//            for (int i = 0 ; i < dataArray.length() ; i++)
//            {
//                JSONObject obj=new JSONObject(dataArray.get(i).toString());

            for (int i = 0 ; i < simpleClass.length() ; i++) {
                    JSONObject obj=new JSONObject(simpleClass.get(i).toString());

                Countries add=new Countries();
//                add.name = obj.getString("country");
//                add.code = obj.getString("code");
                add.name = obj.getString("departure");
                add.code = obj.getString("name");

                countries.add(add);

            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}

