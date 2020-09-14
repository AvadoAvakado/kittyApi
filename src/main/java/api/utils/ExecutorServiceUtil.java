package api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceUtil {
    private static List<ExecutorService> createdExecutors = new ArrayList<>();

    public static ExecutorService getNewExecutor(int numberOfThreads) {
        ExecutorService executorService;
        if (numberOfThreads == 1) {
            executorService = Executors.newSingleThreadExecutor();
        } else {
            executorService = Executors.newFixedThreadPool(numberOfThreads);
        }
        createdExecutors.add(executorService);
        return executorService;
    }

    public static void shutdownAllThreadExecutors() {
        createdExecutors.forEach(ExecutorService::shutdown);
        createdExecutors.clear();
    }
}
