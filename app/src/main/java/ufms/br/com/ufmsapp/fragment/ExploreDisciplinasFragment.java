package ufms.br.com.ufmsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.adapter.ExploreDisciplinasAdapter;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Professor;
import ufms.br.com.ufmsapp.pojo.TipoDisciplina;
import ufms.br.com.ufmsapp.pojo.TipoEvento;
import ufms.br.com.ufmsapp.pojo.TituloProfessor;
import ufms.br.com.ufmsapp.utils.CustomLinearGridLayoutManager;

public class ExploreDisciplinasFragment extends Fragment {

    protected ExploreDisciplinasAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView listDisciplinas;

    @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ExploreDisciplinasFragment newInstance() {
        return new ExploreDisciplinasFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.disciplinas_card_group, container, false);

        adapter = new ExploreDisciplinasAdapter(getActivity());

        ArrayList<Disciplina> disciplinas = new ArrayList<>();

        TipoEvento tipoEvento = new TipoEvento();
        tipoEvento.setNomeTipoEvento("Trabalho");

        //TipoDisciplina tipoDisciplina = new TipoDisciplina();
       // tipoDisciplina.setTipoDisciplina("OBR");

        //TituloProfessor tituloProfessor = new TituloProfessor();
        //tituloProfessor.setTituloProfessor("Mestre");

        Professor professor = new Professor();
        professor.setNome("Luiz");
        Calendar c = Calendar.getInstance();
        professor.setEmail("luizhsda@gmail.com");
        professor.setAnoIngresso(new Date(c.getTimeInMillis()));
        professor.setFormacao("Engenharia de Software");

        Disciplina disciplina = new Disciplina();
        disciplina.setTitulo("Banco de dados");
        disciplina.setCodigo("BD20150792");
        disciplina.setDescricao("45 alunos matriculados");

        Disciplina disciplina2 = new Disciplina();
        disciplina2.setTitulo("Engenharia de Software");
        disciplina2.setCodigo("ES91009191");
        disciplina2.setDescricao("30 alunos matriculados");

        Disciplina disciplina3 = new Disciplina();
        disciplina3.setTitulo("Inteligência Artificial");
        disciplina3.setCodigo("IA20150792");
        disciplina3.setDescricao("10 alunos matriculados");

        disciplinas.add(disciplina);
        disciplinas.add(disciplina2);
        disciplinas.add(disciplina3);

        adapter.setDisciplinaList(disciplinas);

        //messageConnectionError = (TextView) view.findViewById(R.id.txtVolleyError);

        // swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_layout);
        //swipeRefreshLayout.setOnRefreshListener(this);

        listDisciplinas = (RecyclerView) view.findViewById(R.id.list_disciplinas);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());

        listDisciplinas.setLayoutManager(new CustomLinearGridLayoutManager(getActivity(), 3));

        //listDisciplinas.setLayoutManager(new LinearLayoutManager(getActivity()));


        listDisciplinas.setAdapter(adapter);

//        if (savedInstanceState != null) {
//            listMovies = savedInstanceState.getParcelableArrayList(STATE_MOVIES);
//        } else {
//            listMovies = MyApplication.getWritableDatabase().readMovies(0);
//
//            if (listMovies.isEmpty()) {
//                new TaskLoadMoviesBoxOffice(this).execute();
//            }
//        }
//        adapterBoxOfficeMovies.setMovieList(listMovies);

        return view;
    }

}
