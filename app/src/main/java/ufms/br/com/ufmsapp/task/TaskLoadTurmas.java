package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Turma;

public class TaskLoadTurmas extends AsyncTask<Void, Integer, ArrayList<Turma>> {

    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    public TaskLoadTurmas() {
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<Turma> doInBackground(Void... params) {

        return LoadDataUtils.loadTurmas(requestQueue);
    }

}
