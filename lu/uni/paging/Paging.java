package lu.uni.paging;

/**
 * Sample framework for evaluation of different paging algorithms.
 * Based upon a reference string read from a file and a given number
 * of memory tiles available, the reference string is processed in
 * sequential order. In case no free memory tile is available, a
 * pluggable page replacement algorithm is invoked to determine
 * which tile to use for the replacement.
 * 
 * @author Steffen Rothkugel
 */
public class Paging
{

	public static void main(String[] args)
	{
		if (args.length != 3)
		{
			usage();
		}
		
		// create set of tiles ...
		Tiles tiles = new Tiles(Integer.parseInt(args[0]));
		// utility reading reference string from file ...
		ReferenceStringReader referenceStringReader = new ReferenceStringReader(args[1]);
		// instantiate paging algorithm ...
		PagingAlgorithm pagingAlgorithm = instantiatePagingAlgorithm(args[2]);

		// process reference string, see below ...
		processReferenceString(tiles, referenceStringReader, pagingAlgorithm);
	}

	/**
	 * Process reference string in sequential order, checking whether:<p>
	 * - the page accessed is already in memory, or<p>
	 * - there is a free tile to load the page, or<p>
	 * - the page replacement algorithm needs to be invoked for
	 *   determination of the tile to be replaced
	 * 
	 * @param tiles set of memory tiles
	 * @param referenceStringReader utility for iterating over memory references
	 * @param pagingAlgorithm concrete instance of a page replacement algorithm
	 */
	private static void processReferenceString(Tiles tiles, ReferenceStringReader referenceStringReader, PagingAlgorithm pagingAlgorithm)
	{
		// count number of page faults ...
		int	nrOfPageFaults = 0;
		
		// sequentially process each memory reference ...
		while (referenceStringReader.hasNextReference())
		{
			// which page is accessed ?
			int pageNr = referenceStringReader.nextReference();
			Tile tileAccessed;
			
			// advance internal clock by one tick; used for keeping track of
			// when pages are loaded and accessed ...
			InternalClock.nextTick();
			
			// show tiles / page numbers currently in memory ...
			tiles.print();
			System.out.print("  next ref: " + pageNr);
			
			// check whether page accessed is already in memory ...
			if ((tileAccessed = tiles.locate(pageNr)) != null)
			{
				// it's a hit ...
				System.out.println(" hit");
			}
			else
			{
				// page is currently _not_ in memory, so we have to
				// handle a page fault ...
				++nrOfPageFaults;

				// first check whether there is any free tile left ...
				if ((tileAccessed = tiles.getFreeTile()) != null)
				{
					System.out.println(" free tile found");
					// load page into free tile ...
					tileAccessed.loadPage(pageNr);
				}
				else
				{			
					// no free tile left, so we must determine a tile to evict
					// and to load the page being requested into--this decision
					// is taken by the page replacement algorithm, which receives:
					// - the set of tiles, allowing to check which pages are currently
					//   loaded, together with certain properties like time (ticks) when
					//   the current page has been loaded into the tile, the time (ticks)
					//   of last access, and the number of accesses to that page since
					//   it has been loaded; please refer also to Tile for more information
					// - the page number of the page being requested
					// - an iterator of the reference string, positioned past the current
					//   reference, i.e. the next() method will return the next element
					//   in the reference string
					tileAccessed = pagingAlgorithm.selectTileToReplace(tiles, pageNr, referenceStringReader.getListIteratorPastCurrent());
				
					System.out.println(" replace " + tileAccessed.getPageNr());
					// evict current page and load new one into that tile ...
					tileAccessed.loadPage(pageNr);
				}
			}
			
			// mark access to that tile, incrementing number of accesses and remembering
			// time (ticks) of this access ...
			tileAccessed.access();
		}
		tiles.print();
		System.out.println();
		// report total number of page faults ...
		System.out.println("Number of page faults: " + nrOfPageFaults);
	}

	private static PagingAlgorithm instantiatePagingAlgorithm(String algorithmClassName)
	{
		try
		{
			Class<?>	algorithmClass = Class.forName(algorithmClassName);
			
			if (! PagingAlgorithm.class.isAssignableFrom(algorithmClass))
			{
				System.out.println(algorithmClassName + " does not implement " + PagingAlgorithm.class.getName());
				System.exit(1);
			}
			
			System.out.println("using paging algoritm: " + algorithmClassName);
		
			return (PagingAlgorithm) algorithmClass.newInstance();
			
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		System.exit(1);
		
		// never reached
		return null;
	}
	
	private static void usage()
	{
		System.out.println("Usage:");
		System.out.println("Paging <nrOfTiles> <referenceStringFile> <algorithmClass>");
		System.exit(1);
	}

}
