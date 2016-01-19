package ufms.br.com.ufmsapp.utils;


import android.webkit.MimeTypeMap;

public class GetFileMimeType {

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }
}
