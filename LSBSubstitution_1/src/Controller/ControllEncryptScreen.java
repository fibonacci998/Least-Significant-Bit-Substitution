/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Analyze.Encode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import View.EncryptScreen;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 *
 * @author tuans
 */
public class ControllEncryptScreen implements ActionListener{

    private EncryptScreen screen;
    public ControllEncryptScreen(EncryptScreen screen) {
        this.screen=screen;
    }

    public void getFileFromBrowser(){
        // TODO add your handling code here:
        JFileChooser fileChooser=new JFileChooser();
        //set directory of open function at same directory's project
        fileChooser.setCurrentDirectory(new File("."));
        //if (fileChooser.showOpenDialog()==JFileChooser.APPROVE_OPTION){
        fileChooser.showOpenDialog(null);
        File f=fileChooser.getSelectedFile();
        //path=fileChooser.getSelectedFile().toString();
        JPanel pn=screen.getPnOriginalImage();
        screen.getPnOriginalImage().removeAll();
        BufferedImage img=null;
        try {
            img=ImageIO.read(f);
        } catch (IOException ex) {
            Logger.getLogger(EncryptScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        screen.setOriginImage(img);
        Image dimg=img.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        JLabel jl=new JLabel();
        jl.setSize(300, 300);
        jl.setIcon(new ImageIcon(dimg));
        jl.setHorizontalAlignment(JLabel.CENTER);
        screen.getPnOriginalImage().add(jl);
        screen.getPnOriginalImage().repaint();
        screen.getTxtFileSrc().setText(fileChooser.getSelectedFile().toString());
    }
    public void saveFile(){
        JFileChooser fileChooser=new JFileChooser();
        //set directory of open function at same directory's project
        fileChooser.setCurrentDirectory(new File("."));
        int result=fileChooser.showSaveDialog(null);
        if (result==JFileChooser.APPROVE_OPTION){
            File file=fileChooser.getSelectedFile();
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(EncryptScreen.class.getName()).log(Level.SEVERE, null, ex);
            }
            BufferedImage image=screen.getStegoImage();
            try {
                ImageIO.write(image, "png", file);
            } catch (IOException ex) {
                Logger.getLogger(EncryptScreen.class.getName()).log(Level.SEVERE, null, ex);
            }    
        }
    }
    public void hideMessage(){
        Encode encode=new Encode(screen.getOriginImage());
        screen.setStegoImage(encode.getEmbedImage(screen.getTxtSecretText().getText()));
        
        Image dimg=screen.getStegoImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        JLabel jl=new JLabel();
        jl.setSize(300, 300);
        jl.setIcon(new ImageIcon(dimg));
        jl.setHorizontalAlignment(JLabel.CENTER);
        screen.getPnStegoImage().add(jl);
        screen.getPnStegoImage().repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
