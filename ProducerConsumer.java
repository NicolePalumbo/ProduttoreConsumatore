import java.util.Random;
import java.util.Scanner;
import java.util.LinkedList;


public class ProducerConsumer {

	
	static class Producer extends Thread{
		private final int X;
		public int currentValue = 0;
		private baffer baffer;
		
		public Producer (int X, baffer buffer) {
			this.X = X;
			this.buffer = buffer;
		}
		
		

	   
		@Override 
		public void run() {
			try {
                while (currentValue <= X) {
                	buffer.produce(value++);
                    Thread.sleep(X);  // Aspetta 120 ms
                    currentValue++;     // Incrementa il contatore
                    System.out.println(Thread.currentThread().getName() + " stampa " + currentValue);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}
		
	}
	
	static class Consumer extends Thread{
		
		private baffer buffer;

	    public Consumer(baffer buffer) {
	        this.buffer = buffer;
	    }
	    
	    public void run() {
	        try {
	            while (true) {
	                buffer.consume();
	                Thread.sleep(150);  // Simula il tempo per consumare un elemento
	            }
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	        }
	    }

		
		
	}
	
	
	public static void main (String[] args) {
		 	Scanner scanner = new Scanner(System.in);
	        System.out.print("Inserisci il numero di thread (T): ");
	        int T = scanner.nextInt();
	        System.out.print("Inserisci il valore massimo N: ");
	        int N = scanner.nextInt();
	        
	        Producer[] threads = new Producer[T];
	        Random random = new Random();
	        
	        for (int i = 0; i < T; i++) {
	            int X = random.nextInt(N + 1);  // Genera un numero casuale tra 0 e N
	            threads[i] = new Producer(X);
	            threads[i].start();  // Avvia il thread
	            System.out.println("Thread " + i + " conta fino a " + X);
	        }

	}
	
	
	public class baffer {
		private Queue<Integer> queueInterface = new LinkedList<>();
	    private int capacity;

	    public baffer(int capacity) {
	        this.capacity = capacity;
	    }

	// Produce permette al thread di aggiumgere un elemento al buffer
	    public synchronized void produce(int value) throws InterruptedException {


	// Controllo se il baffer è pieno, se si sospendo il produttore
	        while (queueInterface.size() == capacity) {
	            wait();
	        }

	        
	// Aggiungo l'elemento al buffer
	        queueInterface.add(value);
	        System.out.println("Prodotto: " + value);

	        
	// Risveglio i consumatori visto lìaggiunta di un nuovo elemento 
	        notifyAll();
	    }

	    
	    
	    
	// permette a un thread consumatore di prelevare un elemento dal buffer
	    public synchronized int consume() throws InterruptedException {
	    	
	    	
	// Se il buffer è vuoto, attendi che ci sia un elemento
	        while (queueInterface.isEmpty()) {
	            wait();
	        }

	        
	// Rimuovi l'elemento dal buffer
	        int value = queueInterface.poll();
	        System.out.println("Consumatore ha consumato: " + value);
	        
	        

	// Risveglio i produttore ch c'è spazoo nel buffer
	        notifyAll();
	        return value;
	    }
	}



	
	
}

