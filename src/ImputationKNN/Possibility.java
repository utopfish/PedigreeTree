package ImputationKNN;

import java.util.Arrays;

import view.MainFrame;

public class Possibility {
	private int[] anis;  
	private int ValueClass;
	private double[] chance;
	private MainFrame mf;
	
	public void setMainFrame(MainFrame mf) {
		this.mf = mf;
	}
	
	public int[] getAnis() {
		return anis;
	}
	public void setAnis(int[] anis) {
		this.anis = anis;
	}
	public int getValueClass() {
		return ValueClass;
	}
	public void setValueClass(int valueClass) {
		ValueClass = valueClass;
	}
	public double[] getChance() {
		return chance;
	}
	public void setChance(double[] chance) {
		this.chance = chance;
	}
    public String toString() {
    	System.out.println("缺失数据位置:"+Arrays.toString(anis));
    	if (ValueClass == 6) {
    		System.out.println("估计值: N ");
		}
    	else {
        	System.out.println("估计值:"+ValueClass);
		}
    	System.out.println("插补概率:");
    	for(int i=0;i<chance.length;i++) {
    		if (chance[i] != 0) {
    			if (i == 6) {
    				System.out.print(" N:"+chance[i]+"   ");
				}
    			else {
            		System.out.print(i+":"+chance[i]+"   ");	
				}
			}
    	}
    	System.out.println(" ");
		return null;
    }
}
