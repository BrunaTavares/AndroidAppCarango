package br.com.caelum.fj59.carangos.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.adapter.PublicacaoAdapter;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesDelegate;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesTask;

public class MainActivity extends ActionBarActivity implements BuscaMaisPublicacoesDelegate {

    private ListView listView;
    private List<Publicacao> publicacoes;
    private PublicacaoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publicacoes_list);

        this.listView = (ListView) findViewById(R.id.publicacoes_list);
        this.publicacoes = new ArrayList<Publicacao>();
        this.adapter = new PublicacaoAdapter(this, this.publicacoes);

        this.listView.setAdapter(adapter);

        new BuscaMaisPublicacoesTask(this).execute();
    }

//    public void atualizaListaCom(List<Publicacao> publicacoes) {
//        this.publicacoes.clear();
//        this.publicacoes.addAll(publicacoes);
//        this.adapter.notifyDataSetChanged();
//    }

    public List<Publicacao> getPublicacoes() {
        return this.publicacoes;
    }

    @Override
    public void lidaComRetorno(List<Publicacao> retorno) {
        this.publicacoes.clear();
        this.publicacoes.addAll(retorno);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void lidaComErro(Exception e) {
        e.printStackTrace();
        Toast.makeText(this,"Erro ao buscar dados", Toast.LENGTH_LONG).show();
    }
}
