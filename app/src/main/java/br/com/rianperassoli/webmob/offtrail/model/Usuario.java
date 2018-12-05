package br.com.rianperassoli.webmob.offtrail.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

@DatabaseTable
public class Usuario implements Serializable {

    @DatabaseField(generatedId = true)
    private Integer codigo;

    @DatabaseField(canBeNull = false)
    private String email;

    @DatabaseField(canBeNull = false)
    private String senha;

    public Usuario(){}

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
