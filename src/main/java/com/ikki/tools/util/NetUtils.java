package com.ikki.tools.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetUtils {

    public static InetAddress localAddress;

    public static byte[] MAC;

    static {
        try {
            localAddress = getLocalInetAddress();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public static InetAddress getLocalInetAddress() throws SocketException {

        Enumeration<NetworkInterface> enu = NetworkInterface.getNetworkInterfaces();

        while (enu.hasMoreElements()) {

            NetworkInterface networkInterface = enu.nextElement();
            System.out.println(networkInterface.getIndex());
            System.out.println(networkInterface.getName());

            if (networkInterface.isLoopback() || networkInterface.isVirtual()) {
                continue;
            }

            Enumeration<InetAddress> addressEnumeration = networkInterface.getInetAddresses();
            MAC = networkInterface.getHardwareAddress();
            while (addressEnumeration.hasMoreElements()) {
                InetAddress address = addressEnumeration.nextElement();
                // ignore 127.0.0.1
                if (address.isLinkLocalAddress()
                        || address.isLoopbackAddress()
                        || address.isAnyLocalAddress()) {
                    continue;
                }
                return address;
            }

        }
        throw new RuntimeException("No validated local address!");

    }


    public static String getLocalAddress() {
        return localAddress.getHostAddress();
    }

    public static byte[] getMAC() {
        return MAC;
    }

}
