package ptrabalho;

public class MensagemReta extends Mensagem{
	int dist; 
	
	public MensagemReta(int id, int tipo, int dist) {
		super(id, tipo, dist, 0);
		this.dist = dist;
	}
	
	public MensagemReta(int id, int tipo, int dist, int arg2) {
		super(id, tipo, dist, 0);
		this.dist = dist;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	@Override
	public String toString() {
		return super.toString() + " distancia= " + dist + "}";
	}
}