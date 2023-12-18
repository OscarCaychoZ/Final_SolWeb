/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Controller;

import Grupo1.ProyectoSolWeb.Model.Rol;
import Grupo1.ProyectoSolWeb.Model.Usuario;
import Grupo1.ProyectoSolWeb.Services.IRolService;
import Grupo1.ProyectoSolWeb.Services.IUserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/usuario/")
public class ControladorUsuario {
    String carpeta = "Usuario/";
    
    @Autowired
    private IUserService service;
    @Autowired
    private IRolService serviceR;

    @GetMapping("/nuevo") 
    public String NuevoCliente(Model model) {
        List<Rol> rol= serviceR.listar();
        model.addAttribute("rolp",rol);
        return carpeta + "nuevoUsuario"; //nuevoCliente.html
    }
    
    @PostMapping("/registrar")
    public String RegistrarUsuario(
            @RequestParam("nom") String nombre,           
            @RequestParam("ape") String apellido,
            @RequestParam("email") String email,
            @RequestParam("pass") String password,
            @RequestParam("rol") Rol rol,
            @RequestParam("user") String user,

            Model model) {
        //Aqui será el proceso para registrar
        Usuario p = new Usuario();
        BCryptPasswordEncoder bw = new BCryptPasswordEncoder();
        String pass_encriptado = bw.encode(password);
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setCorreo(email);
        p.setPassword(pass_encriptado);
        p.setUser(user);
        p.setRol(rol);
        service.guardar(p);
        return listar(model);
    }
    
    @GetMapping("/listar") //localhost/
    public String listar(Model model) {
        List<Usuario> clientes = service.listar();
        model.addAttribute("usuarios", clientes);
        return carpeta + "listaUsuario"; //listaProducto.html
    }
    
    
    
    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("/editar")
    public String editar(@RequestParam("cod") int cod,
            Model model) {
        List<Rol> rol= serviceR.listar();
        model.addAttribute("rolp",rol);
        Optional<Usuario> proveedor = service.encontrar(cod);
        model.addAttribute("Usuario", proveedor);
        return carpeta + "editarUsuario";
    }
    
    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("/eliminar")
    public String Eliminar(@RequestParam("cod") int cod,
            Model model) {
        service.eliminar(cod);
        return listar(model);
    }
    
        //Para actualizar es necesario insertar los datos de la clase
    @PostMapping("/actualizar") //localhost/registrar
    public String actualizarUsuario(@RequestParam("idUsuario") int cod,
            @RequestParam("nombre") String nom,
            @RequestParam("apellido") String ape,
            @RequestParam("correo") String email,
            @RequestParam("password") String pass,
            @RequestParam("rol") Rol rol,
            @RequestParam("user") String user,
            Model model) {

        //Aqui será el proceso para registrar
        Usuario c = new Usuario();
        BCryptPasswordEncoder bw = new BCryptPasswordEncoder();
        String pass_encriptado = bw.encode(pass);
        c.setIdUsuario(cod);
        c.setNombre(nom);
        c.setApellido(ape);
        c.setCorreo(email);
        c.setPassword(pass_encriptado);
        c.setRol(rol);
        c.setUser(user);
        service.guardar(c);
        return listar(model);
    }
    
    @GetMapping("/ascendente")
    public String Ascendente(Model model) {

        List<Usuario> user = service.Ascendente();
        model.addAttribute("usuarios", user);
        return carpeta + "listaUsuario";
    }

    @GetMapping("/descendente")
    public String Descendente(Model model) {

        List<Usuario> user = service.Descendente();
        model.addAttribute("usuarios", user);
        return carpeta + "listaUsuario";
    }

    
      
}
