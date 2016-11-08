/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ayed2obligatorio2016;

import ayed1obligatorio2016.Grafo.Grafo;
import ayed1obligatorio2016.Grafo.NodoGrafo;
import ayed1obligatorio2016.ListaDoble.ListaDobleEnc;
import ayed1obligatorio2016.ListaSimple.ListaSimpleGeneric;

import clases.Servicio;
import clases.Cliente;
import clases.Servicio;
import clases.Viaje;

import java.time.LocalDateTime;

/**
 *
 * @author Daniel
 */
public class Sistema implements IMetro {
    
    private static ListaSimpleGeneric<Viaje> ListaViaje;
    private static ListaSimpleGeneric<Servicio> ListaServicio;
    private static ListaDobleEnc<Cliente> ListaCliente;

    public static ListaSimpleGeneric<Viaje> getListaViaje() {
        return ListaViaje;
    }

    public static void setListaViaje(ListaSimpleGeneric<Viaje> aListaViaje) {
        ListaViaje = aListaViaje;
    }

    public static ListaSimpleGeneric<Servicio> getListaServicio() {
        return ListaServicio;
    }

    public static void setListaServicio(ListaSimpleGeneric<Servicio> aListaServicio) {
        ListaServicio = aListaServicio;
    }

    public static ListaDobleEnc<Cliente> getListaCliente() {
        return ListaCliente;
    }

    public static void setListaCliente(ListaDobleEnc<Cliente> aListaCliente) {
        ListaCliente = aListaCliente;
    }
    

    public enum TipoRet {
        OK, ERROR_1, ERROR_2, ERROR_3, ERROR_4, NO_IMPLEMENTADA
    };

    public TipoRet crearMetro() {
        return TipoRet.NO_IMPLEMENTADA;
    }

    public TipoRet destruirMetro() {
                return TipoRet.NO_IMPLEMENTADA;
    }

    public TipoRet altaTramo(char linea, String origen, String destino, float distancia, float tarifa) {
                return TipoRet.NO_IMPLEMENTADA;
    }

    public TipoRet agregarViaje(String origen, String destino, int ciCliente, LocalDateTime fechaHora) {
                return TipoRet.NO_IMPLEMENTADA;
    }

    public TipoRet listarClientes() {
        return TipoRet.NO_IMPLEMENTADA;
    }

    public TipoRet listarViajesCliente(int ciCliente) {
                return TipoRet.NO_IMPLEMENTADA;
    }

    public TipoRet listarServiciosEstacion(String estacion) {
                return TipoRet.NO_IMPLEMENTADA;
    }

    public TipoRet listarLineas() {
                return TipoRet.NO_IMPLEMENTADA;
    }

    public TipoRet caminoMasCorto(String origen, String destino) {
                return TipoRet.NO_IMPLEMENTADA;
    }

    public TipoRet precioBoleto(String origen, String destino) {
                return TipoRet.NO_IMPLEMENTADA;
    }

    //region Hechas
    public TipoRet altaCliente(int cedula, String nombre) {
          
        Cliente unCliente = new Cliente();
       unCliente.setCedula(cedula);
       unCliente.setNombre(nombre);
              
       if(unCliente.CorrobarCanDigitos(cedula))
       {
           return TipoRet.ERROR_1;
       }
       else
       {
           if(unCliente.BuscarCliente(cedula))
           {
               ListaCliente.insertarInicio(unCliente);
               return TipoRet.OK;
           }
           
           else
           {
               return TipoRet.ERROR_2;
           }
       }
    }

    public TipoRet bajaCliente(int cedula) {
                
        Cliente unCliente = new Cliente();
        unCliente.setCedula(cedula);
        ListaDobleEnc LD = new ListaDobleEnc();
        
        if(unCliente.CorrobarCanDigitos(cedula))
        {
            if(unCliente.BuscarCliente(cedula))
            {
                LD.RemoveNodo(unCliente);
                return TipoRet.OK;
            }
            else
            {
                return TipoRet.ERROR_1;
            }
        }
        else
        {
            return TipoRet.ERROR_1;
        }
    }
    
    public TipoRet agregarServicio(String estacion, String servicio) {
             
        NodoGrafo Es = new NodoGrafo();
        Es.setNombre(estacion);
        
        Grafo G = new Grafo();
        if(G.Buscar(Es)==null)
        {
            return TipoRet.ERROR_1;
        }
        else
        {
            Servicio Ser = new Servicio();
            Ser.setNombre(servicio);
        
            Es.getServicios().insertarInicio(Ser);
            return TipoRet.OK;
        }  
    }
    //endregion
    
}
