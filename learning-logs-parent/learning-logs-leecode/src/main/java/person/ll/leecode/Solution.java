package person.ll.leecode;

import java.util.LinkedList;

class Solution {

        public int findRepeatNumber(int[] nums) {
            for (int i = 0; i < nums.length; i++) {
                if(i == nums[i]){
                    continue;
                }
                while (nums[i]!=i){
                    if(nums[nums[i]] == nums[i]){
                        return nums[i];
                    }
                    int temp = nums[nums[i]];
                    nums[nums[i]] = nums[i];
                    nums[i] = temp;
                }
            }
            return  -1;

        }

    public int lengthOfLongestSubstring(String s) {
        int maxLength = 0;
        LinkedList<Character> chars = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char currentChar;
            if(chars.contains(currentChar = s.charAt(i))){
                removeLeft(currentChar,chars);
                chars.add(currentChar);
            }else {
                chars.add(currentChar);
                maxLength = Math.max(chars.size(), maxLength);
            }

        }
        return maxLength;
    }



    private void removeLeft(char currentChar,LinkedList<Character> chars) {
        int i = chars.indexOf(currentChar);
        for (int j = 0;j<=i;j++){
            chars.remove();
        }
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findRepeatNumber(new int[]{6, 3, 1, 0, 2, 5, 6}));
        System.out.println(solution.lengthOfLongestSubstring("pwwkew"));
    }

}