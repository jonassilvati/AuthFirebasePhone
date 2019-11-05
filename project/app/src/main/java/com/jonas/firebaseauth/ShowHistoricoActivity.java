package com.jonas.firebaseauth;

import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ShowHistoricoActivity extends AppCompatActivity {

    private TextView tvNome;
    private TextView tvStatus;
    private TextView tvTipo;
    private ImageView ivShapeCor;
    private TextView tvHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_historico);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvHistorico = findViewById(R.id.tvHistorico);
        tvNome = findViewById(R.id.tvNome);
        tvStatus = findViewById(R.id.tvStatus);
        tvTipo = findViewById(R.id.tvTipo);
        ivShapeCor = findViewById(R.id.ivShapeCor);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            tvHistorico.setText(bundle.getString("historico"));
            tvTipo.setText(Integer.toString(bundle.getInt("tipo")));
            ivShapeCor.setColorFilter(Color.parseColor(bundle.getString("cor")));
            tvNome.setText(bundle.getString("nome"));
            tvStatus.setText(Integer.toString(bundle.getInt("status")));
        }

    }

}
