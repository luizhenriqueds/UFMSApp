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

import java.util.ArrayList;
import java.util.List;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.fragment.AlunosDisciplinaFragment;
import ufms.br.com.ufmsapp.fragment.DetalheDisciplinaFragment;
import ufms.br.com.ufmsapp.fragment.DisciplinasFragment;
import ufms.br.com.ufmsapp.fragment.MateriaisDisciplinaFragment;
import ufms.br.com.ufmsapp.pojo.Disciplina;

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

        disciplina = getIntent().getParcelableExtra(DisciplinasFragment.DISCIPLINA_EXTRA);

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
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
