package ayed2obligatorio2016.ArbolBinario;

import ayed2obligatorio2016.Grafo.Arista;
import ayed2obligatorio2016.Sistema;
import java.util.ArrayList;
import java.lang.Comparable;

public class ArbolBinario<E extends Comparable<E>> 
{
    private NodoBinario<E> raiz;
    private int cantidad;

    public ArbolBinario()
    {
        this.cantidad=0;
    }

    public int getCantidad()
    {
        return cantidad;
    }

    public Elemento<E> getRaiz()
    {
        return raiz;
    }

    private NodoBinario<E> validar(Elemento<E> elemento)
    {
        if(!(elemento instanceof NodoBinario))
        {
        return null;
        }
        NodoBinario<E> nodoBinario = (NodoBinario<E>)elemento;
        return nodoBinario.getPadre()==nodoBinario ?null:nodoBinario;
    }

    public Elemento<E> obtenerPadre(Elemento<E> elemento)
    {
        return validar(elemento).getPadre();
    }

    public Elemento<E> obtenerHijoIzquierdo(Elemento<E> elemento)
    {
        return validar(elemento).getHijoIzquierdo();
    }

    public Elemento<E> obtenerHijoDerecho(Elemento<E> elemento)
    {
        return validar(elemento).getHijoDerecho();
    }

    public boolean esRaiz(Elemento<E> elemento)
    {
        return elemento==getRaiz();
    }

    public Elemento<E> insertarRaiz(E elemento)
    {
        if(!estaVacia())
        {
            return null;
        }
        raiz=crearNodo(elemento,null,null,null);
        cantidad=1;
        return raiz;
    }

    public Elemento<E> insertarHijoIzquierdo(Elemento<E> elemento,E dato)
    {
         NodoBinario<E> temporal=validar(elemento);
        if(temporal.getHijoIzquierdo()!=null)
        {
            return null;
        }

        NodoBinario<E> hijoIzquierdo=crearNodo(dato,temporal,null,null);
        temporal.setHijoIzquierdo(hijoIzquierdo);
        cantidad++;
        return hijoIzquierdo;
    }

    public Elemento<E> insertarHijoDerecho(Elemento<E> elemento,E dato)
    {
        NodoBinario<E> temporal=validar(elemento);
        if(temporal.getHijoDerecho()!=null)
        {
            return null;
        }

        NodoBinario<E> hijoDerecho=crearNodo(dato,temporal,null,null);
        temporal.setHijoDerecho(hijoDerecho);
        cantidad++;
        return hijoDerecho;
    }

    public Elemento<E> insertar(E dato)
    {
        return insertar(dato,getRaiz());
    }

    private Elemento<E> insertar(E dato, Elemento<E> nodo)
    {
        if(nodo==null)
        {
            return insertarRaiz(dato);
        }
        else if(((Comparable<E>)dato).compareTo(nodo.getElemento())<0)
        {
            if(obtenerHijoIzquierdo(nodo)==null)
            {
                return insertarHijoIzquierdo(validar(nodo),dato);
            }
            return insertar(dato,obtenerHijoIzquierdo(nodo));
        }
        else {
            if(obtenerHijoDerecho(nodo)==null)
            {
                return insertarHijoDerecho(validar(nodo),dato);
            }
            return insertar(dato, obtenerHijoDerecho(nodo));
        }
    }
    
    private int CharAInt(char pChar)
    {
        return (int)pChar;
    }
    
    private NodoBinario<E> crearNodo(E elemento, NodoBinario<E> padre,
                                     NodoBinario<E> hijoIzquierdo,
                                     NodoBinario<E> hijoDerecho)
    {
        return new NodoBinario<E>(elemento,padre,hijoIzquierdo,hijoDerecho);
    }

    public boolean estaVacia()
    {
        return getCantidad()==0;
    }

