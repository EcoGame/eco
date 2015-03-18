package eco;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.lwjgl.opengl.SharedDrawable;

/**
 * This class manages concurrency through a thread pool
 * 
 * Also holds the LWJGl drawable, used to pass around the openGL context
 * 
 * @author phil
 * 
 */

public class ThreadManager {

	// Cores + 1 is the optimal size for a pool
	private static ExecutorService pool = Executors.newFixedThreadPool(Runtime
			.getRuntime().availableProcessors() + 1);

	public static SharedDrawable drawable;

	public static void addJob(Runnable runnable) {
		pool.execute(runnable);
	}

}
