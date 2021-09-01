package com.example.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText User_field;
    private Button Main_btn;
    private TextView ResultTemp;
    private TextView ResultWeather;
    private TextView ResultPhoto;
    private Button Login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper;

        User_field = findViewById(R.id.user_field);
        Main_btn = findViewById(R.id.Main_btn);
        ResultTemp = findViewById(R.id.ResultTemp);
        ResultWeather = findViewById(R.id.ResultWeather);
        ResultPhoto = findViewById(R.id.ResultPhoto);
        Login_btn = findViewById(R.id.Login_btn);
        //dbHelper = new DBHelper();


        Main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues  contentValues = new ContentValues();

                if (User_field.getText().toString().trim().equals("")) {
                    Toast.makeText(MainActivity.this, R.string.Your_City, Toast.LENGTH_SHORT).show();
                } else {

                    String city = User_field.getText().toString().trim();
                    String key = "dd9de74e723da39f1b227ccb81027dcf";
                    String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + key + "&units=metric&lang=ru";

                    new GetURLDate().execute(url);
                }

            }
        });

        Login_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                        startActivity(intent);

                    }
                }
        );
    }



    private class GetURLDate extends AsyncTask<String, String, String>{


        protected void onPreExecute () {

            super.onPreExecute();
            ResultTemp.setText(R.string.Wait);

        }

        @Override
        protected String doInBackground(String[] strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            if(strings != null) {

                try {
                    URL url = new URL(strings[0]);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream stream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer SB = new StringBuffer();
                    String line = "";


                    while ((line = reader.readLine()) != null) {
                        SB.append(line).append("\n");


                        return SB.toString();
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }

                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



            }
            else {
                Toast.makeText(MainActivity.this, R.string.Error, Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            if (result != null) {
                try {
                    JSONObject object = new JSONObject(result);
                    ResultTemp.setText("Tемпература: " + object.getJSONObject("main").getDouble("temp") + "\n" + "Ощущается как:" + object.getJSONObject("main").getDouble("feels_like"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject object = new JSONObject(result);

                    ResultWeather.setText("Погода: " + object.getJSONArray("weather").getJSONObject(0).optString("description"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject object = new JSONObject(result);
                    String Photo = null;
                    Photo = object.getJSONArray("weather").getJSONObject(0).getString("description");
                    String con = "ясно";
                    if (Photo.equals("ясно")) {
                        ;
                    } else {
                        ResultPhoto.setText("хуита");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else {
                Toast.makeText(MainActivity.this, R.string.Error, Toast.LENGTH_SHORT).show();
            }




        }



    }

}