/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dijkstra;

import ayed2obligatorio2016.Grafo.Arista;
import ayed2obligatorio2016.Grafo.CHash;
import ayed2obligatorio2016.Grafo.Grafo;
import ayed2obligatorio2016.Grafo.NodoGrafo;
import ayed2obligatorio2016.ListaDoble.NodoLista;
import ayed2obligatorio2016.ListaSimple.ListaSimpleGeneric;
import ayed2obligatorio2016.ListaSimple.NodoListaSimple;
import static ayed2obligatorio2016.Sistema.getMetro;
import clases.CHashSolucionNodo;
import clases.Hash;

public class TablaCaminoCorto {
    
    private NodoTablaCaminoCorto[] Tabla;
    private Hash ch;
    public NodoGrafo AImprimir;
    private char linea;
    
    public TablaCaminoCorto()
    {
        Tabla = new NodoTablaCaminoCorto[100];
        ch = getMetro().getTablaEstaciones();
    }
    
    public TablaCaminoCorto(CHashSolucionNodo pch,char nombrelinea)
    {
        linea = nombrelinea;
        Tabla = new NodoTablaCaminoCorto[100];
        ch = pch;
    }
    
    public void AgregarNodoInicio(NodoGrafo pN)
    {
        int i = ch.ObtenerIndice(pN.getNombre());
        NodoTablaCaminoCorto a = AgregarInicio(pN.getNombre());
        getTabla()[i]=a;
    }
    
    private NodoTablaCaminoCorto AgregarInicio(String pN)
    {
        NodoTablaCaminoCorto a;
        a = new NodoTablaCaminoCorto();
        a.setConocido(false);
        a.setDistancia(0);
        a.setEstacion(pN);
        a.setEstacionA(null);
        return a;
    }
    
    public void Agregar(NodoGrafo pN,int pIndice)
    {
        NodoTablaCaminoCorto a = new NodoTablaCaminoCorto();
        a.setConocido(false);
        a.setDistancia(Integer.MAX_VALUE);
        a.setEstacion(pN.getNombre());
        a.setEstacionA(null);
        Tabla[pIndice]=a;
    }

    public NodoTablaCaminoCorto[] getTabla() {
        return Tabla;
    }
    
    public Grafo m = getMetro();
    
    public void Dijktra(NodoGrafo pN)
    {
        CargarTabla(pN);
        ImplementandoDijktra(Tabla);
    }
    
    public void DijktraLineas(NodoGrafo pN)
    {
        CargarTabla(pN);
        ImplementandoDijktraLineas(Tabla);
    }

    private void ImplementandoDijktraLineas(NodoTablaCaminoCorto[] r)
    {
        int IndiceActual;
        NodoGrafo v, w;
        for (int i = 0;i<Tabla.length;i++) {
            if(Tabla[i]!=null)
                {
                    v = NodoTablaConDistanciaMasCortaDesconocido();
                    if(v!=null){
                    IndiceActual = ch.ObtenerIndice(v.getNombre());
                    Tabla[IndiceActual].setConocido(true);

                    NodoLista nls = v.getAristas().getInicio();//Capturamos La Primera Arista
                    Arista a = (Arista)nls.getDato();//Arista
                    
                    while(!ComprobarSiEsDeEstaLinea(a))
                    {
                        if(nls.getSiguiente()!=null)
                        a = (Arista)nls.getSiguiente().getDato();
                    }
                    
                    NodoGrafo aux = a.getDestino();//siquiente Estacion
                    if(aux.getNombre().equals(v.getNombre()))//Aux Siguiente estacion
                    {
                        aux = a.getOrigen();
                    }
                    
                    while(aux!=null){
                        int indi = ch.ObtenerIndice(aux.getNombre());
                        if(!Tabla[indi].getConocido()&&((Tabla[IndiceActual].getDistancia()+a.getDistancia())<Tabla[indi].getDistancia()))
                        {
                            float floataux = Tabla[IndiceActual].getDistancia()+a.getDistancia();
                            Tabla[indi].setDistancia(floataux);
                            Tabla[indi].setEstacionA(v);
                        }
                        nls = aux.getAristas().getInicio();
                        if(nls!=null)
                        {
                            Arista au = (Arista)nls.getDato();
                            
                            while(!ComprobarSiEsDeEstaLinea(au))
                            {
                                if(nls.getSiguiente()!=null)
                                au = (Arista)nls.getSiguiente().getDato();
                            }

                            while(au==a){
                                
                                nls = nls.getSiguiente();
                                if(nls==null){
                                aux = null;
                                break;}
                                else{au = (Arista)nls.getDato();
                                while(!ComprobarSiEsDeEstaLinea(au))
                                {
                                    if(nls.getSiguiente()!=null)
                                    au = (Arista)nls.getSiguiente().getDato();
                                }
                                }
                            }
                            if(aux !=null){
                                a = au;
                                v = aux;
                                    if(aux.getNombre().equals(a.getDestino().getNombre()))
                                        aux = a.getOrigen();
                                    else{
                                        aux = a.getDestino();}
                            } 
                        }
                        else
                        {
                            aux = null;
                        }
                    }
                }
            }
        }
    }
    
