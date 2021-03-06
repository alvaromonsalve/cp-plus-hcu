
package atencionurgencia.ingreso;

import atencionurgencia.AtencionUrgencia;
import atencionurgencia.ListadoPacientes.addMedicamentos;
import entidades.HcuHistoriac2;
import entidades.InfoAntPersonales;
import entidades.InfoHistoriac;
import entidades.InfoPaciente;
import entidades.InfoPruebasComplement;
import entidades.PypInfoAntecedentesg;
import entidades.StaticCie10;
import java.awt.Color;
import java.awt.Event;
import java.awt.HeadlessException;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import jpa.HcuHistoriac2JpaController;
import jpa.InfoAntPersonalesJpaController;
import jpa.InfoHistoriacJpaController;
import jpa.InfoPruebasComplementJpaController;
import jpa.PypInfoAntecedentesgJpaController;
import jpa.StaticCie10JpaController;
import jpa.exceptions.NonexistentEntityException;
import other.*;
import tools.Funciones;
import tools.myStringsFunctions;

/**
 *
 * @author Alvaro Monsalve
 */
public class HC extends javax.swing.JPanel {

    // <editor-fold desc="Declaracion">
    private cie10 cie = null;
    FormPersonas personas = null;
    public InfoPaciente infopaciente = null;
    public InfoHistoriac infohistoriac = null;
//    public InfoAdmision infoadmision = null;
//    private InfoHcExpfisica infoexploracion = null;
//    private InfoHcExpfisicaJpaController infohsfisicoJPA=null;
    private StaticCie10 staticCie10 = null;
    private StaticCie10JpaController staticcie = null;
    private InfoPruebasComplement infoPruebasComplement = null;
    private final EntityManagerFactory factory;
    private InfoHistoriacJpaController infohistoriaJPA = null;
    private InfoPruebasComplementJpaController infoPruebasComplementJPA = null;
    public int idDiag1 = 1, idDiag2 = 1, idDiag3 = 1, idDiag4 = 1, idDiag5 = 1;
    public DefaultTableModel modeloAyudDiag, modDestroyAyudDiag;
    private final addMedicamentos trat = null;
//    public int finalizar = 0;
    public Boolean edite = false;
    private final Object dato[] = null;
    private final String tipoAyudaDiag[] = {"LABORATORIO", "IMAGEN", "ENDOSCOPIA", "ANATOMIA PATOLOGICA", "ELECTROGRAMAS", "ESTUDIOS ALERGOLÓGICOS"};
    private List<InfoPruebasComplement> listaPruebas = null;
    private final String s = System.getProperty("file.separator");
    private InfoAntPersonalesJpaController antPersonalesJPA = null;
//    private InfoAntPersonales antPersonales;
    public pTratMedic pMedic = null;
    public pTratPConsultDiag pConsultDiag = null;
    public pTratLaboratorio pLaboratorio = null;
    public pTratImagenologia pImagenologia = null;
    public pTratQuirurgico pQuirurgico = null;
    public pTratMasProcedimientos pProcedimientos = null;
    public pTratInterconsulta pInterconsulta0 = null, pInterconsulta1 = null, pInterconsulta2 = null, pInterconsulta3 = null, pInterconsulta4 = null;
    public pTratOtrasInterconsultas pOtrasInterconsultas = null;
    public pTratMedidaGeneral pMedidaGeneral = null;
    public pAnexo3 pAnexo31 = null;
//    public Ftriaje ftriaje = null;
    private HcuDestino hcuDestino = null;
    final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000; //Milisegundos al día 
    public HcuHistoriac2JpaController hcuHistoriac2JpaC = null;
    public boolean auditoria=false;
    private PypInfoAntecedentesgJpaController piajc=null;
    private PypInfoAntecedentesg pia=null;
    private HcuHistoriac2 historiac2;
    private InfoAntPersonales antPersonales;
    
    // </editor-fold>

    public HC(EntityManagerFactory factory) {
        initComponents();
        TablaAyudDiag();
        inicio();
        InputMap map2 = jTextArea10.getInputMap(JTextArea.WHEN_FOCUSED);
        map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        this.factory = factory;
        infohistoriaJPA = new InfoHistoriacJpaController(factory);
        hcuHistoriac2JpaC = new HcuHistoriac2JpaController(factory);
    }

    private void inicio() {
        jpCentro.removeAll();
        jpMotivoC.setBounds(0, 0, 584, 472);
        jpCentro.add(jpMotivoC);
        jpMotivoC.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
    }

    private Integer getSelectionNivelTriage() {
        if (jRadioButton1.isSelected()) {
            return 0;
        } else if (jRadioButton2.isSelected()) {
            return 1;
        } else if (jRadioButton3.isSelected()) {
            return 2;
        } else {
            return 3;
        }
    }

    public void setSelectionNivelTriage(int var) {
        switch (var) {
            case 0:
                jRadioButton1.setSelected(true);
                break;
            case 1:
                jRadioButton2.setSelected(true);
                break;
            case 2:
                jRadioButton3.setSelected(true);
                break;
            case 3:
                jRadioButton4.setSelected(true);
                break;
        }
    }

