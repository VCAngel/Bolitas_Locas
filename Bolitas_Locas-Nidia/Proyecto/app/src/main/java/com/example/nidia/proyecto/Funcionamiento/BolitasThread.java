package com.example.nidia.proyecto.Funcionamiento;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.nidia.proyecto.Grafico.PantallaJuego;

public class BolitasThread extends Thread {
    Canvas canvas;
    Paint fillPaint, strokePaint;
    Bolitas bola;
    float x, y;

    public BolitasThread(Canvas canvas, Paint fillPaint, Paint strokePaint, Bolitas bola, float x, float y){
        this.canvas = canvas;
        this.fillPaint = fillPaint;
        this.strokePaint = strokePaint;
        this.bola = bola;
        this.x = x+150;
        this.y = y+150;
    }

    @Override
    public void run(){
        try{
            for (int i = 0; i < 10; i++){
                canvas.drawCircle(x,y,bola.getSize(),strokePaint);
                canvas.drawCircle(x,y,bola.getSize(),fillPaint);
                x--;
                y--;
                Thread.sleep(1000);
            }
            Log.println(Log.INFO,"JUE","AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH");
        }catch(Exception e){

        }
    }

}
