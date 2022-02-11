package lu.uni.paging;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ListIterator;
import java.util.Vector;

/**
 * Utility for reading a reference string from a file.
 * Allows also to iterate over the references.
 * 
 * @author Steffen Rothkugel
 */
public class ReferenceStringReader
{
	
	private Vector<Integer> references;
	private ListIterator<Integer> refListIter;

	
	/**
	 * Initialize reference string to be processed from a file.
	 * The file is supposed to contain a list of space separated
	 * integer values representing page numbers.
	 * 
	 * @param referenceStringFileName the name of the file containing the reference string.
	 */
	ReferenceStringReader(String referenceStringFileName)
	{
		try
		{
			// read reference string from file, storing all references
			// into a vector ...
			StreamTokenizer st = new StreamTokenizer(new BufferedReader(new FileReader(referenceStringFileName)));
			
			st.parseNumbers();
			references = new Vector<Integer>();
			
			while (st.nextToken() != StreamTokenizer.TT_EOF)
			{
				if (st.ttype != StreamTokenizer.TT_NUMBER)
				{
					System.out.println("non-number found in reference string file");
					System.exit(1);
				}
				references.add(new Integer((int) st.nval));
			}
			
			// create iterator for processing each reference in sequential order ...
			refListIter = references.listIterator();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/**
	 * Returns a <tt>ListIterator</tt> allowing to iterate over future
	 * as well as past references.
	 * The iterator is positioned between the current reference (the one
	 * returned by the last invocation of <tt>nextReference</tt>) and
	 * the next one. Calling <tt>hasNext</tt> and <tt>next</tt> will return
	 * the subsequent references in the future, starting with the next
	 * access in order. Calling <tt>hasPrevious</tt> and <tt>previous</tt>
	 * will return references from the past (in reverse order), starting
	 * with the current reference.
	 * 
	 * @return a <tt>ListIterator</tt> allowing to iterate over future as well as past references.
	 */
	public ListIterator<Integer> getListIteratorPastCurrent()
	{
		return references.listIterator(refListIter.nextIndex());
	}
	
	/**
	 * Returns whether there is another reference to be processed.
	 * 
	 * @return whether there is another reference to be processed.
	 */
	public boolean hasNextReference()
	{
		return refListIter.hasNext();
	}
	
	/**
	 * Returns the next reference to be processed.
	 * 
	 * @return the next reference to be processed.
	 */
	public int nextReference()
	{
		Integer nextRef = (Integer) refListIter.next();
		
		return nextRef.intValue();
	}

}
