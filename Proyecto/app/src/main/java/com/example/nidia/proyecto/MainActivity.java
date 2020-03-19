package com.example.nidia.proyecto;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nidia.proyecto.Grafico.LoginUsuarios;
import com.example.nidia.proyecto.Grafico.RegistroUsuarios;

public class MainActivity extends Activity {
    EditText user, pass;
    Button mRegistrarse, mLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRegistrarse = findViewById(R.id.mRegistrarse);
        mLogin = findViewById(R.id.mLogin);

        mRegistrarse.setOnClickListener(onClickListener);
        mLogin.setOnClickListener(onClickListener);

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
            case R.id.mRegistrarse:
                Intent intent1 = new Intent(this, RegistroUsuarios.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.mLogin:
                Intent intent2 = new Intent(this, LoginUsuarios.class);
                startActivity(intent2);
                finish();
                break;
        }
    }

}
