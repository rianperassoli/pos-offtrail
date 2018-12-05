package br.com.rianperassoli.webmob.offtrail.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.sql.SQLException;
import java.util.List;

import br.com.rianperassoli.webmob.offtrail.helper.DatabaseHelper;
import br.com.rianperassoli.webmob.offtrail.model.Trilheiro;
import br.com.rianperassoli.webmob.offtrail.ui.TrilheiroItemView;
import br.com.rianperassoli.webmob.offtrail.ui.TrilheiroItemView_;

@EBean
public class TrilheiroAdapter
        extends BaseAdapter {
    List<Trilheiro> trilheiros;

    @Bean
    DatabaseHelper dh;

    @RootContext
    Context context;

    @AfterInject
    void initAdapter() {
        try {
            trilheiros = dh.getTrilheiroDao().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCount() {
        return trilheiros.size();
    }

    @Override
    public Trilheiro getItem(int position) {
        return trilheiros.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TrilheiroItemView trilheiroItemView;
        if (convertView == null) {
            trilheiroItemView = TrilheiroItemView_.build(context);
        } else {
            trilheiroItemView = (TrilheiroItemView) convertView;
        }

        trilheiroItemView.bind(getItem(position));

        return trilheiroItemView;
    }
}