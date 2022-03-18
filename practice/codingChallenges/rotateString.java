package ca.jrvs.practice.codingChallenges;

public class RotateString{
    public boolean rotateString(String s, String goal) {
        String newString = s + s;
        return newString.contains(goal);
    }
}