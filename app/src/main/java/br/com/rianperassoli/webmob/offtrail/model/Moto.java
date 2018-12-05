package br.com.rianperassoli.webmob.offtrail.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Moto implements Serializable {

    @DatabaseField(generatedId = true)
    private Integer codigo;

    @DatabaseField(canBeNull = false)
    private String modelo;

    @DatabaseField
    private String marca;

    @DatabaseField
    private String cin;

    @DatabaseField
    private String cor;

    public Moto(){}

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    @Override
    public String toString() {
        return "Moto " + marca + " " + modelo + " " + cin + " - " + cor;
    }
}
