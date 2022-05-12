package com.example.victorlelis_p2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "Jogos.db";

    /*variaveis para a criação da tabela time*/
    private static final String TABLE_TIME_NAME = "times";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String TABLE_TIME_CREATE = "create table "+ TABLE_TIME_NAME  +
            "("+ COL_ID + " integer primary key autoincrement, "+ COL_NAME + " text not null);";
/*    private static final String TABLE_CREATE="create table "+TABLE_NAME+
            "("+COL_ID+" integer primary key autoincrement, "+
            COL_NAME+" text not null, "+COL_EMAIL+" text not null, " +
            COL_USER+" text not null, "+COL_PASS+" text not null);";*/

    /*variaveis para a criação da tabela jogador*/


    SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_TIME_CREATE);
        /*db.execSQL(TABLE_JOGADOR_CREATE);*/
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+ TABLE_TIME_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void insereTime(Time t){
        db = this.getWritableDatabase();
        db.beginTransaction();

        try{
            ContentValues values = new ContentValues();
            values.put(COL_NAME, t.getName());
            db.insertOrThrow(TABLE_TIME_NAME, null, values);
            db.setTransactionSuccessful();
        }catch(Exception e){
            Log.d(TAG, "erro ao inserir na tabela time");
        }finally {
            db.endTransaction();
        }

    }

    //retorna todos os times cadastrados
    public ArrayList<Time> listaTimes(){
        /*lembrar de modificar para receber um argumento como filtro*/
        String[] coluns = {COL_ID, COL_NAME};//todas as colunas
        //ordena por nome todas as linhas retornadas
        Cursor cursor = getReadableDatabase().query(
                TABLE_TIME_NAME,
                coluns,
                null,
                null,
                null,
                null,
                "upper(name)",
                null);

        ArrayList<Time> list = new ArrayList<Time>();
        while(cursor.moveToNext()){
            Time t = new Time();
            t.setIdTime(cursor.getInt(0));
            t.setName(cursor.getString(1));
            list.add(t);
        }
        cursor.close();
        return list;
    }

    public long excluirTime(Time t){
        long retornoDB = 0;
        db = this.getWritableDatabase();
        String args[] = {String.valueOf(t.getIdTime())};
        retornoDB = db.delete(TABLE_TIME_NAME, COL_ID+"=?", args);
        return retornoDB;
    }

    public long atualizarTime(Time t){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, t.getName());
        String args[] = {String.valueOf(t.getIdTime())};
        long retornoDB = db.update(TABLE_TIME_NAME, values, "id = ?", args);
        db.close();
        return retornoDB;
    }
}
