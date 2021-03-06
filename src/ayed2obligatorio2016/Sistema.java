/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ayed2obligatorio2016;

import Dijkstra.TablaCaminoCorto;
import Dijkstra.TablaPrecioMenor;
import ayed2obligatorio2016.ArbolBinario.ArbolBinario;
import ayed2obligatorio2016.ArbolBinario.NodoBinario;
import ayed2obligatorio2016.Grafo.Arista;
import ayed2obligatorio2016.Grafo.CHash;
import ayed2obligatorio2016.Grafo.Grafo;
import ayed2obligatorio2016.Grafo.NodoGrafo;
import ayed2obligatorio2016.ListaDoble.ListaDobleEnc;
import ayed2obligatorio2016.ListaSimple.ListaSimpleGeneric;
import ayed2obligatorio2016.ListaSimple.NodoListaSimple;

import clases.Servicio;
import clases.Cliente;
import clases.NodoListaConNombre;
import clases.Servicio;
import clases.Viaje;

import java.time.LocalDateTime;

/**
 *
 * @author Daniel
 */

public class Sistema implements IMetro {
    
    private static ListaSimpleGeneric<Viaje> ListaViaje;
    private static ListaDobleEnc<Cliente> ListaCliente;
    private static ArbolBinario<NodoListaConNombre> ListaAristaOrdenadasNombre;
    private static ArbolBinario<Cliente> ListaClienteOrdenadosNombre;
    private static Grafo Metro;
    
    // <editor-fold defaultstate="collapsed" desc=" GetSet ">
    public static Grafo getMetro() {
        return Metro;
    }

    public static void setMetro(Grafo aMetro) {
        Metro = aMetro;
    }

    /**
     * @return the ListaArista
     */
    public static ArbolBinario<NodoListaConNombre> getListaAristaOrdenadasNombre() {
        return ListaAristaOrdenadasNombre;
    }

    /**
     * @param aListaArista the ListaArista to set
     */
    public static void setListaAristaOrdenadasNombre(ArbolBinario<NodoListaConNombre> aListaArista) {
        ListaAristaOrdenadasNombre = aListaArista;
    }

    /**
     * @return the ListaClienteOrdenadosNombre
     */
    public static ArbolBinario<Cliente> getListaClienteOrdenadosNombre() {
        return ListaClienteOrdenadosNombre;
    }

    public ListaSimpleGeneric<Viaje> getListaViaje() {
        return ListaViaje;
    }

    public void setListaViaje(ListaSimpleGeneric<Viaje> aListaViaje) {
        ListaViaje = aListaViaje;
    }

    public ListaDobleEnc<Cliente> getListaCliente() {
        return ListaCliente;
    }
    
    public void setListaCliente(ListaDobleEnc<Cliente> aListaCliente) {
        ListaCliente = aListaCliente;
    }
     // </editor-fold>

    public enum TipoRet {
        OK, ERROR_1, ERROR_2, ERROR_3, ERROR_4, NO_IMPLEMENTADA
    };

    public TipoRet listarServiciosEstacion(String estacion) {
        NodoGrafo ng = Metro.getTablaEstaciones().BuscarHash(estacion);
        if(ng!=null)
        {
            ng.ListadoServicioDeEstacion(ng);
            return TipoRet.OK;
        }
        return TipoRet.ERROR_1;
    }

    public TipoRet listarLineas() {
        if(Metro.getListaAristas().getInicio()==null)
        {
            System.out.println("No hay Líneas en el Metro.\n");
        }
        else
        {
          Arista a = new Arista();
          a.ListadoAristas();  
        }
        return TipoRet.OK;
    }

    public TipoRet precioBoleto(String origen, String destino) {
        TablaPrecioMenor t = new TablaPrecioMenor();
        NodoGrafo ori = Metro.getTablaEstaciones().BuscarHash(origen);
        NodoGrafo des = Metro.getTablaEstaciones().BuscarHash(destino);
        if(des!=null)
        {
            if(ori!=null)
            {
                t.Dijktra(ori);
                t.imprimir_Precio_Menor(des,ori);
                return TipoRet.OK;
            }
            else
            {
                System.out.println("No existen conexiones entre "+ origen +" y "+ destino);
                return TipoRet.ERROR_1;
            }
        }
        else
        {
            System.out.println("No existen conexiones entre "+ origen +" y "+ destino);
            return TipoRet.ERROR_2;
        }
    }

