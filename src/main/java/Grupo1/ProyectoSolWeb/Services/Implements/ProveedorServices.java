/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Services.Implements;

import Grupo1.ProyectoSolWeb.Model.Proveedor;
import Grupo1.ProyectoSolWeb.Repository.IProveedor;
import Grupo1.ProyectoSolWeb.Services.IProveedorService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorServices implements IProveedorService{
    @Autowired
    private IProveedor data;
    @Override
    public List<Proveedor> listarProveedor() {
        return (List<Proveedor>) data.findAll(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void guardar(Proveedor p) {
        data.save(p);
    }

    @Override
    public void eliminar(int idProveedor) {
        data.deleteById(idProveedor);
    }

    @Override
    public Optional<Proveedor> encontrarProveedor(int idProveedor) {
        return data.findById(idProveedor); 
    }

    @Override
    public List<Proveedor> Buscar(String desc) {
        return data.findForAll(desc); 
    }

    @Override
    public int contarFilas() {
         return data.contarFilas();
    }
    
    @Override
    public int contarFilasFiltro(String desc){
        return data.contarFilasFiltro(desc);
    }
     
}
