package StructureAnalyze;
import java.lang.String;

public class Algebraic {
	
	private String algebraic;  
	private String DCSTreealgebraic;
	private int level;
	private int line;
    
	public Algebraic()
	{
		
	}
	
    public void setAlgebraic(String algebraic)
    {
    	this.algebraic = algebraic;
    }
    
    public void setDCSTreealgebraic(String DCSTreealgebraic)
    {
    	this.DCSTreealgebraic = DCSTreealgebraic;
    }
    	
    public void setLevel(int level)
    {
    	this.level = level;
    }
    
    public void setLine(int line)
    {
    	this.line = line;
    }
    
    public String getDCSTreealgebraic()
    {
    	return this.DCSTreealgebraic;
    }
    
    public String getAlgebraic()
    {
    	return this.algebraic;
    }
    
    public int getLevel()
    {
    	return this.level;
    }
    
    public int getLine()
    {
    	return this.line;
    }
    
}
