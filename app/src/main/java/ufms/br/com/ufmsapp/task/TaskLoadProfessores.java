package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Professor;

public class TaskLoadProfessores extends AsyncTask<Void, Integer, ArrayList<Professor>> {

    //private ProfessoresLoadedListener myComponent;
    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadProfessores() {
        //this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<Professor> doInBackground(Void... params) {

        return LoadDataUtils.loadProfessores(requestQueue);
    }


}
