package com.appon.gtantra;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
public class LineEnumeration implements Enumeration {
    private GFont font;
    private String text;
    private int width ;
    private String texts[];
    boolean init = false;
    private int counter;
    public LineEnumeration(GFont font, String text, int width) {
        this.font = font;
        this.text = text;
        this.width = width;
    }
    public boolean hasMoreElements() {
        if(!init)
        {
            init = true;
            texts = wrapText(text , width);
        }
        return counter < texts.length ;
    }
 
    public Object nextElement() {
      return texts[counter++];
    }
 
 
   
   public static void wrap(String str, int wrapLength) {
	   wrapLength = 14;
	   str = "This is a sentence that we're using to test the wrap method and hereisaveryveryveryverylongword checkit jkasd aksdh asdkjh asdkjh asdkh asdkjh asdkjh askdh askdhaks dh";
	   boolean wrapLongWords = false;
	   String newLineStr = "\n";
	           if (str == null) {
	               return ;
	           }
//	           if (newLineStr == null) {
//	               newLineStr = SystemUtils.LINE_SEPARATOR;
//	           }
	           if (wrapLength < 1) {
	               wrapLength = 1;
	           }
	           int inputLineLength = str.length();
	           int offset = 0;
	           
	           StringBuffer wrappedLine = new StringBuffer(inputLineLength + 32);
	           
	           while ((inputLineLength - offset) > wrapLength) {
	               if (str.charAt(offset) == ' ') {
	                   offset++;
	                   continue;
	               }
	               int spaceToWrapAt = str.lastIndexOf(' ', wrapLength + offset);
	   
	               if (spaceToWrapAt >= offset) {
	                   // normal case
	                   wrappedLine.append(str.substring(offset, spaceToWrapAt));
	                   wrappedLine.append(newLineStr);
	                   offset = spaceToWrapAt + 1;
	                   
	               } else {
	                   // really long word or URL
	                   if (wrapLongWords) {
	                       // wrap really long word one line at a time
	                       wrappedLine.append(str.substring(offset, wrapLength + offset));
	                       wrappedLine.append(newLineStr);
	                       offset += wrapLength;
	                   } else {
	                      // do not wrap really long word, just extend beyond limit
	                       spaceToWrapAt = str.indexOf(' ', wrapLength + offset);
	                       if (spaceToWrapAt >= 0) {
	                           wrappedLine.append(str.substring(offset, spaceToWrapAt));
	                           wrappedLine.append(newLineStr);
	                           offset = spaceToWrapAt + 1;
	                       } else {
	                           wrappedLine.append(str.substring(offset));
	                           offset = inputLineLength;
	                       }
	                   }
	               }
	           }
	   
	           // Whatever is left in line is short enough to just pass through
	           wrappedLine.append(str.substring(offset));
	   
//	           System.out.println("text:");
//	           System.out.println(wrappedLine.toString());
	}
   public int getStringLength(String text)
   {
       return font.getStringWidth(text);
   }
   public int getCharLength(char ch)
   {
       return font.getCharWidth(ch);
   }
   public int getStringLength(String str,int index)
   {
       int length = 0;
       int maxSize = Math.min(index, str.length());
       for (int i = 0; i < maxSize && i >= 0; i++) {
    	   length += getCharLength(str.charAt(i));
       }
       return length;
   }
   public int getCharIndexUptoSpecificLength(int length,String text)
   {
//       int lengthCalculated = 0;
//       int newSize = 0;
//       int maxSize = Math.min(length, text.length());
//       for (int i = 0; i < maxSize; i++) {
//    	   newSize = getCharLength(text.charAt(i));
//    	   if(newSize + lengthCalculated > length)
//    	   {
//    		   return (i - i) == -1 ? 0 : i - 1;
//    	   }else{
//    		   lengthCalculated += newSize;
//    	   }
//       }
//       return maxSize;
	   return getCharIndexUptoSpecificLength(length, text,0);
   }
   public int getCharIndexUptoSpecificLength(int length,String text,int offset)
   {
       int lengthCalculated = 0;
       int newSize = 0;
       int maxSize = Math.min(length, text.length());
       for (int i = offset; i < maxSize; i++) {
    	   newSize = getCharLength(text.charAt(i));
    	   if(newSize + lengthCalculated > length)
    	   {
    		   return (i - i) == -1 ? 0 : i - 1;
    	   }else{
    		   lengthCalculated += newSize;
    	   }
       }
       return maxSize;
   }
   public String[] wrapChange(String str, int wrapLength) {
	  
	   ArrayList<String> list = new ArrayList<String>();
	   
	           if (str == null) {
	               return null;
	           }
//	           if (newLineStr == null) {
//	               newLineStr = SystemUtils.LINE_SEPARATOR;
//	           }
	           if (wrapLength < 1) {
	               wrapLength = 1;
	           }
	           int inputLineLength = getStringLength(str);
	           int offset = 0;
	           int spaceConsumed = 0;
	         
	           
	           while ((inputLineLength - spaceConsumed) > wrapLength) {
	               if (str.charAt(offset) == ' ') {
	                   offset++;
	                   spaceConsumed += getCharLength(' ');
	                   continue;
	               }
//	               
	               int charIndex = getCharIndexUptoSpecificLength(wrapLength + spaceConsumed, str);
	               int spaceToWrapAt = str.lastIndexOf(' ', charIndex);
	   
	               if (spaceToWrapAt >= offset) {
	                   // normal case
	            	   list.add(str.substring(offset, spaceToWrapAt));
	                   offset = spaceToWrapAt + 1;
	                   spaceConsumed = getStringLength(str, offset);
	                   
	               } else {
	                   
	                       // wrap really long word one line at a time
	            	      charIndex = getCharIndexUptoSpecificLength(wrapLength + spaceConsumed, str,offset);
	            	      list.add(str.substring(offset, charIndex));
	            	      offset = charIndex + 1;
	            	      spaceConsumed = getStringLength(str, offset);
	                    
	               }
	           }
	   
	           // Whatever is left in line is short enough to just pass through
	           if(offset < str.length())
	           list.add(str.substring(offset));
	           
	           String text[] = new String[list.size()];
	           offset = 0;
	           for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				text[offset++] = string;
			   }
	           list.removeAll(list);
	           list = null;
	           return text;
	           
//	           System.out.println(wrappedLine.toString());
	}