    private DefaultTableModel getModAyudaDiag() {
        try {
            return (new DefaultTableModel(
                    null, new String[]{"imagen", "iamgen2", "archivo", "file", "tipo", "ruta", "bd"}) {
                        Class[] types = new Class[]{
                            javax.swing.JLabel.class,
                            javax.swing.JLabel.class,
                            java.lang.String.class,
                            java.io.File.class,
                            java.lang.String.class,
                            java.lang.String.class,
                            java.lang.String.class
                        };

                        boolean[] canEdit = new boolean[]{
                            false, false, false, false, false, false, false
                        };

                        @Override
                        public Class getColumnClass(int columnIndex) {
                            return types[columnIndex];
                        }

                        @Override
                        public boolean isCellEditable(int rowIndex, int colIndex) {
                            return canEdit[colIndex];
                        }
                    });
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "10071:\n" + ex.getMessage(), HC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
    }

    private void TablaAyudDiag() {
        try {
            modeloAyudDiag = getModAyudaDiag();
            modDestroyAyudDiag = getModAyudaDiag();
            jtbTratamiento4.setModel(modeloAyudDiag);
            jtbTratamiento4.getTableHeader().setReorderingAllowed(false);
            jtbTratamiento4.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            Funciones.setSizeColumnas(jtbTratamiento4, new int[]{0, 1, 2}, new int[]{20, 20, 330});
            Funciones.setOcultarColumnas(jtbTratamiento4, new int[]{3, 5, 6});
            jtbTratamiento4.setDefaultRenderer(Object.class, new tools.IconCellRenderer());
            jtbTratamiento4.setTableHeader(null);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "10052:\n" + ex.getMessage(), HC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void viewHCinForm(InfoHistoriac ih, HcuHistoriac2 hh, PypInfoAntecedentesg pia){
        this.infohistoriac = ih;
        this.historiac2 = hh;
        this.pia = pia;
        this.setHistoryC();
        this.setFisicExplorer();
        this.setHelpDiag();
    }

//    public void CrearHistoriaC(){
////        saveHistoryClinic();
////        saveFisicExplorer();
////        saveHelpDiag();
////        infohistoriac = infohistoriaJPA.findInfoHistoriac(this.infohistoriac.getId());
//        if (pImagenologia != null) {
//            pImagenologia.saveChanges(factory, infohistoriac);
//        }
//        if (pLaboratorio != null) {
//            pLaboratorio.saveChanges(factory, infohistoriac);
//        }
//        if (pProcedimientos != null) {
//            pProcedimientos.saveChanges(factory, infohistoriac);
//        }
//        if (pConsultDiag != null) {
//            pConsultDiag.saveChanges(factory, infohistoriac);
//        }
//        if (pQuirurgico != null) {
//            pQuirurgico.saveChanges(factory, infohistoriac);
//        }
//        if (pInterconsulta0 != null) {
//            pInterconsulta0.saveChanges(infohistoriac);
//        }
//        if (pInterconsulta1 != null) {
//            pInterconsulta1.saveChanges(infohistoriac);
//        }
//        if (pInterconsulta2 != null) {
//            pInterconsulta2.saveChanges(infohistoriac);
//        }
//        if (pInterconsulta3 != null) {
//            pInterconsulta3.saveChanges(infohistoriac);
//        }
//        if (pInterconsulta4 != null) {
//            pInterconsulta4.saveChanges(infohistoriac);
//        }
//        if (pOtrasInterconsultas != null) {
//            pOtrasInterconsultas.saveChanges(infohistoriac);
//        }
//        if (pMedidaGeneral != null) {
//            pMedidaGeneral.saveChanges(factory, infohistoriac);
//        }
//        if (pMedic != null) {
//            pMedic.saveChanges(factory, infohistoriac);
//        }
//    }

    public void saveHistoryClinic() {
        if (jComboBox1.getSelectedIndex() > -1) {
            infohistoriac.setCausaExterna(jComboBox1.getSelectedItem().toString());
        } else {
            infohistoriac.setCausaExterna("ENFERMEDAD GENERAL O COMÚN");
        }
        infohistoriac.setMotivoConsulta(jTextArea10.getText().toUpperCase());
        infohistoriac.setNivelTriaje(getSelectionNivelTriage());
        infohistoriac.setAlergias(jTextArea11.getText().toUpperCase());
        infohistoriac.setIngresosPrevios(jTextArea12.getText().toUpperCase());
        infohistoriac.setTraumatismos(jTextArea22.getText().toUpperCase());
        infohistoriac.setTratamientos(jTextArea23.getText().toUpperCase());
        infohistoriac.setSituacionBasal(jTextArea24.getText().toUpperCase());
        infohistoriac.setHta(jCheckBox1.isSelected());
        infohistoriac.setDm(jCheckBox2.isSelected());
        infohistoriac.setDislipidemia(jCheckBox3.isSelected());
        infohistoriac.setTabaco(jCheckBox7.isSelected());
        infohistoriac.setAlcohol(jCheckBox8.isSelected());
        infohistoriac.setDroga(jCheckBox9.isSelected());
        infohistoriac.setOtrosHabitos(jTextArea7.getText().toUpperCase());
        infohistoriac.setDescHdd(jTextArea5.getText().toUpperCase());
        infohistoriac.setAntFamiliar(jTextArea25.getText().toUpperCase());
        infohistoriac.setEnfermedadActual(jTextArea13.getText().toUpperCase());
        infohistoriac.setDiagnostico(idDiag1);
        infohistoriac.setDiagnostico2(idDiag2);
        infohistoriac.setDiagnostico3(idDiag3);
        infohistoriac.setDiagnostico4(idDiag4);
        infohistoriac.setDiagnostico5(idDiag5);
        infohistoriac.setHallazgo(jTextArea19.getText().toUpperCase());
        if (hcuDestino != null) {
            infohistoriac.setDestino(hcuDestino.saveChanges());
            if (infohistoriac.getDestino().equals("DOMICILIO")) {
                infohistoriac.setEstado(2);
            }else if(infohistoriac.getDestino().equals("CONSULTA EXTERNA (CITA PRIORITARIA)")){
                infohistoriac.setEstado(3);
            }else if(infohistoriac.getDestino().equals("OBSERVACION")){
                infohistoriac.setEstado(1);
            }else if(infohistoriac.getDestino().equals("HOSPITALIZACION")){
                infohistoriac.setEstado(1);
            }
        }
        infohistoriac.setIdConfigdecripcionlogin(AtencionUrgencia.configdecripcionlogin);
        try {
            Date hoy = new Date();
            long diferencia = (hoy.getTime() - historiac2.getFIngreso().getTime());// MILLSECS_PER_DAY;
            if (diferencia > 900000) {
                diferencia = 900000;
            }
            historiac2.setTiempoConsulta((int) diferencia);
            if(jDateChooser1.getDate()==null){
                historiac2.setFum(new SimpleDateFormat("yyyy-MM-dd").parse("0001-01-01"));
            }else{
                historiac2.setFum(jDateChooser1.getDate());
            }
            historiac2.setNAbortos(jTextField19.getText());
            historiac2.setNCesarias(jTextField17.getText());
            historiac2.setNGestas(jTextField20.getText());
            historiac2.setNPartos(jTextField18.getText());
            historiac2.setObsAntGine(jTextArea26.getText().toUpperCase());
            hcuHistoriac2JpaC.edit(historiac2);
            infohistoriac.getInfoHcExpfisica().setTa(jTextField1.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setT(jTextField8.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setTam(jTextField2.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setSao2(jTextField7.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setFc(jTextField3.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setPvc("");
            infohistoriac.getInfoHcExpfisica().setFr(jTextField4.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setPic(jTextField5.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setPeso(jTextField9.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setTalla(jTextField10.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setOtros(jTextArea2.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setAspectogeneral(jTextArea3.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setCara(jTextArea4.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setCardio(jTextArea6.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setRespiratorio(jTextArea8.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setGastro(jTextArea9.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setRenal(jTextArea14.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setHemato(jTextArea15.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setEndo(jTextArea16.getText().toUpperCase());
            infohistoriac.getInfoHcExpfisica().setOsteo(jTextArea17.getText().toUpperCase());
            AtencionUrgencia.panelindex.infohsfisicoJPA.edit(infohistoriac.getInfoHcExpfisica());
            this.SaveAntPersonales();
            if (pImagenologia != null) {
            pImagenologia.saveChanges(factory, infohistoriac);
        }
        if (pLaboratorio != null) {
            pLaboratorio.saveChanges(factory, infohistoriac);
        }
        if (pProcedimientos != null) {
            pProcedimientos.saveChanges(factory, infohistoriac);
        }
        if (pConsultDiag != null) {
            pConsultDiag.saveChanges(factory, infohistoriac);
        }
        if (pQuirurgico != null) {
            pQuirurgico.saveChanges(factory, infohistoriac);
        }
        if (pInterconsulta0 != null) {
            pInterconsulta0.saveChanges(infohistoriac);
        }
        if (pInterconsulta1 != null) {
            pInterconsulta1.saveChanges(infohistoriac);
        }
        if (pInterconsulta2 != null) {
            pInterconsulta2.saveChanges(infohistoriac);
        }
        if (pInterconsulta3 != null) {
            pInterconsulta3.saveChanges(infohistoriac);
        }
        if (pInterconsulta4 != null) {
            pInterconsulta4.saveChanges(infohistoriac);
        }
        if (pOtrasInterconsultas != null) {
            pOtrasInterconsultas.saveChanges(infohistoriac);
        }
        if (pMedidaGeneral != null) {
            pMedidaGeneral.saveChanges(factory, infohistoriac);
        }
        if (pMedic != null) {
            pMedic.saveChanges(factory, infohistoriac);
        }
            infohistoriaJPA.edit(this.infohistoriac);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "10055:\n" + ex.getMessage()+" "+ex.getLocalizedMessage(), HC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public HcuHistoriac2 FindHcuHistoriac2(InfoHistoriac ihc){
        EntityManager em = hcuHistoriac2JpaC.getEntityManager();
        try {
            return (HcuHistoriac2) em.createQuery("SELECT h FROM HcuHistoriac2 h WHERE h.idInfoHistoriac = :hc")
            .setParameter("hc", ihc.getId())
            .setHint("javax.persistence.cache.storeMode", "REFRESH")
            .getSingleResult();
        }catch(Exception e){
            return null;
        }        
        finally {
            em.close();
        }
   }

//    public void DatosAntPersonales() {
//        if (antPersonalesJPA == null) {
//            antPersonalesJPA = new InfoAntPersonalesJpaController(factory);
//            piajc = new PypInfoAntecedentesgJpaController(factory);
//        }
//        antPersonales = antPersonalesJPA.findInfoAntPersonalesIDPac(infopaciente);
//        pia = findPiaIDPac(infopaciente);
//        if (antPersonales == null) {
//            antPersonales = new InfoAntPersonales();
//            antPersonales.setIdPaciente(infopaciente.getId());
//            antPersonales.setAlcohol(false);
//            antPersonales.setAlergias("NINGUNO");
//            antPersonales.setAntFamiliares("NINGUNO");
//            antPersonales.setDescHdd("");
//            antPersonales.setDislipidemia(false);
//            antPersonales.setDm(false);
//            antPersonales.setDroga(false);
//            antPersonales.setHta(false);
//            antPersonales.setIngresosPrevios("NINGUNO");
//            antPersonales.setOtrosHabitos("NINGUNO");
//            antPersonales.setSituacionBasal("NINGUNO");
//            antPersonales.setTabaco(false);
//            antPersonales.setTratamientos("NINGUNO");
//            antPersonales.setTraumatismos("NINGUNO");
//            antPersonalesJPA.create(antPersonales);
//        } else {
//            jTextArea11.setText(antPersonales.getAlergias());
//            jTextArea11.setCaretPosition(0);
//            jTextArea12.setText(antPersonales.getIngresosPrevios());
//            jTextArea12.setCaretPosition(0);
//            jTextArea22.setText(antPersonales.getTraumatismos());
//            jTextArea22.setCaretPosition(0);
//            jTextArea23.setText(antPersonales.getTratamientos());
//            jTextArea23.setCaretPosition(0);
//            jTextArea24.setText(antPersonales.getSituacionBasal());
//            jTextArea24.setCaretPosition(0);
//            jCheckBox1.setSelected(antPersonales.getHta());
//            jCheckBox2.setSelected(antPersonales.getDm());
//            jCheckBox3.setSelected(antPersonales.getDislipidemia());
//            jCheckBox7.setSelected(antPersonales.getTabaco());
//            jCheckBox8.setSelected(antPersonales.getAlcohol());
//            jCheckBox9.setSelected(antPersonales.getDroga());
//            jTextArea7.setText(antPersonales.getOtrosHabitos());
//            jTextArea7.setCaretPosition(0);
//            jTextArea5.setText(antPersonales.getDescHdd());
//            jTextArea5.setCaretPosition(0);
//            jTextArea25.setText(antPersonales.getAntFamiliares());
//            jTextArea25.setCaretPosition(0);
//            //muestra de la cajas de texto
//        }
//        if(pia==null){
//            pia = new PypInfoAntecedentesg();
//            pia.setInfoPaciente(infopaciente);
//            pia.setAbortos(jTextField19.getText());
//            pia.setCesareas(jTextField17.getText());
//            pia.setFum(jDateChooser1.getDate());
//            pia.setPartos(jTextField18.getText());
//            pia.setGestas(jTextField20.getText());
//            pia.setObservaciones(jTextArea26.getText().toUpperCase());  
//            piajc.create(pia);
//        }else{
//            jTextArea26.setText(pia.getObservaciones());
//            jTextField20.setText(pia.getGestas());
//            jTextField18.setText(pia.getPartos());
//            jTextField17.setText(pia.getCesareas());
//            jTextField19.setText(pia.getAbortos());
//            if(pia.getFum()!=null) jDateChooser1.setDate(pia.getFum());            
//        }
//    }
    
    public PypInfoAntecedentesg findPiaIDPac(InfoPaciente ip){
        EntityManager em = piajc.getEntityManager();
        try {
            List results = em.createQuery("SELECT p FROM PypInfoAntecedentesg p WHERE p.infoPaciente = :ip")
                    .setParameter("ip", ip)
                    .setHint("javax.persistence.cache.storeMode", "REFRESH")
                    .getResultList();
                    if (results.isEmpty()) return null;
                    else return (PypInfoAntecedentesg) results.get(0);
        }catch(Exception ex){
            return null;
        } finally {
            em.close();
        }
    }

    private void SaveAntPersonales() {
        if(piajc==null) piajc = new PypInfoAntecedentesgJpaController(factory);
        if(antPersonalesJPA==null)antPersonalesJPA = new InfoAntPersonalesJpaController(factory);
        antPersonales = AtencionUrgencia.panelindex.antPersonalesJPA.findInfoAntPersonalesIDPac(infohistoriac.getIdInfoAdmision().getIdDatosPersonales());  
        antPersonales.setAlcohol(jCheckBox8.isSelected());
        antPersonales.setAlergias(jTextArea11.getText().toUpperCase());
        antPersonales.setAntFamiliares(jTextArea25.getText().toUpperCase());
        antPersonales.setDescHdd(jTextArea5.getText().toUpperCase());
        antPersonales.setDislipidemia(jCheckBox3.isSelected());
        antPersonales.setDm(jCheckBox2.isSelected());
        antPersonales.setDroga(jCheckBox9.isSelected());
        antPersonales.setHta(jCheckBox1.isSelected());
        antPersonales.setIngresosPrevios(jTextArea12.getText().toUpperCase());
        antPersonales.setOtrosHabitos(jTextArea7.getText().toUpperCase());
        antPersonales.setSituacionBasal(jTextArea24.getText().toUpperCase());
        antPersonales.setTabaco(jCheckBox7.isSelected());
        antPersonales.setTratamientos(jTextArea23.getText().toUpperCase());
        antPersonales.setTraumatismos(jTextArea22.getText().toUpperCase());
            pia.setAbortos(jTextField19.getText());
            pia.setCesareas(jTextField17.getText());
            pia.setFum(jDateChooser1.getDate());
            pia.setPartos(jTextField18.getText());
            pia.setGestas(jTextField20.getText());
            pia.setObservaciones(jTextArea26.getText().toUpperCase());  
        try {
            piajc.edit(pia);
             antPersonalesJPA.edit(antPersonales);            
        } catch (NonexistentEntityException ex) {
            JOptionPane.showMessageDialog(null, "10063:\n" + ex.getMessage(), HC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "10064:\n" + ex.getMessage(), HC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
    }

//    public void viewClinicHistory(InfoHistoriac infoHistoriac) {
//        this.infohistoriac = infoHistoriac;
//        edite = true;
//        auditoria=false;
//        setHistoryC();
//        setFisicExplorer();
//        setHelpDiag();
//    }

    private void setHistoryC() {
        jlbNombrePaciente.setText(infohistoriac.getIdInfoAdmision().getIdDatosPersonales().getNombre1() + " " + 
                infohistoriac.getIdInfoAdmision().getIdDatosPersonales().getApellido1() + " [" + 
                infohistoriac.getIdInfoAdmision().getIdDatosPersonales().getNumDoc() + "]     [" + 
                infohistoriac.getIdInfoAdmision().getIdEntidadAdmision().getNombreEntidad() + "]");
        jTextArea10.setText(infohistoriac.getMotivoConsulta());
        jTextArea10.setCaretPosition(0);
        jComboBox1.setSelectedItem(infohistoriac.getCausaExterna());
        this.setSelectionNivelTriage(infohistoriac.getNivelTriaje());
        jTextArea11.setText(infohistoriac.getAlergias());
        jTextArea11.setCaretPosition(0);
        jTextArea12.setText(infohistoriac.getIngresosPrevios());
        jTextArea12.setCaretPosition(0);
        jTextArea22.setText(infohistoriac.getTraumatismos());
        jTextArea22.setCaretPosition(0);
        jTextArea23.setText(infohistoriac.getTratamientos());
        jTextArea23.setCaretPosition(0);
        jTextArea24.setText(infohistoriac.getSituacionBasal());
        jTextArea24.setCaretPosition(0);
        jCheckBox1.setSelected(infohistoriac.getHta());
        jCheckBox2.setSelected(infohistoriac.getDm());
        jCheckBox3.setSelected(infohistoriac.getDislipidemia());
        jCheckBox7.setSelected(infohistoriac.getTabaco());
        jCheckBox8.setSelected(infohistoriac.getAlcohol());
        jCheckBox9.setSelected(infohistoriac.getDroga());
        jTextArea7.setText(infohistoriac.getOtrosHabitos());
        jTextArea7.setCaretPosition(0);
        jTextArea5.setText(infohistoriac.getDescHdd());
        jTextArea5.setCaretPosition(0);
        jTextArea13.setText(infohistoriac.getEnfermedadActual());
        jTextArea13.setCaretPosition(0);
        jTextArea25.setText(infohistoriac.getAntFamiliar());
        jTextArea25.setCaretPosition(0);
        if (staticcie == null) {
            staticcie = new StaticCie10JpaController(factory);
        }
        if (infohistoriac.getDiagnostico() != 0 && infohistoriac.getDiagnostico() != 1) {
            staticCie10 = staticcie.findStaticCie10(infohistoriac.getDiagnostico());
            jTextField11.setText("[" + staticCie10.getCodigo() + "] " + staticCie10.getDescripcion());
            jTextField11.setCaretPosition(0);
            jTextArea12.setToolTipText(myStringsFunctions.stringToDIVstring(jTextArea12.getText()));
            idDiag1 = infohistoriac.getDiagnostico();
        }
        if (infohistoriac.getDiagnostico2() != 0 && infohistoriac.getDiagnostico2() != 1) {
            staticCie10 = staticcie.findStaticCie10(infohistoriac.getDiagnostico2());
            jTextField12.setText("[" + staticCie10.getCodigo() + "] " + staticCie10.getDescripcion());
            jTextField12.setCaretPosition(0);
            jTextArea12.setToolTipText(myStringsFunctions.stringToDIVstring(jTextArea12.getText()));
            idDiag2 = infohistoriac.getDiagnostico2();
        }
        if (infohistoriac.getDiagnostico3() != 0 && infohistoriac.getDiagnostico3() != 1) {
            staticCie10 = staticcie.findStaticCie10(infohistoriac.getDiagnostico3());
            jTextField13.setText("[" + staticCie10.getCodigo() + "] " + staticCie10.getDescripcion());
            jTextField13.setCaretPosition(0);
            jTextArea13.setToolTipText(myStringsFunctions.stringToDIVstring(jTextArea13.getText()));
            idDiag3 = infohistoriac.getDiagnostico3();
        }
        if (infohistoriac.getDiagnostico4() != 0 && infohistoriac.getDiagnostico4() != 1) {
            staticCie10 = staticcie.findStaticCie10(infohistoriac.getDiagnostico4());
            jTextField14.setText("[" + staticCie10.getCodigo() + "] " + staticCie10.getDescripcion());
            jTextField14.setCaretPosition(0);
            jTextArea14.setToolTipText(myStringsFunctions.stringToDIVstring(jTextArea14.getText()));
            idDiag4 = infohistoriac.getDiagnostico4();
        }
        if (infohistoriac.getDiagnostico5() != 0 && infohistoriac.getDiagnostico5() != 1) {
            staticCie10 = staticcie.findStaticCie10(infohistoriac.getDiagnostico5());
            jTextField15.setText("[" + staticCie10.getCodigo() + "] " + staticCie10.getDescripcion());
            jTextField15.setCaretPosition(0);
            jTextArea15.setToolTipText(myStringsFunctions.stringToDIVstring(jTextArea15.getText()));
            idDiag5 = infohistoriac.getDiagnostico5();
        }
        jTextArea19.setText(infohistoriac.getHallazgo());
        jDateChooser1.setDate(null);
    }

    private void setFisicExplorer() {
        jTextField1.setText(infohistoriac.getInfoHcExpfisica().getTa());
        jTextField1.setCaretPosition(0);
        jTextField8.setText(infohistoriac.getInfoHcExpfisica().getT());
        jTextField8.setCaretPosition(0);
        jTextField2.setText(infohistoriac.getInfoHcExpfisica().getTam());
        jTextField2.setCaretPosition(0);
        jTextField7.setText(infohistoriac.getInfoHcExpfisica().getSao2());
        jTextField7.setCaretPosition(0);
        jTextField3.setText(infohistoriac.getInfoHcExpfisica().getFc());
        jTextField3.setCaretPosition(0);
        jTextField6.setText("");
        jTextField6.setCaretPosition(0);
        jTextField4.setText(infohistoriac.getInfoHcExpfisica().getFr());
        jTextField4.setCaretPosition(0);
        jTextField5.setText(infohistoriac.getInfoHcExpfisica().getPic());
        jTextField5.setCaretPosition(0);
        jTextField9.setText(infohistoriac.getInfoHcExpfisica().getPeso());
        jTextField9.setCaretPosition(0);
        jTextField10.setText(infohistoriac.getInfoHcExpfisica().getTalla());
        jTextField10.setCaretPosition(0);
        jTextArea2.setText(infohistoriac.getInfoHcExpfisica().getOtros());
        jTextArea2.setCaretPosition(0);
        jTextArea3.setText(infohistoriac.getInfoHcExpfisica().getAspectogeneral());
        jTextArea3.setCaretPosition(0);
        jTextArea4.setText(infohistoriac.getInfoHcExpfisica().getCara());
        jTextArea4.setCaretPosition(0);
        jTextArea6.setText(infohistoriac.getInfoHcExpfisica().getCardio());
        jTextArea6.setCaretPosition(0);
        jTextArea8.setText(infohistoriac.getInfoHcExpfisica().getRespiratorio());
        jTextArea8.setCaretPosition(0);
        jTextArea9.setText(infohistoriac.getInfoHcExpfisica().getGastro());
        jTextArea9.setCaretPosition(0);
        jTextArea14.setText(infohistoriac.getInfoHcExpfisica().getRenal());
        jTextArea14.setCaretPosition(0);
        jTextArea15.setText(infohistoriac.getInfoHcExpfisica().getHemato());
        jTextArea15.setCaretPosition(0);
        jTextArea16.setText(infohistoriac.getInfoHcExpfisica().getEndo());
        jTextArea16.setCaretPosition(0);
        jTextArea17.setText(infohistoriac.getInfoHcExpfisica().getOsteo());
        jTextArea17.setCaretPosition(0);
    }

//    private void saveFisicExplorer() {
//        if (infoexploracion == null) {
//            infoexploracion = new InfoHcExpfisica();
//        }
//        infoexploracion.setIdInfohistoriac(infohistoriac);
//        infoexploracion.setTa(jTextField1.getText().toUpperCase());
//        infoexploracion.setT(jTextField8.getText().toUpperCase());
//        infoexploracion.setTam(jTextField2.getText().toUpperCase());
//        infoexploracion.setSao2(jTextField7.getText().toUpperCase());
//        infoexploracion.setFc(jTextField3.getText().toUpperCase());
//        infoexploracion.setPvc("");
//        infoexploracion.setFr(jTextField4.getText().toUpperCase());
//        infoexploracion.setPic(jTextField5.getText().toUpperCase());
//        infoexploracion.setPeso(jTextField9.getText().toUpperCase());
//        infoexploracion.setTalla(jTextField10.getText().toUpperCase());
//        infoexploracion.setOtros(jTextArea2.getText().toUpperCase());
//        infoexploracion.setAspectogeneral(jTextArea3.getText().toUpperCase());
//        infoexploracion.setCara(jTextArea4.getText().toUpperCase());
//        infoexploracion.setCardio(jTextArea6.getText().toUpperCase());
//        infoexploracion.setRespiratorio(jTextArea8.getText().toUpperCase());
//        infoexploracion.setGastro(jTextArea9.getText().toUpperCase());
//        infoexploracion.setRenal(jTextArea14.getText().toUpperCase());
//        infoexploracion.setHemato(jTextArea15.getText().toUpperCase());
//        infoexploracion.setEndo(jTextArea16.getText().toUpperCase());
//        infoexploracion.setOsteo(jTextArea17.getText().toUpperCase());
//        Boolean active = false;
//        if (infohsfisicoJPA == null) {
//            infohsfisicoJPA = new InfoHcExpfisicaJpaController(factory);
//            if (edite==false) {
//                infohsfisicoJPA.create(infoexploracion);
//                edite=true;
//            } else {
//                active = true;
//            }
//        } else {
//            active = true;
//        }
//        if (active) {
//            try {
//                infohsfisicoJPA.edit(infoexploracion);
//            } catch (NonexistentEntityException ex) {
//                JOptionPane.showMessageDialog(null, "10056:\n" + ex.getMessage(), HC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
//            } catch (Exception ex) {
//                JOptionPane.showMessageDialog(null, "10057:\n" + ex.getMessage(), HC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
//            }
//        }
//    }

    private void setHelpDiag() {
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/Delete16x16.png"));
        ImageIcon icon2 = new ImageIcon(ClassLoader.getSystemResource("images/download.png"));
        File file = null;
        try {
            file = File.createTempFile("temporal", null);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "10065:\n" + ex.getMessage(), HC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "10066:\n" + ex.getMessage(), HC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
        listaPruebas = infohistoriac.getInfoPruebasComplements();
        for (int i = 0; i < listaPruebas.size(); i++) {
            modeloAyudDiag.addRow(dato);
            modeloAyudDiag.setValueAt(new JLabel(icon), i, 0);
            modeloAyudDiag.setValueAt(new JLabel(icon2), i, 1);
            modeloAyudDiag.setValueAt(listaPruebas.get(i).getNombre(), i, 2);
            modeloAyudDiag.setValueAt(file, i, 3);
            modeloAyudDiag.setValueAt(listaPruebas.get(i).getTipo(), i, 4);
            modeloAyudDiag.setValueAt(ReturnPathdiagHelpSaveFile(listaPruebas.get(i).getTipo()), i, 5);
            modeloAyudDiag.setValueAt("1", i, 6);
        }
        //descargar archivos
    }

    private void saveHelpDiag() {
        if (infoPruebasComplementJPA == null) {//verifico si no existe la instancia de la persistencia para crearla
            infoPruebasComplementJPA = new InfoPruebasComplementJpaController(factory);
        }
        if (modDestroyAyudDiag.getRowCount() > 0) {
            Boolean existe = false;
            int id = 0;
            for (int i = 0; i < modDestroyAyudDiag.getRowCount(); i++) {
                if (((String) modDestroyAyudDiag.getValueAt(i, 6)).equals("1")) {//comprobamos si es registro antiguo
                    for (InfoPruebasComplement infopruebas : infohistoriac.getInfoPruebasComplements()) {//recorremos los registros en la hc
                        if ((((String) modDestroyAyudDiag.getValueAt(i, 5)
                                + System.getProperty("file.separator")
                                + (String) modDestroyAyudDiag.getValueAt(i, 2))).equals(infopruebas.getRuta())) {//verificamos coincidencias
                            existe = true;
                            id = infopruebas.getId();
                            break;
                        } else {
                            existe = false;
                        }
                    }
                    if (existe) {
                        try {
                            infoPruebasComplementJPA.destroy(id);
                            Funciones.fileDelete((String) modDestroyAyudDiag.getValueAt(i, 5)
                                    + System.getProperty("file.separator") + (String) modDestroyAyudDiag.getValueAt(i, 2), (String) modDestroyAyudDiag.getValueAt(i, 5), (String) modDestroyAyudDiag.getValueAt(i, 2));
                        } catch (NonexistentEntityException ex) {
                            JOptionPane.showMessageDialog(null, "10058:\n" + ex.getMessage(), HC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "10059:\n" + ex.getMessage(), HC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            }
        } else {
            //no hay archivos por eliminar
        }
        for (int i = 0; i < modDestroyAyudDiag.getRowCount(); i++) {
            modDestroyAyudDiag.removeRow(i);
        }
        if (modeloAyudDiag.getRowCount() > 0) {//verificamos si existen archivos en el modelo de muestra
            for (int i = 0; i < modeloAyudDiag.getRowCount(); i++) {//recorremos el modelo de muestra
                if (((String) modeloAyudDiag.getValueAt(i, 6)).equals("0")) {//comprobamos si esta recien añadido
                    if (infoPruebasComplement == null) {
                        infoPruebasComplement = new InfoPruebasComplement();
                    }
                    infoPruebasComplement.setIdInfohistoriac(infohistoriac);
                    infoPruebasComplement.setNombre((String) modeloAyudDiag.getValueAt(i, 2));
                    infoPruebasComplement.setTipo((String) modeloAyudDiag.getValueAt(i, 4));
                    infoPruebasComplement.setRuta((String) modeloAyudDiag.getValueAt(i, 5)
                            + System.getProperty("file.separator") + (String) modeloAyudDiag.getValueAt(i, 2));
                    infoPruebasComplementJPA.create(infoPruebasComplement);
                    Funciones.fileUpload(((File) modeloAyudDiag.getValueAt(i, 3)).getAbsolutePath(), (String) modeloAyudDiag.getValueAt(i, 5), (String) modeloAyudDiag.getValueAt(i, 2));
                    modeloAyudDiag.setValueAt("1", i, 6);
                    infoPruebasComplement = null;
                } else {
                    //como existe el registro no hacemos nada
                }
            }
        }
    }

    public void cerrarPanel() {
        Funciones.setLabelInfo("GUARDANDO...");
//        CrearHistoriaC();
//        SaveAntPersonales();
        atencionurgencia.AtencionUrgencia.panelindex.jpContainer.removeAll();
        atencionurgencia.AtencionUrgencia.panelindex.jpContainer.validate();
        atencionurgencia.AtencionUrgencia.panelindex.jpContainer.repaint();
        Funciones.setLabelInfo("DATOS GUARDADOS");
    }

    /**
     * @param tipo
     * @return "numero documento/año/mes/id admision/tipo de archivo"
     */
    private String ReturnPathdiagHelpSaveFile(String tipo) {
        Calendar ahoraCal = Calendar.getInstance();
        ahoraCal.setTime(infohistoriac.getIdInfoAdmision().getFechaIngreso());
        String year = String.valueOf(ahoraCal.get(Calendar.YEAR));
        String month = String.valueOf(ahoraCal.get(Calendar.MONTH));
        return infohistoriac.getIdInfoAdmision().getIdDatosPersonales().getNumDoc() + s + year + s + month + s + infohistoriac.getIdInfoAdmision().getId() + s + tipo;
    }

    private Boolean getValidaFirma() {
        boolean f = false;
        try {
            String sFile = this.infohistoriac.getIdConfigdecripcionlogin().getRuta_firma();
            File sFile1 = null;
            if (sFile == null) {
                JOptionPane.showMessageDialog(this, "Errores leyendo archivo de firma del usuario.");
            } else {
                sFile1 = new File(sFile);
                if (sFile1.exists()) {
                    f = true;
                } else {
                    JOptionPane.showMessageDialog(this, "Errores leyendo archivo de firma del usuario.");
                }
            }

        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, "10116:\n" + e.getMessage(), HC.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
        return f;
    }

    private boolean getCamposObligatoriosVacios() {
        boolean retorno = false;
        List<String> mensaje = new ArrayList<String>();
        String mensajeT = "";
        if (jComboBox1.getSelectedIndex() == -1) {
            mensaje.add("*Origen de la enfermedad o accidente*");
            retorno = true;
        }
        if (jTextArea10.getText().isEmpty()) {
            mensaje.add("*Motivo de Consulta*");
            retorno = true;
        }
        if (jTextArea13.getText().isEmpty()) {
            mensaje.add("*Enfermedad Actual*");
            retorno = true;
        }
        if (jTextArea3.getText().isEmpty()) {
            mensaje.add("*Aspectos Generales*");
            retorno = true;
        }
        if (jTextField11.getText().isEmpty()) {
            mensaje.add("*Diagnostico Medico*");
            retorno = true;
        }
        if (getValidPanels(pMedic) == false && getValidPanels(pConsultDiag) == false
                && getValidPanels(pImagenologia) == false && getValidPanels(pLaboratorio) == false
                && getValidPanels(pQuirurgico) == false && getValidPanels(pProcedimientos) == false
                && getValidPanels(pOtrasInterconsultas) == false && getValidPanels(pInterconsulta0) == false
                && getValidPanels(pInterconsulta1) == false && getValidPanels(pInterconsulta2) == false
                && getValidPanels(pInterconsulta3) == false && getValidPanels(pInterconsulta4) == false
                && getValidPanels(pMedidaGeneral) == false) {
            mensaje.add("*Tratamientos e Indicaciones*");
            retorno = true;
        }

        for (String men : mensaje) {
            mensajeT += men + "\n";
        }

        if (!mensajeT.equals("")) {
            JOptionPane.showMessageDialog(null, "Los siguientes campos no han sido diligenciados: \n" + mensajeT, "Campos Obligatorios", JOptionPane.DEFAULT_OPTION);
        }
        return retorno;
    }

    private boolean getValidPanels(JPanel jp) {
        if (jp != null) {
            if (jp instanceof pTratMedic) {
                if (((pTratMedic) jp).estadoTablas() == true) {
                    return true;
                }
            } else if (jp instanceof pTratPConsultDiag) {
                if (((pTratPConsultDiag) jp).estadoTablas() == true) {
                    return true;
                }
            } else if (jp instanceof pTratImagenologia) {
                if (((pTratImagenologia) jp).estadoTablas() == true) {
                    return true;
                }
            } else if (jp instanceof pTratLaboratorio) {
                if (((pTratLaboratorio) jp).estadoTablas() == true) {
                    return true;
                }
            } else if (jp instanceof pTratQuirurgico) {
                if (((pTratQuirurgico) jp).estadoTablas() == true) {
                    return true;
                }
            } else if (jp instanceof pTratMasProcedimientos) {
                if (((pTratMasProcedimientos) jp).estadoTablas() == true) {
                    return true;
                }
            } else if (jp instanceof pTratOtrasInterconsultas) {
                if (((pTratOtrasInterconsultas) jp).estadoTablas() == true) {
                    return true;
                }
            } else if (jp instanceof pTratInterconsulta) {
                if (((pTratInterconsulta) jp).estadoTablas() == true) {
                    return true;
                }
            } else if (jp instanceof pTratMedidaGeneral) {
                if (((pTratMedidaGeneral) jp).estadoTablas() == true) {
                    return true;
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpMotivoC = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton1 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea10 = new javax.swing.JTextArea();
        jLabel37 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        buttonGroup1 = new javax.swing.ButtonGroup();
        jpAntPersonales = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel30 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea11 = new javax.swing.JTextArea();
        jPanel33 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextArea12 = new javax.swing.JTextArea();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane21 = new javax.swing.JScrollPane();
        jTextArea22 = new javax.swing.JTextArea();
        jPanel36 = new javax.swing.JPanel();
        jScrollPane22 = new javax.swing.JScrollPane();
        jTextArea23 = new javax.swing.JTextArea();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel8 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel39 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        jPanel9 = new javax.swing.JPanel();
        jCheckBox7 = new javax.swing.JCheckBox();
        jCheckBox8 = new javax.swing.JCheckBox();
        jCheckBox9 = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTextArea7 = new javax.swing.JTextArea();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane23 = new javax.swing.JScrollPane();
        jTextArea24 = new javax.swing.JTextArea();
        jPanel20 = new javax.swing.JPanel();
        jScrollPane25 = new javax.swing.JScrollPane();
        jTextArea25 = new javax.swing.JTextArea();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel37 = new javax.swing.JPanel();
        jScrollPane24 = new javax.swing.JScrollPane();
        jTextArea26 = new javax.swing.JTextArea();
        jLabel22 = new javax.swing.JLabel();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel57 = new javax.swing.JLabel();
        jTextField17 = new javax.swing.JTextField();
        jLabel61 = new javax.swing.JLabel();
        jTextField18 = new javax.swing.JTextField();
        jLabel62 = new javax.swing.JLabel();
        jTextField19 = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        jTextField20 = new javax.swing.JTextField();
        jpEnfActual = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea13 = new javax.swing.JTextArea();
        jLabel40 = new javax.swing.JLabel();
        jpExpFisica = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextArea4 = new javax.swing.JTextArea();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTextArea8 = new javax.swing.JTextArea();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTextArea9 = new javax.swing.JTextArea();
        jPanel18 = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTextArea14 = new javax.swing.JTextArea();
        jPanel22 = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTextArea15 = new javax.swing.JTextArea();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTextArea16 = new javax.swing.JTextArea();
        jPanel24 = new javax.swing.JPanel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTextArea17 = new javax.swing.JTextArea();
        jLabel41 = new javax.swing.JLabel();
        jpPruebasDiag = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jScrollPane26 = new javax.swing.JScrollPane();
        jTextArea19 = new javax.swing.JTextArea();
        jScrollPane27 = new javax.swing.JScrollPane();
        jtbTratamiento4 = new javax.swing.JTable();
        jLabel34 = new javax.swing.JLabel();
        jTextField16 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jpDiagMedico = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jTextField13 = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jpTratamiento = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel71 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel82 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jPanel80 = new javax.swing.JPanel();
        jLabel165 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jFileChooser1 = new javax.swing.JFileChooser();
        jpCentro = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jlbNombrePaciente = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel44 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();

        jpMotivoC.setMaximumSize(new java.awt.Dimension(584, 472));
        jpMotivoC.setMinimumSize(new java.awt.Dimension(584, 472));
        jpMotivoC.setPreferredSize(new java.awt.Dimension(584, 472));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jPanel26.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Nivel de Triaje", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel26.setOpaque(false);

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton4.setText("CUATRO");
        jRadioButton4.setDoubleBuffered(true);
        jRadioButton4.setHideActionText(true);
        jRadioButton4.setOpaque(false);

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton3.setSelected(true);
        jRadioButton3.setText("TRES");
        jRadioButton3.setHideActionText(true);
        jRadioButton3.setOpaque(false);

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton2.setText("DOS");
        jRadioButton2.setOpaque(false);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jRadioButton1.setText("UNO");
        jRadioButton1.setOpaque(false);

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addGap(28, 28, 28)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jRadioButton3)
                .addGap(18, 18, 18)
                .addComponent(jRadioButton4)
                .addGap(23, 23, 23))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jScrollPane1.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea10.setColumns(20);
        jTextArea10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea10.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea10.setLineWrap(true);
        jTextArea10.setRows(5);
        jTextArea10.setDragEnabled(true);
        jTextArea10.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea10.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea10.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea10KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextArea10KeyTyped(evt);
            }
        });
        jScrollPane1.setViewportView(jTextArea10);

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel37.setText("MOTIVO DE CONSULTA");

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Origen de la Enfermedad o Accidente", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel5.setOpaque(false);

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PACIENTE SANO", "ENFERMEDAD GENERAL O COMÚN", "ENFERMEDAD PROFESIONAL U OCUPACIONAL", "ACCIDENTE DE TRABAJO", "ACCIDENTE NO DE TRABAJO O FUERA DEL TRABAJO", "ACCIDENTE DE TRANSITO" }));
        jComboBox1.setSelectedIndex(1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, 0, 294, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(117, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpMotivoCLayout = new javax.swing.GroupLayout(jpMotivoC);
        jpMotivoC.setLayout(jpMotivoCLayout);
        jpMotivoCLayout.setHorizontalGroup(
            jpMotivoCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpMotivoCLayout.setVerticalGroup(
            jpMotivoCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpAntPersonales.setMaximumSize(new java.awt.Dimension(584, 472));
        jpAntPersonales.setMinimumSize(new java.awt.Dimension(584, 472));
        jpAntPersonales.setPreferredSize(new java.awt.Dimension(584, 472));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel38.setText("ANTECEDENTES PERSONALES");

        jPanel30.setOpaque(false);

        jScrollPane2.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea11.setColumns(20);
        jTextArea11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea11.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea11.setLineWrap(true);
        jTextArea11.setRows(2);
        jTextArea11.setText("NINGUNO");
        jTextArea11.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea11.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea11.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea11FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea11FocusLost(evt);
            }
        });
        jTextArea11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea11KeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(jTextArea11);

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Alergias a Medicamentos", jPanel30);

        jPanel33.setOpaque(false);

        jScrollPane8.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane8.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane8.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea12.setColumns(20);
        jTextArea12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea12.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea12.setLineWrap(true);
        jTextArea12.setRows(2);
        jTextArea12.setText("NINGUNO");
        jTextArea12.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea12.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea12.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea12FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea12FocusLost(evt);
            }
        });
        jTextArea12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea12KeyPressed(evt);
            }
        });
        jScrollPane8.setViewportView(jTextArea12);

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Ingresos previos y cirugías", jPanel33);

        jPanel34.setOpaque(false);

        jScrollPane21.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane21.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane21.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea22.setColumns(20);
        jTextArea22.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea22.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea22.setLineWrap(true);
        jTextArea22.setRows(2);
        jTextArea22.setText("NINGUNO");
        jTextArea22.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea22.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea22.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea22FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea22FocusLost(evt);
            }
        });
        jTextArea22.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea22KeyPressed(evt);
            }
        });
        jScrollPane21.setViewportView(jTextArea22);

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane21, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Traumatismos, accidentes", jPanel34);

        jPanel36.setOpaque(false);

        jScrollPane22.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane22.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane22.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea23.setColumns(20);
        jTextArea23.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea23.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea23.setLineWrap(true);
        jTextArea23.setRows(2);
        jTextArea23.setText("NINGUNO");
        jTextArea23.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea23.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea23.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea23FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea23FocusLost(evt);
            }
        });
        jTextArea23.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea23KeyPressed(evt);
            }
        });
        jScrollPane22.setViewportView(jTextArea23);

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane22, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Tratamientos habituales", jPanel36);

        jPanel8.setOpaque(false);

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCheckBox1.setText("HTA");
        jCheckBox1.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox1.setOpaque(false);

        jCheckBox2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCheckBox2.setText("DM");
        jCheckBox2.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox2.setOpaque(false);

        jCheckBox3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCheckBox3.setText("DISLIPIDEMIA");
        jCheckBox3.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox3.setOpaque(false);

        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel39.setText("Observacion:");

        jTextArea5.setColumns(20);
        jTextArea5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea5.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea5.setLineWrap(true);
        jTextArea5.setRows(2);
        jTextArea5.setDoubleBuffered(true);
        jTextArea5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea5KeyPressed(evt);
            }
        });
        jScrollPane7.setViewportView(jTextArea5);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jCheckBox1)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox2)
                        .addGap(18, 18, 18)
                        .addComponent(jCheckBox3))
                    .addComponent(jLabel39))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel39)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("HTA, DM, DISLIPIDEMIA", jPanel8);

        jPanel9.setOpaque(false);

        jCheckBox7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCheckBox7.setText("Tabaco");
        jCheckBox7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox7.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox7.setOpaque(false);

        jCheckBox8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCheckBox8.setText("Alcohol");
        jCheckBox8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox8.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox8.setOpaque(false);

        jCheckBox9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jCheckBox9.setText("Drogas de Abuso");
        jCheckBox9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jCheckBox9.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jCheckBox9.setOpaque(false);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel9.setText("Otros:");

        jTextArea7.setColumns(20);
        jTextArea7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea7.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea7.setLineWrap(true);
        jTextArea7.setRows(1);
        jTextArea7.setDoubleBuffered(true);
        jTextArea7.setMaximumSize(new java.awt.Dimension(104, 30));
        jTextArea7.setMinimumSize(new java.awt.Dimension(104, 30));
        jTextArea7.setPreferredSize(new java.awt.Dimension(164, 30));
        jTextArea7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea7KeyPressed(evt);
            }
        });
        jScrollPane9.setViewportView(jTextArea7);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jCheckBox7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox9))
                    .addComponent(jLabel9))
                .addContainerGap(324, Short.MAX_VALUE))
            .addComponent(jScrollPane9, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox7)
                    .addComponent(jCheckBox8)
                    .addComponent(jCheckBox9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
        );

        jTabbedPane4.addTab("Hábitos Tóxicos", jPanel9);

        jPanel19.setOpaque(false);

        jScrollPane23.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane23.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane23.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea24.setColumns(20);
        jTextArea24.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea24.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea24.setLineWrap(true);
        jTextArea24.setRows(2);
        jTextArea24.setText("NINGUNO");
        jTextArea24.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea24.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea24.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea24FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea24FocusLost(evt);
            }
        });
        jTextArea24.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea24KeyPressed(evt);
            }
        });
        jScrollPane23.setViewportView(jTextArea24);

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane23, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("Situación basal (crónicos)", jPanel19);

        jPanel20.setOpaque(false);

        jScrollPane25.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane25.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane25.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea25.setColumns(20);
        jTextArea25.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea25.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea25.setLineWrap(true);
        jTextArea25.setRows(2);
        jTextArea25.setText("NINGUNO");
        jTextArea25.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea25.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea25.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea25FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea25FocusLost(evt);
            }
        });
        jTextArea25.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea25KeyPressed(evt);
            }
        });
        jScrollPane25.setViewportView(jTextArea25);

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane25, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("Antecedentes Familiares de Interes", jPanel20);

        jPanel37.setOpaque(false);

        jScrollPane24.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane24.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane24.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea26.setColumns(20);
        jTextArea26.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea26.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea26.setLineWrap(true);
        jTextArea26.setRows(2);
        jTextArea26.setText("NINGUNO");
        jTextArea26.setMaximumSize(new java.awt.Dimension(164, 40));
        jTextArea26.setMinimumSize(new java.awt.Dimension(164, 40));
        jTextArea26.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea26FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea26FocusLost(evt);
            }
        });
        jTextArea26.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea26KeyPressed(evt);
            }
        });
        jScrollPane24.setViewportView(jTextArea26);

        jLabel22.setText("FUM");
        jLabel22.setToolTipText("FECHA ULTIMA MENSTRUACION");

        jLabel57.setText("Cesarias");

        jLabel61.setText("Partos");

        jLabel62.setText("Abortos");

        jLabel63.setText("Gestaciones");

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane24, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel61, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel62, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel63, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel37Layout.createSequentialGroup()
                .addGap(0, 2, Short.MAX_VALUE)
                .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jDateChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel63, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel62, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel61, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel57, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane24, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane5.addTab("Ginecobstetricos", jPanel37);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane3)
                    .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane4)
                    .addComponent(jTabbedPane5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpAntPersonalesLayout = new javax.swing.GroupLayout(jpAntPersonales);
        jpAntPersonales.setLayout(jpAntPersonalesLayout);
        jpAntPersonalesLayout.setHorizontalGroup(
            jpAntPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpAntPersonalesLayout.setVerticalGroup(
            jpAntPersonalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpEnfActual.setMaximumSize(new java.awt.Dimension(584, 472));
        jpEnfActual.setMinimumSize(new java.awt.Dimension(584, 472));
        jpEnfActual.setPreferredSize(new java.awt.Dimension(584, 472));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane4.setMaximumSize(new java.awt.Dimension(164, 20));
        jScrollPane4.setMinimumSize(new java.awt.Dimension(164, 20));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(164, 20));

        jTextArea13.setColumns(20);
        jTextArea13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea13.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea13.setLineWrap(true);
        jTextArea13.setRows(5);
        jTextArea13.setMaximumSize(new java.awt.Dimension(164, 60));
        jTextArea13.setMinimumSize(new java.awt.Dimension(164, 60));
        jScrollPane4.setViewportView(jTextArea13);

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel40.setText("ENFERMEDAD ACTUAL");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 564, Short.MAX_VALUE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(156, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpEnfActualLayout = new javax.swing.GroupLayout(jpEnfActual);
        jpEnfActual.setLayout(jpEnfActualLayout);
        jpEnfActualLayout.setHorizontalGroup(
            jpEnfActualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpEnfActualLayout.setVerticalGroup(
            jpEnfActualLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpExpFisica.setMaximumSize(new java.awt.Dimension(584, 472));
        jpExpFisica.setMinimumSize(new java.awt.Dimension(584, 472));
        jpExpFisica.setPreferredSize(new java.awt.Dimension(584, 472));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setFocusable(false);
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        jPanel12.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText("TaS");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel2.setText("TAM");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel3.setText("FC");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel4.setText("FR");

        jTextField2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(0, 102, 255));

        jTextField3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(0, 102, 255));

        jTextField4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(0, 102, 255));

        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(0, 102, 255));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel5.setText("PIC");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel6.setText("TaD");

        jTextField6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(0, 102, 255));
        jTextField6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField6FocusLost(evt);
            }
        });

        jTextField7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(0, 102, 255));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel7.setText("SaO2");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel8.setText("T");

        jTextField8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(0, 102, 255));

        jPanel25.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "OTROS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel25.setOpaque(false);

        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea2.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(3);
        jTextArea2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea2KeyPressed(evt);
            }
        });
        jScrollPane5.setViewportView(jTextArea2);

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE)
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5)
        );

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel10.setText("mmHg");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel11.setText("mmHg");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel12.setText("x'");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel13.setText("x'");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel14.setText("°C");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel15.setText("%");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel16.setText("mmHg");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel17.setText("mmHg");

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel18.setText("PESO");

        jTextField9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField9.setForeground(new java.awt.Color(0, 102, 255));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel19.setText("Kg");

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel20.setText("TALLA");

        jTextField10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(0, 102, 255));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel21.setText("m");

        jTextField1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(0, 102, 255));
        jTextField1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextField1FocusLost(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                    .addComponent(jTextField4)
                    .addComponent(jTextField3)
                    .addComponent(jTextField2)
                    .addComponent(jTextField1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField8)
                    .addComponent(jTextField10)
                    .addComponent(jTextField7)
                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel17))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19)
                            .addComponent(jLabel20)
                            .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel10)
                            .addComponent(jLabel6)
                            .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel8)
                            .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel12)
                            .addComponent(jLabel7)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel13)))
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("CONSTANTES", jPanel12);

        jTextArea3.setColumns(20);
        jTextArea3.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea3.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea3.setLineWrap(true);
        jTextArea3.setRows(5);
        jTextArea3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea3KeyPressed(evt);
            }
        });
        jScrollPane6.setViewportView(jTextArea3);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("ASPECTO GENERAL", jPanel14);

        jTextArea4.setColumns(20);
        jTextArea4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea4.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea4.setLineWrap(true);
        jTextArea4.setRows(5);
        jTextArea4.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea4.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea4FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea4FocusLost(evt);
            }
        });
        jTextArea4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea4KeyPressed(evt);
            }
        });
        jScrollPane10.setViewportView(jTextArea4);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("NEUROLOGICO ORL Y CARA", jPanel15);

        jTextArea6.setColumns(20);
        jTextArea6.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea6.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea6.setLineWrap(true);
        jTextArea6.setRows(5);
        jTextArea6.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea6.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea6FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea6FocusLost(evt);
            }
        });
        jTextArea6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea6KeyPressed(evt);
            }
        });
        jScrollPane11.setViewportView(jTextArea6);

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("CARDIOVASCULAR", jPanel16);

        jTextArea8.setColumns(20);
        jTextArea8.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea8.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea8.setLineWrap(true);
        jTextArea8.setRows(5);
        jTextArea8.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea8.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea8FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea8FocusLost(evt);
            }
        });
        jTextArea8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea8KeyPressed(evt);
            }
        });
        jScrollPane12.setViewportView(jTextArea8);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("RESPIRATORIO", jPanel13);

        jTabbedPane2.setFocusable(false);
        jTabbedPane2.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        jTextArea9.setColumns(20);
        jTextArea9.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea9.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea9.setLineWrap(true);
        jTextArea9.setRows(5);
        jTextArea9.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea9.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea9FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea9FocusLost(evt);
            }
        });
        jTextArea9.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea9KeyPressed(evt);
            }
        });
        jScrollPane13.setViewportView(jTextArea9);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("GASTROINTESTINAL", jPanel17);

        jTextArea14.setColumns(20);
        jTextArea14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea14.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea14.setLineWrap(true);
        jTextArea14.setRows(5);
        jTextArea14.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea14.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea14FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea14FocusLost(evt);
            }
        });
        jTextArea14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea14KeyPressed(evt);
            }
        });
        jScrollPane14.setViewportView(jTextArea14);

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane14, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("GENITOURINARIO", jPanel18);

        jTextArea15.setColumns(20);
        jTextArea15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea15.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea15.setLineWrap(true);
        jTextArea15.setRows(5);
        jTextArea15.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea15.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea15FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea15FocusLost(evt);
            }
        });
        jTextArea15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea15KeyPressed(evt);
            }
        });
        jScrollPane15.setViewportView(jTextArea15);

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane15, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("HEMATOINFECCIOSO", jPanel22);

        jTextArea16.setColumns(20);
        jTextArea16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea16.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea16.setLineWrap(true);
        jTextArea16.setRows(5);
        jTextArea16.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea16.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea16FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea16FocusLost(evt);
            }
        });
        jTextArea16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea16KeyPressed(evt);
            }
        });
        jScrollPane16.setViewportView(jTextArea16);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane16, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("ENDOCRINO - METABOLICO", jPanel23);

        jTextArea17.setColumns(20);
        jTextArea17.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea17.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea17.setLineWrap(true);
        jTextArea17.setRows(5);
        jTextArea17.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        jTextArea17.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jTextArea17FocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jTextArea17FocusLost(evt);
            }
        });
        jTextArea17.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextArea17KeyPressed(evt);
            }
        });
        jScrollPane17.setViewportView(jTextArea17);

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
        );

        jTabbedPane2.addTab("OSTEOMUSCULAR Y PIEL", jPanel24);

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel41.setText("EXPLORACION FISICA");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
            .addComponent(jLabel41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2))
        );

        javax.swing.GroupLayout jpExpFisicaLayout = new javax.swing.GroupLayout(jpExpFisica);
        jpExpFisica.setLayout(jpExpFisicaLayout);
        jpExpFisicaLayout.setHorizontalGroup(
            jpExpFisicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpExpFisicaLayout.setVerticalGroup(
            jpExpFisicaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpPruebasDiag.setMaximumSize(new java.awt.Dimension(584, 472));
        jpPruebasDiag.setMinimumSize(new java.awt.Dimension(584, 472));
        jpPruebasDiag.setPreferredSize(new java.awt.Dimension(584, 472));

        jPanel27.setBackground(new java.awt.Color(255, 255, 255));

        jPanel32.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "HALLAZGOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 10))); // NOI18N
        jPanel32.setOpaque(false);

        jTextArea19.setColumns(20);
        jTextArea19.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextArea19.setForeground(new java.awt.Color(0, 102, 255));
        jTextArea19.setLineWrap(true);
        jTextArea19.setRows(5);
        jScrollPane26.setViewportView(jTextArea19);

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane26, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
        );

        jScrollPane27.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane27.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane27.setPreferredSize(new java.awt.Dimension(200, 200));

        jtbTratamiento4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jtbTratamiento4.setForeground(new java.awt.Color(0, 51, 255));
        jtbTratamiento4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jtbTratamiento4.setShowHorizontalLines(false);
        jtbTratamiento4.setShowVerticalLines(false);
        jtbTratamiento4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtbTratamiento4MouseClicked(evt);
            }
        });
        jScrollPane27.setViewportView(jtbTratamiento4);

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel34.setText("Adjuntar Documentos");

        jTextField16.setEditable(false);
        jTextField16.setBackground(new java.awt.Color(255, 255, 255));
        jTextField16.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField16.setForeground(new java.awt.Color(0, 102, 255));
        jTextField16.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel42.setText("PRUEBAS COMPLEMENTARIAS");

        jLabel70.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel70.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/001_38.png"))); // NOI18N
        jLabel70.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel70MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel27Layout = new javax.swing.GroupLayout(jPanel27);
        jPanel27.setLayout(jPanel27Layout);
        jPanel27Layout.setHorizontalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel27Layout.createSequentialGroup()
                        .addGap(0, 46, Short.MAX_VALUE)
                        .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel70))
                    .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel27Layout.setVerticalGroup(
            jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel27Layout.createSequentialGroup()
                .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel32, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel27Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(jLabel70))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane27, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jpPruebasDiagLayout = new javax.swing.GroupLayout(jpPruebasDiag);
        jpPruebasDiag.setLayout(jpPruebasDiagLayout);
        jpPruebasDiagLayout.setHorizontalGroup(
            jpPruebasDiagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpPruebasDiagLayout.setVerticalGroup(
            jpPruebasDiagLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpDiagMedico.setMaximumSize(new java.awt.Dimension(584, 472));
        jpDiagMedico.setMinimumSize(new java.awt.Dimension(584, 472));
        jpDiagMedico.setPreferredSize(new java.awt.Dimension(584, 472));

        jPanel28.setBackground(new java.awt.Color(255, 255, 255));
        jPanel28.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel28.setText("DIAGNOSTICO PRINCIPAL");

        jTextField11.setEditable(false);
        jTextField11.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField11.setForeground(new java.awt.Color(0, 102, 255));
        jTextField11.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField11KeyReleased(evt);
            }
        });

        jTextField12.setEditable(false);
        jTextField12.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField12.setForeground(new java.awt.Color(0, 102, 255));
        jTextField12.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField12KeyReleased(evt);
            }
        });

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel29.setText("DIAGNOSTICO RELACIONADO 1");

        jTextField13.setEditable(false);
        jTextField13.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField13.setForeground(new java.awt.Color(0, 102, 255));
        jTextField13.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField13KeyReleased(evt);
            }
        });

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel30.setText("DIAGNOSTICO RELACIONADO 2");

        jLabel31.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel31.setText("DIAGNOSTICO RELACIONADO 3");

        jTextField14.setEditable(false);
        jTextField14.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField14.setForeground(new java.awt.Color(0, 102, 255));
        jTextField14.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField14KeyReleased(evt);
            }
        });

        jTextField15.setEditable(false);
        jTextField15.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTextField15.setForeground(new java.awt.Color(0, 102, 255));
        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField15KeyReleased(evt);
            }
        });

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel32.setText("DIAGNOSTICO RELACIONADO 4");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel43.setText("DIAGNOSTICO MEDICO");

        jLabel64.setBackground(new java.awt.Color(255, 255, 255));
        jLabel64.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel64.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/001_38.png"))); // NOI18N
        jLabel64.setFocusable(false);
        jLabel64.setOpaque(true);
        jLabel64.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel64MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel64MouseReleased(evt);
            }
        });
        jLabel64.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel64MouseMoved(evt);
            }
        });

        jLabel65.setBackground(new java.awt.Color(255, 255, 255));
        jLabel65.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel65.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/001_38.png"))); // NOI18N
        jLabel65.setFocusable(false);
        jLabel65.setOpaque(true);
        jLabel65.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel65MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel65MouseReleased(evt);
            }
        });
        jLabel65.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel65MouseMoved(evt);
            }
        });

        jLabel66.setBackground(new java.awt.Color(255, 255, 255));
        jLabel66.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel66.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/001_38.png"))); // NOI18N
        jLabel66.setFocusable(false);
        jLabel66.setOpaque(true);
        jLabel66.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel66MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel66MouseReleased(evt);
            }
        });
        jLabel66.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel66MouseMoved(evt);
            }
        });

        jLabel67.setBackground(new java.awt.Color(255, 255, 255));
        jLabel67.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel67.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/001_38.png"))); // NOI18N
        jLabel67.setFocusable(false);
        jLabel67.setOpaque(true);
        jLabel67.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel67MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel67MouseReleased(evt);
            }
        });
        jLabel67.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel67MouseMoved(evt);
            }
        });

        jLabel68.setBackground(new java.awt.Color(255, 255, 255));
        jLabel68.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel68.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/001_38.png"))); // NOI18N
        jLabel68.setFocusable(false);
        jLabel68.setOpaque(true);
        jLabel68.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel68MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel68MouseReleased(evt);
            }
        });
        jLabel68.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel68MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout jPanel28Layout = new javax.swing.GroupLayout(jPanel28);
        jPanel28.setLayout(jPanel28Layout);
        jPanel28Layout.setHorizontalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel28Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(28, 28, 28))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(202, 202, 202))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(165, 165, 165))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                        .addGap(207, 207, 207))
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField14, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                                .addComponent(jTextField15))
                            .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField12, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                                .addComponent(jTextField13)
                                .addComponent(jTextField11)
                                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel66, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel68, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel67, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel64, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel65, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel28Layout.setVerticalGroup(
            jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel28Layout.createSequentialGroup()
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel64, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel30))
                    .addComponent(jLabel65, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel31))
                    .addComponent(jLabel66, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel28Layout.createSequentialGroup()
                        .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel32))
                    .addComponent(jLabel67, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel28Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(178, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jpDiagMedicoLayout = new javax.swing.GroupLayout(jpDiagMedico);
        jpDiagMedico.setLayout(jpDiagMedicoLayout);
        jpDiagMedicoLayout.setHorizontalGroup(
            jpDiagMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpDiagMedicoLayout.setVerticalGroup(
            jpDiagMedicoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jpTratamiento.setBackground(new java.awt.Color(255, 255, 255));
        jpTratamiento.setMaximumSize(new java.awt.Dimension(584, 472));
        jpTratamiento.setMinimumSize(new java.awt.Dimension(584, 472));
        jpTratamiento.setPreferredSize(new java.awt.Dimension(584, 472));

        jPanel1.setMaximumSize(new java.awt.Dimension(584, 472));
        jPanel1.setMinimumSize(new java.awt.Dimension(584, 472));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(584, 472));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel35.setText("TRTAMIENTO E INDICACIONES");

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setFocusable(false);
        jPanel35.setMaximumSize(new java.awt.Dimension(380, 420));
        jPanel35.setMinimumSize(new java.awt.Dimension(380, 420));
        jPanel35.setOpaque(false);
        jPanel35.setPreferredSize(new java.awt.Dimension(380, 420));

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 392, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(jPanel35, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel29Layout.createSequentialGroup()
                .addComponent(jPanel35, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(214, 223, 247), 1, true));
        jPanel4.setMaximumSize(new java.awt.Dimension(134, 45));
        jPanel4.setMinimumSize(new java.awt.Dimension(134, 45));
        jPanel4.setPreferredSize(new java.awt.Dimension(134, 45));

        jLabel71.setBackground(new java.awt.Color(214, 223, 247));
        jLabel71.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel71.setText("MEDIDAS GENERALES");
        jLabel71.setOpaque(true);

        jLabel72.setBackground(new java.awt.Color(255, 255, 255));
        jLabel72.setText("Medidas Generales");
        jLabel72.setOpaque(true);
        jLabel72.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel72MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel72MouseReleased(evt);
            }
        });
        jLabel72.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel72MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel71, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel72, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel71)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel72)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(214, 223, 247), 1, true));
        jPanel6.setMaximumSize(new java.awt.Dimension(134, 45));
        jPanel6.setMinimumSize(new java.awt.Dimension(134, 45));
        jPanel6.setPreferredSize(new java.awt.Dimension(134, 45));

        jLabel73.setBackground(new java.awt.Color(214, 223, 247));
        jLabel73.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel73.setText("MEDICAMENTOS");
        jLabel73.setOpaque(true);

        jLabel74.setBackground(new java.awt.Color(255, 255, 255));
        jLabel74.setText("Lista de Medicamentos");
        jLabel74.setOpaque(true);
        jLabel74.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel74MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel74MouseReleased(evt);
            }
        });
        jLabel74.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel74MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel73)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel74)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(214, 223, 247), 1, true));
        jPanel31.setMaximumSize(new java.awt.Dimension(144, 113));
        jPanel31.setMinimumSize(new java.awt.Dimension(144, 113));
        jPanel31.setPreferredSize(new java.awt.Dimension(144, 113));

        jLabel77.setBackground(new java.awt.Color(214, 223, 247));
        jLabel77.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel77.setText("PROCEDIMIENTOS");
        jLabel77.setOpaque(true);

        jLabel78.setBackground(new java.awt.Color(255, 255, 255));
        jLabel78.setText("Laboratorio Clinico");
        jLabel78.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel78.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jLabel78.setOpaque(true);
        jLabel78.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jLabel78.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel78MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel78MouseReleased(evt);
            }
        });
        jLabel78.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel78MouseMoved(evt);
            }
        });

        jLabel79.setBackground(new java.awt.Color(255, 255, 255));
        jLabel79.setText("Imagenologia");
        jLabel79.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel79.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jLabel79.setOpaque(true);
        jLabel79.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jLabel79.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel79MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel79MouseReleased(evt);
            }
        });
        jLabel79.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel79MouseMoved(evt);
            }
        });

        jLabel80.setBackground(new java.awt.Color(255, 255, 255));
        jLabel80.setText("Quirúrgicos");
        jLabel80.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel80.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jLabel80.setOpaque(true);
        jLabel80.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jLabel80.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel80MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel80MouseReleased(evt);
            }
        });
        jLabel80.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel80MouseMoved(evt);
            }
        });

        jLabel81.setBackground(new java.awt.Color(255, 255, 255));
        jLabel81.setText("Otros Procedimientos");
        jLabel81.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLabel81.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        jLabel81.setOpaque(true);
        jLabel81.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        jLabel81.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel81MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel81MouseReleased(evt);
            }
        });
        jLabel81.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel81MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel77, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel78, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel79, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel80, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel81, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel31Layout.createSequentialGroup()
                .addComponent(jLabel77)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel78)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel79)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel80)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel81)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(214, 223, 247), 1, true));
        jPanel38.setPreferredSize(new java.awt.Dimension(164, 159));

        jLabel82.setBackground(new java.awt.Color(214, 223, 247));
        jLabel82.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel82.setText("VALORACION ESPECIALISTA");
        jLabel82.setOpaque(true);

        jLabel48.setBackground(new java.awt.Color(255, 255, 255));
        jLabel48.setText("Cirugia General");
        jLabel48.setOpaque(true);
        jLabel48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel48MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel48MouseReleased(evt);
            }
        });
        jLabel48.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel48MouseMoved(evt);
            }
        });

        jLabel52.setBackground(new java.awt.Color(255, 255, 255));
        jLabel52.setText("Ginecología");
        jLabel52.setOpaque(true);
        jLabel52.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel52MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel52MouseReleased(evt);
            }
        });
        jLabel52.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel52MouseMoved(evt);
            }
        });

        jLabel53.setBackground(new java.awt.Color(255, 255, 255));
        jLabel53.setText("Medicina Interna");
        jLabel53.setOpaque(true);
        jLabel53.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel53MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel53MouseReleased(evt);
            }
        });
        jLabel53.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel53MouseMoved(evt);
            }
        });

        jLabel54.setBackground(new java.awt.Color(255, 255, 255));
        jLabel54.setText("Ortopedia");
        jLabel54.setOpaque(true);
        jLabel54.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel54MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel54MouseReleased(evt);
            }
        });
        jLabel54.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel54MouseMoved(evt);
            }
        });

        jLabel55.setBackground(new java.awt.Color(255, 255, 255));
        jLabel55.setText("Pediatria");
        jLabel55.setOpaque(true);
        jLabel55.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel55MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel55MouseReleased(evt);
            }
        });
        jLabel55.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel55MouseMoved(evt);
            }
        });

        jLabel56.setBackground(new java.awt.Color(255, 255, 255));
        jLabel56.setText("Otras Especialidades");
        jLabel56.setOpaque(true);
        jLabel56.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel56MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel56MouseReleased(evt);
            }
        });
        jLabel56.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel56MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout jPanel38Layout = new javax.swing.GroupLayout(jPanel38);
        jPanel38.setLayout(jPanel38Layout);
        jPanel38Layout.setHorizontalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel82, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel54, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel55, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel56, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel38Layout.setVerticalGroup(
            jPanel38Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel38Layout.createSequentialGroup()
                .addComponent(jLabel82)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel52)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel54)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel55)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel56)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel80.setBackground(new java.awt.Color(255, 255, 255));
        jPanel80.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(214, 223, 247), 1, true));
        jPanel80.setMaximumSize(new java.awt.Dimension(134, 45));
        jPanel80.setMinimumSize(new java.awt.Dimension(134, 45));
        jPanel80.setPreferredSize(new java.awt.Dimension(134, 45));

        jLabel165.setBackground(new java.awt.Color(214, 223, 247));
        jLabel165.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel165.setText("DESTINO");
        jLabel165.setOpaque(true);

        jLabel60.setBackground(new java.awt.Color(255, 255, 255));
        jLabel60.setText("Destino");
        jLabel60.setOpaque(true);
        jLabel60.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel60MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel60MouseReleased(evt);
            }
        });
        jLabel60.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel60MouseMoved(evt);
            }
        });

        javax.swing.GroupLayout jPanel80Layout = new javax.swing.GroupLayout(jPanel80);
        jPanel80.setLayout(jPanel80Layout);
        jPanel80Layout.setHorizontalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel165, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel80Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel60, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel80Layout.setVerticalGroup(
            jPanel80Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel80Layout.createSequentialGroup()
                .addComponent(jLabel165)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel60)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(jPanel31, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addComponent(jPanel38, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel80, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel29, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel31, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel38, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel80, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(14, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout jpTratamientoLayout = new javax.swing.GroupLayout(jpTratamiento);
        jpTratamiento.setLayout(jpTratamientoLayout);
        jpTratamientoLayout.setHorizontalGroup(
            jpTratamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpTratamientoLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jpTratamientoLayout.setVerticalGroup(
            jpTratamientoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setBackground(new java.awt.Color(255, 255, 255));
        setMaximumSize(new java.awt.Dimension(764, 514));
        setMinimumSize(new java.awt.Dimension(764, 514));
        setPreferredSize(new java.awt.Dimension(764, 514));

        jpCentro.setBackground(new java.awt.Color(204, 204, 255));
        jpCentro.setMaximumSize(new java.awt.Dimension(584, 472));
        jpCentro.setMinimumSize(new java.awt.Dimension(584, 472));
        jpCentro.setPreferredSize(new java.awt.Dimension(584, 472));

        javax.swing.GroupLayout jpCentroLayout = new javax.swing.GroupLayout(jpCentro);
        jpCentro.setLayout(jpCentroLayout);
        jpCentroLayout.setHorizontalGroup(
            jpCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
        );
        jpCentroLayout.setVerticalGroup(
            jpCentroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 472, Short.MAX_VALUE)
        );

        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel23.setText(" Antecedentes Personales");
        jLabel23.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(194, 224, 255)));
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel23.setOpaque(true);
        jLabel23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel23MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel23MouseReleased(evt);
            }
        });
        jLabel23.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel23MouseMoved(evt);
            }
        });

        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel24.setText(" Enfermedad Actual");
        jLabel24.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(194, 224, 255)));
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel24.setOpaque(true);
        jLabel24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel24MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel24MouseReleased(evt);
            }
        });
        jLabel24.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel24MouseMoved(evt);
            }
        });

        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel25.setText(" Exploracion Fisica");
        jLabel25.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(194, 224, 255)));
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel25.setOpaque(true);
        jLabel25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel25MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel25MouseReleased(evt);
            }
        });
        jLabel25.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel25MouseMoved(evt);
            }
        });

        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel26.setText(" Diagnostico Medico");
        jLabel26.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(194, 224, 255)));
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel26.setOpaque(true);
        jLabel26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel26MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel26MouseReleased(evt);
            }
        });
        jLabel26.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel26MouseMoved(evt);
            }
        });

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel27.setText(" Pruebas Complementarias");
        jLabel27.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(194, 224, 255)));
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel27.setOpaque(true);
        jLabel27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel27MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel27MouseReleased(evt);
            }
        });
        jLabel27.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel27MouseMoved(evt);
            }
        });

        jLabel33.setBackground(new java.awt.Color(255, 255, 255));
        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel33.setText(" Ordenes Medicas");
        jLabel33.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(194, 224, 255)));
        jLabel33.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jLabel33.setOpaque(true);
        jLabel33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel33MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel33MouseReleased(evt);
            }
        });
        jLabel33.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel33MouseMoved(evt);
            }
        });

        jlbNombrePaciente.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jlbNombrePaciente.setForeground(new java.awt.Color(0, 102, 255));
        jlbNombrePaciente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/user.png"))); // NOI18N
        jlbNombrePaciente.setText("NOMBRE");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/save_large.png"))); // NOI18N
        jButton1.setContentAreaFilled(false);
        jButton1.setFocusable(false);
        jButton1.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton1.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton1.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
        });
        jButton1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton1MouseMoved(evt);
            }
        });
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cerrar.png"))); // NOI18N
        jButton9.setBorderPainted(false);
        jButton9.setContentAreaFilled(false);
        jButton9.setFocusable(false);
        jButton9.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton9.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton9.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton9MouseExited(evt);
            }
        });
        jButton9.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton9MouseMoved(evt);
            }
        });
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/unlock.png"))); // NOI18N
        jButton10.setBorderPainted(false);
        jButton10.setContentAreaFilled(false);
        jButton10.setFocusable(false);
        jButton10.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton10.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton10.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton10MouseExited(evt);
            }
        });
        jButton10.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton10MouseMoved(evt);
            }
        });
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/print.png"))); // NOI18N
        jButton11.setBorderPainted(false);
        jButton11.setContentAreaFilled(false);
        jButton11.setFocusable(false);
        jButton11.setMaximumSize(new java.awt.Dimension(32, 32));
        jButton11.setMinimumSize(new java.awt.Dimension(32, 32));
        jButton11.setPreferredSize(new java.awt.Dimension(32, 32));
        jButton11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton11MouseExited(evt);
            }
        });
        jButton11.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jButton11MouseMoved(evt);
            }
        });
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jLabel44.setBackground(new java.awt.Color(255, 255, 255));
        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel44.setText(" Motivo de Consulta");
        jLabel44.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(194, 224, 255)));
        jLabel44.setOpaque(true);
        jLabel44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel44MouseExited(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel44MouseReleased(evt);
            }
        });
        jLabel44.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jLabel44MouseMoved(evt);
            }
        });

        jLabel69.setBackground(new java.awt.Color(255, 255, 255));
        jLabel69.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel69.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add32x32.png"))); // NOI18N
        jLabel69.setToolTipText("Ver mas datos del paciente");
        jLabel69.setOpaque(true);
        jLabel69.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jLabel69MouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel26, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel27, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(jpCentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jlbNombrePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 686, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jlbNombrePaciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel69, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jpCentro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(25, 25, 25))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jtbTratamiento4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtbTratamiento4MouseClicked
        if (SwingUtilities.isLeftMouseButton(evt)) {
            if (jtbTratamiento4.columnAtPoint(evt.getPoint()) == 0) {
                int confirmar = JOptionPane.showConfirmDialog(this, "¿Está seguro que desea eliminar este archivo de forma permanente?", "Eliminar archivo", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                if (confirmar == JOptionPane.YES_OPTION) {
                    int row = jtbTratamiento4.rowAtPoint(evt.getPoint());
                    modDestroyAyudDiag.addRow(new Object[]{modeloAyudDiag.getValueAt(row, 0), modeloAyudDiag.getValueAt(row, 1), modeloAyudDiag.getValueAt(row, 2), modeloAyudDiag.getValueAt(row, 3), modeloAyudDiag.getValueAt(row, 4), modeloAyudDiag.getValueAt(row, 5), modeloAyudDiag.getValueAt(row, 6)});
                    modeloAyudDiag.removeRow(row);
                }
            } else if (jtbTratamiento4.columnAtPoint(evt.getPoint()) == 1) {
                //verificar existencia del archivo en la bd o en la clase entidad
                if (((String) jtbTratamiento4.getValueAt(jtbTratamiento4.rowAtPoint(evt.getPoint()), 6))
                        .equals("1")) {
                    String path2 = (String) modeloAyudDiag.getValueAt(jtbTratamiento4.rowAtPoint(evt.getPoint()), 5)
                            + s + (String) modeloAyudDiag.getValueAt(jtbTratamiento4.rowAtPoint(evt.getPoint()), 2);
//                    String path3 = "Epicrisis.jasper";
                  Funciones.fileDownload(path2);
//                    Funciones.reportDownload("", path3);
                }
            }
        }
    }//GEN-LAST:event_jtbTratamiento4MouseClicked

    private void jTextField15KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyReleased
        if ("".equals(jTextField15.getText())) {
            idDiag5 = 1;
        }
    }//GEN-LAST:event_jTextField15KeyReleased

    private void jTextField14KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField14KeyReleased
        if ("".equals(jTextField14.getText())) {
            idDiag4 = 1;
        }
    }//GEN-LAST:event_jTextField14KeyReleased

    private void jTextField13KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField13KeyReleased
        if ("".equals(jTextField13.getText())) {
            idDiag3 = 1;
        }
    }//GEN-LAST:event_jTextField13KeyReleased

    private void jTextField12KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField12KeyReleased
        if ("".equals(jTextField12.getText())) {
            idDiag2 = 1;
        }
    }//GEN-LAST:event_jTextField12KeyReleased

    private void jTextField11KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField11KeyReleased
        if ("".equals(jTextField11.getText())) {
            idDiag1 = 1;
        }
    }//GEN-LAST:event_jTextField11KeyReleased

    private void jTextArea10KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea10KeyTyped
        if (evt.getKeyCode() == KeyEvent.VK_CONTROL) {
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea10KeyTyped

    private void jLabel48MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseExited
        Funciones.setLabelInfo();
        jLabel48.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel48MouseExited

    private void jLabel48MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseMoved
        Funciones.setLabelInfo("CIRUGIA GENERAL");
        jLabel48.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel48MouseMoved

    private void jLabel52MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseExited
        Funciones.setLabelInfo();
        jLabel52.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel52MouseExited

    private void jLabel52MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseMoved
        Funciones.setLabelInfo("GINECOLOGIA");
        jLabel52.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel52MouseMoved

    private void jLabel53MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseExited
        Funciones.setLabelInfo();
        jLabel53.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel53MouseExited

    private void jLabel53MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseMoved
        Funciones.setLabelInfo("MEDICINA INTERNA");
        jLabel53.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel53MouseMoved

    private void jLabel54MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseExited
        Funciones.setLabelInfo();
        jLabel54.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel54MouseExited

    private void jLabel54MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseMoved
        Funciones.setLabelInfo("ORTOPEDIA");
        jLabel54.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel54MouseMoved

    private void jLabel55MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseExited
        Funciones.setLabelInfo();
        jLabel55.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel55MouseExited

    private void jLabel55MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseMoved
        Funciones.setLabelInfo("PEDIATRIA");
        jLabel55.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel55MouseMoved

    private void jLabel56MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseExited
        Funciones.setLabelInfo();
        jLabel56.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel56MouseExited

    private void jLabel56MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseMoved
        Funciones.setLabelInfo("OTRAS ESPECIALIDADES");
        jLabel56.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel56MouseMoved

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Funciones.setLabelInfo("GUARDANDO...");
        this.saveHistoryClinic();
//        CrearHistoriaC();//guardo en hc
//        SaveAntPersonales();
        //guardo en antecedentes
        Funciones.setLabelInfo("DATOS GUARDADOS");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        AtencionUrgencia.panelindex.jpContainer.removeAll();
        AtencionUrgencia.panelindex.jpContainer.validate();
        AtencionUrgencia.panelindex.jpContainer.repaint();
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jLabel23MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseMoved
        jLabel23.setBackground(new java.awt.Color(194, 224, 255));
        Funciones.setLabelInfo("ANTECEDENTES PERSONALES");
    }//GEN-LAST:event_jLabel23MouseMoved

    private void jLabel24MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseMoved
        jLabel24.setBackground(new java.awt.Color(194, 224, 255));
        Funciones.setLabelInfo("ENFERMEDAD ACTUAL");
    }//GEN-LAST:event_jLabel24MouseMoved

    private void jLabel25MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseMoved
        jLabel25.setBackground(new java.awt.Color(194, 224, 255));
        Funciones.setLabelInfo("EXPLORACION FISICA");
    }//GEN-LAST:event_jLabel25MouseMoved

    private void jLabel27MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseMoved
        jLabel27.setBackground(new java.awt.Color(194, 224, 255));
        Funciones.setLabelInfo("PRUEBAS COMPLEMENTARIAS");
    }//GEN-LAST:event_jLabel27MouseMoved

    private void jLabel26MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseMoved
        jLabel26.setBackground(new java.awt.Color(194, 224, 255));
        Funciones.setLabelInfo("DIAGNOSTICO MEDICO");
    }//GEN-LAST:event_jLabel26MouseMoved

    private void jLabel33MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseMoved
        jLabel33.setBackground(new java.awt.Color(194, 224, 255));
        Funciones.setLabelInfo("TRATAMIENTO E INDICACIONES");
    }//GEN-LAST:event_jLabel33MouseMoved

    private void jLabel23MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseExited
        jLabel23.setBackground(new java.awt.Color(255, 255, 255));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel23MouseExited

    private void jLabel24MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseExited
        jLabel24.setBackground(new java.awt.Color(255, 255, 255));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel24MouseExited

    private void jLabel25MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseExited
        jLabel25.setBackground(new java.awt.Color(255, 255, 255));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel25MouseExited

    private void jLabel27MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseExited
        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel27MouseExited

    private void jLabel26MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseExited
        jLabel26.setBackground(new java.awt.Color(255, 255, 255));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel26MouseExited

    private void jLabel33MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseExited
        jLabel33.setBackground(new java.awt.Color(255, 255, 255));
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jLabel33MouseExited

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
//        if (getValidaFirma()) {
//            if (getCamposObligatoriosVacios() == false) {
//                Object[] objeto = {"Si", "No"};
//                int n = JOptionPane.showOptionDialog(this, "¿Desea Finalizar la Nota de Ingreso?", "Mensaje", JOptionPane.YES_NO_CANCEL_OPTION,
//                        JOptionPane.QUESTION_MESSAGE, null, objeto, objeto[1]);
//                if (n == 0) {
////                    finalizar = 1;
//                    CrearHistoriaC();//guardo en hc
////                    SaveAntPersonales();    
//                    
//                    jButton10.setEnabled(false);
//                    impresionesHC imp = new impresionesHC(factory);
//                    imp.setidHC(this.infohistoriac);
//                    imp.activarLinks();
//                    imp.setdestinoHc(this.infohistoriac.getDestino());
//                    imp.setLocationRelativeTo(null);
//                    imp.setNoValido(false);
//                    imp.setVisible(true);
//                } else {
////                    finalizar = 0;
//                    CrearHistoriaC();//guardo en hc
////                    SaveAntPersonales();
//                }
//            }
//        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton1MouseExited

    private void jButton10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton10MouseExited

    private void jButton9MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton9MouseExited

    private void jButton1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseMoved
        Funciones.setLabelInfo("GUARDAR");
    }//GEN-LAST:event_jButton1MouseMoved

    private void jButton10MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton10MouseMoved
        Funciones.setLabelInfo("GUARDAR Y FINALIZAR CONSULTA");
    }//GEN-LAST:event_jButton10MouseMoved

    private void jButton9MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton9MouseMoved
        Funciones.setLabelInfo("SALIR DE ATENCION DE URGENCIAS");
    }//GEN-LAST:event_jButton9MouseMoved

    private void jTextArea11FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea11FocusLost
        if (jTextArea11.getText().isEmpty()) {
            jTextArea11.setText("NINGUNO");
        }
    }//GEN-LAST:event_jTextArea11FocusLost

    private void jTextArea11FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea11FocusGained
        if ("NINGUNO".equals(jTextArea11.getText())) {
            jTextArea11.selectAll();
        }
    }//GEN-LAST:event_jTextArea11FocusGained

    private void jTextArea12FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea12FocusGained
        if ("NINGUNO".equals(jTextArea12.getText())) {
            jTextArea12.selectAll();
        }
    }//GEN-LAST:event_jTextArea12FocusGained

    private void jTextArea12FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea12FocusLost
        if (jTextArea12.getText().isEmpty()) {
            jTextArea12.setText("NINGUNO");
        }
    }//GEN-LAST:event_jTextArea12FocusLost

    private void jTextArea22FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea22FocusGained
        if ("NINGUNO".equals(jTextArea22.getText())) {
            jTextArea22.selectAll();
        }
    }//GEN-LAST:event_jTextArea22FocusGained

    private void jTextArea22FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea22FocusLost
        if (jTextArea22.getText().isEmpty()) {
            jTextArea22.setText("NINGUNO");
        }
    }//GEN-LAST:event_jTextArea22FocusLost

    private void jTextArea23FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea23FocusGained
        if ("NINGUNO".equals(jTextArea23.getText())) {
            jTextArea23.selectAll();
        }
    }//GEN-LAST:event_jTextArea23FocusGained

    private void jTextArea23FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea23FocusLost
        if (jTextArea23.getText().isEmpty()) {
            jTextArea23.setText("NINGUNO");
        }
    }//GEN-LAST:event_jTextArea23FocusLost

    private void jTextArea24FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea24FocusGained
        if ("NINGUNO".equals(jTextArea24.getText())) {
            jTextArea24.selectAll();
        }
    }//GEN-LAST:event_jTextArea24FocusGained

    private void jTextArea24FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea24FocusLost
        if (jTextArea24.getText().isEmpty()) {
            jTextArea24.setText("NINGUNO");
        }
    }//GEN-LAST:event_jTextArea24FocusLost

    private void jTextArea25FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea25FocusGained
        if ("NINGUNO".equals(jTextArea25.getText())) {
            jTextArea25.selectAll();
        }
    }//GEN-LAST:event_jTextArea25FocusGained

    private void jTextArea25FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea25FocusLost
        if (jTextArea25.getText().isEmpty()) {
            jTextArea25.setText("NINGUNO");
        }
    }//GEN-LAST:event_jTextArea25FocusLost

    private void jTextArea4FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea4FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea4.getText())) {
            jTextArea4.selectAll();
        }
    }//GEN-LAST:event_jTextArea4FocusGained

    private void jTextArea4FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea4FocusLost
        if (jTextArea4.getText().isEmpty()) {
            jTextArea4.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea4FocusLost

    private void jTextArea6FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea6FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea6.getText())) {
            jTextArea6.selectAll();
        }
    }//GEN-LAST:event_jTextArea6FocusGained

    private void jTextArea6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea6FocusLost
        if (jTextArea6.getText().isEmpty()) {
            jTextArea6.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea6FocusLost

    private void jTextArea8FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea8FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea8.getText())) {
            jTextArea8.selectAll();
        }
    }//GEN-LAST:event_jTextArea8FocusGained

    private void jTextArea8FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea8FocusLost
        if (jTextArea8.getText().isEmpty()) {
            jTextArea8.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea8FocusLost

    private void jTextArea9FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea9FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea9.getText())) {
            jTextArea9.selectAll();
        }
    }//GEN-LAST:event_jTextArea9FocusGained

    private void jTextArea9FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea9FocusLost
        if (jTextArea9.getText().isEmpty()) {
            jTextArea9.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea9FocusLost

    private void jTextArea14FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea14FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea14.getText())) {
            jTextArea14.selectAll();
        }
    }//GEN-LAST:event_jTextArea14FocusGained

    private void jTextArea14FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea14FocusLost
        if (jTextArea14.getText().isEmpty()) {
            jTextArea14.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea14FocusLost

    private void jTextArea15FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea15FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea15.getText())) {
            jTextArea15.selectAll();
        }
    }//GEN-LAST:event_jTextArea15FocusGained

    private void jTextArea15FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea15FocusLost
        if (jTextArea15.getText().isEmpty()) {
            jTextArea15.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea15FocusLost

    private void jTextArea16FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea16FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea16.getText())) {
            jTextArea16.selectAll();
        }
    }//GEN-LAST:event_jTextArea16FocusGained

    private void jTextArea16FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea16FocusLost
        if (jTextArea16.getText().isEmpty()) {
            jTextArea16.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea16FocusLost

    private void jTextArea17FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea17FocusGained
        if ("NO SE ENCUENTRAN DATOS RELEVANTES".equals(jTextArea17.getText())) {
            jTextArea17.selectAll();
        }
    }//GEN-LAST:event_jTextArea17FocusGained

    private void jTextArea17FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea17FocusLost
        if (jTextArea17.getText().isEmpty()) {
            jTextArea17.setText("NO SE ENCUENTRAN DATOS RELEVANTES");
        }
    }//GEN-LAST:event_jTextArea17FocusLost

    private void jTextArea2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea2KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea2.transferFocusBackward();
            } else {
                jTextArea2.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea2KeyPressed

    private void jTextArea10KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea10KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea10.transferFocusBackward();
            } else {
                jTextArea10.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea10KeyPressed

    private void jTextArea11KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea11KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea11.transferFocusBackward();
            } else {
                jTextArea11.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea11KeyPressed

    private void jTextArea12KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea12KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea12.transferFocusBackward();
            } else {
                jTextArea12.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea12KeyPressed

    private void jTextArea22KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea22KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea22.transferFocusBackward();
            } else {
                jTextArea22.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea22KeyPressed

    private void jTextArea23KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea23KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea23.transferFocusBackward();
            } else {
                jTextArea23.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea23KeyPressed

    private void jTextArea5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea5KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {
                jTextArea5.transferFocusBackward();
            } else {
                jTextArea5.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea5KeyPressed

    private void jTextArea7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea7KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea7.transferFocusBackward();
            } else {
                jTextArea7.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea7KeyPressed

    private void jTextArea24KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea24KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea24.transferFocusBackward();
            } else {
                jTextArea24.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea24KeyPressed

    private void jTextArea25KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea25KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea24.transferFocusBackward();
            } else {
                jTextArea24.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea25KeyPressed

    private void jTextArea9KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea9KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea9.transferFocusBackward();
            } else {
                jTextArea9.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea9KeyPressed

    private void jTextArea14KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea14KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea14.transferFocusBackward();
            } else {
                jTextArea14.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea14KeyPressed

    private void jTextArea15KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea15KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {
                jTextArea15.transferFocusBackward();
            } else {
                jTextArea15.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea15KeyPressed

    private void jTextArea16KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea16KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {
                jTextArea16.transferFocusBackward();
            } else {
                jTextArea16.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea16KeyPressed

    private void jTextArea17KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea17KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {
                jTextArea17.transferFocusBackward();
            } else {
                jTextArea17.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea17KeyPressed

    private void jTextArea3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea3KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea3.transferFocusBackward();
            } else {
                jTextArea3.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea3KeyPressed

    private void jTextArea4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea4KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea4.transferFocusBackward();
            } else {
                jTextArea4.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea4KeyPressed

    private void jTextArea6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea6KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea6.transferFocusBackward();
            } else {
                jTextArea6.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea6KeyPressed

    private void jTextArea8KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea8KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_TAB) {
            if (evt.getModifiers() > 0) {

                jTextArea8.transferFocusBackward();
            } else {
                jTextArea8.transferFocus();
            }
            evt.consume();
        }
    }//GEN-LAST:event_jTextArea8KeyPressed

    private void jButton11MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseExited
        Funciones.setLabelInfo();
    }//GEN-LAST:event_jButton11MouseExited

    private void jButton11MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton11MouseMoved
        Funciones.setLabelInfo("IMPRIMIR");
    }//GEN-LAST:event_jButton11MouseMoved

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        if (getValidaFirma()) {
            impresionesHC imp = new impresionesHC(factory);
            imp.setidHC(this.infohistoriac);
            imp.activarLinks();
            if(infohistoriac.getDestino()==null){
                imp.setdestinoHc("");
            }else{
                imp.setdestinoHc(infohistoriac.getDestino());
            }            
            imp.setLocationRelativeTo(null);
//            imp.setNoValido(true);
            imp.setVisible(true);
        }
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jLabel44MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseReleased
        jpCentro.removeAll();
        jpMotivoC.setBounds(0, 0, 584, 472);
        jpCentro.add(jpMotivoC);
        jpMotivoC.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
    }//GEN-LAST:event_jLabel44MouseReleased

    private void jLabel44MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseExited
        Funciones.setLabelInfo();
        jLabel44.setBackground(new java.awt.Color(255, 255, 255));//194, 224, 255
    }//GEN-LAST:event_jLabel44MouseExited

    private void jLabel44MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel44MouseMoved
        jLabel44.setBackground(new java.awt.Color(194, 224, 255));//194, 224, 255
        Funciones.setLabelInfo("MOTIVO DE CONSULTA");
    }//GEN-LAST:event_jLabel44MouseMoved

    private void jLabel23MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel23MouseReleased
        jpCentro.removeAll();
        jpAntPersonales.setBounds(0, 0, 584, 472);
        jpCentro.add(jpAntPersonales);
        jpAntPersonales.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
    }//GEN-LAST:event_jLabel23MouseReleased

    private void jLabel24MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel24MouseReleased
        jpCentro.removeAll();
        jpEnfActual.setBounds(0, 0, 584, 472);
        jpCentro.add(jpEnfActual);
        jpEnfActual.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
    }//GEN-LAST:event_jLabel24MouseReleased

    private void jLabel25MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel25MouseReleased
        jpCentro.removeAll();
        jpExpFisica.setBounds(0, 0, 584, 472);
        jpCentro.add(jpExpFisica);
        jpExpFisica.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
    }//GEN-LAST:event_jLabel25MouseReleased

    private void jLabel27MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel27MouseReleased
        jpCentro.removeAll();
        jpPruebasDiag.setBounds(0, 0, 584, 472);
        jpCentro.add(jpPruebasDiag);
        jpPruebasDiag.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
    }//GEN-LAST:event_jLabel27MouseReleased

    private void jLabel26MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel26MouseReleased
        jpCentro.removeAll();
        jpDiagMedico.setBounds(0, 0, 584, 472);
        jpCentro.add(jpDiagMedico);
        jpDiagMedico.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
    }//GEN-LAST:event_jLabel26MouseReleased

    private void jLabel33MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel33MouseReleased
        jpCentro.removeAll();
        jpTratamiento.setBounds(0, 0, 584, 472);
        jpCentro.add(jpTratamiento);
        jpTratamiento.setVisible(true);
        jpCentro.validate();
        jpCentro.repaint();
    }//GEN-LAST:event_jLabel33MouseReleased

    private void jLabel60MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel60MouseExited
        Funciones.setLabelInfo();
        jLabel60.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel60MouseExited

    private void jLabel60MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel60MouseMoved
        Funciones.setLabelInfo("DESTINO");
        jLabel60.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel60MouseMoved

    private void jLabel60MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel60MouseReleased
        if (hcuDestino == null) {
            hcuDestino = new HcuDestino(infohistoriac);
        }
        jPanel35.removeAll();
        hcuDestino.setBounds(0, 0, 380, 420);
        jPanel35.add(hcuDestino);
        hcuDestino.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel60MouseReleased

    private void jTextField6FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField6FocusLost
        if(!jTextField1.getText().isEmpty() && !jTextField6.getText().isEmpty()){
            String si = jTextField1.getText();
            String d = jTextField6.getText();
            Boolean numeric = true;
            for(int i=0;i<d.length();i++ ){
                if(!Character.isDigit(d.charAt(i))){
                    numeric = false;
                    jTextField6.setText("");
                    break;
                }
            }
            for(int i=0;i<si.length();i++ ){
                if(!Character.isDigit(si.charAt(i))){
                    numeric = false;
                    jTextField1.setText("");
                    break;
                }
            }
            if(numeric == true){
                float var = Integer.parseInt(d) + (Integer.parseInt(si)-Integer.parseInt(d))/3;                
                jTextField2.setText(String.format("%.2f",var));
            }
        }
    }//GEN-LAST:event_jTextField6FocusLost

    private void jTextField1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextField1FocusLost
        if(!jTextField1.getText().isEmpty() && !jTextField6.getText().isEmpty()){
            String si = jTextField1.getText();
            String d = jTextField6.getText();
            Boolean numeric = true;
            for(int i=0;i<d.length();i++ ){
                if(!Character.isDigit(d.charAt(i))){
                    numeric = false;
                    jTextField6.setText("");
                    break;
                }
            }
            for(int i=0;i<si.length();i++ ){
                if(!Character.isDigit(si.charAt(i))){
                    numeric = false;
                    jTextField1.setText("");
                    break;
                }
            }
            if(numeric == true){
                float var = Integer.parseInt(d) + (Integer.parseInt(si)-Integer.parseInt(d))/3;                
                jTextField2.setText(String.format("%.2f",var));
            }
        }
    }//GEN-LAST:event_jTextField1FocusLost

    private void jTextArea26FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea26FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea26FocusGained

    private void jTextArea26FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jTextArea26FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea26FocusLost

    private void jTextArea26KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextArea26KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextArea26KeyPressed

    private void jLabel64MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel64MouseReleased
                if (cie == null) {
            cie = new cie10();
            cie.setLocationRelativeTo(this);
            cie.setVisible(true);
        } else {
            cie.setVisible(true);
        }
        cie.diag = 1;
    }//GEN-LAST:event_jLabel64MouseReleased

    private void jLabel65MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel65MouseReleased
               if (cie == null) {
            cie = new cie10();
            cie.setLocationRelativeTo(this);
            cie.setVisible(true);
        } else {
            cie.setVisible(true);
        }
        cie.diag = 2;
    }//GEN-LAST:event_jLabel65MouseReleased

    private void jLabel66MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel66MouseReleased
        if (cie == null) {
            cie = new cie10();
            cie.setLocationRelativeTo(this);
            cie.setVisible(true);
        } else {
            cie.setVisible(true);
        }
        cie.diag = 3;
    }//GEN-LAST:event_jLabel66MouseReleased

    private void jLabel67MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel67MouseReleased
        if (cie == null) {
            cie = new cie10();
            cie.setLocationRelativeTo(this);
            cie.setVisible(true);
        } else {
            cie.setVisible(true);
        }
        cie.diag = 4;
    }//GEN-LAST:event_jLabel67MouseReleased

    private void jLabel68MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel68MouseReleased
        if (cie == null) {
            cie = new cie10();
            cie.setLocationRelativeTo(this);
            cie.setVisible(true);
        } else {
            cie.setVisible(true);
        }
        cie.diag = 5;
    }//GEN-LAST:event_jLabel68MouseReleased

    private void jLabel64MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel64MouseExited
        jLabel64.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel64MouseExited

    private void jLabel65MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel65MouseExited
        jLabel65.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel65MouseExited

    private void jLabel66MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel66MouseExited
        jLabel66.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel66MouseExited

    private void jLabel67MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel67MouseExited
        jLabel67.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel67MouseExited

    private void jLabel68MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel68MouseExited
        jLabel68.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel68MouseExited

    private void jLabel64MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel64MouseMoved
        jLabel64.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel64MouseMoved

    private void jLabel65MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel65MouseMoved
        jLabel65.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel65MouseMoved

    private void jLabel66MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel66MouseMoved
        jLabel66.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel66MouseMoved

    private void jLabel67MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel67MouseMoved
        jLabel67.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel67MouseMoved

    private void jLabel68MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel68MouseMoved
        jLabel68.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel68MouseMoved

    private void jLabel69MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel69MouseReleased
        if (personas == null) {
            personas = new FormPersonas();
            personas.infopaciente = infohistoriac.getIdInfoAdmision().getIdDatosPersonales();
            //mostrar datos personas
        } else {
            personas.infopaciente = infohistoriac.getIdInfoAdmision().getIdDatosPersonales();
        }
        personas.setVisible(true);
    }//GEN-LAST:event_jLabel69MouseReleased

    private void jLabel70MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel70MouseReleased
                jFileChooser1 = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & PNG", "jpg", "png");
        jFileChooser1.setMultiSelectionEnabled(false);
        jFileChooser1.setFileFilter(filter);
        int result = jFileChooser1.showOpenDialog(null);
        File file = null;
        if (result == JFileChooser.APPROVE_OPTION) {
            String seleccion = (String) JOptionPane.showInputDialog(this, "Tipo de Archivo Adjunto", "Mensaje",
                    JOptionPane.QUESTION_MESSAGE, null, tipoAyudaDiag, "LABORATORIO");
            file = jFileChooser1.getSelectedFile();
            jTextField16.setText(file.getAbsolutePath());
            ImageIcon icon = new javax.swing.ImageIcon(getClass().getResource("/images/Delete16x16.png"));
            ImageIcon icon2 = new javax.swing.ImageIcon(getClass().getResource("/images/download.png"));
            int indexRow = jtbTratamiento4.getRowCount();
            if (modeloAyudDiag.getRowCount() == 0) {
                modeloAyudDiag.addRow(dato);
                modeloAyudDiag.setValueAt(file.getName(), indexRow, 2);
                modeloAyudDiag.setValueAt(new JLabel(icon), indexRow, 0);
                modeloAyudDiag.setValueAt(new JLabel(icon2), indexRow, 1);
                modeloAyudDiag.setValueAt(file, indexRow, 3);
                modeloAyudDiag.setValueAt(seleccion, indexRow, 4);
                modeloAyudDiag.setValueAt(ReturnPathdiagHelpSaveFile(seleccion), indexRow, 5);
                modeloAyudDiag.setValueAt("0", indexRow, 6);//identifica que aun no ha sido guardado en la bd
            } else {
                Boolean exist = null;
                for (int i = 0; i < modeloAyudDiag.getRowCount(); i++) {
                    if (((String) modeloAyudDiag.getValueAt(i, 2)).equals(file.getName())) {
                        exist = true;
                        break;
                    } else {
                        exist = false;
                    }
                }
                if (!exist) {
                    modeloAyudDiag.addRow(dato);
                    modeloAyudDiag.setValueAt(file.getName(), indexRow, 2);
                    modeloAyudDiag.setValueAt(new JLabel(icon), indexRow, 0);
                    modeloAyudDiag.setValueAt(new JLabel(icon2), indexRow, 1);
                    modeloAyudDiag.setValueAt(file, indexRow, 3);
                    modeloAyudDiag.setValueAt(seleccion, indexRow, 4);
                    modeloAyudDiag.setValueAt(ReturnPathdiagHelpSaveFile(seleccion), indexRow, 5);
                    modeloAyudDiag.setValueAt("0", indexRow, 6);//identifica que aun no ha sido guardado en la bd
                }
            }
        }
    }//GEN-LAST:event_jLabel70MouseReleased

    private void jLabel72MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel72MouseMoved
        Funciones.setLabelInfo("MEDIDAS GENERALES");
        jLabel72.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel72MouseMoved

    private void jLabel72MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel72MouseExited
        Funciones.setLabelInfo();
        jLabel72.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel72MouseExited

    private void jLabel72MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel72MouseReleased
        if (pMedidaGeneral == null) {
            pMedidaGeneral = new pTratMedidaGeneral();
            pMedidaGeneral.showListExistentes(factory, infohistoriac);
        }
        jPanel35.removeAll();
        pMedidaGeneral.setBounds(0, 0, 380, 420);
        jPanel35.add(pMedidaGeneral);
        pMedidaGeneral.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel72MouseReleased

    private void jLabel74MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel74MouseExited
        Funciones.setLabelInfo();
        jLabel74.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel74MouseExited

    private void jLabel74MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel74MouseReleased
                if (pMedic == null) {
            pMedic = new pTratMedic();
            pMedic.showListExistentes(factory, infohistoriac);
        }
        jPanel35.removeAll();
        pMedic.setBounds(0, 0, 380, 420);
        jPanel35.add(pMedic);
        pMedic.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel74MouseReleased

    private void jLabel74MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel74MouseMoved
        Funciones.setLabelInfo("LISTA DE MEDICAMENTOS");
        jLabel74.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel74MouseMoved

    private void jLabel78MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel78MouseExited
        Funciones.setLabelInfo();
        jLabel78.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel78MouseExited

    private void jLabel78MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel78MouseReleased
        if (pLaboratorio == null) {
            pLaboratorio = new pTratLaboratorio();
            pLaboratorio.showListExistentes(factory, infohistoriac);
        }
        jPanel35.removeAll();
        pLaboratorio.setBounds(0, 0, 380, 420);
        jPanel35.add(pLaboratorio);
        pLaboratorio.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
