
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
import ufms.br.com.ufmsapp.pojo.Turma;
import ufms.br.com.ufmsapp.utils.Constants;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListTurmasParser {

    public static ArrayList<Turma> parseTurmaJSON(JSONObject response) {
        ArrayList<Turma> listTurmas = new ArrayList<>();

        if (response != null && response.length() > 0) {

            String nomeTurma = Constants.NA;
            int idServidorTurma = -1;


            try {
                JSONArray jsonArray = response.getJSONArray(Keys.TurmasEndpointColumns.KEY_TURMAS);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);

                    if (JSONUtils.contains(currentObject, Keys.TurmasEndpointColumns.KEY_NOME_TURMA)) {
                        nomeTurma = currentObject.getString(Keys.TurmasEndpointColumns.KEY_NOME_TURMA);
                    }

                    if (JSONUtils.contains(currentObject, Keys.TurmasEndpointColumns.KEY_ID_TURMA)) {
                        idServidorTurma = currentObject.getInt(Keys.TurmasEndpointColumns.KEY_ID_TURMA);
                    }


                    Turma turma = new Turma();

                    turma.setNomeTurma(nomeTurma);

                    if (idServidorTurma != -1) {
                        turma.setIdServidorTurma(idServidorTurma);
                    }

                    listTurmas.add(turma);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listTurmas;

    }
}
