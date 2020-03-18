package com.example.nidia.proyecto.Grafico;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nidia.proyecto.ConexionBDD;
import com.example.nidia.proyecto.R;
import com.example.nidia.proyecto.UtilidadesBDD.Utilidades;

public class RegistroUsuarios extends Activity {
    Button registrarse;
    EditText userName, cuenta, pass;
    Integer num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        registrarse = findViewById(R.id.registrar);
        userName = findViewById(R.id.userName);
        cuenta = findViewById(R.id.cuenta);
        pass = findViewById(R.id.pass);

        registrarse.setOnClickListener(onClickListener);
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
                registrarUsuarios();
                break;
        }
    }

    private void registrarUsuarios() {
        ConexionBDD conn = new ConexionBDD(this,"bd_usuarios",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        num = (int)(Math.random() * 2000)+1;
        values.put(Utilidades.campo_id,num);
        values.put(Utilidades.campo_userName,userName.getText().toString());
        values.put(Utilidades.campo_cuenta,cuenta.getText().toString());
        values.put(Utilidades.campo_pass,pass.getText().toString());
        values.put(Utilidades.campo_puntuacion,0);

        Long id_resu = db.insert(Utilidades.tabla_usuarios,Utilidades.campo_id,values);
        Toast.makeText(this, "Registrado " + id_resu, Toast.LENGTH_SHORT).show();
        db.close();
    }
}
