package com.henriquenapimo1;

import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class EleicoesBot {

    private static TwitterClient twtClient;
    private static Timer timer;

    public static void main(String[] args) {

        System.out.println("Iniciando o bot!\nIniciando a API do Twitter...");

         twtClient = new TwitterClient(TwitterCredentials.builder()
                .accessToken(Utils.twtAccess)
                .accessTokenSecret(Utils.twtASecret)
                .apiKey(Utils.twtApi)
                .apiSecretKey(Utils.twtApiSecret)
                .build());

        System.out.println("API iniciada com sucesso!\nIniciando timer das postagens...");

        timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    System.out.println("APURAÇÃO INICIADA!\nApurando votos...");
                    new DadosTSE().apurar();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 60*5*1000);
    }

    public static TwitterClient getTwitter() {
        return twtClient;
    }

    public static void cancelTimer() {
        System.out.println("AVISO: Timer cancelado!");
        timer.cancel();
    }
}