    public Iterable<Elemento<E>> obtenerHijos(Elemento<E> elemento)
    {
        ArrayList<Elemento<E>> hijos=new ArrayList<Elemento<E>>(2);
        if(obtenerHijoIzquierdo(elemento)!=null)
        {
            hijos.add(obtenerHijoIzquierdo(elemento));
        }
        if(obtenerHijoDerecho(elemento)!=null)
        {
            hijos.add(obtenerHijoDerecho(elemento));
        }
        return hijos;
    }

    public ArrayList<Elemento<E>> recorridoPreOrden()
    {
        ArrayList<Elemento<E>> elementos=new ArrayList<Elemento<E>>();
        if(!estaVacia())
        {
            recorridoPreOrden(getRaiz(),elementos);
        }

        return elementos;
    }

    private void recorridoPreOrden(Elemento<E> raiz, ArrayList<Elemento<E>> elementos)
    {
        elementos.add(raiz);
        for(Elemento<E> elemento:obtenerHijos(raiz))
        {
            recorridoPreOrden(elemento,elementos);
        }
    }

    public ArrayList<Elemento<E>> recorridoInOrden()
    {
        ArrayList<Elemento<E>> elementos=new ArrayList<Elemento<E>>();
        if(!estaVacia())
        {
             recorridoInOrden(getRaiz(),elementos);
        }

        return elementos;
    }

    private void recorridoInOrden(Elemento<E> raiz, ArrayList<Elemento<E>> elementos)
    {
        if(obtenerHijoIzquierdo(raiz)!=null)
        {
            recorridoInOrden(obtenerHijoIzquierdo(raiz),elementos);
        }
        elementos.add(raiz);
        if(obtenerHijoDerecho(raiz)!=null)
        {
            recorridoInOrden(obtenerHijoDerecho(raiz),elementos);
        }
    }

    public ArrayList<Elemento<E>> recorridoPosOrden()
    {
        ArrayList<Elemento<E>> elementos=new ArrayList<Elemento<E>>();
        if(!estaVacia())
        {
            recorridoPosOrden(getRaiz(), elementos);
        }

        return elementos;
    }

    private void recorridoPosOrden(Elemento<E> raiz, ArrayList<Elemento<E>> elementos)
    {
        for(Elemento<E>elemento:obtenerHijos(raiz))
        {
            recorridoPosOrden(elemento,elementos);
        }
        elementos.add(raiz);
                                            for(int i=0;i<elementos.size();i++)
                                            {
                                            Elemento<E> s = elementos.get(i);
                                            NodoBinario s1 = (NodoBinario)s;
                                            Arista s2 = (Arista)s1.getElemento();
                                            System.out.println(s2.getNombre());
                                            }
    }

   /* public Elemento<E> Buscar(Elemento<E> e)
    {
        if(validar(e)!=null)
        {
            NodoBinario r = (NodoBinario)e.getElemento();
            ArbolBinario l = Sistema.getListaAristaOrdenadasNombre();
            return BuscarRe(r);  
        }
        return null;
    }
    
    private Elemento<E> BuscarRe(NodoBinario a)
    {
        
    }*/
    
    public Elemento<E> Buscar(E dato)
    {
        if(dato !=null)
        {
            if(this.getRaiz().equals(dato))
            {
                return getRaiz();
            }
            else{
                NodoBinario n = new NodoBinario(dato);
                NodoBinario r = (NodoBinario)getRaiz();
                return Buscar(n, r);
            }
        }
        else
        {return null;}
    }
    private Elemento<E> Buscar(NodoBinario<E> buscado, NodoBinario<E> recorrido)
    {
        Arista s = new Arista();
        if(recorrido == null){
            return null;
        }  
        else if(buscado.equals(recorrido)){
            return recorrido;
        }
        else
        {
            if(((Comparable<E>)buscado).compareTo(recorrido.getElemento())<0) 
            {
                return Buscar(buscado, recorrido.getHijoDerecho());
            }
            else 
                return Buscar(buscado, recorrido.getHijoIzquierdo());
        }
    }
    
}