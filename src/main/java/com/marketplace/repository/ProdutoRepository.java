package com.marketplace.repository;

import com.marketplace.model.Produto;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByVendedorId(Long vendedorId);

    List<Produto> findByQuantidadeGreaterThan(int quantidade);

    /** Tranca a linha do produto: dois compradores não conseguem "levar a última unidade" juntos. */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Produto p where p.id = :id")
    Optional<Produto> buscarComLock(@Param("id") Long id);
}
