package com.jonas.firebaseauth.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jonas.firebaseauth.database.DB;
import com.jonas.firebaseauth.model.Nome;

import java.util.ArrayList;
import java.util.List;

public class NomeDao {

    protected static final String TABELA = "tb_nome";
    protected static final String NOME = "nome";
    protected static final String STATUS = "status";
    protected static final String COD = "cod";
    protected static final String ID = "_id";

    private SQLiteDatabase db;

    public NomeDao(Context context){
        db = new DB(context).getDb();
    }

    public Boolean insertNome(Nome nome){
        ContentValues nomeValues = new ContentValues();
        nomeValues.put(NOME, nome.getNome());
        nomeValues.put(COD, nome.getCod());
        nomeValues.put(STATUS, nome.getStatus());

        if(db.insert(TABELA,null, nomeValues) != -1){
            return true;
        }
        return false;
    }

    public Boolean checkCod(String cod){
        String[] colunas = new String[]{NOME};
        Cursor cursor = db.query(TABELA
                , colunas
                , COD+"=?"
                , new String[]{cod}
                ,null
                , null
                ,null);

        if(cursor.getCount() > 0){
            return true;
        }

        return false;
    }

    public Boolean deleteNome(Nome nome){
        if(nomeIsUsed(nome.getId())){
            return false;
        }
        String[] arg = new String[]{Integer.toString(nome.getId())};
        if(db.delete(TABELA, ID+"=?", arg) != 0){
            return true;
        }
        return false;
    }

    public Boolean update(Nome nome){
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOME, nome.getNome());
        contentValues.put(COD, nome.getCod());
        contentValues.put(STATUS, nome.getStatus());

        String[] arg = new String[]{Integer.toString(nome.getId())};

        if(db.update(TABELA, contentValues, ID+"=?", arg) > 0){
            return true;
        }

        return false;
    }

    public List<Nome> getNomes(){
        List<Nome> nomes = new ArrayList<>();

        String[] colunas = new String[]{ID, NOME, COD, STATUS};

        Cursor cursor = db.query(
                TABELA
                , colunas
                , null
                , null
                , null
                , null
                , ID+" DESC");


        if(cursor.moveToFirst()){
            final int INDEX_ID = cursor.getColumnIndex(ID);
            final int INDEX_NOME = cursor.getColumnIndex(NOME);
            final int INDEX_STATUS = cursor.getColumnIndex(STATUS);
            final int INDEX_COD = cursor.getColumnIndex(COD);
            do{
                Nome nome = new Nome(
                        cursor.getInt(INDEX_ID),
                        cursor.getString(INDEX_NOME),
                        cursor.getString(INDEX_COD),
                        cursor.getInt(INDEX_STATUS)
                        );
                nomes.add(nome);
            }while(cursor.moveToNext());
        }

        cursor.close();

        return nomes;
    }

    public Nome getNome(int id){
        Nome nome = new Nome();

        String[] colunas = new String[]{ID, NOME, COD, STATUS};

        Cursor cursor = db.query(
                TABELA
                , colunas
                , ID+"=?"
                , new String[]{Integer.toString(id)}
                , null
                , null
                , null);


        if(cursor.moveToFirst()){
            final int INDEX_ID = cursor.getColumnIndex(ID);
            final int INDEX_NOME = cursor.getColumnIndex(NOME);
            final int INDEX_STATUS = cursor.getColumnIndex(STATUS);
            final int INDEX_COD = cursor.getColumnIndex(COD);

            nome = new Nome(
                    cursor.getInt(INDEX_ID),
                    cursor.getString(INDEX_NOME),
                    cursor.getString(INDEX_COD),
                    cursor.getInt(INDEX_STATUS)
            );

        }

        cursor.close();
        return nome;
    }

    public Boolean nomeIsUsed(int id){
        String[] arg = new String[]{Integer.toString(id)};
        Cursor cursor = db.query(
                HistoricoDao.TABELA,
                new String[]{ID},
                HistoricoDao.ID_NOME+"=?",
                arg,
                null,
                null,
                null
        );

        if(cursor.getCount() > 0){
            return true;
        }

        return false;
    }

}
