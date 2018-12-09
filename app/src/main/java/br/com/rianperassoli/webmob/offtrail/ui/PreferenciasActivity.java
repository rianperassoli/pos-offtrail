package br.com.rianperassoli.webmob.offtrail.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;
import org.androidannotations.annotations.sharedpreferences.Pref;

import br.com.rianperassoli.webmob.offtrail.R;

@EActivity(R.layout.activity_preferencias)
@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
public class PreferenciasActivity extends AppCompatActivity {

    @Pref
    Configuracao_ configuracao;

    @ViewById(R.id.edtPreferenciasBoasVindas)
    EditText edtBoasVindas;

    @AfterViews
    public void inicializar(){
        edtBoasVindas.setText(configuracao.mensagemBoasVindas().get());
    }

    public void cancelar(View v){

        finish();
    }

    public void salvar(View v){

        if (edtBoasVindas.getText().toString().trim() != ""){
            configuracao.edit().mensagemBoasVindas().put(edtBoasVindas.getText().toString()).apply();
        }

        finish();
    }

}
