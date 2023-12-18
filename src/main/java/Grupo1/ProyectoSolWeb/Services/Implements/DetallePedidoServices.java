/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Services.Implements;

import Grupo1.ProyectoSolWeb.Model.DetallePedido;
import Grupo1.ProyectoSolWeb.Repository.IDetallePedido;
import Grupo1.ProyectoSolWeb.Services.IDetallePedidoService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetallePedidoServices implements IDetallePedidoService {

    @Autowired
    private IDetallePedido data;

    @Override
    public List<DetallePedido> listarDetalle() {
        return (List<DetallePedido>) data.findAll(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void guardar(DetallePedido P) {
        data.save(P); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void eliminar(int idDetallePedido) {
        data.deleteById(idDetallePedido); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<DetallePedido> encontrarDetalle(int idDetallePedido) {
        return data.findById(idDetallePedido);  // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DetallePedido> BuscarDetalle(String desc) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DetallePedido> pedidoActual() {
        return data.pedidoMasReciente(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    


}
