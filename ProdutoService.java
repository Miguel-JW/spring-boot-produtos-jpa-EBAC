package com.exemplo.produtos.service;

import com.exemplo.produtos.entity.Categoria;
import com.exemplo.produtos.entity.Produto;
import com.exemplo.produtos.repository.CategoriaRepository;
import com.exemplo.produtos.repository.ProdutoRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProdutoService(ProdutoRepository produtoRepository, CategoriaRepository categoriaRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // --- Categoria ---

    public Categoria salvarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public Optional<Categoria> buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public void deletarCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    // --- Produto ---

    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> salvarTodos(List<Produto> produtos) {
        return produtoRepository.saveAll(produtos);
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public void deletarProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public List<Produto> buscarPorNomeExato(String nome) {
        return produtoRepository.findByNomeExato(nome);
    }

    // Paginação simples
    public Page<Produto> listarPaginado(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return produtoRepository.findAll(pageable);
    }

    // Paginação + filtro por nome + ordenação (asc ou desc)
    public Page<Produto> buscarPorNomePaginado(String nome, int page, int size, String ordenarPor, String direcao) {
        Sort sort = direcao.equalsIgnoreCase("desc")
                ? Sort.by(ordenarPor).descending()
                : Sort.by(ordenarPor).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return produtoRepository.findByNomeContendo(nome, pageable);
    }
}
