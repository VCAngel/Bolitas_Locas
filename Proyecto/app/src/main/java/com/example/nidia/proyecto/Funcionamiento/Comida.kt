package com.example.nidia.proyecto.Funcionamiento

import android.graphics.Color

class Comida {
    private var size: Int = 0
    private val colorArray = arrayOf(Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN,
            Color.MAGENTA, Color.RED, Color.YELLOW)
    private var color: Int = 0
    private var value: Int? = null

    constructor(size: Int, color: Int){ //--> Bolitas <--> Comida del jugador
        this.size = size
        if(color < 0 || color >= colorArray.size)
            this.color = Color.BLUE
        else
            this.color = colorArray[color]
    }

    fun setSize(size: Int){
        this.size = size
    }
    fun getSize(): Int?{
        return size;
    }

    fun setValue(value: Int?){
        this.value = value
    }
    fun getValue():Int?{
        return value
    }

    fun setColor(color: Int){
        if(color < 0 || color >= colorArray.size)
            this.color = Color.BLUE
        else
            this.color = colorArray[color]
    }
    fun getColor():Int {
        return color;
    }
}