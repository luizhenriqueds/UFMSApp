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

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Nota;

public class AtividadeNotaAdapter extends RecyclerView.Adapter<AtividadeNotaAdapter.NotasViewHolder> implements View.OnClickListener {

    Nota nota;
    private ArrayList<Nota> notasList;
    LayoutInflater inflater;
    View itemView;
    private int lastPosition = -1;
    private OnNotaClickListener mListener;
    VolleySingleton volleySingleton;
    ImageLoader imageLoader;

    public AtividadeNotaAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    @Override
    public int getItemCount() {
        return notasList.size();
    }

    public void setNotaClickListener(OnNotaClickListener listener) {
        mListener = listener;
    }


    @Override
    public NotasViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.layout_nota_atividades_adapter_row, viewGroup, false);
        NotasViewHolder vh = new NotasViewHolder(itemView);
        itemView.setTag(vh);
        itemView.setOnClickListener(this);

        return vh;
    }

    public void setNotasList(ArrayList<Nota> notasList) {
        this.notasList = notasList;
        notifyItemRangeChanged(0, notasList.size());
    }


    @Override
    public void onBindViewHolder(NotasViewHolder notasViewHolder, int i) {
        nota = notasList.get(i);

        notasViewHolder.descricaoNota.setText(nota.getDescricaoNota());
        notasViewHolder.valorNota.setText(String.valueOf(nota.getNota()));


        setAnimation(notasViewHolder.rootLayout, i);

    }

    @Override
    public void onClick(final View view) {
        if (mListener != null) {
            NotasViewHolder vh = (NotasViewHolder) view.getTag();
            int position = vh.getAdapterPosition();
            mListener.onNotaClick(view, position, notasList.get(position));
        }

    }

    public interface OnNotaClickListener {
        void onNotaClick(View v, int position, Nota nota);
    }


    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public static class NotasViewHolder extends RecyclerView.ViewHolder {
        protected TextView descricaoNota;
        protected TextView valorNota;
        protected CardView rootLayout;


        public NotasViewHolder(View v) {
            super(v);
            rootLayout = (CardView) v.findViewById(R.id.atividade_nota_root_layout);
            descricaoNota = (TextView) v.findViewById(R.id.txt_atividade_desc);
            valorNota = (TextView) v.findViewById(R.id.txt_nota);
        }
    }

}
