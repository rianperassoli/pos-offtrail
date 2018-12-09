package br.com.rianperassoli.webmob.offtrail.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.WindowFeature;

import br.com.rianperassoli.webmob.offtrail.R;
import br.com.rianperassoli.webmob.offtrail.helper.DatabaseHelper;
import br.com.rianperassoli.webmob.offtrail.model.Usuario;

@EActivity(R.layout.activity_login)
@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
public class LoginActivity extends AppCompatActivity {

    @Bean
    DatabaseHelper db;

    @ViewById(R.id.edtLogin)
    EditText edtLogin;

    @ViewById(R.id.edtPassword)
    EditText edtPassword;

    public void entrar(View v){

        String login = edtLogin.getText().toString();
        String senha = edtPassword.getText().toString();

        if (!login.trim().isEmpty() && !senha.trim().isEmpty()){

            Usuario usuario = db.validaLogin(login, senha);

            if (usuario != null) {
                Intent telaMain = new Intent(this, MainActivity_.class);

                telaMain.putExtra("usuario", usuario); //envia para a tela atraves de um hash

                startActivity(telaMain);

                finish();
            } else {
                mostrarMensagemErroNoLogin("Login Inválido");
            }

        } else {
            mostrarMensagemErroNoLogin("Informe o usuário e senha");
        }

    }

    public void mostrarMensagemErroNoLogin(String mensagem){
        Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show();
        edtLogin.setText("");
        edtPassword.setText("");
        edtLogin.requestFocus();
    }

    public void sair(View v){

        finish();

        System.exit(0);
    }


}
