package br.edu.ifsp.livros

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.livros.adapter.LivrosAdapter
import br.edu.ifsp.livros.databinding.ActivityMainBinding
import br.edu.ifsp.livros.model.Livro

class MainActivity : AppCompatActivity() {

    companion object Extras {
        const val EXTRA_LIVRO = "Extra_Livro"
        const val EXTRA_POSICAO = "Extra_Posicao"
    }

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    // Data source
    private val livrosList: MutableList<Livro> = mutableListOf()

    // Adapter
    private val livrosAdapter: LivrosAdapter by lazy {
        LivrosAdapter(this, livrosList)
    }

    // Activity Result Launchers
    private lateinit var livroActivityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var editarLivroActivityResultLauncher: ActivityResultLauncher<Intent>

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
                    livrosAdapter.notifyDataSetChanged()
                }
            }
        }

        editarLivroActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                val posicao = resultado.data?.getIntExtra(EXTRA_POSICAO, -1)

                resultado.data?.getParcelableExtra<Livro>(EXTRA_LIVRO)?.apply {
                    if (posicao != null && posicao != -1) {
                        livrosList[posicao] = this
                        livrosAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        // Associar ListView com Menu de Contexto
        registerForContextMenu(activityMainBinding.lvLivros)

        // Setar Clique curto nos itens
        activityMainBinding.lvLivros.setOnItemClickListener { _, _, posicao, _ ->
            val livro = livrosList[posicao]
            val consultarLivroIntent = Intent(this, LivroActivity::class.java)
            consultarLivroIntent.putExtra(EXTRA_LIVRO, livro)
            startActivity(consultarLivroIntent)
        }

        // Setar Click do FloatBottom
        activityMainBinding.fabAdicionarLivro.setOnClickListener {
            livroActivityResultLauncher.launch(Intent(this, LivroActivity::class.java))
            true
        }
    }
    // Inicializa a Lista de Livros - Teste
    private fun inicializarLivrosList() {
        for (indice in 1..12) {
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

    // Menu Main
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.miSair -> {
            finish()
            true
        }
        else -> {
            false
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
        super.onCreateContextMenu(menu, v, menuInfo)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val posicao = (item.menuInfo as AdapterView.AdapterContextMenuInfo).position

        return when (item.itemId) {
            R.id.miEditarLivro -> {
                // Editar Livro
                val livro = livrosList[posicao]
                val editarLivroIntent = Intent(this, LivroActivity::class.java)
                editarLivroIntent.putExtra(EXTRA_LIVRO, livro)
                editarLivroIntent.putExtra(EXTRA_POSICAO, posicao)
                editarLivroActivityResultLauncher.launch(editarLivroIntent)
                true
            }
            R.id.miRemoverLivro -> {
                // Excluir Livro
                livrosList.removeAt(posicao)
                livrosAdapter.notifyDataSetChanged()
                true
            }
            else -> {
                false
            }
        }
    }
}
