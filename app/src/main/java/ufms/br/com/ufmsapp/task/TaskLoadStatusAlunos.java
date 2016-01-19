package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.StatusAluno;

public class TaskLoadStatusAlunos extends AsyncTask<Void, Integer, ArrayList<StatusAluno>> {

    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    public TaskLoadStatusAlunos() {
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<StatusAluno> doInBackground(Void... params) {

        return LoadDataUtils.loadStatusAluno(requestQueue);
    }

}
