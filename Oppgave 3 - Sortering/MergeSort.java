import java.util.ArrayList;
public class MergeSort {
    ArrayList<Integer> ar;

    static ArrayList<Integer> Merge(ArrayList<Integer> A1, ArrayList<Integer> A2){
        int i = 0;
        int j = 0;
        ArrayList<Integer> A = new ArrayList<Integer>();
        
        while (i < A1.size() && j < A2.size()){
            if (A1.get(i) <= A2.get(j)){
                A.add(i+j, A1.get(i));
                i++;
            } 
            else{
                A.add(i+j, A2.get(j));
                j++;
            }
        }
        while (i < A1.size()){
            A.add(i+j, A1.get(i));
            i++;
        }

        while (j < A2.size()){
            A.add(i+j, A2.get(j));
            j++;
        }

        return A;
    }

    static ArrayList<Integer> mSort(ArrayList<Integer> A){
        if (A.size() <= 1){
            return A;
        }
        int i = A.size() / 2;
        ArrayList<Integer> firstHalf = new ArrayList<>(A.subList(0, i));
        ArrayList<Integer> secondHalf = new ArrayList<>(A.subList(i, A.size()));

        ArrayList<Integer> A1 = mSort(firstHalf);
        ArrayList<Integer> A2 = mSort(secondHalf);
        return Merge(A1, A2);
    }
    

    ArrayList<Integer> MergeSort(ArrayList<Integer> a){
        ar = mSort(a);
        return ar;
    }
}
