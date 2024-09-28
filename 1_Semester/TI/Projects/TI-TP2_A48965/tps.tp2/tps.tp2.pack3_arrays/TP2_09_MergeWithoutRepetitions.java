

import java.io.*;

import java.util.Scanner;

public class TP2_09_MergeWithoutRepetitions{

  public static void main(String[] args) {
    int[] a = {1, 2, 3, 4, 8, 5, 7, 9, 6, 0};
    int[] b = {2,3,4,3,1,5,2,10};
    int[] r= mergeWithoutRepetitions(a,b);
    for (int i=0;i<r.length;i++){
              System.out.println(r[i]);
            }
  }

  public static int[] mergeWithoutRepetitions(int[] a, int[] b) {
    if(a==null && b==null){
      return null;
    }
    int [] c = merge(a,b);
    int [] r = remove(c);

    return r;
}


private static  int[] merge(int[] a, int[] b) {
    int[] c = new int[a.length + b.length];
    int j=b.length;
      for (j=0;j<b.length;j++){
        c[j]=b[j];
      }
      for (int i=0;i<a.length;i++){
      c[j]=a[i];
      j++;
    }
    return c;
}

private static  int[] remove(int[] c) {
    int len=c.length;
    int k=0;
    int[] iguais = new int[len];
    for (int i=0;i<len;i++){
        for (int j=i+1;j<len;j++){
            if (c[i] == c[j]) {
              iguais[k]=c[i];
              k++;
            }
          }
        }

    int x=0;
    int p=0;
    int[] f= new int [len];
    for (int i=0;i<len;i++){
      for (int j=0;j<k;j++){
        if(c[i]!=iguais[j]){
          x++;
        }
        if(x==k){
        f[p]=c[i];
        p++;
            }
        }
        x=0;
      }
      int [] r = new int [p];
      for (int i=0;i<r.length;i++){
        r[i]=f[i];
      }

    return r;
}

}
