import java.io.*;
class TheGrid {
  private static InputStreamReader isr = new InputStreamReader(System.in);
  private static BufferedReader br = new BufferedReader(isr);
  private static String name = "";
  private static int length = 0;
  private static int d[] = new int[2];
  public void main() throws IOException {
    String S = "";
    System.out.println(">>>>>>>>>>>>>>>>>>>>>      W E L C O M E      <<<<<<<<<<<<<<<<<<<<<");
    System.out.println(">>>>>>>>>>>>>>>>>>>>>           To            <<<<<<<<<<<<<<<<<<<<<");
    System.out.println(">>>>>>>>>>>>>>>>>>>>>   T  H  E   G  R  I  D  <<<<<<<<<<<<<<<<<<<<<");
    System.out.print("\nEnter your name : ");
    name = br.readLine();
    if (name.equalsIgnoreCase("sukhi") || name.equalsIgnoreCase("sukhmeet") || name.equalsIgnoreCase("sukhmeet singh dev") || name.equalsIgnoreCase("ssd") || name.equalsIgnoreCase("sukhmeet singh")) {
      System.out.println("\nA very special welcome to you sir. Welcome to your own production. I hope I will not disappoint you. Happy visit Sir !!\n");
      name = "sir";
    }

    System.out.println("\nThank you.\n");
    do {
      System.out.println("Enter how big the grid should be(<100 allowed). This decides the level of difficulty");
      length = Integer.parseInt(br.readLine());
      if (length > 100) {
        System.out.println("\nOnly less than hundred allowed. Next time read everything carefully.\n");
        System.out.println("Do you want to try again?(Y/N)");
        S = br.readLine();
        if (S.equalsIgnoreCase("y")) {
          String s = "";
          do {
            System.out.println("\nOK. Be careful this time !!                            Press Enter\n");
            s = br.readLine();
          }

          while (s.equals("") == false);
        } else byeMsg();
      } else {
        System.out.println("\nGetting Dimensions....");
        d = getDimensions(length);
        int a[][] = new int[d[0]][d[1]];
        System.out.println("Got Dimensions....");
        System.out.println("Filling and generating the grid....");
        a = fill();
        generateGrid(a);
        System.out.println("Grid filled and generated....\n");
        play(a);
      }

      System.out.println("Do you want to continue?(Y/N)");
      S = br.readLine();
      if (S.equalsIgnoreCase("n")) byeMsg();
    }

    while (S.equalsIgnoreCase("y"));
  }

  int[] getDimensions(int length) {
    int n = length;
    int ctr = 0, ab[] = new int[2];
    if (isPerfectSquare(n)) {
      ab[0] = ab[1] = (int) Math.sqrt(n);
      return ab;
    } else {
      n = n % 2 == 0 ? n : n + 1;
      length = n;
      for (int i = 1; i <= n / 2; i++)
        if (n % i == 0) ctr++;
      int aa[] = new int[ctr];
      int k = 0;
      for (int i = 1; i <= n / 2; i++)
        if (n % i == 0) aa[k++] = i;
      int M = aa[ctr / 2], N = n / M;
      ab[0] = M;
      ab[1] = N;
      return ab;
    }

  }

  int[][] fill() {
    int m = d[0], n = d[1];
    int ctr = 0, a[][] = new int[m][n];
    for (int i = 0; i < m; i++)
      for (int j = 0; j < n; j++)
        a[i][j] = ++ctr;
    return a;
  }

  void generateGrid(int a[][]) {
    int last = 25 + (int)(Math.random() * 100);
    System.out.println(last);
    for (int i = 1; i <= last; i++) {
      int ctr = length - 1;
      for (int j = ctr; j > 0; j--)
        if (canBeMoved(j, a)) {
          System.out.println(canBeMoved(j, a));
          move(j, a);
          print(a);
        }

    }

  }

