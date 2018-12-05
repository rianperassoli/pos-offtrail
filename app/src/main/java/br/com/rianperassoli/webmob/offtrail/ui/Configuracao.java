package br.com.rianperassoli.webmob.offtrail.ui;

import android.graphics.Color;

import org.androidannotations.annotations.sharedpreferences.DefaultInt;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

@SharedPref
public interface Configuracao {

    // The field name will have default value "John"
    @DefaultInt(Color.LTGRAY)
    int cor();

    @DefaultString("valor padrão")
    String parametro();
}