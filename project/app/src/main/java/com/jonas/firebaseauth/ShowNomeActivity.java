package com.jonas.firebaseauth;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

public class ShowNomeActivity extends AppCompatActivity {

    private TextView tvCod;
    private TextView tvNome;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_nome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvStatus = findViewById(R.id.tvStatus);
        tvNome = findViewById(R.id.tvNome);
        tvCod = findViewById(R.id.tvCodigo);

        final Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            tvCod.setText(bundle.getString("codigo"));
            tvNome.setText(bundle.getString("nome"));
            tvStatus.setText(bundle.getString("status"));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
