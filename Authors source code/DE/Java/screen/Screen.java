package DeApp1.screen;
import java.awt.*;

/*********************************************************************************
* -   Screen											 -
* -	this class is a Frame with the function constraint.	 -
* -	We use this fonction to place the component			 -
*
* gridx, gridy 
* Specify the row and column at the upper left of the component. The leftmost
* column has address gridx=0, and the top row has address gridy=0. Use
* GridBagConstraints.RELATIVE (the default value) to specify that the
* component be placed just to the right of (for gridx) or just below (for gridy)
* the component that was added to the container just before this component was
* added. 
*
* gridwidth, gridheight 
* Specify the number of columns (for gridwidth) or rows (for gridheight) in
* the component's display area. These constraints specify the number of cells the
* component uses, not the number of pixels it uses. The default value is 1. Use
* GridBagConstraints.REMAINDER to specify that the component be the last
* one in its row (for gridwidth) or column (for gridheight). Use
* GridBagConstraints.RELATIVE to specify that the component be the next
* to last one in its row (for gridwidth) or column (for gridheight). 
*
* Note: Due to a bug in the the 1.0 release of Java, GridBagLayout doesn't allow
* components to span multiple rows unless the component is in the leftmost
* column. 
*
* fill 
* Used when the component's display area is larger than the component's
* requested size to determine whether and how to resize the component. Valid
* values are GridBagConstraints.NONE (the default),
* GridBagConstraints.HORIZONTAL (make the component wide enough to fill
* its display area horizontally, but don't change its height),
* GridBagConstraints.VERTICAL (make the component tall enough to fill its
* display area vertically, but don't change its width), and
* GridBagConstraints.BOTH (make the component fill its display area
* entirely). 
*
* insets 
* Specifies the external padding of the component -- the minimum amount of
* space between the component and the edges of its display area. The value is
* specified as an Insets object. By default, each component has no external
* padding. 
*
* anchor 
* Used when the component is smaller than its display area to determine where
* (within the area) to place the component. Valid values are
* GridBagConstraints.CENTER (the default), GridBagConstraints.NORTH,
* GridBagConstraints.NORTHEAST, GridBagConstraints.EAST,
* GridBagConstraints.SOUTHEAST, GridBagConstraints.SOUTH,
* GridBagConstraints.SOUTHWEST, GridBagConstraints.WEST, and
* GridBagConstraints.NORTHWEST. 
*
* weightx, weighty 
* Specifying weights is an art that can have a significant impact on the appearance
* of the components a GridBagLayout controls. Weights are used to determine
* how to distribute space among columns (weightx) and among rows
* (weighty); this is important for specifying resizing behavior. 
*
* Unless you specify at least one nonzero value for weightx or weighty, all the
* components clump together in the center of their container. This is because
* when the weight is 0.0 (the default), the GridBagLayout puts any extra space
* between its grid of cells and the edges of the container. 
*
* Generally weights are specified with 0.0 and 1.0 as the extremes, with numbers
* in between used as necessary. Larger numbers indicate that the component's
* row or column should get more space. For each column, the weight is related to
* the highest weightx specified for a component within that column (with each
* multi-column component's weight being split somehow between the columns the
* component is in). Similarly, each row's weight is related to the highest weighty
* specified for a component within that row. Extra space tends to go toward the
* rightmost column and bottom row.
*
* ************************************************************************************/

//=========================================================
public class Screen extends Frame
//=========================================================
 {
	Image image;

//---------------------------------------------------------
 	protected Screen(String title)
//---------------------------------------------------------
	{
		super(title);
	}

//---------------------------------------------------------
	public boolean handleEvent(Event event)
//---------------------------------------------------------
	/** This event must be managed by the class Window
	but because it was a problem, Screen takes now care itself
	of this event*/
	{
		 if(event.id==Event.WINDOW_DESTROY)
				 this.dispose();
		 else return super.handleEvent(event);
		 return true;
	}
	
//---------------------------------------------------------
	protected void constrain(Container container,Component component
		,int grid_x,int grid_y,int grid_with,int grid_height,int fill,
		int anchor,double weight_x,double weight_y,int top,int left,
		int bottom,int right)
//---------------------------------------------------------

	/** This Methode place the component in the container
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

//---------------------------------------------------------
	protected void constrain(Container container,Component component,
		int grid_x,int grid_y,int grid_with,int grid_height
		,int top,int left,int bottom,int right)
//--------------------------------------------------------- 
	/** This is the same method with some default values*/

	{
		constrain(container,component,grid_x,grid_y, grid_with, 
			grid_height,GridBagConstraints.
			NONE,GridBagConstraints.NORTHWEST,
			0.0,0.0,top, left,bottom, right);
	}

//---------------------------------------------------------
	protected void constrain(Container container,Component component,
		int grid_x,int grid_y,int grid_with,int grid_height)
//---------------------------------------------------------
	{	//This is the same method with some default values
		constrain(container,component,grid_x,grid_y, grid_with, 
			grid_height,GridBagConstraints.NONE,
			GridBagConstraints.NORTHWEST,
			0.0,0.0,0,0,0,0);
	}
}



