import java.util.Arrays;
import java.util.Comparator;

public class testing2{

    private static class comparelengths implements Comparator<String>{

        @Override
        public int compare(String arg0, String arg1) {
            // TODO Auto-generated method stub
            return (arg0.length()- arg1.length());
        }
    }

    private static class lastchar implements Comparator<String>{

        @Override
        public int compare(String s1, String s2) {
            // TODO Auto-generated method stub
            return (s1.charAt(s1.length()-1) - s2.charAt(s2.length()-1));
        }
    }

    public static void main(String args[]){
        String arr[] = {"0,37,1" , "1003,1136,4","598,752,2" , "43,354,3"};
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
        System.out.println("\n");
        /*for(int i=0;i<arr.length;i++){
            System.out.println(arr[i]);
        }*/

        // sorts according to length of string  
        Arrays.sort(arr, new comparelengths());
       /* for(int j=0;j<arr.length;j++){
            System.out.println(arr[j]);
        }*/
        System.out.println(Arrays.toString(arr));
        System.out.println("\n");
        
        
        //sorts lexicographically according to last letter  
        Arrays.sort(arr, new lastchar());
      /*  for(int k=0;k<arr.length;k++){
            System.out.println(arr[k]);
        }*/
        System.out.println(Arrays.toString(arr));
        
        
    }
}