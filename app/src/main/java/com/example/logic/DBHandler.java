package com.example.logic;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler  extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "boardgamesdb";
    private static final String TABLE_NAME = "boardgames";
    // When you do change the structure of the database change the version number from 1 to 2
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ROWID = "_id";
    private static final String KEY_NAME="name";
    private static final String KEY_GENRE = "genre";
    private static final String KEY_AGE = "age";
    private static final String KEY_MINNUMBEROFPLAYERS = "minNumberOfPlayers";
    private static final String KEY_MAXNUMBEROFPLAYERS = "maxNumberOfPlayers";
    private static final String KEY_DURATION = "duration";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_NAME + " TEXT,"
                    + KEY_AGE + " INTEGER,"
                    + KEY_GENRE + " TEXT,"
                    + KEY_MINNUMBEROFPLAYERS + " INTEGER,"
                    + KEY_MAXNUMBEROFPLAYERS + " INTEGER,"
                    + KEY_DURATION + " INTEGER)";
            db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addNewGame(BasicInformation boardGame) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, boardGame.getName());
        values.put(KEY_AGE, boardGame.getAge());
        values.put(KEY_GENRE, boardGame.getGenre());
        values.put(KEY_MINNUMBEROFPLAYERS, boardGame.getMinNumberOfPlayers());
        values.put(KEY_MAXNUMBEROFPLAYERS, boardGame.getMaxNumberOfPlayers());
        values.put(KEY_DURATION, boardGame.getDuration());

        db.insert(TABLE_NAME, null, values);

        db.close();
    }

    @SuppressLint("Range")
    public String[] getGameNames() {
        List<String> nameList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        @SuppressLint("Recycle") Cursor res = db.rawQuery( "select * from boardgames", null );
        res.moveToFirst();
        while(!res.isAfterLast()) {
            nameList.add(res.getString(res.getColumnIndex("name")));
            res.moveToNext();
        }

        String[] names = new String[ nameList.size() ];
        nameList.toArray(names);

        return names;
    }

    @SuppressLint("Range")
    public String[] getSearchGameNames(String minNumberOfPlayers, String maxNumberOfPlayers, String age, String duration, String genre) {
        List<String> nameList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        @SuppressLint("Recycle") Cursor res = db.rawQuery( "select * from boardgames WHERE genre = ? OR age >= ? OR duration = ? OR (minNumberOfPlayers >= ? AND maxNumberOfPlayers <= ?)", new String [] {genre, age, duration, minNumberOfPlayers, maxNumberOfPlayers} );
        res.moveToFirst();
        while(!res.isAfterLast()) {
            nameList.add(res.getString(res.getColumnIndex("name")));
            res.moveToNext();
        }

        String[] names = new String[ nameList.size() ];
        nameList.toArray(names);

        return names;
    }

    @SuppressLint("Range")
    public BasicInformation getGame(long position) {
        BasicInformation game = new BasicInformation();
        SQLiteDatabase db = this.getWritableDatabase();

        long id = position + 1;

        @SuppressLint("Recycle") Cursor res = db.rawQuery( "select * from boardgames WHERE _id = ?", new String [] {String.valueOf(id)} );
        res.moveToFirst();
        game.setName(res.getString(res.getColumnIndex("name")));
        game.setAge(Integer.parseInt(res.getString(res.getColumnIndex("age"))));
        game.setDuration(Integer.parseInt(res.getString(res.getColumnIndex("duration"))));
        game.setGenre(res.getString(res.getColumnIndex("genre")));
        game.setMinNumberOfPlayers(Integer.parseInt(res.getString(res.getColumnIndex("minNumberOfPlayers"))));
        game.setMaxNumberOfPlayers(Integer.parseInt(res.getString(res.getColumnIndex("maxNumberOfPlayers"))));

        return game;
    }

    @SuppressLint("Range")
    public BasicInformation getGame(String gameName) {
        BasicInformation game = new BasicInformation();
        SQLiteDatabase db = this.getWritableDatabase();

        @SuppressLint("Recycle") Cursor res = db.rawQuery( "select * from boardgames WHERE name = ?", new String [] {String.valueOf(gameName)} );
        res.moveToFirst();
        game.setName(res.getString(res.getColumnIndex("name")));
        game.setAge(Integer.parseInt(res.getString(res.getColumnIndex("age"))));
        game.setDuration(Integer.parseInt(res.getString(res.getColumnIndex("duration"))));
        game.setGenre(res.getString(res.getColumnIndex("genre")));
        game.setMinNumberOfPlayers(Integer.parseInt(res.getString(res.getColumnIndex("minNumberOfPlayers"))));
        game.setMaxNumberOfPlayers(Integer.parseInt(res.getString(res.getColumnIndex("maxNumberOfPlayers"))));

        return game;
    }
}
