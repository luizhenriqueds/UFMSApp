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
import android.support.v4.widget.SwipeRefreshLayout;
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
import ufms.br.com.ufmsapp.activity.NotasAtividadesActivity;
import ufms.br.com.ufmsapp.adapter.ListaDisciplinaNotasAdapter;
import ufms.br.com.ufmsapp.callbacks.DisciplinasLoadedListener;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.task.TaskLoadDisciplinas;
import ufms.br.com.ufmsapp.utils.ConnectionUtils;


public class NotasFragment extends Fragment implements ListaDisciplinaNotasAdapter.OnDisciplinaClickListener, SwipeRefreshLayout.OnRefreshListener, DisciplinasLoadedListener {

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

        setRetainInstance(true);

        filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        receiver = new NetworkChangeReceiver();
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
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

        listDisciplinas = MyApplication.getWritableDatabase().listarDisciplinas(1);

        if (listDisciplinas != null) {
            adapter.setDisciplinasList(listDisciplinas);
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
