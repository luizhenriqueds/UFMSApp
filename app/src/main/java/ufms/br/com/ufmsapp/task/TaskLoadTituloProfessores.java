package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.TituloProfessor;

public class TaskLoadTituloProfessores extends AsyncTask<Void, Integer, ArrayList<TituloProfessor>> {

    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadTituloProfessores() {
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<TituloProfessor> doInBackground(Void... params) {

        return LoadDataUtils.loadTituloProfessor(requestQueue);
    }


}
