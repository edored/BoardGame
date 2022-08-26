package com.example.boardgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logic.BasicInformation;
import com.example.logic.DBHandler;

//Anzeige der vorhandenen Spiele
public class GameViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;
    DBHandler databaseAdapter;

    Button btnViewToMain, btnViewToCreate, btnViewToSearch;
    ListView lvShowGames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_view);

        lvShowGames = findViewById(R.id.lvShowGames);

        btnViewToMain = findViewById(R.id.btnViewToMain);
        btnViewToCreate = findViewById(R.id.btnViewToCreate);
        btnViewToSearch = findViewById(R.id.btnViewToSearch);

        btnViewToMain.setOnClickListener(this);
        btnViewToCreate.setOnClickListener(this);
        btnViewToSearch.setOnClickListener(this);

        databaseAdapter = new DBHandler(this);
        String[] gameNames = databaseAdapter.getGameNames();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.single_item, R.id.textView, gameNames);
        lvShowGames.setAdapter(arrayAdapter);
        lvShowGames.setOnItemClickListener((arg0, view, position, id) -> {
            intent = new Intent(this, SingleGameViewActivity.class);
            sendGameToActivity(intent, databaseAdapter.getGame(id));
            startActivity(intent);
        });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
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
        }
    }

    public void sendGameToActivity(Intent intent, BasicInformation game) {
        intent.putExtra("gameName", game.getName());
        intent.putExtra("gameAge", game.getAge());
        intent.putExtra("gameDuration", game.getDuration());
        intent.putExtra("gameMinNumberOfPlayers", game.getMinNumberOfPlayers());
        intent.putExtra("gameMaxNumberOfPlayers", game.getMaxNumberOfPlayers());
        intent.putExtra("gameGenre", game.getGenre());
    }
}