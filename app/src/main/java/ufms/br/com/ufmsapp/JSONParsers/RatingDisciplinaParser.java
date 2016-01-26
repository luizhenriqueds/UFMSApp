package ufms.br.com.ufmsapp.JSONParsers;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ufms.br.com.ufmsapp.json.Endpoints;
import ufms.br.com.ufmsapp.network.VolleySingleton;
import ufms.br.com.ufmsapp.pojo.RatingDisciplina;

public class RatingDisciplinaParser {

    private static final String ALUNO_KEY = "alunoKey";
    private static final String DISCIPLINA_KEY = "disciplinaKey";
    private static final String RATING = "disciplinaRating";
    static int idInserted;

    public static int newRatingDisciplina(final RatingDisciplina ratingDisciplina) {
        final StringRequest postRequest = new StringRequest(Request.Method.POST, Endpoints.getRequestUrlInsertRating(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonResponse;

                        try {
                            jsonResponse = new JSONObject(response);
                            idInserted = jsonResponse.getInt("id");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.getMessage());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(ALUNO_KEY, String.valueOf(ratingDisciplina.getAlunoKey()));
                params.put(DISCIPLINA_KEY, String.valueOf(ratingDisciplina.getDisciplinaKey()));
                params.put(RATING, String.valueOf(ratingDisciplina.getRating()));

                return params;
            }
        };

        VolleySingleton.getInstance().getRequestQueue().add(postRequest);

        return idInserted;

    }


}