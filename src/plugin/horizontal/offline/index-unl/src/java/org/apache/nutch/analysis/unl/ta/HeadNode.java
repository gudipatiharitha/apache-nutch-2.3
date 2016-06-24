package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.enconversion.unl.ta;
import java.io.*;
public class HeadNode implements Serializable
{
	public RelationNode rownext;
public	ConceptNode colnext;
	
	public HeadNode()
		{
			rownext = null;
			colnext = null;
		}
	public RelationNode getRowNext()
		{
			return rownext;
		}
		
		public ConceptNode getColNext()
		{
			return colnext;
		}
		
		public void setRowNext(RelationNode rwnxt)
		{
			rownext = rwnxt;
		}
		
		public void setColNext(ConceptNode colnxt)
		{
			colnext = colnxt;
		}	
		
}
