package com.example.nidia.proyecto.Grafico;

import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Clase que se utilizará para ocultar la barra de estado
 * y la barra de acción dentro de la app
 */

public class HideVisibilityStyle {
    View decorView;

    public HideVisibilityStyle(AppCompatActivity activity) {
        decorView = activity.getWindow().getDecorView();
    }

    public void hideStatusBar() { //ocultar la barra de estado en la aplicación
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void hideActionBar() { //ocultar la barra de acción dentro de la aplicación
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public void hideAll() {
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}
