package ufms.br.com.ufmsapp.callbacks;


import java.util.ArrayList;

import ufms.br.com.ufmsapp.pojo.Professor;

public interface ProfessoresLoadedListener {

    void onProfessoresLoaded(ArrayList<Professor> listProfessores);
}
