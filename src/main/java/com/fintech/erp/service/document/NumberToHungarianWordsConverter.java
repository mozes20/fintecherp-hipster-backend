package com.fintech.erp.service.document;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility for converting non-negative integers to Hungarian words.
 */
public final class NumberToHungarianWordsConverter {

    private static final String[] UNITS = { "nulla", "egy", "ketto", "harom", "negy", "ot", "hat", "het", "nyolc", "kilenc" };

    private static final String[] TEENS = {
        "tiz",
        "tizenegy",
        "tizenkettő",
        "tizenharom",
        "tizennegy",
        "tizenot",
        "tizenhat",
        "tizenhet",
        "tizennyolc",
        "tizenkilenc",
    };

    private static final String[] TENS = { "", "tiz", "husz", "harminc", "negyven", "otven", "hatvan", "hetven", "nyolcvan", "kilencven" };

    private static final String[] HUNDREDS = {
        "",
        "szaz",
        "ketszaz",
        "haromszaz",
        "negyszaz",
        "otszaz",
        "hatszaz",
        "hetszaz",
        "nyolcszaz",
        "kilencszaz",
    };

    private static final Map<Long, String> SCALE_WORDS = new LinkedHashMap<>();

    static {
        SCALE_WORDS.put(1_000_000_000_000L, "billio");
        SCALE_WORDS.put(1_000_000_000L, "milliard");
        SCALE_WORDS.put(1_000_000L, "millio");
        SCALE_WORDS.put(1_000L, "ezer");
        SCALE_WORDS.put(1L, "");
    }

    private NumberToHungarianWordsConverter() {}

    public static String convert(long value) {
        if (value == 0) {
            return UNITS[0];
        }
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Long, String> entry : SCALE_WORDS.entrySet()) {
            long scale = entry.getKey();
            if (value >= scale) {
                int part = (int) (value / scale);
                value %= scale;
                appendScale(builder, part, entry.getValue());
            }
        }
        return builder.toString().trim();
    }

    private static void appendScale(StringBuilder builder, int part, String scaleWord) {
        if (part == 0) {
            return;
        }
        if (builder.length() > 0) {
            builder.append(" ");
        }
        if (scaleWord.equals("ezer")) {
            if (part == 1) {
                builder.append("ezer");
                return;
            }
            if (part == 2) {
                builder.append("ketezer");
                return;
            }
        } else if (scaleWord.equals("millio")) {
            if (part == 1) {
                builder.append("egymillio");
                return;
            }
            if (part == 2) {
                builder.append("ketmillio");
                return;
            }
        } else if (scaleWord.equals("milliard")) {
            if (part == 1) {
                builder.append("egymilliard");
                return;
            }
            if (part == 2) {
                builder.append("ketmilliard");
                return;
            }
        } else if (scaleWord.equals("billio")) {
            if (part == 1) {
                builder.append("egybillio");
                return;
            }
            if (part == 2) {
                builder.append("ketbillio");
                return;
            }
        }
        builder.append(convertBelowThousand(part));
        if (!scaleWord.isEmpty()) {
            builder.append(scaleWord);
        }
    }

    private static String convertBelowThousand(int value) {
        if (value == 0) {
            return "";
        }
        StringBuilder part = new StringBuilder();
        int hundreds = value / 100;
        if (hundreds > 0) {
            part.append(HUNDREDS[hundreds]);
        }
        int remainder = value % 100;
        if (remainder >= 10 && remainder < 20) {
            part.append(TEENS[remainder - 10]);
            return part.toString();
        }
        int tens = remainder / 10;
        if (tens > 0) {
            if (tens == 2) {
                part.append("husz");
                if (remainder % 10 != 0) {
                    part.append("on");
                }
            } else {
                part.append(TENS[tens]);
            }
        }
        int units = remainder % 10;
        if (units > 0) {
            part.append(UNITS[units]);
        }
        return part.toString();
    }
}
