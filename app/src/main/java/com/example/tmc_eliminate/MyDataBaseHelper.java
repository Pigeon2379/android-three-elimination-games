package com.example.tmc_eliminate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "user.db";
    private static MyDataBaseHelper MDBH = null;
    private SQLiteDatabase MDB = null;
    public static final String TABLE_NAME = "table_user";

    //constructors
    private MyDataBaseHelper(Context context, int version){
        super(context,DB_NAME,null,version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql_create="CREATE TABLE IF NOT EXISTS "+TABLE_NAME+" (" +
                "account VARCHAR PRIMARY KEY NOT NULL," +
                "PASSWORD VARCHAR NOT NULL," +
                "ECOUNTS INTEGER NOT NULL," +
                "MCOUNTS INTEGER NOT NULL," +
                "DCOUNTS INTEGER NOT NULL," +
                "ECOUNTF INTEGER NOT NULL," +
                "MCOUNTF INTEGER NOT NULL," +
                "DCOUNTF INTEGER NOT NULL," +
                "TIME INTEGER NOT NULL);";
        sqLiteDatabase.execSQL(sql_create);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){}

    //单例模式
    public static MyDataBaseHelper getInstance(Context context,int version){
        if(MDBH==null)MDBH=new MyDataBaseHelper(context,version);
        return MDBH;
    }


    //readLink,writeLink,closeLink
    public SQLiteDatabase openReadLink(){
        if(MDB==null||!MDB.isOpen())MDB=MDBH.getReadableDatabase();
        return MDB;
    }
    public SQLiteDatabase openWriteLink(){
        if(MDB==null||!MDB.isOpen())MDB=MDBH.getWritableDatabase();
        return MDB;
    }
    public void closeLink(){
        if(MDB!=null&&MDB.isOpen()){
            MDB.close();
            MDB=null;
        }
    }


    //insert a user record
    public long insert(ContentValues cv) {
        long result=-1;
        openWriteLink();
        result=MDB.insert(TABLE_NAME,null,cv);
        closeLink();
        return result;
    }

    //update user record
    public int update(ContentValues cv,String condition){
        openWriteLink();
        int result=MDB.update(TABLE_NAME,cv,condition,null);
        closeLink();
        return result;
    }
    //get a user record
    public User query(String account){
        String sql="select * from "+TABLE_NAME;
        openReadLink();
        Cursor cursor=MDB.rawQuery(sql,null);
        while(cursor.moveToNext()){
            if(cursor.getString(0).equals(account)){
                return new User(cursor.getString(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3)
                        ,cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getInt(7),cursor.getInt(8));
            }
        }
        return new User("null","null");
    }

    //get user record list
    public List<User> getUserRecordList(){
        List<User> arr=new ArrayList<User>();
        String sql="select * from "+TABLE_NAME;
        openReadLink();
        Cursor cursor=MDB.rawQuery(sql,null);
        while(cursor.moveToNext()){
            User user=new User(null,null);
            user.setAccount(cursor.getString(0));
            user.setPassword(cursor.getString(1));
            user.setEcount_s(cursor.getInt(2));
            user.setMcount_s(cursor.getInt(3));
            user.setDcount_s(cursor.getInt(4));
            user.setEcount_f(cursor.getInt(5));
            user.setMcount_f(cursor.getInt(6));
            user.setDcount_f(cursor.getInt(7));
            user.setMin_time(cursor.getInt(8));
            arr.add(user);
        }
        return arr;
    }

}
