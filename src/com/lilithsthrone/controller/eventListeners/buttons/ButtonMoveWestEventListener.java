package com.lilithsthrone.controller.eventListeners.buttons;

import com.lilithsthrone.controller.eventListeners.tooltips.ClonedEventListener;
import com.lilithsthrone.main.Main;
import org.w3c.dom.events.Event;

/**
 * @since 0.1.69.9
 * @version 0.1.69.9
 * @author Innoxia
 */
public class ButtonMoveWestEventListener implements ClonedEventListener {

	@Override
	public void handleEvent(Event event) {
		Main.mainController.moveWest();
	}

    @Override
    public ClonedEventListener newInstance() {
        return new ButtonMoveWestEventListener();
    }
}