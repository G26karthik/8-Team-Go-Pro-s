import java.util.Scanner;

public class DiningPhilosophersMonitor {
    private final int N;
    private final int[] state;
    private static final int THINKING = 0, HUNGRY = 1, EATING = 2;

    public DiningPhilosophersMonitor(int n) {
        N = n;
        state = new int[N];
    }

    private synchronized void takeForks(int i) throws InterruptedException {
        state[i] = HUNGRY;
        test(i);
        while (state[i] != EATING) wait();
    }

    private synchronized void putForks(int i) {
        state[i] = THINKING;
        test((i + N - 1) % N);
        test((i + 1) % N);
        notifyAll();
    }

    private void test(int i) {
        if (state[i] == HUNGRY && state[(i + N - 1) % N] != EATING && state[(i + 1) % N] != EATING) {
            state[i] = EATING;
            notifyAll();
        }
    }

    class Philosopher extends Thread {
        int id;
        Philosopher(int id) { this.id = id; }
        public void run() {
            try {
                while (true) {
                    System.out.println("Philosopher " + id + " is THINKING");
                    Thread.sleep((int)(Math.random() * 1000) + 500);
                    takeForks(id);
                    System.out.println("Philosopher " + id + " is EATING");
                    Thread.sleep((int)(Math.random() * 1000) + 500);
                    putForks(id);
                }
            } catch (InterruptedException e) {}
        }
    }

    public void startSimulation() {
        for (int i = 0; i < N; i++) new Philosopher(i).start();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter number of philosophers: ");
        int n = sc.nextInt();
        new DiningPhilosophersMonitor(n).startSimulation();
    }
}