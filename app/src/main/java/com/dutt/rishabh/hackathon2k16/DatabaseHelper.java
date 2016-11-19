package com.dutt.rishabh.hackathon2k16;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rishabh on 19-11-2016.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="notes.db";
    public static final String TABLE_NAME="virtual_notes";
    public static final int DATABASE_VERSION=1;
    private static final String COL_1="ID";
    private static final String COL_2="NOTES";
    private static final String COL_3="DATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String create="create table "+TABLE_NAME+"("+COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_2+" TEXT NOT NULL,"+COL_3+" TEXT NOT NULL)";
        db.execSQL(create);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String upgrade="Drop table if exists "+TABLE_NAME;
        db.execSQL(upgrade);
        onCreate(db);

    }
    public boolean addNotes(String name,String date){
        SQLiteDatabase myDB=this.getWritableDatabase();
        ContentValues content=new ContentValues();
        content.put(COL_2,name);
        content.put(COL_3,date);
        long result=myDB.insert(TABLE_NAME,null,content);
        if(result == -1){
            return false;
        }
        else{
            return true;
        }

    }
    public Cursor viewAll(){
        SQLiteDatabase myDB=this.getReadableDatabase();
        Cursor res=myDB.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }
    /*public Cursor viewNote(int id){
        SQLiteDatabase myDB=this.getReadableDatabase();
        Cursor cursor=myDB.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE ID=?",new String[]{String.valueOf(id)});
        return cursor;
    }
    public boolean updateNotes(String id,String name,String date){
        SQLiteDatabase myDB=this.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put(COL_2,name);
        value.put(COL_3,date);
        myDB.update(TABLE_NAME,value,"ID = ?",new String[]{ id });
        return true;
    }*/
    public boolean deleteNote(String id){
        SQLiteDatabase myDB=this.getWritableDatabase();
        int a=myDB.delete(TABLE_NAME,"ID = ?",new String[] {id});
        if(a>0){
            return true;
        }else{
            return false;
        }
    }
    /*public void deleteAll(){
        SQLiteDatabase myDB=this.getWritableDatabase();
        myDB.execSQL("Drop table if exists "+TABLE_NAME);
        onCreate(myDB);
    }*/
}

