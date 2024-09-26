package ptrabalho;

public class CanalComunicacaoConsistente extends CanalComunicacao{


    public CanalComunicacaoConsistente(int n) {
		super(n);
		// TODO Auto-generated constructor stub
	}

	public boolean GetandSetWrite(Mensagem msg) {
        Mensagem mensagem = GetandSetRead();
        //System.out.println(mensagem);
        try {
        	if(mensagem == null)
        		return false;
            if(mensagem.tipo == iMensagem.vazia) {
                System.out.println("entrou, no buffer: " + mensagem + "enviei msg = " + msg);

                fl = canal.lock();

                enviarMensagem(msg, true);

                fl.release();
                return true;
            }else {
            	//System.out.println("NÃO entrou, no buffer: " + mensagem + "quero enviar msg = " + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public Mensagem GetandSetRead(){

        try {
            fl = canal.lock();

            Mensagem mensagem = receberMensagem(true);
                        
            //limparLida();

            fl.release();

            return mensagem;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    public Mensagem GetandSetReadLeitor(){

        try {
            fl = canal.lock();

            Mensagem mensagem = receberMensagem(false);
            System.out.println(mensagem);
                        
            limparLida();

            fl.release();

            return mensagem;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }



    /*public static void main (String[] args) {
        CanalComunicacaoConsistente cc = new CanalComunicacaoConsistente();

        MensagemParar msgParar = new MensagemParar(0, 0, false);   //int id, int tipo, boolean sincrono
        MensagemReta msg 	   = new MensagemReta(1,1,20);         //int id, int tipo, int dist
        MensagemCurvar msgCD   = new MensagemCurvar(2, 2, 15, 15); //int id, int tipo, int raio, int ang
        MensagemCurvar msgCE   = new MensagemCurvar(3, 3, 12, 30);

        //Testar
        cc.abrirCanal("comunicacao.dat");


        cc.GetandSetWrite(msg);
        System.out.println(cc.receberMensagem());
        cc.GetandSetWrite(msgCD);
        cc.GetandSetWrite(msgCD);
        cc.GetandSetWrite(msgCE);
        cc.GetandSetWrite(msgCD);
        cc.GetandSetWrite(msgCD);
        cc.GetandSetWrite(msgParar);
        cc.GetandSetWrite(msgParar);
        cc.GetandSetWrite(msgCD);
        cc.GetandSetWrite(msgCD);
        cc.GetandSetWrite(msgCD);
        cc.GetandSetWrite(msgCD);
        cc.GetandSetWrite(msgParar);
        cc.GetandSetWrite(msgParar);
        cc.GetandSetWrite(msgCD);
        cc.GetandSetWrite(msgCD);
        cc.GetandSetWrite(msgCD);
        cc.GetandSetWrite(msgParar);
        cc.GetandSetWrite(msgParar);

        cc.fecharCanal();
    }*/

}