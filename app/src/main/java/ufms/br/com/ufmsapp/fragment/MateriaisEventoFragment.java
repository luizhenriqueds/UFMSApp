package ufms.br.com.ufmsapp.fragment;


import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.adapter.MateriaisAdapter;
import ufms.br.com.ufmsapp.extras.UrlEndpoints;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.pojo.Material;
import ufms.br.com.ufmsapp.task.DownloadTask;
import ufms.br.com.ufmsapp.utils.GetFileMimeType;
import ufms.br.com.ufmsapp.utils.OrientationUtils;


public class MateriaisEventoFragment extends Fragment implements MateriaisAdapter.OnMaterialClickListener {

    protected RecyclerView eventosUploadsRecycler;
    protected MateriaisAdapter adapter;
    protected Evento evento;
    protected ArrayList<Material> uploads;
    private TextView emptyListText;
    private ImageView emptyListIcon;
    protected PendingIntent piOpenNotification;

    public static MateriaisEventoFragment newInstance() {
        return new MateriaisEventoFragment();
    }

    public MateriaisEventoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_materiais_evento, container, false);

        evento = getActivity().getIntent().getParcelableExtra(EventosFragment.EVENTO_EXTRA);

        emptyListText = (TextView) view.findViewById(R.id.no_upload_txt);
        emptyListIcon = (ImageView) view.findViewById(R.id.no_upload_icon);
        emptyListIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.no_connection_msg));

        eventosUploadsRecycler = (RecyclerView) view.findViewById(R.id.recycler_uploads_evento);

        if (OrientationUtils.isPortrait(getResources().getConfiguration())) {
            eventosUploadsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            eventosUploadsRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }


        adapter = new MateriaisAdapter(getActivity());

        uploads = MyApplication.getWritableDatabase().listarMateriaisByEvento(evento.getIdEventoServidor());

        adapter.setUploadList(uploads);

        updateList();

        return view;
    }

    public void updateList() {
        adapter.setMaterialClickListener(this);
        eventosUploadsRecycler.setAdapter(adapter);
        setupRecyclerView();
    }

    private void checkAdapterIsEmpty() {

        if (adapter.getItemCount() == 0) {
            emptyListIcon.setVisibility(View.VISIBLE);
            emptyListText.setVisibility(View.VISIBLE);
            emptyListText.setText(R.string.txt_sem_anexos_lista);
        } else {
            emptyListIcon.setVisibility(View.GONE);
            emptyListText.setVisibility(View.GONE);
        }

    }

    protected void setupRecyclerView() {
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                checkAdapterIsEmpty();
            }
        });


        checkAdapterIsEmpty();
    }

    private void openDocument(String path) {
        String fileName = path.replace(DownloadTask.UPLOAD_PATH_REPLACE, "");
        Intent intent = new Intent();
        intent.setAction(android.content.Intent.ACTION_VIEW);
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
        intent.setDataAndType(Uri.fromFile(file), GetFileMimeType.getMimeType(fileName));
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }

    @Override
    public void onMaterialClick(View v, int position, Material material) {
        switch (v.getId()) {
            case R.id.root_open_document:
                //TODO

                openDocument(material.getPathMaterial());

                break;
            case R.id.btn_file_download:
                final DownloadTask downloadTask = new DownloadTask(getActivity(), R.mipmap.ic_file_download_black_24dp, material.getPathMaterial(), getActivity().getResources().getString(R.string.txt_download_em_progresso), getActivity());

                ufms.br.com.ufmsapp.utils.RequestPermission.verifyStoragePermissions(getActivity());

                downloadTask.execute(UrlEndpoints.URL_ENDPOINT_DOWNLOAD_FILE + material.getPathMaterial());

                break;
        }
    }
}
