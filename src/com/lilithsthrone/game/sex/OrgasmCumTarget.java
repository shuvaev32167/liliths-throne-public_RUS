package com.lilithsthrone.game.sex;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.CoverableArea;
import com.lilithsthrone.game.dialogue.utils.UtilText;

/**
 * @since 0.1.97
 * @version 0.4.1
 * @author Innoxia
 */
public enum OrgasmCumTarget {

	// Specials:
	LILAYA_PANTIES("в трусики Лилайи", "panties", false) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.NONE;
		}
	},
	
	WALL("на стену", "wall", false) {
		@Override
		public String getName() {
			return UtilText.parse("на [pc.wall]");
		}

		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.NONE;
		}
	},
	FLOOR("на пол", "floor", false) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.NONE;
		}
	},
	
	INSIDE("внутрь", "inside", true) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.NONE;
		}
	},
	INSIDE_SWITCH_DOUBLE("внутрь (двойной)", "inside", true) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.NONE;
		}
	},
	
	ARMPITS("на подмышку", "armpit", true) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.ARMPITS;
		}
	},
	ASS("на задницу", "ass", true) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.ASS;
		}
	},
	GROIN("на промежность", "groin", true) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			if(owner.hasVagina()) {
				return CoverableArea.VAGINA;
			} else if(owner.hasPenis()) {
				return CoverableArea.PENIS;
			}
			return CoverableArea.MOUND;
		}
	},
	BREASTS("на грудь", "breasts", true) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.BREASTS;
		}
	},
	FACE("на лицо", "face", true) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.MOUTH;
		}
	},
	HAIR("на волосы", "hair", true) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.HAIR;
		}
	},
	STOMACH("на живот", "stomach", true) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.STOMACH;
		}
	},
	LEGS("на ноги", "legs", true) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.LEGS;
		}
	},
	FEET("на ступни", "feet", true) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.FEET;
		}
	},
	BACK("на спину", "back", true) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.BACK;
		}
	},
	
	SELF_GROIN("на свою промежность", "own groin", false) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			if(owner.hasVagina()) {
				return CoverableArea.VAGINA;
			} else if(owner.hasPenis()) {
				return CoverableArea.PENIS;
			}
			return CoverableArea.MOUND;
		}
	},
	SELF_STOMACH("на свой живот", "own stomach", false) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.STOMACH;
		}
	},
	SELF_LEGS("на свои ноги", "own legs", false) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.LEGS;
		}
	},
	SELF_FEET("на свои ступни", "own feet", false) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.FEET;
		}
	},
	SELF_BREASTS("на свои груди", "own breasts", false) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.BREASTS;
		}
	},
	SELF_HANDS("на свои руки", "own hands", false) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.HANDS;
		}
	},
	SELF_FACE("на свое лицо", "own face", false) {
		@Override
		public CoverableArea getRelatedCoverableArea(GameCharacter owner) {
			return CoverableArea.MOUTH;
		}
	};
	
	private String name;
	private String simpleName;
	private boolean requiresPartner;

	private OrgasmCumTarget(String name, String simpleName, boolean requiresPartner) {
		this.name = name;
		this.simpleName = simpleName;
		this.requiresPartner = requiresPartner;
	}

	/**
	 * @return A name that's suitable for a brief action description, e.g. 'onto floor'
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return A one-word name that's suitable for use in scenes, e.g. 'floor'
	 */
	public String getSimpleName() {
		return simpleName;
	}

	public boolean isRequiresPartner() {
		return requiresPartner;
	}
	
	public abstract CoverableArea getRelatedCoverableArea(GameCharacter owner);
}
