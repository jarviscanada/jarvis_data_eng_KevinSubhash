package ca.jrvs.practice.codingChallenges;

public class OnlyDigits{
    public boolean onlyDigits(String s){
        for (char c : s.toCharArray()){
            if (!Character.isDigit()){
                return false;
            }
        }
        return true;
    }
}