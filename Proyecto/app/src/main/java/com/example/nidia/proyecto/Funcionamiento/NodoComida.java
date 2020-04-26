package com.example.nidia.proyecto.Funcionamiento;

import android.graphics.Path;

public class NodoComida {
    private Comida comida; //Se guarda el objeto de Comida, y se meterá en una lista
    private Path comidaPath;
    private float posiX = 0, posiY = 0;
    //Adicion que se le hace a x/y Calculada (dentro de la clase AreaJuego) para que sus coordenadas sean adecuadas
    //Tambien se utiliza para que los puntos tengan posiciones iniciales aleatorias

    private NodoComida siguiente;
    private boolean dentroAreaJuego;

    public NodoComida() {
        this.siguiente = null;
        this.comida = null;
        this.comidaPath = null;
        this.dentroAreaJuego = true;
    }

    public NodoComida(Path comidaPath) {
        this.comida = new Comida(10, (int) (Math.random() * 8));
        this.comidaPath = comidaPath;
        this.dentroAreaJuego = true;
        this.siguiente = null;
    }

    public Comida getComida() {
        return comida;
    }

    public Path getPath() {
        return comidaPath;
    }

    public NodoComida getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoComida siguiente) {
        this.siguiente = siguiente;
    }

    public float getPosiX() {
        return posiX;
    }

    public void setPosiX(float posiX) {
        this.posiX = posiX;
    }

    public float getPosiY() {
        return posiY;
    }

    public void setPosiY(float posiY) {
        this.posiY = posiY;
    }

    public void setDentroAreaJuego(boolean dentroAreaJuego){
        this.dentroAreaJuego = dentroAreaJuego;
    }

    public boolean isDentroAreaJuego(){
        return dentroAreaJuego;
    }
}
