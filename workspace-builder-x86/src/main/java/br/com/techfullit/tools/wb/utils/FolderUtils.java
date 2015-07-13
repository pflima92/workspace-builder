/** Copyright - 2015 - Paulo Henrique Ferreira de Lima - TechFull IT Services
Licensed under the Apache License, Version 2.0 (the “License”);
you may not use this file except in compliance with the License.
You may obtain a copy of the License at
http://www.apache.org/licenses/LICENSE-2.0
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an “AS IS” BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. */
package br.com.techfullit.tools.wb.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The Class FolderUtils.
 */
public class FolderUtils {

    /**
	 * Copy file.
	 *
	 * @param source
	 *            the source
	 * @param destination
	 *            the destination
	 * @return true, if successful
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
    public static boolean copyFile(File source, File destination) throws IOException {  
        if (destination.exists())  
            destination.delete();  
     
        FileChannel sourceChannel = null;  
        FileChannel destinationChannel = null;  
     
        try {  
            sourceChannel = new FileInputStream(source).getChannel();  
            destinationChannel = new FileOutputStream(destination).getChannel();  
            sourceChannel.transferTo(0, sourceChannel.size(),  
                    destinationChannel);  
        } finally {  
            if (sourceChannel != null && sourceChannel.isOpen())  
                sourceChannel.close();  
            if (destinationChannel != null && destinationChannel.isOpen())  
                destinationChannel.close();  
       }
		return true;  
   }

    /**
     * Delete dir.
     *
     * @param dir the dir
     * @return true, if successful
     */
    public static boolean deleteDir(File dir) {
	if (dir.isDirectory()) {
	    String[] children = dir.list();
	    for (int i = 0; i < children.length; i++) {
		boolean success = deleteDir(new File(dir, children[i]));
		if (!success) {
		    return false;
		}
	    }
	}
	return dir.delete();
    }

    /**
     * Generate file name.
     *
     * @param extension the extension
     * @return the string
     */
    public static String generateFileName(String extension) {
	Calendar c = new GregorianCalendar();
	String name = "BCKP-" + c.get(Calendar.DATE) + c.get(Calendar.MONTH) + c.get(Calendar.YEAR) + +c.get(Calendar.HOUR)
		+ c.get(Calendar.MILLISECOND);
	return name + "." + extension;
    }

    /**
     * Gets the files folder.
     *
     * @param path the path
     * @return the files folder
     */
    public static File[] getFilesFolder(String path) {
	File file = new File(path);
	return file.listFiles();
    }

    /**
     * Gets the folder size.
     *
     * @param path the path
     * @return the folder size
     */
    public static int getFolderSize(String path) {
	File folder = new File(path);
	int size = 0;
	if (folder.isDirectory()) {
	    String[] dirList = folder.list();
	    if (dirList != null) {
		for (int i = 0; i < dirList.length; i++) {
		    String fileName = dirList[i];
		    File f = new File(path, fileName);
		    if (f.isDirectory()) {
			String filePath = f.getPath();
			size += getFolderSize(filePath);
			continue;
		    }
		    size += f.length();
		}
	    }
	}
	return size;
    }
    
    /**
     * Verify folder.
     *
     * @param files the files
     */
    public void verifyFolder(File[] files) {
	System.out.println("Deletando conteudo que nao faz parte do deploy...");
	for (File file : files) {
	    if (!file.getAbsolutePath().contains(".ear")) {
		System.out.println("Deletando " + file.getAbsolutePath());
		if (file.delete()) {
		    System.out.println("Arquivo deletado com sucesso.");
		} else {
		    System.out.println("Arquivo n�o delatado...");
		}
	    }
	}
	System.out.println("Fim do processo de verifica��o do diretorio");
    }  

}
