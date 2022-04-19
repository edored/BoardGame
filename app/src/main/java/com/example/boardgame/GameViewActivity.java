package com.example.boardgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.logic.BasicInformation;

import java.util.ArrayList;
import java.util.List;

//Anzeige der vorhandenen Spiele
public class GameViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;
    private SQLiteDatabase database;

    TextView lblViewName, lblViewAge, lblViewNumberOfPlayers, lblViewDuration, lblViewGenre;
    Button btnViewToMain, btnViewToCreate, btnViewToSearch, btnViewGames, btnSingleView;
    ListView lvShowGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        lblViewName = findViewById(R.id.lblViewName);
        lblViewAge = findViewById(R.id.lblViewAge);
        lblViewNumberOfPlayers = findViewById(R.id.lblViewNumberOfPlayers);
        lblViewDuration = findViewById(R.id.lblViewDuration);
        lblViewGenre = findViewById(R.id.lblViewGenre);

        lvShowGames = findViewById(R.id.lvShowGames);

        btnViewToMain = findViewById(R.id.btnViewToMain);
        btnViewToCreate = findViewById(R.id.btnViewToCreate);
        btnViewToSearch = findViewById(R.id.btnViewToSearch);
        btnViewGames = findViewById(R.id.btnSearchGames);
        btnSingleView = findViewById(R.id.btnSingleView);

        btnViewToMain.setOnClickListener(this);
        btnViewToCreate.setOnClickListener(this);
        btnViewToSearch.setOnClickListener(this);
        btnViewGames.setOnClickListener(this);
        btnSingleView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSearchGames:
               //Logik der Anzeigefunktion
                List<BasicInformation> games = viewGames();
                List<String> gameNames = new ArrayList<>();
                List<String> gameAges = new ArrayList<>();
                List<String> gameDurations = new ArrayList<>();
                List<String> gameGenres = new ArrayList<>();
                List<String> gameNumberOfPlayers = new ArrayList<>();
                for(int i=0; i < games.size(); i++) {
                    gameNames.add(games.get(i).getName());
                    gameAges.add(games.get(i).getAge());
                    gameDurations.add(games.get(i).getDuration());
                    gameGenres.add(games.get(i).getGenre());
                    gameNumberOfPlayers.add(games.get(i).getMinNumberOfPlayers());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        gameNames );

                lvShowGames.setAdapter(arrayAdapter);

                break;
            case R.id.btnViewToMain:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnViewToCreate:
                intent = new Intent(this, CreateActivity.class);
                startActivity(intent);
                break;
            case R.id.btnViewToSearch:
                intent = new Intent(this, SearchGameActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSingleView:
                intent = new Intent(this, SingleGameViewActivity.class);
                sendGameToActivity(intent, generateFakeGame());
                startActivity(intent);
                break;
        }
    }

    @SuppressLint("Range")
    public List<BasicInformation> viewGames() {
        List<BasicInformation> games = new ArrayList<>();
        database = openOrCreateDatabase("boardgames.db", MODE_PRIVATE, null);
        //database.execSQL("SELECT * FROM boardgames");

        Cursor res = database.rawQuery( "select * from boardgames", null );
        res.moveToFirst();
        while(!res.isAfterLast()) {
            BasicInformation basicInformation = new BasicInformation();
            basicInformation.setName(res.getString(res.getColumnIndex("name")));
            basicInformation.setAge(res.getString(res.getColumnIndex("age")));
            basicInformation.setGenre(res.getString(res.getColumnIndex("genre")));
            basicInformation.setMinNumberOfPlayers(res.getString(res.getColumnIndex("numberOfPlayers")));
            basicInformation.setDuration(res.getString(res.getColumnIndex("duration")));
            games.add(basicInformation);

            res.moveToNext();
        }

        database.close();
        return games;
    }

    public Intent sendGameToActivity(Intent intent, BasicInformation game) {
        intent.putExtra("gameName", game.getName());
        intent.putExtra("gameAge", game.getAge());
        intent.putExtra("gameDuration", game.getDuration());
        intent.putExtra("gameMinNumberOfPlayers", game.getMinNumberOfPlayers());
        intent.putExtra("gameMaxNumberOfPlayers", game.getMaxNumberOfPlayers());
        intent.putExtra("gameGenre", game.getGenre());
        return intent;
    }

    public BasicInformation generateFakeGame() {
        BasicInformation game = new BasicInformation();
        game.setName("Testpsiel");
        game.setGenre("Testgenre");
        game.setDuration("99");
        game.setMinNumberOfPlayers("2");
        game.setMaxNumberOfPlayers("8");
        game.setAge("12");
        return game;
    }
}