package com.lilithsthrone.game.character.attributes;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.race.AbstractRace;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.PresetColour;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * NOTE: Racial attributes are added at the bottom of the static block in Race.java!
 * 
 * @since 0.1.0
 * @version 0.4
 * @author Innoxia
 */
public class Attribute {

	public static AbstractAttribute MAJOR_PHYSIQUE = new AbstractAttribute(false,
			0,
			0,
			100,
            "телосложение",
			"Телосложение",
			"strengthIcon",
			PresetColour.ATTRIBUTE_PHYSIQUE,
			"power",
			"weakness",
			Util.newArrayListOfValues(
					"<b>+2</b> <b style='color: " + PresetColour.ATTRIBUTE_HEALTH.toWebHexString() + "'>Energy</b> per 1 physique")) {
		@Override
		public boolean hasStatusEffect() {
			return true;
		}
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"Мера того, насколько [npc.name] физически [npc.genderBasedWord(здоров, здорова)], телосложение <b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>пассивно увеличивает</b> [npc.her]"
							+ " <b style='color:" + PresetColour.ATTRIBUTE_HEALTH.toWebHexString() + ";'>максимальное здоровье</b>.");
		}
        @Override
		public int getOrderPriority() {
			return 10;
		}
	};

	public static AbstractAttribute MAJOR_ARCANE = new AbstractAttribute(false,
			0,
			0,
			100,
            "магия",
			"Магия",
			"intelligenceIcon",
			PresetColour.ATTRIBUTE_ARCANE,
			"arcane-boost",
			"arcane-drain",
			Util.newArrayListOfValues(
					"<b>+2</b> <b style='color: " + PresetColour.ATTRIBUTE_MANA.toWebHexString() + "'>Aura</b> per 1 arcane")) {
		@Override
		public boolean hasStatusEffect() {
			return true;
		}
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"Мера близости к магии. <b style='color:" + PresetColour.GENERIC_GOOD.toWebHexString() + ";'>Пассивно увеличивает</b> [npc.morphSingleAccus([npc.morphGenSinFem([npc.her])])]"
							+ " <b style='color:" + PresetColour.ATTRIBUTE_MANA.toWebHexString() + ";'>максимальную аура</b>.");
		}
        @Override
		public int getOrderPriority() {
			return 20;
		}
	};

	public static AbstractAttribute MAJOR_CORRUPTION = new AbstractAttribute(false,
			0,
			0,
			100,
			"развращённость",
			"Развращённость",
			"corruptionIcon",
			PresetColour.ATTRIBUTE_CORRUPTION,
			"развращённость",
			"чистота",
			Util.newArrayListOfValues(
					"<b>-0.5</b> <b style='color: " + PresetColour.ATTRIBUTE_MANA.toWebHexString() + "'>сопротивление похоти</b> за 1 развращённость",
					"<b>+0.5</b> <b style='color: " + PresetColour.DAMAGE_TYPE_MANA.toWebHexString() + "'>урон похотью</b> за 1 развращённость")) {
		@Override
		public boolean hasStatusEffect() {
			return true;
		}
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner.isPlayer()) {
				return "Развращённость - это показатель твоей извращенности и испорченности, она влияет на <b style='color:" + PresetColour.ATTRIBUTE_CORRUPTION.toWebHexString() + ";'>какие действия в сексе тебе комфортно выполнять</b>.";
			} else {
				return UtilText.parse(owner,
						"Развращённость - это показатель [npc.namePos] извращенности и испорченности. <i>Не</i> отражает то, насколько [npc.she] [npc.genderBasedWord(добр или зол, добра или зла)].");
			}
		}
        @Override
		public int getOrderPriority() {
			return 30;
		}
	};

	public static AbstractAttribute HEALTH_MAXIMUM = new AbstractAttribute(false,
			1,
			1,
			1000,
			"здоровье",
			"Здоровье",
			"healthIcon",
			PresetColour.ATTRIBUTE_HEALTH,
			"health",
			"sickness",
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"Количество выносливости и решимости у [npc.morphSingleNameGene([npc.name])]. [npc.She] [npc.targetBasedWord(будешь, будет)] побежден в бою, если этот показатель достигнет 0.<br/>"
							+ "Дополнительное здоровье добавляется к значению «Бонус» по формуле:<br/>"
						+"<b>"+ GameCharacter.HEALTH_CALCULATION + "</b>");
		}

		@Override
		public int getOrderPriority() {
			return 40;
		}
	};

	public static AbstractAttribute MANA_MAXIMUM = new AbstractAttribute(false,
			1,
			1,
			1000,
			"аура",
			"Аура",
			"manaIcon",
			PresetColour.ATTRIBUTE_MANA,
			"aura-boost",
			"aura-drain",
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"Мера количества магической энергии, которое [npc.name] [npc.has] в [npc.morphSingleInstr([npc.morphGenSinFem([npc.her])])] ауре.<br/>"
							+ "Дополнительная аура добавляется к значению «Бонус» по формуле:<br/>"
						+ "<b>" + GameCharacter.MANA_CALCULATION + "</b>");
		}

		@Override
		public int getOrderPriority() {
			return 50;
		}
	};

	public static AbstractAttribute EXPERIENCE = new AbstractAttribute(false,
			0,
			0,
			1000000,
			"experience",
			"Experience",
			"experienceIcon",
			PresetColour.GENERIC_EXPERIENCE,
			"learning",
			"forgetfulness",
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"How much progress [npc.name] [npc.has] made to the next level.");
		}

		@Override
		public int getOrderPriority() {
			return 60;
		}
	};

	public static AbstractAttribute ACTION_POINTS = new AbstractAttribute(false,
			0,
			0,
			10,
			"action points",
			"Action points",
			"action_points",
			PresetColour.GENERIC_ACTION_POINTS,
			"initiative",
			"lethargy",
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"How many action points [npc.nameHasFull] available to spend on moves in combat.");
		}

		@Override
		public int getOrderPriority() {
			return 70;
		}
	};

	public static AbstractAttribute ENCHANTMENT_LIMIT = new AbstractAttribute(false,
			0,
			0,
			1000,
			"ёмкость зачарования",
			"Ёмкость зачарования",
			"enchantmentLimitIcon",
			PresetColour.GENERIC_ENCHANTMENT,
			"освоение",
			"неуклюжесть",
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner,
					"Общее количество зачарований одежды и татуировок, которые [npc.nameIsFull] [npc.targetBasedWord(можешь, может)] выдержать, не накладывая больших штрафов.");
		}
		@Override
		public boolean isAffectedByEnchantmentCost() {
			return false;
		}
        @Override
		public int getOrderPriority() {
			return 80;
		}
	};

	// Sexual attributes:

	public static AbstractAttribute LUST = new AbstractAttribute(false,
			0,
			0,
			100,
			"похоть",
			"Похоть",
			"arousalIcon",
			PresetColour.ATTRIBUTE_LUST,
			"passion",
			"indifference",
			null) {
		@Override
		public boolean hasStatusEffect() {
			return true;
		}
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner.isPlayer()) {
				return "Насколько отчаянно ты жаждешь сексуального контакта. С течением времени похоть будет приближаться к минимальному значению.<br/>"
						+ "<b>Минимальное значение = " + GameCharacter.RESTING_LUST_CALCULATION + "</b>";
			} else {
				return UtilText.parse(owner,
						"Как отчаянно [npc.name] нуждается в сексуальном контакте.");
			}
		}
		@Override
		public int getOrderPriority() {
			return 100;
		}
	};
	
	public static AbstractAttribute RESTING_LUST = new AbstractAttribute(false,
			0,
			-100,
			80,
			"resting lust",
			"Resting lust",
			"arousalIcon",
			PresetColour.ATTRIBUTE_LUST,
			"passion",
			"indifference",
			null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return UtilText.parse(owner, "The amount of lust which [npc.name] naturally gravitates towards over a period of time.");
		}
		public int getOrderPriority() {
			return 110;
        }
	};

	public static AbstractAttribute AROUSAL = new AbstractAttribute(false,
			0,
			0,
			100,
			"arousal",
			"Arousal",
			"arousalIcon",
			PresetColour.ATTRIBUTE_AROUSAL,
			"long-lasting",
			"prematurity",
			null) {
		@Override
		public boolean hasStatusEffect() {
			return true;
		}
		@Override
		public String getDescription(GameCharacter owner) {
			if(owner.isPlayer())
				return "How aroused you currently are. You will orgasm when your arousal maxes out.";
			else
				return UtilText.parse(owner,
						"How aroused [npc.name] is. [npc.She] will orgasm when [npc.her] arousal maxes out.");
		}
		@Override
		public int getOrderPriority() {
			return 120;
		}
	};
	
	public static AbstractAttribute VIRILITY = new AbstractAttribute(true, 10, -100, 100, "вирильность", "Вирильность", "shieldIcon", PresetColour.GENERIC_SEX, "вирильность", "стерильность", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Повышает вероятность оплодотворения.";
		}

        @Override
		public int getOrderPriority() {
			return 130;
		}
	};

	public static AbstractAttribute FERTILITY = new AbstractAttribute(true, 10, -100, 100, "плодородие", "Плодородие", "shieldIcon", PresetColour.GENERIC_SEX, "плодородие", "бесплодие", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Повышает вероятность забеременеть.";
		}

		@Override
		public int getOrderPriority() {
			return 140;
		}
	};
