package ufms.br.com.ufmsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.adapter.ExploreEventosAdapter;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.utils.CustomLinearLayoutManager;

public class ExploreEventosFragment extends Fragment {

    protected ExploreEventosAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView listEventos;
    ArrayList<Evento> eventos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public static ExploreEventosFragment newInstance() {
        return new ExploreEventosFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.eventos_card_group, container, false);

        adapter = new ExploreEventosAdapter(getActivity());


        ArrayList<Disciplina> disciplinas = MyApplication.getWritableDatabase().listarDisciplinas(1);

        for (int i = 0; i < disciplinas.size(); i++) {
            eventos = MyApplication.getWritableDatabase().listarEventos(disciplinas.get(i).getIdDisciplinaServidor());
        }

        adapter.setExploreEventosList(eventos);


        listEventos = (RecyclerView) view.findViewById(R.id.list_eventos);

        listEventos.setLayoutManager(new CustomLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        listEventos.setAdapter(adapter);
        return view;
    }

}
