package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.enconversion.unl.ta;
import java.io.*;
public  class RelationNode implements Serializable
	{
		String relnlabel;
				
	public	RelationNode rownext;
	public ConceptToNode colnext;
		
		// Node constructor
		
		public RelationNode()
		{
			relnlabel="";
						
			rownext = null;
			colnext = null;
		}
		
		public RelationNode(String rl)
		{
			relnlabel = rl;
						
			rownext = null;
			colnext = null;
			
		}
		
		public void setData(String rl)
		{
			relnlabel = rl;
		}
		
		public RelationNode getRowNext()
		{
			return rownext;
		}
		
		public ConceptToNode getColNext()
		{
			return colnext;
		}
		
		public void setRowNext(RelationNode rwnxt)
		{
			rownext = rwnxt;
		}
		
		public void setColNext(ConceptToNode colnxt)
		{
			colnext = colnxt;
		}
	}
	
