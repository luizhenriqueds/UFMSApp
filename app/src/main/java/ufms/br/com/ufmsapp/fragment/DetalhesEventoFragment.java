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

package ufms.br.com.ufmsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.pojo.EventoFavorite;
import ufms.br.com.ufmsapp.pojo.EventoRead;
import ufms.br.com.ufmsapp.pojo.NotifyStatus;
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
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();

        getActivity().getMenuInflater().inflate(R.menu.detalhe_evento_menu, menu);

        MenuItem item = menu.findItem(R.id.action_mark_favorite);

        EventoFavorite eventoFavorite = MyApplication.getWritableDatabase().eventoFavoriteById(evento.getIdEventoServidor());

        if (eventoFavorite != null) {
            if (eventoFavorite.getEventoFavoriteStatus() == 0) {
                item.setIcon(R.mipmap.ic_favorite_border_white_24dp);
            } else if (eventoFavorite.getEventoFavoriteStatus() == 1) {
                item.setIcon(R.mipmap.ic_favorite_white_24dp);
            }
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        switch (itemId) {
            case R.id.action_mark_favorite:
                if (MyApplication.getWritableDatabase().countEventoFavorite(evento.getIdEventoServidor()) == 0) {
                    MyApplication.getWritableDatabase().setEventoFavorite(new EventoFavorite(0, evento.getIdEventoServidor()));
                } else if (MyApplication.getWritableDatabase().countEventoFavorite(evento.getIdEventoServidor()) == 1) {
                    EventoFavorite eventoFavorite = MyApplication.getWritableDatabase().eventoFavoriteById(evento.getIdEventoServidor());
                    if (eventoFavorite.getEventoFavoriteStatus() == 0) {
                        MyApplication.getWritableDatabase().updateEventoFavorite(new EventoFavorite(1, evento.getIdEventoServidor()));
                    } else {
                        MyApplication.getWritableDatabase().updateEventoFavorite(new EventoFavorite(0, evento.getIdEventoServidor()));
                    }
                }
                getActivity().invalidateOptionsMenu();
                break;
            case R.id.action_mark_as_not_read:
                MyApplication.getWritableDatabase().updateEventoRead(new EventoRead(0, evento.getIdEventoServidor()));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        //evento = getActivity().getIntent().getParcelableExtra(EventosFragment.EVENTO_EXTRA);

        int eventoId = -1;
        if (getActivity().getIntent().getStringExtra("EVENTO_CREATED") != null) {
            eventoId = Integer.parseInt(getActivity().getIntent().getStringExtra("EVENTO_CREATED"));
        }

        if (getActivity().getIntent().getParcelableExtra(EventosFragment.EVENTO_EXTRA) != null) {
            evento = getActivity().getIntent().getParcelableExtra(EventosFragment.EVENTO_EXTRA);
        } else {
            evento = MyApplication.getWritableDatabase().eventoById(eventoId);
        }


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

            if (MyApplication.getWritableDatabase().countNotifyStatus(evento.getIdEventoServidor()) == 0) {
                MyApplication.getWritableDatabase().criarNotificationStatus(new NotifyStatus(0, evento.getIdEventoServidor()));
            }

            NotifyStatus status = MyApplication.getWritableDatabase().notifyById(evento.getIdEventoServidor());

            if (status != null) {
                if (status.getNotifyStatus() == 1) {
                    desativarNotificacaoSwitch.setChecked(true);
                } else if (status.getNotifyStatus() == 0) {
                    desativarNotificacaoSwitch.setChecked(false);
                }
            } else {
                desativarNotificacaoSwitch.setChecked(false);
            }


        }

        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if (isChecked) {
            MyApplication.getWritableDatabase().updateNotifyStatus(new NotifyStatus(1, evento.getIdEventoServidor()));
        } else {
            MyApplication.getWritableDatabase().updateNotifyStatus(new NotifyStatus(0, evento.getIdEventoServidor()));
        }

    }

}
