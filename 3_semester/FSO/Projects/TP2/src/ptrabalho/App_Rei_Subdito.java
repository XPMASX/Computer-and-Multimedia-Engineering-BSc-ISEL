package ptrabalho;

import java.util.concurrent.Semaphore;

public class App_Rei_Subdito
{
    @SuppressWarnings("unused")
    private GUI_Rei_Subdito gui;
    private BD_Base bd = new BD_Base();
    private BufferCircular bc = new BufferCircular();
    private Semaphore ht = new Semaphore(1);

    public App_Rei_Subdito(String[] args) 
    {
       gui = new GUI_Rei_Subdito(bd, bc, ht);
    }

    

    public void run() throws InterruptedException 
    {
        while(true) {
        	//System.out.println("sai");
            Thread.sleep(100);
        }
		
        
    }

    public static void main(String[] args) throws InterruptedException 
    {
    	
    	App_Rei_Subdito app = new App_Rei_Subdito(args);
        System.out.println("A aplicação começou.");
        app.run();
        System.out.println("A aplicação terminou.");
    }

}