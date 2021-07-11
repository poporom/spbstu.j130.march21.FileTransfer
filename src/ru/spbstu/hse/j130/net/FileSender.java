/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.spbstu.hse.j130.net;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import static ru.spbstu.hse.j130.net.FileReceiver.BUFFER_SIZE;

/**
 *
 * @author rompop
 */
public class FileSender {

    public static final int SERVER_PORT = 34567;
    public static final int BUFFER_SIZE = 4096;

    public static void main(String[] args) throws ClassNotFoundException {

        File file = new File("test.xml");

        try (Socket sock = new Socket(InetAddress.getByName("localhost"), SERVER_PORT);
                ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
                InputStream in = new FileInputStream(file);
                OutputStream out = sock.getOutputStream();) {

            out.write(file.getName().getBytes());

            byte[] bytes = new byte[16 * 1024];

            int count;

            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }

            int n = in.read(bytes);

            System.out.println(new String(bytes, 0, n));

        } catch (IOException e) {
            System.err.println("Error #1: " + e.getMessage());
        }

    }

}
