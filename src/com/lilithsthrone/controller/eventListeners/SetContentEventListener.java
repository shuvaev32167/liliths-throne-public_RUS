package com.lilithsthrone.controller.eventListeners;

import com.lilithsthrone.controller.MainController;
import com.lilithsthrone.controller.eventListeners.tooltips.ClonedEventListener;
import com.lilithsthrone.main.Main;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.events.Event;

/**
 * Sets the MainController's content.
 * 
 * @since 0.1.0
 * @version 0.1.7
 * @author Innoxia
 * Перевод не требуется
 */
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class SetContentEventListener implements ClonedEventListener<SetContentEventListener> {
	private int index;
	private boolean nextPage = false, previousPage = false;
	@Getter(onMethod = @__(@Override))
	private final SetContentEventListener parent;

	@Override
	public void handleEvent(Event event) {
		if (parent != null) {
			parent.handleEvent(event);
			return;
		}
		if (nextPage) {
			if (Main.game.isHasNextResponsePage()) {
				Main.game.setResponsePage(Main.game.getResponsePage() + 1);
				Main.game.updateResponses();
//				Main.game.setResponses(Main.game.getCurrentDialogueNode());
			}
		} else if (previousPage) {
			if (Main.game.getResponsePage() != 0) {
				Main.game.setResponsePage(Main.game.getResponsePage() - 1);
				Main.game.updateResponses();
//				Main.game.setResponses(Main.game.getCurrentDialogueNode());
			}
		} else {
			if (Main.game.getResponsePage() == 0)
				Main.game.setContent(Main.game.getResponsePage() * MainController.RESPONSE_COUNT + index);
			else {
				if (index != 0)
					Main.game.setContent(Main.game.getResponsePage() * MainController.RESPONSE_COUNT + index - 1);
				else
					Main.game.setContent(Main.game.getResponsePage() * MainController.RESPONSE_COUNT + (MainController.RESPONSE_COUNT-1));
			}

		}
	}

	public SetContentEventListener setIndex(int index) {
		if (parent != null) {
			parent.setIndex(index);
			return this;
		}
		this.index = index;

		nextPage = false;
		previousPage = false;
		return this;
	}

	public SetContentEventListener nextPage() {
		if (parent != null) {
			parent.nextPage();
			return this;
		}
		nextPage = true;
		previousPage = false;

		return this;
	}

	public SetContentEventListener previousPage() {
		if (parent != null) {
			parent.previousPage();
			return this;
		}
		nextPage = false;
		previousPage = true;

		return this;
	}

	@Override
	public SetContentEventListener newInstance() {
		return new SetContentEventListener(tryGetParent());
	}
}
