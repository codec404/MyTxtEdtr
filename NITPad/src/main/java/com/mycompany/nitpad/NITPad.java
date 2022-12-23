package com.mycompany.nitpad;

import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.border.BevelBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class NITPad extends JFrame implements ActionListener, WindowListener
{
    JTextArea jta = new JTextArea();
    File fnameContainer;
    Container cont = getContentPane();
    JPanel statusPanel = new JPanel();
//    boolean getVal = false;
    public NITPad(){
        Font fnt = new Font(Font.MONOSPACED, Font.BOLD,15);
        setBounds(100,10,800,600);
        jta.setBackground(Color.BLACK);
        jta.setForeground(Color.GREEN);
        JMenuBar jmb = new JMenuBar();
        JMenu jmFile = new JMenu("File");
        JMenu jmEdit = new JMenu("Edit");
        JMenu jmView = new JMenu("View");
        JMenu jmFonts = new JMenu("Fonts");
        JMenu jmTheme = new JMenu("Theme");
        JMenu jmHelp = new JMenu("Help");
        
        cont.setLayout(new BorderLayout());
        
        JScrollPane scrlP = new JScrollPane(jta);
        add(scrlP);
        scrlP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrlP.setBorder(BorderFactory.createEmptyBorder());
        
        scrlP.setVisible(true);
        jta.setFont(fnt);
        jta.setLineWrap(true);
        jta.setWrapStyleWord(true);
        
        statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
        cont.add(statusPanel, BorderLayout.SOUTH);
        statusPanel.setPreferredSize(new Dimension(cont.getWidth(), 25));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        JLabel statusLabel = new JLabel(" UTF-8  |  Windows-11  |  AMD-Ryzen5");
        statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        statusPanel.add(statusLabel);
        
        cont.add(scrlP);
        
        createMenuItem(jmFile, "New");
        createMenuItem(jmFile, "New Window");
        createMenuItem(jmFile, "Open");
        createMenuItem(jmFile, "Save");
        createMenuItem(jmFile, "Save As");
        createMenuItem(jmFile, "Print");
        jmFile.addSeparator();
        createMenuItem(jmFile, "Exit");
        
        createMenuItem(jmEdit , "Cut");
        createMenuItem(jmEdit , "Copy");
        createMenuItem(jmEdit , "Paste");
        createMenuItem(jmEdit, "Select All");
        
        jmView.add(jmFonts);
        createMenuItem(jmFonts , "Sans-Serif");
        createMenuItem(jmFonts , "Dialog-Input");
        createMenuItem(jmFonts , "Monospaced");
        JCheckBoxMenuItem sb = new JCheckBoxMenuItem("Status Bar");
        sb.setSelected(true);
        sb.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if(sb.getState()==false){
                cont.remove(statusPanel);
                cont.revalidate();
                }
                else{
                cont.add(statusPanel, BorderLayout.SOUTH);
                cont.revalidate();
                }
            }});
        jmView.add(sb);
        createMenuItem(jmTheme, "Default");
        createMenuItem(jmTheme, "Light");
        createMenuItem(jmTheme, "Dracula Black");
        
        createMenuItem(jmHelp , "About NITPad");
        
        jmb.add(jmFile);
        jmb.add(jmEdit);
        jmb.add(jmView);
        jmb.add(jmTheme);
        jmb.add(jmHelp);
        
        setJMenuBar(jmb);
        String filepath = "D:\\Projects\\NITPad\\NITPad\\src\\main\\java\\com\\mycompany\\nitpad\\icon.png";
        setIconImage(Toolkit.getDefaultToolkit().getImage(filepath));
        
        addWindowListener(this);
        setSize(600,600);
        setTitle("Unsaved.txt - NITPad");
        setVisible(true);
    }
    
    public void createMenuItem(JMenu jm, String getTxt){
        JMenuItem jmi = new JMenuItem(getTxt);
        if(getTxt.equals("New"))
        {
            jmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_DOWN_MASK));
        }
        else if(getTxt.equals("New Window"))
        {
            jmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,KeyEvent.CTRL_DOWN_MASK + KeyEvent.SHIFT_DOWN_MASK));
        }
        else if(getTxt.equals("Open"))
        {
            jmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,KeyEvent.CTRL_DOWN_MASK));
        }
        else if(getTxt.equals("Save"))
        {
            jmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK));
        }
        else if(getTxt.equals("Save As"))
        {
            jmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,KeyEvent.CTRL_DOWN_MASK+KeyEvent.SHIFT_DOWN_MASK));
        }
        else if(getTxt.equals("Print"))
        {
            jmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,KeyEvent.CTRL_DOWN_MASK));
        }
        else if(getTxt.equals("Exit"))
        {
            jmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,KeyEvent.CTRL_DOWN_MASK));
        }
        else if(getTxt.equals("Cut"))
        {
            jmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,KeyEvent.CTRL_DOWN_MASK));
        }
        else if(getTxt.equals("Copy"))
        {
            jmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_DOWN_MASK));
        }
        else if(getTxt.equals("Paste"))
        {
            jmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,KeyEvent.CTRL_DOWN_MASK));
        }
        else if(getTxt.equals("Select All"))
        {
            jmi.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,KeyEvent.CTRL_DOWN_MASK));
        }
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
        else if(e.getActionCommand().equals("New Window"))
        {
            new NITPad();
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
                try{
                        File fyl = jFc.getSelectedFile();
                        SaveFile(fyl.getAbsolutePath());
                        this.setTitle(fyl.getName()+ " - NITPad");
                        fnameContainer = fyl;
                    }
                    catch(IOException exp2){ }
            }
            else
            {
                jFc.setSelectedFile(new File("Unsaved.txt"));
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
        }
        else if(e.getActionCommand().equals("Save As"))
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
        else if(e.getActionCommand().equals("Print"))
        {
            try {
                jta.print();
            } catch (PrinterException ex) {
                Logger.getLogger(NITPad.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(e.getActionCommand().equals("Default"))
        {
            jta.setBackground(Color.BLACK);
            jta.setForeground(Color.GREEN);
        } 
        else if(e.getActionCommand().equals("Light"))
        {
            jta.setBackground(Color.WHITE);
            jta.setForeground(Color.BLACK);
        } 
        else if(e.getActionCommand().equals("Dracula Black"))
        {
            jta.setBackground(new Color(65,68,80));
            jta.setForeground(Color.WHITE);
        } 
        else if(e.getActionCommand().equals("Copy"))
            jta.copy();
        else if(e.getActionCommand().equals("Paste"))
            jta.paste();
        else if(e.getActionCommand().equals("Select All"))
            jta.selectAll();
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
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    public void windowOpened(WindowEvent e){}
    public void getExit(){
        System.exit(0);
    }
}