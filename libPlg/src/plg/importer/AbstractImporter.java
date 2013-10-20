package plg.importer;

import plg.model.Process;

/**
 * This interface represents the basic structure of a model importer.
 * 
 * @author Andrea Burattin
 */
public interface AbstractImporter {

	/**
	 * General interface of a method that import a model.
	 * 
	 * @param filename the source of the model to import
	 * @return the imported model
	 */
	public Process importModel(String filename);
}
