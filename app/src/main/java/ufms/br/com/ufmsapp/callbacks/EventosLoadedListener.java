package ufms.br.com.ufmsapp.callbacks;


import java.util.ArrayList;

import ufms.br.com.ufmsapp.pojo.Evento;

public interface EventosLoadedListener {

    void onEventosLoaded(ArrayList<Evento> listEventos);
}
