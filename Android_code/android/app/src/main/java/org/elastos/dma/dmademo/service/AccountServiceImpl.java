package org.elastos.dma.dmademo.service;

import org.elastos.dma.base.entity.did.DIDAccount;
import org.elastos.dma.base.entity.ela.ElaAccount;
import org.elastos.dma.base.entity.eth.EthAccount;
import org.elastos.dma.base.passport.DidRpc;
import org.elastos.dma.base.util.HdSupport;
import org.elastos.dma.base.wallet.ElaWallet;
import org.elastos.dma.base.wallet.EthWallet;
import org.elastos.dma.service.utility.entity.Account;
import org.elastos.dma.utility.exception.ApiException;

public class AccountServiceImpl implements AccountService {
    private DidRpc did;
    private EthWallet ethWallet;
    private ElaWallet elaWallet;
    public AccountServiceImpl(String ip) {
        did = new DidRpc(ip);
        ethWallet = new EthWallet(ip);
        elaWallet = new ElaWallet(ip);
    }
    /**
     * 创建DID,附带：eth wallet、elaWallet
     *
     * @return response
     */
    @Override
    public Account create() {
        DIDAccount didAccount = did.create(HdSupport.MnemonicType.ENGLISH);
        ElaAccount elaAccount = elaWallet.exportByMnemonic(didAccount.getMnemonic());
        EthAccount ethAccount = null;
        try {
            ethAccount = ethWallet.exportByMnemonic(didAccount.getMnemonic());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        Account account = new Account();
        account.setDidAccount(didAccount);
        account.setElaAccount(elaAccount);
        account.setEthAccount(ethAccount);
        return account;
    }

    /**
     * 根据私钥导出多钱包
     * @param privateKey 私钥
     * @return
     */
    @Override
    public Account exportMultiWalletByPrivateKey(String privateKey) {
        DIDAccount didAccount = did.exportByPrivateKey(privateKey);
        ElaAccount elaAccount = elaWallet.exportByPrivateKey(privateKey);
        EthAccount ethAccount = null;
        try {
            ethAccount = ethWallet.exportByPrivateKey(privateKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        Account account = new Account();
        account.setDidAccount(didAccount);
        account.setElaAccount(elaAccount);
        account.setEthAccount(ethAccount);
        return account;
    }
    /**
     * 根据助记词导出多钱包
     * @param mnemonic 助记词
     * @return
     */
    @Override
    public Account exportMultiWalletByMnemonic(String mnemonic) {
        DIDAccount didAccount = did.exportByMnemonic(mnemonic);
        ElaAccount elaAccount = elaWallet.exportByMnemonic(mnemonic);
        EthAccount ethAccount = null;
        try {
            ethAccount = ethWallet.exportByMnemonic(mnemonic);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        Account account = new Account();
        account.setDidAccount(didAccount);
        account.setElaAccount(elaAccount);
        account.setEthAccount(ethAccount);

        return account;
    }
}
