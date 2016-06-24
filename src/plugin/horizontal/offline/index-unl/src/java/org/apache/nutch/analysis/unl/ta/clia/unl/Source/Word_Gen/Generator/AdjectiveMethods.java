package org.apache.nutch.analysis.unl.ta.clia.unl.Source.Word_Gen.Generator;

import org.apache.nutch.analysis.unl.ta.TabMethods;

public class AdjectiveMethods {

    TabMethods tm = new TabMethods();
    ByteMethods bm = new ByteMethods();

    /**
     * In this method when a noun is passed it adds adjective and the return
     * type is String
     */
    public String addAdjective(String noun) {
        String str = "";
        byte[] aana = {2, 31, 1};
        byte[] nounbyte = tm.convert(noun);
        //  if(nounbyte.length==0) nounbyte[0]=0;
        byte[] numbyte, numbyte1;
        String adject[] = {"ஐந்து", "ஆறு", "ஏழு", "எட்டு", "பது", "பத்து", "ஒரு", "இரு", "மூன்று", "நான்கு", "ஒன்று", "இரண்டு", "நூறு", "ஆயிரம்", "இலட்சம்", "கோடி", "நல்ல", "கெட்ட", "பழைய", "புதிய "};
        String adj[] = {"ஐந்து", "ஆறு", "ஏழு", "எட்டு", "பது", "பத்து", "ஒரு", "இரு", "மூன்று", "நான்கு", "ஒன்று", "இரண்டு", "நூறு", "ஆயிரம்", "இலட்சம்", "கோடி", "நல்ல", "கெட்ட", "பழைய", "புதிய ", "அவள்", "அவன்", "இவன்", "இவள்", "என்", "எம்", "எங்கள்", "அவர்", "இவர்", "அவர்கள்", "இவர்கள் ", "உன்", " உங்கள்", "உம்", "நம்"};
        //  String[] adject1={"அழகு","கருப்பு","பயங்கரம்","கோரம்","தூய்மை"};
        try {

            for (int i = 0; i < adject.length; i++) {
                numbyte = tm.convert(adject[i]);

                try {
                    if (bm.endswith(nounbyte, numbyte)) {
                        return noun + " ";
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    for (int j = 0; j < adject.length; j++) {
                        numbyte = tm.convert(adject[i]);
                        if (bm.isequal(numbyte, nounbyte)) {
                            return noun + " ";
                        }
                    }
                }
            }
            for (int i = 0; i < adj.length; i++) {
                numbyte1 = tm.convert(adj[i]);
                if (!(bm.isequal(nounbyte, numbyte1))) {
                    return tm.revert(bm.addarray(nounbyte, aana)) + " ";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }
}
