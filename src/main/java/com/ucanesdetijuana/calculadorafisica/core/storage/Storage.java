package com.ucanesdetijuana.calculadorafisica.core.storage;

import com.ucanesdetijuana.calculadorafisica.core.logic.Ecuation;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Guarda y obtiene datos de los objetos almacenados en la memoria del sistema.
 * @author José Alfredo Interián Polanco
 */
public class Storage {
    public static void write(Ecuation ec, File file) {
        if (!file.getName().toLowerCase().endsWith(".equ")) {
            file = new File(file.getParentFile(), file.getName() + ".equ");
        }

        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }

        try (FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(ec);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Ecuation read(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            System.err.println("El archivo no existe o no es válido: " + (file != null ? file.getAbsolutePath() : "null"));
            return null;
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            Ecuation ec = (Ecuation) ois.readObject();
            return ec;

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al leer la ecuación desde el archivo.");
            e.printStackTrace();
            return null;
        }
    }
}
