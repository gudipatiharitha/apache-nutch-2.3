package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.enconversion.unl.ta;
import java.io.*;
public class ConceptToNode implements Serializable
	{
	public	String uwfrmconcept;
	public	String uwtoconcept;
	public	String relnlabel;
//	public  String scopeid; 
	public	String docid;
	public	String sentid;
		
	
		
	public	ConceptToNode rownext;
	public	ConceptToNode colnext;
		
		// Node constructor
		
		public ConceptToNode()
		{
			uwfrmconcept="";
			uwtoconcept="";
			relnlabel="";
		//	scopeid="";
			docid="";
			sentid="";
			
			
			rownext = null;
			colnext = null;
		}
		
		public ConceptToNode(String con,String con1,String rl,String did,String sid)
		{
			uwfrmconcept=con;
			uwtoconcept=con1;
			relnlabel = rl;
		//	scopeid = scpid;
			docid = did;
			sentid = sid;
			
			
			rownext = null;
			colnext = null;
			
		}
		
		public void setData(String con,String con1,String rl,String scpid,String did,String sid)
		{
			uwfrmconcept=con;
			uwtoconcept=con1;
			relnlabel = rl;
		//	scopeid = scpid;
			docid = did;
			sentid = sid;
			
		}
		
		public ConceptToNode getRowNext()
		{
			return rownext;
		}
		
		public ConceptToNode getColNext()
		{
			return colnext;
		}
		
		public void setRowNext(ConceptToNode rwnxt)
		{
			rownext = rwnxt;
		}
		
		public void setColNext(ConceptToNode colnxt)
		{
			colnext = colnxt;
		}
	}
