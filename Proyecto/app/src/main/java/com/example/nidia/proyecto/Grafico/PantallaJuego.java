package com.example.nidia.proyecto.Grafico;

<<<<<<< HEAD
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
=======
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.nidia.proyecto.Funcionamiento.*;
>>>>>>> master

import com.example.nidia.proyecto.R;

public class PantallaJuego extends AppCompatActivity {
    TextView pjUserName, pjPuntuacion;
<<<<<<< HEAD
    Button pjSalir;
=======
    FrameLayout pjCanvas;
    Button pjSalir;
    boolean obsBande = false, obsBandeM = false;// Para dibujar el obstaculo
    private Hilo1 obsH1;
    private Hilo2 obsH2;// para iniciar los hilos
    //HideVisibilityStyle estilo;  //TODO Falta ocultar la barra de tareas y de acciones

>>>>>>> master
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_juego);
        pjUserName = findViewById(R.id.pjUser);
        pjPuntuacion = findViewById(R.id.pjPuntuacion);
        pjSalir = findViewById(R.id.pjSalir);
<<<<<<< HEAD

        pjSalir.setOnClickListener(onClickListener);

        String pjrUser = getIntent().getStringExtra("pjrUser");
        String pjrPuntuacion = getIntent().getStringExtra("pjrPuntuacion");
        pjUserName.setText(pjrUser);
        pjPuntuacion.setText(pjrPuntuacion);
        Toast.makeText(this, pjrPuntuacion, Toast.LENGTH_SHORT).show();
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pressed(view);
        }
    };
    private void pressed(View view){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Importante");
        dialog.setMessage("¿Seguro que desea salir de este juego?");
        dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.exit(0);
            }
        });
        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        dialog.show();
=======
        pjCanvas = findViewById(R.id.pjCanvas);
        //estilo = new HideVisibilityStyle(this);

        pjSalir.setOnClickListener(onClickListener);

        String pjrUser = getIntent().getStringExtra("pjrUser");
        String pjrPuntuacion = getIntent().getStringExtra("pjrPuntuacion");
        pjUserName.setText(pjrUser);
        pjPuntuacion.setText(pjrPuntuacion);
<<<<<<< Updated upstream
        pjCanvas.addView(new AreaJuego(this));
=======
        pjCanvasJugador.addView(new AreaJuego(this));
        inicio();// método que inicia los hilos
