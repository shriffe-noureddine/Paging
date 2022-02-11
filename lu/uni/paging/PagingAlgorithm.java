package lu.uni.paging;

import java.util.ListIterator;

/**
 * Interface for a page replacement algorithm.
 * 
 * @author Steffen Rothkugel
 */
public interface PagingAlgorithm
{

	/**
	 * Determine tile to be used for page replacement.
	 * A concrete implementation needs to identify exactly one <tt>Tile</tt>
	 * instance, i.e. one element of <tt>tiles</tt> that will be replaced.
	 * The algorithm can retrieve certain properties like for instance
	 * loading time and number of accesses from each tile. Moreover,
	 * the reference string can be queried in order to determine past
	 * as well as future memory references.
	 * 
	 * @param tiles set of memory tiles.
	 * @param pageNr page number that is currently accessed and needs to be loaded.
	 * @param referencesPastCurrent references 
	 * @return
	 * @see Tile
	 * @see ReferenceStringReader#getListIteratorPastCurrent()
	 */
	public Tile selectTileToReplace(Tiles tiles, int pageNr, ListIterator<Integer> referencesPastCurrent);

}
