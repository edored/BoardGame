package com.example.boardgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logic.BasicInformation;

import java.util.ArrayList;
import java.util.List;

//Suche nach Spielen bzw. Filtern nach bestimmten Eigenschaften
public class SearchGameActivity extends AppCompatActivity implements View.OnClickListener{

    private Intent intent;
    private SQLiteDatabase database;

    Spinner spinGenre;
    EditText txtSearchAge, txtSearchMinNumberOfPlayers, txtSearchMaxNumberOfPlayers, txtSearchDuration;
    Button btnSearchGames, btnSearchToMain, btnSearchToCreate, btnSearchToView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_game);

        spinGenre = findViewById(R.id.spinGenre);
        txtSearchAge = findViewById(R.id.txtSearchAge);
        txtSearchMinNumberOfPlayers = findViewById(R.id.txtSearchMinNumberOfPlayers);
        txtSearchMaxNumberOfPlayers = findViewById(R.id.txtSearchMaxNumberOfPlayers);
        txtSearchDuration = findViewById(R.id.txtSearchDuration);

        btnSearchGames = findViewById(R.id.btnSearchGames);
        btnSearchToCreate = findViewById(R.id.btnSearchToCreate);
        btnSearchToMain = findViewById(R.id.btnSearchToMain);
        btnSearchToView = findViewById(R.id.btnSearchToView);

        btnSearchGames.setOnClickListener(this);
        btnSearchToCreate.setOnClickListener(this);
        btnSearchToMain.setOnClickListener(this);
        btnSearchToView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSearchGames:
                searchGames(txtSearchMinNumberOfPlayers.getText().toString(),
                        txtSearchMaxNumberOfPlayers.getText().toString(),
                        txtSearchAge.getText().toString(),
                        txtSearchDuration.getText().toString(),
                        spinGenre.getSelectedItem().toString());
                break;
            case R.id.btnSearchToMain:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSearchToCreate:
                intent = new Intent(this, CreateActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSearchToView:
                intent = new Intent(this, GameViewActivity.class);
                startActivity(intent);
                break;
        }
    }

    @SuppressLint("Range")
    private List<BasicInformation> searchGames(String minNumberOfPlayers, String maxNumberOfPlayers, String age, String duration, String genre) {
        List<BasicInformation> games = new ArrayList<>();

        database = openOrCreateDatabase("boardgames.db", MODE_PRIVATE, null);
        //database.execSQL("SELECT * FROM boardgames");


        Cursor res = database.rawQuery( "select * from boardgames WHERE genre = ? OR age >= ? OR duration = ? OR (minNumberOfPlayers >= ? AND maxNumberOfPlayers <= ?)", new String [] {genre, age, duration, minNumberOfPlayers, maxNumberOfPlayers} );
        res.moveToFirst();
        while(!res.isAfterLast()) {
            BasicInformation basicInformation = new BasicInformation();
            basicInformation.setName(res.getString(res.getColumnIndex("name")));
            basicInformation.setAge(res.getString(res.getColumnIndex("age")));
            basicInformation.setGenre(res.getString(res.getColumnIndex("genre")));
            basicInformation.setMinNumberOfPlayers(res.getString(res.getColumnIndex("minNumberOfPlayers")));
            basicInformation.setMaxNumberOfPlayers(res.getString(res.getColumnIndex("maxNumberOfPlayers")));
            basicInformation.setDuration(res.getString(res.getColumnIndex("duration")));
            games.add(basicInformation);

            res.moveToNext();
        }

        database.close();


        return games;
    }

    private boolean checkConvertIntValue(String unconvertedValue) {
        try {
            Integer.parseInt(unconvertedValue);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
