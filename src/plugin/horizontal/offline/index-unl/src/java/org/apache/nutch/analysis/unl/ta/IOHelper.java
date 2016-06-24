package org.apache.nutch.analysis.unl.ta;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author root
 */
public final class IOHelper {

    public static BufferedReader getBufferedReader(String fileName) {
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bufferedReader;
    }

    public static String readLineFromBufferedReader(BufferedReader bufferedReader) {
        String line = null;
        try {
            line = bufferedReader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return line;
    }

    public static ObjectInputStream getObjectInputStream(String serializedObject) {
        ObjectInputStream objectInputStream = null;
        try {
	    if(serializedObject!=null){
            objectInputStream = new ObjectInputStream(new FileInputStream(serializedObject));}
        } catch (Exception ex) {
		ex.printStackTrace();
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objectInputStream;
    }

    public static Object readObjectFromInputStream(ObjectInputStream objectInputStream) {
        Object object = null;
        try {
		if(objectInputStream!=null)
		
            object = objectInputStream.readObject();
        } catch (IOException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return object;
    }

    public static BufferedWriter getBufferedWriter(String fileName) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fileName,true));
        } catch (IOException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bufferedWriter;
    }

    public static void writeLineToBufferedWriter(BufferedWriter bufferedWriter, String line) {
        try {
            bufferedWriter.write(line + "\n");
            bufferedWriter.flush();
        } catch (IOException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeBufferedWriter (BufferedWriter bufferedWriter) {
        try {
            bufferedWriter.close();
        } catch (IOException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeBufferedReader (BufferedReader bufferedReader) {
        try {
            bufferedReader.close();
        } catch (IOException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeObjectInputStream(ObjectInputStream objectInputStream) {
        try {
            objectInputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ObjectOutputStream getObjectOutputStream(String fileName) {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        } catch (IOException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return objectOutputStream;
    }

    public static void writeObjectToOutputStream(ObjectOutputStream objectOutputStream, Object object) {
        try {
            objectOutputStream.writeObject(object);
        } catch (IOException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void closeObjectOutputStream(ObjectOutputStream objectOutputStream) {
        try {
            objectOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(IOHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean isFileEmpty(String fileName) {
        File file = new File(fileName);
        long minimum = 1L;
        if (file.length() > minimum) {
            return false;
        }
        return true;
    }
}
