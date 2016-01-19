package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Aluno;

public class TaskLoadAlunos extends AsyncTask<Void, Integer, ArrayList<Aluno>> {

    //private DisciplinasLoadedListener myComponent;
    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    // protected ProgressBar listDisciplinasProgressBar;
    // protected boolean isRefreshing;
    // protected TextView noResultsText;
    // protected ImageView noResultsImage;

   /* public TaskLoadAlunos(DisciplinasLoadedListener myComponent, ProgressBar listDisciplinasProgressBar, boolean isRefreshing, TextView noResultsText, ImageView noResultsImage) {
        //this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
       // this.listDisciplinasProgressBar = listDisciplinasProgressBar;
       // this.isRefreshing = isRefreshing;
        //this.noResultsText = noResultsText;
        //this.noResultsImage = noResultsImage;
    }
*/

    public TaskLoadAlunos() {
        //this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        // this.listDisciplinasProgressBar = listDisciplinasProgressBar;
        // this.isRefreshing = isRefreshing;
        //this.noResultsText = noResultsText;
        //this.noResultsImage = noResultsImage;
    }

    @Override
    protected ArrayList<Aluno> doInBackground(Void... params) {

        return LoadDataUtils.loadAlunos(requestQueue);
    }


}
