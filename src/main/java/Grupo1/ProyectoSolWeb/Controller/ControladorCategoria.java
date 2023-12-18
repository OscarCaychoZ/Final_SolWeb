package Grupo1.ProyectoSolWeb.Controller;

import Grupo1.ProyectoSolWeb.Model.Categoria;
import Grupo1.ProyectoSolWeb.Services.ICategoriaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RequestMapping("/categoria/")
@Controller
public class ControladorCategoria {
    String carpeta = "Categoria/";
        
    @Autowired
    private ICategoriaService service;
    
    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("/nuevo") //localhost/nuevo
    public String NuevoProducto(Model model) {
        List<Categoria> roles = service.listar();
        model.addAttribute("categorias", roles);
        return carpeta + "Categoria"; //nuevoCliente.html
    }

    @PostMapping("/registrar") //localhost/registrar
    public String RegistrarProducto(
            
        @RequestParam("nom") String nombre,           
        Model model) {
        //Aqui será el proceso para registrar
        Categoria p = new Categoria();
        p.setDescripcion(nombre);
        service.guardar(p);
        return "redirect:/categoria/nuevo"; //listaProducto.html
        
    }

    @GetMapping("/eliminar")
    public String eliminarProducto(@RequestParam("cod") int codpro,
            Model model) {
            service.eliminar(codpro);
            return "redirect:/categoria/nuevo";
    }
    
    
}
