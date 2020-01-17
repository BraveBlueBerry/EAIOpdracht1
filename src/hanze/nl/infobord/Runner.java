package hanze.nl.infobord;

public class Runner {
	
	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}

    public static void main(String[] args) throws Exception {
		System.out.println("START");
		thread(new ListenerStarter("(halte='A') AND (richting='1')"),false);
		thread(new ListenerStarter("(halte='G') AND (richting='1')"),false);
        thread(new ListenerStarter("(halte='Z') AND (richting='1')"),false);
    }
}
