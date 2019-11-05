package com.jonas.firebaseauth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jonas.firebaseauth.dao.NomeDao;
import com.jonas.firebaseauth.model.Nome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static com.jonas.firebaseauth.HomeActivity.MODE;
import static com.jonas.firebaseauth.HomeActivity.MODE_INSERT;
import static com.jonas.firebaseauth.HomeActivity.MODE_UPDATE;
import static com.jonas.firebaseauth.ListNomes.ID_NOME;

public class PersistNome extends AppCompatActivity {
    private String mode;
    private NomeDao nomeDao;
    private EditText edtNome;
    private EditText edtCod;
    private EditText edtStatus;
    private Nome nomeUpdate;
    //lista de string para spinner
    public static List<String> nomes;
    //id's para referenciar os nomes
    public static List<Integer> idNomes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persist_nome);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            mode = bundle.getString(MODE);
        }

        nomeDao = new NomeDao(PersistNome.this);

        edtNome = findViewById(R.id.edtNome);
        edtCod = findViewById(R.id.edtCod);
        edtStatus = findViewById(R.id.edtStatus);

        //verificar se está em modo de edição
        if(mode.equals(MODE_UPDATE)){
            nomeUpdate = nomeDao.getNome(Integer.parseInt(bundle.getString(ID_NOME)));
            edtNome.setText(nomeUpdate.getNome());
            edtStatus.setText(Integer.toString(nomeUpdate.getStatus()));
            edtCod.setText(nomeUpdate.getCod());
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode.equals(MODE_INSERT)){
                    insertNome();
                }else if(mode.equals(MODE_UPDATE)){
                    updateNome();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void updateNome(){
        if(edtNome.getText().toString().isEmpty()){
            edtNome.setError("Campo obrigatório");
            return;
        }

        if(edtCod.getText().toString().isEmpty()){
            edtCod.setError("Campo obrigatório");
            return;
        }

        String status = edtStatus.getText().toString().trim();
        if(!status.equals("0") && !status.equals("1") ){
            edtStatus.setError("Status deve ser 1 ou 0");
            edtStatus.requestFocus();
            return;
        }

        nomeUpdate.setNome(edtNome.getText().toString().trim());
        nomeUpdate.setStatus(Integer.parseInt(edtStatus.getText().toString().trim()));
        nomeUpdate.setCod(edtCod.getText().toString().trim());
        if(nomeDao.update(nomeUpdate)){
            Toast.makeText(getApplicationContext(), "Nome atualizado!!", Toast.LENGTH_LONG).show();
            onBackPressed();
            finish();
        }else{
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Erro ao atualizar Nome!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void insertNome(){

        if(edtNome.getText().toString().isEmpty()){
            edtNome.setError("Campo obrigatório");
            return;
        }

        if(edtCod.getText().toString().isEmpty()){
            edtCod.setError("Campo obrigatório");
            return;
        }else if(nomeDao.checkCod(edtCod.getText().toString().trim())){
            edtCod.setError("Código já existe");
            return;
        }

        String status = edtStatus.getText().toString().trim();
        if(!status.equals("0") && !status.equals("1") ){
            edtStatus.setError("Status deve ser 1 ou 0");
            edtStatus.requestFocus();
            return;
        }



        Log.i("INSERT NOME", "tudo checado");

        Nome nome = new Nome();
        nome.setNome(edtNome.getText().toString().trim());
        nome.setCod(edtCod.getText().toString().trim());
        nome.setStatus(Integer.parseInt(edtStatus.getText().toString()));
        if(nomeDao.insertNome(nome)){
            Toast.makeText(getApplicationContext(), "Nome inserido!!", Toast.LENGTH_LONG).show();
            onBackPressed();
            finish();
        }else{
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Erro ao inserir Nome!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

}
