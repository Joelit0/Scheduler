public class Main {
  public static void main(String[] args) {
    Scheduler scheduler = new Scheduler();
    Process process1 = new Process("Process 1", scheduler);
/*    Process process2 = new Process("Process 2", scheduler);
    Process process3 = new Process("Process 3", scheduler);
    Process process4 = new Process("Process 4", scheduler);
    Process process5 = new Process("Process 5", scheduler);
    scheduler.start();
    process1.start();
    process2.start();
    process3.start();
    process4.start();
    process5.start();*/
    scheduler.start();
    process1.start();
  }
}
