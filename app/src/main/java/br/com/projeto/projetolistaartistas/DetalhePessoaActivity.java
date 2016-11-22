package br.com.projeto.projetolistaartistas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.parceler.Parcels;

import br.com.projeto.projetolistaartistas.model.Pessoa;

public class DetalhePessoaActivity extends AppCompatActivity {

    public static final String EXTRA_PESSOA = "pessoa";
    public static final String EXTRA_PESSOA2 = "pessoa2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_pessoa);

        Pessoa pessoa = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_PESSOA));
        int usu_id = getIntent().getIntExtra(EXTRA_PESSOA2, 0);

        DetalhePessoaFragment dpf = DetalhePessoaFragment.newInstance(pessoa, usu_id);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.detalhe_jogo, dpf, "detalhe")
                        //.replace(R.id.list_jogos, djf, "detalhe")
                .commit();
    }

}
