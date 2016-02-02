package ufms.br.com.ufmsapp.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.adapter.AtividadeNotaAdapter;
import ufms.br.com.ufmsapp.fragment.NotasFragment;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Nota;
import ufms.br.com.ufmsapp.utils.OrientationUtils;

public class NotasAtividadesActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected Disciplina disciplina;
    protected ArrayList<Nota> notas;
    private AtividadeNotaAdapter adapter;
    private RecyclerView recyclerNotas;

    private TextView emptyListText;
    private ImageView emptyListImg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notas_atividades_layout_adapter);

        disciplina = getIntent().getParcelableExtra(NotasFragment.DISCIPLINA_EXTRA);
        recyclerNotas = (RecyclerView) findViewById(R.id.recycler_notas);

        emptyListText = (TextView) findViewById(R.id.txt_no_atividade);
        emptyListImg = (ImageView) findViewById(R.id.atividade_no_item);

        if (OrientationUtils.isPortrait(getResources().getConfiguration())) {
            recyclerNotas.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerNotas.setLayoutManager(new GridLayoutManager(this, 2));
        }


        toolbar = (Toolbar) findViewById(R.id.toolbar);

        adapter = new AtividadeNotaAdapter(this);

        setupToolbar(disciplina.getTitulo());

        notas = MyApplication.getWritableDatabase().listarNotas(disciplina.getIdDisciplinaServidor());

        adapter.setNotasList(notas);

        updateList();


    }

    private void setupToolbar(String titulo) {
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar toolbar = getSupportActionBar();


        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        if (toolbar != null) {
            toolbar.setTitle(titulo);
            toolbar.setDisplayShowTitleEnabled(true);
        }
    }

    public void updateList() {
        recyclerNotas.setAdapter(adapter);
        setupRecyclerView();
    }

    private void checkAdapterIsEmpty() {

        if (adapter.getItemCount() == 0) {
            emptyListImg.setVisibility(View.VISIBLE);
            emptyListText.setVisibility(View.VISIBLE);
            emptyListText.setText(R.string.txt_atividade_empty);
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
