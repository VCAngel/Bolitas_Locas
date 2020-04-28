package com.example.nidia.proyecto.Grafico;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
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

import com.example.nidia.proyecto.ConexionBDD;
import com.example.nidia.proyecto.Funcionamiento.*;

import com.example.nidia.proyecto.R;

import static com.example.nidia.proyecto.UtilidadesBDD.Utilidades.*;

public class PantallaJuego extends AppCompatActivity {
    TextView pjUserName, pjPuntuacion;
    FrameLayout pjCanvasJugador;
    Button pjSalir;
    boolean obsBande = false, obsBandeM = false;// Para dibujar el obstaculo
    private Hilo1 obsH1;
    private Hilo2 obsH2;// para iniciar los hilos
    private int puntuacion = 0;
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
        puntuacion = Integer.parseInt(pjrPuntuacion);
        pjCanvasJugador.addView(new AreaJuego(this));
        //inicio();// método que inicia los hilos
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
                int punt = Integer.parseInt(pjPuntuacion.getText().toString());
                addPuntuacionBDD(punt);
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

    private void inicio() {//dar inicio a los hilos
        //obsH1 = new Hilo1();
        //obsH1.start();
    }

    private void addPuntuacionBDD(int punt) {
        ConexionBDD conn = new ConexionBDD(PantallaJuego.this, "bd_usuarios", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("UPDATE " + tabla_usuarios + " SET " + campo_puntuacion + " = " + punt + " WHERE " + campo_userName + " = '" + pjUserName.getText().toString() + "'");
        db.close();
    }// --> Guarda la puntuacion en la base de datos


    private class AreaJuego extends View { // --> Clase que representa el área donde se encuentra el juego
        private Bolitas jugador;
        private Path pathJugador = new Path();
        private Obstaculo obs;
        private Path pathObs = new Path();//Declaré un path para el obstaculo
        private int contadorHilo = 0;
        private int cantidadComida = 10; // --> Cantidad de comida predefinida dentro del area de juego

        private ListaComponente listaComida = new ListaComponente(); //Lista de Comida, para mantener el seguimiento y control de cada objeto

        private String action = ""; //Accion realizada en el canvas

        private float xCalculada = 0, yCalculada = 0; //Coordenadas calculadas del jugador
        private float x2 = 0, y2 = 0; //Punto hacia donde se arrastra la pantalla
        private float x2ini = 0, y2ini = 0; //Punto donde se presiona la pantalla inicialmente
        private float canvasSizeX = 0, canvasSizeY = 0;

        private boolean startingState = false;// Estado inicial del juego

        public AreaJuego(Context context) { // --> Constructor inicia de la clase
            super(context);

            for (int i = 0; i < cantidadComida; i++) {
                listaComida.addBegin(new NodoComida(new Path()));
            }
            jugador = new Bolitas(35, 5);
            obs = new Obstaculo(200, 200, 4, 400, 400);//No se como sea mejor hacerlos
        }

        public void onDraw(Canvas canvas) { //Se dibuja el canvas, se mantiene actualizándose continuamente
            super.onDraw(canvas);
            Paint paintFill = paintPropertiesFill();
            Paint paintStroke = paintPropertiesStroke();
            Paint paintObs = paintPropertiesRect();
            canvasSizeX = canvas.getWidth();
            canvasSizeY = canvas.getHeight();

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
                }

                xCalculada = canvasSizeX / 2;
                yCalculada = canvasSizeY / 2;

                jugador.setPosiX(xCalculada);
                jugador.setPosiY(yCalculada);
                pathJugador.addCircle(xCalculada, yCalculada, jugador.getSize(), Path.Direction.CCW);

                startingState = true;
            }

            if (action.equals("move")) {
                /*
                Aqui se evalua el estado del MotionEvent sobre el Canvas, una vez evaluado se hacen los calculos necesarios
                para manejar el posicionamiento del jugador
                 */
                float pixelesX = x2ini - x2;
                float pixelesY = y2ini - y2;

                // vvv Para mantener al jugador dentro del area de la pantalla vvv
                if (jugador.getPosiX() >= canvasSizeX) {
                    xCalculada = canvasSizeX - 0.1f;
                } else if (jugador.getPosiX() <= 0) {
                    xCalculada = 0.1f;
                } else {
                    xCalculada -= pixelesX;
                }

                if (jugador.getPosiY() >= canvasSizeY) {
                    yCalculada = canvasSizeY - 0.1f;
                } else if (jugador.getPosiY() <= 0) {
                    yCalculada = 0.1f;
                } else {
                    yCalculada -= pixelesY;
                }
                // ^^^ - - - - - - - - - - - - - - - - - - - - - - - - - - - ^^^

                x2ini = x2;
                y2ini = y2;

                jugador.setPosiX(xCalculada);
                jugador.setPosiY(yCalculada);
                pathJugador.reset();
                pathJugador.addCircle(xCalculada, yCalculada, jugador.getSize(), Path.Direction.CCW);
            }

            verifyNodeEaten(listaComida, jugador);
            addComidaRandom(listaComida);

            for (int i = 0; i < listaComida.size(); i++) {
                try {
                    canvas.drawPath(listaComida.getAt(i).getPath(), paintProperties(i));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            canvas.drawPath(pathJugador, paintFill);
            canvas.drawPath(pathJugador, paintStroke);

            if (contadorHilo < 2) {
                // --> Se usa por que el canvas cambia de referencia de memoria 2 veces mientras se
                // inicializa, de esta manera podemos controlarlo desde los hilos secundarios
                obsH1 = new Hilo1(canvas, pathObs, paintObs);
                if (contadorHilo == 1) {
                    obsH1 = new Hilo1(canvas, pathObs, paintObs);
                    obsH1.start();
                }
                contadorHilo++;
            }

            invalidate();
        }

        public void verifyNodeEaten(ListaComponente listaComponente, Bolitas bolita) {
            int listaSize = listaComponente.size();
            String punt;
            for (int i = 0; i < listaSize; i++) {
                try {
                    if (listaComponente.getAt(i).isBetween(jugador.getPosiX() - 25, jugador.getPosiX() + 25,
                            jugador.getPosiY() - 25, jugador.getPosiY() + 25)) {
                        listaComponente.deleteAt(i);
                        listaSize--;
                        puntuacion += listaComponente.getAt(i).getComida().getValue();
                        punt = String.valueOf(puntuacion);
                        pjPuntuacion.setText(punt);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }// --> Comprueba si un nodo se encuentra en la misma posicion que el jugador

        private void addComidaRandom(ListaComponente listaComponente) {
            int listaSize = listaComponente.size();
            int randomNum = (int) (Math.random() * 50) + 1;
            if (randomNum == 1) {
                listaComponente.addBegin(new NodoComida(new Path()));
                try {
                    listaComponente.getAt(0).setPosiX((float) (Math.random() * (canvasSizeX)) + 1);
                    listaComponente.getAt(0).setPosiY((float) (Math.random() * (canvasSizeY)) + 1);
                    listaComponente.getAt(0).getPath().addCircle(listaComponente.getAt(0).getPosiX(), listaComponente.getAt(0).getPosiY(),
                            listaComponente.getAt(0).getComida().getSize(), Path.Direction.CCW);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (listaSize <= 3) { // --> Evita que ocurra excepcion por no tener nodos en la lista
                for (int i = 0; i < 5; i++) {
                    listaComponente.addBegin(new NodoComida(new Path()));
                    try {
                        listaComponente.getAt(i).setPosiX((float) (Math.random() * (canvasSizeX)) + 1);
                        listaComponente.getAt(i).setPosiY((float) (Math.random() * (canvasSizeY)) + 1);
                        listaComponente.getAt(0).getPath().addCircle(listaComponente.getAt(i).getPosiX(), listaComponente.getAt(i).getPosiY(),
                                listaComponente.getAt(0).getComida().getSize(), Path.Direction.CCW);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }// --> agrega comida de manera aleatoria al area de juego

        private Paint paintPropertiesFill() {
            Paint fillPaint = new Paint();
            fillPaint.setColor(jugador.getColor());
            fillPaint.setStyle(Paint.Style.FILL);

            return fillPaint;
        } // --> Propiedades de relleno de la bolita del jugador

        private Paint paintPropertiesStroke() {
            Paint strokePaint = new Paint();
            strokePaint.setColor(jugador.getStroke());
            strokePaint.setStyle(Paint.Style.STROKE);
            strokePaint.setStrokeWidth(10);

            return strokePaint;
        }// --> Propiedades de delineado de la bolita del jugador

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

        private Paint paintPropertiesRect() {
            Paint fillPaint = new Paint();
            fillPaint.setColor(obs.getColor());
            fillPaint.setStyle(Paint.Style.FILL);

            return fillPaint;
        }// --> Propiedades de trazado de obstaculos

        public boolean onTouchEvent(MotionEvent evt) { // --> Revisa si se llevó a cabo un OnTouchEvent, para realizar acciones
            if (evt.getAction() == MotionEvent.ACTION_DOWN) {
                x2ini = evt.getX();
                y2ini = evt.getY();
                invalidate();
            }
            x2 = evt.getX();
            y2 = evt.getY();

            if (evt.getAction() == MotionEvent.ACTION_MOVE) {
                action = "move";
                invalidate();
            }
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

    private class Hilo1 extends Thread {
        Canvas canvas;
        Path obsPath;
        Paint paint;

        public Hilo1(Canvas canvas, Path obsPath, Paint paint) {
            this.canvas = canvas;
            this.obsPath = obsPath;
            this.paint = paint;
        }

        @Override
        public void run() {
            int x = 5000;
            /*do {
                x = (int) (Math.random() * 10000) + 1;
            } while (x < 2500);*/
            try {
                Thread.sleep(x);// Duerme aleatorioamente de entre 2500 a 10000 milis
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        randomRectX(obsPath);
                        //obsH2 = new Hilo2();
                        //obsH2.start();
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void randomRectX(Path obsPath) { // --> Genera rectangulos en una de 3 posiciones
            int randomPosi = (int) (Math.random() * 1) + 1; // --> Para determinar si aparecerá arriba, centro, abajo
            int randomLado = (int) (Math.random() * 2) + 1; // --> 1 = De izq. a der. || 2 = De der. a izq.
            float canvasSizeX = canvas.getWidth();
            float canvasSizeY = canvas.getHeight();

            float left, top, right, bottom;
            switch (randomPosi) {
                case 1:
                    top = 0;
                    bottom = canvasSizeY / 3;
                    if (randomLado == 1) {
                        left = -canvasSizeX / 3;
                        right = 0;
                        do { // --> Acerca el rectangulo a la zona de juego
                            obsPath.reset();
                            obsPath.addRect(left, top, right, bottom, Path.Direction.CW);
                            canvas.drawPath(obsPath, paint);
                            left += 0.1f;
                            right += 0.1f;
                        } while (right < canvasSizeX / 24);

                        try {
                            currentThread().sleep(3000); // --> El rectangulo espera 3 segundos
                            do { // --> El rectangulo avanza hasta salir del canvas
                                obsPath.reset();
                                obsPath.addRect(left, top, right, bottom, Path.Direction.CW);
                                canvas.drawPath(obsPath, paint);
                                left++;
                                right++;
                            } while (left > canvasSizeX);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        left = canvasSizeX;
                        right = (canvasSizeX / 3) + canvasSizeX;
                        do {// --> Acerca el rectangulo a la zona de juego
                            obsPath.reset();
                            obsPath.addRect(left, top, right, bottom, Path.Direction.CW);
                            canvas.drawPath(obsPath, paint);
                            left -= 0.1f;
                            right -= 0.1f;
                        } while (left > (canvasSizeX - (canvasSizeX / 24)));

                        try {
                            currentThread().sleep(3000); // --> El rectangulo espera 3 segundos
                            do { // --> El rectangulo avanza hasta salir del canvas
                                obsPath.reset();
                                obsPath.addRect(left, top, right, bottom, Path.Direction.CW);
                                canvas.drawPath(obsPath, paint);
                                left--;
                                right--;
                            } while (right < 0);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                /////////
                default:
                    System.out.println("aaaaah");
            }
        }
    }

    private class Hilo2 extends Thread {
        @Override
        public void run() {
            int x = 0;
            do {
                x = (int) (Math.random() * 10000) + 1;
            } while (x < 2500);
            try {
                Thread.sleep(x);//Duerme aleatorioamente de entre 2500 a 10000 milis
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        obsBande = true;
                        Toast.makeText(PantallaJuego.this, "Terminó hilo2, deberia aparecer rectangulo", Toast.LENGTH_SHORT).show();
                        //obsH1 = new Hilo1();
                        //obsH1.start();
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
