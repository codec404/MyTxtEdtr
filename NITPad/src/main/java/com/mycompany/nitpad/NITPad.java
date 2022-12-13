package com.mycompany.nitpad;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NITPad extends JFrame implements ActionListener, WindowListener
{
    JTextArea jta = new JTextArea();
    File fnameContainer;
    
    public NITPad(){
        Font fnt = new Font("Arial", Font.PLAIN,15);
        Container cont = getContentPane();
        JMenuBar jmb = new JMenuBar();
        JMenu jmFile = new JMenu("File");
        JMenu jmEdit = new JMenu("Edit");
        JMenu jmHelp = new JMenu("Help");
        
        cont.setLayout(new BorderLayout());
        
        JScrollPane scrlP = new JScrollPane(jta);
        scrlP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        scrlP.setVisible(true);
        jta.setFont(fnt);
        jta.setLineWrap(true);
        jta.setWrapStyleWord(true);
        
        cont.add(scrlP);
        
        createMenuItem(jmFile, "New");
        createMenuItem(jmFile, "Open");
        createMenuItem(jmFile, "Save");
        jmFile.addSeparator();
        createMenuItem(jmFile, "Exit");
        
        createMenuItem(jmEdit , "Cut");
        createMenuItem(jmEdit , "Copy");
        createMenuItem(jmEdit , "Paste");
        
        createMenuItem(jmHelp , "About NITPad");
        
        jmb.add(jmFile);
        jmb.add(jmEdit);
        jmb.add(jmHelp);
        
        setJMenuBar(jmb);
        setIconImage(Toolkit.getDefaultToolkit().getImage("notepad.gif"));
        
        addWindowListener(this);
        setSize(600,600);
        setTitle("Unsaved.txt - NITPad");
        setVisible(true);
    }
    
    public void createMenuItem(JMenu jm, String getTxt){
        JMenuItem jmi = new JMenuItem(getTxt);
        jmi.addActionListener(this);
        jm.add(jmi);
    }
    
    public void actionPerformed(ActionEvent e){
        JFileChooser jFc = new JFileChooser();
        if(e.getActionCommand().equals("New"))
        {
            this.setTitle("Unsaved.txt - NITPad");
            jta.setText("");
            fnameContainer = null;
        }
        else if(e.getActionCommand().equals("Open"))
        {
            int show = jFc.showDialog(null, "Open");
            if(show == JFileChooser.APPROVE_OPTION){
                try{
                    File getFl = jFc.getSelectedFile();
                    OpenFile(getFl.getAbsolutePath());
                    this.setTitle(getFl.getName()+ "-NITPad");
                    fnameContainer = getFl;
                }
                catch(IOException exp1){ }
            }
        }
        else if(e.getActionCommand().equals("Save"))
        {
            if(fnameContainer != null)
            {
                jFc.setCurrentDirectory(fnameContainer);
                jFc.setSelectedFile(fnameContainer);
            }
            else
            {
                jFc.setSelectedFile(new File("Unsaved.txt"));
            }
            
            int show = jFc.showSaveDialog(null);
            if(show == JFileChooser.APPROVE_OPTION){
                try{
                    File fyl = jFc.getSelectedFile();
                    SaveFile(fyl.getAbsolutePath());
                    this.setTitle(fyl.getName()+ " - NITPad");
                    fnameContainer = fyl;
                }
                catch(IOException exp2){ }
            }
        }
        else if(e.getActionCommand().equals("Exit"))
        {
            getExit();
        }
        else if(e.getActionCommand().equals("Copy"))
            jta.copy();
        else if(e.getActionCommand().equals("Paste"))
            jta.paste();
        else if(e.getActionCommand().equals("About NITPad")){
            JOptionPane.showMessageDialog(this,"Created by @codec404 --NITDGP","NITPad",JOptionPane.INFORMATION_MESSAGE);
        }
        else if(e.getActionCommand().equals("Cut"))
            jta.cut();
    }
    public void OpenFile(String getFname) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(getFname)));
        
        String s;
        jta.setText("");
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        while((s = br.readLine()) != null )
        {
            jta.setText(jta.getText()+s+"\r\n");
        }
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        br.close();
    }
    public void SaveFile(String getFname) throws IOException{
        setCursor(new Cursor(Cursor.WAIT_CURSOR));
        DataOutputStream op = new DataOutputStream(new FileOutputStream(getFname));
        op.writeBytes(jta.getText());
        op.close();
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    public void windowDeactivated(WindowEvent e){}
    public void windowActivated(WindowEvent e){}
    public void windowDeiconified(WindowEvent e){}
    public void windowIconified(WindowEvent e){}
    public void windowClosed(WindowEvent e){}
    public void windowClosing(WindowEvent e){
        getExit();
    }
    public void windowOpened(WindowEvent e){}
    public void getExit(){
        System.exit(0);
    }
}
