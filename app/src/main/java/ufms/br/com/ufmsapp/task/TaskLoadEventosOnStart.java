package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.callbacks.EventosLoadedListener;
import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Evento;

public class TaskLoadEventosOnStart extends AsyncTask<Void, Integer, ArrayList<Evento>> {

    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadEventosOnStart() {
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

    }


    @Override
    protected ArrayList<Evento> doInBackground(Void... params) {
        return LoadDataUtils.loadEventos(requestQueue);
    }

}
