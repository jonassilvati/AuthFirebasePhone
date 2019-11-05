package com.jonas.firebaseauth;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText edtPhone;
    private EditText edtCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(isAuth()){
            Intent intentHome = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intentHome);
            finish();
        }

        edtPhone = findViewById(R.id.edtPhone);
        edtCountry = findViewById(R.id.edtCountry);
        edtPhone.addTextChangedListener(MaskEditUtil.mask(edtPhone, "(##) ##### - ####"));
        edtCountry.addTextChangedListener(MaskEditUtil.mask(edtCountry, "###"));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtPhone.getText().toString().length() == 17 && edtCountry.getText().toString().length() == 3 ){
                    auth(edtCountry.getText().toString(),edtPhone.getText().toString());
                }else{
                    Toast.makeText(getApplicationContext(), "Dados inválidos", Toast.LENGTH_LONG).show();
                    edtCountry.requestFocus(View.FOCUS_FORWARD);
                }

            }
        });
    }

    public Boolean isAuth(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            return true;
        }else{
            return false;
        }
    }

    public void auth(String country,String phone){
        String phoneComplete = (country+phone);
        phoneComplete = MaskEditUtil.unmask(phoneComplete);

        Intent intent = new Intent(getApplicationContext(), OTPActivity.class);
        intent.putExtra("phone", phoneComplete);
        startActivity(intent);

    }


}
