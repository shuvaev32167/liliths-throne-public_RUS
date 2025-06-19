package com.lilithsthrone.controller.eventListeners.tooltips;

import com.lilithsthrone.controller.TooltipUpdateThread;
import com.lilithsthrone.main.Main;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.events.Event;

/**
 * Hides the tooltip.
 * 
 * @since 0.1.0
 * @version 0.1.3
 * @author Innoxia
 * Перевод не требуется
 */
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class TooltipHideEventListener implements ClonedEventListener<TooltipHideEventListener> {
    @Getter(onMethod = @__(@Override))
    private final TooltipHideEventListener parent;
	@Override
	public void handleEvent(Event event) {
        if (parent != null) {
            parent.handleEvent(event);
            return;
        }
		TooltipUpdateThread.cancelThreads = true;
		Main.mainController.getTooltip().hide();
	}

	public TooltipHideEventListener newInstance() {
        return new TooltipHideEventListener(tryGetParent());
    }
}
