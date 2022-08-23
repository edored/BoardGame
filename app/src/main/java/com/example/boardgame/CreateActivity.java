package com.example.boardgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.logic.BasicInformation;
import com.example.logic.DBHandler;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//Hinzufügen eines neuen Spiels
public class CreateActivity extends AppCompatActivity implements View.OnClickListener  {

    Button btnCreateGame;
    EditText txtName, txtAge, txtMinNumberOfPlayers, txtMaxNumberOfPlayers, txtDuration;
    Spinner spinGenre;

    private DBHandler dbHandler;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create);

        txtName = findViewById(R.id.txtName);
        txtAge = findViewById(R.id.txtAge);
        txtDuration = findViewById(R.id.txtDuration);
        txtMinNumberOfPlayers = findViewById(R.id.txtMinNumberOfPlayers);
        txtMaxNumberOfPlayers = findViewById(R.id.txtMaxNumberOfPlayers);
        spinGenre = findViewById(R.id.spinGenre);
        btnCreateGame = findViewById(R.id.btnCreateGame);

        btnCreateGame.setOnClickListener(this);

        dbHandler = new DBHandler(CreateActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnCreateGame:
                //TODO: Validierung ob Felder gefüllt
                BasicInformation game = new BasicInformation(
                        txtName.getText().toString(),
                        txtAge.getText().toString(),
                        txtMinNumberOfPlayers.getText().toString(),
                        txtMaxNumberOfPlayers.getText().toString(),
                        txtDuration.getText().toString(),
                        spinGenre.getSelectedItem().toString());
                fillDatabase(game);
                //TODO: Fenster einblenden das das Spiel hinzugefügt wurde, danach fragen ob weitere oder zum Home
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.btnToMain:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void fillDatabase(BasicInformation game) {
        dbHandler.addNewGame(game);
        Toast.makeText(CreateActivity.this, "Spiel wurde angelegt.", Toast.LENGTH_SHORT).show();
    }
}