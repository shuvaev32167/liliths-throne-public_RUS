package com.lilithsthrone.world.places;

import com.lilithsthrone.game.character.npc.dominion.*;
import com.lilithsthrone.game.character.npc.submission.*;
import com.lilithsthrone.game.character.quests.Quest;
import com.lilithsthrone.game.character.quests.QuestLine;
import com.lilithsthrone.game.character.race.AbstractSubspecies;
import com.lilithsthrone.game.character.race.Subspecies;
import com.lilithsthrone.game.character.race.SubspeciesSpawnRarity;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueManager;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.encounters.AbstractEncounter;
import com.lilithsthrone.game.dialogue.encounters.Encounter;
import com.lilithsthrone.game.dialogue.npcDialogue.dominion.DaddyDialogue;
import com.lilithsthrone.game.dialogue.places.dominion.*;
import com.lilithsthrone.game.dialogue.places.dominion.cityHall.CityHall;
import com.lilithsthrone.game.dialogue.places.dominion.cityHall.CityHallDemographics;
import com.lilithsthrone.game.dialogue.places.dominion.cityHall.CityHallProperty;
import com.lilithsthrone.game.dialogue.places.dominion.enforcerHQ.BraxOffice;
import com.lilithsthrone.game.dialogue.places.dominion.enforcerHQ.EnforcerHQDialogue;
import com.lilithsthrone.game.dialogue.places.dominion.feliciaApartment.FeliciaApartment;
import com.lilithsthrone.game.dialogue.places.dominion.harpyNests.HarpyNestHelena;
import com.lilithsthrone.game.dialogue.places.dominion.harpyNests.HarpyNestsDialogue;
import com.lilithsthrone.game.dialogue.places.dominion.helenaHotel.HelenaApartment;
import com.lilithsthrone.game.dialogue.places.dominion.lilayashome.*;
import com.lilithsthrone.game.dialogue.places.dominion.nightlife.NightlifeDistrict;
import com.lilithsthrone.game.dialogue.places.dominion.nyansApartment.NyanApartment;
import com.lilithsthrone.game.dialogue.places.dominion.shoppingArcade.*;
import com.lilithsthrone.game.dialogue.places.dominion.slaverAlley.BountyHunterLodge;
import com.lilithsthrone.game.dialogue.places.dominion.slaverAlley.ScarlettsShop;
import com.lilithsthrone.game.dialogue.places.dominion.slaverAlley.SlaverAlleyDialogue;
import com.lilithsthrone.game.dialogue.places.dominion.slaverAlley.SlaveryAdministration;
import com.lilithsthrone.game.dialogue.places.dominion.warehouseDistrict.DominionExpress;
import com.lilithsthrone.game.dialogue.places.dominion.warehouseDistrict.KaysWarehouse;
import com.lilithsthrone.game.dialogue.places.dominion.warehouseDistrict.Warehouses;
import com.lilithsthrone.game.dialogue.places.dominion.zaranixHome.ZaranixHomeFirstFloor;
import com.lilithsthrone.game.dialogue.places.dominion.zaranixHome.ZaranixHomeFirstFloorRepeat;
import com.lilithsthrone.game.dialogue.places.dominion.zaranixHome.ZaranixHomeGroundFloor;
import com.lilithsthrone.game.dialogue.places.dominion.zaranixHome.ZaranixHomeGroundFloorRepeat;
import com.lilithsthrone.game.dialogue.places.fields.FieldsDialogue;
import com.lilithsthrone.game.dialogue.places.submission.BatCaverns;
import com.lilithsthrone.game.dialogue.places.submission.LyssiethPalaceDialogue;
import com.lilithsthrone.game.dialogue.places.submission.SlimeQueensLair;
import com.lilithsthrone.game.dialogue.places.submission.SubmissionGenericPlaces;
import com.lilithsthrone.game.dialogue.places.submission.gamblingDen.GamblingDenDialogue;
import com.lilithsthrone.game.dialogue.places.submission.gamblingDen.PregnancyRoulette;
import com.lilithsthrone.game.dialogue.places.submission.gamblingDen.RoxysShop;
import com.lilithsthrone.game.dialogue.places.submission.impFortress.ImpCitadelDialogue;
import com.lilithsthrone.game.dialogue.places.submission.impFortress.ImpFortressDialogue;
import com.lilithsthrone.game.dialogue.places.submission.ratWarrens.RatWarrensCaptiveDialogue;
import com.lilithsthrone.game.dialogue.places.submission.ratWarrens.RatWarrensDialogue;
import com.lilithsthrone.game.dialogue.places.submission.ratWarrens.VengarCaptiveDialogue;
import com.lilithsthrone.game.dialogue.places.submission.rebelBase.RebelBase;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.CharacterInventory;
import com.lilithsthrone.game.inventory.clothing.AbstractClothing;
import com.lilithsthrone.game.inventory.item.ItemType;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.world.*;
import com.lilithsthrone.world.population.Population;
import com.lilithsthrone.world.population.PopulationDensity;
import com.lilithsthrone.world.population.PopulationType;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.Map.Entry;

/**
 * @since 0.1.0
 * @version 0.4
 * @author Innoxia
 */
public class PlaceType {
	
	// Generic holding map:
	
	public static final AbstractPlaceType GENERIC_IMPASSABLE = new AbstractPlaceType(
			WorldRegion.MISC, "Impassable Tile", "", null, PresetColour.BASE_GREY, null, Darkness.ALWAYS_LIGHT, null, "");
	
	public static final AbstractPlaceType GENERIC_EMPTY_TILE = new AbstractPlaceType(
			WorldRegion.MISC, "Empty Tile", "", "dominion/slaverAlleyIcon", PresetColour.BASE_CRIMSON, null, Darkness.ALWAYS_LIGHT, null, "");

	public static final AbstractPlaceType GENERIC_HOLDING_CELL = new AbstractPlaceType(
			WorldRegion.MISC, "Holding Cell", "", "dominion/slaverAlleyIcon", PresetColour.BASE_GREY, null, Darkness.ALWAYS_LIGHT, null, "");

	public static final AbstractPlaceType GENERIC_CLUB_HOLDING_CELL = new AbstractPlaceType(
			WorldRegion.MISC, "Holding Cell (club)", "", "dominion/slaverAlleyIcon", PresetColour.BASE_GREY, null, Darkness.ALWAYS_LIGHT, null, "");
	
	public static final AbstractPlaceType GENERIC_MUSEUM = new AbstractPlaceType(
			WorldRegion.OLD_WORLD, "Музей", "", "dominion/slaverAlleyIcon", PresetColour.BASE_TAN, null, Darkness.ALWAYS_LIGHT, null, "в музее Лили"
			).initItemsPersistInTile()
			.initWeatherImmune();
	
	
	
	// Museum:
	