//   public String [] wrapText (String text, int len)
//   {
//	   return wrapChange(text, len);
//   }
   private static int checkSize = 0;
   private int getMaxFontWidth(String text)
   {
	   int size = 0;
	   for (int i = 0; i < text.length(); i++) {
		if(getCharLength(text.charAt(i)) > size)
		{
			size = getCharLength(text.charAt(i));
		}
	   }
	   return size + 2;
   }
   public String[] testWrapped(String text,int len)
   {
	   int maxSize = getMaxFontWidth(text);
	   if(maxSize > len)
	   {
		   len = maxSize;
	   }
	   // return empty array for null text
	   if (text == null)
	   return new String [] {};

	   // return text if len is zero or less
	   if (len <= 0)
	   return new String [] {text};

	   // return text if less than length
	   if (getStringLength(text) <= len)
	   return new String [] {text};

	   
	   int charW = getCharLength(text.charAt(0));
	   int numMaxCharLine = (len / charW) * 2;
	   int maxLines = (getStringLength(text) / len) * 2;
	 //  ArrayList<String> linesList = new ArrayList<String>(maxLines);
	   ArrayList<String> linesList = new ArrayList<String>();
//	   System.out.println("maxLines: "+maxLines + " len: "+len +" text: "+text);
//	   Log.v("wrap", "maxLines: "+maxLines + " len: "+len +" text: "+text);
	 //  Vector lines = new Vector();
	   int lineCounter = 0;
	   StringBuffer line = new StringBuffer();
	   StringBuffer word = new StringBuffer();

	   for (int i = 0; i < text.length(); i++) {
	     word.append(text.charAt(i));

	     if (text.charAt(i) == ' ') {
	       if (( getStringLength(line.toString()) + getStringLength(word.toString()) > len)) {
	     	linesList.add(line.toString());
	         lineCounter++;
	         checkSize++;
	         line.delete(0, line.length());
	       }
	       line.append(word);
	       word.delete(0, word.length());
	      }else if (text.charAt(i) == '\n' ) {
	         line.append(word);
	         word.delete(0, word.length());
//	         Log.v("wrap", "1 Size: "+linesList.size());
//	         System.out.println("1 Size: "+linesList.size());
	         linesList.add(line.toString().trim());
	         lineCounter++;
	         checkSize++;
	         line.delete(0, line.length());
	      }else if( getStringLength(line.toString()) + getStringLength(word.toString()) > len)
	      {
	          if(line.length() > 0)
	          {
	             i -= word.length();
	             word.delete(0, word.length() );
//	             Log.v("wrap", "2 Size: "+linesList.size());
//	             System.out.println("2 Size: "+linesList.size());
	             linesList.add(line.toString().trim());
	             checkSize++;
	             lineCounter++;
	             line.delete(0, line.length());
	          }else
	          {
	                 word.deleteCharAt(word.length() - 1);
	                 i--;
	                 line.append(word);
	                 word.delete(0, word.length());
//	                 Log.v("wrap", "3 Size: "+linesList.size());
//	                 System.out.println("3 Size: "+linesList.size());
	                 linesList.add(line.toString().trim());
	                 checkSize++;
	                 lineCounter++;
	                 line.delete(0, line.length());
	          }
//	      
	          
	       
//	        
	      }
	   }

	   // handle any extra chars in current word
	   if ( getStringLength(word.toString()) > 0) {
	     if (( getStringLength(line.toString()) +  getStringLength(word.toString())) > len) {
	      
	       linesList.add(line.toString().trim());
	       lineCounter++;
	       line.delete(0, line.length());
	      
	     }
	     line.append(word);
	   }

	   // handle extra line
	   if (line.length() > 0) {
	 	 linesList.add(line.toString().trim());
	      lineCounter++;
	     
	   }

	   String [] ret = new String[lineCounter];
	   for (int j = 0; j < lineCounter; j++) {
	 	ret[j] = linesList.get(j);
	   }
	   linesList.removeAll(linesList);
	   linesList = null;
	   line = null;
	   word = null;
	   return ret;
	 }
   
public String [] wrapText (String text, int len)
{
	
		checkSize = 0;
		return testWrapped(text, len);
	
}
}