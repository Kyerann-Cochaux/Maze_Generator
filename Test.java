public class Test
{
	public static void main(String[] args)
	{
		Maze maze = new Maze();
		
		boolean bValidArgs = true;
		
		for (int i = 0; i < args.length; i++)
			bValidArgs &= Test.isInt(args[i]);
		
		if ( !bValidArgs )
		{
			System.out.println("Invalid arguments !");
			return;
		}
		
		switch (args.length)
		{
			case 2:
				maze = new Maze(
					Integer.parseInt(args[0]), // Height
					Integer.parseInt(args[1])  // Width
				);
				break;
			
			
			case 6:
				maze = new Maze(
					Integer.parseInt(args[0]), // Height
					Integer.parseInt(args[1]), // Width
					
					Integer.parseInt(args[2]), // Start X
					Integer.parseInt(args[3]), // Start Y
					
					Integer.parseInt(args[4]), // End   X
					Integer.parseInt(args[5])  // End   Y
				);
				break;
			
			default :
				System.out.println("Generating the \"Basic\" Maze.");
				break;
		}
		
		System.out.println(maze);
	}
	
	private static boolean isInt(String str)
	{
		try
		{
			Integer.parseInt(str);
		}
		catch (Exception e)
		{
			return false;
		}
		
		return true;
	}
}
