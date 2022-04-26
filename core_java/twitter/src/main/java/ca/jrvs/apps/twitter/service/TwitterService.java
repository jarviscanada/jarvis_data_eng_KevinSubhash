package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@org.springframework.stereotype.Service
public class TwitterService implements Service{

    private CrdDao dao;
    private ArrayList<String> ALL_FIELDS = new ArrayList<>(Arrays.asList("created_at", "id",
            "id_str", "text", "entities", "coordinates", "retweet_count", "favorite_count", "favorited", "retweeted"));

    @Autowired
    public TwitterService(CrdDao dao){
        this.dao = dao;
    }

    public Tweet postTweet(Tweet tweet){
        validatePostTweet(tweet);
        return (Tweet) dao.create(tweet);
    }

    public Tweet showTweet(String id, String[] fields){
        validateTweetId(id);
        Field fieldList[] = Tweet.class.getDeclaredFields();

        for (String f : fields){
            if(!ALL_FIELDS.contains(f)){
                throw new IllegalArgumentException("Invalid Field.");
            }
        }

        return (Tweet) dao.findById(id);

    }

    public List<Tweet> deleteTweets(String[] ids){
        List<Tweet> list = new ArrayList<Tweet>();
        for(String id : ids){
            if(validateTweetId(id)){
                list.add((Tweet) dao.deleteById(id));
            }
        }
        return list;
    }

    public boolean validatePostTweet(Tweet tweet){
        String msg = tweet.getText();
        Coordinates cords = tweet.getCoordinates();

        if (msg.length() <= 0 && msg.length() > 140){
            return false;
        } else if (cords.getCoordinates().get(0) > 180 || cords.getCoordinates().get(0) < -180){
            return false;
        } else if (cords.getCoordinates().get(1) > 90 || cords.getCoordinates().get(1) < -90){
            return false;
        } else {
            return true;
        }
    }

    public boolean validateTweetId(String id){
        try{
            return Long.parseLong(id) > 0;
        } catch (NumberFormatException n){
            return false;
        }
    }
}