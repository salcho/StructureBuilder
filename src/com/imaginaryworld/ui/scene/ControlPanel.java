package com.imaginaryworld.ui.scene;

import com.imaginaryworld.Launcher;
import com.imaginaryworld.ui.Java3DFrame;
import com.sun.j3d.exp.swing.JCanvas3D;

import javax.swing.*;
import java.awt.event.*;

public class ControlPanel {
    public JPanel panel1;
    private JTextField txtInitialX;
    private JTextField txtInitialY;
    private JTextField txtInitialZ;
    private JTextField txtNumIterations;
    private JTextField textField5;
    private JSlider slider1;
    private JButton runUniverseButton;
    private JButton refreshViewButton;
    private JButton clearButton;
    public JCanvas3D canvas;

    public ControlPanel(Java3DFrame frame) {
        runUniverseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //runUniverse();
            }
        });
        clearButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.clear();
            }
        });
        refreshViewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.repaint();
            }
        });
        runUniverseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                frame.runUniverse(txtInitialX.getText(), txtInitialY.getText(), txtInitialZ.getText(), txtNumIterations.getText());
            }
        });
    }
}
