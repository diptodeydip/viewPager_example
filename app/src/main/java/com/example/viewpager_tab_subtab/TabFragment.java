package com.example.viewpager_tab_subtab;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONObject;


public class TabFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1", TAG = "temp";
    SwipeRefreshLayout refreshLayout;
    HistoryListAdapter adapter;
    Context mContext;
    ImageButton imgbtn;
    EditText editText;
    JSONObject info = new JSONObject();
    JSONArray array = new JSONArray();
    int tabPosition = 0;

    public TabFragment(Context context) {
        mContext = context;
        // Required empty public constructor
    }

    public static TabFragment newInstance(int tabPosition, Context context) {
        TabFragment fragment = new TabFragment(context);
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, tabPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        tabPosition = getArguments().getInt(ARG_PARAM1);

        try {
            info = MainActivity.getJsonObject("info.json",mContext);
            array = info.getJSONArray(tabPosition+"");
        }catch (Exception e){}

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.activity_tab_fragment, container, false);
        Log.d(TAG, "onCreateView: ");
        init(currentView);
        createList(currentView);
        return currentView;
    }

    private void init(View view){
        refreshLayout = view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });

        editText = view.findViewById(R.id.value);
        imgbtn = view.findViewById(R.id.add);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt = editText.getText().toString();
                if(!txt.isEmpty()){
                    adapter.list.add(txt);
                    try {
                        array.put(txt);
                        info.put(tabPosition+"",array);
                    }catch (Exception e){}
                    refreshLayout.setRefreshing(true);
                    adapter.notifyDataSetChanged();
                    refreshLayout.setRefreshing(false);
                    MainActivity.saveFileLocally("info.json",info.toString(),mContext);
                }
            }
        });
    }

    private void createList(View view){
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new HistoryListAdapter(array);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}