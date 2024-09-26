package ptrabalho;
import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class CanalComunicacao {
    FileLock fl;
    
    // ficheiro
    private File ficheiro;
    
    // canal que liga o conteúdo do ficheiro ao Buffer
    protected FileChannel canal;
    
    // buffer
    private MappedByteBuffer buffer;

    // dimensão máxima em bytes do buffer = (Nº grup + 1) * 4 bytes (int) * 4 ints
    //final int nGrupo = 15;
    //final int BUFFER_MAX = (nGrupo + 1) * 4 * 4;
    protected int nMensagens = 8;
    protected int BUFFER_MAX = nMensagens*16;
    protected int putBuffer, getBuffer;
   


    //Construtor onde se cria o canal
    public CanalComunicacao(int n) {
        ficheiro = null;
        canal    = null;
        buffer   = null;
        nMensagens = n;
        BUFFER_MAX = nMensagens*16;
    }

    public boolean abrirCanalEscritor(String Filename) {
        // cria um ficheiro
        ficheiro = new File(Filename);

        //cria um canal de comunicação de leitura e escrita
        try {
            canal = new RandomAccessFile(ficheiro, "rw").getChannel();
        } catch (FileNotFoundException e) {
            return false;
        }

        // mapeia para memória o conteúdo do ficheiro
        try {
            buffer = canal.map(FileChannel.MapMode.READ_WRITE, 0, BUFFER_MAX);

            //Inicializar apontadores - put e get buffer a zero
            getBuffer = 0;
            putBuffer = 0;

            //Inicializar buffer - dizer que todos os blocos são vazios
            inicializarBuffer();


        } catch (IOException e) {
            return false;
        }

        return true;
    }
    
    public boolean abrirCanalLeitor(String Filename) {
        // cria um ficheiro
        ficheiro = new File(Filename);

        //cria um canal de comunicação de leitura e escrita
        try {
            canal = new RandomAccessFile(ficheiro, "rw").getChannel();
        } catch (FileNotFoundException e) {
            return false;
        }

        // mapeia para memória o conteúdo do ficheiro
        try {
            buffer = canal.map(FileChannel.MapMode.READ_WRITE, 0, BUFFER_MAX);

            //Inicializar apontadores - put e get buffer a zero
            getBuffer = 0;
            putBuffer = 0;

            


        } catch (IOException e) {
            return false;
        }

        return true;
    }

    // recebe e converte numa Mensagem, lê e retorna mensagem
    //true escritor// false leitor
    Mensagem receberMensagem(boolean e_l) {

        int id, tipo, distancia, raio, angulo;
        boolean sincrono;

        buffer.asIntBuffer();
        //buffer.position(0);
        //System.out.println("getbuffer" + getBuffer + "nMensanges" + nMensagens);
        buffer.position(getBuffer * 16);

        id   = buffer.getInt();
        tipo = buffer.getInt();

        Mensagem msg = null;

        switch (tipo) {
            case iMensagem.parar:
                sincrono = buffer.getInt() == 1;
                msg = new MensagemParar(id, tipo, sincrono);
                break;

            case iMensagem.reta:
                distancia = buffer.getInt();
                msg = new MensagemReta(id, tipo, distancia);
                break;

            case iMensagem.curvarDir:
                raio = buffer.getInt();
                angulo = buffer.getInt();
                msg = new MensagemCurvar(id, tipo, raio, angulo);
                break;

            case iMensagem.curvarEsq:
                raio = buffer.getInt();
                angulo = buffer.getInt();
                msg = new MensagemCurvar(id, tipo, raio, angulo);
                break;

            case iMensagem.vazia:
                msg = new Mensagem(id, tipo, 0, 0);
                break;
        }

        if(e_l) {
        	if(tipo==4)
        		getBuffer =++ getBuffer % nMensagens;
        }
        else {
        	if (tipo != 4)
            	getBuffer =++ getBuffer % nMensagens;
        }

        return msg;

    }
    
 // recebe e converte numa Mensagem, lê e retorna mensagem
    Mensagem receberMensagemLeitor() {

        int id, tipo, distancia, raio, angulo;
        boolean sincrono;

        buffer.asIntBuffer();
        //buffer.position(0);
        //System.out.println("getbuffer" + getBuffer + "nMensanges" + nMensagens);
        buffer.position(getBuffer * 16);

        id   = buffer.getInt();
        tipo = buffer.getInt();

        Mensagem msg = null;

        switch (tipo) {
            case iMensagem.parar:
                sincrono = buffer.getInt() == 1;
                msg = new MensagemParar(id, tipo, sincrono);
                break;

            case iMensagem.reta:
                distancia = buffer.getInt();
                msg = new MensagemReta(id, tipo, distancia);
                break;

            case iMensagem.curvarDir:
                raio = buffer.getInt();
                angulo = buffer.getInt();
                msg = new MensagemCurvar(id, tipo, raio, angulo);
                break;

            case iMensagem.curvarEsq:
                raio = buffer.getInt();
                angulo = buffer.getInt();
                msg = new MensagemCurvar(id, tipo, raio, angulo);
                break;

            case iMensagem.vazia:
                msg = new Mensagem(id, tipo, 0, 0);
                break;
        }

        if (tipo != 4)
        	getBuffer =++ getBuffer % nMensagens;

        return msg;

    }

    // envia uma Mensagem como um conjunto de ints
    void enviarMensagem(Mensagem msg, boolean e_c) {

        try {
            //buffer.position(0);
            buffer.position(putBuffer * 16);

            // Obter ID e escrevê-lo no buffer
            int id = msg.getId();
            buffer.putInt(id);

            // Obter Tipo e escrevê-lo no buffer
            int tipo = msg.getTipo();
            buffer.putInt(tipo);

            // Escrever o conteúdo no buffer, consoante o tipo da mensagem (Ex: para o tipo reta -> escrever distância
            switch (tipo) {
                case iMensagem.parar:
                    buffer.putInt(((MensagemParar) msg).isSincrono() ? 1 : 0);
                    break;

                case iMensagem.reta:
                    buffer.putInt(msg.getArg1());
                    break;

                case iMensagem.curvarDir:
                	System.out.println("yo");
                    buffer.putInt(msg.getArg1());
                    buffer.putInt(msg.getArg2());
                    break;

                case iMensagem.curvarEsq:
                    buffer.putInt(msg.getArg1());
                    buffer.putInt(msg.getArg2());
                    break;

                case iMensagem.vazia:
                    buffer.putInt(0);
                    System.out.println("limpei");
                    //id = -1;
                    break;
            }

            if (e_c) {
            	if (tipo != 4)
                	putBuffer =++ putBuffer % nMensagens;
            }
            else
            {
            	putBuffer =++ putBuffer % nMensagens;
                msg.setId(id + 1);
            }
            
            //msg.setId(id + 1);

        }catch(Exception e) {
            e.printStackTrace();
        }

    }
    
    void enviarMensagemClean(Mensagem msg) {

        try {
            //buffer.position(0);
            buffer.position(putBuffer * 16);

            // Obter ID e escrevê-lo no buffer
            int id = msg.getId();
            buffer.putInt(id);

            // Obter Tipo e escrevê-lo no buffer
            int tipo = msg.getTipo();
            buffer.putInt(tipo);

            // Escrever o conteúdo no buffer, consoante o tipo da mensagem (Ex: para o tipo reta -> escrever distância
            switch (tipo) {
                case iMensagem.parar:
                    buffer.putInt(((MensagemParar) msg).isSincrono() ? 1 : 0);
                    break;

                case iMensagem.reta:
                    buffer.putInt(msg.getArg1());
                    break;

                case iMensagem.curvarDir:
                	System.out.println("yo");
                    buffer.putInt(msg.getArg1());
                    buffer.putInt(msg.getArg2());
                    break;

                case iMensagem.curvarEsq:
                    buffer.putInt(msg.getArg1());
                    buffer.putInt(msg.getArg2());
                    break;

                case iMensagem.vazia:
                    buffer.putInt(0);
                    System.out.println("limpei");
                    //id = -1;
                    break;
            }

            putBuffer =++ putBuffer % nMensagens;
            msg.setId(id + 1);

        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    // fecha o canal entre o buffer e o ficheiro
    void fecharCanal() {
        if (canal != null)
            try {
                canal.close();
            } catch (IOException e) {
                canal = null;
            }
    }

    /**Colocar todos os slots com mensagens vazias*/
    //Mts duvidas, gestao do ID, so tipo e id
    void inicializarBuffer() {

        //1 msg - 4 * 4 = 16 bytes
        MensagemVazia msg = new MensagemVazia(0, iMensagem.vazia);
        System.out.print(BUFFER_MAX);
        
        for(int i = 0; i < nMensagens; i++) {
            enviarMensagem(msg, false);
        }
        buffer.clear();
        putBuffer=0;
        getBuffer=0;

    }

    void limparLida(){
    	System.out.println(getBuffer);
    	if(getBuffer != 0)
    		buffer.position((getBuffer-1) * 16);
    	else
    		buffer.position((7) * 16);
        buffer.putInt(getBuffer);
        buffer.putInt(iMensagem.vazia);

    }


    /*public static void main(String[] args) {
		
    	
    	MensagemParar msgParar = new MensagemParar(0, 0, false);   //int id, int tipo, boolean sincrono
        MensagemReta msg 	   = new MensagemReta(1,1,20);         //int id, int tipo, int dist
        MensagemCurvar msgCD   = new MensagemCurvar(2, 2, 15, 15); //int id, int tipo, int raio, int ang
        MensagemCurvar msgCE   = new MensagemCurvar(3, 3, 12, 30);
        MensagemVazia msgV = new MensagemVazia(4,4);
        
		
        CanalComunicacao cc = new CanalComunicacao(8);
        cc.abrirCanalEscritor("comunicacao.dat");
        
        cc.enviarMensagem(msg);
        Mensagem msg1 =cc.receberMensagemLeitor();
        System.out.println(msg1);
        cc.enviarMensagem(msgV);
        System.out.println(cc.receberMensagemLeitor());
        cc.enviarMensagem(msgCD);
        System.out.println(cc.receberMensagemLeitor());
        cc.enviarMensagem(msgCE);
        System.out.println(cc.receberMensagemLeitor());
        
        cc.fecharCanal();
        }*/












}