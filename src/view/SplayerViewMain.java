package view;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;

/**
 * Classe repr�sentant la fen�tre principale
 * @author S�bastien Poulmane & Lo�c Daara
 *
 */
@SuppressWarnings("serial")
public class SplayerViewMain extends JFrame {

    /* Data stage */
    
    /* Interface stage*/
    // Panels
    private JPanel panel;
    private JPanel timePanel, infoPanel, controlerPanel;
    // Text
    private HashMap<String, JLabel> display;
    // Interactive components
    private HashMap<String, JButton> buttonPlayer;
    private JSlider sliderPlayer, sliderVolume;
    // Actions
    
    public SplayerViewMain()
    {
        // Frame
        super("Splayer");
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Data 
        
        // Components
        display = new HashMap<String, JLabel>();
        display.put("time", new JLabel("0:00"));
        display.put("left", new JLabel("99:99"));
        display.put("artist", new JLabel("John Doe"));
        display.put("title", new JLabel("Soft Kitty"));
        display.put("album", new JLabel("Sweet Songs When Being Sick"));
        
        // TODO Cr�e des ic�nes et si possible des boutons non-rectangulaires
        buttonPlayer = new HashMap<String, JButton>();
        buttonPlayer.put("play", new JButton(">"));
        buttonPlayer.put("previous", new JButton("|<"));
        buttonPlayer.put("next", new JButton(">|"));
        buttonPlayer.put("rewind", new JButton("<<"));
        buttonPlayer.put("forward", new JButton(">>"));
        buttonPlayer.put("loop", new JButton("O"));
        buttonPlayer.put("random", new JButton("R"));
        
        // TODO Trouver un meilleur emplacement
        buttonPlayer.put("PLAYLIST", new JButton("Playlist"));
        buttonPlayer.put("LIBRARY", new JButton("Library"));
        
        sliderPlayer = new JSlider(0, 100, 0);
        sliderVolume = new JSlider(0, 100, 25);
        
        // Actions
        
        // Layout
        GridBagConstraints layoutManager = new GridBagConstraints();
        layoutManager.fill = GridBagConstraints.NONE;
        
            // Time panel
        timePanel = new JPanel();
        timePanel.setLayout(new GridBagLayout());
        layoutManager.gridx = layoutManager.gridy = 0;
        timePanel.add(display.get("time"), layoutManager);
        layoutManager.gridy = 1;
        timePanel.add(display.get("left"), layoutManager);
        
            // Info Panel
        infoPanel = new JPanel();
        infoPanel.setLayout(new GridBagLayout());
        layoutManager.gridy = 0;
        layoutManager.gridwidth = 3;
        infoPanel.add(display.get("title"), layoutManager);
        layoutManager.gridy = 1;
        layoutManager.gridwidth = 1;
        infoPanel.add(display.get("artist"), layoutManager);
        layoutManager.gridx = 1;
        infoPanel.add(new JLabel(" - "), layoutManager);
        layoutManager.gridx = 2;
        infoPanel.add(display.get("album"), layoutManager);
        
            // Sound Panel
        // ...
        
            // Controler Panel
        controlerPanel = new JPanel(new FlowLayout());
        // TODO Faire une boucle plut�t
        controlerPanel.add(buttonPlayer.get("loop"));
        controlerPanel.add(buttonPlayer.get("previous"));
        controlerPanel.add(buttonPlayer.get("rewind"));
        controlerPanel.add(buttonPlayer.get("play"));
        controlerPanel.add(buttonPlayer.get("forward"));
        controlerPanel.add(buttonPlayer.get("next"));
        controlerPanel.add(buttonPlayer.get("random"));
        controlerPanel.add(buttonPlayer.get("PLAYLIST"));
        controlerPanel.add(buttonPlayer.get("LIBRARY"));
        
            // Layout building
        layoutManager.gridx = layoutManager.gridy = 0;
        panel.add(timePanel, layoutManager);
        layoutManager.gridx = 1;
        panel.add(infoPanel, layoutManager);
        layoutManager.gridx = 2;
        panel.add(sliderVolume, layoutManager);
        layoutManager.gridx = 0;
        layoutManager.gridy = 1;
        layoutManager.gridwidth = 3;
        panel.add(sliderPlayer, layoutManager);
        layoutManager.gridy = 2;
        panel.add(controlerPanel, layoutManager);
        
        this.add(panel);
        
        // Packing
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}