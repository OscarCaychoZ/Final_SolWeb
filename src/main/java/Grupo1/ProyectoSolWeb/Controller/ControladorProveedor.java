/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Controller;

import Grupo1.ProyectoSolWeb.Model.Almacen;
import Grupo1.ProyectoSolWeb.Model.Proveedor;
import Grupo1.ProyectoSolWeb.Services.IProveedorService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/proveedor/")
@Controller
public class ControladorProveedor {

    String carpeta = "Proveedor/";

    @Autowired
    private IProveedorService service;

    @GetMapping("/nuevo") //localhost/nuevo
    public String NuevoCliente() {
        return carpeta + "nuevoProveedor"; //nuevoCliente.html
    }

    //Los parametros que se requieren son los nombres de cada input donde se inserta la informacion 
    @PostMapping("/registrar") //localhost/registrar
    public String RegistrarCliente(
            @RequestParam("nom") String nom,
            @RequestParam("ruc") String ape,
            @RequestParam("dir") String dni,
            @RequestParam("cel") String cel,
            Model model) {
        //Aqui será el proceso para registrar
        Proveedor c = new Proveedor();
        c.setNombre(nom);
        c.setRUC(ape);
        c.setDireccion(dni);
        c.setCelular(cel);
        service.guardar(c);

        return listar(model);
    }

    //Para actualizar es necesario insertar los datos de la clase
    @PostMapping("/actualizar") //localhost/registrar
    public String actualizarCliente(@RequestParam("idProveedor") int cod,
            @RequestParam("nombre") String nom,
            @RequestParam("RUC") String ruc,
            @RequestParam("direccion") String dir,
            @RequestParam("celular") String cel,
            Model model) {

        //Aqui será el proceso para registrar
        Proveedor c = new Proveedor();
        c.setIdProveedor(cod);
        c.setNombre(nom);
        c.setRUC(ruc);
        c.setCelular(cel);
        c.setDireccion(dir);

        service.guardar(c);

        return listar(model);
    }

    @GetMapping("/listar") //localhost/
    public String listar(Model model) {
        List<Proveedor> prov = service.listarProveedor();
        model.addAttribute("proveedores", prov);
        int totalP = 0;

        totalP = service.contarFilas();
        model.addAttribute("totalP", totalP);

        return carpeta + "listaProveedor";
    }

    @GetMapping("/eliminar")
    public String Eliminar(@RequestParam("cod") int cod,
            Model model) {
        service.eliminar(cod);
        return listar(model);
    }

    @GetMapping("/editar")
    public String editar(@RequestParam("cod") int cod,
            Model model) {
        Optional<Proveedor> proveedor = service.encontrarProveedor(cod);
        model.addAttribute("Proveedor", proveedor);
        return carpeta + "editarProveedor";
    }

    @PostMapping("/buscar")
    public String BuscarAtencion(@RequestParam("desc") String desc,
            Model model) {

        List<Proveedor> atenciones = service.Buscar(desc);
        model.addAttribute("proveedores", atenciones);

        int totalP = 0;
        totalP = service.contarFilasFiltro(desc);
        model.addAttribute("totalP", totalP);
        return carpeta + "listaProveedor";
    }
}
