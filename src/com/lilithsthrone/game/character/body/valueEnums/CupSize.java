package com.lilithsthrone.game.character.body.valueEnums;

/**
 * Measurements are in inches. Measured in bust to underbust using the UK system.
 * 
 * @since 0.1.0
 * @version 0.1.83
 * @author Innoxia
 */
public enum CupSize {
	
	FLAT("плоско", "плоско", 0),

	// Training bra sizes:
	
	TRAINING_AAA("почти незаметная", "тренеруемая-AAA", 1) {
		@Override
		public boolean isTrainingBraSize() {
			return true;
		}
	},
	TRAINING_AA("почти незаметная", "тренеруемая-AA", 2) {
		@Override
		public boolean isTrainingBraSize() {
			return true;
		}
	},
	TRAINING_A("почти незаметная", "тренеруемая-A", 3) {
		@Override
		public boolean isTrainingBraSize() {
			return true;
		}
	},
	
	// Normal cup sizes:

	AA("очень маленькая", "AA", 4),
	A("маленькая", "A", 5),
	B("небольшая", "B", 6),
    C("средняя", "C", 7),
	D("большая", "D", 8),
	DD("большая", "DD", 9),
	E("значительная", "E", 10),
	F("значительная", "F", 11),
	FF("значительная", "FF", 12),
	G("огромная", "G", 13),
	GG("огромная", "GG", 14),
	H("огромная", "H", 15),
	HH("массивная", "HH", 16),
	J("массивная", "J", 17),
	JJ("массивная", "JJ", 18),
	K("гигантская", "K", 19),
	KK("гигантская", "KK", 20),
	L("гигантская", "L", 21),
	LL("колоссальная", "LL", 22),
	M("колоссальная", "M", 23),
	MM("колоссальная", "MM", 24),
	N("колоссальная", "N", 25),
	
	// Hyper sizes:

	X_AA("экстремальная", "X-AA", 26),
	X_A("экстремальная", "X-A", 27),
	X_B("экстремальная", "X-B", 28),
	X_C("экстремальная", "X-C", 29),
	X_D("экстремальная", "X-D", 30),
	X_DD("экстремальная", "X-DD", 31),
	X_E("экстремальная", "X-E", 32),
	X_F("экстремальная", "X-F", 33),
	X_FF("экстремальная", "X-FF", 34),
	X_G("экстремальная", "X-G", 35),
	X_GG("экстремальная", "X-GG", 36),
	X_H("экстремальная", "X-H", 37),
	X_HH("экстремальная", "X-HH", 38),
	X_J("экстремальная", "X-J", 39),
	X_JJ("экстремальная", "X-JJ", 40),
	X_K("экстремальная", "X-K", 41),
	X_KK("экстремальная", "X-KK", 42),
	X_L("экстремальная", "X-L", 43),
	X_LL("экстремальная", "X-LL", 44),
	X_M("экстремальная", "X-M", 45),
	X_MM("экстремальная", "X-MM", 46),
	X_N("экстремальная", "X-N", 47),

	XX_AA("монструозная", "XX-AA", 48),
	XX_A("монструозная", "XX-A", 49),
	XX_B("монструозная", "XX-B", 50),
	XX_C("монструозная", "XX-C", 51),
	XX_D("монструозная", "XX-D", 52),
	XX_DD("монструозная", "XX-DD", 53),
	XX_E("монструозная", "XX-E", 54),
	XX_F("монструозная", "XX-F", 55),
	XX_FF("монструозная", "XX-FF", 56),
	XX_G("монструозная", "XX-G", 57),
	XX_GG("монструозная", "XX-GG", 58),
	XX_H("монструозная", "XX-H", 59),
	XX_HH("монструозная", "XX-HH", 60),
	XX_J("монструозная", "XX-J", 61),
	XX_JJ("монструозная", "XX-JJ", 62),
	XX_K("монструозная", "XX-K", 63),
	XX_KK("монструозная", "XX-KK", 64),
	XX_L("монструозная", "XX-L", 65),
	XX_LL("монструозная", "XX-LL", 66),
	XX_M("монструозная", "XX-M", 67),
	XX_MM("монструозная", "XX-MM", 68),
	XX_N("монструозная", "XX-N", 69),

	XXX_AA("гипер", "XXX-AA", 70),
	XXX_A("гипер", "XXX-A", 71),
	XXX_B("гипер", "XXX-B", 72),
	XXX_C("гипер", "XXX-C", 73),
	XXX_D("гипер", "XXX-D", 74),
	XXX_DD("гипер", "XXX-DD", 75),
	XXX_E("гипер", "XXX-E", 76),
	XXX_F("гипер", "XXX-F", 77),
	XXX_FF("гипер", "XXX-FF", 78),
	XXX_G("гипер", "XXX-G", 79),
	XXX_GG("гипер", "XXX-GG", 80),
	XXX_H("гипер", "XXX-H", 81),
	XXX_HH("гипер", "XXX-HH", 82),
	XXX_J("гипер", "XXX-J", 83),
	XXX_JJ("гипер", "XXX-JJ", 84),
	XXX_K("гипер", "XXX-K", 85),
	XXX_KK("гипер", "XXX-KK", 86),
	XXX_L("гипер", "XXX-L", 87),
	XXX_LL("гипер", "XXX-LL", 88),
	XXX_M("гипер", "XXX-M", 89),
	XXX_MM("гипер", "XXX-MM", 90),
	XXX_N("гипер", "XXX-N", 91);

	private final String descriptor;
	private final String cupSizeName;
	private final int measurement;

	CupSize(String descriptor, String cupSizeName, int measurement) {
		this.descriptor = descriptor;
		this.cupSizeName = cupSizeName;
		this.measurement = measurement;
	}
	
	public boolean isTrainingBraSize() {
		return false;
	}
	
	/**
	 * @return The minimum size which is regarded as a character 'having breasts' by the game.
	 */
	public static CupSize getMinimumCupSizeForBreasts() {
		return CupSize.AA;
	}
	
	public static CupSize getMinimumCupSizeForEggIncubation() {
		return CupSize.C;
	}
	
	public static CupSize getMinimumCupSizeForPaizuri() {
		return CupSize.C;
	}
	
	public static CupSize getMinimumCupSizeForPenetration() {
		return CupSize.C;
	}

	/**
	 * @param size Measurement in inches from bust to underbust.
	 */
	public static CupSize getCupSizeFromInt(int size) {
		for (CupSize cs : values()) {
			if (size == cs.measurement) {
				return cs;
			}
		}
		return XXX_N;
	}

	/**
	 * To fit into a sentence: "You have "+getDescriptor()+" breasts."
	 */
	public String getDescriptor() {
		return descriptor;
	}

	public String getCupSizeName() {
		return cupSizeName;
	}

	public int getMeasurement() {
		return measurement;
	}
	
	public static CupSize getMaximumCupSize() {
		return XXX_N;
	}
}
