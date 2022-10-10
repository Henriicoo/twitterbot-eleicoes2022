package com.henriquenapimo1;

import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;

public class EleicoesBot {

    private static TwitterClient twtClient;

    public static void main(String[] args) {

         twtClient = new TwitterClient(TwitterCredentials.builder()
                .accessToken(Utils.twtAccess)
                .accessTokenSecret(Utils.twtASecret)
                .apiKey(Utils.twtApi)
                .apiSecretKey(Utils.twtApiSecret)
                .build());

         new EleicoesTimer();
    }

    public static TwitterClient getTwitter() {
        return twtClient;
    }
}
