package general;

import java.util.Scanner;

public class MyScanner 
{
	private static Scanner m_scn=new Scanner(System.in);
	public static int nextInt()
	{
		return m_scn.nextInt();
	}
	public static char nextChar() 
	{
		return m_scn.next().charAt(0);
	}
	
public void close()
	{
		m_scn.close();
	}
}
