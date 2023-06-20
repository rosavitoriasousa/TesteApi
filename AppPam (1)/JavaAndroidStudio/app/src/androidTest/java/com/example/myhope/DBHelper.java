package com.example.myhope;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static  String DATABASE_NAME = "Api.db";
    private static  String TABLE_NAME = "ApiUsuario";
    private static  String COL_ID = "ID";
    private static  String COL_LAST_SEARCH = "UltimaPesquisa";
    private static  String COL_FAVORITES = "Favoritos";


    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Criando a tabela no banco de dados com as colunas

        String creatTableQuery = "CREATE TABLE ApiUsuario" + TABLE_NAME + " (" +
        COL_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COL_LAST_SEARCH + "TEXT ," +
        COL_FAVORITES + " TEXT";
        db.execSQL(creatTableQuery);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersio) {
    //Atualizando o banco de dados

        db.execSQL("DROP TABLE IF EXISTS ApiUsuario" + TABLE_NAME);
        onCreate(db);

    }

    public boolean saveLastSearch(String searchQuery){
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_LAST_SEARCH, searchQuery);

        // atualizar o registro que ja existe se não existir insira um novo
        long result = db.update(TABLE_NAME,contentValues, null , null);
        if(result == -1){
                return false; // falha ao salvar
        } else {
           return true; //ultima pesquisa salva
        }
    }

    public  String getLastSearch(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT LastSearch" + COL_LAST_SEARCH + "FROM" + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String lastSearch = "";

        if(cursor.moveToFirst()){
            lastSearch = cursor.getString(cursor.getColumnIndex(COL_LAST_SEARCH));
        }

        cursor.close();
        return lastSearch;
    }

    public String getFavorites(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT Favorites" + COL_FAVORITES + "FROM" + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String favorites = "";

        if(cursor.moveToFirst()){
            favorites = cursor.getString(cursor.getColumnIndex(COL_FAVORITES));
        }

        cursor.close();
        return favorites;

    }



}


