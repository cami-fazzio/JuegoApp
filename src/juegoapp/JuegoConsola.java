
package juegoapp;

import java.util.Scanner;


public class JuegoConsola {
    
    
    public static void Play(){
        Scanner sc = new Scanner(System.in);
    
            int opcion = 0;

            do{
                opcion = MenuInicio();

                if(opcion == 1){
                    System.out.println("Ingrese el nombre");
                    String nombre = sc.next();
                    Mascota mascota = new Mascota(nombre);
                    mascota.Estado();

                    do{
                    opcion = opcionesMascota();

                        if(opcion == 1){
                            System.out.println("Alimetar");
                            mascota.eat();
                        }
                        if(opcion == 2){
                            System.out.println("jugar");
                            mascota.play();
                        }
                        if(opcion == 3){
                            System.out.println("Dormir");
                            mascota.sleep();
                        }
                        if(opcion == 4){
                            System.out.println("Bañar");
                            mascota.wash();
                        }
                    }while(opcion != 0 && opcion != 9);



                }

            }while(opcion != 0);

    }
    
    
    
    
    
    
    public static int MenuInicio(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese Eleccion");
        System.out.println("[1] Crear Mascota");
        System.out.println("[0] Salir");
        return sc.nextInt();
    }
    
    public static int opcionesMascota(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ingrese opcion");
        System.out.println("[1] Alimentar");
        System.out.println("[2] Jugar");
        System.out.println("[3] Dormir");
        System.out.println("[4] Bañar");
        System.out.println("[5] Resetear");
        System.out.println("[0] Resetear");
        return sc.nextInt();
    }
    
    
    
}
