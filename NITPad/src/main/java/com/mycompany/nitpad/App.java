package com.mycompany.nitpad;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;

public class App {
    public static void main(String[] args)throws Exception{
        try {
            UIManager.setLookAndFeel( new FlatDarkLaf());
        } catch( Exception ex ) {
        System.err.println( "Failed to initialize LaF" );
        }
        NITPad notepad = new NITPad();
}

    }
