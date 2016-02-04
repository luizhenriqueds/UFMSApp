package ufms.br.com.ufmsapp.task;

import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Disciplina;


public class TaskLoadDisciplinasOnStart extends AsyncTask<Void, Integer, ArrayList<Disciplina>> {

    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadDisciplinasOnStart() {
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<Disciplina> doInBackground(Void... params) {

        return LoadDataUtils.loadDisciplinas(requestQueue);
    }

}
