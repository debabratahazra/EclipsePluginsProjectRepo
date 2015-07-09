package com.odcgroup.integrationfwk.ui.decorators.core;

/**
 * 
 *
 * Utiltity Class to store the label decoration preference. This should
 * be enhanced by users so that the values are persistent across sessions 
 */
public class DecoratorStore
{
  private static DecoratorStore instance_ = null;
  
  private boolean displayTextLabelInformation_ = true;
  private boolean displayProjectName_ = true;

	/**
	 * Constructor for DemoStore.
	 */
	private DecoratorStore()
	{
		super();
	}

  public static DecoratorStore getInstance()
  {
    if (instance_ == null)
    {
      instance_ = new DecoratorStore();
    }
    return instance_;
  }
  
  /**
   * Function to determine whether the File Label prefix / suffix should be 
   * displayed
   * 
   * @return true owner name should be displayed
   *          false otherwise 
   */ 
  public boolean shouldDisplayTextLabel()
  {
    return displayTextLabelInformation_;
  }
  
  
  /**
   * Set values for boolean flag to display prefix/suffix
   */ 
  public void setDisplayTextLabel (boolean value)
  {
    displayTextLabelInformation_ = value;
  }
  
  /**
   * Display Project label decorator or not
   */ 
  public boolean shouldDisplayProject ()
  {
    return displayProjectName_;
  }
  
  public void setDisplayProject (boolean value)
  {
    displayProjectName_ = value;
  }
}
