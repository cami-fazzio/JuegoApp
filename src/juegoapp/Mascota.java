/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package juegoapp;


public class Mascota {
    public String nombre;
    private int hunger = 950,sleepy = 950, hapiness = 950,clean= 950;
    private boolean eat,sleep,play,wash ;
    private int maxHunger = 950, maxSleep = 950, maxHappiness = 950, maxClean = 950;
    
    public Mascota(String nombre){
        this.nombre = nombre;
    }
    
    public void eat(){
        System.out.println("yum");
        eat = true;
        hunger += 50;
        sleepy += 5;
        if(hunger > maxHunger){
            hunger = maxHunger;
        }
        Estado();
    }
    
    public void sleep(){
        System.out.println("zzz");
        sleep = true;
        sleepy += 350;
        hunger -= 150;
        hapiness -= 100;
        if(sleepy > maxSleep){
            sleepy = maxSleep;
        }
        Estado();
    }
    
    public void play(){
        System.out.println("fun");
        play = true;
        hapiness += 90;
        hunger -= 10;
        clean -= 40;
        sleepy -= 25;
        if(hapiness > maxHappiness){
            hapiness = maxHappiness;
        }
        Estado();
    }
    
    public void wash(){
        System.out.println("clean");
        wash = true;
        clean += 75;
        hapiness += 50;
        if(clean > maxClean){
            clean = maxClean;
        }
        Estado();
    }
    
    
    public void Estado(){
        System.out.println(nombre + "\n");
        System.out.println("\t" + "Hambre : " + hunger + "/950" + "\n");
        System.out.println("\t" + "Limpieza : " + clean + "/950" + "\n");
        System.out.println("\t" + "Felicidad : " + hapiness + "/950" + "\n");
        System.out.println("\t" + "Energia : " + sleepy + "/950" + "\n");
    }
    
}
