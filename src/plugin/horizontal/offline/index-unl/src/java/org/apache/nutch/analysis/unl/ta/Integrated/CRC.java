package org.apache.nutch.analysis.unl.ta.Integrated;

public class CRC implements Comparable {
    //tam1 is Concept1 Tamil term

    public String tam1;
    //c1 is Concept1 english concept
    public String c1;
    //pos1 as concept1's parts of speech
    public String pos1;
    //QWTag1 says Concept1 is QW or EQW
    public String QWTag1;
    //MWTag1 says Concept1 is MW or Not
    public String MWTag1;
    // rel shows the re4lation between two concepts
    public String rel;
    //tam2 is Concept1 Tamil term
    public String tam2;
    //c2 is Concept2 english concept
    public String c2;
    //pos2 as concept2's parts of speech
    public String pos2;
    //QWTag2 says Concept2 is QW or EQW
    public String QWTag2;
    //MWTag2 says Concept2 is MW or Not
    public String MWTag2;
    //frequency count
    public int crcFreqCnt;
    public int tamilconceptid;
    public int uwconceptid;

    /**
     * @return the tam1
     */
    public String getTam1() {
        return tam1;
    }

    /**
     * @param tam1 the tam1 to set
     */
    public void setTam1(String tam1) {
        this.tam1 = tam1;
    }

    /**
     * @return the c1
     */
    public String getC1() {
        return c1;
    }

    /**
     * @param c1 the c1 to set
     */
    public void setC1(String c1) {
        this.c1 = c1;
    }

    /**
     * @return the pos1
     */
    public String getPos1() {
        return pos1;
    }

    /**
     * @param pos1 the pos1 to set
     */
    public void setPos1(String pos1) {
        this.pos1 = pos1;
    }

    /**
     * @return the qWTag1
     */
    public String getQWTag1() {
        return QWTag1;
    }

    /**
     * @param qWTag1 the qWTag1 to set
     */
    public void setQWTag1(String qWTag1) {
        QWTag1 = qWTag1;
    }

    /**
     * @return the mWTag1
     */
    public String getMWTag1() {
        return MWTag1;
    }

    /**
     * @param mWTag1 the mWTag1 to set
     */
    public void setMWTag1(String mWTag1) {
        MWTag1 = mWTag1;
    }

    /**
     * @return the rel
     */
    public String getRel() {
        return rel;
    }

    /**
     * @param rel the rel to set
     */
    public void setRel(String rel) {
        this.rel = rel;
    }

    /**
     * @return the tam2
     */
    public String getTam2() {
        return tam2;
    }

    /**
     * @param tam2 the tam2 to set
     */
    public void setTam2(String tam2) {
        this.tam2 = tam2;
    }

    /**
     * @return the c2
     */
    public String getC2() {
        return c2;
    }

    /**
     * @param c2 the c2 to set
     */
    public void setC2(String c2) {
        this.c2 = c2;
    }

    /**
     * @return the pos2
     */
    public String getPos2() {
        return pos2;
    }

    /**
     * @param pos2 the pos2 to set
     */
    public void setPos2(String pos2) {
        this.pos2 = pos2;
    }

    /**
     * @return the qWTag2
     */
    public String getQWTag2() {
        return QWTag2;
    }

    /**
     * @param qWTag2 the qWTag2 to set
     */
    public void setQWTag2(String qWTag2) {
        QWTag2 = qWTag2;
    }

    /**
     * @return the mWTag2
     */
    public String getMWTag2() {
        return MWTag2;
    }

    /**
     * @param mWTag2 the mWTag2 to set
     */
    public void setMWTag2(String mWTag2) {
        MWTag2 = mWTag2;
    }

    /**
     * @return the crcFreqCnt
     */
    public int getCrcFreqCnt() {
        return crcFreqCnt;
    }

    /**
     * @param crcFreqCnt the crcFreqCnt to set
     */
    public void setCrcFreqCnt(int crcFreqCnt) {
        this.crcFreqCnt = crcFreqCnt;
    }

    public int compareTo(Object o) {
        if (this.crcFreqCnt == ((CRC) o).getCrcFreqCnt()) {
            return 0;
        } else if (this.crcFreqCnt > ((CRC) o).getCrcFreqCnt()) {
            return -1;
        } else {
            return 1;
        }
    }

    public int getTamilconceptid() {
        return tamilconceptid;
    }

    public void setTamilconceptid(int tamilconceptid) {
        this.tamilconceptid = tamilconceptid;
    }

    public int getUwconceptid() {
        return uwconceptid;
    }

    public void setUwconceptid(int uwconceptid) {
        this.uwconceptid = uwconceptid;
    }

    public static void main(String args[]) {
    }
}
