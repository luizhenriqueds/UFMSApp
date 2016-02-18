package ufms.br.com.ufmsapp.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.activity.DetalhesDisciplinaActivity;
import ufms.br.com.ufmsapp.adapter.DisciplinasAdapter;
import ufms.br.com.ufmsapp.callbacks.DisciplinasLoadedListener;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.preferences.UserSessionPreference;
import ufms.br.com.ufmsapp.task.TaskLoadDisciplinas;
import ufms.br.com.ufmsapp.task.TaskLoadMateriais;
import ufms.br.com.ufmsapp.task.TaskLoadMatriculas;
import ufms.br.com.ufmsapp.task.TaskLoadRatingDisciplinas;
import ufms.br.com.ufmsapp.utils.ConnectionUtils;

public class DisciplinasFragment extends Fragment implements DisciplinasLoadedListener, DisciplinasAdapter.OnDisciplinaClickListener, SwipeRefreshLayout.OnRefreshListener {
    public static boolean mIsInForegroundMode;

    protected ArrayList<Disciplina> listDisciplinas;
    protected DisciplinasAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView mRecyclerDisciplinas;
    protected TextView txtEmptyList;
    protected ImageView imgEmptyView;
    private static View view;
    private TaskLoadDisciplinas mTaskDisciplinas;
    protected boolean isConnected;
    private NetworkChangeReceiver receiver;
    private IntentFilter filter;
    ProgressBar listDisciplinasProgressBar;

    private static final String STATE_DISCIPLINAS = "state_disciplinas";
    public static final String DISCIPLINA_EXTRA = "disciplina_extra";

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
        getActivity().registerReceiver(mMessageReceiver, new IntentFilter("updateDisciplinas"));
        updateList();
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


    public static DisciplinasFragment newInstance() {
        return new DisciplinasFragment();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //menu.clear();

        getActivity().getMenuInflater().inflate(R.menu.eventos_list_menu, menu);

        //MenuItem item = menu.findItem(R.id.action_filter_event);
        //item.setVisible(ConnectionUtils.hasConnection(getActivity()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        view = inflater.inflate(R.layout.fragment_disciplina_layout, container, false);

        listDisciplinasProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar_list_disciplinas);

        mRecyclerDisciplinas = (RecyclerView) view.findViewById(R.id.recycler_disciplinas);
        mRecyclerDisciplinas.setLayoutManager(new LinearLayoutManager(getActivity()));

        txtEmptyList = (TextView) view.findViewById(R.id.txt_no_disciplina);
        imgEmptyView = (ImageView) view.findViewById(R.id.disciplina_no_item);
        imgEmptyView.setColorFilter(ContextCompat.getColor(getActivity(), R.color.no_connection_msg));


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_disciplinas);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.green, R.color.blue, R.color.yellow);

        listDisciplinas = new ArrayList<>();


        //if (OrientationUtils.isPortrait(getResources().getConfiguration())) {
        mRecyclerDisciplinas.setLayoutManager(new LinearLayoutManager(getActivity()));
        //} else {
        //     mRecyclerDisciplinas.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        // }

        adapter = new DisciplinasAdapter(getActivity());

        if (savedInstanceState != null) {
            listDisciplinas = savedInstanceState.getParcelableArrayList(STATE_DISCIPLINAS);
            adapter.setDisciplinasList(listDisciplinas);
        } else {
            UserSessionPreference prefs = new UserSessionPreference(getActivity());

            if (!prefs.isFirstTime()) {
                // Aluno ID como parâmetro
                listDisciplinas = MyApplication.getWritableDatabase().listarDisciplinas(MyApplication.getWritableDatabase().alunoByEmail(prefs.getEmail()).getAlunoIdServidor());
            }

            adapter.setDisciplinasList(listDisciplinas);

            if (listDisciplinas.isEmpty()) {
                if (ConnectionUtils.hasConnection(getActivity())) {
                    //new TaskLoadMatriculas().execute();
                    mTaskDisciplinas = new TaskLoadDisciplinas(this, listDisciplinasProgressBar, false, txtEmptyList, imgEmptyView);
                    mTaskDisciplinas.execute();
                } else {
                    showConnectionErrorMessage();
                }
            }

        }


        updateList();

        return view;
    }


    private void checkAdapterIsEmpty() {
        if (adapter.getItemCount() == 0) {
            imgEmptyView.setVisibility(View.VISIBLE);
            txtEmptyList.setVisibility(View.VISIBLE);
            txtEmptyList.setText(R.string.txt_sem_disciplinas_lista);
        } else {
            imgEmptyView.setVisibility(View.GONE);
            txtEmptyList.setVisibility(View.GONE);
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

    private void updateList() {
        adapter.setDisciplinaClickListener(this);
        mRecyclerDisciplinas.setAdapter(adapter);
        setupRecyclerView();
    }

    private boolean isDisciplinaListEmpty() {
        return listDisciplinas.isEmpty();
    }


    public static void showConnectionErrorMessage() {
        final Snackbar snackbar = Snackbar.make(view.findViewById(R.id.root_layout_disciplinas), R.string.txt_no_connection_msg, Snackbar.LENGTH_LONG);

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
        outState.putParcelableArrayList(STATE_DISCIPLINAS, listDisciplinas);
    }


    @Override
    public void onDisciplinasLoaded(ArrayList<Disciplina> listDisciplinas) {

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }


        if (!listDisciplinas.isEmpty()) {
            adapter.setDisciplinasList(listDisciplinas);
            updateList();
        } else {
            checkAdapterIsEmpty();
        }
    }

    @Override
    public void onDisciplinaClick(View v, int position, Disciplina disciplina) {

        Intent intent = new Intent(getActivity(), DetalhesDisciplinaActivity.class);
        intent.putExtra(DISCIPLINA_EXTRA, disciplina);

        startActivity(intent);
    }

    @Override
    public void onRefresh() {

        if (ConnectionUtils.hasConnection(getActivity())) {
            //sincroniza matriculas
            new TaskLoadMatriculas().execute();

            //sincroniza uploads
            new TaskLoadMateriais().execute();

            //sincroniza disciplinas rating
            new TaskLoadRatingDisciplinas().execute();

            mTaskDisciplinas = new TaskLoadDisciplinas(this, listDisciplinasProgressBar, true, txtEmptyList, imgEmptyView);
            mTaskDisciplinas.execute();

        } else {
            showConnectionErrorMessage();
            swipeRefreshLayout.setRefreshing(false);
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

                if (isDisciplinaListEmpty()) {
                    mTaskDisciplinas = new TaskLoadDisciplinas(DisciplinasFragment.this, listDisciplinasProgressBar, false, txtEmptyList, imgEmptyView);
                    mTaskDisciplinas.execute();
                }

            }

            isConnected = false;
            //getActivity().invalidateOptionsMenu();

            return false;
        }

    }
}
