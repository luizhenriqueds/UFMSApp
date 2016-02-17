package ufms.br.com.ufmsapp.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ufms.br.com.ufmsapp.task.TaskLoadAlunos;
import ufms.br.com.ufmsapp.task.TaskLoadDisciplinasOnStart;
import ufms.br.com.ufmsapp.task.TaskLoadEventosOnStart;
import ufms.br.com.ufmsapp.task.TaskLoadMateriais;
import ufms.br.com.ufmsapp.task.TaskLoadMatriculas;
import ufms.br.com.ufmsapp.task.TaskLoadNotas;
import ufms.br.com.ufmsapp.task.TaskLoadProfessores;
import ufms.br.com.ufmsapp.task.TaskLoadRatingDisciplinas;
import ufms.br.com.ufmsapp.task.TaskLoadStatusAlunos;
import ufms.br.com.ufmsapp.task.TaskLoadStatusDisciplina;
import ufms.br.com.ufmsapp.task.TaskLoadTipoDisciplina;
import ufms.br.com.ufmsapp.task.TaskLoadTipoEventos;
import ufms.br.com.ufmsapp.task.TaskLoadTituloProfessores;
import ufms.br.com.ufmsapp.task.TaskLoadTurmas;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ufmsapp.db";
    private static final int DB_VERSION = 63;
    private static final String COMMA_SEPARATOR = ", ";
    private static final String APP_TAG = "log_db";


    private static final String SQL_DROP_TABLE_TIPO_CURSO = "DROP TABLE IF EXISTS " + DataContract.TipoCursoEntry.TABLE_NAME_TIPO_CURSO;

    private static final String SQL_DROP_TABLE_NOTIFY_EVENTO = "DROP TABLE IF EXISTS " + DataContract.NotifyStatusEvento.TABLE_NAME_NOTIFY_STATUS;

    private static final String SQL_DROP_TABLE_EVENTO_READ = "DROP TABLE IF EXISTS " + DataContract.EventoReadEntry.TABLE_NAME_EVENTO_READ;

    private static final String SQL_DROP_TABLE_EVENTO_FAVORITE = "DROP TABLE IF EXISTS " + DataContract.EventoFavoriteEntry.TABLE_NAME_EVENTO_FAVORITE;

    private static final String SQL_DROP_TABLE_RATING_DISCIPLINA = "DROP TABLE IF EXISTS " + DataContract.RatingDisciplinaEntry.TABLE_NAME_RATING_DISCIPLINA;

    private static final String SQL_DROP_TABLE_MATERIAIS = "DROP TABLE IF EXISTS " + DataContract.EventoUploadsEntry.TABLE_NAME_EVENTO_UPLOADS;

    private static final String SQL_DROP_TABLE_TURMA = "DROP TABLE IF EXISTS " + DataContract.TurmaEntry.TABLE_NAME_TURMA;

    private static final String SQL_DROP_TABLE_PERIODO_CURSO = "DROP TABLE IF EXISTS " + DataContract.PeriodoCursoEntry.TABLE_NAME_PERIODO_CURSO;

    private static final String SQL_DROP_TABLE_CURSO = "DROP TABLE IF EXISTS " + DataContract.CursoEntry.TABLE_NAME_CURSO;

    private static final String SQL_DROP_TABLE_TITULO_PROFESSOR = "DROP TABLE IF EXISTS " + DataContract.TituloProfessorEntry.TABLE_NAME_TITULO_PROFESSOR;

    private static final String SQL_DROP_TABLE_PROFESSOR = "DROP TABLE IF EXISTS " + DataContract.ProfessorEntry.TABLE_NAME_PROFESSOR;

    private static final String SQL_DROP_TABLE_TIPO_DISCIPLINA = "DROP TABLE IF EXISTS " + DataContract.TipoDisciplinaEntry.TABLE_NAME_TIPO_DISCIPLINA;

    private static final String SQL_DROP_TABLE_DISCIPLINA = "DROP TABLE IF EXISTS " + DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA;

    private static final String SQL_DROP_TABLE_TIPO_EVENTO = "DROP TABLE IF EXISTS " + DataContract.TipoEventoEntry.TABLE_NAME_TIPO_EVENTO;

    private static final String SQL_DROP_TABLE_EVENTO = "DROP TABLE IF EXISTS " + DataContract.EventoEntry.TABLE_NAME_EVENTO;

    private static final String SQL_DROP_TABLE_STATUS_ALUNO = "DROP TABLE IF EXISTS " + DataContract.AlunoStatusEntry.TABLE_NAME_STATUS_ALUNO;

    private static final String SQL_DROP_TABLE_ALUNO = "DROP TABLE IF EXISTS " + DataContract.AlunoEntry.TABLE_NAME_ALUNO;

    private static final String SQL_DROP_TABLE_STATUS_DISCIPLINA = "DROP TABLE IF EXISTS " + DataContract.StatusDisciplinaEntry.TABLE_NAME_STATUS_DISCIPLINA;

    private static final String SQL_DROP_TABLE_ALUNO_X_DISCIPLINA = "DROP TABLE IF EXISTS " + DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA;

    private static final String SQL_DROP_TABLE_NOTA = "DROP TABLE IF EXISTS " + DataContract.NotaEntry.TABLE_NAME_NOTA;


    private static final String SQL_CREATE_TABLE_TIPO_CURSO = "CREATE TABLE " + DataContract.TipoCursoEntry.TABLE_NAME_TIPO_CURSO + "(" +
            DataContract.TipoCursoEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.TipoCursoEntry.COLUMN_NOME + " TEXT NOT NULL);";

    private static final String SQL_CREATE_TABLE_PERIODO_CURSO = "CREATE TABLE " + DataContract.PeriodoCursoEntry.TABLE_NAME_PERIODO_CURSO + "(" +
            DataContract.PeriodoCursoEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.PeriodoCursoEntry.COLUMN_NOME + " TEXT NOT NULL);";


    private static final String SQL_CREATE_TABLE_CURSO = "CREATE TABLE " + DataContract.CursoEntry.TABLE_NAME_CURSO + "(" +
            DataContract.CursoEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.CursoEntry.COLUMN_CODIGO + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.CursoEntry.COLUMN_NOME + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.CursoEntry.COLUMN_CARGA_HORARIA + " INTEGER NOT NULL" + COMMA_SEPARATOR +

            DataContract.CursoEntry.CURSO_PERIODO_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.CursoEntry.TIPO_CURSO_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +

            " FOREIGN KEY (" + DataContract.CursoEntry.CURSO_PERIODO_FK + ") REFERENCES " +
            DataContract.PeriodoCursoEntry.TABLE_NAME_PERIODO_CURSO + " (" + DataContract.PeriodoCursoEntry.COLUMN_ID +
            ")" + COMMA_SEPARATOR + " FOREIGN KEY (" + DataContract.CursoEntry.TIPO_CURSO_FK + ") REFERENCES " +
            DataContract.TipoCursoEntry.TABLE_NAME_TIPO_CURSO + " (" + DataContract.TipoCursoEntry.COLUMN_ID +
            "));";

    private static final String SQL_CREATE_TABLE_TITULO_PROFESSOR = "CREATE TABLE " + DataContract.TituloProfessorEntry.TABLE_NAME_TITULO_PROFESSOR + "(" +
            DataContract.TituloProfessorEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.TituloProfessorEntry.COLUMN_NOME + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.TituloProfessorEntry.COLUMN_ID_SERVIDOR_TITULO_PROFESSOR + " INTEGER UNIQUE);";

    private static final String SQL_CREATE_TABLE_EVENTO_NOTIFY_STATUS = "CREATE TABLE " + DataContract.NotifyStatusEvento.TABLE_NAME_NOTIFY_STATUS + "(" +
            DataContract.NotifyStatusEvento.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.NotifyStatusEvento.COLUMN_NOTIFY_STATUS + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.NotifyStatusEvento.EVENTO_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +

            " FOREIGN KEY (" + DataContract.NotifyStatusEvento.EVENTO_FK + ") REFERENCES " +
            DataContract.EventoEntry.TABLE_NAME_EVENTO + " (" + DataContract.EventoEntry.COLUMN_ID +
            "))";

    private static final String SQL_CREATE_TABLE_EVENTO_READ = "CREATE TABLE " + DataContract.EventoReadEntry.TABLE_NAME_EVENTO_READ + "(" +
            DataContract.EventoReadEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.EventoReadEntry.COLUMN_EVENTO_READ + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.EventoReadEntry.EVENTO_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +

            " FOREIGN KEY (" + DataContract.EventoReadEntry.EVENTO_FK + ") REFERENCES " +
            DataContract.EventoEntry.TABLE_NAME_EVENTO + " (" + DataContract.EventoEntry.COLUMN_ID +
            "))";

    private static final String SQL_CREATE_TABLE_EVENTO_FAVORITE = "CREATE TABLE " + DataContract.EventoFavoriteEntry.TABLE_NAME_EVENTO_FAVORITE + "(" +
            DataContract.EventoFavoriteEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.EventoFavoriteEntry.COLUMN_EVENTO_FAVORITE + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.EventoFavoriteEntry.EVENTO_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +

            " FOREIGN KEY (" + DataContract.EventoFavoriteEntry.EVENTO_FK + ") REFERENCES " +
            DataContract.EventoEntry.TABLE_NAME_EVENTO + " (" + DataContract.EventoEntry.COLUMN_ID +
            "))";


    private static final String SQL_CREATE_TABLE_PROFESSOR = "CREATE TABLE " + DataContract.ProfessorEntry.TABLE_NAME_PROFESSOR + "(" +
            DataContract.ProfessorEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.ProfessorEntry.COLUMN_NOME + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.ProfessorEntry.COLUMN_EMAIL + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.ProfessorEntry.COLUMN_ANO_INGRESSO + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.ProfessorEntry.COLUMN_FORMACAO + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.ProfessorEntry.COLUMN_PROFESSOR_ID_SERVIDOR + " INTEGER UNIQUE" + COMMA_SEPARATOR +
            DataContract.ProfessorEntry.TITULO_PROFESSOR_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +

            " FOREIGN KEY (" + DataContract.ProfessorEntry.TITULO_PROFESSOR_FK + ") REFERENCES " +
            DataContract.TituloProfessorEntry.TABLE_NAME_TITULO_PROFESSOR + " (" + DataContract.TituloProfessorEntry.COLUMN_ID +
            "))";


    private static final String SQL_CREATE_TABLE_TIPO_DISCIPLINA = "CREATE TABLE " + DataContract.TipoDisciplinaEntry.TABLE_NAME_TIPO_DISCIPLINA + "(" +
            DataContract.TipoDisciplinaEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.TipoDisciplinaEntry.COLUMN_NOME + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.TipoDisciplinaEntry.COLUMN_ID_SERVIDOR_TIPO_DISCIPLINA + " INTEGER UNIQUE);";

    private static final String SQL_CREATE_TABLE_DISCIPLINA = "CREATE TABLE " + DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA + "(" +
            DataContract.DisciplinaEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.DisciplinaEntry.COLUMN_CODIGO + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.DisciplinaEntry.COLUMN_NOME + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.DisciplinaEntry.COLUMN_DESCRICAO + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.DisciplinaEntry.COLUMN_CARGA_HORARIA + " INTEGER NOT NULL" + COMMA_SEPARATOR +

            DataContract.DisciplinaEntry.COLUMN_ID_SERVIDOR + " INTEGER UNIQUE" + COMMA_SEPARATOR +

            DataContract.DisciplinaEntry.TIPO_DISCIPLINA_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.DisciplinaEntry.PROFESSOR_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +

            " FOREIGN KEY (" + DataContract.DisciplinaEntry.TIPO_DISCIPLINA_FK + ") REFERENCES " +
            DataContract.TipoDisciplinaEntry.TABLE_NAME_TIPO_DISCIPLINA + " (" + DataContract.TipoDisciplinaEntry.COLUMN_ID +
            ")" + COMMA_SEPARATOR + "FOREIGN KEY (" + DataContract.DisciplinaEntry.PROFESSOR_FK + ") REFERENCES " +
            DataContract.ProfessorEntry.TABLE_NAME_PROFESSOR + " (" + DataContract.ProfessorEntry.COLUMN_ID +
            "));";


    private static final String SQL_CREATE_TABLE_TIPO_EVENTO = "CREATE TABLE " + DataContract.TipoEventoEntry.TABLE_NAME_TIPO_EVENTO + "(" +
            DataContract.TipoEventoEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.TipoEventoEntry.COLUMN_NOME + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.TipoEventoEntry.COLUMN_TIPO_EVENTO_ID_SERVIDOR + " INTEGER UNIQUE);";


    private static final String SQL_CREATE_TABLE_EVENTO = "CREATE TABLE " + DataContract.EventoEntry.TABLE_NAME_EVENTO + "(" +
            DataContract.EventoEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.EventoEntry.COLUMN_NOME + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.EventoEntry.COLUMN_DESCRICAO + " TEXT NOT NULL" + COMMA_SEPARATOR +

            DataContract.EventoEntry.COLUMN_TIMESTAMP + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.EventoEntry.COLUMN_DATA_LIMITE + " INTEGER NOT NULL" + COMMA_SEPARATOR +

            DataContract.EventoEntry.COLUMN_SMALL_ICON_PATH + " TEXT" + COMMA_SEPARATOR +
            DataContract.EventoEntry.COLUMN_BIG_ICON_PATH + " TEXT" + COMMA_SEPARATOR +

            DataContract.EventoEntry.COLUMN_ID_SERVIDOR_EVENTO + " INTEGER UNIQUE" + COMMA_SEPARATOR +

            DataContract.EventoEntry.DISCIPLINA_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.EventoEntry.TIPO_EVENTO_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +

            " FOREIGN KEY (" + DataContract.EventoEntry.DISCIPLINA_FK + ") REFERENCES " +
            DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA + " (" + DataContract.DisciplinaEntry.COLUMN_ID +
            ")" + COMMA_SEPARATOR + "FOREIGN KEY (" + DataContract.EventoEntry.TIPO_EVENTO_FK + ") REFERENCES " +
            DataContract.TipoEventoEntry.TABLE_NAME_TIPO_EVENTO + " (" + DataContract.TipoEventoEntry.COLUMN_ID +
            "));";


    private static final String SQL_CREATE_TABLE_STATUS_ALUNO = "CREATE TABLE " + DataContract.AlunoStatusEntry.TABLE_NAME_STATUS_ALUNO + "(" +
            DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_NOME + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_ID_SERVIDOR + " INTEGER UNIQUE);";

    private static final String SQL_CREATE_TABLE_ALUNO = "CREATE TABLE " + DataContract.AlunoEntry.TABLE_NAME_ALUNO + "(" +
            DataContract.AlunoEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.AlunoEntry.COLUMN_NOME + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.AlunoEntry.COLUMN_EMAIL + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.AlunoEntry.COLUMN_RGA + " TEXT NOT NULL" + COMMA_SEPARATOR +

            DataContract.AlunoEntry.STATUS_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.AlunoEntry.COLUMN_ALUNO_ID_SERVIDOR + " INTEGER UNIQUE" + COMMA_SEPARATOR +

            " FOREIGN KEY (" + DataContract.AlunoEntry.STATUS_FK + ") REFERENCES " +
            DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_ID + " (" + DataContract.AlunoStatusEntry.COLUMN_STATUS_ALUNO_ID +
            "))";

    private static final String SQL_CREATE_TABLE_STATUS_DISCIPLINA = "CREATE TABLE " + DataContract.StatusDisciplinaEntry.TABLE_NAME_STATUS_DISCIPLINA + "(" +
            DataContract.StatusDisciplinaEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.StatusDisciplinaEntry.COLUMN_NOME + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.StatusDisciplinaEntry.COLUMN_STATUS_DISCIPLINA_ID_SERVIDOR + " INTEGER UNIQUE);";

    private static final String SQL_CREATE_TABLE_ALUNO_X_DISCIPLINA = "CREATE TABLE " + DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA + "(" +
            DataContract.AlunoXDisciplinaEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.AlunoXDisciplinaEntry.ALUNO_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.AlunoXDisciplinaEntry.DISCIPLINA_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.AlunoXDisciplinaEntry.STATUS_DISCIPLINA_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.AlunoXDisciplinaEntry.TURMA_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.AlunoXDisciplinaEntry.COLUMN_ID_SERVIDOR + " INTEGER UNIQUE" + COMMA_SEPARATOR +

            " FOREIGN KEY (" + DataContract.AlunoXDisciplinaEntry.ALUNO_FK + ") REFERENCES " +
            DataContract.AlunoEntry.TABLE_NAME_ALUNO + " (" + DataContract.AlunoEntry.COLUMN_ID +
            ")" + COMMA_SEPARATOR +

            "FOREIGN KEY (" + DataContract.AlunoXDisciplinaEntry.DISCIPLINA_FK + ") REFERENCES " +
            DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA + " (" + DataContract.DisciplinaEntry.COLUMN_ID +
            ")" + COMMA_SEPARATOR +

            "FOREIGN KEY (" + DataContract.AlunoXDisciplinaEntry.STATUS_DISCIPLINA_FK + ") REFERENCES " +
            DataContract.StatusDisciplinaEntry.TABLE_NAME_STATUS_DISCIPLINA + " (" + DataContract.StatusDisciplinaEntry.COLUMN_ID +
            ")" + COMMA_SEPARATOR +

            "FOREIGN KEY (" + DataContract.AlunoXDisciplinaEntry.TURMA_FK + ") REFERENCES " +
            DataContract.TurmaEntry.TABLE_NAME_TURMA + " (" + DataContract.TurmaEntry.COLUMN_ID + "));";

    private static final String SQL_CREATE_TABLE_MATERIAIS = "CREATE TABLE " + DataContract.EventoUploadsEntry.TABLE_NAME_EVENTO_UPLOADS + "(" +
            DataContract.EventoUploadsEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.EventoUploadsEntry.COLUMN_UPLOAD_PATH + " TEXT NOT NULL" + COMMA_SEPARATOR +

            DataContract.EventoUploadsEntry.EVENTO_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +

            " FOREIGN KEY (" + DataContract.EventoUploadsEntry.EVENTO_FK + ") REFERENCES " +
            DataContract.EventoEntry.TABLE_NAME_EVENTO + " (" + DataContract.EventoEntry.COLUMN_ID +
            "))";

    private static final String SQL_CREATE_TABLE_RATING_DISCIPLINA = "CREATE TABLE " + DataContract.RatingDisciplinaEntry.TABLE_NAME_RATING_DISCIPLINA + "(" +
            DataContract.RatingDisciplinaEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +

            DataContract.RatingDisciplinaEntry.COLUMN_ALUNO_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +
            DataContract.RatingDisciplinaEntry.COLUMN_RATING + " REAL NOT NULL" + COMMA_SEPARATOR +

            " FOREIGN KEY (" + DataContract.RatingDisciplinaEntry.COLUMN_ALUNO_FK + ") REFERENCES " +
            DataContract.AlunoEntry.TABLE_NAME_ALUNO + " (" + DataContract.AlunoEntry.COLUMN_ID +
            ")" + COMMA_SEPARATOR +

            "FOREIGN KEY (" + DataContract.RatingDisciplinaEntry.COLUMN_DISCIPLINA_FK + ") REFERENCES " +
            DataContract.DisciplinaEntry.TABLE_NAME_DISCIPLINA + " (" + DataContract.DisciplinaEntry.COLUMN_ID + "));";


    private static final String SQL_CREATE_TABLE_TURMA = "CREATE TABLE " + DataContract.TurmaEntry.TABLE_NAME_TURMA + "(" +
            DataContract.TurmaEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.TurmaEntry.COLUMN_NOME + " TEXT NOT NULL" + COMMA_SEPARATOR +

            DataContract.TurmaEntry.COLUMN_ID_SERVIDOR + " INTEGER UNIQUE);";

    private static final String SQL_CREATE_TABLE_NOTA = "CREATE TABLE " + DataContract.NotaEntry.TABLE_NAME_NOTA + "(" +
            DataContract.NotaEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + COMMA_SEPARATOR +
            DataContract.NotaEntry.COLUMN_DESCRICAO_NOTA + " TEXT NOT NULL" + COMMA_SEPARATOR +
            DataContract.NotaEntry.COLUMN_NOTA + " REAL NOT NULL" + COMMA_SEPARATOR +

            DataContract.NotaEntry.ALUNO_X_DISCIPLINA_FK + " INTEGER NOT NULL" + COMMA_SEPARATOR +

            " FOREIGN KEY (" + DataContract.NotaEntry.ALUNO_X_DISCIPLINA_FK + ") REFERENCES " +
            DataContract.AlunoXDisciplinaEntry.TABLE_NAME_ALUNO_X_DISCIPLINA + " (" + DataContract.AlunoXDisciplinaEntry.COLUMN_ID +
            "))";

    protected static DataHelper instance;

    public static DataHelper newInstance(Context context) {

        if (instance == null) {
            instance = new DataHelper(context, DB_NAME, null, DB_VERSION);
        }

        return instance;
    }

    private DataHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

    }

    private void sincronizarDados() {
        new TaskLoadAlunos().execute();

        new TaskLoadTipoEventos().execute();

        new TaskLoadTipoDisciplina().execute();

        new TaskLoadTituloProfessores().execute();

        new TaskLoadStatusAlunos().execute();

        new TaskLoadStatusDisciplina().execute();

        new TaskLoadTurmas().execute();

        new TaskLoadMatriculas().execute();

        new TaskLoadMateriais().execute();

        new TaskLoadTituloProfessores().execute();

        new TaskLoadProfessores().execute();

        new TaskLoadRatingDisciplinas().execute();

        new TaskLoadNotas().execute();

        new TaskLoadDisciplinasOnStart().execute();

        new TaskLoadEventosOnStart().execute();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_TIPO_CURSO);
        db.execSQL(SQL_CREATE_TABLE_PERIODO_CURSO);
        db.execSQL(SQL_CREATE_TABLE_MATERIAIS);
        db.execSQL(SQL_CREATE_TABLE_TURMA);
        db.execSQL(SQL_CREATE_TABLE_CURSO);
        db.execSQL(SQL_CREATE_TABLE_TITULO_PROFESSOR);
        db.execSQL(SQL_CREATE_TABLE_PROFESSOR);
        db.execSQL(SQL_CREATE_TABLE_TIPO_DISCIPLINA);
        db.execSQL(SQL_CREATE_TABLE_DISCIPLINA);
        db.execSQL(SQL_CREATE_TABLE_TIPO_EVENTO);
        db.execSQL(SQL_CREATE_TABLE_EVENTO);
        db.execSQL(SQL_CREATE_TABLE_STATUS_ALUNO);
        db.execSQL(SQL_CREATE_TABLE_ALUNO);
        db.execSQL(SQL_CREATE_TABLE_STATUS_DISCIPLINA);
        db.execSQL(SQL_CREATE_TABLE_ALUNO_X_DISCIPLINA);
        db.execSQL(SQL_CREATE_TABLE_NOTA);
        db.execSQL(SQL_CREATE_TABLE_RATING_DISCIPLINA);
        db.execSQL(SQL_CREATE_TABLE_EVENTO_NOTIFY_STATUS);
        db.execSQL(SQL_CREATE_TABLE_EVENTO_READ);
        db.execSQL(SQL_CREATE_TABLE_EVENTO_FAVORITE);

        Log.i(APP_TAG, "DB Created!");

        sincronizarDados();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP_TABLE_TIPO_CURSO);
        db.execSQL(SQL_DROP_TABLE_PERIODO_CURSO);
        db.execSQL(SQL_DROP_TABLE_MATERIAIS);
        db.execSQL(SQL_DROP_TABLE_TURMA);
        db.execSQL(SQL_DROP_TABLE_CURSO);
        db.execSQL(SQL_DROP_TABLE_TITULO_PROFESSOR);
        db.execSQL(SQL_DROP_TABLE_PROFESSOR);
        db.execSQL(SQL_DROP_TABLE_TIPO_DISCIPLINA);
        db.execSQL(SQL_DROP_TABLE_DISCIPLINA);
        db.execSQL(SQL_DROP_TABLE_TIPO_EVENTO);
        db.execSQL(SQL_DROP_TABLE_EVENTO);
        db.execSQL(SQL_DROP_TABLE_STATUS_ALUNO);
        db.execSQL(SQL_DROP_TABLE_ALUNO);
        db.execSQL(SQL_DROP_TABLE_STATUS_DISCIPLINA);
        db.execSQL(SQL_DROP_TABLE_ALUNO_X_DISCIPLINA);
        db.execSQL(SQL_DROP_TABLE_NOTA);
        db.execSQL(SQL_DROP_TABLE_RATING_DISCIPLINA);
        db.execSQL(SQL_DROP_TABLE_NOTIFY_EVENTO);
        db.execSQL(SQL_DROP_TABLE_EVENTO_READ);
        db.execSQL(SQL_DROP_TABLE_EVENTO_FAVORITE);

        Log.i(APP_TAG, "DB Dropped!");

        onCreate(db);
    }
}
