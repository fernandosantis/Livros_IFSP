package br.edu.ifsp.livros


// Interface que será implementada na Acitvity para tratar eventos de cloque que será usada no
// Adapter para tratar eventos de clique nas células do RecyclerView

interface OnLivroClickListener {
    fun onLivroClick(posicao: Int)
}