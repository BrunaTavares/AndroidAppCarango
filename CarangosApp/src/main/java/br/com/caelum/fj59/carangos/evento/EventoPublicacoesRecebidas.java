package br.com.caelum.fj59.carangos.evento;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import java.io.Serializable;
import java.util.List;
import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.modelo.Publicacao;
import br.com.caelum.fj59.carangos.tasks.BuscaMaisPublicacoesDelegate;

/**
 * Created by android5628 on 19/01/16.
 */
public class EventoPublicacoesRecebidas extends BroadcastReceiver {

    private BuscaMaisPublicacoesDelegate delegate;
    private static final String RETORNO = "retorno";
    private static final String SUCESSO = "sucesso";
    private static final String PUBLICACOES_RECEBIDAS = "publicacoes recebidas";


    public static EventoPublicacoesRecebidas(BuscaMaisPublicacoesDelegate delegate) {
        EventoPublicacoesRecebidas receiver = new EventoPublicacoesRecebidas();
        receiver.delegate = delegate;

        LocalBroadcastManager
                .getInstance(delegate.getCarangosApplication())
                .registerReceiver(receiver,new IntentFilter(PUBLICACOES_RECEBIDAS));
        return receiver;
    }

    public void onReceive(Context context, Intent intent){
        boolean deuCerto = intent.getBooleanExtra(SUCESSO,false);
        MyLog.i("Recebi o Evento!!! Deu Certo?" + deuCerto);

        if(deuCerto){
            delegate.lidaComRetorno((List<Publicacao>) intent.getSerializableExtra(RETORNO));
        }else{
            delegate.lidaComErro((Exception) intent.getSerializableExtra(RETORNO));
        }
    }
    public static void notifica(Context context, Serializable resultado, boolean sucesso){
        
    }

}
