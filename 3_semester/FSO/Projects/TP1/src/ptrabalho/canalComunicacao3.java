package ptrabalho;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class canalComunicacao3 {

	// ficheiro
	 private File ficheiro;
	// canal que liga o conteúdo do ficheiro ao Buffer
	private FileChannel canal;
	// buffer
	private MappedByteBuffer buffer;
	// dimensão máxima em bytes do buffer
	
	protected final int DIM_STRING = 20;
	protected final int NUM_STRINGS = 1;
	
	final int BUFFER_MAX= DIM_STRING * NUM_STRINGS;
	protected int index;

	public canalComunicacao3(){
		 ficheiro=null;
		 canal= null;
		 buffer= null;
		 index = 0;
		}
	
	public boolean abrirCanal() {
		// cria um ficheiro com o nome comunicacao.dat
		ficheiro = new File("comunicacao2.dat");
		
		//cria um canal de comunicação de leitura e escrita
		try {
			canal = new RandomAccessFile(ficheiro, "rw").getChannel();
		} catch (FileNotFoundException e) { return false;}
		
		// mapeia para memória o conteúdo do ficheiro
		try {
			 buffer = canal.map(FileChannel.MapMode.READ_WRITE, 0, BUFFER_MAX);
		} catch (IOException e) { return false;}
		return true;
	}
	
		// recebe uma mensagem convertendo-a numa String
	String receberMensagem() {
		String msg=new String();
		char c;
		buffer.position(index * DIM_STRING);
		while ((c= buffer.getChar()) != '\0')
			msg += c;
	
		index = (index + 1)%NUM_STRINGS;
		return msg;
	}
		
		// envia uma String como mensagem
	void enviarMensagem(String msg) {
		char c;
		buffer.position(index);
		for (int i= 0 ; i< msg.length() ; ++i){
			c= msg.charAt(i);
			buffer.putChar(c);
		 	}
		 	buffer.putChar('\0');
		}
	
		// fecha o canal entre o buffer e o ficheiro
	void fecharCanal() {
		if (canal!=null)
		try {
			canal.close();
		} catch (IOException e) { canal= null; }
		}
	
	public static void main(String[] args) {
		/*String tx = "Ola";
		String rx = "";
		canalComunicacao cc = new canalComunicacao();
		
		cc.abrirCanal();
		cc.enviarMensagem(tx);
		rx = cc.receberMensagem();
		cc.fecharCanal();
		
		System.out.println("Recebido: " + rx);*/
		
		String tx1 = "Ola";
		String rx1 = "";
		String tx2 = "ZE";
		String rx2 = "";
		String tx3 = "MAnel";
		String rx3 = "";
		canalComunicacao3 cc = new canalComunicacao3();
		
		cc.abrirCanal();
		
		cc.enviarMensagem(tx1);
		rx1 = cc.receberMensagem();
		
	
		cc.enviarMensagem(tx2);
		rx2 = cc.receberMensagem();
		
		
		cc.enviarMensagem(tx3);
		rx3 = cc.receberMensagem();
		
		
		cc.fecharCanal();
		
		System.out.println("Recebido: " + rx1 + " " + rx2 + " " + rx3);
		
	}


}
