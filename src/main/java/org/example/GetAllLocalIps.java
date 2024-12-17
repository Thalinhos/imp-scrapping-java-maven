package org.example;

import java.net.NetworkInterface;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Enumeration;

public class GetAllLocalIps {

    public String returnIpAddress() {
        StringBuilder ipValue = new StringBuilder();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();

                    if (inetAddress.getAddress().length == 4) {
                        String ipString = inetAddress.getHostAddress();



                        String[] ipArray = ipString.split("\\.");

                        int lastValue = Integer.parseInt(ipArray[ipArray.length - 1]);

                        if(lastValue > 2){
                            System.out.println("Verificando seu ip: " + ipString);
                            int count = 0;
                            for(String i : ipArray){
                                if(count == 3){
                                    break;
                                }
                                count += 1;
                                assert false;
                                ipValue.append(i).append(".");
                            }
                           return ipValue.toString();
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println("Unable to retrieve network interfaces.");
        }
        return "";
    }
}

