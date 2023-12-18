/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Services.Implements;

import Grupo1.ProyectoSolWeb.Model.Almacen;
import Grupo1.ProyectoSolWeb.Repository.IAlmacen;
import Grupo1.ProyectoSolWeb.Services.IAlmacenService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlmacenServices implements IAlmacenService{
    @Autowired
    private IAlmacen data;
    
    @Override
    public List<Almacen> listar() {
        return (List<Almacen>) data.findAll();
    }

    @Override
    public void guardar(Almacen a) {
         data.save(a);
    }

    @Override
    public void eliminar(int idAlmacen) {
         data.deleteById(idAlmacen);
    }

    @Override
    public Optional<Almacen> consultar(int idAlmacen) {
        return data.findById(idAlmacen); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
   
}
