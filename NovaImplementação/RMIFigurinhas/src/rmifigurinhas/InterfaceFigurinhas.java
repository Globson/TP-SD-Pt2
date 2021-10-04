/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmifigurinhas;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author grobs
 */
public interface InterfaceFigurinhas extends Remote {
    public boolean loga_usuario(String nome,String senha) throws RemoteException;
    public boolean compra_coins(float valor) throws RemoteException;
    public boolean compra_pacotes(int quant) throws RemoteException;
    public boolean cola_figurinha(int fig) throws RemoteException;
    public boolean cola_todas_figurinhas() throws RemoteException;
    public boolean vende_figurinha(int fig,float valor) throws RemoteException;
    public boolean compra_figurinha(int fig,float valor) throws RemoteException;
    public boolean cria_usuario(String nome,String senha) throws RemoteException;
    public boolean atualiza_usuario() throws RemoteException;
    public VendaFigurinha get_vendas() throws RemoteException;
    public Usuario get_usuario() throws RemoteException;
    public boolean atualiza_vendas() throws RemoteException;
}
