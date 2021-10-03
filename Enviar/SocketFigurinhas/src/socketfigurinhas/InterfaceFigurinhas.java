/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfigurinhas;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author grobs
 */
public interface InterfaceFigurinhas extends Remote {
    public Usuario loga_usuario(String nome,String senha) throws RemoteException;
    public boolean cria_usuario(String nome,String senha) throws RemoteException;
    public boolean atualiza_usuario(Usuario a) throws RemoteException;
    public VendaFigurinha get_vendas() throws RemoteException;
    public boolean atualiza_vendas(VendaFigurinha a) throws RemoteException;
}
