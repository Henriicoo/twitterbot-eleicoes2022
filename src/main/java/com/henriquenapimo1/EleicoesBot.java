package com.henriquenapimo1;

import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.signature.TwitterCredentials;

public class EleicoesBot {

    public static void main(String[] args) {

        TwitterClient twitterClient = new TwitterClient(TwitterCredentials.builder()
                .accessToken(Utils.twtAccess)
                .accessTokenSecret(Utils.twtASecret)
                .apiKey(Utils.twtApi)
                .apiSecretKey(Utils.twtApiSecret)
                .build());

        twitterClient.postTweet("Olá mundo!");
    }
}
