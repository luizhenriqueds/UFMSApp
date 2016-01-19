package ufms.br.com.ufmsapp.extras;


import com.android.volley.RequestQueue;

import org.json.JSONObject;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.JSONParsers.ListAlunoXDisciplinaParser;
import ufms.br.com.ufmsapp.JSONParsers.ListAlunosParser;
import ufms.br.com.ufmsapp.JSONParsers.ListDisciplinasParser;
import ufms.br.com.ufmsapp.JSONParsers.ListEventosParser;
import ufms.br.com.ufmsapp.JSONParsers.ListMateriaisUploadParser;
import ufms.br.com.ufmsapp.JSONParsers.ListProfessoresParser;
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
import ufms.br.com.ufmsapp.pojo.Material;
import ufms.br.com.ufmsapp.pojo.Professor;
import ufms.br.com.ufmsapp.pojo.StatusAluno;
import ufms.br.com.ufmsapp.pojo.StatusDisciplina;
import ufms.br.com.ufmsapp.pojo.TipoDisciplina;
import ufms.br.com.ufmsapp.pojo.TipoEvento;
import ufms.br.com.ufmsapp.pojo.TituloProfessor;
import ufms.br.com.ufmsapp.pojo.Turma;

public class LoadDataUtils {

    public static ArrayList<Evento> loadEventos(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlEventos());
        ArrayList<Evento> listEventos = ListEventosParser.parseEventosJSON(response);
        MyApplication.getWritableDatabase().criarEvento(listEventos, true);

        ArrayList<Disciplina> disciplinas = MyApplication.getWritableDatabase().listarDisciplinas(1);

        ArrayList<Evento> eventos = new ArrayList<>();

        for (int i = 0; i < disciplinas.size(); i++) {
            eventos.addAll(MyApplication.getWritableDatabase().listarEventos(disciplinas.get(i).getIdDisciplinaServidor()));
        }

        // return listEventos;
        return eventos;
    }

    public static ArrayList<Disciplina> loadDisciplinas(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlDisciplinas());
        ArrayList<Disciplina> listDisciplinas = ListDisciplinasParser.parseDisciplinasJSON(response);
        MyApplication.getWritableDatabase().criarDisciplina(listDisciplinas, true);

        //return listDisciplinas;
        return MyApplication.getWritableDatabase().listarDisciplinas(1);
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

    public static ArrayList<AlunoXDisciplina> loadMatriculas(RequestQueue requestQueue) {
        JSONObject response = Requestor.requestJSON(requestQueue, Endpoints.getRequestUrlAlunoxDisciplina());
        ArrayList<AlunoXDisciplina> listMatriculas = ListAlunoXDisciplinaParser.parseAlunoXDisciplinaJSON(response);
        MyApplication.getWritableDatabase().criarMatricula(listMatriculas, true);

        return listMatriculas;
    }
}
