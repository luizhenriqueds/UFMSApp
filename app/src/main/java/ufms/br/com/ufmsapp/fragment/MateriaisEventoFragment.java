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

package ufms.br.com.ufmsapp.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
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
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.adapter.MateriaisAdapter;
import ufms.br.com.ufmsapp.extras.UrlEndpoints;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.pojo.Material;
import ufms.br.com.ufmsapp.task.DownloadTask;
import ufms.br.com.ufmsapp.utils.ConnectionUtils;
import ufms.br.com.ufmsapp.utils.GetFileMimeType;
import ufms.br.com.ufmsapp.utils.OrientationUtils;
import ufms.br.com.ufmsapp.utils.RequestPermission;


public class MateriaisEventoFragment extends Fragment implements MateriaisAdapter.OnMaterialClickListener {

    protected RecyclerView eventosUploadsRecycler;
    protected MateriaisAdapter adapter;
    protected Evento evento;
    protected ArrayList<Material> uploads;
    private TextView emptyListText;
    private ImageView emptyListIcon;

    public static MateriaisEventoFragment newInstance() {
        return new MateriaisEventoFragment();
    }

    public MateriaisEventoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_materiais_evento, container, false);

        //evento = getActivity().getIntent().getParcelableExtra(EventosFragment.EVENTO_EXTRA);

        int eventoId = -1;
        if (getActivity().getIntent().getStringExtra("EVENTO_CREATED") != null) {
            eventoId = Integer.parseInt(getActivity().getIntent().getStringExtra("EVENTO_CREATED"));
        }

        if (getActivity().getIntent().getParcelableExtra(EventosFragment.EVENTO_EXTRA) != null) {
            evento = getActivity().getIntent().getParcelableExtra(EventosFragment.EVENTO_EXTRA);
        } else {
            evento = MyApplication.getWritableDatabase().eventoById(eventoId);
        }

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

        if (evento != null) {
            uploads = MyApplication.getWritableDatabase().listarMateriaisByEvento(evento.getIdEventoServidor());
        }

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
    public void onMaterialClick(View v, int position, final Material material) {

        if (RequestPermission.verifyStoragePermissions(getActivity())) {
            String fileName = material.getPathMaterial().replace(DownloadTask.UPLOAD_PATH_REPLACE, "");
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);

            switch (v.getId()) {
                case R.id.root_open_document:
                    if (file.exists()) {
                        openDocument(material.getPathMaterial());
                    } else {
                        final DownloadTask downloadTask = new DownloadTask(getActivity(), R.mipmap.ic_file_download_black_24dp, material.getPathMaterial(), getActivity().getResources().getString(R.string.txt_download_em_progresso), getActivity());
                        downloadTask.execute(UrlEndpoints.URL_ENDPOINT_DOWNLOAD_FILE + material.getPathMaterial());
                        openDocument(material.getPathMaterial());
                    }
                    break;
                case R.id.btn_file_download:

                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    boolean networkTypeWifi = prefs.getBoolean(getResources().getString(R.string.pref_network_type), true);

                    //Se existir o arquivo, não baixa novamente
                    if (!file.exists()) {
                        if (ConnectionUtils.isConnectedWifi(getActivity())) {
                            final DownloadTask downloadTask = new DownloadTask(getActivity(), R.mipmap.ic_file_download_black_24dp, material.getPathMaterial(), getActivity().getResources().getString(R.string.txt_download_em_progresso), getActivity());
                            downloadTask.execute(UrlEndpoints.URL_ENDPOINT_DOWNLOAD_FILE + material.getPathMaterial());
                        } else if (ConnectionUtils.isConnectedMobile(getActivity()) && !networkTypeWifi) {
                            final DownloadTask downloadTask = new DownloadTask(getActivity(), R.mipmap.ic_file_download_black_24dp, material.getPathMaterial(), getActivity().getResources().getString(R.string.txt_download_em_progresso), getActivity());
                            downloadTask.execute(UrlEndpoints.URL_ENDPOINT_DOWNLOAD_FILE + material.getPathMaterial());
                        } else {
                            Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.txt_wifi_only_snackbar, Snackbar.LENGTH_LONG).show();
                        }

                    } else {

                        final Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.txt_success_download_snack_bar, Snackbar.LENGTH_LONG);

                        snackbar.setAction(R.string.txt_open_document, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                openDocument(material.getPathMaterial());

                            }
                        }).show();

                        //Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.txt_success_download_snack_bar, Snackbar.LENGTH_LONG).show();
                    }
                    break;
            }

        } else {
            Toast.makeText(MyApplication.getAppContext(), "Para baixar os materiais, ative a permissão", Toast.LENGTH_LONG).show();
        }


    }
}
