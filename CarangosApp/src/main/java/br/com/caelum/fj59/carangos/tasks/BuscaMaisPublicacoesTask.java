package br.com.caelum.fj59.carangos.tasks;

import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.converter.PublicacaoConverter;
import br.com.caelum.fj59.carangos.evento.EventoPublicacoesRecebidas;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.webservice.Pagina;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by erich on 7/16/13.
 */
public class BuscaMaisPublicacoesTask extends AsyncTask<Pagina, Void, List<Publicacao>> {

    private CarangosApplication application;
    private Exception erro;
    private MainActivity activity;
    private BuscaMaisPublicacoesDelegate delegate;
    private Application context;

    public BuscaMaisPublicacoesTask(CarangosApplication application) {
        this.application = application;
    }


    @Override
    protected List<Publicacao> doInBackground(Pagina... paginas) {
        try {
            Thread.sleep(2000);
            Pagina paginaParaBuscar = paginas.length > 1? paginas[0] : new Pagina();

            String jsonDeResposta = new WebClient("post/list?" + paginaParaBuscar).get();

            List<Publicacao> publicacoesRecebidas = new PublicacaoConverter().converte(jsonDeResposta);

            return publicacoesRecebidas;
        } catch (Exception e) {
            this.erro = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Publicacao> produtos) {
        MyLog.i("RETORNO OBTIDO!" + produtos);
        LocalBroadcastManager bm = LocalBroadcastManager.getInstance(context);

        if (produtos!=null) {
            EventoPublicacoesRecebidas.notifica(this.application,(Serializable) produtos, true);
        } else {
            EventoPublicacoesRecebidas.notifica(this.application,erro,false);
            Toast.makeText(this.activity, "Erro na busca dos dados", Toast.LENGTH_SHORT).show();
        }
        this.application.desregistra(this);

    }
}
