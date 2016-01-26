package ufms.br.com.ufmsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.adapter.MateriaisAdapter;
import ufms.br.com.ufmsapp.extras.UrlEndpoints;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.pojo.Material;
import ufms.br.com.ufmsapp.task.DownloadTask;


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

        evento = getActivity().getIntent().getParcelableExtra(EventosFragment.EVENTO_EXTRA);

        emptyListText = (TextView) view.findViewById(R.id.no_upload_txt);
        emptyListIcon = (ImageView) view.findViewById(R.id.no_upload_icon);
        emptyListIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.no_connection_msg));

        eventosUploadsRecycler = (RecyclerView) view.findViewById(R.id.recycler_uploads_evento);
        eventosUploadsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
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

    @Override
    public void onMaterialClick(View v, int position, Material material) {
        switch (v.getId()) {
            case R.id.root_open_document:
                Toast.makeText(getActivity(), "FILE DOWNLOAD TEST => " + material.getPathMaterial().replace("/uploads/", ""), Toast.LENGTH_LONG).show();
                break;
            case R.id.btn_file_download:
                final DownloadTask downloadTask = new DownloadTask(getActivity(), R.mipmap.ic_file_download_black_24dp, material.getPathMaterial(), getActivity().getResources().getString(R.string.txt_download_em_progresso));

                ufms.br.com.ufmsapp.utils.RequestPermission.verifyStoragePermissions(getActivity());

                downloadTask.execute(UrlEndpoints.URL_ENDPOINT_DOWNLOAD_FILE + material.getPathMaterial());

                break;
        }
    }
}
