/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Services.Implements;

import Grupo1.ProyectoSolWeb.Model.Categoria;
import Grupo1.ProyectoSolWeb.Repository.ICategoria;
import Grupo1.ProyectoSolWeb.Services.ICategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaServices implements ICategoriaService{
    @Autowired
    private ICategoria data;
    @Override
    public List<Categoria> listar() {
        return (List<Categoria>) data.findAll(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void guardar(Categoria P) {
        data.save(P);
    }

    @Override
    public void eliminar(int idCategoria) {
        data.deleteById(idCategoria); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int contarFilas() {
        return data.contarFilas();// Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
