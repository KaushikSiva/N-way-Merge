import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;
public class NWayMerge {
	/**
	 * This class demonstrates merging K Sorted Streams.Uses FileInput Stream as Test Case
	 * Use 4 textfile namely read1.txt,read2.txt,read3.txt,read4.txt as input(All files are sorted)
	 * The numbers in files are seperated by "," example:1,2,3,4
	 * @author Kaushik Sivakumar 
	 * @version 1
	 */
	public static Integer resultant[];

	public static void main(String[] args) {
		try {
			//main function

			List<InputStream>  list=new LinkedList<InputStream>();//linkedlist of input streams
			InputStream is1=new FileInputStream("read1.txt");
			InputStream is2=new FileInputStream("read2.txt");
			InputStream is3=new FileInputStream("read3.txt");
			InputStream is4=new FileInputStream("read4.txt");
			//adding inputstreams to linkedlist
			list.add(is1);
			list.add(is2);
			list.add(is3);
			list.add(is4);
			OutputStream os=new FileOutputStream("output.txt");
    		try {
				merge(list,os);
			} 
    		//error messages also printed in outputstream
    		catch(NumberFormatException e)
    		{
    			PrintWriter pw = new PrintWriter(os, true); 
    			System.out.println("Unsuccessfull:Incorrect Input: Try each of these methods to rectify you problem" );
    			System.out.println("1.Enter input in correct format with numbers seperated by  commas example:1,20,30,40");
    			System.out.println("2.Your Input should contain values between -2147483648 and 2147483647"+",try correcting inputs out of this range");
    			pw.println("Unsuccessfull:Incorrect Input:Try each of these methods to rectify you problem" );
    			pw.println("1.Enter input in correct format with numbers seperated by  commas example:1,20,30,40");
    			pw.println("2.Your Input should contain values between -2147483648 and 2147483647"+",try correcting inputs out of this range");
    		}
    		
    		catch (Exception e) {//catch exception and print slack trace
				e.printStackTrace();
			}
    	
			finally//close all the input streams
			{
				try
				{
					int count=0;
					ListIterator<InputStream> isd=list.listIterator();
					while(isd.hasNext())
					{
						list.get(count).close();
					    count++;
					    isd.next();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}  
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}
/**
 * This function takes a list of inputstreams,reads the inputstream,stores them in an array,calls the merged funtion 
 * and merges this array with the resultant array ,sorts it completely and stores them as the new resultant array.
 * i.e initially resultant array is 1st sorted input stream.then 2nd sorted input stream is merged with 1st sorted input stream
 * to form a new sorted resultant(consisting of 1st and 2nd streams),which is then merged with 3rd stream,to form a new sorted 
 * resultant of three streams and so on....Finally output is written to OutputStream o using a PrintWriter
 *  @param args list of inputstreams,outputstream
 *   @return nothing.
 *   @exception throws ioexception on wrong input
	 */
	public static void merge(List<InputStream>  inputStreams, OutputStream o) throws IOException
	{
		boolean cont=true;//condition to check if inputstreams are indeed sorted before sorting as a whole
		//using a print writer to write to output stream o
		PrintWriter pw = new PrintWriter(o, true); 
        String outputter="";
		int count=0;
		ListIterator<InputStream> i=inputStreams.listIterator();
		while(i.hasNext())
		{ 
			String builder=new String();
			int c;
			while((c = inputStreams.get(count).read())!= -1)
			{
				builder=builder+(char) c;
			}
			String[] numbers=(builder.split(","));
			Integer srtedarray[] = new Integer[numbers.length];
			for(int iter=0;iter<numbers.length;iter++)
			{
				srtedarray[iter]=Integer.parseInt(numbers[iter].trim());
			}
			for(int it=srtedarray.length-1;it>0;it--)
			{
				if(srtedarray[it]<srtedarray[it-1])
						{
					System.out.println("Unsuccessfull:This program needs sorted inputstreams kindly rectify input");
					pw.println("Unsuccessfull:This program needs sorted inputstreams kindly rectify input");
					cont=false;
						}
			}
			if(cont==false)
			{
				break;
			}
			
			i.next();
			count++;
         //initially resultant is 1st sorted stream
			if(resultant==null)
			{
				resultant=srtedarray;
			}
			//merging new stream with resultant
			else
			{
				resultant=merged(resultant,srtedarray);
			}

		}
		
		if(cont==true)
		{
		//outputstring to be written to output stream
		for(int rs=0;rs<resultant.length;rs++)
		{
			outputter=outputter+resultant[rs].toString();
			if(rs!=(resultant.length-1))
			{
				outputter=outputter+",";
			}
				
		}
	
		pw.println(outputter);//string written to output stream
		System.out.println("Successfull:Output written to file");
		}
		
	}

	/**
	 * This method is to implement merge() of the mergesort where it merges two sorted arrays into a single sorted array.
	 * @param args a[],b[] i.e.The arrays to be merged.
	 * @return MergedArray.
	 */
	public static Integer[] merged(Integer a[], Integer b[]) {
		Integer result[] = new Integer[a.length + b.length];
		int j = a.length - 1;
		int k = b.length - 1;
		int i = j + k + 1;
		while (j >= 0 || k >= 0) {
			if (j >= 0 && k >= 0) {
				if (a[j] >= b[k]) {
					result[i] = a[j];
					--j;
				} else {
					result[i] = b[k];
					--k;
				}
			} else if (j >= 0) {
				result[i] = a[j];
				--j;
			} else {
				result[i] = b[k];
				--k;
			}
			--i;
		}
		return result;
	}
}
