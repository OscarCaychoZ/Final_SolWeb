/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.Data;

@Entity
@Data
@Table(name="pedido")
public class Pedido {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idPedido;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario Usuario;
    
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "idProveedor")
    private Proveedor proveedor;
    
    private String tipocomprobante;
    
    private String metodopago;
    
    private Date fecha;
    
    private Boolean estado;
    
}
