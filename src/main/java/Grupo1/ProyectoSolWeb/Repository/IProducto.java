/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Repository;

import Grupo1.ProyectoSolWeb.Model.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProducto extends CrudRepository<Producto, Integer> {

    //sumar cantidad
    @Query(value = " SELECT COUNT(*) FROM producto", nativeQuery = true)
    int contarFilas();

    //ordenar de manera descendente
    @Query(value = "SELECT * FROM producto "
            + "ORDER BY precio DESC ", nativeQuery = true)
    List<Producto> descProduct();

    //ordenar de manera ascendente
    @Query(value = "SELECT * FROM producto "
            + "ORDER BY precio ASC ", nativeQuery = true)
    List<Producto> ascProduct();

    @Query(value = "SELECT * FROM producto "
            + "ORDER BY precio DESC LIMIT 3; ", nativeQuery = true)
    List<Producto> MasCostoso();
    
}
