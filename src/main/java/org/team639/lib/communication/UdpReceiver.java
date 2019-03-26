package org.team639.lib.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A UDP receiver to be run in a separate thread.
 */
public class UdpReceiver implements Runnable {
    private DatagramSocket socket;

    private final int port;

    private volatile byte[] buf = new byte[0];
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private final int maxLength;

    /**
     * Creates a new UdpReceiver.
     * @param port The port the receiver is listening on.
     * @param maxLength The maximum length packet that can be received.
     * @throws SocketException A possible exception from creating the underlying socket.
     */
    public UdpReceiver(int port, int maxLength) throws SocketException {
        this.port = port;
        this.maxLength = maxLength;

        socket = new DatagramSocket(this.port);
    }

    /**
     * Returns the most recently received buffer.
     * @return The most recently received buffer.
     */
    public byte[] getCurrentBuffer() {
        lock.readLock().lock();
        var b = buf.clone();
        lock.readLock().unlock();
        return b;
    }

    private void setBuf(byte[] buf) {
        lock.writeLock().lock();
        this.buf = buf.clone();
        lock.writeLock().unlock();
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        while (true) {
            DatagramPacket packet = new DatagramPacket(new byte[maxLength], maxLength);
            try {
                socket.receive(packet);
                setBuf(packet.getData());
            } catch (IOException ioe) {
                setBuf(new byte[0]);
            }
        }
    }

    /**
     * Returns the port the receiver is listening on.
     * @return The port the receiver is listening on.
     */
    public int getPort() {
        return port;
    }

    /**
     * Returns the maximum length of the received packets.
     * @return The maximum length of the received packets.
     */
    public int getMaxLength() {
        return maxLength;
    }
}