// Combat attributes:

	public static AbstractAttribute SPELL_COST_MODIFIER = new AbstractAttribute(true, 0, 0, 80, "эффективность заклинаний", "Эффективность заклинаний", "shieldIcon", PresetColour.ATTRIBUTE_MANA, "мастерство", "некомпетентность", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Уменьшает стоимость произнесения заклинаний.";
		}

		@Override
		public int getOrderPriority() {
			return 200;
		}
	};

	public static AbstractAttribute CRITICAL_DAMAGE = new AbstractAttribute(true, 150, 100, 500, "критический урон", "Критический урон", "shieldIcon", PresetColour.ATTRIBUTE_HEALTH, "влияние", "провал", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Каждое очко дает 1% дополнительного критического урона.";
		}

		@Override
		public int getOrderPriority() {
			return 210;
		}
	};

	// Damages:

	public static AbstractAttribute DAMAGE_UNARMED = new AbstractAttribute(true, 0, -80, 100, "безоружный урон", "Безоружный урон", "swordIcon", PresetColour.DAMAGE_TYPE_UNARMED, "martial arts", "martial incompetence", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases damage dealt from unarmed attacks, including special attacks obtained from non-human bodyparts.";
		}

		@Override
		public int getOrderPriority() {
			return 220;
		}
	};
	public static AbstractAttribute DAMAGE_MELEE_WEAPON = new AbstractAttribute(true, 0, -80, 100, "урон в ближнем бою", "Урон в ближнем бою", "swordIcon", PresetColour.DAMAGE_TYPE_MELEE, "melee mastery", "melee incompetence", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases damage dealt from attacks by melee weapons.";
		}

		@Override
		public int getOrderPriority() {
			return 230;
		}
	};

	public static AbstractAttribute DAMAGE_RANGED_WEAPON = new AbstractAttribute(true, 0, -80, 100, "урон в дали", "Урон в дали", "swordIcon", PresetColour.DAMAGE_TYPE_RANGED, "ranged mastery", "ranged incompetence", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases damage dealt from attacks by ranged weapons.";
		}

		@Override
		public int getOrderPriority() {
			return 240;
		}
	};

	public static AbstractAttribute DAMAGE_SPELLS = new AbstractAttribute(true, 0, -80, 100, "урон заклинаний", "Урон заклинаний", "swordIcon", PresetColour.ATTRIBUTE_MANA, "arcane power", "arcane dulling", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases spell damage.";
		}

		@Override
		public int getOrderPriority() {
			return 250;
		}
	};

	public static AbstractAttribute DAMAGE_PHYSICAL = new AbstractAttribute(true, 0, -80, 100, "physical damage", "Physical damage", "swordIcon", PresetColour.DAMAGE_TYPE_PHYSICAL, "force", "softness", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases physical damage.";
		}

		@Override
		public int getOrderPriority() {
			return 260;
		}
	};

	public static AbstractAttribute DAMAGE_LUST = new AbstractAttribute(true, 0, -80, 100, "lust damage", "Lust damage", "swordIcon", PresetColour.GENERIC_SEX, "seduction", "repulsion", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases lust damage.";
		}

		@Override
		public int getOrderPriority() {
			return 270;
		}
	};

	public static AbstractAttribute DAMAGE_FIRE = new AbstractAttribute(true, 0, -80, 100, "fire damage", "Fire damage", "swordIcon", PresetColour.DAMAGE_TYPE_FIRE, "inferno", "dying embers", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases fire damage.";
		}

		@Override
		public int getOrderPriority() {
			return 280;
		}
	};

	public static AbstractAttribute DAMAGE_ICE = new AbstractAttribute(true, 0, -80, 100, "cold damage", "Cold damage", "swordIcon", PresetColour.DAMAGE_TYPE_COLD, "blizzard", "slush", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases cold damage.";
		}

		@Override
		public int getOrderPriority() {
			return 290;
		}
	};

	public static AbstractAttribute DAMAGE_POISON = new AbstractAttribute(true, 0, -80, 100, "poison damage", "Poison damage", "swordIcon", PresetColour.DAMAGE_TYPE_POISON, "venom", "dilution", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases poison damage.";
		}

		@Override
		public int getOrderPriority() {
			return 300;
		}
	};


	public static AbstractAttribute ENERGY_SHIELDING = new AbstractAttribute(false, 0, -100, 500, "защита здоровья", "Защита здоровья", "shieldIcon", PresetColour.ATTRIBUTE_HEALTH, "выносливость", "уязвимость", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Это значение применяется к защите здоровья в начале каждого боевого хода.";
		}
		@Override
		public boolean isInfiniteAtUpperLimit() {
			return true;
		}
		@Override
		public String getInfiniteDescription() {
			return "[style.colourExcellent(Невосприимчивость)] ко [style.colourHealth(всем повреждениям)]";
		}
		@Override
		public int getOrderPriority() {
			return 400;
		}
	};

	
	// Resistances:

	public static AbstractAttribute RESISTANCE_PHYSICAL = new AbstractAttribute(false, 0, -100, 500, "физическая защита", "Физическая защита", "shieldIcon", PresetColour.DAMAGE_TYPE_PHYSICAL, "toughness", "softness", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Reduces physical damage taken.";
		}
		@Override
		public boolean isInfiniteAtUpperLimit() {
			return true;
		}
		@Override
		public String getInfiniteDescription() {
			return "[style.colourExcellent(Immune)] to [style.colourPhysical(physical damage)]";
		}
		@Override
		public int getOrderPriority() {
			return 410;
		}
	};

	public static AbstractAttribute RESISTANCE_LUST = new AbstractAttribute(false, 0, -100, 500, "защита от похоти", "Защита от похоти", "shieldIcon", PresetColour.GENERIC_SEX, "chastity", "temptation", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Reduces lust damage taken.";
		}
		@Override
		public boolean isInfiniteAtUpperLimit() {
			return true;
		}
		@Override
		public String getInfiniteDescription() {
			return "[style.colourExcellent(Immune)] to [style.colourLust(lust damage)]";
		}
		@Override
		public int getOrderPriority() {
			return 420;
		}
	};

	public static AbstractAttribute RESISTANCE_FIRE = new AbstractAttribute(false, 0, -100, 500, "защита от огня", "Защита от огня", "shieldIcon", PresetColour.DAMAGE_TYPE_FIRE, "extinguishing", "flammability", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Reduces fire damage taken.";
		}
		@Override
		public boolean isInfiniteAtUpperLimit() {
			return true;
		}
		@Override
		public String getInfiniteDescription() {
			return "[style.colourExcellent(Immune)] to [style.colourFire(fire damage)]";
		}
		@Override
		public int getOrderPriority() {
			return 430;
		}
	};

	public static AbstractAttribute RESISTANCE_ICE = new AbstractAttribute(false, 0, -100, 500, "защита от холода", "Защита от холода", "shieldIcon", PresetColour.DAMAGE_TYPE_COLD, "warmth", "frostbite", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Reduces cold damage taken.";
		}
		@Override
		public boolean isInfiniteAtUpperLimit() {
			return true;
		}
		@Override
		public String getInfiniteDescription() {
			return "[style.colourExcellent(Immune)] to [style.colourIce(ice damage)]";
		}
		@Override
		public int getOrderPriority() {
			return 440;
		}
	};

	public static AbstractAttribute RESISTANCE_POISON = new AbstractAttribute(false, 0, -100, 500, "защита от яда", "Защита от яда", "shieldIcon", PresetColour.DAMAGE_TYPE_POISON, "anti-venom", "sickness", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Reduces poison damage taken.";
		}
		@Override
		public boolean isInfiniteAtUpperLimit() {
			return true;
		}
		@Override
		public String getInfiniteDescription() {
			return "[style.colourExcellent(Immune)] to [style.colourPoison(poison damage)]";
		}
		@Override
		public int getOrderPriority() {
			return 450;
		}
	};
	
	// From v0.4, these are automatically generated in the static block at the end of the Race.java class!
