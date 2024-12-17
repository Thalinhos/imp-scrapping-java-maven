package org.example;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ParallelPingRange {
    public List<String> runParallelPings() {
        GetAllLocalIps getAllLocalIps = new GetAllLocalIps();
        String subnet = getAllLocalIps.returnIpAddress();
        int start = 2;
        int end = 254;
        int timeout = 5000;
        List<String> arrayIpsAcessiveis = new CopyOnWriteArrayList<>();

        int numThreads = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        for (int i = start; i <= end; i++) {
            final String ipAddress = subnet + i;
            executor.submit(() -> {
                try {
                    InetAddress address = InetAddress.getByName(ipAddress);
                    boolean isReachable = address.isReachable(timeout);

                    if (isReachable) {
                        arrayIpsAcessiveis.add(ipAddress);
                    }

                } catch (UnknownHostException e) {
                    System.err.println("Não foi possível resolver o IP: " + ipAddress);
                } catch (IOException e) {
                    System.err.println("Erro de I/O ao pingar o IP: " + ipAddress);
                }
            });
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.MINUTES)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        return arrayIpsAcessiveis;
    }
}


