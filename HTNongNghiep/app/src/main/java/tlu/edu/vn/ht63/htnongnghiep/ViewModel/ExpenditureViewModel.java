package tlu.edu.vn.ht63.htnongnghiep.ViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;

public class ExpenditureViewModel extends ViewModel {

    private MutableLiveData<List<Expenditure>> listMutableLiveData;
    private ArrayList<Expenditure> expenditureList;

    public ExpenditureViewModel(){
        listMutableLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData(){
        expenditureList = new ArrayList<>();
        listMutableLiveData.setValue(expenditureList);
    }

    public void setData(ArrayList<Expenditure> expenditureList){
        this.expenditureList = expenditureList;
        listMutableLiveData.setValue(expenditureList);
    }

    public MutableLiveData<List<Expenditure>> getListMutableLiveData(){
        return listMutableLiveData;
    }

    public LiveData<Expenditure> getLiveData(Expenditure expenditure) {
        MutableLiveData<Expenditure> expenditureLiveData = new MutableLiveData<>();
        for (Expenditure item : expenditureList) {
            if (item.getId() == expenditure.getId()) {
                expenditureLiveData.setValue(item);
                break;
            }
        }
        return expenditureLiveData;
    }

    public void addExpenditure(Expenditure expenditure){
        expenditureList.add(expenditure);
        listMutableLiveData.setValue(expenditureList);
    }
}
