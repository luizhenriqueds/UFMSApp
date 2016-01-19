package ufms.br.com.ufmsapp.utils;


import org.json.JSONObject;

public class JSONUtils {

    public static boolean contains(JSONObject jsonObject, String key) {
        return jsonObject != null && jsonObject.has(key) && !jsonObject.isNull(key);
    }
}
