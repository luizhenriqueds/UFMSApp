package ufms.br.com.ufmsapp.task;


import android.app.Activity;
import android.os.AsyncTask;

import ufms.br.com.ufmsapp.JSONParsers.UpdateRatingDisciplinaParser;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.RatingDisciplina;

public class TaskUpdateRateDisciplina extends AsyncTask<RatingDisciplina, Void, Void> {

    protected VolleySingleton volleySingleton;
    Activity activity;

    public void setActivityContext(Activity activity) {
        this.activity = activity;
    }


    public TaskUpdateRateDisciplina() {
        volleySingleton = VolleySingleton.getInstance();
    }

    @Override
    protected Void doInBackground(RatingDisciplina... params) {
        RatingDisciplina ratingDisciplina = params[0];

        UpdateRatingDisciplinaParser.updateRatingDisciplinaParser(ratingDisciplina);

        return null;

    }


}
