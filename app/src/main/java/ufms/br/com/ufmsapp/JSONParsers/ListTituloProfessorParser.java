
package ufms.br.com.ufmsapp.JSONParsers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.Keys;
import ufms.br.com.ufmsapp.pojo.TituloProfessor;
import ufms.br.com.ufmsapp.utils.Constants;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListTituloProfessorParser {

    public static ArrayList<TituloProfessor> parseTituloProfessorJSON(JSONObject response) {
        ArrayList<TituloProfessor> listTituloProfessores = new ArrayList<>();

        if (response != null && response.length() > 0) {

            String descricaoTituloProfessor = Constants.NA;
            int idServidorTituloProfessor = -1;


            try {
                JSONArray jsonArray = response.getJSONArray(Keys.TituloProfessorEndpointColumns.KEY_TITULO_PROFESSOR);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);

                    if (JSONUtils.contains(currentObject, Keys.TituloProfessorEndpointColumns.KEY_DESCRICAO_TITULO_PROFESSOR)) {
                        descricaoTituloProfessor = currentObject.getString(Keys.TituloProfessorEndpointColumns.KEY_DESCRICAO_TITULO_PROFESSOR);
                    }

                    if (JSONUtils.contains(currentObject, Keys.TituloProfessorEndpointColumns.KEY_ID_TITULO_PROFESSOR)) {
                        idServidorTituloProfessor = currentObject.getInt(Keys.TituloProfessorEndpointColumns.KEY_ID_TITULO_PROFESSOR);
                    }


                    TituloProfessor tituloProfessor = new TituloProfessor();

                    tituloProfessor.setTituloProfessor(descricaoTituloProfessor);

                    if (idServidorTituloProfessor != -1) {
                        tituloProfessor.setTituloProfessorIdServidor(idServidorTituloProfessor);
                    }

                    listTituloProfessores.add(tituloProfessor);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listTituloProfessores;

    }
}
