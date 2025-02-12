package wannabit.io.cosmostaion.dao;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;

import org.bitcoinj.crypto.DeterministicKey;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import wannabit.io.cosmostaion.base.BaseChain;
import wannabit.io.cosmostaion.base.BaseConstant;
import wannabit.io.cosmostaion.utils.WDp;
import wannabit.io.cosmostaion.utils.WUtil;

public class Account {
    public Long     id;
    public String   uuid;
    public String   nickName;
    public Boolean  isFavo;
    public String   address;
    public String   baseChain;

    public Boolean  hasPrivateKey;
    public String   resource;
    public String   spec;
    public Boolean  fromMnemonic;
    public String   path;

    public Boolean  isValidator;
    public Integer  sequenceNumber;
    public Integer  accountNumber;
    public Long     fetchTime;
    public int      msize;
    public Long     importTime;

    public String   lastTotal;

    public ArrayList<Balance>   balances;


    public static Account getNewInstance() {
        Account result = new Account();
        result.uuid = UUID.randomUUID().toString();
        return result;
    }

    public Account() {
    }

    public Account(Long id, String uuid, String nickName, boolean isFavo, String address,
                   String baseChain, boolean hasPrivateKey, String resource, String spec,
                   boolean fromMnemonic, String path, boolean isValidator, int sequenceNumber,
                   int accountNumber, Long fetchTime, int msize, long importTime, String lastTotal) {
        this.id = id;
        this.uuid = uuid;
        this.nickName = nickName;
        this.isFavo = isFavo;
        this.address = address;
        this.baseChain = baseChain;
        this.hasPrivateKey = hasPrivateKey;
        this.resource = resource;
        this.spec = spec;
        this.fromMnemonic = fromMnemonic;
        this.path = path;
        this.isValidator = isValidator;
        this.sequenceNumber = sequenceNumber;
        this.accountNumber = accountNumber;
        this.fetchTime = fetchTime;
        this.msize = msize;
        this.importTime = importTime;
        this.lastTotal = lastTotal;
    }

    public ArrayList<Balance> getBalances() {
        return balances;
    }

    public void setBalances(ArrayList<Balance> balances) {
        this.balances = balances;
    }


    public BigDecimal getAtomBalance() {
        BigDecimal result = BigDecimal.ZERO;
        if(balances == null || balances.size() == 0)  {
            return result;
        }
        for(Balance balance:balances) {
            if(balance.symbol.equals(BaseConstant.COSMOS_ATOM) || balance.symbol.equals(BaseConstant.COSMOS_MUON)) {
                result = balance.balance;
                break;
            }
        }
        return result;
    }

    public BigDecimal getPhotonBalance() {
        BigDecimal result = BigDecimal.ZERO;
        if(balances == null || balances.size() == 0) {
            return  result;
        }
        for(Balance balance:balances) {
            if(balance.symbol.equals(BaseConstant.COSMOS_PHOTON) || balance.symbol.equals(BaseConstant.COSMOS_PHOTINO)) {
                result = balance.balance;
                break;
            }
        }
        return result;
    }

    public BigDecimal getIrisBalance() {
        BigDecimal result = BigDecimal.ZERO;
        if(balances == null || balances.size() == 0)  {
            return result;
        }
        for(Balance balance:balances) {
            if(balance.symbol.equals(BaseConstant.COSMOS_IRIS_ATTO)) {
                result = balance.balance;
                break;
            }
        }
        return result;
    }

    public BigDecimal getBnbBalance() {
        BigDecimal result = BigDecimal.ZERO;
        if(balances == null || balances.size() == 0)  {
            return result;
        }
        for(Balance balance:balances) {
            if(balance.symbol.equals(BaseConstant.COSMOS_BNB)) {
                result = balance.balance;
                break;
            }
        }
        return result;
    }

    public BigDecimal getBnbTokenBalance(String symbol) {
        BigDecimal result = BigDecimal.ZERO;
        if(balances == null || balances.size() == 0)  {
            return result;
        }
        for(Balance balance:balances) {
            if(balance.symbol.equals(symbol)) {
                result = balance.balance;
                break;
            }
        }
        return result;
    }

    public BigDecimal getIrisTokenBalance(String symbol) {
        BigDecimal result = BigDecimal.ZERO;
        if(balances == null || balances.size() == 0)  {
            return result;
        }
        for(Balance balance:balances) {
            if(balance.symbol.split("-")[0].equals(symbol)) {
                result = balance.balance;
                break;
            }
        }
        return result;
    }


    public SpannableString getLastTotal(Context c, BaseChain chain) {
        if (TextUtils.isEmpty(lastTotal)) {
            return SpannableString.valueOf("--");
        }
        try {
            return WDp.getDpAmount(c, new BigDecimal(lastTotal), 6, chain);

        } catch (Exception e) {
            return SpannableString.valueOf("--");
        }

    }
}
