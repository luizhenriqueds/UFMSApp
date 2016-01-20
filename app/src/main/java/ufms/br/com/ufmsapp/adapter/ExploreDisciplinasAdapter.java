package ufms.br.com.ufmsapp.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

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
        disciplinasViewHolder.disciplinaDescription.setText("12 Alunos Matriculados");
        disciplinasViewHolder.disciplinaScore.setRating(4.2F);
        disciplinasViewHolder.disciplinaScore.setAlpha(1.0F);

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

        public DisciplinasViewHolder(View v) {
            super(v);
            mCardTopLayout = v.findViewById(R.id.card_disciplinas_item_layout);
            disciplinaScore = (RatingBar) v.findViewById(R.id.disciplina_score);
            disciplinaTitle = (TextView) v.findViewById(R.id.txt_title_disciplina);
            disciplinaDescription = (TextView) v.findViewById(R.id.txt_subtitle_disciplina);

        }
    }

}
