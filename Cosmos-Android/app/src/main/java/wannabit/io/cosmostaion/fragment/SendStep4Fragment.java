package wannabit.io.cosmostaion.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.math.BigDecimal;

import wannabit.io.cosmostaion.R;
import wannabit.io.cosmostaion.activities.SendActivity;
import wannabit.io.cosmostaion.base.BaseChain;
import wannabit.io.cosmostaion.base.BaseFragment;
import wannabit.io.cosmostaion.utils.WDp;

import static wannabit.io.cosmostaion.base.BaseConstant.COSMOS_BNB;
import static wannabit.io.cosmostaion.base.BaseConstant.COSMOS_IRIS;

public class SendStep4Fragment extends BaseFragment implements View.OnClickListener {

    private TextView        mSendAmount;
    private TextView        mFeeAmount;
    private RelativeLayout  mTotalSpendLayer;
    private TextView        mTotalSpendAmount, mTotalPrice;
    private TextView        mCurrentBalance, mRemainingBalance, mRemainingPrice;
    private TextView        mRecipientAddress, mMemo;
    private Button          mBeforeBtn, mConfirmBtn;
    private TextView        mDenomSendAmount, mDenomFeeType, mDenomTotalSpend, mDenomCurrentAmount, mDenomRemainAmount;

    public static SendStep4Fragment newInstance(Bundle bundle) {
        SendStep4Fragment fragment = new SendStep4Fragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView       = inflater.inflate(R.layout.fragment_send_step4, container, false);
        mSendAmount         = rootView.findViewById(R.id.send_amount);
        mFeeAmount          = rootView.findViewById(R.id.send_fees);
        mTotalSpendLayer    = rootView.findViewById(R.id.spend_total_layer);
        mTotalSpendAmount   = rootView.findViewById(R.id.spend_total);
        mTotalPrice         = rootView.findViewById(R.id.spend_total_price);
        mCurrentBalance     = rootView.findViewById(R.id.current_available_atom);
        mRemainingBalance   = rootView.findViewById(R.id.remaining_available_atom);
        mRemainingPrice     = rootView.findViewById(R.id.remaining_price);
        mRecipientAddress   = rootView.findViewById(R.id.recipient_address);
        mMemo               = rootView.findViewById(R.id.memo);
        mBeforeBtn          = rootView.findViewById(R.id.btn_before);
        mConfirmBtn         = rootView.findViewById(R.id.btn_confirm);
        mDenomSendAmount    = rootView.findViewById(R.id.send_amount_title);
        mDenomFeeType       = rootView.findViewById(R.id.send_fees_type);
        mDenomTotalSpend    = rootView.findViewById(R.id.spend_total_type);
        mDenomCurrentAmount = rootView.findViewById(R.id.current_available_title);
        mDenomRemainAmount  = rootView.findViewById(R.id.remaining_available_title);

        WDp.DpMainDenom(getContext(), getSActivity().mAccount.baseChain, mDenomFeeType);
        WDp.DpMainDenom(getContext(), getSActivity().mAccount.baseChain, mDenomTotalSpend);

        mBeforeBtn.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        return rootView;
    }


