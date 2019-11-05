package com.jonas.firebaseauth;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class NomeHolder extends RecyclerView.ViewHolder {
    public TextView tvNome;
    public TextView tvStatus;
    public TextView tvCod;
    public ImageView ivInflaterMenu;
    public ConstraintLayout root;
    public NomeHolder(View nomeView){
        super(nomeView);
        tvNome = nomeView.findViewById(R.id.tvNome);
        tvStatus = nomeView.findViewById(R.id.tvStatus);
        tvCod = nomeView.findViewById(R.id.tvCodigo);
        ivInflaterMenu = nomeView.findViewById(R.id.ivInflaterMenu);
        root = nomeView.findViewById(R.id.item_root);
    }

}
