package com.zanclick.redpacket.core.controller.H5;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zanclick.redpacket.common.entity.Response;
import com.zanclick.redpacket.common.jms.JmsMessaging;
import com.zanclick.redpacket.common.jms.SendMessage;
import com.zanclick.redpacket.common.utils.DataUtils;
import com.zanclick.redpacket.common.utils.LoginContext;
import com.zanclick.redpacket.common.utils.MoneyUtils;
import com.zanclick.redpacket.common.utils.StringUtils;
import com.zanclick.redpacket.core.entity.CorrelationConfiguration;
import com.zanclick.redpacket.core.entity.RedPacket;
import com.zanclick.redpacket.core.entity.Wallet;
import com.zanclick.redpacket.core.query.RedPacketQuery;
import com.zanclick.redpacket.core.service.CorrelationConfigurationService;
import com.zanclick.redpacket.core.service.RedPacketService;
import com.zanclick.redpacket.core.service.WalletService;
import com.zanclick.redpacket.core.vo.RedPacketVo;
import com.zanclick.redpacket.core.vo.h5.WalletVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ Description   :  红包controller
 * @ Author        :  wusi
 * @ CreateDate    :  2020/12/7$ 18:11$
 */
@Slf4j
@RestController
@RequestMapping(value = "/h5/api/")
public class RedPacketController {
    @Autowired
    private RedPacketService redPacketService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private CorrelationConfigurationService correlationConfigurationService;

    @ApiOperation("查询红包")
    @RequestMapping(value = "queryRedPacket")
    @ResponseBody
    public Response<Page<RedPacketVo>> queryRedPacket(RedPacketQuery query) {
        LoginContext.RequestUser currentUser = LoginContext.getCurrentUser();
        //先查询此账号是否配置批量领取
        if(DataUtils.isEmpty(query.getTradeNo()) && DataUtils.isEmpty(query.getCustomPhone())){
            CorrelationConfiguration configuration=new CorrelationConfiguration();
            configuration.setUserName(currentUser.getUsername());
            CorrelationConfiguration queryOne = correlationConfigurationService.queryOne(configuration);
            if(DataUtils.isEmpty(queryOne)){
                return Response.ok(null);
            }else{
                //通过商户号匹配红包  type=1
                if(queryOne.getType().equals(1)){
                    query.setMerchantNo(queryOne.getCorrelationName());
                }
                //通过收银员手机号匹配红包  type=2
                if(queryOne.getType().equals(2)){
                    query.setCashierPhoneNo(queryOne.getCorrelationName());
                }
                //通过收款账号  type=3
                if(queryOne.getType().equals(3)){
                    query.setSellerNo(queryOne.getCorrelationName());
                }
            }
        }
        if (DataUtils.isEmpty(query.getLimit())) {
            query.setLimit(5);
        }
        if (DataUtils.isEmpty(query.getPage())) {
            query.setPage(0);
        }
        //仅显示待领取红包
        query.setState(RedPacket.State.WAITING.getCode());
        Pageable pageable= PageRequest.of(query.getPage(), query.getLimit());
        Page<RedPacket> page=redPacketService.queryPage(query,pageable);
        List<RedPacketVo> voList = new ArrayList<>();
        if (DataUtils.isEmpty(page)) {
            return Response.fail("无数据");
        }
        for (RedPacket packet : page.getContent()) {
            voList.add(getVo(packet));
        }
        Page<RedPacketVo>  voPage=new PageImpl<>(voList,pageable,page.getTotalElements());
        return Response.ok(voPage);
    }

    private RedPacketVo getVo(RedPacket packet) {
        RedPacketVo VO = new RedPacketVo();
        VO.setId(packet.getId());
        VO.setTradeNo(packet.getTradeNo());
        VO.setAmount(packet.getAmount());
        VO.setState(packet.getState());
        return VO;
    }

