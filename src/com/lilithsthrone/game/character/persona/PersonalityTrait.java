package com.lilithsthrone.game.character.persona;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;

import java.util.ArrayList;
import java.util.List;

/***
 * @since 0.2.4
 * @version 0.4.9
 * @author Innoxia
 */
public enum PersonalityTrait {
	
	// Core traits:

	CONFIDENT(false, PersonalityCategory.CORE, "уверенность", "[npc.NameIsFull] очень [npc.genderBasedWord(напористый, напористая)] и [npc.genderBasedWord(уверенный, уверенная)] в себе.", "", PresetColour.BASE_GREEN_LIME) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(SHY);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(CONFIDENT)
							? "[style.colourDisabled([npc.Name] [npc.isFull] уже [npc.genderBasedWord(уверен, уверена)] в себе, поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorGood(чувствует себя намного увереннее)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(CONFIDENT)
							? "[style.colourDisabled([npc.Name] уже не имеет уверенности, поэтому ничего не происходит...)]"
							: "[npc.Name] [style.colourMinorBad([npc.targetBasedWord(чувствуешь, чувствует)] себя очень неуверенно)]!")
						+ "</p>");
		}
	},

	SHY(false, PersonalityCategory.CORE, "застенчивость", "[npc.NameIsFull] очень [npc.genderBasedWord(застенчивый, застенчивая)] в окружении других людей и, по возможности, [npc.targetBasedWord(будешь, будет)] избегать разговоров.", "", PresetColour.BASE_YELLOW_LIGHT) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(CONFIDENT);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(SHY)
							? "[style.colourDisabled([npc.Name] [npc.isFull] уже [npc.targetBasedWord(стесняешься, стесняется)], поэтому ничего не происходит...)]"
							: "[npc.Name] [style.colourMinorGood([npc.targetBasedWord(чувствуешь, чувствует)] себя намного застенчивее)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(SHY)
							? "[style.colourDisabled([npc.Name] уже не [npc.targetBasedWord(страдаешь, страдает)] от застенчивости, поэтому ничего не происходит....)]"
							: "[npc.Name] [style.colourMinorBad([npc.targetBasedWord(теряешь, теряет)] свою застенчивость)]!")
						+ "</p>");
		}
	},

	KIND(false, PersonalityCategory.CORE, "доброта", "[npc.Name] всегда [npc.targetBasedWord(пытаешься, пытается)] проявлять доброту к людям, иногда даже жертвуя своим счастьем.", "", PresetColour.BASE_GREEN) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(SELFISH);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(KIND)
							? "[style.colourDisabled([npc.Name] [npc.isFull] уже [npc.genderBasedWord(добрый, добрая)], поэтому ничего не происходит...)]"
							: "[npc.Name] [style.colourMinorGood([npc.targetBasedWord(чувствуешь, чувствует)] себя намного добрее)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(KIND)
							? "[style.colourDisabled([npc.Name] уже не очень [npc.genderBasedWord(добрый, добрая)], поэтому ничего не происходит...)]"
							: "[npc.Name] [style.colourMinorBad([npc.targetBasedWord(теряешь, теряет)] свою доброту)]!")
						+ "</p>");
		}
	},

	SELFISH(false, PersonalityCategory.CORE, "эгоизм", "[npc.Name] всегда [npc.targetBasedWord(ставишь, ставит)] себя на первое место, и не [npc.targetBasedWord(будешь, будет)] делать то, что не приведет к личной выгоде.", "", PresetColour.BASE_RED) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(KIND);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(SELFISH)
							? "[style.colourDisabled([npc.Name] [npc.isFull] уже [npc.genderBasedWord(эгоист, эгоистка)], поэтому ничего не происходит...)]"
							: "[npc.Name] [style.colourMinorGood([npc.targetBasedWord(чувствуешь, чувствует)] себя гораздо эгоистичнее)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(SELFISH)
							? "[style.colourDisabled([npc.Name] уже не [npc.genderBasedWord(эгоистичен, эгоистична)], поэтому ничего не происходит...)]"
							: "[npc.Name] [style.colourMinorBad([npc.targetBasedWord(теряешь, теряет)] свою эгоистичность)]!")
						+ "</p>");
		}
	},

	NAIVE(false, PersonalityCategory.CORE, "наивность", "Нехватка жизненного опыта и воли, [npc.name] абсолютно не [npc.targetBasedWord(понимаешь, понимает)] жестокость реальности.", "", PresetColour.BASE_PINK_LIGHT) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(CYNICAL);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(NAIVE)
							? "[style.colourDisabled([npc.Name] [npc.isFull] уже [npc.genderBasedWord(наивен, наивна)], поэтому ничего не происходит...)]"
							: "[npc.Name] [style.colourMinorGood(чувствует себя гораздо наивнее)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(NAIVE)
							? "[style.colourDisabled([npc.Name] уже не [npc.genderBasedWord(наивен, наивна)], поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorBad(теряет свою наивность)]!")
						+ "</p>");
		}
	},

	CYNICAL(false, PersonalityCategory.CORE, "циничность", "[npc.NameIsFull] особенно не [npc.targetBasedWord(доверяешь, доверяет)] намерениям и мотивам других людей.", "", PresetColour.BASE_RED_DARK) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(NAIVE);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(CYNICAL)
							? "[style.colourDisabled([npc.Name] [npc.isFull] уже [npc.genderBasedWord(циничен, цинична)], поэтому ничего не происходит...)]"
							: "[npc.Name] [style.colourMinorGood(чувствует себя гораздо циничнее)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(CYNICAL)
							? "[style.colourDisabled([npc.Name] не [npc.genderBasedWord(циничен, цинична)], поэтому ничего не происходит....)]"
								:"[npc.Name] [style.colourMinorBad(теряет свой цинизм)]!")
						+ "</p>");
		}
	},
	
	// Combat traits:

	BRAVE(false, PersonalityCategory.COMBAT, "храбрость", "[npc.Name] всегда [npc.targetBasedWord(действуешь, действует)] в отважной манере, и никогда не [npc.targetBasedWord(боишься, боится)] боя.", "", PresetColour.BASE_ORANGE) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(COWARDLY);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(BRAVE)
							? "[style.colourDisabled([npc.Name] [npc.isFull] уже [npc.genderBasedWord(храбрый, храбрая)], поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorGood(чувствует себя намного храбрее)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(BRAVE)
							? "[style.colourDisabled([npc.Name] не имеет храбрости, поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorBad(теряет свою храбрость)]!")
						+ "</p>");
		}
	},

	COWARDLY(false, PersonalityCategory.COMBAT, "трусость", "[npc.Name] легко [npc.targetBasedWord(пугаешься, пугается)] и [npc.targetBasedWord(предпочитаешь, предпочитает)] избегать конфликтов.", "", PresetColour.BASE_RED_LIGHT) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(BRAVE);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(COWARDLY)
							? "[style.colourDisabled([npc.Name] [npc.isFull] уже [npc.genderBasedWord(труслив, труслива)], поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorGood(чувствует себя гораздо трусливее)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(COWARDLY)
							? "[style.colourDisabled([npc.Name] не [npc.genderBasedWord(труслив, труслива)], поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorBad(теряет свою трусость)]!")
						+ "</p>");
		}
	},

	// Sex traits:
	
	LEWD(false,
			PersonalityCategory.SEX,
			"развратность",
			"[npc.NameHasFull] [npc.targetBasedWord(имеешь, имеет)] глубокое познание о сексе и никогда не [npc.targetBasedWord(откажешься, откажется)] от эротических разговоров.",
			"", PresetColour.BASE_PINK) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(PRUDE, INNOCENT);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(LEWD)
							? "[style.colourDisabled([npc.Name] [npc.isFull] уже [npc.genderBasedWord(развратен, развратна)], поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorGood(чувствует себя еще более развратно)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(LEWD)
							? "[style.colourDisabled([npc.Name] не [npc.genderBasedWord(развратен, развратна)], поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorBad(теряет свою развратность)]!")
						+ "</p>");
		}
	},
	
	INNOCENT(false,
			PersonalityCategory.SEX,
			"невинность",
			"[npc.Name] всегда [npc.targetBasedWord(смущаешься, смущается)] при упоминании вещей связанных с сексом.",
			"", PresetColour.BASE_BLUE_LIGHT) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(LEWD, PRUDE);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(INNOCENT)
							? "[style.colourDisabled([npc.Name] [npc.isFull] уже [npc.genderBasedWord(невинен, невинна)], поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorGood(чувствует себя гораздо невиннее)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(INNOCENT)
							? "[style.colourDisabled([npc.Name] не [npc.genderBasedWord(невинен, невинна)], поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorBad(теряет свою невинность)]!")
						+ "</p>");
		}
	},
	
	PRUDE(false,
			PersonalityCategory.SEX,
			"ханжа",
            "[npc.Name] не [npc.targetBasedWord(любишь, любит)] говорить о вещах связанных с сексом и [npc.targetBasedWord(отказываешься, отказывается)] признавать что [npc.targetBasedWord(имеешь, имеет)] какие-либо знания из этой области.",
			"", PresetColour.BASE_BLUE_STEEL) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(LEWD, INNOCENT);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(INNOCENT)
								?"[style.colourDisabled([npc.Name] [npc.isFull] уже ханжа, поэтому ничего не происходит...)]"
							: "[npc.Name] [style.colourMinorGood(чувствует себя гораздо более ханжой)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(INNOCENT)
								?"[style.colourDisabled([npc.Name] уже не ханжа, поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorBad(теряет свои ханжеские манеры)]!")
						+ "</p>");
		}
	},

	// Speech traits:
	
	LISP(false,
			PersonalityCategory.SPEECH,
			"картавость",
            "[npc.Name] [npc.targetBasedWord(говоришь, говорит)] картаво, произнося 'с' и 'з' как 'с'.",
            "[style.italicsBad(Все [npc.targetBasedWord(твои, [npc.namePos])])] разговоры буду изменятся под действием картавости! (Не переведено!!!)]", PresetColour.BASE_PURPLE_LIGHT) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(MUTE);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(LISP)
								?"[style.colourDisabled([npc.Name] уже говорит картаво, поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorBad(говорит с картавостью)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(LISP)
							? "[style.colourDisabled([npc.Name] уже не говорит картаво, поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorGood(может говорить без картавости)]!")
						+ "</p>");
		}
	},

	STUTTER(false,
			PersonalityCategory.SPEECH,
			"заика",
			"[npc.NameHasFull] привычку заикаться и ошибаться.",
            "[style.italicsBad(Все [npc.targetBasedWord(твои, [npc.namePos])])] разговоры буду изменятся под действием заикания! (Не переведено!!!))]", PresetColour.BASE_PINK_SALMON) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(MUTE);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(STUTTER)
								?"[style.colourDisabled([npc.Name] уже говорит с заиканием, поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorBad(говорит с заиканием)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(STUTTER)
								?"[style.colourDisabled([npc.Name] уже не заикается, поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorGood(может говорить без заикания)]!")
						+ "</p>");
		}
	},

	MUTE(true,
			PersonalityCategory.SPEECH,
			"немота",
			"[npc.NameIsFull] обладает немотой, и хотя [npc.she] может издавать некоторые похотливые звуки в порыве страсти, в остальное время [npc.she] полностью не может говорить.",
			"[style.italicsBad((Все игровые реплики [npc.namePos] будут удалены!)]", PresetColour.BASE_CRIMSON) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(LISP, STUTTER, SLOVENLY);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(STUTTER)
								?"[style.colourDisabled([npc.NameIsFull] уже обладает немотой, поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorBad(не может говорить)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(STUTTER)
								?"[style.colourDisabled([npc.NameIsFull] уже может говорить, поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorGood(может говорить)]!")
						+ "</p>");
		}
	},

	SLOVENLY(false,
			PersonalityCategory.SPEECH,
			"неряшливость",
            "[npc.Name] [npc.targetBasedWord(говоришь, говорит)] очень неряшливо; проглатывая слоги и имея плохую дикцию, [npc.targetBasedWord(твою, [npc.her])] речь часто бывает очень трудно понять.",
			"[style.italicsBad(Это повлияет на всю игровую речь [npc.namePos]!)]", PresetColour.BASE_BROWN) {
		@Override
		public List<PersonalityTrait> getMutuallyExclusiveSettings() {
			return Util.newArrayListOfValues(MUTE);
		}
		@Override
		public String getAdditionDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (target.hasPersonalityTrait(SLOVENLY)
								?"[style.colourDisabled([npc.Name] уже говорит в неряшливой манере, поэтому ничего не происходит...)]"
                            : "[npc.Name] find [npc.herself] [style.colourMinorBad(говорит в неряшливой манере)]!")
						+ "</p>");
		}
		@Override
		public String getRemovalDescription(GameCharacter target) {
			return UtilText.parse(target,
					"<p style='text-align:center;'>"
							+ (!target.hasPersonalityTrait(SLOVENLY)
								?"[style.colourDisabled([npc.Name] уже не говорит в неряшливой манере, поэтому ничего не происходит...)]"
								:"[npc.Name] [style.colourMinorGood(юольше не говорит в неряшливой манере)]!")
						+ "</p>");
		}
	},;
	
	private final boolean specialRequirements;
	private final PersonalityCategory personalityCategory;
	private final String name;
	private final String description;
	private final String gameplayInformation;
	private final Colour colour;
	
	PersonalityTrait(boolean specialRequirements, PersonalityCategory personalityCategory, String name, String description, String gameplayInformation, Colour colour) {
		this.specialRequirements = specialRequirements;
		this.personalityCategory = personalityCategory;
		this.name = name;
		this.description = description;
		this.gameplayInformation = gameplayInformation;
		this.colour = colour;
	}
	
	public PersonalityCategory getPersonalityCategory() {
		return personalityCategory;
	}

	public String getName() {
		return name;
	}
	
	public String getDescription(GameCharacter character, boolean withGameplayInformation, boolean withMutuallyExclusiveInformation) {
		StringBuilder sb = new StringBuilder();

		sb.append(UtilText.parse(character, description));
		
		if(withGameplayInformation) {
			if(gameplayInformation!=null && !gameplayInformation.isEmpty()) {
				sb.append("<br/>"+UtilText.parse(character, gameplayInformation));
			} else {
				sb.append("<br/>[style.italicsDisabled(На данный момент не влияет на игру...)]");
			}
		}
		
		if(withMutuallyExclusiveInformation) {
			if(!this.getMutuallyExclusiveSettings().isEmpty()) {
				sb.append("<br/>[style.colourBad(Взаимоисключающе)] с ");
				List<String> names = new ArrayList<>();
				for(PersonalityTrait trait : this.getMutuallyExclusiveSettings()) {
					names.add("<span style='color:"+trait.getColour().toWebHexString()+";'>"+trait.getName()+"</span>");
				}
				sb.append(Util.stringsToStringList(names, false)+"!");
			}
		}
		
		return sb.toString();
	}
	
	public Colour getColour() {
		return colour;
	}

	public abstract List<PersonalityTrait> getMutuallyExclusiveSettings();

	public abstract String getAdditionDescription(GameCharacter target);
	
	public abstract String getRemovalDescription(GameCharacter target);
	
	public boolean isSpecialRequirements() {
		return specialRequirements;
	}
}
