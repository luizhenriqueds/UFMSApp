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
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Professor;
import ufms.br.com.ufmsapp.pojo.RatingDisciplina;

public class DisciplinasAdapter extends RecyclerView.Adapter<DisciplinasAdapter.DisciplinaViewHolder> implements View.OnClickListener {

    Disciplina disciplina;
    private ArrayList<Disciplina> disciplinasList;
    LayoutInflater inflater;
    View itemView;
    private int lastPosition = -1;
    private OnDisciplinaClickListener mListener;
    VolleySingleton volleySingleton;
    ImageLoader imageLoader;

    private DateFormat df = new SimpleDateFormat("dd MMM", new Locale("pt", "br"));


    public DisciplinasAdapter(Context context) {
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
                inflate(R.layout.disciplina_list_adapter_row, viewGroup, false);
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

        Professor professor = MyApplication.getWritableDatabase().professorById(disciplina.getProfessor());

        RatingDisciplina rating = MyApplication.getWritableDatabase().getRatingDisciplina(disciplina.getIdDisciplinaServidor());

        float ratingAVG = MyApplication.getWritableDatabase().getRatingDisciplinaAVG(disciplina.getIdDisciplinaServidor());

        disciplinaViewHolder.disciplinaTitle.setText(disciplina.getTitulo());
        disciplinaViewHolder.disciplinaProfessor.setText(professor.getNome());
        disciplinaViewHolder.disciplinaDescricao.setText(disciplina.getDescricao());

        if (rating != null && rating.getRating() > 0.0) {
            disciplinaViewHolder.disciplinaRatingBar.setVisibility(View.VISIBLE);

            Drawable drawable = disciplinaViewHolder.disciplinaRatingBar.getProgressDrawable();
            drawable.setColorFilter(Color.parseColor("#757575"), PorterDuff.Mode.SRC_ATOP);

            disciplinaViewHolder.disciplinaScore.setText(String.format("%.01f", ratingAVG));
        } else {
            disciplinaViewHolder.disciplinaRatingBar.setVisibility(View.GONE);
            disciplinaViewHolder.disciplinaScore.setText(null);
        }


        setAnimation(disciplinaViewHolder.disciplinaAdapterLayout, i);

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
        protected LinearLayout disciplinaAdapterLayout;
        protected TextView disciplinaTitle;
        protected TextView disciplinaProfessor;
        protected TextView disciplinaDescricao;
        protected TextView disciplinaScore;
        protected ImageView disciplinaSmallIcon;
        protected RatingBar disciplinaRatingBar;


        public DisciplinaViewHolder(View v) {
            super(v);
            disciplinaSmallIcon = (ImageView) v.findViewById(R.id.disciplina_small_icon);
            disciplinaAdapterLayout = (LinearLayout) v.findViewById(R.id.disciplina_adapter_layout);
            disciplinaTitle = (TextView) v.findViewById(R.id.txt_disciplina_title);
            disciplinaProfessor = (TextView) v.findViewById(R.id.txt_disciplina_professor);
            disciplinaDescricao = (TextView) v.findViewById(R.id.txt_disciplina_descricao);
            disciplinaScore = (TextView) v.findViewById(R.id.disciplina_score);
            disciplinaRatingBar = (RatingBar) v.findViewById(R.id.adapter_rating_bar);

        }
    }

}
