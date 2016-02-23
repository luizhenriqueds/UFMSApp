
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
import ufms.br.com.ufmsapp.pojo.AlunoXDisciplina;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListAlunoXDisciplinaParser {

    public static ArrayList<AlunoXDisciplina> parseAlunoXDisciplinaJSON(JSONObject response) {
        ArrayList<AlunoXDisciplina> listMatriculas = new ArrayList<>();

        if (response != null && response.length() > 0) {

            int alunoKey = -1;
            int disciplinaKey = -1;
            int statusDisciplinaKey = -1;
            int turmaKey = -1;
            int idServidor = -1;


            try {
                JSONArray jsonArray = response.getJSONArray(Keys.AlunoXDisciplinaEndpointColumns.KEY_MATRICULAS);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);


                    if (JSONUtils.contains(currentObject, Keys.AlunoXDisciplinaEndpointColumns.KEY_ALUNO_FK)) {
                        alunoKey = currentObject.getInt(Keys.AlunoXDisciplinaEndpointColumns.KEY_ALUNO_FK);
                    }

                    if (JSONUtils.contains(currentObject, Keys.AlunoXDisciplinaEndpointColumns.KEY_DISCIPLINA_FK)) {
                        disciplinaKey = currentObject.getInt(Keys.AlunoXDisciplinaEndpointColumns.KEY_DISCIPLINA_FK);
                    }

                    if (JSONUtils.contains(currentObject, Keys.AlunoXDisciplinaEndpointColumns.KEY_STATUS_DISCIPLINA_FK)) {
                        statusDisciplinaKey = currentObject.getInt(Keys.AlunoXDisciplinaEndpointColumns.KEY_STATUS_DISCIPLINA_FK);
                    }

                    if (JSONUtils.contains(currentObject, Keys.AlunoXDisciplinaEndpointColumns.KEY_TURMA_FK)) {
                        turmaKey = currentObject.getInt(Keys.AlunoXDisciplinaEndpointColumns.KEY_TURMA_FK);
                    }

                    if (JSONUtils.contains(currentObject, Keys.AlunoXDisciplinaEndpointColumns.KEY_ID_SERVIDOR)) {
                        idServidor = currentObject.getInt(Keys.AlunoXDisciplinaEndpointColumns.KEY_ID_SERVIDOR);
                    }


                    AlunoXDisciplina matricula = new AlunoXDisciplina();

                    if (alunoKey != -1 && disciplinaKey != -1 && statusDisciplinaKey != -1 && turmaKey != -1 && idServidor != -1) {
                        matricula.setAluno(alunoKey);
                        matricula.setDisciplina(disciplinaKey);
                        matricula.setStatusDisciplina(statusDisciplinaKey);
                        matricula.setTurma(turmaKey);
                        matricula.setAlunoXDisciplinaIdServidor(idServidor);
                    }

                    listMatriculas.add(matricula);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listMatriculas;

    }
}
