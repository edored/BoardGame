package com.example.logic;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SimpleCursorAdapter;

import com.example.boardgame.R;

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
                    + KEY_AGE + " TEXT,"
                    + KEY_GENRE + " TEXT,"
                    + KEY_MINNUMBEROFPLAYERS + " TEXT,"
                    + KEY_MAXNUMBEROFPLAYERS + " TEXT,"
                    + KEY_DURATION + " TEXT)";
            db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addNewGame(String name, String age, String genre, String minNumberOfPlayers, String maxNumberOfPlayers, String duration) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_NAME, name);
        values.put(KEY_AGE, age);
        values.put(KEY_GENRE, genre);
        values.put(KEY_MINNUMBEROFPLAYERS, minNumberOfPlayers);
        values.put(KEY_MAXNUMBEROFPLAYERS, maxNumberOfPlayers);
        values.put(KEY_DURATION, duration);

        db.insert(TABLE_NAME, null, values);

        db.close();
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

        Cursor res = db.rawQuery( "select * from boardgames", null );
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
    public BasicInformation getGame(int position) {
        BasicInformation game = new BasicInformation();
        SQLiteDatabase db = this.getWritableDatabase();

        int id = position + 1;

        Cursor res = db.rawQuery( "select * from boardgames WHERE _id = ?", new String [] {String.valueOf(id)} );
        res.moveToFirst();
        game.setName(res.getString(res.getColumnIndex("name")));
        game.setAge(res.getString(res.getColumnIndex("age")));
        game.setDuration(res.getString(res.getColumnIndex("duration")));
        game.setGenre(res.getString(res.getColumnIndex("genre")));
        game.setMinNumberOfPlayers(res.getString(res.getColumnIndex("minNumberOfPlayers")));
        game.setMaxNumberOfPlayers(res.getString(res.getColumnIndex("maxNumberOfPlayers")));

        return game;
    }

//    public SimpleCursorAdapter populateListViewFromDB() {
//        SQLiteDatabase db = DBHandler.getWritableDatabase();
//        String columns[] = {DBHandler.KEY_ROWID, DBHandler.KEY_NAME, DBHandler.KEY_DURATION};
//        Cursor cursor = db.query(DBHandler.TABLE_NAME, columns,null, null,null, null, null, null);
//        String[] fromFieldNames = new String[]{
//                DBHandler.KEY_ROWID, DBHandler.KEY_NAME, DBHandler.KEY_DURATION
//        };
//        int[] toViewIDs = new int[]{R.id.rowid, R.id.item_name, R.id.item_duration};
//
//        SimpleCursorAdapter gameAdapter = new SimpleCursorAdapter(
//                null,
//                R.layout.single_item,
//                cursor,
//                fromFieldNames,
//                toViewIDs,
//                0
//        );
//        return gameAdapter;
//    }

//    public SimpleCursorAdapter populateListViewFromDB(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        String columns[] = {DBHandler.KEY_ROWID, DBHandler.KEY_NAME, DBHandler.KEY_GENRE, DBHandler.KEY_AGE, DBHandler.KEY_MINNUMBEROFPLAYERS, DBHandler.KEY_MAXNUMBEROFPLAYERS, DBHandler.KEY_DURATION};
//        Cursor cursor = db.query(DBHandler.TABLE_NAME, columns,null, null,null, null, null, null);
//        String[] fromFieldNames = new String[]{
//                DBHandler.KEY_ROWID, DBHandler.KEY_NAME, DBHandler.KEY_GENRE, DBHandler.KEY_AGE, DBHandler.KEY_MINNUMBEROFPLAYERS, DBHandler.KEY_MAXNUMBEROFPLAYERS, DBHandler.KEY_DURATION
//        };
//        int[] toViewIDs = new int[]{R.id.item_id, R.id.txtName, R.id.spinGenre,  R.id.txtAge, R.id.txtMinNumberOfPlayers, R.id.txtMaxNumberOfPlayers, R.id.txtDuration};
//        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
//                DBHandler.this,
//                R.layout.single_item,
//                cursor,
//                fromFieldNames,
//                toViewIDs
//        );
        //return contactAdapter;
//    }

//    private static class DatabaseHelper extends SQLiteOpenHelper {
//
//
//
//        public DatabaseHelper(Context context) {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//            this.context = context;
//            Toast.makeText(context, "constructor called", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            String query = "CREATE TABLE " + TABLE_NAME + " ("
//                    + KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//                    + KEY_NAME + " TEXT,"
//                    + KEY_AGE + " TEXT,"
//                    + KEY_GENRE + " TEXT,"
//                    + KEY_MINNUMBEROFPLAYERS + " TEXT,"
//                    + KEY_MAXNUMBEROFPLAYERS + " TEXT,"
//                    + KEY_DURATION + " TEXT)";
//            db.execSQL(query);
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            db.execSQL(DROP_TABLE);
//            onCreate(db);
//        }
//    }

    //    public SimpleCursorAdapter populateListViewFromDB(){
//        String columns[] = {DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NAME, DatabaseHelper.KEY_GENRE, DatabaseHelper.KEY_AGE, DatabaseHelper.KEY_MINNUMBEROFPLAYERS, DatabaseHelper.KEY_MAXNUMBEROFPLAYERS, DatabaseHelper.KEY_DURATION};
//        Cursor cursor = db.query(DatabaseHelper.TABLE_NAME, columns,null, null,null, null, null, null);
//        String[] fromFieldNames = new String[]{
//                DatabaseHelper.KEY_ROWID, DatabaseHelper.KEY_NAME, DatabaseHelper.KEY_GENRE, DatabaseHelper.KEY_AGE, DatabaseHelper.KEY_MINNUMBEROFPLAYERS, DatabaseHelper.KEY_MAXNUMBEROFPLAYERS, DatabaseHelper.KEY_DURATION
//        };
//        int[] toViewIDs = new int[]{R.id.item_id, R.id.txtName, R.id.spinGenre,  R.id.txtAge, R.id.txtMinNumberOfPlayers, R.id.txtMaxNumberOfPlayers, R.id.txtDuration};
//        SimpleCursorAdapter contactAdapter = new SimpleCursorAdapter(
//                context,
//                R.layout.single_item,
//                cursor,
//                fromFieldNames,
//                toViewIDs
//        );
//        return contactAdapter;
//    }
}