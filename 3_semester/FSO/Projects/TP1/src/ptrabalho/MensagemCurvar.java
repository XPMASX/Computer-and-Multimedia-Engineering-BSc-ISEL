package ptrabalho;

public class MensagemCurvar extends Mensagem{
	int raio, ang;
	
	public MensagemCurvar(int id, int tipo, int raio, int ang) {
		super(id, tipo, raio, ang);
		this.raio = raio;
		this.ang  = ang;
	}

	public int getRaio() {
		return raio;
	}

	public void setRaio(int raio) {
		this.raio = raio;
	}

	public int getAng() {
		return ang;
	}

	public void setAng(int ang) {
		this.ang = ang;
	}
}