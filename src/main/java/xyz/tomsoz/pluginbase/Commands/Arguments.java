package xyz.tomsoz.pluginbase.Commands;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.function.Consumer;

public class Arguments {
    private LinkedHashMap<String, Argument> arguments;

    public Arguments(Argument... args) {
        arguments = new LinkedHashMap<>();
        for (Argument a : args) {
            arguments.put(a.getName(), a);
        }
    }

    public Argument get(String name) {
        return arguments.getOrDefault(name, null);
    }

    public Arguments add(Argument arg) {
        arguments.put(arg.getName(), arg);
        return this;
    }

    public boolean contains(String name) {
        return arguments.containsKey(name) && arguments.get(name) != null;
    }

    public Argument last() {
        if (arguments.values().size() == 0) return null;
        return (Argument) arguments.values().toArray()[arguments.values().size() - 1];
    }

    public Argument getIndex(int index) {
        return (Argument) arguments.values().toArray()[index];
    }

    public int size() {
        return arguments.size();
    }

    public void forEach(@NotNull Consumer<Argument> consumer) {
        arguments.values().forEach(consumer);
    }

    public String usage() {
        StringBuilder usageStr = new StringBuilder();
        forEach((a) -> {
            if (a.isRequired()) {
                usageStr.append(" <").append(a.getName()).append(">");
            } else {
                usageStr.append(" [").append(a.getName()).append("]");
            }
        });
        return usageStr.toString();
    }
}