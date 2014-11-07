package sol;

import java.io.PrintStream;

public class UTM
{
  public static String NewTape(String paramString1, String paramString2, int paramInt1, int paramInt2)
  {
    int i = 0;

    int j = 0;

    int i1 = paramString2.length();
    String str2 = paramString2.substring(0, paramInt2);

    int i2 = str2.length();
    String str3 = paramString2.substring(paramInt2 + 1);

    int i3 = str3.length();
    for (int i4 = 1; i4 <= paramInt1; i4++) {
      int m = Integer.parseInt(paramString2.substring(paramInt2, paramInt2 + 1));
      int k = j * 16 + m * 8;
      String str1 = paramString1.substring(k, k + 1);
      int n = Integer.parseInt(paramString1.substring(k + 1, k + 2));
      if (n == 0) {
        paramInt2++;
        if (paramInt2 == i1) {
          System.out.println("\nRight limit of tape exceeded");
          return "";
        }
        str2 = str2 + str1;
        i2++;
        paramString2 = str2 + str3;
        i3--;
        str3 = str3.substring(1);
      } else {
        paramInt2--;
        if (paramInt2 < 0) {
          System.out.println("\nLeft limit of tape exceeded");
          return "";
        }
        str3 = str1 + str3;
        i3++;
        paramString2 = str2 + str3;
        i2--;
        str2 = str2.substring(0, i2);
      }
      j = 0;
      for (int i5 = k + 2; i5 < k + 8; i5++) {
        j *= 2;
        if (paramString1.substring(i5, i5 + 1).equals("1")) {
          j++;
        }
      }
      if (j == 63) {
        System.out.println("\n\nHALT state was reached");
        System.out.printf("%10.0f transitions were performed\n", new Object[] { Float.valueOf(i4) });
        long l = 0L;
        for (int i6 = 0; i6 < paramString2.length(); i6++) {
          if (paramString2.substring(i6, i6 + 1).equals("1")) {
            l += 1L;
          }
        }
        System.out.println("The productivity of this machine is " + l);
        return paramString2;
      }
      i++;
      if (i == 10000) {
        System.out.print("\b\b\b\b\b\b\b\b\b\b");
        System.out.print("\t" + i4);
        i = 0;
      }
    }
    System.out.println("\nMaximum number of transitions was reached");
    return paramString2;
  }
}