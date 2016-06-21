package net.bytten.gameutil;

import java.util.*;

public class TextSubstitution {
    public static interface IVariables {
        public String get(String key);
    }

    private abstract static class Elem {
        protected String value;
        public Elem(String value) {
            this.value = value;
        }
        public abstract String sub(IVariables vars);
    }
    private static class TextElem extends Elem {
        public TextElem(String value) {
            super(value);
        }

        @Override
        public String sub(IVariables vars) {
            return value;
        }
        
    }
    private static class VarElem extends Elem {
        public VarElem(String value) {
            super(value);
        }

        @Override
        public String sub(IVariables vars) {
            String text = vars.get(value);
            if (text == null) return "${"+value+"}";
            return text;
        }
        
    }

    private static List<Elem> parseText(String text) {
        List<Elem> result = new ArrayList<Elem>();
        String currentText = "";
        for (int i = 0; i < text.length(); ++i) {
            if (i+2 <= text.length() && text.substring(i,i+2).equals("${")) {
                if (!currentText.equals("")) {
                    result.add(new TextElem(currentText));
                    currentText = "";
                }
                i += 2;
                int j = text.indexOf('}', i);
                if (j == -1) j = text.length();
                result.add(new VarElem(text.substring(i,j)));
                i = j;
            } else {
                currentText += text.charAt(i);
            }
        }
        if (!currentText.equals("")) {
            result.add(new TextElem(currentText));
            currentText = "";
        }
        return result;
    }

    public static String substitute(IVariables variables, String text) {
        List<Elem> elems = parseText(text);
        String result = "";
        for (Elem elem: elems) {
            result += elem.sub(variables);
        }
        return result;
    }
    
    private TextSubstitution() {}
}
