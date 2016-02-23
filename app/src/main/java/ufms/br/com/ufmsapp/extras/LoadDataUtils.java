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

package ufms.br.com.ufmsapp.extras;


import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.JSONParsers.ListAlunoXDisciplinaParser;
import ufms.br.com.ufmsapp.JSONParsers.ListAlunosParser;
import ufms.br.com.ufmsapp.JSONParsers.ListDisciplinasParser;
import ufms.br.com.ufmsapp.JSONParsers.ListEventosParser;
import ufms.br.com.ufmsapp.JSONParsers.ListMateriaisUploadParser;
import ufms.br.com.ufmsapp.JSONParsers.ListNotasParser;
import ufms.br.com.ufmsapp.JSONParsers.ListProfessoresParser;
import ufms.br.com.ufmsapp.JSONParsers.ListRatingDisciplinaParser;
import ufms.br.com.ufmsapp.JSONParsers.ListStatusAlunosParser;
import ufms.br.com.ufmsapp.JSONParsers.ListStatusDisciplinaParser;
import ufms.br.com.ufmsapp.JSONParsers.ListTipoDisciplinaParser;
import ufms.br.com.ufmsapp.JSONParsers.ListTipoEventoParser;
import ufms.br.com.ufmsapp.JSONParsers.ListTituloProfessorParser;
import ufms.br.com.ufmsapp.JSONParsers.ListTurmasParser;
import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.json.Endpoints;
import ufms.br.com.ufmsapp.json.Requestor;
import ufms.br.com.ufmsapp.pojo.Aluno;
import ufms.br.com.ufmsapp.pojo.AlunoXDisciplina;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.pojo.EventoRead;
import ufms.br.com.ufmsapp.pojo.Material;
import ufms.br.com.ufmsapp.pojo.Nota;
import ufms.br.com.ufmsapp.pojo.Professor;
import ufms.br.com.ufmsapp.pojo.RatingDisciplina;
import ufms.br.com.ufmsapp.pojo.StatusAluno;
import ufms.br.com.ufmsapp.pojo.StatusDisciplina;
import ufms.br.com.ufmsapp.pojo.TipoDisciplina;
import ufms.br.com.ufmsapp.pojo.TipoEvento;
import ufms.br.com.ufmsapp.pojo.TituloProfessor;
import ufms.br.com.ufmsapp.pojo.Turma;
import ufms.br.com.ufmsapp.preferences.UserSessionPreference;

public class LoadDataUtils {
    private static UserSessionPreference prefs = new UserSessionPreference(MyApplication.getAppContext());

    public static ArrayList<Evento> loadEventos(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlEventos());
        ArrayList<Evento> listEventos = ListEventosParser.parseEventosJSON(response);
        MyApplication.getWritableDatabase().criarEvento(listEventos, true);

        Aluno aluno = null;
        if (!prefs.isFirstTime()) {
            aluno = MyApplication.getWritableDatabase().alunoByEmail(prefs.getEmail());
        }

        ArrayList<Evento> eventos = null;
        if (aluno != null) {
            ArrayList<Disciplina> disciplinas = MyApplication.getWritableDatabase().listarDisciplinas(aluno.getAlunoIdServidor());

            eventos = new ArrayList<>();

            for (int i = 0; i < disciplinas.size(); i++) {
                eventos.addAll(MyApplication.getWritableDatabase().listarEventos(disciplinas.get(i).getIdDisciplinaServidor()));
            }


            for (int j = 0; j < eventos.size(); j++) {
                if (MyApplication.getWritableDatabase().countEventoRead(eventos.get(j).getIdEventoServidor()) == 0) {
                    MyApplication.getWritableDatabase().setEventoRead(new EventoRead(0, eventos.get(j).getIdEventoServidor()));
                }
            }

        }

