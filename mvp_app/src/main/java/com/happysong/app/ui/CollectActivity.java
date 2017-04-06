package com.happysong.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.happysong.app.bean.RndomInfo;
import com.happysong.app.db.DBHelper;
import com.happysong.app.ui.masonry.MasonryAdapter;
import com.happysong.app.ui.masonry.RecycleItemClickListener;
import com.happysong.app.ui.masonry.SpacesItemDecoration;

import java.util.List;


public class CollectActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.happysong.app.R.layout.collect_activity);


        DBHelper helper = new DBHelper(this);
        List<RndomInfo> rndomInfos = helper.queryRandomList();

        Toolbar tb = (Toolbar) findViewById(com.happysong.app.R.id.mian_toolbar);
        tb.setTitle("点滴记录");
        tb.setNavigationIcon(com.happysong.app.R.drawable.ic_arrow_back_white);
        tb.setNavigationOnClickListener(v -> finish());

        //set recycleview
        recyclerView = (RecyclerView) findViewById(com.happysong.app.R.id.recycler);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        RecycleItemClickListener itemClickListener = new RecycleItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Log.e("position","="+position);
                Intent intent = new Intent();
                intent.putExtra(CollectDetailActivity.INFO_OBJ_ID, rndomInfos.get(position).getObjectId());
                intent.setClass(CollectActivity.this, CollectDetailActivity.class);
                startActivity(intent);
            }
        };

        MasonryAdapter adapter = new MasonryAdapter(this, rndomInfos, itemClickListener);
        recyclerView.setAdapter(adapter);
        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);
    }
}
