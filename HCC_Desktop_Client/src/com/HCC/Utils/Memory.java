package com.HCC.Utils;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

import com.HCC.Controller.WebConnect;
import com.HCC.Model.GRAPH;
import com.HCC.Model.Sentence;
import com.HCC.Model.Teacher;

public class Memory {
	String filename="record.txt";
	static final File dir = new File(System.getProperty("user.dir")+"/images");
	// array of supported extensions (use a List if you prefer)
    static final String[] EXTENSIONS = new String[]{
        "gif", "png", "bmp", "jpg" // and other formats you need
    };
    public static String currentuser="currentuser";
    public static String graph="graph";
    public static String sentence="sentence";
    // filter to identify images based on their extensions
    public static void main(String[] args) {
    	List<String> sl=new Memory().getAllSentences();
    	for(String s:sl){
    		System.out.println(s);
    	}
    }
    static final FilenameFilter IMAGE_FILTER = new FilenameFilter() {

        @Override
        public boolean accept(final File dir, final String name) {
            for (final String ext : EXTENSIONS) {
                if (name.endsWith("." + ext)) {
                    return (true);
                }
            }
            return (false);
        }
    };
    public List<String> getAllSentenceNames(){
		List<String> sentence_list=new LinkedList<String>();
		if (dir.isDirectory()) { // make sure it's a directory
			System.out.println("mark");
            for (final File f : dir.listFiles(IMAGE_FILTER)) {
                BufferedImage img = null;
                try {
                    img = ImageIO.read(f);

                    // you probably want something more involved here
                    // to display in your UI
                    sentence_list.add(stripExtension(f.getName()));
                    //System.out.println("image: " + f.getName());
                    //System.out.println(" width : " + img.getWidth());
                    //System.out.println(" height: " + img.getHeight());
                    //System.out.println(" size  : " + f.length());
                } catch (final IOException e) {
                    // handle errors here
                }
            }
        }
		return sentence_list;
	}
    static String stripExtension (String str) {
        // Handle null case specially.

        if (str == null) return null;

        // Get position of last '.'.

        int pos = str.lastIndexOf(".");

        // If there wasn't any '.' just return the string as is.

        if (pos == -1) return str;

        // Otherwise return the string, up to the dot.

        return str.substring(0, pos);
    }

    public List<String> getAllSentences(){
		List<String> sentence_list=new LinkedList<String>();
		if (dir.isDirectory()) { // make sure it's a directory
			System.out.println("mark");
            for (final File f : dir.listFiles(IMAGE_FILTER)) {
                BufferedImage img = null;
                try {
                    img = ImageIO.read(f);

                    // you probably want something more involved here
                    // to display in your UI
                    sentence_list.add(f.getName());
                    //System.out.println("image: " + f.getName());
                    //System.out.println(" width : " + img.getWidth());
                    //System.out.println(" height: " + img.getHeight());
                    //System.out.println(" size  : " + f.length());
                } catch (final IOException e) {
                    // handle errors here
                }
            }
        }
		return sentence_list;
	}
    public static void extractGRAPH(){
		List<GRAPH> gl= null;
	      try
	      {
	    	 File f=new File(Memory.graph);
	    	 if(!f.exists()){
	    		 f.createNewFile();
	    	 }
	         FileInputStream fileIn = new FileInputStream(Memory.graph);
	         if(f.length()==0){
	        	 System.out.println("file null");
	        	 return;
	         }
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         gl = (List<GRAPH>) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	         return;
	      }
	      new GRAPH().GRAPH_list=gl;

	}
	public static void writeGRAPH(){
	      try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(Memory.graph);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(GRAPH.GRAPH_list);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized graph list is saved in "+Memory.graph);
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }

	}
	public static void extractSENTENCE(){
		List<Sentence> gl= null;
	      try
	      {
	    	 File f=new File(Memory.sentence);
	    	 if(!f.exists()){
	    		 f.createNewFile();
	    		 new WebConnect().getAllSentenceId();
	    	 }
	         FileInputStream fileIn = new FileInputStream(Memory.sentence);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         gl = (List<Sentence>) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	         return;
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	         return;
	      }
	      new Sentence().sentences=gl;
	}
	public static void writeSENTENCE(){
	      try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(Memory.sentence);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(Sentence.sentences);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized sentence list is saved in "+Memory.sentence);
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }

	}
	public static void writeTeacher(Teacher t){
		try
	      {
	         FileOutputStream fileOut =
	         new FileOutputStream(Memory.currentuser);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(t);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized current teacher is saved in "+Memory.currentuser);
	      }catch(IOException i)
	      {
	          i.printStackTrace();
	      }
	}
	public static Teacher extractTeacher(){
		Teacher t = null;
	      try
	      {
	         FileInputStream fileIn = new FileInputStream(Memory.currentuser);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         t = (Teacher) in.readObject();
	         in.close();
	         fileIn.close();
	      }catch(IOException i)
	      {
	         i.printStackTrace();
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Employee class not found");
	         c.printStackTrace();
	      }
	    return t;
	}
	public static void refreshGRAPH() {
		// TODO Auto-generated method stub
		new WebConnect().getAllGraphIdandStore();
	}
	public static void refreshSentence() {
		// TODO Auto-generated method stub
		new WebConnect().getAllSentenceId();
	}
}
