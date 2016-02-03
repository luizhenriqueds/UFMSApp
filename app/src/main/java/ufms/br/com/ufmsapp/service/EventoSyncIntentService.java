package ufms.br.com.ufmsapp.service;


import android.app.IntentService;
import android.content.Intent;

public class EventoSyncIntentService extends IntentService {


    public EventoSyncIntentService() {
        super("EventoSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            //new TaskLoadEventos();
        }
    }
}
