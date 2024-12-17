package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import javax.net.ssl.*;
import java.io.IOException;
import java.security.cert.X509Certificate;

public class JsoupFetchData{

    public void showImpData(String ip) {

        String PAGE_SERIAL_NUMBER = "/hp/device/InternalPages/Index?id=UsagePage";
        String serialValue = "UsagePage.DeviceInformation.DeviceSerialNumber";
        String monoId = "UsagePage.EquivalentImpressionsTable.Monochrome.Total";
        String colorId = "UsagePage.EquivalentImpressionsTable.Color.Total";
        String totalColorId = "UsagePage.EquivalentImpressionsTable.Total.Total";
        String TotalNoneColorId = "UsagePage.EquivalentImpressionsTable.Total.Total";

        String serialNumberValue = null;
        String monoValue = null;
        String colorValue = null;
        String totalWithColorValue = null;
        String totalNoneColorValue = null;

        String PAGE_COLORS_VALUES = "/hp/device/InternalPages/Index?id=SuppliesStatus";
        String yellowId = "YellowCartridge1-PagesPrintedWithSupply";
        String magentaId = "MagentaCartridge1-PagesPrintedWithSupply";
        String cianoId = "CyanCartridge1-PagesPrintedWithSupply";
        String blackId = "BlackCartridge1-PagesPrintedWithSupply";


        String yellowValue = null;
        String magentaValue = null;
        String cianoValue = null;
        String blackValue = null;




        try {
            trustAllCertificates();

            //String ip = "10.244.37.70";
            String urlSerial = "https://" + ip + PAGE_SERIAL_NUMBER;
            String urlColor = "https://" + ip + PAGE_COLORS_VALUES;

            Element serialNumber;
            Document docSerial;


            try {
                docSerial = Jsoup.connect(urlSerial).get();
                serialNumber = docSerial.getElementById(serialValue);

            } catch (IOException e) {
                return;
            }


            if (serialNumber == null) {
                System.out.println("Serial não encontrado para " + urlSerial);
                return;
            } else {
                serialNumberValue = serialNumber.text().trim();
            }

            InfoImp infoImp = new InfoImp();
            infoImp.setIp(ip);
            infoImp.setSerial(serialNumberValue);

            Element monoValueEl = docSerial.getElementById(monoId);
            Element colorValueEl = docSerial.getElementById(colorId);
            Element totalValueEl = docSerial.getElementById(totalColorId);
            Element totalNoneColorEl = docSerial.getElementById(TotalNoneColorId);

            if (monoValueEl != null && colorValueEl != null && totalValueEl != null) {
                monoValue = monoValueEl.text().trim();
                colorValue = colorValueEl.text().trim();
                totalWithColorValue = totalValueEl.text().trim();

                infoImp.setMono(infoImp.transformToInt(monoValue));
                infoImp.setColor(infoImp.transformToInt(colorValue));
                infoImp.setTotal(infoImp.transformToInt(totalWithColorValue));
            }

            if (totalNoneColorEl != null) {
                totalNoneColorValue = totalNoneColorEl.text().trim();
                infoImp.setTotal(infoImp.transformToInt(totalNoneColorValue));
            }

            Document docColor = Jsoup.connect(urlColor).get();
            Element yellowColor = docColor.getElementById(yellowId);
            Element magentaColor = docColor.getElementById(magentaId);
            Element cianoColor = docColor.getElementById(cianoId);
            Element blackColor = docColor.getElementById(blackId);

            if (yellowColor != null && magentaColor != null && cianoColor != null) {
                yellowValue = yellowColor.text().trim();
                magentaValue = magentaColor.text().trim();
                cianoValue = cianoColor.text().trim();

                infoImp.setY(infoImp.transformToInt(yellowValue));
                infoImp.setM(infoImp.transformToInt(magentaValue));
                infoImp.setC(infoImp.transformToInt(cianoValue));
            }

            if (blackColor != null) {
                blackValue = blackColor.text().trim();
                infoImp.setK(infoImp.transformToInt(blackValue));
            }

            System.out.println(infoImp.toString());


        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void trustAllCertificates() throws Exception {
        TrustManager[] trustAll = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                    }
                }
        };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAll, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }
}
