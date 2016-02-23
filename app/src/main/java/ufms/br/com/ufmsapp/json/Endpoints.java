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

package ufms.br.com.ufmsapp.json;


import ufms.br.com.ufmsapp.extras.UrlEndpoints;

public class Endpoints {

    public static String getRequestUrlEventos() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_EVENTOS;
    }

    public static String getRequestUrlDisciplinas() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_DISCIPLINAS;
    }

    public static String getRequestUrlTipoDisciplina() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_TIPO_DISCIPLINAS;
    }

    public static String getRequestUrlProfessores() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_PROFESSORES;
    }

    public static String getRequestUrlTipoEventos() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_TIPO_EVENTOS;
    }

    public static String getRequestUrlAlunos() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_ALUNOS;
    }

    public static String getRequestUrlInsertRating() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.INSERT_RATING_DISCIPLINA;
    }

    public static String getRequestUrlUpdateRating() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.UPDATE_RATING_DISCIPLINA;
    }

    public static String getRequestUrlStatusAlunos() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_STATUS_ALUNOS;
    }

    public static String getRequestUrlMateriais() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_MATERIAIS;
    }

    public static String getRequestUrlAlunoxDisciplina() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_ALUNOXDISCIPLINAS;
    }

    public static String getRequestUrlNotas() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_NOTAS;
    }

    public static String getRequestUrlStatusDisciplina() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_STATUS_DISCIPLINA;
    }

    public static String getRequestUrlTurmas() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_TURMAS;
    }

    public static String getRequestUrlTituloProfessor() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_TITULO_PROFESSOR;
    }

    public static String getRequestUrlRatingDisciplina() {

        return UrlEndpoints.URL_ENDPOINT + UrlEndpoints.LIST_RATING_DISCIPLINAS;
    }
}
