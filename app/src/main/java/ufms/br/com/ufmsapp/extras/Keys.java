package ufms.br.com.ufmsapp.extras;


import android.provider.BaseColumns;

public class Keys {

    public static class EventosEndpointColumns implements BaseColumns {

        public static final String KEY_EVENTO = "eventos";
        public static final String KEY_ID_EVENTO = "app_id_evento";
        public static final String KEY_NOME_EVENTO = "app_nome_evento";
        public static final String KEY_DESCRICAO_EVENTO = "app_descricao_evento";
        public static final String KEY_ANEXO_CAMINHO = "app_anexo_caminho_evento";
        public static final String KEY_DATA_EVENTO_CRIADO = "app_evento_timestamp";
        public static final String KEY_DATA_LIMITE_EVENTO = "app_evento_data_limite";
        public static final String KEY_SMALL_ICON = "app_evento_small_icon";
        public static final String KEY_BIG_ICON = "app_evento_big_icon";
        public static final String KEY_EVENTO_ID_SERVIDOR = "app_id_evento";
        public static final String KEY_EVENTO_TIPO_EVENTO = "app_tipo_evento_fk";
        public static final String KEY_EVENTO_DISCIPLINA = "app_disciplina_fk";

    }

    public static class DisciplinasEndpointColumns implements BaseColumns {

        public static final String KEY_DISCIPLINA = "disciplinas";
        public static final String KEY_ID_DISCIPLINA = "app_id_disciplina";
        public static final String KEY_CODIGO_DISCIPLINA = "app_codigo_disciplina";
        public static final String KEY_TITULO_DISCIPLINA = "app_titulo_disciplina";
        public static final String KEY_DESCRICAO_DISCIPLINA = "app_descricao_disciplina";
        public static final String KEY_CARGA_HORARIA = "app_disciplina_carga_horaria";
        public static final String KEY_DISCIPLINA_SCORE = "app_disciplina_score";
        public static final String KEY_DISCIPLINA_ID_SERVIDOR = "app_id_disciplina";
        public static final String KEY_TIPO_DISCIPLINA = "app_tipo_disciplina_fk";
        public static final String KEY_PROFESSOR = "app_professor_fk";

    }

    public static class ProfessoresEndpointColumns implements BaseColumns {

        public static final String KEY_PROFESSOR = "professores";
        public static final String KEY_ID_PROFESSOR = "app_id_professor";
        public static final String KEY_NOME_PROFESSOR = "app_nome_professor";
        public static final String KEY_EMAIL_PROFESSOR = "app_email_professor";
        public static final String KEY_ANO_INGRESSO_PROFESSOR = "app_ano_ingresso_professor";
        public static final String KEY_FORMACAO = "app_formacao_professor";
        public static final String KEY_TITULO_PROFESSOR = "app_titulo_professor_fk";
        //public static final String KEY_ID_SERVIDOR = "app_titulo_professor_fk";

    }

    public static class TipoEventoEndpointColumns implements BaseColumns {

        public static final String KEY_TIPO_EVENTO = "tipoEventos";
        public static final String KEY_ID_TIPO_EVENTO = "app_id_tipo_evento";
        public static final String KEY_DESCRICAO_TIPO_EVENTO = "app_descricao_tipo_evento";

    }

    public static class StatusDisciplinaEndpointColumns implements BaseColumns {

        public static final String KEY_STATUS_DISCIPLINA = "statusDisciplinas";
        public static final String KEY_ID_STATUS_DISCIPLINA = "app_id_status_disciplina";
        public static final String KEY_DESCRICAO_STATUS_DISCIPLINA = "app_descricao_status_disciplina";

    }

    public static class StatusAlunoEndpointColumns implements BaseColumns {

        public static final String KEY_STATUS_ALUNO = "statusAlunos";
        public static final String KEY_ID_STATUS_ALUNO = "app_id_status_aluno";
        public static final String KEY_DESCRICAO_STATUS_ALUNO = "app_descricao_status_aluno";

    }

    public static class TipoDisciplinaEndpointColumns implements BaseColumns {

        public static final String KEY_TIPO_DISCIPLINA = "tipoDisciplinas";
        public static final String KEY_ID_TIPO_DISCIPLINA = "app_id_disciplina_tipo";
        public static final String KEY_DESCRICAO_TIPO_DISCIPLINA = "app_descricao_disciplina_tipo";

    }

    public static class AlunosEndpointColumns implements BaseColumns {

        public static final String KEY_ALUNOS = "alunos";
        public static final String KEY_ID_ALUNOS = "app_id_aluno";
        public static final String KEY_NOME_ALUNO = "app_nome_aluno";
        public static final String KEY_EMAIL_ALUNO = "app_email_aluno";
        public static final String KEY_RGA_ALUNO = "app_rga_aluno";
        public static final String KEY_STATUS_ALUNO_FK = "app_status_aluno_fk";

    }

    public static class MateriaisEndpointColumns implements BaseColumns {

        public static final String KEY_MATERIAIS = "uploads";
        //public static final String KEY_ID_MATERIAIS = "app_evento_upload_id";
        public static final String KEY_UPLOAD_CAMINHO_MATERIAL = "app_upload_path";
        public static final String KEY_EVENTO_FK = "app_evento_key";

    }

    public static class TurmasEndpointColumns implements BaseColumns {

        public static final String KEY_TURMAS = "turmas";
        public static final String KEY_ID_TURMA = "app_turma_id";
        public static final String KEY_NOME_TURMA = "app_turma_nome";

    }

    public static class TituloProfessorEndpointColumns implements BaseColumns {

        public static final String KEY_TITULO_PROFESSOR = "tituloProfessor";
        public static final String KEY_ID_TITULO_PROFESSOR = "app_id_titulo_professor";
        public static final String KEY_DESCRICAO_TITULO_PROFESSOR = "app_descricao_titulo_professor";

    }

    public static class AlunoXDisciplinaEndpointColumns implements BaseColumns {

        public static final String KEY_MATRICULAS = "matriculas";
        //public static final String KEY_ID_MATRICULA = "app_id_aluno_x_disciplina";
        public static final String KEY_ALUNO_FK = "app_aluno_fk";
        public static final String KEY_DISCIPLINA_FK = "app_disciplina_fk";
        public static final String KEY_STATUS_DISCIPLINA_FK = "app_status_disciplina_fk";
        public static final String KEY_TURMA_FK = "app_turma_key";

    }

}