    public TipoRet listarClientes() {        
        Cliente c = new Cliente();
        c.ListadoClientes();
        return TipoRet.OK;
    }
    
    public TipoRet caminoMasCorto(String origen, String destino) {
        NodoGrafo ori = Metro.getTablaEstaciones().BuscarHash(origen);
        NodoGrafo des = Metro.getTablaEstaciones().BuscarHash(destino);
        if(ori!=null)
        {
            if(des!=null)
            {
                TablaCaminoCorto t = new TablaCaminoCorto();
                t.Dijktra(ori);
                t.imprimir_Camino(des,ori);
                return TipoRet.OK;
            }
            else
            {
                System.out.println("No existen lineas entre "+ origen +" y "+ destino);
                return TipoRet.ERROR_2;
            }
        }
        System.out.println("No existen lineas entre "+ origen +" y "+ destino);
        return TipoRet.ERROR_1;
    }
    
    public TipoRet altaTramo(char linea, String origen, String destino, float distancia, float tarifa) {
        Arista a = new Arista();
        CHash ha = Metro.getTablaEstaciones();
        
        
        NodoGrafo dest = ha.BuscarHash(destino);
        NodoGrafo orig = ha.BuscarHash(origen);
        // <editor-fold defaultstate="collapsed" desc=" Agregar Estaciones">
         if(orig==null)
         {
             orig = new NodoGrafo();
             orig.setNombre(origen);
             ha.insertarEstacion(orig);
             Metro.getListaEstaciones().insertarInicio(orig);
         }
         if(dest==null)
         {
            dest = new NodoGrafo();
            dest.setNombre(destino);
            ha.insertarEstacion(dest);
            Metro.getListaEstaciones().insertarInicio(dest);
         }
         // </editor-fold>

         if(distancia >0)
         {
             if(tarifa >0)
             {
                 dest.setNombre(destino);
                 orig.setNombre(origen);
                 a.setDestino(dest);
                 a.setOrigen(orig);
                    if(Metro.BuscarAristaOrigenDestino(a)==null)
                    {
                        a.setDestino(dest);
                        a.setOrigen(orig);
                        a.setDistancia(distancia);
                        a.setNombre(linea);
                        a.setTarifa(tarifa);
                        
                        dest.getAristas().insertarInicio(a);
                        orig.getAristas().insertarInicio(a);
                        Metro.getListaAristas().insertarInicio(a);
                                                
                        NodoListaConNombre nlcn = new NodoListaConNombre();
                        nlcn.setNombre(a.getNombre());
                        NodoBinario s = (NodoBinario) ListaAristaOrdenadasNombre.Buscar(nlcn);
                        
                        
                        if(s!=null){
                            nlcn = (NodoListaConNombre)s.getElemento();
                            if(nlcn.getHashEstacionLinea().BuscarHash(origen)==null)
                            {
                            nlcn.getHashEstacionLinea().insertarEstacion(orig);
                            nlcn.setCantidadEnLista(nlcn.getCantidadEnLista()+1);
                            }
                            if(nlcn.getHashEstacionLinea().BuscarHash(destino)==null)
                            {
                            nlcn.getHashEstacionLinea().insertarEstacion(dest);
                            nlcn.setCantidadEnLista(nlcn.getCantidadEnLista()+1);
                            }
                        }
                        else
                        {
                            NodoListaConNombre newAux = new NodoListaConNombre();
                            newAux.setNombre(a.getNombre());
                            newAux.getHashEstacionLinea().insertarEstacion(orig);
                            newAux.setCantidadEnLista(1);
                            if(newAux.getHashEstacionLinea().BuscarHash(destino)==null)
                            {
                            newAux.getHashEstacionLinea().insertarEstacion(dest);
                            newAux.setCantidadEnLista(2);
                            }
                            ListaAristaOrdenadasNombre.insertar(newAux);
                        }                       
                        
                        return TipoRet.OK;
                    }
                    else
                    {
                        return TipoRet.ERROR_1;
                    }
             }
             else
             {
                 return TipoRet.ERROR_3;
             }
         }
         else
         {
             return TipoRet.ERROR_2;
         }
    }
    
