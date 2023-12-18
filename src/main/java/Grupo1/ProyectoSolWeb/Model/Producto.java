
package Grupo1.ProyectoSolWeb.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
@Entity
@Data
@Table(name="producto")
public class Producto {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int idProducto;
    public String nombre;
    public String talla;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idCategoria")
    public Categoria categoria;
    public double precio;
    public int stock;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="idInventario")
    public Almacen inventario;
}
