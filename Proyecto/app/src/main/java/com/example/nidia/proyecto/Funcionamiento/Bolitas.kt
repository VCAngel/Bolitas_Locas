package com.example.nidia.proyecto.Funcionamiento

import android.graphics.Color

class Bolitas {
    private var size: Int = 0
    private var speed: Float? = null
    private val colorArray = arrayOf(Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN,
                                    Color.MAGENTA, Color.RED, Color.YELLOW)
    private var stroke: Int = Color.BLACK;
    private var color: Int = 0
    private var value: Int? = null
    private var posiX: Float = 0f
    private var posiY: Float = 0f

    constructor(size: Int, color: Int){ // --> Bolita del jugador
        this.size = size;
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

    fun getStroke():Int{
        return stroke
    }

    fun setValue(value: Int?){
        this.value = value
    }
    fun getValue():Int?{
        return value
    }

    fun setPosiX(posiX: Float){
        this.posiX = posiX
    }
    fun getPosiX():Float{
        return posiX
    }

    fun setPosiY(posiY: Float){
        this.posiY = posiY
    }
    fun getPosiY():Float{
        return posiY
    }
    fun isInside(left: Int, top: Int, right: Int, bottom: Int): Boolean{
        if((this.getPosiX() > left) && (this.getPosiX() < right)){
            if((this.getPosiY() > top)&&(this.getPosiY() < bottom)){
                return true
            }
        }
        return false
    }
}