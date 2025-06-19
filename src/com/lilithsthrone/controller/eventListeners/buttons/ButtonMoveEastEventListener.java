package com.lilithsthrone.controller.eventListeners.buttons;

import com.lilithsthrone.controller.eventListeners.tooltips.ClonedEventListener;
import com.lilithsthrone.main.Main;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.events.Event;

/**
 * @since 0.1.69.9
 * @version 0.1.69.9
 * @author Innoxia
 */
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class ButtonMoveEastEventListener implements ClonedEventListener<ButtonMoveEastEventListener> {
    @Getter(onMethod = @__(@Override))
    private final ButtonMoveEastEventListener parent;

	@Override
	public void handleEvent(Event event) {
        if (parent != null) {
            parent.handleEvent(event);
            return;
        }
        Main.mainController.moveEast();
	}

    @Override
    public ButtonMoveEastEventListener newInstance() {
        return new ButtonMoveEastEventListener(tryGetParent());
    }
}