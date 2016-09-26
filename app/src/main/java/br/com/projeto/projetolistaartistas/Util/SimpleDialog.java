package br.com.projeto.projetolistaartistas.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

public class SimpleDialog extends DialogFragment
        implements OnClickListener {

    private static final
    String EXTRA_ID      = "id";
    private static final
    String EXTRA_MESSAGE = "message";
    private static final
    String EXTRA_TITLE   = "title";
    private static final
    String EXTRA_BUTTONS = "buttons";
    private static final
    String DIALOG_TAG    = "br.com.projeto.projetolistajogos.Util.SimpleDialog";

    private int dialogId;

    public static SimpleDialog newDialog(int id,
                                         String title, String message, int[] buttonTexts){
        // Usando o Bundle para salvar o estado
        Bundle bundle  = new Bundle();
        bundle.putInt(EXTRA_ID, id);
        bundle.putString(EXTRA_TITLE, title);
        bundle.putString(EXTRA_MESSAGE, message);
        bundle.putIntArray(EXTRA_BUTTONS, buttonTexts);

        SimpleDialog dialog = new SimpleDialog();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(
            Bundle savedInstanceState) {

        String title = getArguments()
                .getString(EXTRA_TITLE);
        String message = getArguments()
                .getString(EXTRA_MESSAGE);
        int[] buttons = getArguments()
                .getIntArray(EXTRA_BUTTONS);

        AlertDialog.Builder alertDialogBuilder =
                new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage(message);

        switch (buttons.length) {
            case 3:
                alertDialogBuilder.setNeutralButton(
                        buttons[2], this);

            case 2:
                alertDialogBuilder.setNegativeButton(
                        buttons[1], this);

            case 1:
                alertDialogBuilder.setPositiveButton(
                        buttons[0], this);
        }
        return alertDialogBuilder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        Intent it = new Intent();
        it.putExtra("which", which);
        // Chamando o onActivityResult do targetFragment
        getTargetFragment().onActivityResult(
                getTargetRequestCode(), Activity.RESULT_OK, it);
    }

    public void openDialog(
            FragmentManager supportFragmentManager) {

        if (supportFragmentManager.findFragmentByTag(
                DIALOG_TAG) == null){

            show(supportFragmentManager, DIALOG_TAG);
        }
    }
    // Interface que erá chamada ao clicar no bot"ao
    public interface FragmentDialogInterface {
        void onClick(int id, int which);
    }
}