package com.marketplace.repository;

import com.marketplace.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    List<Pedido> findByCompradorIdOrderByCriadoEmDesc(Long compradorId);

    List<Pedido> findByVendedorIdOrderByCriadoEmDesc(Long vendedorId);
}
