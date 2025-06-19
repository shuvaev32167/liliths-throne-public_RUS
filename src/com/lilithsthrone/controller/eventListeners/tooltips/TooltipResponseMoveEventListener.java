package com.lilithsthrone.controller.eventListeners.tooltips;

import com.lilithsthrone.controller.MainController;
import com.lilithsthrone.main.Main;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.MouseEvent;

/**
 * @since 0.1.61
 * @version 0.1.69
 * @author Innoxia
 * Перевод не требуется
 */
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class TooltipResponseMoveEventListener implements ClonedEventListener<TooltipResponseMoveEventListener> {
	@Getter(onMethod = @__(@Override))
	private final TooltipResponseMoveEventListener parent;

	@Override
	public void handleEvent(Event event) {
		if (parent != null) {
			parent.handleEvent(event);
			return;
		}
		double xPosition = ((MouseEvent) event).getScreenX() + 16 - 180;
		if (xPosition + 360 > Main.primaryStage.getX() + Main.primaryStage.getWidth() - 16)
			xPosition = Main.primaryStage.getX() + Main.primaryStage.getWidth() - 360 - 16;

		Main.mainController.getTooltip().setAnchorX(xPosition);
		Main.mainController.getTooltip().setAnchorY(Main.primaryStage.getY() + Main.primaryStage.getHeight() - (34*(MainController.RESPONSE_COUNT/5) + 4) - Main.mainController.getTooltip().getHeight()
				- (Main.mainScene.getWindow().getHeight() - Main.mainScene.getHeight() - Main.mainScene.getY()));
	}

    @Override
	public TooltipResponseMoveEventListener newInstance() {
		return new TooltipResponseMoveEventListener(tryGetParent());
    }
}
