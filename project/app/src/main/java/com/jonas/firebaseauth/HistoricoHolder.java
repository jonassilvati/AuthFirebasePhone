package com.jonas.firebaseauth;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class HistoricoHolder extends RecyclerView.ViewHolder {

    public TextView tvNome;
    public TextView tvHistorico;
    public ImageView ivInflaterMenu;
    public ImageView ivShape;
    public ConstraintLayout root;

    public HistoricoHolder(@NonNull View itemView) {
        super(itemView);
        tvNome = itemView.findViewById(R.id.tvNome);
        tvHistorico = itemView.findViewById(R.id.tvHistorico);
        ivInflaterMenu = itemView.findViewById(R.id.ivInflaterMenu);
        ivShape = itemView.findViewById(R.id.ivShapeCor);
        root = itemView.findViewById(R.id.root);
    }

}
