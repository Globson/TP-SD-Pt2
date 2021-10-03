/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfigurinhas;
import java.rmi.Naming;
import java.rmi.registry.Registry;
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
            RMI.get_vendas().PrintaFigurinhasAVenda();
            //InterfaceFigurinhas interf = (InterfaceFigurinhas) Naming.lookup("//localhost/figService");
        }
        catch (Exception e)
        {
            System.out.println("Erro: "+e);
        }
    }
}
