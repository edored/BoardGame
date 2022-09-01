package com.example.boardgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logic.DBHandler;

//Einstiegsseite
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnToCreate, btnToSearch, btnToView, btnCreateData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnToCreate = findViewById(R.id.btnToCreate);
        btnToSearch = findViewById(R.id.btnToSearch);
        btnToView = findViewById(R.id.btnView);
        btnCreateData = findViewById(R.id.btnCreateData);

        btnToCreate.setOnClickListener(this);
        btnToSearch.setOnClickListener(this);
        btnToView.setOnClickListener(this);
        btnCreateData.setOnClickListener(this);

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
            case R.id.btnCreateData:
                DBHandler databaseAdapter = new DBHandler(this);
                databaseAdapter.clear();
                databaseAdapter.createMyGames();
                Toast.makeText(MainActivity.this, "Testdaten angelegt.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
