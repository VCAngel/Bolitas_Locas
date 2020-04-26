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
import android.widget.Toast;

import com.example.nidia.proyecto.Funcionamiento.*;

import com.example.nidia.proyecto.R;

public class PantallaJuego extends AppCompatActivity {
    TextView pjUserName, pjPuntuacion;
    FrameLayout pjCanvasJugador;
    Button pjSalir;
    //HideVisibilityStyle estilo;  //TODO Falta ocultar la barra de tareas y de acciones

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_juego);
        pjUserName = findViewById(R.id.pjUser);
        pjPuntuacion = findViewById(R.id.pjPuntuacion);
        pjSalir = findViewById(R.id.pjSalir);
        pjCanvasJugador = findViewById(R.id.pjCanvasJugador);
        //estilo = new HideVisibilityStyle(this);

        pjSalir.setOnClickListener(onClickListener);

        String pjrUser = getIntent().getStringExtra("pjrUser");
        String pjrPuntuacion = getIntent().getStringExtra("pjrPuntuacion");
        pjUserName.setText(pjrUser);
        pjPuntuacion.setText(pjrPuntuacion);
        pjCanvasJugador.addView(new AreaJuego(this));
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
        private Path pathJugador = new Path();
        private int cantidadComida = 16; // --> Cantidad de comida predefinida dentro del area de juego

        private ListaComponente listaComida = new ListaComponente(); //Lista de Comida, para mantener el seguimiento y control de cada objeto

        private String action = ""; //Accion realizada en el canvas
        private float xCalculada = 0, yCalculada = 0; //Coordenadas calculadas de la comida
        private float x2 = 0, y2 = 0; //Punto hacia donde se arrastra la pantalla
        private float x2ini = 0, y2ini = 0; //Punto donde se presiona la pantalla inicialmente
        private float canvasSizeX = 0, canvasSizeY = 0;
        private float areaSpawnX = 0, areaSpawnY = 0;
        private boolean startingState = false;// Estado inicial del juego

        public AreaJuego(Context context) { // --> Constructor inicia de la clase
            super(context);

            for (int i = 0; i < cantidadComida; i++) {
                listaComida.add(new NodoComida(new Path()));
            }
            jugador = new Bolitas(30, 5, 0);
        }

        public void onDraw(Canvas canvas) { //Se dibuja el canvas, se mantiene actualizándose continuamente
            super.onDraw(canvas);
            Paint paint = paintProperties();
            canvasSizeX = canvas.getWidth();
            canvasSizeY = canvas.getHeight();
            areaSpawnX = canvasSizeX / 6;
            areaSpawnY = canvasSizeY / 4;
            //areaSpawnX|Y --> Las areas donde pueden aparecer los objetos fuera de pantalla

            if (!startingState) { //Estado Inicial del juego
                for (int i = 0; i < listaComida.size(); i++) {
                    try {
                        listaComida.getAt(i).setPosiX((float) (Math.random() * (canvasSizeX)) + 1);
                        listaComida.getAt(i).setPosiY((float) (Math.random() * (canvasSizeY)) + 1);
                        listaComida.getAt(i).getPath().addCircle(listaComida.getAt(i).getPosiX(), listaComida.getAt(i).getPosiY(),
                                listaComida.getAt(i).getComida().getSize(), Path.Direction.CCW);

                        canvas.drawPath(listaComida.getAt(i).getPath(), paintProperties(i));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    verifyNodeState(listaComida);
                }

                pathJugador.addCircle(canvasSizeX / 2, canvasSizeY / 2, jugador.getSize(), Path.Direction.CCW);
                startingState = true;
            }

            if (action.equals("move")) {
                /*
                Aqui se evalua el estado del MotionEvent sobre el Canvas, una vez evaluado se hacen los calculos necesarios
                para manejar el posicionamiento de la Comida
                Puesto que la comida se encuentra en ubicaciones aleatorias se tiene que hacer un calculo un poco
                raro de entender, sin embargo, muy practico
                 */
                float pixelesX = x2ini - x2;
                float pixelesY = y2ini - y2;

                xCalculada -= pixelesX;
                yCalculada -= pixelesY;
                x2ini = x2;
                y2ini = y2;

                for (int i = 0; i < listaComida.size(); i++) {
                    try {
                        listaComida.getAt(i).getPath().reset();
                        listaComida.getAt(i).getPath().addCircle(xCalculada + listaComida.getAt(i).getPosiX(), yCalculada + listaComida.getAt(i).getPosiY(),
                                listaComida.getAt(i).getComida().getSize(), Path.Direction.CCW);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            verifyNodeState(listaComida); //Verificar que los nodos se encuentran en el area de juego

            for (int i = 0; i < listaComida.size(); i++) {
                try {
                    if (!listaComida.getAt(i).isDentroAreaJuego()) { // --> Si los nodos no se encuentran dentro del area de juego, estos se eliminan para ahorrar memoria
                        listaComida.deleteAt(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            verifyNodeQuantity(listaComida, canvas);

            for (int i = 0; i < listaComida.size(); i++) {
                try {
                    canvas.drawPath(listaComida.getAt(i).getPath(), paintProperties(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            canvas.drawPath(pathJugador, paint);
        }

        public void verifyNodeQuantity(ListaComponente listaComponente, Canvas canvas) {
            int listaTamaño = listaComponente.size();
            if (listaTamaño < cantidadComida) {
                try {
                    for (int i = listaTamaño; i <= cantidadComida; i++) {
                        listaComponente.add(new NodoComida(new Path()));
                        listaComida.getAt(i).setPosiX((float) (Math.random() * ()) + 1);
                        listaComida.getAt(i).setPosiY((float) (Math.random() * (canvasSizeY)) + 1);

                        listaComponente.getAt(i).getPath().addCircle(canvasSizeX + areaSpawnX, canvasSizeY + areaSpawnY,
                                listaComponente.getAt(i).getComida().getSize(), Path.Direction.CCW);
                        canvas.drawPath(listaComponente.getAt(i).getPath(), paintProperties(i));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }// --> Comprueba que hay suficientes nodos dentro del area de juego, en caso contrario, los agrega

        private void verifyNodeState(ListaComponente listaComponente) {
            for (int i = 0; i < listaComponente.size(); i++) {
                try {
                    float positionX = xCalculada + listaComponente.getAt(i).getPosiX();
                    float positionY = yCalculada + listaComponente.getAt(i).getPosiY();

                    if (positionX > (canvasSizeX + areaSpawnX)) // --> X positivo en coordenadas
                        listaComponente.getAt(i).setDentroAreaJuego(false);
                    if (positionX < (-areaSpawnX)) // --> X negativo en coordenadas
                        listaComponente.getAt(i).setDentroAreaJuego(false);
                    if (positionY > (canvasSizeY + areaSpawnY)) // --> Y positivo en coordenadas
                        listaComponente.getAt(i).setDentroAreaJuego(false);
                    if (positionY < (-areaSpawnY)) // --> Y negativo en coordenadas
                        listaComponente.getAt(i).setDentroAreaJuego(false);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }// --> Comprueba si los nodos se encuentran dentro del area de juego, y les establece un boolean

        private Paint paintProperties() {
            Paint fillPaint = new Paint();
            fillPaint.setColor(jugador.getColor());
            fillPaint.setStyle(Paint.Style.FILL);

            return fillPaint;
        } // --> Propiedades de trazado de las bolitas

        private Paint paintProperties(int value) {
            Paint fillPaint = new Paint();
            try {
                fillPaint.setColor(listaComida.getAt(value).getComida().getColor());
            } catch (Exception e) {
                e.printStackTrace();
            }
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
}
