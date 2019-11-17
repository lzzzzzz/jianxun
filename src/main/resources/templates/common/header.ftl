<#import "language.ftl" as Language>
<#assign menusBase=[{
        "zh": "首页",
        "en": "Home",
        "id": 'menu-shouye',
        "url": "${Language.lans[lan].url_path}index.html"
    },{
        "zh": "产品中心",
        "en": "Products",
        "id": "menu-chanpinzhongxin",
        "url": "${Language.lans[lan].url_path}products.html"
    },{
        "zh": "服务中心",
        "en": "Services",
        "id": "menu-fuwuzhongxin",
        "url": "#",
        "menus": [{
            "zh": "科研服务",
            "url": "#"
        },{
            "zh": "技术服务",
            "url": "#"
        },{
            "zh": "培训课堂",
            "en": "keyanfuwu",
            "url": "${Language.lans[lan].url_path}training.html"
        },{
            "zh": "常见问题",
            "en": "issue",
            "url": "${Language.lans[lan].url_path}issue.html"
        }]
    },{
        "zh": "招商合作",
        "en": "Cooperation",
        "url": "${Language.lans[lan].url_path}cooperation.html",
        "id": "menu-zhaoshanghezuo"
    },{
        "zh": "新闻中心",
        "en": "News",
        "id": "menu-xinwenzhongxin",
        "url": "${Language.lans[lan].url_path}news.html"
    },{
        "zh": "关于我们",
        "en": "About Us",
        "id": "menu-guanyuwomen",
        "url": "#",
        "menus": [{
            "zh": "党建工作",
            "en": "dangjian",
            "url": "${Language.lans[lan].url_path}partys.html"
        }, {
            "zh": "公司简介",
            "en": "company profile",
            "url": "${Language.lans[lan].url_path}profile.html"
        }, {
        "zh": "发展历程",
        "en": "history",
        "url": "${Language.lans[lan].url_path}history.html"
        }]
    },{
        "zh": "加入我们",
        "en": "Join Us",
        "id": "menu-jiaruwomen",
        "url": "#",
        "menus": [{
            "zh": "社会招聘",
            "en": 'social recruitment',
            "url": "${Language.lans[lan].url_path}offers.html"
        }, {
        "zh": "校园招聘",
        "en": 'campus recruiting',
        "url": "${Language.lans[lan].url_path}coffers.html"
        }]
}]>

