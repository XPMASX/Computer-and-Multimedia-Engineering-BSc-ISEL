package ptrabalho;
import java.lang.Math;
import java.util.concurrent.Semaphore;

public class App_Subdito extends Thread
{
    @SuppressWarnings("unused")
    protected GUI_Subdito gui;
    private BD_Subdito bd;
    Mensagem msg = null;
    Mensagem myMensagem = null;
    private int state = 2;
    private int counter = 0;
    private final int receberMensagem = 1;
    private final int dormir = 2;
    private final int esperarTempoExecucao = 3;
    private final int bloqueado = 4;
    private final int reproduzir = 5;
    private final int RETA = 1;
    private final int CURVARDIR = 2;
    private final int CURVARESQ = 3;
    private final int PARAR = 4;
    private boolean aReproduzir = false;
    
    BufferCircular bufferCircular, bufferCircularReproduzir;
    Semaphore haTrabalho, livreMyMensagem, ocupadaMyMensagem, acessoMyMensagem, subAvailable, haTrabalhoG;

    public App_Subdito(BD_Subdito bdSub, BufferCircular bc, Semaphore ht, Semaphore sub, BufferCircular bcG, Semaphore htG) 
    {
    	bd = bdSub;
    	
        gui = new GUI_Subdito(bd);
        
        subAvailable = sub;
        bufferCircular= bc;
        bufferCircularReproduzir = bcG;
        haTrabalho= ht;
        haTrabalhoG= htG;
        myMensagem= null;
        livreMyMensagem= new Semaphore(1);
        ocupadaMyMensagem= new Semaphore(0);
        acessoMyMensagem= new Semaphore(1);
    }

    public BD_Subdito getBD() 
    {
        return bd;
    }
    
    public Mensagem getMensagem() {
    	try { ocupadaMyMensagem.acquire();
    	acessoMyMensagem.acquire();
    	} catch (InterruptedException e) {}
    	Mensagem s= myMensagem;
    	acessoMyMensagem.release();
    	livreMyMensagem.release();
    	return s;
    	}


    public void run()  
    {
        	while(!bd.getTerminar()) {
            	
            	switch (state) {
    			
            	case receberMensagem:
            		System.out.println("recebe");
            		gui.write(" Recebi = " + msg + "\n");
            		bd.addMensagem(msg);
            		if (bd.isLigado())
            			state = esperarTempoExecucao;
            		else
            			state = dormir;
            		break;
            			
    			case dormir:
    				try {
						Thread.sleep(100);
						//System.out.println("permits" + subAvailable.availablePermits());
        				if(subAvailable.availablePermits() == 0) {
        					state = bloqueado;
        					break;
        				}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				//System.out.println("busca" + aReproduzir );
    				if(bufferCircularReproduzir.available() != 0 || aReproduzir)
    				{
    					System.out.println("REPRODUZIR");
    					state = reproduzir;
    					break;
    				}
    				if (bufferCircular.available() != 0) {
    					//System.out.println(haTrabalho.availablePermits());
    					try { 
    						haTrabalho.acquire();
    						livreMyMensagem.acquire();
    						} catch (InterruptedException e) {}
    					Mensagem m= bufferCircular.removerElemento();
    					try { acessoMyMensagem.acquire(); }
    					catch (InterruptedException e) {}
    					myMensagem = m;
    					acessoMyMensagem.release();
    					ocupadaMyMensagem.release();
    					msg = getMensagem(); 
    					//System.out.println(bd.getNMensagens());
    					state = receberMensagem;
    					break;
    				}
    				else
    					if (bd.isLigado() && bd.getMensagens().size() != 0)
    						state = esperarTempoExecucao;
    					break;
    					
    				
    			case esperarTempoExecucao:
    				msg = bd.getMensagens().get(0);
    				//System.out.println("esperaExec id:" + msg.getId());
    				int tipo = msg.getTipo();
    				//System.out.println("Tipo:" + tipo);
    				try {
    				switch (tipo) {
    					case RETA:
    						gui.write(" O robot avançou " + msg.getArg1() + "\n");
    						bd.getRobot().reta(msg);
    						bd.getRobot().parar(msg);
    						Thread.sleep((long) ((Math.abs(msg.getArg1())) / 0.03));
    						break;
    					case CURVARDIR:
    						gui.write(" O robot virou direita com raio = " + msg.getArg1() +" e angulo = " + msg.getArg2() + "\n");
    						bd.getRobot().curvarDireita(msg);
    						bd.getRobot().parar(msg);
    						Thread.sleep((long) ((msg.getArg1() * (msg.getArg2() * 0.017)) / 0.03));
    						break;
    					case CURVARESQ:
    						gui.write(" O robot virou esquerda com raio = " + msg.getArg1() +" e angulo = " + msg.getArg2() + "\n");
        					bd.getRobot().curvarEsquerda(msg);
        					bd.getRobot().parar(msg);
    						Thread.sleep((long) ((msg.getArg1() * (msg.getArg2() * 0.017)) / 0.03));
    						break;
    					case PARAR:
    						gui.write(" O robot parou \n");
        					bd.getRobot().parar(msg);
    						Thread.sleep(100);
    						break;
    				}
    				} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				bd.removeMensagem();
    				state = dormir;
    				break;
    				
    			case bloqueado:
        			try {
        				gui.write("Estou bloqueado \n");
        				subAvailable.acquire();
        				gui.write("Desbloqueado \n");
        				state = dormir;
        				break;
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			
    			case reproduzir:
    				
    				try { 
    					haTrabalhoG.acquire();
						System.out.println("haT");
						livreMyMensagem.acquire();
					} catch (InterruptedException e) {}
					Mensagem m= bufferCircularReproduzir.removerElemento();
					
					try { acessoMyMensagem.acquire(); }
					catch (InterruptedException e) {}
					myMensagem = m;
					acessoMyMensagem.release();
					ocupadaMyMensagem.release();
					msg = getMensagem(); 
					//System.out.println(bd.getNMensagens());
					
					if (msg.getTipo() != 5)
						gui.write(" Vou reproduzir = " + msg + "\n");
					else
						gui.write(" Acabou a reprodução \n");
            		
            		aReproduzir = true;
            		//if (bd.isLigado())
            		if(msg.tipo == 5) {
            			aReproduzir = false;
            			state = dormir;
            			break;
            		}
            		bd.addMensagem(msg);
            		state = esperarTempoExecucao;
					break;
        			
    			}
    		}
    		System.out.println("sai");
            
    }

    /*public static void main(String[] args) throws InterruptedException 
    {
        App_Subdito app = new App_Subdito();
        System.out.println("A aplicação começou.");
        app.run();
        System.out.println("A aplicação terminou.");
    }*/

}