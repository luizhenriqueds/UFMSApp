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
import android.widget.RatingBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.pojo.Disciplina;

public class ExploreDisciplinasAdapter extends RecyclerView.Adapter<ExploreDisciplinasAdapter.DisciplinasViewHolder> implements View.OnClickListener {

    Disciplina disciplina;
    private ArrayList<Disciplina> disciplinaList;
    LayoutInflater inflater;
    View itemView;
    private int lastPosition = -1;
    private OnExploreDisciplinaClick mListener;

    public ExploreDisciplinasAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return disciplinaList.size();
    }

    public void setExploreDisciplinaListener(OnExploreDisciplinaClick listener) {
        mListener = listener;
    }

    @Override
    public DisciplinasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.disciplinas_adapter_row, viewGroup, false);
        DisciplinasViewHolder vh = new DisciplinasViewHolder(itemView);
        itemView.setTag(vh);

        itemView.setOnClickListener(this);

        return vh;
    }

    public void setDisciplinaList(ArrayList<Disciplina> disciplinaList) {
        this.disciplinaList = disciplinaList;
        notifyItemRangeChanged(0, disciplinaList.size());
    }


    @Override
    public void onBindViewHolder(DisciplinasViewHolder disciplinasViewHolder, int i) {
        disciplina = disciplinaList.get(i);

        disciplinasViewHolder.disciplinaTitle.setText(disciplina.getTitulo());

        float ratingDisciplina = MyApplication.getWritableDatabase().getRatingDisciplinaAVG(disciplina.getIdDisciplinaServidor());

        String matriculas = MyApplication.getWritableDatabase().getMatriculasCount(disciplina.getIdDisciplinaServidor()) + " Aluno(s) Matriculado(s)";

        disciplinasViewHolder.disciplinaDescription.setText(matriculas);

        Drawable drawable = disciplinasViewHolder.disciplinaScore.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#757575"), PorterDuff.Mode.SRC_ATOP);

        disciplinasViewHolder.disciplinaScore.setRating(ratingDisciplina);
        disciplinasViewHolder.disciplinaScore.setAlpha(1.0F);

        String firstLetter = String.valueOf(disciplina.getTitulo().charAt(0));

        ColorGenerator generator = ColorGenerator.MATERIAL;

        int color = generator.getRandomColor();

        TextDrawable drawableImage = TextDrawable.builder()
                .buildRoundRect(firstLetter, color, 5);

        disciplinasViewHolder.disciplinaIcon.setImageDrawable(drawableImage);

        setAnimation(disciplinasViewHolder.mCardTopLayout, i);
    }

    @Override
    public void onClick(final View view) {
        if (mListener != null) {
            DisciplinasViewHolder vh = (DisciplinasViewHolder) view.getTag();
            int position = vh.getAdapterPosition();
            mListener.onExploreDisciplinaClick(view, position, disciplinaList.get(position));
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public interface OnExploreDisciplinaClick {
        void onExploreDisciplinaClick(View v, int position, Disciplina disciplina);
    }


    public static class DisciplinasViewHolder extends RecyclerView.ViewHolder {
        protected TextView disciplinaTitle;
        protected TextView disciplinaDescription;
        protected RatingBar disciplinaScore;
        protected View mCardTopLayout;
        protected ImageView disciplinaIcon;

        public DisciplinasViewHolder(View v) {
            super(v);
            mCardTopLayout = v.findViewById(R.id.card_disciplinas_item_layout);
            disciplinaScore = (RatingBar) v.findViewById(R.id.disciplina_score);
            disciplinaTitle = (TextView) v.findViewById(R.id.txt_title_disciplina);
            disciplinaDescription = (TextView) v.findViewById(R.id.txt_subtitle_disciplina);
            disciplinaIcon = (ImageView) v.findViewById(R.id.explore_disciplina_icon);

        }
    }

}
