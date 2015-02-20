package eco;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lwjgl.opengl.SharedDrawable;

public class ThreadManager {
	
	// Don't Touch this
	// I Mean seriously
	// You'll probably break something
	
	// Cores + 1 is the optimal size for a pool
	private static ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
	
	// Do. Not. Touch. This.
	public static SharedDrawable drawable;
	
	public static void addJob(Runnable runnable){
		pool.execute(runnable);
	}

}
