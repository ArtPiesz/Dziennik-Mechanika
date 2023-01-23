package com.example.dziennikmechanika;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itextpdf.text.pdf.BaseFont;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;

import static android.os.Build.VERSION.SDK_INT;

public class MainActivity extends AppCompatActivity {


    ArrayList<String> stringArray;
    ArrayList<ArrayList<String>> la ;
    TableLayout stk ;
    Field[] fields;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    String json;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!checkPermission()){
            requestPermission();
        }

                stringArray = new ArrayList<String>();
                sp = getSharedPreferences("TableData",Context.MODE_PRIVATE);
                editor= sp.edit();
                stk = (TableLayout) findViewById(R.id.table_main);
                fields= R.string.class.getFields();

                la = new ArrayList<ArrayList<String>>();
                String json = sp.getString("TableData",null);


                if(json!= null) {
                    la = gson.fromJson(json,la.getClass() );

                }
                Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
                setSupportActionBar(toolbar);

                addTable(la);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(MainActivity.this, RowEdition.class);
                startActivityForResult(intent,111);
                return true;

            case R.id.action_delete:
                if(!(la.size()==0)) {
                    la.remove(la.size() - 1);
                    //editor.clear().apply();
                    json = gson.toJson(la);
                    editor.putString("TableData", json).apply();
                    stk.removeViews(0, Math.max(0, stk.getChildCount()));
                    addTable(la);
                    Toast toast3 = Toast.makeText(MainActivity.this, "usunięto ostatni wiersz", Toast.LENGTH_SHORT);
                    toast3.show();
                }
                else
                {
                    Toast toast4 = Toast.makeText(MainActivity.this, "Nie ma co usunąć", Toast.LENGTH_SHORT);
                    toast4.show();
                }
                return true;

            case R.id.action_export:
                if(!(la.size()==0)) {
                    GeneratePDF();
                    editor.clear().apply();
                }
                else
                {
                    Toast toast4 = Toast.makeText(MainActivity.this, "Nie ma co sPeDeeFić", Toast.LENGTH_SHORT);
                    toast4.show();
                }
               // SharedPreferences.Editor.remove();
                return true;


            case R.id.action_save:
                json = gson.toJson(la);
                editor.putString("TableData", json).apply();
                Toast toast2 = Toast.makeText(MainActivity.this, "saved", Toast.LENGTH_SHORT);
                toast2.show();
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    public void onRestart() {
        super.onRestart();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111) {
            if(resultCode == -1) {
                stringArray = data.getStringArrayListExtra("wiersz");
                    la.add(stringArray);
                    addToTable(stringArray);

            }
        }
        if (requestCode == 2296) {
            if (SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {

                } else {
                    Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }



public void addTable(ArrayList<ArrayList<String>> arrayList) {
    TableRow tbrow0 = new TableRow(this);

    //headers
    TextView tv0 = new TextView(this);

    tv0.setText("Typ SP" +"/"+"Silnik");
    tv0.setTextColor(Color.BLACK);

    tbrow0.addView(tv0);

    //TextView tv1 = new TextView(this);

   // tv1.setText(" Silnik ");
    //tv1.setTextColor(Color.BLACK);

   // tbrow0.addView(tv1);

    TextView tv2 = new TextView(this);

    tv2.setText(R.string.header1);
    tv2.setTextColor(Color.BLACK);

    tbrow0.addView(tv2);

    TextView tv3 = new TextView(this);

    tv3.setText(R.string.header2);
    tv3.setTextColor(Color.BLACK);
    tbrow0.addView(tv3);

    TextView tv4 = new TextView(this);

    tv4.setText(R.string.header5);
    tv4.setTextColor(Color.BLACK);
    tbrow0.addView(tv4);

    TextView tv5 = new TextView(this);

    tv5.setText(R.string.header3);
    tv5.setTextColor(Color.BLACK);
    tbrow0.addView(tv5);

    TextView tv6 = new TextView(this);

    tv6.setText(" Czynność ");
    tv6.setTextColor(Color.BLACK);
    tbrow0.addView(tv6);

    TextView tv7 = new TextView(this);

    tv7.setText(R.string.header4);
    tv7.setTextColor(Color.BLACK);
    tbrow0.addView(tv7);

    stk.addView(tbrow0);

    //cache rows
        if(arrayList!= null) {
            ArrayList<String> tmp2 = new ArrayList<String>();
            for (int i =0;i<arrayList.size();i++) {

                TableRow tbrow = new TableRow(this);
                tmp2 = arrayList.get(i);
                TextView t0v = new TextView(this);
                TextView t1v = new TextView(this);
                TextView t2v = new TextView(this);
                TextView t3v = new TextView(this);
                TextView t4v = new TextView(this);
                TextView t5v = new TextView(this);
                TextView t6v = new TextView(this);
                TextView t7v = new TextView(this);
               // t0v.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                String SP = tmp2.get(0);
                String Silnik = tmp2.get(1);
                t0v.setText(SP+"/"+Silnik);
                t0v.setTextColor(Color.BLACK);
                t0v.setBackgroundResource(R.color.grey);
                tbrow.addView(t0v);

               // t1v.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
               // t1v.setText(tmp2.get(1));
               // t1v.setTextColor(Color.BLACK);
               // t1v.setBackgroundResource(R.color.grey);
               // tbrow.addView(t1v);

               // t2v.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                t2v.setText(tmp2.get(2));
                t2v.setTextColor(Color.BLACK);
                t2v.setBackgroundResource(R.color.grey);
                tbrow.addView(t2v);

              //  t3v.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                t3v.setText(tmp2.get(3));
                t3v.setTextColor(Color.BLACK);
                t3v.setBackgroundResource(R.color.grey);
                tbrow.addView(t3v);

              //  t4v.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                t4v.setText(tmp2.get(4));
                t4v.setTextColor(Color.BLACK);
                t4v.setBackgroundResource(R.color.grey);
                tbrow.addView(t4v);

               // t5v.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                t5v.setText(tmp2.get(5));
                t5v.setTextColor(Color.BLACK);
                t5v.setBackgroundResource(R.color.grey);
                tbrow.addView(t5v);

              //  t6v.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                t6v.setText(tmp2.get(6));
                t6v.setTextColor(Color.BLACK);
                t6v.setBackgroundResource(R.color.grey);
                tbrow.addView(t6v);

                t7v.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
                t7v.setText(tmp2.get(7));
                t7v.setTextColor(Color.BLACK);
                t7v.setBackgroundResource(R.color.grey);

                tbrow.addView(t7v);
                stk.addView(tbrow);

            }

        }

}





    public void addToTable(ArrayList<String> arrayList){

        TableRow tbrowx = new TableRow(this);

        TextView tv0 =new TextView(this);
        tv0.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        TextView tv1 =new TextView(this);
       // tv1.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        TextView tv2 =new TextView(this);
       // tv2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        TextView tv3 =new TextView(this);
       // tv3.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        TextView tv4 =new TextView(this);
       // tv4.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        TextView tv5 =new TextView(this);
       // tv5.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        TextView tv6 =new TextView(this);
        tv6.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

        TextView tv7 =new TextView(this);
        tv7.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));

        tv0.setText(arrayList.get(0) +"/"+arrayList.get(1));
        tv0.setTextColor(Color.BLACK);
        tv0.setBackgroundResource(R.color.grey);
        tbrowx.addView(tv0);

        /*tv1.setText(arrayList.get(1));
        tv1.setTextColor(Color.BLACK);
        tv1.setBackgroundResource(R.color.grey);
        tbrowx.addView(tv1);*/

        tv2.setText(arrayList.get(2));
        tv2.setTextColor(Color.BLACK);
        tv2.setBackgroundResource(R.color.grey);
        tbrowx.addView(tv2);

        tv3.setText(arrayList.get(3));
        tv3.setTextColor(Color.BLACK);
        tv3.setBackgroundResource(R.color.grey);
        tbrowx.addView(tv3);

        tv4.setText(arrayList.get(4));
        tv4.setTextColor(Color.BLACK);
        tv4.setBackgroundResource(R.color.grey);
        tbrowx.addView(tv4);

        tv5.setText(arrayList.get(5));
        tv5.setTextColor(Color.BLACK);
        tv5.setBackgroundResource(R.color.grey);
        tbrowx.addView(tv5);

        tv6.setText(arrayList.get(6));
        tv6.setTextColor(Color.BLACK);
        tv6.setBackgroundResource(R.color.grey);
        tbrowx.addView(tv6);

        tv7.setText(arrayList.get(7));
        tv7.setTextColor(Color.BLACK);
        tv7.setBackgroundResource(R.color.grey);
        tbrowx.addView(tv7);

        stk.addView(tbrowx);

    }
    private boolean checkPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }
    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                startActivityForResult(intent, 2296);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                startActivityForResult(intent, 2296);
            }
        } else {
            //below android 11
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2296);
        }
    }

    public void GeneratePDF()
    {
        String filename = "Książka";
        String filecontent = "Mechanika";

        Metodos fop = new Metodos();

        if (fop.write(filename, filecontent,la)) {
            Toast.makeText(getApplicationContext(),
                    filename + ".pdf created", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error",
                    Toast.LENGTH_SHORT).show();
        }
    }




}