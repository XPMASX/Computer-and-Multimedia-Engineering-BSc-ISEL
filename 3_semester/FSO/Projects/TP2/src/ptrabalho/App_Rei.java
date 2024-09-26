package ptrabalho;

import java.util.concurrent.Semaphore;

public class App_Rei extends Thread
{
    @SuppressWarnings("unused")
    protected GUI_Rei gui;
    private BD_Rei bd;
    Mensagem msg;
    Mensagem myMensagem = null;
    private int state = 2;
    private int counter = 0;
    private final int escreverMensagem = 1;
    private final int dormir = 2;
    private final int bloqueado = 4;
    
    BufferCircular bufferCircular;
    Semaphore haTrabalho, livreMyMensagem, ocupadaMyMensagem, acessoMyMensagem, reiAvailable;
    

    public App_Rei(BD_Rei bdRei, BufferCircular bc, Semaphore ht, Semaphore ra) 
    {
    	bd = bdRei;
        gui = new GUI_Rei(bd);
        bufferCircular= bc;
    	haTrabalho= ht;
    	reiAvailable = ra;
    	myMensagem = null;
    	livreMyMensagem= new Semaphore(1);
    	ocupadaMyMensagem= new Semaphore(0);
    	acessoMyMensagem= new Semaphore(1);
    }

    public BD_Rei getBD() 
    {
        return bd;
    }
    
    public void setMensagem(Mensagem m)
    {
    	try {
    	livreMyMensagem.acquire();
    	acessoMyMensagem.acquire();
    	} catch (InterruptedException e) {}
    	myMensagem= m;
    	acessoMyMensagem.release();
    	ocupadaMyMensagem.release();
    }


    public void run() 
    {
        while(true) {
        	
        	switch (state) {
			
        		case escreverMensagem:
        			System.out.println("escreve");
        			System.out.println("Mensagens à espera: " + bd.getMensagens().size());
        			msg = bd.getMensagens().get(0);
        			msg.setId(counter);
        			setMensagem(msg);
        			try {
        				ocupadaMyMensagem.acquire();
        				acessoMyMensagem.acquire();
        			} catch (InterruptedException e) {}
        				bufferCircular.inserirElemento(myMensagem);
        				acessoMyMensagem.release();
        				livreMyMensagem.release();
        				haTrabalho.release();
        				System.out.println("ht:" + haTrabalho.availablePermits());
        				gui.write(" Enviei = " + msg + "\n");
        				counter =++ counter % bd.getNMensagens();
        				bd.removeMensagem();
        				state = dormir;
        				break;
        			
        		case dormir:
        			//System.out.println("sleep");
        			try {
        				Thread.sleep(1000);
        				//System.out.println("permits" + reiAvailable.availablePermits());
        				if(reiAvailable.availablePermits() == 0) {
        					state = bloqueado;
        					break;
        				}
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			//System.out.println(bd.getMensagens().size());
        			if(bd.getMensagens().size() !=0) {
        				state = escreverMensagem;
        				break;
        			} 
        			else {break;}
        			
        		case bloqueado:
        			try {
        				gui.write("Estou bloqueado \n");
        				reiAvailable.acquire();
        				gui.write("Desbloqueado \n");
        				state = dormir;
        				break;
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
				
			}
		}
		/*System.out.println("sai");
        try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
    }

    /*public static void main(String[] args) throws InterruptedException 
    {
    	
        App_Rei app = new App_Rei();
        System.out.println("A aplicação começou.");
        app.run();
        System.out.println("A aplicação terminou.");
    }*/

}