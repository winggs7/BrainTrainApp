package com.groups.BrainTrainApp.Components.Attention.Language;

import com.groups.BrainTrainApp.Datas.LanguageData;
import com.groups.BrainTrainApp.Model.LanguageModels.ConjunctionGameModel;
import com.groups.BrainTrainApp.Model.LanguageModels.FindWordGameModel;
import com.groups.BrainTrainApp.Model.LanguageModels.SortingCharGameModel;

import java.util.ArrayList;
import java.util.List;

public class LanguageDAO {


    public LanguageDAO() {
    }

    public List<FindWordGameModel> findWordGameModels(LanguageData languageData){
        int count = 0;
        List<FindWordGameModel> findWordGameModels = new ArrayList<>();
        for (String i: languageData.getFindWords()) {
            FindWordGameModel findWordGameModel = new FindWordGameModel(count++, i, 0);
            findWordGameModels.add(findWordGameModel);
        }

        return findWordGameModels;
    }

    public List<SortingCharGameModel> findSortingCharGameModels(LanguageData languageData){
        int count = 0;
        List<SortingCharGameModel> sortingCharGameModels = new ArrayList<>();
        for (String i: languageData.getSortChars()) {
            SortingCharGameModel sortingCharGameModel = new SortingCharGameModel(count++, i, 0);
            sortingCharGameModels.add(sortingCharGameModel);

        }

        return sortingCharGameModels;
    }

    public List<ConjunctionGameModel> findConjunctionGameModel(LanguageData languageData){
        int count = 0;
        List<ConjunctionGameModel> conjunctionGameModels = new ArrayList<>();
        for (String i: languageData.getFindWords()) {
            ConjunctionGameModel conjunctionGameModel = new ConjunctionGameModel(count++, i, 0);
            conjunctionGameModels.add(conjunctionGameModel);

        }
        return conjunctionGameModels;
    }
}
