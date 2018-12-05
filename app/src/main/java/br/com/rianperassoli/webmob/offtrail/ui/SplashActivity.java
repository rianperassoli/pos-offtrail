package br.com.rianperassoli.webmob.offtrail.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.WindowFeature;

import java.sql.SQLException;

import br.com.rianperassoli.webmob.offtrail.R;
import br.com.rianperassoli.webmob.offtrail.helper.DatabaseHelper;

@EActivity(R.layout.activity_splash)
@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
public class SplashActivity extends AppCompatActivity {

    @AfterViews
    @Background(delay = 3000)
    public void abrirLogin(){

        Intent telaLogin = new Intent(SplashActivity.this, LoginActivity_.class);

        startActivity(telaLogin);

        finish();
    }
}
