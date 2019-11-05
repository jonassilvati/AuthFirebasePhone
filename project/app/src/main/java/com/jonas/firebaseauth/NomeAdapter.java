package com.jonas.firebaseauth;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jonas.firebaseauth.model.Nome;

import java.util.List;

import static com.jonas.firebaseauth.HomeActivity.MODE;
import static com.jonas.firebaseauth.HomeActivity.MODE_UPDATE;
import static com.jonas.firebaseauth.ListNomes.ID_NOME;

public class NomeAdapter extends RecyclerView.Adapter<NomeHolder> {

    private final List<Nome> mNomes;
    private Context context;

    public NomeAdapter(List<Nome> mNomes, final Context context){
        this.mNomes = mNomes;
        this.context = context;
    }

    @NonNull
    @Override
    public NomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NomeHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nome, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final NomeHolder holder, int position) {
        final Nome nome = mNomes.get(position);
        holder.tvCod.setText("#"+nome.getCod());
        holder.tvStatus.setText(Integer.toString(nome.getStatus()));
        holder.tvNome.setText(nome.getNome());

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mostrar Nome
                Intent intentShow = new Intent(context, ShowNomeActivity.class);
                intentShow.putExtra("nome", nome.getNome());
                intentShow.putExtra("codigo", nome.getCod());
                intentShow.putExtra("status", Integer.toString(nome.getStatus()));
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
                                Intent intentShow = new Intent(context, ShowNomeActivity.class);
                                intentShow.putExtra("nome", nome.getNome());
                                intentShow.putExtra("codigo", nome.getCod());
                                intentShow.putExtra("status", Integer.toString(nome.getStatus()));
                                context.startActivity(intentShow);
                                break;
                            case R.id.edit:
                                Intent intentEdit = new Intent(context, PersistNome.class);
                                intentEdit.putExtra(MODE, MODE_UPDATE);
                                intentEdit.putExtra(ID_NOME, Integer.toString(nome.getId()));
                                context.startActivity(intentEdit);
                                break;
                            case R.id.delete:
                                new AlertDialog.Builder(context)
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setTitle("Apagar nome")
                                        .setMessage("Deseja apagar "+nome.getNome()+"?")
                                        .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(((ListNomes)context).nomeDao.deleteNome(nome)){
                                                    ((ListNomes)context).setupRecycler();
                                                }else{
                                                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                    builder.setMessage("Erro ao apagar "+nome.getNome()+"!")
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

    }

    @Override
    public int getItemCount() {
        return mNomes != null ? mNomes.size() : 0;
    }
}
