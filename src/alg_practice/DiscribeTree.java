package alg_practice;
import view.MainFrame;

public class DiscribeTree implements Cloneable {
	public String  Topology;
	public double CI;
	public double HI;
	public double RI;
	public double RC;
	public int Length;
	
	private MainFrame mf;
	public void setMainFrame(MainFrame mf) {
		this.mf = mf;	
	}
	
	public DiscribeTree() {	
	}
	
	public DiscribeTree(String topology , double ci , double hi , double ri , double rc , int length) {
		this.setTopology(topology);
		this.setCI(ci);
		this.setHI(hi);
		this.setRI(ri);
		this.setRC(rc);
		this.setLength(length);
	}
	public String toString() {
		return Topology + Length + CI + HI + RI + RC;
	}
	
	public String isStringEqual() {
		return Topology + Length + CI + HI + RI + RC;
	}
	
	public DiscribeTree Clone(DiscribeTree tree) {
		DiscribeTree newtree = new DiscribeTree();
		String  Topology = tree.getTopology();
		double CI = tree.getCI();
		double HI = tree.getHI();
		double RI = tree.getRI();
		double RC = tree.getRC();
		int Length = tree.getLength();
		newtree.setTopology(Topology);
		newtree.setCI(CI);
		newtree.setHI(HI);
		newtree.setRI(RI);
		newtree.setRC(RC);
		newtree.setLength(Length);
		return newtree;
	}
	
	@Override  
    protected Object clone() throws CloneNotSupportedException {  
        return super.clone();  
    } 	
	public String getTopology() {
		return Topology;
	}
	public void setTopology(String topology) {
		Topology = topology;
	}
	public double getCI() {
		return CI;
	}
	public void setCI(double cI) {
		CI = cI;
	}
	public double getHI() {
		return HI;
	}
	public void setHI(double hI) {
		HI = hI;
	}
	public double getRI() {
		return RI;
	}
	public void setRI(double rI) {
		RI = rI;
	}
	public double getRC() {
		return RC;
	}
	public void setRC(double rC) {
		RC = rC;
	}
	public int getLength() {
		return Length;
	}
	public void setLength(int length) {
		Length = length;
	}
}
