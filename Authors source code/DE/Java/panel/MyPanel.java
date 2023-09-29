package DeApp1.panel;

import java.awt.*;         // Import all classes from the java.awt package
                           // AWT is the Abstract Window Toolkit. The AWT
import java.io.*;



public class MyPanel extends Panel
/***********************************************************
**                                                        **
** Enables the usage of gridbaglayout in panels.          **
**                                                        **
** Authors:            Rainer Storn                       **
**                                                        **
***********************************************************/ 
{

  public MyPanel ()
  /***************************************
  ** Constructor.                       **
  ***************************************/
  {
	 //do nothing
  }


//---------------------------------------------------------
	protected void constrain(Container container,Component component
		,int grid_x,int grid_y,int grid_with,int grid_height,int fill,
		int anchor,double weight_x,double weight_y,int top,int left,
		int bottom,int right)
//---------------------------------------------------------
    /** Unfortunately we can't use the constrain() method defined 
	 in class Screen, so we have to define it here again.
	*/
	/** This method places the component in the container
	grid_x, gird_y		  : the absolute place of the cells in the gridlayout
	grid_width,grid_height: the number of cells which use this component
	fill				  : the direction, where the component become bigger when it is possible.
	anchor				  : the position in its cells (NORTH SOUTH EAST...)
	weight_x,weight_y     : when the window becomes larger, this direction
	top,left,bottom,right : the marge arround the component
	*/
	{
		GridBagConstraints c =new GridBagConstraints();
		c.gridx		=grid_x;
		c.gridy		=grid_y;
		c.gridwidth	=grid_with;
		c.gridheight=grid_height;
		c.fill		=fill;
		c.anchor	=anchor;
		c.weightx	=weight_x;
		c.weighty	=weight_y;
		if (top+bottom+left+right>0)
			c.insets=new Insets(top,left,bottom,right);
		((GridBagLayout)container.getLayout()).setConstraints(component,c);
		container.add(component);
	}
}// End class ControlPanel





