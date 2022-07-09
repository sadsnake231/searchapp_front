package com.example.abc;

import  androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
// активити с двумя кнопками: создать поиск и присоединиться
public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button buttonmake, buttonjoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("SearchApp");

        buttonmake = (Button) findViewById(R.id.buttondraw);
        buttonmake.setOnClickListener(this);

        buttonjoin = (Button) findViewById(R.id.buttonmarker);
        buttonjoin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttondraw:
                Intent intent = new Intent(this, MakeASearchActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonmarker:
                Intent intent1 = new Intent(this, JoinASearchActivity.class);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

        }