// Imports \\


public class MazeTile
{
	
	
	//      Attributes      \\
	
	// Instance attributes \\
	// Variables
	private boolean [][] openings;
	
	
	
	// Constructors \\
	public MazeTile()
	{
		this.openings = new boolean[2][2];
		
		for (int y = 0; y < this.openings.length; y++)
			for (int x = 0; x < this.openings.length; x++)
				this.openings[y][x] = false;
	}
	
	
	
	// Accessors \\
	public boolean isPassed() { return this.openings[1][1]; }
	
	
	
	// Modifier \\
	public void open(int x, int y)
	{
		try
		{
			this.openings[y][x] = true;
		} catch (Exception e) {}
	}
	
	// Standard methods \\
	@Override
	public String toString()
	{
		String sRet = "";
		
		
		for (int y = 0; y < openings.length; y++)
		{
			for (int x = 0; x < openings.length; x++)
			{
				if      ( x == y   ) sRet += openings[y][x] ? " " : "+";
				else if ( x == 2-y ) sRet += openings[y][x] ? " " : "#";
				else if ( y != 1   ) sRet += openings[y][x] ? " " : "-";
				else if ( x != 1   ) sRet += openings[y][x] ? " " : "|";
				else                 sRet += ".";
				
				if ( x == 1 && y != 1 ) sRet += "\n";
			}
		}
		
		return sRet;
	}
}
