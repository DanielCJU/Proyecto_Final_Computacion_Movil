package com.example.proyecto_final_computacion_movil.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBUserAdapter
{
    public static final String KEY_ROWID = "_id";
    public static final String KEY_USERNAME= "username";
    public static final String KEY_PASSWORD = "password";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_NAME = "usersdb";
    private static final String DATABASE_TABLE = "users";
    private static final String DATABASE_PTABLE = "passwords";
    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_CREATE =
            "create table users (_id integer primary key autoincrement, "
                    + "username text not null, "
                    + "password text not null);";

    private static final String DATABASE_PASSWORD =
            "create table passwords (pid integer primary key autoincrement, " +
                    "_id integer not null, " +
                    "upass text not null, " +
                    "FOREIGN KEY(_id) REFERENCES users(_id));";

    private Context context = null;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBUserAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_PASSWORD);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS users");
            db.execSQL("DROP TABLE IF EXISTS passwords");
            onCreate(db);
        }
    }


    public void open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
    }


    public void close()
    {
        DBHelper.close();
    }

    public long AddUser(String username, String password)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_USERNAME, username);
        initialValues.put(KEY_PASSWORD, password);
        return db.insert(DATABASE_TABLE, null, initialValues);

    }

    public int findId(String username) throws SQLException
    {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE username=?", new String[]{username});
        int UID;
        if(mCursor != null && mCursor.moveToFirst()){
            @SuppressLint("Range") String temp = mCursor.getString(mCursor.getColumnIndex("_id"));
            UID = Integer.parseInt(temp);
            mCursor.close();
        } else {
            return -1;
        }
        return UID;
    }

    public boolean Login(String username, String password) throws SQLException
    {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + DATABASE_TABLE + " WHERE username=? AND password=?", new String[]{username,password});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public long AddPass (int UID, String upassword) throws SQLException
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put("_id", UID);
        initialValues.put("upass", upassword);
        return db.insert(DATABASE_PTABLE, null, initialValues);
    }

    public Cursor getAllPasswords(int UID) throws SQLException{
        try{
            Cursor mCursor = db.rawQuery("SELECT * FROM passwords WHERE _id=?", new String[]{String.valueOf(UID)});
            if(mCursor != null){
                return mCursor;
            } else {
                return null;
            }
        } catch(Exception e){
            return null;
        }
    }

    public boolean PassExists(String upass, int UID) throws SQLException
    {
        Cursor mCursor = db.rawQuery("SELECT * FROM " + "passwords"+ " WHERE _id=? AND upass=?", new String[]{String.valueOf(UID),upass});
        if (mCursor != null) {
            if(mCursor.getCount() > 0)
            {
                return true;
            }
        }
        return false;
    }
}
