package com.example.abc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class JoinASearchActivity extends AppCompatActivity implements View.OnClickListener {
    Button join;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_asearch);
        setTitle("Joining");
        join = (Button) findViewById(R.id.buttonjoin);
        join.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final EditText input = findViewById(R.id.editTextID);
        id = Integer.parseInt(input.getText().toString());
        // TODO должны здесь проверять, есть ли такой айди. если есть, передаем searchactivity область этого айди
        searchActivity.searchID = id;
        Intent intent = new Intent(this, searchActivity.class);
        startActivity(intent);
    }
}