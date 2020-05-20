package person.ll.foundation.algorithm;

public class QuickSort {

    public static void main(String[] args) {
        int[] a = {3,1,0,2,8,4,2};
        sort(a);
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
    }
    public static void sort(int[] array){
        sort(array,0,array.length-1);
    }
    private static void sort(int[] array,int left,int right){
        if(left<right){
            int n = array[left];
            int i = left;
            int j = right;

            while (i<j){

                while (i<j){
                    if(array[j]<n){
                        array[i] = array[j];
                        break;
                    }
                    j--;
                }
                while (i<j){
                    if(array[i]>=n){
                        array[j] = array[i];
                        break;
                    }
                    i++;
                }
            }
            array[i] = n;
            sort(array,left,i-1);
            sort(array,i+1,right);

        }
    }
}
