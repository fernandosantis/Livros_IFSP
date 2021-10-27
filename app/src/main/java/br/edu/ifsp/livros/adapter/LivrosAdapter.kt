package br.edu.ifsp.livros.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.livros.R
import br.edu.ifsp.livros.databinding.LayoutLivroBinding
import br.edu.ifsp.livros.model.Livro

class LivrosAdapter(
    val contexto: Context,
    val livrosList: MutableList<Livro>
) : ArrayAdapter<Livro>(contexto, R.layout.layout_livro, livrosList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val livrolayout: View = if (convertView != null) {
            convertView
        } else {
            val layoutLivroBinding = LayoutLivroBinding.inflate((contexto.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater), parent, false)

            val livroLayoutHolder = LivroLayoutHolder(layoutLivroBinding.tvTitulo, layoutLivroBinding.tvPrimeiroAutor, layoutLivroBinding.tvEditora)
            layoutLivroBinding.root.tag = livroLayoutHolder

            layoutLivroBinding.root
        }

        // Preencher ou Atualizar a View - livrolayout

        val livro: Livro = livrosList[position]
        val holder = livrolayout.tag as LivroLayoutHolder
        holder.tvTitulo.text = livro.titulo
        holder.tvPrimeiroAutor.text = livro.primeiroAutor
        holder.tvEditora.text = livro.editora

        return livrolayout
    }

    private data class LivroLayoutHolder(
        val tvTitulo: TextView,
        val tvPrimeiroAutor: TextView,
        val tvEditora: TextView

    )
}
