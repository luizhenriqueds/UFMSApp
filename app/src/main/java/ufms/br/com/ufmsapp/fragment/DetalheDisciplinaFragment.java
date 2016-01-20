package ufms.br.com.ufmsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Professor;
import ufms.br.com.ufmsapp.pojo.TipoDisciplina;


public class DetalheDisciplinaFragment extends Fragment {

    protected TextView disciplinaEmenta;
    protected TextView disciplinaCargaHoraria;
    protected TextView disciplinaTipo;
    protected TextView disciplinaRatingValue;
    protected TextView disciplinaProfessor;
    protected RatingBar disciplinaRating;
    protected Disciplina disciplina;
    protected static final String CARGA_HORARIA_UN_TEMPO = "h";

    public DetalheDisciplinaFragment() {
    }

    public static DetalheDisciplinaFragment newInstance() {
        return new DetalheDisciplinaFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhe_disciplina, container, false);

        disciplina = getActivity().getIntent().getParcelableExtra(DisciplinasFragment.DISCIPLINA_EXTRA);


        disciplinaEmenta = (TextView) view.findViewById(R.id.disciplina_details);
        disciplinaCargaHoraria = (TextView) view.findViewById(R.id.disciplina_detalhes_carga_horaria);
        disciplinaTipo = (TextView) view.findViewById(R.id.disciplina_detalhes_tipo);
        disciplinaProfessor = (TextView) view.findViewById(R.id.disciplina_detalhes_professor);
        disciplinaRatingValue = (TextView) view.findViewById(R.id.disciplina_detalhes_rating_value);
        disciplinaRating = (RatingBar) view.findViewById(R.id.disciplina_detalhes_rating_bar);


        if (disciplina != null) {

            TipoDisciplina tipoDisciplina = MyApplication.getWritableDatabase().tipoDisciplinaById(disciplina.getTipoDisciplina());
            Professor professor = MyApplication.getWritableDatabase().professorById(disciplina.getProfessor());

            disciplinaEmenta.setText(disciplina.getDescricao());
            disciplinaTipo.setText(tipoDisciplina.getTipoDisciplinaDescricao());
            disciplinaProfessor.setText(professor.getNome());
            disciplinaCargaHoraria.setText(String.valueOf(disciplina.getCargaHoraria() + CARGA_HORARIA_UN_TEMPO));

            disciplinaRating.setRating(3.2F);
            disciplinaRating.setAlpha(1.0F);

            disciplinaRatingValue.setText(String.valueOf(3.2F));
        }


        return view;
    }

}
