package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import ufms.br.com.ufmsapp.callbacks.DisciplinasLoadedListener;
import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.fragment.NotasFragment;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Disciplina;

public class TaskLoadDisciplinas extends AsyncTask<Void, Integer, ArrayList<Disciplina>> {

    private DisciplinasLoadedListener myComponent;
    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    protected ProgressBar listDisciplinasProgressBar;
    protected boolean isRefreshing;
    protected TextView noResultsText;
    protected ImageView noResultsImage;

    public TaskLoadDisciplinas(DisciplinasLoadedListener myComponent, ProgressBar listDisciplinasProgressBar, boolean isRefreshing, TextView noResultsText, ImageView noResultsImage) {
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        this.listDisciplinasProgressBar = listDisciplinasProgressBar;
        this.isRefreshing = isRefreshing;
        this.noResultsText = noResultsText;
        this.noResultsImage = noResultsImage;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (noResultsText != null && noResultsImage != null) {
            noResultsText.setVisibility(View.GONE);
            noResultsImage.setVisibility(View.GONE);
        }

        if (listDisciplinasProgressBar != null) {
            if (isRefreshing) {
                listDisciplinasProgressBar.setVisibility(View.GONE);
            } else {
                listDisciplinasProgressBar.setVisibility(View.VISIBLE);
                listDisciplinasProgressBar.setIndeterminate(true);
            }
        }


    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        int currentProgress = values[0];
        if (listDisciplinasProgressBar != null) {
            listDisciplinasProgressBar.setProgress(currentProgress);
        }

    }

    @Override
    protected ArrayList<Disciplina> doInBackground(Void... params) {

        return LoadDataUtils.loadDisciplinas(requestQueue);
    }

    @Override
    protected void onPostExecute(ArrayList<Disciplina> listDisciplinas) {
        if (myComponent != null) {
            listDisciplinasProgressBar.setVisibility(View.GONE);
            myComponent.onDisciplinasLoaded(listDisciplinas);
        }
    }
}
