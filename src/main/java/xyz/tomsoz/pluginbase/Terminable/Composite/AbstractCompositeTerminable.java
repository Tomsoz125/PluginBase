package xyz.tomsoz.pluginbase.Terminable.Composite;

import xyz.tomsoz.pluginbase.Terminable.Terminable;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;

public class AbstractCompositeTerminable implements CompositeTerminable {

    private final Deque<AutoCloseable> closeables = new ConcurrentLinkedDeque<>();

    protected AbstractCompositeTerminable() {

    }

    @Override
    public CompositeTerminable with(final AutoCloseable autoCloseable) {
        Objects.requireNonNull(autoCloseable, "autoCloseable");
        this.closeables.push(autoCloseable);
        return this;
    }

    @Override
    public void close() throws CompositeClosingException {
        final List<Exception> caught = new ArrayList<>();
        for (AutoCloseable ac; (ac = this.closeables.poll()) != null; ) {
            try {
                ac.close();
            } catch (final Exception e) {
                caught.add(e);
            }
        }

        if (!caught.isEmpty()) {
            throw new CompositeClosingException(caught);
        }
    }

    @Override
    public void cleanup() {
        this.closeables.removeIf(ac -> {
            if (!(ac instanceof Terminable)) {
                return false;
            }
            if (ac instanceof CompositeTerminable) {
                ((CompositeTerminable) ac).cleanup();
            }
            return ((Terminable) ac).isClosed();
        });
    }
}
