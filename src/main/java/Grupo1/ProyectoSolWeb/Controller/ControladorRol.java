/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Controller;


import Grupo1.ProyectoSolWeb.Model.Rol;
import Grupo1.ProyectoSolWeb.Repository.IRol;
import Grupo1.ProyectoSolWeb.Services.IRolService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/rol/")
@Controller
public class ControladorRol {
    String carpeta = "Rol/";
        
    @Autowired
    private IRolService service;
    
    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("/nuevo") //localhost/nuevo
    public String NuevoRol(Model model) {
        List<Rol> roles = service.listar();
        model.addAttribute("roles", roles);
        return carpeta + "Rol"; //nuevoCliente.html
    }

    @PostMapping("/registrar") //localhost/registrar
    public String RegistrarRol(
            
            @RequestParam("nom") String nombre,
            Model model) {
        //Aqui será el proceso para registrar
        Rol p = new Rol();
        p.setDescripcion(nombre);
        service.guardar(p);
        return "redirect:/rol/nuevo"; //listaProducto.html
        
    }
    
    @GetMapping("/eliminar")
    public String eliminarRol(@RequestParam("cod") int codpro,
            Model model) {
            service.eliminar(codpro);
            return "redirect:/rol/nuevo";
    }
}
