
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
import ufms.br.com.ufmsapp.pojo.Professor;
import ufms.br.com.ufmsapp.utils.Constants;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListProfessoresParser {

    public static ArrayList<Professor> parseProfessoresJSON(JSONObject response) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", new Locale("en", "us"));
        ArrayList<Professor> listProfessores = new ArrayList<>();

        if (response != null && response.length() > 0) {

            //int idProfessor = -1;
            String nomeProfessor = Constants.NA;
            String emailProfessor = Constants.NA;
            String anoIngresso = Constants.NA;
            String formacaoProfessor = Constants.NA;
            int tituloProfessor = -1;
            int idServidorProfessor = -1;


            try {
                JSONArray jsonArray = response.getJSONArray(Keys.ProfessoresEndpointColumns.KEY_PROFESSOR);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);


                    if (JSONUtils.contains(currentObject, Keys.ProfessoresEndpointColumns.KEY_NOME_PROFESSOR)) {
                        nomeProfessor = currentObject.getString(Keys.ProfessoresEndpointColumns.KEY_NOME_PROFESSOR);
                    }

                    if (JSONUtils.contains(currentObject, Keys.ProfessoresEndpointColumns.KEY_EMAIL_PROFESSOR)) {
                        emailProfessor = currentObject.getString(Keys.ProfessoresEndpointColumns.KEY_EMAIL_PROFESSOR);
                    }

                    if (JSONUtils.contains(currentObject, Keys.ProfessoresEndpointColumns.KEY_ANO_INGRESSO_PROFESSOR)) {
                        anoIngresso = currentObject.getString(Keys.ProfessoresEndpointColumns.KEY_ANO_INGRESSO_PROFESSOR);
                    }

                    if (JSONUtils.contains(currentObject, Keys.ProfessoresEndpointColumns.KEY_FORMACAO)) {
                        formacaoProfessor = currentObject.getString(Keys.ProfessoresEndpointColumns.KEY_FORMACAO);
                    }

                    if (JSONUtils.contains(currentObject, Keys.ProfessoresEndpointColumns.KEY_TITULO_PROFESSOR)) {
                        tituloProfessor = currentObject.getInt(Keys.ProfessoresEndpointColumns.KEY_TITULO_PROFESSOR);
                    }

                    if (JSONUtils.contains(currentObject, Keys.ProfessoresEndpointColumns.KEY_ID_PROFESSOR)) {
                        idServidorProfessor = currentObject.getInt(Keys.ProfessoresEndpointColumns.KEY_ID_PROFESSOR);
                    }


                    Professor professor = new Professor();

                    //professor.setId(idProfessor);
                    professor.setNome(nomeProfessor);
                    professor.setEmail(emailProfessor);
                    professor.setFormacao(formacaoProfessor);


                    Date dateAnoIngresso = null;
                    try {
                        dateAnoIngresso = dateFormat.parse(anoIngresso);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    professor.setAnoIngresso(dateAnoIngresso);

                    professor.setTituloProfessor(tituloProfessor);
                    professor.setIdProfessorServidor(idServidorProfessor);


                    listProfessores.add(professor);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listProfessores;

    }
}
