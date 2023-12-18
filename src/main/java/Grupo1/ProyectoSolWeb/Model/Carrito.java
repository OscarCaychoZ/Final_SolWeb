/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Model;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class Carrito {
    
    private int id;
    private String producto;
    private Double precio;
    private int cantidad;
    private Double total;
    
}
