package com.example.w2d1_as7;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private EditText nameET;
    private FileOutputStream out;
    private FileInputStream in;
    private File file;
    private String fileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    //Set up EditText
    private void init() {
        nameET = findViewById(R.id.etName);
        fileName = getString(R.string.fileName);
    }

    public void writeToFile(View view) {
        String inputName = nameET.getText().toString();
        try {
            if (checkExternal()) {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), fileName);
                out = new FileOutputStream(file);
                out.write(inputName.getBytes());
                out.close();
            } else {
                Log.d("externalCheck", "External storage is not available.");
            }
        } catch (Exception e) {
            Log.e("Whoopsie", "Oh no!");
        }
    }

    public void printName(View view) {
        try {
            if (checkExternal()) {
                in = new FileInputStream(file);
                InputStreamReader isr = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(isr);

                String output = br.readLine();
                Log.d("lastNameEntered", output);
            } else {
                Log.d("externalCheck", "External storage is not available.");
            }

        } catch (FileNotFoundException e) {
            Log.e("Whoopsie", "printName: File not found.", e.getCause());
        } catch (java.io.IOException e) {
            Log.e("Uhoh", "printName: IO problem.", e.getCause());
        }
    }

    private boolean checkExternal() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}