<header>
    <nav class="header navbar navbar-fixed-top" id="top">
        <div class="container">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed nav-mobile" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <div class="nav-mobile-icon-default">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </div>
                    <div class="nav-mobile-icon-close">
                        <img src="http://img.bnibt.com/static/387682afdcf741f19bcce628ba54053f.png" alt="">
                    </div>
                </button><!-- button 移动端菜单按钮 -->
                <a class="navbar-brand" href="${Language.lans[lan].url_path}" title="北方所创立于1985年，隶属于中国核工业集团有限公司旗下的中国同辐股份有限公司（中国同辐，1763．HK），专注于体外诊断试剂产品的研发、生产、经营及服务，产品涵盖放射免疫、化学发光、酶联免疫、时间分辨、胶体金和生物原材料等技术平台，拥有150多项产品注册证书，形成了覆盖全国的营销网络，产品出口至亚非欧等10余个国家或地区，能够为体外诊断领域提供全面的产品解决方案和整体服务。">
                    <img src="http://img.bnibt.com/static/logo.png" alt="Brand">
                </a><!-- a Logo图片 -->
                <a href="#" class="navbar-toggle collapsed btn navbar-btn navbar-right language visible-xs-block" style="float: right;">
                    <#if lan == "en">
                        中文
                    <#else>
                        English
                    </#if>
                </a><!-- a 移动端显示中英文切换 -->
            </div>
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <div class="visible-xs-block">
                    <ul class="nav navbar-nav">
                        <div class="search-mobile">
                            <div class="search-mobile-input">
                                <input type="text" value="${Language.lans[lan].header_search_placeholder}" placeholder="${Language.lans[lan].header_search_placeholder}">
                            </div>
                            <div class="search-mobile-submit">
                                <button type="button" class="btn">
                                    <img src="http://img.bnibt.com/static/search_black.png" alt="">
                                </button>
                            </div>
                        </div>
                    </ul>
                </div>
                <ul class="nav navbar-nav visible-xs-block" id="nav-mobile">
                    <#list menusBase as menu>
                        <#if menu[lan]?? && menu.menus?exists>
                            <li class="dropdown" id="M${menu.id}">
                                <a href="${menu.url}" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                    <p>${menu[lan]}</p>
                                    <img class="down" src="http://img.bnibt.com/static/down.png" alt="">
                                    <img class="up" src="http://img.bnibt.com/static/up.png" alt="">
                                </a>
                                <ul class="dropdown-menu">
                                    <#list menu.menus as submenu>
                                        <#--<#if submenu.menus?exists>-->
                                            <#--<li class="dropdown">-->
                                                <#--<a href="${submenu.url}" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">-->
                                                    <#--${submenu[lan]}-->
                                                    <#--<span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>-->
                                                <#--</a>-->
                                                <#--<ul class="dropdown-menu submenu">-->
                                                    <#--<#list submenu.menus as item>-->
                                                        <#--<li>-->
                                                            <#--<a href="${item.url}">${item[lan]}</a>-->
                                                        <#--</li>-->
                                                    <#--</#list>-->
                                                <#--</ul>-->
                                            <#--</li>-->
                                        <#--<#else>-->
                                            <#if submenu[lan]??>
                                                <li>
                                                    <a href="${submenu.url}">${submenu[lan]}</a>
                                                </li>
                                            </#if>
                                        <#--</#if>-->
                                    </#list>
                                </ul>
                            </li>
                        <#elseif menu.id == "menu-chanpinzhongxin">
                            <#if menus??>
                                <li class="dropdown" id="M${menu.id}">
                                    <a href="${menu.url}" class="dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <p>${menu[lan]}</p>
                                        <img class="down" src="http://img.bnibt.com/static/down.png" alt="">
                                        <img class="up" src="http://img.bnibt.com/static/up.png" alt="">
                                    </a>
                                    <ul class="dropdown-menu">
                                        <#list menus as submenu>
                                            <#if submenu[lan]??>
                                            <li>
                                                <a href="${(submenu.url)!'#'}">${(submenu[lan])!'异常'}</a>
                                            </li>
                                            </#if>
                                        </#list>
                                    </ul>
                                </li>
                            <#else>
                                <li id="M${menu.id}">
                                    <a href="${menu.url}">${menu[lan]}</a>
                                </li>
                            </#if>
                        <#elseif menu[lan]??>
                            <li id="M${menu.id}">
                                <a href="${menu.url}">${menu[lan]}</a>
                            </li>
                        </#if>
                    </#list>
                </ul>
                <div class="visible-xs-block">
                    <div class="login-control">
                        <button class="btn login" data-toggle="modal" data-target="#loginModal">${Language.lans[lan].header_login}</button>
                    </div>
                    <div class="mobile-logout">
                        <button class="btn" data-toggle="modal" data-target="#loginModal">${Language.lans[lan].header_vip_center_signout}</button>
                    </div>
                </div>
                <ul class="nav navbar-nav navbar-right hidden-xs nav-control">
                    <li>
                        <a href="#" class="language">
                            <#if lan == "en">
                                中文
                            <#else>
                                English
                            </#if>
                        </a>
                    </li>
                    <li><button class="btn navbar-btn login" role="button" data-toggle="modal" data-target="#loginModal">${Language.lans[lan].header_login}</button></li>
                    <li class="vip-center">
                        <div class="btn-group">
                            <button type="button" class="btn dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                <img src="http://img.bnibt.com/static/default_chat.png" alt="">
                                <span>${Language.lans[lan].header_vip_center_title}</span>
                                <span id="newMessageCount">(9)</span>
                            </button>
                            <ul class="dropdown-menu">
                                <li><a href="${Language.lans[lan].url_path}wish.html">${Language.lans[lan].header_vip_center_wish}</a></li>
                                <li><a href="${Language.lans[lan].url_path}advisory.html">${Language.lans[lan].header_vip_center_session}<span id="messageCount"></span></a></li>
                                <li role="separator" class="divider"></li>
                                <li class="logout-btn"><a href="#">${Language.lans[lan].header_vip_center_signout}</a></li>
                            </ul>
                        </div>
                    </li>
                </ul>
                <div class="search-desktop navbar-form navbar-right hidden-xs">
                    <div class="search-desktop-input">
                        <input type="text" value="${Language.lans[lan].header_search_placeholder}" placeholder="${Language.lans[lan].header_search_placeholder}">
                    </div>

                    <button type="button" class="search-confirm">
                        <img src="http://img.bnibt.com/static/1e587615f7634f02b9050c32551c6824.png" alt="">
                    </button>
                </div>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container -->
    </nav>
    <nav class="navbar navbar-fixed-top nav-desktop hidden-xs">
        <div class="container">
            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav" id="nav-desktop">
                    <#list menusBase as menu>
                        <#if menu[lan]?? && menu.menus?exists>
                            <li class="dropdown" id="${menu.id}">
                                <a href="${menu.url}" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                    ${menu[lan]}
                                </a>
                                <ul class="dropdown-menu">
                                    <#list menu.menus as submenu>
                                        <#if submenu[lan]?? && submenu.menus?exists>
                                            <li class="dropdown">
                                                <a href="${submenu.url}" class="dropdown-toggle subdropdown" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                                    <p>${submenu[lan]}</p>
                                                    <img src="http://img.bnibt.com/static/r_black.png" alt="">
                                                </a>
                                                <ul class="dropdown-menu submenu">
                                                    <#list submenu.menus as item>
                                                        <#if item[lan]??>
                                                            <li>
                                                                <a href="${item.url}">${item[lan]}</a>
                                                            </li>
                                                        </#if>
                                                    </#list>
                                                </ul>
                                            </li>
                                        <#elseif submenu[lan]??>
                                            <li>
                                                <a href="${(submenu.url)!'#'}">${(submenu[lan])!'异常'}</a>
                                            </li>
                                        </#if>
                                    </#list>
                                </ul>
                            </li>
                        <#elseif menu.id == "menu-chanpinzhongxin">
                            <#if menus??>
                                <li class="dropdown" id="${menu.id}">
                                    <a href="${menu.url}" class="dropdown-toggle">
                                        ${menu[lan]}
                                    </a>
                                    <ul class="dropdown-menu">
                                        <#list menus as submenu>
                                            <#if submenu.menus?exists>
                                                <li class="dropdown">
                                                    <a href="${submenu.url}" class="dropdown-toggle subdropdown" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
                                                        <p>${submenu[lan]}</p>
                                                        <img src="http://img.bnibt.com/static/r_black.png" alt="">
                                                    </a>
                                                    <ul class="dropdown-menu submenu">
                                                        <#list submenu.menus as item>
                                                            <li>
                                                                <a href="${item.url}">${item[lan]}</a>
                                                            </li>
                                                        </#list>
                                                    </ul>
                                                </li>
                                            <#else>
                                                <li>
                                                    <a href="${(submenu.url)!'#'}">${(submenu[lan])!'异常'}</a>
                                                </li>
                                            </#if>
                                        </#list>
                                    </ul>
                                </li>
                            <#else >
                                <li id="${menu.id}">
                                    <a href="${menu.url}">${menu[lan]}</a>
                                </li>
                            </#if>
                        <#elseif menu[lan]??>
                            <li id="${menu.id}">
                                <a href="${menu.url}">${menu[lan]}</a>
                            </li>
                        </#if>
                    </#list>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container -->
    </nav>
