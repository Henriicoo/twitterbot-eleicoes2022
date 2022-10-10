package com.henriquenapimo1;

import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.tweet.MediaCategory;
import io.github.redouane59.twitter.dto.tweet.TweetParameters;
import io.github.redouane59.twitter.dto.tweet.UploadMediaResponse;

import java.io.File;
import java.util.Collections;

public class TweetManager {

    public static void postTweetWithImage(String content, String imageName) {
        TwitterClient twitterClient = EleicoesBot.getTwitter();

        UploadMediaResponse response = twitterClient.uploadMedia(new File("src/main/resources/"+imageName), MediaCategory.TWEET_IMAGE);

        TweetParameters parameters = TweetParameters.builder()
                .text(content)
                .media(TweetParameters.Media.builder()
                        .mediaIds(Collections.singletonList(response.getMediaId()))
                        .build())
                .build();

        twitterClient.postTweet(parameters);
    }
}
