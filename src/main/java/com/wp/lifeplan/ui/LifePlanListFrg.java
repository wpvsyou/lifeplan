package com.wp.lifeplan.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wp.lifeplan.LpApplication;
import com.wp.lifeplan.R;
import com.wp.lifeplan.model.beans.LpDetailsBean;
import com.wp.lifeplan.model.db.LpChangedObserver;
import com.wp.lifeplan.model.db.LpDbHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.wp.lifeplan.ui.CollectTimeLinearView.SDF_LOG2;

/**
 * Created by wangpeng on 2017/5/21.
 */

public class LifePlanListFrg extends Fragment
        implements LpChangedObserver, OnItemClickListener {
    private View mRoot;
    private RecyclerView mRecyclerView;
    private LayoutInflater mInflater;
    private CustomRecyclerAdapter mAdapter;
    private LpDbHelper mDbHelper;
    private final static List<LpDetailsBean> DATA_ARRAYS = new ArrayList<LpDetailsBean>();

    public static LifePlanListFrg newInstance() {
        return new LifePlanListFrg();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(getActivity());
        mDbHelper = new LpDbHelper();
        mAdapter = new CustomRecyclerAdapter();
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    public void onLpChanged() {
        new AsyncLoadLpDetailsTask().execute();
    }

    @Override
    public void onItemClick(LpDetailsBean lpDetailsBean) {
        DetailActivity.startShowLpDetail(getActivity(), lpDetailsBean);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    private class AsyncLoadLpDetailsTask extends AsyncTask<Void, Void, List<LpDetailsBean>> {

        @Override
        protected List<LpDetailsBean> doInBackground(Void... params) {
            return mDbHelper.queryAllLp();
        }

        @Override
        protected void onPostExecute(final List<LpDetailsBean> lpDetailsBeen) {
            super.onPostExecute(lpDetailsBeen);
            Activity act = getActivity();
            if (act != null && !act.isFinishing()) {
                act.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        synchronized (DATA_ARRAYS) {
                            DATA_ARRAYS.clear();
                            DATA_ARRAYS.addAll(lpDetailsBeen);
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.life_plan_list_layout, container, false);
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        /*mRecyclerView.setRemoveListener(new CustomSwipeView.RemoveListener() {
            @Override
            public void removeItem(CustomSwipeView.RemoveDirection direction, int position) {
                synchronized (DATA_ARRAYS) {
                    LpDetailsBean lpDetailsBean = DATA_ARRAYS.get(position);
                    if (lpDetailsBean != null) {
                        DATA_ARRAYS.remove(lpDetailsBean);
                        mDbHelper.deleteLp(lpDetailsBean.getUuid());
                    }
                }
            }
        });*/
        new AsyncLoadLpDetailsTask().execute();
        LpApplication.getInstance().getDatabase().regsiterObserver(this);
    }

    private class CustomRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(mInflater.inflate(R.layout.life_plan_item_layout, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final LpDetailsBean lpDetailsBean = DATA_ARRAYS.get(position);
            holder.headIdea.setText(lpDetailsBean.getIdea());

            holder.tableStatus.setText(lpDetailsBean.getStatus());

            holder.tablePlan.setText(lpDetailsBean.getPlan());

            holder.tableLevel.setText(lpDetailsBean.getLevel());

            holder.subTime.setText(SDF_LOG2.format(new Date(lpDetailsBean.getGeneratePlanData())));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(lpDetailsBean);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return DATA_ARRAYS.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView headIdea;
        TextView subTime;
        TextView tableLevel;
        TextView tablePlan;
        TextView tableStatus;

        public ViewHolder(View view) {
            super(view);
            itemView = view.findViewById(R.id.item_view);
            this.headIdea = (TextView) view.findViewById(R.id.item_idea);
            this.subTime = (TextView) view.findViewById(R.id.time);
            this.tableLevel = (TextView) view.findViewById(R.id.table_level);
            this.tablePlan = (TextView) view.findViewById(R.id.table_plan);
            this.tableStatus = (TextView) view.findViewById(R.id.table_status);
        }
    }
}
