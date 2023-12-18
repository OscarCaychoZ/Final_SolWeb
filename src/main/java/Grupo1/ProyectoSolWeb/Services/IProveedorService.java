/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Services;

import Grupo1.ProyectoSolWeb.Model.Proveedor;
import java.util.List;
import java.util.Optional;


public interface IProveedorService {
    public List<Proveedor> listarProveedor();
    public void guardar(Proveedor P);
    public void eliminar(int idProveedor);
    public Optional<Proveedor> encontrarProveedor(int idProveedor);
    public List<Proveedor> Buscar (String desc);
    public int contarFilas();
    public int contarFilasFiltro(String desc);
}
