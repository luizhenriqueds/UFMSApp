package ufms.br.com.ufmsapp.data;

import android.provider.BaseColumns;

public class DataContract {

    public class CursoEntry implements BaseColumns {

        /* Constante que representa o nome da tabela curso que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_CURSO = "app_curso";

        /* Coluna id da tabela curso será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna nome do curso será armazenada como string */
        public static final String COLUMN_NOME = "app_curso_nome";

        /* Coluna codigo do curso será armazenada como string */
        public static final String COLUMN_CODIGO = "app_curso_codigo";

        /* Coluna carga horária será armazenada como inteiro */
        public static final String COLUMN_CARGA_HORARIA = "app_curso_carga_horaria";

        /* Coluna período do curso será armazenada como inteiro e representará ligação com chave estrageira com tabela app_periodo_curso */
        public static final String CURSO_PERIODO_FK = "app_curso_periodo_fk";

        /* Coluna tipo do curso será armazenada como inteiro e representará ligação com chave estrageira com tabela app_tipo_curso */
        public static final String TIPO_CURSO_FK = "app_curso_tipo_curso_fk";

    }

    public class TipoCursoEntry implements BaseColumns {

        /* Constante que representa o nome da tabela tipo de curso que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_TIPO_CURSO = "app_tipo_curso";

        /* Coluna id da tabela app_tipo_curso será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna descrição do tipo de curso será armazenada como string */
        public static final String COLUMN_NOME = "app_tipo_curso_nome";

    }

    public class RatingDisciplinaEntry implements BaseColumns {

        public static final String TABLE_NAME_RATING_DISCIPLINA = "app_rating_disciplina";

        public static final String COLUMN_ID = "_id";

        public static final String COLUMN_ALUNO_FK = "app_rating_aluno_key";

        public static final String COLUMN_DISCIPLINA_FK = "app_rating_disciplina_key";

        public static final String COLUMN_RATING = "app_rating_value";

    }

    public class PeriodoCursoEntry implements BaseColumns {

        /* Constante que representa o nome da tabela período do curso que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_PERIODO_CURSO = "app_periodo_curso";

        /* Coluna id da tabela app_periodo_curso será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna descrição do período do curso será armazenada como string */
        public static final String COLUMN_NOME = "app_periodo_curso_nome";

    }

    public class NotifyStatusEvento implements BaseColumns {

        /* Constante que representa o nome da tabela período do curso que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_NOTIFY_STATUS = "app_evento_notify_status";

        /* Coluna id da tabela app_evento_notify_status será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna status de notificação será armazenado como int - 0 ou 1 */
        public static final String COLUMN_NOTIFY_STATUS = "app_notify_status";

        /* Coluna chave estrangeira do evento*/
        public static final String EVENTO_FK = "app_evento_key";
    }


    public class ProfessorEntry implements BaseColumns {

        /* Constante que representa o nome da tabela professor que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_PROFESSOR = "app_professor";

        /* Coluna id da tabela app_professor será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna nome do professor será armazenada como string */
        public static final String COLUMN_NOME = "app_professor_nome";

        /* Coluna email do professor será armazenada como string */
        public static final String COLUMN_EMAIL = "app_professor_email";

        /* Coluna ano ingresso do professor será armazenada como string */
        public static final String COLUMN_ANO_INGRESSO = "app_professor_ano_ingresso";

        /* Coluna formação do professor será armazenada como string */
        public static final String COLUMN_FORMACAO = "app_professor_formacao";

        public static final String COLUMN_PROFESSOR_ID_SERVIDOR = "app_professor_id_servidor";

        /* Título do professor será armazenado com inteiro e representará ligação com a tabela título do professor */
        public static final String TITULO_PROFESSOR_FK = "app_titulo_professor_fk";

    }

    public class TituloProfessorEntry implements BaseColumns {

        /* Constante que representa o nome da tabela título do professor que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_TITULO_PROFESSOR = "app_titulo_professor";

        /* Coluna id da tabela app_titulo_professor será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna nome da formação será armazenada como string */
        public static final String COLUMN_NOME = "app_titulo_professor_nome";

