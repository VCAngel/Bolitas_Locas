package com.example.nidia.proyecto.Grafico;

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

import com.example.nidia.proyecto.R;

public class PantallaJuego extends AppCompatActivity {
    TextView pjUserName, pjPuntuacion;
    FrameLayout pjCanvas;
    Button pjSalir;
    //HideVisibilityStyle estilo;  //TODO Falta ocultar la barra de tareas y de acciones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_juego);
        pjUserName = findViewById(R.id.pjUser);
        pjPuntuacion = findViewById(R.id.pjPuntuacion);
        pjSalir = findViewById(R.id.pjSalir);
        pjCanvas = findViewById(R.id.pjCanvas);
        //estilo = new HideVisibilityStyle(this);

        pjSalir.setOnClickListener(onClickListener);

        String pjrUser = getIntent().getStringExtra("pjrUser");
        String pjrPuntuacion = getIntent().getStringExtra("pjrPuntuacion");
        pjUserName.setText(pjrUser);
        pjPuntuacion.setText(pjrPuntuacion);
        pjCanvas.addView(new AreaJuego(this));
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

    private class AreaJuego extends View { // --> Clase que representa el área donde se encuentra el juego
        private Bolitas jugador;

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
            jugador = new Bolitas(30,5,0);
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

            for (int i = 0; i < comida.length; i++)
                canvas.drawPath(pathComida[i], paintProperties(i));
            canvas.drawPath(pathJugador, paint);
        }

        private Paint paintProperties() {
            Paint fillPaint = new Paint();
            fillPaint.setColor(jugador.getColor());
            fillPaint.setStyle(Paint.Style.FILL);

            return fillPaint;
        } // --> Propiedades de trazado de las bolitas
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
    }

}
