package Grupo1.ProyectoSolWeb.Controller;

import Grupo1.ProyectoSolWeb.Model.Almacen;
import Grupo1.ProyectoSolWeb.Model.Producto;
import Grupo1.ProyectoSolWeb.Services.IAlmacenService;
import Grupo1.ProyectoSolWeb.Services.IProductoService;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/almacen/")
@Controller
public class ControladorAlmacen {

    String carpeta = "Almacen/";

    @Autowired
    private IAlmacenService service;
    private Logger logger = Logger.getLogger(getClass().getName());
    
    @PreAuthorize("hasAuthority('ROLE_Administrador')")
    @GetMapping("/nuevo")
    public String Nuevo(Model model) {
        return carpeta + "Almacen";
    }

    @GetMapping("/listar")
    public String mostrarFormularioNuevo(Model model) {
        List<Almacen> list = service.listar();
        model.addAttribute("almacenes", list);
        return carpeta + "listaAlmacen"; // Vista para el formulario de nuevo almacen
    }

    @PostMapping("/registrar") //localhost/registrar
    public String Registrar(
            @RequestParam("nom") String nom,
            @RequestParam("ubi") String ubi,
            @RequestParam("cant") String cantidad,
            Model model) {

        //Aqui será el proceso para registrar
        Almacen p = new Almacen();
        p.setNombre(nom);
        p.setUbicacion(ubi);
        p.setCantidad(cantidad);
        service.guardar(p);
        // Después de guardar el almacén, actualiza la lista de almacenes
        List<Almacen> list = service.listar();
        model.addAttribute("almacenes", list);
        return  mostrarFormularioNuevo(model);
    }

    //Para actualizar es necesario insertar los datos de la clase
    @PostMapping("/actualizar") //localhost/registrar
    public String actualizar(@RequestParam("idAlmacen") int cod,
            @RequestParam("nombre") String nom,
            @RequestParam("ubicacion") String ruc,
            @RequestParam("cantidad") String cant,
            Model model) {

        //Aqui será el proceso para registrar
        Almacen c = new Almacen();

        c.setIdAlmacen(cod);
        c.setNombre(nom);
        c.setUbicacion(ruc);
        c.setCantidad(cant);

        service.guardar(c);

        return mostrarFormularioNuevo(model);

    }

    @GetMapping("/eliminar")
    public String Eliminar(@RequestParam("cod") int cod,
            Model model) {
        service.eliminar(cod);
        return mostrarFormularioNuevo(model);
    }

    @GetMapping("/editar")
    public String editar(@RequestParam("cod") int cod,
            Model model) {
        Optional<Almacen> proveedor = service.consultar(cod);
        model.addAttribute("Almacen", proveedor);
        return carpeta + "editarAlmacen";
    }


    public ByteArrayInputStream exportarExcel() throws IOException {

        // Tu lógica para obtener los datos a exportar (por ejemplo, una lista)
        String cols[] = {"ID", "Inventario", "Ubicacion", "Capacidad Maxima"};

        // Crear un libro de Excel en memoria usando ByteArrayOutputStream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Workbook workbook = new HSSFWorkbook();

        // Crear una hoja en el libro
        Sheet sheet = (Sheet) workbook.createSheet("Reporte de almacen");

        Row row = sheet.createRow(0);

        for (int i = 0; i < cols.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(cols[i]);
        }

        List<Almacen> listaDatos = service.listar();

        // Crear filas y celdas con los datos
        int rowNum = 1;
        for (Almacen dato : listaDatos) {
            row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(dato.getIdAlmacen());
            row.createCell(1).setCellValue(dato.getNombre());
            row.createCell(2).setCellValue(dato.getUbicacion());
            row.createCell(3).setCellValue(dato.getCantidad());
            rowNum++;
        }

        workbook.write(baos);
        workbook.close();
        return new ByteArrayInputStream(baos.toByteArray());

    }
    
    @GetMapping("/misResportes")
    public ResponseEntity<InputStreamResource> generaReporte ()throws Exception{
        ByteArrayInputStream stream = exportarExcel(); 
        HttpHeaders headers = new HttpHeaders();
         
        headers.add("Content-Disposition","attachment; filename=ReporteAlmacen.xls");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(stream));
    }   
    
    
    //Exportar pdf
    @GetMapping("/misReportes-pdf")
    public void exportar_pdf(HttpServletResponse response) {
        Document document = new Document();
        try {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=\"Reporte de almacen.pdf\"");

            PdfWriter write = PdfWriter.getInstance(document, response.getOutputStream());

            document.open();
            agregarCabecera(document);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            String[] headers = {"ID de almacen", "Nombre", "Ubicacion", "Capacidad Máx"};

            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(header));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(cell);
            }

            List<Almacen> subjectSentences = service.listar();
            for (Almacen dato : subjectSentences) {
                int identificador = dato.getIdAlmacen();
                table.addCell(new PdfPCell(new Phrase(String.valueOf(identificador))));
                table.addCell(new PdfPCell(new Phrase(dato.getNombre())));
                table.addCell(new PdfPCell(new Phrase(dato.getUbicacion()))); 
                table.addCell(new PdfPCell(new Phrase(dato.getCantidad()))); 
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
        PdfPCell headerCell = new PdfPCell(new Paragraph("Reporte de almacen"));
        headerCell.setBackgroundColor(new BaseColor(0, 84, 179));
        headerCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        headerCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        headerCell.setPadding(10);
        headerCell.setBorder(0); // Sin bordes
        headerCell.setPhrase(new Paragraph("Reporte de almacen", FontFactory.getFont(FontFactory.HELVETICA, 15, Font.BOLD, BaseColor.WHITE))); // Letras blancas

        headerTable.addCell(headerCell);
        document.add(headerTable);

    }

}
