package tools;
import com.itextpdf.text.pdf.PdfReader;
import java.io.File;
import com.mysql.jdbc.Connection;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

public class ImprimirNotaegresoFull {
    
    private String idhc, nombrereport, codigo, servicio, version, destinohc;
    private Connection conexion;
    public File tempFile;
    
    public PdfReader ImprimirEgresoFull(){
        try {
            Map parametro = new HashMap();
            parametro.put("idHC",""+getIdhc());
            parametro.put("NameReport",""+getNombrereport());
            parametro.put("DestinoHC",""+getDestinohc());
            parametro.put("codigo",""+getCodigo());
            parametro.put("version",""+getVersion());
            parametro.put("servicio",""+getServicio());
            JasperPrint informe = JasperFillManager.fillReport(System.getProperty("user.dir")+"/Reportes/Negreso.jasper", parametro, getConnection());
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, informe);
            tempFile = File.createTempFile("Egreso",".pdf");
            exporter.setParameter(JRExporterParameter.OUTPUT_FILE,tempFile);
            exporter.exportReport();
            return new PdfReader(tempFile.getAbsolutePath());
        }catch (IOException e) {
            JOptionPane.showMessageDialog(null,"Error General Lanzando Reporte Descripcion: Ex00026 " +e.getMessage());
            return null;
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null,"Error General Lanzando Reporte Descripcion: Ex00026 " +e.getMessage());
            return null;
        }
    }

    public void setConnection(Connection conexion){
        this.conexion = conexion;
    }
    private Connection getConnection(){
        return conexion;
    }
    
    public String getIdhc() {
        return idhc;
    }

    public void setIdhc(String idhc) {
        this.idhc = idhc;
    }

    public String getNombrereport() {
        return nombrereport;
    }

    public void setNombrereport(String nombrereport) {
        this.nombrereport = nombrereport;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDestinohc() {
        return destinohc;
    }

    public void setDestinohc(String destinohc) {
        this.destinohc = destinohc;
    } 
}
