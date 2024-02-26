package com.sulsul.suldaksuldak.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.ServletWebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@Slf4j
@Component
@RequiredArgsConstructor
public class StartUpEventListener
        implements ApplicationListener<ServletWebServerInitializedEvent>
{
    private Integer myPortNum;
    private String myIpAddress;

    @Override
    public void onApplicationEvent(ServletWebServerInitializedEvent event) {
        ServletWebServerApplicationContext applicationContext = event.getApplicationContext();
        Integer myPort = applicationContext.getWebServer().getPort();
        myPortNum = myPort;
//        String myIpAddress = null;
//
//        try {
//            Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();
//            while (nienum.hasMoreElements()) {
//                NetworkInterface ni = nienum.nextElement();
//                Enumeration<InetAddress> kk= ni.getInetAddresses();
//                while (kk.hasMoreElements()) {
//                    InetAddress inetAddress = kk.nextElement();
//                    if (!inetAddress.isLoopbackAddress() &&
//                            !inetAddress.isLinkLocalAddress() &&
//                            inetAddress.isSiteLocalAddress()
//                    ) {
//                        myIpAddress = inetAddress.getHostAddress().toString();
//                    }
//                }
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//            myIpAddress = null;
//        }
//
//        if (myIpAddress != null) {
//            this.myIpAddress = myIpAddress;
//        }
        log.info("PORT >> {}", this.myPortNum);
    }

    @PostConstruct
    private void findIp() {
        String myIpAddress = null;

        try {
            Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();
            while (nienum.hasMoreElements()) {
                NetworkInterface ni = nienum.nextElement();
                Enumeration<InetAddress> kk= ni.getInetAddresses();
                while (kk.hasMoreElements()) {
                    InetAddress inetAddress = kk.nextElement();
                    if (!inetAddress.isLoopbackAddress() &&
                            !inetAddress.isLinkLocalAddress() &&
                            inetAddress.isSiteLocalAddress()
                    ) {
                        myIpAddress = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            myIpAddress = null;
        }

        if (myIpAddress != null) {
            this.myIpAddress = myIpAddress;
        }
        log.info("IP >> {}", this.myIpAddress);
    }

    public Integer getMyPortNum() {
        return myPortNum;
    }
    public String getMyIpAddress() {
        return myIpAddress;
    }
}
