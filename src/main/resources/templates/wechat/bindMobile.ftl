<!DOCTYPE html>
<html lang="zh">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="format-detection" content="telephone=no">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
        <meta content="产品,试剂,医疗,北方所" name="Keywords">
        <meta name="description" content="北方所创立于1985年，隶属于中国核工业集团有限公司旗下的中国同辐股份有限公司（中国同辐，1763．HK），专注于体外诊断试剂产品的研发、生产、经营及服务，产品涵盖放射免疫、化学发光、酶联免疫、时间分辨、胶体金和生物原材料等技术平台，拥有150多项产品注册证书，形成了覆盖全国的营销网络，产品出口至亚非欧等10余个国家或地区，能够为体外诊断领域提供全面的产品解决方案和整体服务。">
        <title>绑定手机号</title>

        <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" type="text/css" href="/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="/css/login.css">
        <link rel="stylesheet" type="text/css" href="/css/wechat/bindMobile.css">
        <!--[if lt IE 9]>
        <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv-printshiv.min.js"></script>
        <script src="/js/respond.min.js"></script>
        <![endif]-->
    </head>
    <body data-content="${(errorStatus)!''}">
        <div class="bindMobile">
            <div class="banner">
                <p>为了提升您的体验，需要绑定手机号</p>
            </div>
            <div class="login-container">
                <div class="login-title">
                    请输入手机号
                </div>
                <form action="/index.html" class="bs-example" method="post">
                    <div class="login-field">
                        <input name="phone" type="homeNumber" placeholder="请输入手机号">
                    </div>
                    <div class="login-field">
                        <div class="login-field-input">
                            <input name="captcha" type="text" placeholder="请输入验证码">
                        </div>
                        <div class="login-field-sendCode">
                            <button id="sendCode" type="button">获取验证码</button>
                        </div>
                    </div>
                    <div class="login-control">
                        <button type="button" class="btn login-btn">绑定</button>
                    </div>
                    <div class="error-infor">
                        <p></p>
                    </div>
                </form>
            </div>
        </div>

        <script src="/js/jquery.min.js"></script>
        <script src="/js/jquery.cookie.js"></script>
        <script src="/js/bootstrap.min.js"></script>
        <script src="/js/wechat/jweixin-1.4.0.js"></script>
        <script src="/js/base.js"></script>
        <script src="/js/wechat/bindMobile.js"></script>
    </body>
</html>