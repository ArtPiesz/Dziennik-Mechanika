package com.example.dziennikmechanika;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class RowEdition extends AppCompatActivity {


    ArrayList<String> Trow;
    Button btnDodaj;

    EditText editText0;
    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    Spinner  Spinner1;
    Spinner  Spinner2;
    public EditText getEditText5() {
        return editText5;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_row_edition);
      Trow = new ArrayList<>();
      btnDodaj = (Button) findViewById(R.id.Dodaj);

      editText0 = (EditText) findViewById(R.id.silnik);
      editText1 = (EditText) findViewById(R.id.data);
      editText2 = (EditText) findViewById(R.id.znaki);
      editText3 = (EditText) findViewById(R.id.Nr_zad);
      editText4 = (EditText) findViewById(R.id.hangar);
      editText5 = (EditText) findViewById(R.id.opis);
      Spinner1 = (Spinner) findViewById(R.id.spinner1);
      Spinner2 = (Spinner) findViewById(R.id.spinner2);
      btnDodaj.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if(TextUtils.isEmpty(editText0.getText().toString() )||TextUtils.isEmpty(editText1.getText().toString() ) || TextUtils.isEmpty(editText2.getText().toString() )|| TextUtils.isEmpty(editText3.getText().toString() ) || TextUtils.isEmpty(editText4.getText().toString() )|| TextUtils.isEmpty(editText4.getText().toString())){
                  Toast.makeText(RowEdition.this,
                          "Alarm! Wykryto puste pole.",
                          Toast.LENGTH_SHORT).show();
              }
              else {
                  Trow.add(editText0.getText().toString());
                  Trow.add(Spinner2.getSelectedItem().toString());
                  Trow.add(editText1.getText().toString());
                  Trow.add(editText2.getText().toString());
                  Trow.add(editText3.getText().toString());
                  Trow.add(editText4.getText().toString());
                  if(Spinner1.getSelectedItem().toString().equals("NIC"))
                  {
                      Trow.add(" ");
                  }
                  else
                  {
                      Trow.add(Spinner1.getSelectedItem().toString());
                  }

                  Trow.add(editText5.getText().toString());

                  Intent intent = new Intent(RowEdition.this, MainActivity.class);
                  intent.putStringArrayListExtra("wiersz", Trow);
                  setResult(-1, intent);
                  finish();
              }
          }
      });
        editText5.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(RowEdition.this, StringList.class);
              startActivityForResult(intent,222);
          }
      });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 222) {
            if(resultCode == -1) {
                if(editText5.getText().toString().matches(""))
                    editText5.setText(data.getCharSequenceExtra("String opis"));
                else
                    editText5.setText(editText5.getText().toString() + "@" +data.getCharSequenceExtra("String opis"));

            }
        }
    }
}