package org.apache.nutch.analysis.unl.ta;

//import org.apache.nutch.unl.utils.*;
import java.util.*;

public class RelativeParticiple {

    static String x = "";

    public static boolean check_Atha(Stack s) {
        //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "RP Atha");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        // Atha
        if (ByteMeth.endsWith(topElmt, Constant.Atha)) {
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "Atha");
            s.pop();
            s.push(new Entry(Constant.a, Tag.RelativeParticipleSuffix));
            s.push(new Entry(Constant.Athu, Tag.FutureNegativeSuffix));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.Atha.length);
            topElmt = ByteMeth.addArray(topElmt, Constant.a);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            return true;
        }
        return false;
    }

    public static boolean check_uLLa(Stack s) {
        //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "RP - uLLa");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        // uLLa
        if (ByteMeth.endsWith(topElmt, Constant.uLLa)) {
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "uLLa");
            s.pop();
            s.push(new Entry(Constant.uLLa, Tag.AdjectivalSuffix));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.uLLa.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            Sandhi.k(s);
            return true;
        }
        return false;
    }

    public static boolean check_thakka(Stack s) {
        //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "RP - uLLa");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        // thakka
        if (ByteMeth.endsWith(topElmt, Constant.thakka)) {
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "thakka");
            s.pop();
            s.push(new Entry(Constant.thakka, Tag.AdjectivalSuffix));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.thakka.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            Sandhi.k(s);
            return true;
        }
        return false;
    }

    public static boolean check_verbal_noun(Stack s) {
        //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "RP - al");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;
        byte[] u_add=null;

        ////System.out.println("check_verbal_noun topElmt "+UnicodeConverter.revert(topElmt));
        // new
        // a
        if (ByteMeth.endsWith(topElmt, Constant.thal)) {
            s.pop();
            s.push(new Entry(Constant.thal, Tag.verbalsuffix));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.thal.length);
            ////System.out.println("verbal noun if" + UnicodeConverter.revert(topElmt));
            s.push(new Entry(topElmt, -1, oldTopElmt));
            return true;
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "al");
        } else if (!ByteMeth.contains(topElmt, Constant.thth)) {
            if (ByteMeth.endsWith(topElmt, Constant.al)) {
                s.pop();
                s.push(new Entry(Constant.al, Tag.verbalsuffix));
                topElmt = ByteMeth.subArray(topElmt, 0,
                        topElmt.length - Constant.al.length);
                ////System.out.println("verbal noun else" + UnicodeConverter.revert(topElmt));
                if(ByteMeth.endsWith(topElmt, Constant.p)){ // for the word புலம்பல்
                    u_add=ByteMeth.addArray(topElmt,Constant.u);
                    s.push(new Entry(u_add, -1, oldTopElmt));
                    return true;
                }else{
                s.push(new Entry(topElmt, -1, oldTopElmt));
                return true;
                }
            }
        }
        return false;
    }

    public static boolean check_al(Stack s) {
        //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "RP - al");
        byte[] topElmt = ((Entry) s.peek()).getPart();
        byte[] oldTopElmt = topElmt;

        // new
        // a
        if (ByteMeth.endsWith(topElmt,
                ByteMeth.addArray(Constant.Tense, Constant.al))) {
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "al");
            s.pop();
            s.push(new Entry(Constant.al, Tag.RelativeParticipleSuffix));
            topElmt = ByteMeth.subArray(topElmt, 0,
                    topElmt.length - Constant.al.length);
            s.push(new Entry(topElmt, -1, oldTopElmt));
            return true;
        }
        return false;
    }

    public static boolean check_a(Stack s) {
        if (!ADictionary.m_EndNoun(s)) {
            ////System.out.println("check_a in Relative participle ");
            //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "RP - a");
            byte[] topElmt = ((Entry) s.peek()).getPart();
            byte[] oldTopElmt = topElmt;
            ////System.out.println("check_a in Relative participle " + UnicodeConverter.revert(topElmt));
            // new
            // a
            if (!ByteMeth.endsWith(topElmt, Constant.Ana)) {
                ////System.out.println("check_a in Relative participle 1 " + UnicodeConverter.revert(topElmt));
                if (ByteMeth.endsWith(topElmt, ByteMeth.addArray(Constant.RTense, Constant.a))) {
                    ////System.out.println("check_a in Relative participle 1");
                    //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "a");
                    s.pop();
                    s.push(new Entry(Constant.a, Tag.RelativeParticipleSuffix));
                    topElmt = ByteMeth.subArray(topElmt, 0,
                            topElmt.length - Constant.a.length);
                    s.push(new Entry(topElmt, -1, oldTopElmt));
                    ////System.out.println("remaining topElmt in relative participle " + UnicodeConverter.revert(topElmt));
                    Sandhi.check1(s);
                    return true;
                } else if (ByteMeth.endsWith(topElmt, Constant.ta)) {
                    ////System.out.println("check_a in Relative participle 2");
                    //clia.unl.unicode.utils.Utils.printOut(Analyser.print, x + "a");
                    s.pop();
                    s.push(new Entry(Constant.a, Tag.RelativeParticipleSuffix));
                    topElmt = ByteMeth.subArray(topElmt, 0,
                            topElmt.length - Constant.a.length);
                    topElmt = ByteMeth.addArray(topElmt, Constant.u);
                    s.push(new Entry(topElmt, -1, oldTopElmt));
                    Sandhi.check1(s);
                    return true;
                }
            }
        }
        //////System.out.println("relative participle false");
        return false;
    }
}
