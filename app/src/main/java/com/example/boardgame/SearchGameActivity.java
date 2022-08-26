package com.example.boardgame;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.logic.DBHandler;

//Suche nach Spielen bzw. Filtern nach bestimmten Eigenschaften
public class SearchGameActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;
    DBHandler databaseAdapter;

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

    @SuppressLint("NonConstantResourceId")
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
    private void searchGames(String minNumberOfPlayers, String maxNumberOfPlayers, String age, String duration, String genre) {
        databaseAdapter = new DBHandler(this);
        String[] gameNames = databaseAdapter.getSearchGameNames(minNumberOfPlayers, maxNumberOfPlayers, age, duration, genre);
        intent = new Intent(this, SearchViewActivity.class);
        sendGameNamesToActivity(intent, gameNames);
        startActivity(intent);
    }

    public void sendGameNamesToActivity(Intent intent, String[] gameNames) {
        intent.putExtra("gameNames", gameNames);
    }
}
