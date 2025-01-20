package com.lilithsthrone.controller.eventListeners.tooltips;

import com.lilithsthrone.controller.TooltipUpdateThread;
import com.lilithsthrone.main.Main;
import org.w3c.dom.events.Event;

/**
 * Hides the tooltip.
 * 
 * @since 0.1.0
 * @version 0.1.3
 * @author Innoxia
 * Перевод не требуется
 */
public class TooltipHideEventListener implements ClonedEventListener {
	@Override
	public void handleEvent(Event event) {
		TooltipUpdateThread.cancelThreads = true;
		Main.mainController.getTooltip().hide();
	}

	public TooltipHideEventListener newInstance() {
		return new TooltipHideEventListener();
	}
}
