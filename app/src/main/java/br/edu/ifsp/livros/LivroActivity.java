package br.edu.ifsp.livros;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import br.edu.ifsp.livros.databinding.ActivityLivroBinding;
import br.edu.ifsp.livros.model.Livro;

public class LivroActivity extends AppCompatActivity {
    private ActivityLivroBinding activityLivroBinding;
    private Livro livro;
    private int posicao = -1;
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
            if (posicao != -1) {
                resultadoIntent.putExtra(MainActivity.EXTRA_POSICAO, posicao);
            }
            setResult(RESULT_OK, resultadoIntent);
            finish();
        });

        // Verificando se é uma edição
        livro = getIntent().getParcelableExtra(MainActivity.EXTRA_LIVRO);
        posicao = getIntent().getIntExtra(MainActivity.EXTRA_POSICAO,-1);
        if (livro != null) {
            activityLivroBinding.etTitulo.setText(livro.getTitulo());
            activityLivroBinding.etISBN.setText(livro.getIsbn());
            activityLivroBinding.etPrimeiroAutor.setText(livro.getPrimeiroAutor());
            activityLivroBinding.etEditora.setText(livro.getEditora());
            activityLivroBinding.etEdicao.setText(String.valueOf(livro.getEdicao()));
            activityLivroBinding.etPaginas.setText(String.valueOf(livro.getPaginas()));
            if (posicao == -1) {
                for (int i = 0; i < activityLivroBinding.getRoot().getChildCount(); i++) {
                    activityLivroBinding.getRoot().getChildAt(i).setEnabled(false);
                }
                activityLivroBinding.btSalvar.setVisibility(View.GONE);
            }
        }

    }
}