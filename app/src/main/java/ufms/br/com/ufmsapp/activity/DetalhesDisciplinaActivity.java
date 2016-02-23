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
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
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

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.extras.UrlEndpoints;
import ufms.br.com.ufmsapp.fragment.AlunosDisciplinaFragment;
import ufms.br.com.ufmsapp.fragment.DetalheDisciplinaFragment;
import ufms.br.com.ufmsapp.fragment.DisciplinasFragment;
import ufms.br.com.ufmsapp.fragment.MateriaisDisciplinaFragment;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.utils.Constants;
import ufms.br.com.ufmsapp.utils.VersionUtils;

public class DetalhesDisciplinaActivity extends AppCompatActivity {


    protected TabLayout mTabs;
    protected ViewPager mPager;
    protected Toolbar toolbar;
    private Disciplina disciplina;
    ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_disciplinas);

        toolbar = (Toolbar) findViewById(R.id.detalhes_disciplina_toolbar);
        mTabs = (TabLayout) findViewById(R.id.tabs);
        mPager = (ViewPager) findViewById(R.id.viewpager);

        int disciplinaId = -1;
        if (getIntent().getStringExtra(Constants.DISCIPLINA_CREATED_EXTRA) != null) {
            disciplinaId = Integer.parseInt(getIntent().getStringExtra(Constants.DISCIPLINA_CREATED_EXTRA));
        }

        if (getIntent().getParcelableExtra(DisciplinasFragment.DISCIPLINA_EXTRA) != null) {
            disciplina = getIntent().getParcelableExtra(DisciplinasFragment.DISCIPLINA_EXTRA);
        } else {
            disciplina = MyApplication.getWritableDatabase().disciplinaById(disciplinaId);
        }


        setupToolbar();

        setupViewPager(mPager);

        //mTabs.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.whiteTextColor));

        mTabs.setupWithViewPager(mPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void setupToolbar() {
        toolbar.setTitle(disciplina.getTitulo());

        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            new ImageLoaderWorker().execute(UrlEndpoints.URL_ENDPOINT + "ws_images/edit.png");
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    private void setupViewPager(ViewPager viewPager) {

        String[] tabs = getResources().getStringArray(R.array.tabs);

        DetalheDisciplinaFragment detalhesFragment = DetalheDisciplinaFragment.newInstance();

        AlunosDisciplinaFragment alunosFragment = AlunosDisciplinaFragment.newInstance();

        MateriaisDisciplinaFragment materiaisDisciplinaFragment = MateriaisDisciplinaFragment.newInstance();

        Bundle bundle = new Bundle();
        bundle.putParcelable(DisciplinasFragment.DISCIPLINA_EXTRA, disciplina);

        detalhesFragment.setArguments(bundle);
        alunosFragment.setArguments(bundle);
        materiaisDisciplinaFragment.setArguments(bundle);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(detalhesFragment, tabs[0]);
        adapter.addFragment(alunosFragment, tabs[1]);
        adapter.addFragment(materiaisDisciplinaFragment, tabs[2]);
        viewPager.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent parentIntent = NavUtils.getParentActivityIntent(this);
                parentIntent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(parentIntent);

                supportFinishAfterTransition();

                return true;
        }
        return super.onOptionsItemSelected(item);
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
                image = Picasso.with(DetalhesDisciplinaActivity.this).load(params[0]).get();
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
