package com.imaginaryworld.ui.scene;

import com.sun.j3d.exp.swing.JCanvas3D;

import javax.swing.*;
import java.awt.event.*;

public class UniverseViewer{
    public JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JSlider slider1;
    private JButton runUniverseButton;
    private JButton refreshViewButton;
    public JCanvas3D canvas;

    public UniverseViewer() {
        runUniverseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //runUniverse();
            }
        });
    }
}