        // return listEventos;
        return eventos;
    }

    public static ArrayList<Disciplina> loadDisciplinas(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlDisciplinas());
        ArrayList<Disciplina> listDisciplinas = ListDisciplinasParser.parseDisciplinasJSON(response);
        MyApplication.getWritableDatabase().criarDisciplina(listDisciplinas, true);

        ArrayList<Disciplina> disciplinas = null;

        if (!prefs.isFirstTime()) {
            disciplinas = MyApplication.getWritableDatabase().listarDisciplinas(MyApplication.getWritableDatabase().alunoByEmail(prefs.getEmail()).getAlunoIdServidor());
        }

        //return listDisciplinas;
        return disciplinas;
    }

    public static ArrayList<Professor> loadProfessores(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlProfessores());
        ArrayList<Professor> listProfessores = ListProfessoresParser.parseProfessoresJSON(response);
        MyApplication.getWritableDatabase().criarProfessor(listProfessores, true);

        return listProfessores;
    }

    public static ArrayList<TipoEvento> loadTipoEventos(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlTipoEventos());
        ArrayList<TipoEvento> listTipoEventos = ListTipoEventoParser.parseTipoEventoJSON(response);
        MyApplication.getWritableDatabase().criarTipoEvento(listTipoEventos, true);

        return listTipoEventos;
    }

    public static ArrayList<StatusDisciplina> loadStatusDisciplinas(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlStatusDisciplina());
        ArrayList<StatusDisciplina> listStatusDisciplina = ListStatusDisciplinaParser.parseStatusDisciplinaJSON(response);
        MyApplication.getWritableDatabase().criarStatusDisciplina(listStatusDisciplina, true);

        return listStatusDisciplina;
    }

    public static ArrayList<StatusAluno> loadStatusAluno(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlStatusAlunos());
        ArrayList<StatusAluno> listStatusAlunos = ListStatusAlunosParser.parseStatusAlunoJSON(response);
        MyApplication.getWritableDatabase().criarStatusAluno(listStatusAlunos, true);

        return listStatusAlunos;
    }

    public static ArrayList<TipoDisciplina> loadTipoDisciplinas(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlTipoDisciplina());
        ArrayList<TipoDisciplina> listTipoDisciplinas = ListTipoDisciplinaParser.parseTipoDisciplinaJSON(response);
        MyApplication.getWritableDatabase().criarTipoDisciplina(listTipoDisciplinas, true);

        return listTipoDisciplinas;
    }

    public static ArrayList<Aluno> loadAlunos(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlAlunos());
        ArrayList<Aluno> listAlunos = ListAlunosParser.parseAlunoJSON(response);
        MyApplication.getWritableDatabase().criarAluno(listAlunos, true);

        return listAlunos;
    }


    public static ArrayList<Material> loadMateriais(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlMateriais());
        ArrayList<Material> listMateriais = ListMateriaisUploadParser.parseMaterialJSON(response);
        MyApplication.getWritableDatabase().uploadMaterial(listMateriais, true);

        return listMateriais;
    }

    public static ArrayList<Turma> loadTurmas(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlTurmas());
        ArrayList<Turma> listTurmas = ListTurmasParser.parseTurmaJSON(response);
        MyApplication.getWritableDatabase().criarTurma(listTurmas, true);

        return listTurmas;
    }

    public static ArrayList<TituloProfessor> loadTituloProfessor(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlTituloProfessor());
        ArrayList<TituloProfessor> listTituloProfessor = ListTituloProfessorParser.parseTituloProfessorJSON(response);
        MyApplication.getWritableDatabase().criarTituloProfessor(listTituloProfessor, true);

        return listTituloProfessor;
    }

    public static ArrayList<RatingDisciplina> loadRatingDisciplinas(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlRatingDisciplina());
        ArrayList<RatingDisciplina> listRatingDisciplina = ListRatingDisciplinaParser.listRatingDisciplinasParser(response);
        MyApplication.getWritableDatabase().ratingDisciplina(listRatingDisciplina, true);

        return listRatingDisciplina;
    }

    public static ArrayList<AlunoXDisciplina> loadMatriculas(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlAlunoxDisciplina());
        ArrayList<AlunoXDisciplina> listMatriculas = ListAlunoXDisciplinaParser.parseAlunoXDisciplinaJSON(response);
        MyApplication.getWritableDatabase().criarMatricula(listMatriculas, true);

        return listMatriculas;
    }

    public static ArrayList<Nota> loadNotas(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlNotas());
        ArrayList<Nota> notas = ListNotasParser.parseNotasJSON(response);
        MyApplication.getWritableDatabase().criarNota(notas, true);

        return notas;
    }
}
