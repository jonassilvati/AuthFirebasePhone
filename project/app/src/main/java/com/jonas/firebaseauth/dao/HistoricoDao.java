package com.jonas.firebaseauth.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jonas.firebaseauth.database.DB;
import com.jonas.firebaseauth.model.Historico;

import java.util.ArrayList;

public class HistoricoDao {

    protected  static final String TABELA = "tb_historico";
    protected  static final String ID = "_id";
    protected  static final String HISTORICO = "historico";
    protected  static final String COR = "cor";
    protected  static final String TIPO = "tipo";
    protected  static final String STATUS = "status";
    protected static final String ID_NOME = "fk_id_tb_nome";

    private SQLiteDatabase db;

    public HistoricoDao(Context context){
        db = new DB(context).getDb();
    }

    public Boolean insertHistorico(Historico historico){
        ContentValues contentValues = new ContentValues();
        contentValues.put(HISTORICO, historico.getHistorico());
        contentValues.put(COR, historico.getCor());
        contentValues.put(TIPO, historico.getTipo());
        contentValues.put(STATUS, historico.getStatus());
        contentValues.put(ID_NOME, historico.getIdNome());


        if(db.insert(TABELA, null, contentValues) != -1){
            return true;
        }

        return false;
    }

    public Boolean deleteHistorico(Historico historico){
        String[] arg = new String[]{Integer.toString(historico.getId())};

        if(db.delete(TABELA, ID+"=?",arg) != 0){
            return true;
        }

        return false;
    }

    public ArrayList<Historico> getHistoricos(){
        ArrayList<Historico> listHistoricos = new ArrayList<Historico>();
        String[] columns = new String[]{ID, HISTORICO, COR, TIPO, STATUS, ID_NOME};
        Cursor cursor = db.query(
                TABELA,
                columns,
                null,
                null,
                null,
                null,
                ID+" DESC");

        if(cursor.moveToFirst()){
            final int INDEX_ID = cursor.getColumnIndex(ID);
            final int INDEX_HISTORICO = cursor.getColumnIndex(HISTORICO);
            final int INDEX_COR = cursor.getColumnIndex(COR);
            final int INDEX_TIPO = cursor.getColumnIndex(TIPO);
            final int INDEX_STATUS = cursor.getColumnIndex(STATUS);
            final int INDEX_ID_NOME = cursor.getColumnIndex(ID_NOME);

            do{
                Historico h = new Historico(
                        cursor.getInt(INDEX_ID),
                        cursor.getString(INDEX_HISTORICO),
                        cursor.getInt(INDEX_ID_NOME),
                        cursor.getString(INDEX_COR),
                        cursor.getInt(INDEX_TIPO),
                        cursor.getInt(INDEX_STATUS)
                );
                listHistoricos.add(h);
            }while(cursor.moveToNext());
        }

        return listHistoricos;
    }

    public Historico getHistorico(int id){
        Historico h = new Historico();
        String[] columns = new String[]{ID, HISTORICO, COR, TIPO, STATUS, ID_NOME};

        Cursor cursor = db.query(
                TABELA,
                columns,
                ID+"=?",
                new String[]{Integer.toString(id)},
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            final int INDEX_ID = cursor.getColumnIndex(ID);
            final int INDEX_HISTORICO = cursor.getColumnIndex(HISTORICO);
            final int INDEX_COR = cursor.getColumnIndex(COR);
            final int INDEX_TIPO = cursor.getColumnIndex(TIPO);
            final int INDEX_STATUS = cursor.getColumnIndex(STATUS);
            final int INDEX_ID_NOME = cursor.getColumnIndex(ID_NOME);

            h = new Historico(
                    cursor.getInt(INDEX_ID),
                    cursor.getString(INDEX_HISTORICO),
                    cursor.getInt(INDEX_ID_NOME),
                    cursor.getString(INDEX_COR),
                    cursor.getInt(INDEX_TIPO),
                    cursor.getInt(INDEX_STATUS)
            );
        }

        cursor.close();
        return h;
    }

    public Boolean update(Historico h){
        ContentValues contentValues = new ContentValues();
        contentValues.put(COR, h.getCor());
        contentValues.put(STATUS, h.getStatus());
        contentValues.put(TIPO, h.getTipo());
        contentValues.put(ID_NOME, h.getIdNome());
        contentValues.put(HISTORICO, h.getHistorico());

        String[] arg = new String[]{Integer.toString(h.getId())};

        if(db.update(TABELA, contentValues, ID+"=?",arg) > 0){
            return true;
        }

        return false;
    }




}
