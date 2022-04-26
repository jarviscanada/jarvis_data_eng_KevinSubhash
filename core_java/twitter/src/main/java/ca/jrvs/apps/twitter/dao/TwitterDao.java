package ca.jrvs.apps.twitter.dao;
import ca.jrvs.apps.twitter.model.Tweet;
import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

@Repository
public class TwitterDao implements CrdDao<Tweet, String>{
    private static final String API_BASE_URI = "https://api.twitter.com";
    private static final String POST_PATH = "/1.1/statuses/update.json";
    private static final String SHOW_PATH = "/1.1/statuses/show.json";
    private static final String DELETE_PATH = "/1.1/statuses/destroy";

    private static final String QUERY_SYM = "?";
    private static final String AMPERSAND = "&";
    private static final String EQUAL = "=";

    private static final int HTTP_OK = 200;
    private HttpHelper httpHelper;

    @Autowired
    public TwitterDao(HttpHelper httpHelper){
        this.httpHelper = httpHelper;
    }

    @Override
    public Tweet create(Tweet tweet){
        URI uri;
        try{
            uri = getPostUri(tweet);
        } catch (URISyntaxException | UnsupportedEncodingException e){
            throw new IllegalArgumentException("Invalid tweet input", e);
        }
        System.out.println("uri: " + uri.toString());
        HttpResponse response = httpHelper.httpPost(uri);

        return parseResponseBody(response, HTTP_OK);
    }

    private URI getPostUri(Tweet tweet) throws URISyntaxException, UnsupportedEncodingException {
        String text = tweet.getText();
        PercentEscaper percentEscaper = new PercentEscaper("",false);

        String uri_string = API_BASE_URI + POST_PATH + QUERY_SYM + "status" + EQUAL+percentEscaper.escape(text);

        if(tweet.getCoordinates()!=null){
            uri_string = uri_string + AMPERSAND + "long" + EQUAL + tweet.getCoordinates().getCoordinates().get(0)
                    + AMPERSAND + "lat" + EQUAL + tweet.getCoordinates().getCoordinates().get(1);
        }
        return new URI(uri_string);
    }

    public Tweet findById(String id){
        URI uri;

        try{
            uri = new URI(API_BASE_URI + SHOW_PATH + QUERY_SYM + "id=" + id);
        } catch (URISyntaxException e){
            throw new IllegalArgumentException("Invalid id input", e);
        }

        HttpResponse response = httpHelper.httpGet(uri);

        return parseResponseBody(response, HTTP_OK);
    }

    public Tweet deleteById(String id){
        URI uri;

        try{
            uri = new URI(API_BASE_URI + DELETE_PATH + "/" + id + ".json");
        } catch (URISyntaxException e){
            throw new IllegalArgumentException("Invalid id input", e);
        }

        HttpResponse response = httpHelper.httpPost(uri);

        return parseResponseBody(response, HTTP_OK);
    }

    public Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode){
        Tweet tweet = null;

        int status = response.getStatusLine().getStatusCode();
        if(status != expectedStatusCode){
            try{
                System.out.println(EntityUtils.toString(response.getEntity()));
            } catch (IOException e){
                System.out.println("Response has no entity.");
            }
            throw new RuntimeException("Unexpected HTTP status:" + status);
        }

        if (response.getEntity() == null){
            throw new RuntimeException("Empty response body");
        }

        String jsonStr;
        try{
            jsonStr = EntityUtils.toString(response.getEntity());
        } catch (IOException e){
            throw new RuntimeException("Failed to convert entity to String.", e);
        }

        try{
            tweet = JsonUtil.toObjectFromJson(jsonStr, Tweet.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to convert JSON str to Object", e);
        }

        return tweet;

    }
}