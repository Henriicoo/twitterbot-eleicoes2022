package com.henriquenapimo1;

import io.github.redouane59.twitter.TwitterClient;
import io.github.redouane59.twitter.dto.tweet.MediaCategory;
import io.github.redouane59.twitter.dto.tweet.Tweet;
import io.github.redouane59.twitter.dto.tweet.TweetParameters;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TweetManager {

    public static void postThread(String content, String... imageName) {
        TwitterClient twitterClient = EleicoesBot.getTwitter();

        List<String> mediaIds = new ArrayList<>();
        for (String s : imageName) {
            mediaIds.add(twitterClient.uploadMedia(new File("src/main/resources/" + s), MediaCategory.TWEET_IMAGE).getMediaId());
        }

        TweetParameters parameters = TweetParameters.builder()
                .text(content)
                .media(TweetParameters.Media.builder()
                        .mediaIds(Collections.singletonList(mediaIds.get(0)))
                        .build())
                .build();

        Tweet tweet = twitterClient.postTweet(parameters);
        mediaIds.remove(0);

        twitterClient.postTweet(TweetParameters.builder()
                        .reply(TweetParameters.Reply.builder().inReplyToTweetId(tweet.getId()).build())
                .text("APURAÇÃO POR ESTADOS #Eleições2022")
                        .media(TweetParameters.Media.builder()
                                .mediaIds(mediaIds)
                                .build())
                .build()
        );
    }
}
