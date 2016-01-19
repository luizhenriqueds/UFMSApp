
package ufms.br.com.ufmsapp.JSONParsers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.Keys;
import ufms.br.com.ufmsapp.pojo.StatusAluno;
import ufms.br.com.ufmsapp.utils.Constants;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListStatusAlunosParser {

    public static ArrayList<StatusAluno> parseStatusAlunoJSON(JSONObject response) {
        ArrayList<StatusAluno> listStatusAluno = new ArrayList<>();

        if (response != null && response.length() > 0) {

            String descricaoStatusAluno = Constants.NA;
            int idServidorStatusAluno = -1;


            try {
                JSONArray jsonArray = response.getJSONArray(Keys.StatusAlunoEndpointColumns.KEY_STATUS_ALUNO);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);

                    if (JSONUtils.contains(currentObject, Keys.StatusAlunoEndpointColumns.KEY_DESCRICAO_STATUS_ALUNO)) {
                        descricaoStatusAluno = currentObject.getString(Keys.StatusAlunoEndpointColumns.KEY_DESCRICAO_STATUS_ALUNO);
                    }

                    if (JSONUtils.contains(currentObject, Keys.StatusAlunoEndpointColumns.KEY_ID_STATUS_ALUNO)) {
                        idServidorStatusAluno = currentObject.getInt(Keys.StatusAlunoEndpointColumns.KEY_ID_STATUS_ALUNO);
                    }


                    StatusAluno statusAluno = new StatusAluno();

                    statusAluno.setStatusAlunoDescricao(descricaoStatusAluno);

                    if (idServidorStatusAluno != -1) {
                        statusAluno.setStatusAlunoIdServidor(idServidorStatusAluno);
                    }

                    listStatusAluno.add(statusAluno);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listStatusAluno;

    }
}
