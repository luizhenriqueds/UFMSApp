
package ufms.br.com.ufmsapp.JSONParsers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import ufms.br.com.ufmsapp.extras.Keys;
import ufms.br.com.ufmsapp.pojo.RatingDisciplina;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListRatingDisciplinaParser {

    public static ArrayList<RatingDisciplina> listRatingDisciplinasParser(JSONObject response) {
        ArrayList<RatingDisciplina> listRating = new ArrayList<>();

        if (response != null && response.length() > 0) {

            int alunoKey = -1;
            int disciplinaKey = -1;
            float rating = -1;

            try {
                JSONArray jsonArray = response.getJSONArray(Keys.RatingDisciplinaEndpointColumns.KEY_RATING_DISCIPLINA);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);


                    if (JSONUtils.contains(currentObject, Keys.RatingDisciplinaEndpointColumns.KEY_ALUNO_KEY)) {
                        alunoKey = currentObject.getInt(Keys.RatingDisciplinaEndpointColumns.KEY_ALUNO_KEY);
                    }

                    if (JSONUtils.contains(currentObject, Keys.RatingDisciplinaEndpointColumns.KEY_DISCIPLINA_KEY)) {
                        disciplinaKey = currentObject.getInt(Keys.RatingDisciplinaEndpointColumns.KEY_DISCIPLINA_KEY);
                    }

                    if (JSONUtils.contains(currentObject, Keys.RatingDisciplinaEndpointColumns.KEY_RATING)) {
                        rating = BigDecimal.valueOf(currentObject.getDouble(Keys.RatingDisciplinaEndpointColumns.KEY_RATING)).floatValue();
                    }


                    RatingDisciplina ratingDisciplina = new RatingDisciplina();

                    if (alunoKey != -1 && disciplinaKey != -1 && rating != -1) {
                        ratingDisciplina.setAlunoKey(alunoKey);
                        ratingDisciplina.setDisciplinaKey(disciplinaKey);

                        if (rating >= 0 && rating <= 5) {
                            ratingDisciplina.setRating(rating);

                        }
                    }


                    listRating.add(ratingDisciplina);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listRating;

    }
}
