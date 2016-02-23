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
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.callbacks.NotasLoadedListener;
import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Nota;

public class TaskLoadNotas extends AsyncTask<Void, Integer, ArrayList<Nota>> {

    private NotasLoadedListener myComponent;
    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    protected ProgressBar listNotasProgressBar;
    protected boolean isRefreshing;
    protected TextView noResultsText;
    protected ImageView noResultsImage;

   /* public TaskLoadNotas(NotasLoadedListener myComponent, ProgressBar listNotasProgressBar, boolean isRefreshing, TextView noResultsText, ImageView noResultsImage) {
        this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        this.listNotasProgressBar = listNotasProgressBar;
        this.isRefreshing = isRefreshing;
        this.noResultsText = noResultsText;
        this.noResultsImage = noResultsImage;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        noResultsText.setVisibility(View.GONE);
        noResultsImage.setVisibility(View.GONE);


        if (isRefreshing) {
            listNotasProgressBar.setVisibility(View.GONE);
        } else {
            listNotasProgressBar.setVisibility(View.VISIBLE);
            listNotasProgressBar.setIndeterminate(true);
        }

    }*/

  /*  @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

        int currentProgress = values[0];
        listNotasProgressBar.setProgress(currentProgress);
    }*/

    public TaskLoadNotas() {
        //this.myComponent = myComponent;
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }

    @Override
    protected ArrayList<Nota> doInBackground(Void... params) {

        return LoadDataUtils.loadNotas(requestQueue);
    }

    /*@Override
    protected void onPostExecute(ArrayList<Nota> listNotas) {
        if (myComponent != null) {
            listNotasProgressBar.setVisibility(View.GONE);
            myComponent.onNotasLoaded(listNotas);
        }
    }*/


}
