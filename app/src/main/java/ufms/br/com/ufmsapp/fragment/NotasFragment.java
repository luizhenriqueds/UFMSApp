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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.activity.NotasAtividadesActivity;
import ufms.br.com.ufmsapp.adapter.ListaDisciplinaNotasAdapter;
import ufms.br.com.ufmsapp.callbacks.DisciplinasLoadedListener;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.preferences.UserSessionPreference;
import ufms.br.com.ufmsapp.task.TaskLoadDisciplinas;
import ufms.br.com.ufmsapp.task.TaskLoadNotas;
import ufms.br.com.ufmsapp.utils.ConnectionUtils;


public class NotasFragment extends Fragment implements ListaDisciplinaNotasAdapter.OnDisciplinaClickListener, SwipeRefreshLayout.OnRefreshListener, DisciplinasLoadedListener, SearchView.OnQueryTextListener {
    public static boolean mIsInForegroundMode;

    private RecyclerView mRecyclerDisciplinasNota;
    private ListaDisciplinaNotasAdapter adapter;
    protected Disciplina disciplina;
    ArrayList<Disciplina> listDisciplinas;
    CircularProgressBar progressBar;
    TextView emptyListText;
    ImageView emptyListImg;
    protected boolean isConnected = false;
    private IntentFilter filter;
    private NetworkChangeReceiver receiver;
    private static View view;
    private SwipeRefreshLayout swipeRefreshNotas;
    public static final String DISCIPLINA_EXTRA = "disciplina";
    private TaskLoadDisciplinas mTask;

    public NotasFragment() {
    }

    public static NotasFragment newInstance() {
        return new NotasFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver, filter);
        getActivity().registerReceiver(mMessageReceiver, new IntentFilter("updateNotas"));
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


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.notas_list_menu, menu);

        SearchManager searchManager =
                (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_nota).getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setQueryHint(getString(R.string.txt_busca));
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getActivity().getComponentName()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRetainInstance(true);
        view = inflater.inflate(R.layout.fragment_notas_layout_adapter, container, false);


        disciplina = getActivity().getIntent().getParcelableExtra(DisciplinasFragment.DISCIPLINA_EXTRA);

        mRecyclerDisciplinasNota = (RecyclerView) view.findViewById(R.id.recycler_notas);

        swipeRefreshNotas = (SwipeRefreshLayout) view.findViewById(R.id.swipe_notas);

        swipeRefreshNotas.setOnRefreshListener(this);

        swipeRefreshNotas.setColorSchemeResources(R.color.green, R.color.blue, R.color.yellow);


        //  if (OrientationUtils.isPortrait(getResources().getConfiguration())) {
        mRecyclerDisciplinasNota.setLayoutManager(new LinearLayoutManager(getActivity()));
        //} else {
        // mRecyclerDisciplinasNota.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        // }

        progressBar = (CircularProgressBar) view.findViewById(R.id.progress_bar_list_notas);

        emptyListText = (TextView) view.findViewById(R.id.txt_no_nota);
        emptyListImg = (ImageView) view.findViewById(R.id.notas_no_item);

        adapter = new ListaDisciplinaNotasAdapter(getActivity());

        UserSessionPreference prefs = new UserSessionPreference(getActivity());

        if (!prefs.isFirstTime()) {
            listDisciplinas = MyApplication.getWritableDatabase().listarDisciplinas(MyApplication.getWritableDatabase().alunoByEmail(prefs.getEmail()).getAlunoIdServidor());
        }

        //listDisciplinas = MyApplication.getWritableDatabase().listarDisciplinas(1);

        if (!listDisciplinas.isEmpty()) {
            adapter.setDisciplinasList(listDisciplinas);

        } else {
            if (ConnectionUtils.hasConnection(getActivity())) {
                mTask = new TaskLoadDisciplinas(this, progressBar, true, emptyListText, emptyListImg);
                mTask.execute();

                new TaskLoadNotas().execute();
            } else {
                showConnectionErrorMessage();
            }

        }


        updateList();

        return view;
    }

    public void updateList() {
        adapter.setDisciplinaClickListener(this);
        mRecyclerDisciplinasNota.setAdapter(adapter);
        setupRecyclerView();
    }

    private void checkAdapterIsEmpty() {

        if (adapter.getItemCount() == 0) {
            emptyListImg.setVisibility(View.VISIBLE);
            emptyListText.setVisibility(View.VISIBLE);
            emptyListText.setText(R.string.txt_no_disciplina_nota);
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

    @Override
    public void onDisciplinaClick(View v, int position, Disciplina disciplina) {
        Intent intent = new Intent(getActivity(), NotasAtividadesActivity.class);
        intent.putExtra(DISCIPLINA_EXTRA, disciplina);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {

        if (ConnectionUtils.hasConnection(getActivity())) {
            mTask = new TaskLoadDisciplinas(this, progressBar, true, emptyListText, emptyListImg);
            mTask.execute();

            new TaskLoadNotas().execute();

        } else {
            showConnectionErrorMessage();
            swipeRefreshNotas.setRefreshing(false);
        }

    }

    public static void showConnectionErrorMessage() {
        final Snackbar snackbar = Snackbar.make(view.findViewById(R.id.root_layout_notas), R.string.txt_no_connection_msg, Snackbar.LENGTH_LONG);

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
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final ArrayList<Disciplina> resultList = filter(listDisciplinas, newText);
        adapter.setDisciplinasList(resultList);
        updateList();
        return true;
    }

    private ArrayList<Disciplina> filter(ArrayList<Disciplina> disciplinas, String query) {
        query = query.toLowerCase();

        final ArrayList<Disciplina> filteredDisciplinas = new ArrayList<>();
        for (Disciplina disciplina : disciplinas) {
            final String text = disciplina.getTitulo().toLowerCase();
            if (text.contains(query)) {
                filteredDisciplinas.add(disciplina);
            }
        }
        return filteredDisciplinas;
    }


    @Override
    public void onDisciplinasLoaded(ArrayList<Disciplina> listDisciplinas) {
        if (swipeRefreshNotas.isRefreshing()) {
            swipeRefreshNotas.setRefreshing(false);
        }

        if (!listDisciplinas.isEmpty()) {
            adapter.setDisciplinasList(listDisciplinas);
            updateList();
        } else {
            checkAdapterIsEmpty();
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent != null) {
                onRefresh();
            }
        }
    };

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

                if (listDisciplinas.isEmpty()) {
                    mTask = new TaskLoadDisciplinas(NotasFragment.this, progressBar, false, emptyListText, emptyListImg);
                    mTask.execute();
                }

            }

            isConnected = false;
            //getActivity().invalidateOptionsMenu();

            return false;
        }

    }

}
