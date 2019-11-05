package com.jonas.firebaseauth;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jonas.firebaseauth.dao.NomeDao;
import com.jonas.firebaseauth.model.Nome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

import static com.jonas.firebaseauth.HomeActivity.MODE;
import static com.jonas.firebaseauth.HomeActivity.MODE_INSERT;

public class ListNomes extends AppCompatActivity {


    public static final String ID_NOME = "id_nome";
    public RecyclerView mRecyclerView;
    public NomeAdapter nomeAdapter;
    public NomeDao nomeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nomes);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPersistNome();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get recycler
        mRecyclerView = findViewById(R.id.recycler);

        //get list of nomes
        nomeDao = new NomeDao(ListNomes.this);
        setupRecycler();
    }

    public void startPersistNome(){
        Intent intent = new Intent(getBaseContext(), PersistNome.class);
        intent.putExtra(MODE, MODE_INSERT);
        startActivity(intent);
    }

    public void setupRecycler(){
        ArrayList<Nome> items = (ArrayList<Nome>) nomeDao.getNomes();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        // Adiciona o adapter que irá anexar os objetos à lista.
        nomeAdapter = new NomeAdapter(items, ListNomes.this);
        mRecyclerView.setAdapter(nomeAdapter);

        // Configurando um divider entre linhas, para uma melhor visualização.
        mRecyclerView.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        setupRecycler();
    }
}
