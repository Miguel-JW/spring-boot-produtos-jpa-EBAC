package com.exemplo.produtos.repository;

import com.exemplo.produtos.entity.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Consulta JPQL por nome exato
    @Query("SELECT p FROM Produto p WHERE p.nome = :nome")
    java.util.List<Produto> findByNomeExato(@Param("nome") String nome);

    // Paginação + filtro por nome (contém) + ordenação via Pageable
    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    Page<Produto> findByNomeContendo(@Param("nome") String nome, Pageable pageable);

    // Paginação sem filtro
    Page<Produto> findAll(Pageable pageable);
}
