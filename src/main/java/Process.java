import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.lang.Thread;

public class Process extends Thread {
  private enum states {
    READY,
    IN_PROGRESS,
    BLOCKED,
    SUSPENDED,
    DONE
  }

  private String name;
  private states state;
  private Timer durationTimer;
  private Timer schedulerTimer;

  private boolean x = false;

  public Process(String name, int durationTime, int schedulerTimeout) {
    this.name = name;
    this.state = states.READY;

    this.durationTimer = new Timer(durationTime, new ActionListener()  {
      public void actionPerformed(ActionEvent e)
      {
        finish();
      }
    });

    this.schedulerTimer = new Timer(schedulerTimeout, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        block();
      }
    });

    this.durationTimer.setRepeats(false);
    this.schedulerTimer.setRepeats(false);
  }

  public String getEstado() {
    return this.state.toString();
  }

  @Override
  public void run() {
    if (this.state == states.READY) {
      System.out.println("Dando CPU al proceso " + this.name);

      this.durationTimer.start();
      this.schedulerTimer.start();

      System.out.println(x);

      while (true) { }
    }
  }

  public void block() {
    this.durationTimer.stop();
    System.out.println("Bloqueando");
    this.state = states.BLOCKED;
    this.x = true;
    this.state = states.READY;
    this.interrupt();
  }

  public void finish() {
    System.out.println("Terminando");
    this.schedulerTimer.stop();
    this.state = states.DONE;
    this.interrupt();
  }
}