    @ApiOperation("领取红包")
    @RequestMapping(value = "getRedPacket")
    @ResponseBody
    public Response queryRedPacket(Long id) {
        if(DataUtils.isEmpty(id)){
            return Response.fail("id为空,未查询到红包信息!");
        }
        RedPacket packet = redPacketService.queryById(id);
        if(DataUtils.isEmpty(packet)){
            return Response.fail("未查询到红包信息!");
        }
        if(RedPacket.State.RECEIVED.getCode().equals(packet.getState())){
            return Response.fail("红包已领取!");
        }
        if(RedPacket.State.SUCCESS.getCode().equals(packet.getState())){
            return Response.fail("红包已到账!");
        }
        if(RedPacket.State.CANCLE.getCode().equals(packet.getState())){
            return Response.fail("红包已取消!");
        }
        LoginContext.RequestUser currentUser = LoginContext.getCurrentUser();
        JSONObject map=new JSONObject();
        map.put("id", id);
        map.put("userName",currentUser.getUsername());
        if(RedPacket.State.WAITING.getCode().equals(packet.getState())){
            SendMessage.sendMessage(JmsMessaging.RECEIVE_REDPACKET_MESSAGE,map.toJSONString());
        }

        return Response.ok("领取成功,请到钱包查看!");
    }
    //获取钱包详情
    @ApiOperation("获取钱包详情")
    @RequestMapping(value = "getWallet")
    @ResponseBody
    public Response<WalletVO> getWallet() {
        LoginContext.RequestUser currentUser = LoginContext.getCurrentUser();
        Wallet wallet=new Wallet();
        wallet.setUserName(currentUser.getUsername());
        Wallet query = walletService.queryOne(wallet);
        WalletVO walletVo = getWalletVo(query);
        return Response.ok(walletVo);
    }

    private WalletVO getWalletVo(Wallet query) {
        WalletVO vo= new WalletVO();
        vo.setCanWithdrawAmount(query.getCanWithdrawAmount());
        vo.setReceiveNo(query.getReceiveNo());
        vo.setTotalAmount(query.getTotalAmount());
        return vo;
    }
    //添加或修改提现账号
    @ApiOperation("添加或修改提现账号")
    @ResponseBody
    @RequestMapping(value = "/changeAccount")
    public Response<String> changeAccount(String account,String name){
        if(StringUtils.isChinese(account)){
            return Response.fail("账号信息不能包含汉字");
        }
        if(!StringUtils.isEmail(account)&&!StringUtils.isPhone(account)){
            return Response.fail("账号格式有误!");
        }
        LoginContext.RequestUser currentUser = LoginContext.getCurrentUser();
        Wallet query = walletService.findByUserName(currentUser.getUsername());
        if(DataUtils.isEmpty(query)){
            return Response.fail("操作失败,无此钱包账号!");
        }
        query.setReceiveName(name);
        query.setReceiveNo(account);
        try {
            walletService.updateById(query);
        } catch (Exception e) {
            log.error("操作失败,{},{}", account,name);
        }
        return Response.ok("操作成功！");
    }
    //提现接口
    @ApiOperation("提现")
    @ResponseBody
    @RequestMapping(value = "/transfer")
    public Response<String> transfer(String money){
        System.out.println(money.compareTo("0"));
        if(money.compareTo("0")<=0){
            return Response.fail("提现金额需大于0!");
        }
        System.out.println(money.compareTo("0"));
        LoginContext.RequestUser currentUser = LoginContext.getCurrentUser();
        Wallet query = walletService.findByUserName(currentUser.getUsername());
        if(DataUtils.isEmpty(query)){
            return Response.fail("提现失败,无此钱包账号!");
        }
        if(query.getState().equals(0)){
            return Response.fail("提现失败,此钱包账号已冻结!");
        }
        Boolean large = MoneyUtils.largeOrEqual(query.getCanWithdrawAmount(), money);
        if(!large){
            return Response.fail("提现金额不能大于余额!");
        }
        redPacketService.transfer(query,money);
        return Response.ok("提现成功");

    }
}
