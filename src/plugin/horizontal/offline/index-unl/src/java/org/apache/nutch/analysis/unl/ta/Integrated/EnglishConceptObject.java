package org.apache.nutch.analysis.unl.ta.Integrated;

public class EnglishConceptObject {

    private String headword = "";
    private String uwword = "";
    private String pos = "";
    private int conceptid = 0;

    /**
     * @return the headword
     */
    public String getHeadword() {
        return headword;
    }

    /**
     * @param headword the headword to set
     */
    public void setHeadword(String headword) {
        this.headword = headword;
    }

    /**
     * @return the uwword
     */
    public String getUwword() {
        return uwword;
    }

    /**
     * @param uwword the uwword to set
     */
    public void setUwword(String uwword) {
        this.uwword = uwword;
    }

    /**
     * @return the pos
     */
    public String getPos() {
        return pos;
    }

    /**
     * @param pos the pos to set
     */
    public void setPos(String pos) {
        this.pos = pos;
    }

    /**
     * @return the conceptid
     */
    public int getConceptid() {
        return conceptid;
    }

    /**
     * @param conceptid the conceptid to set
     */
    public void setConceptid(int conceptid) {
        this.conceptid = conceptid;
    }
}