    @Override
    public void onRefreshTab() {
        BigDecimal toSendAmount   = new BigDecimal(getSActivity().mTargetCoins.get(0).amount);
        BigDecimal feeAmount      = new BigDecimal(getSActivity().mTargetFee.amount.get(0).amount);
        if (getSActivity().mBaseChain.equals(BaseChain.COSMOS_MAIN)) {
            mSendAmount.setText(WDp.getDpAmount(getContext(), toSendAmount, 6, getSActivity().mBaseChain));
            mFeeAmount.setText(WDp.getDpAmount(getContext(), feeAmount, 6, getSActivity().mBaseChain));
            mTotalSpendAmount.setText(WDp.getDpAmount(getContext(), feeAmount.add(toSendAmount), 6, getSActivity().mBaseChain));
            mTotalPrice.setText(WDp.getValueOfAtom(getContext(), getBaseDao(), feeAmount.add(toSendAmount)));

            BigDecimal currentAvai  = getSActivity().mAccount.getAtomBalance();
            mCurrentBalance.setText(WDp.getDpAmount(getContext(), currentAvai, 6, getSActivity().mBaseChain));
            mRemainingBalance.setText(WDp.getDpAmount(getContext(), currentAvai.subtract(toSendAmount).subtract(feeAmount), 6, getSActivity().mBaseChain));
            mRemainingPrice.setText(WDp.getValueOfAtom(getContext(), getBaseDao(), currentAvai.subtract(toSendAmount).subtract(feeAmount)));

        } else if (getSActivity().mBaseChain.equals(BaseChain.IRIS_MAIN)) {
            mDenomSendAmount.setText(getSActivity().mIrisToken.base_token.symbol.toUpperCase());
            mDenomCurrentAmount.setText(getSActivity().mIrisToken.base_token.symbol.toUpperCase());
            mDenomRemainAmount.setText(getSActivity().mIrisToken.base_token.symbol.toUpperCase());

            mSendAmount.setText(WDp.getDpAmount(getContext(), toSendAmount, getSActivity().mIrisToken.base_token.decimal, getSActivity().mBaseChain));
            mFeeAmount.setText(WDp.getDpAmount(getContext(), feeAmount, 18, getSActivity().mBaseChain));

            if (getSActivity().mIrisToken.base_token.id.equals(COSMOS_IRIS)) {
                mDenomSendAmount.setTextColor(getResources().getColor(R.color.colorIris));
                mDenomCurrentAmount.setTextColor(getResources().getColor(R.color.colorIris));
                mDenomRemainAmount.setTextColor(getResources().getColor(R.color.colorIris));

                mTotalSpendAmount.setText(WDp.getDpAmount(getContext(), feeAmount.add(toSendAmount), getSActivity().mIrisToken.base_token.decimal, getSActivity().mBaseChain));
                mTotalPrice.setText(WDp.getValueOfIris(getContext(), getBaseDao(), feeAmount.add(toSendAmount)));

                BigDecimal currentAvai  = getSActivity().mAccount.getIrisBalance();
                mCurrentBalance.setText(WDp.getDpAmount(getContext(), currentAvai, getSActivity().mIrisToken.base_token.decimal, getSActivity().mBaseChain));
                mRemainingBalance.setText(WDp.getDpAmount(getContext(), currentAvai.subtract(toSendAmount).subtract(feeAmount), getSActivity().mIrisToken.base_token.decimal, getSActivity().mBaseChain));
                mRemainingPrice.setText(WDp.getValueOfIris(getContext(), getBaseDao(), currentAvai.subtract(toSendAmount).subtract(feeAmount)));

            } else {
                mTotalSpendLayer.setVisibility(View.GONE);
                mTotalPrice.setVisibility(View.GONE);
                mRemainingPrice.setVisibility(View.GONE);

                BigDecimal currentAvai  = getSActivity().mAccount.getIrisTokenBalance(getSActivity().mIrisToken.base_token.symbol);
                mCurrentBalance.setText(WDp.getDpAmount(getContext(), currentAvai, getSActivity().mIrisToken.base_token.decimal, getSActivity().mBaseChain));
                mRemainingBalance.setText(WDp.getDpAmount(getContext(), currentAvai.subtract(toSendAmount), getSActivity().mIrisToken.base_token.decimal, getSActivity().mBaseChain));
            }

        } else if (getSActivity().mBaseChain.equals(BaseChain.BNB_MAIN)) {
            mDenomSendAmount.setText(getSActivity().mBnbToken.original_symbol.toUpperCase());
            mDenomCurrentAmount.setText(getSActivity().mBnbToken.original_symbol.toUpperCase());
            mDenomRemainAmount.setText(getSActivity().mBnbToken.original_symbol.toUpperCase());

            mSendAmount.setText(WDp.getDpAmount(getContext(), toSendAmount, 8, getSActivity().mBaseChain));
            mFeeAmount.setText(WDp.getDpAmount(getContext(), feeAmount, 8, getSActivity().mBaseChain));

            if (getSActivity().mBnbToken.symbol.equals(COSMOS_BNB)) {
                mDenomSendAmount.setTextColor(getResources().getColor(R.color.colorBnb));
                mDenomCurrentAmount.setTextColor(getResources().getColor(R.color.colorBnb));
                mDenomRemainAmount.setTextColor(getResources().getColor(R.color.colorBnb));

                mTotalSpendAmount.setText(WDp.getDpAmount(getContext(), feeAmount.add(toSendAmount), 8, getSActivity().mBaseChain));
                mTotalPrice.setText(WDp.getValueOfBnb(getContext(), getBaseDao(), feeAmount.add(toSendAmount)));

                BigDecimal currentAvai  = getSActivity().mAccount.getBnbBalance();
                mCurrentBalance.setText(WDp.getDpAmount(getContext(), currentAvai, 8, getSActivity().mBaseChain));
                mRemainingBalance.setText(WDp.getDpAmount(getContext(), currentAvai.subtract(toSendAmount).subtract(feeAmount), 8, getSActivity().mBaseChain));
                mRemainingPrice.setText(WDp.getValueOfBnb(getContext(), getBaseDao(), currentAvai.subtract(toSendAmount).subtract(feeAmount)));

            } else {
                mTotalSpendLayer.setVisibility(View.GONE);
                mTotalPrice.setVisibility(View.GONE);
                mRemainingPrice.setVisibility(View.GONE);

                BigDecimal currentAvai  = getSActivity().mAccount.getBnbTokenBalance(getSActivity().mBnbToken.symbol);
                mCurrentBalance.setText(WDp.getDpAmount(getContext(), currentAvai, 8, getSActivity().mBaseChain));
                mRemainingBalance.setText(WDp.getDpAmount(getContext(), currentAvai.subtract(toSendAmount), 8, getSActivity().mBaseChain));
            }

        }
        mRecipientAddress.setText(getSActivity().mTagetAddress);
        mMemo.setText(getSActivity().mTargetMemo);
    }

    @Override
    public void onClick(View v) {
        if(v.equals(mBeforeBtn)) {
            getSActivity().onBeforeStep();

        } else if (v.equals(mConfirmBtn)) {
            getSActivity().onStartSend();

        }

    }

    private SendActivity getSActivity() {
        return (SendActivity)getBaseActivity();
    }
}
