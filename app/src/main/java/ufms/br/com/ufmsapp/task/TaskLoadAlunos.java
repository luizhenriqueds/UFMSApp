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
