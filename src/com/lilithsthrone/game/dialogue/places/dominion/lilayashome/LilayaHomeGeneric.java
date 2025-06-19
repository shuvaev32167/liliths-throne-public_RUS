package com.lilithsthrone.game.dialogue.places.dominion.lilayashome;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.fetishes.Fetish;
import com.lilithsthrone.game.character.npc.NPC;
import com.lilithsthrone.game.character.npc.dominion.Daddy;
import com.lilithsthrone.game.character.npc.dominion.Lilaya;
import com.lilithsthrone.game.character.npc.dominion.Rose;
import com.lilithsthrone.game.character.quests.Quest;
import com.lilithsthrone.game.character.quests.QuestLine;
import com.lilithsthrone.game.combat.spells.Spell;
import com.lilithsthrone.game.combat.spells.SpellSchool;
import com.lilithsthrone.game.dialogue.DialogueFlagValue;
import com.lilithsthrone.game.dialogue.DialogueManager;
import com.lilithsthrone.game.dialogue.DialogueNode;
import com.lilithsthrone.game.dialogue.companions.CompanionManagement;
import com.lilithsthrone.game.dialogue.companions.OccupantDialogue;
import com.lilithsthrone.game.dialogue.companions.OccupantManagementDialogue;
import com.lilithsthrone.game.dialogue.companions.SlaveDialogue;
import com.lilithsthrone.game.dialogue.npcDialogue.dominion.DaddyDialogue;
import com.lilithsthrone.game.dialogue.places.dominion.DominionPlaces;
import com.lilithsthrone.game.dialogue.responses.Response;
import com.lilithsthrone.game.dialogue.responses.ResponseSex;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.game.inventory.clothing.ClothingType;
import com.lilithsthrone.game.inventory.item.ItemType;
import com.lilithsthrone.game.occupantManagement.slave.SlaveJob;
import com.lilithsthrone.game.occupantManagement.slave.SlaveJobSetting;
import com.lilithsthrone.game.occupantManagement.slave.SlavePermissionSetting;
import com.lilithsthrone.game.sex.managers.dominion.SMRoseHands;
import com.lilithsthrone.game.sex.positions.slots.SexSlotUnique;
import com.lilithsthrone.main.Main;
import com.lilithsthrone.utils.Util;
import com.lilithsthrone.utils.Util.Value;
import com.lilithsthrone.utils.colours.BaseColour;
import com.lilithsthrone.utils.colours.Colour;
import com.lilithsthrone.utils.colours.PresetColour;
import com.lilithsthrone.world.Cell;
import com.lilithsthrone.world.WorldType;
import com.lilithsthrone.world.places.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.lilithsthrone.utils.Constants.RUSSIAN_LOCALE;

/**
 * @since 0.1.75
 * @version 0.3.9
 * @author Innoxia
 */
public class LilayaHomeGeneric {
	
	public static Response interactWithNPC(GameCharacter slave) {
        return new Response(UtilText.parse(slave, "[npc.Name]"), UtilText.parse(slave, "Взаимодействовать с [npc.morphSingleNameInstr([npc.name])]."), slave.isSlave() ? SlaveDialogue.SLAVE_START : OccupantDialogue.OCCUPANT_START) {
            @Override
            public Colour getHighlightColour() {
                return slave.getFemininity().getColour();
            }

            @Override
            public void effects() {
                if (slave.isSlave()) {
                    SlaveDialogue.initDialogue((NPC) slave, false);
                } else {
                    OccupantDialogue.initDialogue((NPC) slave, false, false);
                }
            }
        };

    }
	
	public static void dailyUpdate() {
        if (Main.game.getDialogueFlags().hasSavedLong(LilayaSpa.SPA_CONSTRUCTTION_TIMER_ID)) {
            Cell constructionCell = Main.game.getWorlds().get(WorldType.LILAYAS_HOUSE_GROUND_FLOOR).getCell(PlaceType.LILAYA_HOME_UNDER_CONSTRUCTION);
            if (constructionCell != null) {
                long dayInstalled = Main.game.getDialogueFlags().getSavedLong(LilayaSpa.SPA_CONSTRUCTTION_TIMER_ID);
                if (Main.game.getDayNumber() - dayInstalled >= 7) {
                    constructionCell.getPlace().setPlaceType(PlaceType.LILAYA_HOME_SPA);
                    Main.game.getDialogueFlags().removeSavedLong(LilayaSpa.SPA_CONSTRUCTTION_TIMER_ID);
                }
            }
        }
    }
	
	public static List<NPC> getSlavesAndOccupantsPresent() {
        List<NPC> charactersPresent = Main.game.getNonCompanionCharactersPresent();
        charactersPresent.removeIf((character) -> character.isElemental());
        return charactersPresent;
    }
	
	private static boolean isPlayerHasDolls() {
        return Main.game.getPlayer().getSlavesOwnedAsCharacters().stream().anyMatch(slave -> slave.isDoll());
    }
	
	public static String getLilayasHouseStandardResponseTabs(int i) {
        AbstractPlaceType playerPlaceType = Main.game.getPlayer().getLocationPlace().getPlaceType();
        switch (i) {
            case 0:
                return "Действия";
            case 1:
                return "Б. путешествие";
            case 2:
                if (playerPlaceType == PlaceType.LILAYA_HOME_ROOM_PLAYER) {
                    return "Ванная";
                }
                if (isPlayerHasDolls()
                        && (playerPlaceType == PlaceType.LILAYA_HOME_ENTRANCE_HALL
                        || playerPlaceType == PlaceType.LILAYA_HOME_CORRIDOR
                        || playerPlaceType == PlaceType.LILAYA_HOME_GARDEN
                        || playerPlaceType == PlaceType.LILAYA_HOME_FOUNTAIN
                        || playerPlaceType == PlaceType.LILAYA_HOME_STAIR_DOWN
                        || playerPlaceType == PlaceType.LILAYA_HOME_STAIR_DOWN_SECONDARY
                        || playerPlaceType == PlaceType.LILAYA_HOME_STAIR_UP
                        || playerPlaceType == PlaceType.LILAYA_HOME_STAIR_UP_SECONDARY)) {
                    return "Кукольные станции";
                }
                break;

        }
        return null;
    }
	
	public static Response getLilayasHouseDollStationResponses(int index) {
        if (index == 0) {
            index = 15;
        } else if (index < 15) {
            index--;
        }
        List<GameCharacter> dolls = Main.game.getPlayer().getSlavesOwnedAsCharacters().stream().filter(slave -> slave.isDoll()).collect(Collectors.toList());
        List<Response> responses = new ArrayList<>();
        for (int i = 0; i < dolls.size(); i++) {
            GameCharacter doll = dolls.get(i);
            boolean alreadyActive = doll.getSlaveStationWorldType() == Main.game.getPlayer().getWorldLocation() && Main.game.getPlayer().getLocation().equals(doll.getSlaveStationLocation());
            responses.add(new Response(
                    UtilText.parse(doll, "[npc.Name]"),
                    UtilText.parse(doll,
                            "Установить эту плитку как участок <span style='color:" + doll.getFemininity().getColour().toWebHexString() + "'>[npc.morphSingleNameGene([npc.name])]" + (doll.hasSurname() ? " [npc.morphSingleSurnameGene([npc.surname])]" : "") + "</span>, когда [npc.sheIs] работает как статуя."
                                    + "<br/>[style.italics("
                                    + (doll.getSlaveStationWorldType() == null
                                    ? "Поскольку у н[npc.morphSingleNameGene([npc.sheHasFull])] нет установленной станции, [npc.name] будет использовать случайную плитку коридора в качестве [npc.her] станции."
                                    : (alreadyActive
                                    ? "[style.colourExcellent(Эта плитка уже установлена как станция [npc.morphSingleNameGene([npc.namePos])].)]"
                                    : "[style.colourMinorGood(Хотя эта плитка не является станцией [npc.morphSingleNameGene([npc.namePos])], у н[npc.morphSingleNameGene([npc.she])] уже есть одна, установленная в другом месте.)]"))
                                    + ")]"),
                    Main.game.getDefaultDialogue(false)) {
                @Override
                public Colour getHighlightColour() {
                    if (doll.getSlaveStationWorldType() == null) {
                        return super.getHighlightColour();
                    } else if (!alreadyActive) {
                        return PresetColour.GENERIC_MINOR_GOOD;
                    } else {
                        return PresetColour.GENERIC_EXCELLENT;
                    }
                }

                @Override
                public void effects() {
                    doll.setSlaveStationWorldType(Main.game.getPlayer().getWorldLocation());
                    doll.setSlaveStationLocation(Main.game.getPlayer().getLocation());
                }
            });
        }
        if (responses.size() <= index) {
            return null;
        }
        return responses.get(index);
    }
	
