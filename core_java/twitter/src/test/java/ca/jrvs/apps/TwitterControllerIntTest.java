package ca.jrvs.apps;

import ca.jrvs.apps.twitter.controller.TwitterController;
import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerIntTest {

    static TwitterController twitterController;

    @BeforeClass
    public static void setUp() throws Exception {
        String CONSUMER_KEY = System.getenv("consumerKey");
        String CONSUMER_SECRET = System.getenv("consumerSecret");
        String ACCESS_TOKEN = System.getenv("accessToken");
        String TOKEN_SECRET = System.getenv("tokenSecret");

        TwitterHttpHelper helper=new TwitterHttpHelper(CONSUMER_KEY,CONSUMER_SECRET,ACCESS_TOKEN,TOKEN_SECRET);
        TwitterDao dao=new TwitterDao(helper);
        TwitterService service=new TwitterService(dao);
        twitterController=new TwitterController(service);
    }

    @Test
    public void postTweet() {
        String hashtag="#abc";
        String text="Tweet for CLI Testing..... "+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        String  args[]=new String[]{"post",text,lat+":"+lon};
        Tweet postTweet= twitterController.postTweet(args);
        assertEquals(text,postTweet.getText());
        assertEquals(lon, postTweet.getCoordinates().getCoordinates().get(0));
        assertEquals(lat, postTweet.getCoordinates().getCoordinates().get(1));
    }

    @Test
    public void deleteTweet() {
        String hashtag="#abc";
        String text="Tweet for CLI Testing..... "+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        String  args[]=new String[]{"post",text,lat+":"+lon};
        Tweet postTweet= twitterController.postTweet(args);
        String newArgs[] = new String[]{"delete", postTweet.getIdString()};
        List<Tweet> deletedTweets= twitterController.deleteTweet(newArgs);
        assertEquals(text, deletedTweets.get(0).getText());
    }

    @Test
    public void showTweet() {
        String hashtag="#abc";
        String text="Tweet for CLI Testing..... "+hashtag+" at "+java.time.LocalTime.now();
        Double lat=1d;
        Double lon=-1d;
        String  args[]=new String[]{"post",text,lat+":"+lon};
        Tweet postTweet= twitterController.postTweet(args);

        String newArgs[] = new String[]{"show", postTweet.getIdString(), text};
        Tweet showTweet = twitterController.showTweet(newArgs);
        assertEquals(postTweet.getText(), showTweet.getText());
    }
}