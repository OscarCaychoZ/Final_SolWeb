/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Controller;

import Grupo1.ProyectoSolWeb.Model.Almacen;
import Grupo1.ProyectoSolWeb.Model.Categoria;
import Grupo1.ProyectoSolWeb.Model.Producto;
import Grupo1.ProyectoSolWeb.Services.IAlmacenService;
import Grupo1.ProyectoSolWeb.Services.ICategoriaService;
import Grupo1.ProyectoSolWeb.Services.IProductoService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/producto/")
@Controller
public class ControladorProducto {

    String carpeta = "Producto/";

    @Autowired
    private IProductoService service;
    @Autowired
    private ICategoriaService serviceC;
    @Autowired
    private IAlmacenService serviceA;

    @GetMapping("/nuevoPro")
    public String addProductos(Model model) {

        List<Categoria> cat = serviceC.listar();
        List<Almacen> alm = serviceA.listar();

        model.addAttribute("categoriap", cat);
        model.addAttribute("almacenes", alm);

        return carpeta + "nuevoProducto";
    }

    @PostMapping("/registrar") //localhost/registrar
    public String RegistrarProducto(
            @RequestParam("nom") String nombre,
            @RequestParam("categoria") Categoria categoria,
            @RequestParam("tal") String talla,
            @RequestParam("pre") Double precio,
            @RequestParam("stock") int stock,
            @RequestParam("almacen") Almacen alm,
            Model model) {
        //Aqui será el proceso para registrar
        Producto p = new Producto();
        p.setNombre(nombre);
        p.setCategoria(categoria);
        p.setTalla(talla);
        p.setPrecio(precio);
        p.setStock(stock);
        p.setInventario(alm);
        service.guardar(p);
        return listarPro(model);
    }

    @GetMapping("/listar") //localhost/
    public String listarPro(Model model) {
        List<Producto> pro = service.listarProductos();
        model.addAttribute("productos", pro);
        return carpeta + "listaProducto"; //listaProducto.html
    }

    @GetMapping("/eliminar")
    public String eliminarProducto(@RequestParam("cod") int codpro,
            Model model) {
        service.eliminar(codpro);
        return listarPro(model);
    }

    @GetMapping("/editar")
    public String editarProducto(@RequestParam("cod") int codpro,
            Model model) {

        Optional<Producto> product = service.consultarCodigo(codpro);
        List<Categoria> cat = serviceC.listar();
        List<Almacen> alm = serviceA.listar();

        model.addAttribute("categoriap", cat);
        model.addAttribute("almacenes", alm);
        //se llama al object form de editarProducto.html
        model.addAttribute("Producto", product);

        return carpeta + "editarProducto";
    }

    @PostMapping("/actualizar") //localhost/
    public String actualizarProd(@RequestParam("idProducto") int codpro,
            @RequestParam("nombre") String nombre,
            @RequestParam("categoria") Categoria categoria,
            @RequestParam("talla") String talla,
            @RequestParam("precio") Double precio,
            @RequestParam("stock") int stock,
            @RequestParam("almacen") Almacen alm,
            Model model) {
        //Aqui será el proceso para registrar
        Producto p = new Producto();
        p.setIdProducto(codpro);
        p.setNombre(nombre);
        p.setCategoria(categoria);
        p.setTalla(talla);
        p.setPrecio(precio);
        p.setStock(stock);
        p.setInventario(alm);
        service.guardar(p);
        return listarPro(model);
    }

    @GetMapping("/ascendente")
    public String Ascendente(Model model) {

        List<Producto> pro = service.Asc();
        model.addAttribute("productos", pro);
        return carpeta + "listaProducto";
    }

    @GetMapping("/descendente")
    public String Descendente(Model model) {

        List<Producto> pro = service.Desc();
        model.addAttribute("productos", pro);
        return carpeta + "listaProducto";
    }

}