        public static final String COLUMN_ID_SERVIDOR_TITULO_PROFESSOR = "app_titulo_professor_id_servidor";

    }

    public class DisciplinaEntry {

        /* Constante que representa o nome da tabela disciplina que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_DISCIPLINA = "app_disciplina";

        /* Coluna id da tabela app_disciplina será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna código da disciplina será armazenada como string */
        public static final String COLUMN_CODIGO = "app_disciplina_codigo_disciplina";

        /* Coluna nome da disciplina será armazenada como string */
        public static final String COLUMN_NOME = "app_disciplina_nome";

        /* Coluna descrição da disciplina será armazenada como string */
        public static final String COLUMN_DESCRICAO = "app_disciplina_descricao";

        public static final String COLUMN_CARGA_HORARIA = "app_disciplina_carga_horaria";

        /* Coluna score da disciplina será armazenada como string */
        public static final String COLUMN_SCORE = "app_disciplina_score";

        /* Coluna id do servidor será armazenada como inteiro */
        public static final String COLUMN_ID_SERVIDOR = "app_disciplina_id_servidor";

        /* Coluna tipo da disciplina será armazenada como inteiro e irá referenciar a tabela app_tipo_disciplina */
        public static final String TIPO_DISCIPLINA_FK = "app_tipo_disciplina_fk";

        /* Coluna professor será armazenada como inteiro e irá referenciar a tabela app_professor */
        public static final String PROFESSOR_FK = "app_professor_fk";

    }

    public class TipoDisciplinaEntry {

        /* Constante que representa o nome da tabela título do professor que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_TIPO_DISCIPLINA = "app_tipo_disciplina";

        /* Coluna id da tabela tipo_disciplina será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna tipo da disciplina será armazenada como string */
        public static final String COLUMN_NOME = "app_tipo_disciplina_nome";

        public static final String COLUMN_ID_SERVIDOR_TIPO_DISCIPLINA = "app_tipo_disciplina_id_servidor";
    }

    public class EventoEntry implements BaseColumns {

        /* Constante que representa o nome da tabela evento que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_EVENTO = "app_evento";

        /* Coluna id da tabela evento será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna nome do evento será armazenada como string */
        public static final String COLUMN_NOME = "app_evento_nome";

        /* Coluna descrição do evento será armazenada como string */
        public static final String COLUMN_TIMESTAMP = "app_evento_timestamp";

        public static final String COLUMN_DATA_LIMITE = "app_evento_data_limite";

        public static final String COLUMN_DESCRICAO = "app_evento_descricao";

        /* Coluna caminho do ícone pequeno será armazenada como string */
        public static final String COLUMN_SMALL_ICON_PATH = "app_evento_small_icon";

        /* Coluna caminho do ícone grande será armazenada como string */
        public static final String COLUMN_BIG_ICON_PATH = "app_evento_big_icon";

        /* Coluna notify status será armazenada como int */
        public static final String COLUMN_NOTIFY_STATUS = "app_evento_notify_status";

        public static final String COLUMN_ID_SERVIDOR_EVENTO = "app_evento_id_servidor";

        /* Coluna chave estrangeira tipo de evento será armazenada como inteiro */
        public static final String TIPO_EVENTO_FK = "app_tipo_evento_fk";

        /* Coluna chave estrangeira disciplina será armazenada como inteiro */
        public static final String DISCIPLINA_FK = "app_disciplina_fk";

    }

    public class EventoUploadsEntry implements BaseColumns {

        /* Constante que representa o nome da tabela aluno que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_EVENTO_UPLOADS = "app_evento_uploads";

        /* Coluna id da tabela Evento Upload será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna nome do anexo será armazenada como string */
        public static final String COLUMN_UPLOAD_PATH = "app_upload_path";

        /* Coluna chave estrangeira status do evento será armazenada como inteiro */
        public static final String EVENTO_FK = "app_evento_fk";

    }

    public class TurmaEntry implements BaseColumns {

        /* Constante que representa o nome da tabela turma que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_TURMA = "app_turma";

        /* Coluna id da tabela Turma será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna nome da turma será armazenada como string */
        public static final String COLUMN_NOME = "app_turma_nome";

