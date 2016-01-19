package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.callbacks.EventosLoadedListener;
import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Evento;

public class TaskLoadEventos extends AsyncTask<Void, Integer, ArrayList<Evento>> {

    private EventosLoadedListener myComponent;
    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    protected ProgressBar listEventosProgressBar;
    protected boolean isRefreshing;
    protected TextView noResultsText;
    protected ImageView noResultsImage;

    public TaskLoadEventos(EventosLoadedListener myComponent, ProgressBar listEventosProgressBar, boolean isRefreshing, TextView noResultsText, ImageView noResultsImage) {
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        this.listEventosProgressBar = listEventosProgressBar;
        this.isRefreshing = isRefreshing;
        this.noResultsText = noResultsText;
        this.noResultsImage = noResultsImage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        noResultsText.setVisibility(View.GONE);
        noResultsImage.setVisibility(View.GONE);


        if (isRefreshing) {
            listEventosProgressBar.setVisibility(View.GONE);
        } else {
            listEventosProgressBar.setVisibility(View.VISIBLE);
            listEventosProgressBar.setIndeterminate(true);
        }

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        int currentProgress = values[0];
        listEventosProgressBar.setProgress(currentProgress);
    }

    @Override
    protected ArrayList<Evento> doInBackground(Void... params) {

        return LoadDataUtils.loadEventos(requestQueue);
    }

    @Override
    protected void onPostExecute(ArrayList<Evento> listEventos) {
        if (myComponent != null) {
            listEventosProgressBar.setVisibility(View.GONE);
            myComponent.onEventosLoaded(listEventos);
        }
    }
}