//	// Racial:
//	
//	public static AbstractAttribute DAMAGE_ANGEL = new AbstractAttribute(true, 0, -100, 100, "angelic damage", "Angelic damage", "swordIcon", PresetColour.RACE_ANGEL, "angelic-obliteration", "angelic-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs angels.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_CAT_MORPH = new AbstractAttribute(true, 0, -100, 100, "cat-morph damage", "Cat-morph damage", "swordIcon", PresetColour.RACE_CAT_MORPH, "cat-morph-obliteration", "cat-morph-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs cat-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_COW_MORPH = new AbstractAttribute(true, 0, -100, 100, "cow-morph damage", "Cow-morph damage", "swordIcon", PresetColour.RACE_COW_MORPH, "cow-morph-obliteration", "cow-morph-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs cow-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_DEMON = new AbstractAttribute(true, 0, -100, 100, "demonic damage", "Demonic damage", "swordIcon", PresetColour.RACE_DEMON, "demonic-obliteration", "demonic-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs demons.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_DOG_MORPH = new AbstractAttribute(true, 0, -100, 100, "dog-morph damage", "Dog-morph damage", "swordIcon", PresetColour.RACE_DOG_MORPH, "dog-morph-obliteration", "dog-morph-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs dog-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_HARPY = new AbstractAttribute(true, 0, -100, 100, "harpy damage", "Harpy damage", "swordIcon", PresetColour.RACE_HARPY, "harpy-obliteration", "harpy-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs harpies.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_HORSE_MORPH = new AbstractAttribute(true, 0, -100, 100, "horse-morph damage", "Horse-morph damage", "swordIcon", PresetColour.RACE_HORSE_MORPH, "horse-morph-obliteration", "horse-morph-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs horse-morphs.";
//		}
//	};

	public static AbstractAttribute DAMAGE_ELDER_LILIN = new AbstractAttribute(true, 0, -100, 100, "elder lilin damage", "Elder lilin damage", "swordIcon", PresetColour.RACE_LILIN, "elder-lilin-obliteration", "elder-lilin-mercy", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases damage vs elder lilin.";
		}

		@Override
		public int getOrderPriority() {
			return 1000;
		}
	};

	public static AbstractAttribute DAMAGE_LILIN = new AbstractAttribute(true, 0, -100, 100, "lilin damage", "Lilin damage", "swordIcon", PresetColour.RACE_LILIN, "lilin-obliteration", "lilin-mercy", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases damage vs lilin.";
		}

		@Override
		public int getOrderPriority() {
			return 1100;
		}
	};

	public static AbstractAttribute DAMAGE_IMP = new AbstractAttribute(true, 0, -100, 100, "imp damage", "Imp damage", "swordIcon", PresetColour.RACE_IMP, "impish-obliteration", "impish-mercy", null) {
		@Override
		public String getDescription(GameCharacter owner) {
			return "Increases damage vs imps.";
		}

		@Override
		public int getOrderPriority() {
			return 1200;
		}
	};

