package ptrabalho;

import robot.RobotLegoEV3;

public class myRobotLegoEV3 extends RobotEV3{
	
	private RobotLegoEV3 robot;
	
	public myRobotLegoEV3(Gravador g) {
		super(g);
		robot = new RobotLegoEV3();
	}


	public RobotLegoEV3 getRobot() {
		return robot;
	}
	
	
	synchronized void reta(Mensagem msg)
	{
		super.reta(msg);
		robot.Reta(msg.getArg1());
	}
	
	synchronized void curvarDireita(Mensagem msg)
	{
		super.curvarDireita(msg);
		robot.CurvarDireita(msg.getArg1(),msg.getArg2());
	}
	
	synchronized void curvarEsquerda(Mensagem msg)
	{
		super.curvarEsquerda(msg);
		robot.CurvarEsquerda(msg.getArg1(),msg.getArg2());
		
	}
	
	synchronized void parar(Mensagem msg)
	{
		super.parar(msg);
		robot.Parar(false);
	}
}
