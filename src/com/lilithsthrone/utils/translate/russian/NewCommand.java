package com.lilithsthrone.utils.translate.russian;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.dialogue.utils.ParserCommand;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import com.lilithsthrone.utils.Util;
import ru.shuvaev.morpher.tools.enams.Case;
import ru.shuvaev.morpher.tools.enams.Numeration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;

import static com.lilithsthrone.utils.translate.russian.Morpher.convertGender;

public class NewCommand {

    public static void fillRussianCommand(BooleanSupplier isCapitalise) {
        //Позволяет подставлять слова, в зависимости от фемминости персонажа
        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues(
                        "genderBasedWord"),
                true,
                false,
                "(maleWord, femaleWord)",
                "Returns a word depending on the character's femininity.") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                Gender gender = character.getGender();
                return Optional.ofNullable(arguments)
                        .map(string -> string.split(","))
                        .map(strings -> Arrays.stream(strings).map(String::strip).distinct().collect(Collectors.toList()))
                        .map(args -> {
                            if (args.isEmpty()) {
                                return null;
                            }
                            if (args.size() != 2 || !gender.isFeminine()) {
                                return args.get(0);
                            }
                            return args.get(1);
                        })
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        //Позволяет подставлять слова, в зависимости от фемминости персонажа
        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues(
                        "genderBasedWordAuto"),
                true,
                false,
                "(maleWord, femaleWord)",
                "Returns a word depending on the character's femininity.") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                Gender gender = character.getGender();
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphGender(string, convertGender(gender), Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        })
                        .orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("targetBasedWord"),
                true,
                false,
                "(youWord, heSheWord)",
                "Returns a word depending on the character's target.") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> string.split(","))
                        .map(strings -> Arrays.stream(strings).map(String::strip).distinct().collect(Collectors.toList()))
                        .map(args -> {
                            if (args.isEmpty()) {
                                return null;
                            }
                            if (args.size() != 2 || (character != null && character.isPlayer())) {
                                return args.get(0);
                            }
                            return args.get(1);
                        })
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        })
                        .orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphPluralGent"),
                true,
                false,
                "(word)",
                "Слово во множественном числе, родитльского падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphNoun(string, Case.GENITIVUS, Numeration.PLURAL))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphPluralDativ"),
                true,
                false,
                "(word)",
                "Слово во множественном числе, дательного падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphNoun(string, Case.DATIVUS, Numeration.PLURAL))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphPluralInstr"),
                true,
                false,
                "(word)",
                "Слово во множественном числе, творительного падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphNoun(string, Case.INSTRUMENTALIS, Numeration.PLURAL))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphPluralPreap"),
                true,
                false,
                "(word)",
                "Слово во множественном числе, предложного падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphNoun(string, Case.PRAEPOSITIONALIS, Numeration.PLURAL))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphSingleGent"),
                true,
                false,
                "(word)",
                "Слово в единственном числе, родитльского падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphNoun(string, Case.GENITIVUS, Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphSingleDativ"),
                true,
                false,
                "(word)",
                "Слово в единственном числе, дательного падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphNoun(string, Case.DATIVUS, Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphSingleAccus"),
                true,
                false,
                "(word)",
                "Слово в единственном числе, винительного падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphNoun(string, Case.ACCUSATIVUS, Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphSingleInstr"),
                true,
                false,
                "(word)",
                "Слово в единственном числе, творительного падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphNoun(string, Case.INSTRUMENTALIS, Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphSinglePreap"),
                true,
                false,
                "(word)",
                "Слово в единственном числе, предложного падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphNoun(string, Case.PRAEPOSITIONALIS, Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphGenSinNetr"),
                true,
                false,
                "(word)",
                "Слово в единственном числе, среднего рода") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphGender(string, ru.shuvaev.morpher.tools.enams.Gender.MEDIUM, Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        })
                        .orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphGenSinFem"),
                true,
                false,
                "(word)",
                "Слово в единственном числе, женского рода") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphGender(string, ru.shuvaev.morpher.tools.enams.Gender.FEMALE, Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        })
                        .orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphGenPlural"),
                true,
                false,
                "(word)",
                "Слово во множественном числе") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphGender(string, ru.shuvaev.morpher.tools.enams.Gender.MALE, Numeration.PLURAL))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        })
                        .orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphSingleNameGene"),
                true,
                false,
                "(word)",
                "Имя в единственном числе, дательного падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                final var gender = Optional.ofNullable(character)
                        .map(GameCharacter::getGender)
                        .orElse(Gender.M_P_MALE);

                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphFirstName(string, gender, Case.GENITIVUS, Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphSingleNameDativ"),
                true,
                false,
                "(word)",
                "Имя в единственном числе, дательного падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                final var gender = Optional.ofNullable(character)
                        .map(GameCharacter::getGender)
                        .orElse(Gender.M_P_MALE);

                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphFirstName(string, gender, Case.DATIVUS, Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphSingleNameAccus"),
                true,
                false,
                "(word)",
                "Имя в единственном числе, винительного падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                final var gender = Optional.ofNullable(character)
                        .map(GameCharacter::getGender)
                        .orElse(Gender.M_P_MALE);

                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphFirstName(string, gender, Case.ACCUSATIVUS, Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphSingleNameInstr"),
                true,
                false,
                "(word)",
                "Имя в единственном числе, творительного падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                final var gender = Optional.ofNullable(character)
                        .map(GameCharacter::getGender)
                        .orElse(Gender.M_P_MALE);

                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphFirstName(string, gender, Case.INSTRUMENTALIS, Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphSingleNamePreap"),
                true,
                false,
                "(word)",
                "Имя в единственном числе, предложного падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                final var gender = Optional.ofNullable(character)
                        .map(GameCharacter::getGender)
                        .orElse(Gender.M_P_MALE);

                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphFirstName(string, gender, Case.PRAEPOSITIONALIS, Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphSingleSurnameGene"),
                true,
                false,
                "(word)",
                "Фамилия в единственном числе, дательного падежа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                final var gender = Optional.ofNullable(character)
                        .map(GameCharacter::getGender)
                        .orElse(Gender.M_P_MALE);

                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphSurname(string, gender, Case.GENITIVUS, Numeration.SINGLE))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });

        UtilText.COMMANDS_LIST.add(new ParserCommand(
                Util.newArrayListOfValues("morphPluralParticipleToShortForm"),
                true,
                false,
                "(word)",
                "Преобразование причастия из полной формы множественного числа к причастию краткой формы множественного числа") {
            @Override
            public String parse(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
                return Optional.ofNullable(arguments)
                        .map(string -> Morpher.parseText(specialNPCs, command, arguments, target, character))
                        .map(string -> Morpher.morphParticipleToShortForm(string, ru.shuvaev.morpher.tools.enams.Gender.MALE, Numeration.PLURAL))
                        .map(word -> {
                            if (isCapitalise.getAsBoolean()) {
                                return Util.capitaliseSentence(word);
                            } else {
                                return word;
                            }
                        }).orElse("");
            }
        });
    }
}
