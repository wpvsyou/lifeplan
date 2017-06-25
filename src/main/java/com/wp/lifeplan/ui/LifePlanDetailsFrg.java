package com.wp.lifeplan.ui;

import android.app.*;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.google.gson.JsonSyntaxException;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.wp.lifeplan.LpApplication;
import com.wp.lifeplan.R;
import com.wp.lifeplan.model.LifePlan;
import com.wp.lifeplan.model.LifePlan_Table;
import com.wp.lifeplan.service.LocationService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.wp.lifeplan.ui.CollectTimeLinearView.SDF_LOG;
import static com.wp.lifeplan.ui.CollectTimeLinearView.SDF_LOG3;

/**
 * Created by wangpeng on 2017/5/20.
 */

public class LifePlanDetailsFrg extends Fragment implements View.OnClickListener {
    private final static String TAG = "LifePlanDetailsFrg";
    private final static String EXTRA_LPDTS_JSON = "extra_lpdts_json";
    private static UiMode mMode;
    private CollectTimeLinearView mGenerateLpTime;
    private CollectTimeLinearView mPlanDoneTime;
    private HorizontalSpinnerView mPlanLevel;
    private HorizontalSpinnerView mPlan;
    private HorizontalSpinnerView mPlanStatus;
    private ExtendTextView mIdea;
    private ExtendTextView mTarget;
    private ExtendTextView mNextStep;
    private EditText mStartLocation;
    private EditText mLastLocation;
    private Button mSave;
    private Button mCancel;
    private View mRootView;
    private LifePlan mLifePlan;

    public static LifePlanDetailsFrg newInstance() {
        LifePlanDetailsFrg f = new LifePlanDetailsFrg();
        mMode = UiMode.createMode;
        return f;
    }

    public static LifePlanDetailsFrg newInstance(LifePlan lpDetailsBean) {
        if (lpDetailsBean == null) {
            return newInstance();
        }
        LifePlanDetailsFrg f = new LifePlanDetailsFrg();
        Bundle b = new Bundle(1);
        b.putString(EXTRA_LPDTS_JSON, lpDetailsBean.toJson());
        f.setArguments(b);
        mMode = UiMode.recoveryMode;
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.life_plan_details, container, false);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSave = (Button) view.findViewById(R.id.save);
        mSave.setOnClickListener(this);
        mCancel = (Button) view.findViewById(R.id.cancel);
        mCancel.setOnClickListener(this);
        mGenerateLpTime = (CollectTimeLinearView) view.findViewById(R.id.generate_date_item);
        mPlanDoneTime = (CollectTimeLinearView) view.findViewById(R.id.schedule_time_item);
        mPlanLevel = (HorizontalSpinnerView) view.findViewById(R.id.level_item);
        mPlan = (HorizontalSpinnerView) view.findViewById(R.id.plan_item);
        mPlanStatus = (HorizontalSpinnerView) view.findViewById(R.id.status_item);
        mIdea = (ExtendTextView) view.findViewById(R.id.idea);
        mTarget = (ExtendTextView) view.findViewById(R.id.target);
        mNextStep = (ExtendTextView) view.findViewById(R.id.next_step);
        mStartLocation = (EditText) view.findViewById(R.id.create_location);
        mLastLocation = (EditText) view.findViewById(R.id.last_location);
        mGenerateLpTime.setEnable(false);
        mGenerateLpTime.setHint(getString(R.string.generate_lp_time_hine));

        mPlanLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updatePlanSelected(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        do {
            Bundle b = getArguments();
            if (b == null) {
                break;
            }
            String json = b.getString(EXTRA_LPDTS_JSON);
            if (TextUtils.isEmpty(json)) {
                break;
            }
            try {
                LifePlan lifePlan = new LifePlan(json);
                mLifePlan = SQLite
                        .select()
                        .from(LifePlan.class)
                        .where(LifePlan_Table.uuid.is(lifePlan.uuid))
                        .querySingle();
            } catch (JsonSyntaxException j) {
                j.printStackTrace();
                break;
            }
            resetUiByLpDetailsBean(mLifePlan);
        } while (false);
    }

