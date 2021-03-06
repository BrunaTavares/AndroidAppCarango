package br.com.caelum.fj59.carangos.tasks;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.fj59.carangos.app.CarangosApplication;
import br.com.caelum.fj59.carangos.modelo.Publicacao;

/**
 * Created by android5628 on 18/01/16.
 */
public interface BuscaMaisPublicacoesDelegate {

    void lidaComRetorno(ArrayList<Publicacao> resultado);
    void lidaComErro(Exception e);

    CarangosApplication getCarangosApplication();
}
