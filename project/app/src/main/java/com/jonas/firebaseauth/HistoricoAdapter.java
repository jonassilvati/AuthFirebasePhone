package com.jonas.firebaseauth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jonas.firebaseauth.dao.HistoricoDao;
import com.jonas.firebaseauth.dao.NomeDao;
import com.jonas.firebaseauth.model.Historico;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.jonas.firebaseauth.HomeActivity.MODE;
import static com.jonas.firebaseauth.HomeActivity.MODE_UPDATE;
import static com.jonas.firebaseauth.ListHistorico.ID_HISTORICO;
import static com.jonas.firebaseauth.ListNomes.ID_NOME;

public class HistoricoAdapter extends RecyclerView.Adapter<HistoricoHolder> {

    private List<Historico> mHistoricos;
    private Context context;
    private NomeDao nDao;

    public HistoricoAdapter(List<Historico> mHistoricos, Context context){
        this.mHistoricos = mHistoricos;
        this.context = context;
        nDao = new NomeDao(context);
    }

    @NonNull
    @Override
    public HistoricoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoricoHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_historico, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoricoHolder holder, int position) {
        try {
            Historico h = mHistoricos.get(position);
            String nome = nDao.getNome(h.getIdNome()).getNome();
            holder.tvHistorico.setText(h.getHistoricoDisplay());
            holder.tvNome.setText(nome);
            holder.ivShape.setColorFilter(Color.parseColor(h.getCor()));

            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentShow = new Intent(context, ShowHistoricoActivity.class);
                    intentShow.putExtra("nome", nome);
                    intentShow.putExtra("status", h.getStatus());
                    intentShow.putExtra("cor", h.getCor());
                    intentShow.putExtra("tipo", h.getTipo());
                    intentShow.putExtra("historico", h.getHistoricoDisplay());
                    context.startActivity(intentShow);
                }
            });

            holder.ivInflaterMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, holder.ivInflaterMenu);
                    popup.inflate(R.menu.nome_item_menu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()){
                                case R.id.show:
                                    //mostrar Nome
                                    Intent intentShow = new Intent(context, ShowHistoricoActivity.class);
                                    intentShow.putExtra("nome", nome);
                                    intentShow.putExtra("status", h.getStatus());
                                    intentShow.putExtra("cor", h.getCor());
                                    intentShow.putExtra("tipo", h.getTipo());
                                    intentShow.putExtra("historico", h.getHistoricoDisplay());
                                    context.startActivity(intentShow);
                                    break;
                                case R.id.edit:
                                    Intent intentEdit = new Intent(context, PersistHistorico.class);
                                    intentEdit.putExtra(MODE, MODE_UPDATE);
                                    intentEdit.putExtra(ID_HISTORICO, h.getId());
                                    context.startActivity(intentEdit);
                                    break;
                                case R.id.delete:
                                    new AlertDialog.Builder(context)
                                            .setIcon(android.R.drawable.ic_dialog_alert)
                                            .setTitle("Apagar historico")
                                            .setMessage("Deseja apagar o historico de "+nome+"?")
                                            .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    if(((ListHistorico)context).historicoDao.deleteHistorico(h)){
                                                        ((ListHistorico)context).setupRecycler();
                                                    }else{
                                                        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                        builder.setMessage("Erro ao apagar historico de "+nome+"!")
                                                                .setCancelable(false)
                                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                    public void onClick(DialogInterface dialog, int id) {
                                                                        dialog.cancel();                        }
                                                                });
                                                        AlertDialog alert = builder.create();
                                                        alert.show();
                                                    }

                                                }

                                            })
                                            .setNegativeButton("Não", null)
                                            .show();
                                    break;
                            }
                            return false;
                        }
                    });

                    popup.show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mHistoricos != null ? mHistoricos.size() : 0;
    }
}
