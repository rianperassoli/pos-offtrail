package br.com.rianperassoli.webmob.offtrail.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.VectorDrawable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.ViewById;

import java.io.ByteArrayOutputStream;
import java.sql.SQLException;
import java.util.Date;

import br.com.rianperassoli.webmob.offtrail.R;
import br.com.rianperassoli.webmob.offtrail.helper.DatabaseHelper;
import br.com.rianperassoli.webmob.offtrail.model.Grupo;
import br.com.rianperassoli.webmob.offtrail.model.GrupoTrilheiro;
import br.com.rianperassoli.webmob.offtrail.model.Moto;
import br.com.rianperassoli.webmob.offtrail.model.Trilheiro;

@EActivity(R.layout.activity_trilheiro)
public class TrilheiroActivity extends AppCompatActivity {

    @ViewById(R.id.imvFoto)
    ImageView imvFoto;

    @ViewById(R.id.edtTrilheiroNome)
    EditText edtNome;

    @ViewById(R.id.edtTrilheiroIdade)
    EditText edtIdade;

    @ViewById(R.id.spTrilheiroMoto)
    Spinner spMotos;

    @ViewById(R.id.spTrilheiroGrupo)
    Spinner spGrupos;

    Trilheiro trilheiro;

    @Bean
    DatabaseHelper db;

    @AfterViews
    public void inicializar(){

        this.trilheiro = (Trilheiro) this.getIntent().getSerializableExtra("trilheiro");

        try {

            ArrayAdapter<Moto> motos  = new ArrayAdapter<Moto>(this, android.R.layout.simple_spinner_item, db.getMotoDao().queryForAll());

            ArrayAdapter<Grupo> grupos = new ArrayAdapter<Grupo>(this, android.R.layout.simple_spinner_item, db.getGrupoDao().queryForAll());

            spMotos.setAdapter(motos);
            spGrupos.setAdapter(grupos);

            if (this.trilheiro != null) {
                edtNome.setText(trilheiro.getNome());
                edtIdade.setText(trilheiro.getIda().toString());

                for (int i = 0; i < spMotos.getCount(); i++){
                    if (spMotos.getItemAtPosition(i).toString().equals(trilheiro.getMoto().toString())) {
                        spMotos.setSelection(i);
                    }
                }

                Grupo grupo =  db.findGrupoDoTrilheiro(trilheiro.getCodigo());

                for (int i = 0; i < spGrupos.getCount(); i++){
                    if (spGrupos.getItemAtPosition(i).toString().equals(grupo.toString())) {
                        spGrupos.setSelection(i);
                    }
                }

                imvFoto.setImageBitmap(BitmapFactory.decodeByteArray(trilheiro.getFoto(), 0, trilheiro.getFoto().length));
            }




        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void salvar(View v){

        if (trilheiro == null) {
            trilheiro = new Trilheiro();
        }

        trilheiro.setNome(edtNome.getText().toString());
        trilheiro.setIda(Integer.parseInt(edtIdade.getText().toString()));
        trilheiro.setMoto((Moto)spMotos.getSelectedItem());
        trilheiro.setFoto(getFotoTrilheiroTela());

        try {
            if (trilheiro.getCodigo() == null) {
                db.getTrilheiroDao().create(trilheiro);
            } else {
                db.getTrilheiroDao().update(trilheiro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        db.deleteGrupoDoTrilheiro(trilheiro.getCodigo());

        GrupoTrilheiro grupoTrilheiro = new GrupoTrilheiro();

        grupoTrilheiro.setTrilheiro(trilheiro);
        grupoTrilheiro.setGrupo((Grupo) spGrupos.getSelectedItem());
        grupoTrilheiro.setDataCadastro(new Date());

        try {
            db.getGrupoTrilheiroDao().create(grupoTrilheiro);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        finish();
    }

    @NonNull
    private byte[] getFotoTrilheiroTela() {

        if (imvFoto.getDrawable() instanceof VectorDrawable) {
            return null;
        }

        Bitmap bitmap = ((BitmapDrawable) imvFoto.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        return baos.toByteArray();
    }


    public void cancelar(View v){

        finish();
    }

    @LongClick(R.id.imvFoto)
    public void capturarFoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 100);
        }
    }

    @OnActivityResult(100)
    void onResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imvFoto.setImageBitmap(imageBitmap);
        }
    }
}
