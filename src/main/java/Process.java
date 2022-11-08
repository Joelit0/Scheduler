import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

enum states {
  READY,
  IN_PROGRESS,
  BLOCKED,
  SUSPENDED,
  DONE
}

public class Process extends Thread {
  private String name;
  private states state;
  private Timer durationTimer;
  private Timer schedulerTimer;

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

  public void run() {
    if (this.state == states.READY) {
      System.out.println("Dando CPU al proceso " + this.name);

      this.durationTimer.start();
      this.schedulerTimer.start();

      while (true) { }
    }
  }

  public void block() {
    System.out.println("Bloqueando");
    this.interrupt();
    this.state = states.BLOCKED;
    this.durationTimer.stop();
  }

  public void finish() {
    System.out.println("Terminando");
    this.interrupt();
    this.state = states.DONE;
  }
}
