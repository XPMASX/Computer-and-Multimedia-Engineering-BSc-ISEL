package ptrabalho;
import robot.RobotLegoEV3;

public class BD_Subdito extends BD_Base
{
    private RobotLegoEV3 robot;
    private boolean terminar;
    private boolean ligado;
    
    private String nome;

    public BD_Subdito()
    {
    	super();
        robot = new RobotLegoEV3();
        terminar = false;
        ligado = false;
        
    }

    public RobotLegoEV3 getRobot() 
    {
        return robot;
    }

    public boolean getTerminar() 
    {
        return terminar;
    }

    public void setTerminar(boolean b) 
    {
        terminar = b;
    }

    public boolean isLigado() 
    {
        return ligado;
    }

    public void setLigado(boolean b) 
    {
        ligado = b;
    }

    public void setNome(String n)
    {
        nome = n;
    }

    public String getNome()
    {
        return nome;
    }

}