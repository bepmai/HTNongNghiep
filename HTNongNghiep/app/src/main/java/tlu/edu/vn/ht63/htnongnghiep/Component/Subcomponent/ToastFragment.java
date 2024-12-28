package tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import tlu.edu.vn.ht63.htnongnghiep.Component.Interface.OnToastDismissListener;
import tlu.edu.vn.ht63.htnongnghiep.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ToastFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ToastFragment extends Fragment {

    private String message;

    private int typeIcon;
    public ToastFragment() {
    }

    public  ToastFragment(int typeIcon, String message){
        this.typeIcon = typeIcon;
        this.message = message;
    }
    public static ToastFragment newInstance() {
        ToastFragment fragment = new ToastFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private OnToastDismissListener onToastDismissListener;

    public void setOnToastDismissListener(OnToastDismissListener listener) {
        this.onToastDismissListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.feature_toast, container, false);
        TextView textView = view.findViewById(R.id.message);
        ImageView imageView = view.findViewById(R.id.icon);
        textView.setText(this.message);
        if(typeIcon==1){
            imageView.setImageResource(R.drawable.ic_success);
        }
        else if(typeIcon==2){
            imageView.setImageResource(R.drawable.ic_warning);
        }
        else{
            imageView.setImageResource(R.drawable.ic_error);
        }
        return view;
    }

    public void showToast(FragmentManager fragmentManager,int containerId) {
        FragmentTransaction fragmentTransaction  = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out,
                R.anim.enter_from_bottom,
                R.anim.exit_to_bottom
        );
        fragmentTransaction.add(containerId,this);
        fragmentTransaction.commit();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            fragmentManager.beginTransaction().remove(this).commit();
            if (onToastDismissListener != null) {
                onToastDismissListener.onToastDismiss();
            }
        }, 2000);
    }

    public void showOverlayToast(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        View toastView = LayoutInflater.from(context).inflate(R.layout.feature_toast, null);
        TextView textView = toastView.findViewById(R.id.message);
        ImageView imageView = toastView.findViewById(R.id.icon);
        textView.setText(this.message);
        if(typeIcon==1){
            imageView.setImageResource(R.drawable.ic_success);
        }
        else if(typeIcon==2){
            imageView.setImageResource(R.drawable.ic_warning);
        }
        else{
            imageView.setImageResource(R.drawable.ic_error);
        }
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        params.y = 350;

        Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);

        toastView.setAnimation(fadeIn);
        toastView.startAnimation(fadeIn);
        windowManager.addView(toastView, params);

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            toastView.startAnimation(fadeOut);
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                windowManager.removeView(toastView);
            }, 500);
        }, 2000);
    }

}