    public TipoRet altaCliente(int cedula, String nombre) {
          
       Cliente unCliente = new Cliente();
       unCliente.setCedula(cedula);
       unCliente.setNombre(nombre);
              
       if(!unCliente.CorrobarCanDigitos(cedula))
       {
           return TipoRet.ERROR_2;
       }
       else
       {
           if(unCliente.BuscarCliente(cedula) == null)
           {
               ListaCliente.insertarInicio(unCliente);
               ListaClienteOrdenadosNombre.insertar(unCliente);
               return TipoRet.OK;
           }
           
           else
           {
               return TipoRet.ERROR_1;
           }
       }
    }

    public TipoRet bajaCliente(int cedula) {
                
        Cliente unCliente = new Cliente();
        unCliente.setCedula(cedula);
        
        if(unCliente.CorrobarCanDigitos(cedula))
        {
            unCliente = unCliente.BuscarCliente(cedula);
            if(unCliente != null)
            {
                ListaCliente.RemoveNodo(unCliente);
                ListaClienteOrdenadosNombre.eliminar(unCliente);
                return TipoRet.OK;
            }
            else
            {
                return TipoRet.ERROR_1;
            }
        }
        else
        {
            return TipoRet.ERROR_2;
        }
    }
    
    public TipoRet agregarServicio(String estacion, String servicio) {
            
        NodoGrafo Es = Metro.getTablaEstaciones().BuscarHash(estacion);
        
        if(Es == null)
        {
            return TipoRet.ERROR_1;
        }
        else
        {
            Servicio Ser = new Servicio();
            Ser.setNombre(servicio);
        
            Es.getServicios().insertar((Comparable) Ser);
            return TipoRet.OK;
        }  
    }
    
    public TipoRet listarViajesCliente(int ciCliente) {
        Cliente Cli = new Cliente();
        if(Cli.CorrobarCanDigitos(ciCliente))
        {
          if(Cli.BuscarCliente(ciCliente)!= null)
            {
                Viaje V = new Viaje();
                V.ImprimirViajes(ciCliente);
                return TipoRet.OK;
            }
            else
            {
                return TipoRet.ERROR_1;
            }
        }
        else
        {
            return TipoRet.ERROR_2;
        }
    }
    
    public TipoRet crearMetro() {
        ListaViaje = new ListaSimpleGeneric<Viaje>();
        ListaCliente = new ListaDobleEnc<Cliente>();
        ListaAristaOrdenadasNombre = new ArbolBinario<NodoListaConNombre>();
        ListaClienteOrdenadosNombre = new ArbolBinario<Cliente>();
        Metro = new Grafo();
        return TipoRet.OK;
    }

    public TipoRet destruirMetro() {
        Metro = new Grafo();
        ListaViaje = new ListaSimpleGeneric<Viaje>();
        ListaCliente = new ListaDobleEnc<Cliente>();
        ListaAristaOrdenadasNombre = new ArbolBinario<NodoListaConNombre>();
        ListaClienteOrdenadosNombre = new ArbolBinario<Cliente>();
        return TipoRet.OK;
    }
    
    public TipoRet agregarViaje(String origen, String destino, int ciCliente, LocalDateTime fechaHora) {
       Viaje v = new Viaje();
       Cliente c = new Cliente();
       
       if(c.CorrobarCanDigitos(ciCliente)){
       c = c.BuscarCliente(ciCliente);
           if(c!=null)
           {
                Arista a = new Arista();
                
                NodoGrafo ng = Metro.getTablaEstaciones().BuscarHash(origen);
                a.setOrigen(ng);
                ng = Metro.getTablaEstaciones().BuscarHash(destino);
                a.setDestino(ng);
                if(Metro.BuscarAristaOrigenDestino(a)!=null)
                {
                    v.setCiCliente(ciCliente);
                    v.setDestino(destino);
                    v.setOrigen(origen);
                    v.setFecha(fechaHora);
                    this.getListaViaje().insertarInicio(v);
                    c.getViajes().push(v);
                    return TipoRet.OK;
                }
                return TipoRet.ERROR_1;
           }
           else
           {
               return TipoRet.ERROR_2;
           }
       }
       else
       {
           return TipoRet.ERROR_3;
       }
    }
    
}
