package com.example.boardgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

//Einstiegsseite
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnToCreate, btnToSearch, btnToView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnToCreate = findViewById(R.id.btnToCreate);
        btnToSearch = findViewById(R.id.btnToSearch);
        btnToView = findViewById(R.id.btnView);

        btnToCreate.setOnClickListener(this);
        btnToSearch.setOnClickListener(this);
        btnToView.setOnClickListener(this);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnToCreate:
                Intent intent = new Intent(this, CreateActivity.class);
                startActivity(intent);
                break;
            case R.id.btnToSearch:
                intent = new Intent(this, SearchGameActivity.class);
                startActivity(intent);
                break;
            case R.id.btnView:
                intent = new Intent(this, GameViewActivity.class);
                startActivity(intent);
                break;
        }
    }

}
