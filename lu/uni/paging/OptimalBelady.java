package lu.uni.paging;

import java.util.HashSet;
import java.util.ListIterator;

/**
 * Optimal page replacement algorithm by Belady.
 * 
 * @author Steffen Rothkugel
 */
public class OptimalBelady implements PagingAlgorithm
{

	public Tile selectTileToReplace(Tiles tiles, int pageNr, ListIterator<Integer> referencesPastCurrent)
	{
		// determine page which will not be referred to in the future
		// for the longest time ...
		
		// build set of pages currently in memory ...
		HashSet<Integer> currentTiles = new HashSet<Integer>();
		
		for (int i=0 ; i<tiles.getNrOfTiles() ; ++i)
		{
			Tile t = tiles.getTile(i);
			
			currentTiles.add(new Integer(t.getPageNr()));
		}
		
		// continue removing pages accessed in the future, in order
		// of their access, until having identified a single tile
		// that is the one to be replaced ...
		while (currentTiles.size() > 1)
		{
			// stop also if there are no more accesses ...
			if (! referencesPastCurrent.hasNext())
			{
				break;		
			}
			
			// determine page accessed next ...
			Integer nextRef = (Integer) referencesPastCurrent.next();
			// ... and remove it from the set ...
			currentTiles.remove(nextRef);
		}

		// note that currentTiles might still contain more than one entry,
		// e.g. in case of the reference string being too short to determine
		// a single candidate only; in that case, every entry left will do ...		
		int pageNrToReplace = ((Integer) currentTiles.iterator().next()).intValue();
		
		// identify tile to be replaced ...
		for (int i=0 ; i<tiles.getNrOfTiles() ; ++i)
		{
			Tile t = tiles.getTile(i);
			
			if (t.getPageNr() == pageNrToReplace)
			{
				return t;
			}
		}
		
		return null;
	}

}
