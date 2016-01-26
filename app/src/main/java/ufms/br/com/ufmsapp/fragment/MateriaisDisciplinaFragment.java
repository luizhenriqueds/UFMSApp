package ufms.br.com.ufmsapp.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.adapter.MateriaisAdapter;
import ufms.br.com.ufmsapp.extras.UrlEndpoints;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Material;
import ufms.br.com.ufmsapp.task.DownloadTask;


public class MateriaisDisciplinaFragment extends Fragment implements MateriaisAdapter.OnMaterialClickListener {

    protected RecyclerView mRecyclerMateriais;
    protected MateriaisAdapter adapter;
    protected Disciplina disciplina;
    //ProgressDialog mProgressDialog;

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
        mRecyclerMateriais.setLayoutManager(new LinearLayoutManager(getActivity()));

        disciplina = getActivity().getIntent().getParcelableExtra(DisciplinasFragment.DISCIPLINA_EXTRA);

        ArrayList<Material> uploads = MyApplication.getWritableDatabase().listarMateriaisByDisciplina(disciplina.getIdDisciplinaServidor());

        adapter = new MateriaisAdapter(getActivity());
        adapter.setMaterialClickListener(this);
        adapter.setUploadList(uploads);


        mRecyclerMateriais.setAdapter(adapter);

        return view;
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
