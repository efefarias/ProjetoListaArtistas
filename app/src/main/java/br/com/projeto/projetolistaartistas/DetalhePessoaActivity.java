package br.com.projeto.projetolistaartistas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.parceler.Parcels;

import br.com.projeto.projetolistaartistas.model.Pessoa;

public class DetalhePessoaActivity extends AppCompatActivity {

    public static final String EXTRA_PESSOA = "pessoa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_pessoa);

        Pessoa pessoa = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_PESSOA));

        DetalhePessoaFragment dpf = DetalhePessoaFragment.newInstance(pessoa);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detalhe_jogo, dpf, "detalhe")
                        //.replace(R.id.list_jogos, djf, "detalhe")
                .commit();
    }

}
