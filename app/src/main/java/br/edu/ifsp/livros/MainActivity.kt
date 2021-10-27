package br.edu.ifsp.livros

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.livros.databinding.ActivityMainBinding
import br.edu.ifsp.livros.model.Livro

class MainActivity : AppCompatActivity() {

    companion object Extras {
        const val EXTRA_LIVRO = "Extra_Livro"
    }

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val livrosList: MutableList<Livro> = mutableListOf()

    // Adapter
    private val livrosAdapter: ArrayAdapter<String> by lazy {

        ArrayAdapter(
            this, android.R.layout.simple_expandable_list_item_1,
            livrosList.run {
                val livrosStringList = mutableListOf<String>()
                this.forEach { livro -> livrosStringList.add(livro.toString()) }
                livrosStringList
            }
        )
    }

    // Activity Result Launchers
    private lateinit var livroActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        // Inicializa a Lista de Livros - Teste
        inicializarLivrosList()

        activityMainBinding.lvLivros.adapter = livrosAdapter

        livroActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                resultado.data?.getParcelableExtra<Livro>(EXTRA_LIVRO)?.apply {
                    livrosList.add(this)
                    livrosAdapter.add(this.toString())
                }
            }
        }
    }
    // Inicializa a Lista de Livros - Teste
    private fun inicializarLivrosList() {
        for (indice in 1..5) {
            livrosList.add(
                Livro(
                    "Titulo $indice",
                    "Isbn $indice",
                    "Autor $indice",
                    "Editora $indice",
                    indice,
                    indice
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.mi_AdicionarLivro -> {
            livroActivityResultLauncher.launch(Intent(this, LivroActivity::class.java))
            true
        }
        else -> {
            false
        }
    }
}
