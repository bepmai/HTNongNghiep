package tlu.edu.vn.ht63.htnongnghiep.Container.UI;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tlu.edu.vn.ht63.htnongnghiep.Activity.InfUserDetail;
import tlu.edu.vn.ht63.htnongnghiep.Activity.LibTreeAcitivity;
import tlu.edu.vn.ht63.htnongnghiep.Activity.LoginSignupActivity;
import tlu.edu.vn.ht63.htnongnghiep.Activity.RevenueExpenditureActivity;
import tlu.edu.vn.ht63.htnongnghiep.Activity.SignUpActivity;
import tlu.edu.vn.ht63.htnongnghiep.Component.Subcomponent.ToastFragment;
import tlu.edu.vn.ht63.htnongnghiep.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserRights#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserRights extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserRights() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserRights.
     */
    // TODO: Rename and change types and number of parameters

    public static UserRights newInstance(String param1, String param2) {
        UserRights fragment = new UserRights();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    CardView infUser, instruct, support, logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_rights, container, false);

        infUser = view.findViewById(R.id.infUser);
        instruct = view.findViewById(R.id.instruct);
        support = view.findViewById(R.id.support);
        logout = view.findViewById(R.id.logout);

        instruct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LibTreeAcitivity.class);
                startActivity(intent);
            }
        });

        infUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), InfUserDetail.class);
                startActivity(intent);
            }
        });

        support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RevenueExpenditureActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("userId"); // Xóa giá trị của key "userId"
                editor.apply(); // Lưu thay đổi
                ToastFragment toastFragment = new ToastFragment(1, "Đăng xuất thành công!");
                toastFragment.setOnToastDismissListener(() -> {
                    Intent intent = new Intent(getContext(), LoginSignupActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                });
                toastFragment.showToast(getActivity().getSupportFragmentManager(), R.id.main);
            }
        });
        return view;
    }
}