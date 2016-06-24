package org.apache.nutch.analysis.unl.ta.Integrated;

import static org.apache.nutch.analysis.unl.ta.Integrated.Symbols.SYMBOLS;

public class Symbol_Filter {

    /**
     * to remove symbols in a Query
     *
     * @param input as actual query word
     * @return query word without symbols
     */
    public String remove_Symbol(String input) {
        String result = "";
        String[] symbols = SYMBOLS;
        for (int i = 0; i < symbols.length; i++) {
            if (input.contains(symbols[i])) {
                if (!input.contains("+")) {
                    input = input.replace(symbols[i], "");//to replace the symbol to null value
                } else {
                    input = input.replace(symbols[i], " ");//to replace the symbol to null value
                }
            }
        }
        String[] query = input.split(" ");
        for (String temp : query) {
            if (temp.length() != 0 && !temp.equals(" ")) {
                result += temp.trim() + " ";
            }
        }
        return result.trim();
    }
    /**
     * Singleton class
     *
     * @return
     */
    private static Symbol_Filter filtersymbol = null;

    public static Symbol_Filter instanceOf() {
        if (filtersymbol == null) {
            filtersymbol = new Symbol_Filter();
        }
        return filtersymbol;
    }

    public String[] splitData(String input, String regex) {
        String[] splitedData = input.split(regex);
        return splitedData;
    }
}
