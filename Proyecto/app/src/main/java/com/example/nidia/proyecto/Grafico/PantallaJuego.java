package com.example.nidia.proyecto.Grafico;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nidia.proyecto.Funcionamiento.Bolitas;

import com.example.nidia.proyecto.R;

public class PantallaJuego extends AppCompatActivity {
    TextView pjUserName, pjPuntuacion;
    FrameLayout pjCanvas;
    Button pjSalir;
    //HideVisibilityStyle estilo;

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

    private class AreaJuego extends View { //Area donde se encuentra el juego
        private Bolitas bola;
        String action = ""; //Accion realizada en el canvas
        float x = 0, y = 0; //Coordenadas del Jugador
        float x2 = 0, y2 = 0;
        float x2ini = 0, y2ini = 0;
        boolean startingState = false;// Inicio ene el Centro de la pantalla
        Path path = new Path();

        public AreaJuego(Context context) {
            super(context);
            bola = new Bolitas(30, 5, 0);
        }

        public void onDraw(Canvas canvas) {
            Paint paint = paintProperties();

            if (action == "move") {
                float pixelesX = x2ini-x2;
                float pixelesY = y2ini-y2;

                x -= pixelesX;
                y -= pixelesY;

                x2ini = x2;
                y2ini = y2;

                path.reset();
                path.addCircle(x, y, bola.getSize(), Path.Direction.CCW);
            }

            if (startingState == false) {
                x = canvas.getWidth() / 2;
                y = canvas.getHeight() / 2;
                path.addCircle(x, y, bola.getSize(), Path.Direction.CCW);
                canvas.drawPath(path, paint);
                startingState = true;
            }

            canvas.drawPath(path, paint);
        }

        private Paint paintProperties() {
            Paint fillPaint = new Paint();
            fillPaint.setColor(bola.getColor());
            fillPaint.setStrokeWidth(5);
            fillPaint.setStyle(Paint.Style.FILL);

            return fillPaint;
        }

        public boolean onTouchEvent(MotionEvent evt) {
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
