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

    private String processName;
    private states state;
    private Scheduler scheduler;


    public String getProcessName() {
        return processName;
    }

    public Process(String name, Scheduler scheduler){
        this.processName = name;
        this.scheduler = scheduler;
    }


    public String getEstado() {
        return this.state.toString();
    }

    public void setEstadoIN_PROGRESS() {
        this.state = states.IN_PROGRESS;
    }

    @Override
    public void run() {
        System.out.println("initializing process thread...");
        System.out.println("process ready to execute");
        this.state = states.READY;
        scheduler.Listo(this);
        while (state != states.IN_PROGRESS){
            try {
                synchronized (Syncro.syncro)
                {
                    Syncro.syncro.wait();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            System.out.println(Thread.currentThread().getName());
            Work();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void Work() throws InterruptedException {
        System.out.println("working...");
        sleep(1000000000);
        finish();
    }

    public void block() {
        System.out.println("Bloqueando");
        this.state = states.BLOCKED;
        this.interrupt();
    }

    public void finish() {
        System.out.println("done");
        this.state = states.DONE;
        this.interrupt();
    }
}
