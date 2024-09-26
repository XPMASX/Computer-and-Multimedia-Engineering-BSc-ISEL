package ptrabalho;
import java.lang.Math;

public class App_Subdito
{
    @SuppressWarnings("unused")
    private GUI_Subdito gui;
    private BD_Subdito bd;
    Mensagem msg = null;
    private int state = 2;
    private int counter = 0;
    private final int receberMensagem = 1;
    private final int dormir = 2;
    private final int esperarTempoExecucao = 3;
    private final int RETA = 1;
    private final int CURVARDIR = 2;
    private final int CURVARESQ = 3;

    public App_Subdito(String[] args) 
    {
    	bd = new BD_Subdito();
    	
    	if (args.length == 2)
        {
    		int nMensagens = Integer.parseInt(args[0]);
        	if (nMensagens >= 8 && nMensagens <= 12)
        		bd.setNMensagens(nMensagens);
        	bd.setFile(args[1]);
        }
        gui = new GUI_Subdito(bd);
    }

    public BD_Subdito getBD() 
    {
        return bd;
    }

    public void run() throws InterruptedException 
    {
        	while(!bd.getTerminar()) {
            	
            	switch (state) {
    			
            	case receberMensagem:
            		System.out.println("recebe");
            		gui.txtLog.append(" Recebi = " + msg + "\n");
            		bd.addMensagem(msg);
            		state = dormir;
            		break;
            			
    			case dormir:
    				System.out.println("busca");
    				
    				if(bd.getCanal() != null){
    					msg = bd.getCanal().GetandSetReadLeitor(); 
    					if(msg.getTipo() != 4) {
    						System.out.println("existe msg");
    						
    						state = receberMensagem;
    						break;
    					} 
    					else {
    						if(bd.getMensagens().size()!=0 && bd.isLigado()) {
    	        				state = esperarTempoExecucao;
    	        				break;
    	        			} 
    						System.out.println("sleep");
    						Thread.sleep(1000);
    						break;}
    					}else {
    						Thread.sleep(1000);
    						System.out.println("sleep");
    						break;}
    				
    			case esperarTempoExecucao:
    				msg = bd.getMensagens().get(0);
    				System.out.println("esperaExec id:" + msg.getId());
    				int tipo = msg.getTipo();
    				System.out.println("Tipo:" + tipo);
    				switch (tipo) {
    					case RETA:
    						gui.txtLog.append(" O robot avançou " + msg.getArg1() + "\n");
    						bd.getRobot().Reta(msg.getArg1());
    						bd.getRobot().Parar(false);
    						Thread.sleep((long) ((Math.abs(msg.getArg1())) / 0.03));
    						break;
    					case CURVARDIR:
    						gui.txtLog.append(" O robot virou direita com raio = " + msg.getArg1() +" e angulo = " + msg.getArg2() + "\n");
    						bd.getRobot().CurvarDireita(msg.getArg1(),msg.getArg2());
    						bd.getRobot().Parar(false);
    						Thread.sleep((long) ((msg.getArg1() * (msg.getArg2() * 0.017)) / 0.03));
    						break;
    					case CURVARESQ:
    						gui.txtLog.append(" O robot virou esquerda com raio = " + msg.getArg1() +" e angulo = " + msg.getArg2() + "\n");
        					bd.getRobot().CurvarEsquerda(msg.getArg1(),msg.getArg2());
        					bd.getRobot().Parar(false);
    						Thread.sleep((long) ((msg.getArg1() * (msg.getArg2() * 0.017)) / 0.03));
    						break;
    				}
    				bd.removeMensagem();
    				state = dormir;
    				break;
    			}
    		}
    		System.out.println("sai");
            Thread.sleep(100);
    }

    public static void main(String[] args) throws InterruptedException 
    {
        App_Subdito app = new App_Subdito(args);
        System.out.println("A aplicação começou.");
        app.run();
        System.out.println("A aplicação terminou.");
    }

}