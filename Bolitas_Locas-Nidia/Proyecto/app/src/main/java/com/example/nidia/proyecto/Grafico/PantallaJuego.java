package com.example.nidia.proyecto.Grafico;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nidia.proyecto.Funcionamiento.Bolitas;

import com.example.nidia.proyecto.R;

public class PantallaJuego extends AppCompatActivity {
    TextView pjUserName, pjPuntuacion;
    FrameLayout pjCanvas;
    Button pjSalir;
    AreaJuego area;
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
        pjCanvas.setOnDragListener(onDragListener);

        String pjrUser = getIntent().getStringExtra("pjrUser");
        String pjrPuntuacion = getIntent().getStringExtra("pjrPuntuacion");
        pjUserName.setText(pjrUser);
        pjPuntuacion.setText(pjrPuntuacion);
        //Toast.makeText(this, pjrPuntuacion, Toast.LENGTH_SHORT).show();
        area = new AreaJuego(this);
        pjCanvas.addView(area);

        //new HiloSecundario().execute();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pressed(view);
        }
    };

    View.OnDragListener onDragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Toast.makeText(PantallaJuego.this, "EMPECE A ARRASTRAR SJSJS", Toast.LENGTH_SHORT).show();
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Toast.makeText(PantallaJuego.this, "YA NO WE AAAH", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    };

    private void pressed(View view) {
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
    }

    private class AreaJuego extends View { //Area donde se encuentra el juego
        private Bolitas bola;
        float x, y;

        public AreaJuego(Context context) {
            super(context);
            bola = new Bolitas(30, 1, 0);
        }

        public void onDraw(Canvas canvas) {
            Paint fillPaint = new Paint();
            fillPaint.setColor(bola.getColor());
            fillPaint.setStrokeWidth(5);
            fillPaint.setStyle(Paint.Style.FILL);

            Paint strokePaint = new Paint();
            strokePaint.setColor(bola.getStroke());
            strokePaint.setStrokeWidth(5);
            strokePaint.setStyle(Paint.Style.FILL_AND_STROKE);

            canvas.drawCircle(canvas.getWidth()/2,canvas.getHeight()/2,bola.getSize(),fillPaint);
        }

        /*public boolean onTouchEvent(MotionEvent event){
            switch(event.getAction()){
                case MotionEvent.ACTION_MOVE:
                    Toast.makeText(PantallaJuego.this,"ARRASTRO OLV", Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }*/

    }

    private static class HiloSecundario extends AsyncTask<Void, Void, String> {
        float x, y;

        @Override
        protected String doInBackground(Void... voids) {
            try {
                for (int i = 0; i < 100; i++) {
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Log.getStackTraceString(e);
            }
            return "";
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {
            System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAH");
        }
    }
}
