package br.com.caelum.fj59.carangos.gcm;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.List;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.LeilaoActivity;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.infra.MyLog;

/**
 * Created by android5628 on 21/01/16.
 */
public class GCMBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String mensagem = (String) intent.getExtras().getSerializable("message");

        if (appEstaRodando(context)){
            Toast.makeText(context,"Um novo leilao começou",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"Fora do App",Toast.LENGTH_LONG).show();
        }
        Intent irParaLeilao = new Intent(context, LeilaoActivity.class);
        // 0  request code id da pIntent flag reaproveitar
        PendingIntent acaoPendente = PendingIntent.getActivity(context,0,irParaLeilao,PendingIntent.FLAG_CANCEL_CURRENT);
        Notification notificacao = new Notification.Builder(context)
                .setContentTitle("Um novo leilao começou")
                .setContentText("Leilão: " + mensagem)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentIntent(acaoPendente)
                .setLights(0xFF00FF00, 300, 100)
                .setAutoCancel(true)
                .build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(Constantes.ID_NOTIFICACAO,notificacao);

        Toast.makeText(context,"Chegou a mensagem do GCM!",Toast.LENGTH_LONG).show();
        MyLog.i("Chegou a mensagem do GCM!");
    }

    private boolean appEstaRodando(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if(!tasks.isEmpty()){
            ComponentName topActivity = tasks.get(0).topActivity;
            if(!topActivity.getPackageName().equals(context.getPackageName())){
                return false;
            }
        }
        return true;
    }
}
