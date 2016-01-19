package ufms.br.com.ufmsapp.fragment;


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
import ufms.br.com.ufmsapp.adapter.AlunosAdapter;
import ufms.br.com.ufmsapp.pojo.AlunoXDisciplina;
import ufms.br.com.ufmsapp.pojo.Disciplina;


public class AlunosDisciplinaFragment extends Fragment {

    private RecyclerView mRecyclerAlunos;
    private AlunosAdapter adapter;
    private Disciplina disciplina;
    ArrayList<AlunoXDisciplina> matriculas;


    public AlunosDisciplinaFragment() {
    }

    public static AlunosDisciplinaFragment newInstance() {
        return new AlunosDisciplinaFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alunos_disciplina, container, false);


        disciplina = getActivity().getIntent().getParcelableExtra(DisciplinasFragment.DISCIPLINA_EXTRA);

        mRecyclerAlunos = (RecyclerView) view.findViewById(R.id.recycler_alunos_disciplina);
        mRecyclerAlunos.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new AlunosAdapter(getActivity());

        matriculas = MyApplication.getWritableDatabase().listMatriculas(disciplina.getIdDisciplinaServidor());

        if (matriculas != null) {
            adapter.setAlunosList(matriculas);
        } else {
            Toast.makeText(getActivity(), "Sem alunos matriculados", Toast.LENGTH_LONG).show();
        }


        mRecyclerAlunos.setAdapter(adapter);

        return view;
    }

}
