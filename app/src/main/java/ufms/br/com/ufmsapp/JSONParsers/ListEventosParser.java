
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import ufms.br.com.ufmsapp.extras.Keys;
import ufms.br.com.ufmsapp.pojo.Evento;
import ufms.br.com.ufmsapp.utils.Constants;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListEventosParser {

    public static ArrayList<Evento> parseEventosJSON(JSONObject response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "us"));
        ArrayList<Evento> listEventos = new ArrayList<>();

        if (response != null && response.length() > 0) {

            int idEvento = -1;
            int tipoEvento = -1;
            String descricaoEvento = Constants.NA;
            int disciplina = -1;
            String tituloEvento = Constants.NA;
            String smallIcon = Constants.NA;
            String bigIcon = Constants.NA;
            int idServidorEvento = -1;
            String dataEventoCriado = Constants.NA;
            String dataLimiteEvento = Constants.NA;


            try {
                JSONArray jsonArray = response.getJSONArray(Keys.EventosEndpointColumns.KEY_EVENTO);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);

                    if (JSONUtils.contains(currentObject, Keys.EventosEndpointColumns.KEY_ID_EVENTO)) {
                        idEvento = currentObject.getInt(Keys.EventosEndpointColumns.KEY_ID_EVENTO);
                    }

                    if (JSONUtils.contains(currentObject, Keys.EventosEndpointColumns.KEY_EVENTO_TIPO_EVENTO)) {
                        tipoEvento = currentObject.getInt(Keys.EventosEndpointColumns.KEY_EVENTO_TIPO_EVENTO);
                    }

                    if (JSONUtils.contains(currentObject, Keys.EventosEndpointColumns.KEY_DESCRICAO_EVENTO)) {
                        descricaoEvento = currentObject.getString(Keys.EventosEndpointColumns.KEY_DESCRICAO_EVENTO);
                    }

                    if (JSONUtils.contains(currentObject, Keys.EventosEndpointColumns.KEY_EVENTO_DISCIPLINA)) {
                        disciplina = currentObject.getInt(Keys.EventosEndpointColumns.KEY_EVENTO_DISCIPLINA);
                    }

                    if (JSONUtils.contains(currentObject, Keys.EventosEndpointColumns.KEY_NOME_EVENTO)) {
                        tituloEvento = currentObject.getString(Keys.EventosEndpointColumns.KEY_NOME_EVENTO);
                    }

                    if (JSONUtils.contains(currentObject, Keys.EventosEndpointColumns.KEY_DATA_EVENTO_CRIADO)) {
                        dataEventoCriado = currentObject.getString(Keys.EventosEndpointColumns.KEY_DATA_EVENTO_CRIADO);
                    }

                    if (JSONUtils.contains(currentObject, Keys.EventosEndpointColumns.KEY_SMALL_ICON)) {
                        smallIcon = currentObject.getString(Keys.EventosEndpointColumns.KEY_SMALL_ICON);
                    }

                    if (JSONUtils.contains(currentObject, Keys.EventosEndpointColumns.KEY_BIG_ICON)) {
                        bigIcon = currentObject.getString(Keys.EventosEndpointColumns.KEY_BIG_ICON);
                    }

                    if (JSONUtils.contains(currentObject, Keys.EventosEndpointColumns.KEY_EVENTO_ID_SERVIDOR)) {
                        idServidorEvento = currentObject.getInt(Keys.EventosEndpointColumns.KEY_EVENTO_ID_SERVIDOR);
                    }

                    if (JSONUtils.contains(currentObject, Keys.EventosEndpointColumns.KEY_DATA_LIMITE_EVENTO)) {
                        dataLimiteEvento = currentObject.getString(Keys.EventosEndpointColumns.KEY_DATA_LIMITE_EVENTO);
                    }


                    Date date = null;
                    Date dataLimite = null;
                    try {
                        date = dateFormat.parse(dataEventoCriado);
                        dataLimite = dateFormat.parse(dataLimiteEvento);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Evento evento = new Evento();

                    evento.setId(idEvento);
                    evento.setTitulo(tituloEvento);
                    evento.setDescricao(descricaoEvento);
                    evento.setDataEventoCriado(date);
                    evento.setDataLimiteEvento(dataLimite);
                    evento.setSmallIcon(smallIcon);
                    evento.setBigIcon(bigIcon);

                    if (tipoEvento != -1 && disciplina != -1 && idServidorEvento != -1) {
                        evento.setTipoEvento(tipoEvento);
                        evento.setDisciplina(disciplina);
                        evento.setIdEventoServidor(idServidorEvento);
                    }

                    listEventos.add(evento);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listEventos;

    }
}
