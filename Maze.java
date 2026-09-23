// Imports \\
import java.util.ArrayList;;

public class Maze
{
	//      Attributes      \\
	
	// Class attributes \\
	// Variables
	public static int nbInstances;

	// Instance attributes \\
	// Constants
	private final int HEIGHT;
	private final int WIDTH;
	
	// Variables
	// Generation
	private MazeTile[][] mazeTiles;
	private ArrayList<int[]> path;
	
	private int cursorX;
	private int cursorY;
	
	// Usage
	private int startX;
	private int startY;
	
	private int endX;
	private int endY;
	
	private ArrayList<int[]> discoveredTiles;
	
	
	// Constructors \\
	public Maze(int height, int width)
	{
		Maze.nbInstances++;
		
		this.HEIGHT = height;
		this.WIDTH  = width;
		
		this.startX = 0;
		this.startY = 0;
		
		this.endX = this.WIDTH -1;
		this.endY = this.HEIGHT-1;
		
		this.discoveredTiles = new ArrayList<>();
		
		this.mazeTiles = new MazeTile[this.HEIGHT+1][this.WIDTH+1];
		
		for (int y = 0; y < this.mazeTiles.length; y++)
		{
			for (int x = 0; x < this.mazeTiles[0].length; x++)
			{
				this.mazeTiles[y][x] = new MazeTile();
				
				if ( x == this.WIDTH || y == this.HEIGHT )
					this.mazeTiles[y][x].open(1, 1);
				
				if ( x == this.WIDTH )
					this.mazeTiles[y][x].open(1, 0);
				
				if ( y == this.HEIGHT )
					this.mazeTiles[y][x].open(0, 1);
			}
		}
		
		this.generateMaze();
		
		
	}

	public Maze()
	{
		this(10, 10);
	}
	
	public Maze(int height, int width, int startX, int startY, int endX, int endY)
	{
		this(height, width);
		
		this.startX = startX;
		this.startY = startY;
		
		if ( this.startX >= this.WIDTH ) this.startX = this.WIDTH-1;
		if ( this.startX <  0            ) this.startX = 0;
		if ( this.startY >= this.HEIGHT ) this.startY = this.HEIGHT-1;
		if ( this.startY <  0            ) this.startY = 0;
		
		
		this.endX = endX;
		this.endY = endY;
		
		if ( this.endX >= this.WIDTH ) this.endX = this.WIDTH-1;
		if ( this.endX <  0            ) this.endX = 0;
		if ( this.endY >= this.HEIGHT ) this.endY = this.HEIGHT-1;
		if ( this.endY <  0            ) this.endY = 0;
	}
	
	
	// Accessors \\
	//public int getVar() { return var; }
	
	
	
	
	// Other methods \\
	
	private void generateMaze()
	{
		this.cursorX = 0;
		this.cursorY = 0;
		this.path = new ArrayList<>();
		
		
		this.mazeTiles[this.cursorY][this.cursorX].open(1, 1);
		
		int[][] dirs;
		int choice;
		
		do
		{
			dirs = this.getMoveOptions(this.cursorX, this.cursorY);
			
			if ( dirs.length != 0 )
			{
				choice = (int) (Math.random() * dirs.length);
				
				if ( dirs[choice][0] < 0 || dirs[choice][1] < 0 )
					this.mazeTiles[this.cursorY][this.cursorX].open(1 + dirs[choice][0], 1 + dirs[choice][1]);
				
				this.cursorX += dirs[choice][0];
				this.cursorY += dirs[choice][1];
				
				this.path.addLast(dirs[choice]);
				
				if ( dirs[choice][0] > 0 || dirs[choice][1] > 0 )
					this.mazeTiles[this.cursorY][this.cursorX].open(1 - dirs[choice][0], 1 - dirs[choice][1]);
				
				this.mazeTiles[this.cursorY][this.cursorX].open(1, 1);
			}
			else
			{
				this.cursorX -= this.path.getLast()[0];
				this.cursorY -= this.path.getLast()[1];
				
				this.path.removeLast();
			}
		}
		while (this.path.size() > 0);
		
	}
	
	
	private int[][] getMoveOptions(int x, int y)
	{
		int[][] res = {};
		int nbChoise = 4;
		
		if ( x == 0 ) nbChoise--;
		if ( y == 0 ) nbChoise--;
		
		// check the 4 possibilities
		
		if ( x != 0 && this.mazeTiles[y  ][x-1].isPassed() ) nbChoise--;
		if ( y != 0 && this.mazeTiles[y-1][x  ].isPassed() ) nbChoise--;
		
		if (           this.mazeTiles[y  ][x+1].isPassed() ) nbChoise--;
		if (           this.mazeTiles[y+1][x  ].isPassed() ) nbChoise--;
		
		
		// add the possible directions by doing the same thing as above
		res = new int[nbChoise][2];
		
		int ind = 0;
		
		if ( x != 0 && !this.mazeTiles[y  ][x-1].isPassed() ) { res[ind] = new int[]{-1,  0}; ind++; }
		if ( y != 0 && !this.mazeTiles[y-1][x  ].isPassed() ) { res[ind] = new int[]{ 0, -1}; ind++; }
		
		if (           !this.mazeTiles[y  ][x+1].isPassed() ) { res[ind] = new int[]{ 1,  0}; ind++; }
		if (           !this.mazeTiles[y+1][x  ].isPassed() ) { res[ind] = new int[]{ 0,  1}; ind++; }
		
		return res;
	}
	
	public void discoverTile(int x, int y)
	{
		this.discoveredTiles.addLast(new int[]{x, y});
	}
	
	// Standard methods \\
	@Override
	public String toString()
	{
		String sRet = "";
		String l1 = "";
		String l2 = "";
		
		
		for (int y = 0; y < this.HEIGHT + 1; y++)
		{
			l1 = "";
			l2 = "";
			
			for (int x = 0; x < this.WIDTH + 1; x++)
			{
				String[] tileStr  = this.mazeTiles[y][x]
					.toString()
					.split("\n");
				
				String   lineAdd1 = tileStr[0];
				String   lineAdd2 = tileStr[1];
				
				
				if ( this.cursorX == x && this.cursorY == y)
					lineAdd2 = "" + lineAdd2.charAt(0) + '@' ;
				
				if ( this.startX == x && this.startY == y)
					lineAdd2 = "" + lineAdd2.charAt(0) + 'S' ;
				
				if ( this.endX == x && this.endY == y)
					lineAdd2 = "" + lineAdd2.charAt(0) + 'E' ;
				
				if ( this.endX == x && this.endY == y && this.startX == x && this.startY == y )
					lineAdd2 = "" + lineAdd2.charAt(0) + 'X' ;
				
				
				l1 += lineAdd1;
				l2 += lineAdd2;
			}
			
			sRet += l1;
			if ( y != this.HEIGHT )
				sRet += "\n";
			
			sRet += l2;
			if ( y != this.HEIGHT )
				sRet += "\n";
		}
		
		return sRet;
	}
}
