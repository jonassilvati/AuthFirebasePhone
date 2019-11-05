package com.jonas.firebaseauth;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jonas.firebaseauth.dao.HistoricoDao;
import com.jonas.firebaseauth.dao.NomeDao;
import com.jonas.firebaseauth.model.Historico;
import com.jonas.firebaseauth.model.Nome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.jonas.firebaseauth.HomeActivity.MODE;
import static com.jonas.firebaseauth.HomeActivity.MODE_INSERT;

public class ListHistorico extends AppCompatActivity {

    public static List<Nome> listNomes;
    //dao para operações de persistencia
    public HistoricoDao historicoDao;
    private NomeDao nomeDao;
    private RecyclerView mRecyclerView;
    private HistoricoAdapter historicoAdapter;
    public static final String ID_HISTORICO = "id_historico";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_historico);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PersistHistorico.class);
                intent.putExtra(MODE, MODE_INSERT);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        historicoDao = new HistoricoDao(ListHistorico.this);
        nomeDao = new NomeDao(ListHistorico.this);

        listNomes = nomeDao.getNomes();

        mRecyclerView = findViewById(R.id.recycler);
        setupRecycler();
    }

    public void setupRecycler(){
        ArrayList<Historico> items = historicoDao.getHistoricos();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        // Adiciona o adapter que irá anexar os objetos à lista.
        historicoAdapter = new HistoricoAdapter(items, ListHistorico.this);
        mRecyclerView.setAdapter(historicoAdapter);

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
