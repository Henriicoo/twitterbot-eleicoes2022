package com.henriquenapimo1;

import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class EleicoesBot {

    private static TwitterClient twtClient;

    public static void main(String[] args) {

         twtClient = new TwitterClient(TwitterCredentials.builder()
                .accessToken(Utils.twtAccess)
                .accessTokenSecret(Utils.twtASecret)
                .apiKey(Utils.twtApi)
                .apiSecretKey(Utils.twtApiSecret)
                .build());

        Timer timer = new Timer();

        timer.schedule(new TimerTask() {
            public void run() {
                try {
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
}
