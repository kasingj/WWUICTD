package com.example.kasingj.smokecessation2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alvin on 3/3/2016.
 */
public class DatabaseOperations extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;

    // UserStats Query String
    public String CREATE_USER_QUERY = "CREATE TABLE " + TableData.TableInfo.USER_TABLE_NAME + "(" +
            TableData.TableInfo.USER_NAME + " TEXT," + TableData.TableInfo.ID + " TEXT," + TableData.TableInfo.TIME +
            " TEXT," + TableData.TableInfo.TOTAL_DAYS_FREE + " TEXT," + TableData.TableInfo.LONGEST_STREAK +
            " TEXT," + TableData.TableInfo.CURRENT_STREAK + " TEXT," + TableData.TableInfo.NUM_CRAVINGS +
            " TEXT," + TableData.TableInfo.CRAVINGS_RESISTED + " TEXT," + TableData.TableInfo.NUM_CIGS_SMOKED +
            " TEXT," + TableData.TableInfo.MONEY_SAVED + " TEXT," + TableData.TableInfo.LIFE_REGAINED + " TEXT);";

    // UserAuthentication Query String
    public String CREATE_USER_AUTH_QUERY = "CREATE TABLE " + TableData.TableInfo.USER_AUTH_NAME + "(" +
            TableData.TableInfo.USER_NAME + " TEXT," + TableData.TableInfo.PASSWORD +
            " TEXT," + TableData.TableInfo.EMAIL + " TEXT);";

    // Create Database
    public DatabaseOperations(Context context) {
        super(context, TableData.TableInfo.DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Database Operations", "Database created");
    }

    @Override
    public void onCreate(SQLiteDatabase sdb) {

        // Create user_auth Table
        Log.d("Database Operations", "creating user_auth table");
        sdb.execSQL(CREATE_USER_AUTH_QUERY);
        Log.d("Database Operations", "user_stats auth created");

        // Create user_stats Table
        Log.d("Database Operations", "creating user_stats table");
        sdb.execSQL(CREATE_USER_QUERY);
        Log.d("Database Operations", "user_stats table created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sdb, int oldVersion, int newVersion) {

    }

    public static String getCurrTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        String time = sdf.format(date).toString();
        return time;
    }

    // pulling user authentication data
    public Cursor getUserAuth(DatabaseOperations dbop) {

        SQLiteDatabase sq = dbop.getReadableDatabase();
        String[] columns = {TableData.TableInfo.USER_NAME, TableData.TableInfo.PASSWORD};
        Cursor cr = sq.query(TableData.TableInfo.USER_AUTH_NAME, columns, null, null, null, null, null);
        return cr;
    }

    // pulling from user_stats table
    public Cursor getUserStats(DatabaseOperations dbop, String username) {

        SQLiteDatabase sq = dbop.getReadableDatabase();
        String[] columns = {TableData.TableInfo.USER_NAME, TableData.TableInfo.ID, TableData.TableInfo.TIME, TableData.TableInfo.TOTAL_DAYS_FREE,
                TableData.TableInfo.LONGEST_STREAK, TableData.TableInfo.CURRENT_STREAK, TableData.TableInfo.NUM_CRAVINGS,
                TableData.TableInfo.CRAVINGS_RESISTED, TableData.TableInfo.NUM_CIGS_SMOKED, TableData.TableInfo.MONEY_SAVED,
                TableData.TableInfo.LIFE_REGAINED};
        String where = TableData.TableInfo.USER_NAME + " = ?";
        String[] whereArgs = new String[] {username};
        String orderBy = TableData.TableInfo.TIME + " DESC LIMIT 1";
        Cursor cr = sq.query(TableData.TableInfo.USER_TABLE_NAME, columns, where, whereArgs, null, null, orderBy);
        return cr;


////        String where = TableData.TableInfo.TIME + " = (SELECT max(" + TableData.TableInfo.TIME + ") FROM " + TableData.TableInfo.USER_TABLE_NAME + ") AS lastTime";
////        String whereArgs = "(SELECT man(" + TableData.TableInfo.TIME + ") FROM " + TableData.TableInfo.USER_TABLE_NAME + ") AS time";
//        String where = TableData.TableInfo.TIME + " = ? And " +
//        Cursor cr = sq.query(TableData.TableInfo.USER_TABLE_NAME, columns, where, null, null, null, null);
//        return cr;
    }

    // adding to user_auth Table
    public void addUserAuth(DatabaseOperations dbop, String username, String password, String email) {

        SQLiteDatabase sq = dbop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TableData.TableInfo.USER_NAME, username);
        cv.put(TableData.TableInfo.PASSWORD, password);
        cv.put(TableData.TableInfo.EMAIL, email);

        sq.insert(TableData.TableInfo.USER_AUTH_NAME, null, cv);
        Log.d("Database Operations", "One row inserted into user_auth table");
    }

    // adding to user_stats Table
    public void addUserStats(DatabaseOperations dbop, String username, String id, String time, String totsDayFree, String longStreak, String currStreak,
                            String cravs, String cravsRes, String numSmokes, String moneySaved, String lifeReg) {

        SQLiteDatabase sq = dbop.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TableData.TableInfo.USER_NAME, username);
        cv.put(TableData.TableInfo.ID, id);
        cv.put(TableData.TableInfo.TIME, time);
        cv.put(TableData.TableInfo.TOTAL_DAYS_FREE, totsDayFree);
        cv.put(TableData.TableInfo.LONGEST_STREAK, longStreak);
        cv.put(TableData.TableInfo.CURRENT_STREAK, currStreak);
        cv.put(TableData.TableInfo.NUM_CRAVINGS, cravs);
        cv.put(TableData.TableInfo.CRAVINGS_RESISTED, cravsRes);
        cv.put(TableData.TableInfo.NUM_CIGS_SMOKED, numSmokes);
        cv.put(TableData.TableInfo.MONEY_SAVED, moneySaved);
        cv.put(TableData.TableInfo.LIFE_REGAINED, lifeReg);

        sq.insert(TableData.TableInfo.USER_TABLE_NAME, null, cv);
        Log.d("Database Operations", "One row inserted into user_stats Table");
    }

}
