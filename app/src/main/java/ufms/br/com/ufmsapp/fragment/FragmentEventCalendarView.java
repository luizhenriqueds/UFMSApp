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

package ufms.br.com.ufmsapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import ufms.br.com.ufmsapp.decorators.HighlightWeekendsDecorator;
import ufms.br.com.ufmsapp.decorators.OneDayDecorator;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.preferences.UserSessionPreference;

public class FragmentEventCalendarView extends Fragment implements OnDateSelectedListener {

    MaterialCalendarView calendarView;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();


    public FragmentEventCalendarView() {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_list_view:
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_layout_container, EventosFragment.newInstance());
                fragmentTransaction.commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.calendar_view_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public static FragmentEventCalendarView newInstance() {
        return new FragmentEventCalendarView();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_evento_calendar, container, false);

        calendarView = (MaterialCalendarView) view.findViewById(R.id.calendarView);
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_NONE);

        DateFormat df = new SimpleDateFormat("MMMM yyyy", new Locale("PT", "BR"));
        final DateFormat dayFormatter = new SimpleDateFormat("d", new Locale("pt", "BR"));

        calendarView.setTitleFormatter(new DateFormatTitleFormatter(df));
        calendarView.setDayFormatter(new DateFormatDayFormatter(dayFormatter));

        calendarView.setOnDateChangedListener(this);
        calendarView.setTopbarVisible(true);


        calendarView.addDecorators(
                new HighlightWeekendsDecorator(),
                oneDayDecorator
        );

        setupCalendar();

        return view;
    }

    private void setupCalendar() {

        UserSessionPreference prefs = new UserSessionPreference(getActivity());

        ArrayList<Disciplina> disciplinas = new ArrayList<>();

        if (!prefs.isFirstTime()) {
            disciplinas = MyApplication.getWritableDatabase().listarDisciplinas(MyApplication.getWritableDatabase().alunoByEmail(prefs.getEmail()).getAlunoIdServidor());
        }


        ArrayList<Evento> eventosList = new ArrayList<>();
        for (int i = 0; i < disciplinas.size(); i++) {
            eventosList.addAll(MyApplication.getWritableDatabase().listarEventos(disciplinas.get(i).getIdDisciplinaServidor()));
        }

        Evento evento;
        for (int i = 0; i < eventosList.size(); i++) {
            evento = eventosList.get(i);
            calendarView.setDateSelected(evento.getDataEventoCriado(), true);
        }


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
