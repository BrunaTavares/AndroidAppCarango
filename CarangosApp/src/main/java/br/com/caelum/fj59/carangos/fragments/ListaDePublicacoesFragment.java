package br.com.caelum.fj59.carangos.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.adapter.PublicacaoAdapter;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;

/**
 * Created by erich on 9/11/13.
 */
public class ListaDePublicacoesFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private PublicacaoAdapter adapter;
    private ListView publicacoesList;
    private SwipeRefreshLayout swipe;
    private  MainActivity activity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.swipe = (SwipeRefreshLayout) inflater.inflate(R.layout.publicacoes_list, container,false);
        this.swipe.setOnRefreshListener(this);

        this.publicacoesList = (ListView) swipe.findViewById(R.id.publicacoes_list);
        this.activity = (MainActivity) this.getActivity();

        this.swipe.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);

        CarangosApplication app = activity.getCarangosApplication();
        this.adapter = new PublicacaoAdapter(getActivity(), activity.getPublicacoes());
        this.publicacoesList.setAdapter(this.adapter);

        return this.swipe;
    }

    @Override
    public void onPause() {
        super.onPause();
        this.swipe.setRefreshing(false);
        this.swipe.clearAnimation();
    }

    @Override
    public void onRefresh() {

        MyLog.i("PULL TO REFRESH INICIANDO!!!");
        activity.alteraEstadoEExecuta(EstadoMainActivity.PULL_TO_REFRESH_REQUISITADO);
    }

}
