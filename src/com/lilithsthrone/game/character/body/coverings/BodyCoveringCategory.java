package com.lilithsthrone.game.character.body.coverings;

/**
 * @since 0.1.0
 * @version 0.4.0
 * @author Innoxia
 */
public enum BodyCoveringCategory {

	// Main covering types
	MAIN_SKIN("кожа"),
	MAIN_HAIR("волосы"),
	MAIN_FUR("мех"),
	MAIN_SCALES("чешуя"),
	MAIN_FEATHER("перья"),
	MAIN_CHITIN("хитин"),
	
	// Eyes
	EYE_IRIS("радужки"),
	EYE_PUPIL("зрачки"),
	EYE_SCLERA("белки"),

	// Head
	ANTENNAE("усики"),
	HORN("рога"),
	ANTLER("ветвистые рога"),
	HAIR("волосы"),

	// Orifices
	ANUS("анус"),
	MOUTH("рот"),
	TONGUE("язык"),
	NIPPLE("соски"),
	NIPPLE_CROTCH("лобковые соски"),
	VAGINA("вагина"),
	PENIS("пенис"),
	SPINNERET("прядущий орган"),

	// Other
	BODY_HAIR("body hair"),
	
	// Specials
	ARTIFICIAL("дилдо") {
		public boolean isInfluencedByMaterialType() {
			return false;
		}
	},
	FLUID("жидкости") {
		public boolean isInfluencedByMaterialType() {
			return false;
		}
	},
	MAKEUP("макияж") {
		public boolean isInfluencedByMaterialType() {
			return false;
		}
	};
	
	private final String name;

	BodyCoveringCategory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * @return true if this BodyCoveringCategory changes based on the material which the character's body is made out of.
	 */
	public boolean isInfluencedByMaterialType() {
		return true;
	}
}