  void play(int a[][]) throws IOException {
    int ctr = 0, chances = 0;
    System.out.println("Do you want to take a Chances Challange?(Y/N)");
    String ch = br.readLine();
    if (ch.equalsIgnoreCase("y")) {
      System.out.print("Then enter the number of chances in which you can arrange the grid : ");
      chances = Integer.parseInt(br.readLine());
      System.out.println();
    }

    String s = "";
    do {
      System.out.println("\nSo lets play now....");
      System.out.println("You can type \"exit\" or \"new game\" at any moment of game to perform the respective actions                     Press Enter\n");
      s = br.readLine();
    }

    while (s.equals("") == false);
    System.out.println("Here is the grid\n");
    print(a);
    int f = 0;
    do {
      System.out.print((ctr + 1) + "---> Enter the number to be moved : ");
      String nu = br.readLine();
      System.out.println();
      if (nu.equalsIgnoreCase("exit")) {
        String ss = "";
        System.out.println("Are you sure you want to exit?(Y/N)");
        ss = br.readLine();
        if (ss.equalsIgnoreCase("n")) {
          print(a);
          continue;
        } else byeMsg();
      } else if (nu.equalsIgnoreCase("new game")) {
        main();
      } else if (nu.equalsIgnoreCase("instawin")) goWin(a);
      else {
        try {
          int num = Integer.parseInt(nu);
          if (canBeMoved(num, a)) move(num, a);
          else System.out.println("The entered number can't be moved or doesn't exist in the grid !!");
        } catch (Exception e) {
          System.out.println("Some exception occured. It seems you dont know the cheat!!");
          String ss = br.readLine();
          if (ss.equalsIgnoreCase("show") || ss.equalsIgnoreCase("s") || ss.equalsIgnoreCase("show exception")) {
            System.out.println(e + "           Press enter");
            ss = br.readLine();
          }

          print(a);
          continue;
        }

      }

      if (won(a)) {
        print(a);
        f = 1;
        System.out.print("Congratulations " + name + " !! You did it in " + (ctr + 1) + " chances, ");
        if (chances != 0) System.out.println("even within the challange limit. Bravo!!");
        else System.out.println("you won !!");
        return;
      }

      ctr++;
      if (ctr == chances && f == 0) {
        System.out.println("Sorry to say but you lost the challange.....");
        System.out.println("Do you want to continue anyway?(Y/N)");
        s = br.readLine();
        if (s.equalsIgnoreCase("n")) {
          System.out.println("\nOkay then.....Good Bye.\n");
          return;
        }

      }

      print(a);
    }

    while (f != 1);
  }

  boolean canBeMoved(int b, int a[][]) {
    int loc[] = new int[2];
    loc = getLoc(b, a);
    //function
    int i = 1;
    while (i <= 4) {
      try {
        switch (i) {
        case 1:
          if (a[loc[0] - 1][loc[1]] == length) return true;
          break;
        case 2:
          if (a[loc[0]][loc[1] + 1] == length) return true;
          break;
        case 3:
          if (a[loc[0] + 1][loc[1]] == length) return true;
          break;
        case 4:
          if (a[loc[0]][loc[1] - 1] == length) return true;
          break;
        }

        i++;
      } catch (Exception e) {
        i++;
        continue;
      }

    }

    return false;
  }

  void move(int b, int a[][]) {
    int loc0[] = new int[2], locb[] = new int[2];
    loc0 = getLoc(length, a);
    locb = getLoc(b, a);
    int t = a[loc0[0]][loc0[1]];
    a[loc0[0]][loc0[1]] = a[locb[0]][locb[1]];
    a[locb[0]][locb[1]] = t;
  }

  int[] getLoc(int b, int a[][]) {
    int loc[] = new int[2];
    for (int i = 0; i < d[0]; i++)
      for (int j = 0; j < d[1]; j++)
        if (a[i][j] == b) {
          loc[0] = i;
          loc[1] = j;
          return loc;
        }

    return loc;
  }

  boolean won(int a[][]) {
    int ctr = 0;
    for (int i = 0; i < d[0]; i++)
      for (int j = 0; j < d[1]; j++) {
        if (i == d[0] - 1 && j == d[1] - 1) return true;
        if (a[i][j] != ++ctr) return false;
      }

    return true;
  }

  void print(int a[][]) {
    System.out.println("\n");
    printLine();
    System.out.println();
    for (int i = 0; i < d[0]; i++) {
      System.out.print("| ");
      for (int j = 0; j < d[1]; j++) {
        if (a[i][j] == length) {
          System.out.print("   | ");
          continue;
        }

        if (a[i][j] < 10) System.out.print(" " + a[i][j] + " ");
        else System.out.print(a[i][j] + " ");
        System.out.print("| ");
      }

      System.out.println();
      printLine();
      System.out.println();
    }

    System.out.println("\n");
  }

  void printLine() {
    System.out.print("  ");
    for (int i = 0; i < d[1]; i++)
      System.out.print("__   ");
  }

  void byeMsg() throws IOException {
    String s = "";
    do {
      System.out.println("\nThis program was the production of S U K H M E E T   S I N G H");
      System.out.println("Hope you like it");
      System.out.println("Good day and visit again !!");
      System.out.println("\nPress Enter to leave the program and ENTER the world.");
      s = br.readLine();
    }

    while (s.equals("") == false);
    System.exit(0);
  }

  void goWin(int a[][]) throws IOException {
    System.out.println("You have entered the I N S T A W I N mode");
    System.out.println("Do you want to win instantly(1) or see yourself winning step by step(2)");
    int ch = Integer.parseInt(br.readLine());
    int l = a.length * a[0].length;
    while (won(a) == false) {
      int n = 1 + (int)(Math.random() * l);
      if (canBeMoved(n, a)) {
        move(n, a);
        print(a);
      }

    }

  }

  boolean isPerfectSquare(int n) {
    int i = 1;
    while ((i * i) <= n)
      if ((i * i++) == n) return true;
    return false;
  }

  public static void main(String[] args) {
    TheGrid theGrid = new TheGrid();
    try {
      theGrid.main();
    } catch (Exception e) {
      System.out.println("Exception occurred: " + e);
    }

  }

}
