package com.lilithsthrone.controller.eventListeners.buttons;

import com.lilithsthrone.controller.eventListeners.tooltips.ClonedEventListener;
import com.lilithsthrone.main.Main;
import org.w3c.dom.events.Event;

/**
 * @since 0.1.69.9
 * @version 0.1.69.9
 * @author Innoxia
 */
public class ButtonMoveSouthEventListener implements ClonedEventListener {

	@Override
	public void handleEvent(Event event) {
		Main.mainController.moveSouth();
	}

    @Override
    public ClonedEventListener newInstance() {
        return new ButtonMoveSouthEventListener();
    }
}