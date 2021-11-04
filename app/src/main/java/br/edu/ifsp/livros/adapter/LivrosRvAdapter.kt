package br.edu.ifsp.livros.adapter

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.edu.ifsp.livros.OnLivroClickListener
import br.edu.ifsp.livros.R
import br.edu.ifsp.livros.databinding.LayoutLivroBinding
import br.edu.ifsp.livros.model.Livro

class LivrosRvAdapter(
    private val onLivroClickListener: OnLivroClickListener,
    private val listaLivros: MutableList<Livro>
) : RecyclerView.Adapter<LivrosRvAdapter.LivroLayoutHolder>() {

    // ViewHolder - Fixador de Views
    inner class LivroLayoutHolder(layoutLivroBinding: LayoutLivroBinding) : RecyclerView.ViewHolder(layoutLivroBinding.root), View.OnCreateContextMenuListener {
        val tituloTv: TextView = layoutLivroBinding.tvTitulo
        val primeiroAutorTv: TextView = layoutLivroBinding.tvPrimeiroAutor
        val editoraTv: TextView = layoutLivroBinding.tvEditora

        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            MenuInflater(v?.context).inflate(R.menu.context_menu_main, menu)
        }
    }

    // Chamada pelo LayoutManager quando uma nova celula precisa ser criada
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LivroLayoutHolder {
        // Cria uma nova celula a partir da nossa classe de viewBinding
        val layoutLivroBinding = LayoutLivroBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        // Cria um viewHolder e associa a raiz da instancia da classe de viewBinding=celula
        return LivroLayoutHolder(layoutLivroBinding)
    }

    // Chamada pelo LayoytManager para alterar o conteudo de uma celula
    override fun onBindViewHolder(holder: LivroLayoutHolder, position: Int) {
        // Buscar o Livro
        val livro = listaLivros[position]

        // Altera os valors das views do viewHolder
        with(holder) {
            tituloTv.text = livro.titulo
            primeiroAutorTv.text = livro.primeiroAutor
            editoraTv.text = livro.editora
        }

        // Seta o onClickListener da Celula que est´associada ao viewHolder como lambda
        // que chama uma função na MainActivity
        holder.itemView.setOnClickListener {
            onLivroClickListener.onLivroClick(position)
        }

        // Configuranto o Clique Longo
        holder.itemView.setOnLongClickListener {
            posicao = position
            false
        }
    }

    override fun getItemCount(): Int = listaLivros.size

    // Posição a ser recuperada no menu de contexto
    var posicao: Int = -1
}
