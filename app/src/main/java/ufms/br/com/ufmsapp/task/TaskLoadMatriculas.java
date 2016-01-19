package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.AlunoXDisciplina;

public class TaskLoadMatriculas extends AsyncTask<Void, Integer, ArrayList<AlunoXDisciplina>> {

    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    public TaskLoadMatriculas() {
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<AlunoXDisciplina> doInBackground(Void... params) {

        return LoadDataUtils.loadMatriculas(requestQueue);
    }


}
