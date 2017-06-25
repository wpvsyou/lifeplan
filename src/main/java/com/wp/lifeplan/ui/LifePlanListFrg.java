package com.wp.lifeplan.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raizlabs.android.dbflow.runtime.FlowContentObserver;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.wp.lifeplan.R;
import com.wp.lifeplan.model.LifePlan;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.ContentValues.TAG;
import static com.wp.lifeplan.ui.CollectTimeLinearView.SDF_LOG2;

/**
 * Created by wangpeng on 2017/5/21.
 */

public class LifePlanListFrg extends Fragment implements OnItemClickListener {
    private View mRoot;
    private RecyclerView mRecyclerView;
    private LayoutInflater mInflater;
    private CustomRecyclerAdapter mAdapter;
    private final static List<LifePlan> DATA_ARRAYS = new ArrayList<LifePlan>();

    public static LifePlanListFrg newInstance() {
        return new LifePlanListFrg();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mInflater = LayoutInflater.from(getActivity());
        mAdapter = new CustomRecyclerAdapter();
        mAdapter.setOnItemClickListener(this);

        FlowContentObserver observer = new FlowContentObserver();
        // registers for callbacks from the specified table
        observer.registerForContentChanges(getActivity(), LifePlan.class);
        FlowContentObserver.OnModelStateChangedListener modelChangeListener = new
                FlowContentObserver.OnModelStateChangedListener() {

            @Override
            public void onModelStateChanged(@Nullable Class<?> table,
                                            BaseModel.Action action,
                                            @NonNull SQLOperator[] primaryKeyValues) {
                Log.i(TAG, "onModelStateChanged");
                asyncQueryLifePlanList();
            }
        };
        observer.addModelChangeListener(modelChangeListener);
    }

    @Override
    public void onItemClick(LifePlan lpDetailsBean) {
        DetailActivity.startShowLpDetail(getActivity(), lpDetailsBean);
    }

    @Override
    public void onItemLongClick(View view, int position) {

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
        asyncQueryLifePlanList();
    }

    private void asyncQueryLifePlanList() {
        Log.i(TAG, "asyncQueryLifePlanList");
        List<LifePlan> lps = SQLite.select()
                .from(LifePlan.class)
                .queryList();

        DATA_ARRAYS.clear();
                DATA_ARRAYS.addAll(lps);
                if (getActivity() != null && !getActivity().isFinishing()) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
       /*         }
        SQLite.select().from(LifePlan.class).async().queryResultCallback(new QueryTransaction.QueryResultCallback<LifePlan>() {
            @Override
            public void onQueryResult(QueryTransaction<LifePlan> queryTransaction, @NonNull CursorResult<LifePlan> cursorResult) {
                Log.i(TAG, "size: " + cursorResult.getCount());

            }
        });*/
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
            final LifePlan lpDetailsBean = DATA_ARRAYS.get(position);
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
