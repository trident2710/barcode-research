package com.trident.barcode.model;

import java.util.List;

public final class BarcodeDictionaries {

    public static final BarcodeCharsetType CHARSET_A = BarcodeDictionaryUtil.charsetType(0, "A");
    public static final BarcodeCharsetType CHARSET_B = BarcodeDictionaryUtil.charsetType(1, "B");
    public static final BarcodeCharsetType CHARSET_C = BarcodeDictionaryUtil.charsetType(2, "C");
    public static final BarcodeCharsetType CHARSET_D = BarcodeDictionaryUtil.charsetType(3, "D");

    private static final List<BarcodeCharsetType> CHARSET_TYPES = List.of(CHARSET_A, CHARSET_B, CHARSET_C, CHARSET_D);


    public static final BarcodeDictionary BASE_59 = BarcodeDictionaryUtil.dictionaryFromCharsets(List.of(
            BarcodeDictionaryUtil.charset(CHARSET_A, List.of(
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 0, "A", "001221131"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 1, "B", "002332212"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 2, "C", "003113323"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 3, "D", "012211310"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 4, "E", "013030221"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 5, "F", "010123102"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 6, "G", "011302033"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 7, "H", "023322120"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 8, "I", "022103011"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 9, "J", "021010332"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 10, "K", "020231203"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 11, "L", "031133230"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 12, "M", "030312301"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 13, "N", "033201022"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 14, "O", "032020113"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 15, "P", "122113100"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 16, "Q", "123332031"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 17, "R", "120221312"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 18, "S", "121000223"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 19, "T", "130302210"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 20, "U", "131123321"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 21, "V", "132030002"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 22, "W", "133211133"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 23, "X", "101231020"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 24, "Y", "100010111"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 25, "Z", "103103232"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 26, "0", "102322303"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 27, "1", "113020330"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 28, "2", "112201201"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 29, "3", "111312122"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 30, "4", "110133013"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 31, "5", "233221200"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 32, "6", "232000331"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 33, "7", "231113012"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 34, "8", "230332123"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 35, "9", "221030110"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 36, "!", "220211021"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 37, "\"", "223302302"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 38, "$", "222123233"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 39, "&", "210103320"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 40, "*", "211322211"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 41, "+", "212231132"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 42, " ", "213010003"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 43, "'", "202312030"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 44, ".", "203133101"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 45, ",", "200020222"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 46, ";", "201201313"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 47, "(", "311332300"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 48, ")", "310113231"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 49, "-", "313000112"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 50, "/", "312221023"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 51, ":", "303123010"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 52, "@", "302302121"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 53, "#", "301211202"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_A, 54, "%", "300030333"),
                    BarcodeDictionaryUtil.padding(CHARSET_A, 55, "{pad}", "332010220"),
                    BarcodeDictionaryUtil.switcher(CHARSET_A, 56, "{setB}", "333231311", CHARSET_B),
                    BarcodeDictionaryUtil.switcher(CHARSET_A, 57, "{setC}", "330322032", CHARSET_C),
                    BarcodeDictionaryUtil.switcher(CHARSET_A, 58, "{setD}", "331103103", CHARSET_D)
            )),
            BarcodeDictionaryUtil.charset(CHARSET_B, List.of(
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 0, "a", "001221131"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 1, "b", "002332212"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 2, "c", "003113323"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 3, "d", "012211310"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 4, "e", "013030221"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 5, "f", "010123102"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 6, "g", "011302033"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 7, "h", "023322120"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 8, "i", "022103011"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 9, "j", "021010332"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 10, "k", "020231203"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 11, "l", "031133230"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 12, "m", "030312301"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 13, "n", "033201022"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 14, "o", "032020113"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 15, "p", "122113100"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 16, "q", "123332031"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 17, "r", "120221312"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 18, "s", "121000223"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 19, "t", "130302210"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 20, "u", "131123321"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 21, "v", "132030002"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 22, "w", "133211133"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 23, "x", "101231020"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 24, "y", "100010111"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 25, "z", "103103232"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 26, "0", "102322303"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 27, "1", "113020330"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 28, "2", "112201201"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 29, "3", "111312122"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 30, "4", "110133013"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 31, "5", "233221200"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 32, "6", "232000331"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 33, "7", "231113012"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 34, "8", "230332123"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 35, "9", "221030110"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 36, "!", "220211021"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 37, "\"", "223302302"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 38, "$", "222123233"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 39, "&", "210103320"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 40, "*", "211322211"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 41, "+", "212231132"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 42, " ", "213010003"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 43, "'", "202312030"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 44, ".", "203133101"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 45, ",", "200020222"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 46, ";", "201201313"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 47, "(", "311332300"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 48, ")", "310113231"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 49, "-", "313000112"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 50, "/", "312221023"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 51, ":", "303123010"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 52, "@", "302302121"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 53, "#", "301211202"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_B, 54, "%", "300030333"),
                    BarcodeDictionaryUtil.padding(CHARSET_B, 55, "{pad}", "332010220"),
                    BarcodeDictionaryUtil.switcher(CHARSET_B, 56, "{setA}", "333231311", CHARSET_A),
                    BarcodeDictionaryUtil.switcher(CHARSET_B, 57, "{setC}", "330322032", CHARSET_C),
                    BarcodeDictionaryUtil.switcher(CHARSET_B, 58, "{setD}", "331103103", CHARSET_D)
            )),
            BarcodeDictionaryUtil.charset(CHARSET_C, List.of(
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 0, "А", "001221131"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 1, "Б", "002332212"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 2, "В", "003113323"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 3, "Г", "012211310"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 4, "Д", "013030221"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 5, "Е", "010123102"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 6, "Є", "011302033"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 7, "Ж", "023322120"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 8, "З", "022103011"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 9, "И", "021010332"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 10, "І", "020231203"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 11, "Ї", "031133230"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 12, "Й", "030312301"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 13, "К", "033201022"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 14, "Л", "032020113"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 15, "М", "122113100"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 16, "Н", "123332031"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 17, "О", "120221312"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 18, "П", "121000223"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 19, "Р", "130302210"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 20, "С", "131123321"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 21, "Т", "132030002"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 22, "У", "133211133"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 23, "Ф", "101231020"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 24, "Х", "100010111"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 25, "Ц", "103103232"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 26, "Ч", "102322303"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 27, "Ш", "113020330"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 28, "Щ", "112201201"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 29, "Ю", "111312122"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 30, "Я", "110133013"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 31, "Ь", "233221200"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 32, "6", "232000331"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 33, "7", "231113012"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 34, "8", "230332123"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 35, "9", "221030110"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 36, "!", "220211021"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 37, "\"", "223302302"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 38, "$", "222123233"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 39, "&", "210103320"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 40, "*", "211322211"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 41, "+", "212231132"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 42, " ", "213010003"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 43, "'", "202312030"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 44, ".", "203133101"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 45, ",", "200020222"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 46, ";", "201201313"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 47, "(", "311332300"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 48, ")", "310113231"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 49, "-", "313000112"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 50, "/", "312221023"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 51, ":", "303123010"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 52, "@", "302302121"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 53, "#", "301211202"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_C, 54, "%", "300030333"),
                    BarcodeDictionaryUtil.padding(CHARSET_C, 55, "{pad}", "332010220"),
                    BarcodeDictionaryUtil.switcher(CHARSET_C, 56, "{setA}", "333231311", CHARSET_A),
                    BarcodeDictionaryUtil.switcher(CHARSET_C, 57, "{setB}", "330322032", CHARSET_B),
                    BarcodeDictionaryUtil.switcher(CHARSET_C, 58, "{setD}", "331103103", CHARSET_D)
            )),
            BarcodeDictionaryUtil.charset(CHARSET_D, List.of(
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 0, "а", "001221131"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 1, "б", "002332212"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 2, "в", "003113323"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 3, "г", "012211310"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 4, "д", "013030221"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 5, "е", "010123102"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 6, "є", "011302033"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 7, "ж", "023322120"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 8, "з", "022103011"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 9, "и", "021010332"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 10, "і", "020231203"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 11, "ї", "031133230"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 12, "й", "030312301"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 13, "к", "033201022"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 14, "л", "032020113"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 15, "м", "122113100"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 16, "н", "123332031"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 17, "о", "120221312"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 18, "п", "121000223"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 19, "р", "130302210"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 20, "с", "131123321"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 21, "т", "132030002"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 22, "у", "133211133"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 23, "ф", "101231020"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 24, "х", "100010111"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 25, "ц", "103103232"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 26, "ч", "102322303"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 27, "ш", "113020330"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 28, "щ", "112201201"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 29, "ю", "111312122"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 30, "я", "110133013"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 31, "ь", "233221200"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 32, "6", "232000331"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 33, "7", "231113012"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 34, "8", "230332123"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 35, "9", "221030110"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 36, "!", "220211021"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 37, "\"", "223302302"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 38, "$", "222123233"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 39, "&", "210103320"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 40, "*", "211322211"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 41, "+", "212231132"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 42, " ", "213010003"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 43, "'", "202312030"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 44, ".", "203133101"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 45, ",", "200020222"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 46, ";", "201201313"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 47, "{", "311332300"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 48, "}", "310113231"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 49, "-", "313000112"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 50, "/", "312221023"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 51, ":", "303123010"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 52, "@", "302302121"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 53, "#", "301211202"),
                    BarcodeDictionaryUtil.regularSymbol(CHARSET_D, 54, "%", "300030333"),
                    BarcodeDictionaryUtil.padding(CHARSET_D, 55, "{pad}", "332010220"),
                    BarcodeDictionaryUtil.switcher(CHARSET_D, 56, "{setA}", "333231311", CHARSET_A),
                    BarcodeDictionaryUtil.switcher(CHARSET_D, 57, "{setB}", "330322032", CHARSET_B),
                    BarcodeDictionaryUtil.switcher(CHARSET_D, 58, "{setС}", "331103103", CHARSET_C)
            ))));

    private BarcodeDictionaries() {
    }
}
