/*
 * Copyright [2016] [UFMS]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
