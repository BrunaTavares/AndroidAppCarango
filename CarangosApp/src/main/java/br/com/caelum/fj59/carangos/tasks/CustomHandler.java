package br.com.caelum.fj59.carangos.tasks;

import android.os.Message;
import android.widget.ArrayAdapter;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.logging.Handler;

import br.com.caelum.fj59.carangos.converter.LanceConverter;
import br.com.caelum.fj59.carangos.modelo.Lance;

/**
 * Created by android5628 on 22/01/16.
 */
public class CustomHandler extends android.os.Handler {

    ArrayAdapter<Lance> adapter;
    List<Lance> lancesAteMomento;

    public CustomHandler(ArrayAdapter<Lance> adapter, List<Lance> lancesAteMomento) {
        this.adapter = adapter;
        this.lancesAteMomento = lancesAteMomento;
    }

    @Override
    public void handleMessage(Message msg) {
        String json = (String) msg.obj;
        List<Lance> novosLances = new LanceConverter().converte(json);

        lancesAteMomento.addAll(novosLances);
        adapter.notifyDataSetChanged();

        Collections.sort(lancesAteMomento);


    }
}
