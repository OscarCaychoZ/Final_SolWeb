/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Services;

import Grupo1.ProyectoSolWeb.Model.Categoria;
import java.util.List;

public interface ICategoriaService {
    public List<Categoria> listar();
    public void guardar(Categoria P);
    public void eliminar(int idCategoria);
    public int contarFilas();
}
