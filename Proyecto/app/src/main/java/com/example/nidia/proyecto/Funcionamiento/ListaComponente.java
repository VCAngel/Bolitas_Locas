package com.example.nidia.proyecto.Funcionamiento;

public class ListaComponente {
    private NodoComida inicio;
    private NodoComida fin;

    public ListaComponente() {
        this.inicio = null;
        this.fin = null;
    }

    public boolean isEmpty() {
        if (inicio == null)
            return true;
        else
            return false;
    }

    //AGREGAR NODO AL FINAL DE LA LISTA
    public void add(NodoComida nuevo) {
        //Comprobar que la lista esta vacia
        if (isEmpty()) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            fin.setSiguiente(nuevo);
            fin = nuevo;
        }
    }

    //AGREGAR NODO AL INICIO DE LA LISA
    public void addBegin(NodoComida nuevo) {
        //Comprobar que la lista esta vacía
        if (isEmpty()) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            nuevo.setSiguiente(inicio);
            inicio = nuevo;
        }
    }

    //INSERTAR NODO EN UNA POSICÓN
    public void insertAt(NodoComida nuevo, int pos) throws Exception {
        if (isEmpty()) {
            inicio = nuevo;
            fin = nuevo;
        } else {
            if ((pos < 0) || (pos >= size()))
                throw new Exception("La posición es inválida");
            else {
                int cont = 0;
                NodoComida temp = inicio;
                while (cont < (pos - 1)) {
                    temp = temp.getSiguiente();
                    cont++;
                }
                nuevo.setSiguiente(temp.getSiguiente());
                temp.setSiguiente(nuevo);
            }
        }
    }

    //IMPRIMIR LISTA
    public void print() {
        NodoComida temp = inicio;
        while (temp != null) {
            System.out.print(temp.getComida() + "-");
            temp = temp.getSiguiente();
        }
        System.out.println("");
    }

    //REGRESAR TAMAÑO DE LA LISTA
    public int size() {
        int iCont = 0;
        NodoComida temp = inicio;
        while (temp != null) {
            iCont++;
            temp = temp.getSiguiente();
        }
        return iCont;
    }

    //LIMPIAR LISTA
    public void clear() {
        inicio = null;
        fin = null;
    }

    //BORRAR NODO EN UNA POSICIÓN
    public void deleteAt(int pos) throws Exception {
        if (isEmpty())
            throw new Exception("La lista está vacía");
        if ((pos < 0) || (pos >= size()))
            throw new Exception(("La posición es inválida"));

        if (size() == 1) { //Solo un nodo
            clear();
        } else {//Varios nodos
            if (pos == 0) {
                inicio = inicio.getSiguiente();
            } else {
                NodoComida temp = inicio;
                int cont = 0;
                while (cont < (pos - 1)) {
                    temp = temp.getSiguiente();
                    cont++;
                }
                temp.setSiguiente(temp.getSiguiente().getSiguiente());
                if (pos == (size() - 1))
                    fin = temp;
            }
        }

    }

    public NodoComida getAt(int pos) throws Exception {
        if (isEmpty())
            throw new Exception("La lista esta vacia");
        if ((pos < 0) || (pos >= size())) {
            throw new Exception("La posicion es invalida");
        } else {
            NodoComida temp = inicio;
            for (int i = 0; i < pos; i++) {
                temp = temp.getSiguiente();
            }
            return temp;
        }
    }
}
