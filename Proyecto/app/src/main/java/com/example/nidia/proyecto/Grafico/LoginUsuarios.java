package com.example.nidia.proyecto.Grafico;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nidia.proyecto.ConexionBDD;
import com.example.nidia.proyecto.R;
import com.example.nidia.proyecto.UtilidadesBDD.Utilidades;

public class LoginUsuarios extends Activity {
    EditText user, pass;
    Button registro, log, jugar;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registro = findViewById(R.id.registrarse);
        log = findViewById(R.id.login);

        registro.setOnClickListener(onClickListener);
        log.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pressed(view);
        }
    };

    private void pressed(View view){
        switch(view.getId()){
            case R.id.registrarse:
                //Intent intent = new Intent(this, RegistroUsuarios.class);
                startActivity(new Intent(this, RegistroUsuarios.class));
                break;
            case R.id.login:
                buscarUsuarios();
                break;
        }
    }

    private void buscarUsuarios() {
        ConexionBDD conn = new ConexionBDD(this,"bd_usuarios",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] where = new String[]{user.getText().toString(),pass.getText().toString()};
        Cursor c = db.rawQuery("SELECT * FROM " + Utilidades.tabla_usuarios,where);
        if(c!=null){
            c.moveToFirst();
            do {
                String userName = c.getString(c.getColumnIndex(Utilidades.campo_userName));
                Integer puntucaion = c.getInt(c.getColumnIndex(Utilidades.campo_puntuacion));
                Intent intent = new Intent(this, PantallaJuego.class);
                startActivity(intent);
            }while(c.moveToNext());
        }
    }
}

