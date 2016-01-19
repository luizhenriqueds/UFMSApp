package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.callbacks.TipoEventoLoadedListener;
import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.TipoEvento;

public class TaskLoadTipoEventos extends AsyncTask<Void, Integer, ArrayList<TipoEvento>> {

    // private TipoEventoLoadedListener myComponent;
    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    public TaskLoadTipoEventos() {
        //this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<TipoEvento> doInBackground(Void... params) {

        return LoadDataUtils.loadTipoEventos(requestQueue);
    }

    @Override
    protected void onPostExecute(ArrayList<TipoEvento> listTipoEventos) {
        //if (myComponent != null) {
        // myComponent.onTipoEventoLoaded(listTipoEventos);
        //}
    }
}
