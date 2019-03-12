package jlu.edu.cn.lsh.controller;


import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.builder.AlipayTradeQueryRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;

import java.io.*;

import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import jlu.edu.cn.lsh.allclass.PageConstants;
import jlu.edu.cn.lsh.allclass.PaymentUtil;
import jlu.edu.cn.lsh.po.*;
import jlu.edu.cn.lsh.service.CartitemService;
import jlu.edu.cn.lsh.service.OrderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private CartitemService cartitemService;

    //获取页面上的页码，传入其它出了数字以外的，抛异常，设为默认值1
    private int getPc(HttpServletRequest request){
        int pc = 1;
        String param = request.getParameter("pc");
        if (param != null&&!param.trim().isEmpty()){
            try {
                pc = Integer.parseInt(param);
            }catch (RuntimeException e){ }
        }
        return pc;
    }

    private String getUrl(HttpServletRequest request){
        String s1 = request.getRequestURI();//获得？前内容
        String s2 = request.getQueryString();//获得？后内容
        String s3;
        if (s2==null||s2==""||s2==""){
            s3 = s1+"?";
        }else {
            s3 = s1 + "?" + s2;
        }
        String url;
        int index = s3.lastIndexOf("pc=");
        if (index !=-1){
            url = s3.substring(0,index);
        }else {
            url = s3;
        }
        return url;
    }

    //我的订单
    @RequestMapping("/myOrders")
    public String myOrders(HttpServletResponse response, HttpServletRequest request, Model model) throws Exception {
    /*  private int pc;//当前页码，页面传过来的
        private int tp;//总页数，tr/ps
        private int tr;//总记录数,sql查询
        private int ps;//订单每页记录数，自定义8，可修改
        private String url;//第一次查询时的条件，地址*/
        int pc = getPc(request);
        String url = getUrl(request);
        int ps = PageConstants.Order_Page_Size;
        User user = (User) request.getSession().getAttribute("sessionUser");
        String uid = user.getUid();
        int tr = orderService.findOrderCountByUid(uid);
        int tp = (tr % ps==0)?(tr/ps):(tr/ps+1);
        List<OrderCustom> orderCustomList = orderService.findOrderByUid(pc,uid);
        for (int i = 0;i<orderCustomList.size();i++){
            String oid = orderCustomList.get(i).getOid();
            List<Orderitem> orderitemList = orderService.findOrderItemsByOid(oid);
            orderCustomList.get(i).setOrderitemList(orderitemList);
        }
        OrderVo orderVo = new OrderVo();
        orderVo.setPs(ps);
        orderVo.setPc(pc);
        orderVo.setTp(tp);
        orderVo.setTr(tr);
        orderVo.setUrl(url);
        orderVo.setUid(uid);

        request.setAttribute("pb",orderVo);
        request.setAttribute("orderCustomList",orderCustomList);
        return "/jsps/order/list.jsp";
    }

    //创建订单
    @RequestMapping("/createOrder")
    public String createOrder(HttpServletRequest request,HttpServletResponse response, Model model)throws Exception{

        String cartItemIds = request.getParameter("cartItemIds");
        Object[] cartItemIdsArray = cartItemIds.split(",");
        String address = request.getParameter("address");
        BigDecimal total = new BigDecimal(0);
        User user = (User) request.getSession().getAttribute("sessionUser");
        String uid = user.getUid();
        BigDecimal b1;
        BigDecimal b2;

        List<Orderitem> orderitemList = new ArrayList<Orderitem>();
        for (int i = 0;i<cartItemIdsArray.length;i++){
            CartitemVo cartitemVo = cartitemService.findCartItemByCartItemId(cartItemIdsArray[i]+"");
            b1 = BigDecimal.valueOf(cartitemVo.getQuantity());
            b2 = cartitemVo.getCurrprice();
            total = total.add(b1.multiply(b2));
        }
        OrderCustom orderCustom = orderService.insertOrder(total,address,uid);
        for (int i = 0;i<cartItemIdsArray.length;i++){
            CartitemVo cartitemVo = cartitemService.findCartItemByCartItemId(cartItemIdsArray[i]+"");
            cartitemService.deleteCartItemByCartItemId(cartItemIdsArray[i]+"");
            Orderitem orderitem = orderService.insertOrderitem(cartitemVo.getQuantity(),cartitemVo.getSubtotal(),cartitemVo.getBid(),cartitemVo.getBname(),cartitemVo.getCurrprice(),cartitemVo.getImageB(),orderCustom.getOid());
            orderitemList.add(orderitem);
        }
        orderCustom.setOrderitemList(orderitemList);
        request.setAttribute("order",orderCustom);
        return "/jsps/order/ordersucc.jsp";
    }

    //加载订单信息，用于“查看订单”
    @RequestMapping("/load")
    public String load(HttpServletRequest request,HttpServletResponse response,Model model)throws Exception{
        String oid = request.getParameter("oid");
        String btn = request.getParameter("btn");
        OrderCustom orderCustom = orderService.findOrderByOid(oid);
        List<Orderitem> orderitemList = orderService.findOrderItemsByOid(oid);
        BigDecimal b1;
        BigDecimal b2;
        for (int i = 0;i<orderitemList.size();i++){
            b1 = orderitemList.get(i).getCurrprice();
            b2 = BigDecimal.valueOf(orderitemList.get(i).getQuantity());
            orderitemList.get(i).setSubtotal(b1.multiply(b2));
        }
        orderCustom.setOrderitemList(orderitemList);
        request.setAttribute("order",orderCustom);
        request.setAttribute("btn",btn);
        return "/jsps/order/desc.jsp";
    }

    //取消订单
    @RequestMapping("/cancel")
    public String cancel(HttpServletRequest request,HttpServletResponse response,Model model)throws Exception{

        String oid = request.getParameter("oid");
        int status = orderService.findStatusByOid(oid);
        if (status==0){
            request.setAttribute("code","error");
            request.setAttribute("msg","查询状态错误！");
            return "forward:/jsps/msg.jsp";
        }
        if (status!=1){
            request.setAttribute("code","error");
            request.setAttribute("msg","状态错误，不能取消订单");
            return "forward:/jsps/msg.jsp";
        }else {
            orderService.updateStatusByOid(oid,5);
            request.setAttribute("code","success");
            request.setAttribute("msg","恭喜您操作成功，订单已取消！");
            return "forward:/jsps/msg.jsp";
        }
    }

    //确认订单
    @RequestMapping("/confirm")
    public String confirm(HttpServletRequest request,HttpServletResponse response,Model model)throws Exception{

        String oid = request.getParameter("oid");
        int status = orderService.findStatusByOid(oid);
        if (status==0){
            request.setAttribute("code","error");
            request.setAttribute("msg","查询状态错误！");
            return "forward:/jsps/msg.jsp";
        }
        if (status!=3){
            request.setAttribute("code","error");
            request.setAttribute("msg","状态错误，无法确认收货！");
            return "forward:/jsps/msg.jsp";
        }else {
            orderService.updateStatusByOid(oid,4);
            request.setAttribute("code","success");
            request.setAttribute("msg","恭喜您确认收货，交易成功！");
            return "forward:/jsps/msg.jsp";
        }
    }

    //准备支付
    @RequestMapping("/paymentPre")
    public String paymentPre(HttpServletRequest request,HttpServletResponse response,Model model)throws Exception{

        String oid = request.getParameter("oid");
        OrderCustom orderCustom = orderService.findOrderByOid(oid);
        request.setAttribute("order",orderCustom);
        return "forward:/jsps/order/pay.jsp";
    }

    //支付
    @RequestMapping("/payment")
    public String payment(HttpServletRequest request,HttpServletResponse response,Model model)throws Exception{

        //Properties props = new Properties();
        //props.load(this.getClass().getClassLoader().getResourceAsStream("payment.properties"));
        //String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";

        String p0_Cmd = "Buy";//业务类型，固定值Buy
        String p1_MerId = "10001126856";//商号编码，在易宝的唯一标识
        String p2_Order = request.getParameter("oid");//订单编码
        String p3_Amt = "0.01";//支付金额
        String p4_Cur = "CNY";//交易币种，固定值CNY
        String p5_Pid = "";//商品名称
        String p6_Pcat = "";//商品种类
        String p7_Pdesc = "";//商品描述
        String p8_Url = "http://localhost:8080/goods/order/back.action";//在支付成功后，易宝会访问这个地址。
        String p9_SAF = "";//送货地址
        String pa_MP = "";//扩展信息
        String pd_FrpId = request.getParameter("yh");//支付通道
        String pr_NeedResponse = "1";//应答机制，固定值1

        String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
                p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse, keyValue);

        StringBuilder sb = new StringBuilder("https://www.yeepay.com/app-merchant-proxy/node");
        sb.append("?").append("p0_Cmd=").append(p0_Cmd);
        sb.append("&").append("p1_MerId=").append(p1_MerId);
        sb.append("&").append("p2_Order=").append(p2_Order);
        sb.append("&").append("p3_Amt=").append(p3_Amt);
        sb.append("&").append("p4_Cur=").append(p4_Cur);
        sb.append("&").append("p5_Pid=").append(p5_Pid);
        sb.append("&").append("p6_Pcat=").append(p6_Pcat);
        sb.append("&").append("p7_Pdesc=").append(p7_Pdesc);
        sb.append("&").append("p8_Url=").append(p8_Url);
        sb.append("&").append("p9_SAF=").append(p9_SAF);
        sb.append("&").append("pa_MP=").append(pa_MP);
        sb.append("&").append("pd_FrpId=").append(pd_FrpId);
        sb.append("&").append("pr_NeedResponse=").append(pr_NeedResponse);
        sb.append("&").append("hmac=").append(hmac);

        response.sendRedirect(sb.toString());
        return null;
    }

    //支付完成返回
    @RequestMapping("/back")
    public String back(HttpServletRequest request,HttpServletResponse response,Model model)throws Exception{

        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");
        String hmac = request.getParameter("hmac");

        String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
        Boolean b = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId,
                r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType,
                keyValue);
        if(!b) {
            request.setAttribute("code", "error");
            request.setAttribute("msg", "无效的签名，支付失败！（你蒙谁呢？）");
            return "f:/jsps/msg.jsp";
        }
        if(r1_Code.equals("1")) {
            orderService.updateStatusByOid(r6_Order, 2);
            //判断是重定向还是点对点
            if(r9_BType.equals("1")) {
                request.setAttribute("code", "success");
                request.setAttribute("msg", "恭喜，支付成功！");
                return "f:/jsps/msg.jsp";
            } else if(r9_BType.equals("2")) {
                response.getWriter().print("success");
            }
        }

        return null;
    }




    //支付宝支付准备提价订单
    @RequestMapping("/payment2")
    public String payment2(HttpServletRequest request,HttpServletResponse response,Model model)throws Exception{
        String oid = request.getParameter("oid");
        OrderCustom orderCustom = orderService.findOrderByOid(oid);
        request.setAttribute("order",orderCustom);
        return "forward:/jsps/alipay/trade_precreate.jsp";
    }

    //配置静态数据的订单信息，并生成二维码
    @RequestMapping("/precreate2")
    public String precreate2(HttpServletRequest request,HttpServletResponse response,Model model)throws Exception{
        String oidtest = request.getParameter("oid");
        String oid1 = "111222333444";
        String suject1 = "订单名称";
        String totalAmount1 = "0.01";
        String undiscountableAmount1 = "0.00";
        String body1 = "订单描述";
        String outTradeNo = oid1;//订单号
        String subject = suject1;//订单名称
        String totalAmount = totalAmount1;//订单总金额
        String undiscountableAmount = undiscountableAmount1;//订单不可打折金额
        String body = body1;// 订单描述
        Log log = LogFactory.getLog("trade_precreate");

        if(outTradeNo!=""){
            Configs.init("zfbinfo.properties");
            AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

            String sellerId = "";// 卖家支付宝账号ID，没有就是配置文件中的pid
            String operatorId = "test_operator_id";// 商户操作员编号
            String storeId = "test_store_id";//商户门店编号

            // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)
            ExtendParams extendParams = new ExtendParams();
            extendParams.setSysServiceProviderId("2088100200300400500");
            String timeoutExpress = "120m";// 支付超时，为120分钟

            //创建商品订单表,id、名称、单价、数量
            List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
            GoodsDetail goods1 = GoodsDetail.newInstance("goods_id001", "全麦小面包", 1500, 1);
            goodsDetailList.add(goods1);
            GoodsDetail goods2 = GoodsDetail.newInstance("goods_id002", "黑人牙刷", 505, 2);
            goodsDetailList.add(goods2);

            AlipayTradePrecreateRequestBuilder builder =new AlipayTradePrecreateRequestBuilder()
                    .setSubject(subject)
                    .setTotalAmount(totalAmount)
                    .setOutTradeNo(outTradeNo)
                    .setUndiscountableAmount(undiscountableAmount)
                    .setSellerId(sellerId)
                    .setBody(body)
                    .setOperatorId(operatorId)
                    .setStoreId(storeId)
                    .setExtendParams(extendParams)
                    .setTimeoutExpress(timeoutExpress)
                    .setGoodsDetailList(goodsDetailList);

            AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
            switch (result.getTradeStatus()) {
                case SUCCESS:
                    request.setAttribute("number",0);
                    log.info("支付宝预下单成功: )");

                    AlipayTradePrecreateResponse res = result.getResponse();

                    String basePath = request.getSession().getServletContext().getRealPath("/");
                    String fileName = String.format("images%sqr-%s.png", File.separator, res.getOutTradeNo());
                    String filePath = new StringBuilder(basePath).append(fileName).toString();

                    //System.out.println("<img src=\"" + fileName + "\" />");
                    File file = ZxingUtils.getQRCodeImge(res.getQrCode(), 256, filePath);
                    String oldfilepath = file.toString();
                    String newfilepath = "F:\\project1222\\goods-master\\src\\main\\webapp\\erweima_img\\";
                    File start = new File(oldfilepath);
                    File end = new File(newfilepath+"qr-"+oid1+".png");
                    method4(start,end);

                    break;

                case FAILED:
                    request.setAttribute("number",1);
                    log.error("支付宝预下单失败!!!");
                    break;

                case UNKNOWN:
                    log.error("系统异常，预下单状态未知!!!");
                    break;

                default:
                    log.error("不支持的交易状态，交易返回异常!!!");
                    break;
            }
            request.setAttribute("oid",oid1);

            return "forward:/jsps/alipay/erweima.jsp";
        }else {
            return "forward:/jsps/alipay/erweima.jsp";
        }
    }

    //刷新二维码使用的
    @RequestMapping("/flush")
    public String flush(HttpServletRequest request,HttpServletResponse response,Model model)throws Exception{
       String oid = request.getParameter("oid");
        request.setAttribute("number",1);
       request.setAttribute("oid",oid);
       return "forward:/jsps/alipay/erweima.jsp";
    }

    //复制文件到erweima_img下，便于页面访问图片，但是需要项目刷新路径才可以，这里没时间就先不解决了
    private static void method4(File start, File end) throws IOException {
                 // 缓冲字节流一次读取一个字节数组
                BufferedInputStream bfi = new BufferedInputStream (new FileInputStream(start));
                 BufferedOutputStream bfo = new BufferedOutputStream(new FileOutputStream(end));
               byte[] by = new byte[1024];
                 int len = 0;
                while((len = bfi.read(by)) != -1){
                         bfo.write(by,0,len);
                     }
                 bfi.close();
                 bfo.close();

    }


    //支付完成返回2
    @RequestMapping("/back2")
    public String back2(HttpServletRequest request,HttpServletResponse response,Model model)throws Exception {

        //静态数据的订单号111222333444
        String outtradeno = request.getParameter("oid");

        Log log = LogFactory.getLog("trade_query");

        if (outtradeno != null) {
            Configs.init("zfbinfo.properties");
            AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
            String outTradeNo = outtradeno;
            AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder().setOutTradeNo(outTradeNo);
            AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
            System.out.println(result.getTradeStatus().toString());
            switch (result.getTradeStatus()) {
                case SUCCESS:
                    System.out.println("成功");
                    log.info("查询返回该订单支付成功: )");

                    AlipayTradeQueryResponse resp = result.getResponse();

                    log.info(resp.getTradeStatus());
                    log.info(resp.getFundBillList());
                    request.setAttribute("code","success");
                    request.setAttribute("msg","支付成功！");
                    break;

                case FAILED:
                    System.out.println("失败");
                    log.error("查询返回该订单支付失败!!!");
                    request.setAttribute("code","error");
                    request.setAttribute("msg","订单支付失败！");

                    break;

                case UNKNOWN:
                    System.out.println("未知");
                    log.error("系统异常，订单支付状态未知!!!");
                    request.setAttribute("code","error");
                    request.setAttribute("msg","订单支付失败！");
                    break;

                default:
                    System.out.println("异常");
                    log.error("不支持的交易状态，交易返回异常!!!");
                    request.setAttribute("code","error");
                    request.setAttribute("msg","订单支付失败！");
                    break;
            }
            System.out.println(result.getResponse().getBody());
            request.setAttribute("oid", outtradeno);
            return "forward:/jsps/msg.jsp";
        }
        return "forward:/jsps/msg.jsp";
    }
}
