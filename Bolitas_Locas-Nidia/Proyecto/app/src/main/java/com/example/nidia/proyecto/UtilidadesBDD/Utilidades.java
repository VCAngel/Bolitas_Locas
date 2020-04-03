package com.example.nidia.proyecto.UtilidadesBDD;

public class Utilidades {
    //constantes de campos de tabla usuarios
    public final static String tabla_usuarios = "usuarios";
    public final static String campo_id = "id";
    public final static String campo_userName = "userName";
    public final static String campo_cuenta = "cuenta";
    public final static String campo_pass = "pass";
    public final static String campo_puntuacion = "puntuacion";

    public static final String CREAR_TABLA = "CREATE TABLE " + tabla_usuarios + " (" + campo_id + " INTEGER, " + campo_userName + " TEXT, " + campo_cuenta + " TEXT, " + campo_pass + " TEXT, " + campo_puntuacion + " INTEGER)";
}
