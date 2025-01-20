package com.lilithsthrone.controller.eventListeners.tooltips;

import org.w3c.dom.events.EventListener;

public interface ClonedEventListener extends EventListener {
    ClonedEventListener newInstance();
}
