package com.jonas.firebaseauth.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBCore extends SQLiteOpenHelper {

    private static final String NOME_DB = "db_nomes";
    private static final int VERSAO_DB = 2;

    public DBCore(Context context){
        super(context, NOME_DB, null, VERSAO_DB);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlNome = "CREATE TABLE tb_nome(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome varchar(200) NOT NULL," +
                "cod varchar(30) NOT NULL," +
                "status INTEGER NOT NULL DEFAULT 0);";

        String sqlHistorico = "CREATE TABLE tb_historico(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "historico datetime NOT NULL," +
                "cor varchar(20) NOT NULL," +
                "tipo INTEGER NOT NULL DEFAULT 1," +
                "status INTEGER NOT NULL DEFAULT 0," +
                "fk_id_tb_nome INTEGER NOT NULL," +
                "FOREIGN KEY(fk_id_tb_nome) REFERENCES tb_nome(_id));";

        db.execSQL(sqlNome);
        db.execSQL(sqlHistorico);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE tb_historico");
        db.execSQL("DROP TABLE tb_nome");
        onCreate(db);
    }
}
