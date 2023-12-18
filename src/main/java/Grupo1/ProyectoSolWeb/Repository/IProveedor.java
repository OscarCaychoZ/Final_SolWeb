/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Repository;

import Grupo1.ProyectoSolWeb.Model.Proveedor;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProveedor extends CrudRepository<Proveedor, Integer>{
    
    //buscar 
    @Query(value = "SELECT * FROM proveedor "
            + "WHERE nombre LIKE %?1% "
            + "OR ruc LIKE %?1% "
            + "OR celular LIKE %?1% "
            + "OR direccion LIKE %?1% ", nativeQuery = true)
    List<Proveedor> findForAll(String desc);
    
    //sumar cantidad
    @Query(value =" SELECT COUNT(*) FROM proveedor", nativeQuery= true)
    int contarFilas();
    
    @Query(value = "SELECT COUNT(*) FROM proveedor "
            + "WHERE nombre LIKE %?1% "
            + "OR ruc LIKE %?1% "
            + "OR celular LIKE %?1% "
            + "OR direccion LIKE %?1% ", nativeQuery = true)
    int contarFilasFiltro(String desc);
    
    
    
}
