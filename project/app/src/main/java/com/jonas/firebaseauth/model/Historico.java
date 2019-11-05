package com.jonas.firebaseauth.model;

import androidx.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Historico {
    private int id;
    private String historico;
    private int idNome;
    private String cor;
    private int tipo;
    private int status;

    public Historico(){}

    public Historico(int id, String historico, int idNome, String cor, @Nullable Integer tipo,
    @Nullable Integer status){
        this.id = id;
        this.historico = historico;
        this.idNome = idNome;
        this.cor = cor;
        this.tipo = tipo != null ? tipo : 1;
        this.status = status != null ? status : 0;
    }

    public int getId() {
        return id;
    }

    public String getHistoricoDisplay(){
        try{
            Date dtH = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(historico);
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(dtH);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public String getHistorico(){
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico;
    }

    public int getIdNome() {
        return idNome;
    }

    public void setIdNome(int idNome) {
        this.idNome = idNome;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