</header>
<!-- 登录弹窗 -->
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">${Language.lans[lan].login_title_close}</span></button>
                <h4 class="modal-title" id="loginModalLabel">${Language.lans[lan].login_title_text}</h4>
            </div>
            <div class="modal-body">
                <div class="login-container phone">
                    <form action="/index.html" class="bs-example" method="post">
                        <div class="login-field">
                            <input name="phone" type="text" value="${Language.lans[lan].login_phone_placeholder}" placeholder="${Language.lans[lan].login_phone_placeholder}">
                        </div>
                        <div class="login-field">
                            <div class="login-field-input">
                                <input name="captcha" type="text" value="${Language.lans[lan].login_captcha_placeholder}" placeholder="${Language.lans[lan].login_captcha_placeholder}">
                            </div>
                            <div class="login-field-sendCode">
                                <button id="sendCode" type="button">${Language.lans[lan].login_captcha_button}</button>
                            </div>
                        </div>
                        <div class="error-infor"><p></p></div>
                        <div class="login-control">
                            <button type="button" class="btn login-btn">${Language.lans[lan].login_login_button}</button>
                            <#--<img class="wechat-login" src="http://img.bnibt.com/static/wechat.png" alt="">-->
                            <#--<p class="wechat-login-p">${Language.lans[lan].login_login_wechat}</p>-->
                        </div>
                    </form>
                    <#--<div class="wechat-qrcode">-->
                        <#--<div id="wechat-login-container"></div>-->
                        <#--<p>-->
                            <#--<button class="use-phone">${Language.lans[lan].login_login_phone}</button>-->
                        <#--</p>-->
                    <#--</div>-->
                </div>
            </div>
        </div>
    </div>
