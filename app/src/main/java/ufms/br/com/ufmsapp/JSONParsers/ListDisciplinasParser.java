
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

package ufms.br.com.ufmsapp.JSONParsers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.Keys;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.utils.Constants;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListDisciplinasParser {

    public static ArrayList<Disciplina> parseDisciplinasJSON(JSONObject response) {
        ArrayList<Disciplina> listDisciplinas = new ArrayList<>();

        if (response != null && response.length() > 0) {

            int idDisciplina = -1;
            String codigoDisciplina = Constants.NA;
            String tituloDisciplina = Constants.NA;
            String descricaoDisciplina = Constants.NA;
            int cargaHorariaDisciplina = -1;
            int idDisciplinaServidor = -1;
            int tipoDisciplina = -1;
            int professor = -1;


            try {
                JSONArray jsonArray = response.getJSONArray(Keys.DisciplinasEndpointColumns.KEY_DISCIPLINA);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);

                    if (JSONUtils.contains(currentObject, Keys.DisciplinasEndpointColumns.KEY_ID_DISCIPLINA)) {
                        idDisciplina = currentObject.getInt(Keys.DisciplinasEndpointColumns.KEY_ID_DISCIPLINA);
                    }

                    if (JSONUtils.contains(currentObject, Keys.DisciplinasEndpointColumns.KEY_CODIGO_DISCIPLINA)) {
                        codigoDisciplina = currentObject.getString(Keys.DisciplinasEndpointColumns.KEY_CODIGO_DISCIPLINA);
                    }

                    if (JSONUtils.contains(currentObject, Keys.DisciplinasEndpointColumns.KEY_TITULO_DISCIPLINA)) {
                        tituloDisciplina = currentObject.getString(Keys.DisciplinasEndpointColumns.KEY_TITULO_DISCIPLINA);
                    }

                    if (JSONUtils.contains(currentObject, Keys.DisciplinasEndpointColumns.KEY_DESCRICAO_DISCIPLINA)) {
                        descricaoDisciplina = currentObject.getString(Keys.DisciplinasEndpointColumns.KEY_DESCRICAO_DISCIPLINA);
                    }

                    if (JSONUtils.contains(currentObject, Keys.DisciplinasEndpointColumns.KEY_CARGA_HORARIA)) {
                        cargaHorariaDisciplina = currentObject.getInt(Keys.DisciplinasEndpointColumns.KEY_CARGA_HORARIA);
                    }

                   /* if (JSONUtils.contains(currentObject, Keys.DisciplinasEndpointColumns.KEY_DISCIPLINA_SCORE)) {
                        disciplinaScore = BigDecimal.valueOf(currentObject.getDouble(Keys.DisciplinasEndpointColumns.KEY_DISCIPLINA_SCORE)).floatValue();
                    }*/

                    if (JSONUtils.contains(currentObject, Keys.DisciplinasEndpointColumns.KEY_DISCIPLINA_ID_SERVIDOR)) {
                        idDisciplinaServidor = currentObject.getInt(Keys.DisciplinasEndpointColumns.KEY_DISCIPLINA_ID_SERVIDOR);
                    }

                    if (JSONUtils.contains(currentObject, Keys.DisciplinasEndpointColumns.KEY_TIPO_DISCIPLINA)) {
                        tipoDisciplina = currentObject.getInt(Keys.DisciplinasEndpointColumns.KEY_TIPO_DISCIPLINA);
                    }

                    if (JSONUtils.contains(currentObject, Keys.DisciplinasEndpointColumns.KEY_PROFESSOR)) {
                        professor = currentObject.getInt(Keys.DisciplinasEndpointColumns.KEY_PROFESSOR);
                    }


                    Disciplina disciplina = new Disciplina();

                    disciplina.setId(idDisciplina);
                    disciplina.setCodigo(codigoDisciplina);
                    disciplina.setTitulo(tituloDisciplina);
                    disciplina.setDescricao(descricaoDisciplina);
                    disciplina.setCargaHoraria(cargaHorariaDisciplina);

                   /* if (disciplinaScore >= 0 && disciplinaScore <= 5) {
                        disciplina.setScore(disciplinaScore);
                    }*/

                    if (tipoDisciplina != -1 && professor != -1 && idDisciplinaServidor != -1) {
                        disciplina.setTipoDisciplina(tipoDisciplina);
                        disciplina.setProfessor(professor);
                        disciplina.setIdDisciplinaServidor(idDisciplinaServidor);
                    }

                    listDisciplinas.add(disciplina);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listDisciplinas;

    }
}
