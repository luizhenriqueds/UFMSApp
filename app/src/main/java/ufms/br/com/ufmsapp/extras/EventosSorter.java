package ufms.br.com.ufmsapp.extras;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ufms.br.com.ufmsapp.pojo.Evento;

public class EventosSorter {

    public static void sortEventosByName(List<Evento> eventos) {

        Collections.sort(eventos, new Comparator<Evento>() {
            @Override
            public int compare(Evento lhs, Evento rhs) {
                return lhs.getTitulo().compareTo(rhs.getTitulo());
            }
        });

    }
}
