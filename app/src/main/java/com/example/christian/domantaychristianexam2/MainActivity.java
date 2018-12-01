package com.example.christian.domantaychristianexam2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
EditText fname,lname,exam1,exam2;
TextView avg;
Button display;
    public String[] away;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fname= findViewById(R.id.fname);
        avg= findViewById(R.id.avg);
        lname = findViewById(R.id.lname);
        exam1= findViewById(R.id.exam1);
        exam2= findViewById(R.id.exam2);
        display = (Button) findViewById(R.id.display);
        display.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                try {
                    saveInternal();
                    loadInternal();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        loadInternal();

    }


    public void saveData(View v){

        String fn = fname.getText().toString();
        String ln = lname.getText().toString();

        SharedPreferences sp = getSharedPreferences("Data1", MODE_PRIVATE);
        SharedPreferences.Editor writer = sp.edit();
        writer.putString("fn",fn);
        writer.putString("ln",ln);
        writer.commit();
        Toast.makeText(this,"Data Saved...",Toast.LENGTH_LONG).show();


    }

    public void saveInternal() throws IOException {
         int ex1= Integer.parseInt(exam1.getText().toString());
        int ex2= Integer.parseInt(exam2.getText().toString());

        int avg = (ex1+ex2)/2;
        String fn = fname.getText().toString();
        String ln = "%"+ lname.getText().toString();
        String ave = "%"+ avg;
        FileOutputStream fos = null;
        try {
            fos = openFileOutput("Data2.txt",MODE_PRIVATE);
            fos.write(fn.getBytes());
            fos.write(ln.getBytes());
            fos.write(ave.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Toast.makeText(this, "Saved in "+getFilesDir(),Toast.LENGTH_LONG).show();
    }

    public void loadData(View v){
        SharedPreferences sp = getSharedPreferences("Data1",MODE_PRIVATE);
        String fn = sp.getString("fname",null);
        String ln = sp.getString("lname",null);
        fname.setText(fn);
        lname.setText(ln);
    }

    public void loadInternal(){
        try {
            FileInputStream fis = openFileInput("Data2.txt");
            StringBuffer buffer = new StringBuffer();
            int read=0;

            while ((read = fis.read())!=-1){
                buffer.append((char)read);

            }
            String s  = buffer.toString();
             away = s.split("%");
            int count= away.length;
            if(buffer!=null){
                fname.setText(away[count-3]);
                lname.setText(away[count-2]);
                avg.setText(away[count-1]);
            }

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }


    }
}
