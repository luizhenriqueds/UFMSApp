/*
 *
 *  * Copyright (C) 2014 The Android Open Source Project
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package ufms.br.com.ufmsapp.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.pojo.Disciplina;

public class AvaliarDisciplinaDialog extends DialogFragment implements OnClickListener {

    private OnRatingDisciplinaListener listener;
    int idAluno;
    int idDisciplina;
    float rating;
    protected TextView nomeDisciplinaDialog;
    protected TextView messageDialog;
    protected TextView ratingDialog;
    protected LinearLayout rootView;
    protected RatingBar ratingBar;
    protected RatingBar ratingDetails;

    public static AvaliarDisciplinaDialog newInstance(int idAluno, int idDisciplina, float rating, OnRatingDisciplinaListener listener, RatingBar ratingDetails) {
        AvaliarDisciplinaDialog dialog = new AvaliarDisciplinaDialog();
        dialog.idAluno = idAluno;
        dialog.idDisciplina = idDisciplina;
        dialog.rating = rating;
        dialog.listener = listener;
        dialog.ratingDetails = ratingDetails;
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.custom_dialog_rating, rootView);

        rootView = (LinearLayout) view.findViewById(R.id.view_root);

        Disciplina disciplina = MyApplication.getWritableDatabase().disciplinaById(idDisciplina);

        nomeDisciplinaDialog = (TextView) view.findViewById(R.id.txt_dialog_disciplina_name);
        messageDialog = (TextView) view.findViewById(R.id.txt_dialog_msg);
        ratingDialog = (TextView) view.findViewById(R.id.txt_dialog_rating);
        ratingBar = (RatingBar) view.findViewById(R.id.dialog_rating_bar);


        ratingBar.setRating(rating);
        messageDialog.setText(R.string.txt_dialog_msg);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                ratingDialog.setText(String.valueOf(rating));

            }
        });


        nomeDisciplinaDialog.setText(disciplina.getTitulo());

        builder.setView(view);


        builder.setNegativeButton("Cancelar", this);
        builder.setPositiveButton("Avaliar", this);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE && listener != null) {
            listener.onRatingDisciplina(idAluno, idDisciplina, rating);
            Toast.makeText(getActivity(), "OK!!!", Toast.LENGTH_LONG).show();
        } else if (which == DialogInterface.BUTTON_NEGATIVE && listener != null) {
            dialog.dismiss();
            ratingDetails.setRating(0);
        }
    }

    public interface OnRatingDisciplinaListener {
        void onRatingDisciplina(int idAluno, int idDisciplina, float rating);
    }
}
