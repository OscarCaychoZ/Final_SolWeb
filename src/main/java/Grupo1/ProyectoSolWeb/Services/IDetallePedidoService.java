/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Services;

import Grupo1.ProyectoSolWeb.Model.DetallePedido;
import java.util.List;
import java.util.Optional;


public interface IDetallePedidoService {
    public List<DetallePedido> listarDetalle();
    public void guardar(DetallePedido P);
    public void eliminar(int idDetallePedido);
    public Optional<DetallePedido> encontrarDetalle(int idDetallePedido);
    public List<DetallePedido> BuscarDetalle (String desc);
    public List<DetallePedido> pedidoActual();
}
