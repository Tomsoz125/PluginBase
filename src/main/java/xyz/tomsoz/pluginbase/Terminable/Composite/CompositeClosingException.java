package xyz.tomsoz.pluginbase.Terminable.Composite;

import java.util.Collections;
import java.util.List;

/**
 * Exception thrown to propagate exceptions thrown by {@link CompositeTerminable#close()}.
 */
public class CompositeClosingException extends Exception {

    private final List<? extends Throwable> causes;

    public CompositeClosingException(final List<? extends Throwable> causes) {
        super("Exception(s) occurred whilst closing: " + causes.toString());
        if (causes.isEmpty()) {
            throw new IllegalArgumentException("No causes");
        }
        this.causes = Collections.unmodifiableList(causes);
    }

    public List<? extends Throwable> getCauses() {
        return this.causes;
    }

    public void printAllStackTraces() {
        this.printStackTrace();
        for (final Throwable cause : this.causes) {
            cause.printStackTrace();
        }
    }

}
