package com.groups.BrainTrainApp.Datas;

import java.util.ArrayList;
import java.util.List;

public final class LanguageData {

    public List<String> sortChars = new ArrayList<>();
    public   List<String> findWords = new ArrayList<>();

    public   List<String> completeWords = new ArrayList<>();

    public List<String> conjunction = new ArrayList<>();

    public List<String> getSortChars() {
        return sortChars;
    }

    public List<String> getFindWords() {
        return findWords;
    }

    public List<String> getCompleteWords() {
        return completeWords;
    }

    public List<String> getConjunction() {
        return conjunction;
    }

    public LanguageData() {
        createFindWords();
        createSortChars();

    }
    private List<String> createFindWords(){
        this.findWords.add("nhân");
        this.findWords.add("lực");
        this.findWords.add("chân");
        this.findWords.add("đức");
        this.findWords.add("đồng");
        this.findWords.add("dân");
        this.findWords.add("ghi");
        this.findWords.add("thành");
        this.findWords.add("công");
        this.findWords.add("trọng");
        this.findWords.add("lương");
        this.findWords.add("phong");
        this.findWords.add("ước");
        this.findWords.add("ngoại");
        this.findWords.add("mỹ");
        this.findWords.add("phúc");
        this.findWords.add("tự");
        this.findWords.add("nội");
        return this.findWords;
    }

    private List<String> createSortChars(){
        this.sortChars.add("đất nước");
        this.sortChars.add("điện ảnh");
        this.sortChars.add("cú sốc");
        this.sortChars.add("con cua");
        this.sortChars.add("ba phải");
        this.sortChars.add("đắc chí");
        this.sortChars.add("trí khôn");
        this.sortChars.add("dại khờ");
        this.sortChars.add("hòa nhã");
        this.sortChars.add("hạnh phúc");
        this.sortChars.add("thanh niên");
        this.sortChars.add("vẻ đẹp");
        this.sortChars.add("búa gỗ");
        this.sortChars.add("trong lành");
        this.sortChars.add("hòn đảo");

        return this.sortChars;
    }
}
