/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Grupo1.ProyectoSolWeb.Controller;

import Grupo1.ProyectoSolWeb.Model.Carrito;
import Grupo1.ProyectoSolWeb.Model.DetallePedido;
import Grupo1.ProyectoSolWeb.Model.Pedido;
import Grupo1.ProyectoSolWeb.Model.Producto;
import Grupo1.ProyectoSolWeb.Model.Proveedor;
import Grupo1.ProyectoSolWeb.Model.Usuario;
import Grupo1.ProyectoSolWeb.Repository.IDetallePedido;
import Grupo1.ProyectoSolWeb.Services.IDetallePedidoService;
import Grupo1.ProyectoSolWeb.Services.IPedidoService;
import Grupo1.ProyectoSolWeb.Services.IProductoService;
import Grupo1.ProyectoSolWeb.Services.IProveedorService;
import Grupo1.ProyectoSolWeb.Services.IUserService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.System.Logger.Level;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/pedido/")
@Controller
public class ControladorPedido {

    String carpeta = "Pedido/";
    ArrayList<Carrito> carrito_venta_lista = new ArrayList();
    @Autowired
    private IUserService serviceUsuario;
    @Autowired
    private IProveedorService serviceProveedor;
    @Autowired
    private IPedidoService serviceP;
    @Autowired
    private IProductoService service_product;
    @Autowired
    private IDetallePedidoService service_dp;

    private Logger logger = Logger.getLogger(getClass().getName());

    public List<String> TipoComprobante() {
        return List.of("Boleta", "Factura");
    }

    public List<String> MetodoPago() {
        return List.of("Efectivo", "Debito", "Paypal", "Yape", "Plin", "Credito");
    }

    @GetMapping("/nuevoPedido")
    public String NuevoPedido(Model model) {

        List<Proveedor> proveedor = serviceProveedor.listarProveedor();
        List<Producto> producto = service_product.listarProductos();
        List<Usuario> user = serviceUsuario.listar();
        List<String> metodopago = MetodoPago();
        List<String> tipocomprobante = TipoComprobante();

        model.addAttribute("tipocomprobante", tipocomprobante);
        model.addAttribute("metodopago", metodopago);
        model.addAttribute("proveedores", serviceProveedor.listarProveedor());
        model.addAttribute("usuarios", serviceUsuario.listar());
        model.addAttribute("productos", service_product.listarProductos());
        model.addAttribute("carrito", carrito_venta_lista);

        return carpeta + "nuevoPedido";
    }

    @PostMapping("/registrarPedido") //localhost/cliente/registrar
    public String ConcretarVenta(
            @RequestParam("proveedor") Proveedor proveedor_id,
            @RequestParam("usuario") Usuario usuario_id,
            @RequestParam("fec") String fec,
            @RequestParam("mp") String mp,
            @RequestParam("tc") String tc,
            Model model) throws ParseException {

        //Configurar fecha
        String[] parts = fec.split("T");
        String part1 = parts[0]; // 2023-06-12
        String part2 = parts[1]; //20:28
        String fec_ = part1 + " " + part2 + ":00";

        SimpleDateFormat formateador_fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fecha = formateador_fecha.parse(fec_);

        //Aqui va el proceso de registrar
        Pedido v = new Pedido();
        //c.setId(cod);
        v.setProveedor(proveedor_id);
        v.setUsuario(usuario_id);
        v.setFecha(fecha);
        v.setTipocomprobante(tc);
        v.setMetodopago(mp);
        v.setEstado(Boolean.TRUE);
        //lista.add(p);
        serviceP.guardar(v);

        //Conocer el ID de la venta
        int id_compra = serviceP.UltimoIdVenta();
        Pedido IdC = new Pedido();
        IdC.setIdPedido(id_compra);

        for (int i = 0; i < carrito_venta_lista.size(); i++) {
            int id_producto = carrito_venta_lista.get(i).getId();
            Producto p = new Producto();
            p.setIdProducto(id_producto);

            int cant = carrito_venta_lista.get(i).getCantidad();
            Double prec = carrito_venta_lista.get(i).getPrecio();
            Double total = carrito_venta_lista.get(i).getTotal();

            DetallePedido vd = new DetallePedido();
            vd.setPedido(v);
            vd.setProducto(p);
            vd.setCantidad(cant);
            vd.setPrecio(prec);
            vd.setTotal(total);

            service_dp.guardar(vd);

        }
        carrito_venta_lista.clear();

        return MostrarPedidos(model);
    }

