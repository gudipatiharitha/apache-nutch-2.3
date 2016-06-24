package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.enconversion.unl.ta;
import java.io.*;
public  class ConceptNode implements Serializable
	{
	public String gn_word;
	public String uwconcept;
	public String conceptid;
	public String docid;
	public String sentid;
	public String poscheck;
	public	int rowct;
	public String freq_count;
	public String con_freq;
	public String con_weight;
	public String tam_uid;
	public String con_uid;
	public String queryTag;
	public String MWtag_Qw;
		public ConceptToNode rownext;
		public ConceptNode colnext;
		
		// Node constructor
		
		public ConceptNode()
		{
			uwconcept="";
			conceptid="";
			docid="";
			sentid="";
			poscheck="";
			freq_count="";
			con_freq = "";
			con_weight = "";
			tam_uid = "";
			con_uid = "";
			rowct=0;
			queryTag = null;
			MWtag_Qw=null;
			rownext = null;
			colnext = null;
		}
		
		public ConceptNode(String w,String uw,String cid,String did,String sid,String pos,String fcount, String c_freq, String c_weight, String t_uid, String c_uid, String qTag,String MWtag)
		{
			//System.out.println("ConceptNode:"+w+" "+uw+" "+cid+" "+did+" "+sid+" "+pos+" "+fcount+" "+qTag+" "+MWtag);
			gn_word = w;
			uwconcept = uw;
			conceptid = cid;
			docid = did;
			sentid = sid;
			poscheck= pos;
			freq_count=fcount;
			con_freq = c_freq;
			con_weight = c_weight;
			tam_uid = t_uid;
			con_uid = c_uid;
			rowct=0;
			queryTag = qTag;
			MWtag_Qw=MWtag;
			rownext = null;
			colnext = null;
			
		}
		
		public void setData(String w, String uw,String cid,String did,String sid,String pos,String fcount,String c_freq, String c_weight, String t_uid, String c_uid, String qTag,String MWtag)
		{
			gn_word = w;
			uwconcept = uw;
			conceptid = cid;
			docid = did;
			sentid = sid;
			poscheck= pos;
			freq_count=fcount;
			con_freq = c_freq;
			con_weight = c_weight;
			tam_uid = t_uid;
			con_uid = c_uid;
			queryTag = qTag;
			MWtag_Qw=MWtag;
		}
		
		public ConceptToNode getRowNext()
		{
			return rownext;
		}
		
		public ConceptNode getColNext()
		{
			return colnext;
		}
		
		public void setRowNext(ConceptToNode rwnxt)
		{
			rownext = rwnxt;
		}
		
		public void setColNext(ConceptNode colnxt)
		{
			colnext = colnxt;
		}
	}
	
