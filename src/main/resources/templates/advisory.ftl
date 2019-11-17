<#import 'common/base.ftl' as base>
<#import 'common/breadcrumb.ftl' as breadcrumb>
<#import "common/language.ftl" as Language>
<@base.base_container
title="我的咨询-北方所"
headers=[
"/css/breadcrumb.css",
"/css/advisory.css"
]
scripts=[
"/js/advisory.js"
]>
    <div class="advisory">
        <@breadcrumb.breadcrumb paths=[
        {
        "url": "${Language.lans[lan].url_path}index.html",
        "zh": "首页",
        "en": "Home"
        }, {
        "url": "${Language.lans[lan].url_path}advisory.html",
        "zh": "我的咨询",
        "en": "Advisory"
        }] />
       <div class="container">
           <div class="advisory-list">
               <#if sessionList?? && sessionList?size gt 0>
                   <div class="advisory-list-container">
                       <#list sessionList as session>
                           <div class="advisory-item">
                               <div class="advisory-item-container">
                                   <div class="staff-infor">
                                       <div class="infor-container">
                                           <div class="staff-banner">
                                               <#if session.salesAvatarUrl?? && session.salesAvatarUrl?has_content>
                                                   <img src="${session.salesAvatarUrl}" alt="">
                                               <#else>
                                                   <img src="http://img.bnibt.com/static/offical.png" alt="">
                                               </#if>

                                           </div>
                                           <div class="staff-detail">
                                               <h2 class="staff-title">
                                                   ${session.salesName}
                                               </h2>
                                               <#if (session.message)?? && (session.message.content)?has_content>
                                                   <p class="staff-message">${session.message.content}</p>
                                               </#if>
                                               <p class="staff-other">地区：${(session.salesDistrictName)!'无'}</p>
                                           </div>
                                       </div>
                                   </div>
                                   <div class="staff-advisory">
                                       <button data-content="${session.id}" class="btn btn-advisory visible-xs-block" role="button" data-toggle="modal" data-target="#advisoryModal">
                                           <#if session.unreadCount gt 0>
                                               记录<span>(${session.unreadCount})</span>
                                           <#else>
                                               记录<span></span>
                                           </#if>
                                       </button>
                                       <button data-content="${session.id}" class="btn btn-advisory hidden-xs" role="button" data-toggle="modal" data-target="#advisoryModal">
                                           <#if session.unreadCount gt 0>
                                               查看沟通记录<span>(${session.unreadCount})</span>
                                           <#else>
                                               查看沟通记录<span></span>
                                           </#if>
                                       </button>
                                   </div>
                               </div>
                           </div>
                       </#list>
                   </div>
                   <div class="mobile-more advisory-more visible-xs-block">
                       <a href="#" class="btn previous">上一页</a>
                       <a href="#" class="btn next">下一页</a>
                   </div>
                   <div class="advisory-pages hidden-xs">
                       <nav aria-label="Page navigation">
                           <ul class="pagination" content="${page.page?c}/${page.totalPage?c}/${page.limit?c}/${page.totalItem?c}">
                               <div class="page-total">
                                   <p>${page.totalItem?c}条数据，共${page.totalPage?c}页</p>
                               </div>
                               <li class="previous-page">
                            <span aria-label="Previous">
                                上一页
                            </span>
                               </li>
                               <#if page.totalItem != 0>
                                   <#list 1..page.totalPage as item>
                                       <li class="${(page.page == item)?string("active", "")} pageIndex">
                                           <a role="button" href="#">${item?c}</a>
                                       </li>
                                   </#list>
                               </#if>
                               <li class="next-page ${(page.page == page.totalPage)?string("disabled", "")}">
                                   <a href="#" aria-label="Next">
                                       下一页
                                   </a>
                               </li>
                           </ul>
                       </nav>
                   </div>
               <#else>
                   <div class="advisory-none">
                       <img src="http://img.bnibt.com/static/img_none.png" alt="">
                       <p>暂无相关内容</p>
                   </div>
               </#if>
           </div>
       </div>

    </div>
    <!-- 删除单个弹窗 -->
    <div class="modal fade" id="deleteOneModal" tabindex="-1" role="dialog" aria-labelledby="deleteOneModalLabel">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <img src="http://img.bnibt.com/static/delete_icon.png" alt="">
                    <p>确定删除该商品吗</p>
                </div>
                <div class="modal-footer">
                    <button class="btn delete-cancel" data-dismiss="modal" aria-label="Close">取消</button>
                    <button class="btn delete-confirm" data-dismiss="modal" aria-label="Close">删除</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 删除全部弹窗 -->
    <div class="modal fade" id="deleteAllModal" tabindex="-1" role="dialog" aria-labelledby="deleteAllModalLabel">
        <div class="modal-dialog modal-sm" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <img src="http://img.bnibt.com/static/delete_icon.png" alt="">
                    <p>确定删除全部商品吗</p>
                </div>
                <div class="modal-footer">
                    <button class="btn delete-cancel" data-dismiss="modal" aria-label="Close">取消</button>
                    <button class="btn delete-confirm" data-dismiss="modal" aria-label="Close">删除</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 咨询弹窗 -->
    <div class="modal fade" id="advisoryModal" tabindex="-1" role="dialog" aria-labelledby="advisoryModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">关闭</span></button>
                    <h4 class="modal-title" id="advisoryModalLabel">在线咨询</h4>
                </div>
                <div class="modal-body">
                    <div class="advisory-msg">
                        <div class="message-load">
                            <img src="http://img.bnibt.com/static/loading2.gif" alt="">
                        </div>
                        <div class="msg-list">
                        </div>
                    </div>
                    <div class="advisory-msg-control">
                        <div class="send-input">
                            <input type="text" class="form-control" id="messageText" value="请在这里输入..." placeholder="请在这里输入...">
                        </div>
                        <div class="send-btn">
                            <button class="btn send" role="button">发送</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@base.base_container>