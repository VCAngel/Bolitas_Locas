package com.example.nidia.proyecto.Grafico;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nidia.proyecto.R;

public class PantallaJuego extends AppCompatActivity {
    TextView pjUserName, pjPuntuacion;
    Button pjSalir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_juego);
        pjUserName = findViewById(R.id.pjUser);
        pjPuntuacion = findViewById(R.id.pjPuntuacion);
        pjSalir = findViewById(R.id.pjSalir);

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
    }
}
