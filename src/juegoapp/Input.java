
package juegoapp;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener{
    private boolean[] button;
    public boolean escape,test;
    
    public Input(){
        button = new boolean[256];
    }
    
    public void update(){
        escape = button[KeyEvent.VK_ESCAPE];
        test = button[KeyEvent.VK_SPACE];
        
        
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        button[ke.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        button[ke.getKeyCode()] = false;
        if(JuegoPantalla.menu == true && JuegoPantalla.name.length() < 10){
            if(ke.getKeyCode() == 65){
                JuegoPantalla.name += "a";
            }
            if(ke.getKeyCode() == 66){
                JuegoPantalla.name += "b";
            }
            if(ke.getKeyCode() == 67){
                JuegoPantalla.name += "c";
            }
            if(ke.getKeyCode() == 68){
                JuegoPantalla.name += "d";
            }
            if(ke.getKeyCode() == 69){
                JuegoPantalla.name += "e";
            }
            if(ke.getKeyCode() == 70){
                JuegoPantalla.name += "f";
            }
            if(ke.getKeyCode() == 71){
                JuegoPantalla.name += "g";
            }
            if(ke.getKeyCode() == 72){
                JuegoPantalla.name += "h";
            }
            if(ke.getKeyCode() == 73){
                JuegoPantalla.name += "i";
            }
            if(ke.getKeyCode() == 74){
                JuegoPantalla.name += "j";
            }
            if(ke.getKeyCode() == 75){
                JuegoPantalla.name += "k";
            }
            if(ke.getKeyCode() == 76){
                JuegoPantalla.name += "l";
            }
            if(ke.getKeyCode() == 77){
                JuegoPantalla.name += "m";
            }
            if(ke.getKeyCode() == 78){
                JuegoPantalla.name += "n";
            }
            if(ke.getKeyCode() == 79){
                JuegoPantalla.name += "o";
            }
            if(ke.getKeyCode() == 80){
                JuegoPantalla.name += "p";
            }
            if(ke.getKeyCode() == 81){
                JuegoPantalla.name += "q";
            }
            if(ke.getKeyCode() == 82){
                JuegoPantalla.name += "r";
            }
            if(ke.getKeyCode() == 83){
                JuegoPantalla.name += "s";
            }
            if(ke.getKeyCode() == 84){
                JuegoPantalla.name += "t";
            }
            if(ke.getKeyCode() == 85){
                JuegoPantalla.name += "u";
            }
            if(ke.getKeyCode() == 86){
                JuegoPantalla.name += "v";
            }
            if(ke.getKeyCode() == 87){
                JuegoPantalla.name += "w";
            }
            if(ke.getKeyCode() == 88){
                JuegoPantalla.name += "x";
            }
            if(ke.getKeyCode() == 89){
                JuegoPantalla.name += "y";
            }
            if(ke.getKeyCode() == 90){
                JuegoPantalla.name += "z";
            }
            
        }
        
        if(JuegoPantalla.menu == true){
            if(ke.getKeyCode() == 8){
                if(JuegoPantalla.name.length() > 0){
                    JuegoPantalla.name = JuegoPantalla.name.substring(0,JuegoPantalla.name.length() - 1);
                }      
                            
            }
            
            if(ke.getKeyCode() == 10){
                JuegoPantalla.menu = false;
            }
    
        }
        
    }
    
}
