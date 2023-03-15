package com.synergy.synergydhrishtiplus.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.synergy.synergydhrishtiplus.R;
import com.synergy.synergydhrishtiplus.adapters.TransactionsAdapter;
import com.synergy.synergydhrishtiplus.data_model.TransactionModel;
import com.synergy.synergydhrishtiplus.room.DatabaseClient;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Transaction_fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Transaction_fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    RecyclerView transactionRecyclerView;

    public Transaction_fragment() {
    }

    public static Transaction_fragment newInstance(String param1, String param2) {
        Transaction_fragment fragment = new Transaction_fragment();
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
        View view= inflater.inflate(R.layout.fragment_transaction_fragment, container, false);
        init(view);
        getTransaction();
        return view;
    }

    private void getTransaction() {
        class GetTasks extends AsyncTask<Void, Void, List<TransactionModel>> {

            @Override
            protected List<TransactionModel> doInBackground(Void... voids) {
                try {
                    List<TransactionModel> taskList = DatabaseClient
                            .getInstance(getActivity())
                            .getAppDatabase()
                            .transactionDao()
                            .getAll().subList(0, 10);
                    return taskList;
                }
                catch (Exception e){
                    e.getLocalizedMessage();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<TransactionModel> tasks) {
                super.onPostExecute(tasks);
                if(tasks!=null) {
                    TransactionsAdapter adapter = new TransactionsAdapter(getActivity(), tasks);
                    transactionRecyclerView.setAdapter(adapter);
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    private void init(View view) {
        transactionRecyclerView     = view.findViewById(R.id.transactionRecyclerViews);
        transactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        transactionRecyclerView.addItemDecoration(itemDecorator);
    }
}