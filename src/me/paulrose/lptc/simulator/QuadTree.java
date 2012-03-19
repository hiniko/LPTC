package me.paulrose.lptc.simulator;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;;

public class QuadTree
{
	private int maxElements, maxDepth;
	
	private Dimension size;
	private Rectangle area;
	private Node root;
	private boolean preDivided;
	private ArrayList<Entity> elements;
	
	
	public QuadTree(Point loc, Dimension size, int maxElements, int maxDepth)
	{
		area = new Rectangle(loc.intX(), loc.intY(), size.width, size.height);;
		root = new Node(loc, size ,0, null);
		this.maxElements = maxElements;
		this.maxDepth = maxDepth;
		preDivided = false;
	}
	
	
	public QuadTree(int x, int y, int w, int h, int maxElements, int maxDepth)
	{
		this(new Point(x,y), new Dimension(w,h), maxElements, maxDepth);
	}
	
	
	public void preDivide()
	{
		if( ! preDivided)
			root.forceSubdivide();
	}
	
	public void insert(Entity e)
	{
		if(!area.intersects(e.bounds))
		{
			throw new IllegalArgumentException(
					"Point " + e.bounds.getX() + ":" +e.bounds.getY() + 
					"not in quad tree range " + e.toString());
		}
		
		root.insert(e);
	}
	
	public void delete(Entity e)
	{
		root.delete(e);
	}
	
	public void update()
	{
		root.update();
	}
	
	public void draw(Graphics g)
	{
		root.draw(g);
	}
	
	public ArrayList<Entity> getItems(Rectangle r)
	{
		// This list is passed to nodes so they can add the entities that 
		// intersect withe the given rectangle
		ArrayList<Entity> items = new ArrayList<Entity>();
		
		root.get(r, items);
		
		return items;
		
	}
	
	public void findCollisions()
	{
		root.findCollisions();
	}
	
	private class Node
	{
		
		
		private static final int TOP_LEFT 		= 0;
		private static final int TOP_RIGHT 		= 1;
		private static final int BOTTOM_LEFT	= 2;
		private static final int BOTTOM_RIGHT 	= 3;
		
		private ArrayList<Entity> elements;
		private HashMap<Integer, Node> nodes;
		
