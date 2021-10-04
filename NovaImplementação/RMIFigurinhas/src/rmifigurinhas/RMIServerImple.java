/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmifigurinhas;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 *
 * @author grobs
 */
public class RMIServerImple extends UnicastRemoteObject implements InterfaceFigurinhas{
    ArrayList <Object> ListaUsuarios = null;
    ArrayList <Object> LVendas = null;
    int index_user_logado;
    
    protected RMIServerImple() throws RemoteException{
        super();
        ListaUsuarios = Persistencia.lerArquivoBinario("Persistencia.txt");
        LVendas = Persistencia.lerArquivoBinario("VendasFigurinha.txt");
        if(LVendas.size()<1){
           LVendas.add(new VendaFigurinha());
        }
        ((VendaFigurinha)LVendas.get(0)).PrintaFigurinhasAVenda();
        this.index_user_logado = -1;
    }
    
    public boolean loga_usuario(String nome,String senha) throws RemoteException{
        boolean user_encontrado = false;
        System.out.println("Credenciais recebidas: "+ nome+" - "+senha);
        for(int i=0;i<ListaUsuarios.size();i++){
            //System.out.println(((Usuario)ListaUsuarios.get(i)).getNome());
            if(((Usuario)ListaUsuarios.get(i)).ComparaNomeSenha(nome,senha)){
                this.index_user_logado = i;
                user_encontrado = true;
            }
        }
        return user_encontrado;
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
    
    public boolean atualiza_usuario() throws RemoteException{ 
        if(this.index_user_logado != -1){
            System.out.println("Atualizando usuario modificado! -> "+ ((Usuario)ListaUsuarios.get(this.index_user_logado)).getNome() +" - Coins: "+ ((Usuario)ListaUsuarios.get(this.index_user_logado)).GetCoins());
            Persistencia.gravarArquivoBinario(ListaUsuarios,"Persistencia.txt");
            this.index_user_logado = -1;
            return true;
        }
        return false;
    }

    public VendaFigurinha get_vendas() throws RemoteException{
        return ((VendaFigurinha)LVendas.get(0));
    }

    public boolean atualiza_vendas() throws RemoteException{
        ((VendaFigurinha) LVendas.get(0)).CompensaVenda(ListaUsuarios);
        System.out.println("Vendas Atualizadas!");
        ((VendaFigurinha) LVendas.get(0)).PrintaFigurinhasAVenda();
        Persistencia.gravarArquivoBinario(LVendas, "VendasFigurinha.txt");
        return true;
    }
    
    public boolean compra_coins(float valor) throws RemoteException{
        if(this.index_user_logado != -1){
            return ((Usuario)ListaUsuarios.get(this.index_user_logado)).AdicionaCoins(valor);    
        }
        return false;
    }
    
    public boolean compra_pacotes(int quant) throws RemoteException{
        if(this.index_user_logado != -1){
            return ((Usuario)ListaUsuarios.get(this.index_user_logado)).CompraPacoteFigurinha(quant);    
        }
        return false;
    }
    public boolean cola_figurinha(int fig) throws RemoteException{
        if(this.index_user_logado != -1){
            return ((Usuario)ListaUsuarios.get(this.index_user_logado)).ColaFigurinha(fig);    
        }
        return false;
    }
    public boolean cola_todas_figurinhas() throws RemoteException{
        if(this.index_user_logado != -1){
            ((Usuario)ListaUsuarios.get(this.index_user_logado)).ColaTodasFigurinhas();    
            return true;
        }
        return false;
    }
    
    public boolean vende_figurinha(int fig,float valor) throws RemoteException{
        if(this.index_user_logado != -1){
            return ((VendaFigurinha) LVendas.get(0)).ColocaFigurinhaAVenda(((Usuario)ListaUsuarios.get(this.index_user_logado)), fig, valor);
        }
        return false;
    }
    
    public boolean compra_figurinha(int fig,float valor) throws RemoteException{
        if(this.index_user_logado != -1){
            return ((VendaFigurinha) LVendas.get(0)).CompraFigurinha(((Usuario)ListaUsuarios.get(this.index_user_logado)), fig, valor);
        }
        return false;
    }
   
    
    public Usuario get_usuario() throws RemoteException{
        return ((Usuario)ListaUsuarios.get(this.index_user_logado));
    }
    
}
