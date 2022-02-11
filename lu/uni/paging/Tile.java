package lu.uni.paging;

/**
 * A <tt>Tile</tt> represents a tile of the internal memory.
 * Each tile might store one particular page, identified by
 * a page number. Certain characteristics are remembered for
 * each tile, as described in the attributes.
 * 
 * @author Steffen Rothkugel
 */
public class Tile
{
	
	/**
	 * Indicates whether a page has been loaded into the tile.
	 */
	private boolean inUse;

	/**
	 * The page number of the page loaded into that tile.
	 * Valid only iff the tile is in use.
	 */
	private int pageNr;

	/**
	 * The time (ticks of <tt>InternalClock</tt>) when the page has been loaded.
	 * Valid only iff the tile is in use.
	 */
	private int ticksLoaded;

	/**
	 * The time (ticks of <tt>InternalClock</tt>) of the last access to that tile.
	 * Valid only iff the tile is in use.
	 */
	private int ticksLastAccessed;

	/**
	 * Number of accesses to the page contained in that tile since loading.
	 * Valid only iff the page is in use.
	 */
	private int nrOfAccesses;
	
	
	public Tile()
	{
		this.inUse = false;
		this.pageNr = -1;
		this.nrOfAccesses = 0;
		this.ticksLastAccessed = this.ticksLoaded = InternalClock.currentTicks();
	}

	/**
	 * Returns whether the tile is in use.
	 * 
	 * @return whether the tile is in use.
	 */
	public boolean isInUse()
	{
		return inUse;
	}

	/**
	 * Returns the page number of the page loaded into that tile.
	 * Valid only iff the tile is in use.
	 * 
	 * @return the page number of the page loaded into that tile.
	 */
	public int getPageNr()
	{
		return pageNr;
	}

	/**
	 * Returns number of accesses to the page contained in that tile since loading.
	 * Valid only iff the page is in use.
	 * 
	 * @return number of accesses to the page contained in that tile since loading.
	 */
	public int getNrOfAccesses()
	{
		return nrOfAccesses;
	}

	/**
	 * Returns the time (ticks of <tt>InternalClock</tt>) of the last access to that tile.
	 * Valid only iff the tile is in use.
	 * 
	 * @return the time (ticks of <tt>InternalClock</tt>) of the last access to that tile.
	 */
	public int getTicksLastAccessed()
	{
		return ticksLastAccessed;
	}

	/**
	 * Returns the time (ticks of <tt>InternalClock</tt>) when the page has been loaded.
	 * Valid only iff the tile is in use.
	 * 
	 * @return the time (ticks of <tt>InternalClock</tt>) when the page has been loaded.
	 */
	public int getTicksLoaded()
	{
		return ticksLoaded;
	}

	/**
	 * Load a page into the tile.
	 * Marks page as in use. Remembers time (ticks) when the page has been
	 * loaded, which also reflects the time of last access. Resets number
	 * of accesses.
	 * 
	 * @param pageNr	the page number to be loaded.
	 */
	public void loadPage(int pageNr)
	{
		this.pageNr = pageNr;
		inUse = true;
		ticksLastAccessed = ticksLoaded = InternalClock.currentTicks();
		nrOfAccesses = 0;
	}

	/**
	 * Mark access to that tile.
	 * Increment number of accesses and remember time (ticks) of this access.
	 */
	public void access()
	{
		++nrOfAccesses;
		ticksLastAccessed = InternalClock.currentTicks();
	}
	
}
