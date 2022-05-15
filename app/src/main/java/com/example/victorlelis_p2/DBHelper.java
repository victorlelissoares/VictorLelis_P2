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


    /*variaveis para a criação da tabela jogador*/
    private  static final String TABLE_JOGADOR_NAME = "jogador";
    private  static final String COL_ID_JOGADOR = "idJogador";
    private  static final String COL_ID_TIME = "idTime";//chave estrangeira para a tabela times
    private  static final String COL_NAME_TIME = "nameTime";//chave estrangeira para a tabela times
    private  static final String COL_NAME_JOGADOR = "nome";
    private  static final String COL_CPF_JOGADOR = "cpf";
    private  static final String COL_ANO_JOGADOR = "anoNascimento";
    private static final String TABLE_JOGADOR_CREATE = "create table "+ TABLE_JOGADOR_NAME  +
            "("+ COL_ID_JOGADOR + " integer primary key autoincrement, " + COL_ID_TIME + " integer not null, "
            + COL_NAME_TIME + " text not null, "+  COL_NAME_JOGADOR + " text not null, " + COL_CPF_JOGADOR + " text not null, " + COL_ANO_JOGADOR +
            " integer not null, " + "FOREIGN KEY(idTime) REFERENCES times(id));";


    SQLiteDatabase db;

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_TIME_CREATE);
        db.execSQL(TABLE_JOGADOR_CREATE);
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+ TABLE_TIME_NAME;
        db.execSQL(query);
        query = "DROP TABLE IF EXISTS "+ TABLE_JOGADOR_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    //métodos referentes a tabela jogador
    public void insereJogador(Jogador j){
        db = this.getWritableDatabase();
        db.beginTransaction();

        try{
            ContentValues values = new ContentValues();
            values.put(COL_ID_TIME, j.getIdTime());
            values.put(COL_NAME_TIME, j.getNomeTime());
            values.put(COL_NAME_JOGADOR, j.getNome());
            values.put(COL_CPF_JOGADOR, j.getCpf());
            values.put(COL_ANO_JOGADOR, j.getAnoNascimento());
            db.insertOrThrow(TABLE_JOGADOR_NAME, null, values);
            db.setTransactionSuccessful();
        }catch(Exception e){
            Log.d(TAG, "erro ao inserir na tabela jogador");
        }finally {
            db.endTransaction();
        }

    }

    public ArrayList<Jogador> listJogador() {
        /*String query = "SELECT jogador.nome, times.name FROM jogador  INNER JOIN times ON jogador.idtime = times.id;";
        db = getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery(query, null);

        ArrayList<String> list = new ArrayList<>();
        while(cursor.moveToNext()){
          String s = "Jogador: " + cursor.getString(0) +", Time: "+ cursor.getString(1);
          list.add(s);
        }
        cursor.close();

        return list;*/

        /*lembrar de modificar para receber um argumento como filtro*/
        String[] coluns = {COL_ID_JOGADOR ,COL_ID_TIME, COL_NAME_TIME, COL_NAME_JOGADOR, COL_CPF_JOGADOR,
                COL_ANO_JOGADOR};//todas as colunas
        //ordena por nome todas as linhas retornadas
        Cursor cursor = getReadableDatabase().query(
                TABLE_JOGADOR_NAME,
                coluns,
                null,
                null,
                null,
                null,
                null,
                null);
        ArrayList<Jogador> list = new ArrayList<Jogador>();
        while(cursor.moveToNext()){
            Jogador j = new Jogador();
            j.setIdJogador(cursor.getInt(0));
            j.setIdTime(cursor.getInt(1));
            j.setNomeTime(cursor.getString(2));
            j.setNome(cursor.getString(3));
            j.setCpf(cursor.getString(4));
            j.setAnoNascimento(cursor.getInt(5));
            list.add(j);
        }
        cursor.close();
        return list;
    }

    public long excluirJogador(Jogador t){
        long retornoDB;
        db = this.getWritableDatabase();
        String[] args = {String.valueOf(t.getIdJogador())};
        retornoDB = db.delete(TABLE_JOGADOR_NAME, COL_ID_JOGADOR+"=?", args);
        return retornoDB;
    }

    public void atualizarJogador(Jogador j){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COL_ID_JOGADOR, j.getIdJogador());
        values.put(COL_ID_TIME, j.getIdTime());
        values.put(COL_NAME_TIME, j.getNomeTime());
        values.put(COL_NAME_JOGADOR, j.getNome());
        values.put(COL_CPF_JOGADOR, j.getCpf());
        values.put(COL_ANO_JOGADOR, j.getAnoNascimento());

        String[] args = {String.valueOf(j.getIdJogador())};
        long retornoDB = db.update(TABLE_JOGADOR_NAME, values, "idJogador = ?", args);
        db.close();
    }


    //métodos referentes a tabela times
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

        ArrayList<Time> list = new ArrayList<>();
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
        db = this.getWritableDatabase();
        Cursor cursor;
        String query = "SELECT * FROM jogador INNER JOIN times ON jogador.idtime = times.id;" ;
        cursor = db.rawQuery(query, null);


        if(cursor.getCount() == 0) {
            String[] args = {String.valueOf(t.getIdTime())};
            db.delete(TABLE_TIME_NAME, COL_ID + "=?", args);

        }


        return cursor.getCount();
    }

    public void atualizarTime(Time t){
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, t.getName());
        String[] args = {String.valueOf(t.getIdTime())};
        long retornoDB = db.update(TABLE_TIME_NAME, values, "id = ?", args);
        db.close();
    }


}
