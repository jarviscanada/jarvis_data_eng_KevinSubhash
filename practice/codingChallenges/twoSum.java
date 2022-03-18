package ca.jrvs.practice.codingChallenges;
import java.util.HashMap;
import java.util.Map;
public class TwoSum{
    public int[] twoSum(int[] numbers, int target) {
        HashMap<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < nums.length; ++i) {
            int b = nums[i], a = target - b;
            if (seen.containsKey(a)){
                return new int[]{seen.get(a), i};
            }
            seen.put(b, i);
        }
        return new int[]{};
    }
}