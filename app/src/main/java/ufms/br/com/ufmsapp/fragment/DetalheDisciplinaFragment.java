package ufms.br.com.ufmsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.dialog.AvaliarDisciplinaDialog;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Professor;
import ufms.br.com.ufmsapp.pojo.RatingDisciplina;
import ufms.br.com.ufmsapp.pojo.TipoDisciplina;


public class DetalheDisciplinaFragment extends Fragment implements AvaliarDisciplinaDialog.OnRatingDisciplinaListener, View.OnClickListener {

    protected TextView disciplinaEmenta;
    protected TextView disciplinaCargaHoraria;
    protected TextView disciplinaTipo;
    protected TextView disciplinaRatingValue;
    protected TextView disciplinaProfessor;
    protected RatingBar disciplinaRating;
    protected Disciplina disciplina;
    protected static final String CARGA_HORARIA_UN_TEMPO = "h";
    protected static final String DIALOG_TAG = "ratingDialog";
    public static final String DIALOG_EDIT_TAG = "editRatingDialog";
    public static final String DIALOG_MODE = "dialogMode";
    protected Button btnRate;
    protected ImageButton btnEditRating;
    protected TextView disciplinaRatingCount;
    protected TextView disciplinaRatingStatus;
    protected ImageView disciplinaRatingCountImg;
    private RatingDisciplina ratingDisciplina;

    public DetalheDisciplinaFragment() {
    }

    public static DetalheDisciplinaFragment newInstance() {
        return new DetalheDisciplinaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setRetainInstance(true);

        View view = inflater.inflate(R.layout.fragment_detalhe_disciplina, container, false);

        disciplina = getActivity().getIntent().getParcelableExtra(DisciplinasFragment.DISCIPLINA_EXTRA);


        disciplinaEmenta = (TextView) view.findViewById(R.id.disciplina_details);
        disciplinaCargaHoraria = (TextView) view.findViewById(R.id.disciplina_detalhes_carga_horaria);
        disciplinaTipo = (TextView) view.findViewById(R.id.disciplina_detalhes_tipo);
        disciplinaProfessor = (TextView) view.findViewById(R.id.disciplina_detalhes_professor);
        disciplinaRatingValue = (TextView) view.findViewById(R.id.disciplina_detalhes_rating_value);
        disciplinaRatingCount = (TextView) view.findViewById(R.id.disciplina_detalhes_rating_count);
        disciplinaRatingStatus = (TextView) view.findViewById(R.id.txt_rating_status);
        disciplinaRatingCountImg = (ImageView) view.findViewById(R.id.img_count_rating);
        btnEditRating = (ImageButton) view.findViewById(R.id.btn_editar_rating);
        disciplinaRating = (RatingBar) view.findViewById(R.id.disciplina_detalhes_rating_bar);
        btnRate = (Button) view.findViewById(R.id.btn_rate);


        if (disciplina != null) {

            TipoDisciplina tipoDisciplina = MyApplication.getWritableDatabase().tipoDisciplinaById(disciplina.getTipoDisciplina());
            Professor professor = MyApplication.getWritableDatabase().professorById(disciplina.getProfessor());

            disciplinaEmenta.setText(disciplina.getDescricao());
            disciplinaTipo.setText(tipoDisciplina.getTipoDisciplinaDescricao());
            disciplinaProfessor.setText(professor.getNome());
            disciplinaCargaHoraria.setText(String.valueOf(disciplina.getCargaHoraria() + CARGA_HORARIA_UN_TEMPO));

            ratingDisciplina = MyApplication.getWritableDatabase().getRatingDisciplina(1, disciplina.getIdDisciplinaServidor());

            if (ratingDisciplina != null) {
                setRatingBar(false, ratingDisciplina.getRating(), disciplina.getIdDisciplinaServidor());

            } else {
                btnEditRating.setVisibility(View.GONE);
                btnRate.setEnabled(true);

                disciplinaRatingStatus.setVisibility(View.VISIBLE);
                disciplinaRatingStatus.setText(R.string.txt_disciplina_rating_status);

                disciplinaRatingValue.setVisibility(View.GONE);
                disciplinaRatingCount.setVisibility(View.GONE);
                disciplinaRatingCountImg.setVisibility(View.GONE);

                disciplinaRating.setIsIndicator(false);
                disciplinaRating.setRating(0);


            }

            btnRate.setOnClickListener(this);

        }


        return view;
    }

    private void setRatingBar(boolean btnEnable, float rating, int idDisciplina) {
        btnEditRating.setVisibility(View.VISIBLE);
        btnRate.setEnabled(btnEnable);

        disciplinaRating.setRating(rating);
        disciplinaRating.setAlpha(1.0F);

        disciplinaRating.setIsIndicator(true);

        disciplinaRatingValue.setVisibility(View.VISIBLE);
        disciplinaRatingCount.setVisibility(View.VISIBLE);
        disciplinaRatingCountImg.setVisibility(View.VISIBLE);

        disciplinaRatingValue.setText(String.valueOf(rating));

        int ratingCount = MyApplication.getWritableDatabase().getRatingDisciplinaCount(idDisciplina);

        disciplinaRatingCount.setText(String.valueOf(ratingCount));
        disciplinaRatingStatus.setVisibility(View.GONE);

        btnEditRating.setOnClickListener(this);
    }


    @Override
    public void onRatingDisciplina(int idAluno, int idDisciplina, float rating) {
        setRatingBar(false, rating, idDisciplina);
    }

    @Override
    public void onClick(View v) {

        int createMode = 0;
        int editMode = 1;

        Bundle bundle = new Bundle();

        switch (v.getId()) {
            case R.id.btn_editar_rating:
                ratingDisciplina = MyApplication.getWritableDatabase().getRatingDisciplina(1, disciplina.getIdDisciplinaServidor());
                AvaliarDisciplinaDialog editDialog = AvaliarDisciplinaDialog.newInstance(1, disciplina.getIdDisciplinaServidor(), ratingDisciplina.getRating(), DetalheDisciplinaFragment.this, disciplinaRating);
                bundle.putInt(DIALOG_MODE, editMode);
                editDialog.setArguments(bundle);
                editDialog.show(getActivity().getFragmentManager(), DIALOG_EDIT_TAG);
                break;

            case R.id.btn_rate:
                AvaliarDisciplinaDialog dialog = AvaliarDisciplinaDialog.newInstance(1, disciplina.getIdDisciplinaServidor(), disciplinaRating.getRating(), DetalheDisciplinaFragment.this, disciplinaRating);
                bundle.putInt(DIALOG_MODE, createMode);
                dialog.setArguments(bundle);
                dialog.show(getActivity().getFragmentManager(), DIALOG_TAG);
                break;
        }


    }
}
