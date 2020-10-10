package com.example.viewpager_tab_subtab;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("reached","1");
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager(), this);
        Log.d("reached","2");
        ViewPager viewPager = findViewById(R.id.view_pager);
        Log.d("reached","3");
        viewPager.setAdapter(sectionsPagerAdapter);
        Log.d("reached","4");
        TabLayout tabs = findViewById(R.id.tabs);
        Log.d("reached","5");
        tabs.setupWithViewPager(viewPager);
        Log.d("reached","6");
    }

    public static synchronized JSONObject getJsonObject(String jsonFilePath, Context context) {
        String jsonString = getStringFromJsonObjectPath(jsonFilePath, context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static synchronized boolean saveFileLocally(String fileName, String fileContent, Context context) {
        try {
            String path = context.getExternalFilesDir("").getAbsolutePath();
            File file = new File(path, fileName);
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(fileContent);
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public static synchronized String getStringFromJsonObjectPath(String jsonFilePath, Context context) {
        try {
            String path = context.getExternalFilesDir("").getAbsolutePath();
            File file = new File(path + "/" + jsonFilePath);

            StringBuilder data = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                data.append(line);
                data.append("\n");
            }
            br.close();

            return data.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}