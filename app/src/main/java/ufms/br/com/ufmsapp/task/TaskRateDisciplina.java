package ufms.br.com.ufmsapp.task;


import android.app.Activity;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;

import ufms.br.com.ufmsapp.JSONParsers.RatingDisciplinaParser;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.RatingDisciplina;

public class TaskRateDisciplina extends AsyncTask<RatingDisciplina, Integer, Integer> {

    protected VolleySingleton volleySingleton;

    Activity activity;

    public void setActivityContext(Activity activity) {
        this.activity = activity;
    }

    public TaskRateDisciplina() {
        volleySingleton = VolleySingleton.getInstance();
    }

    @Override
    protected Integer doInBackground(RatingDisciplina... params) {

        RatingDisciplina ratingDisciplina = params[0];

        return RatingDisciplinaParser.newRatingDisciplina(ratingDisciplina);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        if (integer > 0) {
            Snackbar.make(activity.findViewById(android.R.id.content), R.string.txt_rated_success, Snackbar.LENGTH_LONG).show();
        }
    }
}
