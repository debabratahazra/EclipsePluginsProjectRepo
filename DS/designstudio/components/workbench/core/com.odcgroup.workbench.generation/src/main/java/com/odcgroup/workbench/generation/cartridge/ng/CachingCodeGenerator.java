package com.odcgroup.workbench.generation.cartridge.ng;

/**
 * Interface for generators which are statefull AND
 * who cache significantly size data structures.    
 *
 * @author Michael Vorburger
 */
public interface CachingCodeGenerator extends StatefullCodeGenerator {

	/**
	 * This method is invoked by the infrastructure when have
	 * nearly run out of memory.  An implementation should free
	 * up any internally cached data structures which can be
	 * re-created  at the next doGenerate() if they really
	 * are still required.
	 */
	public void giveMeSomeSunshineAsWeAreNearOOM();
}
