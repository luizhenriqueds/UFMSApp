package ufms.br.com.ufmsapp.callbacks;


import java.util.ArrayList;

import ufms.br.com.ufmsapp.pojo.Disciplina;

public interface DisciplinasLoadedListener {

    void onDisciplinasLoaded(ArrayList<Disciplina> listDisciplinas);
}
