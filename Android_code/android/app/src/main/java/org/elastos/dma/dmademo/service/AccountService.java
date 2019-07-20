package org.elastos.dma.dmademo.service;

import org.elastos.dma.service.utility.entity.Account;

public interface AccountService {

    /**
     * 创建DID,附带：eth wallet、elaWallet
     *
     * @return response
     */
    Account create();

    /**
     * 根据私钥导出
     * @param privateKey
     * @return
     */
    Account exportMultiWalletByPrivateKey(String privateKey);

    /**
     * 根据助记词导出多钱包
     * @param mnemonic 助记词
     * @return
     */
    Account exportMultiWalletByMnemonic(String mnemonic);

}
