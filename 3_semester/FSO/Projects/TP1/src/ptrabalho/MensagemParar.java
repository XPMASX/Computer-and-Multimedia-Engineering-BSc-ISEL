package ptrabalho;

public class MensagemParar extends Mensagem{

	boolean sincrono;
	
	public MensagemParar(int id, int tipo, boolean sincrono) {
		super(id, tipo, 0, 0);
		this.sincrono = sincrono;
	}

	@Override
	public String toString() {
		return super.toString() +" " +
				"sincrono=" + sincrono +
				'}';
	}

	public boolean isSincrono() {
		return sincrono;
	}

	public void setSincrono(boolean sincrono) {
		this.sincrono = sincrono;
	}

}