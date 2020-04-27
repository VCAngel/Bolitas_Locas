package com.example.nidia.proyecto.Grafico;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.nidia.proyecto.ConexionBDD;
import com.example.nidia.proyecto.R;
import com.example.nidia.proyecto.UtilidadesBDD.Utilidades;

public class LoginUsuarios extends Activity {
    EditText lUser, lPass;
    Button lRegistrarse, lLogin;
    ImageView img;
    AnimationDrawable ani;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        img = findViewById(R.id.lAnimacion);
        img.setBackgroundResource(R.drawable.animated);
        ani = (AnimationDrawable) img.getBackground();
        ani.start();

        lRegistrarse = findViewById(R.id.lRegistrarse);
        lLogin = findViewById(R.id.lLogin);

        lUser = findViewById(R.id.lUser);
        lPass = findViewById(R.id.lPass);

        lRegistrarse.setOnClickListener(onClickListener);
        lLogin.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pressed(view);
        }
    };

    private void pressed(View view) {
        switch (view.getId()) {
            case R.id.lRegistrarse:
                //Intent intent = new Intent(this, RegistroUsuarios.class);
                startActivity(new Intent(this, RegistroUsuarios.class));
                break;
            case R.id.lLogin:
                ConexionBDD conn = new ConexionBDD(this, "bd_usuarios", null, 1);
                SQLiteDatabase db = conn.getWritableDatabase();
                Cursor c = db.rawQuery("SELECT * FROM " + Utilidades.tabla_usuarios, null);
                Boolean ban = false;
                if (c.moveToFirst())
                    do {
                        String userName = c.getString(c.getColumnIndex(Utilidades.campo_userName));
                        String pass = c.getString(c.getColumnIndex(Utilidades.campo_pass));
                        int puntuacion = c.getInt(c.getColumnIndex(Utilidades.campo_puntuacion));
                        String punt = String.valueOf(puntuacion);
                        if (userName.equals(lUser.getText().toString()) && pass.equals(lPass.getText().toString())) {
                            Intent intent = new Intent(this, PantallaJuego.class);
                            intent.putExtra("pjrUser", userName);
                            intent.putExtra("pjrPuntuacion", punt);
                            startActivity(intent);
                            finish();
                            //startActivity(new Intent(this, PantallaJuego.class));
                            Toast.makeText(this, "A jugar!!!", Toast.LENGTH_SHORT).show();
                            ban = true;
                            break;
                        }
                    } while (c.moveToNext());
                if (!ban) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("IMPORTANTE");
                    dialog.setMessage("Usuario no encontrado");
                    dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            lUser.setText("");
                            lPass.setText("");
                            lUser.requestFocus();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                }
        }
    }
}


