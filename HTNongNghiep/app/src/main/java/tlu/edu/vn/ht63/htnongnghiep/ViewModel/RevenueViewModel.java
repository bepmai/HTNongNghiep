package tlu.edu.vn.ht63.htnongnghiep.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;
import tlu.edu.vn.ht63.htnongnghiep.Model.Revenue;

public class RevenueViewModel extends ViewModel {

    private MutableLiveData<List<Revenue>> listMutableLiveData;
    private ArrayList<Revenue> revenueArrayList;

    public RevenueViewModel(){
        listMutableLiveData = new MutableLiveData<>();
        initData();
    }

    public void initData(){
        revenueArrayList = new ArrayList<>();
        listMutableLiveData.setValue(revenueArrayList);
    }

    public void setData(ArrayList<Revenue> revenueArrayList){
        this.revenueArrayList = revenueArrayList;
        listMutableLiveData.setValue(revenueArrayList);
    }

    public MutableLiveData<List<Revenue>> getListMutableLiveData(){
        return listMutableLiveData;
    }

    public LiveData<Revenue> getLiveData(Revenue revenue) {
        MutableLiveData<Revenue> revenueMutableLiveData = new MutableLiveData<>();
        for (Revenue item : revenueArrayList) {
            if (item.getId() == revenue.getId()) {
                revenueMutableLiveData.setValue(item);
                break;
            }
        }
        return revenueMutableLiveData;
    }

    public void addRevenue(Revenue revenue){
        revenueArrayList.add(revenue);
        listMutableLiveData.setValue(revenueArrayList);
    }
}
