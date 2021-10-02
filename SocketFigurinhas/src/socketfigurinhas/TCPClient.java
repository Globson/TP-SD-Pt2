/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfigurinhas;
import java.net.*;
import java.io.*;
import java.util.Scanner;
/**
 *
 * @author grobs
 */
public class TCPClient {
    public static void main(String[] args) {

        // TODO code application logic here
        Socket s = null;
        Scanner sc = new Scanner(System.in);
        Usuario user = null;
        VendaFigurinha Vendinha = null;
        System.out.println("\tOla, bem-vindo ao cliente do Alpokebum!");
        
        int jacadastrado = 0;
        System.out.println("1 - Ja tenho cadastro");
        System.out.println("2 - Nao tenho cadastro");
        System.out.print("Entre com a opcao desejada: ");
        try{
            jacadastrado = sc.nextInt();
            sc.nextLine();
        }catch(Exception e){
            System.out.println("Erro! Entrada invalida!");
        }
        
        try{
            int serverPort=7896;
            s = new Socket("127.0.0.1",serverPort);
            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            ObjectOutputStream outObj = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream inObj = new ObjectInputStream(s.getInputStream());
            if(jacadastrado==2){
                out.writeInt(0);
                if(in.readBoolean()){
                    System.out.println("Vamos criar seu usuário!");
                    String nome;
                    String senha;
                    System.out.print("Entre com o nome de usuario: ");
                    nome = sc.nextLine();
                    System.out.print("Entre com a senha:");
                    senha = sc.nextLine();
                    user = new Usuario(nome, senha);
                    outObj.writeObject(user);
                    if(in.readBoolean()){
                        System.out.println("Usuario criado com sucesso!");
                    }
                    else{
                        System.out.println("Erro! Nome de usuario ja utilizado!");
                        user = null;
                    }
                }
                
            //enviar requisição de verificar se nome esta disponivel e de criar novo usuario.
            
            }else{
                out.writeInt(1);
                if(in.readBoolean()){
                    String nome;
                    String senha;
                    System.out.print("Entre com o nome de usuario: ");
                    nome = sc.nextLine();
                    System.out.print("Entre com a senha:");
                    senha = sc.nextLine();
                    out.writeUTF(nome);
                    //if(in.readBoolean()){
                        out.writeUTF(senha);
                        //if(in.readBoolean()){
                            if(in.readBoolean()){
                                System.out.println("Usuario logado com sucesso!");
                                user = (Usuario)inObj.readObject();
                            }
                            else{
                                System.out.println("Falha! Nome ou senha errado!");
                            }
                            
                        //}
                    //}
                    
                }
                
            //enviar requisição de login
            }
            while(true){
                if(user==null){break;}
                System.out.println("\tMenu principal");
                System.out.println("1 - Imprimir meu album");
                System.out.println("2 - Comprar Coins");
                System.out.println("3 - Comprar pacotes de figurinhas");
                System.out.println("4 - Colar figurinhas");
                System.out.println("5 - Colar todas figurinhas disponiveis");
                System.out.println("6 - Vender figurinhas no mercado");
                System.out.println("7 - Comprar figurinhas no mercado");
                System.out.println("8 - Imprimir inventario"); //figurinhas sem colar e coins
                System.out.println("9 - Finalizar");
                System.out.print("Entre com a opcao desejada: ");
                int opcao = 0;
                try{
                    opcao= sc.nextInt();
                     sc.nextLine();
                }catch(Exception e){
                    System.out.println("Erro! Entrada invalida!");
                }
                if(opcao==9){
                    out.writeBoolean(false);
                    user.PrintaFigurinhas();
                    outObj.reset();
                    outObj.writeObject(user);
                    break;
                }
                switch(opcao){
                    case(1):
                        user.PrintaAlbum();
                        break;
                    case(2):
                        System.out.println("Digite a quantidade de coins em REAIS que deseja comprar: ");
                        float valorcoins = sc.nextFloat();
                        user.AdicionaCoins(valorcoins);
                        break;
                    case(3):
                        System.out.println("Coins na carteira: "+user.GetCoins());
                        System.out.println("Digite a quantidade de pacotes a comprar: ");
                        int quantPac = sc.nextInt();
                        if(user.CompraPacoteFigurinha(quantPac)){
                            System.out.println("Pacote(s) comprado com sucesso!");
                        }else{
                             System.out.println("Erro! Voce esta pobre!");   
                        }
                        break;
                    case(4):
                        System.out.println("Figurinhas no inventario: ");
                        user.PrintaFigurinhas();
                        System.out.println("\nEntre com o numero da figurinha que deseja colar: ");
                        int numFig = sc.nextInt();
                        if(user.ColaFigurinha(numFig)){
                            System.out.println("Figurinha colada com sucesso!");
                        }else{
                            System.out.println("Erro! Voce nao tem essa figurinha disponivel ou ela ja esta colada!");
                        }      
                        break;
                    case(5):
                        user.ColaTodasFigurinhas();
                        break;
                    case(6):
                        out.writeBoolean(true); //vende fig
                        //out.writeInt(1); //venda
                        Vendinha = (VendaFigurinha)inObj.readObject();
                        user.PrintaFigurinhas();
                        System.out.println("Entre com a figurinha a vender:");
                        int numfigv = sc.nextInt();
                        System.out.println("Entre com o valor a vender:");
                        float valorfigv = sc.nextFloat();
                        
                        if(Vendinha.ColocaFigurinhaAVenda(user, numfigv, valorfigv)){
                            System.out.println("Figurinha posta a venda com sucesso!");
                        }else{
                            System.out.println("Erro! Voce nao tem essa figurinha ou valor invalido!");
                        }
                        outObj.reset();
                        outObj.writeObject(Vendinha);
                        break;
                    case(7):
                        out.writeBoolean(true); //compra fig
                        Vendinha = (VendaFigurinha)inObj.readObject();
                        Vendinha.PrintaFigurinhasAVenda();
                        System.out.println("Coins na carteira: "+user.GetCoins());
                        System.out.println("Entre com a figurinha que deseja comprar:");
                        int numfigc = sc.nextInt();
                        System.out.println("Entre com o valor da figurinha que deseja comprar:");
                        float valorfigc = sc.nextFloat();
                        if(Vendinha.CompraFigurinha(user, numfigc,valorfigc)){
                            System.out.println("Figurinha comprada com sucesso!");
                        }else{
                            System.out.println("Erro! Voce esta pobre ou essa figurinha nao esta disponivel!");
                        }
                        outObj.reset();
                        outObj.writeObject(Vendinha);
                        break;
                    case(8):
                        user.PrintaFigurinhas();
                        break;
                    
                }
                        
            }
   
        }
        catch(IOException e){
        
        }catch(ClassNotFoundException e2){}
        finally{
            if(s!=null) try{s.close();}catch(IOException e){}
        }
        sc.close();
    }
}
