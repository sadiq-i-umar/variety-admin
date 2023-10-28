package com.example.varietyadmin.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.varietyadmin.R;
import com.example.varietyadmin.activities.AddCustomerActivity;
import com.example.varietyadmin.activities.HomeActivity;
import com.example.varietyadmin.activities.SelectExistingCustomer;
import com.example.varietyadmin.adapter.CustomerAdapter;
import com.example.varietyadmin.api.RetrofitClient;
import com.example.varietyadmin.models.Customer;
import com.example.varietyadmin.models.CustomersResponse;
import com.example.varietyadmin.models.LoginResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private EditText itemsEditText;
    private RadioGroup myRadioGroup;
    private RadioButton radioButton, pickUpRadioButton, deliveryRadioButton;
    private Button selectDateBtn, addCustomerButton, selectExistingBtn, submitBtn;

    private List<Customer> customerList;

    private TextView customerName, selectedDateTV;

    private LinearLayout custLinearLayout;

    private ImageView customerIcon;

    public RecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordFragment newInstance(String param1, String param2) {
        RecordFragment fragment = new RecordFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        itemsEditText = view.findViewById(R.id.itemsEditText);
        myRadioGroup = view.findViewById(R.id.radioGroup);
        pickUpRadioButton = view.findViewById(R.id.pickUpRadioButton);
        deliveryRadioButton = view.findViewById(R.id.deliveryRadioButton);
        submitBtn = view.findViewById(R.id.submitButton);
        selectExistingBtn = view.findViewById(R.id.existingCustomerButton);
        custLinearLayout = view.findViewById(R.id.custLinearLayout);
        customerName = view.findViewById(R.id.customerName);
        addCustomerButton = view.findViewById(R.id.addCustomerButton);
        customerIcon = view.findViewById(R.id.customerIcon);
        selectDateBtn = view.findViewById(R.id.selectDateBtn);
        selectedDateTV = view.findViewById(R.id.selectedDateTV);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String orderItems = sharedPreferences.getString("orderItems", "");
        int checkedButtonId = sharedPreferences.getInt("checkedButtonId", -1);

        itemsEditText.setText(orderItems);

        if (checkedButtonId == 1) {
            pickUpRadioButton.setChecked(true);
        } else if (checkedButtonId == 2) {
            deliveryRadioButton.setChecked(true);
        }

        Boolean state = getActivity().getIntent().getBooleanExtra("fromSelectCustomer", false);
        int cust_id = getActivity().getIntent().getIntExtra("cust_id", 0);
        if (state) {
            custLinearLayout.setVisibility(View.VISIBLE);
            Call<CustomersResponse> call = RetrofitClient.getInstance().getApi().getAllCustomers();

            call.enqueue(new Callback<CustomersResponse>() {
                @Override
                public void onResponse(Call<CustomersResponse> call, Response<CustomersResponse> response) {
                    customerList = response.body().getCustomers();
                    for (int i = 0; i < customerList.size(); i++) {
                        if (cust_id == customerList.get(i).getId()) {
                            customerName.setText(customerList.get(i).getName());
                        }
                    }
                }

                @Override
                public void onFailure(Call<CustomersResponse> call, Throwable t) {

                }
            });
        }

        selectDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
//                                if (dayOfMonth < 10 && monthOfYear < 10) {
//                                    selectedDateTV.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
//                                }
//                                if (dayOfMonth < 10 && monthOfYear >= 10) {
//                                    selectedDateTV.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
//                                }
//                                if (dayOfMonth >= 10 && monthOfYear < 10) {
//                                    selectedDateTV.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
//                                }
                                if (dayOfMonth < 10 && monthOfYear < 9) {
                                    selectedDateTV.setText(year + "-0" + (monthOfYear + 1) + "-0" + dayOfMonth);
                                }
                                if (dayOfMonth < 10 && monthOfYear > 8) {
                                    selectedDateTV.setText(year + "-" + (monthOfYear + 1) + "-0" + dayOfMonth);
                                }
                                if (dayOfMonth > 10 && monthOfYear < 9) {
                                    selectedDateTV.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                                }
                                if (dayOfMonth >= 10 && monthOfYear > 8) {
                                    selectedDateTV.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                                }
                                selectedDateTV.setVisibility(View.VISIBLE);
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });

        addCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddCustomerActivity.class);
                startActivity(intent);
            }
        });

        selectExistingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectExistingCustomer.class);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("orderItems", itemsEditText.getText().toString());
                int buttonId = myRadioGroup.getCheckedRadioButtonId();
                radioButton = view.findViewById(buttonId);
                if (buttonId % 2 == 1) {
                    editor.putInt("checkedButtonId", 1);
                } else if (buttonId % 2 == 0) {
                    editor.putInt("checkedButtonId", 2);
                } else if (buttonId == -1) {
                    editor.putInt("checkedButtonId", -1);
                }
                editor.commit();
                startActivity(intent);
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String items = itemsEditText.getText().toString();
                int radioButtonId = myRadioGroup.getCheckedRadioButtonId();
                radioButton = view.findViewById(radioButtonId);
                String date = selectedDateTV.getText().toString();
                String collection_method = radioButton.getText().toString();
                Call<LoginResponse> call = RetrofitClient
                        .getInstance()
                        .getApi()
                        .createorderwithdate(items, collection_method, cust_id, date);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                        if(response.code() == 201) {
                            LoginResponse dr = response.body();
                            Toast.makeText(getActivity(), dr.getMessage(), Toast.LENGTH_LONG).show();
                            itemsEditText.setText("");
                            myRadioGroup.clearCheck();
                            custLinearLayout.setVisibility(View.GONE);
                            getActivity().getIntent().putExtra("cust_id", 0);
                            getActivity().getIntent().putExtra("fromSelectCustomer", false);
                        } else if (response.code() == 422){
                            Toast.makeText(getActivity(), "Error in creating order", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("orderItems", "");
        editor.putInt("checkedButtonId", -1);
        getActivity().getIntent().putExtra("cust_id", 0);
        getActivity().getIntent().putExtra("fromSelectCustomer", false);
        editor.commit();
    }
}