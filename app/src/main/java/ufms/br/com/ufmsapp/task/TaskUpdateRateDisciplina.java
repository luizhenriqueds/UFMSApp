package ufms.br.com.ufmsapp.task;


import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import ufms.br.com.ufmsapp.JSONParsers.UpdateRatingDisciplinaParser;
import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.RatingDisciplina;

public class TaskUpdateRateDisciplina extends AsyncTask<RatingDisciplina, Integer, String> {

    protected VolleySingleton volleySingleton;
    Activity activity;

    public void setActivityContext(Activity activity) {
        this.activity = activity;
    }


    public TaskUpdateRateDisciplina() {
        volleySingleton = VolleySingleton.getInstance();
    }

    @Override
    protected String doInBackground(RatingDisciplina... params) {

        RatingDisciplina ratingDisciplina = params[0];

        return UpdateRatingDisciplinaParser.updateRatingDisciplinaParser(ratingDisciplina);
    }

    @Override
    protected void onPostExecute(String updated) {
        super.onPostExecute(updated);

        Toast.makeText(MyApplication.getAppContext(), "UPDATED => " + updated, Toast.LENGTH_LONG).show();

        /*if (integer == 1) {
            Snackbar.make(activity.findViewById(android.R.id.content), R.string.txt_rated_edit_success, Snackbar.LENGTH_LONG).show();
        }*/

    }
}
