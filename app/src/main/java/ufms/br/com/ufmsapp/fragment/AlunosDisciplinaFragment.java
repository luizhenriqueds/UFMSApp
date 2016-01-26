package ufms.br.com.ufmsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.adapter.AlunosAdapter;
import ufms.br.com.ufmsapp.pojo.AlunoXDisciplina;
import ufms.br.com.ufmsapp.pojo.Disciplina;


public class AlunosDisciplinaFragment extends Fragment {

    private RecyclerView mRecyclerAlunos;
    private AlunosAdapter adapter;
    protected Disciplina disciplina;
    ArrayList<AlunoXDisciplina> matriculas;
    CircularProgressBar progressBar;
    TextView emptyListText;
    ImageView emptyListImg;


    public AlunosDisciplinaFragment() {
    }

    public static AlunosDisciplinaFragment newInstance() {
        return new AlunosDisciplinaFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_alunos_disciplina, container, false);


        disciplina = getActivity().getIntent().getParcelableExtra(DisciplinasFragment.DISCIPLINA_EXTRA);

        mRecyclerAlunos = (RecyclerView) view.findViewById(R.id.recycler_alunos_disciplina);
        mRecyclerAlunos.setLayoutManager(new LinearLayoutManager(getActivity()));
        progressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar_list_alunos);

        emptyListText = (TextView) view.findViewById(R.id.aluno_txt_empty_text);
        emptyListImg = (ImageView) view.findViewById(R.id.aluno_empty_icon);

        adapter = new AlunosAdapter(getActivity());

        matriculas = MyApplication.getWritableDatabase().listMatriculas(disciplina.getIdDisciplinaServidor());

        if (matriculas != null) {
            adapter.setAlunosList(matriculas);
        }


        updateList();

        return view;
    }

    public void updateList() {
        mRecyclerAlunos.setAdapter(adapter);
        setupRecyclerView();
    }

    private void checkAdapterIsEmpty() {

        if (adapter.getItemCount() == 0) {
            emptyListImg.setVisibility(View.VISIBLE);
            emptyListText.setVisibility(View.VISIBLE);
            emptyListText.setText(R.string.txt_no_student);
        } else {
            emptyListImg.setVisibility(View.GONE);
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

}
