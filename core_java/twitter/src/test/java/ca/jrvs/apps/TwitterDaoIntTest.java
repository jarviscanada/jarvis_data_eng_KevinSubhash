package ca.jrvs.apps;

import ca.jrvs.apps.twitter.dao.*;
import ca.jrvs.apps.twitter.model.Tweet;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import ca.jrvs.apps.twitter.model.Tweet;
import static org.junit.Assert.*;

public class TwitterDaoIntTest {
    private TwitterDao dao;
    private static String id;
    private static String text;

    @Before
    public void setup(){
        String consumerKey = System.getenv("consumerKey");
        String consumerSecret = System.getenv("consumerSecret");
        String accessToken = System.getenv("accessToken");
        String tokenSecret = System.getenv("tokenSecret");
        System.out.println(consumerKey + "|" + consumerSecret + "|" + accessToken + "|" + tokenSecret);

        HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
        this.dao = new TwitterDao(httpHelper);
    }

    @Test
    public void create() throws Exception{
        String hashTag = "#abc";
        text = "@someone sometext " + hashTag + " " + System.currentTimeMillis();
        Double lat = 1d;
        Double lon = -1d;
        Tweet postTweet = TweetUtil.buildTweet(text, lon, lat);
        System.out.println(JsonUtil.toPrettyJson(postTweet));

        Tweet tweet = dao.create(postTweet);
        TwitterDaoIntTest.id = tweet.getIdString();

        assertEquals(text, tweet.getText());
        System.out.println("TWEET: \n" + JsonUtil.toPrettyJson(tweet));
        assertNotNull(tweet.getCoordinates());
//        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
//        assertEquals(lon, tweet.getCoordinates().getCoordinates().get(0));
//        assertEquals(lat, tweet.getCoordinates().getCoordinates().get(1));
//        assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));

    }

    @Test
    public void findById() throws JsonProcessingException {
        Tweet tweet = dao.findById(TwitterDaoIntTest.id);
        System.out.println(JsonUtil.toPrettyJson(tweet));
        Double expectedLong = -1d;
        Double expectedLat = 1d;
        assertEquals(TwitterDaoIntTest.text, tweet.getText());

        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(expectedLong, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(expectedLat, tweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void deleteById() throws JsonProcessingException {
        Tweet tweet = dao.deleteById(TwitterDaoIntTest.id);
        System.out.println(JsonUtil.toPrettyJson(tweet));
        Double expectedLong = -1d;
        Double expectedLat = 1d;
        assertEquals(TwitterDaoIntTest.text, tweet.getText());
        assertNotNull(tweet.getCoordinates());
        assertEquals(2, tweet.getCoordinates().getCoordinates().size());
        assertEquals(expectedLong, tweet.getCoordinates().getCoordinates().get(0));
        assertEquals(expectedLat, tweet.getCoordinates().getCoordinates().get(1));
    }
}