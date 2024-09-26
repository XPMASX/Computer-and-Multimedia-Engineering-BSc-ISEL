package ptrabalho;

import java.util.concurrent.Semaphore;

public class BD_Gravar extends BD_Base {

	
	private String nome;
	private boolean gravar, reproduzir, gravarOff = false;
	private Semaphore gravarS = new Semaphore(0);
	
	public BD_Gravar()
    {
    	super();
    	
    }
	
	public void setNome(String n)
    {
        nome = n;
    }

    public String getNome()
    {
        return nome;
    }
    
    public void setGravar(boolean g) throws InterruptedException
    {
        gravar = g;
        if (!g) 
        	gravarS.release();
        	
    }

    public boolean getGravar()
    {
        return gravar;
    }
    
    public Semaphore getGravarS()
    {
    	return gravarS;
    }
    
    public void setReproduzir(boolean r)
    {
        reproduzir = r;
    }

    public boolean getReproduzir()
    {
        return reproduzir;
    }
    
    public void setGravarOff(boolean g)
    {
    	gravarOff = g;
    }
    
    public boolean getGravarOff()
    {
    	return gravarOff;
    }
    
    
}
