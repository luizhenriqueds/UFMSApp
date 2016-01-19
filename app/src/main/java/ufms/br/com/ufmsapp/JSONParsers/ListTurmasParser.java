
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
