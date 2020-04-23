package com.example.nidia.proyecto.Funcionamiento

import android.graphics.Color

class Comida {
    private var size: Int = 0
    private val colorArray = arrayOf(Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN,
            Color.MAGENTA, Color.RED, Color.YELLOW)
    private var color: Int = 0
    private var value: Int? = null
    private var coordX: Int = 0
    private var coordY: Int = 0
    private var coordX2: Int = 0 //TODO Usar de alguna manera estos atributos
    private var coordY2: Int = 0
    private var coordX2ini: Int = 0
    private var coordY2ini: Int = 0

    constructor(size: Int, color: Int, coordX: Int, coordY: Int){ //--> Bolitas / Comida del jugador
        this.size = size
        if(color < 0 || color >= colorArray.size)
            this.color = Color.BLUE
        else
            this.color = colorArray[color]

        this.coordX = coordX
        this.coordY = coordY
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

    fun setCoordX(coordX: Int){
        this.coordX = coordX
    }
    fun getCoordX():Int{
        return coordX
    }

    fun setCoordY(coordY: Int){
        this.coordX = coordX
    }
    fun getCoordY():Int{
        return coordY
    }

    fun setCoordX2(coordX2: Int){
        this.coordX2 = coordX2
    }
    fun getCoordX2():Int{
        return coordX2
    }

    fun setCoordY2(coordY2: Int){
        this.coordX2 = coordX2
    }
    fun getCoordY2():Int{
        return coordY2
    }

    fun setCoordX2ini(coordX2ini: Int){
        this.coordX2ini = coordX2ini
    }
    fun getCoordX2ini():Int{
        return coordX2ini
    }

    fun setCoordY2ini(coordY2ini: Int){
        this.coordX2ini = coordX2ini
    }
    fun getCoordY2ini():Int{
        return coordY2ini
    }
}