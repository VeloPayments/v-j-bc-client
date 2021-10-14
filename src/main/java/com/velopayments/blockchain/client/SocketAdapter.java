package com.velopayments.blockchain.client;

import com.velopayments.blockchain.init.Initializer;
import java.net.Socket;

/**
 * SocketAdapter converts a Java Socket to a native socket wrapper.
 */
public class SocketAdapter {

    private Socket socket;
    private long handle;

    /**
     * Create this SocketAdapter from a Socket.
     */
    private SocketAdapter(Socket s) {
        this.socket = s;
        this.handle = initNative(Initializer.getInstance(), s);
    }

    /**
     * Close this SocketAdapter.
     */
    private void close() {
        closeNative(Initializer.getInstance(), handle);
    }

    /**
     * Construct the SocketAdapter and pass it to a method.
     */
    public static void withAdaptedSocket(Socket s, SocketAdapterMethod method) {
        var adapter = new SocketAdapter(s);

        try {
            method.method(adapter);
        } finally {
            adapter.close();
        }
    }

    /**
     * Initialize the native portion of this adapter.
     */
    private native long initNative(long nativeInst, Socket s);

    /**
     * Close the socket adapter.
     */
    private native void closeNative(long nativeInst, long handle);
}
