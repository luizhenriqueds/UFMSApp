package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.TipoDisciplina;

public class TaskLoadTipoDisciplina extends AsyncTask<Void, Integer, ArrayList<TipoDisciplina>> {


    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    public TaskLoadTipoDisciplina() {

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

    }


    @Override
    protected ArrayList<TipoDisciplina> doInBackground(Void... params) {

        return LoadDataUtils.loadTipoDisciplinas(requestQueue);
    }


}
