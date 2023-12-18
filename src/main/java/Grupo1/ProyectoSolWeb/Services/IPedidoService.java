/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Services;

import Grupo1.ProyectoSolWeb.Model.Pedido;
import java.util.List;
import java.util.Optional;

public interface IPedidoService {
    
    public List<Pedido> listarPedido();
    public void guardar(Pedido p);
    public void eliminar(int idPedido);
    public Optional<Pedido> consultarPedido(int idPedido);
    public int UltimoIdVenta();
    public List<Pedido> Buscar(String desc);
    public List<Pedido> VActivas();
    public List<Pedido> VAnuladas();
    public List<Pedido> fechaASC();
    public List<Pedido> fechaDESC();
    public void AnularVentas(Boolean estado, int idPedido);
    public int contarFilas();
}
