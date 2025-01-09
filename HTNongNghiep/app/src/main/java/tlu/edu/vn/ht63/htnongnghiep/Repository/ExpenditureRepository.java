package tlu.edu.vn.ht63.htnongnghiep.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import tlu.edu.vn.ht63.htnongnghiep.Model.Expenditure;

public class ExpenditureRepository {
    private static ExpenditureRepository instance;
    private final MutableLiveData<Expenditure> expenditureLiveData = new MutableLiveData<>();

    private ExpenditureRepository() {}

    public static synchronized ExpenditureRepository getInstance() {
        if (instance == null) {
            instance = new ExpenditureRepository();
        }
        return instance;
    }

    public LiveData<Expenditure> getExpenditureLiveData() {
        return expenditureLiveData;
    }

    public void updateExpenditure(Expenditure expenditure) {
        expenditureLiveData.setValue(expenditure);
    }
}
