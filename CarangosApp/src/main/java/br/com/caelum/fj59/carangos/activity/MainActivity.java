package br.com.caelum.fj59.carangos.activity;


import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.navegacao.EstadoMainActivity;
import br.com.caelum.fj59.carangos.evento.EventoPublicacoesRecebidas;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesDelegate;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesTask;

public class MainActivity extends ActionBarActivity implements BuscaMaisPublicacoesDelegate {


    private EstadoMainActivity estado;
    private static final String ESTADO_ATUAL="ESTADO_ATUAL";
    private EventoPublicacoesRecebidas receiver;
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        MyLog.i("Salvando Estado!");

        outState.putSerializable(ESTADO_ATUAL,estado);
    }

    @Override
    protected void onResume() {
        super.onResume();

        MyLog.i("Executando Estado: "+ this.estado );
        this.estado.executa(this);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        MyLog.i("Restaurando Estado!");
        this.estado = (EstadoMainActivity) savedInstanceState.getSerializable(ESTADO_ATUAL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        receiver = new EventoPublicacoesRecebidas(this);
        LocalBroadcastManager.getInstance(this);
        registerReceiver(receiver,new IntentFilter("produtos"));


        this.estado = EstadoMainActivity.INICIO;
        this.estado.executa(this);
    }

//    public void atualizaListaCom(List<Publicacao> publicacoes) {
//        this.publicacoes.clear();
//        this.publicacoes.addAll(publicacoes);
//        this.adapter.notifyDataSetChanged();
//    }

    @Override
    public void lidaComRetorno(List<Publicacao> resultado) {
        CarangosApplication application = (CarangosApplication) getApplication();
        List<Publicacao> publicacoes = application.getPublicacoes();

        publicacoes.clear();
        publicacoes.addAll(resultado);


        this.estado = EstadoMainActivity.PRIMEIRAS_PUBLICACOES_RECEBIDAS;
        this.estado.executa(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getCarangosApplication().cancela();
        Toast.makeText(this,"onStop",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);


    }

    @Override
    public void lidaComErro(Exception e) {
        e.printStackTrace();
        Toast.makeText(this,"Erro ao buscar dados", Toast.LENGTH_LONG).show();
    }

    @Override
    public CarangosApplication getCarangosApplication() {
        return (CarangosApplication) getApplication();
    }

    public void alteraEstadoEExecuta(EstadoMainActivity estado) {
        this.estado = estado;
        this.estado.executa(this);
    }

    public void buscaPublicacoes(){
        new BuscaMaisPublicacoesTask(this).execute();
    }
}
