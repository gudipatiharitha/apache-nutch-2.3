package org.iitkgp.nutch.scoring.content;

public class Utilities {


// The Following Method helps to decode a ill Formed URL 	
	
	public static  String DecodeURL(String s) {
		StringBuffer sbuf=null;
		
		try{
	    sbuf = new StringBuffer () ;
	    int l  = s.length() ;
	    int ch = -1 ;
	    int b, sumb = 0;
	    for (int i = 0, more = -1 ; i < l ; i++) {
	      /* Get next byte b from URL segment s */
	      switch (ch = s.charAt(i)) {
		case '%':
		  ch = s.charAt (++i) ;
		  int hb = (Character.isDigit ((char) ch) 
			    ? ch - '0'
			    : 10+Character.toLowerCase((char) ch) - 'a') & 0xF ;
		  ch = s.charAt (++i) ;
		  int lb = (Character.isDigit ((char) ch) 
			    ? ch - '0'
			    : 10+Character.toLowerCase((char) ch) - 'a') & 0xF ;
		  b = (hb << 4) | lb ;
		  break ;
		case '+':
		  b = ' ' ;
		  break ;
		default:
		  b = ch ;
	      }
	      /* Decode byte b as UTF-8, sumb collects incomplete chars */
	      if ((b & 0xc0) == 0x80) {			
		sumb = (sumb << 6) | (b & 0x3f) ;	
		if (--more == 0) sbuf.append((char) sumb) ; 
	      } else if ((b & 0x80) == 0x00) {		
		sbuf.append((char) b) ;			
	      } else if ((b & 0xe0) == 0xc0) {		
		sumb = b & 0x1f;
		more = 1;				
	      } else if ((b & 0xf0) == 0xe0) {		
		sumb = b & 0x0f;
		more = 2;				
	      } else if ((b & 0xf8) == 0xf0) {		
		sumb = b & 0x07;
		more = 3;				
	      } else if ((b & 0xfc) == 0xf8) {
		sumb = b & 0x03;
		more = 4;		
	      } else /*if ((b & 0xfe) == 0xfc)*/ {
		sumb = b & 0x01;
		more = 5;				
	      }
	      /* No need to test if the UTF-8 encoding is well-formed */
	    }
	    
		}catch(Exception e){
			System.out.println("Cannot Decode url");
		}
	    
	    return sbuf.toString() ;
	  }
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
