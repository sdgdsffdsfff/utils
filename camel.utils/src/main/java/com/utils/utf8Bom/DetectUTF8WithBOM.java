// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DetectUTF8WithBOM.java

package com.utils.utf8Bom;

import java.io.*;
import java.util.Collection;
import org.apache.commons.io.*;

public class DetectUTF8WithBOM extends DirectoryWalker
{

    public static void main(String args[])
        throws IOException
    {
        DetectUTF8WithBOM dub = new DetectUTF8WithBOM("java");
        dub.start(new File("G:/reference/workspace/spring-mybatis/src/main/java"));
//        dub.start(new File("C:/Users/dengqb/Pictures/top"));
        
    }

    public DetectUTF8WithBOM(String extension)
    {
        this.extension = null;
        this.extension = extension;
    }

    public void start(File rootDir)
        throws IOException
    {
        walk(rootDir, null);
    }

    protected void handleFile(File file, int depth, Collection results)
        throws IOException
    {
//    	if(extension == null || !extension.equalsIgnoreCase(FilenameUtils.getExtension(file.toString()))){
//    		deleteFile(file);
//        }
    	//System.out.println("file name="+ file.toString());
    	//if(extension == null || extension.equalsIgnoreCase(FilenameUtils.getExtension(file.toString()))){
    		
    		mark(file);
            //remove(file);
        //}
    }

    private void mark(File file) throws IOException {
        byte bs[] = FileUtils.readFileToByteArray(file);
        if (bs.length == 0){
        	System.out.println("delete zero file: "+ file.getPath());
        	file.delete();
        }else { 
        	if(bs[0] == -17 && bs[1] == -69 && bs[2] == -65){
        		System.out.println((new StringBuilder()).append("Mark BOM: ").append(file).toString());
        		
        		remove(bs, file);	           
	        }
        }
    }

    private void remove(byte[] fileByte, File file)
        throws IOException
    {
    	byte nbs[] = new byte[fileByte.length - 3];
		System.arraycopy(fileByte, 3, nbs, 0, nbs.length);
		FileUtils.writeByteArrayToFile(file, nbs);
		System.out.println((new StringBuilder()).append("Remove BOM: ").append(file).toString());

    }
    
    private void deleteFile(File file){
    	if (file.getName().startsWith(".#")){
    		file.delete();
    	}
    }

    private String extension;
}
