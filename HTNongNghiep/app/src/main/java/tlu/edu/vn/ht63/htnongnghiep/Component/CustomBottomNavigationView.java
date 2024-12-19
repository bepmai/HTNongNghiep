package tlu.edu.vn.ht63.htnongnghiep.Component;

import android.content.Context;
import android.util.AttributeSet;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomBottomNavigationView extends BottomNavigationView {

    public CustomBottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupCustomBehavior();
    }

    public CustomBottomNavigationView(Context context) {
        super(context);
        setupCustomBehavior();
    }

    private void setupCustomBehavior() {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) getChildAt(0);
        try {
            menuView.setLabelVisibilityMode(LABEL_VISIBILITY_LABELED);
            menuView.setItemHorizontalTranslationEnabled(false); // Tắt hiệu ứng di chuyển
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
