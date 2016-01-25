package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;

import ufms.br.com.ufmsapp.JSONParsers.RatingDisciplinaParser;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.RatingDisciplina;

public class TaskRateDisciplina extends AsyncTask<RatingDisciplina, Integer, Integer> {

    protected VolleySingleton volleySingleton;


    public TaskRateDisciplina() {
        volleySingleton = VolleySingleton.getInstance();
    }

    @Override
    protected Integer doInBackground(RatingDisciplina... params) {

        RatingDisciplina ratingDisciplina = params[0];

        return RatingDisciplinaParser.newRatingDisciplina(ratingDisciplina);
    }

}
