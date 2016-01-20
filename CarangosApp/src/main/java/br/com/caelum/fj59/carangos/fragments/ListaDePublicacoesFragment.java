package br.com.caelum.fj59.carangos.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import br.com.caelum.fj59.carangos.R;
import br.com.caelum.fj59.carangos.activity.MainActivity;
import br.com.caelum.fj59.carangos.adapter.PublicacaoAdapter;
import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.modelo.Publicacao;

/**
 * Created by erich on 9/11/13.
 */
public class ListaDePublicacoesFragment extends Fragment {

    private PublicacaoAdapter adapter;
    private ListView publicacoesList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.publicacoesList = (ListView) inflater.inflate(R.layout.publicacoes_list, container, false);

        MainActivity activity = (MainActivity) this.getActivity();

        CarangosApplication app = activity.getCarangosApplication();
        this.adapter = new PublicacaoAdapter(getActivity(),app.getPublicacoes());
        this.publicacoesList.setAdapter(this.adapter);

        return this.publicacoesList;
    }

}
