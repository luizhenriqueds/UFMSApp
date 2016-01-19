package ufms.br.com.ufmsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ufms.br.com.ufmsapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListEventosFragment extends Fragment {

    Toolbar toolbar;


    public ListEventosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_eventos, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar_list_eventos);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        toolbar.setTitle("TEst");
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        return view;
    }

}
