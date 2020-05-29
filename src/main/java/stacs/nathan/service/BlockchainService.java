package stacs.nathan.service;

import hashstacs.sdk.util.ChainConnector;
import io.stacs.nav.crypto.StacsECKey;
import org.springframework.stereotype.Service;
import stacs.nathan.entity.User;

@Service
public class BlockchainService {

  private static ChainConnector chainConnector;

  public void createWallet(User user){
    StacsECKey submitterKey = new StacsECKey();
    user.setPrivateKey(submitterKey.getHexPriKey());
    user.setWalletAddress(submitterKey.getHexAddress());
  }

}
