/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Services.Implements;


import Grupo1.ProyectoSolWeb.Model.Rol;
import Grupo1.ProyectoSolWeb.Repository.IRol;
import Grupo1.ProyectoSolWeb.Services.IRolService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolServices implements IRolService{
    @Autowired
    private IRol data;
    @Override
    public List<Rol> listar() {
        return (List<Rol>) data.findAll(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void guardar(Rol R) {
        data.save(R); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int idRol) {
         data.deleteById(idRol);
    }

    @Override
    public Optional<Rol> encontrar(int idRol) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
