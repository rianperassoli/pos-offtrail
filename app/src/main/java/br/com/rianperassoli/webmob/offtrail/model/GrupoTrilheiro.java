package br.com.rianperassoli.webmob.offtrail.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

@DatabaseTable
public class GrupoTrilheiro {

    @DatabaseField(generatedId = true)
    private Integer codigo;

    @DatabaseField(foreign = true, foreignColumnName = "codigo")
    private Trilheiro trilheiro;

    @DatabaseField(foreign = true, foreignColumnName = "codigo")
    private Grupo grupo;

    @DatabaseField
    private Date dataCadastro;

    public GrupoTrilheiro(){}

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Trilheiro getTrilheiro() {
        return trilheiro;
    }

    public void setTrilheiro(Trilheiro trilheiro) {
        this.trilheiro = trilheiro;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
}
