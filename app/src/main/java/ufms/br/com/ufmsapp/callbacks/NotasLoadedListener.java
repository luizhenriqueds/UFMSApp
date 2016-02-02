package ufms.br.com.ufmsapp.callbacks;


import java.util.ArrayList;

import ufms.br.com.ufmsapp.pojo.Nota;

public interface NotasLoadedListener {

    void onNotasLoaded(ArrayList<Nota> listNotas);
}
