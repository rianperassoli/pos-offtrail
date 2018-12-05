package br.com.rianperassoli.webmob.offtrail.ui;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;

import br.com.rianperassoli.webmob.offtrail.R;
import br.com.rianperassoli.webmob.offtrail.helper.DatabaseHelper;
import br.com.rianperassoli.webmob.offtrail.model.Trilheiro;



@EViewGroup(R.layout.lista_trilheiros)
public class TrilheiroItemView extends LinearLayout {

    @ViewById
    TextView txtNome;

    @ViewById
    TextView txtMoto;

    @ViewById
    ImageView imvFoto;

    Trilheiro trilheiro;

    @Bean
    DatabaseHelper db;


    public TrilheiroItemView(Context context) {
        super(context);
    }

    @Click(R.id.imvEditar)
    public void editar(){

        Intent telaTrilheiro = new Intent(this.getContext(), TrilheiroActivity_.class);

        telaTrilheiro.putExtra("trilheiro", trilheiro);

        this.getContext().startActivity(telaTrilheiro);
    }

    @Click(R.id.imvExcluir)
    public void excluir(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Exclusão");
        dialog.setMessage("Deseja realmente excluir o trilheiro "+ trilheiro.getNome() + "?");
        dialog.setCancelable(false);
        dialog.setNegativeButton("Não", null);
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    db.getTrilheiroDao().delete(trilheiro);
                    db.deleteGrupoDoTrilheiro(trilheiro.getCodigo());

                }  catch (SQLException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getContext(), "excluir " + trilheiro.getNome(), Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    public void bind(Trilheiro t) {
        trilheiro = t;

        txtNome.setText(t.getNome());
        txtMoto.setText(t.getMoto().getModelo() + " - " + t.getMoto().getCin());
        imvFoto.setImageBitmap(BitmapFactory.decodeByteArray(t.getFoto(), 0, t.getFoto().length));
    }

}