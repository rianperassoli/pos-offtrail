package br.com.rianperassoli.webmob.offtrail.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Fullscreen;
import org.androidannotations.annotations.WindowFeature;

import br.com.rianperassoli.webmob.offtrail.R;

@EActivity(R.layout.activity_about)
@Fullscreen
@WindowFeature(Window.FEATURE_NO_TITLE)
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void voltar(View v){

        finish();
    }
}
