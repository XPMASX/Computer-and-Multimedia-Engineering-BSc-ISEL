package ptrabalho;

import java.util.ArrayList;
import java.util.List;

public class BD_Base {

	protected int distance;
	protected int angulo;
    protected int raio;
    protected String file = "";
    protected int nMensagens = 8;
    private List<Mensagem> mensagens = new ArrayList<>();
	
	public BD_Base() {
		distance = 30;
        angulo = 90;
        raio = 20;
	}
	
	public int getDist()
    {
        return distance;
    }

    public void setDist(int i) 
    {
        distance = i;
    }

    public int getAng()
    {
        return angulo;
    }

    public void setAng(int i) 
    {
        angulo = i;
    }

    public int getRaio()
    {
        return raio;
    }

    public void setRaio(int i) 
    {
        raio = i;
    }
    
    public String getFile()
    {
        return file;
    }

    public void setFile(String f) 
    {
        file = f;
    }
    
    public int getNMensagens()
    {
        return nMensagens;
    }

    public void setNMensagens(int i) 
    {
    	nMensagens = i;
    }
    
    public void addMensagem(Mensagem msg)
    {
    	mensagens.add(msg);
    }
    
    public void removeMensagem()
    {
    	mensagens.remove(0);
    }
    
    public List<Mensagem> getMensagens()
    {
		return mensagens;
    }

    
}
