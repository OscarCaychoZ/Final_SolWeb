package Grupo1.ProyectoSolWeb.Controller;

/*import Grupo1.ProyectoSolWeb.Services.ClienteService;*/
import Grupo1.ProyectoSolWeb.Model.DetallePedido;
import Grupo1.ProyectoSolWeb.Model.Producto;
import Grupo1.ProyectoSolWeb.Model.Proveedor;
import Grupo1.ProyectoSolWeb.Model.Usuario;
import Grupo1.ProyectoSolWeb.Services.ICategoriaService;
import Grupo1.ProyectoSolWeb.Services.IDetallePedidoService;
import Grupo1.ProyectoSolWeb.Services.IPedidoService;
import Grupo1.ProyectoSolWeb.Services.IProductoService;
import Grupo1.ProyectoSolWeb.Services.IProveedorService;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import Grupo1.ProyectoSolWeb.Services.IUserService;
import java.util.List;

@Controller
public class Controlador {

    @Autowired
    private IProveedorService servicePro;
    @Autowired
    private ICategoriaService serviceCat;
    @Autowired
    private IProductoService serviceProduct;
    @Autowired
    private IPedidoService servicePedido;
    @Autowired
    private IDetallePedidoService serviceDP;
    
    @GetMapping("/")
    public String Principal(Model model, HttpSession session) {
        List<Proveedor> prov = servicePro.listarProveedor();
        
        int totalPro = 0;
        int totalCat = 0;
        int totalProduct = 0;
        int totalped= 0;
        
        totalPro=servicePro.contarFilas();
        totalCat=serviceCat.contarFilas();
        totalProduct= serviceProduct.contarFilas();
        totalped=servicePedido.contarFilas();
        List<Producto> pro = serviceProduct.MasCostosos();
        List<DetallePedido> DP = serviceDP.pedidoActual();
        
        model.addAttribute("detalles", DP);
        model.addAttribute("productos", pro);
        model.addAttribute("TotalPro", totalPro);
        model.addAttribute("TotalCat", totalCat);
        model.addAttribute("TotalProduct", totalProduct);
        model.addAttribute("TotalPed", totalped);

        return "principal";
    }

    @GetMapping("/Ingreso")
    public String IniciarSesion(Usuario cliente) {
        return "index";
    }
    

}
