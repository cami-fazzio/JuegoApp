
package juegoapp;

import java.io.IOException;


public class JuegoApp {

    public static void main(String[] args) throws IOException {
        
        if(args.length == 0){
            System.out.println("Parametros invalidos");
            System.exit(-1);
        }
        if(args[0].equals("consola")){
            System.out.println("consola");
            new JuegoConsola().Play();
        }else
        if(args[0].equals ("Pantalla")){
            System.out.println("Pantalla");
            new JuegoPantalla().Play();
        }

        
    }
    
}
