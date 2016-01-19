
package ufms.br.com.ufmsapp.JSONParsers;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ufms.br.com.ufmsapp.extras.Keys;
import ufms.br.com.ufmsapp.pojo.Material;
import ufms.br.com.ufmsapp.utils.Constants;
import ufms.br.com.ufmsapp.utils.JSONUtils;

public class ListMateriaisUploadParser {

    public static ArrayList<Material> parseMaterialJSON(JSONObject response) {
        ArrayList<Material> listMaterial = new ArrayList<>();

        if (response != null && response.length() > 0) {

            String uploadPath = Constants.NA;
            int eventoKey = -1;


            try {
                JSONArray jsonArray = response.getJSONArray(Keys.MateriaisEndpointColumns.KEY_MATERIAIS);

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject currentObject = jsonArray.getJSONObject(i);

                    if (JSONUtils.contains(currentObject, Keys.MateriaisEndpointColumns.KEY_UPLOAD_CAMINHO_MATERIAL)) {
                        uploadPath = currentObject.getString(Keys.MateriaisEndpointColumns.KEY_UPLOAD_CAMINHO_MATERIAL);
                    }

                    if (JSONUtils.contains(currentObject, Keys.MateriaisEndpointColumns.KEY_EVENTO_FK)) {
                        eventoKey = currentObject.getInt(Keys.MateriaisEndpointColumns.KEY_EVENTO_FK);
                    }


                    Material upload = new Material();

                    upload.setPathMaterial(uploadPath);

                    if (eventoKey != -1) {
                        upload.setEventoKey(eventoKey);
                    }

                    listMaterial.add(upload);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return listMaterial;

    }
}
