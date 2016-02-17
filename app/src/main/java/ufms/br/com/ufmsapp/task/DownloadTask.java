package ufms.br.com.ufmsapp.task;


import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.utils.GetFileMimeType;

public class DownloadTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private PowerManager.WakeLock mWakeLock;
    private int iconResId;
    private String title;
    private String msg;

    private String fileName;
    public static final String UPLOAD_PATH_REPLACE = "/uploads/";
    public static final int NOTIFICATION_ID = 100;
    static NotificationCompat.Builder builder;
    static NotificationManager manager;
    protected PendingIntent piOpenNotification;
    private Activity activity;

    public DownloadTask(Context context, int iconResId,
                        String title, String msg, Activity activity) {
        this.context = context;
        this.iconResId = iconResId;
        this.title = title;
        this.msg = msg;
        this.activity = activity;

        fileName = title.replace(UPLOAD_PATH_REPLACE, "");

        buildNotification();

    }

    private void buildNotification() {
        manager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        builder = new NotificationCompat.Builder(context);

        builder.setSmallIcon(iconResId);
        builder.setContentTitle(fileName);
        builder.setContentText(msg);

        builder.setAutoCancel(true);

        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        intent.setDataAndType(Uri.fromFile(file), GetFileMimeType.getMimeType(title.replace(UPLOAD_PATH_REPLACE, "")));

        piOpenNotification = PendingIntent.getActivity(context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(piOpenNotification);

        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();

        builder.setProgress(100, 0, false);
        manager.notify(NOTIFICATION_ID, builder.build());
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        builder.setProgress(100, progress[0], false);
        manager.notify(100, builder.build());
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        mWakeLock.release();

        if (result != null) {
            Toast.makeText(context, context.getString(R.string.txt_download_error) + result, Toast.LENGTH_LONG).show();
        } else {
            //builder.setDefaults(Notification.DEFAULT_ALL);
            builder.setContentText(context.getString(R.string.txt_download_finalizado));
            builder.setProgress(0, 0, false);
            manager.notify(NOTIFICATION_ID, builder.build());
            Snackbar.make(activity.findViewById(android.R.id.content), R.string.txt_success_download_snack_bar, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected String doInBackground(String... sUrl) {
        InputStream input = null;
        OutputStream output = null;

        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            int fileLength = connection.getContentLength();

            input = connection.getInputStream();
            output = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName));

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;
                if (fileLength > 0)
                    publishProgress((int) (total * 100 / fileLength));
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }
}