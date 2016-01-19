
package ufms.br.com.ufmsapp.JSONParsers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.Keys;
import ufms.br.com.ufmsapp.pojo.TipoDisciplina;
import ufms.br.com.ufmsapp.utils.Constants;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListTipoDisciplinaParser {

    public static ArrayList<TipoDisciplina> parseTipoDisciplinaJSON(JSONObject response) {
        ArrayList<TipoDisciplina> listTipoDisciplinas = new ArrayList<>();

        if (response != null && response.length() > 0) {

            String descricaoTipoDisciplina = Constants.NA;
            int idServidorTipoDisciplina = -1;


            try {
                JSONArray jsonArray = response.getJSONArray(Keys.TipoDisciplinaEndpointColumns.KEY_TIPO_DISCIPLINA);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);

                    if (JSONUtils.contains(currentObject, Keys.TipoDisciplinaEndpointColumns.KEY_DESCRICAO_TIPO_DISCIPLINA)) {
                        descricaoTipoDisciplina = currentObject.getString(Keys.TipoDisciplinaEndpointColumns.KEY_DESCRICAO_TIPO_DISCIPLINA);
                    }

                    if (JSONUtils.contains(currentObject, Keys.TipoDisciplinaEndpointColumns.KEY_ID_TIPO_DISCIPLINA)) {
                        idServidorTipoDisciplina = currentObject.getInt(Keys.TipoDisciplinaEndpointColumns.KEY_ID_TIPO_DISCIPLINA);
                    }


                    TipoDisciplina tipoDisciplina = new TipoDisciplina();

                    tipoDisciplina.setTipoDisciplinaDescricao(descricaoTipoDisciplina);

                    if (idServidorTipoDisciplina != -1) {
                        tipoDisciplina.setTipoDisciplinaIdServidor(idServidorTipoDisciplina);
                    }

                    listTipoDisciplinas.add(tipoDisciplina);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listTipoDisciplinas;

    }
}
