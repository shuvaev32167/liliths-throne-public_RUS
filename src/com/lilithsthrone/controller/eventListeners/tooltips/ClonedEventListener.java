package com.lilithsthrone.controller.eventListeners.tooltips;

import lombok.SneakyThrows;
import org.w3c.dom.events.EventListener;

public interface ClonedEventListener<T extends ClonedEventListener> extends EventListener {
    @SneakyThrows
    default T newInstance() {
        var constructor = this.getClass().getDeclaredConstructor(this.getClass());
        constructor.setAccessible(true);
        return (T) constructor.newInstance(tryGetParent());
    }

    @SneakyThrows
    default T getParent() {
        var field = this.getClass().getDeclaredField("parent");
        field.setAccessible(true);
        return (T) field.get(this);
    }

    default T tryGetParent() {
        T parent = getParent();
        if (parent == null) {
            parent = (T) this;
        }
        T targetParent = parent;
        while (parent != null) {
            targetParent = parent;
            parent = (T) parent.getParent();
        }
        return targetParent;
    }
}
