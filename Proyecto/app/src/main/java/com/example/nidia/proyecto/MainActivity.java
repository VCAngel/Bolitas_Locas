package com.example.nidia.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.nidia.proyecto.Grafico.LoginUsuarios;
import com.example.nidia.proyecto.Grafico.RegistroUsuarios;

public class MainActivity extends Activity {
    EditText user, pass;
    Button registro, log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registro = findViewById(R.id.registrarse);
        log = findViewById(R.id.login);

        registro.setOnClickListener(onClickListener);
        log.setOnClickListener(onClickListener);

        /*user.findViewById(R.id.user);
        pass.findViewById(R.id.lpass);*/
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
                Intent intent1 = new Intent(this, RegistroUsuarios.class);
                startActivity(intent1);
                break;
            case R.id.login:
                Intent intent2 = new Intent(this, LoginUsuarios.class);
                startActivity(intent2);
                break;
        }
    }

}
