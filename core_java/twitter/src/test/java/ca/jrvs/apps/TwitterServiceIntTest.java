package ca.jrvs.apps;

import ca.jrvs.apps.twitter.dao.HttpHelper;
import ca.jrvs.apps.twitter.dao.TweetUtil;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TwitterServiceIntTest {

    private TwitterService twitterService;


    public void setup(){
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);

        TwitterHttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        TwitterDao tdao = new TwitterDao(httpHelper);
        twitterService = new TwitterService(tdao);
    }

    @Test
    public void postTweet() {
        String hashtag="#abc";
        String text="Tweet for CLI Testing..... "+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        Tweet postTweet = TweetUtil.buildTweet(text,lon,lat);
        Tweet response = twitterService.postTweet(postTweet);
        assertEquals(postTweet.getText(),response.getText());
        assertEquals(postTweet.getCoordinates().getCoordinates(), response.getCoordinates().getCoordinates());
    }

    @Test
    public void showTweet() {
        String hashtag="#abc";
        String text="Tweet for CLI Testing..... "+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        Tweet postTweet= TweetUtil.buildTweet(text,lon,lat);
        Tweet response = twitterService.postTweet(postTweet);
        Tweet getTweet = twitterService.showTweet(response.getIdString() ,new String[]{"text"});
        assertEquals(text,getTweet.getText());

    }

    @Test
    public void deleteTweets() {
        String hashtag="#Coding";
        String text="Tweet for CLI Testing..... "+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        Tweet postTweet= TweetUtil.buildTweet(text,lon,lat);
        Tweet response = twitterService.postTweet(postTweet);
        List<Tweet> deletedTweets =twitterService.deleteTweets(new String[]{response.getIdString()});
        assertEquals(deletedTweets.get(0).getText(),response.getText());

    }
}