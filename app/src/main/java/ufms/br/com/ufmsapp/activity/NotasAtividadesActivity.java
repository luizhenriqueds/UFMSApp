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

package ufms.br.com.ufmsapp.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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
import ufms.br.com.ufmsapp.utils.Constants;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notas_grafico_view_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notas_atividades_layout_adapter);

        //disciplina = getIntent().getParcelableExtra(NotasFragment.DISCIPLINA_EXTRA);
        int disciplinaId = -1;
        if (getIntent().getStringExtra(Constants.NOTA_CREATED_EXTRA) != null) {
            disciplinaId = Integer.parseInt(getIntent().getStringExtra(Constants.NOTA_CREATED_EXTRA));
        }

        if (getIntent().getParcelableExtra(NotasFragment.DISCIPLINA_EXTRA) != null) {
            disciplina = getIntent().getParcelableExtra(NotasFragment.DISCIPLINA_EXTRA);
        } else {
            disciplina = MyApplication.getWritableDatabase().disciplinaById(disciplinaId);
        }

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


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentIntent = NavUtils.getParentActivityIntent(this);
                parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(parentIntent);

                supportFinishAfterTransition();
                break;
            case R.id.action_desempenho:
                startActivity(new Intent(this, GraficosActivity.class));
                break;

            //return true;
        }
        return super.onOptionsItemSelected(item);
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
