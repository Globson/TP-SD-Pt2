/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfigurinhas;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author grobs
 */
public class RMIServer extends UnicastRemoteObject implements InterfaceFigurinhas{
    ArrayList <Object> ListaUsuarios = null;
    ArrayList <Object> LVendas = null;
    
    protected RMIServer() throws RemoteException{
        super();
        ListaUsuarios = Persistencia.lerArquivoBinario("Persistencia.txt");
        LVendas = Persistencia.lerArquivoBinario("VendasFigurinha.txt");
        if(LVendas.size()<1){
           LVendas.add(new VendaFigurinha());
        }
        ((VendaFigurinha)LVendas.get(0)).PrintaFigurinhasAVenda();
    }
    
    public Usuario loga_usuario(String nome,String senha) throws RemoteException{
        boolean user_encontrado = false;
        int index_user_encontrado=0;
        System.out.println("Credenciais recebidas: "+ nome+" - "+senha);
        for(int i=0;i<ListaUsuarios.size();i++){
            //System.out.println(((Usuario)ListaUsuarios.get(i)).getNome());
            if(((Usuario)ListaUsuarios.get(i)).ComparaNomeSenha(nome,senha)){
                index_user_encontrado = i;
                user_encontrado = true;
            }
        }
        if(user_encontrado){
            return (Usuario)ListaUsuarios.get(index_user_encontrado);
        }
        return null;
    }

    public boolean cria_usuario(String nome,String senha) throws RemoteException{
        Usuario user = new Usuario(nome,senha);
        boolean disponivel = true;
        for(int i=0;i<ListaUsuarios.size();i++){
            if(((Usuario)ListaUsuarios.get(i)).getNome().equals(user.getNome())){
                disponivel = false;
            }
        }
        if(disponivel){
            ListaUsuarios.add(user);
            int index_user_encontrado=ListaUsuarios.indexOf(user);
            System.out.println("Usuario criado: "+ user.getNome());
            System.out.println("Usuario criado index: "+ index_user_encontrado);
            return true;
        }
        return false;
    }
    
    public boolean atualiza_usuario(Usuario a) throws RemoteException{
        boolean encontrado = false;
        int index = 0;
        for (int i = 0; i < ListaUsuarios.size(); i++) {
            if (((Usuario) ListaUsuarios.get(i)).getNome().equals(a.getNome())) {
                encontrado = true;
                index = i;
            }
        }
        if(encontrado){
            ListaUsuarios.remove(index);
            ListaUsuarios.add(a);
            System.out.println("Atualizando usuario modificado! -> "+ a.getNome() +" - Coins: "+ a.GetCoins());
            Persistencia.gravarArquivoBinario(ListaUsuarios,"Persistencia.txt");
            return true;
        }
        return false;
    }

    public VendaFigurinha get_vendas() throws RemoteException{
        return ((VendaFigurinha)LVendas.get(0));
    }

    public boolean atualiza_vendas(VendaFigurinha a) throws RemoteException{
        LVendas.remove(0);
        LVendas.add(a);
        ((VendaFigurinha) LVendas.get(0)).CompensaVenda(ListaUsuarios);
        System.out.println("Vendas Atualizadas!");
        ((VendaFigurinha) LVendas.get(0)).PrintaFigurinhasAVenda();
        Persistencia.gravarArquivoBinario(LVendas, "VendasFigurinha.txt");
        return true;
    }
    public static void main(String args[]) {
        try {
            RMIServer obj = new RMIServer();
            Naming.rebind("Server", obj);
            System.out.println("Server ativo!");

        }
        catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
