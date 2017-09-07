/**
 * 微信支付js
 *
 * @author pengkai <pengxiankaikai@163.com>
 * @since 2017/8/11
 */

/**
 * 微信支付
 * @param pay_amount 支付金额（分）
 * @param desc 支付描述
 * @param suc 成功回调
 * @param fail 失败回调
 */
function wx_pay(pay_amount, desc, suc, fail) {
    if (null == pay_amount || "" == pay_amount){
        $.alert("支付失败，没有填写支付金额");
        return;
    }
    var test1 = /^[0-9]*$/;
    if (!test1.test(pay_amount)){
        $.alert("支付失败，没有正确支付金额");
        return;
    }
    /**
     * 取到支付prepayid
     * @param val 支付金额(分)
     */
    $.ajax({
        url: "/wx/pay",
        type: "post",
        dataType: "json",
        data: {totalFee: pay_amount, body: desc},
        success: function (data) {
            if ("000" == data.code){
                var prepay_id = "prepay_id=" + data.item.prepayId;

                $.ajax({
                    url: "/wx/jssdk/sign",
                    type: "post",
                    dataType: "json",
                    data: {timeStamp: timestamp, nonceStr: nonceStr, packageStr: prepay_id, signType: 'MD5'},
                    success: function (data) {
                        if ("000" == data.code){
                            var paySign = data.item;
                            console.info("paySign 5 = " + paySign);
                            //微信支付
                            wx.chooseWXPay({
                                timestamp: timestamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                                nonceStr: nonceStr, // 支付签名随机串，不长于 32 位
                                package: prepay_id, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
                                signType: 'MD5', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
                                paySign: paySign, // 支付签名
                                success: function (res) {
                                    suc();
                                },
                                error: function () {
                                    console.info("config fail3");
                                    fail("支付订单失败，请稍后重试");
                                }
                            });
                        }else {
                            console.info("config fail4");
                            fail("获取支付订单失败，请稍后重试");
                        }
                    },
                    error: function () {
                        console.info("config fail5");
                        fail("支付订单失败，请稍后重试");
                    }
                })
            }else {
                console.info("config fail6");
                fail(data.message);
            }
        },
        error: function () {
            console.info("config fail7");
            fail("支付订单失败，请稍后重试");
        }
    })
}