	public static final AbstractPlaceType MUSEUM_ENTRANCE = new AbstractPlaceType(
			WorldRegion.OLD_WORLD,
			"Вход",
			"Главный вход в музей, в котором работает твоя тётя Лили.",
			"prologue/exit",
			PresetColour.BASE_RED,
			null,
			Darkness.ALWAYS_LIGHT,
			null, "в музее Лили") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.PERSON, PopulationDensity.FEW, Util.newHashMapOfValues(new Value<>(Subspecies.HUMAN, SubspeciesSpawnRarity.TEN))));
		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	
	public static final AbstractPlaceType MUSEUM_CROWDS = new AbstractPlaceType(
			WorldRegion.OLD_WORLD,
			"Толпы",
			"В настоящее время эта часть главного холла музея заполнена толпой посетителей, которые, как и ты, пришли на открытие выставки.",
			"prologue/crowd",
			PresetColour.BASE_YELLOW,
			null,
			Darkness.ALWAYS_LIGHT,
			null, "в музее Лили") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.CROWD, PopulationDensity.DENSE, Util.newHashMapOfValues(new Value<>(Subspecies.HUMAN, SubspeciesSpawnRarity.TEN))));
		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	
	public static final AbstractPlaceType MUSEUM_OFFICE = new AbstractPlaceType(
			WorldRegion.OLD_WORLD,
			"Офис",
			"Большой офис руководителя. Он кажется знакомым, и в глубине души ты думаешь, что это может быть офис твоей тёти Лили...",
			"prologue/office",
			PresetColour.BASE_BLUE_LIGHT,
			null,
			Darkness.ALWAYS_LIGHT,
			null, "в музее Лили"
			).initItemsPersistInTile()
			.initWeatherImmune();
	
	public static final AbstractPlaceType MUSEUM_STAGE = new AbstractPlaceType(
			WorldRegion.OLD_WORLD,
			"Сцена",
			"Посреди холла возведена большая сцена, и именно с неё твоя тётя Лили должна произнести свою речь.",
			"prologue/stage",
			PresetColour.BASE_ORANGE,
			null,
			Darkness.ALWAYS_LIGHT,
			null, "в музее Лили") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.CROWD, PopulationDensity.DENSE, Util.newHashMapOfValues(new Value<>(Subspecies.HUMAN, SubspeciesSpawnRarity.TEN))));
		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	
	public static final AbstractPlaceType MUSEUM_ROOM = new AbstractPlaceType(
			WorldRegion.OLD_WORLD,
			"Экспозиционный зал",
			"Одна из многочисленных комнат музея, посвященная древним реликвиям и драгоценным артефактам.",
			"prologue/room",
			PresetColour.BASE_TAN,
			null,
			Darkness.ALWAYS_LIGHT,
			null, "в музее Лили"
			).initItemsPersistInTile()
			.initWeatherImmune();
	
	public static final AbstractPlaceType MUSEUM_STAIRS = new AbstractPlaceType(
			WorldRegion.OLD_WORLD,
			"Лестница",
			"Лестница музея соединяет первый и второй этажи.",
			"prologue/stairsUp",
			PresetColour.BASE_GREEN,
			null,
			Darkness.ALWAYS_LIGHT,
			null, "в музее Лили"
			).initItemsPersistInTile()
			.initWeatherImmune();
	
	public static final AbstractPlaceType MUSEUM_LOBBY = new AbstractPlaceType(
			WorldRegion.OLD_WORLD,
			"Вестибюль",
			"Главный вестибюль музея двойной высоты. На перилах верхнего этажа развешаны баннеры, посвящённые новой выставке.",
			null,
			PresetColour.BASE_TAN,
			null,
			Darkness.ALWAYS_LIGHT,
			null, "в музее Лили") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.PERSON, PopulationDensity.FEW, Util.newHashMapOfValues(new Value<>(Subspecies.HUMAN, SubspeciesSpawnRarity.TEN))));
		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	
	public static final AbstractPlaceType MUSEUM_CORRIDOR = new AbstractPlaceType(
			WorldRegion.OLD_WORLD,
			"Коридор",
			"Коридоры верхнего этажа музея по своей планировке напоминают лабиринт.",
			null,
			PresetColour.BASE_TAN,
			null,
			Darkness.ALWAYS_LIGHT,
			null, "в музее Лили"
			).initItemsPersistInTile()
			.initWeatherImmune();
	
	public static final AbstractPlaceType MUSEUM_MIRROR = new AbstractPlaceType(
			WorldRegion.OLD_WORLD,
			"Комната с зеркалом",
			"В этой комнате главным украшением является огромное зеркало высотой до потолка.",
			"prologue/mirror",
			PresetColour.BASE_PINK,
			null,
			Darkness.ALWAYS_LIGHT,
			null, "в музее Лили"
			).initItemsPersistInTile()
			.initWeatherImmune();
	
	
	
	// Dominion:
	
	public static final AbstractPlaceType DOMINION_PLAZA = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Площадь Лилит",
			"В самом центре Доминиона находится обширная площадь, где новости о Домене Лилит зачитывают штатные глашатаи.",
			"dominion/statue",
			PresetColour.BASE_PINK_DEEP,
			DominionPlaces.DOMINION_PLAZA,
			Darkness.ALWAYS_LIGHT,
			null, "на центральной площади Доминиона") {
		@Override
		public List<Population> getPopulation() {
			List<Population> pop = new ArrayList<>();
			
			if(Main.game.isDayTime()) {
				if(Main.game.getCurrentWeather()==Weather.MAGIC_STORM) {
					pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.COUPLE, Subspecies.getDominionStormImmuneSpecies(true)));
					pop.add(new Population(false, PopulationType.ENFORCER, PopulationDensity.OCCASIONAL, Subspecies.getDominionStormImmuneSpecies(true, Subspecies.HUMAN)));
				} else {
					pop.add(new Population(true, PopulationType.CROWD, PopulationDensity.DENSE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
					pop.add(new Population(false, PopulationType.ENFORCER, PopulationDensity.OCCASIONAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true, Subspecies.HUMAN)));
					pop.add(new Population(true, PopulationType.CENTAUR_CARTS, PopulationDensity.NUMEROUS, Util.newHashMapOfValues(new Value<>(Subspecies.CENTAUR, SubspeciesSpawnRarity.TEN))));
				}
			} else {
				if(Main.game.getCurrentWeather()==Weather.MAGIC_STORM) {
					pop.add(new Population(false, PopulationType.PERSON, PopulationDensity.OCCASIONAL, Subspecies.getDominionStormImmuneSpecies(true)));
					pop.add(new Population(false, PopulationType.ENFORCER, PopulationDensity.OCCASIONAL, Subspecies.getDominionStormImmuneSpecies(true, Subspecies.HUMAN)));
				} else {
					pop.add(new Population(false, PopulationType.PERSON, PopulationDensity.OCCASIONAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
					pop.add(new Population(false, PopulationType.ENFORCER, PopulationDensity.OCCASIONAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true, Subspecies.HUMAN)));
				}
			}
			
			return pop;
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_PINK);
	
	public static final AbstractPlaceType DOMINION_STREET = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Улицы Доминиона",
			"Широкие улицы Доминиона усажены ухоженными городскими домами и полностью пешеходны.",
			null,
			PresetColour.BASE_GREY,
			DominionPlaces.STREET,
			Darkness.ALWAYS_LIGHT,
			Encounter.DOMINION_STREET,
			"на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			List<Population> pop = new ArrayList<>();
			
			if(Main.game.isDayTime()) {
				if(Main.game.getCurrentWeather()==Weather.MAGIC_STORM) {
					if(Main.game.getNonCompanionCharactersPresent().isEmpty() || !Main.game.getCurrentDialogueNode().isTravelDisabled()) {
						pop.add(new Population(false, PopulationType.PERSON, PopulationDensity.OCCASIONAL, Subspecies.getDominionStormImmuneSpecies(true)));
					}
					
				} else {
					pop.add(new Population(true, PopulationType.CROWD, PopulationDensity.DENSE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
					pop.add(new Population(false, PopulationType.ENFORCER, PopulationDensity.OCCASIONAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true, Subspecies.HUMAN)));
					pop.add(new Population(true, PopulationType.CENTAUR_CARTS, PopulationDensity.NUMEROUS, Util.newHashMapOfValues(new Value<>(Subspecies.CENTAUR, SubspeciesSpawnRarity.TEN))));
				}
			} else {
				if(Main.game.getCurrentWeather()==Weather.MAGIC_STORM) {
					if(Main.game.getNonCompanionCharactersPresent().isEmpty() || !Main.game.getCurrentDialogueNode().isTravelDisabled()) {
						pop.add(new Population(false, PopulationType.PERSON, PopulationDensity.OCCASIONAL, Subspecies.getDominionStormImmuneSpecies(true)));
					}
				} else {
					pop.add(new Population(true, PopulationType.CROWD, PopulationDensity.SPARSE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
					pop.add(new Population(false, PopulationType.ENFORCER, PopulationDensity.OCCASIONAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true, Subspecies.HUMAN)));
				}
			}
			
			return pop;
		}
	};
	
	public static final AbstractPlaceType DOMINION_BOULEVARD = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Бульвар Доминиона",
			"Главные бульвары, ведущие к центру Доминиона, очень широки и хорошо проходимы, их регулярно патрулируют энфорсеры.",
			null,
			PresetColour.BASE_PINK_LIGHT,
			DominionPlaces.BOULEVARD,
			Darkness.ALWAYS_LIGHT, Encounter.DOMINION_BOULEVARD, "на улицах Доминиона") {
		@Override
		public List<Population> getPopulation() {
			return DOMINION_PLAZA.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_PINK);
	
	public static final AbstractPlaceType DOMINION_LILITHS_TOWER = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Башня Лилит",
			"Колоссальная башня из тёмного камня, в которой живёт Лилит, видна за много миль, и постоянно напоминает жителям города о том, кто здесь главный.",
			"dominion/lilithsTowerIcon",
			PresetColour.BASE_PURPLE,
			LilithsTower.OUTSIDE,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Доминиона") {

		@Override
		public List<Population> getPopulation() {
			return DOMINION_PLAZA.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_PINK);
	
	public static final AbstractPlaceType DOMINION_ENFORCER_HQ = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Штаб-квартира энфорсеров",
			"Штаб-квартира энфорсеров - одно из самых современных зданий в Доминионе, и именно отсюда осуществляется управление всеми сотрудниками правоохранительных органов Доминиона.",
			"dominion/enforcerHQIcon",
			PresetColour.BASE_BLUE,
			EnforcerHQDialogue.EXTERIOR,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Доминиона") {
		@Override
		public List<Population> getPopulation() {
			return DOMINION_STREET.getPopulation();
		}
	};
	
	public static final AbstractPlaceType DOMINION_DEMON_HOME_GATE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Ворота дома демонов",
			"В зону, известную как «Дом демонов», есть всего пара входов, и оба они усиленно охраняются многочисленными энфорсерами.",
			"dominion/gate",
			PresetColour.BASE_PINK_LIGHT,
			DemonHome.DEMON_HOME_GATE,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Дома демонов") {
		@Override
		public List<Population> getPopulation() {
			return DOMINION_PLAZA.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_PINK);
	
	public static final AbstractPlaceType DOMINION_DEMON_HOME = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Дом демонов",
			"Район, окружающий башню Лилит, известен как «Дом демонов», но, несмотря на это название, его жители принадлежат к самым разным расам.",
			null,
			PresetColour.BASE_PINK,
			DemonHome.DEMON_HOME_STREET,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Дома демонов") {
		@Override
		public List<Population> getPopulation() {
			return DOMINION_PLAZA.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_PINK);
	
	public static final AbstractPlaceType DOMINION_DEMON_HOME_ARTHUR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Соулти-Тауэрс",
			"Большое каменное здание, богато украшенное в викторианском стиле, больше напоминает пятизвездочный отель, чем жилой комплекс.",
			"dominion/demonHomeSawltyTowersIcon",
			PresetColour.RACE_HUMAN,
			DemonHome.DEMON_HOME_STREET_ARTHUR,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Дома демонов возле Соулти-Тауэрс.") {
		@Override
		public String getName() {
			if(Main.game.isStarted()) {
				return UtilText.parse("Дом демонов ([arthur.Name])");
			}
			return name;
		}
		@Override
		public String getTooltipDescription() {
			return tooltipDescription + " квартира Артура находится именно в этом районе.";
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_PLAZA.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_PINK);
	
	public static final AbstractPlaceType DOMINION_DEMON_HOME_ZARANIX = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Дом демонов (Зараникс)",
			"Район, окружающий башню Лилит, известен как «Дом демонов», но, несмотря на это название, его жители принадлежат к самым разным расам.",
			"dominion/demonHomeZaranixIcon",
			PresetColour.BASE_PINK,
			DemonHome.DEMON_HOME_STREET_ZARANIX,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Дома демонов") {
		@Override
		public String getName() {
			if(Main.game.isStarted()) {
				return UtilText.parse("Дом демонов ([zaranix.Name])");
			}
			return name;
		}
		@Override
		public String getTooltipDescription() {
			return tooltipDescription + UtilText.parse(" дом [zaranix.morphSingleNameGene([zaranix.NamePos])] расположен именно в этом районе.");
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_PLAZA.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_PINK);
	
	public static final AbstractPlaceType DOMINION_DEMON_HOME_DADDY = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Дом демонов (Папочка)",
			"Район, окружающий башню Лилит, известен как «Дом демонов», но, несмотря на это название, его жители принадлежат к самым разным расам.",
			"dominion/demonHomeDaddyIcon",
			PresetColour.BASE_INDIGO,
			DemonHome.DEMON_HOME_STREET_DADDY,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Дома демонов") {
		@Override
		public String getName() {
			if(Main.game.isStarted()) {
				return UtilText.parse("Дом демонов ([daddy.Name])");
			}
			return name;
		}
		@Override
		public String getTooltipDescription() {
			return tooltipDescription + UtilText.parse(" квартира [daddy.morphSingleNameGene([daddy.NamePos])] расположена именно в этом районе.");
		}
		@Override
		public List<Population> getPopulation() {
			if(Main.game.getCharactersPresent().contains(Main.game.getNpc(Daddy.class))) {
				return Util.newArrayListOfValues(new Population(true, PopulationType.DINER, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			}
			return DOMINION_PLAZA.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_PINK);
	
	public static final AbstractPlaceType DOMINION_DEMON_HOME_SEX_SHOP = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Роскошь Ловиенны",
			"В секс-шопе «Роскошь Ловиенны», рассчитанном на более состоятельных клиентов Дома Демонов, можно купить секс-игрушки и автономные секс-куклы.",
			"dominion/sexShopIcon",
			PresetColour.BASE_PINK_LIGHT,
			DemonHome.DEMON_HOME_SEX_SHOP,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Дома демонов") {
		@Override
		public List<Population> getPopulation() {
			if(Main.game.getDialogueFlags().hasFlag("innoxia_doll_factory_exterior_population_hidden")) {
				return super.getPopulation();
			}
			
			if(Main.game.getPlayer().getQuest(QuestLine.SIDE_DOLL_FACTORY)==Quest.DOLL_FACTORY_7A) {
				if(Main.game.getCurrentWeather()==Weather.MAGIC_STORM) {
					return Util.newArrayListOfValues(
							new Population(false, PopulationType.CROWD, PopulationDensity.DENSE, Subspecies.getDominionStormImmuneSpecies(true)),
							new Population(true, PopulationType.ENFORCER, PopulationDensity.DOZENS, Subspecies.getDominionStormImmuneSpecies(true, Subspecies.HUMAN)));
				} else {
					return Util.newArrayListOfValues(
							new Population(false, PopulationType.CROWD, PopulationDensity.DENSE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)),
							new Population(true, PopulationType.ENFORCER, PopulationDensity.DOZENS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true, Subspecies.HUMAN)));
				}
			}
			return DOMINION_PLAZA.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_PINK);
	
	public static final AbstractPlaceType DOMINION_SHOPPING_ARCADE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Торговая галерея",
			"Хотя по всему Доминиону разбросано бесчисленное множество магазинов, эта галерея известна как лучшее место для шопинга.",
			"dominion/shoppingArcadeIcon",
			PresetColour.BASE_GOLD,
			ShoppingArcadeDialogue.OUTSIDE,
			Darkness.ALWAYS_LIGHT, Encounter.DOMINION_STREET, "на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_STREET.getPopulation();
		}
	};

	public static final AbstractPlaceType DOMINION_NYAN_APARTMENT = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Квартира Ньян",
			"Поскольку она показала вам, где живет, ты знаешь, что многоквартирный дом Ньян находится в этом районе Доминиона.",
			"dominion/homeNyanIcon",
			PresetColour.BASE_PINK_LIGHT,
			DominionPlaces.STREET,
			Darkness.ALWAYS_LIGHT,
			Encounter.DOMINION_STREET,
			"на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_STREET.getPopulation();
		}
	};

	public static final AbstractPlaceType DOMINION_CALLIE_BAKERY = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Творожная выпечка",
			"Эта пекарня принадлежит лошадедевушке по имени Кэлли.",
			"dominion/callieBakeryIcon",
			PresetColour.BASE_BROWN,
			DominionPlaces.STREET,
			Darkness.ALWAYS_LIGHT,
			Encounter.DOMINION_STREET,
			"на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_STREET.getPopulation();
		}
	};
	
	public static final AbstractPlaceType DOMINION_STREET_HARPY_NESTS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Улицы Доминиона",
			"Замысловатые дорожки и мосты Гнезда Гарпии пересекают крыши домов в этом районе, отбрасывая свои тени на улицы внизу.",
			null,
			PresetColour.BASE_GREY,
			DominionPlaces.STREET_SHADED,
			Darkness.ALWAYS_LIGHT, Encounter.DOMINION_STREET, "на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_STREET.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_DARK);
	
	public static final AbstractPlaceType DOMINION_HARPY_NESTS_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Вход в гнёзда гарпий",
			"Большое здание с многочисленными лифтами и винтовыми лестницами соединяет гнёзда гарпий с улицами внизу.",
			"dominion/harpyNestIcon",
			PresetColour.BASE_MAGENTA,
			HarpyNestsDialogue.OUTSIDE,
			Darkness.ALWAYS_LIGHT, Encounter.DOMINION_STREET, "на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_STREET.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_DARK);

	public static final AbstractPlaceType DOMINION_HELENA_HOTEL = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Отель «Золотое перо»",
			"Один из самых престижных и известных отелей Доминиона, «Золотое перо», принадлежит матриарху гарпий Елене.",
			"dominion/demonHomeHelenaIcon",
			PresetColour.BASE_GOLD,
			DominionPlaces.HELENAS_HOTEL,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Дома демонов") {
		@Override
		public List<Population> getPopulation() {
			return DOMINION_STREET.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_DARK);
	
	public static final AbstractPlaceType DOMINION_NIGHTLIFE_DISTRICT = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Район ночной жизни",
			"Хотя клубы, бары и другие подобные заведения есть по всему Доминиону, самая лучшая ночная жизнь, которую может предложить Доминион, находится именно здесь.",
			"dominion/nightlifeIcon",
			PresetColour.BASE_PINK_LIGHT,
			NightlifeDistrict.OUTSIDE,
			Darkness.ALWAYS_LIGHT, Encounter.DOMINION_STREET, "на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_STREET.getPopulation();
		}
	};
	
	public static final AbstractPlaceType DOMINION_CITY_HALL = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Ратуша",
			"Действуя как центр регионального правительства, ратуша управляет административными делами не только Доминиона, но и всего Домена Лилит.",
			"dominion/townHallIcon",
			PresetColour.BASE_INDIGO,
			CityHall.OUTSIDE,
			Darkness.ALWAYS_LIGHT, Encounter.DOMINION_STREET, "на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_STREET.getPopulation();
		}
	};

	public static final AbstractPlaceType DOMINION_BANK = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Банк Доминиона",
			"Единственный банк королевства, «Банк Доминиона», имеет свое главное отделение здесь, в Доминионе.",
			"dominion/bankIcon",
			PresetColour.BASE_GOLD,
			DialogueManager.getDialogueFromId("innoxia_places_dominion_bank_generic_exterior"),
			Darkness.ALWAYS_LIGHT,
			null,
			"на улицах Доминиона") {
		@Override
		public List<Population> getPopulation() {
			return DOMINION_PLAZA.getPopulation();
		}
	};
	
	public static final AbstractPlaceType DOMINION_AUNTS_HOME = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Дом Лилайи",
			"Дом Лилайи больше похож на особняк, чем на городской дом, и благодаря своим внушительным размерам выделяется в этом районе как особенно впечатляющее здание.",
			"dominion/homeIcon",
			PresetColour.BASE_BLUE_LIGHT,
			LilayaHomeGeneric.OUTSIDE,
			Darkness.ALWAYS_LIGHT, Encounter.DOMINION_STREET, "на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_STREET.getPopulation();
		}
	};
	
	public static final AbstractPlaceType DOMINION_SLAVER_ALLEY = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Аллея работорговцев",
			"Хотя рабство в Доминионе полностью легально, этот главный центр для всех сделок с рабами расположен в очень теневой части города, окружённый опасными подворотнями.",
			"dominion/slaverAlleyIcon",
			PresetColour.BASE_CRIMSON,
			SlaverAlleyDialogue.OUTSIDE,
			Darkness.ALWAYS_LIGHT,
			null, "в переулках возле Аллеи работорговцев") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			if(Main.game.getCurrentWeather()==Weather.MAGIC_STORM) {
				return new ArrayList<>();
			}
			return SLAVER_ALLEY_ENTRANCE.getPopulation();
		}
	};

	public static final AbstractPlaceType DOMINION_RED_LIGHT_DISTRICT = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Район красных фонарей",
			"Как бы ни был прост секс в Доминионе, нет никакой гарантии, что партнёр обладает какими-либо навыками в спальне. Именно в этой области можно нанять профессиональных любовников без обязательств.",
			"dominion/brothel",
			PresetColour.BASE_MAGENTA,
			RedLightDistrict.OUTSIDE,
			Darkness.ALWAYS_LIGHT, Encounter.DOMINION_STREET, "на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_STREET.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_DARK);

	public static final AbstractPlaceType DOMINION_PARK = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Парк",
			"На территории Доминиона расположено несколько больших парков, все они полностью открыты для посещения.",
			"dominion/park",
			PresetColour.BASE_GREEN,
			DominionPark.PARK,
			Darkness.DAYLIGHT,
			Encounter.DOMINION_PARK,
			"в одном из парков Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			List<Population> pop = new ArrayList<>();
			
			if(Main.game.getCurrentWeather()!=Weather.MAGIC_STORM) {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.SEVERAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
				pop.add(new Population(false, PopulationType.ENFORCER, PopulationDensity.OCCASIONAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true, Subspecies.HUMAN)));
				if(Main.game.getCharactersPresent().contains(Main.game.getNpc(Natalya.class))) {
					pop.add(new Population(true, PopulationType.CENTAUR_CARTS, PopulationDensity.SEVERAL, Util.newHashMapOfValues(new Value<>(Subspecies.CENTAUR, SubspeciesSpawnRarity.TEN))));
				}
			}
			
			return pop;
		}
	};

	public static final AbstractPlaceType DOMINION_HOME_IMPROVEMENT = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Магазин «Сделай сам» Аргуса",
			"Снабжая как энтузиастов самостоятельного ремонта, так и профессиональные строительные фирмы, «Магазин Аргуса» имеет вид пары огромных складов, расположенных посреди обширного склада пиломатериалов.",
			"dominion/construction",
			PresetColour.BASE_ORANGE,
			HomeImprovements.OUTSIDE,
			Darkness.ALWAYS_LIGHT, Encounter.DOMINION_STREET, "на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_STREET.getPopulation();
		}
	};

	public static final AbstractPlaceType DOMINION_WAREHOUSES = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Складской район",
			"Хотя по всему Доминиону разбросано бесчисленное множество промышленных зданий, наибольшая их концентрация находится в этом специализированном складском районе.",
			"dominion/warehouse",
			PresetColour.BASE_BROWN,
			Warehouses.WAREHOUSE_DISTRICT,
			Darkness.ALWAYS_LIGHT, Encounter.DOMINION_STREET, "на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_STREET.getPopulation();
		}
	};
	
	
	
	// Alleyways:
	
	public static final AbstractPlaceType DOMINION_BACK_ALLEYS_SAFE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Переулки (патрулируемые)",
			"В то время как большинство переулков Доминиона очень опасны, этот участок очень интенсивно патрулируется энфорсерами, которые следят за тем, чтобы грабители не представляли угрозы для населения.",
			"dominion/alleysIcon",
			PresetColour.BASE_GREY,
			DominionPlaces.BACK_ALLEYS_SAFE,
			Darkness.DAYLIGHT, Encounter.DOMINION_STREET, "на одной из подворотен Доминиона."
			) {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather()==Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			if(Main.game.getCurrentWeather()==Weather.MAGIC_STORM) {
				return new ArrayList<>();
			}
			return Util.newArrayListOfValues(
					new Population(true, PopulationType.PERSON, PopulationDensity.FEW, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)),
					new Population(true, PopulationType.ENFORCER, PopulationDensity.SEVERAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
	};
	
	public static final AbstractPlaceType DOMINION_BACK_ALLEYS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Переулки",
			"Лабиринтные переулки города, хотя и предлагают кратчайшие пути между центральными улицами, очень редко посещаются людьми, так как известно, что в них обитают опасные элементы общества.",
			"dominion/alleysIcon",
			PresetColour.BASE_BLACK,
			DominionPlaces.BACK_ALLEYS,
			Darkness.DAYLIGHT, Encounter.DOMINION_ALLEY, "на одной из подворотен Доминиона."
			).initDangerous()
			.initSexNotBlockedFromCharacterPresent();

	public static final AbstractPlaceType DOMINION_DARK_ALLEYS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Тёмные переулки",
			"Тёмные глубины извилистых, похожих на лабиринты переулков Доминиона обходят стороной даже грабители, которые обычно называют эти места своим домом...",
			"dominion/alleysDarkIcon",
			PresetColour.BASE_PURPLE,
			DominionPlaces.DARK_ALLEYS,
			Darkness.DAYLIGHT, Encounter.DOMINION_DARK_ALLEY, "в одном из тёмных переулков Доминиона"
			).initDangerous()
			.initSexNotBlockedFromCharacterPresent();
	
	public static final AbstractPlaceType DOMINION_ALLEYS_CANAL_CROSSING = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Пересечение канала",
			"Эти переходы через городской канал считаются очень опасными и имеют репутацию излюбленных мест обитания опасных отчаянных преступников.",
			"dominion/bridge",
			PresetColour.BASE_BLUE_LIGHT,
			DominionPlaces.BACK_ALLEYS_CANAL,
			Darkness.DAYLIGHT, Encounter.DOMINION_ALLEY, "на одной из подворотен Доминиона."
			).initDangerous()
			.initAquatic(Aquatic.MIXED);
	       
	// Canals:
	
	public static final AbstractPlaceType DOMINION_CANAL = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Канал",
			"Канал Доминиона достаточно безопасен для тех, кто передвигается на барже или лодке, но для тех, кто прогуливается по часто пустынным и свободным от энфорсеров дорожкам, это совсем другая история...",
			"dominion/canalIcon",
			PresetColour.BASE_BLUE_LIGHT,
			DominionPlaces.CANAL,
			Darkness.DAYLIGHT, Encounter.DOMINION_CANAL, "рядом с одним из каналов Доминиона"
			).initDangerous()
			.initAquatic(Aquatic.MIXED);
	
	public static final AbstractPlaceType DOMINION_CANAL_END = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Канал",
			"Тропинки, идущие вдоль канала Доминион, резко обрываются на окраине города.",
			"dominion/canalEndIcon",
			PresetColour.BASE_BLUE,
			DominionPlaces.CANAL_END,
			Darkness.DAYLIGHT, Encounter.DOMINION_CANAL, "рядом с одним из каналов Доминиона"
			).initDangerous()
			.initAquatic(Aquatic.MIXED);
	
	// Exits & entrances:
	
	public static final AbstractPlaceType DOMINION_EXIT_TO_SUBMISSION = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Вход в Подземье",
			"На каждом перекрестке между каналом и главной улицей Доминиона есть охраняемые Энфорсерами входы в Подземье.",
			"dominion/submissionExit",
			PresetColour.BASE_TEAL,
			DominionPlaces.CITY_EXIT_SEWERS,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_PLAZA.getPopulation();
		}
		@Override
		public Bearing getBearing() {
			return Bearing.RANDOM;
		}
	};

	public static final AbstractPlaceType DOMINION_EXIT_TO_BAT_CAVERNS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Проход к пещерам летучих мышей",
			"Эта глубокая, извилистая шахта окружена высоким забором из цепей, а рядом установлены таблички, указывающие, что она ведёт вниз, в пещеры летучих мышей, расположенные под Подземкой.",
			"dominion/batCaverns",
			PresetColour.BASE_BLUE,
			DominionPlaces.CITY_EXIT_BAT_CAVERNS,
			Darkness.ALWAYS_LIGHT,
			null,
			"на улицах Доминиона") {
		@Override
		public boolean isDangerous() {
			return Main.game.getCurrentWeather() == Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return DOMINION_PLAZA.getPopulation();
		}
		@Override
		public Bearing getBearing() {
			return Bearing.RANDOM;
		}
	};
	
	public static final AbstractPlaceType DOMINION_EXIT_EAST = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Выход из Доминиона",
			"Широкие бульвары Доминиона превращаются в обычные улицы, уходящие в удивительно маленькие пригороды города.",
			"dominion/exitEast",
			PresetColour.BASE_RED,
			DominionPlaces.CITY_EXIT,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Доминиона") {
		@Override
		public List<Population> getPopulation() {
			return DOMINION_PLAZA.getPopulation();
		}
		@Override
		public Bearing getBearing() {
			return Bearing.NORTH;
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_PINK);
	
	public static final AbstractPlaceType DOMINION_EXIT_NORTH = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Выход из Доминиона",
			"Широкие бульвары Доминиона превращаются в обычные улицы, уходящие в удивительно маленькие пригороды города.",
			"dominion/exitNorth",
			PresetColour.BASE_RED,
			DominionPlaces.CITY_EXIT,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Доминиона") {
		@Override
		public List<Population> getPopulation() {
			return DOMINION_PLAZA.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_PINK);
	
	public static final AbstractPlaceType DOMINION_EXIT_WEST = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Выход из Доминиона",
			"Широкие бульвары Доминиона превращаются в обычные улицы, уходящие в удивительно маленькие пригороды города.",
			"dominion/exitWest",
			PresetColour.BASE_RED,
			DominionPlaces.CITY_EXIT,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Доминиона") {
		@Override
		public List<Population> getPopulation() {
			return DOMINION_PLAZA.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_PINK);
	
	public static final AbstractPlaceType DOMINION_EXIT_SOUTH = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Выход из Доминиона",
			"Широкие бульвары Доминиона превращаются в обычные улицы, уходящие в удивительно маленькие пригороды города.",
			"dominion/exitSouth",
			PresetColour.BASE_RED,
			DominionPlaces.CITY_EXIT,
			Darkness.ALWAYS_LIGHT,
			null, "на улицах Доминиона") {
		@Override
		public List<Population> getPopulation() {
			return DOMINION_PLAZA.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_PINK);
	
	
	
	// Enforcer HQ:
	
	public static final AbstractPlaceType ENFORCER_HQ_CORRIDOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"По обеим сторонам этого довольно обычного на вид коридора расположено множество дверей, на каждой из которых написано название и специализация подразделения Энфорсеров.",
			null,
			PresetColour.BASE_BLACK,
			EnforcerHQDialogue.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "в штаб-квартире энфорсеров") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.ENFORCER, PopulationDensity.SEVERAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true, Subspecies.HUMAN)));
		}
	}.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_HQ_CELLS_CORRIDOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"Этот коридор не имеет никаких отличительных особенностей.",
			null,
			PresetColour.BASE_BLACK,
			EnforcerHQDialogue.CORRIDOR_PLAIN,
			Darkness.ALWAYS_LIGHT,
			null, "в штаб-квартире энфорсеров")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_HQ_STAIRS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Охраняемая лестница",
			"Лестницу, ведущую на следующий этаж, охраняет бдительный энфорсер",
			"dominion/enforcerHQ/stairs",
			PresetColour.BASE_GREEN,
			EnforcerHQDialogue.STAIRCASE,
			Darkness.ALWAYS_LIGHT,
			null, "в штаб-квартире энфорсеров")
			.initWeatherImmune();
	
	public static final AbstractPlaceType ENFORCER_HQ_WAITING_AREA = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Зона ожидания",
			"Несколько низких диванов, несколько растений в горшках и атмосфера тоскливой скуки - вот что составляет эту зону ожидания.",
			"dominion/enforcerHQ/waitingRoom",
			PresetColour.BASE_BROWN,
			EnforcerHQDialogue.WAITING_AREA,
			Darkness.ALWAYS_LIGHT,
			null, "в штаб-квартире энфорсеров")
			.initWeatherImmune();
	
	public static final AbstractPlaceType ENFORCER_HQ_RECEPTION_DESK = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Стойка администратора",
			"В приёмной штаб-квартиры энфорсеров работает кошкодевушка-бимбо Кэнди.",
			"dominion/enforcerHQ/receptionDesk",
			PresetColour.BASE_BLUE_LIGHT,
			EnforcerHQDialogue.RECEPTION_DESK,
			Darkness.ALWAYS_LIGHT,
			null, "в офисе Кэнди")
			.initWeatherImmune();
	
	public static final AbstractPlaceType ENFORCER_HQ_GUARDED_DOOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Охраняемая дверь",
			"Дверь, соединяющая общественную приемную с остальными помещениями штаб-квартиры энфорсеров, охраняется особо буйным конепарнем.",
			"dominion/enforcerHQ/guardedDoor",
			PresetColour.BASE_CRIMSON,
			EnforcerHQDialogue.GUARDED_DOOR,
			Darkness.ALWAYS_LIGHT,
			null, "в штаб-квартире энфорсеров") {
		@Override
		public Colour getColour() {
			if((Main.game.getDialogueFlags().values.contains(DialogueFlagValue.accessToEnforcerHQ) && !Main.game.isBraxMainQuestComplete())
					|| Main.game.getPlayer().isQuestCompleted(QuestLine.SIDE_WES)) {
				return PresetColour.BASE_GREEN_LIGHT;
			}
			return PresetColour.BASE_CRIMSON;
		} 
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.ENFORCER, PopulationDensity.ONE, Util.newHashMapOfValues(new Value<>(Subspecies.HORSE_MORPH, SubspeciesSpawnRarity.TEN))));
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType ENFORCER_HQ_REQUISITIONS_DOOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Запертая дверь",
			"Эта внутренняя дверь надежно заперта, не позволяя пройти никому, у кого нет необходимого ключа",
			"dominion/enforcerHQ/guardedDoor",
			PresetColour.BASE_CRIMSON,
			EnforcerHQDialogue.REQUISITIONS_DOOR,
			Darkness.ALWAYS_LIGHT,
			null, "в штаб-квартире энфорсеров") {
		@Override
		public Colour getColour() {
			if(Main.game.getPlayer().isQuestCompleted(QuestLine.SIDE_WES)) {
				return PresetColour.BASE_GREEN_LIGHT;
			}
			return PresetColour.BASE_CRIMSON;
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType ENFORCER_HQ_LOCKED_DOOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Запертая дверь",
			"Эта внутренняя дверь надежно заперта, не позволяя пройти никому, у кого нет необходимого ключа",
			"dominion/enforcerHQ/guardedDoor",
			PresetColour.BASE_CRIMSON,
			EnforcerHQDialogue.LOCKED_DOOR,
			Darkness.ALWAYS_LIGHT,
			null, "в штаб-квартире энфорсеров").initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_HQ_LOCKED_DOOR_EDGE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Запертая дверь",
			"Эта внутренняя дверь надежно заперта, не позволяя пройти никому, у кого нет необходимого ключа",
			"dominion/enforcerHQ/guardedDoor",
			PresetColour.BASE_RED_DARK,
			EnforcerHQDialogue.LOCKED_DOOR,
			Darkness.ALWAYS_LIGHT,
			null, "в штаб-квартире энфорсеров")
			.initWeatherImmune();
	
	public static final AbstractPlaceType ENFORCER_HQ_BRAXS_OFFICE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Офис Бракса",
			"Инспекторам разрешается иметь собственный кабинет и украшать его по своему усмотрению.",
			"dominion/enforcerHQ/office",
			PresetColour.BASE_BLUE_DARK,
			BraxOffice.INTERIOR_BRAX,
			Darkness.ALWAYS_LIGHT,
			null, "в его офисе") {
		@Override
		public void applyInventoryInit(CharacterInventory inventory) {
			AbstractClothing jacket = Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdjacket", PresetColour.CLOTHING_BLACK, PresetColour.CLOTHING_BLUE, null, false);
			jacket.setSticker("collar", "tab_ip");
			jacket.setSticker("name", "name_brax");
			jacket.setSticker("ribbon", "ribbon_brax");
			inventory.addClothing(jacket);
			
			inventory.addClothing(Main.game.getItemGen().generateClothing("dsg_eep_servequipset_enfdbelt", PresetColour.CLOTHING_DESATURATED_BROWN, false));
			
			AbstractClothing hat = Main.game.getItemGen().generateClothing("dsg_eep_ptrlequipset_pcap", PresetColour.CLOTHING_BLACK, false);
			hat.setSticker("badge", "badge_dominion");
			inventory.addClothing(hat);
		}
		@Override
		public boolean isItemsDisappear() {
			return false;
		}
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.braxEncountered)) {
				return BraxOffice.INTERIOR_BRAX_REPEAT;
			} else {
				return BraxOffice.INTERIOR_BRAX;
			}
		}
		@Override
		public boolean isDangerous() {
			return !Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_C_WOLFS_DEN);
		}
	}.initDangerous()
	.initWeatherImmune();
	
	public static final AbstractPlaceType ENFORCER_HQ_OFFICE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Запертый офис",
			"Дверь в этот кабинет заперта, и остаётся только гадать, что там может находиться",
			"dominion/enforcerHQ/office",
			PresetColour.BASE_GREY,
			EnforcerHQDialogue.OFFICE,
			Darkness.ALWAYS_LIGHT,
			null, "в штаб-квартире энфорсеров")
			.initWeatherImmune();
	
	public static final AbstractPlaceType ENFORCER_HQ_CELLS_OFFICE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Управление клетками",
			"В этом небольшом кабинете происходит проверка всех заключенных, которые входят и выходят из камер.",
			"dominion/enforcerHQ/office",
			PresetColour.BASE_PURPLE,
			EnforcerHQDialogue.CELLS_OFFICE,
			Darkness.ALWAYS_LIGHT,
			null, "в штаб-квартире энфорсеров")
			.initWeatherImmune();
	
	public static final AbstractPlaceType ENFORCER_HQ_CELL = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Клетка",
			"В камерах штаб-квартиры энфорсеров заключенные временно содержатся до тех пор, пока их не обработают должным образом.",
			"dominion/enforcerHQ/cell",
			PresetColour.BASE_BROWN_DARK,
			EnforcerHQDialogue.CELL,
			Darkness.ALWAYS_LIGHT,
			null, "в камерах штаб-квартиры энфорсеров")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_HQ_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Входная группа",
			"Вход в штаб-квартиру энфорсеров состоит из пары звуконепроницаемых стеклянных дверей.",
			"dominion/enforcerHQ/exit",
			PresetColour.BASE_RED,
			EnforcerHQDialogue.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_HQ_ENFORCER_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Вход для энфорсеров",
			"Один из многих непубличных входов в штаб-квартиру энфорсеров. Стеклянные двери со звукоизоляцией открываются только после того, как специальный пропуск будет проведен по расположенному рядом магическому сканеру.",
			"dominion/enforcerHQ/exit",
			PresetColour.BASE_BLUE,
			EnforcerHQDialogue.ENTRANCE_ENFORCER,
			Darkness.ALWAYS_LIGHT,
			null, "")
			.initWeatherImmune();
	public static final AbstractPlaceType ENFORCER_HQ_OFFICE_QUARTERMASTER = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Квартермейстерский офис",
			"Ответственный за управление снаряжением энфорсеров, квартирмейстер штаба имеет свой кабинет, удобно расположенный напротив стола учёта.",
			"dominion/enforcerHQ/office",
			PresetColour.BASE_ORANGE,
			EnforcerHQDialogue.OFFICE_QUARTERMASTER,
			Darkness.ALWAYS_LIGHT,
			null, "")
			.initWeatherImmune();	public static final AbstractPlaceType ENFORCER_HQ_REQUISITIONS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Стол учёта",
			"В этой зоне проверяется специальное или запасное оборудование энфорсеров.",
			"dominion/enforcerHQ/requisitions",
			PresetColour.BASE_TAN,
			EnforcerHQDialogue.REQUISITIONS,
			Darkness.ALWAYS_LIGHT,
			null, "") {
		@Override
		public List<Population> getPopulation() {
			if(!Main.game.getCharactersPresent(Main.game.getWorlds().get(WorldType.ENFORCER_HQ).getCell(ENFORCER_HQ_REQUISITIONS)).contains(Main.game.getNpc(Wes.class))
					&& !Main.game.getCharactersPresent(Main.game.getWorlds().get(WorldType.ENFORCER_HQ).getCell(ENFORCER_HQ_REQUISITIONS)).contains(Main.game.getNpc(Elle.class))) {
				return Util.newArrayListOfValues(new Population(false, PopulationType.ENFORCER, PopulationDensity.ONE, Util.newHashMapOfValues(new Value<>(Subspecies.DOG_MORPH_GERMAN_SHEPHERD, SubspeciesSpawnRarity.TEN))));
			}
			return super.getPopulation();
		}
	}.initWeatherImmune();
        public static final AbstractPlaceType FELICIA_APARTMENT_ENTRYWAY = new AbstractPlaceType(
			WorldRegion.DOMINION,
				"Прихожая",
				"В прихожей квартиры [felicia.morphSingleNameGene([felicia.NamePos])] есть шкаф для одежды.",
			"dominion/feliciaApartment/entranceHall",
			PresetColour.BASE_RED,
			FeliciaApartment.ENTRYWAY,
			Darkness.ALWAYS_LIGHT,
			null,
				"в прихожей квартиры [felicia.morphSingleNameGene([felicia.NamePos])]"
        ).initWeatherImmune();
	
        //Felicia's Apartment       
        public static final AbstractPlaceType FELICIA_APARTMENT_BEDROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
				"Спальня",
			"PLACEHOLDER_FELICIA_APARTMENT_BEDROOM",
			"dominion/feliciaApartment/feliciaBedroom",
			PresetColour.BASE_YELLOW_PALE,
			FeliciaApartment.FELICIA_BEDROOM,
			Darkness.ALWAYS_LIGHT,
			null,
				"в спальне [felicia.morphSingleNameGene([felicia.NamePos])]"
        ).initWeatherImmune();
        public static final AbstractPlaceType FELICIA_APARTMENT_BATHROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
				"Ванная",
			"PLACEHOLDER_FELICIA_APARTMENT_BATHROOM",
			"dominion/feliciaApartment/toilet",
			PresetColour.BASE_BLUE_LIGHT,
			FeliciaApartment.BATHROOM,
			Darkness.ALWAYS_LIGHT,
			null,
				"в ванной в квартире Фелиции"
        ).initWeatherImmune();
        public static final AbstractPlaceType FELICIA_APARTMENT_KITCHEN = new AbstractPlaceType(
			WorldRegion.DOMINION,
				"Кухня",
				"Беспорядочная кухня выходит в прихожую и столовую.",
			"dominion/feliciaApartment/kitchen",
			PresetColour.BASE_ORANGE,
			FeliciaApartment.KITCHEN,
			Darkness.ALWAYS_LIGHT,
			null,
				"на кухне в квартире [felicia.morphSingleNameGene([felicia.NamePos])]"
        ).initWeatherImmune();
	public static final AbstractPlaceType CITY_HALL_STAIRS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Лестница",
			"Лестницы на верхние этажи ратуши обозначены как частные и оцеплены красными веревочными барьерами.",
			"dominion/cityHall/stairs",
			PresetColour.BASE_GREY,
			CityHall.CITY_HALL_STAIRS,
			Darkness.ALWAYS_LIGHT,
			null, "в ратуше Доминиона") {
	}.initWeatherImmune();
        
        public static final AbstractPlaceType FELICIA_APARTMENT_DINING_AREA = new AbstractPlaceType(
			WorldRegion.DOMINION,
				"Столовая",
				"Столовая в квартире [felicia.morphSingleNameGene([felicia.NamePos])] тесновата, несмотря на то, что она рассчитана только на одного человека.",
			"dominion/feliciaApartment/diningArea",
			PresetColour.BASE_BLUE_STEEL,
			FeliciaApartment.DINING_AREA,
			Darkness.ALWAYS_LIGHT,
			null,
				"в столовой в квартире [felicia.morphSingleNameGene([felicia.NamePos])]"
        ).initWeatherImmune();
        
        public static final AbstractPlaceType FELICIA_APARTMENT_LIVING_AREA = new AbstractPlaceType(
			WorldRegion.DOMINION,
				"Гостиная",
				"Гостиная [felicia.morphSingleNameGene([felicia.Name])] оформлена скудно, но из неё открывается вид на улицы Доминиона.",
			"dominion/feliciaApartment/livingArea",
			PresetColour.BASE_INDIGO,
			FeliciaApartment.LIVING_AREA,
			Darkness.ALWAYS_LIGHT,
			null,
				"в гостиной в квартире [felicia.morphSingleNameGene([felicia.NamePos])]"
        ).initWeatherImmune();
        
        public static final AbstractPlaceType FELICIA_APARTMENT_HALLWAY = new AbstractPlaceType(
			WorldRegion.DOMINION,
				"Прихожая",
				"Прихожая полностью лишена декора и мебели.",
			null,
			PresetColour.BASE_BLACK,
			FeliciaApartment.HALLWAY,
			Darkness.ALWAYS_LIGHT,
			null,
				"в прихожей в квартире [felicia.morphSingleNameGene([felicia.NamePos])]"
        ).initWeatherImmune();
        
	// Enforcer warehouse:
	
	public static final AbstractPlaceType ENFORCER_WAREHOUSE_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Вход",
			"Единственный вход на склад охраняет небольшая будка, за которой сидят энфорсеры.",
			"dominion/enforcerWarehouse/exit",
			PresetColour.BASE_RED,
			EnforcerWarehouse.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initDangerous()
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_WAREHOUSE_CORRIDOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"Многочисленные коридоры, с обеих сторон заставленные деревянными ящиками, петляют по складу.",
			null,
			PresetColour.BASE_BLACK,
			EnforcerWarehouse.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_WAREHOUSE_CLAIRE_WARNING = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"Многочисленные коридоры, с обеих сторон заставленные деревянными ящиками, петляют по складу.",
			null,
			PresetColour.BASE_BLACK,
			EnforcerWarehouse.CLAIRE_WARNING,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_WAREHOUSE_ENCLOSURE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Ограждающие шкафы",
			"Забытый угол склада, закрытый со всех сторон высокими штабелями деревянных ящиков.",
			null,
			PresetColour.BASE_BLACK,
			EnforcerWarehouse.ENCLOSURE,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_WAREHOUSE_ENCLOSURE_TELEPORT_PADS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Телепортационная площадка",
			"Телепортационная площадка, на которую ты [pc.genderBasedWord(попал, попала)] вместе с Клэр, находится в этой зоне.",
			"dominion/enforcerWarehouse/teleportPads",
			PresetColour.BASE_MAGENTA,
			EnforcerWarehouse.ENCLOSURE_TELEPORT_PADS,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_WAREHOUSE_ENCLOSURE_TELEPORT_SHELVING = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Стеллажи",
			"В этом углу сложены несколько отдельно стоящих стеллажей.",
			"dominion/enforcerWarehouse/shelving",
			PresetColour.BASE_PURPLE_LIGHT,
			EnforcerWarehouse.ENCLOSURE_SHELVING,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_WAREHOUSE_ENFORCER_GUARD_POST = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Охранный пост энфорсеров",
			"По всему складу разбросано несколько постов охраны энфорсеров, которые состоят не более чем из стула и стола.",
			"dominion/enforcerWarehouse/enforcerGuardPost",
			PresetColour.BASE_BLUE_STEEL,
			EnforcerWarehouse.ENFORCER_GUARD_POST,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initDangerous()
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_WAREHOUSE_CRATES = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Ящики",
			"Один или два ящика в этой части склада ещё не запечатаны.",
			"dominion/enforcerWarehouse/crates",
			PresetColour.BASE_ORANGE,
			EnforcerWarehouse.CRATES,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_WAREHOUSE_CRATES_SEARCHED = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Ящики (обысканные)",
			"Один или два ящика в этой части склада ещё не были запечатаны, что позволило обыскать их.",
			"dominion/enforcerWarehouse/cratesSearched",
			PresetColour.BASE_GREY,
			EnforcerWarehouse.CRATES,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_WAREHOUSE_CRATES_ARK = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Ящики",
			"Один или два ящика в этой части склада ещё не запечатаны.",
			"dominion/enforcerWarehouse/crates",
			PresetColour.BASE_ORANGE,
			EnforcerWarehouse.CRATES_ARK,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_WAREHOUSE_CRATES_ARK_SEARCHED = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Ящики (обысканные)",
			"Один или два ящика в этой части склада ещё не были запечатаны, что позволило обыскать их.",
			"dominion/enforcerWarehouse/cratesSearched",
			PresetColour.BASE_GREY,
			EnforcerWarehouse.CRATES_ARK,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_WAREHOUSE_CRATES_LUST_WEAPON = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"«Совершенно секретный» ящик",
			"Один из ящиков в этой зоне помечен как «Совершенно секретный».",
			"dominion/enforcerWarehouse/cratesLustWeapon",
			PresetColour.BASE_PINK_DEEP,
			EnforcerWarehouse.CRATES_LUST_WEAPON,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_WAREHOUSE_CRATES_SPELL_BOOK = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Стеллаж",
			"Вместо обычных ящиков, которые можно найти по всему складу, в этой зоне находятся стеллажи, заполненные запрещенными книгами и другой подобной контрабандной литературой.",
			"dominion/enforcerWarehouse/shelvingSpellBook",
			PresetColour.BASE_MAGENTA,
			EnforcerWarehouse.SHELVES_SPELL_BOOK,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initWeatherImmune();

	public static final AbstractPlaceType ENFORCER_WAREHOUSE_CRATES_SPELL_BOOK_SEARCHED = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Стеллаж (обысканный)",
			"Вместо обычных ящиков, которые можно найти по всему складу, в этой зоне находятся стеллажи, заполненные запрещенными книгами и другой подобной контрабандной литературой. You've already searched through them and found a spell book.",
			"dominion/enforcerWarehouse/shelvingSearched",
			PresetColour.BASE_GREY,
			EnforcerWarehouse.SHELVES_SPELL_BOOK,
			Darkness.ALWAYS_LIGHT,
			null, "на складе энфорсеров")
			.initWeatherImmune();
	
	
	// City hall:
	
	public static final AbstractPlaceType CITY_HALL_CORRIDOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"Мраморные коридоры ратуши Доминиона позволяют чиновникам с легкостью переходить из одного кабинета в другой.",
			null,
			PresetColour.BASE_BLACK,
			CityHall.CITY_HALL_CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "в ратуше Доминиона") {

		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.OFFICE_WORKER, PopulationDensity.SEVERAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
	}.initWeatherImmune();

	public static final AbstractPlaceType CITY_HALL_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Вход",
			"Вход в ратушу представляет собой пару вращающихся стеклянных дверей, на одной из которых написано «Выход», а на другой - «Вход: нет доступа».",
			"dominion/cityHall/exit",
			PresetColour.BASE_RED,
			CityHall.CITY_HALL_FOYER,
			Darkness.ALWAYS_LIGHT,
			null, "в ратуше Доминиона") {

		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.PERSON, PopulationDensity.SEVERAL, Subspecies.getDominionStormImmuneSpecies(true)));
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType CITY_HALL_INFORMATION_DESK = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Информационная стойка",
			"В центре большого вестибюля стоит круглая стойка, за которой работают несколько администраторов.",
			"dominion/cityHall/front_desk",
			PresetColour.BASE_BLUE_LIGHT,
			CityHall.CITY_HALL_INFORMATION_DESK,
			Darkness.ALWAYS_LIGHT,
			null, "в ратуше Доминиона") {

		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.PERSON, PopulationDensity.SEVERAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType CITY_HALL_WAITING_AREA = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната ожидания",
			"В дальней части зала ожидания открытой планировки висят большие аналоговые часы; медленное тиканье секундной стрелки постоянно напоминает всем присутствующим о губительной неэффективности бюрократии.",
			"dominion/cityHall/waiting_area",
			PresetColour.BASE_PURPLE_LIGHT,
			CityHall.CITY_HALL_WAITING_AREA,
			Darkness.ALWAYS_LIGHT,
			null, "в ратуше Доминиона") {

		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.PERSON, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
	}.initWeatherImmune();

	public static final AbstractPlaceType CITY_HALL_OFFICE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Кабинет",
			"На двери этого кабинета есть надпись «Личный», дающая всем понять, что внутри не происходит абсолютно ничего важного.",
			"dominion/cityHall/office",
			PresetColour.BASE_ORANGE,
			CityHall.CITY_HALL_OFFICE,
			Darkness.ALWAYS_LIGHT,
			null, "в ратуше Доминиона") {
	}.initWeatherImmune();
	public static final AbstractPlaceType CITY_HALL_BUREAU_OF_DEMOGRAPHICS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Бюро демографии",
			"«Бюро демографии» состоит из небольшого офиса, примыкающего к огромному хранилищу, напоминающему библиотеку.",
			"dominion/cityHall/officeDemographics",
			PresetColour.BASE_TEAL,
			CityHallDemographics.CITY_HALL_DEMOGRAPHICS_ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в ратуше Доминиона") {
	}.initWeatherImmune();
	public static final AbstractPlaceType CITY_HALL_ARCHIVES = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Бюро демографии (архивы)",
			"«Бюро демографии» состоит из небольшого офиса, примыкающего к огромному хранилищу, напоминающему библиотеку.",
			"dominion/cityHall/officeDemographicsArchives",
			PresetColour.BASE_BLUE, // Player cannot enter this tile.
			CityHallDemographics.CITY_HALL_DEMOGRAPHICS_ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в ратуше Доминиона") {
	}.initWeatherImmune();
	public static final AbstractPlaceType CITY_HALL_BUREAU_OF_PROPERTY_RIGHTS_AND_COMMERCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"Бюро собственности и торговли - один из самых крупных и хорошо финансируемых отделов мэрии, состоящий из множества взаимосвязанных офисов и конференц-залов.",
			"dominion/cityHall/officeProperty",
			PresetColour.BASE_GOLD,
			CityHallProperty.CITY_HALL_PROPERTY_ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в ратуше Доминиона") {
	}.initWeatherImmune();
	public static final AbstractPlaceType HOME_IMPROVEMENTS_CORRIDOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Проходы",
			"Широкие проходы с бетонным полом проходят через весь магазин, предоставляя покупателям достаточно места, чтобы катать тележки друг за другом.",
			null,
			PresetColour.BASE_BLACK,
			HomeImprovements.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "в «Магазине „Сделай сам“ Аргуса»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.SHOPPER, PopulationDensity.SEVERAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
	}.initWeatherImmune();

	
	// Home Improvements:
	public static final AbstractPlaceType HOME_IMPROVEMENTS_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Вход",
			"Вход в ратушу представляет собой пару вращающихся стеклянных дверей, на одной из которых написано «Выход», а на другой - «Вход: нет доступа».",
			"dominion/homeImprovements/exit",
			PresetColour.BASE_RED,
			HomeImprovements.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в «Магазине „Сделай сам“ Аргуса»") {
		@Override
		public List<Population> getPopulation() {
			return HOME_IMPROVEMENTS_CORRIDOR.getPopulation();
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType HOME_IMPROVEMENTS_SHELVING_PREMIUM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Стеллажи (премиум)",
			"На стеллажах, расположенных напротив главного входа, вы найдете краски премиум-класса, инструменты и другие различные принадлежности для самостоятельного ремонта.",
			"dominion/homeImprovements/shelving",
			PresetColour.BASE_GOLD,
			HomeImprovements.SHELVING_PREMIUM,
			Darkness.ALWAYS_LIGHT,
			null, "в «Магазине „Сделай сам“ Аргуса»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.SHOPPER, PopulationDensity.FEW, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType HOME_IMPROVEMENTS_SHELVING_STANDARD = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Стеллажи (стандартные)",
			"Стеллажи, расположенные вдоль основных проходов, заполнены «стандартными» красками, инструментами и другими различными принадлежностями для самостоятельного ремонта.",
			"dominion/homeImprovements/shelving",
			PresetColour.BASE_BROWN,
			HomeImprovements.SHELVING_STANDARD,
			Darkness.ALWAYS_LIGHT,
			null, "в «Магазине „Сделай сам“ Аргуса»") {
		@Override
		public List<Population> getPopulation() {
			return HOME_IMPROVEMENTS_CORRIDOR.getPopulation();
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType HOME_IMPROVEMENTS_BUILDING_SUPPLIES = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Строительные материалы",
			"В задней части склада расположены многочисленные стеллажи, заполненные древесиной, плиткой, трубопроводами и другими строительными материалами.",
			"dominion/homeImprovements/crates",
			PresetColour.BASE_ORANGE,
			HomeImprovements.BUILDING_SUPPLIES,
			Darkness.ALWAYS_LIGHT,
			null, "в «Магазине „Сделай сам“ Аргуса»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.SHOPPER, PopulationDensity.COUPLE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType HOME_IMPROVEMENTS_OFFICE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Офис управляющего",
			"Офис Аргуса, владельца и управляющего этим бизнесом, расположен недалеко от входа на склад.",
			"dominion/homeImprovements/office",
			PresetColour.BASE_MAGENTA,
			HomeImprovements.OFFICE,
			Darkness.ALWAYS_LIGHT,
			null, "в офисе Аргуса")
	.initWeatherImmune();
	public static final AbstractPlaceType HOME_IMPROVEMENTS_TOILETS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Туалеты",
			"В заднем углу склада есть несколько туалетов, которыми могут воспользоваться покупатели.",
			"dominion/homeImprovements/toilets",
			PresetColour.BASE_BLUE_LIGHT,
			HomeImprovements.TOILETS,
			Darkness.ALWAYS_LIGHT,
			null, "в туалете в «Магазине „Сделай сам“ Аргуса»")
	.initWeatherImmune();
	public static final AbstractPlaceType DOMINION_EXPRESS_CORRIDOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"Коридор проходит по всей длине склада и фактически отделяет складскую зону открытой планировки от многочисленных офисов предприятия.",
			null,
			PresetColour.BASE_BLACK,
			DominionExpress.CORRIDOR,
			Darkness.ALWAYS_LIGHT, Encounter.DOMINION_EXPRESS, "на складе «Экспресс Доминиона»") {
		@Override
		public List<Population> getPopulation() {
			if(Main.game.isExtendedWorkTime()) {
				return Util.newArrayListOfValues(new Population(true, PopulationType.SLAVE, PopulationDensity.SEVERAL, Util.newHashMapOfValues(new Value<>(Subspecies.CENTAUR, SubspeciesSpawnRarity.TEN))));
			}
			return Util.newArrayListOfValues(new Population(true, PopulationType.SLAVE, PopulationDensity.COUPLE, Util.newHashMapOfValues(new Value<>(Subspecies.CENTAUR, SubspeciesSpawnRarity.TEN))));
		}
	}.initWeatherImmune();

	
	// Dominion Express:
	public static final AbstractPlaceType DOMINION_EXPRESS_EXIT = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Вход",
			"С одной стороны от входа в склад находится одинокая стойка ресепшн, за которой сидят секретари, следящие за тем, чтобы у посетителей была причина находиться здесь.",
			"dominion/dominionExpress/exit",
			PresetColour.BASE_RED,
			DominionExpress.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "на складе «Экспресс Доминиона»") {
		@Override
		public List<Population> getPopulation() {
			if(Main.game.isExtendedWorkTime()) {
				return Util.newArrayListOfValues(new Population(true, PopulationType.RECEPTIONIST, PopulationDensity.FEW, Util.newHashMapOfValues(new Value<>(Subspecies.HORSE_MORPH, SubspeciesSpawnRarity.TEN))));
			}
			return Util.newArrayListOfValues(new Population(false, PopulationType.RECEPTIONIST, PopulationDensity.ONE, Util.newHashMapOfValues(new Value<>(Subspecies.HORSE_MORPH, SubspeciesSpawnRarity.TEN))));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType DOMINION_EXPRESS_STORAGE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Хранилище",
			"Половина площади склада отведена под временное хранение товаров.",
			"dominion/dominionExpress/crates",
			PresetColour.BASE_ORANGE,
			DominionExpress.STORAGE,
			Darkness.ALWAYS_LIGHT, Encounter.DOMINION_EXPRESS, "на складе «Экспресс Доминиона»") {
		@Override
		public List<Population> getPopulation() {
			return DOMINION_EXPRESS_CORRIDOR.getPopulation();
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType DOMINION_EXPRESS_OFFICE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Кабинет",
			"В этих офисах осуществляется повседневное управление «Экспресс Доминиона».",
			"dominion/dominionExpress/office",
			PresetColour.BASE_BLUE_LIGHT,
			DominionExpress.OFFICE,
			Darkness.ALWAYS_LIGHT,
			null, "на складе «Экспресс Доминиона»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.OFFICE_WORKER, PopulationDensity.OCCASIONAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType DOMINION_EXPRESS_FILLY_STATION = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Станция вознаграждения кобылок",
			"В небольшой нише на одной из сторон складского коридора стоит любопытный магический торговый автомат, четко обозначенный как «Станция вознаграждения кобылок».",
			"dominion/dominionExpress/fillyStation",
			PresetColour.BASE_PINK_LIGHT,
			DominionExpress.FILLY_STATION,
			Darkness.ALWAYS_LIGHT,
			null, "на складе «Экспресс Доминиона»")
		.initWeatherImmune();
	public static final AbstractPlaceType DOMINION_EXPRESS_OFFICE_STABLE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Кабинет хозяйки конюшни",
			"Офис, отвечающий за уход и управление рабами-кентаврами, находится в дальнем конце коридора склада.",
			"dominion/dominionExpress/officeStable",
			PresetColour.BASE_TAN,
			DominionExpress.OFFICE_STABLE,
			Darkness.ALWAYS_LIGHT,
			null, "в кабинете хозяйки конюшни на складе «Экспресс Доминиона»")
	.initWeatherImmune();
	public static final AbstractPlaceType DOMINION_EXPRESS_STABLES = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Конюшни",
			"В этой большой одноэтажной пристройке находится огромное количество стойл, каждое из которых достаточно велико, чтобы в нём с комфортом разместился раб-кентавр.",
			"dominion/dominionExpress/stables",
			PresetColour.BASE_BROWN,
			DominionExpress.STABLES,
			Darkness.ALWAYS_LIGHT,
			null, "в стойлах на складе «Экспресс Доминиона»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.SLAVE, PopulationDensity.NUMEROUS, Util.newHashMapOfValues(new Value<>(Subspecies.CENTAUR, SubspeciesSpawnRarity.TEN))));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType HARPY_NESTS_WALKWAYS = new AbstractPlaceType(
			WorldRegion.HARPY_NESTS,
			"Аллея",
			"Гнезда гарпий соединены между собой узкими деревянными дорожками, построенными на вершине жилых домов Доминиона.",
			null,
			PresetColour.BASE_BLACK,
			HarpyNestsDialogue.WALKWAY,
			Darkness.ALWAYS_LIGHT, Encounter.HARPY_NEST_WALKWAYS, "в гнёздах гарпий") {
		@Override
		public boolean isDangerous() {
			return !Main.game.getPlayer().isQuestCompleted(QuestLine.SIDE_HARPY_PACIFICATION) || Main.game.getCurrentWeather()==Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			if(Main.game.getCurrentWeather() == Weather.MAGIC_STORM) {
				return super.getPopulation();
			} else {
				return Util.newArrayListOfValues(new Population(true, PopulationType.HARPY, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.HARPY_NEST, this, false, false)));
			}
		}
	}.initSexNotBlockedFromCharacterPresent();
	
	
	
	// Harpy Nests:
	public static final AbstractPlaceType HARPY_NESTS_WALKWAYS_BRIDGE = new AbstractPlaceType(
			WorldRegion.HARPY_NESTS,
			"Пешеходный мост",
			"То тут, то там над улицами внизу перекинуты мосты, соединяющие один комплекс аллей с другим.",
			"dominion/harpyNests/bridge",
			PresetColour.BASE_GREY,
			HarpyNestsDialogue.WALKWAY_BRIDGE,
			Darkness.ALWAYS_LIGHT, Encounter.HARPY_NEST_WALKWAYS, "в гнёздах гарпий") {
		@Override
		public boolean isDangerous() {
			return !Main.game.getPlayer().isQuestCompleted(QuestLine.SIDE_HARPY_PACIFICATION) || Main.game.getCurrentWeather()==Weather.MAGIC_STORM;
		}
		@Override
		public List<Population> getPopulation() {
			return HARPY_NESTS_WALKWAYS.getPopulation();
		}
	}.initSexNotBlockedFromCharacterPresent();
	public static final AbstractPlaceType HARPY_NESTS_ENTRANCE_ENFORCER_POST = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Пост энфорсеров",
			"Чтобы поддерживать мир между бесчисленными ссорящимися гарпиями, необходим хорошо укомплектованный аванпост энфорсеров.",
			"dominion/harpyNests/exit",
			PresetColour.BASE_RED,
			HarpyNestsDialogue.ENTRANCE_ENFORCER_POST,
			Darkness.ALWAYS_LIGHT,
			null, "в гнёздах гарпий") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.ENFORCER, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true, Subspecies.HUMAN)));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType HARPY_NESTS_HELENAS_NEST = new AbstractPlaceType(
			WorldRegion.HARPY_NESTS,
			"Гнездо Елены",
			"Потрясающе красивая матриарх гарпий, Елена, правит самым большим из всех гнёзд гарпий.",
			"dominion/harpyNests/nestHelena",
			PresetColour.BASE_GOLD,
			HarpyNestHelena.HELENAS_NEST_EXTERIOR,
			Darkness.ALWAYS_LIGHT,
			null, "в гнезде Елены") {
		@Override
		public List<Population> getPopulation() {
			return HARPY_NESTS_WALKWAYS.getPopulation();
		}
	};
	public static final AbstractPlaceType HARPY_NESTS_HARPY_NEST_RED = new AbstractPlaceType(
			WorldRegion.HARPY_NESTS,
			"Гнездо гарпий",
			"Гнездо Дианы состоит в основном из злобных красных гарпий, цвет их перьев пытается подражать внешности их садистского лидера.",
			"dominion/harpyNests/nestRed",
			PresetColour.BASE_CRIMSON,
			null,
			Darkness.ALWAYS_LIGHT,
			null, "в гнезде Дианы") {
		@Override
		public List<Population> getPopulation() {
			return HARPY_NESTS_WALKWAYS.getPopulation();
		}
		@Override
		protected DialogueNode getBaseDialogue(Cell cell) {
			return DialogueManager.getDialogueFromId("innoxia_places_dominion_harpy_nests_dominant_exterior");
		}
	};
	public static final AbstractPlaceType HARPY_NESTS_HARPY_NEST_PINK = new AbstractPlaceType(
			WorldRegion.HARPY_NESTS,
			"Гнездо гарпий",
			"В гнезде Лекси обитает непропорционально большое количество самцов гарпий, каждый из которых околачивается поблизости в надежде трахнуть свою сексуально озабоченную матриарх.",
			"dominion/harpyNests/nestPink",
			PresetColour.BASE_PINK_LIGHT,
			null,
			Darkness.ALWAYS_LIGHT,
			null, "в гнезде Лекси") {
		@Override
		public List<Population> getPopulation() {
			return HARPY_NESTS_WALKWAYS.getPopulation();
		}
		@Override
		protected DialogueNode getBaseDialogue(Cell cell) {
			return DialogueManager.getDialogueFromId("innoxia_places_dominion_harpy_nests_nympho_exterior");
		}
	};
	public static final AbstractPlaceType HARPY_NESTS_HARPY_NEST_YELLOW = new AbstractPlaceType(
			WorldRegion.HARPY_NESTS,
			"Гнездо гарпий",
			"В гнезде Бретани обитает значительная популяция светловолосых, большегрудых гарпий.",
			"dominion/harpyNests/nestYellow",
			PresetColour.BASE_YELLOW_LIGHT,
			null,
			Darkness.ALWAYS_LIGHT,
			null, "в гнезде Бретани") {
		@Override
		public List<Population> getPopulation() {
			return HARPY_NESTS_WALKWAYS.getPopulation();
		}
		@Override
		protected DialogueNode getBaseDialogue(Cell cell) {
			return DialogueManager.getDialogueFromId("innoxia_places_dominion_harpy_nests_bimbo_exterior");
		}
	};
	public static final AbstractPlaceType LILAYA_HOME_CORRIDOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"По центру каждого из коридоров в доме Лилайи проложена безупречно чистая красная дорожка, а стены украшают прекрасные картины и искусно вырезанные мраморные бюсты.",
			null,
			PresetColour.BASE_GREY,
			LilayaHomeGeneric.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			Encounter.LILAYAS_HOME_CORRIDOR,
			"в доме Лилайи"
		).initItemsPersistInTile()
		.initWeatherImmune();
	
			
	// Lilaya's home (ground floor):
	public static final AbstractPlaceType LILAYA_HOME_ROOM_WINDOW_GROUND_FLOOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната",
			"Внешние комнаты на первом этаже, включая эту, выходят окнами либо на улицы Доминиона, либо на улочки, проходящие вокруг дома.",
			"dominion/lilayasHome/room",
			PresetColour.BASE_GREY,
			LilayaHomeGeneric.ROOM_WINDOW,
			Darkness.ALWAYS_LIGHT,
			null,
			"в доме Лилайи") {
		@Override
		protected DialogueNode getBaseDialogue(Cell cell) {
			if(cell!=null) {
				for(AbstractPlaceUpgrade pu : cell.getPlace().getPlaceUpgrades()) {
					if(pu.getRoomDialogue(cell)!=null) {
						return pu.getRoomDialogue(cell);
					}
				}
			}
			return LilayaHomeGeneric.ROOM_WINDOW;
		}
		@Override
		public ArrayList<AbstractPlaceUpgrade> getStartingPlaceUpgrades() {
			return Util.newArrayListOfValues(PlaceUpgrade.LILAYA_EMPTY_ROOM);
		}
		@Override
		public ArrayList<AbstractPlaceUpgrade> getAvailablePlaceUpgrades(Set<AbstractPlaceUpgrade> upgrades) {
			if(upgrades.contains(PlaceUpgrade.LILAYA_GUEST_ROOM)) {
				return PlaceUpgrade.getGuestRoomUpgrades();

			} else if(upgrades.contains(PlaceUpgrade.LILAYA_SLAVE_ROOM)) {
				return PlaceUpgrade.getSlaveQuartersUpgradesSingle();

			} else if(upgrades.contains(PlaceUpgrade.LILAYA_SLAVE_ROOM_DOUBLE)) {
				return PlaceUpgrade.getSlaveQuartersUpgradesDouble();

			} else if(upgrades.contains(PlaceUpgrade.LILAYA_SLAVE_ROOM_QUADRUPLE)) {
				return PlaceUpgrade.getSlaveQuartersUpgradesQuadruple();

			} else if(upgrades.contains(PlaceUpgrade.LILAYA_MILKING_ROOM)) {
				return PlaceUpgrade.getMilkingUpgrades();

			} else if(upgrades.contains(PlaceUpgrade.LILAYA_OFFICE)) {
				return PlaceUpgrade.getOfficeUpgrades();

			} else if(upgrades.contains(PlaceUpgrade.LILAYA_SPA)) {
				return PlaceUpgrade.getSpaUpgrades();

			} else if(upgrades.contains(PlaceUpgrade.LILAYA_DINING_HALL)) {
				return PlaceUpgrade.getDiningHallUpgrades();

			} else if(upgrades.contains(PlaceUpgrade.LILAYA_SLAVE_LOUNGE)) {
				return PlaceUpgrade.getSlaveLoungeUpgrades();
			}

			return PlaceUpgrade.getCoreRoomUpgrades();
		}
		@Override
		public boolean isAbleToBeUpgraded() {
			return true;
		}
		@Override
		public String getPlaceNameAppendFormat(int count) {
			return " G-"+String.format("%02d", count);
		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	public static final AbstractPlaceType LILAYA_HOME_ROOM_GARDEN_GROUND_FLOOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната у сада",
			"Все внутренние комнаты на первом этаже соединены с частным садом при помощи дверей-патио.",
			"dominion/lilayasHome/room",
			PresetColour.BASE_GREY,
			LilayaHomeGeneric.ROOM_GARDEN_GROUND_FLOOR,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Лилайи") {
		@Override
		protected DialogueNode getBaseDialogue(Cell cell) {
			if(cell!=null) {
				for(AbstractPlaceUpgrade pu : cell.getPlace().getPlaceUpgrades()) {
					if(pu.getRoomDialogue(cell)!=null) {
						return pu.getRoomDialogue(cell);
					}
				}
			}
			return LilayaHomeGeneric.ROOM_GARDEN_GROUND_FLOOR;
		}
		@Override
		public ArrayList<AbstractPlaceUpgrade> getStartingPlaceUpgrades() {
			return Util.newArrayListOfValues(PlaceUpgrade.LILAYA_EMPTY_ROOM);
		}
		@Override
		public ArrayList<AbstractPlaceUpgrade> getAvailablePlaceUpgrades(Set<AbstractPlaceUpgrade> upgrades) {
			return LILAYA_HOME_ROOM_WINDOW_GROUND_FLOOR.getAvailablePlaceUpgrades(upgrades);
		}
		@Override
		public boolean isAbleToBeUpgraded() {
			return true;
		}
		@Override
		public String getPlaceNameAppendFormat(int count) {
			return " GG-"+String.format("%02d", count);
		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	public static final AbstractPlaceType LILAYA_HOME_ROOM_GARDEN_FIRST_FLOOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната у сада",
			"Внутренние комнаты на втором этаже, включая эту, выходят окнами в частный сад.",
			"dominion/lilayasHome/room",
			PresetColour.BASE_GREY,
			LilayaHomeGeneric.ROOM_GARDEN,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Лилайи") {
		@Override
		protected DialogueNode getBaseDialogue(Cell cell) {
			if(cell!=null) {
				for(AbstractPlaceUpgrade pu : cell.getPlace().getPlaceUpgrades()) {
					if(pu.getRoomDialogue(cell)!=null) {
						return pu.getRoomDialogue(cell);
					}
				}
			}
			return LilayaHomeGeneric.ROOM_GARDEN;
		}
		@Override
		public ArrayList<AbstractPlaceUpgrade> getStartingPlaceUpgrades() {
			return Util.newArrayListOfValues(PlaceUpgrade.LILAYA_EMPTY_ROOM);
		}
		@Override
		public ArrayList<AbstractPlaceUpgrade> getAvailablePlaceUpgrades(Set<AbstractPlaceUpgrade> upgrades) {
			return LILAYA_HOME_ROOM_WINDOW_GROUND_FLOOR.getAvailablePlaceUpgrades(upgrades);
		}
		@Override
		public boolean isAbleToBeUpgraded() {
			return true;
		}
		@Override
		public String getPlaceNameAppendFormat(int count) {
			return " FG-"+String.format("%02d", count);
		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	
	public static final AbstractPlaceType LILAYA_HOME_ROOM_WINDOW_FIRST_FLOOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната",
			"Внешние комнаты на втором этаже, включая эту, выходят окнами либо на улицы Доминиона, либо на улочки, проходящие вокруг дома.",
			"dominion/lilayasHome/room",
			PresetColour.BASE_GREY,
			LilayaHomeGeneric.ROOM_WINDOW,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Лилайи") {
		@Override
		protected DialogueNode getBaseDialogue(Cell cell) {
			if(cell!=null) {
				for(AbstractPlaceUpgrade pu : cell.getPlace().getPlaceUpgrades()) {
					if(pu.getRoomDialogue(cell)!=null) {
						return pu.getRoomDialogue(cell);
					}
				}
			}
			return LilayaHomeGeneric.ROOM_WINDOW;
		}
		@Override
		public ArrayList<AbstractPlaceUpgrade> getStartingPlaceUpgrades() {
			return Util.newArrayListOfValues(PlaceUpgrade.LILAYA_EMPTY_ROOM);
		}
		@Override
		public ArrayList<AbstractPlaceUpgrade> getAvailablePlaceUpgrades(Set<AbstractPlaceUpgrade> upgrades) {
			return LILAYA_HOME_ROOM_WINDOW_GROUND_FLOOR.getAvailablePlaceUpgrades(upgrades);
		}
		@Override
		public boolean isAbleToBeUpgraded() {
			return true;
		}
		@Override
		public String getPlaceNameAppendFormat(int count) {
			return " F-"+String.format("%02d", count);
		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	public static final AbstractPlaceType LILAYA_HOME_STAIR_UP = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Лестница",
			"Эта лестница соединяет первый и второй этажи дома Лилайи и имеет небольшую вспомогательную площадку на полпути вверх.",
			"dominion/lilayasHome/stairsUp",
			PresetColour.BASE_GREEN_LIGHT,
			LilayaHomeGeneric.STAIRCASE_UP,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Лилайи"
			).initItemsPersistInTile()
			.initWeatherImmune();
	
	public static final AbstractPlaceType LILAYA_HOME_DUNGEON_CELL = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Камера в подземелье",
			"Камеры в подземелье Лилайи спроектированы так, что в них тесно и неудобно.",
			"dominion/lilayasHome/roomSlave",
			PresetColour.BASE_GREY,//BASE_MAGENTA
			LilayaHomeGeneric.DUNGEON_CELL,
			Darkness.ALWAYS_LIGHT,
			null,
			"в подземелье Лилайи") {
		@Override
		protected DialogueNode getBaseDialogue(Cell cell) {
			if(cell!=null) {
				for(AbstractPlaceUpgrade pu : cell.getPlace().getPlaceUpgrades()) {
					if(pu.getRoomDialogue(cell)!=null) {
						return pu.getRoomDialogue(cell);
					}
				}
			}
			return LilayaHomeGeneric.DUNGEON_CELL;
		}
		@Override
		public ArrayList<AbstractPlaceUpgrade> getStartingPlaceUpgrades() {
			return Util.newArrayListOfValues(PlaceUpgrade.LILAYA_DUNGEON_CELL);
		}
		@Override
		public ArrayList<AbstractPlaceUpgrade> getAvailablePlaceUpgrades(Set<AbstractPlaceUpgrade> upgrades) {
			return PlaceUpgrade.getDungeonCellUpgrades();
		}
		@Override
		public boolean isAbleToBeUpgraded() {
			return true;
		}
		@Override
		public String getPlaceNameAppendFormat(int count) {
			return " D-"+String.format("%02d", count);
		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	
	public static final AbstractPlaceType LILAYA_HOME_ARTHUR_ROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната Артура",
			"В переделанном из магазина помещении живет бывший любовник и коллега Лилайи, Артур.",
			"dominion/lilayasHome/roomArthur",
			PresetColour.BASE_BLUE_STEEL,
			RoomArthur.ROOM_ARTHUR,
			Darkness.ALWAYS_LIGHT,
			null, "в комнате Артура"
			).initItemsPersistInTile()
			.initWeatherImmune();
	
	public static final AbstractPlaceType LILAYA_HOME_BIRTHING_ROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Родильная комната",
			"Ты не совсем понимаешь, зачем Лилайе специальная родильная комната, но предполагаешь, что когда-то она предназначалась для магических исследований.",
			"dominion/lilayasHome/roomBirthing",
			PresetColour.BASE_PINK,
			LilayaHomeGeneric.BIRTHING_ROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Лилайи"
			).initItemsPersistInTile()
			.initWeatherImmune();
	
	public static final AbstractPlaceType LILAYA_HOME_KITCHEN = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Кухня",
			"В задней части дома находится огромная, хорошо оборудованная кухня.",
			"dominion/lilayasHome/kitchen",
			PresetColour.BASE_TAN,
			LilayaHomeGeneric.KITCHEN,
			Darkness.ALWAYS_LIGHT,
			null, "на кухне Лилайи"
			).initItemsPersistInTile()
			.initWeatherImmune();
	
	public static final AbstractPlaceType LILAYA_HOME_LIBRARY = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Библиотека",
			"В одном из углов первого этажа расположена огромная библиотека, в которой ряд за рядом стоят стеллажи, заполненные книгами.",
			"dominion/lilayasHome/library",
			PresetColour.BASE_TEAL,
			Library.LIBRARY,
			Darkness.ALWAYS_LIGHT,
			null, "в библиотеке Лилайи") {
		@Override
		public void applyInventoryInit(CharacterInventory inventory) {
			inventory.addItem(Main.game.getItemGen().generateItem(ItemType.getLoreBook(Subspecies.HALF_DEMON)));
		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	public static final AbstractPlaceType LILAYA_HOME_STAIR_UP_SECONDARY = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Лестница",
			"Эта лестница, хотя и меньше главной, расположенной у входа в особняк, выполняет ту же задачу - соединяет первый и второй этажи дома Лилайи.",
			"dominion/lilayasHome/stairsUpSecondary",
			PresetColour.BASE_GREEN_LIME,
			LilayaHomeGeneric.STAIRCASE_UP_SECONDARY,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Лилайи"
			).initItemsPersistInTile()
			.initWeatherImmune();
	public static final AbstractPlaceType LILAYA_HOME_ENTRANCE_HALL = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Вестибюль",
			"Изящные картины и мраморные бюсты украшают стены этого грандиозного вестибюля, а огромная хрустальная люстра свисает с двухъярусного потолка над головой.",
			"dominion/lilayasHome/entranceHall",
			PresetColour.BASE_RED,
			LilayaHomeGeneric.ENTRANCE_HALL,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Лилайи"
			).initItemsPersistInTile()
			.initWeatherImmune();
	public static final AbstractPlaceType LILAYA_HOME_GARDEN = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Сад",
			"Этот частный сад со всех четырех сторон окружён стенами дома Лилайи.",
			null,
			PresetColour.BASE_GREEN,
			LilayaHomeGeneric.GARDEN,
			Darkness.DAYLIGHT,
			null, "в саду Лилайи"
			).initItemsPersistInTile()
			.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_GREEN);
	
	public static final AbstractPlaceType LILAYA_HOME_LAB = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Лаба Лилайи",
			"Комната в одном из углов первого этажа была превращена в специальную лабораторию, в которой Лилайя проводит почти всё своё время.",
			"dominion/lilayasHome/lab",
			PresetColour.BASE_GREEN_LIME,
			Lab.LAB,
			Darkness.ALWAYS_LIGHT,
			null, "в лаборатории Лилайи") {
//		@Override
//		public void applyInventoryInit(CharacterInventory inventory) {
//			inventory.addClothing(Main.game.getItemGen().generateClothing("innoxia_scientist_safety_goggles", false));
//		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	public static final AbstractPlaceType LILAYA_HOME_FOUNTAIN = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Фонтан",
			"В самом центре сада огромный, богато украшенный фонтан радостно журчит, словно сам по себе.",
			"dominion/lilayasHome/fountain",
			PresetColour.BASE_BLUE_LIGHT,
			LilayaHomeGeneric.FOUNTAIN,
			Darkness.DAYLIGHT,
			null, "в саду Лилайи"
			).initItemsPersistInTile()
			.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_GREEN)
			.initAquatic(Aquatic.MIXED);
	public static final AbstractPlaceType LILAYA_HOME_STAIR_DOWN = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Лестница",
			"Эта лестница соединяет второй и первый этажи дома Лилайи и имеет небольшую вспомогательную площадку на полпути вниз.",
			"dominion/lilayasHome/stairsDown",
			PresetColour.BASE_RED,
			LilayaHomeGeneric.STAIRCASE_DOWN,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Лилайи"
			).initItemsPersistInTile()
			.initWeatherImmune();

	public static final AbstractPlaceType LILAYA_HOME_UNDER_CONSTRUCTION = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Строительная площадка",
			"Эта часть особняка Лилайи в настоящее время расширяется и поэтому напоминает строительную площадку...",
			"dominion/lilayasHome/construction",
			PresetColour.BASE_BROWN,
			LilayaSpa.SPA_CONSTRUCTION,
			Darkness.ALWAYS_LIGHT,
			null, "в спа-салоне Лилайи"
			) {
		@Override
		public List<Population> getPopulation() {
			List<Population> pop = new ArrayList<>();
			if(Main.game.isWorkTime()) {
				pop.add(new Population(true, PopulationType.CONSTRUCTION_WORKER, PopulationDensity.SEVERAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			}
			return pop;
		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	
	public static final AbstractPlaceType LILAYA_HOME_SPA = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Спа-бассейны",
			"Серия бассейнов, наполненных тёплой водой из геотермальных источников, является центральным элементом частного спа-салона.",
			"dominion/lilayasHome/roomSpa",
			PresetColour.BASE_TEAL,
			LilayaSpa.SPA_CORE,
			Darkness.ALWAYS_LIGHT,
			null, "в спа-салоне Лилайи"
			).initItemsPersistInTile()
			.initWeatherImmune();
	
	public static final AbstractPlaceType LILAYA_HOME_SPA_POOL = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Плавательный бассейн",
			"В этом месте установлен большой крытый бассейн.",
			"dominion/lilayasHome/roomSpaPool",
			PresetColour.BASE_BLUE_LIGHT,
			LilayaSpa.SPA_POOL,
			Darkness.ALWAYS_LIGHT,
			null, "в спа-салоне Лилайи"
			).initItemsPersistInTile();
	
	public static final AbstractPlaceType LILAYA_HOME_SPA_SAUNA = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Сауна",
			"В этой зоне построены большая сауна и парная.",
			"dominion/lilayasHome/roomSpaSauna",
			PresetColour.BASE_BROWN,
			LilayaSpa.SPA_SAUNA,
			Darkness.ALWAYS_LIGHT,
			null, "в спа-салоне Лилайи"
			).initItemsPersistInTile();
	
	
	
	// Lilaya's home (first floor):

	public static final AbstractPlaceType LILAYA_HOME_ROOM_LILAYA = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната Лилайи",
			"Комната Лилайи расположена в углу второго этажа и заметно соседствует с комнатой Розы...",
			"dominion/lilayasHome/roomLilaya",
			PresetColour.BASE_CRIMSON,
			LilayasRoom.ROOM_LILAYA,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Лилайи"
			).initItemsPersistInTile()
			.initWeatherImmune();
	
	public static final AbstractPlaceType LILAYA_HOME_ROOM_ROSE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната Розы",
			"Комната Розы расположена в углу второго этажа и заметно соседствует с комнатой Лилайи...",
			"dominion/lilayasHome/roomRose",
			PresetColour.BASE_PINK,
			LilayaHomeGeneric.ROOM_ROSE,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Лилайи"
			).initItemsPersistInTile()
			.initWeatherImmune();
	
	public static final AbstractPlaceType LILAYA_HOME_ROOM_PLAYER = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Твоя комната",
			"Комната, которую тебе безвозмездно предоставила Лилайя; это единственное место в Доминионе, где ты можешь по-настоящему беззаботно отдохнуть.",
			"dominion/lilayasHome/roomPlayer",
			PresetColour.BASE_AQUA,
			RoomPlayer.ROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в твоей комнате"
			) {
				@Override
				public ArrayList<AbstractPlaceUpgrade> getAvailablePlaceUpgrades(Set<AbstractPlaceUpgrade> upgrades) {
					return Util.newArrayListOfValues(
							PlaceUpgrade.LILAYA_PLAYER_ROOM_BED);
				}
				@Override
				public boolean isAbleToBeUpgraded() {
					return true;
				}
			}.initItemsPersistInTile()
			.initWeatherImmune();
	public static final AbstractPlaceType LILAYA_HOME_STAIR_DOWN_SECONDARY = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Лестница",
			"Эта лестница, хотя и меньше главной, расположенной у входа в особняк, выполняет ту же задачу - соединяет первый и второй этажи дома Лилайи.",
			"dominion/lilayasHome/stairsDownSecondary",
			PresetColour.BASE_RED_LIGHT,
			LilayaHomeGeneric.STAIRCASE_DOWN_SECONDARY,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Лилайи"
			).initItemsPersistInTile()
			.initWeatherImmune();
	public static final AbstractPlaceType ZARANIX_GF_STAIRS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Лестница",
			"Эта лестница соединяет первый и второй этажи дома Зараникса.",
			"dominion/zaranixHome/stairsDown",
			PresetColour.BASE_GREEN_LIGHT,
			ZaranixHomeGroundFloor.STAIRS,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Зараникса") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeGroundFloorRepeat.STAIRS;

			} else {
				return ZaranixHomeGroundFloor.STAIRS;
			}
		}
	}.initWeatherImmune();
	

	
	
	// Zaranix's home (ground floor):
	
	public static final AbstractPlaceType ZARANIX_GF_CORRIDOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"Коридоры дома Зараникса украшают многочисленные картины, мягкие кресла и добротно сделанные шкафы.",
			null,
			PresetColour.BASE_GREY,
			ZaranixHomeGroundFloor.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Зараникса") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeGroundFloorRepeat.CORRIDOR;
				
			} else {
				return ZaranixHomeGroundFloor.CORRIDOR;
			}
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType ZARANIX_GF_GARDEN = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Сад",
			"Сад, в котором выращивают всевозможные странные и экзотические растения.",
			"dominion/zaranixHome/garden",
			PresetColour.BASE_GREEN,
			ZaranixHomeGroundFloor.GARDEN,
			Darkness.DAYLIGHT,
			null, "в саду Зараникса") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeGroundFloorRepeat.GARDEN;

			} else {
				return ZaranixHomeGroundFloor.GARDEN;
			}
		}
	};
	
	public static final AbstractPlaceType ZARANIX_GF_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Вход",
			"Огромная хрустальная люстра заливает ярким светом прихожую, а на каждой из окружающих стен висят прекрасные картины в золотых рамах.",
			"dominion/zaranixHome/entranceHall",
			PresetColour.BASE_RED,
			ZaranixHomeGroundFloor.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Зараникса") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeGroundFloorRepeat.ENTRANCE;
				
			} else {
				return ZaranixHomeGroundFloor.ENTRANCE;
			}
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType ZARANIX_GF_LOUNGE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Гостиная",
			"Несколько диванов и кресел со вкусом расставлены вокруг низкого столика в центре комнаты, а многочисленные книжные шкафы и тумбы украшают стены, оклеенные цветочными обоями.",
			"dominion/zaranixHome/lounge",
			PresetColour.BASE_ORANGE,
			ZaranixHomeGroundFloor.LOUNGE,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Зараникса") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeGroundFloorRepeat.LOUNGE;
				
			} else {
				return ZaranixHomeGroundFloor.LOUNGE;
			}
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType ZARANIX_GF_ROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната",
			"Дверь в эту комнату заперта, и из неё не доносится ни звука.",
			"dominion/zaranixHome/room",
			PresetColour.BASE_GREY,
			ZaranixHomeGroundFloor.ROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в комнате в доме Зараникса") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeGroundFloorRepeat.ROOM;
				
			} else {
				return ZaranixHomeGroundFloor.ROOM;
			}
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType ZARANIX_GF_MAID = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"Суккуб с кожей цвета слоновой кости, одетая в светло-розовую униформу горничной, деловито вытирает пыль.",
			null,
			PresetColour.BASE_GREY,
			ZaranixHomeGroundFloor.CORRIDOR_MAID,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Зараникса") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeGroundFloorRepeat.CORRIDOR;
			} else {
				return ZaranixHomeGroundFloor.CORRIDOR_MAID;
			}
		}
		@Override
		public String getTooltipDescription() {
			if(isDangerous()) {
				return tooltipDescription;
			} else {
				return ZARANIX_GF_CORRIDOR.getTooltipDescription();
			}
		}
		@Override
		public boolean isDangerous() {
			return !Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE);
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType ZARANIX_GF_GARDEN_ROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната",
			"Довольно неинтересная комната, соединяющая сад с остальной частью дома Зараникса.",
			"dominion/zaranixHome/room",
			PresetColour.BASE_GREY,
			ZaranixHomeGroundFloor.GARDEN_ROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в комнате в доме Зараникса") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeGroundFloorRepeat.GARDEN_ROOM;
				
			} else {
				return ZaranixHomeGroundFloor.GARDEN_ROOM;
			}
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType ZARANIX_GF_GARDEN_ENTRY = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Сад",
			"Это место находится рядом с забором, отделяющим сад Зараникса от улиц Доминиона.",
			"dominion/zaranixHome/entranceHall",
			PresetColour.BASE_GREEN,
			ZaranixHomeGroundFloor.GARDEN_ENTRY,
			Darkness.DAYLIGHT,
			null, "in Zaranix's garden"){
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeGroundFloorRepeat.GARDEN_ENTRY;

			} else {
				return ZaranixHomeGroundFloor.GARDEN_ENTRY;
			}
		}
	};
	public static final AbstractPlaceType ZARANIX_FF_STAIRS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Лестница",
			"Эта лестница соединяет второй и первый этажи дома Зараникса.",
			"dominion/zaranixHome/stairsDown",
			PresetColour.BASE_RED,
			ZaranixHomeFirstFloor.STAIRS,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Зараникса") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeFirstFloorRepeat.STAIRS;

			} else {
				return ZaranixHomeFirstFloor.STAIRS;
			}
		}
	}.initWeatherImmune();
	
	
	
	// Zaranix's home (first floor):
	
	public static final AbstractPlaceType ZARANIX_FF_CORRIDOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"Коридоры дома Зараникса украшают многочисленные картины, мягкие кресла и добротно сделанные шкафы.",
			null,
			PresetColour.BASE_GREY,
			ZaranixHomeFirstFloor.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Зараникса") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeFirstFloorRepeat.CORRIDOR;
				
			} else {
				return ZaranixHomeFirstFloor.CORRIDOR;
			}
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType ANGELS_KISS_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Прихожая",
			"С высокого потолка свисает золотая люстра, освещающая мягким белым светом длинную стойку прихожей из красного дерева.",
			"dominion/angelsKiss/entrance",
			PresetColour.BASE_RED,
			RedLightDistrict.ANGELS_KISS_ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в «Поцелуе ангела»"
			).initWeatherImmune();
	
	public static final AbstractPlaceType ZARANIX_FF_OFFICE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната Зараникса",
			"Офис Зараникса, переделанный в небольшую лабораторию.",
			"dominion/zaranixHome/roomZaranix",
			PresetColour.BASE_GREEN_LIME,
			ZaranixHomeFirstFloor.ZARANIX_ROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Зараникса") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeFirstFloorRepeat.ZARANIX_ROOM;
				
			} else {
				return ZaranixHomeFirstFloor.ZARANIX_ROOM;
			}
		}
		@Override
		public boolean isDangerous() {
			return !Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE);
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType ZARANIX_FF_ROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната",
			"Дверь в эту комнату заперта, и из неё не доносится ни звука.",
			"dominion/zaranixHome/room",
			PresetColour.BASE_GREY,
			ZaranixHomeFirstFloor.ROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в комнате в доме Зараникса") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeFirstFloorRepeat.ROOM;
				
			} else {
				return ZaranixHomeFirstFloor.ROOM;
			}
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType ZARANIX_FF_MAID = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"Суккуб с кожей цвета слоновой кости, одетая в светло-розовую униформу горничной, деловито вытирает пыль.",
			null,
			PresetColour.BASE_RED,
			ZaranixHomeFirstFloor.CORRIDOR_MAID,
			Darkness.ALWAYS_LIGHT,
			null, "в доме Зараникса") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE)) {
				return ZaranixHomeFirstFloorRepeat.CORRIDOR;
				
			} else {
				return ZaranixHomeFirstFloor.CORRIDOR_MAID;
			}
		}
		@Override
		public String getTooltipDescription() {
			if(isDangerous()) {
				return tooltipDescription;
			} else {
				return ZARANIX_FF_CORRIDOR.getTooltipDescription();
			}
		}
		@Override
		public boolean isDangerous() {
			return !Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_1_H_THE_GREAT_ESCAPE);
		}
	}.initWeatherImmune();
	
	
	
	
	// Angel's Kiss:

	public static final AbstractPlaceType ANGELS_KISS_CORRIDOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"Коридоры «Поцелуя Ангела» застелены коврами насыщенного бордового цвета, а стены наполовину отделаны темным деревом, наполовину оклеены светло-голубыми обоями с цветочным рисунком.",
			null,
			PresetColour.BASE_GREY,
			RedLightDistrict.ANGELS_KISS_CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "в «Поцелуе Ангела»"
			).initWeatherImmune();
	public static final AbstractPlaceType ANGELS_KISS_STAIRCASE_UP = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Лестница",
			"Эта лестница соединяет первый и второй этажи «Поцелуя Ангела».",
			"dominion/angelsKiss/stairsUp",
			PresetColour.BASE_GREEN_LIGHT,
			RedLightDistrict.ANGELS_KISS_STAIRS_UP,
			Darkness.ALWAYS_LIGHT,
			null, "в «Поцелуе Ангела»"
		).initWeatherImmune();
	public static final AbstractPlaceType ANGELS_KISS_STAIRCASE_DOWN = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Лестница",
			"Эта лестница соединяет второй и первый этажи «Поцелуя Ангела».",
			"dominion/angelsKiss/stairsDown",
			PresetColour.BASE_RED,
			RedLightDistrict.ANGELS_KISS_STAIRS_DOWN,
			Darkness.ALWAYS_LIGHT,
			null, "в «Поцелуе Ангела»"
			).initWeatherImmune();
	public static final AbstractPlaceType ANGELS_KISS_BEDROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Спальня",
			"В этих спальнях осуществляется деловая жизнь заведения, и для этого в каждой из них стоит чистая кровать королевского размера.",
			"dominion/angelsKiss/bedroom",
			PresetColour.BASE_PINK,
			RedLightDistrict.ANGELS_KISS_BEDROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в «Поцелуе Ангела»"
			).initWeatherImmune();
	public static final AbstractPlaceType ANGELS_KISS_BEDROOM_BUNNY = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Спальня Банни",
			"В этой спальне живет покорная проститутка «Банни», которая является близнецом своей сестры «Лоппи».",
			"dominion/angelsKiss/bedroomBunny",
			PresetColour.BASE_PINK_LIGHT,
			RedLightDistrict.ANGELS_KISS_BEDROOM_BUNNY,
			Darkness.ALWAYS_LIGHT,
			null, "в спальне Банни"
			).initWeatherImmune();
	public static final AbstractPlaceType ANGELS_KISS_BEDROOM_LOPPY = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Спальня Лоппи",
			"В этой спальне живет доминирующая проститутка «Лоппи», которая является близнецом своей сестры «Банни».",
			"dominion/angelsKiss/bedroomLoppy",
			PresetColour.BASE_PURPLE,
			RedLightDistrict.ANGELS_KISS_BEDROOM_LOPPY,
			Darkness.ALWAYS_LIGHT,
			null, "в спальне Лоппи"
			).initWeatherImmune();
	public static final AbstractPlaceType ANGELS_KISS_OFFICE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Офис Ангела",
			"Комната, в которой Ангел занимается всей бумажной работой, необходимой ей как «административному центру района красных фонарей», санкционированному энфорсерами.",
			"dominion/angelsKiss/office",
			PresetColour.BASE_BLUE_LIGHT,
			RedLightDistrict.ANGELS_KISS_OFFICE,
			Darkness.ALWAYS_LIGHT,
			null, "в кабинете Ангела"
			).initWeatherImmune();
	public static final AbstractPlaceType SHOPPING_ARCADE_PATH = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Галерея",
			"Главные коридоры, проходящие через торговую галерею, с обеих сторон обрамлены магазинами самых разных категорий.",
			null,
			PresetColour.BASE_BLACK,
			ShoppingArcadeDialogue.ARCADE,
			Darkness.ALWAYS_LIGHT,
			null, "в Торговой галерее") {
		@Override
		public List<Population> getPopulation() {
			List<Population> pop = new ArrayList<>();
			if(Main.game.isExtendedWorkTime()) {
				pop.add(new Population(true, PopulationType.CROWD, PopulationDensity.DENSE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			} else {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.COUPLE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			}
			pop.add(new Population(true, PopulationType.ENFORCER, PopulationDensity.SEVERAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true, Subspecies.HUMAN)));
			return pop;
		}
	}.initWeatherImmune();
	
	
	
	
	// Shopping arcade:
	public static final AbstractPlaceType SHOPPING_ARCADE_GENERIC_SHOP = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Магазин",
			"Один из множества непримечательных магазинов в галерее, этот конкретный магазин не предлагает ничего, что стоило бы времени или денег.",
			"dominion/shoppingArcade/genericShop",
			PresetColour.BASE_BLACK,
			ShoppingArcadeDialogue.GENERIC_SHOP,
			Darkness.ALWAYS_LIGHT,
			null, "в Торговой галерее") {
		@Override
		public List<Population> getPopulation() {
			if(Main.game.isExtendedWorkTime()) {
				return Util.newArrayListOfValues(new Population(true, PopulationType.SHOPPER, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			} else {
				return new ArrayList<>();
			}
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType SHOPPING_ARCADE_RALPHS_SHOP = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Закуски Ральфа",
			"Магазин, специализирующийся на продаже еды, напитков и других разных товаров. Дружелюбный владелец «Закусочной Ральфа» - мускулистый, коричневоволосый значительны конепарень.",
			"dominion/shoppingArcade/ralphShop",
			PresetColour.BASE_TEAL,
			RalphsSnacks.EXTERIOR,
			Darkness.ALWAYS_LIGHT,
			null, "в его магазине"
			).initWeatherImmune();
	public static final AbstractPlaceType SHOPPING_ARCADE_NYANS_SHOP = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Магазин одежды Ньян",
			"Двухэтажный магазин «Магазин одежды Ньян» - самый большой магазин во всей галерее.",
			"dominion/shoppingArcade/nyanShop",
			PresetColour.BASE_ROSE,
			ClothingEmporium.EXTERIOR,
			Darkness.ALWAYS_LIGHT,
			null,
			"в её магазине") {
		@Override
		public List<Population> getPopulation() {
			if(Main.game.isHourBetween(9, 20) && !Main.game.getCurrentDialogueNode().isTravelDisabled()) { // Travel disabled indicates that the player is in the storeroom with Nyan
				return Util.newArrayListOfValues(new Population(true, PopulationType.SHOPPER, PopulationDensity.DOZENS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			} else {
				return new ArrayList<>();
			}
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType SHOPPING_ARCADE_VICKYS_SHOP = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Магические искусства",
			"Магазин «Магические искусства», специализирующийся на продаже магического оружия и сопутствующих товаров, управляется особенно свирепой волкодевушкой по имени Вики.",
			"dominion/shoppingArcade/vickyShop",
			PresetColour.BASE_MAGENTA,
			ArcaneArts.EXTERIOR,
			Darkness.ALWAYS_LIGHT,
			null, "в её магазине"
			).initWeatherImmune();
	public static final AbstractPlaceType SHOPPING_ARCADE_KATES_SHOP = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Секреты суккубов",
			"На первый взгляд этот салон красоты с заколоченными окнами и облупившейся краской кажется заброшенным, но при ближайшем рассмотрении на двери висит табличка «Открыто».",
			"dominion/shoppingArcade/kateShop",
			PresetColour.BASE_PINK,
			SuccubisSecrets.EXTERIOR,
			Darkness.ALWAYS_LIGHT,
			null, "в её салоне красоты"
			).initWeatherImmune();
	public static final AbstractPlaceType SHOPPING_ARCADE_ASHLEYS_SHOP = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Возлюбленная мечта",
			"Единственный магазин, который ты [pc.genderBasedWord(видел, видела)] в Доминионе, специализирующийся на подарках для других.",
			"dominion/shoppingArcade/ashleyShop",
			PresetColour.BASE_LILAC_LIGHT,
			DreamLover.EXTERIOR,
			Darkness.ALWAYS_LIGHT,
			null, "в его магазине"
			).initWeatherImmune();
	public static final AbstractPlaceType SHOPPING_ARCADE_ANTIQUES = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Антиквариат",
			"Антикварный магазин, торгующий разнообразной старой скрипучей мебелью, устаревшими магическими инструментами и всевозможными ненужными вещами.",
			"dominion/shoppingArcade/antiques",
			PresetColour.BASE_BROWN,
			ShoppingArcadeDialogue.ANTIQUES,
			Darkness.ALWAYS_LIGHT,
			null, "в антикварном магазине"
			).initWeatherImmune();
	public static final AbstractPlaceType SHOPPING_ARCADE_RESTAURANT = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Дубовая поляна",
			"Ресторан «Дубовая поляна» рассчитан на более состоятельных посетителей торговой галереи, а его самый простой обед из трех блюд стоит почти тысячу огней.",
			"dominion/shoppingArcade/restaurant",
			PresetColour.BASE_GREEN_DARK,
			ShoppingArcadeDialogue.RESTAURANT,
			Darkness.ALWAYS_LIGHT,
			null, "в «Дубовой поляне»") {
		@Override
		public List<Population> getPopulation() {
			if(Main.game.getHourOfDay()>=18) {
				return Util.newArrayListOfValues(new Population(true, PopulationType.DINER, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			} else {
				return new ArrayList<>();
			}
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType SHOPPING_ARCADE_PIXS_GYM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Игровая площадка Пикс",
			"Огромный многоэтажный спортзал «Игровая площадка Пикс» принадлежит и управляется особенно энергичной бордерколли-девушкой.",
			"dominion/shoppingArcade/gym",
			PresetColour.BASE_GOLD,
			PixsPlayground.GYM_EXTERIOR,
			Darkness.ALWAYS_LIGHT,
			null, "в спортзале, «Игровая площадка Пикс»"
			).initWeatherImmune();
	public static final AbstractPlaceType SHOPPING_ARCADE_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Выход",
			"Эти большие стеклянные двери ведут на улицы Доминиона.",
			"dominion/shoppingArcade/exit",
			PresetColour.BASE_RED,
			ShoppingArcadeDialogue.ENTRY,
			Darkness.ALWAYS_LIGHT,
			null, "в Торговой галерее"
			).initWeatherImmune();
	public static final AbstractPlaceType SHOPPING_ARCADE_TOILETS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Туалеты",
			"Рядом со входом в торговую галерею удобно расположены общественные туалеты.",
			"dominion/shoppingArcade/toilets",
			PresetColour.BASE_BLUE_LIGHT,
			ShoppingArcadeDialogue.TOILETS,
			Darkness.ALWAYS_LIGHT,
			null, "в туалете в Торговой галерее") {
		@Override
		public List<Population> getPopulation() {
			if(Main.game.isExtendedWorkTime()) {
				return Util.newArrayListOfValues(new Population(true, PopulationType.SHOPPER, PopulationDensity.COUPLE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			} else {
				return new ArrayList<>();
			}
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType TEXTILE_WAREHOUSE_CORRIDOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"По всей длине здания проходит хорошо освещенный коридор, из которого можно попасть в складские и ткацкие помещения.",
			null,
			PresetColour.BASE_BLACK,
			KaysWarehouse.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Текстиле Кея»") {
		@Override
		public String getTooltipDescription() {
			if(Main.game.getPlayer().isQuestCompleted(QuestLine.RELATIONSHIP_NYAN_HELP)) {
				return "После того как с доберманами было покончено, коридор в задней части здания был отремонтирован и стал чистым и хорошо освещённым.";
			} else {
				return tooltipDescription;
			}
		}
	}.initWeatherImmune();
	
	
	
	// Supplier Depot:
	public static final AbstractPlaceType TEXTILE_WAREHOUSE_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Приёмная",
			"В приёмной склада работает собакодевушка с депрессивным видом, которая, кажется, совсем не заинтересована в выполнении своих обязанностей.",
			"dominion/textilesWarehouse/exit",
			PresetColour.BASE_RED,
			KaysWarehouse.RECEPTION,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Текстиле Кея»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.RECEPTIONIST, PopulationDensity.ONE, Util.newHashMapOfValues(new Value<>(Subspecies.RABBIT_MORPH, SubspeciesSpawnRarity.TEN))));
		}
		@Override
		public String getTooltipDescription() {
			if(Main.game.getPlayer().isQuestCompleted(QuestLine.RELATIONSHIP_NYAN_HELP)) {
				return "Когда с доберманами разобрались, депрессивная собакодевушка, работающая в приёмной, кажется намного счастливее и охотнее помогает посетителям.";
			} else {
				return tooltipDescription;
			}
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType TEXTILE_WAREHOUSE_STORAGE_ROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Кладовая",
			"Многочисленные ящики заполняют эту кладовую, каждый из них полон зачарованной одежды.",
			"dominion/textilesWarehouse/storage",
			PresetColour.BASE_RED,
			KaysWarehouse.STORAGE_AREA,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Текстиле Кея»"
			) {
		@Override
		public Colour getColour() {
			if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.kayCratesSearched)
					|| !Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.RELATIONSHIP_NYAN_HELP, Quest.RELATIONSHIP_NYAN_3_STOCK_ISSUES_DOBERMANNS)) {
				return PresetColour.BASE_RED;
			} else {
				return PresetColour.BASE_GREEN;
			}
		}
		@Override
		public String getSVGString(Set<AbstractPlaceUpgrade> upgrades) {
			if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.kayCratesSearched)
					|| !Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.RELATIONSHIP_NYAN_HELP, Quest.RELATIONSHIP_NYAN_3_STOCK_ISSUES_DOBERMANNS)) {
				return SVGString;
			} else {
				return getSVGOverride("dominion/textilesWarehouse/storage", PresetColour.BASE_GREEN);
			}
		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	public static final AbstractPlaceType TEXTILE_WAREHOUSE_ENCHANTING = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Ткацкие станки",
			"Десятки текстильщиков, работающих в этом районе, управляют множеством, похоже, заумных ткацких станков.",
			"dominion/textilesWarehouse/enchanting",
			PresetColour.GENERIC_ARCANE,
			KaysWarehouse.WEAVING_MACHINES,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Текстиле Кея»"
			) {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(
					new Population(true, PopulationType.TEXTILE_WORKER, PopulationDensity.NUMEROUS,
							Util.newHashMapOfValues(
									new Value<>(Subspecies.RABBIT_MORPH, SubspeciesSpawnRarity.TEN),
									new Value<>(Subspecies.CAT_MORPH, SubspeciesSpawnRarity.TEN),
									new Value<>(Subspecies.DOG_MORPH, SubspeciesSpawnRarity.TEN),
									new Value<>(Subspecies.FOX_MORPH, SubspeciesSpawnRarity.TEN),
									new Value<>(Subspecies.HORSE_MORPH, SubspeciesSpawnRarity.TEN),
									new Value<>(Subspecies.COW_MORPH, SubspeciesSpawnRarity.TEN),
									new Value<>(Subspecies.HUMAN, SubspeciesSpawnRarity.TEN))));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType TEXTILE_WAREHOUSE_OFFICE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Офис Кея",
			"Кабинет Кея, в который можно попасть через станцию надзирателя, довольно мал, но очень элегантно обставлен.",
			"dominion/textilesWarehouse/office",
			PresetColour.BASE_BLUE_LIGHT,
			KaysWarehouse.OFFICE,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Текстиле Кея»").initWeatherImmune();
	public static final AbstractPlaceType TEXTILE_WAREHOUSE_OVERSEER_STATION = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Станция надзирателей",
			"Ступеньки ведут на антресольный этаж, с которого открывается вид на весь склад.",
			"dominion/textilesWarehouse/overseer_station",
			PresetColour.BASE_GREY_DARK,
			KaysWarehouse.OVERSEER_STATION,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Текстиле Кея»"
			) {
		@Override
		public List<Population> getPopulation() {
			if(Main.game.getWorlds().get(WorldType.TEXTILES_WAREHOUSE).getCell(PlaceType.TEXTILE_WAREHOUSE_OFFICE).isTravelledTo()) {
				return Util.newArrayListOfValues(new Population(false, PopulationType.TEXTILE_WORKER, PopulationDensity.ONE, Util.newHashMapOfValues(new Value<>(Subspecies.FOX_MORPH, SubspeciesSpawnRarity.TEN))));
			} else {
				return super.getPopulation();
			}
		}
		@Override
		public boolean isDangerous() {
			return !Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.RELATIONSHIP_NYAN_HELP, Quest.RELATIONSHIP_NYAN_3_STOCK_ISSUES_DOBERMANNS);
		}
		@Override
		public String getTooltipDescription() {
			if(Main.game.getWorlds().get(WorldType.TEXTILES_WAREHOUSE).getCell(PlaceType.TEXTILE_WAREHOUSE_OFFICE).isTravelledTo()) {
				return "С доберманами разобрались, и теперь на посту надзирателя возле офиса Кея работает один из текстильщиков.";
			} else {
				return tooltipDescription;
			}
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType SLAVER_ALLEY_PATH = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Переулок",
			"Переулки, проходящие через Аллею работорговцев, совсем не похожи на те, что встречаются в остальных районах Доминиона: они оживлённые, чистые и, что самое главное, очень безопасные.",
			null,
			PresetColour.BASE_BLACK,
			SlaverAlleyDialogue.ALLEYWAY,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			List<Population> pop = new ArrayList<>();
			if(Main.game.isExtendedWorkTime()) {
				pop.add(new Population(true, PopulationType.CROWD, PopulationDensity.SPARSE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			} else {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.COUPLE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			}
			pop.add(new Population(false, PopulationType.PRIVATE_SECURITY_GUARD, PopulationDensity.OCCASIONAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			return pop;
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	
	
	
	// Slaver Alley:
	public static final AbstractPlaceType SLAVER_ALLEY_STALL_FEMALES = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Женское прикосновение",
			"Магазин, специализирующийся на продаже хорошо обученных и послушных рабынь.",
			"dominion/slaverAlley/marketStallFemale",
			PresetColour.BASE_PINK_LIGHT,
			SlaverAlleyDialogue.MARKET_STALL_FEMALE,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			return SLAVER_ALLEY_PATH.getPopulation();
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_STALL_MALES = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Железо и сталь",
			"Этот магазин специализируется на продаже самых разных мужчин-рабов - от подсобных рабочих до привлекательных моделей.",
			"dominion/slaverAlley/marketStallMale",
			PresetColour.BASE_BLUE_STEEL,
			SlaverAlleyDialogue.MARKET_STALL_MALE,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			return SLAVER_ALLEY_PATH.getPopulation();
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_STALL_ANAL = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Задний вход",
			"Этот магазин, специализирующийся на продаже рабов, обученных анальному сексу, является одним из трех, которые окружают развратную статую Марлель.",
			"dominion/slaverAlley/marketStallAnal",
			PresetColour.BASE_ORANGE,
			SlaverAlleyDialogue.MARKET_STALL_ANAL,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			return SLAVER_ALLEY_PATH.getPopulation();
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_STALL_VAGINAL = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Белые лилии",
			"Один из трех магазинов, окружающих статую падшего ангела Марлель, «Белые лилии» специализируется на продаже девственниц.",
			"dominion/slaverAlley/marketStallVaginal",
			PresetColour.BASE_PINK,
			SlaverAlleyDialogue.MARKET_STALL_VAGINAL,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			return SLAVER_ALLEY_PATH.getPopulation();
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_STALL_ORAL = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Вива Воче",
			"«Вива Воче» специализируется на продаже рабов, обученных оральному сексу, и является одним из трех специализированных магазинов, расположенных вокруг статуи Марлель.",
			"dominion/slaverAlley/marketStallOral",
			PresetColour.BASE_BLUE_LIGHT,
			SlaverAlleyDialogue.MARKET_STALL_ORAL,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			return SLAVER_ALLEY_PATH.getPopulation();
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_STATUE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Статуя падшего ангела",
			"Стоящая на высоком постаменте статуя падшего ангела Марлель изображает её в крайне порочной и невероятно развратной форме.",
			"dominion/slaverAlley/marketStallStatue",
			PresetColour.BASE_BLACK,
			SlaverAlleyDialogue.MARKET_STALL_STATUE,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			return SLAVER_ALLEY_PATH.getPopulation();
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_MARKET_STALL_EXCLUSIVE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Магазин аренды рабов",
			"Многие магазины на Аллее работорговцев не продают рабов напрямую, а сдают их в аренду предприятиям, желающим заполнить временный пробел в рабочей силе.",
			"dominion/slaverAlley/marketStallExclusive",
			PresetColour.BASE_GREY,
			SlaverAlleyDialogue.MARKET_STALL_EXCLUSIVE,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			return SLAVER_ALLEY_PATH.getPopulation();
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_MARKET_STALL_BULK = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Биржа Дзайбацу",
			"Самый большой магазин на Аллее работорговцев, «Биржа Зайбацу», контролируется могущественным конгломератом работорговцев и отказывается вести дела с теми, кто не входит в него.",
			"dominion/slaverAlley/marketStallBulk",
			PresetColour.BASE_BLUE,
			SlaverAlleyDialogue.MARKET_STALL_BULK,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			return SLAVER_ALLEY_PATH.getPopulation();
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_CAFE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Кафе",
			"По всей Аллее работорговцев разбросаны многочисленные кафе, где покупатели могут отдохнуть и пополнить запасы энергии.",
			"dominion/slaverAlley/marketStallCafe",
			PresetColour.BASE_BROWN,
			SlaverAlleyDialogue.MARKET_STALL_CAFE,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			List<Population> pop = new ArrayList<>();
			if(Main.game.isExtendedWorkTime()) {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			} else {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.FEW, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			}
			return pop;
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_CAFE_2 = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Кафе",
			"По всей Аллее работорговцев разбросаны многочисленные кафе, где покупатели могут отдохнуть и пополнить запасы энергии.",
			"dominion/slaverAlley/marketStallCafe",
			PresetColour.BASE_BROWN,
			SlaverAlleyDialogue.MARKET_STALL_CAFE,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			List<Population> pop = new ArrayList<>();
			if(Main.game.isExtendedWorkTime()) {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			} else {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.FEW, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			}
			return pop;
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_CAFE_3 = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Кафе",
			"По всей Аллее работорговцев разбросаны многочисленные кафе, где покупатели могут отдохнуть и пополнить запасы энергии.",
			"dominion/slaverAlley/marketStallCafe",
			PresetColour.BASE_BROWN,
			SlaverAlleyDialogue.MARKET_STALL_CAFE,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			List<Population> pop = new ArrayList<>();
			if(Main.game.isExtendedWorkTime()) {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			} else {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.FEW, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			}
			return pop;
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_CAFE_4 = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Кафе",
			"По всей Аллее работорговцев разбросаны многочисленные кафе, где покупатели могут отдохнуть и пополнить запасы энергии.",
			"dominion/slaverAlley/marketStallCafe",
			PresetColour.BASE_BROWN,
			SlaverAlleyDialogue.MARKET_STALL_CAFE,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			List<Population> pop = new ArrayList<>();
			if(Main.game.isExtendedWorkTime()) {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			} else {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.FEW, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
			}
			return pop;
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_AUCTIONING_BLOCK = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Аукционная площадка",
			"Огромный деревянный помост стоит посреди обширной площади, и именно на нём проводятся публичные аукционы.",
			"dominion/slaverAlley/auctionBlock",
			PresetColour.BASE_GOLD,
			SlaverAlleyDialogue.AUCTION_BLOCK,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.CROWD, PopulationDensity.DENSE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_BOUNTY_HUNTERS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"«Ржавый ошейник»",
			"Таверна, в которой можно нанять охотников за головами, чтобы они разыскали беглых рабов.",
			"dominion/slaverAlley/bountyHunters",
			PresetColour.BASE_COPPER,
			SlaverAlleyDialogue.BOUNTY_HUNTERS,
			Darkness.ALWAYS_LIGHT,
			null,
			"на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.PERSON, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);

	public static final AbstractPlaceType SLAVER_ALLEY_PUBLIC_STOCKS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Общественные товары",
			"Непосредственно перед входом на Аллею работорговцев установлен ряд колодок для общественного пользования, которые служат напоминанием о том, что происходит с непокорными рабами.",
			"dominion/slaverAlley/stocks",
			PresetColour.BASE_TAN,
			SlaverAlleyDialogue.PUBLIC_STOCKS,
			Darkness.ALWAYS_LIGHT,
			null, "в колодках на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.CROWD, PopulationDensity.SPARSE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_SLAVERY_ADMINISTRATION = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Управление рабовладением",
			"Главный административный центр по всем вопросам, связанным с владением рабами.",
			"dominion/slaverAlley/slaveryAdministration",
			PresetColour.BASE_PURPLE,
			SlaveryAdministration.SLAVERY_ADMINISTRATION_EXTERIOR,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public ArrayList<AbstractPlaceUpgrade> getStartingPlaceUpgrades() {
			return Util.newArrayListOfValues(PlaceUpgrade.SLAVERY_ADMINISTRATION_CELLS);
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Ворота",
			"Единственный вход и выход с Аллеи работорговцев охраняется парой конепарней, которые зорко следят за беглыми рабами.",
			"dominion/slaverAlley/exit",
			PresetColour.BASE_RED,
			SlaverAlleyDialogue.GATEWAY,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(
					new Population(true, PopulationType.PERSON, PopulationDensity.FEW, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)),
					new Population(true, PopulationType.PRIVATE_SECURITY_GUARD, PopulationDensity.COUPLE, Util.newHashMapOfValues(new Value<>(Subspecies.HORSE_MORPH, SubspeciesSpawnRarity.TEN))));
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType SLAVER_ALLEY_DESERTED_ALLEYWAY = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Безлюдный переулок",
			"Узкий переулок пролегает за несколькими магазинами, а затем неожиданно заходит в тупик.",
			"dominion/slaverAlley/desertedAlleyway",
			PresetColour.BASE_BLACK,
			SlaverAlleyDialogue.DESERTED_ALLEYWAY,
			Darkness.DAYLIGHT,
			null, "на Аллее работорговцев").initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType WATERING_HOLE_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Вход",
			"Вход в клуб «Водопой» охраняется парой зебропарнями вышибалами.",
			"dominion/nightLife/exit",
			PresetColour.BASE_RED,
			NightlifeDistrict.WATERING_HOLE_ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в «Водопое»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.CROWD, PopulationDensity.DENSE, Subspecies.getWorldSpecies(WorldType.NIGHTLIFE_CLUB, this, false)));
		}
		@Override
		public boolean isFurniturePresent() {
			return true;
		}
	}.initWeatherImmune();	public static final AbstractPlaceType SLAVER_ALLEY_SCARLETTS_SHOP = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Магазин Скарлетт",
			"",
			"dominion/slaverAlley/scarlettsStall",
			PresetColour.BASE_CRIMSON,
			ScarlettsShop.SCARLETTS_SHOP_EXTERIOR,
			Darkness.ALWAYS_LIGHT,
			null, "на Аллее работорговцев") {
		@Override
		public Colour getColour() {
			if(Main.game.getPlayer().isQuestFailed(QuestLine.ROMANCE_HELENA)) {
				return PresetColour.BASE_BLACK;
			} else if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.ROMANCE_HELENA, Quest.ROMANCE_HELENA_3_C_EXTERIOR_DECORATOR)) {
				return PresetColour.BASE_GOLD;
			} else if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.ROMANCE_HELENA, Quest.ROMANCE_HELENA_3_A_EXTERIOR_DECORATOR)) {
				return PresetColour.BASE_GREY;
			}
			return PresetColour.BASE_CRIMSON;
		}
		@Override
		public String getSVGString(Set<AbstractPlaceUpgrade> upgrades) {
			return getSVGOverride("dominion/slaverAlley/scarlettsStall", getColour());
		}
		@Override
		public String getName() {
			if(Main.game.isStarted()) {
				if(Main.game.getPlayer().isQuestFailed(QuestLine.ROMANCE_HELENA)) {
					return "Заброшенный магазин";
					
				} else if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.ROMANCE_HELENA, Quest.ROMANCE_HELENA_3_C_EXTERIOR_DECORATOR)) {
					return "Бутик Елены";
					
				} else if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.ROMANCE_HELENA, Quest.ROMANCE_HELENA_3_A_EXTERIOR_DECORATOR)) {
					return "Безымянный магазин";
				}
			}
			return "Магазин Скарлетт";
		}
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isQuestProgressLessThan(QuestLine.MAIN, Quest.MAIN_1_F_SCARLETTS_FATE)) { // Scarlett owns the shop:
				return ScarlettsShop.SCARLETTS_SHOP_EXTERIOR;
				
			} else { // Helena owns the shop:
				return ScarlettsShop.HELENAS_SHOP_EXTERIOR;
			}
		}
		@Override
		public String getTooltipDescription() {
			if(Main.game.getPlayer().isQuestFailed(QuestLine.ROMANCE_HELENA)) {
				return "Этот магазин рабов был заброшен Еленой после того, как ты [pc.genderBasedWord(отказался, отказалась)] продать ей Скарлетт...";
				
			} else if(Main.game.getPlayer().isQuestProgressLessThan(QuestLine.MAIN, Quest.MAIN_1_F_SCARLETTS_FATE)) {
				return "Магазин рабов, которым управляет гарпия Скарлетт. В отличие от всех остальных магазинов на Аллее работорговцев, в её магазине нет абсолютно никаких рабов на продажу...";
				
			} else {
				return "Матриарх Скарлетт, Елена, взяла на себя управление этим рабовладельческим магазином.";
			}
		}
		@Override
		public List<Population> getPopulation() {
			if(Main.game.isStarted()) {
				if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.ROMANCE_HELENA, Quest.ROMANCE_HELENA_6_ADVERTISING)) {
					if(Main.game.getNpc(Helena.class).getLocationPlace().getPlaceType()==PlaceType.SLAVER_ALLEY_SCARLETTS_SHOP) {
						return Util.newArrayListOfValues(
								new Population(true, PopulationType.HARPY, PopulationDensity.SEVERAL, Subspecies.getWorldSpecies(WorldType.HARPY_NEST, this, false, false)),
								new Population(true, PopulationType.SHOPPER, PopulationDensity.FEW, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)),
								new Population(true, PopulationType.FAN, PopulationDensity.SEVERAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
					} else {
						return Util.newArrayListOfValues(new Population(true, PopulationType.FAN, PopulationDensity.COUPLE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
					}
					
				} else if(Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.ROMANCE_HELENA, Quest.ROMANCE_HELENA_3_C_EXTERIOR_DECORATOR) && !Main.game.getPlayer().isQuestFailed(QuestLine.ROMANCE_HELENA)) {
					if(Main.game.getNpc(Helena.class).getLocationPlace().getPlaceType()==PlaceType.SLAVER_ALLEY_SCARLETTS_SHOP) {
						return Util.newArrayListOfValues(new Population(true, PopulationType.FAN, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
					} else {
						return Util.newArrayListOfValues(new Population(true, PopulationType.FAN, PopulationDensity.COUPLE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
					}
				}
			}
			return new ArrayList<>();
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType WATERING_HOLE_MAIN_AREA = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Водопой",
			"В клубе очень много людей, которые пьют, общаются и целуются друг с другом.",
			null,
			PresetColour.BASE_BLUE_LIGHT,
			NightlifeDistrict.WATERING_HOLE_MAIN,
			Darkness.ALWAYS_LIGHT,
			null, "в «Водопое»") {
		@Override
		public List<Population> getPopulation() {
			return WATERING_HOLE_ENTRANCE.getPopulation();
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType BOUNTY_HUNTER_LODGE_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Вход",
			"Главный вход в таверну представляет собой пару покосившихся деревянных дверей.",
			"dominion/slaverAlley/bountyHunterLodge/exit",
			PresetColour.BASE_RED,
			BountyHunterLodge.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Ржавом ошейнике»")
		.initWeatherImmune(Weather.MAGIC_STORM);
	
	
	// Bounty hunter lodge:
	public static final AbstractPlaceType BOUNTY_HUNTER_LODGE_FLOOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Главный этаж",
			"Большую часть пола в таверне занимает дюжина больших деревянных столов, за которыми сидят охотники за головами и всякие неприятные личности.",
			null,
			PresetColour.BASE_BLACK,
			BountyHunterLodge.FLOOR,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Ржавом ошейнике»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.CROWD, PopulationDensity.SPARSE, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
		@Override
		public boolean isLoiteringEnabledOverride() {
			return true;
		}
		@Override
		public boolean isLoiteringEnabled() {
			return true;
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType BOUNTY_HUNTER_LODGE_BOUNTY_BOARD = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Доска заданий",
			"Рядом с главным входом находится огромная деревянная доска, на которой написаны активные задания.",
			"dominion/slaverAlley/bountyHunterLodge/bountyBoard",
			PresetColour.CLOTHING_BLUE_GREY,
			BountyHunterLodge.BOUNTY_BOARD,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Ржавом ошейнике»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.PERSON, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType BOUNTY_HUNTER_LODGE_BAR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Бар",
			"Длинная барная стойка всегда занята, и многочисленные завсегдатаи таверны постоянно угощаются всевозможными алкогольными напитками.",
			"dominion/slaverAlley/bountyHunterLodge/bar",
			PresetColour.BASE_ORANGE,
			BountyHunterLodge.BAR,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Ржавом ошейнике»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.PERSON, PopulationDensity.NUMEROUS, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
		@Override
		public boolean isLoiteringEnabledOverride() {
			return true;
		}
		@Override
		public boolean isLoiteringEnabled() {
			return true;
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType BOUNTY_HUNTER_LODGE_SEATING = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Утопленные сидения",
			"По краям главного этажа таверны есть несколько углубленных зон для сидения.",
			"dominion/slaverAlley/bountyHunterLodge/seatingArea",
			PresetColour.BASE_BROWN,
			BountyHunterLodge.SEATING,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Ржавом ошейнике»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.PERSON, PopulationDensity.FEW, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType BOUNTY_HUNTER_LODGE_STAIRS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Лестница",
			"Узкая лестница ведёт на второй этаж таверны.",
			"dominion/slaverAlley/bountyHunterLodge/stairsUp",
			PresetColour.BASE_GREEN_LIGHT,
			BountyHunterLodge.STAIRS,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Ржавом ошейнике»")
		.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType BOUNTY_HUNTER_LODGE_UPSTAIRS_CORRIDOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Коридор",
			"Узкий коридор соединяет все комнаты на втором этаже таверны.",
			null,
			PresetColour.BASE_BLACK,
			BountyHunterLodge.UPSTAIRS_CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Ржавом ошейнике»") {
		@Override
		public boolean isLoiteringEnabledOverride() {
			return true;
		}
		@Override
		public boolean isLoiteringEnabled() {
			return true;
		}
	}.initWeatherImmune(Weather.MAGIC_STORM);
	
	// First floor:
	public static final AbstractPlaceType BOUNTY_HUNTER_LODGE_UPSTAIRS_STAIRS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Лестница",
			"Узкая лестница ведёт на первый этаж таверны.",
			"dominion/slaverAlley/bountyHunterLodge/stairsDown",
			PresetColour.BASE_RED_LIGHT,
			BountyHunterLodge.UPSTAIRS_STAIRS,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Ржавом ошейнике»")
		.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType BOUNTY_HUNTER_LODGE_UPSTAIRS_ROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната",
			"Как и все остальные комнаты, эта уже сдана в аренду...",
			"dominion/slaverAlley/bountyHunterLodge/room",
			PresetColour.BASE_TEAL,
			BountyHunterLodge.UPSTAIRS_ROOM,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Ржавом ошейнике»")
		.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType BOUNTY_HUNTER_LODGE_UPSTAIRS_ROOM_DOBERMANNS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната",
			"Как и все остальные комнаты, эта уже сдана в аренду...",
			"dominion/slaverAlley/bountyHunterLodge/room",
			PresetColour.BASE_TEAL,
			BountyHunterLodge.UPSTAIRS_ROOM,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Ржавом ошейнике»")
		.initWeatherImmune(Weather.MAGIC_STORM);
	public static final AbstractPlaceType BOUNTY_HUNTER_LODGE_UPSTAIRS_ROOM_SHADOW_SILENCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Комната",
			"Как и все остальные комнаты, эта уже сдана в аренду...",
			"dominion/slaverAlley/bountyHunterLodge/room",
			PresetColour.BASE_TEAL,
			BountyHunterLodge.UPSTAIRS_ROOM,
			Darkness.ALWAYS_LIGHT,
			null,
			"в «Ржавом ошейнике»")
		.initWeatherImmune(Weather.MAGIC_STORM);
	// Watering hole:
	public static final AbstractPlaceType WATERING_HOLE_SEATING_AREA = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Зона отдыха",
			"На окраине клуба выделены зоны, где можно относительно спокойно посидеть и поговорить друг с другом.",
			"dominion/nightLife/seatingArea",
			PresetColour.BASE_BROWN,
			NightlifeDistrict.WATERING_HOLE_SEATING,
			Darkness.ALWAYS_LIGHT,
			null, "в «Водопое»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.CROWD, PopulationDensity.SPARSE, Subspecies.getWorldSpecies(WorldType.NIGHTLIFE_CLUB, this, false)));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType DADDY_APARTMENT_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Прихожая",
			"Прихожая в квартире.",
			"dominion/daddy/entranceHall",
			PresetColour.BASE_GREEN,
			DaddyDialogue.PLACE_ENTRANCE_HALL,
			Darkness.ALWAYS_LIGHT,
			null, "в прихожей квартиры Папочки."
		).initWeatherImmune();
	public static final AbstractPlaceType WATERING_HOLE_VIP_AREA = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"VIP-зона",
			"VIP-зона клуба, охраняемая парой мускулистых львов-вышибал, состоит из множества полукруглых кабинок, в каждой из которых стоит стол из полированного черного мрамора и длинный изогнутый кожаный диван.",
			"dominion/nightLife/vipArea",
			PresetColour.BASE_PURPLE,
			NightlifeDistrict.WATERING_HOLE_VIP,
			Darkness.ALWAYS_LIGHT,
			null, "в «Водопое»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.VIP, PopulationDensity.SEVERAL,
					Util.newHashMapOfValues(
							new Value<>(Subspecies.getSubspeciesFromId("innoxia_panther_subspecies_lion"), SubspeciesSpawnRarity.TEN),
							new Value<>(Subspecies.HORSE_MORPH_ZEBRA, SubspeciesSpawnRarity.TEN))));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType WATERING_HOLE_BAR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Бар",
			"В баре клуба всегда многолюдно: многочисленные зебродевушки и львицедевушки подают заказ за заказом.",
			"dominion/nightLife/bar",
			PresetColour.BASE_ORANGE,
			NightlifeDistrict.WATERING_HOLE_BAR,
			Darkness.ALWAYS_LIGHT,
			null, "в «Водопое»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.CROWD, PopulationDensity.DENSE, Subspecies.getWorldSpecies(WorldType.NIGHTLIFE_CLUB, this, false)));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType WATERING_HOLE_DANCE_FLOOR = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Танцпол",
			"Центральный танцпол - самое оживлённое и громкое место во всём клубе.",
			"dominion/nightLife/danceFloor",
			PresetColour.BASE_PINK_DEEP,
			NightlifeDistrict.WATERING_HOLE_DANCE_FLOOR,
			Darkness.ALWAYS_LIGHT,
			null, "в «Водопое»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.CROWD, PopulationDensity.DENSE, Subspecies.getWorldSpecies(WorldType.NIGHTLIFE_CLUB, this, false)));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType DADDY_APARTMENT_LOUNGE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Гостиная",
			"Гостиная в квартире.",
			"dominion/daddy/lounge",
			PresetColour.BASE_ORANGE,
			DaddyDialogue.PLACE_LOUNGE,
			Darkness.ALWAYS_LIGHT,
			null, "в гостиной квартиры Папочки."
		).initWeatherImmune();
	public static final AbstractPlaceType WATERING_HOLE_TOILETS = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Туалеты",
			"Туалеты клуба довольно большие, в них много кабинок и раковин.",
			"dominion/nightLife/toilets",
			PresetColour.BASE_BLUE_LIGHT,
			NightlifeDistrict.WATERING_HOLE_TOILETS,
			Darkness.ALWAYS_LIGHT,
			null, "в туалете «Водопоя»") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.PERSON, PopulationDensity.SEVERAL, Subspecies.getWorldSpecies(WorldType.NIGHTLIFE_CLUB, this, false)));
		}
	}.initWeatherImmune();
	// Daddy's apartment:
	public static final AbstractPlaceType DADDY_APARTMENT_BEDROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Спальня",
			"Спальня в квартире.",
			"dominion/daddy/bedroom",
			PresetColour.BASE_CRIMSON,
			DaddyDialogue.PLACE_BEDROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в спальне Папочки"
		).initWeatherImmune();
	public static final AbstractPlaceType HELENA_APARTMENT_LOUNGE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Гостиная",
			"В огромной гостиной открытой планировки в квартире Елены стоит множество удобных диванов.",
			"dominion/helenaApartment/lounge",
			PresetColour.BASE_INDIGO,
			HelenaApartment.PLACE_LOUNGE,
			Darkness.ALWAYS_LIGHT,
			null, "в гостиной в квартире Елены"
		) {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.MAID, PopulationDensity.COUPLE, Util.newHashMapOfValues(new Value<>(Subspecies.HARPY, SubspeciesSpawnRarity.TEN))));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType DADDY_APARTMENT_KITCHEN = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Кухня",
			"Кухня в квартире.",
			"dominion/daddy/kitchen",
			PresetColour.BASE_TAN,
			DaddyDialogue.PLACE_KITCHEN,
			Darkness.ALWAYS_LIGHT,
			null, "на кухне в квартире Папочки"
		).initWeatherImmune();
	public static final AbstractPlaceType HELENA_APARTMENT_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Прихожая",
			"Прихожая в пентхаусе Елены впечатляет своими размерами и со вкусом оформлена в современном стиле.",
			"dominion/helenaApartment/entranceHall",
			PresetColour.BASE_GREEN,
			HelenaApartment.PLACE_ENTRANCE_HALL,
			Darkness.ALWAYS_LIGHT,
			null, "в прихожей квартиры Елены"
		) {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.MAID, PopulationDensity.ONE, Util.newHashMapOfValues(new Value<>(Subspecies.HARPY, SubspeciesSpawnRarity.TEN))));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType HELENA_APARTMENT_HALLWAY = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Прихожая",
			"Широкие коридоры с ковровым покрытием соединяют комнаты в квартире Елены.",
			null,
			PresetColour.BASE_BLACK,
			HelenaApartment.PLACE_HALLWAY,
			Darkness.ALWAYS_LIGHT,
			null, "в коридоре квартиры Елены"
		) {
			@Override
			public List<Population> getPopulation() {
				return Util.newArrayListOfValues(new Population(false, PopulationType.MAID, PopulationDensity.OCCASIONAL, Util.newHashMapOfValues(new Value<>(Subspecies.HARPY, SubspeciesSpawnRarity.TEN))));
			}
		}.initWeatherImmune();
	
	
	// Helena's apartment:
	public static final AbstractPlaceType HELENA_APARTMENT_BALCONY = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Балкон",
			"С этого большого балкона с деревянным настилом открывается вид на улицы Доминиона.",
			null,
			PresetColour.BASE_BLUE_LIGHT,
			HelenaApartment.PLACE_BALCONY,
			Darkness.ALWAYS_LIGHT,
			null, "на балконе в квартире Елены"
		).initMapBackgroundColour(PresetColour.MAP_BACKGROUND_BLUE)
		.initWeatherImmune();
	public static final AbstractPlaceType NYAN_APARTMENT_ENTRANCE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Прихожая",
			"Прихожая в квартире Ньян достаточно скромно оформлена и имеет встроенную гардеробную.",
			"dominion/nyanApartment/entranceHall",
			PresetColour.BASE_RED,
			NyanApartment.ENTRANCE_HALL,
			Darkness.ALWAYS_LIGHT,
			null,
			"в прихожей квартиры Ньян"
		).initWeatherImmune();
	public static final AbstractPlaceType HELENA_APARTMENT_HELENA_BEDROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Спальня Елены",
			"Спальня Елены имеет внушительные размеры и роскошно обставлена.",
			"dominion/helenaApartment/bedroomHelena",
			PresetColour.BASE_GOLD,
			HelenaApartment.PLACE_HELENA_BEDROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в спальне Елены"
		).initWeatherImmune();
	public static final AbstractPlaceType HELENA_APARTMENT_SCARLETT_BEDROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Спальня Скарлетт",
			"Спальня Скарлетт имеет внушительные размеры и роскошно обставлена.",
			"dominion/helenaApartment/bedroomScarlett",
			PresetColour.BASE_CRIMSON,
			HelenaApartment.PLACE_SCARLETT_BEDROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в спальне Скарлетт"
		).initWeatherImmune();
	public static final AbstractPlaceType HELENA_APARTMENT_BEDROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Гостевая спальня",
			"Пара свободных спален готовы принять гостей, которые могут остаться здесь на ночь.",
			"dominion/helenaApartment/bedroom",
			PresetColour.BASE_YELLOW,
			HelenaApartment.PLACE_BEDROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в одной из гостевых спален в квартире Елены"
		).initWeatherImmune();
	public static final AbstractPlaceType HELENA_APARTMENT_BATHROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Ванная комната",
			"В каждой из спален апартаментов Елены есть собственная ванная комната.",
			"dominion/helenaApartment/bathroom",
			PresetColour.BASE_BLUE_LIGHT,
			HelenaApartment.PLACE_BATHROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в одной из ванных комнат в квартире Елены"
		).initWeatherImmune();
	public static final AbstractPlaceType HELENA_APARTMENT_OFFICE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Учебная комната",
			"Расположенная в центре квартиры Елены, её личная учебная комната - это место, куда она приходит, чтобы подумать и побыть в одиночестве.",
			"dominion/helenaApartment/office",
			PresetColour.BASE_BROWN,
			HelenaApartment.PLACE_OFFICE,
			Darkness.ALWAYS_LIGHT,
			null, "в кабинете в квартире Елены"
		).initWeatherImmune();
	public static final AbstractPlaceType HELENA_APARTMENT_KITCHEN = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Кухня",
			"На кухне в квартире Елены постоянно работает отличный профессиональный повар.",
			"dominion/helenaApartment/kitchen",
			PresetColour.BASE_ORANGE,
			HelenaApartment.PLACE_KITCHEN,
			Darkness.ALWAYS_LIGHT,
			null, "на кухне в квартире Елены"
		) {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.CHEF, PopulationDensity.ONE, Util.newHashMapOfValues(new Value<>(Subspecies.HARPY, SubspeciesSpawnRarity.TEN))));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType HELENA_APARTMENT_DINING_ROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Столовая",
			"Большой стол, окруженный дюжиной стульев, занимает центральное место в этой просторной столовой.",
			"dominion/helenaApartment/diningRoom",
			PresetColour.BASE_BLUE_STEEL,
			HelenaApartment.PLACE_DINING_ROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в столовой в квартире Елены"
		) {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(false, PopulationType.MAID, PopulationDensity.ONE, Util.newHashMapOfValues(new Value<>(Subspecies.HARPY, SubspeciesSpawnRarity.TEN))));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType NYAN_APARTMENT_LOUNGE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Гостиная",
			"В большой гостиной в квартире Ньян стоит несколько плюшевых диванов, каждый из которых покрыт разноцветными подушками.",
			"dominion/nyanApartment/lounge",
			PresetColour.BASE_INDIGO,
			NyanApartment.LOUNGE,
			Darkness.ALWAYS_LIGHT,
			null,
			"в гостиной в квартире Ньян"
		).initWeatherImmune();
	public static final AbstractPlaceType HELENA_APARTMENT_HOT_TUB = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Джакузи",
			"В одном из углов балкона находится большая джакузи, работающая от магии.",
			"dominion/helenaApartment/hotTub",
			PresetColour.BASE_RED_LIGHT,
			HelenaApartment.PLACE_HOT_TUB,
			Darkness.ALWAYS_LIGHT,
			null, "в джакузи на балконе квартиры Елены"
		).initMapBackgroundColour(PresetColour.MAP_BACKGROUND_BLUE)
		.initWeatherImmune();
	public static final AbstractPlaceType NYAN_APARTMENT_HALLWAY = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Прихожая",
			"Широкие коридоры с ковровым покрытием соединяют комнаты в квартире Ньян.",
			null,
			PresetColour.BASE_BLACK,
			NyanApartment.HALLWAY,
			Darkness.ALWAYS_LIGHT,
			null,
			"в коридоре квартиры Ньян"
		).initWeatherImmune();
	
	

	// Helena's apartment:
	public static final AbstractPlaceType NYAN_APARTMENT_BATHROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Ванная",
			"Эта ванная комната совсем небольшая, в ней есть только унитаз и раковина.",
			"dominion/nyanApartment/toilet",
			PresetColour.BASE_BLUE_LIGHT,
			NyanApartment.BATHROOM,
			Darkness.ALWAYS_LIGHT,
			null,
			"в ванной комнате в квартире Ньян"
		).initWeatherImmune();
	public static final AbstractPlaceType NYAN_APARTMENT_NYAN_BEDROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Спальня Ньян",
			"Спальня Ньян оформлена в мягких пастельных тонах, в ней собрана внушительная коллекция романтических романов и мягких игрушек, и это единственное место, где она чувствует себя совершенно спокойно.",
			"dominion/nyanApartment/bedroomNyan",
			PresetColour.BASE_PINK_LIGHT,
			NyanApartment.NYAN_BEDROOM,
			Darkness.ALWAYS_LIGHT,
			null,
			"в спальне Ньян"
		).initWeatherImmune();
	public static final AbstractPlaceType NYAN_APARTMENT_SPARE_BEDROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Свободная спальня",
			"Прямо напротив спальни Ньян находится полностью обставленная свободная спальня.",
			"dominion/nyanApartment/bedroom",
			PresetColour.BASE_LILAC,
			NyanApartment.SPARE_BEDROOM,
			Darkness.ALWAYS_LIGHT,
			null,
			"в свободной спальне в квартире Ньян"
		).initWeatherImmune();
	public static final AbstractPlaceType NYAN_APARTMENT_ENSUITE = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Ванная комната",
			"В спальне Ньян есть ванная комната, половину которой занимает огромная ванна.",
			"dominion/nyanApartment/bathroom",
			PresetColour.BASE_AQUA,
			NyanApartment.ENSUITE,
			Darkness.ALWAYS_LIGHT,
			null,
			"в ванной комнате в квартире Ньян"
		).initWeatherImmune();
	public static final AbstractPlaceType SUBMISSION_BAT_CAVERNS = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Пещеры летучих мышей",
			"Прогулочные дорожки Подземья здесь заканчиваются, и в глубокий тёмный проём спускается ряд крутых каменных ступеней, ведущих в пещеры летучих мышей, расположенные внизу.",
			"submission/batCaverns",
			PresetColour.BASE_BLUE,
			SubmissionGenericPlaces.BAT_CAVERNS,
			Darkness.ALWAYS_LIGHT,
			null, "в Подземье"
			).initWeatherImmune()
			.initAquatic(Aquatic.MIXED)
			.initSexNotBlockedFromCharacterPresent();
	public static final AbstractPlaceType NYAN_APARTMENT_KITCHEN = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Кухня",
			"Кухня открытой планировки Ньян совмещена со столовой и имеет разумные размеры. Из окон открывается вид на улицу внизу.",
			"dominion/nyanApartment/kitchen",
			PresetColour.BASE_ORANGE,
			NyanApartment.KITCHEN,
			Darkness.ALWAYS_LIGHT,
			null,
			"на кухне в квартире Ньян"
		).initWeatherImmune();
	public static final AbstractPlaceType NYAN_APARTMENT_DINING_ROOM = new AbstractPlaceType(
			WorldRegion.DOMINION,
			"Столовая",
			"Столовая открытой планировки, совмещенная с кухней, содержит несколько шкафов, а также привычные стол и стулья. Из окон открывается вид на улицу внизу.",
			"dominion/nyanApartment/diningRoom",
			PresetColour.BASE_BLUE_STEEL,
			NyanApartment.DINING_ROOM,
			Darkness.ALWAYS_LIGHT,
			null,
			"в столовой в квартире Ньян"
		).initWeatherImmune();
	public static final AbstractPlaceType SUBMISSION_ENTRANCE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"КПП энфорсеров",
			"Чтобы не допустить разгула бесов в Доминионе, каждый из многочисленных входов в Подземье имеет форму хорошо охраняемого контрольно-пропускного пункта энфорсеров.",
			"submission/submissionExit",
			PresetColour.BASE_BROWN,
			SubmissionGenericPlaces.SEWER_ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в Подземье") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.ENFORCER, PopulationDensity.NUMEROUS,
					Util.newHashMapOfValues(
							new Value<>(Subspecies.ALLIGATOR_MORPH, SubspeciesSpawnRarity.FIVE),
							new Value<>(Subspecies.CAT_MORPH, SubspeciesSpawnRarity.TEN),
							new Value<>(Subspecies.DOG_MORPH, SubspeciesSpawnRarity.TEN),
							new Value<>(Subspecies.FOX_MORPH, SubspeciesSpawnRarity.FIVE),
							new Value<>(Subspecies.HORSE_MORPH, SubspeciesSpawnRarity.FIVE),
							new Value<>(Subspecies.RABBIT_MORPH, SubspeciesSpawnRarity.FIVE),
							new Value<>(Subspecies.RAT_MORPH, SubspeciesSpawnRarity.FIVE),
							new Value<>(Subspecies.WOLF_MORPH, SubspeciesSpawnRarity.TEN))));
		}
	}.initWeatherImmune();
	

	
	
	
	// Submission:
	public static final AbstractPlaceType SUBMISSION_WALKWAYS = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Аллеи",
			"Вдоль подземных водных путей, проходящих через большую часть Подземья, вдоль серых каменных стен проложены ухоженные деревянные дорожки.",
			null,
			PresetColour.BASE_BLACK,
			SubmissionGenericPlaces.WALKWAYS,
			Darkness.ALWAYS_LIGHT,
			null, "в Подземье") {
		@Override
		public List<Population> getPopulation() {
			List<Population> pop = new ArrayList<>();
			if(Main.game.isExtendedWorkTime()) {
				pop.add(new Population(true, PopulationType.CROWD, PopulationDensity.SPARSE, Subspecies.getWorldSpecies(WorldType.SUBMISSION, this, false)));
			} else {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.COUPLE, Subspecies.getWorldSpecies(WorldType.SUBMISSION, this, false)));
			}
			pop.addAll(SUBMISSION_ENTRANCE.getPopulation());
			return pop;
		}
	}.initWeatherImmune()
	.initAquatic(Aquatic.MIXED);
	public static final AbstractPlaceType SUBMISSION_GAMBLING_DEN = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Игорный притон",
			"«Игорный притон» - самая популярная достопримечательность во всем Подземье, и сюда в первую очередь приезжают гости из Доминиона.",
			"submission/gamblingDen",
			PresetColour.BASE_GOLD,
			SubmissionGenericPlaces.GAMBLING_DEN,
			Darkness.ALWAYS_LIGHT,
			null, "в Подземье"
			) {
		@Override
		public List<Population> getPopulation() {
			return SUBMISSION_WALKWAYS.getPopulation();
		}
	}.initWeatherImmune()
	.initAquatic(Aquatic.MIXED);
	public static final AbstractPlaceType SUBMISSION_TUNNELS = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Туннели",
			"Тенистые ниши и широкие проёмы труб делают эти тёмные, полные клаустрофобии туннели идеальным местом для засады...",
			"submission/tunnelsIcon",
			PresetColour.BASE_BLACK,
			SubmissionGenericPlaces.TUNNEL,
			Darkness.ALWAYS_DARK, Encounter.SUBMISSION_TUNNELS, "в Подземье"
			).initDangerous()
			.initWeatherImmune()
			.initSexNotBlockedFromCharacterPresent();
	public static final AbstractPlaceType SUBMISSION_RAT_WARREN = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Крысиные угодья",
			"Вход в крысиные норы находится в этой зоне и представляет собой каменную арку, закрытую парой тяжёлых дубовых дверей.",
			"submission/ratWarren",
			PresetColour.BASE_BROWN_DARK,
			SubmissionGenericPlaces.RAT_WARREN,
			Darkness.ALWAYS_DARK,
			null, "в Подземье"
			).initWeatherImmune();
	public static final AbstractPlaceType SUBMISSION_LILIN_PALACE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Дворец Лиссиет",
			"Дворец старшей Лилин, Лиссиет, расположен в дальнем углу Подземья.",
			"submission/lilinPalace",
			PresetColour.BASE_PURPLE,
			SubmissionGenericPlaces.LILIN_PALACE,
			Darkness.ALWAYS_LIGHT,
			null, "в Подземье"
			).initMapBackgroundColour(PresetColour.MAP_BACKGROUND_DARK)
			.initWeatherImmune()
			.initTeleportPermissions(TeleportPermissions.NONE);
	public static final AbstractPlaceType SUBMISSION_IMP_FORTRESS_ALPHA = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Крепость Бесов",
			"В центре огромной подземной пещеры возвышается крепость, обнесённая каменной стеной.",
			"submission/impFortress1",
			PresetColour.BASE_CRIMSON,
			SubmissionGenericPlaces.IMP_FORTRESS_ALPHA,
			Darkness.ALWAYS_LIGHT,
			null, "в Подземье") {
		@Override
		public String getSVGString(Set<AbstractPlaceUpgrade> upgrades) {
			if(Main.game.getNpc(FortressAlphaLeader.class).getWorldLocation()!=WorldType.IMP_FORTRESS_ALPHA) {
				return getSVGOverride("submission/impFortress1", PresetColour.BASE_GREEN_LIGHT);
			}
			return getSVGOverride("submission/impFortress1", PresetColour.BASE_CRIMSON);
		}
	}.initDangerous()
	.initWeatherImmune();
	public static final AbstractPlaceType SUBMISSION_LILIN_PALACE_GATE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Дворцовые ворота Лиссиет",
			"Ворота дворца хорошо охраняются группой полудемонов.",
			"submission/gate",
			PresetColour.BASE_PURPLE_LIGHT,
			SubmissionGenericPlaces.LILIN_PALACE_GATE,
			Darkness.ALWAYS_LIGHT,
			null, "в Подземье") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.GUARD, PopulationDensity.NUMEROUS,
					Util.newHashMapOfValues(new Value<>(Subspecies.HALF_DEMON, SubspeciesSpawnRarity.TEN))));
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_DARK)
	.initWeatherImmune()
	.initTeleportPermissions(TeleportPermissions.NONE);
	public static final AbstractPlaceType SUBMISSION_LILIN_PALACE_CAVERN = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Пещера",
			"Дворец Лиссиет расположен на дальней стороне огромной пещеры, пол которой плавно спускается вниз к парадным воротам.",
			null,
			PresetColour.BASE_GREY,
			SubmissionGenericPlaces.LILIN_PALACE_CAVERN,
			Darkness.ALWAYS_DARK,
			null, "в Подземье"
			).initMapBackgroundColour(PresetColour.MAP_BACKGROUND_DARK)
			.initWeatherImmune();
	// Alpha succubus imp fortress:
	public static final AbstractPlaceType SUBMISSION_IMP_FORTRESS_DEMON = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Цитадель Бесов",
			"Огромные, высотой до потолка, каменные стены образуют внешние укрепления могучей цитадели бесов.",
			"submission/impFortress2",
			PresetColour.BASE_PURPLE,
			SubmissionGenericPlaces.IMP_FORTRESS_DEMON,
			Darkness.ALWAYS_LIGHT,
			null, "в Подземье") {
		@Override
		public String getSVGString(Set<AbstractPlaceUpgrade> upgrades) {
			if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.impFortressDemonDefeated)) {
				return getSVGOverride("submission/impFortress2", PresetColour.BASE_GREEN_LIGHT);
			}
			return getSVGOverride("submission/impFortress2", PresetColour.BASE_PURPLE_DARK);
		}
	}.initDangerous()
	.initWeatherImmune();
	public static final AbstractPlaceType SUBMISSION_IMP_TUNNELS_ALPHA = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Туннели бесов",
			"Эти туннели особенно опасны, так как в них обитают враждебные группы бродячих бесов.",
			"submission/impTunnels1Icon",
			PresetColour.BASE_RED,
			SubmissionGenericPlaces.TUNNEL,
			Darkness.ALWAYS_DARK, Encounter.SUBMISSION_TUNNELS, "в Подземье") {
		@Override
		public String getSVGString(Set<AbstractPlaceUpgrade> upgrades) {
			if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.impFortressAlphaDefeated)) {
				return getSVGOverride("submission/impTunnels1Icon", PresetColour.BASE_GREY);
			}
			return getSVGOverride("submission/impTunnels1Icon", PresetColour.BASE_RED);
		}
	}.initDangerous()
	.initWeatherImmune()
	.initSexNotBlockedFromCharacterPresent();
	public static final AbstractPlaceType FORTRESS_ALPHA_ENTRANCE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Ворота",
			"Через передние ворота крепости можно выйти обратно в Подземье.",
			"submission/impFortress/entrance",
			PresetColour.BASE_RED,
			ImpFortressDialogue.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в крепости Альфа-беса"
			).initWeatherImmune();
	public static final AbstractPlaceType FORTRESS_ALPHA_COURTYARD = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Внутренний двор",
			"От ворот до деревянного замка крепости отделяет лишь пустынный, убогий двор.",
			null,
			PresetColour.BASE_BLACK,
			ImpFortressDialogue.COURTYARD,
			Darkness.ALWAYS_LIGHT,
			null, "в крепости Альфа-беса"
			).initWeatherImmune();
	public static final AbstractPlaceType FORTRESS_ALPHA_KEEP = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Крепость",
			"Грубо построенная крепость служит резиденцией правителя этой крепости.",
			"submission/impFortress/keep",
			PresetColour.BASE_CRIMSON,
			ImpFortressDialogue.KEEP,
			Darkness.ALWAYS_LIGHT,
			null, "в крепости Альфа-беса"
			).initDangerous()
			.initWeatherImmune();
	// Imp citadel:
	public static final AbstractPlaceType SUBMISSION_IMP_FORTRESS_FEMALES = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Крепость Бесов",
			"В центре огромной подземной пещеры возвышается крепость, обнесенная стеной, построенной на возвышенном кургане.",
			"submission/impFortress3",
			PresetColour.BASE_PINK,
			SubmissionGenericPlaces.IMP_FORTRESS_FEMALES,
			Darkness.ALWAYS_LIGHT,
			null, "в Подземье") {
		@Override
		public String getSVGString(Set<AbstractPlaceUpgrade> upgrades) {
			if(Main.game.getNpc(FortressFemalesLeader.class).getWorldLocation()!=WorldType.IMP_FORTRESS_FEMALES) {
				return getSVGOverride("submission/impFortress3", PresetColour.BASE_GREEN_LIGHT);
			}
			return getSVGOverride("submission/impFortress3", PresetColour.BASE_PINK);
		}
	}.initDangerous()
	.initWeatherImmune();
	public static final AbstractPlaceType SUBMISSION_IMP_TUNNELS_DEMON = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Туннели бесов",
			"Эти туннели особенно опасны, так как в них обитают враждебные группы бродячих бесов.",
			"submission/impTunnels2Icon",
			PresetColour.BASE_PURPLE,
			SubmissionGenericPlaces.TUNNEL,
			Darkness.ALWAYS_DARK, Encounter.SUBMISSION_TUNNELS, "в Подземье") {
		@Override
		public String getSVGString(Set<AbstractPlaceUpgrade> upgrades) {
			if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.impFortressDemonDefeated)) {
				return getSVGOverride("submission/impTunnels2Icon", PresetColour.BASE_GREY);
			}
			return getSVGOverride("submission/impTunnels2Icon", PresetColour.BASE_PURPLE);
		}
	}.initDangerous()
	.initWeatherImmune()
	.initSexNotBlockedFromCharacterPresent();
	public static final AbstractPlaceType FORTRESS_DEMON_ENTRANCE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Ворота",
			"Огромные каменные ворота служат единственной точкой доступа между цитаделью и пещерой за её пределами.",
			"submission/impFortress/entrance",
			PresetColour.BASE_RED,
			ImpCitadelDialogue.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в цитадели Темной Сирены") {
		@Override
		public List<Population> getPopulation() {
			if(ImpCitadelDialogue.isImpsDefeated() || ImpCitadelDialogue.isDefeated()) {
				return new ArrayList<>();
			}
			return Util.newArrayListOfValues(new Population(true, PopulationType.GUARD, PopulationDensity.NUMEROUS,
					Util.newHashMapOfValues(
							new Value<>(Subspecies.IMP_ALPHA, SubspeciesSpawnRarity.THREE),
							new Value<>(Subspecies.IMP, SubspeciesSpawnRarity.TEN))));
		}
		@Override
		public Darkness getDarkness() {
			if(ImpCitadelDialogue.isDefeated()) {
				return Darkness.ALWAYS_DARK;
			}
			return Darkness.ALWAYS_LIGHT;
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType FORTRESS_DEMON_COURTYARD = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Внутренний двор",
			"Главный замок отделен от внешних укреплений обширным мощеным внутренним двором.",
			null,
			PresetColour.BASE_BLACK,
			ImpCitadelDialogue.COURTYARD,
			Darkness.ALWAYS_LIGHT,
			null, "в цитадели Темной Сирены") {
		@Override
		public List<Population> getPopulation() {
			return FORTRESS_DEMON_ENTRANCE.getPopulation();
		}
		@Override
		public Darkness getDarkness() {
			return FORTRESS_DEMON_ENTRANCE.getDarkness();
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType FORTRESS_DEMON_WELL = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Колодец",
			"Безграничный источник пресной воды, такой как этот колодец, бесценен для защитников любой крепости.",
			"submission/impFortress/well",
			PresetColour.BASE_BLUE_LIGHT,
			ImpCitadelDialogue.WELL,
			Darkness.ALWAYS_LIGHT,
			null, "в цитадели Темной Сирены") {
		@Override
		public List<Population> getPopulation() {
			return FORTRESS_DEMON_ENTRANCE.getPopulation();
		}
		@Override
		public Darkness getDarkness() {
			return FORTRESS_DEMON_ENTRANCE.getDarkness();
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType FORTRESS_DEMON_KEEP = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Крепость",
			"Главный замок цитадели был вырыт и высечен в твёрдой скале одной из стен пещеры.",
			"submission/impFortress/keep",
			PresetColour.BASE_PURPLE,
			ImpCitadelDialogue.KEEP,
			Darkness.ALWAYS_LIGHT,
			null, "в цитадели Темной Сирены") {
		@Override
		public boolean isDangerous() {
			return Main.game.getPlayer().isQuestProgressLessThan(QuestLine.MAIN, Quest.MAIN_2_C_SIRENS_FALL);
		}
		@Override
		public Darkness getDarkness() {
			return FORTRESS_DEMON_ENTRANCE.getDarkness();
		}
	}.initDangerous()
	.initWeatherImmune();
	public static final AbstractPlaceType FORTRESS_DEMON_CELLS = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Клетки",
			"Высеченные в стенах пещеры, эти клетки служат местом для содержания заключенных.",
			"submission/impFortress/cells",
			PresetColour.BASE_TEAL,
			ImpCitadelDialogue.CELLS,
			Darkness.ALWAYS_LIGHT,
			null, "в цитадели Темной Сирены") {
		@Override
		public List<Population> getPopulation() {
			if(ImpCitadelDialogue.isImpsDefeated() || ImpCitadelDialogue.isDefeated()) {
				return new ArrayList<>();
			}
			return Util.newArrayListOfValues(new Population(true, PopulationType.GUARD, PopulationDensity.FEW,
					Util.newHashMapOfValues(
							new Value<>(Subspecies.IMP_ALPHA, SubspeciesSpawnRarity.THREE),
							new Value<>(Subspecies.IMP, SubspeciesSpawnRarity.TEN))));
		}
		@Override
		public Darkness getDarkness() {
			return FORTRESS_DEMON_ENTRANCE.getDarkness();
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType FORTRESS_LAB = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Лаборатория",
			"С одной стороны двора возведено большое каменное строение, которое служит специализированной лабораторией.",
			"submission/impFortress/laboratory",
			PresetColour.BASE_GREEN_LIME,
			ImpCitadelDialogue.LABORATORY,
			Darkness.ALWAYS_LIGHT,
			null, "в цитадели Темной Сирены") {
		@Override
		public List<Population> getPopulation() {
			return FORTRESS_DEMON_CELLS.getPopulation();
		}
		@Override
		public Darkness getDarkness() {
			return FORTRESS_DEMON_ENTRANCE.getDarkness();
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType FORTRESS_DEMON_TREASURY = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Казна",
			"Каждому правителю цитадели необходимо место, где он мог бы надежно хранить свои драгоценные вещи.",
			"submission/impFortress/treasury",
			PresetColour.BASE_GOLD,
			ImpCitadelDialogue.TREASURY,
			Darkness.ALWAYS_LIGHT,
			null, "в цитадели Темной Сирены") {
		@Override
		public List<Population> getPopulation() {
			return FORTRESS_DEMON_CELLS.getPopulation();
		}
		@Override
		public Darkness getDarkness() {
			return FORTRESS_DEMON_ENTRANCE.getDarkness();
		}
	}.initWeatherImmune();
	// Female seducer imp fortress:
	public static final AbstractPlaceType SUBMISSION_IMP_FORTRESS_MALES = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Крепость Бесов",
			"В центре огромной подземной пещеры возвышается крепость, построенная на возвышенности из скальных пород.",
			"submission/impFortress4",
			PresetColour.BASE_BLUE,
			SubmissionGenericPlaces.IMP_FORTRESS_MALES,
			Darkness.ALWAYS_LIGHT,
			null, "в Подземье") {
		@Override
		public String getSVGString(Set<AbstractPlaceUpgrade> upgrades) {
			if(Main.game.getNpc(FortressMalesLeader.class).getWorldLocation()!=WorldType.IMP_FORTRESS_MALES) {
				return getSVGOverride("submission/impFortress4", PresetColour.BASE_GREEN_LIGHT);
			}
			return getSVGOverride("submission/impFortress4", PresetColour.BASE_BLUE);
		}
	}.initDangerous()
	.initWeatherImmune();
	public static final AbstractPlaceType SUBMISSION_IMP_TUNNELS_FEMALES = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Туннели бесов",
			"Эти туннели особенно опасны, так как в них обитают враждебные группы бродячих бесов.",
			"submission/impTunnels3Icon",
			PresetColour.BASE_PINK_LIGHT,
			SubmissionGenericPlaces.TUNNEL,
			Darkness.ALWAYS_DARK, Encounter.SUBMISSION_TUNNELS, "в Подземье") {
		@Override
		public String getSVGString(Set<AbstractPlaceUpgrade> upgrades) {
			if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.impFortressFemalesDefeated)) {
				return getSVGOverride("submission/impTunnels3Icon", PresetColour.BASE_GREY);
			}
			return getSVGOverride("submission/impTunnels3Icon", PresetColour.BASE_PINK_LIGHT);
		}
	}.initDangerous()
	.initWeatherImmune()
	.initSexNotBlockedFromCharacterPresent();
	public static final AbstractPlaceType FORTRESS_FEMALES_ENTRANCE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Ворота",
			"Через передние ворота крепости можно выйти обратно в Подземье.",
			"submission/impFortress/entrance",
			PresetColour.BASE_RED,
			ImpFortressDialogue.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в крепости женского беса"
			).initWeatherImmune();
	public static final AbstractPlaceType FORTRESS_FEMALES_COURTYARD = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Внутренний двор",
			"От ворот до деревянного замка крепости отделяет лишь пустынный, убогий двор.",
			null,
			PresetColour.BASE_BLACK,
			ImpFortressDialogue.COURTYARD,
			Darkness.ALWAYS_LIGHT,
			null, "в крепости женского беса"
			).initWeatherImmune();
	public static final AbstractPlaceType FORTRESS_FEMALES_KEEP = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Крепость",
			"Грубо построенная крепость служит резиденцией правителя этой крепости.",
			"submission/impFortress/keep",
			PresetColour.BASE_PINK,
			ImpFortressDialogue.KEEP,
			Darkness.ALWAYS_LIGHT,
			null, "в крепости женского беса"
			).initDangerous()
			.initWeatherImmune();
	public static final AbstractPlaceType SUBMISSION_IMP_TUNNELS_MALES = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Туннели бесов",
			"Эти туннели особенно опасны, так как в них обитают враждебные группы бродячих бесов.",
			"submission/impTunnels4Icon",
			PresetColour.BASE_BLUE_LIGHT,
			SubmissionGenericPlaces.TUNNEL,
			Darkness.ALWAYS_DARK, Encounter.SUBMISSION_TUNNELS, "в Подземье") {
		@Override
		public String getSVGString(Set<AbstractPlaceUpgrade> upgrades) {
			if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.impFortressMalesDefeated)) {
				return getSVGOverride("submission/impTunnels4Icon", PresetColour.BASE_GREY);
			}
			return getSVGOverride("submission/impTunnels4Icon", PresetColour.BASE_BLUE_LIGHT);
		}
	}.initDangerous()
	.initWeatherImmune()
	.initSexNotBlockedFromCharacterPresent();
	public static final AbstractPlaceType FORTRESS_MALES_ENTRANCE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Ворота",
			"Через передние ворота крепости можно выйти обратно в Подземье.",
			"submission/impFortress/entrance",
			PresetColour.BASE_RED,
			ImpFortressDialogue.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в крепости мужского беса"
			).initWeatherImmune();
	public static final AbstractPlaceType FORTRESS_MALES_COURTYARD = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Внутренний двор",
			"От ворот до деревянной крепости отделяет пустынный внутренний двор, в котором стоят многочисленные мишени для стрельбы из лука и соломенные манекены.",
			null,
			PresetColour.BASE_BLACK,
			ImpFortressDialogue.COURTYARD,
			Darkness.ALWAYS_LIGHT,
			null, "в крепости мужского беса"
			).initWeatherImmune();
	public static final AbstractPlaceType FORTRESS_MALES_KEEP = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Крепость",
			"Обитель этой крепости имеет форму одноэтажного здания в ярко выраженном японском стиле.",
			"submission/impFortress/keep",
			PresetColour.BASE_BLUE,
			ImpFortressDialogue.KEEP,
			Darkness.ALWAYS_LIGHT,
			null, "в крепости мужского беса"
			).initDangerous()
			.initWeatherImmune();
	public static final AbstractPlaceType LYSSIETH_PALACE_CORRIDOR = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Коридор",
			"Огромные коридоры дворца Лиссиет так же показно роскошны, как и полагается резиденции прямой дочери самой Лилит.",
			null,
			PresetColour.BASE_GREY,
			LyssiethPalaceDialogue.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "во дворце Лиссиет") {
		@Override
		public List<Population> getPopulation() {
			return Util.newArrayListOfValues(new Population(true, PopulationType.MAID, PopulationDensity.COUPLE,
					Util.newHashMapOfValues(
							new Value<>(Subspecies.HUMAN, SubspeciesSpawnRarity.THREE),
							new Value<>(Subspecies.HALF_DEMON, SubspeciesSpawnRarity.TEN))));
		}
	}.initWeatherImmune();
	// Incubus imp fortress:
	public static final AbstractPlaceType LYSSIETH_PALACE_STAIRS_1 = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Лестница",
			"Эти лестницы ведут в комнаты второго этажа, в которых у Лиссиет и её слуг есть свои личные спальни.",
			"submission/lyssiethsPalace/staircase",
			PresetColour.BASE_GREEN,
			LyssiethPalaceDialogue.STAIRCASE,
			Darkness.ALWAYS_LIGHT,
			null, "во дворце Лиссиет") {
		@Override
		public List<Population> getPopulation() {
			return LYSSIETH_PALACE_CORRIDOR.getPopulation();
		}
	}.initWeatherImmune();
	
	
	
	
	// Lyssieth's palace:
	public static final AbstractPlaceType LYSSIETH_PALACE_WINDOWS = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Окна",
			"Коридоры, ответвляющиеся влево и вправо от главного вестибюля, имеют ряд узких окон, из которых открывается вид на пещеру.",
			null,
			PresetColour.BASE_GREY_DARK,
			LyssiethPalaceDialogue.WINDOWS,
			Darkness.ALWAYS_LIGHT,
			null, "во дворце Лиссиет") {
		@Override
		public List<Population> getPopulation() {
			return LYSSIETH_PALACE_CORRIDOR.getPopulation();
		}
	}.initMapBackgroundColour(PresetColour.MAP_BACKGROUND_DARK)
	.initWeatherImmune();
	public static final AbstractPlaceType LYSSIETH_PALACE_ENTRANCE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Вход",
			"Вестибюль дворца Лиссиет поражает своей роскошью и полностью контрастирует с мрачным, унылым экстерьером.",
			"submission/lyssiethsPalace/entrance",
			PresetColour.BASE_RED,
			LyssiethPalaceDialogue.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "во дворце Лиссиет") {
		@Override
		public List<Population> getPopulation() {
			return LYSSIETH_PALACE_CORRIDOR.getPopulation();
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType LYSSIETH_PALACE_ROOM = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Комната",
			"В этих гостиных можно найти мягкие диваны, кофейные столики с экстравагантной резьбой и даже пианино.",
			"submission/lyssiethsPalace/lounge",
			PresetColour.BASE_PINK,
			LyssiethPalaceDialogue.ROOM,
			Darkness.ALWAYS_LIGHT,
			null, "во дворце Лиссиет"
			) {
		@Override
		public DialogueNode getBaseDialogue(Cell cell) {
			if(Main.game.getCharactersPresent().contains(Main.game.getNpc(Elizabeth.class))) {
				return DialogueManager.getDialogueFromId("acexp_submission_palace_elizabeth");
			}
			return LyssiethPalaceDialogue.ROOM;
		}
		@Override
		public List<Population> getPopulation() {
			return LYSSIETH_PALACE_CORRIDOR.getPopulation();
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType LYSSIETH_PALACE_HALL = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Зал",
			"В каждом крыле дворца есть длинный, роскошно обставленный обеденный зал, который Лиссиет использует для приёма особо важных гостей.",
			"submission/lyssiethsPalace/hall",
			PresetColour.BASE_ORANGE_LIGHT,
			LyssiethPalaceDialogue.HALL,
			Darkness.ALWAYS_LIGHT,
			null, "во дворце Лиссиет"
			) {
		@Override
		public List<Population> getPopulation() {
			return LYSSIETH_PALACE_CORRIDOR.getPopulation();
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType LYSSIETH_PALACE_OFFICE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Офис Лиссиет",
			"Лиссиет почти всегда можно найти в её роскошно обставленном офисе, где она исполняет роль важного руководителя.",
			"submission/lyssiethsPalace/office",
			PresetColour.BASE_GOLD,
			LyssiethPalaceDialogue.LYSSIETH_OFFICE_ENTER,
			Darkness.ALWAYS_LIGHT,
			null, "во дворце Лиссиет"
			).initWeatherImmune();
	public static final AbstractPlaceType LYSSIETH_PALACE_SIREN_OFFICE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Офис Мераксис",
			"Комната, через которую нужно пройти, чтобы увидеть Лиссиет, была превращена в кабинет и приемную, и в ней работает не кто иная, как её дочь, Мераксис.",
			"submission/lyssiethsPalace/officeSiren",
			PresetColour.BASE_CRIMSON,
			LyssiethPalaceDialogue.SIREN_OFFICE,
			Darkness.ALWAYS_LIGHT,
			null, "во дворце Лиссиет") {
		@Override
		public List<Population> getPopulation() {
			if(Main.game.getNpc(DarkSiren.class).getHomeWorldLocation()!=WorldType.LYSSIETH_PALACE) {
				return Util.newArrayListOfValues(new Population(false, PopulationType.RECEPTIONIST, PopulationDensity.ONE,
						Util.newHashMapOfValues(
								new Value<>(Subspecies.HALF_DEMON, SubspeciesSpawnRarity.TEN))));
			}
			return super.getPopulation();
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType LYSSIETH_PALACE_STAIRS_2 = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Лестница",
			"Эти лестницы ведут в комнаты второго этажа, в которых у Лиссиет и её слуг есть свои личные спальни.",
			"submission/lyssiethsPalace/staircase",
			PresetColour.BASE_GREEN,
			LyssiethPalaceDialogue.STAIRCASE,
			Darkness.ALWAYS_LIGHT,
			null, "во дворце Лиссиет") {
		@Override
		public List<Population> getPopulation() {
			return LYSSIETH_PALACE_CORRIDOR.getPopulation();
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType SLIME_QUEENS_LAIR_ENTRANCE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Прихожая",
			"Тяжелая дубовая дверь с железными засовами служит для входа и выхода из этой башни.",
			"submission/slimeQueensLair/entranceHall",
			PresetColour.BASE_RED,
			SlimeQueensLair.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в башне Королевы слизи"
			).initWeatherImmune();
	public static final AbstractPlaceType BAT_CAVERN_ENTRANCE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Винтовая лестница",
			"Эта длинная винтовая лестница была высечена из цельной скалы и ведет обратно к главным аллеям Подземья.",
			"submission/batCaverns/cavernStaircase",
			PresetColour.BASE_GREEN,
			BatCaverns.STAIRCASE,
			Darkness.ALWAYS_LIGHT,
			null, "в пещерах летучих мышей"
			).initWeatherImmune();
	
	
	
	// Bat caverns:
	public static final AbstractPlaceType BAT_CAVERN_DARK = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Тёмная пещера",
			"Гнетущую, непроглядную черноту пещер летучих мышей сдерживает мягко светящийся мох, покрывающий весь пол.",
			null,
			PresetColour.BASE_GREY,
			BatCaverns.CAVERN_DARK,
			Darkness.ALWAYS_DARK,
			Encounter.BAT_CAVERN,
			"в пещерах летучих мышей"
			).initDangerous()
			.initWeatherImmune();
	public static final AbstractPlaceType BAT_CAVERN_LIGHT = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Биолюминесцентная пещера",
			"Моховой ковёр в этой части пещеры процветает среди биолюминесцентных грибов всех форм, размеров и цветов и образует тропинки, которые вьются по всей округе.",
			"submission/batCaverns/cavernBioluminescent",
			PresetColour.BASE_AQUA,
			BatCaverns.CAVERN_LIGHT,
			Darkness.ALWAYS_LIGHT, Encounter.BAT_CAVERN, "в пещерах летучих мышей"
			) {
		@Override
		public List<Population> getPopulation() {
			if(Main.game.getCharactersPresent().contains(Main.game.getNpc(Elle.class))) {
				return Util.newArrayListOfValues(new Population(true, PopulationType.GANG_MEMBER, PopulationDensity.SEVERAL, Util.newHashMapOfValues(
						new Value<>(Subspecies.RAT_MORPH, SubspeciesSpawnRarity.TEN),
						new Value<>(Subspecies.ALLIGATOR_MORPH, SubspeciesSpawnRarity.TEN),
						new Value<>(Subspecies.DOG_MORPH, SubspeciesSpawnRarity.TEN))));
			}
			return super.getPopulation();
		}
	}.initDangerous()
	.initWeatherImmune();
	public static final AbstractPlaceType BAT_CAVERN_RIVER = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Подземная река",
			"Медленно движущаяся подземная река прокладывает себе путь через пещеры летучих мышей; её прохладные, тёмные глубины оказываются непроницаемыми для того небольшого количества света, которое дают биолюминесцентные лишайники.",
			"submission/batCaverns/cavernRiver",
			PresetColour.BASE_BLUE,
			BatCaverns.RIVER,
			Darkness.ALWAYS_DARK, Encounter.BAT_CAVERN, "в пещерах летучих мышей"
			).initDangerous()
			.initWeatherImmune()
			.initAquatic(Aquatic.MIXED);
	public static final AbstractPlaceType BAT_CAVERN_RIVER_CROSSING = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Грибной мост",
			"Пара биолюминесцентных грибов размером с дерево была сформирована в горизонтальный, плетёный узор, чтобы образовать живой мост через тёмные глубины реки.",
			"submission/batCaverns/cavernBridge",
			PresetColour.BASE_TEAL,
			BatCaverns.RIVER_BRIDGE,
			Darkness.ALWAYS_DARK, Encounter.BAT_CAVERN, "в пещерах летучих мышей"
			).initDangerous()
			.initWeatherImmune()
			.initAquatic(Aquatic.MIXED);
	public static final AbstractPlaceType BAT_CAVERN_RIVER_END = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Конец подземной реки",
			"Там, где заканчивается тропинка у воды, река падает в бездонную пропасть. Чтобы спастись от падения в бездонную глубину, здесь сооружена металлическая решетка с мелкой сеткой.",
			"submission/batCaverns/cavernRiverEnd",
			PresetColour.BASE_BLUE_DARK,
			BatCaverns.RIVER_END,
			Darkness.ALWAYS_DARK, Encounter.BAT_CAVERN, "в пещерах летучих мышей"
			).initDangerous()
			.initWeatherImmune()
			.initAquatic(Aquatic.MIXED);
	public static final AbstractPlaceType BAT_CAVERN_SHAFT = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Шахта в Доминион",
			"Большая извилистая шахта, расположенная в потолке, обеспечивает прямую связь между пещерами летучих мышей и Доминионом.",
			"submission/batCaverns/cavernShaft",
			PresetColour.BASE_GREEN,
			BatCaverns.SHAFT,
			Darkness.DAYLIGHT,
			null,
			"в пещерах летучих мышей"
			).initWeatherImmune();
	
	public static final AbstractPlaceType BAT_CAVERN_SLIME_QUEEN_LAIR = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Озеро слизи",
			"Гигантское подземное озеро освещается биолюминесцентными грибными лесами, которые выстроились вдоль его берегов. Посреди неподвижных черных вод находится небольшой остров, покрытый грибами.",
			"submission/batCaverns/cavernLake",
			PresetColour.BASE_PINK_LIGHT,
			BatCaverns.SLIME_LAKE,
			Darkness.ALWAYS_LIGHT,
			Encounter.BAT_CAVERN,
			"рядом с озером слизи"
			).initDangerous()
			.initWeatherImmune()
			.initAquatic(Aquatic.MIXED);
       public static final AbstractPlaceType BAT_CAVERNS_REBEL_BASE_ENTRANCE_HANDLE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			   "Странная ручка",
			   "Из скалы торчит странная ручка.",
			"submission/rebelBase/entrance",
			PresetColour.BASE_GREY,
			BatCaverns.REBEL_BASE_ENTRANCE_HANDLE,
			Darkness.ALWAYS_DARK,
			Encounter.BAT_CAVERN,
			   "в пещерах летучих мышей"
			).initDangerous()
			.initWeatherImmune();
	
	// HLF Quest places:
	
	public static final AbstractPlaceType BAT_CAVERNS_REBEL_BASE_ENTRANCE_EXTERIOR = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Вход в скрытую пещеру",
			"Вход в таинственную искусственную пещеру, ранее скрытый за плотно прилегающей каменной дверью.",
			"submission/rebelBase/entrance",
			PresetColour.BASE_RED,
			BatCaverns.REBEL_BASE_ENTRANCE_EXTERIOR,
			Darkness.ALWAYS_DARK,
			Encounter.BAT_CAVERN,
			"рядом со входом в таинственную искусственную пещеру"
			).initDangerous()
			.initWeatherImmune();
	public static final AbstractPlaceType SLIME_QUEENS_LAIR_CORRIDOR = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Коридор",
			"По центру коридора лежит толстый бордово-золотой ковер, а каменные стены по обеим сторонам покрыты гобеленами из плотной ткани.",
			null,
			PresetColour.BASE_GREY,
			SlimeQueensLair.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "в башне Королевы слизи"
			).initWeatherImmune();
	
	// Slime queen's island tower:
	public static final AbstractPlaceType SLIME_QUEENS_LAIR_ROOM = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Спальня",
			"В этой спальне, где отдыхает один или несколько стражей башни, стоит аккуратная кровать с балдахином, а также обычная мебель для спальни.",
			"submission/slimeQueensLair/room",
			PresetColour.BASE_BLUE_LIGHT,
			SlimeQueensLair.ROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в башне Королевы слизи"
			).initWeatherImmune();
	public static final AbstractPlaceType SLIME_QUEENS_LAIR_STAIRS_UP = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Винтовая лестница",
			"На второй этаж башни ведет узкая винтовая лестница.",
			"submission/slimeQueensLair/staircase",
			PresetColour.BASE_GREEN,
			SlimeQueensLair.STAIRCASE_UP,
			Darkness.ALWAYS_LIGHT,
			null, "в башне Королевы слизи"
			).initWeatherImmune();
	public static final AbstractPlaceType SLIME_QUEENS_LAIR_STAIRS_DOWN = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Винтовая лестница",
			"На первый этаж башни ведет узкая винтовая лестница.",
			"submission/slimeQueensLair/staircase",
			PresetColour.BASE_RED,
			SlimeQueensLair.STAIRCASE_DOWN,
			Darkness.ALWAYS_LIGHT,
			null, "в башне Королевы слизи"
			).initWeatherImmune();
	public static final AbstractPlaceType SLIME_QUEENS_LAIR_STORAGE_VATS = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Дистиллятор",
			"Огромный перегонный аппарат, расположенный в этой комнате, является источником не только всех преобразующих в слизь жидкостей в Подземье, но и популярного напитка «Уменьшитель слизи».",
			"submission/slimeQueensLair/storageVats",
			PresetColour.BASE_ORANGE,
			SlimeQueensLair.STORAGE_VATS,
			Darkness.ALWAYS_LIGHT,
			null, "в башне Королевы слизи") {
		@Override
		public void applyInventoryInit(CharacterInventory inventory) {
			for(int i=0; i<15; i++) {
				inventory.addItem(Main.game.getItemGen().generateItem("innoxia_race_slime_slime_quencher"));
			}
			for(int i=0; i<5; i++) {
				inventory.addItem(Main.game.getItemGen().generateItem("innoxia_race_slime_biojuice_canister"));
			}
		}
	}.initItemsPersistInTile()
	.initWeatherImmune();
	public static final AbstractPlaceType GAMBLING_DEN_CORRIDOR = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Игорный притон",
			"Стены просторного помещения «Игорного притона» увешаны бесчисленными игровыми автоматами, а по центру расставлены многочисленные столы и стулья.",
			null,
			PresetColour.BASE_BLACK,
			GamblingDenDialogue.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "в «Игровом притоне»") {
		@Override
		public List<Population> getPopulation() {
			Map<AbstractSubspecies, SubspeciesSpawnRarity> popComponent = new HashMap<>(Subspecies.getWorldSpecies(WorldType.SUBMISSION, this, false));
			Subspecies.getWorldSpecies(WorldType.DOMINION, this, false).forEach((key, value) -> popComponent.merge(key, value, (v1, v2) -> v1));
			popComponent.remove(Subspecies.IMP);
			popComponent.remove(Subspecies.IMP_ALPHA);
			return Util.newArrayListOfValues(new Population(true, PopulationType.CROWD, PopulationDensity.SPARSE, popComponent));
		}
	}.initWeatherImmune();
	public static final AbstractPlaceType SLIME_QUEENS_LAIR_ENTRANCE_GUARDS = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Пост стражи",
			"Здесь находится деревянная баррикада высотой в грудь, построенная от стены до стены.",
			"submission/slimeQueensLair/guards",
			PresetColour.BASE_RED,
			SlimeQueensLair.GUARD_POST,
			Darkness.ALWAYS_LIGHT,
			null, "в башне Королевы слизи"
			) {
		@Override
		public boolean isDangerous() {
			return !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.slimeGuardsDefeated) && !Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.SIDE_SLIME_QUEEN, Quest.SLIME_QUEEN_FOUR);
		}
	}.initDangerous()
	.initWeatherImmune();
	public static final AbstractPlaceType SLIME_QUEENS_LAIR_ROYAL_GUARD = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Пост королевской стражи",
			"Мощный инкуб-слизь фиолетового цвета охраняет именно этот участок коридора.",
			"submission/slimeQueensLair/royalGuards",
			PresetColour.BASE_PURPLE,
			SlimeQueensLair.ROYAL_GUARD_POST,
			Darkness.ALWAYS_LIGHT,
			null, "в башне Королевы слизи"
			) {
		@Override
		public boolean isDangerous() {
			return !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.slimeRoyalGuardDefeated) && !Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.SIDE_SLIME_QUEEN, Quest.SLIME_QUEEN_FOUR);
		}
	}.initDangerous()
	.initWeatherImmune();
	public static final AbstractPlaceType SLIME_QUEENS_LAIR_SLIME_QUEEN = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Палата с кроватью",
			"В спальне королевы слизи находится колоссальная кровать с балдахином, а также огромная ванна, наполненная немалым количеством полупрозрачной розовой жидкости.",
			"submission/slimeQueensLair/bedChamber",
			PresetColour.BASE_PINK,
			SlimeQueensLair.BED_CHAMBER,
			Darkness.ALWAYS_LIGHT,
			null, "в башне Королевы слизи"
			).initWeatherImmune();
	

	
	
	
	
	// Gambling Den:
	


	public static final AbstractPlaceType GAMBLING_DEN_ENTRANCE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Вход",
			"Деревянные двери парадного входа в «Игорный притон» всегда остаются открытыми, что облегчает доступ многочисленным завсегдатаям заведения.",
			"submission/gamblingDen/entrance",
			PresetColour.BASE_GREEN,
			GamblingDenDialogue.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в «Игровом притоне»") {
		@Override
		public List<Population> getPopulation() {
			return GAMBLING_DEN_CORRIDOR.getPopulation();
		}
	}.initWeatherImmune();

	public static final AbstractPlaceType GAMBLING_DEN_OFFICE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Офис Акселя",
			"Кабинет Акселя находится рядом с главным входом и запирается, когда не используется.",
			"submission/gamblingDen/office",
			PresetColour.BASE_ORANGE,
			GamblingDenDialogue.OFFICE,
			Darkness.ALWAYS_LIGHT,
			null, "в «Игровом притоне»") {
	}.initWeatherImmune();
	
	public static final AbstractPlaceType GAMBLING_DEN_TRADER = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Шкатулка Рокси",
			"«Шкатулка Рокси» - довольно завышенный по цене ломбард, предлагающий товары, которые можно найти по гораздо более низким ценам в Доминионе.",
			"submission/gamblingDen/trader",
			PresetColour.BASE_TEAL,
			RoxysShop.TRADER_EXTERIOR,
			Darkness.ALWAYS_LIGHT,
			null, "в «Игровом притоне»"
			).initWeatherImmune();
	
	public static final AbstractPlaceType GAMBLING_DEN_GAMBLING = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Столы для покера с костями",
			"Покер с костями - одна из главных достопримечательностей «Игорного притона», и многочисленные столы, отведенные для этой игры, почти всегда полностью заняты.",
			"submission/gamblingDen/gambling",
			PresetColour.BASE_GOLD,
			GamblingDenDialogue.GAMBLING,
			Darkness.ALWAYS_LIGHT,
			null, "в «Игровом притоне»") {
		@Override
		public List<Population> getPopulation() {
			return GAMBLING_DEN_CORRIDOR.getPopulation();
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType GAMBLING_DEN_PREGNANCY_ROULETTE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Рулетка беременности",
			"Игрой «рулетка беременности» управляет лошадедевушка Эпона, стоящая за длинным деревянным прилавком, вделанным в стену.",
			"submission/gamblingDen/referee",
			PresetColour.BASE_PINK,
			PregnancyRoulette.PREGNANCY_ROULETTE,
			Darkness.ALWAYS_LIGHT,
			null, "в «Игровом притоне»") {
		@Override
		public List<Population> getPopulation() {
			return GAMBLING_DEN_CORRIDOR.getPopulation();
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType GAMBLING_DEN_PREGNANCY = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Стойла для размножения",
			"Именно здесь желающие мужчины, играющие в «рулетку беременности», приступают к своей игре.",
			"submission/gamblingDen/normalPregnancy",
			PresetColour.BASE_BLUE_LIGHT,
			GamblingDenDialogue.PREGNANCY_ROULETTE_MALE_STALLS,
			Darkness.ALWAYS_LIGHT,
			null, "в «Игровом притоне»"
			).initWeatherImmune();
	
	public static final AbstractPlaceType GAMBLING_DEN_FUTA_PREGNANCY = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Стойла фут для размножения",
			"Именно здесь добровольные футанари, играющие в «рулетку беременности», приступают к своей игре.",
			"submission/gamblingDen/futaPregnancy",
			PresetColour.BASE_PINK_LIGHT,
			GamblingDenDialogue.PREGNANCY_ROULETTE_FUTA_STALLS,
			Darkness.ALWAYS_LIGHT,
			null, "в «Игровом притоне»"
			).initWeatherImmune();
	
	
	
	
	// Rat warrens:

	public static final AbstractPlaceType RAT_WARRENS_CORRIDOR_LEFT = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Извилистые проходы",
			"Извилистые проходы в Крысиных угодьях сильно различаются как по ширине, так и по качеству строительства.",
			null,
			PresetColour.BASE_BLACK,
			RatWarrensDialogue.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "в крысиных угодьях") {
		@Override
		public boolean isDangerous() {
			return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensHostile) && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedLeft);
		}
	}.initWeatherImmune();

	public static final AbstractPlaceType RAT_WARRENS_CHECKPOINT_LEFT = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Извилистые проходы",
			"Извилистые проходы в Крысиных угодьях сильно различаются как по ширине, так и по качеству строительства.",
			null,
			PresetColour.BASE_BLACK,
			RatWarrensDialogue.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "в крысиных угодьях") {
		@Override
		public boolean isDangerous() {
			return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensHostile)
					&& (!Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedLeft) || !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedCentre));
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType RAT_WARRENS_CORRIDOR = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Извилистые проходы",
			"Извилистые проходы в Крысиных угодьях сильно различаются как по ширине, так и по качеству строительства.",
			null,
			PresetColour.BASE_BLACK,
			RatWarrensDialogue.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "в крысиных угодьях") {
		@Override
		public boolean isDangerous() {
			return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensHostile) && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedCentre);
		}
	}.initWeatherImmune();

	public static final AbstractPlaceType RAT_WARRENS_CORRIDOR_RIGHT = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Извилистые проходы",
			"Извилистые проходы в Крысиных угодьях сильно различаются как по ширине, так и по качеству строительства.",
			null,
			PresetColour.BASE_BLACK,
			RatWarrensDialogue.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "в крысиных угодьях") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isCaptive()) {
				return VengarCaptiveDialogue.CORRIDOR;
			}
			return super.getDialogue(c, withRandomEncounter, forceEncounter);
		}
		@Override
		public boolean isDangerous() {
			return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensHostile) && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedRight);
		}
	}.initWeatherImmune();

	public static final AbstractPlaceType RAT_WARRENS_CHECKPOINT_RIGHT = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Извилистые проходы",
			"Извилистые проходы в Крысиных угодьях сильно различаются как по ширине, так и по качеству строительства.",
			null,
			PresetColour.BASE_BLACK,
			RatWarrensDialogue.CORRIDOR,
			Darkness.ALWAYS_LIGHT,
			null, "в крысиных угодьях") {
		@Override
		public boolean isDangerous() {
			return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensHostile)
					&& (!Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedCentre) || !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedRight));
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType RAT_WARRENS_ENTRANCE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Вход",
			"Вход в крысиные угодья всегда охраняется как минимум двумя членами банды.",
			"submission/ratWarrens/entrance",
			PresetColour.BASE_GREEN,
			RatWarrensDialogue.ENTRANCE,
			Darkness.ALWAYS_LIGHT,
			null, "в крысиных угодьях") {
		@Override
		public boolean isDangerous() {
			return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensHostile) && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedCentre);
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType RAT_WARRENS_DORMITORY_LEFT = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Общежитие",
			"По стенам этой сырой и мрачной комнаты стоят двухъярусные кровати, а в центре разбросаны несколько столов и стульев.",
			"submission/ratWarrens/dormitory",
			PresetColour.BASE_BROWN,
			RatWarrensDialogue.DORMITORY,
			Darkness.ALWAYS_LIGHT,
			null, "в крысиных угодьях") {
		@Override
		public List<Population> getPopulation() {
			if(!Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedLeft)) {
				if(Main.game.isExtendedWorkTime()) {
					return Util.newArrayListOfValues(new Population(true, PopulationType.GANG_MEMBER, PopulationDensity.FEW, Util.newHashMapOfValues(new Value<>(Subspecies.RAT_MORPH, SubspeciesSpawnRarity.TEN))));
				} else {
					return Util.newArrayListOfValues(new Population(true, PopulationType.GANG_MEMBER, PopulationDensity.SEVERAL, Util.newHashMapOfValues(new Value<>(Subspecies.RAT_MORPH, SubspeciesSpawnRarity.TEN))));
				}
			}
			return new ArrayList<>();
		}
		@Override
		public boolean isDangerous() {
			return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensHostile) && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedLeft);
		}
	}.initWeatherImmune();
	
	public static final AbstractPlaceType RAT_WARRENS_DORMITORY_RIGHT = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Общежитие",
			"По стенам этой сырой и мрачной комнаты стоят двухъярусные кровати, а в центре разбросаны несколько столов и стульев.",
			"submission/ratWarrens/dormitory",
			PresetColour.BASE_BROWN,
			RatWarrensDialogue.DORMITORY,
			Darkness.ALWAYS_LIGHT,
			null, "в крысиных угодьях") {
		@Override
		public List<Population> getPopulation() {
			if(!Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedRight)) {
				if(Main.game.isExtendedWorkTime()) {
					return Util.newArrayListOfValues(new Population(true, PopulationType.GANG_MEMBER, PopulationDensity.FEW, Util.newHashMapOfValues(new Value<>(Subspecies.RAT_MORPH, SubspeciesSpawnRarity.TEN))));
				} else {
					return Util.newArrayListOfValues(new Population(true, PopulationType.GANG_MEMBER, PopulationDensity.SEVERAL, Util.newHashMapOfValues(new Value<>(Subspecies.RAT_MORPH, SubspeciesSpawnRarity.TEN))));
				}
			}
			return new ArrayList<>();
		}
		@Override
		public boolean isDangerous() {
			return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensHostile) && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedRight);
		}
	}.initWeatherImmune();

	public static final AbstractPlaceType RAT_WARRENS_DICE_DEN = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Логово для костей",
			"Место, куда члены банды приходят выпить и поиграть в азартные игры.",
			"submission/ratWarrens/diceDen",
			PresetColour.BASE_COPPER,
			RatWarrensDialogue.DICE_DEN,
			Darkness.ALWAYS_LIGHT,
			null, "в крысиных угодьях") {
		@Override
		public List<Population> getPopulation() {
			if(!Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedCentre)) {
				if(Main.game.isExtendedWorkTime()) {
					return Util.newArrayListOfValues(new Population(true, PopulationType.GANG_MEMBER, PopulationDensity.SEVERAL, Util.newHashMapOfValues(new Value<>(Subspecies.RAT_MORPH, SubspeciesSpawnRarity.TEN))));
				} else {
					return Util.newArrayListOfValues(new Population(true, PopulationType.GANG_MEMBER, PopulationDensity.COUPLE, Util.newHashMapOfValues(new Value<>(Subspecies.RAT_MORPH, SubspeciesSpawnRarity.TEN))));
				}
			}
			return new ArrayList<>();
		}
		@Override
		public boolean isDangerous() {
			return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensHostile) && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedCentre);
		}
	}.initWeatherImmune();

	public static final AbstractPlaceType RAT_WARRENS_MILKING_ROOM = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Доильная комната",
			"Это конечный пункт назначения для людей, которым не повезло быть похищенными бандой Венгара.",
			"submission/ratWarrens/stocks",
			PresetColour.BASE_MAGENTA,
			RatWarrensDialogue.MILKING_ROOM,
			Darkness.ALWAYS_LIGHT,
			null, "в крысиных угодьях") {
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isCaptive()) {
				dialogue = RatWarrensCaptiveDialogue.CAPTIVE_NIGHT;
			} else {
				dialogue = RatWarrensDialogue.MILKING_ROOM;
			}
			return super.getDialogue(c, withRandomEncounter, forceEncounter);
		}
		@Override
		public boolean isDangerous() {
			return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensHostile) && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedLeft);
		}
	}.initWeatherImmune();

	public static final AbstractPlaceType RAT_WARRENS_MILKING_STORAGE = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Молокохранилище",
			"Здесь хранится огромное количество металлических вёдер с молоком; на каждом из них написано, что это за жидкость, и указано, какого она вкуса.",
			"submission/ratWarrens/milkingRoom",
			PresetColour.BASE_YELLOW_LIGHT,
			RatWarrensDialogue.MILKING_STORAGE,
			Darkness.ALWAYS_LIGHT,
			null, "в крысиных угодьях") {
		@Override
		public boolean isDangerous() {
			return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensHostile) && !Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedLeft);
		}
	}.initWeatherImmune();

	public static final AbstractPlaceType RAT_WARRENS_VENGARS_HALL = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Зал Венгара",
			"Огромный каменный зал, заставленный многочисленными длинными деревянными скамьями, с возвышающимся троном в дальнем конце.",
			"submission/ratWarrens/vengarsHall",
			PresetColour.BASE_PURPLE,
			RatWarrensDialogue.VENGARS_HALL,
			Darkness.ALWAYS_LIGHT,
			null, "в крысиных угодьях") {
		@Override
		public AbstractEncounter getEncounterType() {
			if(Main.game.getPlayer().isCaptive()) {
				return Encounter.VENGAR_CAPTIVE_HALL;
			}
			return null;
		}
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isCaptive()) {
				dialogue = VengarCaptiveDialogue.VENGARS_HALL;
			} else {
				dialogue = RatWarrensDialogue.VENGARS_HALL;
			}
			return super.getDialogue(c, withRandomEncounter, forceEncounter);
		}
		@Override
		public List<Population> getPopulation() {
			if(!Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensClearedCentre)) {
				return Util.newArrayListOfValues(new Population(true, PopulationType.GANG_MEMBER, PopulationDensity.NUMEROUS, Util.newHashMapOfValues(new Value<>(Subspecies.RAT_MORPH, SubspeciesSpawnRarity.TEN))));
			}
			return new ArrayList<>();
		}
		@Override
		public boolean isDangerous() {
			return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensHostile);
		}
	}.initWeatherImmune();

	public static final AbstractPlaceType RAT_WARRENS_PRIVATE_BEDCHAMBERS = new AbstractPlaceType(
			WorldRegion.SUBMISSION,
			"Частные спальни",
			"К главному залу примыкают личные покои Венгара и его телохранителей.",
			"submission/ratWarrens/bedroom",
			PresetColour.BASE_PURPLE_LIGHT,
			RatWarrensDialogue.VENGARS_BEDROOM,
			Darkness.ALWAYS_LIGHT,
			null,
			"в крысиных угодьях") {
		@Override
		public AbstractEncounter getEncounterType() {
			if(Main.game.getPlayer().isCaptive()) {
				return Encounter.VENGAR_CAPTIVE_BEDROOM;
			}
			return null;
		}
		@Override
		public DialogueNode getDialogue(Cell c, boolean withRandomEncounter, boolean forceEncounter) {
			if(Main.game.getPlayer().isCaptive()) {
				dialogue = VengarCaptiveDialogue.VENGARS_BEDROOM;
			} else {
				dialogue = RatWarrensDialogue.VENGARS_BEDROOM;
			}
			return super.getDialogue(c, withRandomEncounter, forceEncounter);
		}
		@Override
		public boolean isDangerous() {
			return Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.ratWarrensHostile);
		}
	}.initWeatherImmune();

	// HLF Quest places:
	
    public static final AbstractPlaceType REBEL_BASE_ENTRANCE = new AbstractPlaceType(
 			WorldRegion.SUBMISSION,
			"Вход",
			"Единственный вход и выход из пещеры искусно скрыт за плотно прилегающей каменной дверью.",
			"submission/rebelBase/entrance",
			PresetColour.BASE_RED,
			RebelBase.REBEL_BASE_ENTRANCE,
			Darkness.ALWAYS_DARK,
			null,
			"в таинственной искусственной пещере")
	            .initWeatherImmune();
    
    public static final AbstractPlaceType REBEL_BASE_CORRIDOR = new AbstractPlaceType(
 			WorldRegion.SUBMISSION,
			"Коридор",
			"Искусственная пещера, выстроенная на сомнительных деревянных опорах.",
			null,
			PresetColour.BASE_BLACK,
			RebelBase.REBEL_BASE_CORRIDOR,
			Darkness.ALWAYS_DARK,
			null,
			"в таинственной искусственной пещере")
	            .initWeatherImmune();
    
    public static final AbstractPlaceType REBEL_BASE_SLEEPING_AREA = new AbstractPlaceType(
 			WorldRegion.SUBMISSION,
			"Заброшенный спальный район",
			"Давно заброшенная комната, полная давно заброшенных кроватей.",
			"submission/rebelBase/cache1",
			PresetColour.BASE_BLUE,
			RebelBase.REBEL_BASE_SLEEPING_AREA,
			Darkness.ALWAYS_DARK,
			Encounter.REBEL_BASE,
			"в таинственной искусственной пещере")
	            .initWeatherImmune();
    
    public static final AbstractPlaceType REBEL_BASE_SLEEPING_AREA_SEARCHED = new AbstractPlaceType(
 			WorldRegion.SUBMISSION,
			"Заброшенный спальный район",
			"Давно заброшенная комната, полная давно заброшенных кроватей.",
			"submission/rebelBase/cache1",
			PresetColour.BASE_GREY,
			RebelBase.REBEL_BASE_SLEEPING_AREA_SEARCHED,
			Darkness.ALWAYS_DARK,
			null,
			"в таинственной искусственной пещере")
	            .initWeatherImmune();
    
    public static final AbstractPlaceType REBEL_BASE_COMMON_AREA = new AbstractPlaceType(
 			WorldRegion.SUBMISSION,
			"Заброшенная общая зона",
			"Скудно обставленные руины общей зоны.",
			"submission/rebelBase/cache2",
			PresetColour.BASE_ORANGE,
			RebelBase.REBEL_BASE_COMMON_AREA,
			Darkness.ALWAYS_DARK,
			null,
			"в таинственной искусственной пещере")
	            .initWeatherImmune();
    
    public static final AbstractPlaceType REBEL_BASE_COMMON_AREA_SEARCHED = new AbstractPlaceType(
 			WorldRegion.SUBMISSION,
			"Заброшенная общая зона",
			"Скудно обставленные руины общей зоны.",
			"submission/rebelBase/cache2",
			PresetColour.BASE_GREY,
			RebelBase.REBEL_BASE_COMMON_AREA_SEARCHED,
			Darkness.ALWAYS_DARK,
			null,
			"в таинственной искусственной пещере")
	            .initWeatherImmune();

    public static final AbstractPlaceType REBEL_BASE_ARMORY = new AbstractPlaceType(
 			WorldRegion.SUBMISSION,
			"Частично заваленная комната",
			"Комната, частично заполненная обломками.",
			"submission/rebelBase/cache3",
			PresetColour.BASE_GREEN,
			RebelBase.REBEL_BASE_ARMORY,
			Darkness.ALWAYS_DARK,
			null,
			"в таинственной искусственной пещере")
	            .initWeatherImmune();
    
    public static final AbstractPlaceType REBEL_BASE_ARMORY_SEARCHED = new AbstractPlaceType(
 			WorldRegion.SUBMISSION,
			"Частично заваленная комната",
			"Комната, частично заполненная обломками.",
			"submission/rebelBase/cache3",
			PresetColour.BASE_GREY,
			RebelBase.REBEL_BASE_ARMORY_SEARCHED,
			Darkness.ALWAYS_DARK,
			null,
			"в таинственной искусственной пещере")
	            .initWeatherImmune();
    
     public static final AbstractPlaceType REBEL_BASE_CAVED_IN_ROOM = new AbstractPlaceType(
 			WorldRegion.SUBMISSION,
			 "Захламлённая комната",
			 "Комната, в которой нет ничего, кроме обломков.",
			"submission/rebelBase/cavein",
			PresetColour.BASE_GREY_DARK,
			RebelBase.REBEL_BASE_CAVED_IN_ROOM,
			Darkness.ALWAYS_DARK,
			null,
			 "в таинственной искусственной пещере")
	            .initWeatherImmune();
	
	
	
	// World map tiles:

	public static final AbstractGlobalPlaceType WORLD_MAP_THICK_JUNGLE = new AbstractGlobalPlaceType(
			WorldRegion.JUNGLE,
			"густые джунгли",
			null,
			"Чем дальше в джунгли, тем гуще становится растительность, что позволяет скрываться особенно диким и опасным хищникам...",
			new Colour(Util.newColour(0x6b8f7e)), null, null, "в джунглях") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();
	
	public static final AbstractGlobalPlaceType WORLD_MAP_JUNGLE = new AbstractGlobalPlaceType(
			WorldRegion.JUNGLE,
			"джунгли",
			null,
			"В редкой тропической листве обитает множество различных животных джунглей, не все из которых дружелюбны.",
			new Colour(Util.newColour(0x8fbfa8)), null, null, "в джунглях") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();
	
	public static final AbstractGlobalPlaceType WORLD_MAP_JUNGLE_CITY = new AbstractGlobalPlaceType(
			WorldRegion.JUNGLE_CITY,
			"Ица'аак",
			null,
			"Разросшийся, похожий на город майя, Ица'аак - последний бастион цивилизации перед разросшимися дикими джунглями севера.",
			new Colour(Util.newColour(0xb377b0)), null, null, "за пределами Ица'аака") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	};

	public static final AbstractGlobalPlaceType WORLD_MAP_FOOTHILLS = new AbstractGlobalPlaceType(
			WorldRegion.MOUNTAINS,
			"предгорья",
			null,
			"Постепенное увеличение высоты приводит к холмам у основания Лунных гор.",
			PresetColour.BASE_BLACK, null, null, "в предгорьях Лунных гор") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();
	
	public static final AbstractGlobalPlaceType WORLD_MAP_MOUNTAINS = new AbstractGlobalPlaceType(
			WorldRegion.MOUNTAINS,
			"горы",
			null,
			"Горный хребет на крайнем западе известен как «Лунные горы», и в нем обитает множество альпийских животных-морфов.",
			PresetColour.BASE_GREY_DARK, null, null, "в Лунных горах") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();
	
	public static final AbstractGlobalPlaceType WORLD_MAP_SNOWY_MOUNTAINS = new AbstractGlobalPlaceType(
			WorldRegion.MOUNTAINS,
			"горные вершины",
			null,
			"Самые высокие вершины Лунных гор покрыты снегом, и здесь обитают несколько диких и агрессивных рас...",
			PresetColour.BASE_GREY_LIGHT, null, null, "в Лунных горах") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();

	public static final AbstractGlobalPlaceType WORLD_MAP_SNOWY_VALLEY = new AbstractGlobalPlaceType(
			WorldRegion.SNOW,
			"долина метелей",
			null,
			"В этой защищенной долине регулярно выпадает много снега, и здесь проводятся многочисленные арктические расы.",
			new Colour(Util.newColour(0xeeeeee)), null, null, "в долине метелей") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();
	
	public static final AbstractGlobalPlaceType WORLD_MAP_GLACIAL_LAKE = new AbstractGlobalPlaceType(
			WorldRegion.SNOW,
			"озеро сельков",
			null,
			"На западной стороне долины метелей находится огромное, частично замерзшее озеро.",
			new Colour(Util.newColour(0xbbf0f1)), null, null, "на озере сельков") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous()
	.initAquatic(Aquatic.MIXED);

	public static final AbstractGlobalPlaceType WORLD_MAP_DOMINION = new AbstractGlobalPlaceType(
			WorldRegion.DOMINION,
			"Пригороды Доминиона",
			"Доминион - столица царства Лилит, резиденция королевы суккубов.",
			"global/dominion",
			PresetColour.BASE_PURPLE,
			new Colour(Util.newColour(0x826B85)),
			FieldsDialogue.DOMINION_EXTERIOR,
			null, "на окраине Доминиона") {
		@Override
		protected DialogueNode getBaseDialogue(Cell cell) {
			if(Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.leftDominionFirstTime)) {
				return FieldsDialogue.DOMINION_EXTERIOR;
			} else {
				return DialogueManager.getDialogueFromId("innoxia_places_fields_leaving_dominion_start");
			}
		}
		@Override
		public AbstractWorldType getGlobalLinkedWorldType() {
			return WorldType.DOMINION;
		}
		@Override
		public List<Population> getPopulation() {
			List<Population> pop = new ArrayList<>();
			
			if(Main.game.getCurrentWeather()==Weather.MAGIC_STORM) {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.COUPLE, Subspecies.getDominionStormImmuneSpecies(true)));
				pop.add(new Population(false, PopulationType.ENFORCER, PopulationDensity.OCCASIONAL, Subspecies.getDominionStormImmuneSpecies(true, Subspecies.HUMAN)));
			} else {
				pop.add(new Population(true, PopulationType.PERSON, PopulationDensity.SEVERAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true)));
				pop.add(new Population(false, PopulationType.ENFORCER, PopulationDensity.OCCASIONAL, Subspecies.getWorldSpecies(WorldType.DOMINION, this, true, Subspecies.HUMAN)));
				pop.add(new Population(false, PopulationType.CENTAUR_CARTS, PopulationDensity.OCCASIONAL, Util.newHashMapOfValues(new Value<>(Subspecies.CENTAUR, SubspeciesSpawnRarity.TEN))));
			}
			
			return pop;
		}
	}.initAquatic(Aquatic.MIXED);

	public static final AbstractGlobalPlaceType WORLD_MAP_GRASSLANDS = new AbstractGlobalPlaceType(
			WorldRegion.FIELDS,
			"пастбищенская глушь",//"global/grassland",
			null,
			"В диких землях обитает множество различных рас, большинство из которых такие же дикие и неприрученные, как и земля, которую они населяют.",
			new Colour(Util.newColour(0x688255)),
			FieldsDialogue.GRASSLAND_WILDERNESS,
			null, "в пастбищной глуши Фолойских полей") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();
	
	public static final AbstractGlobalPlaceType WORLD_MAP_FIELDS = new AbstractGlobalPlaceType(
			WorldRegion.FIELDS,
			"Фолойские поля",
			null,
			"Фермерские угодья, окружающие Доминион, известны как «Фолойские поля», и населены в основном животными-морфами.",
			new Colour(Util.newColour(0xB9E3A1)),
			FieldsDialogue.FOLOI_FIELDS,
			null, "на Фолойских полях") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();
	
	public static final AbstractGlobalPlaceType WORLD_MAP_FOREST = new AbstractGlobalPlaceType(
			WorldRegion.WOODLAND,
			"лес",
			"Густые леса, окружающие Фолойские поля, особенно опасны, ведь в них обитают дикие хищные морфы волков, лис и медведей.",
			"global/forest",
			new Colour(Util.newColour(0x51A468)),
			new Colour(Util.newColour(0x5E685E)),
			FieldsDialogue.FOLOI_FOREST,
			null, "в лесистой части Фолойских полей") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();

	public static final AbstractGlobalPlaceType WORLD_MAP_FIELDS_CITY = new AbstractGlobalPlaceType(
			WorldRegion.FIELD_CITY,
			"Элис",
			"Самое большое и процветающее из всех поселений Фолойских полей, Элис служит центром торговли как для юко, так и для рас, обитающих в горах.",
			"global/elis",
			new Colour(Util.newColour(0xd544ae)),
			new Colour(Util.newColour(0x859871)),
			FieldsDialogue.ELIS,
			null, "за пределами Элиса") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	};
	
	public static final AbstractGlobalPlaceType WORLD_MAP_RIVER = new AbstractGlobalPlaceType(
			WorldRegion.RIVER,
			"река Хубур",
			"Река Хубур течет с запада, через Доминион, и впадает в бескрайнее море. Те её участки, которые граничат с Фолойскими полями, считаются безопасными.",
			"global/river",
			new Colour(Util.newColour(0x61BDFF)),
			new Colour(Util.newColour(0x98B4CD)),
			FieldsDialogue.RIVER_HUBUR,
			null, "у реки Хубур") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initAquatic(Aquatic.MIXED)
	.initDangerous();

	public static final AbstractGlobalPlaceType WORLD_MAP_WILD_RIVER = new AbstractGlobalPlaceType(
			WorldRegion.RIVER,
			"река Хубур (дикая)",
			null,
			"Вдали от Доминиона река Хубур - опасное место для купания, ведь в ней обитает множество диких пресноводных рас.",
			new Colour(Util.newColour(0xc1f1ee)), null, null, "у реки Хубур") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous()
	.initAquatic(Aquatic.MIXED);

	public static final AbstractGlobalPlaceType WORLD_MAP_YOUKO_FOREST = new AbstractGlobalPlaceType(
			WorldRegion.YOUKO_FOREST,
			"Шинринское нагорье",
			null,
			"Шинринское нагорье - это гряда невысоких, покрытых лесом холмов, высота которых неуклонно растет по мере продвижения на запад. Здесь обитают неуловимые юко.",
			new Colour(Util.newColour(0x6ccc74)), null, null, "в Шинринском нагорье") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();

	public static final AbstractGlobalPlaceType WORLD_MAP_SEA = new AbstractGlobalPlaceType(
			WorldRegion.SEA,
			"бескрайнее море",
			null,
			"Водные расы, населяющие царство Лилит, не любят отходить далеко от берега, поэтому для них море считается бескрайним.",
			PresetColour.BASE_BLUE_DARK, null, null, "в бескрайнем море") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous()
	.initAquatic(Aquatic.WATER_SURFACE);
	
	public static final AbstractGlobalPlaceType WORLD_MAP_SEA_CITY = new AbstractGlobalPlaceType(
			WorldRegion.SEA_CITY,
			"Лионесс",
			null,
			"Подводный город Лионесс расположен у восточного побережья, и, что неудивительно, его особенно трудно посетить представителям неводных рас.",
			new Colour(Util.newColour(0x8264b0)), null, null, "за пределами Лионессы") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initAquatic(Aquatic.WATER_UNDER);

	public static final AbstractGlobalPlaceType WORLD_MAP_ARID_GRASSLAND = new AbstractGlobalPlaceType(
			WorldRegion.SAVANNAH,
			"засушливые луга",
			null,
			"На юге дикие пастбища начинают высыхать и становятся излюбленным местом обитания таких морф, как львы, леопарды и зебры.",
			PresetColour.BASE_YELLOW_LIGHT, null, null, "на засушливых лугах") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();
	
	public static final AbstractGlobalPlaceType WORLD_MAP_ARID_SAVANNAH = new AbstractGlobalPlaceType(
			WorldRegion.SAVANNAH,
			"саванна",
			null,
			"По всей территории разбросаны редколесья с открытым пологом, в которых обитают те же расы, что и на засушливых лугах.",
			PresetColour.BASE_TAN, null, null, "в саванне") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();

	public static final AbstractGlobalPlaceType WORLD_MAP_DESERT = new AbstractGlobalPlaceType(
			WorldRegion.DESERT,
			"пустыня",
			null,
			"К югу от засушливых лугов вся растительность вымирает, образуя жаркую, бесплодную пустошь.",
			new Colour(Util.newColour(0xffe7a7)), null, null, "в пустыне") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();
	
	public static final AbstractGlobalPlaceType WORLD_MAP_SAND_DUNES = new AbstractGlobalPlaceType(
			WorldRegion.DESERT,
			"песчаные дюны",
			null,
			"На южном краю пустыни находится огромная гряда песчаных дюн, где обитает множество опасных рас.",
			new Colour(Util.newColour(0xffdb7a)), null, null, "в песчаных дюнах") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();
	
	public static final AbstractGlobalPlaceType WORLD_MAP_DESERT_CITY = new AbstractGlobalPlaceType(
			WorldRegion.DESERT_CITY,
			"Тинис",
			null,
			"Город, напоминающий древний Египет, Тинис - самое южное поселение в царстве Лилит, известное своим престижным магическим университетом.",
			new Colour(Util.newColour(0xd5445e)), null, null, "за пределами Тиниса") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	};

	public static final AbstractGlobalPlaceType WORLD_MAP_VOLCANO = new AbstractGlobalPlaceType(
			WorldRegion.VOLCANO,
			"вулкан «Дыхание дракона»",
			null,
			"Огромный вулкан, вечно источающий раскаленную лаву. Несмотря на название, драконы здесь так же редки, как и в остальном царстве Лилит.",
			PresetColour.BASE_ORANGE, null, null, "на вулкане «Дыхание дракона»") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();
	
	public static final AbstractGlobalPlaceType WORLD_MAP_LAVA_FLOWS = new AbstractGlobalPlaceType(
			WorldRegion.VOLCANO,
			"лавовые потоки",
			null,
			"Лава, изливающаяся из вулкана, медленно стекает в южном направлении.",
			PresetColour.BASE_BLACK, null, null, "у лавовых потоков") {
				@Override
				public AbstractWorldType getGlobalLinkedWorldType() {
					return null;
				}
	}.initDangerous();
	
	
	
	private static final List<AbstractPlaceType> allPlaceTypes = new ArrayList<>();
	private static final Map<AbstractPlaceType, String> placeToIdMap = new HashMap<>();
	private static final Map<String, AbstractPlaceType> idToPlaceMap = new HashMap<>();

	public static List<AbstractPlaceType> getAllPlaceTypes() {
		return allPlaceTypes;
	}
	
	public static AbstractPlaceType getPlaceTypeFromId(String id) {
		id = id.replaceAll("ALEXA", "HELENA");
		id = id.replaceAll("SUPPLIER_DEPOT", "TEXTILE_WAREHOUSE");
		
		if(id.equals("ZARANIX_FF_BEDROOM")) {
			id = "ZARANIX_FF_OFFICE";
			
		} else if(id.equals("LILAYA_HOME_ROOM_WINDOW_GROUND_FLOOR_SLAVE")
				|| id.equals("LILAYA_HOME_ROOM_WINDOW_GROUND_FLOOR_MILKING")) {
			id = "LILAYA_HOME_ROOM_WINDOW_GROUND_FLOOR";
			
		} else if(id.equals("LILAYA_HOME_ROOM_GARDEN_GROUND_FLOOR_SLAVE")
				|| id.equals("LILAYA_HOME_ROOM_GARDEN_GROUND_FLOOR_MILKING")) {
			id = "LILAYA_HOME_ROOM_GARDEN_GROUND_FLOOR";
			
		} else if(id.equals("LILAYA_HOME_ROOM_WINDOW_FIRST_FLOOR_SLAVE")
				|| id.equals("LILAYA_HOME_ROOM_WINDOW_FIRST_FLOOR_MILKING")) {
			id = "LILAYA_HOME_ROOM_WINDOW_FIRST_FLOOR";
			
		} else if(id.equals("LILAYA_HOME_ROOM_GARDEN_FIRST_FLOOR_SLAVE")
				|| id.equals("LILAYA_HOME_ROOM_GARDEN_FIRST_FLOOR_MILKING")) {
			id = "LILAYA_HOME_ROOM_GARDEN_FIRST_FLOOR";
			
		} else if(id.equals("DOMINION_EXIT_TO_JUNGLE")) {
			id = "DOMINION_EXIT_EAST";
		} else if(id.equals("DOMINION_EXIT_TO_DESERT")) {
			id = "DOMINION_EXIT_SOUTH";
		} else if(id.equals("DOMINION_EXIT_TO_FIELDS")) {
			id = "DOMINION_EXIT_NORTH";
		} else if(id.equals("DOMINION_EXIT_TO_SEA")) {
			id = "DOMINION_EXIT_WEST";
			
		} else if(id.equals("SHOPPING_ARCADE_SUPPLIER_DEPOT")) {
			id = "SHOPPING_ARCADE_RESTAURANT";
			
		} else if(id.equals("innoxia_fields_elis_town_tavern_seedy")) {
			id = "innoxia_fields_elis_town_tavern_alley";
		}
		
		id = Util.getClosestStringMatch(id, idToPlaceMap.keySet());
		return idToPlaceMap.get(id);
	}

	public static String getIdFromPlaceType(AbstractPlaceType placeType) {
		return placeToIdMap.get(placeType);
	}
	
	static {
		// Modded place types:
		
		Map<String, Map<String, File>> moddedFilesMap = Util.getExternalModFilesById("/maps", "placeTypes", null);
		for(Entry<String, Map<String, File>> entry : moddedFilesMap.entrySet()) {
			for(Entry<String, File> innerEntry : entry.getValue().entrySet()) {
				try {
					String id = innerEntry.getKey().replace("_placeTypes", "");
					AbstractPlaceType placeType = new AbstractPlaceType(innerEntry.getValue(), entry.getKey(), id, true) {};
					allPlaceTypes.add(placeType);
					placeToIdMap.put(placeType, id);
					idToPlaceMap.put(id, placeType);
//					System.out.println("modded PT: "+innerEntry.getKey());
				} catch(Exception ex) {
					System.err.println("Loading modded place type failed at 'PlaceType'. File path: "+innerEntry.getValue().getAbsolutePath());
					System.err.println("Actual exception: ");
					ex.printStackTrace(System.err);
				}
			}
		}
		
		// External res place types:
		
		Map<String, Map<String, File>> filesMap = Util.getExternalFilesById("res/maps", "placeTypes", null);
		for(Entry<String, Map<String, File>> entry : filesMap.entrySet()) {
			for(Entry<String, File> innerEntry : entry.getValue().entrySet()) {
				try {
					String id = innerEntry.getKey().replace("_placeTypes", "");
					AbstractPlaceType placeType = new AbstractPlaceType(innerEntry.getValue(), entry.getKey(), id, false) {};
					allPlaceTypes.add(placeType);
					placeToIdMap.put(placeType, id);
					idToPlaceMap.put(id, placeType);
//					System.out.println("res PT: "+innerEntry.getKey()+" | "+id);
				} catch(Exception ex) {
					System.err.println("Loading place type failed at 'PlaceType'. File path: "+innerEntry.getValue().getAbsolutePath());
					System.err.println("Actual exception: ");
					ex.printStackTrace(System.err);
				}
			}
		}

		// Hard-coded place types (all those up above):
		
		Field[] fields = PlaceType.class.getFields();
		
		for(Field f : fields) {
			if(AbstractPlaceType.class.isAssignableFrom(f.getType())) {
				AbstractPlaceType placeType;
				try {
					placeType = ((AbstractPlaceType) f.get(null));

					placeToIdMap.put(placeType, f.getName());
					idToPlaceMap.put(f.getName(), placeType);
					allPlaceTypes.add(placeType);
					
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
