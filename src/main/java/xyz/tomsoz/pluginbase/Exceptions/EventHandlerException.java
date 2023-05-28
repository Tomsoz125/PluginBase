package xyz.tomsoz.pluginbase.Exceptions;

/**
 * A general exception caused by an event handler.
 */
public class EventHandlerException extends APIException {

    public EventHandlerException(final Throwable cause, final Object event) {
        super("event handler for " + event.getClass().getName(), cause);
    }
}
