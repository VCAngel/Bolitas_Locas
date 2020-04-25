package com.example.nidia.proyecto.Grafico;

import android.app.Activity;
import android.content.ContentValues;
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

public class RegistroUsuarios extends Activity {
    Button ruRegistrar;
    EditText ruUserName, ruCuenta, ruPass;
    Integer num = 0;
    ImageView img;
    AnimationDrawable ani;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        ruRegistrar = findViewById(R.id.ruRegistrar);
        ruUserName = findViewById(R.id.ruUserName);
        ruCuenta = findViewById(R.id.ruCuenta);
        ruPass = findViewById(R.id.ruPass);
        img = findViewById(R.id.ruAnimacion);
        img.setBackgroundResource(R.drawable.animated);
        ani = (AnimationDrawable) img.getBackground();
        ani.start();
        ruRegistrar.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            pressed(view);
        }
    };
    private void pressed(View view){
        switch(view.getId()){
            case R.id.ruRegistrar:
                if(ruUserName.getText().toString().equals("")){
                    Toast.makeText(this, "Porfavor introduzca un nombre de usuario", Toast.LENGTH_SHORT).show();
                }else if(ruCuenta.getText().toString().equals("")){
                    Toast.makeText(this, "Porfavor introduzca una cuenta", Toast.LENGTH_SHORT).show();
                }else if(ruPass.getText().toString().equals("")){
                    Toast.makeText(this, "Porfavor introduzca una contraseña", Toast.LENGTH_SHORT).show();
                }else{
                    registrarUsuarios();
                }
                break;
        }
    }

    private void registrarUsuarios() {
        ConexionBDD conn = new ConexionBDD(this,"bd_usuarios",null,1);
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        Cursor c = db.rawQuery("SELECT * FROM " + Utilidades.tabla_usuarios,null);
        boolean ban = true;

        //db.execSQL("DELETE FROM "+ Utilidades.tabla_usuarios); //BORRAR LOS REGISTROS DE LA BASE DE DATOS SOLO PARA PRUEBAS
        num = (int)(Math.random())+1;
//BUSCAR VALORES REPETIDOS
<<<<<<< HEAD
       if(c.moveToFirst()){
=======
        if(c.moveToFirst()){
>>>>>>> master
            do{
                String userName = c.getString(c.getColumnIndex(Utilidades.campo_userName));
                String cuenta = c.getString(c.getColumnIndex(Utilidades.campo_cuenta));
                if(userName.equals(ruUserName.getText().toString())){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("Importante");
                    dialog.setMessage("Ya existe un usuario con este nombre");
                    dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ruUserName.setText("");
                            ruUserName.requestFocus();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    ban = false;
                }else{
                if(cuenta.equals(ruCuenta.getText().toString())){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                    dialog.setTitle("Importante");
                    dialog.setMessage("Ya existe un usuario con esta cuenta");
                    dialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ruCuenta.setText("");
                            ruCuenta.requestFocus();
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    ban = false;
                }
                }
            }while(c.moveToNext());
        }
        if(ban){
            String suser = ruUserName.getText().toString();
            values.put(Utilidades.campo_id,num);
            values.put(Utilidades.campo_userName,suser);
            values.put(Utilidades.campo_cuenta,ruCuenta.getText().toString());
            values.put(Utilidades.campo_pass,ruPass.getText().toString());
            values.put(Utilidades.campo_puntuacion,0);
            Long id_resu = db.insert(Utilidades.tabla_usuarios,Utilidades.campo_id,values);
            Toast.makeText(this, "Registrado " + id_resu, Toast.LENGTH_SHORT).show();
            db.close();

            Intent intent1 = new Intent(this, PantallaJuego.class);
            intent1.putExtra("pjrUser",suser);
            intent1.putExtra("pjrPuntuacion","0");
            startActivity(intent1);
            finish();
        }
    }
}