</div>
<div class="nav-stacked">
    <div class="stacked-item stacked-advisory">
        <img src="http://img.bnibt.com/static/flow_chat.png" alt="我的咨询">
        <div class="had-new">
            1
        </div>
    </div>
    <div class="stacked-item booking-line">
        <img src="http://img.bnibt.com/static/flow_call.png" alt="预约电话">
    </div>
    <div class="stacked-item stacked-vip-center">
        <img src="http://img.bnibt.com/static/flow_vip.png" alt="我的心愿单">
        <#--<div class="vip-center-container">-->
            <#--<p class="action"><a href="${Language.lans[lan].url_path}wish.html">我的心愿单</a></p>-->
            <#--<p class="action"><a href="${Language.lans[lan].url_path}advisory.html">我的咨询</a></p>-->
            <#--<p class="divider"></p>-->
            <#--<p class="action logout-btn"><a href="#">退出登录</a></p>-->
        <#--</div>-->
    </div>
    <div class="stacked-item gotop">
        <img src="http://img.bnibt.com/static/flow_top.png" alt="回到顶部">
    </div>
</div>

<!-- 预约电话弹窗 -->
<div class="modal fade" id="bookingLineModal" tabindex="-1" role="dialog" aria-labelledby="bookingLineModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">关闭</span></button>
                <h4 class="modal-title" id="bookingLineModalLabel">预约电话</h4>
            </div>
            <div class="modal-body">
                <p class="mobile">
                    预约电话：<a href="tel:800-8100107">800-8100107</a>
                </p>
            </div>
        </div>
    </div>
</div>

<!-- 新消息弹窗 -->
<div class="modal fade" id="newMessageModal" tabindex="-1" role="dialog" aria-labelledby="newMessageModalLabel">
    <div class="modal-dialog modal-sm" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">关闭</span></button>
                <h4 class="modal-title" id="newMessageModalLabel">新的回复消息</h4>
            </div>
            <div class="modal-body">
                <p>您有新的<a href="${Language.lans[lan].url_path}advisory.html">消息回复</a></p>
            </div>
        </div>
    </div>
</div>