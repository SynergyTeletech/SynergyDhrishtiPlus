package com.synergy.synergydhrishtiplus;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

public class Test{
    private static byte[] data = new byte[255];

    public void main(ArrayList<Integer> port ) throws IOException {
        Selector selector = Selector.open();
        int[] ports = {54306,54307,5309};

        ServerSocketChannel server ;


             server = ServerSocketChannel.open();
             server.configureBlocking(false);
             for(int porte:ports) {
                 server.socket().bind(new InetSocketAddress(porte));
             }

             server.register(selector, SelectionKey.OP_ACCEPT);


        while (true) {
            selector.select();
            Set readyKeys = selector.selectedKeys();
            Iterator iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();
                if (key.isAcceptable()) {
                    SocketChannel client = server.accept();
                    System.out.println("Accepted connection from " + client);
                    client.configureBlocking(false);
                    ByteBuffer source = ByteBuffer.wrap(data);
                    SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);
                    key2.attach(source);
                } else if (key.isWritable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer output = (ByteBuffer) key.attachment();
                    if (!output.hasRemaining()) {
                        output.rewind();
                    }
                    client.write(output);
                }
                key.channel().close();
            }
        }
    }
}





//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.SelectionKey;
//import java.nio.channels.Selector;
//import java.nio.channels.ServerSocketChannel;
//import java.nio.channels.SocketChannel;
//import java.nio.channels.spi.SelectorProvider;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//public class NioServer implements Runnable {
//    // The host:port combination to listen on
//    private InetAddress hostAddress;
//    private int port;
//
//    // The channel on which we'll accept connections
//    private ServerSocketChannel serverChannel;
//
//    // The selector we'll be monitoring
//    private Selector selector;
//
//    // The buffer into which we'll read data when it's available;
//    private ByteBuffer readBuffer = ByteBuffer.allocate(8192);
//
//    // The worker handling the read data
//    private DataQueueWorker dataQueueWorker;
//
//    // The worker interpreting the data
//    private DataInterpreterWorker dataInterpreterWorker;
//
//    // A list of the ChangeRequest instances
//    private List<ChangeRequest> changeRequests = new LinkedList<ChangeRequest>();
//
//    // Maps a SocketChannel to a list of ByteBuffer instances
//    private Map<SocketChannel, List<ByteBuffer>> pendingData = new HashMap<SocketChannel, List<ByteBuffer>>();
//
//    public NioServer(InetAddress hostAddress, int port, DataQueueWorker dataQueueWorker, DataInterpreterWorker dataInterpreterWorker) throws IOException {
//        this.hostAddress = hostAddress;
//        this.port = port;
//        this.selector = this.initSelector();
//        this.dataQueueWorker = dataQueueWorker;
//        this.dataInterpreterWorker = dataInterpreterWorker;
//    }
//
//    private Selector initSelector() throws IOException {
//        // Create a new selector
//        Selector socketSelector = SelectorProvider.provider().openSelector();
//
//        // Create a new non-blocking server socket channel
//        this.serverChannel = ServerSocketChannel.open();
//        serverChannel.configureBlocking(false);
//
//        // Bind the server socket to the specified address and port
//        InetSocketAddress isa = new InetSocketAddress(this.hostAddress, this.port);
//        serverChannel.socket().bind(isa);
//
//        //Register the server socket channel, indicating an interest in accepting new connections
//        serverChannel.register(socketSelector, SelectionKey.OP_ACCEPT);
//
//        return socketSelector;
//    }
//
//    private void accept(SelectionKey key) throws IOException {
//        // For an accept to be pending the channel must be a server socket channel.
//        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
//
//        // Accept the connection and make it non-blocking
//        SocketChannel socketChannel = serverSocketChannel.accept();
//        socketChannel.configureBlocking(false);
//
//        // Register the new SocketChannel with our Selector, indicating we'd like to be notified when there's data waiting to be read
//        socketChannel.register(this.selector, SelectionKey.OP_READ);
//    }
//
//    private void read(SelectionKey key) throws IOException {
//        SocketChannel socketChannel = (SocketChannel) key.channel();
//
//        // clear out our read buffer so it's ready for new data
//        this.readBuffer.clear();
//
//        // Attempt to read off the channel
//        int numRead;
//        try {
//            numRead = socketChannel.read(this.readBuffer);
//        } catch(IOException e) {
//            // The remote forcibly closed the connection, cancel the selection key and close the channel.
//            socketChannel.close();
//            key.cancel();
//            return;
//        }
//
//        if(numRead == -1) {
//            //Remote entity shut the socket down cleanly.  do the same for our end and cancel the channel.
//            socketChannel.close();
//            key.cancel();
//            return;
//        }
//
//        // Hand the data off to our worker thread
//        this.dataQueueWorker.processData(this, socketChannel, this.dataInterpreterWorker, this.readBuffer.array(), numRead);
//    }
//
//    public void send(SocketChannel socket, byte[] data) {
//        synchronized(this.changeRequests) {
//            System.out.println("check6");
//            System.out.println(new String(data));
//            System.out.println("check7");
//            // Indicate we want the interest ops set changed
//            this.changeRequests.add(new ChangeRequest(socket, ChangeRequest.CHANGEOPS, SelectionKey.OP_WRITE));
//
//            // And queue the data we want written
//            synchronized (this.pendingData) {
//                List<ByteBuffer> queue = (List<ByteBuffer>) this.pendingData.get(socket);
//                if(queue == null) {
//                    queue = new ArrayList<ByteBuffer>();
//                    this.pendingData.put(socket, queue);
//                }
//                queue.add(ByteBuffer.wrap(data));
//            }
//        }
//
//        // Finally, wake up our selecting thread so it can make the required changes
//        this.selector.wakeup();
//    }
//
//    public void write(SelectionKey key) throws IOException {
//        SocketChannel socketChannel = (SocketChannel) key.channel();
//
//        synchronized(this.pendingData) {
//            List<ByteBuffer> queue = (List<ByteBuffer>) this.pendingData.get(socketChannel);
//
//            // Write until there no more data ...
//            while (!queue.isEmpty()) {
//                ByteBuffer buf = (ByteBuffer) queue.get(0);
//                System.out.println(buf.remaining());
//                byte[] byteArr = new byte[buf.remaining()];
//                buf.get(byteArr);
//                System.out.println(new String(byteArr));
//                System.out.println(socketChannel.socket().toString());
//                System.out.println("check8");
//                buf.flip();
//
//                System.out.println(socketChannel.write(buf));
//                if(buf.remaining() > 0) {
//                    // ... or the socket's buffer fills up
//                    break;
//                }
//                queue.remove(0);
//            }
//
//            if(queue.isEmpty()) {
//                System.out.println("check9");
//                //We wrote away all data, so we're no longer interested in writing on this socket.  Switch back to waiting for data.
//                key.interestOps(SelectionKey.OP_READ);
//            }
//        }
//    }
//
//    public void run() {
//        while(true) {
//            try {
//                // Process any pending changes
//                synchronized(this.changeRequests) {
//                    Iterator<ChangeRequest> changes = this.changeRequests.iterator();
//                    while(changes.hasNext()) {
//                        ChangeRequest change = (ChangeRequest) changes.next();
//                        switch(change.type) {
//                            case ChangeRequest.CHANGEOPS:
//                                SelectionKey key = change.socket.keyFor(this.selector);
//                                key.interestOps(change.ops);
//                        }
//                    }
//                    this.changeRequests.clear();
//                }
//
//                // Wait for an event on one of the registered channels
//                this.selector.select();
//
//                // Iterate over the set of keys for which events are available
//                Iterator<SelectionKey> selectedKeys = this.selector.selectedKeys().iterator();
//                while(selectedKeys.hasNext()) {
//                    SelectionKey key = (SelectionKey) selectedKeys.next();
//                    selectedKeys.remove();
//
//                    if(!key.isValid()) {
//                        continue;
//                    }
//
//                    // Check what event is available and deal with it
//                    if(key.isAcceptable()) {
//                        this.accept(key);
//                    } else if(key.isReadable()) {
//                        this.read(key);
//                    } else if(key.isWritable()) {
//                        this.write(key);
//                    }
//                }
//            } catch(IOException e) {
//                e.printStackTrace();
//            } catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        try {
//            DataQueueWorker dataQueueWorker = new DataQueueWorker();
//            new Thread(dataQueueWorker).start();
//            DataInterpreterWorker dataInterpreterWorker = new DataInterpreterWorker();
//            new Thread(dataInterpreterWorker).start();
//            InetAddress host = InetAddress.getByName("192.168.1.20");
//            new Thread(new NioServer(host, 9999, dataQueueWorker, dataInterpreterWorker)).start();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
