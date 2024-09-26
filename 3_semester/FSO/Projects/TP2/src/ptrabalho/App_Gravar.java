package ptrabalho;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Semaphore;

public class App_Gravar extends Thread {
		
	private Gravador gravador;
	protected GUI_Gravar gui;
    private BD_Gravar bd;
    Mensagem msg;
    Mensagem myMensagem = null;
    private int state = 2;
    private int counter = 0;
    private final int reproduzir = 1;
    private final int dormir = 2;
    private final int gravar = 3;
    private final int bloqueado = 4;
    private final int ler = 5;
    
    BufferCircular bufferCircular;
    private Semaphore haTrabalho, livreMyMensagem, ocupadaMyMensagem, acessoMyMensagem, gravarAvailable;
	
	public App_Gravar(BD_Gravar bg, Gravador g, Semaphore ga, BufferCircular bc, Semaphore ht) 
    {
    	gravador = g;
        gui = new GUI_Gravar(bg);
        bd = bg;
        bufferCircular= bc;
        haTrabalho = ht;
        gravarAvailable = ga;
        livreMyMensagem= new Semaphore(1);
    	ocupadaMyMensagem= new Semaphore(0);
    	acessoMyMensagem= new Semaphore(1);
       
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
	
	public void bloqueado()
	{
		try {
			gui.write("Estou bloqueado \n");
			gravarAvailable.acquire();
			gui.write("Desbloqueado \n");
			state = dormir;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void run() 
    {
        while(true) {
        	
        	switch (state) {
        			
        		case dormir:
        			//System.out.println("sleep");
        			try {
        				Thread.sleep(100);
        				//System.out.println("permits" + reiAvailable.availablePermits());
        				if(gravarAvailable.availablePermits() == 0) {
        					state = bloqueado;
        					break;
        				}
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
        			if (bd.getGravar())
        				state = gravar;
        			if(bd.getReproduzir())
        				state = reproduzir;
        			break;
        			
        		
        		case gravar:
        			System.out.println(bd.getGravarS().availablePermits());
        			gui.write("Estou a Gravar \n");
        			try {
						bd.getGravarS().acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    				gui.write("Parei de gravar \n");
    				state = dormir;
    				break;
        		
        		case reproduzir:
        		    String filePath = bd.getNome();
        		    long currentTime = 0, lastTime = 0;
        		    // Try-with-resources to automatically close resources (like BufferedReader)
        		    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        		        String line;
        		        int numLine = 0;  // Initialize the line number
        		        while ((line = reader.readLine()) != null) {
        		              // Increment the line number for each line read

        		        	if(gravarAvailable.availablePermits() == 0)
        		        		bloqueado();
        		        	
        		            // Split the line into parts using a delimiter (assuming it's a CSV format)
        		            String[] parts = line.split(",");

        		            // Assuming the first part is an integer
        		            int tipo = Integer.parseInt(parts[0]);

        		            switch (tipo) {
        		                case 1:
        		                    // Assuming numLine is declared and initialized somewhere in your code
        		                    msg = new MensagemReta(numLine, tipo, Integer.parseInt(parts[1]));
        		                    currentTime = Long.parseLong(parts[2]);
        		                    bd.addMensagem(msg);
        		                    break;
        		                
        		                case 2:
        		                    // Assuming numLine is declared and initialized somewhere in your code
        		                    msg = new MensagemCurvar(numLine, tipo, Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        		                    currentTime = Long.parseLong(parts[3]);
        		                    bd.addMensagem(msg);
        		                    break;
        		                
        		                case 3:
        		                    // Assuming numLine is declared and initialized somewhere in your code
        		                	msg = new MensagemCurvar(numLine, tipo, Integer.parseInt(parts[1]), Integer.parseInt(parts[2]));
        		                    currentTime = Long.parseLong(parts[3]);
        		                    bd.addMensagem(msg);
        		                    break;
        		                
        		                case 4:
        		                	msg = new MensagemParar(numLine, tipo, false);
        		                    //currentTime = Long.parseLong(parts[2]);
        		                    bd.addMensagem(msg);
        		                    break;
        		                    
        		                case 5:
        		                	msg = new MensagemVazia(numLine, 5);
        		                	bd.addMensagem(msg);
        		                    break;
        		                // Handle other cases if needed
        		            }
        		            
        		            
        		            if(tipo != 4)
        		            	gui.write("Li: " + msg + "\n");
        		            System.out.println("escreve");
                			System.out.println("Mensagens à espera: " + bd.getMensagens().size());
                			msg = bd.getMensagens().get(0);
                			setMensagem(msg);
                			try {
                				
                				ocupadaMyMensagem.acquire();
                				acessoMyMensagem.acquire();
                				
                				long sleepTime = currentTime - lastTime;
                				//System.out.println(sleepTime);
                			    // Check if the sleep time is non-negative
                			    if (lastTime!= 0) {
                			        // Introduce a delay based on the time difference
                			    	System.out.println("sleep");
                			        Thread.sleep(sleepTime);
                			        System.out.println("wake");
                			        if (!bd.getReproduzir())
                			        	break;
                			    }
                			    if(gravarAvailable.availablePermits() == 0)
            		        		bloqueado();
                			    
                			} catch (InterruptedException e) {}
                				bufferCircular.inserirElemento(myMensagem);
                				acessoMyMensagem.release();
                				livreMyMensagem.release();
                				haTrabalho.release();
                				if(tipo != 4)
                					gui.txtLog.append("Enviei = " + msg + "\n");
                				
                				bd.removeMensagem();
                				lastTime = currentTime;
                				numLine++;
        		            
        		        }
        		    } catch (IOException | NumberFormatException e) {
        		        // Handle exceptions
        		        System.out.println("Error reading or parsing the file: " + e.getMessage());
        		        e.printStackTrace();
        		    }
        		    state = dormir;
        		    bd.setReproduzir(false);
        		    gui.tglReproduzir.setSelected(false);
        		    break;

        			
        		case bloqueado:
        			try {
        				gui.write("Estou bloqueado \n");
        				gravarAvailable.acquire();
        				gui.write("Desbloqueado \n");
        				state = dormir;
        				break;
        			} catch (InterruptedException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}
				
			}
		}
	
    }
}
