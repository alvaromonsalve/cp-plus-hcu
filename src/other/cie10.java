/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package other;

import atencionurgencia.AtencionUrgencia;
import entidades.StaticCie10;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import jpa.StaticCie10JpaController;
import tools.myStringsFunctions;

/**
 *
 * @author Alvaro Monsalve
 */
public class cie10 extends javax.swing.JDialog {
    
    public DefaultTableModel modelo;
    private List<StaticCie10> listaCie10;
    private EntityManagerFactory factory;
    public int diag=0;

    /**
     * Creates new form cie10
     */
    public cie10() {
        initComponents();
        ModeloListado();
        listarCie10();
    }
    
    private void listarCie10(){        
        factory = Persistence.createEntityManagerFactory("ClipaEJBPU",AtencionUrgencia.props);
        StaticCie10JpaController staticcie10EJB = new StaticCie10JpaController(factory);
        Object dato[] = null;
        listaCie10 = staticcie10EJB.findStaticCie10Entities();
//        StaticCie10 staticCie10;
        if (listaCie10!=null){
            for (StaticCie10 staticCie10 : listaCie10) {

                    int i = modelo.getRowCount();
                    modelo.addRow(dato);
                    modelo.setValueAt(staticCie10.getId(), i, 0);
                    modelo.setValueAt(staticCie10.getCodigo(), i, 1);
                    modelo.setValueAt(staticCie10.getDescripcion(),i,2);
                
            }
        }
    }
    
     private void ModeloListado(){
         try {
             modelo = (new DefaultTableModel(
                null, new String [] {"id","Codigo", "Descripcion"}){
                Class[] types = new Class [] {
                    java.lang.String.class,
                    java.lang.String.class,
                    java.lang.String.class};

                boolean[] canEdit = new boolean [] {
                false,false,false
                };
                @Override
                public Class getColumnClass(int columnIndex) {
                   return types [columnIndex];
                }
                @Override
                public boolean isCellEditable(int rowIndex, int colIndex){
                   return canEdit [colIndex];
                }
            });
            jtCie10.setModel(modelo);
            jtCie10.getColumnModel().getColumn(1).setMaxWidth(50);
            jtCie10.getColumnModel().getColumn(2).setPreferredWidth(350);
            jtCie10.getTableHeader().setReorderingAllowed(false);
            jtCie10.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            setOcultarColumnas(jtCie10,new int[]{0});
         } catch (Exception ex) {
             JOptionPane.showMessageDialog(null, "10072:\n"+ex.getMessage(), cie10.class.getName(), JOptionPane.INFORMATION_MESSAGE);
         }
    }
     
