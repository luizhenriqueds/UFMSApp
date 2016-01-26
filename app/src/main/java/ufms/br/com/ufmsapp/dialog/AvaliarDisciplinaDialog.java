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
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import ufms.br.com.ufmsapp.MyApplication;
import ufms.br.com.ufmsapp.R;
import ufms.br.com.ufmsapp.fragment.DetalheDisciplinaFragment;
import ufms.br.com.ufmsapp.pojo.Disciplina;
import ufms.br.com.ufmsapp.pojo.RatingDisciplina;
import ufms.br.com.ufmsapp.task.TaskRateDisciplina;

public class AvaliarDisciplinaDialog extends DialogFragment implements OnClickListener {

    private OnRatingDisciplinaListener listener;
    int idAluno;
    int idDisciplina;
    float rating;
    protected TextView nomeDisciplinaDialog;
    protected TextView messageDialog;
    protected TextView ratingDialog;
    protected TextView ratingDialogDesc;
    protected LinearLayout rootView;
    protected RatingBar ratingBar;
    protected RatingBar ratingDetails;
    private int myValue;

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

        Bundle mArgs = getArguments();
        myValue = mArgs.getInt(DetalheDisciplinaFragment.DIALOG_MODE);

        rootView = (LinearLayout) view.findViewById(R.id.view_root);

        Disciplina disciplina = MyApplication.getWritableDatabase().disciplinaById(idDisciplina);

        nomeDisciplinaDialog = (TextView) view.findViewById(R.id.txt_dialog_disciplina_name);
        messageDialog = (TextView) view.findViewById(R.id.txt_dialog_msg);
        ratingDialog = (TextView) view.findViewById(R.id.txt_dialog_rating);
        ratingDialogDesc = (TextView) view.findViewById(R.id.txt_dialog_rating_desc);
        ratingBar = (RatingBar) view.findViewById(R.id.dialog_rating_bar);

        ratingBar.setRating(rating);


        ratingDialog.setText(String.valueOf(rating));
        setRatingDescription(rating);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float newRating,
                                        boolean fromUser) {
                ratingDialog.setText(String.valueOf(newRating));
                setRatingDescription(newRating);
            }
        });


        nomeDisciplinaDialog.setText(disciplina.getTitulo());

        builder.setView(view);

        if (myValue == 0) {
            messageDialog.setText(R.string.txt_dialog_msg);
            builder.setNegativeButton(R.string.txt_cancel, this);
            builder.setPositiveButton(R.string.txt_avaliar, this);

        } else if (myValue == 1) {
            messageDialog.setText(R.string.txt_dialog_msg_edit);
            builder.setNegativeButton(R.string.txt_cancel, this);
            builder.setPositiveButton(R.string.txt_editar, this);
        }


        return builder.create();
    }

    private void setRatingDescription(float rating) {

        String[] options = getActivity().getResources().getStringArray(R.array.rating_desc_items);

        if (rating > 0 && rating <= 1) {
            ratingDialogDesc.setText(options[0]);
        } else if (rating > 1 && rating <= 2) {
            ratingDialogDesc.setText(options[1]);
        } else if (rating > 2 && rating <= 3) {
            ratingDialogDesc.setText(options[2]);
        } else if (rating > 3 && rating <= 4) {
            ratingDialogDesc.setText(options[3]);
        } else if (rating > 4 && rating <= 5) {
            ratingDialogDesc.setText(options[4]);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE && listener != null) {

            if (myValue == 0) {
                RatingDisciplina ratingDisciplina = new RatingDisciplina(idAluno, idDisciplina, ratingBar.getRating());

                TaskRateDisciplina task = new TaskRateDisciplina();
                task.execute(ratingDisciplina);

                int idReturned = MyApplication.getWritableDatabase().ratingDisciplina(ratingDisciplina);

                if (idReturned > 0) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), R.string.txt_rated_success, Snackbar.LENGTH_LONG).show();
                }

                listener.onRatingDisciplina(idAluno, idDisciplina, ratingBar.getRating());
            } else {
                MyApplication.getWritableDatabase().updateRatingDisciplina(idAluno, idDisciplina, ratingBar.getRating());

                Toast.makeText(getActivity(), "UPDATED", Toast.LENGTH_LONG).show();
                //ratingDetails.setRating();
            }


        } else if (which == DialogInterface.BUTTON_NEGATIVE && listener != null) {
            if (myValue == 0) {
                dialog.dismiss();
                ratingDetails.setRating(0);
            }

        }
    }


    public interface OnRatingDisciplinaListener {
        void onRatingDisciplina(int idAluno, int idDisciplina, float rating);
    }
}
