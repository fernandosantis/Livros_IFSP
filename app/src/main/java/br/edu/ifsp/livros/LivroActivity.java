package br.edu.ifsp.livros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.edu.ifsp.livros.databinding.ActivityLivroBinding;
import br.edu.ifsp.livros.model.Livro;

public class LivroActivity extends AppCompatActivity {
    private ActivityLivroBinding activityLivroBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLivroBinding = ActivityLivroBinding.inflate(getLayoutInflater());

        setContentView(activityLivroBinding.getRoot());

        activityLivroBinding.btSalvar.setOnClickListener((View view) -> {
            Livro livro = new Livro(
                    activityLivroBinding.etTitulo.getText().toString(),
                    activityLivroBinding.etISBN.getText().toString(),
                    activityLivroBinding.etPrimeiroAutor.getText().toString(),
                    activityLivroBinding.etEditora.getText().toString(),
                    Integer.parseInt(activityLivroBinding.etEdicao.getText().toString()),
                    Integer.parseInt(activityLivroBinding.etPaginas.getText().toString())
            );

            Intent resultadoIntent = new Intent();
            resultadoIntent.putExtra(MainActivity.EXTRA_LIVRO, livro);
            setResult(RESULT_OK, resultadoIntent);
            finish();
        });
    }
}