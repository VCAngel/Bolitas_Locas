package com.example.nidia.proyecto.Funcionamiento

import android.graphics.Color

class Comida {
    private var size: Int = 0
    private val colorArray = arrayOf(Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN,
            Color.MAGENTA, Color.RED, Color.YELLOW)
    private var color: Int = 0
    private var value: Int? = null
    private var posiX: Int = 0
    private var posiY: Int = 0

    constructor(size: Int, color: Int){ //--> Bolitas <--> Comida del jugador
        this.size = size
        if(color < 0 || color >= colorArray.size)
            this.color = Color.BLUE
        else
            this.color = colorArray[color]

        when(color){
            0 -> this.value = 3
            1 -> this.value = 5
            2 -> this.value = 7
            3 -> this.value = 9
            4 -> this.value = 11
            5 -> this.value = 13
            6 -> this.value = 15
            else -> this.value = 3
        }
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

    fun setPosiX(posiX: Int){
        this.posiX = posiX
    }
    fun getPosiX():Int{
        return posiX
    }

    fun setPosiY(posiY: Int){
        this.posiY = posiY
    }
    fun getPosiY():Int{
        return posiY
    }
}