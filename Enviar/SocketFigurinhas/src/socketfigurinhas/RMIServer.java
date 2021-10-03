/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfigurinhas;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**
 *
 * @author grobs
 */
public class RMIServer {
    
    public static void main(String args[]) 
    {
        try {
            Registry reg = LocateRegistry.createRegistry(1099);
            System.out.println("Server do Alpokebum ativo!");
            Naming.rebind("server", new RMIServerImple());
        }
        catch (Exception e) {
            System.out.println("error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