    private void updatePlanSelected(final int position) {
        Activity activity = getActivity();
        if (activity != null && !activity.isFinishing()) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPlan.setDefaultSelectedPosition(position);
                }
            });
        }
    }

    private void resetUiByLpDetailsBean(LifePlan lpDetailsBean) {
        mGenerateLpTime.setDate(SDF_LOG.format(new Date(lpDetailsBean.getGeneratePlanData())));
        mPlanLevel.putUserSelectedStr(lpDetailsBean.getLevel());
        mPlan.putUserSelectedStr(lpDetailsBean.getPlan());
        mPlanStatus.putUserSelectedStr(lpDetailsBean.getStatus());
        mPlanDoneTime.setDate(SDF_LOG3.format(new Date(lpDetailsBean.getScheduledTime())));
        mIdea.setText(lpDetailsBean.getIdea());
        mTarget.setText(lpDetailsBean.getTarget());
        mNextStep.setText(lpDetailsBean.getNextStep());
        mLastLocation.setText(lpDetailsBean.getFinalAddress());
        mStartLocation.setText(lpDetailsBean.getAddress());

        mGenerateLpTime.setEnable(false);

        if (isFinalStatus()) {
            mPlanDoneTime.setEnable(false);
            mIdea.setEnable(false);
            mTarget.setEnable(false);
            mNextStep.setEnable(false);
            mSave.setEnabled(false);
            mCancel.setEnabled(false);
            mPlanLevel.setEnable(false);
            mPlan.setEnable(false);
            mPlanStatus.setEnable(false);
        }
    }

    private boolean isFinalStatus() {
        if (mLifePlan == null) {
            return false;
        }

        String[] statusArrays = getResources().getStringArray(R.array.status_array);
        List<String> statusList = Arrays.asList(statusArrays);
        String finalStatusKey = statusList.get(statusList.size() - 1);
        return (TextUtils.equals(mLifePlan.getStatus(), finalStatusKey));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.save:
                savePlanInfo();
                break;
            case R.id.cancel:
                Activity activity = getActivity();
                if (activity != null) {
                    Toast.makeText(activity, R.string.save_fp_failed, Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
                break;
        }
    }

    private void savePlanInfo() {
        long generatePlDate = mGenerateLpTime.getDate();
        String level = mPlanLevel.getUserSelectedStr();
        String plan = mPlan.getUserSelectedStr();
        String status = mPlanStatus.getUserSelectedStr();
        long scheduleTime = mPlanDoneTime.getDate();
        String idea = mIdea.getText();
        String target = mTarget.getText();
        String nextStep = mNextStep.getText();

        final long curDate = System.currentTimeMillis();
        mGenerateLpTime.setDate(SDF_LOG.format(curDate));
        if (mLifePlan == null) {
            mLifePlan = new LifePlan(UUID.randomUUID().toString(),
                    curDate, idea,
                    level, plan, status, target, nextStep, scheduleTime);
            Log.i(TAG, "generate a new life plan: " + mLifePlan.getUuid());
        }

        mLifePlan.setStatus(status);
        mLifePlan.setLevel(level);
        mLifePlan.setPlan(plan);
        mLifePlan.setIdea(idea);
        mLifePlan.setTarget(target);
        mLifePlan.setNextStep(nextStep);
        if (scheduleTime > 0) {
            mLifePlan.setScheduledTime(scheduleTime);
        }

        Log.i(TAG, "save-> " + mLifePlan);
        if (!checkLpDetailsBeanValid(mLifePlan)) {
            return;
        }

        final ProgressDialogFragment progressDialogFragment = ProgressDialogFragment.
                newInstance(getString(R.string.dialog_message_request_location));
        progressDialogFragment.show(getFragmentManager(), "RequestLocationDialog");
        final EnterLocationDialog.Callback callback = new EnterLocationDialog.Callback() {
            @Override
            public void onEnterLocationSuccess(String address) {
                if (mLifePlan != null) {
                    if (!isFinalStatus() && mMode == UiMode.createMode) {
                        mLifePlan.setAddress(address);
                    } else {
                        mLifePlan.setFinalAddress(address);
                    }

                    if (mMode == UiMode.createMode) {
                        Log.i(TAG, "insert2: " + mLifePlan);
                        mLifePlan.save();
                    } else {
                        Log.i(TAG, "update2: " + mLifePlan);
                        mLifePlan.update();
                    }
                }
                Activity activity = getActivity();
                Toast.makeText(getActivity(), "添加时间规划成功！", Toast.LENGTH_SHORT).show();
                if (activity != null) {
                    activity.finish();
                }
            }

            @Override
            public void onEnterLocationFailed() {
            }
        };

        final DecisionDialogFragment.Callback askUserEnterLocationCallback = new
                DecisionDialogFragment.Callback() {
                    @Override
                    public void userSelectedYes() {
                        EnterLocationDialog.newInstance(callback, isFinalStatus())
                                .show(getFragmentManager(), "EnterLocationDialog");
                    }

                    @Override
                    public void userSelectedNo() {
                        showToast(getString(R.string.details_invaild_location_error));
                    }
                };

        LpApplication.getInstance().getLocationService().asyncRequestLocation(
                new LocationService.OnLocationCallback() {
                    @Override
                    public void onLocation(BDLocation bdLocation) {
                        progressDialogFragment.dismiss();
                        if (bdLocation == null || TextUtils.isEmpty(bdLocation.getAddrStr())) {
                            Log.i(TAG, "get location failed!");
                            DecisionDialogFragment.newInstance(
                                    getString(R.string.ask_user_enter_location_dialog_message),
                                    askUserEnterLocationCallback
                            )
                                    .show(getFragmentManager(), "AskUserEnterLocation");
                        } else {
                            Log.i(TAG, "get address: " + bdLocation.getAddrStr());
                            if (!isFinalStatus() && mMode == UiMode.createMode) {
                                mLifePlan.setAddress(bdLocation.getAddrStr());
                                mLifePlan.setLatitude(String
                                        .valueOf(bdLocation.getLatitude()));
                                mLifePlan.setLongitude(String
                                        .valueOf(bdLocation.getLongitude()));
                            } else {
                                mLifePlan.setFinalAddress(bdLocation.getAddrStr());
                                mLifePlan.setFinalLatitude(String
                                        .valueOf(bdLocation.getLatitude()));
                                mLifePlan.setFinalLongitude(String
                                        .valueOf(bdLocation.getLongitude()));
                            }
                            if (mLifePlan.getScheduledTime() <= 0) {
                                mLifePlan.setScheduledTime(System.currentTimeMillis());
                            }
                            if (mMode == UiMode.createMode) {
                                Log.i(TAG, "insert1: " + mLifePlan);
                                mLifePlan.save();
                            } else {
                                Log.i(TAG, "update1: " + mLifePlan);
                                mLifePlan.update();
                            }

                            Activity activity = getActivity();
                            Toast.makeText(getActivity(), R.string.add_pl_success, Toast.LENGTH_SHORT).show();
                            if (activity != null) {
                                activity.finish();
                            }
                        }
                    }
                });
    }

    private boolean checkLpDetailsBeanValid(LifePlan lpDetailsBean) {
        if (lpDetailsBean.getGeneratePlanData() <= 0) {
            showToast(R.string.details_invalide_must_set_create_time);
            return false;
        } else if (TextUtils.isEmpty(lpDetailsBean.getIdea())) {
            showToast(getString(R.string.details_invalide_must_set_idea));
            return false;
        }
        return true;
    }

    private void showToast(int id) {
        showToast(getString(id));
    }

    private void showToast(String toast) {
        Snackbar.make(mRootView, R.string.details_invalide_must_set_create_time,
                Snackbar.LENGTH_SHORT).show();
    }

    public enum UiMode {
        recoveryMode, createMode,
    }

    public static class EnterLocationDialog extends DialogFragment {
        private final static String ARGS_DETAILS = "args_details";
        private final static String ARGS_IS_FINAL_STATUS = "args_is_final_status";
        private static Callback sCallback;

        public static EnterLocationDialog newInstance(Callback callback,
                                                      boolean isFinalStatus) {
            EnterLocationDialog df = new EnterLocationDialog();
            Bundle b = new Bundle(2);
            sCallback = callback;
            b.putBoolean(ARGS_IS_FINAL_STATUS, isFinalStatus);
            df.setArguments(b);
            return df;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final View v = LayoutInflater.from(getActivity()).inflate(
                    R.layout.enter_location_dialog_layout,
                    null
            );
            builder.setView(v);
            final EditText locationEt = (EditText) v.findViewById(R.id.location_et);
            Bundle b = getArguments();
            final LifePlan lpDetailsBean = b.getParcelable(ARGS_DETAILS);
            final boolean isFinalStatus = b.getBoolean(ARGS_IS_FINAL_STATUS, false);

            builder.setTitle(R.string.enter_location_dialog_title);
            builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (locationEt.getText() != null
                            && !TextUtils.isEmpty(locationEt.getText().toString().trim())) {
                        sCallback.onEnterLocationSuccess(locationEt.getText().toString());
                    } else {
                        sCallback.onEnterLocationFailed();
                        Toast.makeText(getActivity(), R.string.details_invalid_enter_location_failed,
                                Toast.LENGTH_SHORT).show();
                    }
                    dismiss();
                }
            });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity(), R.string.details_invaild_enter_loctaion_none,
                            Toast.LENGTH_SHORT).show();
                    sCallback.onEnterLocationFailed();
                    dismiss();
                }
            });
            builder.setCancelable(false);

            return builder.create();
        }

        public interface Callback {
            void onEnterLocationSuccess(String address);

            void onEnterLocationFailed();
        }
    }
}
