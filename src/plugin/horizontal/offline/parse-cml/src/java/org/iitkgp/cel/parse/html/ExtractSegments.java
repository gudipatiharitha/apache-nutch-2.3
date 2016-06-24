package org.iitkgp.cel.parse.html;



import de.l3s.boilerpipe.document.TextBlock;
import de.l3s.boilerpipe.document.TextDocument;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

/**
* @author Parnab kr Chanda
*         IIT KGP
*/

public class ExtractSegments {
	TextDocument boilerTextDocument;
	String focusedText;
	String taggedPageSegment;

	public ExtractSegments(TextDocument boilerTextDocument) {
		this.boilerTextDocument = boilerTextDocument;

	}

	
	
	
	public String getFocusedText() {
		extractFocusedText();
		return focusedText;
	}




	public String getTaggedPageSegments() {
		extractSegments();
		return taggedPageSegment;
	}




	private void extractFocusedText() {
		try {
			String text = ArticleExtractor.INSTANCE
					.getText(this.boilerTextDocument);
			text = encodeAndCleanTextBlock(text);
			this.focusedText=text;
		} catch (Exception e) {
			System.out.println("FAILED EXTRACTION OF FOCUSED TEXT :(");
		}
	}
	
	public String getTag(double td, double ld) {

		String tag = "O";

		if (td >= 0.5 && ld >= 0.5) {
			tag = "TL";
		}
		if (td >= 0.5 && ld <= 0.5) {
			tag = "T";
		}

		return tag;
	}
	
	public String encodeAndCleanTextBlock(String text){
		text = TextUtils.WipeOutSpecials(text);
		text = TextUtils.ConvertHexToUnicode(text);
		return text;
	}

	public void extractSegments() {
		try {
			ArticleExtractor.INSTANCE.process(this.boilerTextDocument);
			double normTextDensity = 0.0;
			double normLinkDensity = 0.0;
			for (TextBlock block : this.boilerTextDocument.getTextBlocks()) {
				double textDensity = block.getTextDensity();
				double linkDensity = block.getLinkDensity();

				if (textDensity >= normTextDensity) {
					normTextDensity = textDensity;
				}

				if (linkDensity >= normLinkDensity) {
					normLinkDensity = linkDensity;
				}
			}
			
			StringBuilder taggedSegmentBuilder = new StringBuilder();
			for (TextBlock block : this.boilerTextDocument.getTextBlocks()) {
	        	
	        	double textDensity=block.getTextDensity()/normTextDensity;
	        	double linkDensity=block.getLinkDensity()/normLinkDensity;
	        	
	        	if(textDensity>=0.5){
	        		String tag=getTag(textDensity, linkDensity);
	        		String textBlock=block.getText();
	        		textBlock=encodeAndCleanTextBlock(textBlock);
	        		taggedSegmentBuilder.append("<"+tag+">");
	        		taggedSegmentBuilder.append(textBlock);
	        		taggedSegmentBuilder.append("</"+tag+">");
	        		
	        	}
	        	
	        
	        }
			
			this.taggedPageSegment=taggedSegmentBuilder.toString();

		} catch (Exception e) {
			System.out.println("FAILED EXTRACTION OF SEGMENTS :( ");
		}
	}

}
