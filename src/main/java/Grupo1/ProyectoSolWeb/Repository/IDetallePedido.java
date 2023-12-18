/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Repository;

import Grupo1.ProyectoSolWeb.Model.DetallePedido;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDetallePedido extends CrudRepository<DetallePedido, Integer>{
   @Query(value = "SELECT * FROM detalle_Pedido JOIN pedido v ORDER BY v.fecha DESC LIMIT 1", nativeQuery = true)
    List<DetallePedido> pedidoMasReciente();
}
