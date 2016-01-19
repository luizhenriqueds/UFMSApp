package ufms.br.com.ufmsapp.activity;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.fragment.DetalhesEventoFragment;
import ufms.br.com.ufmsapp.fragment.EventosFragment;
import ufms.br.com.ufmsapp.fragment.MateriaisEventoFragment;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;


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
        // if landscape

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


}



