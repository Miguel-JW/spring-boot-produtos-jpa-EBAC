package com.exemplo.produtos.controller;

import com.exemplo.produtos.entity.Categoria;
import com.exemplo.produtos.entity.Produto;
import com.exemplo.produtos.service.ProdutoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    // --- Categoria ---

    @PostMapping("/categorias")
    public ResponseEntity<Categoria> criarCategoria(@RequestBody Categoria categoria) {
        return ResponseEntity.ok(produtoService.salvarCategoria(categoria));
    }

    @GetMapping("/categorias/{id}")
    public ResponseEntity<Categoria> buscarCategoria(@PathVariable Long id) {
        return produtoService.buscarCategoriaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/categorias/{id}")
    public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
        produtoService.deletarCategoria(id);
        return ResponseEntity.noContent().build();
    }

    // --- Produto ---

    @PostMapping("/produtos")
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto) {
        return ResponseEntity.ok(produtoService.salvarProduto(produto));
    }

    @GetMapping("/produtos/{id}")
    public ResponseEntity<Produto> buscarProduto(@PathVariable Long id) {
        return produtoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/produtos/{id}")
    public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/produtos/buscar")
    public ResponseEntity<List<Produto>> buscarPorNome(@RequestParam String nome) {
        return ResponseEntity.ok(produtoService.buscarPorNomeExato(nome));
    }

    // Paginação simples
    @GetMapping("/produtos")
    public ResponseEntity<Page<Produto>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(produtoService.listarPaginado(page, size));
    }

    // Paginação + filtro + ordenação
    @GetMapping("/produtos/filtrar")
    public ResponseEntity<Page<Produto>> filtrar(
            @RequestParam String nome,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nome") String ordenarPor,
            @RequestParam(defaultValue = "asc") String direcao) {
        return ResponseEntity.ok(produtoService.buscarPorNomePaginado(nome, page, size, ordenarPor, direcao));
    }
}
