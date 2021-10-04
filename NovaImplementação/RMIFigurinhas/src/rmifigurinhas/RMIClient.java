/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmifigurinhas;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.rmi.registry.LocateRegistry;
/**
 *
 * @author grobs
 */
public class RMIClient {
     public static void main(String[] args)
    {
        try
        {
            Registry reg = LocateRegistry.getRegistry("127.0.0.1",1099);
            InterfaceFigurinhas RMI = (InterfaceFigurinhas) reg.lookup("server");
            Scanner sc = new Scanner(System.in);
            Usuario user = null;
            boolean login = false;
            VendaFigurinha Vendinha = null;
            System.out.println("\tOla, bem-vindo ao cliente do Alpokebum!");

            int jacadastrado = 0;
            System.out.println("1 - Ja tenho cadastro");
            System.out.println("2 - Nao tenho cadastro");
            System.out.print("Entre com a opcao desejada: ");
            try {
                jacadastrado = sc.nextInt();
                sc.nextLine();
            } catch (Exception e) {
                System.out.println("Erro! Entrada invalida!");
            }
            if(jacadastrado==2){
                System.out.println("Vamos criar seu usuário!");
                String nome;
                String senha;
                System.out.print("Entre com o nome de usuario: ");
                nome = sc.nextLine();
                System.out.print("Entre com a senha:");
                senha = sc.nextLine();
                boolean usercriado = RMI.cria_usuario(nome, senha);
                if(usercriado){
                    System.out.println("Usuario criado com sucesso!");
                    login = RMI.loga_usuario(nome, senha);     
                    if(login){
                        System.out.println("Erro ao logar!");
                    }
                    else{
                        System.out.println("Logado com sucesso!");
                    }
                }
                else{
                    System.out.println("Erro! Nome de usuario ja utilizado!");
                }
                
               
            
            }else{
                
                String nome;
                String senha;
                System.out.print("Entre com o nome de usuario: ");
                nome = sc.nextLine();
                System.out.print("Entre com a senha:");
                senha = sc.nextLine();
                login = RMI.loga_usuario(nome, senha);
            
                if(login){
                    System.out.println("Usuario logado com sucesso!");
                }
                else{
                    System.out.println("Falha! Nome ou senha errado!");
                }   
            }
            while(true){
                if(!login){
                    System.out.println("Erro! Voce nao esta autenticado!");
                    break;
                }
                user = RMI.get_usuario();
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
                    RMI.atualiza_usuario();
                    System.out.println("Finalizando cliente do Alpokebum...");
                    //user.PrintaFigurinhas();
                    break;
                }
                switch(opcao){
                    case(1):
                        user.PrintaAlbum();
                        break;
                    case(2):
                        System.out.println("Digite a quantidade de coins em REAIS que deseja comprar: ");
                        float valorcoins = sc.nextFloat();
                        boolean compra = RMI.compra_coins(valorcoins);
                        if(compra){
                            System.out.println("Coins comprados com sucesso!");
                        }else{
                            System.out.println("Erro! Voce nao esta logado!");
                        }
                        break;
                    case(3):
                        System.out.println("Coins na carteira: "+user.GetCoins());
                        System.out.println("Digite a quantidade de pacotes a comprar: ");
                        int quantPac = sc.nextInt();
                        if(RMI.compra_pacotes(quantPac)){
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
                        if(RMI.cola_figurinha(numFig)){
                            System.out.println("Figurinha colada com sucesso!");
                        }else{
                            System.out.println("Erro! Voce nao tem essa figurinha disponivel ou ela ja esta colada!");
                        }      
                        break;
                    case(5):
                        if(RMI.cola_todas_figurinhas()){
                            System.out.println("Todas as figurinhas disponiveis coladas!");
                        }
                        break;
                    case(6):
                        Vendinha = RMI.get_vendas();
                        user.PrintaFigurinhas();
                        System.out.println("Entre com a figurinha a vender:");
                        int numfigv = sc.nextInt();
                        System.out.println("Entre com o valor a vender:");
                        float valorfigv = sc.nextFloat();
                        
                        if(RMI.vende_figurinha(numfigv, valorfigv)){
                            System.out.println("Figurinha posta a venda com sucesso!");
                        }else{
                            System.out.println("Erro! Voce nao tem essa figurinha ou valor invalido!");
                        }
                        RMI.atualiza_vendas();
                        break;
                    case(7):
                        Vendinha = RMI.get_vendas();
                        Vendinha.PrintaFigurinhasAVenda();
                        System.out.println("Coins na carteira: "+user.GetCoins());
                        System.out.println("Entre com a figurinha que deseja comprar:");
                        int numfigc = sc.nextInt();
                        System.out.println("Entre com o valor da figurinha que deseja comprar:");
                        float valorfigc = sc.nextFloat();
                        if(RMI.compra_figurinha(numfigc, valorfigc)){
                            System.out.println("Figurinha comprada com sucesso!");
                        }else{
                            System.out.println("Erro! Voce esta pobre ou essa figurinha nao esta disponivel!");
                        }
                        RMI.atualiza_vendas();
                        break;
                    case(8):
                        user.PrintaFigurinhas();
                        break;
                    
                }
                        
            }
            
        }
        catch (Exception e)
        {
            System.out.println("Erro: "+e);
        }
    }
}
