/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Services;

import Grupo1.ProyectoSolWeb.Model.Rol;
import java.util.List;
import java.util.Optional;


public interface IRolService{
    public List<Rol> listar();
    public void guardar(Rol R);
    public void eliminar(int idRol);
    public Optional<Rol> encontrar(int idRol);
}
