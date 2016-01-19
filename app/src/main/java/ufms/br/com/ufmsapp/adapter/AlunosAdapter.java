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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Aluno;
import ufms.br.com.ufmsapp.pojo.AlunoXDisciplina;

public class AlunosAdapter extends RecyclerView.Adapter<AlunosAdapter.AlunosViewHolder> {

    AlunoXDisciplina aluno;
    private ArrayList<AlunoXDisciplina> matriculas;
    LayoutInflater inflater;
    View itemView;
    private int lastPosition = -1;
    //private OnMaterialClickListener mListener;
    VolleySingleton volleySingleton;
    ImageLoader imageLoader;

    private DateFormat df = new SimpleDateFormat("dd MMM", new Locale("pt", "br"));


    public AlunosAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    @Override
    public int getItemCount() {
        return matriculas.size();
    }

   /* public void setDisciplinaClickListener(OnMaterialClickListener listener) {
        mListener = listener;
    }*/


    @Override
    public AlunosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.fragment_alunos_adapter_row, viewGroup, false);
        AlunosViewHolder vh = new AlunosViewHolder(itemView);
        itemView.setTag(vh);
        //itemView.setOnClickListener(this);

        return vh;
    }

    public void setAlunosList(ArrayList<AlunoXDisciplina> matriculas) {
        this.matriculas = matriculas;
        notifyItemRangeChanged(0, matriculas.size());
    }


    @Override
    public void onBindViewHolder(AlunosViewHolder alunosViewHolder, int i) {
        aluno = matriculas.get(i);

        Aluno mAluno = MyApplication.getWritableDatabase().alunoById(aluno.getAluno());


        alunosViewHolder.nomeAluno.setText(mAluno.getNome());
        alunosViewHolder.emailAluno.setText(mAluno.getEmail());


        setAnimation(alunosViewHolder.alunoRootLayout, i);

    }

   /* @Override
    public void onClick(final View view) {
        if (mListener != null) {
            AlunosViewHolder vh = (AlunosViewHolder) view.getTag();
            int position = vh.getAdapterPosition();
            mListener.onDisciplinaClick(view, position, disciplinasList.get(position));
        }

    }*/

    /*public interface OnMaterialClickListener {
        void onDisciplinaClick(View v, int position, Disciplina disciplina);
    }
*/

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public static class AlunosViewHolder extends RecyclerView.ViewHolder {
        protected CardView alunoRootLayout;
        protected TextView nomeAluno;
        protected TextView emailAluno;


        public AlunosViewHolder(View v) {
            super(v);
            alunoRootLayout = (CardView) v.findViewById(R.id.root_layout_aluno);
            nomeAluno = (TextView) v.findViewById(R.id.txt_aluno_nome);
            emailAluno = (TextView) v.findViewById(R.id.txt_aluno_email);


        }
    }

}
