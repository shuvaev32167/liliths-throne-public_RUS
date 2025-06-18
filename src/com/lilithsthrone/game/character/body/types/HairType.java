package com.lilithsthrone.game.character.body.types;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.body.abstractTypes.AbstractHairType;
import com.lilithsthrone.game.character.body.coverings.BodyCoveringType;
import com.lilithsthrone.game.character.body.tags.BodyPartTag;
import com.lilithsthrone.game.character.race.AbstractRace;
import com.lilithsthrone.game.character.race.Race;
import com.lilithsthrone.utils.Util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

/**
 * @since 0.1.83
 * @version 0.3.9.1
 * @author Innoxia
 */
public class HairType {

	public static AbstractHairType HUMAN = new AbstractHairType(BodyCoveringType.HAIR_HUMAN,
			Race.HUMAN,
			"человеческие",
			"волосы",
			"волосы",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues("мягкие", "женственные"),
			"Превращение длится всего несколько мгновений, после чего у [npc.herHim] остаются волосы, похожие на человеческие.<br/>"
					+ "[npc.Name] теперь [npc.has] [npc.hairColour], [style.boldHuman(человеческие волосы)].",
			"У [npc.targetBasedWord(тебя, н[npc.him])] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], человеческие волосы",
            Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX),
            true) {
	};

	public static AbstractHairType ANGEL = new AbstractHairType(BodyCoveringType.HAIR_ANGEL,
			Race.ANGEL,
			"angelic",
			"hair",
			"hairs",
			Util.newArrayListOfValues(""),
			Util.newArrayListOfValues("silken", "soft", "feminine"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] [npc.targetBasedWord(остаёшься, остаётся)] с silken, angelic hair.<br/>"
					+ "[npc.Name] now [npc.has] [npc.hairColour], [style.boldAngel(angelic hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], angelic hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};
	
	public static AbstractHairType DEMON = new AbstractHairType(BodyCoveringType.HAIR_DEMON,
			Race.DEMON,
            "демонические",
            "волосы",
            "волосы",
			Util.newArrayListOfValues(""),
            Util.newArrayListOfValues("шелковистые", "мягкие", "женственные"),
			"#IF(npc.isShortStature())"
                    + "Превращение длится всего несколько мгновений, после чего у [npc.herHim] [npc.targetBasedWord(остаёшься, остаётся)] с шелковистыми, импитскими волосами.<br/>"
                    + "[npc.Name] теперь [npc.has] [npc.hairColour] [style.boldImp(импитские волосы)]."
			+ "#ELSE"
                    + "Превращение длится всего несколько мгновений, после чего у [npc.herHim] [npc.targetBasedWord(остаёшься, остаётся)] с шелковистыми, демоническими волосами.<br/>"
                    + "[npc.Name] теперь [npc.has] [npc.hairColour], [style.boldDemon(демонические волосы)]."
			+ "#ENDIF",
			"У [npc.targetBasedWord(тебя, н[npc.him])] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], #IF(npc.isShortStature())импитские#ELSEдемонические#ENDIF волосы",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX),
			true) {
	};

	public static AbstractHairType DOG_MORPH = new AbstractHairType(BodyCoveringType.HAIR_CANINE_FUR,
			Race.DOG_MORPH,
			"dog",
			"hair",
			"hairs",
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] остаются волосы, похожие на мех.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldDogMorph(dog hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], dog-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};
	
	public static AbstractHairType WOLF_MORPH = new AbstractHairType(BodyCoveringType.HAIR_LYCAN_FUR, //TODO rename
			Race.WOLF_MORPH,
			"wolf",
			"hair",
			"hairs",
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] остаются волосы, похожие на мех.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldWolfMorph(wolf hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], wolf-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
		@Override
		public boolean isNeckFluffRequiresGreater() {
			return true;
		}
		@Override
		public double getNeckFluffChance() {
			return 0.25f;
		}
	};

	public static AbstractHairType FOX_MORPH = new AbstractHairType(BodyCoveringType.HAIR_FOX_FUR,
			Race.FOX_MORPH,
			"fox",
			"hair",
			"hairs",
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] остаются волосы, похожие на мех.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldFoxMorph(fox hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], fox-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
		@Override
		public boolean isNeckFluffRequiresGreater() {
			return true;
		}
		@Override
		public double getNeckFluffChance() {
			return 0.25f;
		}
	};

	public static AbstractHairType CAT_MORPH = new AbstractHairType(BodyCoveringType.HAIR_FELINE_FUR, //TODO change to cat
			Race.CAT_MORPH,
            "кошачьи",
            "волосы",
            "волосы",
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] остаются волосы, похожие на мех.<br/>"
                    + "[npc.Name] теперь [npc.has] [npc.hairColour] [style.boldCatMorph(кошачьи волосы)].",
            "У [npc.targetBasedWord(тебя, н[npc.him])] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], волосы как у кошки",
            Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX),
            true) {
	};

	//TODO should be PANTHER
	public static AbstractHairType CAT_MORPH_SIDEFLUFF = new AbstractHairType(BodyCoveringType.HAIR_FELINE_FUR,
			Race.CAT_MORPH,
			"cat (sidefluff)",
			"hair",
			"hairs",
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] остаются волосы, похожие на мех.<br/>"
                    + "[npc.Name] теперь [npc.has] [npc.hairColour] [style.boldCatMorph(кошачьи волосы)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], cat-like hair, complete with soft, fuzzy fur on the sides of [npc.her] face",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType COW_MORPH = new AbstractHairType(BodyCoveringType.HAIR_BOVINE_FUR, //TODO change to cow
			Race.COW_MORPH,
			"cow",
			"hair",
			"hairs",
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] остаются волосы, похожие на мех.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldCowMorph(cow hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], cow-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType ALLIGATOR_MORPH = new AbstractHairType(BodyCoveringType.HAIR_SCALES_ALLIGATOR, //TODO change to hair
			Race.ALLIGATOR_MORPH,
			"alligator",
			"hair",
			"hairs",
			Util.newArrayListOfValues("coarse"),
			Util.newArrayListOfValues("coarse"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] [npc.targetBasedWord(остаёшься, остаётся)] с coarse hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldAlligatorMorph(alligator hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], coarse alligator hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType SQUIRREL_MORPH = new AbstractHairType(BodyCoveringType.HAIR_SQUIRREL_FUR,
			Race.SQUIRREL_MORPH,
			"squirrel",
			"hair",
			"hairs",
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] остаются волосы, похожие на мех.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldSquirrelMorph(squirrel hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], squirrel-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType RAT_MORPH = new AbstractHairType(BodyCoveringType.HAIR_RAT_FUR,
			Race.RAT_MORPH,
			"rat",
			"hair",
			"hairs",
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] остаются волосы, похожие на мех.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldRatMorph(rat hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], rat-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType RABBIT_MORPH = new AbstractHairType(BodyCoveringType.HAIR_RABBIT_FUR,
			Race.RABBIT_MORPH,
			"rabbit",
			"hair",
			"hairs",
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] остаются волосы, похожие на мех.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldRabbitMorph(rabbit hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], rabbit-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType BAT_MORPH = new AbstractHairType(BodyCoveringType.HAIR_BAT_FUR,
			Race.BAT_MORPH,
			"bat",
			"hair",
			"hairs",
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] остаются волосы, похожие на мех.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldBatMorph(bat hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], bat-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType HORSE_MORPH = new AbstractHairType(BodyCoveringType.HAIR_HORSE_HAIR,
			Race.HORSE_MORPH,
			"horse",
			"hair",
			"hairs",
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] остаются волосы, похожие на мех.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldHorseMorph(horse hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], horse-like hair",
			Util.newArrayListOfValues(
					BodyPartTag.HAIR_HANDLES_IN_SEX,
					BodyPartTag.HAIR_NATURAL_MANE)) {
	};

	public static AbstractHairType REINDEER_MORPH = new AbstractHairType(BodyCoveringType.HAIR_REINDEER_FUR,
			Race.REINDEER_MORPH,
			"reindeer",
			"hair",
			"hairs",
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            Util.newArrayListOfValues("пушистые", "похожие на мех"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] остаются волосы, похожие на мех.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour] [style.boldReindeerMorph(reindeer hair)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], reindeer-like hair",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
	};

	public static AbstractHairType HARPY = new AbstractHairType(BodyCoveringType.HAIR_HARPY,
			Race.HARPY,
			"harpy",
			"head-feather",
			"head-feathers",
			Util.newArrayListOfValues("beautiful", "bird-like"),
			Util.newArrayListOfValues("beautiful", "bird-like"),
            "Превращение длится всего несколько мгновений, после чего у [npc.herHim] [npc.targetBasedWord(остаёшься, остаётся)] с a plume of feathers in place of hair.<br/>"
				+ "[npc.Name] now [npc.has] [npc.hairColour], bird-like [style.boldHarpy(harpy feathers)].",
			"[npc.SheHasFull] [npc.hairDeterminer] [npc.hairLength], [npc.hairColour(true)], bird-like harpy feathers",
			Util.newArrayListOfValues(BodyPartTag.HAIR_HANDLES_IN_SEX)) {
		@Override
		public boolean isDefaultPlural(GameCharacter gc) {
			return true;
		}
		@Override
		public String getDeterminer(GameCharacter gc) {
			return "a plume of";
		}
	};

	private static final List<AbstractHairType> allHairTypes;
	private static final Map<AbstractHairType, String> hairToIdMap = new HashMap<>();
	private static final Map<String, AbstractHairType> idToHairMap = new HashMap<>();
	
	static {
		allHairTypes = new ArrayList<>();

		// Modded types:
		
		Map<String, Map<String, File>> moddedFilesMap = Util.getExternalModFilesById("/race", "bodyParts", null);
		for(Entry<String, Map<String, File>> entry : moddedFilesMap.entrySet()) {
			for(Entry<String, File> innerEntry : entry.getValue().entrySet()) {
				if(Util.getXmlRootElementName(innerEntry.getValue()).equals("hair")) {
					try {
						AbstractHairType type = new AbstractHairType(innerEntry.getValue(), entry.getKey(), true) {};
						String id = innerEntry.getKey().replaceAll("bodyParts_", "");
						allHairTypes.add(type);
						hairToIdMap.put(type, id);
						idToHairMap.put(id, type);
					} catch(Exception ex) {
						ex.printStackTrace(System.err);
					}
				}
			}
		}
		
		// External res types:
		
		Map<String, Map<String, File>> filesMap = Util.getExternalFilesById("res/race", "bodyParts", null);
		for(Entry<String, Map<String, File>> entry : filesMap.entrySet()) {
			for(Entry<String, File> innerEntry : entry.getValue().entrySet()) {
				if(Util.getXmlRootElementName(innerEntry.getValue()).equals("hair")) {
					try {
						AbstractHairType type = new AbstractHairType(innerEntry.getValue(), entry.getKey(), false) {};
						String id = innerEntry.getKey().replaceAll("bodyParts_", "");
						allHairTypes.add(type);
						hairToIdMap.put(type, id);
						idToHairMap.put(id, type);
					} catch(Exception ex) {
						ex.printStackTrace(System.err);
					}
				}
			}
		}
		// Add in hard-coded hair types:
		
		Field[] fields = HairType.class.getFields();
		
		for(Field f : fields){
			if (AbstractHairType.class.isAssignableFrom(f.getType())) {
				
				AbstractHairType ct;
				try {
					ct = ((AbstractHairType) f.get(null));

					hairToIdMap.put(ct, f.getName());
					idToHairMap.put(f.getName(), ct);
					
					allHairTypes.add(ct);
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		Collections.sort(allHairTypes, (t1, t2)->
			t1.getRace()==Race.NONE
				?-1
				:(t2.getRace()==Race.NONE
					?1
					:t1.getRace().getName(false).compareTo(t2.getRace().getName(false))));
	}
	
	public static AbstractHairType getHairTypeFromId(String id) {
		if(id.equals("IMP") || id.equals("DEMON_COMMON")) {
			return HairType.DEMON;
		}
		if(id.equals("LYCAN")) {
			return HairType.WOLF_MORPH;
		}
		id = Util.getClosestStringMatch(id, idToHairMap.keySet());
		return idToHairMap.get(id);
	}
	
	public static String getIdFromHairType(AbstractHairType hairType) {
		return hairToIdMap.get(hairType);
	}
	
	public static List<AbstractHairType> getAllHairTypes() {
		return allHairTypes;
	}
	
	private static final Map<AbstractRace, List<AbstractHairType>> typesMap = new HashMap<>();
	
	public static List<AbstractHairType> getHairTypes(AbstractRace r) {
		if(typesMap.containsKey(r)) {
			return typesMap.get(r);
		}
		
		List<AbstractHairType> types = new ArrayList<>();
		for(AbstractHairType type : HairType.getAllHairTypes()) {
			if(type.getRace()==r) {
				types.add(type);
			}
		}
		typesMap.put(r, types);
		return types;
	}
}