package br.com.rianperassoli.webmob.offtrail.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import org.androidannotations.annotations.EBean;

import java.sql.SQLException;
import java.util.List;

import br.com.rianperassoli.webmob.offtrail.model.Cidade;
import br.com.rianperassoli.webmob.offtrail.model.Grupo;
import br.com.rianperassoli.webmob.offtrail.model.GrupoTrilheiro;
import br.com.rianperassoli.webmob.offtrail.model.Moto;
import br.com.rianperassoli.webmob.offtrail.model.Trilheiro;
import br.com.rianperassoli.webmob.offtrail.model.Usuario;


/**
 * Database helper class used to manage the creation and upgrading of your database. This class also usually provides
 * the DAOs used by the other classes.
 */
@EBean
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // name of the database file for your application -- change to something appropriate for your app
    private static final String DATABASE_NAME = "offtrail.db";
    // any time you make changes to your database objects, you may have to increase the database version
    private static final int DATABASE_VERSION = 1;

    // the DAO object we use to access the SimpleData table
    private Dao<Cidade, Integer> cidadeDao = null;
    private Dao<Usuario, Integer> usuarioDao = null;
    private Dao<Grupo, Integer> grupoDao = null;
    private Dao<Trilheiro, Integer> trilheiroDao = null;
    private Dao<Moto, Integer> motoDao = null;
    private Dao<GrupoTrilheiro, Integer> grupoTrilheiroDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is first created. Usually you should call createTable statements here to create
     * the tables that will store your data.
     */
    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");

            TableUtils.createTable(connectionSource, Cidade.class);
            TableUtils.createTable(connectionSource, Grupo.class);
            TableUtils.createTable(connectionSource, Moto.class);
            TableUtils.createTable(connectionSource, Usuario.class);
            TableUtils.createTable(connectionSource, Trilheiro.class);
            TableUtils.createTable(connectionSource, GrupoTrilheiro.class);

            //insert usuario
            Usuario u = new Usuario();
            u.setEmail("rian");
            u.setSenha("rian");

            getUsuarioDao().create(u);

            //insert cidade
            Cidade cidade = new Cidade();
            cidade.setNome("São Miguel do Oeste");

            getCidadeDao().create(cidade);

            //insert grupos
            Grupo g1 = new Grupo();
            Grupo g2 = new Grupo();

            g1.setCidade(cidade);
            g1.setNome("Tapa na pantera");

            g2.setCidade(cidade);
            g2.setNome("Acelera");

            getGrupoDao().create(g1);
            getGrupoDao().create(g2);


            //insert moto
            Moto moto1 = new Moto();
            Moto moto2 = new Moto();

            moto1.setCin("200cc");
            moto1.setCor("Vermelha");
            moto1.setMarca("Honda");
            moto1.setModelo("CRF");

            moto2.setCin("100cc");
            moto2.setCor("Preta");
            moto2.setMarca("Sundown");
            moto2.setModelo("Web");

            getMotoDao().create(moto1);
            getMotoDao().create(moto2);

        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }


        Log.i(DatabaseHelper.class.getName(), "created new entries in onCreate!");
    }

    /**
     * This is called when your application is upgraded and it has a higher version number. This allows you to adjust
     * the various data to match the new version number.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.dropTable(connectionSource, Cidade.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Cidade, Integer> getCidadeDao() throws SQLException {
        if (cidadeDao == null) {
            cidadeDao = getDao(Cidade.class);
        }
        return cidadeDao;
    }

    public Dao<Grupo, Integer> getGrupoDao() throws SQLException {
        if (grupoDao == null) {
            grupoDao = getDao(Grupo.class);
        }
        return grupoDao;
    }

    public Dao<Trilheiro, Integer> getTrilheiroDao() throws SQLException {
        if (trilheiroDao == null) {
            trilheiroDao = getDao(Trilheiro.class);
        }
        return trilheiroDao;
    }

    public Dao<Usuario, Integer> getUsuarioDao() throws SQLException {
        if (usuarioDao == null) {
            usuarioDao = getDao(Usuario.class);
        }
        return usuarioDao;
    }

    public Dao<Moto, Integer> getMotoDao() throws SQLException {
        if (motoDao == null) {
            motoDao = getDao(Moto.class);
        }
        return motoDao;
    }

    public Dao<GrupoTrilheiro, Integer> getGrupoTrilheiroDao() throws SQLException {
        if (grupoTrilheiroDao == null) {
            grupoTrilheiroDao = getDao(GrupoTrilheiro.class);
        }
        return grupoTrilheiroDao;
    }

    @Override
    public void close() {
        super.close();

        cidadeDao = null;
        grupoDao = null;
        grupoTrilheiroDao = null;
        motoDao = null;
        trilheiroDao = null;
        usuarioDao = null;
    }

    public Usuario validaLogin(String email, String senha){

        List<Usuario> usuarios = null;

        try {

            usuarios = getUsuarioDao().queryBuilder()
                    .where().eq("email", email).and().eq("senha", senha).query();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (usuarios != null && usuarios.size() > 0) {
            return usuarios.get(0);
        }

        return null;
    }

    public Grupo findGrupoDoTrilheiro(int codigoTrilheiro){

        GrupoTrilheiro grupoTrilheiro = null;
        Grupo grupo = null;

        try {

            grupoTrilheiro = this.findGrupoTrilheiro(codigoTrilheiro).get(0);

            if (grupoTrilheiro != null) {
                grupo = getGrupoDao().queryBuilder().where().eq("codigo", grupoTrilheiro.getGrupo().getCodigo()).query().get(0);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (grupo != null) {
            return grupo;
        }

        return null;
    }

    public void deleteGrupoDoTrilheiro(int codigoTrilheiro){

        try {

           List<GrupoTrilheiro> gruposTrilheiro = this.findGrupoTrilheiro(codigoTrilheiro);

            for (GrupoTrilheiro grupoTrilheiro: gruposTrilheiro){
                grupoTrilheiroDao.delete(grupoTrilheiro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<GrupoTrilheiro> findGrupoTrilheiro(int codigoTrilheiro){
        try {

            return getGrupoTrilheiroDao().queryBuilder().where().eq("trilheiro_codigo", codigoTrilheiro).query();

        } catch (SQLException e) {
            e.printStackTrace();

            return  null;
        }
    }
}