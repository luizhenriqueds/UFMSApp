
package ufms.br.com.ufmsapp.JSONParsers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.Keys;
import ufms.br.com.ufmsapp.pojo.AlunoXDisciplina;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListAlunoXDisciplinaParser {

    public static ArrayList<AlunoXDisciplina> parseAlunoXDisciplinaJSON(JSONObject response) {
        ArrayList<AlunoXDisciplina> listMatriculas = new ArrayList<>();

        if (response != null && response.length() > 0) {

            int alunoKey = -1;
            int disciplinaKey = -1;
            int statusDisciplinaKey = -1;
            int turmaKey = -1;


            try {
                JSONArray jsonArray = response.getJSONArray(Keys.AlunoXDisciplinaEndpointColumns.KEY_MATRICULAS);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);


                    if (JSONUtils.contains(currentObject, Keys.AlunoXDisciplinaEndpointColumns.KEY_ALUNO_FK)) {
                        alunoKey = currentObject.getInt(Keys.AlunoXDisciplinaEndpointColumns.KEY_ALUNO_FK);
                    }

                    if (JSONUtils.contains(currentObject, Keys.AlunoXDisciplinaEndpointColumns.KEY_DISCIPLINA_FK)) {
                        disciplinaKey = currentObject.getInt(Keys.AlunoXDisciplinaEndpointColumns.KEY_DISCIPLINA_FK);
                    }

                    if (JSONUtils.contains(currentObject, Keys.AlunoXDisciplinaEndpointColumns.KEY_STATUS_DISCIPLINA_FK)) {
                        statusDisciplinaKey = currentObject.getInt(Keys.AlunoXDisciplinaEndpointColumns.KEY_STATUS_DISCIPLINA_FK);
                    }

                    if (JSONUtils.contains(currentObject, Keys.AlunoXDisciplinaEndpointColumns.KEY_TURMA_FK)) {
                        turmaKey = currentObject.getInt(Keys.AlunoXDisciplinaEndpointColumns.KEY_TURMA_FK);
                    }


                    AlunoXDisciplina matricula = new AlunoXDisciplina();

                    if (alunoKey != -1 && disciplinaKey != -1 && statusDisciplinaKey != -1 && turmaKey != -1) {
                        matricula.setAluno(alunoKey);
                        matricula.setDisciplina(disciplinaKey);
                        matricula.setStatusDisciplina(statusDisciplinaKey);
                        matricula.setTurma(turmaKey);
                    }

                    listMatriculas.add(matricula);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listMatriculas;

    }
}