//        pLaboratorio.formularioOpen();
    }//GEN-LAST:event_jLabel78MouseReleased

    private void jLabel78MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel78MouseMoved
        Funciones.setLabelInfo("LABORATORIOS");
        jLabel78.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel78MouseMoved

    private void jLabel79MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel79MouseExited
        Funciones.setLabelInfo();
        jLabel79.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel79MouseExited

    private void jLabel79MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel79MouseReleased
        if (pImagenologia == null) {
            pImagenologia = new pTratImagenologia();
            pImagenologia.showListExistentes(factory, infohistoriac);
        }
        jPanel35.removeAll();
        pImagenologia.setBounds(0, 0, 380, 420);
        jPanel35.add(pImagenologia);
        pImagenologia.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
        pImagenologia.formularioOpen();
    }//GEN-LAST:event_jLabel79MouseReleased

    private void jLabel79MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel79MouseMoved
        Funciones.setLabelInfo("IMAGENOLOGIA");
        jLabel79.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel79MouseMoved

    private void jLabel80MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel80MouseExited
        Funciones.setLabelInfo();
        jLabel80.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel80MouseExited

    private void jLabel80MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel80MouseReleased
        if (pQuirurgico == null) {
            pQuirurgico = new pTratQuirurgico();
            pQuirurgico.showListExistentes(factory, infohistoriac);
        }
        jPanel35.removeAll();
        pQuirurgico.setBounds(0, 0, 380, 420);
        jPanel35.add(pQuirurgico);
        pQuirurgico.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
        pQuirurgico.formularioOpen();
    }//GEN-LAST:event_jLabel80MouseReleased

    private void jLabel80MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel80MouseMoved
        Funciones.setLabelInfo("QUIRURGICOS");
        jLabel80.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel80MouseMoved

    private void jLabel81MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel81MouseExited
        Funciones.setLabelInfo();
        jLabel81.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_jLabel81MouseExited

    private void jLabel81MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel81MouseReleased
        if (pProcedimientos == null) {
            pProcedimientos = new pTratMasProcedimientos(false);
            pProcedimientos.showListExistentes(factory, infohistoriac);
        }
        jPanel35.removeAll();
        pProcedimientos.setBounds(0, 0, 380, 420);
        jPanel35.add(pProcedimientos);
        pProcedimientos.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
        pProcedimientos.formularioOpen(0);
    }//GEN-LAST:event_jLabel81MouseReleased

    private void jLabel81MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel81MouseMoved
        Funciones.setLabelInfo("OTROS PROCEDIMIENTOS");
        jLabel81.setBackground(new java.awt.Color(194, 224, 255));
    }//GEN-LAST:event_jLabel81MouseMoved

    private void jLabel48MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel48MouseReleased
        if (pInterconsulta0 == null) {
            pInterconsulta0 = new pTratInterconsulta(0);//cirugia
            pInterconsulta0.showExistente(infohistoriac);
        }
        jPanel35.removeAll();
        pInterconsulta0.setBounds(0, 0, 380, 420);
        jPanel35.add(pInterconsulta0);
        pInterconsulta0.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel48MouseReleased

    private void jLabel52MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel52MouseReleased
        if (pInterconsulta1 == null) {
            pInterconsulta1 = new pTratInterconsulta(1);//Ginecologia
            pInterconsulta1.showExistente(infohistoriac);
        }
        jPanel35.removeAll();
        pInterconsulta1.setBounds(0, 0, 380, 420);
        jPanel35.add(pInterconsulta1);
        pInterconsulta1.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel52MouseReleased

    private void jLabel53MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel53MouseReleased
        if (pInterconsulta2 == null) {
            pInterconsulta2 = new pTratInterconsulta(2);//Medicina Interna
            pInterconsulta2.showExistente(infohistoriac);
        }
        jPanel35.removeAll();
        pInterconsulta2.setBounds(0, 0, 380, 420);
        jPanel35.add(pInterconsulta2);
        pInterconsulta2.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel53MouseReleased

    private void jLabel54MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel54MouseReleased
        if (pInterconsulta3 == null) {
            pInterconsulta3 = new pTratInterconsulta(3);//Ortopedia
            pInterconsulta3.showExistente(infohistoriac);
        }
        jPanel35.removeAll();
        pInterconsulta3.setBounds(0, 0, 380, 420);
        jPanel35.add(pInterconsulta3);
        pInterconsulta3.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel54MouseReleased

    private void jLabel55MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel55MouseReleased
        if (pInterconsulta4 == null) {
            pInterconsulta4 = new pTratInterconsulta(4);//Pediatria
            pInterconsulta4.showExistente(infohistoriac);
        }
        jPanel35.removeAll();
        pInterconsulta4.setBounds(0, 0, 380, 420);
        jPanel35.add(pInterconsulta4);
        pInterconsulta4.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel55MouseReleased

    private void jLabel56MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel56MouseReleased
        if (pOtrasInterconsultas == null) {
            pOtrasInterconsultas = new pTratOtrasInterconsultas();
            pOtrasInterconsultas.showLista(infohistoriac);
        }
        jPanel35.removeAll();
        pOtrasInterconsultas.setBounds(0, 0, 380, 420);
        jPanel35.add(pOtrasInterconsultas);
        pOtrasInterconsultas.setVisible(true);
        jPanel35.validate();
        jPanel35.repaint();
    }//GEN-LAST:event_jLabel56MouseReleased

    // </editor-fold>
    // <editor-fold desc="Variables declaration - do not modify"> 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    public javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JCheckBox jCheckBox7;
    private javax.swing.JCheckBox jCheckBox8;
    private javax.swing.JCheckBox jCheckBox9;
    public javax.swing.JComboBox jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel80;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane21;
    private javax.swing.JScrollPane jScrollPane22;
    private javax.swing.JScrollPane jScrollPane23;
    private javax.swing.JScrollPane jScrollPane24;
    private javax.swing.JScrollPane jScrollPane25;
    private javax.swing.JScrollPane jScrollPane26;
    private javax.swing.JScrollPane jScrollPane27;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    public javax.swing.JTextArea jTextArea10;
    private javax.swing.JTextArea jTextArea11;
    private javax.swing.JTextArea jTextArea12;
    private javax.swing.JTextArea jTextArea13;
    private javax.swing.JTextArea jTextArea14;
    private javax.swing.JTextArea jTextArea15;
    private javax.swing.JTextArea jTextArea16;
    private javax.swing.JTextArea jTextArea17;
    private javax.swing.JTextArea jTextArea19;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea22;
    private javax.swing.JTextArea jTextArea23;
    private javax.swing.JTextArea jTextArea24;
    private javax.swing.JTextArea jTextArea25;
    private javax.swing.JTextArea jTextArea26;
    private javax.swing.JTextArea jTextArea3;
    private javax.swing.JTextArea jTextArea4;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JTextArea jTextArea8;
    private javax.swing.JTextArea jTextArea9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    public javax.swing.JTextField jTextField11;
    public javax.swing.JTextField jTextField12;
    public javax.swing.JTextField jTextField13;
    public javax.swing.JTextField jTextField14;
    public javax.swing.JTextField jTextField15;
    public javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField20;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    public javax.swing.JLabel jlbNombrePaciente;
    private javax.swing.JPanel jpAntPersonales;
    private javax.swing.JPanel jpCentro;
    private javax.swing.JPanel jpDiagMedico;
    private javax.swing.JPanel jpEnfActual;
    private javax.swing.JPanel jpExpFisica;
    private javax.swing.JPanel jpMotivoC;
    private javax.swing.JPanel jpPruebasDiag;
    private javax.swing.JPanel jpTratamiento;
    private javax.swing.JTable jtbTratamiento4;
    // End of variables declaration//GEN-END:variables
// </editor-fold>

}
