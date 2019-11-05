package com.jonas.firebaseauth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jonas.firebaseauth.dao.HistoricoDao;
import com.jonas.firebaseauth.model.Historico;
import com.jonas.firebaseauth.model.Nome;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;

import static com.jonas.firebaseauth.HomeActivity.MODE;
import static com.jonas.firebaseauth.HomeActivity.MODE_INSERT;
import static com.jonas.firebaseauth.HomeActivity.MODE_UPDATE;
import static com.jonas.firebaseauth.ListHistorico.ID_HISTORICO;

public class PersistHistorico extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private Spinner spinnerNomes;
    private ImageView ivShapeColor;
    private EditText edtHistorico;
    private EditText edtTipo;
    private EditText edtStatus;
    //string para persistir no banco
    private String dateTime;
    //string para mostrar no edtHistorico
    private String dateTimeDisplay;
    //mode atual(insert ou update)
    private String mode;
    private HistoricoDao historicoDao;
    private ArrayList<String> nomes;
    private ArrayList<Integer> idNomes;
    private String hexColor;
    private Historico mHistorico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persist_historico);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            mode = bundle.getString(MODE);
        }

        historicoDao = new HistoricoDao(PersistHistorico.this);

        spinnerNomes = findViewById(R.id.spinnerNomes);
        nomes = new ArrayList<>();
        idNomes = new ArrayList<>();
        setupSpinner();

        edtHistorico = findViewById(R.id.edtHistorico);
        edtHistorico.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            PersistHistorico.this,
                            now.get(Calendar.YEAR), // Initial year selection
                            now.get(Calendar.MONTH), // Initial month selection
                            now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                    );
                    dpd.show(getSupportFragmentManager(), "Datepickerdialog");
                }
            }
        });

        edtTipo = findViewById(R.id.edtTipo);
        hexColor = String.format("#%06X", (0xFFFFFF & getColor(R.color.colorPrimary)));
        edtStatus = findViewById(R.id.edtStatus);

        ivShapeColor = findViewById(R.id.ivShapeCor);
        ivShapeColor.setColorFilter(Color.parseColor(hexColor));
        ivShapeColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mode.equals(MODE_INSERT)){
                    insertHistorico();
                }else if(mode.equals(MODE_UPDATE)){
                    updateHistorico();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(mode.equals(MODE_UPDATE)){
            mHistorico = historicoDao.getHistorico(bundle.getInt(ID_HISTORICO));
            setSpinnerPosition(mHistorico.getId());
            edtHistorico.setText(mHistorico.getHistoricoDisplay());
            dateTime = mHistorico.getHistorico();
            edtStatus.setText(Integer.toString(mHistorico.getStatus()));
            edtTipo.setText(Integer.toString(mHistorico.getTipo()));
            hexColor = mHistorico.getCor();
            ivShapeColor.setColorFilter(Color.parseColor(hexColor));
        }
    }

   /*Este metodo seta a posição do spinner a partir
   * do id
   * @param id - id do nome
   * */
    public void setSpinnerPosition(int id){
        int position = 0;
        for (int i=0; i<idNomes.size();i++){
            if(idNomes.get(i) == id){
                position = i;
            }
        }

        spinnerNomes.setSelection(position);
    }

    public void setupSpinner(){
        for (Nome nome : ListHistorico.listNomes) {
            nomes.add(nome.getNome());
            idNomes.add(nome.getId());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
          PersistHistorico.this,
                android.R.layout.simple_spinner_dropdown_item,
                nomes
        );

        spinnerNomes.setAdapter(adapter);
    }

    public void insertHistorico(){

        if(edtHistorico.getText().toString().isEmpty()){
            edtHistorico.setError("Historico deve ser preenchido");
            edtHistorico.requestFocus();
            return;
        }

        if(edtTipo.getText().toString().isEmpty()){
            edtTipo.setError("Tipo deve ser preenchido");
            edtTipo.requestFocus();
            return;
        }

        String status = edtStatus.getText().toString().trim();
        if(!status.equals("0") && !status.equals("1") ){
            edtStatus.setError("Status deve ser 1 ou 0");
            edtStatus.requestFocus();
            return;
        }

        //id do nome selecionado no spinner
        int idNome = idNomes.get(spinnerNomes.getSelectedItemPosition());
        Historico h = new Historico();
        h.setCor(hexColor);
        h.setHistorico(dateTime);
        h.setTipo(Integer.parseInt(edtTipo.getText().toString()));
        h.setIdNome(idNome);
        h.setStatus(Integer.parseInt(status));

        if(historicoDao.insertHistorico(h)){
            Toast.makeText(getApplicationContext(), "Historico inserido!!", Toast.LENGTH_LONG).show();
            onBackPressed();
            finish();
        }else{
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Erro ao inserir Historico!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }

    public void updateHistorico(){
        if(edtHistorico.getText().toString().isEmpty()){
            edtHistorico.setError("Historico deve ser preenchido");
            edtHistorico.requestFocus();
            return;
        }

        if(edtTipo.getText().toString().isEmpty()){
            edtTipo.setError("Tipo deve ser preenchido");
            edtTipo.requestFocus();
            return;
        }

        String status = edtStatus.getText().toString().trim();
        if(!status.equals("0") && !status.equals("1") ){
            edtStatus.setError("Status deve ser 1 ou 0");
            edtStatus.requestFocus();
            return;
        }

        mHistorico.setIdNome(idNomes.get(spinnerNomes.getSelectedItemPosition()));
        mHistorico.setHistorico(dateTime);
        mHistorico.setCor(hexColor);
        mHistorico.setTipo(Integer.parseInt(edtTipo.getText().toString()));
        mHistorico.setStatus(Integer.parseInt(edtStatus.getText().toString()));

        if(historicoDao.update(mHistorico)){
            Toast.makeText(getApplicationContext(), "Historico atualizado!!", Toast.LENGTH_LONG).show();
            onBackPressed();
            finish();
        }else{
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Erro ao atualizar Historico!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void openColorPicker(){
        AmbilWarnaDialog ambilWarnaDialog = new AmbilWarnaDialog(this, ContextCompat.getColor(this, R.color.colorPrimary), new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                ivShapeColor.setColorFilter(color);
                hexColor = String.format("#%06X", (0xFFFFFF & color));
            }
        });
        ambilWarnaDialog.show();
    }

    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        String hour = String.format("%02d", hourOfDay);
        String sMinute = String.format("%02d", minute);
        String sSecond = String.format("%02d", second);

        dateTime = dateTime+' '+hour+':'+sMinute+':'+sSecond;
        dateTimeDisplay = dateTimeDisplay+' '+hour+':'+sMinute+':'+sSecond;
        edtHistorico.setText(dateTimeDisplay);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String sYear = Integer.toString(year);
        String sMonth = String.format("%02d", monthOfYear+1);
        String day = String.format("%02d", dayOfMonth);

        dateTime = sYear+'-'+sMonth+'-'+day;
        dateTimeDisplay = day+'/'+sMonth+'/'+sYear;

        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                PersistHistorico.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show(getSupportFragmentManager(), "Time Picker");
    }
}
