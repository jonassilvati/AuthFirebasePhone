package com.jonas.firebaseauth.model;

import androidx.annotation.Nullable;

public class Nome {
    private int id;
    private String nome;
    private String cod;
    private int status;

    public Nome(){

    }

    public Nome(int id, String nome, String cod,@Nullable Integer status){
        this.id = id;
        this.nome = nome;
        this.cod = cod;
        this.status = status != null ? status : 0;
    }

    public int getId(){
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
