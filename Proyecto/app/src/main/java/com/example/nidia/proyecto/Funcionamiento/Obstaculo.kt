package com.example.nidia.proyecto.Funcionamiento

import android.graphics.Color

class Obstaculo {
    private val colorArray = arrayOf(Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN,
            Color.MAGENTA, Color.RED, Color.YELLOW)
    private var color: Int = 0
    private var left: Int = 0
    private var top: Int = 0
    private var right: Int = 0
    private var bottom: Int = 0

    constructor(left: Int, top: Int, color: Int, right: Int, bottom: Int){
        if(color < 0 || color >= colorArray.size)
            this.color = Color.BLUE
        else
            this.color = colorArray[color]
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
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
    fun setTop(top: Int){
        this.top = top
    }
    fun getTop():Int{
        return top
    }
    fun setLeft(left: Int){
        this.left = left
    }
    fun getLeft():Int{
        return left
    }
    fun setRight(right: Int){
        this.right = right
    }
    fun getRight():Int{
        return right
    }
    fun setBottom(bottom: Int){
        this.bottom = bottom
    }
    fun getBottom():Int{
        return bottom
    }

}