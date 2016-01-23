package br.com.caelum.fj59.carangos.timer;

import android.os.Message;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import br.com.caelum.fj59.carangos.infra.MyLog;
import br.com.caelum.fj59.carangos.tasks.CustomHandler;
import br.com.caelum.fj59.carangos.webservice.WebClient;

/**
 * Created by android5628 on 22/01/16.
 */
public class BuscaLeiloesTask extends TimerTask {
    private CustomHandler handler;
    private Calendar horarioUltimaBusca;

    public BuscaLeiloesTask(CustomHandler handler, Calendar horarioUltimaBusca) {
        this.handler = handler;
        this.horarioUltimaBusca = horarioUltimaBusca;
    }


    @Override
    public void run() {
        MyLog.i("Efetuando nova busca!");

        WebClient webClient = new WebClient("leilao/leilaoid54635/"+new SimpleDateFormat("ddMMyy-HHmmss").format(horarioUltimaBusca.getTime()));
        String json = webClient.get();

        MyLog.i("Lances recebidos:"+json);

        Message message = handler.obtainMessage();
        message.obj = json;
        handler.sendMessage(message);

        horarioUltimaBusca = Calendar.getInstance();

    }

    public void executa (){
        Timer timer = new Timer();
        timer.schedule(this,0,3000);
    }
}
