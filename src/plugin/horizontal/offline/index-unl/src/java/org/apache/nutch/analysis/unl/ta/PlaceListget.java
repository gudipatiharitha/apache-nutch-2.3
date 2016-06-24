package org.apache.nutch.analysis.unl.ta;
//package org.apache.nutch.snippetgeneration.unl.ta;

import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Set;

public class PlaceListget {

    public static boolean isPlaceLoaded = false;
    public static HashMap<String, String> place;

    public void loadPlace() {
        if (isPlaceLoaded) {
            return;
        }
        ObjectInputStream placeReader = IOHelper.getObjectInputStream("./crawl-unl/placeList.ser");
        place = (HashMap<String, String>) IOHelper.readObjectFromInputStream(placeReader);
        isPlaceLoaded = true;
    }

    public String getPlaceList(String docid) {
        loadPlace();
        String constraints = "";
        Set key = place.keySet();
        if (!key.contains(docid)) {
            return "NoPlaceAvailable";
        } else {
            constraints = place.get(docid).toString();
        }
        return constraints;
    }
}
