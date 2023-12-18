/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Services;

import Grupo1.ProyectoSolWeb.Model.Producto;
import java.util.List;
import java.util.Optional;


public interface IProductoService {
    public List<Producto> listarProductos();
    public void guardar(Producto p);
    public List<Producto> MasCostosos();
    public void eliminar(int idProducto);
    public Optional<Producto> consultarCodigo(int idProducto);
    public int contarFilas();
    public List<Producto> Asc();
    public List<Producto> Desc();
    
}
