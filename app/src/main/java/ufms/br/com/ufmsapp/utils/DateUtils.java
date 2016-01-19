package ufms.br.com.ufmsapp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    /* Recebe objeto date, formata para o padrão 'dd-mm-yyyy' e retorna String para ser inserido no banco de dados. */
    public static String getFormattedDate(Date date) {

        DateFormat df = new SimpleDateFormat("dd-mm-yyyy", new Locale("en", "us"));

        return df.format(date);
    }

}
