package ptrabalho;

public class RobotEV3 {
	
	protected Gravador gravador;
	
	public RobotEV3(Gravador g) {
		gravador = g;
	}
	
	
	synchronized void reta(Mensagem msg) {
		//System.out.println(gravador.getGravar());
		if (gravador!=null && gravador.getGravar() && gravador.bdGravar.getGravarOff())
			gravador.reta(msg);
		//System.currentTimeMillis();
	}
	
	synchronized void curvarEsquerda(Mensagem msg) {
		//System.out.println(gravador.getGravar());
		if (gravador!=null && gravador.getGravar() && gravador.bdGravar.getGravarOff())
			gravador.curvarEsquerda(msg);
		//System.currentTimeMillis();
	}
	
	synchronized void curvarDireita(Mensagem msg) {
		//System.out.println(gravador.getGravar());
		if (gravador!=null && gravador.getGravar() && gravador.bdGravar.getGravarOff())
			gravador.curvarDireita(msg);
		//System.currentTimeMillis();
	}
	
	synchronized void parar(Mensagem msg) {
		//System.out.println(gravador.getGravar());
		if (gravador!=null && gravador.getGravar() && gravador.bdGravar.getGravarOff())
			gravador.parar(msg);
		//System.currentTimeMillis();
	}
	
}
