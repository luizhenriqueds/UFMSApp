package ufms.br.com.ufmsapp.task;


import android.os.AsyncTask;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.LoadDataUtils;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Material;

public class TaskLoadMateriais extends AsyncTask<Void, Integer, ArrayList<Material>> {

    protected VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    public TaskLoadMateriais() {
        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
    }


    @Override
    protected ArrayList<Material> doInBackground(Void... params) {

        return LoadDataUtils.loadMateriais(requestQueue);
    }

}
