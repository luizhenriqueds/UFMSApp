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
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Aluno;
import ufms.br.com.ufmsapp.pojo.AlunoXDisciplina;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.TipoDisciplina;
import ufms.br.com.ufmsapp.pojo.Turma;
import ufms.br.com.ufmsapp.preferences.UserSessionPreference;

public class ListaDisciplinaNotasAdapter extends RecyclerView.Adapter<ListaDisciplinaNotasAdapter.DisciplinaViewHolder> implements View.OnClickListener {

    Disciplina disciplina;
    private ArrayList<Disciplina> disciplinasList;
    LayoutInflater inflater;
    View itemView;
    private int lastPosition = -1;
    private OnDisciplinaClickListener mListener;
    VolleySingleton volleySingleton;
    ImageLoader imageLoader;

    public ListaDisciplinaNotasAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    @Override
    public int getItemCount() {
        return disciplinasList.size();
    }

    public void setDisciplinaClickListener(OnDisciplinaClickListener listener) {
        mListener = listener;
    }


    @Override
    public DisciplinaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.layout_notas_adapter_row, viewGroup, false);
        DisciplinaViewHolder vh = new DisciplinaViewHolder(itemView);
        itemView.setTag(vh);
        itemView.setOnClickListener(this);

        return vh;
    }

    public void setDisciplinasList(ArrayList<Disciplina> disciplinasList) {
        this.disciplinasList = disciplinasList;
        notifyItemRangeChanged(0, disciplinasList.size());
    }


    @Override
    public void onBindViewHolder(DisciplinaViewHolder disciplinaViewHolder, int i) {
        disciplina = disciplinasList.get(i);

        TipoDisciplina tipoDisciplina = MyApplication.getWritableDatabase().tipoDisciplinaById(disciplina.getTipoDisciplina());

        UserSessionPreference prefs = new UserSessionPreference(itemView.getContext());


        Aluno aluno = null;
        if (!prefs.isFirstTime()) {
            aluno = MyApplication.getWritableDatabase().alunoByEmail(prefs.getEmail());
        }

        AlunoXDisciplina matricula = null;
        if (aluno != null && disciplina != null) {
            matricula = MyApplication.getWritableDatabase().getMatriculaAluno(aluno.getAlunoIdServidor(), disciplina.getIdDisciplinaServidor());

        }


        disciplinaViewHolder.disciplinaTitle.setText(disciplina.getTitulo());
        disciplinaViewHolder.tipoDisciplina.setText(tipoDisciplina.getTipoDisciplinaDescricao());


        if (matricula != null) {
            Turma turma = MyApplication.getWritableDatabase().getTurmaById(matricula.getTurma());

            float media = MyApplication.getWritableDatabase().getNotaMediaDisciplina(matricula.getAlunoXDisciplinaIdServidor());

            if (media != 0.0) {
                disciplinaViewHolder.notaDisciplina.setVisibility(View.VISIBLE);
                disciplinaViewHolder.notaDisciplina.setText(String.format("%.01f", media));
            } else {
                disciplinaViewHolder.notaDisciplina.setVisibility(View.GONE);
            }

            if (turma != null) {
                disciplinaViewHolder.periodoDisciplina.setVisibility(View.VISIBLE);
                disciplinaViewHolder.periodoDisciplina.setText(turma.getNomeTurma());
            } else {
                disciplinaViewHolder.periodoDisciplina.setVisibility(View.GONE);
            }
        } else {
            disciplinaViewHolder.notaDisciplina.setVisibility(View.GONE);
        }

        setAnimation(disciplinaViewHolder.rootLayout, i);

    }

    @Override
    public void onClick(final View view) {
        if (mListener != null) {
            DisciplinaViewHolder vh = (DisciplinaViewHolder) view.getTag();
            int position = vh.getAdapterPosition();
            mListener.onDisciplinaClick(view, position, disciplinasList.get(position));
        }

    }

    public interface OnDisciplinaClickListener {
        void onDisciplinaClick(View v, int position, Disciplina disciplina);
    }


    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public static class DisciplinaViewHolder extends RecyclerView.ViewHolder {
        protected TextView disciplinaTitle;
        protected TextView tipoDisciplina;
        protected TextView periodoDisciplina;
        protected TextView notaDisciplina;
        protected CardView rootLayout;


        public DisciplinaViewHolder(View v) {
            super(v);
            rootLayout = (CardView) v.findViewById(R.id.notas_root_layout);
            disciplinaTitle = (TextView) v.findViewById(R.id.txt_nota_nome_disciplina);
            tipoDisciplina = (TextView) v.findViewById(R.id.txt_nota_tipo_disciplina);
            periodoDisciplina = (TextView) v.findViewById(R.id.txt_nota_periodo_disciplina);
            notaDisciplina = (TextView) v.findViewById(R.id.txt_nota_disciplina);

        }
    }

}
