package com.example.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler  extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "boardgamesdb";
    private static final String TABLE_NAME = "boardgames";
    // When you do change the structure of the database change the version number from 1 to 2
    private static final int DATABASE_VERSION = 1;
    private static final String KEY_ROWID = "id";
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
