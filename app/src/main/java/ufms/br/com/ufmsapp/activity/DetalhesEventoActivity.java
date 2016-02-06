package ufms.br.com.ufmsapp.activity;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.extras.UrlEndpoints;
import ufms.br.com.ufmsapp.fragment.DetalhesEventoFragment;
import ufms.br.com.ufmsapp.fragment.EventosFragment;
import ufms.br.com.ufmsapp.fragment.MateriaisEventoFragment;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.utils.VersionUtils;


public class DetalhesEventoActivity extends AppCompatActivity {

    protected Toolbar toolbar;
    protected Evento evento;
    protected Disciplina disciplina;

    protected TabLayout mTabs;
    protected ViewPager mPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detalhes_evento);

        evento = getIntent().getParcelableExtra(EventosFragment.EVENTO_EXTRA);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mTabs = (TabLayout) findViewById(R.id.tabs);
        mPager = (ViewPager) findViewById(R.id.viewpager);

        setupToolbar(evento.getTitulo());

        setupViewPager(mPager);

        mTabs.setupWithViewPager(mPager);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void setupToolbar(String titulo) {
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar toolbar = getSupportActionBar();


        if (toolbar != null) {
            toolbar.setDisplayHomeAsUpEnabled(true);
        }

        if (toolbar != null) {

            new ImageLoaderWorker().execute(UrlEndpoints.URL_ENDPOINT + evento.getSmallIcon());

            toolbar.setTitle(titulo);
            toolbar.setDisplayShowTitleEnabled(true);
        }
    }

    public void setColor(Bitmap bitmap) {
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int defaultColor = 0x000000;

                int lightMutedColor = palette.getLightMutedColor(defaultColor);
                int mutedColor = palette.getMutedColor(defaultColor);

                if (VersionUtils.isGraterThanLollipop()) {
                    getWindow().setStatusBarColor(mutedColor);
                }
                mTabs.setBackgroundColor(lightMutedColor);
                toolbar.setBackgroundColor(lightMutedColor);
            }
        });
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


    private void setupViewPager(ViewPager viewPager) {

        String[] tabs = getResources().getStringArray(R.array.tabs_eventos);

        DetalhesEventoFragment detalhesEventoFragment = DetalhesEventoFragment.newInstance();

        MateriaisEventoFragment materiaisEventoFragment = MateriaisEventoFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putParcelable(EventosFragment.EVENTO_EXTRA, evento);

        detalhesEventoFragment.setArguments(bundle);
        materiaisEventoFragment.setArguments(bundle);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(detalhesEventoFragment, tabs[0]);
        adapter.addFragment(materiaisEventoFragment, tabs[1]);
        viewPager.setAdapter(adapter);
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    private class ImageLoaderWorker extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap image = null;

            try {
                image = Picasso.with(DetalhesEventoActivity.this).load(params[0]).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return image;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            setColor(bitmap);
        }


    }

}



