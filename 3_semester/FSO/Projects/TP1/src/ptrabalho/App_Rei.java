package ptrabalho;

public class App_Rei
{
    @SuppressWarnings("unused")
    private GUI_Rei gui;
    private BD_Rei bd;
    Mensagem msg = null;
    private int state = 2;
    private int counter = 0;
    private final int escreverMensagem = 1;
    private final int dormir = 2;
    private final int esperarTempoExecucao = 3;

    public App_Rei(String[] args) 
    {
        bd = new BD_Rei();
        
        if (args.length == 2 )
        {
        	int nMensagens = Integer.parseInt(args[0]);
        	if (nMensagens >= 8 && nMensagens <= 12)
        		bd.setNMensagens(nMensagens);
        	bd.setFile(args[1]);
        }
        gui = new GUI_Rei(bd);
    }

    public BD_Rei getBD() 
    {
        return bd;
    }

    public void run() throws InterruptedException 
    {
        while(!bd.getTerminar()) {
        	
        	switch (state) {
			
        		case escreverMensagem:
        			System.out.println("escreve");
        			System.out.println("Mensagens à espera: " + bd.getMensagens().size());
        			msg = bd.getMensagens().get(0);
        			msg.setId(counter);
        			if (bd.getCanal().GetandSetWrite(msg)) {
        				gui.txtLog.append(" Enviei = " + msg + "\n");
        				counter =++ counter % bd.getNMensagens();
        				bd.removeMensagem();
        				state = dormir;
        				break;
        			} 
        			else {
        				state = dormir;
        				break;
						}
        			
        		case dormir:
        			System.out.println("sleep");
        			Thread.sleep(1000);
        			if(bd.getMensagens().size()!=0 && bd.getCanal() != null) {
        				state = escreverMensagem;
        				break;
        			} 
        			else {break;}
				
			}
		}
		System.out.println("sai");
        Thread.sleep(100);
        
    }

    public static void main(String[] args) throws InterruptedException 
    {
    	
        App_Rei app = new App_Rei(args);
        System.out.println("A aplicação começou.");
        app.run();
        System.out.println("A aplicação terminou.");
    }

}