package com.example.nidia.proyecto.Funcionamiento

class BDDUsuarios {
    private var id: Int? = null
    private var UserName: String? = null
    private var cuenta: Char = ' '
    private var Pass: Char = ' '
    private var puntuacion: Int? = null
    private var puntuacion2: Int? = null
    private var puntuacion3: Int? = null

    fun BDDUsuarios(id: Int?, userName: String, cuenta: Char, pass: Char, puntuacion: Int?) {
        this.id = id
        UserName = userName
        this.cuenta = cuenta
        Pass = pass
        this.puntuacion = puntuacion
    }

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getUserName(): String? {
        return UserName
    }

    fun setUserName(userName: String) {
        UserName = userName
    }

    fun getCuenta(): Char {
        return cuenta
    }

    fun setCuenta(cuenta: Char) {
        this.cuenta = cuenta
    }

    fun getPass(): Char {
        return Pass
    }

    fun setPass(pass: Char) {
        Pass = pass
    }

    fun getPuntuacion(): Int? {
        return puntuacion
    }

    fun setPuntuacion(puntuacion: Int?) {
        this.puntuacion = puntuacion
    }
    fun getPuntuacion2(): Int? {
        return puntuacion2
    }

    fun setPuntuacion2(puntuacion2: Int?) {
        this.puntuacion2 = puntuacion2
    }
    fun getPuntuacion3(): Int? {
        return puntuacion3
    }

    fun setPuntuacion3(puntuacion3: Int?) {
        this.puntuacion3 = puntuacion3
    }
}