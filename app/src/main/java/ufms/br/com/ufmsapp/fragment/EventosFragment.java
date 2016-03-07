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


import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.activity.DetalhesEventoActivity;
import ufms.br.com.ufmsapp.adapter.EventosAdapter;
import ufms.br.com.ufmsapp.callbacks.EventosLoadedListener;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.pojo.EventoRead;
import ufms.br.com.ufmsapp.preferences.UserSessionPreference;
import ufms.br.com.ufmsapp.task.TaskLoadEventos;
import ufms.br.com.ufmsapp.task.TaskLoadMateriais;
import ufms.br.com.ufmsapp.task.TaskLoadMatriculas;
import ufms.br.com.ufmsapp.utils.ConnectionUtils;
import ufms.br.com.ufmsapp.utils.Constants;

public class EventosFragment extends Fragment implements EventosLoadedListener, SwipeRefreshLayout.OnRefreshListener, EventosAdapter.OnEventClickListener, SearchView.OnQueryTextListener {

    public static boolean mIsInForegroundMode;

    protected EventosAdapter adapter;
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView mRecyclerEventos;
    protected static TextView messageConnectionError;
    protected static ImageView iconConnectionError;
    protected ArrayList<Evento> eventos;
    public static View view;
    private NetworkChangeReceiver receiver;
    protected boolean isConnected = false;
    protected static ProgressBar listEventosProgressBar;
    private TaskLoadEventos mTask;
    private IntentFilter filter;

