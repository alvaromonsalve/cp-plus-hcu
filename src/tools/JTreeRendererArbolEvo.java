/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import entidades.HcuEvolucion;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author Administrador
 */
public class JTreeRendererArbolEvo extends DefaultTreeCellRenderer{
    
    private final ImageIcon bullet_grey,bullet_green,folder_open,folder,bullet_yellow,bullet_blue,bullet_blue2,bullet_purple,bullet_orange;
    
    public JTreeRendererArbolEvo(){
        this.bullet_grey=new ImageIcon(getClass().getResource("/images/bullet_grey.png"));
        this.bullet_green=new ImageIcon(getClass().getResource("/images/bullet_green.png"));
        this.bullet_yellow=new ImageIcon(getClass().getResource("/images/bullet_yellow.png"));
        this.bullet_blue=new ImageIcon(getClass().getResource("/images/bullet_blue.png"));
        this.bullet_blue2=new ImageIcon(getClass().getResource("/images/bullet_blue2.png"));
        this.bullet_purple=new ImageIcon(getClass().getResource("/images/bullet_purple.png"));
        this.bullet_orange=new ImageIcon(getClass().getResource("/images/bullet_orange.png"));
        this.folder_open=new ImageIcon(getClass().getResource("/images/folder_open.png"));
        this.folder=new ImageIcon(getClass().getResource("/images/folder.png"));
    }
    
    @Override
    public Component getTreeCellRendererComponent(
            JTree tree, Object value, boolean selected, boolean expanded,
            boolean leaf, int row, boolean hasFocus){
        super.getTreeCellRendererComponent(tree,value,selected,expanded,leaf,row,hasFocus);   
        try {
            if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getTipo()==0){
                if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==2){
                    this.setIcon(bullet_green);
                }else if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==1){
                    this.setIcon(bullet_yellow);
                }else if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==3){   
                    this.setIcon(bullet_blue);
                }else if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==4){   
                    this.setIcon(bullet_blue2);
                }else{
                    this.setIcon(bullet_grey);
                }
            }else if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getTipo()==1){
                if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==2){
                    this.setIcon(bullet_purple);
                }else if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==1){
                    this.setIcon(bullet_yellow);
                }else if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==3){   
                    this.setIcon(bullet_blue);
                }else if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==4){   
                    this.setIcon(bullet_blue2);
                }else{
                    this.setIcon(bullet_grey);
                }
            }else if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getTipo()==2){
                if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==2){
                    this.setIcon(bullet_orange);
                }else if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==1){
                    this.setIcon(bullet_yellow);
                }else if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==3){   
                    this.setIcon(bullet_blue);
                }else if(((HcuEvolucion)((DefaultMutableTreeNode)value).getUserObject()).getEstado()==4){   
                    this.setIcon(bullet_blue2);
                }else{
                    this.setIcon(bullet_grey);
                }
            }
            
            
            
            
        } catch (Exception e) {
             this.setOpenIcon(folder_open);
             this.setClosedIcon(folder);
        }
        return this;
    }
    
}