    private void ImplementandoDijktra(NodoTablaCaminoCorto[] r)
    {
        int IndiceActual;
        NodoGrafo v, w;
        for (int i = 0;i<Tabla.length;i++) {
            if(Tabla[i]!=null)
                {
                    v = NodoTablaConDistanciaMasCortaDesconocido();
                    if(v!=null){
                    IndiceActual = ch.ObtenerIndice(v.getNombre());
                    Tabla[IndiceActual].setConocido(true);

                    NodoLista nls = v.getAristas().getInicio();//Capturamos La Primera Arista
                    Arista a = (Arista)nls.getDato();//Arista                    
                    NodoGrafo aux = a.getDestino();//siquiente Estacion
                    if(aux.getNombre().equals(v.getNombre()))//Aux Siguiente estacion
                    {
                        aux = a.getOrigen();
                    }
                    
                    while(aux!=null){
                        int indi = ch.ObtenerIndice(aux.getNombre());
                        if(!Tabla[indi].getConocido()&&((Tabla[IndiceActual].getDistancia()+a.getDistancia())<Tabla[indi].getDistancia()))
                        {
                            float floataux = Tabla[IndiceActual].getDistancia()+a.getDistancia();
                            Tabla[indi].setDistancia(floataux);
                            Tabla[indi].setEstacionA(v);
                        }
                        nls = aux.getAristas().getInicio();
                        if(nls!=null)
                        {
                            Arista au = (Arista)nls.getDato();

                            while(au==a){
                                
                                nls = nls.getSiguiente();
                                if(nls==null){
                                aux = null;
                                break;}
                                else
                                    au = (Arista)nls.getDato();
                            }
                            if(aux !=null){
                                a = au;
                                v = aux;
                                    if(aux.getNombre().equals(a.getDestino().getNombre()))
                                        aux = a.getOrigen();
                                    else{
                                        aux = a.getDestino();}
                            } 
                        }
                        else
                        {
                            aux = null;
                        }
                    }
                }
            }
        }
    }
    
    private boolean ComprobarSiEsDeEstaLinea(Arista p)
    {
        return p.getNombre()==linea;
    }
    
    private NodoGrafo NodoTablaConDistanciaMasCortaDesconocido()
    {
        float MenorDistancia = Integer.MAX_VALUE;
        NodoTablaCaminoCorto aux = null;
        
        for (NodoTablaCaminoCorto Tabla1 : Tabla) {
            if (Tabla1 != null) {
                if(!Tabla1.getConocido()&&Tabla1.getDistancia()<MenorDistancia)
                {
                    MenorDistancia = Tabla1.getDistancia();
                    aux = Tabla1;
                }
            }       
        }
        if(aux!=null)
        {
            return ch.BuscarHash(aux.getEstacion());
        }
        return null;
    }
    
    private  void CargarTabla(NodoGrafo pN)
    {
        NodoGrafo[] nga = ch.getArray();
        for(int i=0; i<Tabla.length ;i++)
        {
            if(nga[i]!=null)
            {
                Agregar(nga[i],i);
            }
            else
            {
                Tabla[i]=null;
            }
        }
        this.AgregarNodoInicio(pN);
    }
    
    public void imprimir_Camino(NodoGrafo pNinicio, NodoGrafo pNfin)
    {
        int indice = ch.ObtenerIndice(pNinicio.getNombre());
        if(Tabla[indice].getEstacionA()!=null&&Tabla[indice].getEstacionA().equals(pNfin))
        {
            return;
        }
        else{
            if(Tabla[indice].getEstacionA()!=null)
            {
                imprimir_Camino(Tabla[indice].getEstacionA(),pNfin);
                System.out.print(pNinicio.getNombre());
            }
        }
    }

    public void imprimir_Camino(NodoGrafo pNinicio)
    {
        int indice = ch.ObtenerIndice(pNinicio.getNombre());
        if(Tabla[indice].getEstacionA()!=null)
        {
            imprimir_Camino(Tabla[indice].getEstacionA());
            System.out.print(pNinicio.getNombre()+" - "+Tabla[indice].getDistancia()+" Km\n");
        }
    }
    
    public void AImprimir()
    {
        float mayor = 0;
        for(int i = 0; i<Tabla.length;i++)
        {
            if(Tabla[i]!=null)
            {
                if(Tabla[i].getDistancia()>mayor&&Tabla[i].getDistancia()!=Integer.MAX_VALUE)
                {
                    mayor=Tabla[i].getDistancia();
                    AImprimir = ch.BuscarHash(Tabla[i].getEstacion());
                }
            }
        }
    }
}
