
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
import ufms.br.com.ufmsapp.pojo.TipoEvento;
import ufms.br.com.ufmsapp.utils.Constants;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListTipoEventoParser {

    public static ArrayList<TipoEvento> parseTipoEventoJSON(JSONObject response) {
        ArrayList<TipoEvento> listTipoEventos = new ArrayList<>();

        if (response != null && response.length() > 0) {

            //int idTipoEvento = -1;
            String descricaoTipoEvento = Constants.NA;
            int idServidorTipoEvento = -1;


            try {
                JSONArray jsonArray = response.getJSONArray(Keys.TipoEventoEndpointColumns.KEY_TIPO_EVENTO);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);

                    if (JSONUtils.contains(currentObject, Keys.TipoEventoEndpointColumns.KEY_DESCRICAO_TIPO_EVENTO)) {
                        descricaoTipoEvento = currentObject.getString(Keys.TipoEventoEndpointColumns.KEY_DESCRICAO_TIPO_EVENTO);
                    }

                    if (JSONUtils.contains(currentObject, Keys.TipoEventoEndpointColumns.KEY_ID_TIPO_EVENTO)) {
                        idServidorTipoEvento = currentObject.getInt(Keys.TipoEventoEndpointColumns.KEY_ID_TIPO_EVENTO);
                    }


                    TipoEvento tipoEvento = new TipoEvento();

                    tipoEvento.setNomeTipoEvento(descricaoTipoEvento);

                    if (idServidorTipoEvento != -1) {
                        tipoEvento.setTipoEventoIdServidor(idServidorTipoEvento);
                    }

                    listTipoEventos.add(tipoEvento);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listTipoEventos;

    }
}
