package ufms.br.com.ufmsapp.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.activity.DetalhesDisciplinaActivity;
import ufms.br.com.ufmsapp.activity.DetalhesEventoActivity;
import ufms.br.com.ufmsapp.activity.MainActivity;
import ufms.br.com.ufmsapp.adapter.EventosAdapter;
import ufms.br.com.ufmsapp.adapter.ExploreDisciplinasAdapter;
import ufms.br.com.ufmsapp.adapter.ExploreEventosAdapter;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.utils.CustomLinearGridLayoutManager;
import ufms.br.com.ufmsapp.utils.CustomLinearLayoutManager;

public class ExploreFragment extends Fragment implements ExploreEventosAdapter.OnExploreEventoClick, ExploreDisciplinasAdapter.OnExploreDisciplinaClick {

    ExploreEventosAdapter eventosAdapter;
    ExploreDisciplinasAdapter disciplinasAdapter;
    RecyclerView listDisciplinas;
    RecyclerView listEventos;
    protected ArrayList<Evento> eventos;
    protected ArrayList<Disciplina> disciplinas;
    protected CircularProgressBar exploreProgressBar;

    protected LinearLayout cardContent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.explore_layout_fragment, container, false);

        eventosAdapter = new ExploreEventosAdapter(getActivity());
        eventosAdapter.setExploreEventoListener(this);

        disciplinasAdapter = new ExploreDisciplinasAdapter(getActivity());
        disciplinasAdapter.setExploreDisciplinaListener(this);

        Button btnMoreEvents = (Button) view.findViewById(R.id.btn_explore_more_events);
        Button btnMoreDisciplinas = (Button) view.findViewById(R.id.explore_more_disciplinas);

        exploreProgressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar_explore);
        cardContent = (LinearLayout) view.findViewById(R.id.explore_card_content);

        listDisciplinas = (RecyclerView) view.findViewById(R.id.list_disciplinas);
        listEventos = (RecyclerView) view.findViewById(R.id.explore_list_eventos);

        listEventos.setLayoutManager(new CustomLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listDisciplinas.setLayoutManager(new CustomLinearGridLayoutManager(getActivity(), 3));

        final android.support.v4.app.FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();

        btnMoreEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = EventosFragment.newInstance();
                fragmentTransaction.replace(R.id.main_layout_container, fragment);
                fragmentTransaction.commit();

                MainActivity.setNavSelected(R.id.nav_drawer_eventos);

            }
        });

        btnMoreDisciplinas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment = DisciplinasFragment.newInstance();
                fragmentTransaction.replace(R.id.main_layout_container, fragment);
                fragmentTransaction.commit();

                MainActivity.setNavSelected(R.id.nav_drawer_disciplina);
            }
        });

        new TaskLoadExplore().execute();

        return view;
    }


    @Override
    public void onExploreEventClick(View v, int position, Evento evento) {
        @SuppressWarnings("unchecked") ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        Pair.create(v.findViewById(R.id.explore_eventos_icon), EventosAdapter.SHARED_TRANSITION_ICON_KEY)
                );

        Intent intent = new Intent(getActivity(), DetalhesEventoActivity.class);
        intent.putExtra(EventosFragment.EVENTO_EXTRA, evento);

        ActivityCompat.startActivity(getActivity(), intent, options.toBundle());
    }

    @Override
    public void onExploreDisciplinaClick(View v, int position, Disciplina disciplina) {
        Intent intent = new Intent(getActivity(), DetalhesDisciplinaActivity.class);
        intent.putExtra(DisciplinasFragment.DISCIPLINA_EXTRA, disciplina);
        startActivity(intent);
    }

    private class TaskLoadExplore extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cardContent.setVisibility(View.GONE);
            exploreProgressBar.setVisibility(View.VISIBLE);
            exploreProgressBar.setIndeterminate(true);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            int progress = values[0];
            exploreProgressBar.setProgress(progress);
        }

        @Override
        protected Void doInBackground(Void... params) {

            disciplinas = MyApplication.getWritableDatabase().listarDisciplinas(1);
            eventos = new ArrayList<>();

            for (int i = 0; i < disciplinas.size(); i++) {
                eventos.addAll(MyApplication.getWritableDatabase().listarEventos(disciplinas.get(i).getIdDisciplinaServidor(), 3));
            }

            disciplinas = MyApplication.getWritableDatabase().listarDisciplinas(1, 3);

            eventosAdapter.setExploreEventosList(eventos);
            disciplinasAdapter.setDisciplinaList(disciplinas);

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            exploreProgressBar.setVisibility(View.GONE);
            cardContent.setVisibility(View.VISIBLE);
            listDisciplinas.setAdapter(disciplinasAdapter);
            listEventos.setAdapter(eventosAdapter);

        }
    }


}
