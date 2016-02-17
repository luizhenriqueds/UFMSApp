package ufms.br.com.ufmsapp.adapter;


import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.extras.UrlEndpoints;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.pojo.EventoRead;

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.EventosViewHolder> implements View.OnClickListener {

    Evento evento;
    private ArrayList<Evento> eventosList;
    LayoutInflater inflater;
    View itemView;
    VolleySingleton volleySingleton;
    ImageLoader imageLoader;
    private int lastPosition = -1;
    private OnEventClickListener mListener;
    private DateFormat df = new SimpleDateFormat("dd MMM", new Locale("pt", "br"));
    public static final String SHARED_TRANSITION_ICON_KEY = "icon";
    //public static final String SHARED_TRANSITION_TITLE_KEY = "title";

    public EventosAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    public void setListener(OnEventClickListener listener) {
        mListener = listener;
    }

    @Override
    public int getItemCount() {
        return eventosList.size();
    }

    @Override
    public EventosViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.eventos_list_adapter_row, viewGroup, false);
        EventosViewHolder vh = new EventosViewHolder(itemView);
        itemView.setTag(vh);

        itemView.setOnClickListener(this);

        return vh;
    }

    public void setEventosList(ArrayList<Evento> eventosList) {
        this.eventosList = eventosList;
        notifyItemRangeChanged(0, eventosList.size());
    }


    @Override
    public void onBindViewHolder(EventosViewHolder eventosViewHolder, int i) {
        evento = eventosList.get(i);

        /*EventoRead eventoRead = MyApplication.getWritableDatabase().eventoReadById(evento.getIdEventoServidor());

        if (eventoRead != null) {
            if (eventoRead.getEventoReadStatus() == 0) {
                eventosViewHolder.cardLayout.setBackground(itemView.getResources().getDrawable(R.drawable.custom_bg_list_read));
            } else if (eventoRead.getEventoReadStatus() == 1) {
                eventosViewHolder.cardLayout.setBackground(itemView.getResources().getDrawable(R.drawable.custom_bg));
            }
        }*/

        eventosViewHolder.eventoTitle.setText(evento.getTitulo());

        Disciplina disciplina = MyApplication.getWritableDatabase().disciplinaById(evento.getDisciplina());


        eventosViewHolder.eventoSubtitle.setText(disciplina.getTitulo());
        eventosViewHolder.eventoDescription.setText(evento.getDescricao());
        eventosViewHolder.eventoTimeStamp.setText(df.format(evento.getDataEventoCriado()));
        //Picasso.with(itemView.getContext()).load(UrlEndpoints.URL_ENDPOINT + evento.getSmallIcon()).into(eventosViewHolder.eventoIcon);

        Picasso.with(itemView.getContext())
                .load(UrlEndpoints.URL_ENDPOINT + evento.getBigIcon())
                .resize(110, 80)
                .centerCrop()
                .into(eventosViewHolder.eventoIcon);

        setAnimation(eventosViewHolder.cardLayout, i);


    }

    @Override
    public void onClick(final View view) {
        if (mListener != null) {
            EventosViewHolder vh = (EventosViewHolder) view.getTag();
            int position = vh.getAdapterPosition();
            mListener.onEventClick(view, position, eventosList.get(position));
        }

    }

    public interface OnEventClickListener {
        void onEventClick(View v, int position, Evento evento);
    }


    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public static class EventosViewHolder extends RecyclerView.ViewHolder {
        protected LinearLayout cardLayout;
        protected TextView eventoTitle;
        protected TextView eventoSubtitle;
        protected TextView eventoDescription;
        protected TextView eventoTimeStamp;
        protected ImageView eventoIcon;


        public EventosViewHolder(View v) {
            super(v);

            cardLayout = (LinearLayout) v.findViewById(R.id.adapter_layout);
            eventoTitle = (TextView) v.findViewById(R.id.txt_evento_title);
            eventoIcon = (ImageView) v.findViewById(R.id.icon_item_evento);
            eventoSubtitle = (TextView) v.findViewById(R.id.txt_evento_subtitle);
            eventoDescription = (TextView) v.findViewById(R.id.txt_evento_descricao);
            eventoTimeStamp = (TextView) v.findViewById(R.id.txt_evento_timestamp);

            ViewCompat.setTransitionName(eventoIcon, SHARED_TRANSITION_ICON_KEY);
            // ViewCompat.setTransitionName(eventoTitle, SHARED_TRANSITION_TITLE_KEY);

        }
    }

}
