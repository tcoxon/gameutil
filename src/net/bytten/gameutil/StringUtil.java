package net.bytten.gameutil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class StringUtil {
    
    public static String capitalize(String text) {
        StringBuilder sb = new StringBuilder();
        boolean lastB = true;
        for (char c: text.toCharArray()) {
            if (Character.isLetter(c)) {
                if (lastB) {
                    c = Character.toUpperCase(c);
                }
                    
                lastB = false;
            } else {
                if (c != '\'')
                    lastB = true;
            }
            
            sb.append(c);
        }
        return sb.toString();
    }
    
    public static String rightJustify(String text, int cols) {
        while (text.length() < cols)
            text = " "+text;
        return text;
    }
    
    public static List<String> splitWords(String text) {
        List<String> words = new LinkedList<String>();
        String current = "";
        for (int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            
            if (Character.isWhitespace(c) && !"".equals(current.trim())) {
                words.add(current);
                current = "";
            }
            
            if (c == '\n')
                words.add("\n");
            else
                current += c;
        }
        if (!"".equals(current.trim()))
            words.add(current);
        
        return words;
    }
    
    public static List<String> wrapWords(String text, int cols) {
        // Wrap a string at a specified number of columns into a list of lines,
        // while trying to preserve whole words
        List<String> words = splitWords(text),
                     lines = new LinkedList<String>();
        String current = "";
        for (int i = 0; i < words.size(); ++i) {
            String word = words.get(i);
            
            if (word.equals("\n")) {
                // New line
                lines.add(current);
                current = "";
            } else if (current.length() + word.length() <= cols) {
                // word fits on the line
                current += word;
            } else if (current.length() < cols-1 && word.length() > cols) {
                // word doesn't fit on the line, and won't fit on the next
                // either, so break it up
                int spaceLeft = cols - current.length();
                current += word.substring(0, spaceLeft-1);
                current += "-";
                
                words.set(i, word.substring(spaceLeft-1));
                --i;
            } else {
                // go to the next line...
                lines.add(current);
                if (word.charAt(0) == '\n' || word.charAt(0) == ' ')
                    word = word.substring(1);
                
                if (word.length() > cols) {
                    // word won't fit on the new line, so break it up
                    current = word.substring(0, cols-1);
                    current += "-";
                    
                    words.set(i, word.substring(cols-1));
                    --i;
                    
                } else {
                    current = word;
                }
            }
        }
        if (!"".equals(current))
            lines.add(current);
        if (lines.size() == 0) lines.add("");
        return lines;
    }

    public static List<String> wrap(String text, int cols) {
        // Wrap a string at a specified number of columns, into a list of lines
        List<String> lines = new LinkedList<String>();
        String current = "";
        int col = 0;
        for (int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            if (c == '\n') {
                lines.add(current);
                current = "";
                col = 0;
            } else {
                current += c;
                if (c == '\t')
                    col += 4;
                else
                    ++col;
                
                if (col >= cols) {
                    lines.add(current);
                    current = "";
                    col = 0;
                }
            }
        }
        lines.add(current);
        return lines;
    }
    
    public static List<String> splitLines(String text) {
        List<String> lines = new LinkedList<String>();
        String current = "";
        for (int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            if (c == '\n') {
                lines.add(current);
                current = "";
            } else {
                current += c;
            }
        }
        lines.add(current);
        return lines;
    }

    public static String clip(String text, int w, int h) {
        List<String> lines = splitLines(text);
        
        StringBuilder clipped = new StringBuilder();
        for (int i = 0; i < h && i < lines.size(); ++i) {
            String line = lines.get(i);
            if (line.length() > w)
                line = line.substring(0,w);
            
            if (clipped.length() != 0)
                clipped.append('\n');
            clipped.append(line);
        }
        return clipped.toString();
    }
    
    public static String clipWithEllipsis(String text, int w, int h) {
        List<String> lines = splitLines(text);
        
        StringBuilder clipped = new StringBuilder();
        for (int i = 0; i < h && i < lines.size(); ++i) {
            String line = lines.get(i);
            if (line.length() > w)
                line = line.substring(0,w-3) + "...";
            
            if (clipped.length() != 0)
                clipped.append('\n');
            clipped.append(line);
        }
        return clipped.toString();
    }
    
    public static String dedent(String text) {
        List<String> lines = splitLines(text);
        for (int i = 0; i < lines.size(); ++i) {
            lines.set(i, lines.get(i).trim());
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.size(); ++i) {
            String line = lines.get(i);
            sb.append(line);
            if (i < lines.size()-1)
                sb.append('\n');
        }
        return sb.toString();
    }
    
    public static String ltrim(String text) {
        while (text.length() > 0 && Character.isWhitespace(text.charAt(0)))
            text = text.substring(1);
        return text;
    }
    
    public static String rtrim(String text) {
        while (text.length() > 0 && Character.isWhitespace(text.charAt(
                text.length()-1)))
            text = text.substring(0, text.length()-1);
        return text;
    }

    public static String join(String glue, Collection<String> pieces) {
        return join(glue, pieces.toArray(new String[0]));
    }
    
    public static String join(String glue, String... pieces) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < pieces.length; ++i) {
            result.append(pieces[i]);
            if (i < pieces.length-1) {
                result.append(glue);
            }
        }
        return result.toString();
    }

    public static String urlencode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            assert false;
            return value;
        }
    }
    
}
