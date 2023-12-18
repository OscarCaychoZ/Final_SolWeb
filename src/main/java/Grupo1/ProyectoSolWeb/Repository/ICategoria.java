/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Repository;
import Grupo1.ProyectoSolWeb.Model.Categoria;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoria extends CrudRepository<Categoria,Integer>{
    //sumar cantidad
    @Query(value =" SELECT COUNT(*) FROM categoria", nativeQuery= true)
    int contarFilas();
}
