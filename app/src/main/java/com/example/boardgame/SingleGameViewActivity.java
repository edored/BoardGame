package com.example.boardgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SingleGameViewActivity extends AppCompatActivity implements View.OnClickListener {

    private Intent intent;

    TextView lblSingleGameName, lblSingleGameAge, lblSingleGameDuration,
            lblSingleGameGenre, lblSingleGameNumberOfPlayers;
    Button btnSingleGameToMain;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_game_view);

        lblSingleGameName = findViewById(R.id.lblSingleGameName);
        lblSingleGameAge = findViewById(R.id.lblSingleGameAge);
        lblSingleGameDuration = findViewById(R.id.lblSingleGameDuration);
        lblSingleGameGenre = findViewById(R.id.lblSingleGameGenre);
        lblSingleGameNumberOfPlayers = findViewById(R.id.lblSingleGameNumberOfPlayers);

        btnSingleGameToMain = findViewById(R.id.btnSingleViewToMain);

        btnSingleGameToMain.setOnClickListener(this);
        loadData();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSingleViewToMain:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void loadData() {
        lblSingleGameName.setText(getIntent().getExtras().get("gameName").toString());
        lblSingleGameAge.setText("ab " + getIntent().getExtras().get("gameAge").toString() + " Jahren");
        lblSingleGameDuration.setText(getIntent().getExtras().get("gameDuration").toString() + " min");
        lblSingleGameGenre.setText(getIntent().getExtras().get("gameGenre").toString());
        lblSingleGameNumberOfPlayers.setText(getIntent().getExtras().get("gameMinNumberOfPlayers").toString() + " - "
                + getIntent().getExtras().get("gameMaxNumberOfPlayers").toString());
    }
}
