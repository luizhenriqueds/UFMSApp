package ufms.br.com.ufmsapp.data;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

import ufms.br.com.ufmsapp.pojo.Aluno;
import ufms.br.com.ufmsapp.pojo.AlunoXDisciplina;
import ufms.br.com.ufmsapp.pojo.Curso;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.pojo.EventoFavorite;
import ufms.br.com.ufmsapp.pojo.EventoRead;
import ufms.br.com.ufmsapp.pojo.Material;
import ufms.br.com.ufmsapp.pojo.Nota;
import ufms.br.com.ufmsapp.pojo.NotifyStatus;
import ufms.br.com.ufmsapp.pojo.Professor;
import ufms.br.com.ufmsapp.pojo.RatingDisciplina;
import ufms.br.com.ufmsapp.pojo.StatusAluno;
import ufms.br.com.ufmsapp.pojo.StatusDisciplina;
import ufms.br.com.ufmsapp.pojo.TipoCurso;
import ufms.br.com.ufmsapp.pojo.TipoDisciplina;
import ufms.br.com.ufmsapp.pojo.TipoEvento;
import ufms.br.com.ufmsapp.pojo.TituloProfessor;
import ufms.br.com.ufmsapp.pojo.Turma;
import ufms.br.com.ufmsapp.utils.DateUtils;

public class AppDAO {

    private SQLiteDatabase database;
    DataHelper helper;


    public AppDAO(Context context) {
        helper = DataHelper.newInstance(context);
        database = helper.getWritableDatabase();
    }


    public static final String[] PROJECTION_DISCIPLINA = {
            DataContract.DisciplinaEntry.COLUMN_ID,
            DataContract.DisciplinaEntry.COLUMN_CODIGO,
            DataContract.DisciplinaEntry.COLUMN_NOME,
            DataContract.DisciplinaEntry.COLUMN_DESCRICAO,
            DataContract.DisciplinaEntry.COLUMN_CARGA_HORARIA,
            DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR,
            DataContract.DisciplinaEntry.TIPO_DISCIPLINA_FK,
            DataContract.DisciplinaEntry.PROFESSOR_FK
    };

    public static final String[] PROJECTION_PROFESSOR = {
            DataContract.ProfessorEntry.COLUMN_ID,
            DataContract.ProfessorEntry.COLUMN_NOME,
            DataContract.ProfessorEntry.COLUMN_EMAIL,
            DataContract.ProfessorEntry.COLUMN_ANO_INGRESSO,
            DataContract.ProfessorEntry.COLUMN_PROFESSOR_ID_SERVIDOR,
            DataContract.ProfessorEntry.COLUMN_FORMACAO,
            DataContract.ProfessorEntry.TITULO_PROFESSOR_FK
    };

    public static final String[] PROJECTION_RATING_DISCIPLINAS = {
            DataContract.RatingDisciplinaEntry.COLUMN_ID,
            DataContract.RatingDisciplinaEntry.COLUMN_ALUNO_FK,
            DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK,
            DataContract.RatingDisciplinaEntry.COLUMN_RATING
    };

    public static final String[] PROJECTION_EVENTO = {
            DataContract.EventoEntry.COLUMN_ID,
            DataContract.EventoEntry.COLUMN_NOME,
            DataContract.EventoEntry.COLUMN_DESCRICAO,
            DataContract.EventoEntry.COLUMN_TIMESTAMP,
            DataContract.EventoEntry.COLUMN_DATA_LIMITE,
            DataContract.EventoEntry.COLUMN_SMALL_ICON_PATH,
            DataContract.EventoEntry.COLUMN_BIG_ICON_PATH,
            DataContract.EventoEntry.COLUMN_ID_SERVIDOR_EVENTO,
            DataContract.EventoEntry.TIPO_EVENTO_FK,
            DataContract.EventoEntry.DISCIPLINA_FK
    };

    public static final String[] PROJECTION_TIPO_EVENTO = {
            DataContract.TipoEventoEntry.COLUMN_ID,
            DataContract.TipoEventoEntry.COLUMN_NOME,
            DataContract.TipoEventoEntry.COLUMN_TIPO_EVENTO_ID_SERVIDOR
    };

    public static final String[] PROJECTION_NOTIFY_EVENTO = {
            DataContract.NotifyStatusEvento.COLUMN_ID,
            DataContract.NotifyStatusEvento.COLUMN_NOTIFY_STATUS,
            DataContract.NotifyStatusEvento.EVENTO_FK
    };

    public static final String[] PROJECTION_EVENTO_READ = {
            DataContract.EventoReadEntry.COLUMN_ID,
            DataContract.EventoReadEntry.COLUMN_EVENTO_READ,
            DataContract.EventoReadEntry.EVENTO_FK
    };

    public static final String[] PROJECTION_EVENTO_FAVORITE = {
            DataContract.EventoFavoriteEntry.COLUMN_ID,
            DataContract.EventoFavoriteEntry.COLUMN_EVENTO_FAVORITE,
            DataContract.EventoFavoriteEntry.EVENTO_FK
    };

    public static final String[] PROJECTION_STATUS_DISCIPLINA = {
            DataContract.StatusDisciplinaEntry.COLUMN_ID,
            DataContract.StatusDisciplinaEntry.COLUMN_NOME,
            DataContract.StatusDisciplinaEntry.COLUMN_STATUS_DISCIPLINA_ID_SERVIDOR
    };

    public static final String[] PROJECTION_STATUS_ALUNO = {
            DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_ID,
            DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_NOME,
            DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_ID_SERVIDOR
    };

    public static final String[] PROJECTION_TIPO_DISCIPLINA = {
            DataContract.TipoDisciplinaEntry.COLUMN_ID,
            DataContract.TipoDisciplinaEntry.COLUMN_NOME,
            DataContract.TipoDisciplinaEntry.COLUMN_ID_SERVIDOR_TIPO_DISCIPLINA
    };

    public static final String[] PROJECTION_ALUNOS = {
            DataContract.AlunoEntry.COLUMN_ID,
            DataContract.AlunoEntry.COLUMN_NOME,
            DataContract.AlunoEntry.COLUMN_EMAIL,
            DataContract.AlunoEntry.COLUMN_RGA,
            DataContract.AlunoEntry.STATUS_FK,
            DataContract.AlunoEntry.COLUMN_ALUNO_ID_SERVIDOR
    };

    public static final String[] PROJECTION_UPLOADS = {
            DataContract.EventoUploadsEntry.COLUMN_ID,
            DataContract.EventoUploadsEntry.COLUMN_UPLOAD_PATH,
            DataContract.EventoUploadsEntry.EVENTO_FK
    };

    public static final String[] PROJECTION_TURMA = {
            DataContract.TipoEventoEntry.COLUMN_ID,
            DataContract.TipoEventoEntry.COLUMN_NOME,
            DataContract.TipoEventoEntry.COLUMN_TIPO_EVENTO_ID_SERVIDOR
    };

    public static final String[] PROJECTION_TITULO_PROFESSOR = {
            DataContract.TituloProfessorEntry.COLUMN_ID,
            DataContract.TituloProfessorEntry.COLUMN_NOME,
            DataContract.TituloProfessorEntry.COLUMN_ID_SERVIDOR_TITULO_PROFESSOR
    };

    public static final String[] PROJECTION_ALUNO_X_DISCIPLINA = {
            DataContract.AlunoXDisciplinaEntry.COLUMN_ID,
            DataContract.AlunoXDisciplinaEntry.ALUNO_FK,
            DataContract.AlunoXDisciplinaEntry.DISCIPLINA_FK,
            DataContract.AlunoXDisciplinaEntry.STATUS_DISCIPLINA_FK,
            DataContract.AlunoXDisciplinaEntry.TURMA_FK,
            DataContract.AlunoXDisciplinaEntry.COLUMN_ID_SERVIDOR

    };


