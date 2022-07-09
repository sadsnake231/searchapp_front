package com.example.abc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.concurrent.ThreadLocalRandom;

//активити, создающее поиск
public class MakeASearchActivity extends AppCompatActivity implements OnClickListener {

    Button buttoncreate;
    String searchName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_asearch);
        setTitle("Search creation");
        buttoncreate = (Button) findViewById(R.id.entersearchnamebutton);
        buttoncreate.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final EditText input = findViewById(R.id.editText);
        searchName = input.getText().toString();
        //передаем карте имя поиска, потом сюда нужно будет прикрутить передачу серверу
        MapsActivity.searchName = this.searchName;
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}