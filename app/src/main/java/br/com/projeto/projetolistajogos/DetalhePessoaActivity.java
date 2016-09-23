package br.com.projeto.projetolistajogos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import org.parceler.Parcels;

import br.com.projeto.projetolistajogos.Util.SimpleDialog;
import br.com.projeto.projetolistajogos.model.Pessoa;

public class DetalhePessoaActivity extends AppCompatActivity {

    public static final String EXTRA_PESSOA = "pessoa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_jogo);

        Pessoa pessoa = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_PESSOA));

        DetalhePessoaFragment dpf = DetalhePessoaFragment.newInstance(pessoa);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detalhe_jogo, dpf, "detalhe")
                        //.replace(R.id.list_jogos, djf, "detalhe")
                .commit();
    }

}
