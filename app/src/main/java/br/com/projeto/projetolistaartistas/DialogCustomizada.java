package br.com.projeto.projetolistaartistas;

import android.support.v4.app.*;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by f.soares.de.farias on 11/18/2016.
 */


public class DialogCustomizada extends DialogFragment {
    static DialogCustomizada newInstance() {
        return new DialogCustomizada();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_dialog_customizada, container, false);
        View tv = v.findViewById(R.id.text_avaliacao);
        //((TextView)tv).setText("This is an instance of MyDialogFragment");
        ((TextView)tv).setHint("Preencha um comentário");
        return v;
    }



}