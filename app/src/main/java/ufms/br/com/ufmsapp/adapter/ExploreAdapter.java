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

package ufms.br.com.ufmsapp.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;

public class ExploreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_EVENTOS = 0;
    private static final int TYPE_DISCIPLINAS = 1;

    Disciplina disciplina;
    private ArrayList<Disciplina> disciplinaList;
    LayoutInflater inflater;
    View itemView;

    Evento evento;
    private ArrayList<Evento> eventoList;

    public ExploreAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_EVENTOS) {
            View eventosItemView = inflater.inflate(R.layout.eventos_adapter_row, parent, false);
            //itemView.setOnClickListener(this);

            return new EventosViewHolder(eventosItemView);
        } else {
            View disciplinasItemView = inflater.inflate(R.layout.disciplinas_adapter_row, parent, false);
            //  itemView.setOnClickListener(this);

            return new DisciplinasViewHolder(disciplinasItemView);
        }


    }

   /* public void deleteFromList(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }*/

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return TYPE_EVENTOS;
        } else {
            return TYPE_DISCIPLINAS;
        }
    }

    public void setEventoList(ArrayList<Evento> eventoList) {
        this.eventoList = eventoList;
        notifyItemRangeChanged(0, eventoList.size());
    }

    public void setDisciplinaList(ArrayList<Disciplina> disciplinaList) {
        this.disciplinaList = disciplinaList;
        notifyItemRangeChanged(0, disciplinaList.size());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof EventosViewHolder) {

            EventosViewHolder eventosViewHolder = (EventosViewHolder) holder;
            evento = eventoList.get(position);

            eventosViewHolder.eventoItemTitle.setText(evento.getTitulo());
            eventosViewHolder.eventoItemSubtitle.setText(evento.getDescricao());

        } else {

            DisciplinasViewHolder disciplinasViewHolder = (DisciplinasViewHolder) holder;
            disciplina = disciplinaList.get(position);

            disciplinasViewHolder.disciplinaTitle.setText(disciplina.getTitulo());
            disciplinasViewHolder.disciplinaDescription.setText(disciplina.getDescricao());
        }


    }

    @Override
    public int getItemCount() {

        return 3;
    }

    public static class DisciplinasViewHolder extends RecyclerView.ViewHolder {
        protected TextView disciplinaTitle;
        protected TextView disciplinaDescription;
        //protected LinearLayout mCardTopLayout;
        //protected CardView mCardItemLayout;

        public DisciplinasViewHolder(View v) {
            super(v);
            disciplinaTitle = (TextView) v.findViewById(R.id.txt_title_disciplina);
            disciplinaDescription = (TextView) v.findViewById(R.id.txt_subtitle_disciplina);

        }
    }


    public static class EventosViewHolder extends RecyclerView.ViewHolder {
        protected TextView eventoItemTitle;
        protected TextView eventoItemSubtitle;
        //protected LinearLayout mCardTopLayout;
        //protected CardView mCardItemLayout;

        public EventosViewHolder(View v) {
            super(v);
            eventoItemTitle = (TextView) v.findViewById(R.id.evento_item_title);
            eventoItemSubtitle = (TextView) v.findViewById(R.id.evento_item_subtitle);

        }
    }


}
