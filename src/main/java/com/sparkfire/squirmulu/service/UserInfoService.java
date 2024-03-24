package com.sparkfire.squirmulu.service;

import com.sparkfire.squirmulu.dao.UserCoinDao;
import com.sparkfire.squirmulu.entity.UserCoin;
import com.sparkfire.squirmulu.entity.request.*;
import com.sparkfire.squirmulu.exception.ServiceException;
import com.sparkfire.squirmulu.mapper.SysUserMapper;
import com.sparkfire.squirmulu.pojo.SysUser;
import com.sparkfire.squirmulu.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserInfoService {
    @Autowired
    UserCoinDao userInfoDao;

    @Autowired
    SysUserMapper userMapper;

    @Value("${http.path}")
    private String httpPath;


    public CoinUpdateRsp updateCoin(CoinUpdateReq req) {
        //todo 一致性问题解决
        long now = System.currentTimeMillis() / 1000;
        String table = "user_coin_" + TimeUtil.getMonthFormat(now);
        for (CoinUpdateTarget target : req.getTargets()) {
            switch (target.getUpdate_rule()) {
                case "plus": {
                    userInfoDao.updateAmountByCoinType(req.getId(), target.getCoin_type(), target.getAmount(), now);
                    userInfoDao.updateAmountByCoinTypeMonthly(table, req.getId(), target.getCoin_type(), target.getAmount(), now);
                    break;
                }
                case "minus": {
                    userInfoDao.updateAmountByCoinType(req.getId(), target.getCoin_type(), -target.getAmount(), now);
                    userInfoDao.updateAmountByCoinTypeMonthly(table, req.getId(), target.getCoin_type(), -target.getAmount(), now);
                    break;
                }
                case "cover": {
                    userInfoDao.updateAmountSetByCoinType(req.getId(), target.getCoin_type(), target.getAmount(), now);
                    userInfoDao.updateAmountSetByCoinTypeMonthly(table, req.getId(), target.getCoin_type(), target.getAmount(), now);
                    break;
                }
                default:
                    throw new ServiceException("更新类型错误");
            }
        }
        return new CoinUpdateRsp(req.getTargets());

    }

    public CoinPullRsp pullCoin(CoinPullReq req) {

        List<CoinPullRecord> recordList = new ArrayList<>();
        switch (req.getPull_rule()) {
            case "unique": {
                UserCoin coin = userInfoDao.findById(req.getTarget_id(), req.getCoin_type());
                recordList.add(new CoinPullRecord(coin.getId(), "", coin.getCoin_type(), coin.getAmount()));
                break;
            }
            case "monthly_rank": {
                List<UserCoin> coins = userInfoDao.findMonthRank("user_coin_" + TimeUtil.getMonthFormat(System.currentTimeMillis() / 1000), req.getCoin_type());
                recordList = coins.stream().map(coin -> {
                    SysUser user = userMapper.getSysUserInfoById(coin.getId());
                    return new CoinPullRecord(coin.getId(), httpPath + user.getImage(), coin.getCoin_type(), coin.getAmount());
                }).collect(Collectors.toList());
                break;
            }
            default:
                throw new ServiceException("更新类型错误");
        }

        return new CoinPullRsp(recordList);

    }
}