		private Rectangle area;
		private Point location;
		private int depth;
		private Node parent;
		private Color c;
		
		
		public Node(Rectangle area, int depth, Node parent)
		{
			this.area = area;
			this.elements = new ArrayList<Entity>();
			this.nodes = new HashMap<Integer, Node>();
			this.depth = depth;
			this.parent = parent;
			
			c = new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256));
		}
		
		public Node(Point loc, Dimension size, int depth, Node parent)
		{
			this(new Rectangle(loc.intX(), loc.intY(), size.width, size.height)
			, depth, parent);
		}
		
		
		public void insert(Entity e)
		{
			
			// Check if node is divided and call the node if it is
			if(nodes.size() != 0)
			{	
				for(Node n : nodes.values())
				{
					if(e.bounds.intersects(n.area))
					{
						n.insert(e);
					}
				}
				return;
			}
			else
			{
				// Add to the element list for this node No dupes though
				if(!elements.contains(e))
					elements.add(e);
				
				// Check to see if this node is full and we are not at the
			 	// So the tree dosn't get too big
				if(elements.size() > maxElements && depth < maxDepth)
				{
					// Create the new nodes
					subdivide();
					
					// Insert all of our elements into the new nodes
					for(Entity ce : elements)
					{
						 insert(ce);
					}
					// Remove all of the elements from this node as there are 
					// in the children node
					elements.clear();	
					
				}
			}
		}
		
		public void clear()
		{
			for(Node n : nodes.values())
			{
				n.clear();
			}
			
			elements.clear();
			nodes.clear();
			
			
		}
		
		public void draw(Graphics g)
		{
			//System.out.println("Drawing at depth " + depth);
			// Draw this Area
			g.setColor(c);
			g.fillRect(area.getX(),area.getY(), area.getWidth(), area.getHeight());
			g.setColor(Color.black);
			g.drawString(""+ elements.size(), area.getX()+ area.getWidth()/2, area.getY()+area.getHeight()/2);
			
			// if there are any children draw them 
			for(Node n : nodes.values())
			{
				n.draw(g);
			}
		}
		
		public void update()
		{
			// Check all the entities in this node to see if they are still 
			// within their area, reInsert them into the tree if they are
	
			if(elements.size() > 0)
			{	
				ArrayList<Entity> reInsert = new ArrayList<Entity>();
				
				for(Iterator<Entity> i = elements.iterator(); i.hasNext();)
				{
					Entity e = i.next();
					
					// Has the entity left this node completely?
					if(!e.bounds.intersects(area) && parent != null)
					{
						i.remove();
						reInsert.add(e);
					}
					// is the entity still fully within this area?
					else if (!area.contains(e.bounds) && parent != null)
					{
						// it is not, it needs reinserting to be in other nodes
						reInsert.add(e);
					}
				}
				
				for(Entity e : reInsert)
				{
					root.insert(e); 
				}
			
			}
			
			// Get the number of items that live in this node or the children of this node
			// and shrink down if there are less nodes then the max Amount.
			
			if(getItemCount() <= maxElements && nodes.size() > 0)
			{
				// Add all child items to this node
				
				for(Node n : nodes.values())
				{
					for(Entity e : n.elements)
					{
						elements.add(e);
					}
				}
				
				// Remove the nodes!
				nodes.clear();
			}
			else
			{
				// We still have items in those node, Update them
				for(Node n : nodes.values())
				{
					n.update();
				}
			}

		}
		
		public int getItemCount()
		{
			if(nodes.size() == 0)
			{
				return elements.size(); 
			}
			else
			{
				int childElementCount = 0;
				for(Node n : nodes.values()){
					childElementCount += n.getItemCount();
				}
				
				return childElementCount;
				
			}
		}
		
		public void findCollisions()
		{
			
			// Walk through all nodes to find collisions
			if(nodes.size() == 0)
			{
				// Check all elements in this node for collisions
				for(Entity e : elements)
				{
					// check to see if it collides with other elements
					for(Entity t : elements)
					{
						// Don't check against self
						if (t != e)
						{
							float a = e.bounds.getWidth()/2 + 
									  t.bounds.getWidth()/2;
							
							float dx = e.bounds.getCenterX() - t.bounds.getCenterX();
							float dy = e.bounds.getCenterY() - t.bounds.getCenterY();
							// Check the radius combined are not longer then 
							// the distance between two points
							boolean isCollided = (a*a) > (dx*dx + dy*dy);
							if(isCollided)
							{
								if(!e.viewCollisions.contains(t))
									e.viewCollisions.add(t);
							}
						}
					}
				}
			}
			else
			{
				for (Node n : nodes.values())
				{
					n.findCollisions();
				}
			}
		}
		
		public void get(Rectangle r, ArrayList<Entity> list)
		{
			// Check to see if this node intersects the given area
			if(r.intersects(area))
			{
				// Check if we have children, if we don't add intersecting items
				// to the list
				if(nodes.size() == 0)
				{
					// Return all of the elements that are contained in r
					for(Entity e : elements)
					{
						if(r.contains(e.bounds))
							list.add(e);
					}
				}
				else // We have children, pass the get request to them
				{
					for(Node n : nodes.values())
					{
						n.get(r, list);
					}
				}
		
			}
			
		}
		
		
		public void delete(Entity e)
		{
			// Check to see if the Entity is in this node
			if(elements.contains(e))
			{
				elements.remove(e);
				return;
			}
			else // check in children
			{
				for(Node n : nodes.values())
				{
					n.delete(e);
				}
			}
		}
		
		public void forceSubdivide()
		{
			
			if(depth < maxDepth)
			{
				subdivide();
				for(Node n : nodes.values())
				{
					n.forceSubdivide();
				}
			}
			
		}
		
		public void subdivide()
		{
			int d = depth + 1;
			
			// Calculate  new area size
			float width = area.getWidth() / 2;
			float height = area.getHeight() / 2;
			
			// Top Left Node
			nodes.put(TOP_LEFT, new Node(new Rectangle(
					area.getX(), area.getY(), width, height
			), d, this));
			
			// Top Right Node
			nodes.put(TOP_RIGHT, new Node(new Rectangle(
					area.getX() + width, area.getY(), width, height
			), d, this));
			
			//Bottom Left Node
			nodes.put(BOTTOM_LEFT, new Node(new Rectangle(
					area.getX(), area.getY() + height, width, height
			), d, this));
			
			//Bottom Right Node
			nodes.put(BOTTOM_RIGHT, new Node(new Rectangle(
					area.getX() + width, area.getY() + height, width, height
			), d, this));
			
			
		}
	}
}
