/*
 *
 *  * Copyright (C) 2014 The Android Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */


package ufms.br.com.ufmsapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.extras.UrlEndpoints;
import ufms.br.com.ufmsapp.pojo.Evento;

public class ExploreEventosAdapter extends RecyclerView.Adapter<ExploreEventosAdapter.EventosViewHolder> implements View.OnClickListener {

    Evento evento;
    private ArrayList<Evento> eventoList;
    LayoutInflater inflater;
    View itemView;
    private int lastPosition = -1;
    private OnExploreEventoClick mListener;


    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy", new Locale("pt", "br"));


    public ExploreEventosAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return eventoList.size();
    }

    public void setExploreEventoListener(OnExploreEventoClick listener) {
        mListener = listener;
    }

    @Override
    public EventosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.eventos_adapter_row, viewGroup, false);

        EventosViewHolder vh = new EventosViewHolder(itemView);
        itemView.setTag(vh);

        itemView.setOnClickListener(this);

        return vh;
    }

    public void setExploreEventosList(ArrayList<Evento> eventoList) {
        this.eventoList = eventoList;
        notifyItemRangeChanged(0, eventoList.size());
    }


    @Override
    public void onBindViewHolder(EventosViewHolder eventosViewHolder, int i) {
        evento = eventoList.get(i);

        eventosViewHolder.eventoItemTitle.setText(evento.getTitulo());
        eventosViewHolder.eventoItemSubtitle.setText(evento.getDescricao());
        eventosViewHolder.eventoItemTimeStamp.setText(df.format(evento.getDataEventoCriado()));
        Picasso.with(itemView.getContext()).load(UrlEndpoints.URL_ENDPOINT + evento.getSmallIcon()).into(eventosViewHolder.exploreEventosIcon);

        setAnimation(eventosViewHolder.mCardTopLayout, i);
    }

    @Override
    public void onClick(final View view) {
        if (mListener != null) {
            EventosViewHolder vh = (EventosViewHolder) view.getTag();
            int position = vh.getAdapterPosition();
            mListener.onExploreEventClick(view, position, eventoList.get(position));
        }

    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public interface OnExploreEventoClick {
        void onExploreEventClick(View v, int position, Evento evento);
    }

    public static class EventosViewHolder extends RecyclerView.ViewHolder {
        protected TextView eventoItemTitle;
        protected TextView eventoItemSubtitle;
        protected TextView eventoItemTimeStamp;
        protected View mCardTopLayout;
        protected ImageView exploreEventosIcon;
        //protected CardView mCardItemLayout;

        public EventosViewHolder(View v) {
            super(v);
            mCardTopLayout = v.findViewById(R.id.card_eventos_first_item_layout);
            eventoItemTitle = (TextView) v.findViewById(R.id.evento_item_title);
            eventoItemSubtitle = (TextView) v.findViewById(R.id.evento_item_subtitle);
            eventoItemTimeStamp = (TextView) v.findViewById(R.id.evento_explore_timestamp);
            exploreEventosIcon = (ImageView) v.findViewById(R.id.explore_eventos_icon);
        }
    }


}
