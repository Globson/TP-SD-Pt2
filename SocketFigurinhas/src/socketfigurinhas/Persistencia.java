/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfigurinhas;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author grobs
 */
public class Persistencia {
    //Serialização e gravação em arquivo para persistencia de objetos em arquivo nomeArquivo
    public static void gravarArquivoBinario(ArrayList<Object> listaObjs, String nomeArquivo) {
      File arquivo = new File(nomeArquivo);
      try {
        arquivo.delete();
        arquivo.createNewFile();

        ObjectOutputStream ObjOut = new ObjectOutputStream(new FileOutputStream(arquivo));
        ObjOut.writeObject(listaObjs);
        ObjOut.close();

      } catch(IOException e) {
          System.out.printf("Erro: %s", e.getMessage());
      }
    }
    
    //Leitura e desserialização de arquivo nomeArquivo gerado de objetos persistentes
    public static ArrayList<Object> lerArquivoBinario(String nomeArquivo) {
      ArrayList<Object> listaObjs = new ArrayList();
      try {
        File arquivo = new File(nomeArquivo);
        if (arquivo.exists()) {
           ObjectInputStream ObjIn = new ObjectInputStream(new FileInputStream(arquivo));
           listaObjs = (ArrayList<Object>)ObjIn.readObject();
           ObjIn.close();
        }
      } catch(IOException e1) {
          System.out.printf("Erro: %s", e1.getMessage());
      } catch(ClassNotFoundException e2) {
          System.out.printf("Erro: %s", e2.getMessage());
      }

      return(listaObjs);
    }

 }

