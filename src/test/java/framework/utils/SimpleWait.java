package framework.utils;

/**
 * Класс-утилита, содержащий методы, по работе с ожиданиями.
 */
public class SimpleWait {

    private static final Logger LOG = Logger.getInstance();

    private SimpleWait() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Ожидание заданный инвервал времени (sleep).
     *
     * @param timeInSeconds интервал времени в секундах.
     */
    public static void waitSec(final long timeInSeconds) {
        waitMillis(timeInSeconds * 1000);
    }

    /**
     * Ожидание заданный инвервал времени (sleep).
     *
     * @param timeInMilliseconds интервал времени в миллисекундах.
     */
    public static void waitMillis(final long timeInMilliseconds) {
        try {
            Thread.sleep(timeInMilliseconds);
        } catch (InterruptedException e) {
            LOG.debug("SimpleWait.waitSec() interrupted");
            Thread.currentThread().interrupt();
        }
    }
}