    public int criarEvento(ArrayList<Evento> eventoList, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.EventoEntry.TABLE_NAME_EVENTO);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < eventoList.size(); i++) {
            Evento currentEvento = eventoList.get(i);

            // values.put(DataContract.EventoEntry.COLUMN_ID, currentEvento.getId());

            values.put(DataContract.EventoEntry.COLUMN_NOME, currentEvento.getTitulo());
            values.put(DataContract.EventoEntry.COLUMN_DESCRICAO, currentEvento.getDescricao());

            values.put(DataContract.EventoEntry.COLUMN_TIMESTAMP, currentEvento.getDataEventoCriado() == null ? -1 : currentEvento.getDataEventoCriado().getTime());
            values.put(DataContract.EventoEntry.COLUMN_DATA_LIMITE, currentEvento.getDataLimiteEvento() == null ? -1 : currentEvento.getDataLimiteEvento().getTime());

            values.put(DataContract.EventoEntry.COLUMN_SMALL_ICON_PATH, currentEvento.getSmallIcon());
            values.put(DataContract.EventoEntry.COLUMN_BIG_ICON_PATH, currentEvento.getBigIcon());

            values.put(DataContract.EventoEntry.COLUMN_ID_SERVIDOR_EVENTO, currentEvento.getIdEventoServidor());

            values.put(DataContract.EventoEntry.TIPO_EVENTO_FK, currentEvento.getTipoEvento());
            values.put(DataContract.EventoEntry.DISCIPLINA_FK, currentEvento.getDisciplina());

            returnedId = (int) database.insert(DataContract.EventoEntry.TABLE_NAME_EVENTO, null, values);
            currentEvento.setId(returnedId);
        }


        return returnedId;

    }


    public ArrayList<Evento> listarEventos(int disciplinaId) {

        ArrayList<Evento> eventos = new ArrayList<>();

        String selectQuery =
                "SELECT * FROM " + DataContract.EventoEntry.TABLE_NAME_EVENTO + ", " + DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA
                        + " WHERE (" + DataContract.EventoEntry.DISCIPLINA_FK + " = " + DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR + ") AND ("
                        + DataContract.EventoEntry.DISCIPLINA_FK + " = " + disciplinaId + ")";

        //Log.i("DB_EXEC", selectQuery);

        // Cursor cursor = database.query(DataContract.EventoEntry.TABLE_NAME_EVENTO, PROJECTION_EVENTO, DataContract.EventoEntry.DISCIPLINA_FK + " = ? ", new String[]{String.valueOf(disciplinaId)}, null, null, DataContract.EventoEntry.COLUMN_TIMESTAMP + " DESC");
        Cursor cursor = database.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Evento evento = AppDAO.fromCursorEvento(cursor);
                    eventos.add(evento);
                } while (cursor.moveToNext());
            }

            return eventos;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public ArrayList<Evento> listarEventos(int disciplinaId, int limit) {

        ArrayList<Evento> eventos = new ArrayList<>();

        String selectQuery =
                "SELECT * FROM " + DataContract.EventoEntry.TABLE_NAME_EVENTO + ", " + DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA
                        + " WHERE (" + DataContract.EventoEntry.DISCIPLINA_FK + " = " + DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR + ") AND ("
                        + DataContract.EventoEntry.DISCIPLINA_FK + " = " + disciplinaId + ") LIMIT " + limit + ";";

        //Log.i("DB_EXEC", selectQuery);

        // Cursor cursor = database.query(DataContract.EventoEntry.TABLE_NAME_EVENTO, PROJECTION_EVENTO, DataContract.EventoEntry.DISCIPLINA_FK + " = ? ", new String[]{String.valueOf(disciplinaId)}, null, null, DataContract.EventoEntry.COLUMN_TIMESTAMP + " DESC");
        Cursor cursor = database.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Evento evento = AppDAO.fromCursorEvento(cursor);
                    eventos.add(evento);
                } while (cursor.moveToNext());
            }

            return eventos;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public ArrayList<Evento> listarEventosByDate(Date date) {

        ArrayList<Evento> eventos = new ArrayList<>();

        String sqlQry = SQLiteQueryBuilder.buildQueryString(false, DataContract.EventoEntry.TABLE_NAME_EVENTO, null, DataContract.EventoEntry.COLUMN_TIMESTAMP + "=" + String.valueOf(date.getTime()), null, null, DataContract.EventoEntry.COLUMN_TIMESTAMP + " DESC", null);

        Log.i("DB_TEST", sqlQry);


        Cursor cursor = database.query(DataContract.EventoEntry.TABLE_NAME_EVENTO, PROJECTION_EVENTO, DataContract.EventoEntry.COLUMN_TIMESTAMP + " = ?", new String[]{String.valueOf(date.getTime())}, null, null, DataContract.EventoEntry.COLUMN_TIMESTAMP + " DESC");

        try {
            if (cursor.moveToFirst()) {
                do {
                    Evento evento = AppDAO.fromCursorEvento(cursor);
                    eventos.add(evento);
                } while (cursor.moveToNext());
            }

            return eventos;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    /*public ArrayList<Evento> listarEventos(int limit) {

        ArrayList<Evento> eventos = new ArrayList<>();

        Cursor cursor = database.query(DataContract.EventoEntry.TABLE_NAME_EVENTO, PROJECTION_EVENTO, null, null, null, null, DataContract.EventoEntry.COLUMN_TIMESTAMP + " DESC limit " + limit);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Evento evento = AppDAO.fromCursorEvento(cursor);
                    eventos.add(evento);
                } while (cursor.moveToNext());
            }

            return eventos;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }*/

    public int criarDisciplina(ArrayList<Disciplina> disciplinaList, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < disciplinaList.size(); i++) {
            Disciplina currentDisciplina = disciplinaList.get(i);

            values.put(DataContract.DisciplinaEntry.COLUMN_CODIGO, currentDisciplina.getCodigo());
            values.put(DataContract.DisciplinaEntry.COLUMN_NOME, currentDisciplina.getTitulo());
            values.put(DataContract.DisciplinaEntry.COLUMN_DESCRICAO, currentDisciplina.getDescricao());
            values.put(DataContract.DisciplinaEntry.COLUMN_CARGA_HORARIA, currentDisciplina.getCargaHoraria());
            values.put(DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR, currentDisciplina.getIdDisciplinaServidor());
            values.put(DataContract.DisciplinaEntry.TIPO_DISCIPLINA_FK, currentDisciplina.getTipoDisciplina());
            values.put(DataContract.DisciplinaEntry.PROFESSOR_FK, currentDisciplina.getProfessor());


            returnedId = (int) database.insert(DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA, null, values);
            currentDisciplina.setId(returnedId);
        }


        return returnedId;

    }

    public ArrayList<Disciplina> listarDisciplinas(int alunoId, int limit) {

        ArrayList<Disciplina> disciplinas = new ArrayList<>();

        String selectQuery =
                "SELECT * FROM " + DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA + "," + DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA + "," + DataContract.AlunoEntry.TABLE_NAME_ALUNO + ","
                        + DataContract.StatusDisciplinaEntry.TABLE_NAME_STATUS_DISCIPLINA + " WHERE (" + DataContract.AlunoXDisciplinaEntry.DISCIPLINA_FK + " = " + DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR + ") " +
                        "AND (" + DataContract.AlunoXDisciplinaEntry.ALUNO_FK + " = " + DataContract.AlunoEntry.COLUMN_ALUNO_ID_SERVIDOR + ") AND (" + DataContract.AlunoXDisciplinaEntry.STATUS_DISCIPLINA_FK + " = "
                        + DataContract.StatusDisciplinaEntry.COLUMN_STATUS_DISCIPLINA_ID_SERVIDOR + ") AND ("
                        + DataContract.AlunoEntry.COLUMN_ALUNO_ID_SERVIDOR + " = " + alunoId + ") LIMIT " + limit + ";";

        Log.i("DB_TST", selectQuery);

        //Cursor cursor = database.query(DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA, PROJECTION_DISCIPLINA, null, null, null, null, DataContract.DisciplinaEntry.COLUMN_NOME);
        Cursor cursor = database.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Disciplina disciplina = AppDAO.fromCursorDisciplina(cursor);
                    disciplinas.add(disciplina);
                } while (cursor.moveToNext());
            }

            return disciplinas;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public ArrayList<Disciplina> listarDisciplinas(int alunoId) {

        ArrayList<Disciplina> disciplinas = new ArrayList<>();

        String selectQuery =
                "SELECT " +
                        DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA + "." + DataContract.DisciplinaEntry.COLUMN_ID + ", " + DataContract.DisciplinaEntry.COLUMN_CODIGO + ", " + DataContract.DisciplinaEntry.COLUMN_NOME + ", " + DataContract.DisciplinaEntry.COLUMN_DESCRICAO + ", " +
                        DataContract.DisciplinaEntry.COLUMN_CARGA_HORARIA + ", " + DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR + ", " + DataContract.DisciplinaEntry.TIPO_DISCIPLINA_FK +
                        ", " + DataContract.DisciplinaEntry.PROFESSOR_FK + " FROM " + DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA + ", " + DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA + ", " + DataContract.AlunoEntry.TABLE_NAME_ALUNO + ", "
                        + DataContract.StatusDisciplinaEntry.TABLE_NAME_STATUS_DISCIPLINA + " WHERE (" + DataContract.AlunoXDisciplinaEntry.DISCIPLINA_FK + " = " + DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR + ") " +
                        "AND (" + DataContract.AlunoXDisciplinaEntry.ALUNO_FK + " = " + DataContract.AlunoEntry.COLUMN_ALUNO_ID_SERVIDOR + ") AND (" + DataContract.AlunoXDisciplinaEntry.STATUS_DISCIPLINA_FK + " = "
                        + DataContract.StatusDisciplinaEntry.COLUMN_STATUS_DISCIPLINA_ID_SERVIDOR + ") AND ("
                        + DataContract.AlunoEntry.COLUMN_ALUNO_ID_SERVIDOR + " = " + alunoId + ")";

        Log.i("DB_TST", selectQuery);

        //Cursor cursor = database.query(DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA, PROJECTION_DISCIPLINA, null, null, null, null, DataContract.DisciplinaEntry.COLUMN_NOME);
        Cursor cursor = database.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Disciplina disciplina = AppDAO.fromCursorDisciplina(cursor);
                    disciplinas.add(disciplina);
                } while (cursor.moveToNext());
            }

            return disciplinas;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }


    public int uploadMaterial(ArrayList<Material> materialList, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.EventoUploadsEntry.TABLE_NAME_EVENTO_UPLOADS);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < materialList.size(); i++) {
            Material currentMaterial = materialList.get(i);

            values.put(DataContract.EventoUploadsEntry.COLUMN_UPLOAD_PATH, currentMaterial.getPathMaterial());
            values.put(DataContract.EventoUploadsEntry.EVENTO_FK, currentMaterial.getEventoKey());


            returnedId = (int) database.insert(DataContract.EventoUploadsEntry.TABLE_NAME_EVENTO_UPLOADS, null, values);
            currentMaterial.setIdMaterial(returnedId);
        }


        return returnedId;

    }

    public int ratingDisciplina(RatingDisciplina rating) {

        ContentValues values = new ContentValues();
        int returnedId = -1;

        values.put(DataContract.RatingDisciplinaEntry.COLUMN_ALUNO_FK, rating.getAlunoKey());
        values.put(DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK, rating.getDisciplinaKey());
        values.put(DataContract.RatingDisciplinaEntry.COLUMN_RATING, rating.getRating());

        returnedId = (int) database.insert(DataContract.RatingDisciplinaEntry.TABLE_NAME_RATING_DISCIPLINA, null, values);
        rating.setIdRatingDisciplina(returnedId);


        return returnedId;

    }

    public int criarNota(ArrayList<Nota> notas, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.NotaEntry.TABLE_NAME_NOTA);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < notas.size(); i++) {
            Nota nota = notas.get(i);
            values.put(DataContract.NotaEntry.COLUMN_DESCRICAO_NOTA, nota.getDescricaoNota());
            values.put(DataContract.NotaEntry.COLUMN_NOTA, nota.getNota());
            values.put(DataContract.NotaEntry.ALUNO_X_DISCIPLINA_FK, nota.getAlunoXDisciplina());

            returnedId = (int) database.insert(DataContract.NotaEntry.TABLE_NAME_NOTA, null, values);
            nota.setId(returnedId);
        }

        return returnedId;

    }

    public ArrayList<Nota> listarNotas(int disciplinaId) {

        ArrayList<Nota> notas = new ArrayList<>();

        String selectQuery =
                "SELECT * FROM " + DataContract.NotaEntry.TABLE_NAME_NOTA + "," + DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA +
                        " WHERE " + "(" + DataContract.NotaEntry.ALUNO_X_DISCIPLINA_FK + "=" + DataContract.AlunoXDisciplinaEntry.COLUMN_ID_SERVIDOR + ")" +
                        " AND " + "(" + DataContract.AlunoXDisciplinaEntry.DISCIPLINA_FK + " = " + disciplinaId + ")";

        Cursor cursor = database.rawQuery(selectQuery, null);

        Log.i("DB_TEST_QUERY", selectQuery);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Nota nota = AppDAO.fromCursorNota(cursor);
                    notas.add(nota);
                } while (cursor.moveToNext());
            }

            return notas;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }


    public int ratingDisciplina(ArrayList<RatingDisciplina> ratingList, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.RatingDisciplinaEntry.TABLE_NAME_RATING_DISCIPLINA);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < ratingList.size(); i++) {
            RatingDisciplina rating = ratingList.get(i);

            values.put(DataContract.RatingDisciplinaEntry.COLUMN_ALUNO_FK, rating.getAlunoKey());
            values.put(DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK, rating.getDisciplinaKey());
            values.put(DataContract.RatingDisciplinaEntry.COLUMN_RATING, rating.getRating());


            Log.i("RATING_TEST", "INSERTED!!!");
            returnedId = (int) database.insert(DataContract.RatingDisciplinaEntry.TABLE_NAME_RATING_DISCIPLINA, null, values);
            rating.setIdRatingDisciplina(returnedId);
        }


        return returnedId;

    }

    public ArrayList<Material> listarMateriaisByEvento(int eventoId) {

        ArrayList<Material> materiais = new ArrayList<>();

        String selectQuery =
                "SELECT * FROM " + DataContract.EventoUploadsEntry.TABLE_NAME_EVENTO_UPLOADS + "," + DataContract.EventoEntry.TABLE_NAME_EVENTO +
                        " WHERE " + "(" + DataContract.EventoUploadsEntry.EVENTO_FK + "=" + DataContract.EventoEntry.COLUMN_ID_SERVIDOR_EVENTO + ")" +
                        " AND " + "(" + DataContract.EventoEntry.COLUMN_ID_SERVIDOR_EVENTO + " = " + eventoId + ")";

        Cursor cursor = database.rawQuery(selectQuery, null);

        Log.i("DB_TEST_QUERY", selectQuery);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Material material = AppDAO.fromCursorMaterial(cursor);
                    materiais.add(material);
                } while (cursor.moveToNext());
            }

            return materiais;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }


    public ArrayList<Material> listarMateriaisByDisciplina(int disciplina) {

        ArrayList<Material> materiais = new ArrayList<>();

        String selectQuery =
                "SELECT * FROM " + DataContract.EventoUploadsEntry.TABLE_NAME_EVENTO_UPLOADS + "," + DataContract.EventoEntry.TABLE_NAME_EVENTO + "," + DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA +
                        " WHERE " + "(" + DataContract.EventoUploadsEntry.EVENTO_FK + "=" + DataContract.EventoEntry.COLUMN_ID_SERVIDOR_EVENTO + ")" +
                        " AND " + "(" + DataContract.EventoEntry.DISCIPLINA_FK + "=" + DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR + ")" +
                        " AND " + "(" + DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR + " = " + disciplina + ")";


        //Cursor cursor = database.query(DataContract.EventoUploadsEntry.TABLE_NAME_EVENTO_UPLOADS, PROJECTION_UPLOADS, null, null, null, null, null);
        Cursor cursor = database.rawQuery(selectQuery, null);

        Log.i("DB_TEST_QUERY", selectQuery);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Material material = AppDAO.fromCursorMaterial(cursor);
                    materiais.add(material);
                } while (cursor.moveToNext());
            }

            return materiais;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }


    public int criarProfessor(ArrayList<Professor> professorList, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.ProfessorEntry.TABLE_NAME_PROFESSOR);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < professorList.size(); i++) {
            Professor currentProfessor = professorList.get(i);

            values.put(DataContract.ProfessorEntry.COLUMN_NOME, currentProfessor.getNome());
            values.put(DataContract.ProfessorEntry.COLUMN_EMAIL, currentProfessor.getEmail());
            values.put(DataContract.ProfessorEntry.COLUMN_ANO_INGRESSO, currentProfessor.getAnoIngresso() == null ? -1 : currentProfessor.getAnoIngresso().getTime());
            values.put(DataContract.ProfessorEntry.COLUMN_FORMACAO, currentProfessor.getFormacao());
            values.put(DataContract.ProfessorEntry.TITULO_PROFESSOR_FK, currentProfessor.getTituloProfessor());
            values.put(DataContract.ProfessorEntry.COLUMN_PROFESSOR_ID_SERVIDOR, currentProfessor.getIdProfessorServidor());

            returnedId = (int) database.insert(DataContract.ProfessorEntry.TABLE_NAME_PROFESSOR, null, values);
            currentProfessor.setId(returnedId);
        }


        return returnedId;

    }


    public ArrayList<Professor> listarProfessores() {

        ArrayList<Professor> professores = new ArrayList<>();

        Cursor cursor = database.query(DataContract.ProfessorEntry.TABLE_NAME_PROFESSOR, PROJECTION_PROFESSOR, null, null, null, null, DataContract.ProfessorEntry.COLUMN_NOME);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Professor professor = AppDAO.fromCursorProfessor(cursor);
                    professores.add(professor);
                } while (cursor.moveToNext());
            }

            return professores;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public int criarTipoEvento(ArrayList<TipoEvento> tipoEventoList, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.ProfessorEntry.TABLE_NAME_PROFESSOR);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < tipoEventoList.size(); i++) {
            TipoEvento currentTipoEvento = tipoEventoList.get(i);

            values.put(DataContract.TipoEventoEntry.COLUMN_NOME, currentTipoEvento.getNomeTipoEvento());
            values.put(DataContract.TipoEventoEntry.COLUMN_TIPO_EVENTO_ID_SERVIDOR, currentTipoEvento.getTipoEventoIdServidor());

            returnedId = (int) database.insert(DataContract.TipoEventoEntry.TABLE_NAME_TIPO_EVENTO, null, values);
            currentTipoEvento.setTipoEventoId(returnedId);
        }

        if (returnedId != -1) {
            Log.i("DB_TEST", "Inserido com sucesso!!");
        }


        return returnedId;

    }


    public ArrayList<TipoEvento> listarTipoEventos() {

        ArrayList<TipoEvento> tipoEventos = new ArrayList<>();

        Cursor cursor = database.query(DataContract.TipoEventoEntry.TABLE_NAME_TIPO_EVENTO, PROJECTION_TIPO_EVENTO, null, null, null, null, DataContract.TipoEventoEntry.COLUMN_NOME);

        try {
            if (cursor.moveToFirst()) {
                do {
                    TipoEvento tipoEvento = AppDAO.fromCursorTipoEvento(cursor);
                    tipoEventos.add(tipoEvento);
                } while (cursor.moveToNext());
            }

            return tipoEventos;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public int criarTipoDisciplina(ArrayList<TipoDisciplina> tipoDisciplinaList, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.TipoDisciplinaEntry.TABLE_NAME_TIPO_DISCIPLINA);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < tipoDisciplinaList.size(); i++) {
            TipoDisciplina currentTipoDisciplina = tipoDisciplinaList.get(i);

            values.put(DataContract.TipoDisciplinaEntry.COLUMN_NOME, currentTipoDisciplina.getTipoDisciplinaDescricao());
            values.put(DataContract.TipoDisciplinaEntry.COLUMN_ID_SERVIDOR_TIPO_DISCIPLINA, currentTipoDisciplina.getTipoDisciplinaIdServidor());

            returnedId = (int) database.insert(DataContract.TipoDisciplinaEntry.TABLE_NAME_TIPO_DISCIPLINA, null, values);
            currentTipoDisciplina.setId(returnedId);
        }


        return returnedId;

    }

    public int criarTurma(ArrayList<Turma> turmaList, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.TurmaEntry.TABLE_NAME_TURMA);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < turmaList.size(); i++) {
            Turma currentTurma = turmaList.get(i);

            values.put(DataContract.TurmaEntry.COLUMN_NOME, currentTurma.getNomeTurma());
            values.put(DataContract.TurmaEntry.COLUMN_ID_SERVIDOR, currentTurma.getIdServidorTurma());

            returnedId = (int) database.insert(DataContract.TurmaEntry.TABLE_NAME_TURMA, null, values);
            currentTurma.setId(returnedId);
        }


        return returnedId;

    }


    public ArrayList<Turma> listarTurmas() {

        ArrayList<Turma> turmas = new ArrayList<>();

        Cursor cursor = database.query(DataContract.TurmaEntry.TABLE_NAME_TURMA, PROJECTION_TURMA, null, null, null, null, DataContract.TurmaEntry.COLUMN_NOME);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Turma turma = AppDAO.fromCursorTurma(cursor);
                    turmas.add(turma);
                } while (cursor.moveToNext());
            }

            return turmas;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public int criarMatricula(ArrayList<AlunoXDisciplina> matriculaList, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < matriculaList.size(); i++) {
            AlunoXDisciplina currentAlunoXDisciplina = matriculaList.get(i);

            values.put(DataContract.AlunoXDisciplinaEntry.ALUNO_FK, currentAlunoXDisciplina.getAluno());
            values.put(DataContract.AlunoXDisciplinaEntry.DISCIPLINA_FK, currentAlunoXDisciplina.getDisciplina());
            values.put(DataContract.AlunoXDisciplinaEntry.STATUS_DISCIPLINA_FK, currentAlunoXDisciplina.getStatusDisciplina());
            values.put(DataContract.AlunoXDisciplinaEntry.TURMA_FK, currentAlunoXDisciplina.getTurma());
            values.put(DataContract.AlunoXDisciplinaEntry.COLUMN_ID_SERVIDOR, currentAlunoXDisciplina.getAlunoXDisciplinaIdServidor());

            returnedId = (int) database.insert(DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA, null, values);
            currentAlunoXDisciplina.setId(returnedId);
        }


        return returnedId;

    }


    public ArrayList<AlunoXDisciplina> listarMatriculas() {

        ArrayList<AlunoXDisciplina> matriculas = new ArrayList<>();

        Cursor cursor = database.query(DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA, PROJECTION_ALUNO_X_DISCIPLINA, null, null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    AlunoXDisciplina alunoXDisciplina = AppDAO.fromCursorAlunoXDisciplina(cursor);
                    matriculas.add(alunoXDisciplina);
                } while (cursor.moveToNext());
            }

            return matriculas;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }


    public int criarStatusDisciplina(ArrayList<StatusDisciplina> statusDisciplinaList, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.StatusDisciplinaEntry.TABLE_NAME_STATUS_DISCIPLINA);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < statusDisciplinaList.size(); i++) {
            StatusDisciplina currentStatusDisciplina = statusDisciplinaList.get(i);

            values.put(DataContract.StatusDisciplinaEntry.COLUMN_NOME, currentStatusDisciplina.getStatusDisciplina());
            values.put(DataContract.StatusDisciplinaEntry.COLUMN_STATUS_DISCIPLINA_ID_SERVIDOR, currentStatusDisciplina.getIdServidorDisciplina());

            returnedId = (int) database.insert(DataContract.StatusDisciplinaEntry.TABLE_NAME_STATUS_DISCIPLINA, null, values);
            currentStatusDisciplina.setId(returnedId);
        }


        return returnedId;

    }


    public ArrayList<StatusDisciplina> listarStatusDisciplina() {

        ArrayList<StatusDisciplina> statusDisciplinas = new ArrayList<>();

        Cursor cursor = database.query(DataContract.StatusDisciplinaEntry.TABLE_NAME_STATUS_DISCIPLINA, PROJECTION_STATUS_DISCIPLINA, null, null, null, null, DataContract.StatusDisciplinaEntry.COLUMN_NOME);

        try {
            if (cursor.moveToFirst()) {
                do {
                    StatusDisciplina statusDisciplina = AppDAO.fromCursorStatusDisciplina(cursor);
                    statusDisciplinas.add(statusDisciplina);
                } while (cursor.moveToNext());
            }

            return statusDisciplinas;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public RatingDisciplina getRatingDisciplina(int idDisciplina) {


        String selectQuery =
                "SELECT " + DataContract.RatingDisciplinaEntry.TABLE_NAME_RATING_DISCIPLINA + "." + DataContract.RatingDisciplinaEntry.COLUMN_ID + "," + DataContract.RatingDisciplinaEntry.COLUMN_ALUNO_FK + "," + DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK + "," + DataContract.RatingDisciplinaEntry.COLUMN_RATING
                        + " FROM " + DataContract.RatingDisciplinaEntry.TABLE_NAME_RATING_DISCIPLINA + "," + DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA + ", " + DataContract.AlunoEntry.TABLE_NAME_ALUNO +
                        " WHERE " + "(" + DataContract.RatingDisciplinaEntry.COLUMN_ALUNO_FK + "=" + DataContract.AlunoEntry.COLUMN_ALUNO_ID_SERVIDOR + ")" +
                        " AND " + "(" + DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK + "=" + DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR + ")" +
                        " AND " + "(" + DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK + " = " + idDisciplina + ")";

        Cursor cursor = database.rawQuery(selectQuery, null);

        RatingDisciplina ratingDisciplina = null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    ratingDisciplina = AppDAO.fromCursorRatingDisciplina(cursor);
                } while (cursor.moveToNext());
            }

            return ratingDisciplina;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public int countNotifyStatus(int eventoId) {

        String countQuery = "SELECT COUNT(" + DataContract.NotifyStatusEvento.TABLE_NAME_NOTIFY_STATUS + "." + DataContract.NotifyStatusEvento.COLUMN_ID + ") FROM " + DataContract.NotifyStatusEvento.TABLE_NAME_NOTIFY_STATUS + " WHERE (" + DataContract.NotifyStatusEvento.EVENTO_FK + " = " + eventoId + ");";

        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }

    public int criarNotificationStatus(NotifyStatus notifyStatus) {

        ContentValues values = new ContentValues();
        int returnedId;

        values.put(DataContract.NotifyStatusEvento.COLUMN_NOTIFY_STATUS, notifyStatus.getNotifyStatus());
        values.put(DataContract.NotifyStatusEvento.EVENTO_FK, notifyStatus.getEventoKey());

        returnedId = (int) database.insert(DataContract.NotifyStatusEvento.TABLE_NAME_NOTIFY_STATUS, null, values);
        notifyStatus.setNotifyId(returnedId);

        return returnedId;

    }

    public void updateNotifyStatus(NotifyStatus status) {

        String updateQuery = "UPDATE " + DataContract.NotifyStatusEvento.TABLE_NAME_NOTIFY_STATUS + " SET " + DataContract.NotifyStatusEvento.COLUMN_NOTIFY_STATUS +
                " = " + status.getNotifyStatus() + " WHERE (" + DataContract.NotifyStatusEvento.EVENTO_FK + " = " + status.getEventoKey() + ");";

        Cursor cursor = database.rawQuery(updateQuery, null);
        cursor.moveToFirst();
        cursor.close();

    }


    public int countEventoRead(int eventoId) {

        String countQuery = "SELECT COUNT(" + DataContract.EventoReadEntry.TABLE_NAME_EVENTO_READ + "." + DataContract.EventoReadEntry.COLUMN_ID + ") FROM " + DataContract.EventoReadEntry.TABLE_NAME_EVENTO_READ + " WHERE (" + DataContract.EventoReadEntry.EVENTO_FK + " = " + eventoId + ");";

        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }

    public int setEventoRead(EventoRead eventoRead) {

        ContentValues values = new ContentValues();
        int returnedId;

        values.put(DataContract.EventoReadEntry.COLUMN_EVENTO_READ, eventoRead.getEventoReadStatus());
        values.put(DataContract.EventoReadEntry.EVENTO_FK, eventoRead.getEventoKey());

        returnedId = (int) database.insert(DataContract.EventoReadEntry.TABLE_NAME_EVENTO_READ, null, values);
        eventoRead.setEventoReadId(returnedId);

        return returnedId;

    }

    public void updateEventoRead(EventoRead eventoRead) {

        String updateQuery = "UPDATE " + DataContract.EventoReadEntry.TABLE_NAME_EVENTO_READ + " SET " + DataContract.EventoReadEntry.COLUMN_EVENTO_READ +
                " = " + eventoRead.getEventoReadStatus() + " WHERE (" + DataContract.EventoReadEntry.EVENTO_FK + " = " + eventoRead.getEventoKey() + ");";

        Cursor cursor = database.rawQuery(updateQuery, null);
        cursor.moveToFirst();
        cursor.close();
    }

    public int countEventoFavorite(int eventoId) {

        String countQuery = "SELECT COUNT(" + DataContract.EventoFavoriteEntry.TABLE_NAME_EVENTO_FAVORITE + "." + DataContract.EventoFavoriteEntry.COLUMN_ID + ") FROM " + DataContract.EventoFavoriteEntry.TABLE_NAME_EVENTO_FAVORITE + " WHERE (" + DataContract.EventoFavoriteEntry.EVENTO_FK + " = " + eventoId + ");";

        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);

        cursor.close();

        return count;
    }

    public int setEventoFavorite(EventoFavorite eventoFavorite) {

        ContentValues values = new ContentValues();
        int returnedId;

        values.put(DataContract.EventoFavoriteEntry.COLUMN_EVENTO_FAVORITE, eventoFavorite.getEventoFavoriteStatus());
        values.put(DataContract.EventoFavoriteEntry.EVENTO_FK, eventoFavorite.getEventoKey());

        returnedId = (int) database.insert(DataContract.EventoReadEntry.TABLE_NAME_EVENTO_READ, null, values);
        eventoFavorite.setEventoFavoriteId(returnedId);

        return returnedId;

    }

    public void updateEventoFavorite(EventoFavorite eventoFavorite) {

        String updateQuery = "UPDATE " + DataContract.EventoFavoriteEntry.TABLE_NAME_EVENTO_FAVORITE + " SET " + DataContract.EventoFavoriteEntry.COLUMN_EVENTO_FAVORITE +
                " = " + eventoFavorite.getEventoFavoriteStatus() + " WHERE (" + DataContract.EventoFavoriteEntry.EVENTO_FK + " = " + eventoFavorite.getEventoKey() + ");";

        Cursor cursor = database.rawQuery(updateQuery, null);
        cursor.moveToFirst();
        cursor.close();
    }


    public void updateRatingDisciplina(int idAluno, int idDisciplina, float rating) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataContract.RatingDisciplinaEntry.COLUMN_RATING, rating);

        String updateQuery = "UPDATE " + DataContract.RatingDisciplinaEntry.TABLE_NAME_RATING_DISCIPLINA + " SET " + DataContract.RatingDisciplinaEntry.COLUMN_RATING +
                " = " + rating + " WHERE (" + DataContract.RatingDisciplinaEntry.COLUMN_ALUNO_FK + " = " + idAluno + ") AND (" + DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK + " = " + idDisciplina + ");";

        Cursor cursor = database.rawQuery(updateQuery, null);
        cursor.moveToFirst();
        cursor.close();
    }

    public RatingDisciplina getRatingDisciplina(int idAluno, int idDisciplina) {


        String selectQuery =
                "SELECT " + DataContract.RatingDisciplinaEntry.TABLE_NAME_RATING_DISCIPLINA + "." + DataContract.RatingDisciplinaEntry.COLUMN_ID + "," + DataContract.RatingDisciplinaEntry.COLUMN_ALUNO_FK + "," + DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK + "," + DataContract.RatingDisciplinaEntry.COLUMN_RATING
                        + " FROM " + DataContract.RatingDisciplinaEntry.TABLE_NAME_RATING_DISCIPLINA + "," + DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA + ", " + DataContract.AlunoEntry.TABLE_NAME_ALUNO +
                        " WHERE " + "(" + DataContract.RatingDisciplinaEntry.COLUMN_ALUNO_FK + "=" + DataContract.AlunoEntry.COLUMN_ALUNO_ID_SERVIDOR + ")" +
                        " AND " + "(" + DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK + "=" + DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR + ")" +
                        " AND " + "(" + DataContract.RatingDisciplinaEntry.COLUMN_ALUNO_FK + " = " + idAluno + ")" +
                        " AND " + "(" + DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK + " = " + idDisciplina + ")";

        Cursor cursor = database.rawQuery(selectQuery, null);

        RatingDisciplina ratingDisciplina = null;
        try {
            if (cursor.moveToFirst()) {
                do {
                    ratingDisciplina = AppDAO.fromCursorRatingDisciplina(cursor);
                } while (cursor.moveToNext());
            }

            return ratingDisciplina;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public int getRatingDisciplinaCount(int idDisciplina) {

        String countQuery = "SELECT COUNT(" + DataContract.RatingDisciplinaEntry.TABLE_NAME_RATING_DISCIPLINA + "." + DataContract.RatingDisciplinaEntry.COLUMN_ID + ") FROM " + DataContract.RatingDisciplinaEntry.TABLE_NAME_RATING_DISCIPLINA + " WHERE (" + DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK + " = " + idDisciplina + ");";

        Log.i("COUNT_QUERY", countQuery);

        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    public int getMatriculasCount(int idDisciplina) {

        String countQuery = "SELECT COUNT(" + DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA + "." + DataContract.AlunoXDisciplinaEntry.COLUMN_ID + ") FROM " + DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA + " WHERE (" + DataContract.AlunoXDisciplinaEntry.DISCIPLINA_FK + " = " + idDisciplina + ");";

        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    public float getRatingDisciplinaAVG(int idDisciplina) {

        String countAVG = "SELECT AVG(" + DataContract.RatingDisciplinaEntry.COLUMN_RATING + ") FROM " + DataContract.RatingDisciplinaEntry.TABLE_NAME_RATING_DISCIPLINA + " WHERE (" + DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK + " = " + idDisciplina + ");";

        Cursor cursor = database.rawQuery(countAVG, null);

        cursor.moveToFirst();
        float avg = cursor.getFloat(0);
        cursor.close();


        return avg;
    }

    public ArrayList<TipoDisciplina> listarTipoDisciplinas() {

        ArrayList<TipoDisciplina> tipoDisciplinas = new ArrayList<>();

        Cursor cursor = database.query(DataContract.TipoDisciplinaEntry.TABLE_NAME_TIPO_DISCIPLINA, PROJECTION_TIPO_DISCIPLINA, null, null, null, null, DataContract.TipoDisciplinaEntry.COLUMN_NOME);

        try {
            if (cursor.moveToFirst()) {
                do {
                    TipoDisciplina tipoDisciplina = AppDAO.fromCursorTipoDisciplina(cursor);
                    tipoDisciplinas.add(tipoDisciplina);
                } while (cursor.moveToNext());
            }

            return tipoDisciplinas;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public int criarAluno(ArrayList<Aluno> alunoList, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.AlunoEntry.TABLE_NAME_ALUNO);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < alunoList.size(); i++) {
            Aluno currentAluno = alunoList.get(i);

            values.put(DataContract.AlunoEntry.COLUMN_NOME, currentAluno.getNome());
            values.put(DataContract.AlunoEntry.COLUMN_EMAIL, currentAluno.getEmail());
            values.put(DataContract.AlunoEntry.COLUMN_RGA, currentAluno.getRga());
            values.put(DataContract.AlunoEntry.COLUMN_ALUNO_ID_SERVIDOR, currentAluno.getAlunoIdServidor());
            values.put(DataContract.AlunoEntry.STATUS_FK, currentAluno.getStatusAluno());

            returnedId = (int) database.insert(DataContract.AlunoEntry.TABLE_NAME_ALUNO, null, values);
            currentAluno.setId(returnedId);
        }


        return returnedId;

    }

    public int criarAluno(Aluno aluno) {

        ContentValues values = new ContentValues();
        int returnedId = -1;

        values.put(DataContract.AlunoEntry.COLUMN_NOME, aluno.getNome());
        values.put(DataContract.AlunoEntry.COLUMN_EMAIL, aluno.getEmail());
        values.put(DataContract.AlunoEntry.COLUMN_RGA, aluno.getRga());
        values.put(DataContract.AlunoEntry.COLUMN_ALUNO_ID_SERVIDOR, aluno.getAlunoIdServidor());
        values.put(DataContract.AlunoEntry.STATUS_FK, aluno.getStatusAluno());

        returnedId = (int) database.insert(DataContract.AlunoEntry.TABLE_NAME_ALUNO, null, values);
        aluno.setId(returnedId);

        return returnedId;

    }


    public ArrayList<Aluno> listarAlunos() {

        ArrayList<Aluno> alunos = new ArrayList<>();

        Cursor cursor = database.query(DataContract.AlunoEntry.TABLE_NAME_ALUNO, PROJECTION_ALUNOS, null, null, null, null, DataContract.AlunoEntry.COLUMN_NOME);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Aluno aluno = AppDAO.fromCursorAluno(cursor);
                    alunos.add(aluno);
                } while (cursor.moveToNext());
            }

            return alunos;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    public int criarTituloProfessor(ArrayList<TituloProfessor> tituloProfessorList, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.TituloProfessorEntry.TABLE_NAME_TITULO_PROFESSOR);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < tituloProfessorList.size(); i++) {
            TituloProfessor currentTituloProfessor = tituloProfessorList.get(i);

            values.put(DataContract.TituloProfessorEntry.COLUMN_NOME, currentTituloProfessor.getTituloProfessor());
            values.put(DataContract.TituloProfessorEntry.COLUMN_ID_SERVIDOR_TITULO_PROFESSOR, currentTituloProfessor.getTituloProfessorIdServidor());

            returnedId = (int) database.insert(DataContract.TituloProfessorEntry.TABLE_NAME_TITULO_PROFESSOR, null, values);
            currentTituloProfessor.setId(returnedId);
        }


        return returnedId;

    }


    public ArrayList<TituloProfessor> listarTituloProfessores() {

        ArrayList<TituloProfessor> tituloProfessores = new ArrayList<>();

        Cursor cursor = database.query(DataContract.TituloProfessorEntry.TABLE_NAME_TITULO_PROFESSOR, PROJECTION_TITULO_PROFESSOR, null, null, null, null, DataContract.TituloProfessorEntry.COLUMN_NOME);

        try {
            if (cursor.moveToFirst()) {
                do {
                    TituloProfessor titulo = AppDAO.fromCursorTituloProfessor(cursor);
                    tituloProfessores.add(titulo);
                } while (cursor.moveToNext());
            }

            return tituloProfessores;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }


    public int criarStatusAluno(ArrayList<StatusAluno> statusAlunoList, boolean clearPrevious) {

        if (clearPrevious) {
            deleteTable(DataContract.AlunoStatusEntry.TABLE_NAME_STATUS_ALUNO);
        }

        ContentValues values = new ContentValues();
        int returnedId = -1;


        for (int i = 0; i < statusAlunoList.size(); i++) {
            StatusAluno currentStatusAluno = statusAlunoList.get(i);

            values.put(DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_NOME, currentStatusAluno.getStatusAlunoDescricao());
            values.put(DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_ID_SERVIDOR, currentStatusAluno.getStatusAlunoIdServidor());

            returnedId = (int) database.insert(DataContract.AlunoStatusEntry.TABLE_NAME_STATUS_ALUNO, null, values);
            currentStatusAluno.setIdStatusAluno(returnedId);
        }


        return returnedId;

    }


    public ArrayList<StatusAluno> listarStatusAlunos() {

        ArrayList<StatusAluno> statusAlunos = new ArrayList<>();

        Cursor cursor = database.query(DataContract.AlunoStatusEntry.TABLE_NAME_STATUS_ALUNO, PROJECTION_STATUS_ALUNO, null, null, null, null, DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_NOME);

        try {
            if (cursor.moveToFirst()) {
                do {
                    StatusAluno statusAluno = AppDAO.fromCursorStatusAluno(cursor);
                    statusAlunos.add(statusAluno);
                } while (cursor.moveToNext());
            }

            return statusAlunos;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }


   /* public ArrayList<Disciplina> listarDisciplinas(int limit) {

        ArrayList<Disciplina> disciplinas = new ArrayList<>();

        Cursor cursor = database.query(DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA, PROJECTION_DISCIPLINA, null, null, null, null, DataContract.DisciplinaEntry.COLUMN_NOME + " limit " + limit);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Disciplina disciplina = AppDAO.fromCursorDisciplina(cursor);
                    disciplinas.add(disciplina);
                } while (cursor.moveToNext());
            }

            return disciplinas;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }*/


    public Disciplina disciplinaById(int id) {

        Cursor cursor = database.query(DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA, PROJECTION_DISCIPLINA,
                DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        Disciplina disciplina = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    disciplina = fromCursorDisciplina(cursor);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return disciplina;
    }

    public ArrayList<AlunoXDisciplina> listMatriculas(int disciplina) {

        // Cursor cursor = database.query(DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA, PROJECTION_ALUNO_X_DISCIPLINA,
        // DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        ArrayList<AlunoXDisciplina> matriculas = new ArrayList<>();

        Cursor cursor = database.query(DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA, PROJECTION_ALUNO_X_DISCIPLINA,
                DataContract.AlunoXDisciplinaEntry.DISCIPLINA_FK + " = ?",
                new String[]{String.valueOf(disciplina)},
                null,
                null,
                null);

        AlunoXDisciplina matricula;

        try {
            if (cursor.moveToFirst()) {
                do {
                    matricula = fromCursorAlunoXDisciplina(cursor);
                    matriculas.add(matricula);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return matriculas;
    }

    public Evento eventoById(int id) {

        Cursor cursor = database.query(DataContract.EventoEntry.TABLE_NAME_EVENTO, PROJECTION_EVENTO,
                DataContract.EventoEntry.COLUMN_ID_SERVIDOR_EVENTO + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        Evento evento = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    evento = fromCursorEvento(cursor);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return evento;
    }

    public EventoRead eventoReadById(int eventoId) {

        Cursor cursor = database.query(DataContract.EventoReadEntry.TABLE_NAME_EVENTO_READ, PROJECTION_EVENTO_READ,
                DataContract.EventoReadEntry.EVENTO_FK + " = ?", new String[]{String.valueOf(eventoId)}, null, null, null);

        EventoRead eventoRead = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    eventoRead = fromCursorEventoRead(cursor);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return eventoRead;
    }

    public NotifyStatus notifyById(int eventoId) {

        Cursor cursor = database.query(DataContract.NotifyStatusEvento.TABLE_NAME_NOTIFY_STATUS, PROJECTION_NOTIFY_EVENTO,
                DataContract.NotifyStatusEvento.EVENTO_FK + " = ?", new String[]{String.valueOf(eventoId)}, null, null, null);

        NotifyStatus notifyStatus = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    notifyStatus = fromCursorNotifyEvento(cursor);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return notifyStatus;
    }


    public TipoEvento tipoEventoById(int id) {

        Cursor cursor = database.query(DataContract.TipoEventoEntry.TABLE_NAME_TIPO_EVENTO, PROJECTION_TIPO_EVENTO,
                DataContract.TipoEventoEntry.COLUMN_TIPO_EVENTO_ID_SERVIDOR + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        TipoEvento tipoEvento = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    tipoEvento = fromCursorTipoEvento(cursor);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return tipoEvento;
    }

    public Aluno alunoById(int id) {

        Cursor cursor = database.query(DataContract.AlunoEntry.TABLE_NAME_ALUNO, PROJECTION_ALUNOS,
                DataContract.AlunoEntry.COLUMN_ALUNO_ID_SERVIDOR + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        Aluno aluno = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    aluno = fromCursorAluno(cursor);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return aluno;
    }

    public Aluno alunoByEmail(String email) {

        Cursor cursor = database.query(DataContract.AlunoEntry.TABLE_NAME_ALUNO, PROJECTION_ALUNOS,
                DataContract.AlunoEntry.COLUMN_EMAIL + " = ?", new String[]{String.valueOf(email)}, null, null, null);

        Aluno aluno = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    aluno = fromCursorAluno(cursor);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return aluno;
    }

    public TipoDisciplina tipoDisciplinaById(int id) {

        Cursor cursor = database.query(DataContract.TipoDisciplinaEntry.TABLE_NAME_TIPO_DISCIPLINA, PROJECTION_TIPO_DISCIPLINA,
                DataContract.TipoDisciplinaEntry.COLUMN_ID_SERVIDOR_TIPO_DISCIPLINA + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        TipoDisciplina tipoDisciplina = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    tipoDisciplina = fromCursorTipoDisciplina(cursor);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return tipoDisciplina;
    }

    public Professor professorById(int id) {

        Cursor cursor = database.query(DataContract.ProfessorEntry.TABLE_NAME_PROFESSOR, PROJECTION_PROFESSOR,
                DataContract.ProfessorEntry.COLUMN_PROFESSOR_ID_SERVIDOR + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        Professor professor = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    professor = fromCursorProfessor(cursor);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return professor;
    }

    public TituloProfessor tituloProfessorById(int id) {

        Cursor cursor = database.query(DataContract.TituloProfessorEntry.TABLE_NAME_TITULO_PROFESSOR, PROJECTION_TITULO_PROFESSOR,
                DataContract.TituloProfessorEntry.COLUMN_ID_SERVIDOR_TITULO_PROFESSOR + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        TituloProfessor tituloProfessor = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    tituloProfessor = fromCursorTituloProfessor(cursor);
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }


        return tituloProfessor;
    }


    public void deleteTable(String table) {
        database.delete(table, null, null);
    }


    public int novoProfessor(Professor professor) {
        ContentValues values = new ContentValues();
        int returnedId;

        try {
            database.beginTransaction();
            values.put(DataContract.ProfessorEntry.COLUMN_NOME, professor.getNome());
            values.put(DataContract.ProfessorEntry.COLUMN_EMAIL, professor.getEmail());
            values.put(DataContract.ProfessorEntry.COLUMN_ANO_INGRESSO, DateUtils.getFormattedDate(professor.getAnoIngresso()));
            values.put(DataContract.ProfessorEntry.COLUMN_FORMACAO, professor.getFormacao());
            values.put(DataContract.ProfessorEntry.TITULO_PROFESSOR_FK, professor.getTituloProfessor());
            returnedId = (int) database.insert(DataContract.ProfessorEntry.TABLE_NAME_PROFESSOR, null, values);
            database.setTransactionSuccessful();

        } finally {
            if (database != null) {
                database.endTransaction();
            }
        }
        return returnedId;
    }

    public int novoTituloProfessor(TituloProfessor tituloProfessor) {
        ContentValues values = new ContentValues();
        int returnedId;

        try {
            database.beginTransaction();

            values.put(DataContract.TituloProfessorEntry.COLUMN_NOME, tituloProfessor.getTituloProfessor());
            returnedId = (int) database.insert(DataContract.TituloProfessorEntry.TABLE_NAME_TITULO_PROFESSOR, null, values);
            database.setTransactionSuccessful();

        } finally {
            if (database != null) {
                database.endTransaction();
            }
        }

        return returnedId;
    }


    /**
     * @param disciplina recebe como parâmetro
     * @return o id do objeto que acabou de ser inserido no banco de dados
     **/

    public int criarDisciplina(Disciplina disciplina) {
        ContentValues values = new ContentValues();
        int returnedId;

        try {
            // inicia uma transação no objeto SQLiteDatabase
            database.beginTransaction();

            // mapeia os pares de chave-valor do objeto disciplina para coluna no banco de dados usando o objeto ContentValues
            values.put(DataContract.DisciplinaEntry.COLUMN_CODIGO, disciplina.getCodigo());
            values.put(DataContract.DisciplinaEntry.COLUMN_NOME, disciplina.getTitulo());
            values.put(DataContract.DisciplinaEntry.COLUMN_DESCRICAO, disciplina.getDescricao());
            values.put(DataContract.DisciplinaEntry.PROFESSOR_FK, disciplina.getProfessor());
            values.put(DataContract.DisciplinaEntry.TIPO_DISCIPLINA_FK, disciplina.getTipoDisciplina());

            // insere no banco de dados e retorna o id inserido
            returnedId = (int) database.insert(DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA, null, values);

            // commita a transação (seta com bem-sucedida)
            database.setTransactionSuccessful();

        } finally {
            // verifica se o objeto de database que estava sendo usado não está nulo
            if (database != null) {
                // finaliza a transação com o banco de dados
                database.endTransaction();
            }
        }

        return returnedId;
    }


    /**
     * Recebe um objeto do tipo cursor como parâmetro, extrai os dados do cursor e retorna um objeto do tipo Disciplina
     *
     * @param cursor cursor
     * @return Disciplina
     */

    private static Evento fromCursorEvento(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.EventoEntry.COLUMN_ID));
        String nomeEvento = cursor.getString(cursor.getColumnIndex(DataContract.EventoEntry.COLUMN_NOME));
        String descricaoEvento = cursor.getString(cursor.getColumnIndex(DataContract.EventoEntry.COLUMN_DESCRICAO));

        long eventoTimeStampInMilliseconds = cursor.getLong(cursor.getColumnIndex(DataContract.EventoEntry.COLUMN_TIMESTAMP));
        Date timeStampEvento = (eventoTimeStampInMilliseconds != -1 ? new Date(eventoTimeStampInMilliseconds) : null);

        long eventoDataLimiteInMilliseconds = cursor.getLong(cursor.getColumnIndex(DataContract.EventoEntry.COLUMN_DATA_LIMITE));
        Date dataLimiteEvento = (eventoDataLimiteInMilliseconds != -1 ? new Date(eventoDataLimiteInMilliseconds) : null);

        String smallIcon = cursor.getString(cursor.getColumnIndex(DataContract.EventoEntry.COLUMN_SMALL_ICON_PATH));
        String bigIcon = cursor.getString(cursor.getColumnIndex(DataContract.EventoEntry.COLUMN_BIG_ICON_PATH));

        int idEventoServidor = cursor.getInt(cursor.getColumnIndex(DataContract.EventoEntry.COLUMN_ID_SERVIDOR_EVENTO));

        int tipoEvento = cursor.getInt(cursor.getColumnIndex(DataContract.EventoEntry.TIPO_EVENTO_FK));
        int disciplina = cursor.getInt(cursor.getColumnIndex(DataContract.EventoEntry.DISCIPLINA_FK));


        return new Evento(id, nomeEvento, descricaoEvento, timeStampEvento, dataLimiteEvento, smallIcon, bigIcon, idEventoServidor, tipoEvento, disciplina);
    }

    private static TipoEvento fromCursorTipoEvento(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.TipoEventoEntry.COLUMN_ID));
        String nomeTipoEvento = cursor.getString(cursor.getColumnIndex(DataContract.TipoEventoEntry.COLUMN_NOME));
        int tipoEventoIdServidor = cursor.getInt(cursor.getColumnIndex(DataContract.TipoEventoEntry.COLUMN_TIPO_EVENTO_ID_SERVIDOR));


        return new TipoEvento(id, nomeTipoEvento, tipoEventoIdServidor);
    }


    private static NotifyStatus fromCursorNotifyEvento(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.NotifyStatusEvento.COLUMN_ID));
        int notify = cursor.getInt(cursor.getColumnIndex(DataContract.NotifyStatusEvento.COLUMN_NOTIFY_STATUS));
        int eventoKey = cursor.getInt(cursor.getColumnIndex(DataContract.NotifyStatusEvento.EVENTO_FK));


        return new NotifyStatus(id, notify, eventoKey);
    }

    private static EventoRead fromCursorEventoRead(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.EventoReadEntry.COLUMN_ID));
        int eventoRead = cursor.getInt(cursor.getColumnIndex(DataContract.EventoReadEntry.COLUMN_EVENTO_READ));
        int eventoKey = cursor.getInt(cursor.getColumnIndex(DataContract.EventoReadEntry.EVENTO_FK));


        return new EventoRead(id, eventoRead, eventoKey);
    }

    private static AlunoXDisciplina fromCursorAlunoXDisciplina(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.AlunoXDisciplinaEntry.COLUMN_ID));
        int aluno = cursor.getInt(cursor.getColumnIndex(DataContract.AlunoXDisciplinaEntry.ALUNO_FK));
        int disciplina = cursor.getInt(cursor.getColumnIndex(DataContract.AlunoXDisciplinaEntry.DISCIPLINA_FK));
        int statusDisciplina = cursor.getInt(cursor.getColumnIndex(DataContract.AlunoXDisciplinaEntry.STATUS_DISCIPLINA_FK));
        int turma = cursor.getInt(cursor.getColumnIndex(DataContract.AlunoXDisciplinaEntry.TURMA_FK));
        int idServidor = cursor.getInt(cursor.getColumnIndex(DataContract.AlunoXDisciplinaEntry.COLUMN_ID_SERVIDOR));


        return new AlunoXDisciplina(id, aluno, disciplina, statusDisciplina, turma, idServidor);
    }


    private static StatusDisciplina fromCursorStatusDisciplina(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.StatusDisciplinaEntry.COLUMN_ID));
        String nomeStatusDisciplina = cursor.getString(cursor.getColumnIndex(DataContract.StatusDisciplinaEntry.COLUMN_NOME));
        int statusDisciplinaIdServidor = cursor.getInt(cursor.getColumnIndex(DataContract.StatusDisciplinaEntry.COLUMN_STATUS_DISCIPLINA_ID_SERVIDOR));


        return new StatusDisciplina(id, nomeStatusDisciplina, statusDisciplinaIdServidor);
    }

    private static RatingDisciplina fromCursorRatingDisciplina(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.RatingDisciplinaEntry.COLUMN_ID));
        int alunoKey = cursor.getInt(cursor.getColumnIndex(DataContract.RatingDisciplinaEntry.COLUMN_ALUNO_FK));
        int disciplinaKey = cursor.getInt(cursor.getColumnIndex(DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK));
        float rating = cursor.getFloat(cursor.getColumnIndex(DataContract.RatingDisciplinaEntry.COLUMN_RATING));


        return new RatingDisciplina(id, alunoKey, disciplinaKey, rating);
    }

    private static Turma fromCursorTurma(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.TurmaEntry.COLUMN_ID));
        String nomeTurma = cursor.getString(cursor.getColumnIndex(DataContract.TurmaEntry.COLUMN_NOME));
        int turmaIdServidor = cursor.getInt(cursor.getColumnIndex(DataContract.TurmaEntry.COLUMN_ID_SERVIDOR));


        return new Turma(id, nomeTurma, turmaIdServidor);
    }

    private static StatusAluno fromCursorStatusAluno(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_ID));
        String statusAlunoDescricao = cursor.getString(cursor.getColumnIndex(DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_NOME));
        int statusAlunoIdServidor = cursor.getInt(cursor.getColumnIndex(DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_ID_SERVIDOR));


        return new StatusAluno(id, statusAlunoDescricao, statusAlunoIdServidor);
    }

    private static Aluno fromCursorAluno(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.AlunoEntry.COLUMN_ID));
        String nomeAluno = cursor.getString(cursor.getColumnIndex(DataContract.AlunoEntry.COLUMN_NOME));
        String emailAluno = cursor.getString(cursor.getColumnIndex(DataContract.AlunoEntry.COLUMN_EMAIL));
        String RGAAluno = cursor.getString(cursor.getColumnIndex(DataContract.AlunoEntry.COLUMN_RGA));
        int statusAlunoKey = cursor.getInt(cursor.getColumnIndex(DataContract.AlunoEntry.STATUS_FK));
        int statusAlunoIdServidor = cursor.getInt(cursor.getColumnIndex(DataContract.AlunoEntry.COLUMN_ALUNO_ID_SERVIDOR));


        return new Aluno(id, nomeAluno, emailAluno, RGAAluno, statusAlunoKey, statusAlunoIdServidor);
    }


    private static TipoDisciplina fromCursorTipoDisciplina(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.TipoDisciplinaEntry.COLUMN_ID));
        String tipoDisciplinaDescricao = cursor.getString(cursor.getColumnIndex(DataContract.TipoDisciplinaEntry.COLUMN_NOME));
        int tipoDisciplinaIdServidor = cursor.getInt(cursor.getColumnIndex(DataContract.TipoDisciplinaEntry.COLUMN_ID_SERVIDOR_TIPO_DISCIPLINA));


        return new TipoDisciplina(id, tipoDisciplinaDescricao, tipoDisciplinaIdServidor);
    }

    private static TituloProfessor fromCursorTituloProfessor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.TituloProfessorEntry.COLUMN_ID));
        String descricaoTituloProfessor = cursor.getString(cursor.getColumnIndex(DataContract.TituloProfessorEntry.COLUMN_NOME));
        int tituloProfessorIdServidor = cursor.getInt(cursor.getColumnIndex(DataContract.TituloProfessorEntry.COLUMN_ID_SERVIDOR_TITULO_PROFESSOR));


        return new TituloProfessor(id, descricaoTituloProfessor, tituloProfessorIdServidor);
    }

    private static Nota fromCursorNota(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.NotaEntry.COLUMN_ID));
        String descricaoNota = cursor.getString(cursor.getColumnIndex(DataContract.NotaEntry.COLUMN_DESCRICAO_NOTA));
        float nota = cursor.getInt(cursor.getColumnIndex(DataContract.NotaEntry.COLUMN_NOTA));
        int alunoXDisciplinaKey = cursor.getInt(cursor.getColumnIndex(DataContract.NotaEntry.ALUNO_X_DISCIPLINA_FK));


        return new Nota(id, descricaoNota, nota, alunoXDisciplinaKey);
    }

    private static Material fromCursorMaterial(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.EventoUploadsEntry.COLUMN_ID));
        String materialPath = cursor.getString(cursor.getColumnIndex(DataContract.EventoUploadsEntry.COLUMN_UPLOAD_PATH));
        int eventoKey = cursor.getInt(cursor.getColumnIndex(DataContract.EventoUploadsEntry.EVENTO_FK));


        return new Material(id, materialPath, eventoKey);
    }


    /**
     * Recebe um objeto do tipo cursor como parâmetro, extrai os dados do cursor e retorna um objeto do tipo Disciplina
     *
     * @param cursor cursor
     * @return Disciplina
     */

    private static Disciplina fromCursorDisciplina(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.DisciplinaEntry.COLUMN_ID));
        String codigo = cursor.getString(cursor.getColumnIndex(DataContract.DisciplinaEntry.COLUMN_CODIGO));
        String nome = cursor.getString(cursor.getColumnIndex(DataContract.DisciplinaEntry.COLUMN_NOME));
        String descricao = cursor.getString(cursor.getColumnIndex(DataContract.DisciplinaEntry.COLUMN_DESCRICAO));
        int cargaHoraria = cursor.getInt(cursor.getColumnIndex(DataContract.DisciplinaEntry.COLUMN_CARGA_HORARIA));
        int idServidorDisciplina = cursor.getInt(cursor.getColumnIndex(DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR));

        int tipoDisciplina = cursor.getInt(cursor.getColumnIndex(DataContract.DisciplinaEntry.TIPO_DISCIPLINA_FK));
        int professor = cursor.getInt(cursor.getColumnIndex(DataContract.DisciplinaEntry.PROFESSOR_FK));

        return new Disciplina(id, nome, codigo, descricao, cargaHoraria, idServidorDisciplina, tipoDisciplina, professor);
    }

    /**
     * Recebe um objeto do tipo cursor como parâmetro, extrai os dados do cursor e retorna um objeto do tipo Professor
     *
     * @param cursor cursor
     * @return Professor
     */

    private static Professor fromCursorProfessor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndex(DataContract.ProfessorEntry.COLUMN_ID));
        String nome = cursor.getString(cursor.getColumnIndex(DataContract.ProfessorEntry.COLUMN_NOME));
        String email = cursor.getString(cursor.getColumnIndex(DataContract.ProfessorEntry.COLUMN_EMAIL));
        long anoIngresso = cursor.getLong(cursor.getColumnIndex(DataContract.ProfessorEntry.COLUMN_ANO_INGRESSO));
        String formacao = cursor.getString(cursor.getColumnIndex(DataContract.ProfessorEntry.COLUMN_FORMACAO));
        int tituloProfessor = cursor.getInt(cursor.getColumnIndex(DataContract.ProfessorEntry.TITULO_PROFESSOR_FK));
        int idProfessorServidor = cursor.getInt(cursor.getColumnIndex(DataContract.ProfessorEntry.COLUMN_PROFESSOR_ID_SERVIDOR));

        return new Professor(id, nome, email, (anoIngresso != -1 ? new Date(anoIngresso) : null),
                formacao, idProfessorServidor, tituloProfessor);
    }


    public int novoAluno(Aluno aluno) {
        ContentValues values = new ContentValues();
        long returnedId;

        try {
            database.beginTransaction();

            values.put(DataContract.AlunoEntry.COLUMN_NOME, aluno.getNome());
            values.put(DataContract.AlunoEntry.COLUMN_EMAIL, aluno.getEmail());
            values.put(DataContract.AlunoEntry.COLUMN_RGA, aluno.getRga());
            values.put(DataContract.AlunoEntry.STATUS_FK, aluno.getStatusAluno());
            returnedId = database.insert(DataContract.AlunoEntry.TABLE_NAME_ALUNO, null, values);
            database.setTransactionSuccessful();

        } finally {
            if (database != null) {
                database.endTransaction();
            }
        }

        return (int) returnedId;
    }

    public int novoAlunoXDisciplina(AlunoXDisciplina alunoXDisciplina) {
        ContentValues values = new ContentValues();
        long returnedId;

        try {
            database.beginTransaction();

            values.put(DataContract.AlunoXDisciplinaEntry.ALUNO_FK, alunoXDisciplina.getAluno());
            values.put(DataContract.AlunoXDisciplinaEntry.DISCIPLINA_FK, alunoXDisciplina.getDisciplina());
            values.put(DataContract.AlunoXDisciplinaEntry.STATUS_DISCIPLINA_FK, alunoXDisciplina.getStatusDisciplina());
            returnedId = database.insert(DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA, null, values);
            database.setTransactionSuccessful();

        } finally {
            if (database != null) {
                database.endTransaction();
            }
        }

        return (int) returnedId;
    }

    public int adicionarNota(Nota nota) {
        ContentValues values = new ContentValues();
        long returnedId;

        try {
            database.beginTransaction();

            values.put(DataContract.NotaEntry.COLUMN_NOTA, nota.getNota());
            values.put(DataContract.NotaEntry.ALUNO_X_DISCIPLINA_FK, nota.getAlunoXDisciplina());
            returnedId = database.insert(DataContract.NotaEntry.TABLE_NAME_NOTA, null, values);
            database.setTransactionSuccessful();

        } finally {
            if (database != null) {
                database.endTransaction();
            }
        }

        return (int) returnedId;
    }

    public int criarTipoCurso(TipoCurso tipoCurso) {
        ContentValues values = new ContentValues();
        long returnedId;

        try {
            database.beginTransaction();
            values.put(DataContract.TipoCursoEntry.COLUMN_NOME, tipoCurso.getTipoCurso());
            returnedId = database.insert(DataContract.TipoCursoEntry.TABLE_NAME_TIPO_CURSO, null, values);
            database.setTransactionSuccessful();

        } finally {
            if (database != null) {
                database.endTransaction();
            }
        }


        return (int) returnedId;

    }

    public int adicionarCurso(Curso curso) {
        ContentValues values = new ContentValues();
        long returnedId;

        try {
            database.beginTransaction();

            values.put(DataContract.CursoEntry.COLUMN_NOME, curso.getNome());
            values.put(DataContract.CursoEntry.COLUMN_CARGA_HORARIA, curso.getCargaHoraria());
            values.put(DataContract.CursoEntry.COLUMN_CODIGO, curso.getCodigo());
            values.put(DataContract.CursoEntry.CURSO_PERIODO_FK, curso.getPeriodoCurso());
            values.put(DataContract.CursoEntry.TIPO_CURSO_FK, curso.getTipoCurso());
            returnedId = database.insert(DataContract.CursoEntry.TABLE_NAME_CURSO, null, values);
            database.setTransactionSuccessful();

        } finally {
            if (database != null) {
                database.endTransaction();
            }
        }

        return (int) returnedId;
    }
}