//	public static AbstractAttribute DAMAGE_REINDEER_MORPH = new AbstractAttribute(true, 0, -100, 100, "reindeer-morph damage", "Reindeer-morph damage", "swordIcon", PresetColour.RACE_REINDEER_MORPH, "reindeer-morph-obliteration", "reindeer-morph-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs reindeer-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_HUMAN = new AbstractAttribute(true, 0, -100, 100, "human damage", "Human damage", "swordIcon", PresetColour.RACE_HUMAN, "human-obliteration", "human-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs humans.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_SQUIRREL_MORPH = new AbstractAttribute(true, 0, -100, 100, "squirrel-morph damage", "Squirrel-morph damage", "swordIcon", PresetColour.RACE_SQUIRREL_MORPH, "squirrel-morph-obliteration", "squirrel-morph-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs squirrel-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_RAT_MORPH = new AbstractAttribute(true, 0, -100, 100, "rat-morph damage", "Rat-morph damage", "swordIcon", PresetColour.RACE_RAT_MORPH, "rat-morph-obliteration", "rat-morph-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs rat-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_RABBIT_MORPH = new AbstractAttribute(true, 0, -100, 100, "rabbit-morph damage", "Rabbit-morph damage", "swordIcon", PresetColour.RACE_RABBIT_MORPH, "rabbit-morph-obliteration", "rabbit-morph-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs rabbit-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_BAT_MORPH = new AbstractAttribute(true, 0, -100, 100, "bat-morph damage", "Bat-morph damage", "swordIcon", PresetColour.RACE_BAT_MORPH, "bat-morph-obliteration", "bat-morph-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs bat-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_ALLIGATOR_MORPH = new AbstractAttribute(true, 0, -100, 100, "alligator-morph damage", "Alligator-morph damage", "swordIcon", PresetColour.RACE_ALLIGATOR_MORPH, "alligator-morph-obliteration", "alligator-morph-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs alligator-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_WOLF_MORPH = new AbstractAttribute(true, 0, -100, 100, "wolf-morph damage", "Wolf-morph damage", "swordIcon", PresetColour.RACE_WOLF_MORPH, "wolf-morph-obliteration", "wolf-morph-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs wolf-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_FOX_MORPH = new AbstractAttribute(true, 0, -100, 100, "fox-morph damage", "Fox-morph damage", "swordIcon", PresetColour.RACE_FOX_MORPH, "fox-morph-obliteration", "fox-morph-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs fox-morphs.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_SLIME = new AbstractAttribute(true, 0, -100, 100, "slime damage", "Slime damage", "swordIcon", PresetColour.RACE_SLIME, "slime-obliteration", "slime-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs slimes.";
//		}
//	};
//	public static AbstractAttribute DAMAGE_ELEMENTAL = new AbstractAttribute(true, 0, -100, 100, "elemental damage", "Elemental damage", "swordIcon", PresetColour.SPELL_SCHOOL_ARCANE, "elemental-obliteration", "elemental-mercy", null) {
//		@Override
//		public String getDescription(GameCharacter owner) {
//			return "Increases damage vs elementals.";
//		}
//	};
	
	
	public static Map<AbstractAttribute, String> attributeToIdMap = new HashMap<>();
	public static Map<String, AbstractAttribute> idToAttributeMap = new HashMap<>();
	public static List<AbstractAttribute> allAttributes;
	
	public static Map<AbstractRace, AbstractAttribute> racialAttributes = new HashMap<>();

	private static final Map<String, AbstractAttribute> oldConversionMapping = new HashMap<>();
	static {
		oldConversionMapping.put("CORRUPTION", Attribute.MAJOR_CORRUPTION);
		oldConversionMapping.put("STRENGTH", Attribute.MAJOR_PHYSIQUE);
		oldConversionMapping.put("MAJOR_STRENGTH", Attribute.MAJOR_PHYSIQUE);
		oldConversionMapping.put("INTELLIGENCE", Attribute.MAJOR_ARCANE);
		oldConversionMapping.put("RESISTANCE_ATTACK", Attribute.RESISTANCE_PHYSICAL);
		oldConversionMapping.put("RESISTANCE_MANA", Attribute.RESISTANCE_LUST);
		oldConversionMapping.put("RESISTANCE_PURE", Attribute.ENERGY_SHIELDING);
	}

	/**
	 * @return The Attribute that has an id closest to the supplied attributeId.
	 *  <b>Will return null</b> if the matching distance is greater than 3 (which typically will be more than enough to catch spelling errors, indicating that the flag has been removed).
	 */
	public static AbstractAttribute getAttributeFromId(String attributeId) {
		if(attributeId.startsWith("RESISTANCE_ELEMENTAL")) {
			attributeId = "RESISTANCE_ELEMENTAL";
		} else if(attributeId.startsWith("DAMAGE_ELEMENTAL")) {
			attributeId = "DAMAGE_ELEMENTAL";
		} else if(attributeId.startsWith("CRITICAL_CHANCE")) { // Critical chance was removed, so return damage instead as a replacement for old saves
			attributeId = "CRITICAL_DAMAGE";
		}
		
		if(oldConversionMapping.containsKey(attributeId)) {
			return oldConversionMapping.get(attributeId);
		}

		attributeId = Util.getClosestStringMatch(attributeId, idToAttributeMap.keySet(), 3);
		
		return idToAttributeMap.get(attributeId);
	}

	public static String getIdFromAttribute(AbstractAttribute attribute) {
		return attributeToIdMap.get(attribute);
	}

	public static List<AbstractAttribute> getAllAttributes() {
		return allAttributes;
	}
	
	public static AbstractAttribute getRacialDamageAttribute(AbstractRace race) {
		return racialAttributes.get(race);
	}
	
	static {
		allAttributes = new ArrayList<>();
		
		// Hard-coded attributes (all those up above):
		
		Field[] fields = Attribute.class.getFields();
		
		for(Field f : fields) {
			if (AbstractAttribute.class.isAssignableFrom(f.getType())) {
				AbstractAttribute attribute;
				try {
					attribute = ((AbstractAttribute) f.get(null));

					attributeToIdMap.put(attribute, f.getName());
					idToAttributeMap.put(f.getName(), attribute);
					allAttributes.add(attribute);
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
		
		// NOTE: Racial attributes are added at the bottom of the static block in Race.java!
	}
	
}
