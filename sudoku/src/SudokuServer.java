import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.HashMap;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.reflect.*;
/*
 *Check # of threads ---> Threaded Server
 *  --------------
 * get connection
 * 
 * read stream
 * 				CLASS RUNABLE (THREADED)
 * calculate
 * 
 * output
 *  ------------
 *  while(true)
 *  1. wait for connection
 *  2. pass to worker (PASS WORKERCLASS)
 *  
 *  
 *  USE EXECUTOR
  */
@Target(TYPE)

@Retention(RUNTIME)
@interface Author {
public String name() default "Jeremy Clifton" ;
public String uteid() default "jdc3887" ;
}
//TODO(EE422C): update these to your name and eid
@Author(name="Jeremy Clifton", uteid="jdc3887")
public class SudokuServer {
  static int PORT = -1;
  // EE422C: no matter how many concurrent requests you get,
  // do not have more than three solvers running concurrently
  final static int MAXPARALLELTHREADS = 3;
  public static void main(String[] args) {
	  try{
	  	SudokuServer.start(16789);
	  } catch (Exception e) {
	  
	  }
	    //System.out.println("Your solver ouput is " + (correct ? "wrong" : "right"));
  }
  public static void start(int portNumber ) throws IOException {
    PORT = portNumber;
    Runnable serverThread = new ThreadedSudokuServer();
    Thread t = new Thread( serverThread );
    t.start();
    try {
    	Thread.sleep(1000);
    }
    catch(Exception e) {
    	
    }
  }
}

    //TODOBEGIN(EE422C)
class ThreadedSudokuServer implements Runnable{
	ExecutorService ex = Executors.newFixedThreadPool(SudokuServer.MAXPARALLELTHREADS);
	public ThreadedSudokuServer() {
	}
	public  void run() {
		ServerSocket serversock;
		try {
			serversock = new ServerSocket(SudokuServer.PORT);
			for(;;) {
				Socket sock = serversock.accept();
				Worker myWork = new Worker(sock);
				ex.execute(myWork);
		
			}
	    }
		catch(IOException e) {
			System.out.println(e);
		}
	}
	
	
}
class Worker implements Runnable {
	Socket sock;
	static String cacheQuery = "";
	static String cacheAnswer = "";
	public Integer mapIndex = 0;
	public Worker(Socket mysock)  {
		this.sock = mysock;
		
	}
	
	public void run() {
		synchronized(this) { 
			try {
				PrintWriter pw = new PrintWriter(sock.getOutputStream());
				BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
				String query = br.readLine();
				SudokuPuzzle mypuzzle = new SudokuPuzzle(SudokuSolver.readInput(query));
				mypuzzle.solveThisBitch();
				//SudokuSolver.print("", mypuzzle.convertToMatrix());
				if(!cacheQuery.equals(query)) {
					cacheQuery = query;
					cacheAnswer = SudokuSolver.matrixToString(mypuzzle.convertToMatrix());
				}
				pw.print(cacheAnswer);
				
				pw.flush();
				pw.close();
				br.close();
			}	 
			catch(Exception e) {
				System.out.println(e);
			}
		}
		
	}
	
	
	
}
    //TODOEND(EE422C)