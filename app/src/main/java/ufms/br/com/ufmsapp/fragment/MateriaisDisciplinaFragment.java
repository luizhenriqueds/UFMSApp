package ufms.br.com.ufmsapp.fragment;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.adapter.MateriaisAdapter;
import ufms.br.com.ufmsapp.extras.UrlEndpoints;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Material;
import ufms.br.com.ufmsapp.task.DownloadTask;
import ufms.br.com.ufmsapp.utils.GetFileMimeType;
import ufms.br.com.ufmsapp.utils.OrientationUtils;
import ufms.br.com.ufmsapp.utils.RequestPermission;


public class MateriaisDisciplinaFragment extends Fragment implements MateriaisAdapter.OnMaterialClickListener {

    protected RecyclerView mRecyclerMateriais;
    protected MateriaisAdapter adapter;
    protected Disciplina disciplina;
    CircularProgressBar mProgressDialog;
    TextView emptyListText;
    ImageView emptyListIcon;

    public MateriaisDisciplinaFragment() {
    }

    public static MateriaisDisciplinaFragment newInstance() {
        return new MateriaisDisciplinaFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        View view = inflater.inflate(R.layout.fragment_materiais_disciplina, container, false);

        mRecyclerMateriais = (RecyclerView) view.findViewById(R.id.recycler_files_disciplina);
        //mRecyclerMateriais.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (OrientationUtils.isPortrait(getResources().getConfiguration())) {
            mRecyclerMateriais.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            mRecyclerMateriais.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }

        mProgressDialog = (CircularProgressBar) view.findViewById(R.id.progress_bar_list_files);

        emptyListText = (TextView) view.findViewById(R.id.file_txt_empty_text);
        emptyListIcon = (ImageView) view.findViewById(R.id.file_empty_icon);
        emptyListIcon.setColorFilter(ContextCompat.getColor(getActivity(), R.color.no_connection_msg));

        disciplina = getActivity().getIntent().getParcelableExtra(DisciplinasFragment.DISCIPLINA_EXTRA);

        ArrayList<Material> uploads = MyApplication.getWritableDatabase().listarMateriaisByDisciplina(disciplina.getIdDisciplinaServidor());

        adapter = new MateriaisAdapter(getActivity());
        adapter.setMaterialClickListener(this);
        adapter.setUploadList(uploads);


        updateList();

        return view;
    }

    public void updateList() {
        adapter.setMaterialClickListener(this);
        mRecyclerMateriais.setAdapter(adapter);
        setupRecyclerView();
    }

    private void checkAdapterIsEmpty() {

        if (adapter.getItemCount() == 0) {
            emptyListIcon.setVisibility(View.VISIBLE);
            emptyListText.setVisibility(View.VISIBLE);
            emptyListText.setText(R.string.txt_no_upload_available);
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

                    //Se existir o arquivo, não baixa novamente
                    if (!file.exists()) {
                        final DownloadTask downloadTask = new DownloadTask(getActivity(), R.mipmap.ic_file_download_black_24dp, material.getPathMaterial(), getActivity().getResources().getString(R.string.txt_download_em_progresso), getActivity());
                        downloadTask.execute(UrlEndpoints.URL_ENDPOINT_DOWNLOAD_FILE + material.getPathMaterial());
                    } else {

                        final Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.txt_success_download_snack_bar, Snackbar.LENGTH_LONG);

                        snackbar.setAction(R.string.txt_open_document, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                openDocument(material.getPathMaterial());

                            }
                        }).show();
                    }
                    break;
            }

        } else {
            Toast.makeText(MyApplication.getAppContext(), "Para baixar os materiais, ative a permissão", Toast.LENGTH_LONG).show();
        }

    }
}