    @GetMapping("/listaPedido")
    private String MostrarPedidos(Model model) {
        List<Pedido> compras = serviceP.listarPedido();
        model.addAttribute("pedidos", compras);
        return carpeta + "listaPedido";
    }

    @PostMapping("/agregarPedido")
    public String AgregarCarrito(@RequestParam("idProducto") int producto_id,
            @RequestParam("cant") int cant,
            Model model) {
        Optional<Producto> producto = service_product.consultarCodigo(producto_id);
        String nombre = producto.get().getNombre();
        Double precioc = producto.get().getPrecio();
        Double total = cant * precioc;

        Carrito carrito = new Carrito();
        carrito.setId(producto_id);
        carrito.setProducto(nombre);
        carrito.setPrecio(precioc);
        carrito.setCantidad(cant);
        carrito.setTotal(total);
        carrito_venta_lista.add(carrito);

        return NuevoPedido(model);
    }

    @GetMapping("/eliminarcarrito")
    public String EliminarCarrito(@RequestParam("cod") int id,
            Model model) {

        carrito_venta_lista.remove(id - 1);
        return NuevoPedido(model);
    }

    //Funcionalidades de la lista
    @GetMapping("/eliminarPedido")
    public String AnularPedido(@RequestParam("cod") int id,
            Model model) {
        serviceP.eliminar(id);
        return MostrarPedidos(model);
    }

    @GetMapping("/anularPedido")
    public String AnularVenta(@RequestParam("cod") int id,
            Model model) {
        boolean estado = false;
        serviceP.AnularVentas(estado, id);
        return MostrarPedidos(model);
    }

    @PostMapping("/BuscarPedido")
    public String BuscarPedido(@RequestParam("desc") String desc,
            Model model) {
        List<Pedido> pedidos = serviceP.Buscar(desc);
        model.addAttribute("pedidos", pedidos);
        return carpeta + "listaPedido";
    }

    //Ordenar por estado
    @GetMapping("/pedidosActivos")
    public String pedidoActivos(Model model) {
        List<Pedido> user = serviceP.VActivas();
        model.addAttribute("pedidos", user);
        return carpeta + "listaPedido";
    }

    @GetMapping("/pedidosAnulados")
    public String pedidosAnulados(Model model) {
        List<Pedido> user = serviceP.VAnuladas();
        model.addAttribute("pedidos", user);
        return carpeta + "listaPedido";
    }

    //Ordernar por fecha
    @GetMapping("/fechaASC")
    public String PFechaAsc(Model model) {
        List<Pedido> user = serviceP.fechaASC();
        model.addAttribute("pedidos", user);
        return carpeta + "listaPedido";
    }

    @GetMapping("/fechaDESC")
    public String PFechaDesc(Model model) {
        List<Pedido> user = serviceP.fechaDESC();
        model.addAttribute("pedidos", user);
        return carpeta + "listaPedido";
    }

