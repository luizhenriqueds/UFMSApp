package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.StatusDisciplina;

public class TaskLoadStatusDisciplina extends AsyncTask<Void, Integer, ArrayList<StatusDisciplina>> {

    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    public TaskLoadStatusDisciplina() {
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<StatusDisciplina> doInBackground(Void... params) {

        return LoadDataUtils.loadStatusDisciplinas(requestQueue);
    }


}
