package ufms.br.com.ufmsapp.callbacks;


import java.util.ArrayList;

import ufms.br.com.ufmsapp.pojo.TipoEvento;

public interface TipoEventoLoadedListener {

    void onTipoEventoLoaded(ArrayList<TipoEvento> listTipoEventos);
}
