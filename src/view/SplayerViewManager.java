package view;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.TransferHandler;
import javax.swing.event.ChangeListener;

import data.Library;
import data.Music;
import data.SplayerDataManager;
import engine.Player;
import engine.action.ActionOpenPlaylist;
import engine.listener.SplayerKeyListener;
import engine.listener.SplayerSliderListener;
import engine.listener.SplayerVolumeListener;
import engine.listener.SplayerWindowListener;


public class SplayerViewManager implements Observer {

    /* Data stage */
    private SplayerViewMain viewMain;
    private SplayerViewPlaylist viewPlaylist;
    
    /* Builder stage */
    public SplayerViewManager()
    {
        // Building
        this.viewMain       = new SplayerViewMain();
        this.viewPlaylist   = new SplayerViewPlaylist();
        
        // Action mapping
        this.viewMain.setAction("playlist", new ActionOpenPlaylist(viewPlaylist));
        
        // Post-init messages
        System.out.println("Splayer:ViewManager initialized.");
    }

    @Override
    public void update(Observable model, Object obj)
    {
        String argument = (String)obj;
        
        // Mise a jour du timer
        if( argument.equals("timerUpdate") )
    		updateTimer( Math.round( ((Player)model).getPosition() ) );
        // Mise a jour de la selection (changement de musique)
        if( argument.equals("playlistSelection") ) {
            Music current = ((SplayerDataManager)model).getCurrentMusic();
            this.viewMain.updateData(current);
        }
        // Ajout d'une musique a playlist
        else if( argument.equals("playlistUpdate") ) { // TODO cette update semble inutile ?
            this.viewPlaylist.setPlaylist( ((SplayerDataManager)model).getPlaylist());
        }
        else if ( argument.equals("libraryUpdate") ) {
        	this.viewPlaylist.setLibrary( ((SplayerDataManager)model).getLibrary() );
        }
        	
        // Modification du volume
        else if( argument.equals("volumeUpdate") ) {
            this.viewMain.setDisplay("volume", "" + Math.round(((Player)model).getVolume()*100) );
        }
        // Initialisation de l'application
        else if( argument.equals("initialization") ) {
            this.viewPlaylist.setPlaylist( ((SplayerDataManager)model).getPlaylist());
            Music current = ((SplayerDataManager)model).getCurrentMusic();
            this.viewMain.updateData(current);
        }
        // Initialisation du player
        else if( argument.equals("playerInit") ) {
            this.viewMain.setDisplay("volume", "" + Math.round(((Player)model).getVolume()*100) );
            this.viewMain.setvolume( (int) (((Player)model).getVolume() * 100) );
        }
    }
    
    public void updateTimer(int timeInMilliSec)
    {
        int timeInSec = (int) (timeInMilliSec/1000);
        viewMain.setDisplay("time", (int)Math.floor(timeInSec/60) + ":" + String.format("%02d", timeInSec%60));
        viewMain.setSlider(timeInMilliSec);
    }

    /* Interface stage */
    /**
     * Associe un bouton avec une action. Attention, ceci pourrait �tre modifiŽ si tous les boutons sont gŽrŽs par le SplayerViewManager.
     * @param buttonName code bouton ˆ associer (ex: "play" pour le bouton de lecture/pause)
     * @param action AbstractAction ˆ associer
     */
    public void setAction(String buttonName, AbstractAction action)
    {
        viewMain.setAction(buttonName, action);
        viewPlaylist.setAction(buttonName, action);
    }
    
    public void setListener(String componentName, Object listener)
    {   
        // Listener pour la playlist
        if( listener instanceof MouseListener ) {
            if( componentName.equals("PLAYLIST") )
                viewPlaylist.setPlaylistListener(listener);
            else
                viewMain.setListener(componentName, listener);
        }
        // Listener pour le slider de volume
        else if( listener instanceof  SplayerVolumeListener )
            viewMain.addVolumeListener((ChangeListener) listener);
        // Listener pour le slider de lecture
        else if( listener instanceof SplayerSliderListener )
            viewMain.addSliderListener(listener);
        // Listener de fenetre principale
        else if( listener instanceof SplayerWindowListener )
            viewMain.addWindowListener((WindowListener)listener);
        // Listener de barre de recherche
        else if( listener instanceof SplayerKeyListener )
        	viewPlaylist.setSearchListener((KeyListener)listener);
    }
    
    public void setPlaylistHandler(TransferHandler handler)
    {
        viewPlaylist.setPlaylistHandler(handler);
    }
    
    /* Implementation stage */
    public void go()
    {
        viewMain.setVisible(true);
    }
      
}