	public static Response getLilayasHouseFastTravelResponses(int index) {
        if (index == 1) {
            if (Main.game.getPlayer().getLocationPlace().getPlaceType() == PlaceType.LILAYA_HOME_ROOM_PLAYER) {
                return new Response("Твоя комната", "Ты уже в своей комнате, поэтому нет необходимости быстро перемещаться туда...", null);
            }
            return new Response("Твоя комната", "Быстрое перемещение в твою комнату", PlaceType.LILAYA_HOME_ROOM_PLAYER.getDialogue(false)) {
                @Override
                public void effects() {
                    Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_FIRST_FLOOR, PlaceType.LILAYA_HOME_ROOM_PLAYER, false);
                    Main.game.setResponseTab(0);
                }
            };

        } else if (index == 2) {
            if (Main.game.getPlayer().getLocationPlace().getPlaceType() == PlaceType.LILAYA_HOME_LAB) {
                return new Response("Лаба Лилайи", "Ты уже в лаборатории Лилайи, так что нет необходимости быстро перемещаться туда...", null);
            }
            return new Response("Лаба Лилайи", "Быстрое перемещение в лабораторию Лилайи.", PlaceType.LILAYA_HOME_LAB.getDialogue(false)) {
                @Override
                public void effects() {
                    Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_GROUND_FLOOR, PlaceType.LILAYA_HOME_LAB, false);
                    Main.game.setResponseTab(0);
                }
            };

        } else if (index == 3) {
            if (Main.game.getPlayer().getLocationPlace().getPlaceType() == PlaceType.LILAYA_HOME_KITCHEN) {
                return new Response("Кухня", "Ты уже на кухне, поэтому нет необходимости быстро перемещаться туда...", null);
            }
            return new Response("Кухня", "Быстрое перемещение на кухню.", PlaceType.LILAYA_HOME_KITCHEN.getDialogue(false)) {
                @Override
                public void effects() {
                    Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_GROUND_FLOOR, PlaceType.LILAYA_HOME_KITCHEN, false);
                    Main.game.setResponseTab(0);
                }
            };

        } else if (index == 4) {
            if (Main.game.getPlayer().getLocationPlace().getPlaceType() == PlaceType.LILAYA_HOME_LIBRARY) {
                return new Response("Библиотека", "Ты уже в библиотеке, поэтому нет необходимости быстро перемещаться туда...", null);
            }
            return new Response("Библиотека", "Быстрое перемещение в библиотеку.", PlaceType.LILAYA_HOME_LIBRARY.getDialogue(false)) {
                @Override
                public void effects() {
                    Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_GROUND_FLOOR, PlaceType.LILAYA_HOME_LIBRARY, false);
                    Main.game.setResponseTab(0);
                }
            };

        } else if (index == 5) {
            if (Main.game.getPlayer().getLocationPlace().getPlaceType() == PlaceType.LILAYA_HOME_ENTRANCE_HALL) {
                return new Response("Вестибюль", "Ты уже в вестибюле, поэтому нет необходимости быстро перемещаться туда...", null);
            }
            return new Response("Вестибюль", "Быстрое перемещение в вестибюль.", PlaceType.LILAYA_HOME_ENTRANCE_HALL.getDialogue(false)) {
                @Override
                public void effects() {
                    Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_GROUND_FLOOR, PlaceType.LILAYA_HOME_ENTRANCE_HALL, false);
                    Main.game.setResponseTab(0);
                }
            };
        }

        return null;
    }
	
	private static Response getRoomResponse(int responseTab, int index) {
        AbstractPlaceUpgrade coreUpgrade = null;
        for (AbstractPlaceUpgrade pu : Main.game.getPlayer().getLocationPlace().getPlaceUpgrades()) {
            if (pu.isCoreRoomUpgrade()) {
                coreUpgrade = pu;
            }
        }

        if (responseTab == 1) {
            return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
        }

        List<NPC> charactersPresent = getSlavesAndOccupantsPresent();
        List<NPC> slavesAssignedToRoom = new ArrayList<>();

        if (coreUpgrade == PlaceUpgrade.LILAYA_OFFICE
                || coreUpgrade == PlaceUpgrade.LILAYA_SPA) {
            slavesAssignedToRoom.addAll(charactersPresent);

        } else {
            for (String slave : Util.mergeLists(Main.game.getPlayer().getFriendlyOccupants(), Main.game.getPlayer().getSlavesOwned())) {
                try {
                    NPC slaveNPC = (NPC) Main.game.getNPCById(slave);
                    if (slaveNPC != null && (slaveNPC.getHomeWorldLocation() == Main.game.getPlayer().getWorldLocation() && slaveNPC.getHomeLocation().equals(Main.game.getPlayer().getLocation()))) {
                        slavesAssignedToRoom.add(slaveNPC);
                    }
                } catch (Exception e) {
                    Util.logGetNpcByIdError("getRoomResponse()", slave);
                }
            }
        }
//		charactersPresent.removeIf((characterPresent) -> Main.game.getPlayer().hasCompanion(characterPresent) && !slavesAssignedToRoom.contains(characterPresent));

        if (index == 0) {
            return null;

        } else if (index == 1) {
            if (Main.game.getPlayer().isAbleToAccessRoomManagement()) {
                return new Response("Управление комнатой", "Открыть экран управления данной комнатой.", OccupantManagementDialogue.ROOM_UPGRADES) {
                    @Override
                    public void effects() {
                        OccupantManagementDialogue.cellToInspect = Main.game.getPlayerCell();
                    }
                };
            } else {
                return new Response("Управление комнатой", "Чтобы получить доступ к этому меню, нужна лицензия рабовладельца или разрешение от Лилайи на размещение своих друзей или кукол!", null);
            }

        } else if (index == 2) {
            if (Main.game.getPlayer().isAbleToAccessRoomManagement()) {
                return new Response("Управление людьми", "Открыть экран управления своими рабами и дружелюбными жильцами.", CORRIDOR) {
                    @Override
                    public DialogueNode getNextDialogue() {
                        return OccupantManagementDialogue.getSlaveryRoomListDialogue(null, null);
                    }

                    @Override
                    public void effects() {
                        CompanionManagement.initManagement(Main.game.getDefaultDialogue(), 0, null);
                    }
                };
            } else {
                return new Response("Управление людьми", "Чтобы получить доступ к этому меню, нужна лицензия рабовладельца или разрешение от Лилайи на размещение своих друзей или кукол!", null);
            }
        }

        int indexPresentStart = 3;

        if (coreUpgrade == PlaceUpgrade.LILAYA_OFFICE) {
            indexPresentStart = 4;
            if (index == 3) {
                return new Response("Книга занятости", "Открыть экран «Книга занятости», с помощью которого можно управлять всеми комнатами, рабами и дружественными жильцами.", CORRIDOR) {
                    @Override
                    public DialogueNode getNextDialogue() {
                        return OccupantManagementDialogue.getSlaveryOverviewDialogue(null);
                    }

                    @Override
                    public void effects() {
                        CompanionManagement.initManagement(Main.game.getDefaultDialogue(), 0, null);
                    }
                };
            }
        }

        if (coreUpgrade == PlaceUpgrade.LILAYA_SPA) {
            indexPresentStart = 4;
            if (index == 3) {
                return LilayaSpa.SPA_RECEPTION.getResponse(responseTab, index);
            }
        }

        if (index - indexPresentStart < slavesAssignedToRoom.size()) {
            NPC character = slavesAssignedToRoom.get(index - indexPresentStart);
            if (charactersPresent.contains(character) || (character.getHomeCell().equals(Main.game.getPlayerCell()) && Main.game.getPlayer().getCompanions().contains(character))) {
                return interactWithNPC(character);
            } else {
                return new Response(UtilText.parse(character, "[npc.Name]"),
                        UtilText.parse(character, "Хотя эта комната [npc.morphSingleNameGene([npc.namePos])], [npc.sheIs] "
                                + (character.getLocationPlace().getPlaceUpgrades().contains(PlaceUpgrade.LILAYA_SLAVE_LOUNGE)
                                ? "в данный момент отдыхает в комнате отдыха для рабов."
                                : "сейчас на работе.")), null);
            }
        }

        return null;
    }
	
	public static String getBaseRoomDescription() {
        if (Main.game.getPlayerCell().getPlace().getPlaceType() == PlaceType.LILAYA_HOME_ROOM_WINDOW_GROUND_FLOOR) {
            return "<p>"
                    + "В этой комнате в одну стену встроен ряд больших окон, благодаря которым в коридор проникает большое количество естественного дневного света, когда дверь оставлена открытой."
                    + " Через эти окна можно наблюдать за суетой и шумом оживленных улиц Доминиона."
                    + "</p>";

        } else if (Main.game.getPlayerCell().getPlace().getPlaceType() == PlaceType.LILAYA_HOME_ROOM_WINDOW_FIRST_FLOOR) {
            return "<p>"
                    + "В этой комнате в одну стену встроен ряд больших окон, благодаря которым в коридор проникает большое количество естественного дневного света, когда дверь оставлена открытой."
                    + " Через эти окна можно наблюдать за суетой и шумом оживленных улиц Доминиона."
                    + "</p>";

        } else if (Main.game.getPlayerCell().getPlace().getPlaceType() == PlaceType.LILAYA_HOME_ROOM_GARDEN_GROUND_FLOOR) {
            return "<p>"
                    + "В этой комнате есть пара французских дверей, которые соединяют её с прилегающим садом во внутреннем дворе."
                    + " Через эти двери и окна рядом с ними можно наблюдать за большой частью сада."
                    + "</p>";

        } else if (Main.game.getPlayerCell().getPlace().getPlaceType() == PlaceType.LILAYA_HOME_ROOM_GARDEN_FIRST_FLOOR) {
            return "<p>"
                    + "В этой комнате на одной стене расположена серия больших окон, которые, когда дверь оставлена открытой, позволяют большому количеству естественного дневного света заливать коридор."
                    + " Через эти окна можно смотреть вниз на дворик-сад."
                    + "</p>";
        }
        return "";
    }
	
	public static String getRoomModificationsDescription(boolean includeDefaultRoomDescription) {
        GenericPlace place = Main.game.getPlayer().getLocationPlace();

        for (AbstractPlaceUpgrade pu : place.getPlaceUpgrades()) {
            DialogueNode dn = pu.getRoomDialogue(Main.game.getPlayerCell());
            if (dn != null) {
                return dn.getContent();
            }
        }

        StringBuilder roomSB = new StringBuilder();

        if (includeDefaultRoomDescription) {
            roomSB.append(getBaseRoomDescription());
        }

        for (AbstractPlaceUpgrade pu : PlaceUpgrade.getAllPlaceUpgrades()) { // For consistent ordering.
            if (place.getPlaceUpgrades().contains(pu)) {
                roomSB.append(formatRoomUpgrade(pu));
            }
        }

        return roomSB.toString();
    }
	
	public static String getRoomCharactersPresentDescription() {
        List<NPC> charactersHome = Main.game.getCharactersTreatingCellAsHome(Main.game.getPlayerCell());
        StringBuilder sb = new StringBuilder();

        if (!charactersHome.isEmpty()) {
            sb.append("<p>");
            boolean first = true;
            for (NPC npc : charactersHome) {
                if (!first) {
                    sb.append("<br/>");
                }
                sb.append("<span style='color:" + npc.getFemininity().getColour().toWebHexString() + ";'>");
                sb.append(UtilText.parse(npc, "[npc.Name]"));
                sb.append("</span>");
                sb.append(" ");

                if (Main.game.getPlayer().getFriendlyOccupants().contains(npc.getId())) { // Friendly occupant:
                    if (!Main.game.getCharactersPresent().contains(npc)) {
                        sb.append(UtilText.parse(npc,
                                "в данный момент [style.colourMinorBad(не здесь)], и, бегло осмотрев комнату в поисках каких-либо [npc.herHim] признаков , ты видишь, что на  [npc.her] прикроватной тумбочке была оставлена небольшая записка."
                                        + " Подойдя и взяв её в руки, ты читаешь:"
                                        + "</p>" +
                                        "<p style='text-align:center;'><i>"
                                        + "Привет, [pc.name]!<br/>"));
                        if (npc.hasJob()) {
                            sb.append("Я сейчас на работе, мой график работы - с " + npc.getHistory().getWorkHourStart() + ":00 по " + npc.getHistory().getWorkHourEnd() + ":00, "
                                    + npc.getHistory().getStartDay().getDisplayName(TextStyle.FULL, RUSSIAN_LOCALE) + "-" + npc.getHistory().getEndDay().getDisplayName(TextStyle.FULL, RUSSIAN_LOCALE) + "<br/>");
                        } else {
                            sb.append(UtilText.parse(npc, "Я сейчас помогаю в особняке.<br/>"));
                        }
                        sb.append(UtilText.parse(npc,
                                "- [npc.Name]"
                                        + "</i>"
                                        + "</p>"
                                        + "<p>"));
                        sb.append(UtilText.parse(npc, "<i>[npc.Name] спит между [style.time(" + npc.getSleepStartHour() + ")]-[style.time(" + npc.getSleepEndHour() + ")]</i>"));

                    } else {
                        sb.append(UtilText.parse(npc, "сейчас [style.colourMinorGood(здесь)],"));
                        if (npc.isAsleep()) {
                            sb.append(UtilText.parse(npc, " но [npc.sheIs] сейчас [style.colourSleep(спит)]..."));
                        } else {
                            sb.append(UtilText.parse(npc, " и поэтому ты можешь взаимодействовать с [npc.herHim], если захочешь..."));
                        }
                        sb.append("<br/>");
                        sb.append(UtilText.parse(npc, "<i>[npc.Name] спит между [style.time(" + npc.getSleepStartHour() + ")]-[style.time(" + npc.getSleepEndHour() + ")]</i>"));
                    }

                } else { // Slave:
                    if (!Main.game.getCharactersPresent().contains(npc)) {
                        sb.append(UtilText.parse(npc, "в данный момент [style.colourMinorBad(не здесь)],"));
                        if (npc.isAtWork()) {
                            String jobName = npc.getSlaveJob(Main.game.getHourOfDay()).getName(npc);
                            sb.append(UtilText.parse(npc, " [npc.sheIs] работает как " + UtilText.generateSingularDeterminer(jobName) + " " + jobName + " в это время."));
                        } else {
                            boolean houseFree = npc.hasSlavePermissionSetting(SlavePermissionSetting.GENERAL_HOUSE_FREEDOM);
                            boolean outsideFree = npc.hasSlavePermissionSetting(SlavePermissionSetting.GENERAL_OUTSIDE_FREEDOM);
                            if (houseFree) {
                                if (outsideFree) {
                                    sb.append(" и поэтому [npc.genderBasedWord(должен, должна)] быть либо где-то в особняке, либо в Доминионе...");
                                } else {
                                    sb.append(" и поэтому [npc.genderBasedWord(должен, должна)] быть где-то в особняке...");
                                }
                            } else if (outsideFree) {
                                sb.append(" и поэтому [npc.genderBasedWord(должен, должна)] быть где-то в Доминионе...");
                            } else {
                                sb.append(" но, скорее всего, скоро вернётся сюда...");
                            }
                        }

                    } else {
                        sb.append(UtilText.parse(npc, "colourMinorGood"));
                        if (npc.isAsleep()) {
                            sb.append(UtilText.parse(npc, " но [npc.sheIs] сейчас [style.colourSleep(спит)]..."));
                        } else {
                            sb.append(UtilText.parse(npc, " и поэтому ты можешь взаимодействовать с [npc.herHim], если захочешь..."));
                        }
                    }
                }
                first = false;
            }
            sb.append("</p>");
        }
        return sb.toString();
    }

	public static String getSlavePresentDescription(GameCharacter slave) {
        return getSlavePresentDescription(slave, "", "", "", "", "");
    }

    /**
     * Descriptions should fit into:<br/>
     * <i>'She '</i> + <code>desc</code><br/>
     * and<br/>
     * <i>'As you've instructed her to crawl, she is down on all fours, and '</i> + <code>desc</code>
     */
    public static String getSlavePresentDescription(GameCharacter slave, String minimumObedienceText, String lowObedienceText, String neutralObedienceText, String highObedienceText, String maximumObedienceText) {
        StringBuilder sb = new StringBuilder();
        sb.append("<p>");

        if (slave.getSlaveJob(Main.game.getHourOfDay()) == SlaveJob.DOLL_STATUE) {
            sb.append(UtilText.parse(slave, "Получив приказ позировать как статуя, <b style='color:" + slave.getFemininity().getColour().toWebHexString() + ";'>[npc.name]</b> присутствует в этом районе."));

            for (SlaveJobSetting sjs : slave.getSlaveJobSettings(SlaveJob.DOLL_STATUE)) {
                switch (sjs) {
                    case DOLL_STATUE_ALL_FOURS:
                        sb.append(UtilText.parse(slave, " [npc.SheIsFull] на четвереньках"));
                        break;
                    case DOLL_STATUE_ARTISTIC:
                        sb.append(UtilText.parse(slave, " [npc.SheIsFull] принимает артистическую позу"));
                        break;
                    case DOLL_STATUE_ATTENTION:
                        sb.append(UtilText.parse(slave, " [npc.SheIsFull] стоит по стойке смирно"));
                        break;
                    case DOLL_STATUE_BRIDGE:
                        sb.append(UtilText.parse(slave, " [npc.nameHasFull] наклоняется назад, чтобы выполнить мостик."));
                        break;
                    case DOLL_STATUE_MISSIONARY:
                        sb.append(UtilText.parse(slave, " [npc.SheIsFull] лежит на спине, с [npc.her] раздвинутыми [npc.morphSingleInstr([npc.legs])]"));
                        break;
                    case DOLL_STATUE_SQUATTING:
                        sb.append(UtilText.parse(slave, " [npc.SheIsFull] сидит на корточках, раздвинув колени и закинув [npc.her] [npc.morphSingleInstr([npc.hands])] за [npc.her] голову."));
                        break;
                    case DOLL_STATUE_STANDING_SPLIT:
                        sb.append(UtilText.parse(slave, " [npc.SheIsFull] стоит на одной ноге, другая [npc.her] нога поднята вертикально."));
                        break;
                    default:
                        break;
                }
            }
            sb.append(UtilText.parse(slave, ", и совершенно [npc.genderBasedWord(неподвижен, неподвижна)]."));

        } else {
            sb.append(UtilText.parse(slave, "Получив задание работать как " + (slave.getSlaveJob(Main.game.getHourOfDay()).getName(slave))
                    + ", <b style='color:" + slave.getFemininity().getColour().toWebHexString() + ";'>[npc.name]</b> присутствует в этой зоне."));

            if (slave.hasSlavePermissionSetting(SlavePermissionSetting.GENERAL_CRAWLING)) {
                sb.append(UtilText.parse(slave,
                        " Поскольку ты приказал [npc.morphSingleNameDativ([npc.sheIs])] ползти, [npc.sheIs] опускается на четвереньки, и "));
            } else {
                sb.append(UtilText.parse(slave,
                        " [npc.She] "));
            }
            switch (slave.getObedience()) {
                case NEGATIVE_FIVE_REBELLIOUS:
                case NEGATIVE_FOUR_DEFIANT:
                case NEGATIVE_THREE_STRONG_INSUBORDINATE:
                    sb.append(UtilText.parse(slave, minimumObedienceText));
                    break;
                case NEGATIVE_ONE_DISOBEDIENT:
                case NEGATIVE_TWO_UNRULY:
                    sb.append(UtilText.parse(slave, lowObedienceText));
                    break;
                case ZERO_FREE_WILLED:
                    sb.append(UtilText.parse(slave, neutralObedienceText));
                    break;
                case POSITIVE_ONE_AGREEABLE:
                case POSITIVE_TWO_OBEDIENT:
                    sb.append(UtilText.parse(slave, highObedienceText));
                    break;
                case POSITIVE_THREE_DISCIPLINED:
                case POSITIVE_FOUR_DUTIFUL:
                case POSITIVE_FIVE_SUBSERVIENT:
                    sb.append(UtilText.parse(slave, maximumObedienceText));
                    break;
            }
            sb.append("</p>");
        }

        return sb.toString();
    }

	private static String formatRoomUpgrade(AbstractPlaceUpgrade upgrade) {
        return "<p>"
                + "<b style='color:" + upgrade.getColour().toWebHexString() + ";'>" + upgrade.getName() + "</b><br/>"
                + upgrade.getRoomDescription(Main.game.getPlayerCell())
                + "</p>";
    }
	
	public static final DialogueNode OUTSIDE = new DialogueNode("", "", false) {

        @Override
        public int getSecondsPassed() {
            return DominionPlaces.TRAVEL_TIME_STREET;
        }

        @Override
        public String getLabel() {
            return "Дом Лилайи - улица";
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "OUTSIDE");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            NPC characterAnsweringDoor;
            Optional<NPC> guardAtEntrance =
                    Main.game.getCharactersPresent(WorldType.LILAYAS_HOUSE_GROUND_FLOOR, PlaceType.LILAYA_HOME_ENTRANCE_HALL).stream().filter(
                            npc -> npc.isSlave() && npc.isAtWork() && npc.hasSlaveJobSetting(SlaveJob.SECURITY, SlaveJobSetting.SECURITY_ANSWER_DOOR)).findFirst();
            if (guardAtEntrance.isPresent()) {
                characterAnsweringDoor = guardAtEntrance.get();
            } else {
                characterAnsweringDoor = Main.game.getNpc(Rose.class);
            }

            if (index == 1) {
                LocalDateTime time = Main.game.getDateNow();
                if (!Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.daddyFound)
                        && !Main.game.getPlayer().getFetishDesire(Fetish.FETISH_INCEST).isNegative()
                        && Main.game.getPlayer().isQuestProgressGreaterThan(QuestLine.MAIN, Quest.MAIN_2_D_MEETING_A_LILIN) // Only trigger after having met Lyssieth
                        && Main.game.isExtendedWorkTime()
                        && time.getMonth().equals(Month.JUNE) && time.getDayOfMonth() >= 14 && time.getDayOfMonth() <= 21) { // Father's day timing, 3rd week of June
                    return new Response("Вход", UtilText.parse(characterAnsweringDoor, "Постучись в дверь и дождись, пока [npc.name] впустит тебя."), DaddyDialogue.FIRST_ENCOUNTER) {
                        @Override
                        public void effects() {
                            Main.game.getNpc(Daddy.class).setLocation(Main.game.getPlayer(), false);
                            Main.game.getDialogueFlags().setFlag(DialogueFlagValue.daddyFound, true);
                        }
                    };

                } else if (Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.daddySendingReward)) {
                    return new Response("Вход", UtilText.parse(characterAnsweringDoor, "Постучись в дверь и дождись, пока Роза впустит тебя."), DADDY_PACKAGE) {
                        @Override
                        public void effects() {
                            Main.game.getDialogueFlags().setFlag(DialogueFlagValue.daddySendingReward, false);

                            if (characterAnsweringDoor.equals(Main.game.getNpc(Rose.class))) {
                                Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "OUTSIDE_DADDY_PACKAGE"));
                            } else {
                                Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "OUTSIDE_DADDY_PACKAGE_SECURITY", characterAnsweringDoor));
                            }

                            Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_GROUND_FLOOR, PlaceType.LILAYA_HOME_ENTRANCE_HALL, false);

                            Main.game.getTextStartStringBuilder().append(Main.game.getPlayer().addItem(Main.game.getItemGen().generateItem(ItemType.getSpellBookType(Spell.TELEKENETIC_SHOWER)), false, true));
                            Main.game.getTextStartStringBuilder().append(Main.game.getPlayer().addItem(Main.game.getItemGen().generateItem(ItemType.getSpellScrollType(SpellSchool.EARTH)), 5, false, true));
                        }
                    };

                } else {
                    return new Response("Вход", UtilText.parse(characterAnsweringDoor, "Постучись в дверь и дождись, пока Роза впустит тебя."), PlaceType.LILAYA_HOME_ENTRANCE_HALL.getDialogue(false)) {
                        @Override
                        public void effects() {
                            if (characterAnsweringDoor.equals(Main.game.getNpc(Rose.class))) {
                                Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "OUTSIDE_KNOCK_ON_DOOR"));
                            } else {
                                Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "OUTSIDE_KNOCK_ON_DOOR_SECURITY", characterAnsweringDoor));
                            }
                            Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_GROUND_FLOOR, PlaceType.LILAYA_HOME_ENTRANCE_HALL, false);
                        }
                    };
                }

            } else {
                return null;
            }
        }
    };

	public static final DialogueNode DADDY_PACKAGE = new DialogueNode("Вестибюль", "", true) {

        @Override
        public int getSecondsPassed() {
            return 60;
        }

        @Override
        public String getContent() {
            return "";
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Продолжить", "Продолжи путь к дому Лилайи.", ENTRANCE_HALL);
            }
            return null;
        }
    };
	
	public static final DialogueNode CORRIDOR = new DialogueNode("Коридор", ".", false) {

        @Override
        public int getSecondsPassed() {
            return 10;
        }

        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);
            List<NPC> charactersPresent = getSlavesAndOccupantsPresent();

            UtilText.nodeContentSB.append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "CORRIDOR"));

            if (charactersPresent.isEmpty()) {
                UtilText.nodeContentSB.append("<p>"
                        + "Этот коридор сейчас пустынен, и, похоже, здесь нечем заняться."
                        + "</p>");
            } else {
                for (NPC slave : charactersPresent) {
                    SlaveJob job = slave.getSlaveJob(Main.game.getHourOfDay());
                    if (job == SlaveJob.CLEANING) {
                        UtilText.nodeContentSB.append(getSlavePresentDescription(slave,
                                "даже не пытается притвориться, что [npc.sheIs] убирается.",
                                "полушутя-полусерьезно чистит ковер.",
                                "вытирает пыль с плинтусов.",
                                "деловито полирует доски пола.",
                                "покорно вытирает пыль, полирует и чистит всё вокруг."));

                    } else if (job == SlaveJob.SECURITY) {
                        UtilText.nodeContentSB.append(getSlavePresentDescription(slave,
                                "даже не пытается притвориться, что [npc.sheIs] заботится о безопасности.",
                                "полусерьезно наблюдает за безопасностью.",
                                "следит за любым признаком опасности.",
                                "бдительно следит за любым признаком опасности.",
                                "очень бдительно и внимательно следит за любым признаком опасности."));
                    } else {
                        UtilText.nodeContentSB.append(getSlavePresentDescription(slave));
                    }
                }
            }

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (responseTab == 1) {
                return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
            }
            if (responseTab == 2) {
                return getLilayasHouseDollStationResponses(index);
            }

            List<NPC> charactersPresent = getSlavesAndOccupantsPresent();

            if (index == 0) {
                return null;

            } else if (index - 1 < charactersPresent.size()) {
                GameCharacter slave = charactersPresent.get(index - 1);
                return interactWithNPC(slave);
            } else {
                return null;
            }
        }
    };
	
	public static final DialogueNode ROOM_WINDOW = new DialogueNode("Комната", ".", false) {
        @Override
        public int getSecondsPassed() {
            return 10;
        }

        @Override
        public String getLabel() {
            return Main.game.getPlayer().getLocationPlace().getName();
        }

        @Override
        public String getContent() {
            return getRoomModificationsDescription(true)
                    + getRoomCharactersPresentDescription();
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getRoomResponse(responseTab, index);
        }
    };
	
	public static final DialogueNode ROOM_GARDEN_GROUND_FLOOR = new DialogueNode("Комната с видом на сад", ".", false) {
        @Override
        public int getSecondsPassed() {
            return 10;
        }

        @Override
        public String getLabel() {
            return Main.game.getPlayer().getLocationPlace().getName();
        }

        @Override
        public String getContent() {
            return getRoomModificationsDescription(true)
                    + getRoomCharactersPresentDescription();
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getRoomResponse(responseTab, index);
        }
    };
	
	public static final DialogueNode ROOM_GARDEN = new DialogueNode("Комната с видом на сад", ".", false) {
        @Override
        public int getSecondsPassed() {
            return 10;
        }

        @Override
        public String getLabel() {
            return Main.game.getPlayer().getLocationPlace().getName();
        }

        @Override
        public String getContent() {
            return getRoomModificationsDescription(true)
                    + getRoomCharactersPresentDescription();
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getRoomResponse(responseTab, index);
        }
    };
	
	public static final DialogueNode DUNGEON_CELL = new DialogueNode("Камера в подземелье", ".", false) {
        @Override
        public int getSecondsPassed() {
            return 10;
        }

        @Override
        public String getLabel() {
            return Main.game.getPlayer().getLocationPlace().getName();
        }

        @Override
        public String getContent() {
            return getRoomModificationsDescription(true);
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            return getRoomResponse(responseTab, index);
        }
    };
	
	public static final DialogueNode BIRTHING_ROOM = new DialogueNode("Родильная комната", ".", false) {

        @Override
        public int getSecondsPassed() {
            return 10;
        }

        @Override
        public String getContent() {
            return "<p>"
                    + "Лилайя переоборудовала эту комнату, чтобы сделать её подходящим местом для родов."
                    + "</p>"
                    + "<p>"
                    + "Вместо коврового покрытия, как в большинстве других комнат этого дома, для пола этой комнаты была выбрана чистая белая плитка."
                    + " У одной из стен комнаты стоит удивительно современно выглядящая кровать для родов, но, кроме неё, здесь больше нет ничего из медицинского оборудования."
                    + " По краям комнаты расставлено несколько удобных кресел, но кроме них и шкафа с напитками, стоящего в одном из углов, в комнате нет никакой другой мебели."
                    + "</p>"
                    + "<p>"
                    + "В доме любого другого человека комната, предназначенная для принятия родов, могла бы шокировать, но Лилайя, похоже, занимается самыми разными странными вещами,"
                    + " так что ты отмахиваешься от этого как от ещё одной особенности этого мира."
                    + "</p>";
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (responseTab == 1) {
                return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
            }
            return null;
        }
    };
	
	public static final DialogueNode KITCHEN = new DialogueNode("Кухня", ".", false) {

        @Override
        public int getSecondsPassed() {
            return 10;
        }

        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);
            List<NPC> charactersPresent = getSlavesAndOccupantsPresent();

            UtilText.nodeContentSB.append("<p>"
                    + "Как и все остальные комнаты в доме Лилайи, кухня намного больше, чем все те, в какие ты когда-либо [pc.genderBasedWord(видел, видела)]."
                    + " По краям комнаты выстроились ряды деревянных шкафов, увенчанных полированным гранитом, а в центре - пара длинных отдельно стоящих столешниц."
                    + " Трио чугунных духовок в сочетании с дубовым полом в деревенском стиле и отсутствием современной техники придают кухне винтажный вид."
                    + "</p>"
                    + "<p>"
                    + "В одной из сторон комнаты есть открытый дверной проем, заглянув в который, ты видишь ряд холодильников, морозильных камер и буфетов."
                    + " Ингредиенты и продукты питания всех форм и размеров лежат на открытых полках, и ты удивляешься количеству и разнообразию запасов, которые хранятся на складе."
                    + "</p>");

            if (charactersPresent.isEmpty()) {
                UtilText.nodeContentSB.append("<p>"
                        + "На кухне сейчас пустынно, и, похоже, здесь нечем заняться."
                        + "</p>");
            } else {
                for (NPC slave : charactersPresent) {
                    if (slave.getSlaveJob(Main.game.getHourOfDay()) == SlaveJob.KITCHEN) {
                        UtilText.nodeContentSB.append(getSlavePresentDescription(slave,
                                "явно не занимается приготовлением пищи. Что ещё хуже, [npc.she], похоже, не заботится о том, что ты наблюдаешь за [npc.herHim] действиями, и поворачивается к тебе спиной.",
                                "на другом конце кухни в данный момент неспешно готовит еду.",
                                "занимается тем, что готовит что-то в одной из кухонных печей.",
                                "в данный момент готовит еду. Видно, что [npc.sheIs] прилагает много усилий, чтобы обеспечить хорошее выполнение работы.",
                                "послушно готовит Лилайе еду. Ты замечаешь, что [npc.sheIs] заботится о том, чтобы приготовить все так, как нравится твоей демонической [pc.morphSingleDativ([lilaya.relation(pc)])]."));
                    } else {
                        UtilText.nodeContentSB.append(getSlavePresentDescription(slave));
                    }
                }
            }

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (responseTab == 1) {
                return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
            }
            List<NPC> charactersPresent = getSlavesAndOccupantsPresent();

            if (index == 0) {
                return null;

            } else if (index - 1 < charactersPresent.size()) {
                GameCharacter slave = charactersPresent.get(index - 1);
                return interactWithNPC(slave);
            } else {
                return null;
            }
        }
    };
	
	public static final DialogueNode ROOM_ROSE = new DialogueNode("Комната Розы", ".", false) {

        @Override
        public int getSecondsPassed() {
            return 10;
        }

        @Override
		public String getContent() {
			if(!Main.game.isExtendedWorkTime()
					&& Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.dressingRoomLyssiethsWardrobeActivated)
					&& (!Main.game.getDialogueFlags().hasSavedLong("innoxia_lilaya_kitty_time_seen")
							|| (Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("innoxia_lilaya_kitty_time_seen") > 60*60*24*7))) {
				return UtilText.parseFromXMLFile("places/dominion/lilayasHome/room_rose", "ROOM_ROSE_KITTY");
			}
			return UtilText.parseFromXMLFile("places/dominion/lilayasHome/room_rose", "ROOM_ROSE");
		}

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (responseTab == 1) {
                return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
            }
            if (index == 1) {
                if (!Main.game.isExtendedWorkTime()) {
                    return new Response("Призвать Розу", "На табличке, висящей рядом с дверью Розы, прямо написано, что её нельзя беспокоить, поэтому, если хочешь поговорить с ней о чём-то, лучше вернуться в дневное время.", null);
                }

                return new Response("Призвать Розу", "Рабыня Лилайя, Роза, всегда рядом. Стоит позвонить в маленький колокольчик у двери её спальни, и она обязательно прибежит.", AUNT_HOME_ROSE) {
                    @Override
                    public void effects() {
                        roseContent = UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "ROOM_ROSE_INITIAL_CALL");

                        Main.game.getDialogueFlags().values.remove(DialogueFlagValue.auntHomeJustEntered);
                        Main.game.getNpc(Rose.class).setLocation(Main.game.getActiveWorld().getWorldType(), Main.game.getPlayer().getLocation(), false);
                    }
                };

            }

            if(index==2
					&& !Main.game.isExtendedWorkTime()
					&& Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.dressingRoomLyssiethsWardrobeActivated)
					&& (!Main.game.getDialogueFlags().hasSavedLong("innoxia_lilaya_kitty_time_seen")
							|| (Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("innoxia_lilaya_kitty_time_seen") > 60*60*24*7))) {
				return new Response("Keyhole",
						"Look through the keyhole to see if you can identify the source of the loud meowing.",
						DialogueManager.getDialogueFromId("innoxia_places_dominion_lilayas_home_room_rose_lilaya_kitty")) {
					@Override
					public void effects() {
						Main.game.getDialogueFlags().setSavedLong("innoxia_lilaya_kitty_time_seen", Main.game.getSecondsPassed());
					}
				};
			}

			return null;
        }
    };
	
	private static String roseContent = "";
	private static boolean giftedRose = false;
	public static final DialogueNode AUNT_HOME_ROSE = new DialogueNode("", "", true) {
        @Override
        public String getContent() {
            return roseContent;
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new Response("Лилайя", "Спросить Розу о её хозяйке, Лилайе.", AUNT_HOME_ROSE) {
                    @Override
                    public void effects() {
                        roseContent = UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "ROSE_TALK_LILAYA");
                    }
                };

            } else if (index == 2) {
                return new Response("Рабство", "Спросить Розу, как она стала рабыней.", AUNT_HOME_ROSE) {
                    @Override
                    public void effects() {
                        roseContent = UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "ROSE_TALK_SLAVE");
                    }
                };

            } else if (index == 3) {
                return new Response("Мир", "Попросить Розу рассказать что-нибудь об этом мире.", AUNT_HOME_ROSE) {
                    @Override
                    public void effects() {
                        roseContent = UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "ROSE_TALK_WORLD");
                    }
                };

            } else if (index == 4) {
                return new Response("Обязанности", "Спросить Розу о том, какие обязанности она должна выполнять.", AUNT_HOME_ROSE) {
                    @Override
                    public void effects() {
                        roseContent = UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "ROSE_TALK_DUTIES");
                    }
                };

            } else if (index == 5) {
                if (Main.game.getPlayer().hasClothingType(ClothingType.getClothingTypeFromId("innoxia_hair_rose"), false) && !giftedRose) {
                    return new Response("Предложить розу", "Предложить Розе розу, которая есть у тебя в инвентаре.", AUNT_HOME_ROSE) {
                        @Override
                        public void effects() {
                            roseContent = UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "ROSE_TALK_OFFER_ROSE");
                            Main.game.getPlayer().removeClothingByType(ClothingType.getClothingTypeFromId("innoxia_hair_rose"));
                            giftedRose = true;
                        }
                    };

                } else if (giftedRose) {
                    return new Response("Руки Розы", "Ты никогда раньше не [pc.genderBasedWord(замечал, замечала)], какие у Розы потрясающие руки...", ROSE_HANDS) {
                        @Override
                        public boolean isSexHighlight() {
                            return true;
                        }

                        @Override
                        public void effects() {
                            giftedRose = false;
                        }
                    };

                } else {
                    return new Response("Предложить розу", "У тебя нет розы, чтобы предложить её Розе.", null);
                }

            } else if (index == 6
                    && (Main.game.getNpc(Lilaya.class).isPregnant() && Main.game.getNpc(Lilaya.class).isCharacterReactedToPregnancy(Main.game.getPlayer()))) {

                if (Main.game.getPlayer().hasItemType(ItemType.MOTHERS_MILK)) {
                    return new Response("Материнское молоко", "Дай Розе одно материнское молоко из своего инвентаря и попроси её дать его Лилайе, чтобы её беременность быстрее закончилась.", AUNT_HOME_ROSE) {
                        @Override
                        public void effects() {
                            giftedRose = false;
                            roseContent = UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "ROSE_TALK_MOTHERS_MILK");
                            Main.game.getPlayer().removeItemByType(ItemType.MOTHERS_MILK);
                            Main.game.getNpc(Lilaya.class).useItem(Main.game.getItemGen().generateItem(ItemType.MOTHERS_MILK), Main.game.getNpc(Lilaya.class), false);
                        }
                    };

                } else {
                    return new Response("Материнское молоко", "Если у тебя в инвентаре есть «Материнское молоко», ты можешь попросить Розу дать его Лилайе, чтобы её беременность быстрее закончилась.", null);
                }

            } else if (index == 0) {
                return new Response("Отпустить", "Позволить Розе вернуться к своей работе.", ROOM_ROSE) {
                    @Override
                    public void effects() {
                        giftedRose = false;
                        Main.game.getNpc(Rose.class).setLocation(WorldType.LILAYAS_HOUSE_GROUND_FLOOR, PlaceType.LILAYA_HOME_LAB, false);
                    }

                    @Override
                    public DialogueNode getNextDialogue() {
                        return Main.game.getActiveWorld().getCell(Main.game.getPlayer().getLocation()).getDialogue(true);
                    }
                };

            } else {
                return null;
            }
        }
    };

	public static final DialogueNode ROSE_HANDS = new DialogueNode("", "", true) {

        @Override
        public String getLabel() {
            return "Руки Розы";
        }

        @Override
        public String getContent() {
            return UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "ROSE_HANDS");
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (index == 1) {
                return new ResponseSex("Держание за ручки", "Предупреждение: Этот контент содержит экстремальные описания держания за руки, сосания пальцев и даже облизывания ладоней."
                        + " <b>Пожалуйста, помните, что перед началом игры вам необходимо прочитать отказ от ответственности!</b> <b style='color:" + BaseColour.CRIMSON.toWebHexString() + ";'>Только для 18+!</b>",
                        true, false,
                        new SMRoseHands(
                                Util.newHashMapOfValues(new Value<>(Main.game.getPlayer(), SexSlotUnique.HAND_SEX_DOM_ROSE)),
                                Util.newHashMapOfValues(new Value<>(Main.game.getNpc(Rose.class), SexSlotUnique.HAND_SEX_SUB_ROSE))),
                        null,
                        null,
                        END_HAND_SEX);

            } else {
                return null;
            }
        }

        @Override
        public boolean isInventoryDisabled() {
            return true;
        }
    };

    public static final DialogueNode END_HAND_SEX = new DialogueNode("Восстановиться", "И ты, и Роза устали после того, как держались за руки.", true) {
		@Override
		public String getContent() {
			return "<p>"
						+ "Роза, пошатываясь, подходит и поднимает свою маленькую метелку из перьев, бросая страстный взгляд в твою сторону, прежде чем прикусить губу и поспешить в другую часть дома, без сомнения, чтобы прийти в себя после вашего экстремального держания за руки."
					+ "</p>"
					+ "<p>"
						+ "С измученным вздохом ты падаешь на кровать в комнате, и твои мысли сосредотачиваются на том удивительном переживании, которое ты только что [pc.genderBasedWord(пережил, пережила)]."
					+ "</p>";
		}

		@Override
		public Response getResponse(int responseTab, int index) {
			if (index == 1) {
				return new Response("Продолжить", "Ты наконец-то пришёл в себя после напряженного держания за руки с Розой.", RoomPlayer.ROOM){
					@Override
					public void effects() {
						Main.game.getNpc(Rose.class).setLocation(WorldType.LILAYAS_HOUSE_GROUND_FLOOR, PlaceType.LILAYA_HOME_LAB, false);
					}

					@Override
					public DialogueNode getNextDialogue() {
						return Main.game.getActiveWorld().getCell(Main.game.getPlayer().getLocation()).getDialogue(true);
					}
				};
			} else {
				return null;
			}
		}
	};
	
	public static final DialogueNode GARDEN = new DialogueNode("Садовый дворик", "", false) {
        @Override
        public int getSecondsPassed() {
            return 10;
        }

        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);
            List<NPC> charactersPresent = getSlavesAndOccupantsPresent();

            UtilText.nodeContentSB.append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "GARDEN"));

            if (!charactersPresent.isEmpty()) {
                for (NPC slave : charactersPresent) {
                    if (slave.getSlaveJob(Main.game.getHourOfDay()) == SlaveJob.GARDEN) {
                        UtilText.nodeContentSB.append(getSlavePresentDescription(slave,
                                "явно не занимается садоводством. Что ещё хуже, [npc.she], похоже, не заботится о том, что ты наблюдаешь за [npc.herHim] действиями, и поворачивает к тебе спиной.",
                                "в настоящее время неумело подстригает живую изгородь.",
                                "занимается прополкой одной из многочисленных дорожек, которые вьются по саду.",
                                "в данный момент подстригает один из розовых кустов. Видно, что [npc.sheIs] прилагает много усилий, чтобы убедиться, что [npc.sheIs] хорошо справляется со своей работой.",
                                "послушно сажает луковицы и вырывает сорняки. Заметно, что [npc.sheIs] заботится о том, чтобы посадить каждую луковицу в правильное место."));
                    } else {
                        UtilText.nodeContentSB.append(getSlavePresentDescription(slave));
                    }
                }
            }

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (responseTab == 1) {
                return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
            }
            if (responseTab == 2) {
                return getLilayasHouseDollStationResponses(index);
            }
            List<NPC> charactersPresent = getSlavesAndOccupantsPresent();

            if (index == 0) {
                return null;

            } else if (index == 1) {
                if (Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.lilayaGardenPickRose)) {
                    return new Response("Сорвать розу", "Ты уже [pc.genderBasedWord(сорвал, сорвал)] сегодня розу и не [pc.genderBasedWord(хотел, хотела)] бы расстраивать Лилайю, взяв слишком много...", null);
                } else {
                    return new Response("Сорвать розу", "Сорвать цветок с одного из великолепных розовых кустов.", GARDEN) {
                        @Override
                        public void effects() {
                            Main.game.getDialogueFlags().setFlag(DialogueFlagValue.lilayaGardenPickRose, true);
                            Main.game.getTextStartStringBuilder().append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "GARDEN_PICK ROSE"));
                            Main.game.getTextEndStringBuilder().append(Main.game.getPlayer().addClothing(Main.game.getItemGen().generateClothing("innoxia_hair_rose", false), false));
                        }

                        public boolean isStripContent() {
                            return true;
                        }
                    };
                }

            } else if (index - 2 < charactersPresent.size()) {
                GameCharacter slave = charactersPresent.get(index - 2);
                return new Response(UtilText.parse(slave, "[npc.Name]"), UtilText.parse(slave, "Взаимодействовать с [npc.morphSingleNameInstr([npc.name])].\""), SlaveDialogue.SLAVE_START) {
                    @Override
                    public Colour getHighlightColour() {
                        return slave.getFemininity().getColour();
                    }

                    @Override
                    public void effects() {
                        SlaveDialogue.initDialogue((NPC) slave, false);
                    }
                };
            }

            return null;
        }
    };
	
	public static final DialogueNode FOUNTAIN = new DialogueNode("Водяной фонтан", ".", false) {

        @Override
        public int getSecondsPassed() {
            return 10;
        }

        @Override
        public String getContent() {
            UtilText.nodeContentSB.setLength(0);
            List<NPC> charactersPresent = getSlavesAndOccupantsPresent();

            UtilText.nodeContentSB.append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "FOUNTAIN"));

            if (!charactersPresent.isEmpty()) {
                for (NPC slave : charactersPresent) {
                    if (slave.getSlaveJob(Main.game.getHourOfDay()) == SlaveJob.GARDEN) {
                        UtilText.nodeContentSB.append(getSlavePresentDescription(slave,
                                "явно не занимается садоводством. Что ещё хуже, [npc.she], похоже, не заботится о том, что ты наблюдаешь за [npc.herHim] действиями, и поворачивает к тебе спиной.",
                                "в настоящее время неумело подстригает живую изгородь.",
                                "занимается прополкой одной из многочисленных дорожек, которые вьются по саду.",
                                "в данный момент подстригает один из розовых кустов. Видно, что [npc.sheIs] прилагает много усилий, чтобы убедиться, что [npc.sheIs] хорошо справляется со своей работой.",
                                "послушно сажает луковицы и вырывает сорняки. Заметно, что [npc.sheIs] заботится о том, чтобы посадить каждую луковицу в правильное место."));
                    } else {
                        UtilText.nodeContentSB.append(getSlavePresentDescription(slave));
                    }
                }
            }

            return UtilText.nodeContentSB.toString();
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (responseTab == 1) {
                return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
            }
            if (responseTab == 2) {
                return getLilayasHouseDollStationResponses(index);
            }
            if (index == 1 && Main.game.getDialogueFlags().hasFlag(DialogueFlagValue.getDialogueFlagValueFromId("acexp_dungeon_garden_access_found"))) {
                return new Response("Подземелье Лилайи",
                        "Нажать на замаскированную кнопку, чтобы открыть секретный проход в подземелье Лилайи.",
                        DialogueManager.getDialogueFromId("acexp_dominion_lilaya_dungeon_stairsUp_garden")) {
                    @Override
                    public void effects() {
                        Main.game.appendToTextStartStringBuilder(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "DUNGEON_OPENS_FOUNTAIN"));
                        Main.game.appendToTextStartStringBuilder(UtilText.parseFromXMLFile("acexp/dominion/lilaya_dungeon", "DUNGEON_ENTRY"));
                        Main.game.getPlayer().setLocation(WorldType.getWorldTypeFromId("acexp_dungeon"), PlaceType.getPlaceTypeFromId("acexp_dungeon_stairs_garden"), false);
                    }
                };
            }
            return null;
        }
    };
	
	public static final DialogueNode ENTRANCE_HALL = new DialogueNode("Вестибюль", ".", false) {
        private boolean fiammettaMessage = false;

        @Override
        public void applyPreParsingEffects() {
            fiammettaMessage = Main.game.getPlayer().getQuest(QuestLine.SIDE_DOLL_FACTORY) == Quest.DOLL_FACTORY_6
                    && Main.game.isDayTime()
                    && Main.game.getDialogueFlags().hasSavedLong("fia_factory_finished")
                    && (Main.game.getSecondsPassed() - Main.game.getDialogueFlags().getSavedLong("fia_factory_finished") > (2 * 24 * 60 * 60)); // 2 days

            if (fiammettaMessage) {
                Main.game.getNpc(Rose.class).setLocation(Main.game.getPlayer());
            }
        }

        @Override
        public int getSecondsPassed() {
            if (fiammettaMessage) {
                return 60;
            }
            return 10;
        }

        @Override
        public boolean isTravelDisabled() {
            return fiammettaMessage;
        }

        @Override
        public String getContent() {
            StringBuilder sb = new StringBuilder();

            List<NPC> charactersPresent = getSlavesAndOccupantsPresent();

            if (fiammettaMessage) {
                sb.append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/doll_quest", "ENTRANCE_HALL_FIAMMETTA"));

                return sb.toString();
            }

            sb.append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "ENTRANCE_HALL"));

            if (!charactersPresent.isEmpty()) {
                for (NPC slave : charactersPresent) {
                    if (slave.getSlaveJob(Main.game.getHourOfDay()) == SlaveJob.SECURITY) {
                        sb.append(getSlavePresentDescription(slave,
                                "даже не удосуживается притвориться, что [npc.sheIs] заботится о безопасности.",
                                "полусерьезно высматривает опасность.",
                                "следит за любым признаком опасности.",
                                "бдительно и внимательно следит за любыми признаками опасности.",
                                "очень бдительно и внимательно следит за любым признаком опасности."));
                    } else {
                        sb.append(getSlavePresentDescription(slave));
                    }
                }
            }

            return sb.toString();
        }

        @Override
        public String getResponseTabTitle(int index) {
            if (fiammettaMessage) {
                return null;
            }
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (fiammettaMessage) {
                if (index == 1) {
                    return new Response("Следовать за Розой", "Следуй за Розой в библиотеку, чтобы узнать, кто этот гость.", DialogueManager.getDialogueFromId("innoxia_places_dominion_lilayas_home_doll_quest_start"));
                }
                return null;
            }

            if (responseTab == 1) {
                return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
            }
            if (responseTab == 2) {
                return getLilayasHouseDollStationResponses(index);
            }

            List<NPC> charactersPresent = getSlavesAndOccupantsPresent();

            if (index == 0) {
                return null;

            } else if (index == 1) {
                return new Response("Выход", "Покинуть дом Лилайи.", PlaceType.DOMINION_AUNTS_HOME.getDialogue(false)) {
                    @Override
                    public void effects() {
                        Main.game.getPlayer().setLocation(WorldType.DOMINION, PlaceType.DOMINION_AUNTS_HOME, false);
                    }
                };

            } else if (index - 2 < charactersPresent.size()) {
                GameCharacter slave = charactersPresent.get(index - 2);
                return new Response(UtilText.parse(slave, "[npc.Name]"), UtilText.parse(slave, "Взаимодействовать с [npc.morphSingleNameInstr([npc.name])]."), SlaveDialogue.SLAVE_START) {
                    @Override
                    public Colour getHighlightColour() {
                        return slave.getFemininity().getColour();
                    }

                    @Override
                    public void effects() {
                        SlaveDialogue.initDialogue((NPC) slave, false);
                    }
                };

            }
            return null;
        }
    };
	
	public static final DialogueNode STAIRCASE_UP = new DialogueNode("Лестница вверх", ".", false) {
        @Override
        public int getSecondsPassed() {
            return 20;
        }

        @Override
        public String getContent() {
            StringBuilder sb = new StringBuilder();
            sb.append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "STAIRCASE_UP"));
            for (NPC slave : getSlavesAndOccupantsPresent()) {
                sb.append(getSlavePresentDescription(slave));
            }
            return sb.toString();
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (responseTab == 1) {
                return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
            }
            if (responseTab == 2) {
                return getLilayasHouseDollStationResponses(index);
            }

            List<NPC> charactersPresent = getSlavesAndOccupantsPresent();
            if (index == 0) {
                return null;

            } else if (index == 1) {
                return new Response("Наверх", "Подняться по лестнице на второй этаж.", PlaceType.LILAYA_HOME_STAIR_DOWN.getDialogue(false)) {
                    @Override
                    public void effects() {
                        Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_FIRST_FLOOR, PlaceType.LILAYA_HOME_STAIR_DOWN, false);
                    }
                };

            } else if (index - 2 < charactersPresent.size()) {
                GameCharacter slave = charactersPresent.get(index - 2);
                return interactWithNPC(slave);
            }

            return null;
        }
    };
	
	public static final DialogueNode STAIRCASE_UP_SECONDARY = new DialogueNode("Лестница вверх", ".", false) {
        @Override
        public int getSecondsPassed() {
            return 20;
        }

        @Override
        public String getContent() {
            StringBuilder sb = new StringBuilder();
            sb.append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "CORRIDOR"));
            sb.append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "STAIRCASE_UP_SECONDARY"));
            for (NPC slave : getSlavesAndOccupantsPresent()) {
                sb.append(getSlavePresentDescription(slave));
            }
            return sb.toString();
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (responseTab == 1) {
                return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
            }
            if (responseTab == 2) {
                return getLilayasHouseDollStationResponses(index);
            }

            List<NPC> charactersPresent = getSlavesAndOccupantsPresent();
            if (index == 0) {
                return null;

            } else if (index == 1) {
                return new Response("Наверх", "Подняться по лестнице на второй этаж.", STAIRCASE_DOWN_SECONDARY) {
                    @Override
                    public void effects() {
                        Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_FIRST_FLOOR, Main.game.getPlayer().getLocation(), false);
                    }
                };
            } else if (index - 2 < charactersPresent.size()) {
                GameCharacter slave = charactersPresent.get(index - 2);
                return interactWithNPC(slave);
            }

            return null;
        }
    };
	
	public static final DialogueNode STAIRCASE_DOWN = new DialogueNode("Лестница вниз", ".", false) {
        @Override
        public int getSecondsPassed() {
            return 20;
        }

        @Override
        public String getContent() {
            StringBuilder sb = new StringBuilder();
            sb.append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "STAIRCASE_DOWN"));
            for (NPC slave : getSlavesAndOccupantsPresent()) {
                sb.append(getSlavePresentDescription(slave));
            }
            return sb.toString();
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (responseTab == 1) {
                return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
            }
            if (responseTab == 2) {
                return getLilayasHouseDollStationResponses(index);
            }

            List<NPC> charactersPresent = getSlavesAndOccupantsPresent();
            if (index == 0) {
                return null;

            } else if (index == 1) {
                return new Response("Вниз", "Спуститься вниз, на первый этаж.", PlaceType.LILAYA_HOME_STAIR_UP.getDialogue(false)) {
                    @Override
                    public void effects() {
                        Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_GROUND_FLOOR, PlaceType.LILAYA_HOME_STAIR_UP, false);
                    }
                };
            } else if (index - 2 < charactersPresent.size()) {
                GameCharacter slave = charactersPresent.get(index - 2);
                return interactWithNPC(slave);
            }

            return null;
        }
    };
	
	public static final DialogueNode STAIRCASE_DOWN_SECONDARY = new DialogueNode("Лестница вниз", ".", false) {
        @Override
        public int getSecondsPassed() {
            return 20;
        }

        @Override
        public String getContent() {
            StringBuilder sb = new StringBuilder();
            sb.append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "CORRIDOR"));
            sb.append(UtilText.parseFromXMLFile("places/dominion/lilayasHome/generic", "STAIRCASE_DOWN_SECONDARY"));
            for (NPC slave : getSlavesAndOccupantsPresent()) {
                sb.append(getSlavePresentDescription(slave));
            }
            return sb.toString();
        }

        @Override
        public String getResponseTabTitle(int index) {
            return LilayaHomeGeneric.getLilayasHouseStandardResponseTabs(index);
        }

        @Override
        public Response getResponse(int responseTab, int index) {
            if (responseTab == 1) {
                return LilayaHomeGeneric.getLilayasHouseFastTravelResponses(index);
            }
            if (responseTab == 2) {
                return getLilayasHouseDollStationResponses(index);
            }

            List<NPC> charactersPresent = getSlavesAndOccupantsPresent();
            if (index == 0) {
                return null;

            } else if (index == 1) {
                return new Response("Вниз", "Спуститься вниз, на первый этаж.", STAIRCASE_UP_SECONDARY) {
                    @Override
                    public void effects() {
                        Main.game.getPlayer().setLocation(WorldType.LILAYAS_HOUSE_GROUND_FLOOR, Main.game.getPlayer().getLocation(), false);
                    }
                };
            } else if (index - 2 < charactersPresent.size()) {
                GameCharacter slave = charactersPresent.get(index - 2);
                return interactWithNPC(slave);
            }

            return null;
        }
    };
}
