package com.example.nidia.proyecto.Funcionamiento

import android.graphics.Color

class Bolitas {
    private var size: Int = 0
    private var speed: Float? = null
    private val colorArray = arrayOf(Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN,
                                    Color.MAGENTA, Color.RED, Color.YELLOW)
    private var stroke: Int = 0
    private var color: Int = 0
    private var value: Int? = null

    constructor(size: Int, color: Int, stroke: Int){
        this.size = size;
        if(color < 0 || color >= colorArray.size)
            this.color = Color.BLUE
        else
            this.color = colorArray[color]

        if(stroke < 0 || stroke >= colorArray.size)
            this.stroke = Color.BLUE
        else
            this.stroke = colorArray[stroke]
    }

    fun setSize(size: Int){
        this.size = size
    }
    fun getSize(): Int?{
        return size;
    }

    fun setSpeed(speed: Float?){
        this.speed = speed
    }
    fun getSpeed(): Float?{
        return speed
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

    fun setStroke(stroke: Int){
        if(stroke < 0 || stroke >= colorArray.size)
            this.stroke = Color.BLUE
        else
            this.stroke = colorArray[stroke]
    }
    fun getStroke():Int{
        return stroke
    }

    fun setValue(value: Int?){
        this.value = value
    }
    fun getValue():Int?{
        return value
    }
}