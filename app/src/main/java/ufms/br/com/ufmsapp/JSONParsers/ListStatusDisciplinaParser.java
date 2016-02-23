
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
import ufms.br.com.ufmsapp.pojo.StatusDisciplina;
import ufms.br.com.ufmsapp.utils.Constants;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListStatusDisciplinaParser {

    public static ArrayList<StatusDisciplina> parseStatusDisciplinaJSON(JSONObject response) {
        ArrayList<StatusDisciplina> listStatusDisciplinas = new ArrayList<>();

        if (response != null && response.length() > 0) {

            String descricaoStatusDisciplinas = Constants.NA;
            int idServidorStatusDisciplina = -1;


            try {
                JSONArray jsonArray = response.getJSONArray(Keys.StatusDisciplinaEndpointColumns.KEY_STATUS_DISCIPLINA);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);

                    if (JSONUtils.contains(currentObject, Keys.StatusDisciplinaEndpointColumns.KEY_DESCRICAO_STATUS_DISCIPLINA)) {
                        descricaoStatusDisciplinas = currentObject.getString(Keys.StatusDisciplinaEndpointColumns.KEY_DESCRICAO_STATUS_DISCIPLINA);
                    }

                    if (JSONUtils.contains(currentObject, Keys.StatusDisciplinaEndpointColumns.KEY_ID_STATUS_DISCIPLINA)) {
                        idServidorStatusDisciplina = currentObject.getInt(Keys.StatusDisciplinaEndpointColumns.KEY_ID_STATUS_DISCIPLINA);
                    }


                    StatusDisciplina statusDisciplina = new StatusDisciplina();

                    statusDisciplina.setStatusDisciplina(descricaoStatusDisciplinas);

                    if (idServidorStatusDisciplina != -1) {
                        statusDisciplina.setIdServidorDisciplina(idServidorStatusDisciplina);
                    }

                    listStatusDisciplinas.add(statusDisciplina);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listStatusDisciplinas;

    }
}
