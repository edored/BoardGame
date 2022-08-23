package com.example.boardgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logic.BasicInformation;
import com.example.logic.DBHandler;

//Einstiegsseite
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnToCreate, btnToSearch, btnToView;

    private DBHandler dbHandler;
    private Intent intent;

    final String prefNameFirstStart = "firstAppStart";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        btnToCreate = findViewById(R.id.btnToCreate);
        btnToSearch = findViewById(R.id.btnToSearch);
        btnToView = findViewById(R.id.btnView);

        btnToCreate.setOnClickListener(this);
        btnToSearch.setOnClickListener(this);
        btnToView.setOnClickListener(this);

        dbHandler = new DBHandler(MainActivity.this);

    }

//    public boolean firstAppStart(){
//        SharedPreferences preferences = getSharedPreferences(prefNameFirstStart, MODE_PRIVATE);
//        if (preferences.getBoolean(prefNameFirstStart, true)){
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putBoolean(prefNameFirstStart, false);
//            editor.commit();
//            return true;
//        } else {
//            return false;
//        }
//    }

//    public void createDatabase() {
//        BasicInformation basicInformation = new BasicInformation();
//        database = openOrCreateDatabase(basicInformation.getDatabaseName(), MODE_PRIVATE, null);
//        database.execSQL("CREATE TABLE " + basicInformation.getDatabaseTableName() + " (name TEXT, genre TEXT, age TEXT, minNumberOfPlayers TEXT, maxNumberOfPlayers TEXT, duration TEXT)");
//        database.close();
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnToCreate:
                intent = new Intent(this, CreateActivity.class);
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
