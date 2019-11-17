<#import '../common/language.ftl' as Language>
<!DOCTYPE html>
<html lang="zh">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="format-detection" content="telephone=no">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <meta content="产品,试剂,医疗,北方所" name="Keywords">
        <meta name="description" content="北方所创立于1985年，隶属于中国核工业集团有限公司旗下的中国同辐股份有限公司（中国同辐，1763．HK），专注于体外诊断试剂产品的研发、生产、经营及服务，产品涵盖放射免疫、化学发光、酶联免疫、时间分辨、胶体金和生物原材料等技术平台，拥有150多项产品注册证书，形成了覆盖全国的营销网络，产品出口至亚非欧等10余个国家或地区，能够为体外诊断领域提供全面的产品解决方案和整体服务。">
        <title>咨询详情</title>

        <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="/css/wechat/advisory-detail.css">
        <!--[if lt IE 9]>
        <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv-printshiv.min.js"></script>
        <script src="/js/respond.min.js"></script>
        <![endif]-->
    </head>
    <body data-content="${(errorStatus)!''}">
    <div class="advisory-detail">

        <div class="advisory-msg" data-content="${(page.page?c)!'0'}/${(page.totalPage?c)!'0'}/${(page.limit?c)!'0'}/${(page.totalItem?c)!'0'}">
            <div class="advisory-more">
                <p>正在加载更多消息...</p>
            </div>
            <div class="msg-list">
                <#if msgList?? && msgList?size gt 0>
                    <#list msgList as msg>
                        <#-- 客户发的消息，当前用户为销售 -->
                        <#if msg.messageDirection == false && userType?? && userType == "sales">
                            <div class="msg-item left">
                                <div class="msg-avatar">
                                    <img src="http://img.bnibt.com/static/default_chat.png" alt="">
                                </div>
                                <div class="msg-detail">
                                    <p class="msg-owner">
                                        ${(msg.customerName)!'客户'}
                                    </p>
                                    <p class="msg-content">
                                        ${(msg.content)!'无'}
                                    </p>
                                </div>
                            </div>
                        <#-- 销售发的消息，当前用户为客户 -->
                        <#elseif msg.messageDirection == true && userType?? && userType == "user">
                            <div class="msg-item left">
                                <div class="msg-avatar">
                                    <img src="http://img.bnibt.com/static/offical.png" alt="">
                                </div>
                                <div class="msg-detail">
                                    <p class="msg-owner">
                                        ${(msg.salesName)!'销售'}
                                    </p>
                                    <p class="msg-content">
                                        ${(msg.content)!'无'}
                                    </p>
                                </div>
                            </div>
                        <#-- 客户发的消息，当前用户为客户 -->
                        <#elseif msg.messageDirection == false && userType?? && userType == "user">
                            <div class="msg-item right">
                                <div class="msg-detail">
                                    <p class="msg-owner">
                                        ${(msg.customerName)!'客户'}
                                    </p>
                                    <p class="msg-content">
                                        ${(msg.content)!'无'}
                                    </p>
                                </div>
                                <div class="msg-avatar">
                                    <img src="http://img.bnibt.com/static/default_chat.png" alt="">
                                </div>
                            </div>
                        <#-- 销售发的消息，当前用户为销售 -->
                        <#elseif msg.messageDirection == true && userType?? && userType == "sales">
                            <div class="msg-item right">
                                <div class="msg-detail">
                                    <p class="msg-owner">
                                        ${(msg.salesName)!'销售'}
                                    </p>
                                    <p class="msg-content">
                                        ${(msg.content)!'无'}
                                    </p>
                                </div>
                                <div class="msg-avatar">
                                    <img src="http://img.bnibt.com/static/offical.png" alt="">
                                </div>
                            </div>
                        </#if>
                    </#list>
                </#if>
            </div>
        </div>
        <div class="advisory-msg-control">
            <#if userType?? && userType == "sales">
                <div class="staff-more">
                    <button class="btn" role="button">更多</button>
                    <div class="staff-more-modal">
                        <p><a href="/wx/customerdata.html?id=${session.customerUserId}">客户资料</a></p>
                        <p><a href="/wx/wish.html?id=${session.customerUserId}">TA的心愿单</a></p>
                        <p><a href="tel:${(session.customerPhone)!''}">拨打客户电话</a></p>
                        <p><a href="/wx/changestaff.html?id=${session.customerUserId}">转出客户</a></p>
                    </div>
                </div>
            </#if>
            <div class="send-input">
                <input type="text" class="form-control" id="messageText" placeholder="请在这里输入...">
            </div>
            <div class="send-btn">
                <button class="btn send" role="button" data-content="${(userType)!'user'}">发送</button>
            </div>
        </div>
    </div>
        <script src="/js/jquery.min.js"></script>
        <script src="/js/jquery.cookie.js"></script>
        <script src="/js/bootstrap.min.js"></script>
        <script src="/js/wechat/jweixin-1.4.0.js"></script>
        <script src="/js/base.js"></script>
        <script src="/js/wechat/advisory-detail.js"></script>
    </body>
</html>