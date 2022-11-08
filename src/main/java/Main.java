public class Main {
  public static void main(String[] args) {
    Process process = new Process("Process 1", 7000, 6000);
    process.start();

    while (process.isAlive()) {}

    process.start();
  }
}
