package lu.uni.paging;

public class Tiles
{

	Tile tiles[];


	public Tiles(int nrOfTiles)
	{
		tiles = new Tile [nrOfTiles];
		for (int i=0 ; i<tiles.length ; ++i)
		{
			tiles[i] = new Tile();
		}
	}
	
	public int getNrOfTiles()
	{
		return tiles.length;
	}
	
	public Tile getTile(int i)
	{
		return tiles[i];
	}
	
	public Tile getFreeTile()
	{
		for (int i=0 ; i<tiles.length ; ++i)
		{
			if (! tiles[i].isInUse())
			{
				return tiles[i];
			}
		}
		
		return null;
	}

	public Tile locate(int pageNr)
	{
		for (int i=0 ; i<tiles.length ; ++i)
		{
			if (tiles[i].isInUse() && tiles[i].getPageNr() == pageNr)
			{
				return tiles[i];
			}
		}
		
		return null;
	}

	public void print()
	{
		for (int i=0 ; i<tiles.length ; ++i)
		{
			if (tiles[i].isInUse())
			{
				System.out.printf("%3d ", tiles[i].getPageNr());
			}
			else
			{
				System.out.printf("--- ");
			}
		}
	}

}
