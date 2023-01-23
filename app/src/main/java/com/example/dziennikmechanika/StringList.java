package com.example.dziennikmechanika;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Field;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



public class StringList extends AppCompatActivity {

    ListView listView;
    EditText theFilter;
    Field[] fields;
    String[] allStringsNames;
    int[] allStringsIds;
    String[] allStrings;
    ArrayAdapter<String> contactAdapter;
    int[] Buffer;
    String Opis;
    //String zad;
    //RowEdition rowEdition;
    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string_list);

        listView = (ListView) findViewById(R.id.lv);
        theFilter = (EditText) findViewById(R.id.search);
        fields= R.string.class.getDeclaredFields();
        allStringsNames = new String[fields.length];
        allStringsIds = new int[fields.length];
        Buffer = new int[fields.length];
        allStrings = new String[fields.length];

        for (int  i =0; i < fields.length; i++) {
            allStringsNames[i] = fields[i].getName();
            allStringsIds[i] = getStringIdentifier(this,allStringsNames[i]);
            allStrings[i] = getString(allStringsIds[i]);
        }

        contactAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,allStrings);
        listView.setAdapter(contactAdapter);
        theFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                (StringList.this).contactAdapter.getFilter().filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    Opis = (listView.getItemAtPosition(i).toString());

                Intent intent = new Intent(StringList.this, MainActivity.class);

                intent.putExtra("String opis", Opis);
                setResult(-1, intent);
                finish();
            }
        });
    }
    public static int getStringIdentifier(Context context, String name) {
        return context.getResources().getIdentifier(name, "string", context.getPackageName());

    }

}