import java.util.LinkedList;
import java.util.Queue;

public class Scheduler extends Thread {

    //private Syncro syncro;
    public Queue<Process> procesosListos = new LinkedList<>();

    public Queue<Process> procesosBloqueados = new LinkedList<>();

    public Queue<Process> procesosSuspendidoListo = new LinkedList<>();

    public Queue<Process> procesosSuspendidoBloqueado = new LinkedList<>();

    public Queue<Process> procesoEnEjecucion = new LinkedList<>();

    public Scheduler(){//Syncro syncro) {
        //this.syncro = syncro;
    }

    public void run() {
        try {
            Scheduling();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void Scheduling() throws InterruptedException {
        while (true) {
            // mientras no existan procesos espero
            while (procesosListos.size() == 0) {}
            // ejecuto proceso
            EnEjecucion(procesosListos.peek());
            String estadoInicial = "IN_PROGRESS";
            int timeout = 1;
            // mientras el proceso no haya cambiado el estado
            // y no se le haya acabado el tiempo de procesador (timeout) ejecuta
            while (timeout != 0 && (procesoEnEjecucion.peek().getEstado() == estadoInicial)) {
                timeout--;
            }
            estadoInicial = procesoEnEjecucion.peek().getEstado();
            // proceso no terminó
            if (estadoInicial == "IN_PROGRESS") {
                System.out.println("process has not ended");
                procesoEnEjecucion.peek().run();
            }
            // proceso terminó
            if (estadoInicial == "DONE") {
                System.out.println("process has ended");
            }
            // proceso se bloqueó
            else if (estadoInicial == "BLOCKED") {
                Block(procesoEnEjecucion.peek());
                System.out.println("process has been blocked");
            }
            // proceso fue suspendido
            else if (estadoInicial == "SUSPENDED") {
                Suspender(procesoEnEjecucion.peek());
                System.out.println("process has been suspended");
            }
        }
    }

    public void Listo(Process proceso) {
        System.out.println("process " + proceso.getProcessName() + " ready to execute");
        procesosListos.add(proceso);
    }

    public synchronized void EnEjecucion(Process proceso) throws InterruptedException {

        // cambiar estado a IN_PROGRESS
        proceso.setEstadoIN_PROGRESS();
        System.out.println("executing process " + proceso.getProcessName() + "...");
        procesoEnEjecucion.add(proceso);

        // despierto a todos los procesos, pero
        // solo ejecutara el que tenga el estado IN_PROGRESS
        // el resto volvera a esperar
        synchronized (Syncro.syncro) {
            Syncro.syncro.notifyAll();
        }

        // simulo trabajo
        procesosListos.remove(proceso);
    }

    public void Suspender(Process proceso) {
        // si el proceso suspendido estaba listo, va a suspendido-listo
        System.out.println("suspending process " + proceso.getProcessName() + "...");
        if (procesosListos.contains(proceso)) {
            procesosSuspendidoListo.add(proceso);
            procesosListos.remove(proceso);
        }
        // si el proceso suspendido estaba bloqueado, va a suspendido-bloqueado
        if (procesosBloqueados.contains(proceso)) {
            procesosSuspendidoBloqueado.add(proceso);
            procesosBloqueados.remove(proceso);
        }
    }

    public void Block(Process process) {
        // mientras recurso este ocupado bloqueo
    }
}