     private void setOcultarColumnas(JTable tbl, int columna[]){
         for(int i = 0;i<columna.length;i++){
             tbl.getColumnModel().getColumn(columna[i]).setMaxWidth(0);
             tbl.getColumnModel().getColumn(columna[i]).setMinWidth(0);
             tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMaxWidth(0);
             tbl.getTableHeader().getColumnModel().getColumn(columna[i]).setMinWidth(0);
         }
     }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jtCie10 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setResizable(false);
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });

        jtCie10.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jtCie10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtCie10MouseClicked(evt);
            }
        });
        jtCie10.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jtCie10MouseMoved(evt);
            }
        });
        jScrollPane1.setViewportView(jtCie10);

        jLabel1.setText("FILTRO");

        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField1KeyTyped(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setMaximumSize(new java.awt.Dimension(100, 20));
        jToolBar1.setMinimumSize(new java.awt.Dimension(100, 20));
        jToolBar1.setPreferredSize(new java.awt.Dimension(100, 20));

        jLabel2.setText("Doble clic para seleccionar un diagnostico");
        jToolBar1.add(jLabel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jTextField1))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown

    }//GEN-LAST:event_formComponentShown

    private void jTextField1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyTyped

    }//GEN-LAST:event_jTextField1KeyTyped

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
    TableRowSorter sorter = new TableRowSorter(modelo);
    sorter.setRowFilter (RowFilter.regexFilter(jTextField1.getText().toUpperCase()));
    jtCie10.setRowSorter (sorter);
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jtCie10MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtCie10MouseMoved
       // Table colModel = jtCie10.getColumnModel();
        int rowIndex = jtCie10.rowAtPoint(evt.getPoint());
        jtCie10.setToolTipText(myStringsFunctions.stringToDIVstring((String)jtCie10.getValueAt(rowIndex, 2)));
    }//GEN-LAST:event_jtCie10MouseMoved

    private void jtCie10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtCie10MouseClicked
        try {            
            if(evt.getClickCount() ==2 && evt.getButton()==1){
                int rowIndex = jtCie10.rowAtPoint(evt.getPoint());
                switch (diag){
                    case 1:
                        AtencionUrgencia.panelindex.hc.jTextField11.setText("["+jtCie10.getValueAt(rowIndex, 1)+"] " +jtCie10.getValueAt(rowIndex, 2));
                        AtencionUrgencia.panelindex.hc.idDiag1 = (Integer) jtCie10.getValueAt(rowIndex, 0);
                        break;
                    case 2:
                        AtencionUrgencia.panelindex.hc.jTextField12.setText("["+jtCie10.getValueAt(rowIndex, 1)+"] " +jtCie10.getValueAt(rowIndex, 2));
                        AtencionUrgencia.panelindex.hc.idDiag2 = (Integer) jtCie10.getValueAt(rowIndex, 0);
                        break;
                    case 3:
                        AtencionUrgencia.panelindex.hc.jTextField13.setText("["+jtCie10.getValueAt(rowIndex, 1)+"] " +jtCie10.getValueAt(rowIndex, 2));
                        AtencionUrgencia.panelindex.hc.idDiag3 = (Integer) jtCie10.getValueAt(rowIndex, 0);
                        break;
                    case 4:
                        AtencionUrgencia.panelindex.hc.jTextField14.setText("["+jtCie10.getValueAt(rowIndex, 1)+"] " +jtCie10.getValueAt(rowIndex, 2));
                        AtencionUrgencia.panelindex.hc.idDiag4 = (Integer) jtCie10.getValueAt(rowIndex, 0);
                        break;
                    case 5:
                        AtencionUrgencia.panelindex.hc.jTextField15.setText("["+jtCie10.getValueAt(rowIndex, 1)+"] " +jtCie10.getValueAt(rowIndex, 2));
                        AtencionUrgencia.panelindex.hc.idDiag5 = (Integer) jtCie10.getValueAt(rowIndex, 0);
                        break;
                    case 6:
                        AtencionUrgencia.panelindex.evo.analisis.jTextField1.setText("["+jtCie10.getValueAt(rowIndex, 1)+"] " +jtCie10.getValueAt(rowIndex, 2));
                        AtencionUrgencia.panelindex.evo.analisis.dx = (Integer) jtCie10.getValueAt(rowIndex, 0);
                        break;
                    case 7:
                        AtencionUrgencia.panelindex.evo.analisis.jTextField2.setText("["+jtCie10.getValueAt(rowIndex, 1)+"] " +jtCie10.getValueAt(rowIndex, 2));
                        AtencionUrgencia.panelindex.evo.analisis.dx1 = (Integer) jtCie10.getValueAt(rowIndex, 0);
                        break;
                    case 8:
                        AtencionUrgencia.panelindex.evo.analisis.jTextField3.setText("["+jtCie10.getValueAt(rowIndex, 1)+"] " +jtCie10.getValueAt(rowIndex, 2));
                        AtencionUrgencia.panelindex.evo.analisis.dx2 = (Integer) jtCie10.getValueAt(rowIndex, 0);
                        break;
                    case 9:
                        AtencionUrgencia.panelindex.evo.analisis.jTextField4.setText("["+jtCie10.getValueAt(rowIndex, 1)+"] " +jtCie10.getValueAt(rowIndex, 2));
                        AtencionUrgencia.panelindex.evo.analisis.dx3 = (Integer) jtCie10.getValueAt(rowIndex, 0);
                        break;
                    case 10:
                        AtencionUrgencia.panelindex.evo.analisis.jTextField5.setText("["+jtCie10.getValueAt(rowIndex, 1)+"] " +jtCie10.getValueAt(rowIndex, 2));
                        AtencionUrgencia.panelindex.evo.analisis.dx4 = (Integer) jtCie10.getValueAt(rowIndex, 0);
                        break;
                }   
                this.dispose();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "10073:\n"+ex.getMessage(), cie10.class.getName(), JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_jtCie10MouseClicked

   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable jtCie10;
    // End of variables declaration//GEN-END:variables
}
