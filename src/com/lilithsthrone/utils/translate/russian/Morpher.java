package com.lilithsthrone.utils.translate.russian;

import com.lilithsthrone.game.character.GameCharacter;
import com.lilithsthrone.game.character.gender.Gender;
import com.lilithsthrone.game.character.gender.PronounType;
import com.lilithsthrone.game.dialogue.utils.UtilText;
import ru.shuvaev.morpher.tools.FactoryMorpherKt;
import ru.shuvaev.morpher.tools.enams.Case;
import ru.shuvaev.morpher.tools.enams.Numeration;
import ru.shuvaev.morpher.tools.type.MorpherType;

import java.util.Arrays;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Morpher {
    public static final MorpherType MORPHER = FactoryMorpherKt.getMorpherW3CachedMorpNoun();
    private static final MorpherType SIMPLE_MORPHER = FactoryMorpherKt.getSimpleMorpNoun();

    public static String morphNoun(String text, Case aCase, Numeration numeration) {
        return replaceBetweenHtmlTags(text, string -> MORPHER.morphNoun(string, aCase, numeration));
    }

    public static String morphGender(String text, ru.shuvaev.morpher.tools.enams.Gender gender, Numeration numeration) {
        return replaceBetweenHtmlTags(text, string -> MORPHER.morphGender(string, gender, numeration));
    }

    public static String parseText(List<GameCharacter> specialNPCs, String command, String arguments, String target, GameCharacter character) {
        if (arguments == null) {
            return null;
        }
        var string = arguments;
        while (string.contains("[") && string.contains("]")) {
            string = UtilText.parse(specialNPCs, string);
        }
        return string;
    }

    public static String replaceBetweenHtmlTags(String text, UnaryOperator<String> func) {
        var p = Pattern.compile("(<.+?>)(.+?)(</.+?>)(.+)");
        var m = p.matcher(text);
        if (m.find()) {
            return m.replaceAll(m.group(1) + funcSplitWords(m.group(2), func) + m.group(3) + replaceBetweenHtmlTags(m.group(4), func));
        }

        p = Pattern.compile("(<.+?>)(.+?)(</.+?>)");
        m = p.matcher(text);
        if (m.find()) {
            return m.replaceAll(m.group(1) + funcSplitWords(m.group(2), func) + m.group(3));
        }


        return funcSplitWords(text, func);
    }

    public static String funcSplitWords(String text, UnaryOperator<String> func) {
        return Arrays.stream(text.split(" "))
                .filter(word -> !word.isBlank())
                .map(word -> {
                    //Игнорируем запятую
                    if (word.equals(",")) {
                        return word;
                    }
                    //Игнорируем слова с латиницей
                    var p = Pattern.compile("[a-zA-Z]+");
                    var m = p.matcher(word);
                    if (m.find()) {
                        return word;
                    }
                    return func.apply(word);
                })
                .collect(Collectors.joining(" "));
    }

    public static String morphFirstName(String text, Gender gender, Case aCase, Numeration numeration) {
        return SIMPLE_MORPHER.morphFirstName(text, aCase, convertGenderOnlyTwo(gender), numeration);
    }

    public static String morphSurname(String text, Gender gender, Case aCase, Numeration numeration) {
        return SIMPLE_MORPHER.morphLastName(text, aCase, convertGenderOnlyTwo(gender), numeration);
    }

    public static String convertSurnameToGender(String surname, Gender gender) {
        if (!gender.isFeminine()) {
            return surname;
        }
        final var result = MORPHER.convertSurnameToFemale(surname);
        final var p = Pattern.compile("[a-zA-Z]+");
        final var m = p.matcher(result);
        if (m.find()) {
            System.out.println("Surname: " + surname + " -> " + result);
        }
        return result;
    }

    public static ru.shuvaev.morpher.tools.enams.Gender convertGender(Gender gender) {
        PronounType type = gender.getType();
        return switch (type) {
            case FEMININE -> ru.shuvaev.morpher.tools.enams.Gender.FEMALE;
            case NEUTRAL -> ru.shuvaev.morpher.tools.enams.Gender.MEDIUM;
            case MASCULINE -> ru.shuvaev.morpher.tools.enams.Gender.MALE;
        };
    }

    public static ru.shuvaev.morpher.tools.enams.Gender convertGenderOnlyTwo(Gender gender) {
        if (gender.isFeminine()) {
            return ru.shuvaev.morpher.tools.enams.Gender.FEMALE;
        } else {
            return ru.shuvaev.morpher.tools.enams.Gender.MALE;
        }
    }

    public static String morphCountableNoun(int count, String noun) {
        return MORPHER.morphCountableNoun(count, noun, Case.NOMINATIVUS);
    }

    public static String morphCountableNoun(double count, String noun) {
        return MORPHER.morphCountableNoun(count, noun, Case.NOMINATIVUS);
    }
}
