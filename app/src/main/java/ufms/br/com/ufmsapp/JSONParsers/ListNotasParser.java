
package ufms.br.com.ufmsapp.JSONParsers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.Keys;
import ufms.br.com.ufmsapp.pojo.Aluno;
import ufms.br.com.ufmsapp.pojo.Nota;
import ufms.br.com.ufmsapp.utils.Constants;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListNotasParser {

    public static ArrayList<Nota> parseNotasJSON(JSONObject response) {
        ArrayList<Nota> notas = new ArrayList<>();

        if (response != null && response.length() > 0) {


            String descricaoNota = Constants.NA;
            float valorNota = -1;
            int matriculaKey = -1;

            try {
                JSONArray jsonArray = response.getJSONArray(Keys.NotasEndpointColumns.KEY_NOTAS);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);

                    if (JSONUtils.contains(currentObject, Keys.NotasEndpointColumns.KEY_DESCRICAO_NOTA)) {
                        descricaoNota = currentObject.getString(Keys.NotasEndpointColumns.KEY_DESCRICAO_NOTA);
                    }

                    if (JSONUtils.contains(currentObject, Keys.NotasEndpointColumns.KEY_NOTA)) {
                        valorNota = BigDecimal.valueOf(currentObject.getDouble(Keys.NotasEndpointColumns.KEY_NOTA)).floatValue();
                    }

                    if (JSONUtils.contains(currentObject, Keys.NotasEndpointColumns.KEY_MATRICULA_FK)) {
                        matriculaKey = currentObject.getInt(Keys.NotasEndpointColumns.KEY_MATRICULA_FK);
                    }


                    Nota nota = new Nota();


                    if (matriculaKey != -1) {
                        nota.setDescricaoNota(descricaoNota);
                        nota.setNota(valorNota);
                        nota.setAlunoXDisciplina(matriculaKey);
                    }

                    notas.add(nota);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return notas;

    }
}