        public static final String COLUMN_ID_SERVIDOR = "app_turma_id_servidor";

    }

    public class AlunoEntry implements BaseColumns {

        /* Constante que representa o nome da tabela aluno que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_ALUNO = "app_aluno";

        /* Coluna id da tabela aluno será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna nome do aluno será armazenada como string */
        public static final String COLUMN_NOME = "app_aluno_nome";

        /* Coluna email do aluno será armazenada como string */
        public static final String COLUMN_EMAIL = "app_aluno_email";

        /* Coluna RGA do aluno será armazenada como string */
        public static final String COLUMN_RGA = "app_aluno_rga";

        /* Coluna chave estrangeira status do aluno será armazenada como inteiro */
        public static final String STATUS_FK = "app_aluno_status_fk";

        public static final String COLUMN_ALUNO_ID_SERVIDOR = "app_aluno_id_servidor";

    }

    public class AlunoStatusEntry implements BaseColumns {

        /* Constante que representa o nome da tabela aluno_status que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_STATUS_ALUNO = "app_aluno_status";

        /* Coluna id da tabela aluno_status será armazenada com inteiro chave primária */
        public static final String COLUMN_STATUS_ALUNO_ID = "_id";

        /* Coluna status do aluno será armazenada como string */
        public static final String COLUMN_STATUS_ALUNO_NOME = "app_aluno_nome";

        public static final String COLUMN_STATUS_ALUNO_ID_SERVIDOR = "app_aluno_status_id_servidor";

    }

    public class TipoEventoEntry implements BaseColumns {

        /* Constante que representa o nome da tabela tipo_evento que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_TIPO_EVENTO = "app_tipo_evento";

        /* Coluna id da tabela tipo_evento será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna tipo do evento será armazenada como string */
        public static final String COLUMN_NOME = "app_tipo_evento_nome";

        public static final String COLUMN_TIPO_EVENTO_ID_SERVIDOR = "app_tipo_evento_id_servidor";

    }

    public class AlunoXDisciplinaEntry implements BaseColumns {

        /* Constante que representa o nome da tabela aluno_x_disciplina que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_ALUNO_X_DISCIPLINA = "app_aluno_x_disciplina";

        /* Coluna id da tabela aluno_x_disciplina será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna chave estrangeira da tabela aluno será armazenada como inteiro */
        public static final String ALUNO_FK = "app_aluno_fk";

        /* Coluna chave estrangeira da tabela disciplina será armazenada como inteiro */
        public static final String DISCIPLINA_FK = "app_disciplina_fk";

        /* Coluna chave estrangeira da tabela status da disciplina armazenada como inteiro */
        public static final String STATUS_DISCIPLINA_FK = "app_status_disciplina_fk";

        public static final String TURMA_FK = "app_turma_fk";

        public static final String COLUMN_ID_SERVIDOR = "aluno_x_disciplina_id_servidor";


    }

    public class NotaEntry implements BaseColumns {

        /* Constante que representa o nome da tabela nota que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_NOTA = "app_nota";

        /* Coluna id da tabela nota será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna nota será armazenada como real */
        public static final String COLUMN_NOTA = "app_nota_nota";

        /* Coluna descrição da nota será armazenada como string */
        public static final String COLUMN_DESCRICAO_NOTA = "app_descricao_nota";

        /* Coluna chave estrangeira da tabela aluno_x_disciplina será armazenada como inteiro */
        public static final String ALUNO_X_DISCIPLINA_FK = "app_aluno_x_disciplina_fk";

    }

    public class StatusDisciplinaEntry implements BaseColumns {

        /* Constante que representa o nome da tabela status_disciplina que será referenciada no código de criação do banco */
        public static final String TABLE_NAME_STATUS_DISCIPLINA = "app_status_disciplina";

        /* Coluna id da tabela status_disciplina será armazenada com inteiro chave primária */
        public static final String COLUMN_ID = "_id";

        /* Coluna status da disciplina será armazenada como string */
        public static final String COLUMN_NOME = "app_status_disciplina_nome";

        public static final String COLUMN_STATUS_DISCIPLINA_ID_SERVIDOR = "app_status_disciplina_id_servidor";

    }

}
