package br.com.caelum.fj59.carangos.navegacao;

import android.app.Fragment;
import android.app.FragmentTransaction;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.fragments.ListaDePublicacoesFragment;
import br.com.caelum.fj59.carangos.fragments.ProgressFragment;

/**
 * Created by android5628 on 19/01/16.
 */
public enum EstadoMainActivity {
    INICIO {
        @Override
        public void executa(MainActivity activity) {
            activity.buscaPublicacoes();
            activity.alteraEstadoEExecuta(EstadoMainActivity.AGUARDANDO_PUBLICACOES);

        }
    }, AGUARDANDO_PUBLICACOES {
        @Override
        public void executa(MainActivity activity) {
            ProgressFragment progress = ProgressFragment.comMensagem(R.string.carregando);
            this.colocaFragmentNaTela(activity,progress);
        }
    }, PRIMEIRAS_PUBLICACOES_RECEBIDAS {
        @Override
        public void executa(MainActivity activity) {
            ListaDePublicacoesFragment publicacoesFragment = new ListaDePublicacoesFragment();
            this.colocaFragmentNaTela(activity,publicacoesFragment);
        }
    };

    void colocaFragmentNaTela(MainActivity activity, Fragment fragment){
        //Com support para versoes anterioores a API 21
        //android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
        //android.support.v4.app.FragmentTransaction tx = fm.beginTransaction();

        FragmentTransaction tx = activity.getFragmentManager().beginTransaction();
        tx.replace(R.id.fragment_principal,fragment);
        tx.commit();
    }

    public abstract void executa(MainActivity activity);


}
