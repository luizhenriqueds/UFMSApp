package ufms.br.com.ufmsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.pojo.Professor;
import ufms.br.com.ufmsapp.pojo.TipoEvento;
import ufms.br.com.ufmsapp.pojo.TituloProfessor;
import ufms.br.com.ufmsapp.preferences.NotifyUserPreference;


public class DetalhesEventoFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    protected Evento evento;
    protected Disciplina disciplina;

    protected TextView tipoEvento;
    protected TextView tituloEvento;
    protected TextView descricaoEvento;
    protected TextView tituloDisciplina;
    protected TextView nomeProfessor;
    protected TextView dataLimiteEvento;
    protected NotifyUserPreference notifyUserPreference;
    protected Switch desativarNotificacaoSwitch;

    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "br"));

    public static DetalhesEventoFragment newInstance() {
        return new DetalhesEventoFragment();
    }

    public DetalhesEventoFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        evento = getActivity().getIntent().getParcelableExtra(EventosFragment.EVENTO_EXTRA);

        View view = inflater.inflate(R.layout.fragment_detalhes_evento_content, container, false);

        notifyUserPreference = new NotifyUserPreference(getActivity());

        tipoEvento = (TextView) view.findViewById(R.id.detalhes_evento_tipo_evento);
        tituloEvento = (TextView) view.findViewById(R.id.detalhes_evento_title);
        descricaoEvento = (TextView) view.findViewById(R.id.detalhes_evento_description);
        tituloDisciplina = (TextView) view.findViewById(R.id.detalhes_disciplina_title);
        nomeProfessor = (TextView) view.findViewById(R.id.detalhes_disciplina_professor);
        dataLimiteEvento = (TextView) view.findViewById(R.id.detalhes_evento_deadline);
        desativarNotificacaoSwitch = (Switch) view.findViewById(R.id.detalhes_evento_switch);
        desativarNotificacaoSwitch.setOnCheckedChangeListener(this);


        if (evento != null) {

            TipoEvento tipoEventoObj = MyApplication.getWritableDatabase().tipoEventoById(evento.getTipoEvento());

            disciplina = MyApplication.getWritableDatabase().disciplinaById(evento.getDisciplina());

            Professor professor = MyApplication.getWritableDatabase().professorById(disciplina.getProfessor());

            TituloProfessor tituloProfessor = MyApplication.getWritableDatabase().tituloProfessorById(professor.getTituloProfessor());

            String strProfessor = tituloProfessor.getTituloProfessor().concat(" " + professor.getNome());

            tipoEvento.setText(tipoEventoObj.getNomeTipoEvento());
            nomeProfessor.setText(strProfessor);
            tituloEvento.setText(evento.getTitulo());
            descricaoEvento.setText(evento.getDescricao());
            tituloDisciplina.setText(disciplina.getTitulo());
            dataLimiteEvento.setText(dateFormat.format(evento.getDataLimiteEvento()));


           /* if (notifyUserPreference.getNotifyUser() == disciplina.getIdDisciplinaServidor()) {
                desativarNotificacaoSwitch.setChecked(true);
            } else {
                desativarNotificacaoSwitch.setChecked(false);
            }*/


        }

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {
            //notifyUserPreference.setNotifyUser(disciplina.getIdDisciplinaServidor());
        } else {
            //notifyUserPreference.setNotifyUser(-1);

        }

    }
}
