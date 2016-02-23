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
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;

public class CalendarViewAdapter extends RecyclerView.Adapter<CalendarViewAdapter.CalendarViewHolder> implements View.OnClickListener {

    Evento calendarView;
    private ArrayList<Evento> calendarList;
    LayoutInflater inflater;
    View itemView;
    private int lastPosition = -1;
    private OnCalendarEventClickListener mListener;
    VolleySingleton volleySingleton;
    ImageLoader imageLoader;

    private DateFormat df = new SimpleDateFormat("dd", new Locale("pt", "br"));
    private DateFormat dfTime = new SimpleDateFormat("HH:mm", new Locale("pt", "br"));
    private DateFormat dfDate = new SimpleDateFormat("yyyy-mm-dd", new Locale("pt", "br"));


    public CalendarViewAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        volleySingleton = VolleySingleton.getInstance();
        imageLoader = volleySingleton.getImageLoader();
    }

    @Override
    public int getItemCount() {
        return calendarList.size();
    }

    public void setDisciplinaClickListener(OnCalendarEventClickListener listener) {
        mListener = listener;
    }


    @Override
    public CalendarViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.calendar_view_adapter, viewGroup, false);
        CalendarViewHolder vh = new CalendarViewHolder(itemView);
        itemView.setTag(vh);
        itemView.setOnClickListener(this);

        return vh;
    }

    public void setCalendarList(ArrayList<Evento> calendarList) {
        this.calendarList = calendarList;
        notifyItemRangeChanged(0, calendarList.size());
    }


    @Override
    public void onBindViewHolder(CalendarViewHolder calendarViewHolder, int i) {
        calendarView = calendarList.get(i);

        Disciplina disciplina = MyApplication.getWritableDatabase().disciplinaById(calendarView.getDisciplina());

        int dateCount = MyApplication.getWritableDatabase().listarEventosByDate(calendarView.getDataEventoCriado()).size();

        calendarViewHolder.calendarDay.setText(df.format(calendarView.getDataEventoCriado()));
        calendarViewHolder.calendarTitle.setText(calendarView.getTitulo());
        calendarViewHolder.calendarSubtitle.setText(disciplina.getTitulo());

        if(dateCount > 1){
            calendarViewHolder.calendarDay.setText(df.format(calendarView.getDataEventoCriado()));
        }


        //setAnimation(disciplinaViewHolder.disciplinaAdapterLayout, i);

    }

    @Override
    public void onClick(final View view) {
        if (mListener != null) {
            CalendarViewHolder vh = (CalendarViewHolder) view.getTag();
            int position = vh.getAdapterPosition();
            mListener.onCalendarEventClickItem(view, position, calendarList.get(position));
        }

    }

    public interface OnCalendarEventClickListener {
        void onCalendarEventClickItem(View v, int position, Evento calendarEvent);
    }


    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(itemView.getContext(), android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public static class CalendarViewHolder extends RecyclerView.ViewHolder {
        protected TextView calendarDay;
        protected TextView calendarTitle;
        protected TextView calendarSubtitle;


        public CalendarViewHolder(View v) {
            super(v);

            calendarDay = (TextView) v.findViewById(R.id.txt_calendar_event_date);
            calendarTitle = (TextView) v.findViewById(R.id.txt_calendar_event_title);
            calendarSubtitle = (TextView) v.findViewById(R.id.txt_calendar_event_subtitle);
        }
    }

}
