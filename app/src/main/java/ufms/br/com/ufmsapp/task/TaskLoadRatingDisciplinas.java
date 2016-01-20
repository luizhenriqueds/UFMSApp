package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.RatingDisciplina;

public class TaskLoadRatingDisciplinas extends AsyncTask<Void, Integer, ArrayList<RatingDisciplina>> {

    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;


    public TaskLoadRatingDisciplinas() {
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();

    }

    @Override
    protected ArrayList<RatingDisciplina> doInBackground(Void... params) {

        return LoadDataUtils.loadRatingDisciplinas(requestQueue);
    }


}
