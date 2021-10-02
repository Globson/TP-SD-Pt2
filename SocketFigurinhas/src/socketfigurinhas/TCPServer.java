/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfigurinhas;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
/**
 *
 * @author grobs
 */
public class TCPServer {
    public static void main(String[] args) {
        // TODO code application logic here
        ArrayList <Object> ListaUsuarios = null;
        ListaUsuarios = Persistencia.lerArquivoBinario("Persistencia.txt");
        ArrayList <Object> LVendas = null;
        LVendas = Persistencia.lerArquivoBinario("VendasFigurinha.txt");
        if(LVendas.size()<1){
            LVendas.add(new VendaFigurinha());
        }
        ((VendaFigurinha)LVendas.get(0)).PrintaFigurinhasAVenda();
        ServerSocket listenSocket = null;
        Socket clientSocket = null;
        DataInputStream in;
        DataOutputStream out;
        ObjectInputStream inObj;
        ObjectOutputStream outObj;
        int serverPort = 7896;
        int index_user_encontrado=-1;
        //Implementar leitura de arquivo persistente 
        while(true){
            try{
            listenSocket = new ServerSocket (serverPort);
            System.out.println("Servidor Alpokebum!");
            System.out.println("Esperando...");
            clientSocket = listenSocket.accept();
            
            
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            inObj = new ObjectInputStream(clientSocket.getInputStream());
            outObj = new ObjectOutputStream(clientSocket.getOutputStream());
            int Entrada = in.readInt();
            if(Entrada==0){ //criar cliente
                out.writeBoolean(true); //ack
                Usuario user = (Usuario)inObj.readObject();
                boolean disponivel = true;
                for(int i=0;i<ListaUsuarios.size();i++){
                    if(((Usuario)ListaUsuarios.get(i)).getNome().equals(user.getNome())){
                        disponivel = false;
                    }
                }
                if(disponivel){
                    ListaUsuarios.add(user);
                    index_user_encontrado=ListaUsuarios.indexOf(user);
                    out.writeBoolean(true); //ack
                    System.out.println("Usuario criado: "+ user.getNome());
                    System.out.println("Usuario criado index: "+ index_user_encontrado);
                }
                else{
                    out.writeBoolean(false); //ack
                }
                
                
            }
            else if(Entrada==1){// logar cliente ja existente
                //System.out.println("Entrou em usuario existente");
                out.writeBoolean(true); //ack
                String nome = in.readUTF();
                //out.writeBoolean(true);
                String senha = in.readUTF();
                //out.writeBoolean(true);
                boolean user_encontrado = false;
                System.out.println("Credenciais recebidas: "+ nome+" - "+senha);
                for(int i=0;i<ListaUsuarios.size();i++){
                    //System.out.println(((Usuario)ListaUsuarios.get(i)).getNome());
                    if(((Usuario)ListaUsuarios.get(i)).ComparaNomeSenha(nome,senha)){
                        index_user_encontrado = i;
                        user_encontrado = true;
                    }
                }
                out.writeBoolean(user_encontrado);
                if(user_encontrado){
                    outObj.writeObject(((Usuario)ListaUsuarios.get(index_user_encontrado)));
                }
            }
            
            while(index_user_encontrado != -1){ //while continuo se usuario é valido
                if(in.readBoolean()){
                    //if(in.readInt()==1){ //venda fig
                    outObj.reset();
                    outObj.writeObject((VendaFigurinha)LVendas.get(0));
                    LVendas.remove(0);
                    LVendas.add(inObj.readObject());
                    ((VendaFigurinha)LVendas.get(0)).CompensaVenda(ListaUsuarios);
                    System.out.println("Vendas Atualizadas!");
                    ((VendaFigurinha)LVendas.get(0)).PrintaFigurinhasAVenda();
                    Persistencia.gravarArquivoBinario(LVendas,"VendasFigurinha.txt");                
                }
                else{
                    Usuario userAtualizado = (Usuario)inObj.readObject();
                    System.out.println("Atualizando usuario modificado! -> "+userAtualizado.getNome() +" - Coins: "+ userAtualizado.GetCoins());
                    //userAtualizado.PrintaFigurinhas();
                    ListaUsuarios.remove(index_user_encontrado);
                    ListaUsuarios.add(userAtualizado);
                    Persistencia.gravarArquivoBinario(ListaUsuarios,"Persistencia.txt");
                    break;
                }
            }
            //String data = in.readUTF();
            //Usuario user = (Usuario)inObj.readObject();
            //System.out.println("Um cliente chegou! Ele disse "+data);
            //out.writeUTF("PONG");
            //System.out.println("Eu respondi PONG!");

            }catch(IOException e){}
            catch(ClassNotFoundException e){}
            finally{
                try{
                    //Persistencia.gravarArquivoBinario(ListaUsuarios,"Persistencia.txt");
                    listenSocket.close();
                    clientSocket.close();
                }catch(IOException e){}
            }
        }
        
    }
}