>>>>>>> Stashed changes
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            exit(view);
        }
    };

    private void exit(View view) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Importante");
        dialog.setMessage("¿Desea salir de este juego?");
        dialog.setPositiveButton("Salir", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                System.exit(0);
            }
        });
        dialog.setNegativeButton("Volver al Juego", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void inicio(){//dar inicio a los hilos
        obsH1 = new Hilo1();
        obsH1.start();
    }

    private class AreaJuego extends View { // --> Clase que representa el área donde se encuentra el juego
        private Bolitas jugador;
<<<<<<< Updated upstream
=======
        private Path pathJugador = new Path();
        private Obstaculo obs;
        private Path pathObs = new Path();//Declaré un path para el obstaculo
        private int cantidadComida = 10; // --> Cantidad de comida predefinida dentro del area de juego
>>>>>>> Stashed changes

        private Comida[] comida = new Comida[100]; //Cantidad de comida en el juego -->
        //El arreglo anterior es temporal, solo para mostrar el funcionamiento, se usarán Listas
        //TODO Cesar! Aquí es donde se instanciarán las listas de Bolitas y Comida, las clases están
        // en la carpeta de .Funcionamiento, solo falta hacer las clases de las listas, wapo

        private String action = ""; //Accion realizada en el canvas
        private float xCalculada = 0, yCalculada = 0; //Coordenadas calculadas de la comida
        private float x2 = 0, y2 = 0; //Punto hacia donde se arrastra la pantalla
        private float x2ini = 0, y2ini = 0; //Punto donde se presiona la pantalla inicialmente
        private float[] posiX = new float[comida.length], posiY = new float[comida.length]; //Adicion que se le hace a x/yCalculada
        private boolean startingState = false;// Estado inicial del juego

        private Path pathJugador = new Path();
        private Path[] pathComida = new Path[comida.length];

        public AreaJuego(Context context) { // --> Constructor inicla de la clase
            super(context);
            for(int i = 0; i < comida.length; i++){
                comida[i] = new Comida(10, (int)(Math.random()*8), 0,0);
                pathComida[i] = new Path();
            }
<<<<<<< Updated upstream
            jugador = new Bolitas(30,5,0);
=======
            jugador = new Bolitas(35, 5);
            obs = new Obstaculo(0,0,4,200,200);//No se si es mejor hacerlo con arreglos
>>>>>>> Stashed changes
        }

        public void onDraw(Canvas canvas) { //Se dibuja el canvas, se mantiene actualizándose continuamente
            Paint paint = paintProperties();

            if (action == "move") {
                float pixelesX = x2ini - x2;
                float pixelesY = y2ini - y2;

                xCalculada -= pixelesX;
                yCalculada -= pixelesY;
                x2ini = x2;
                y2ini = y2;

                for (int i = 0; i < comida.length; i++) {
                    pathComida[i].reset();
                    pathComida[i].addCircle(xCalculada+posiX[i], yCalculada+posiY[i],
                                                comida[i].getSize(), Path.Direction.CCW);
                }
                /*
                pathJugador.reset();
                pathJugador.addCircle(x, y, jugador.getSize(), Path.Direction.CCW);
                 */
            }

            if (startingState == false) { //Estado Inicial del juego
                for (int i = 0; i < comida.length; i++){
                    posiX[i] = (float)(Math.random()*(canvas.getWidth()*10))+1;
                    posiY[i] = (float)(Math.random()*(canvas.getHeight()*10))+1;
                    pathComida[i].addCircle(posiX[i],posiY[i], comida[i].getSize(), Path.Direction.CCW);
                    canvas.drawPath(pathComida[i], paintProperties(i));
                }
                pathJugador.addCircle(canvas.getWidth()/2, canvas.getHeight()/2, jugador.getSize(), Path.Direction.CCW);
                canvas.drawPath(pathJugador, paint);
                startingState = true;
            }
<<<<<<< Updated upstream
=======
            canvas.drawPath(pathJugador, paintFill);
            canvas.drawPath(pathJugador, paintStroke);
            if(obsBande){// se dibuja rectangulo cuando el hilo lo ermite (Creo que no funciona)
                pathObs.addRect(obs.getLeft()+1, obs.getTop()+1, obs.getRight()+1, obs.getBottom()+1, Path.Direction.CCW);
                canvas.drawPath(pathObs, paintPropertiesO());
                //Esto solo dibuja un rectangulo, pero no lo mueve ni nada aun
                obsBande = false;
            }
>>>>>>> Stashed changes

            for (int i = 0; i < comida.length; i++)
                canvas.drawPath(pathComida[i], paintProperties(i));
            canvas.drawPath(pathJugador, paint);
        }

        private Paint paintProperties() {
            Paint fillPaint = new Paint();
            fillPaint.setColor(jugador.getColor());
            fillPaint.setStyle(Paint.Style.FILL);

            return fillPaint;
<<<<<<< Updated upstream
        } // --> Propiedades de trazado de las bolitas
=======
        } // --> Propiedades de relleno de la bolita del jugador

        private Paint paintPropertiesStroke() {
            Paint strokePaint = new Paint();
            strokePaint.setColor(jugador.getStroke());
            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setStrokeWidth(10);

            return strokePaint;
        }// --> Propiedades de delineado de la bolita del jugador
        private Paint paintPropertiesO() {
            Paint fillPaint = new Paint();
            fillPaint.setColor(obs.getColor());
            fillPaint.setStyle(Paint.Style.FILL);

            return fillPaint;
        }// --> Propiedades de trazado de obstaculos

>>>>>>> Stashed changes
        private Paint paintProperties(int value) {
            Paint fillPaint = new Paint();
            fillPaint.setColor(comida[value].getColor());
            fillPaint.setStyle(Paint.Style.FILL);

            return fillPaint;
        } // --> Propiedades de trazado de la comida

        public boolean onTouchEvent(MotionEvent evt) { // --> Revisa si se llevó a cabo un OnTouchEvent, para realizar acciones
            if (evt.getAction() == MotionEvent.ACTION_DOWN) {
                x2ini = evt.getX();
                y2ini = evt.getY();
            }
            x2 = evt.getX();
            y2 = evt.getY();
            if (evt.getAction() == MotionEvent.ACTION_MOVE)
                action = "move";

            invalidate();
            return true;
        }
>>>>>>> master
    }

<<<<<<< Updated upstream
=======

    //TODO Hacer que funcione el Hilo secundario, no jalaaa aaaaaah
    private class BolitasThread implements Runnable { // --> Clase encargada de la ejecucion seucndaria de las Bolitas
        private Canvas canvas;

        public BolitasThread(Canvas canvas) {
            this.canvas = canvas;
        }

        @Override
        public void run() {
            try {
                for (int i = 0; i <= 10; i++) {
                    Thread.sleep(5000);
                    Toast.makeText(PantallaJuego.this, "AAAAAAAAH", Toast.LENGTH_SHORT).show();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private class Hilo1 extends Thread{
        @Override
        public void run() {
            int x = 0;
            do{
                x = (int)(Math.random()*10000)+1;
            }while(x<2500);
            try {
                Thread.sleep(x);// Duerme aleatorioamente de entre 2500 a 10000 milis
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        obsBande = true;// Permitir que se dibuje el rectangulo en onDraw
                        Toast.makeText(PantallaJuego.this, "Terminó hilo1, deberia aparecer rectangulo", Toast.LENGTH_SHORT).show();
                        obsH2 = new Hilo2();
                        obsH2.start();
                    }
                });
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    private class Hilo2 extends Thread{
        @Override
        public void run() {
            int x = 0;
            do{
                x = (int)(Math.random()*10000)+1;
            }while(x<2500);
            try {
                Thread.sleep(x);//Duerme aleatorioamente de entre 2500 a 10000 milis
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        obsBande = true;
                        Toast.makeText(PantallaJuego.this, "Terminó hilo2, deberia aparecer rectangulo", Toast.LENGTH_SHORT).show();
                        obsH1 = new Hilo1();
                        obsH1.start();
                    }
                });
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
>>>>>>> Stashed changes
}

