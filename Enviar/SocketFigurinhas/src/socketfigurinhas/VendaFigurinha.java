/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketfigurinhas;
import java.util.ArrayList;
import java.io.Serializable;
/**
 *
 * @author grobs
 */
public class VendaFigurinha implements Serializable{
    private static final long serialVersionUID = 1L;
    private ArrayList<Integer> Lista_Figurinhas;
    private ArrayList<Float> Valor_Figurinhas;
    private ArrayList<String> Proprietarios;
    boolean NecessitaCompensacao;
    private String Nome_Vendedor_a_Compensar;
    private float Valor_a_Compensar;
    
    public VendaFigurinha(){
        this.Lista_Figurinhas = new ArrayList();
        this.Valor_Figurinhas = new ArrayList();
        this.Proprietarios = new ArrayList();
        this.NecessitaCompensacao = false;
        this.Nome_Vendedor_a_Compensar = null;
        this.Valor_a_Compensar = 0;
    }
    
    public void PrintaFigurinhasAVenda(){
        System.out.println("------Inicio de lista figurinhas a venda------");
        for(int i=0;i<this.Lista_Figurinhas.size();i++){
            System.out.println("Figurinha: "+this.Lista_Figurinhas.get(i)+" Valor: "+this.Valor_Figurinhas.get(i));
        }
        System.out.println("------Fim de lista figurinhas a venda------");
    }
    
    public boolean ColocaFigurinhaAVenda(Usuario A,Integer Figurinha,float Valor){
        if(Valor>=0){
            if(A.RetiraFigurinhaSemColar(Figurinha)){
                this.Lista_Figurinhas.add(Figurinha);
                this.Valor_Figurinhas.add(Valor);
                this.Proprietarios.add(A.getNome());
                return true;
            }
            return false;
            
        }
            return false;
    }
    
    public boolean CompraFigurinha(Usuario A, int Figurinha, float Valor){
        boolean Encontrado = false;
        int index=0;
        while(!Encontrado && index<Lista_Figurinhas.size()){
            if(this.Lista_Figurinhas.get(index)==Figurinha && this.Valor_Figurinhas.get(index)==Valor){
                Encontrado = true;
                break;
            }
            index++;
        }
        if(Encontrado){
            //int index = this.Lista_Figurinhas.lastIndexOf(Figurinha);
            if(A.SubtraiCoins(this.Valor_Figurinhas.get(index))){
                A.AdicionaFigurinhaSemColar(Figurinha);
                //this.Proprietarios.get(index).SomaCoins(this.Valor_Figurinhas.get(index));
                if(A.getNome().equals(this.Proprietarios.get(index))){
                    A.AdicionaCoins(this.Valor_Figurinhas.get(index));
                }
                else{
                    this.NecessitaCompensacao = true;
                    this.Nome_Vendedor_a_Compensar = this.Proprietarios.get(index);
                    this.Valor_a_Compensar = this.Valor_Figurinhas.get(index);
                }
                this.Valor_Figurinhas.remove(index); //Pode acontecer bugs, verificar dps se remove figurinha por indice ou por conteudo, pois ambos sao int
                this.Lista_Figurinhas.remove(index);
                this.Proprietarios.remove(index);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    
    public void CompensaVenda(ArrayList<Object> ListaUsuarios){
        if(this.NecessitaCompensacao){
            for(int i=0;i<ListaUsuarios.size();i++){
                if(((Usuario)ListaUsuarios.get(i)).getNome().equals(this.Nome_Vendedor_a_Compensar)){
                    ((Usuario)ListaUsuarios.get(i)).AdicionaCoins(this.Valor_a_Compensar);
                    break;
                }
            }
            this.NecessitaCompensacao = false;
            this.Nome_Vendedor_a_Compensar = null;
            this.Valor_a_Compensar = 0;
        }
    }
}
