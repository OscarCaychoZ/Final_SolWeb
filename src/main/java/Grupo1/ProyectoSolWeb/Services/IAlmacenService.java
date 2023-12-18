/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Services;

import Grupo1.ProyectoSolWeb.Model.Almacen;
import java.util.List;
import java.util.Optional;


public interface IAlmacenService {
    public List<Almacen> listar();
    public void guardar(Almacen p);
    public void eliminar(int idAlmacen);
    public Optional<Almacen> consultar(int idAlmacen);
}
