package com.example.nidia.proyecto.Funcionamiento

import android.graphics.Color

class Obstaculo {
    private var left: Int = 0
    private var top: Int = 0
    private var right: Int = 0
    private var bottom: Int = 0
    private var tocable: Boolean = true;

    constructor(left: Int, top: Int, right: Int, bottom: Int){
        this.left = left
        this.top = top
        this.right = right
        this.bottom = bottom
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
    fun setTocable(tocable: Boolean){
        this.tocable = tocable
    }
    fun isTocable():Boolean{
        return tocable
    }
}