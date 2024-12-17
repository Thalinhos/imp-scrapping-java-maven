package org.example;

import java.util.Arrays;
import java.util.List;

public class App {
    public static void main(String[] args) {
        ParallelPingRange parallel = new ParallelPingRange();
        List<String> ips = parallel.runParallelPings();
        System.out.println("Lista de ips disponiveis: ");
        System.out.println(ips);
        System.out.println("Verificando impressoras na rede...");

        JsoupFetchData jsoupFetchData = new JsoupFetchData();

        for(String ip : ips){
        jsoupFetchData.showImpData(ip);
        }
    }
}
