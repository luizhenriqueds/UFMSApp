package ufms.br.com.ufmsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.DateFormatDayFormatter;
import com.prolificinteractive.materialcalendarview.format.DateFormatTitleFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.adapter.CalendarViewAdapter;
import ufms.br.com.ufmsapp.decorators.OneDayDecorator;
import ufms.br.com.ufmsapp.pojo.Evento;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentEventCalendarView extends Fragment implements OnDateSelectedListener {

    MaterialCalendarView calendarView;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private CalendarViewAdapter adapter;
    protected RecyclerView mRecyclerCalendarEvents;

    public FragmentEventCalendarView() {
    }

    public static FragmentEventCalendarView newInstance() {
        return new FragmentEventCalendarView();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evento_calendar, container, false);

        adapter = new CalendarViewAdapter(getActivity());

        mRecyclerCalendarEvents = (RecyclerView) view.findViewById(R.id.recycler_calendar_detail);

        calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);

        DateFormat df = new SimpleDateFormat("MMMM yyyy", new Locale("pt", "BR"));
        final DateFormat dayFormatter = new SimpleDateFormat("d", new Locale("pt", "BR"));

        calendarView.setTitleFormatter(new DateFormatTitleFormatter(df));
        calendarView.setDayFormatter(new DateFormatDayFormatter(dayFormatter));

        calendarView.setOnDateChangedListener(this);
        calendarView.setTopbarVisible(false);

        mRecyclerCalendarEvents.setLayoutManager(new LinearLayoutManager(getActivity()));


        /*calendarView.addDecorators(
                new HighlightWeekendsDecorator(),
                oneDayDecorator
        );*/

        setupCalendar();

        return view;
    }

    private void setupCalendar() {


        ArrayList<Evento> eventos = MyApplication.getWritableDatabase().listarEventos(2);


        Evento evento;
        for (int i = 0; i < eventos.size(); i++) {
            evento = eventos.get(i);
            calendarView.setDateSelected(evento.getDataEventoCriado(), true);
        }


        adapter.setCalendarList(eventos);
        mRecyclerCalendarEvents.setAdapter(adapter);


    }


    public static Calendar dateToCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }


    @Override
    public void onDateSelected(MaterialCalendarView widget, CalendarDay date, boolean selected) {
        Toast.makeText(getActivity(), "SELECTED DATE => " + date, Toast.LENGTH_LONG).show();
    }
}