    public static final String EVENTO_EXTRA = "evento";
    public static final String STATE_EVENTOS = "state_evento";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver, filter);
        getActivity().registerReceiver(mMessageReceiver, new IntentFilter(Constants.UPDATE_EVENTO_INTENT));
        adapter.notifyDataSetChanged();
        mIsInForegroundMode = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
        getActivity().unregisterReceiver(mMessageReceiver);
        mIsInForegroundMode = false;
    }

    public static boolean isInForeground() {
        return mIsInForegroundMode;
    }


    public static EventosFragment newInstance() {
        return new EventosFragment();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.eventos_list_menu, menu);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.txt_busca));
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_calendar_view:
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_layout_container, FragmentEventCalendarView.newInstance());
                fragmentTransaction.commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_eventos_layout, container, false);

        //getActivity().invalidateOptionsMenu();

        listEventosProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar_list_eventos);

        eventos = new ArrayList<>();

        mRecyclerEventos = (RecyclerView) view.findViewById(R.id.recycler_eventos);

        messageConnectionError = (TextView) view.findViewById(R.id.txt_volley_error);
        iconConnectionError = (ImageView) view.findViewById(R.id.no_connection_icon);
        iconConnectionError.setColorFilter(ContextCompat.getColor(getActivity(), R.color.no_connection_msg));

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_eventos);
        swipeRefreshLayout.setOnRefreshListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.green, R.color.blue, R.color.yellow);

        // if (OrientationUtils.isPortrait(getResources().getConfiguration())) {
        mRecyclerEventos.setLayoutManager(new LinearLayoutManager(getActivity()));
        // } else {
        //    mRecyclerEventos.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        // }


        adapter = new EventosAdapter(getActivity());

        if (savedInstanceState != null) {
            eventos = savedInstanceState.getParcelableArrayList(STATE_EVENTOS);
            adapter.setEventosList(eventos);
        } else {


            UserSessionPreference prefs = new UserSessionPreference(getActivity());

            ArrayList<Disciplina> disciplinas = new ArrayList<>();

            if (!prefs.isFirstTime()) {
                disciplinas = MyApplication.getWritableDatabase().listarDisciplinas(MyApplication.getWritableDatabase().alunoByEmail(prefs.getEmail()).getAlunoIdServidor());
            }


            for (int i = 0; i < disciplinas.size(); i++) {
                eventos.addAll(MyApplication.getWritableDatabase().listarEventos(disciplinas.get(i).getIdDisciplinaServidor()));
            }
            adapter.setEventosList(eventos);


            if (eventos.isEmpty()) {
                if (ConnectionUtils.hasConnection(getActivity())) {
                    mTask = new TaskLoadEventos(this, listEventosProgressBar, false, messageConnectionError, iconConnectionError);
                    mTask.execute();

                } else {
                    showConnectionErrorMessage();
                }
            }
        }


        updateList();

        return view;
    }

    private boolean isEventosListEmpty() {
        return eventos.isEmpty();
    }


    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       *//* if (eventos.isEmpty()) {
            if (mTask == null) {
                mTask = new TaskLoadEventos(this, listEventosProgressBar, false, messageConnectionError, iconConnectionError);
                mTask.execute();

            }
        } else {
            updateList();
        }*//*
    }*/

    private void checkAdapterIsEmpty() {

        //  Toast.makeText(getContext(), "Adapter Size => " + adapter.getItemCount(), Toast.LENGTH_LONG).show();
        if (adapter.getItemCount() == 0) {
            //Toast.makeText(getContext(), "Code is here", Toast.LENGTH_LONG).show();
            iconConnectionError.setVisibility(View.VISIBLE);
            messageConnectionError.setVisibility(View.VISIBLE);
            messageConnectionError.setText(R.string.txt_sem_eventos_lista);
        } else {
            iconConnectionError.setVisibility(View.GONE);
            messageConnectionError.setVisibility(View.GONE);
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


    public void updateList() {
        adapter.setListener(this);
        mRecyclerEventos.setAdapter(adapter);
        setupRecyclerView();
    }

    public static void showConnectionErrorMessage() {
        final Snackbar snackbar = Snackbar.make(view.findViewById(R.id.root_layout), R.string.txt_no_connection_msg, Snackbar.LENGTH_LONG);

        snackbar.setAction(R.string.txt_retry_connection, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ConnectionUtils.hasConnection(MyApplication.getAppContext())) {
                    snackbar.dismiss();
                } else {
                    showConnectionErrorMessage();
                }

            }
        }).show();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STATE_EVENTOS, eventos);
    }


    @Override
    public void onEventosLoaded(ArrayList<Evento> listEventos) {

        if (swipeRefreshLayout != null) {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        }

        if (!listEventos.isEmpty()) {
            adapter.setEventosList(listEventos);
            updateList();
        } else {
            checkAdapterIsEmpty();
        }

    }

    @Override
    public void onRefresh() {
        if (ConnectionUtils.hasConnection(getActivity())) {
            //sincroniza matriculas
            new TaskLoadMatriculas().execute();

            //sincroniza uploads
            new TaskLoadMateriais().execute();

            mTask = new TaskLoadEventos(this, listEventosProgressBar, true, messageConnectionError, iconConnectionError);
            mTask.execute();

        } else {
            showConnectionErrorMessage();
            swipeRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    public void onEventClick(View v, int position, Evento evento) {

        MyApplication.getWritableDatabase().updateEventoRead(new EventoRead(1, evento.getIdEventoServidor()));

        Intent intent = new Intent(getActivity(), DetalhesEventoActivity.class);
        intent.putExtra(EVENTO_EXTRA, evento);

        startActivity(intent);
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null) {
                onRefresh();
            }
        }
    };

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<Evento> resultList = filter(eventos, newText);
        adapter.setEventosList(resultList);
        updateList();
        return true;
    }

    private ArrayList<Evento> filter(ArrayList<Evento> eventos, String query) {
        query = query.toLowerCase();

        final ArrayList<Evento> filteredEventos = new ArrayList<>();
        for (Evento evento : eventos) {
            final String text = evento.getTitulo().toLowerCase();
            if (text.contains(query)) {
                filteredEventos.add(evento);
            }
        }
        return filteredEventos;
    }


    public class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(final Context context, final Intent intent) {

            isNetworkAvailable(context);

        }


        private boolean isNetworkAvailable(Context context) {

            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();


            if (activeNetInfo != null && activeNetInfo.isConnected()) {
                isConnected = true;
                //getActivity().invalidateOptionsMenu();

                if (isEventosListEmpty()) {
                    mTask = new TaskLoadEventos(EventosFragment.this, listEventosProgressBar, false, messageConnectionError, iconConnectionError);
                    mTask.execute();
                }

            }

            isConnected = false;
            //getActivity().invalidateOptionsMenu();

            return false;
        }

    }

}