    //Exportar excel
    public ByteArrayInputStream exportarExcel() throws IOException {

        // Tu lógica para obtener los datos a exportar (por ejemplo, una lista)
        String cols[] = {"ID", "Usuario", "Proveedor", "Metodo de pago", "Tipo de comprobante", "Fecha solicitada", "Estado"};

        // Crear un libro de Excel en memoria usando ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Workbook workbook = new HSSFWorkbook();

        // Crear una hoja en el libro
        Sheet sheet = (Sheet) workbook.createSheet("Reporte de pedidos");

        Row row = sheet.createRow(0);

        for (int i = 0; i < cols.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(cols[i]);
        }

        List<Pedido> listaDatos = serviceP.listarPedido();

        // Crear filas y celdas con los datos
        int rowNum = 1;
        for (Pedido dato : listaDatos) {
            boolean estado = dato.getEstado();
            Date fecha = dato.getFecha();
            SimpleDateFormat formateador_fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaFormateada = formateador_fecha.format(fecha);
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(dato.getIdPedido());
            row.createCell(1).setCellValue(dato.getUsuario().getNombre());
            row.createCell(2).setCellValue(dato.getProveedor().getNombre());
            row.createCell(3).setCellValue(dato.getMetodopago());
            row.createCell(4).setCellValue(dato.getTipocomprobante());
            row.createCell(5).setCellValue(fechaFormateada);
            row.createCell(6).setCellValue(estado ? "Activo" : "Anulado");
            rowNum++;
        }

        workbook.write(baos);
        workbook.close();
        return new ByteArrayInputStream(baos.toByteArray());

    }

    @GetMapping("/misResportes")
    public ResponseEntity<InputStreamResource> generaReporte() throws Exception {
        ByteArrayInputStream stream = exportarExcel();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", "attachment; filename=ReportePedidos.xls");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }

    //Exportar pdf
    @GetMapping("/misReportes-pdf")
    public void exportar_pdf(HttpServletResponse response) {
        Document document = new Document(PageSize.A4.rotate());
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"Reporte de pedidos.pdf\"");

            PdfWriter write = PdfWriter.getInstance(document, response.getOutputStream());

            document.open();
            agregarCabecera(document);

            PdfPTable table = new PdfPTable(7);
            table.setWidthPercentage(100);
            String[] headers = {"ID de pedido", "Usuario", "Proveedor", "Metodo de pago", "Tipo comprobante", "Fecha solicitada", "Estado"};

            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            List<Pedido> subjectSentences = serviceP.listarPedido();
            for (Pedido dato : subjectSentences) {
                boolean estado = dato.getEstado();
                int identificador = dato.getIdPedido();
                Date fecha = dato.getFecha();
                SimpleDateFormat formateador_fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String fechaFormateada = formateador_fecha.format(fecha);
                table.addCell(new PdfPCell(new Phrase(String.valueOf(identificador))));
                table.addCell(new PdfPCell(new Phrase(dato.getUsuario().getNombre())));
                table.addCell(new PdfPCell(new Phrase(dato.getProveedor().getNombre())));
                table.addCell(new PdfPCell(new Phrase(dato.getMetodopago())));
                table.addCell(new PdfPCell(new Phrase(dato.getTipocomprobante())));
                table.addCell(new PdfPCell(new Phrase(fechaFormateada)));
                table.addCell(new PdfPCell(new Phrase(estado ? "Activo" : "Anulado")));
            }

            document.add(table);
            document.close();

        } catch (DocumentException | IOException e) {
            logger.log(java.util.logging.Level.SEVERE, "Error con exportar pdf", e);

        }
    }

    private void agregarCabecera(Document document) throws DocumentException {
        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setWidthPercentage(100);
        PdfPCell headerCell = new PdfPCell(new Paragraph("Reporte de Pedidos"));
        headerCell.setBackgroundColor(new BaseColor(0, 0, 0)); // Color negro
        headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        headerCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        headerCell.setPadding(10);
        headerCell.setBorder(0); // Sin bordes
        headerCell.setPhrase(new Paragraph("Reporte de Pedidos", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD, BaseColor.WHITE))); // Letras blancas

        headerTable.addCell(headerCell);
        document.add(headerTable);

    }
}
