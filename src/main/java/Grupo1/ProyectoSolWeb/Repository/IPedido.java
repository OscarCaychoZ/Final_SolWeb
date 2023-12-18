/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Repository;

import Grupo1.ProyectoSolWeb.Model.Pedido;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPedido extends CrudRepository<Pedido, Integer> {
    //Obtener el ultimo ID de compra registrado

    @Query(value = "SELECT id_Pedido FROM pedido "
            + "ORDER BY id_Pedido DESC "
            + "LIMIT 1", nativeQuery = true)
    public int ConsultarIdPedido();

    //sumar cantidad
    @Query(value = " SELECT COUNT(*) FROM pedido", nativeQuery = true)
    int contarFilas();
    
    //Ordenar por fecha
    @Query(value = "SELECT * FROM pedido "
            + "ORDER BY fecha ASC", nativeQuery = true)
    List<Pedido> ordenarFechaAsc();

    @Query(value = "SELECT * FROM pedido "
            + "ORDER BY fecha DESC", nativeQuery = true)
    List<Pedido> ordenarFechaDesc();
    
    
    //Busqueda
    @Query(value = "SELECT pe.id_pedido as id_pedido,"
            + "pe.id_proveedor,"
            + "pe.id_usuario,"
            + "pe.fecha,"
            + "pe.estado,"
            + "pe.metodopago,"
            + "pe.tipocomprobante,"
            + "p.id_proveedor as id_p,"
            + "u.id_usuario as id_u,"
            + "p.nombre,"
            + "u.nombre "
            + "FROM pedido pe "
            + "INNER JOIN proveedor p ON pe.id_proveedor = p.id_proveedor "
            + "INNER JOIN usuario u ON pe.id_usuario = u.id_usuario "
            + "WHERE p.nombre LIKE %?1% "
            + "OR u.nombre LIKE %?1% ", nativeQuery = true)
    List<Pedido> findForAll(String desc);

    @Query(value = "SELECT id_pedido,"
            + "id_proveedor,"
            + "id_usuario,"
            + "fecha,"
            + "estado,"
            + "metodopago,"
            + "tipocomprobante "
            + "FROM pedido WHERE estado = true", nativeQuery = true)
    public List<Pedido> PActivos();

    @Query(value = "SELECT id_pedido,"
            + "id_proveedor,"
            + "id_usuario,"
            + "fecha,"
            + "estado,"
            + "metodopago,"
            + "tipocomprobante "
            + "FROM pedido WHERE estado = false", nativeQuery = true)
    public List<Pedido> PAnulados();

    @Modifying
    @Transactional
    @Query(value = "UPDATE pedido set estado = ?1 where id_Pedido = ?2", nativeQuery = true)
    void AnularVenta(@Param("estado") Boolean estado, @Param("id_Pedido") int id);
}
