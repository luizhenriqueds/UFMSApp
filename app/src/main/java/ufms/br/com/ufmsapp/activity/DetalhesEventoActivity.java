package ufms.br.com.ufmsapp.activity;


import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.adapter.EventosAdapter;
import ufms.br.com.ufmsapp.adapter.MateriaisAdapter;
import ufms.br.com.ufmsapp.extras.UrlEndpoints;
import ufms.br.com.ufmsapp.fragment.EventosFragment;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.pojo.Material;
import ufms.br.com.ufmsapp.pojo.Professor;
import ufms.br.com.ufmsapp.pojo.TipoEvento;
import ufms.br.com.ufmsapp.pojo.TituloProfessor;


public class DetalhesEventoActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected Evento evento;
    protected Disciplina disciplina;
    protected TextView tipoEvento;
    protected TextView tituloEvento;
    protected TextView descricaoEvento;
    protected TextView tituloDisciplina;
    protected TextView nomeProfessor;
    protected TextView tituloModoPaisagem;
    protected TextView dataLimiteEvento;
    protected ImageView imageEvento;
    protected RecyclerView eventoUploads;

    protected com.squareup.picasso.Target mPicassoTarget;
    protected AppBarLayout mAppBar;
    protected CollapsingToolbarLayout mCollapsingToolbarLayout;
    protected CoordinatorLayout mCoordinator;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "br"));


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detalhes_evento);

        initViews();

        evento = getIntent().getParcelableExtra(EventosFragment.EVENTO_EXTRA);

        disciplina = MyApplication.getWritableDatabase().disciplinaById(evento.getDisciplina());

        setFieldsDetails(evento);

        setupToolbar(evento.getTitulo());

        loadEventImage();

        setEnterAnimation();

        eventoUploads = (RecyclerView) findViewById(R.id.recycler_uploads_evento);
        eventoUploads.setNestedScrollingEnabled(false);
        eventoUploads.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<Material> uploads = MyApplication.getWritableDatabase().listarMateriaisByEvento(evento.getIdEventoServidor());

        MateriaisAdapter adapter = new MateriaisAdapter(this);
        //adapter.setMaterialClickListener(this);
        adapter.setUploadList(uploads);

        eventoUploads.setAdapter(adapter);

    }


    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mCoordinator = (CoordinatorLayout) findViewById(R.id.coordinator);
        mAppBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        tipoEvento = (TextView) findViewById(R.id.detalhes_evento_tipo_evento);
        tituloEvento = (TextView) findViewById(R.id.detalhes_evento_title);
        descricaoEvento = (TextView) findViewById(R.id.detalhes_evento_description);
        tituloDisciplina = (TextView) findViewById(R.id.detalhes_disciplina_title);
        nomeProfessor = (TextView) findViewById(R.id.detalhes_disciplina_professor);
        tituloModoPaisagem = (TextView) findViewById(R.id.image_title);
        dataLimiteEvento = (TextView) findViewById(R.id.detalhes_evento_deadline);
        imageEvento = (ImageView) findViewById(R.id.img_detalhes);
    }

    private void setFieldsDetails(Evento evento) {
        if (evento != null) {

            TipoEvento tipoEventoObj = MyApplication.getWritableDatabase().tipoEventoById(evento.getTipoEvento());

            Disciplina disciplina = MyApplication.getWritableDatabase().disciplinaById(evento.getDisciplina());

            Professor professor = MyApplication.getWritableDatabase().professorById(disciplina.getProfessor());

            TituloProfessor tituloProfessor = MyApplication.getWritableDatabase().tituloProfessorById(professor.getTituloProfessor());

            String strProfessor = tituloProfessor.getTituloProfessor().concat(" " + professor.getNome());

            tipoEvento.setText(tipoEventoObj.getNomeTipoEvento());
            nomeProfessor.setText(strProfessor);
            tituloEvento.setText(evento.getTitulo());
            descricaoEvento.setText(evento.getDescricao());
            tituloDisciplina.setText(disciplina.getTitulo());
            dataLimiteEvento.setText(dateFormat.format(evento.getDataLimiteEvento()));
        }
    }

    private void loadEventImage() {
        if (mPicassoTarget == null) {
            mPicassoTarget = new com.squareup.picasso.Target() {

                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imageEvento.setImageBitmap(bitmap);
                    startEnterAnimation(mCoordinator);
                    applyPalette(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {
                    startEnterAnimation(mCoordinator);
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                }
            };
            Picasso.with(this)
                    .load(UrlEndpoints.URL_ENDPOINT + evento.getBigIcon())
                    .into(mPicassoTarget);

        }
    }

    private void startEnterAnimation(final View sharedElement) {
        sharedElement.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        sharedElement.getViewTreeObserver().removeOnPreDrawListener(this);
                        ActivityCompat.startPostponedEnterTransition(DetalhesEventoActivity.this);
                        return true;
                    }
                });
    }


    private void setEnterAnimation() {


        ViewCompat.setTransitionName(imageEvento, EventosAdapter.SHARED_TRANSITION_ICON_KEY);
        //ViewCompat.setTransitionName(tituloEvento, EventosAdapter.SHARED_TRANSITION_TITLE_KEY);
        ActivityCompat.postponeEnterTransition(this);
    }

    public void setupToolbar(String titulo) {
        // if landscape

        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar toolbar = getSupportActionBar();

        if (mAppBar != null) {
            if (mAppBar.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mAppBar.getLayoutParams();
                //noinspection SuspiciousNameCombination
                params.height = getResources().getDisplayMetrics().widthPixels;
            }
        }

        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        if (mCollapsingToolbarLayout != null) {
            if (toolbar != null) {
                toolbar.setDisplayShowTitleEnabled(true);
            }

            mCollapsingToolbarLayout.setTitle(titulo);
        } else {
            if (toolbar != null) {
                toolbar.setDisplayShowTitleEnabled(false);

                tituloModoPaisagem.setText(evento.getTitulo());
            }
        }

        /*if (!OrientationUtils.isPortrait(getResources().getConfiguration())) {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);

                tituloModoPaisagem.setText(evento.getTitulo());
            }
        } else {

            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
            }

            mCollapsingToolbarLayout.setTitle(titulo);*/

        // ImageView imageView = (ImageView) findViewById(R.id.img_detalhes);
           /* Bitmap bitmap = ((BitmapDrawable) imageEvento.getDrawable()).getBitmap();

            Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                public void onGenerated(Palette palette) {
                    applyPalette(palette);
                }
            });*/

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Intent parentIntent = NavUtils.getParentActivityIntent(this);
                // parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                // startActivity(parentIntent);
                supportFinishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void applyPalette(Bitmap bitmap) {

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                int primaryDark = getResources().getColor(R.color.primaryColorDark);
                int primary = getResources().getColor(R.color.primaryColor);
                if (mCollapsingToolbarLayout != null) {
                    mCollapsingToolbarLayout.setContentScrimColor(palette.getMutedColor(primary));
                    mCollapsingToolbarLayout.setStatusBarScrimColor(palette.getDarkMutedColor(primaryDark));
                }

                // supportStartPostponedEnterTransition();
                startEnterAnimation(mCoordinator);
            }
        });

    }

    private void initActivityTransitions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Slide transition = new Slide();
            transition.excludeTarget(android.R.id.statusBarBackground, true);
            getWindow().setEnterTransition(transition);
            getWindow().setReturnTransition(transition);
        }
    }


}



