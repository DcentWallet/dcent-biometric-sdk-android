package com.dcentwallet.dcentsdksample.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dcentwallet.dcentsdksample.R;
import com.dcentwallet.dcentsdksample.databinding.FragmentKlaytnTestBinding;

/**
 * A fragment representing a list of Items.
 */
public class KlaytnTestFragment extends DialogFragment {

    private FragmentKlaytnTestBinding binding;
    private String operationItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public KlaytnTestFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static KlaytnTestFragment newInstance() {
        return new KlaytnTestFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        binding = FragmentKlaytnTestBinding.inflate(inflater);

        String[] klaytnTestList = new String[] {
                getResources().getString(R.string.list_opt_Klaytn_LegacyTransaction),
                getResources().getString(R.string.list_opt_Klaytn_ValueTransaction),
                getResources().getString(R.string.list_opt_Klaytn_ValueTransaction_D),
                getResources().getString(R.string.list_opt_Klaytn_ValueTransaction_PD),
                getResources().getString(R.string.list_opt_Klaytn_ValueMemoTx),
                getResources().getString(R.string.list_opt_Klaytn_ValueMemoTx_D),
                getResources().getString(R.string.list_opt_Klaytn_ValueMemoTx_PD),
                getResources().getString(R.string.list_opt_Klaytn_CancelTx),
                getResources().getString(R.string.list_opt_Klaytn_CancelTx_D),
                getResources().getString(R.string.list_opt_Klaytn_CancelTx_PD),
                getResources().getString(R.string.list_opt_Klaytn_KRC20Tx),
                getResources().getString(R.string.list_opt_Klaytn_KRC20Tx_D),
                getResources().getString(R.string.list_opt_Klaytn_KRC20Tx_PD),
        };
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, klaytnTestList);
        binding.listView.setAdapter(adapter);
        binding.listView.setItemsCanFocus(true);
        binding.listView.setChoiceMode
                (ListView.CHOICE_MODE_SINGLE);
        binding.listView.setOnItemClickListener(OnItemClickListener);

        builder.setView(binding.getRoot().getRootView());
        builder.setTitle("Klaytn Test List")
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
        return builder.create();
    }

    private final AdapterView.OnItemClickListener OnItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            operationItem = (String)parent.getItemAtPosition(position);
            fragmentInterfacer.onItemClick(operationItem);
            dismiss();
            //dismissAllowingStateLoss();
            /*dismissAllowingStateLoss();
            //getDialog().dismiss();*/
        }
    };

    public interface DcentSdkTestFragmentInterfacer {
        void onItemClick(String operationItem);
    }

    private DcentSdkTestFragmentInterfacer fragmentInterfacer;

    public void setFragmentInterfacer(DcentSdkTestFragmentInterfacer fragmentInterfacer){
        this.fragmentInterfacer = fragmentInterfacer;
    }

    @Override
    public void onDestroyView(){

        if (getDialog() != null && getRetainInstance()) {

            getDialog().setDismissMessage(null);

        }

        super.onDestroyView();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}